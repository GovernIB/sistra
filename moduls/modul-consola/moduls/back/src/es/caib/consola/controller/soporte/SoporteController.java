/*
 * 
 */
package es.caib.consola.controller.soporte;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

import es.caib.consola.util.GTTUtilsWeb;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Class SoporteController.
 */
@SuppressWarnings("serial")
public class SoporteController extends BaseComposer {

    /**
     * Atributo correo de SoporteController.
     */
    private Label correo;
   
    /**
     * Ventana actual;
     */
    private Window wSoporteTramite;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * es.gva.dgm.tra.util.zk.composer.BaseComposer#doAfterCompose(org.zkoss
     * .zk.ui.Component)
     */
    @Override
    public final void doAfterCompose(final Component comp) {
        super.doAfterCompose(comp);
        correo.setValue("administracionDigital@caib.es");
    }

    // public final void onClick$btnModificarFichero() {

    // }

    /**
     * Método para On click$btn ayuda de la clase SoporteController.
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("soporte");
    }

    /**
     * Click boton Cerrar.
     */
    public final void onClick$btnCerrar() {
    	wSoporteTramite.detach();
    }
    
}
