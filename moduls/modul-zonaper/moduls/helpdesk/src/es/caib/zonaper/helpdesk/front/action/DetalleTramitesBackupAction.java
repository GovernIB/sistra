package es.caib.zonaper.helpdesk.front.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.DetalleTramiteForm;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;

/**
 * @struts.action
 *  name="detalleTramiteForm"
 *  path="/detalleTramiteBackup"
 *  scope="request"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".detalleTramitesBackup"
 *
 * @struts.action-forward
 *  name="fail" path=".backup"
 */
public class DetalleTramitesBackupAction extends BaseAction
{
	protected static Log log = LogFactory.getLog(DetalleTramitesBackupAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		DetalleTramiteForm detalleFrom = ( DetalleTramiteForm ) form;
		ActionErrors messages = new ActionErrors();
		try{
			request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.BACKUP_TAB);		
			String clave = detalleFrom.getClaveTramite();
			TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
			TramitePersistente tp = td.obtenerTramitePersistenteBackup(clave);
			if(tp == null){
				messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("resultadoBusquedaEstado.informacionEstado.N"));
				saveErrors(request,messages);
				return mapping.findForward( "fail" );
			}else{
//				 Buscamos usuario seycon asociado
				if(tp.getNivelAutenticacion() != Constants.MODO_AUTENTICACION_ANONIMO){
					PersonaPAD persona = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(tp.getUsuario());
					if (persona != null){
						request.setAttribute("nombreCompleto",persona.getNombreCompleto());
					}
				}
				request.setAttribute("tramiteBackup",tp);
			}
		}catch(Exception e){
			messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.abrir.tramite"));
			saveErrors(request,messages);
			return mapping.findForward( "fail" );
		}
		return mapping.findForward( "success" );
    }

    		
}
