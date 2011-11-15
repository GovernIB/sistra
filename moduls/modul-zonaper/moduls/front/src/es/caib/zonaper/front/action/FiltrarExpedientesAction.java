package es.caib.zonaper.front.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.form.FiltroExpedientesForm;
import es.caib.zonaper.model.CodigoElementoExpediente;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.IndiceElemento;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * @struts.action
 *  name="filtroExpedientesForm"
 *  path="/protected/filtrarExpedientes"
 *  scope="session"
 *  
 * @struts.action-forward
 *  name="success" path=".estadoExpedientes"
 *  
 */
public class FiltrarExpedientesAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Obtenemos palabra de filtrado
		FiltroExpedientesForm filtroForm = (FiltroExpedientesForm) form;
		String strFiltro = filtroForm.getFiltro();
		
		if (StringUtils.isBlank(strFiltro)) {
			// Cadena vacía -> limpia el filtro 
			request.getSession().removeAttribute(Constants.FILTRO_KEY); 
			request.getSession().removeAttribute(Constants.FILTRO_LISTA_KEY);	
			request.getSession().removeAttribute(Constants.PAGE_KEY);			
		} else {
			// Realizamos filtrado
			//	- Comprobamos si se accede de forma delegada 
			String nif;
			DatosSesion datosSesion = this.getDatosSesion( request );
			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(datosSesion.getPerfilAcceso())){
				nif = this.getDatosSesion(request).getNifEntidad();
			}else{
				nif = this.getDatosSesion(request).getNifUsuario();
			}
			
			//	- Buscamos en tabla de indices por la palabra
			List listaInd = DelegateUtil.getIndiceElementoDelegate().buscarIndice(nif, strFiltro);
			if (listaInd == null) {
				listaInd = new ArrayList();
			}
			
			// - Buscamos ids de expedientes asociados a los indices que no son directamente de expediente 
			List listaCods = new ArrayList();
			for (Iterator it = listaInd.iterator(); it.hasNext();) {
				IndiceElemento ie = (IndiceElemento) it.next();
				if (!ie.getTipoElemento().equals(IndiceElemento.TIPO_EXPEDIENTE)) {
					 listaCods.add(new CodigoElementoExpediente(ie.getTipoElemento(), ie.getCodigoElemento()));
				}
			}
			Map listaIdExpe = DelegateUtil.getElementoExpedienteDelegate().obtenerIdsExpedienteElementos(listaCods);
			
			//	- Construimos lista de filtro con los códigos de expedientes y trámites sin expediente 
			List filtroExpe = new ArrayList();
			//		* Añadimos a la lista los indices de tipo expediente
			for (Iterator it = listaInd.iterator(); it.hasNext();) {
				IndiceElemento ie = (IndiceElemento) it.next();
				if (ie.getTipoElemento().equals(IndiceElemento.TIPO_EXPEDIENTE)) {
					filtroExpe.add("E" + ie.getCodigoElemento());
				} 
			}
			//	 * Añadimos a la lista los expedientes asociados a los otros indices
			for (Iterator it = listaCods.iterator(); it.hasNext();) {
				CodigoElementoExpediente ie = (CodigoElementoExpediente) it.next();
				Long idExpe = (Long) listaIdExpe.get(ie);
				if (idExpe != null) {
					filtroExpe.add("E" + idExpe);
				} else {
					if (ie.getTipo().equals(ElementoExpediente.TIPO_ENTRADA_TELEMATICA)) {
						filtroExpe.add("T" + ie.getCodigo());
					} else if (ie.getTipo().equals(ElementoExpediente.TIPO_ENTRADA_PREREGISTRO)) {
						filtroExpe.add("P" + ie.getCodigo());
					} 
				}				
			}	
			
			// - Guardamos en sesión la palabra de filtro y la lista de expedientes
			request.getSession().setAttribute(Constants.FILTRO_KEY, strFiltro); 
			request.getSession().setAttribute(Constants.FILTRO_LISTA_KEY, filtroExpe);	
			request.getSession().removeAttribute(Constants.PAGE_KEY); // Quitamos pagina para empezar desde el principio
		}
		
		// Redirigimos a lista expedientes
		return mapping.findForward("success");
		
		
	}

}
