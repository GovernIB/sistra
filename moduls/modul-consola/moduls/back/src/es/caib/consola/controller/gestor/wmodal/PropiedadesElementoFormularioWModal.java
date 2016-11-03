package es.caib.consola.controller.gestor.wmodal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana propiedades elemento formulario.
 */
@SuppressWarnings("serial")
public class PropiedadesElementoFormularioWModal extends BaseComposer {

    /**Referencia ventana. */
    private Window wPropiedadesElementoFormulario;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private String modo;
    /** Id elemento. */
    private String idElemento;
    
    /** Textbox identificador. */
    private Textbox identificador;
    
    /** Propiedades texto. */
    private Row rowText;
    private Row rowSeccion;
    private Row rowObligatorio;
    private Row rowLectura;
    private Row rowTipoTexto;
    private Tab tabScripts;
    private Label cabecera;
    private Textbox seccion;
    
    @Override
    public final void doAfterCompose(final Component compPropiedadesForm) {

    	super.doAfterCompose(compPropiedadesForm);
        
    	// Parametros apertura
     	modo = (String) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	idElemento = (String) arg.get(ConstantesWEB.PARAMETER_ID_ELEMENTO_FORMULARIO);
     	
     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
    	
     	if (idElemento.startsWith("campo")) {
	     	cabecera.setValue("CDT: " + idElemento);
	     	identificador.setValue(idElemento);
	     	rowText.setVisible(true);
	     	rowObligatorio.setVisible(true);
	     	rowLectura.setVisible(true);
	     	rowTipoTexto.setVisible(true);
	     	tabScripts.setVisible(true);
     	} else if (idElemento.startsWith("seccion")) {
     		cabecera.setValue("SEC: " + idElemento);
     		identificador.setValue(idElemento);
     		seccion.setValue("Sección 1");
     		rowSeccion.setVisible(true);
     	}
     	
     	
     	
    }

  
}
