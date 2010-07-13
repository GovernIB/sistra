package org.ibit.rol.form.back.action.pantalla;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.AyudaForm;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.TraAyudaPantalla;
import org.ibit.rol.form.model.TraPerfilUsuario;
import org.ibit.rol.form.persistence.delegate.AyudaDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;

/**
 * Action para preparar el alta de una Pantalla.
 *
 * @struts.action
 *  name="ayudaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/ayuda/editar"
 *  parameter="editar"
 *
 * @struts.action
 *  name="ayudaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/ayuda/consulta"
 *  parameter="consulta"
 *
 * @struts.action
 *  name="ayudaForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/ayuda/baja"
 *  parameter="baja"
 *
 * @struts.action-forward
 *  name="editar" path="/pantalla/editarAyuda.jsp"
 *
 * @struts.action-forward
 *  name="consulta" path="/pantalla/consultaAyuda.jsp"
 *
 * @struts.action-forward
 *  name="reload" path=".pantalla.editar"
 */
public class AyudaPantallaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(AyudaPantallaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.debug("Entramos en AyudaPantalla");
        String param = mapping.getParameter();
        String lang = request.getParameter("lang");
        AyudaDelegate ayudaDelegate = DelegateUtil.getAyudaDelegate();
        AyudaForm ayudaForm = (AyudaForm) form;
        ayudaForm.setLang(lang);
        AyudaPantalla ayudaPantalla = (AyudaPantalla) ayudaForm.getValues();

        if (request.getParameter("idPantalla") !=null && request.getParameter("idPerfil") != null){
            log.debug("Entramos aqui");
            ayudaForm.setIdPantalla(new Long(request.getParameter("idPantalla")));
            ayudaForm.setIdPerfil(new Long(request.getParameter("idPerfil")));
        }

        if ("baja".equals(param)){
            log.debug("Entramos en baja");
            ayudaPantalla = ayudaDelegate.obtenerAyudaPantallaPerfil(ayudaForm.getIdPantalla(), ayudaForm.getIdPerfil());
            String nombre = ((TraPerfilUsuario) ayudaPantalla.getPerfil().getTraduccion()).getNombre();

            ayudaPantalla.setTraduccion(lang, null);
            ayudaDelegate.gravarAyuda(ayudaPantalla, ayudaForm.getIdPantalla(), ayudaForm.getIdPerfil());

           request.setAttribute("confirm", nombre);
            return mapping.findForward("reload");
        }

        if ("editar".equals(param)){
            log.debug("Entramos en editar");

            if (isCancelled(request)){
                return null;
            }

            if (isModificacion(request)){
                ayudaDelegate.gravarAyuda(ayudaPantalla, ayudaForm.getIdPantalla(), ayudaForm.getIdPerfil());
                log.debug("Creat/Actualitzat " + ayudaPantalla.getId());
                request.setAttribute("mostrar", "true" );
                return mapping.findForward("consulta");
            }

            ayudaForm.setValues(new AyudaPantalla());
            ayudaPantalla = ayudaDelegate.obtenerAyudaPantallaPerfil(ayudaForm.getIdPantalla(), ayudaForm.getIdPerfil());
            if (ayudaPantalla != null){
                ayudaForm.setValues(ayudaPantalla);
            }
            return mapping.findForward("editar");
        }

        if ("consulta".equals(param)){
            log.debug("Entramos en consulta");
            boolean present = false;

            ayudaForm.setValues(null);
            ayudaPantalla = ayudaDelegate.obtenerAyudaPantallaPerfil(ayudaForm.getIdPantalla(), ayudaForm.getIdPerfil());
            if (ayudaPantalla != null){
                ayudaForm.setValues(ayudaPantalla);
                log.debug("Idioma " + ayudaForm.getLang());
                TraAyudaPantalla traAyudaPantalla = (TraAyudaPantalla) ayudaForm.getTraduccion();
                if (traAyudaPantalla.getDescripcionCorta() != null) present = true;
                if (traAyudaPantalla.getDescripcionLarga() != null) present = true;
                if (traAyudaPantalla.getUrlSonido() != null) present = true;
                if (traAyudaPantalla.getUrlVideo() != null) present = true;
                if (traAyudaPantalla.getUrlWeb() != null) present = true;
                if (present) request.setAttribute("mostrar", "true" );
            }
            return mapping.findForward("consulta");
        }

        return null;
    }
}
