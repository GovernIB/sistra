package es.caib.redose.back.action.modelo;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.model.Modelo;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.ModeloDelegate;
import es.caib.redose.persistence.delegate.VersionDelegate;

/**
 * Action para preparar borrar un Modelo.
 *
 * @struts.action
 *  name="modeloForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/modelo/baja"
 *
 * @struts.action-forward
 *  name="success" path=".modelo.lista"
 */
public class BajaModeloAction extends BaseAction{
    protected static Log log = LogFactory.getLog(BajaModeloAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en BajaModelo");
        ModeloDelegate modeloDelegate = DelegateUtil.getModeloDelegate();
        VersionDelegate versionDelegate = DelegateUtil.getVersionDelegate();

        String idString = request.getParameter("codigo");
        if (idString == null || idString.length() == 0) {
            log.warn("El paràmetre codigo és null!!");
            return mapping.findForward("fail");
        }

        Long id = new Long(idString);
        
        // Comprobamos si podemos borrar el modelo (no hay documentos de ese modelo en el RDS)
        Modelo modelo = modeloDelegate.obtenerModelo(id);
        for (Iterator it = modelo.getVersiones().iterator();it.hasNext();){
        	if (!versionDelegate.puedoBorrarVersion(((Version) it.next()).getCodigo()))  {
        		ActionErrors messages = new ActionErrors();
            	//messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.elementoNoPuedeBorrarse"));        	
            	messages.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.elementoNoPuedeBorrarse"));
            	saveErrors(request,messages);  
            	return mapping.findForward("success");        		
        	}
        }
        
        
        modeloDelegate.borrarModelo(id);
        request.setAttribute("reloadMenu", "true");

        return mapping.findForward("success");
    }
}
