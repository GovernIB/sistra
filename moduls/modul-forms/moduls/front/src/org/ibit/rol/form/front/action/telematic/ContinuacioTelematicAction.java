package org.ibit.rol.form.front.action.telematic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.Constants;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;

/**
 * @struts.action path="/continuacioTelematic"
 * @struts.action path="/auth/continuacioTelematic"
 * @struts.action path="/secure/continuacioTelematic"
 * @struts.action path="/auth/secure/continuacioTelematic"
 */
public class ContinuacioTelematicAction extends BaseAction {

    protected static Log log = LogFactory.getLog(ContinuacioTelematicAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String idInstancia = request.getParameter(RegistroManager.ID_INSTANCIA);

        boolean success = RegistroManager.asignarInstanciaPreregistrada(request, idInstancia);

        if (!success) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.telematico"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        setLocale(request, delegate.obtenerIdioma());

        boolean debugEnabled = delegate.isDebugEnabled();

        if (debugEnabled) {
        	log.debug("Recibida continuación telemática: " + idInstancia);
        }

        // Inicializamos ayuda
        request.getSession().setAttribute(Constants.AYUDA_ACTIVADA_KEY, "true");
        
        // Inicializamos info organismo por entidad
        Map propiedadesForm = ((InstanciaTelematicaDelegate) delegate).obtenerPropiedadesFormulario();
        if (propiedadesForm.get("entidad") != null) {
        	request.getSession().setAttribute(Constants.ORGANISMO_INFO_KEY, DelegateUtil.getConfiguracionDelegate().obtenerOrganismoInfo((String) propiedadesForm.get("entidad")));
        }

        response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + "/ver.do"));
        response.flushBuffer();
        return null;
    }

}
