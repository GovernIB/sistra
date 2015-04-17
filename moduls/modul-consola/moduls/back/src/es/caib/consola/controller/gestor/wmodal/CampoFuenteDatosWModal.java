package es.caib.consola.controller.gestor.wmodal;

import java.util.Iterator;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


import es.caib.bantel.model.CampoFuenteDatos;
import es.caib.bantel.model.FuenteDatos;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FuenteDatosDelegate;
import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.util.StringUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion campo fuente datos.
 */
@SuppressWarnings("serial")
public class CampoFuenteDatosWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wCampoFuenteDatos;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;
    /** Organo editado.*/
    private CampoFuenteDatos campoFuenteDatos;    	   
    /** Fuente datos. */
    private FuenteDatos fuenteDatos;
    /** Codigo. */
    private Textbox codigo;
    /** Es PK. */
    private Checkbox esPK;
        
  /**
     * After compose.
     * 
     * @param compDominio
     *            Parámetro comp dominio
     */
    @Override
    public final void doAfterCompose(final Component compDominio) {
     	super.doAfterCompose(compDominio);
    	
     	// Parametros apertura
     	modo = (TypeModoAcceso) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	campoFuenteDatos = (CampoFuenteDatos) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);   
     	fuenteDatos = (FuenteDatos) arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);
     	
     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapea datos a los controles de la pantalla
     	mapearDatos();
     	
    }

	/**
     * Mapea datos a los controles de la pantalla.
     */
	private void mapearDatos() {
		if (modo != TypeModoAcceso.ALTA) {
			codigo.setValue(campoFuenteDatos.getIdentificador());	
			esPK.setChecked("S".equals(campoFuenteDatos.getEsPK()));
		}
	}
	
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wCampoFuenteDatos.detach();
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {

    	// Modo consulta: acceso no permitido
    	if (modo == TypeModoAcceso.CONSULTA) {
    		ConsolaUtil.generaOperacionNoPermitidaException();
    	}
    	
    	// Verificamos campos obligatorios    	
        final String[] nomCampos = {Labels
                .getLabel("campoFuenteDatos.id")};
        final String[] valCampos = {codigo.getText()};
        boolean ok = verificarCamposObligatorios(nomCampos, valCampos);
        
        String codigoText = codigo.getText().toUpperCase();
        codigo.setText(codigoText);
		if (!StringUtil.validarFormatoIdentificador(codigoText) ){
        	ok = false;
        	 mostrarError(Labels.getLabel("campoFuenteDatos.id") + ": " + Labels.getLabel("error.formatoIdentificadorNoValido"),
        			 Labels.getLabel("mensaje.atencion"));       	
		}
        
        // Guardamos datos
        if (ok) {                    	                        
            try {            	
            	// Grabamos organo
            	FuenteDatosDelegate fuenteDatosDelegate = DelegateUtil.getFuenteDatosDelegate();
            	
            	if (modo == TypeModoAcceso.ALTA) { 
            		fuenteDatosDelegate.altaCampoFuenteDatos(fuenteDatos.getIdentificador(), codigoText, esPK.isChecked()?"S":"N");
                } else {
                	fuenteDatosDelegate.modificarCampoFuenteDatos(fuenteDatos.getIdentificador(), esPK.isChecked()?"S":"N", campoFuenteDatos.getIdentificador(), codigoText);
                }           
            	
            	//Generamos evento
            	CampoFuenteDatos campoModif = null;
            	FuenteDatos fd = fuenteDatosDelegate.obtenerFuenteDatos(fuenteDatos.getIdentificador());
            	for (Iterator it = fd.getCampos().iterator(); it.hasNext();) {
            		 CampoFuenteDatos c = (CampoFuenteDatos) it.next();
            		 if (c.getIdentificador().equals(codigoText)) {
            			 campoModif = c;
            			 break;
            		 }            		
            	}
            	
                Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wCampoFuenteDatos.getParent(), campoModif));
    		} catch (DelegateException e) {
    			ConsolaUtil.generaDelegateException(e);
    		}
    		
    		wCampoFuenteDatos.detach();
        }
    	
    	
    }
    
    /**
     * Ayuda.
     * 
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("configuracion/fichaOrganismo");
    }
    

	
}