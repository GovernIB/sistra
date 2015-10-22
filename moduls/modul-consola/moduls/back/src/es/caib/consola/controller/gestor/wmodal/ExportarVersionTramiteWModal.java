package es.caib.consola.controller.gestor.wmodal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Listbox;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.util.ConsolaUtil;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de exportar version tramite.
 */
@SuppressWarnings("serial")
public class ExportarVersionTramiteWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wExportarVersionTramite;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private String modo;
    /** Version tramite.*/
	private TramiteVersion version;
   
	/** Id tramite.*/
	private Textbox idTramite;
	/** Version tramite.*/
	private Textbox versionTramite;
	
	/** Listbox formularios.*/
	private Listbox formularios;
	
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
     	modo = (String) arg.get(ConstantesWEB.PARAM_MODO_ACCESO);
     	version = (TramiteVersion) arg.get(ConstantesWEB.PARAMETER_VERSION_TRAMITE);     	 

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();
        
     	// Mapear datos
     	mapearDatos();
    }
	
    // Mapea datos
	 private void mapearDatos() {
		 idTramite.setValue(version.getTramite().getIdentificador());
		 versionTramite.setValue(Integer.toString(version.getVersion()));
		 
		 /*	 
		 try {
			  TramiteVersion vtc = DelegateUtil.getTramiteVersionDelegate().obtenerTramiteVersionCompleto(version.getTramite().getIdentificador(), version.getVersion());
			  
			  List<DocumentoNivel> forms = new ArrayList<DocumentoNivel>();
			  for (Iterator it = vtc.getDocumentos().iterator(); it.hasNext();) {
				  Documento doc =  (Documento) it.next();
				  if (doc.getTipo() == Documento.TIPO_FORMULARIO) {
					 for (Iterator it2 = doc.getNiveles().iterator(); it2.hasNext();) {
						 DocumentoNivel dn = (DocumentoNivel) it2.next();
						 forms.add(dn);						 
					 }
				  }
			  }
			  formularios.setModel(new BindingListModelList(forms, true));
			  
		 } catch (DelegateException e) {
			ConsolaUtil.generaDelegateException(e);
		}
		 */
		 
	}

	/**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	wExportarVersionTramite.detach();
    }

}
