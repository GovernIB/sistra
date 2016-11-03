/*
 * 
 */
package es.caib.consola.controller;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.Locales;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Span;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Controlador de la barra de usuario: configuración, mensajes, soporte y cerrar
 * sesión.
 * 
 * @author Indra
 * 
 */
@SuppressWarnings("serial")
public class PaginaPrincipalController extends BaseComposer {

    /**
     * Atributo idiomas.
     */
    private Div idiomas;

    /**
     * Atributo idiomas2.
     */
    private Div idiomas2;

    /**
     * Atributo item configuracion.
     */
    private Menuitem itemConfiguracion;

    /**
     * Atributo item mensajes.
     */
    private Menuitem itemMensajes;

    /**
     * Atributo username.
     */
    private Label username;
    
    /**
     * Atributo perfil de PaginaPrincipalController.
     */
    private Label perfil;

    /**
     * Atributo usuario logado.
     */
    private Usuario usuarioLogado;
    
    /**
     * Atributo entorno de PaginaPrincipalController.
     */
    private Div entorno;
    
    /**
     * Atributo label entorno de PaginaPrincipalController.
     */
    private Label labelEntorno;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component compPrincipal) {
        super.doAfterCompose(compPrincipal);
       
        usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        username.setValue(usuarioLogado.getNombreCompleto());
        //perfil.setValue("(" + usuarioLogado.getRol() + ")");
        
        labelEntorno.setValue(usuarioLogado.getEntorno());
        if ("PRODUCCION".equals(usuarioLogado.getEntorno())) {
        	labelEntorno.setValue(Labels.getLabel("entorno.produccion"));
        	labelEntorno.setSclass("entorn entorn-pr");
        } else {
        	labelEntorno.setValue(Labels.getLabel("entorno.desarrollo"));
        	labelEntorno.setSclass("entorn entorn-de");
        }
        
        // TODO QUITAR MENSAJES
        itemMensajes.setLabel(Labels.getLabel("menu.mensajes"));
        
        // Hay que mirar si tiene permiso para realizar la operaciones de
        // configuración
        // TODO SI ROLE ADMIN, PERMITIR OPERACION
        itemConfiguracion.setVisible(true);
       
        if (usuarioLogado.getIdioma() != null) {
            if (usuarioLogado.getIdioma().equals(ConstantesWEB.ESPANYOL)) {
                idiomas.setVisible(true);
                idiomas2.setVisible(false);
            } else {
                idiomas.setVisible(false);
                idiomas2.setVisible(true);
            }
        }
        
    }

	

    /**
     * Método para On client info de la clase PaginaPrincipalController.
     * 
     * @param event
     *            Parámetro event
     */
    public final void onClientInfo(final ClientInfoEvent event) {
        session.setAttribute("DesktopHeight", event.getDesktopHeight());
        session.setAttribute("DesktopWidth", event.getDesktopWidth());
    }

    /**
     * Método para On click$castellano de la clase PaginaPrincipalController.
     * 
     * @param e
     *            Parámetro e
     */
    public final void onClick$castellano(final Event e) {
        final Event ev = ((ForwardEvent) e).getOrigin();
        if (!(ev.getTarget() instanceof Span)) {
            changeIdioma("es_ES");
        }
    }

    /**
     * Método para On click$valenciano de la clase PaginaPrincipalController.
     * 
     * @param e
     *            Parámetro e
     */
    public final void onClick$catalan(final Event e) {
        final Event ev = ((ForwardEvent) e).getOrigin();
        if (!(ev.getTarget() instanceof Span)) {
            changeIdioma("ca_ES");
        }
    }

    /**
     * Método para Change idioma de la clase PaginaPrincipalController.
     * 
     * @param idioma
     *            Parámetro idioma
     */
    public final void changeIdioma(final String idioma) {
        String localeValue = idioma;
        if (!StringUtils.isEmpty(localeValue)) {
            // BUSCAR IDIOMA COINCIDENTE
            if (localeValue.substring(0, 2).equalsIgnoreCase(ConstantesWEB.ESPANYOL)) {
                usuarioLogado.setIdioma(ConstantesWEB.ESPANYOL);
            } else if (localeValue.substring(0, 2).equalsIgnoreCase("ca")) {
                usuarioLogado.setIdioma(ConstantesWEB.CATALAN);
            } else {
                // IDIOMA DEFECTO
                usuarioLogado.setIdioma(ConstantesWEB.ESPANYOL);
            }
        } else {
            localeValue = "ca_ES";
            // IDIOMA DEFECTO
            usuarioLogado.setIdioma(ConstantesWEB.ESPANYOL);
        }
        final Locale locale = Locales.getLocale(localeValue);

        session.setAttribute(org.zkoss.web.Attributes.PREFERRED_LOCALE, locale);

        execution.sendRedirect("");

        if ("ca_ES".equals(localeValue)) {
            idiomas.setVisible(false);
            idiomas2.setVisible(true);
        } else {
            idiomas.setVisible(true);
            idiomas2.setVisible(false);
        }
    }

    /**
     * Método para On click$item configuracion de la clase
     * PaginaPrincipalController.
     * 
     */
    public final void onClick$itemConfiguracion() {
        final Window ventana = (Window) creaComponente(
                "/configuracion/conf-configuracion.zul", this.self,
                null);
        abreVentanaModal(ventana);
    }

    /**
     * Método para On click$item tancar de la clase PaginaPrincipalController.
     * 
     */
    public final void onClick$itemTancar() {
        mostrarMessageBox(Labels.getLabel("salir.mensaje"),
                Labels.getLabel(ConstantesWEB.WARNING), Messagebox.YES
                        + Messagebox.NO, Messagebox.EXCLAMATION,
                new EventListener() {
                    // @Override
                    public final void onEvent(final Event event) {
                        if (((Integer) event.getData()).intValue() == Messagebox.YES) {
                        	ConsolaUtil.logout();
                        }
                    }
                });
    }

    /**
     * Método para On click$item soporte de la clase PaginaPrincipalController.
     * 
     */
    public final void onClick$itemSoporte() {
        final Window ventana = (Window) creaComponente(
                "/soporte/windows/sop-soporte-win.zul", null, null);
        abreVentanaModal(ventana);
    }
    
    /**
     * Abre ventana de mensajes.
     * 
     */
    public final void onClick$itemMensajes() {
        final Window ventana = (Window) creaComponente(
                "/mensajes/men-mensajes.zul", null, null);
        abreVentanaModal(ventana);
    }
}
