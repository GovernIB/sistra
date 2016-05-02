package org.ibit.rol.form.front.action.telematic;

import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action
 *  name="iniciTelematicForm"
 *  path="/iniciTelematic"
 *  scope="request"
 *  validate="false"
 */
public class IniciTelematicAction extends BaseAction {

    protected static Log log = LogFactory.getLog(IniciTelematicAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        IniciTelematicForm itForm = (IniciTelematicForm) form;

        boolean debugEnabled = "true".equals(itForm.getDebugEnabled());

        if (debugEnabled) {
	        log.debug("Recibida petición telemática");
	        log.debug("XML Data: " + itForm.getXmlData());
	        log.debug("XML Config: " + itForm.getXmlConfig());
        }

        InstanciaTelematicaDelegate delegate = DelegateUtil.getInstanciaTelematica();

        delegate.create(itForm.getXmlConfig(), itForm.getXmlData(), debugEnabled);

        // Se ha inicializado con exito. Registramos la instáncia.
        String token = RegistroManager.preregistrarInstancia(request, delegate);
        byte[] tokenBytes = token.getBytes("UTF-8");

        response.reset();
        response.setContentLength(tokenBytes.length);
        response.setContentType("text/plain; charset=UTF-8");
        response.getOutputStream().write(tokenBytes);
        response.flushBuffer();
        return null;
    }

}
