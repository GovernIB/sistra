package es.caib.bantel.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.bantel.front.Constants;
import es.caib.bantel.front.form.DetalleAvisoForm;
import es.caib.bantel.front.util.MensajesUtil;
import es.caib.zonaper.modelInterfaz.ExpedientePAD;
import es.caib.zonaper.persistence.delegate.PadBackOfficeDelegate;

/**
 * @struts.action
 *  name="detalleAvisoForm"
 *  path="/altaAviso"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".altaAviso"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class AltaAvisoAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(AltaAvisoAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		DetalleAvisoForm 		avisoForm = (DetalleAvisoForm)form;
		PadBackOfficeDelegate 	ejb = new PadBackOfficeDelegate();
		ExpedientePAD 			exp;
		
		MensajesUtil.setMsg(this.getResources(request));
		request.getSession().setAttribute("documentosAltaAviso",null);
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,"3");
		
		try{
			
			// Recuperamos de sesion el expediente actual
			String idExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_IDENTIFICADOR_KEY);
			Long uniAdm = (Long) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_UNIDADADMIN_KEY);
			String claveExpe = (String) request.getSession().getAttribute(Constants.EXPEDIENTE_ACTUAL_CLAVE_KEY);
			
			// Recuperamos expediente
			exp = ejb.consultaExpediente(uniAdm, idExpe,claveExpe);
			
			if(exp != null){
				// Establecemos descripcion expediente e idioma
				avisoForm.setDescripcionExpediente(exp.getDescripcion());
				avisoForm.setIdioma(exp.getIdioma());				
			}else{
				throw new Exception("No se ha encontrado expediente");
			}
			
		}catch(Exception e){
			log.error("Excepcion alta aviso",e);
			String mensajeOk = MensajesUtil.getValue("error.aviso.Excepcion") + ": " + e.getMessage();
			request.setAttribute( Constants.MESSAGE_KEY,mensajeOk);
			return mapping.findForward("fail");
		}
		
		
		return mapping.findForward( "success" );
    }
}
