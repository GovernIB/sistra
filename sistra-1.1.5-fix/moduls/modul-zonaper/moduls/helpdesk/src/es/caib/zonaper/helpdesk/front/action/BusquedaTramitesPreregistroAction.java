package es.caib.zonaper.helpdesk.front.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.BusquedaTramitePreregistroForm;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;

/**
 * @struts.action
 *  name="busquedaTramitePreregistroForm"
 *  path="/busquedaTramitesPreregistro"
 *  scope="request"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaTramitesPreregistro"
 *
 */
public class BusquedaTramitesPreregistroAction extends BaseAction
{
	
	protected static Log log = LogFactory.getLog(BusquedaTramitesPreregistroAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		BusquedaTramitePreregistroForm preregistroForm = ( BusquedaTramitePreregistroForm ) form;
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.PREREGISTRO_TAB);		
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicial = df.parse(preregistroForm.getFechaInicial());
		Date fechaFinal = df.parse(preregistroForm.getFechaFinal());
		String modelo = preregistroForm.getModelo();
		String caducidad = preregistroForm.getCaducidad(); 
		char estadoConfirmacion = preregistroForm.getEstadoConfirmacion();
		String tipo = preregistroForm.getTipo();
		String nivel = preregistroForm.getNivel();
		
		EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
		try {
			List result;
			switch (estadoConfirmacion) {
				case 'S' :
					result = epd.listarEntradaPreregistrosConfirmados(fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel);
					break;
				case 'N' :
					result = epd.listarEntradaPreregistrosNoConfirmados(fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel);
					break;
				default :
					result = epd.listarEntradaPreregistros(fechaInicial, fechaFinal, modelo, caducidad, tipo, nivel);
					break;
			}
				
			request.setAttribute( "lstTramites", result);
			return mapping.findForward( "success" );
		} catch (DelegateException e) {
			request.setAttribute("estado", "X");
			return mapping.findForward( "success" );
		}

    }

	/**
	 *
	 * @param form
	 * @return
	 */
//	private Hashtable crearFiltro(BusquedaTramitePreregistroForm form) throws ParseException {
//		Hashtable filtro = new Hashtable();
//		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//		filtro.put(Constants.KEY_MODELO, form.getModelo());
//		filtro.put(Constants.KEY_FECHA_INICIO, df.parse(form.getFechaInicial()));
//		filtro.put(Constants.KEY_FECHA_FIN, df.parse(form.getFechaFinal()));
//		filtro.put(Constants.KEY_CADUCIDAD, form.getCaducidad());
//		filtro.put(Constants.KEY_TIPO, form.getTipo());
//		filtro.put(Constants.KEY_NIVEL, form.getNivel());
//		return filtro;
//	}
   
}
