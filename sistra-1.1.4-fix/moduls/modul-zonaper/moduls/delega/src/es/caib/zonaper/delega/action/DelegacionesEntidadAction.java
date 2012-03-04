package es.caib.zonaper.delega.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.delega.form.DetalleEntidadForm;
import es.caib.zonaper.delega.util.DelegacionFront;
import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegacionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;

/**
 * @struts.action
 * 	name="detalleEntidadForm"
 *  path="/delegacionesEntidad"
 *  validate="true"
 *  
 * @struts.action-forward
 *  name="success" path=".delegacionesManejar"
 *
 * @struts.action-forward
 *  name="fail" path=".mainLayout"
 */
public class DelegacionesEntidadAction extends BaseAction
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleEntidadForm entForm = (DetalleEntidadForm)form;
		String nif = request.getParameter("nif");
		if(StringUtils.isBlank(nif) && entForm.getPersona() != null){
			nif = entForm.getPersona().getNif();
		}
		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
		DelegacionDelegate deleg = DelegateUtil.getDelegacionDelegate();
		if(!StringUtils.isBlank(nif)){
			try{
				//solo nos devolvera una, ya que el nif es primary key
				List personas = padAplic.buscarEntidades(nif);
				if(personas != null && personas.size() > 0){
					PersonaPAD persona = (PersonaPAD)personas.get(0); 
					entForm.setPersona(persona);
					entForm.setModificacio("S");
					request.setAttribute("provinciaEntidad",persona.getProvincia());
					request.setAttribute("delegaciones",crearDelegacionesFront(deleg.obtenerDelegacionesEntidad(persona.getNif())));
				}else{
					ActionErrors messages = new ActionErrors();
		        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.nifDelegante.no.existe"));
		        	saveErrors(request,messages);  
					return mapping.findForward( "fail" );
				}
				return mapping.findForward( "success" );
			}catch(Exception e){
				ActionErrors messages = new ActionErrors();
	        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.recuperar.entidad"));
	        	saveErrors(request,messages);  
				return mapping.findForward( "fail" );
			}
		}else{
			ActionErrors messages = new ActionErrors();
        	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.nif.entidad.vacio"));
        	saveErrors(request,messages);  
			return mapping.findForward( "fail" );
		}
    }
	
	public List crearDelegacionesFront(List delegaciones) throws Exception{
		List delegacionesFront = new ArrayList();
		ConsultaPADDelegate consulta = DelegateUtil.getConsultaPADDelegate();
		for(int i=0;i<delegaciones.size();i++){
			DelegacionFront delFront = delToDelFront((Delegacion)delegaciones.get(i));
			delFront.setNombreDelegado(consulta.obtenerDatosPADporNif(delFront.getNifDelegado()).getNombreCompleto());
			delegacionesFront.add(delFront);
		}
		return delegacionesFront;
	}
	
	private DelegacionFront delToDelFront(Delegacion del){
		DelegacionFront delFront = new DelegacionFront();
		delFront.setAnulada(del.getAnulada());
		delFront.setClaveRdsDocDelegacion(del.getClaveRdsDocDelegacion());
		delFront.setCodigo(del.getCodigo());
		delFront.setCodigoRdsDocDelegacion(del.getCodigoRdsDocDelegacion());
		delFront.setFechaFinDelegacion(del.getFechaFinDelegacion());
		delFront.setFechaInicioDelegacion(del.getFechaInicioDelegacion());
		delFront.setNifDelegado(del.getNifDelegado());
		delFront.setNifDelegante(del.getNifDelegante());
		delFront.setPermisos(del.getPermisos());
		return delFront;
	}	
}
