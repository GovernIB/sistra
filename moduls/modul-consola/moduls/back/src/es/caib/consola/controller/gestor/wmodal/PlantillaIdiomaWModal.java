package es.caib.consola.controller.gestor.wmodal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.consola.util.ConsolaUtil;
import es.caib.consola.util.GTTUtilsWeb;
import es.caib.redose.model.ArchivoPlantilla;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Ventana de edicion plantilla idioma.
 */
@SuppressWarnings("serial")
public class PlantillaIdiomaWModal extends BaseComposer {

	/** Referencia ventana. */
    private Window wPlantillaIdioma;
    /** Usuario logeado.*/
	private Usuario usuarioLogado;
    /** Modo edicion. */
    private TypeModoAcceso modo;   
    /** Combo idioma. */
    private Combobox idioma;
    /** Plantilla idioma. */
    private PlantillaIdioma plantillaIdioma;
    /** Plantilla. */
    private Plantilla plantilla;
    /** Archivo. */
    private Label archivo;
        
    
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
     	
     	plantillaIdioma = (PlantillaIdioma) arg.get(ConstantesWEB.PARAM_OBJETO_EDICION);
     	plantilla = (Plantilla)  arg.get(ConstantesWEB.PARAM_OBJETO_PADRE);
     	
     	// Refrescamos datos
     	try {
     		plantilla = DelegateUtil.getPlantillaDelegate().obtenerPlantilla(plantilla.getCodigo());
     	} catch (DelegateException ex) {
     		ConsolaUtil.generaDelegateException(ex);
     	}

     	// Usuario autenticado
     	usuarioLogado = ConsolaUtil.recuperarUsuarioLogado();     	     	     
     	
     	// Idiomas
     	idioma.setItemRenderer(new ComboitemRenderer() {
			public void render(Comboitem item, Object data, final int index) throws Exception {
				final String reg = (String) data;
                item.setValue(reg);
                item.setLabel(Labels.getLabel("idioma." + reg)); 
			}
		});	
     	refrescarComboIdiomas();
     	idioma.setDisabled(modo != TypeModoAcceso.ALTA);
     	
     	// Nombre archivo
     	if (plantillaIdioma != null) {
     		archivo.setValue(plantillaIdioma.getNombreFichero());
     	}
     	
    }      
      
    /** Refresca combo idiomas. */
	private void refrescarComboIdiomas() {		
		try {
			List idiomas = DelegateUtil.getIdiomaDelegate().listarLenguajes();
			idioma.setSelectedItem(null);
			idioma.setModel(new ListModelList(idiomas, true));			
		} catch (DelegateException e) {					
			ConsolaUtil.generaDelegateException(e);
		}
	}
	
	/** After render combo idioma. */
    public final void onAfterRender$idioma() {
    	if (plantillaIdioma != null) {		
			this.seleccionarCombo(plantillaIdioma.getIdioma(), idioma);
		} else {
			this.seleccionarCombo("es", idioma);
		}
    }
    
    
	 /**
     * Click boton Cancelar.
     */
    public final void onClick$btnCancelar() {
    	
    	//Generamos evento       
    	if (plantillaIdioma != null) {
    		Events.postEvent(new Event(ConsolaUtil.eventoModoAcesso(modo), wPlantillaIdioma.getParent(), plantillaIdioma));
    	}
    	
    	wPlantillaIdioma.detach();    	    	
    }
    
    /**
     * Click boton Guardar.
     */
    public final void onClick$btnGuardar() {
    	
    }     
    
    /**
     * Descarga fichero.
     */
    public final void onClick$btnDescargarPlantilla() {
    	if (plantillaIdioma != null) {
    		final InputStream is = new ByteArrayInputStream(plantillaIdioma.getArchivo().getDatos());
            Filedownload.save(is, "application/octet-stream", plantillaIdioma.getNombreFichero());    		
    	}        
    }
 
    /**
     * Upload fichero.
     * @param e
     * @throws IOException
     * @throws InterruptedException
     */
    public final void onUpload$btnModificarPlantilla(final UploadEvent e) throws IOException, InterruptedException {
		// Obtiene fichero
    	final Media media = e.getMedia();
		String fileName = media.getName();
		
		byte[] data = null;
		if (media.isBinary()) {
			data = IOUtils.toByteArray(media.getStreamData());
		} else {
			data = media.getStringData().getBytes();
		}
		
		
		// Guarda fichero
		try {			
		
			PlantillaIdioma plantillaIdiomaModif = plantillaIdioma;
			
			if (plantillaIdiomaModif == null) {
				
				String idiomaNuevo = (String) idioma.getSelectedItem().getValue();
				
				// Verificamos que no exista ese idioma
				if (plantilla.getTraduccion(idiomaNuevo) != null) {
					mostrarError(Labels.getLabel("plantillaIdioma.seleccionarIdioma"),
		        			 Labels.getLabel("mensaje.atencion"));
					return;
				}				
				
				plantillaIdiomaModif = new PlantillaIdioma();
				plantillaIdiomaModif.setIdioma(idiomaNuevo);
			}
			
			ArchivoPlantilla archivoPlantilla = plantillaIdiomaModif.getArchivo();
			if (archivoPlantilla == null) {
				archivoPlantilla = new ArchivoPlantilla();
				archivoPlantilla.setPlantillaIdioma(plantillaIdiomaModif);
			}
			archivoPlantilla.setDatos(data);
			
			plantillaIdiomaModif.setNombreFichero(fileName);
			plantillaIdiomaModif.setArchivo(archivoPlantilla);
			plantillaIdiomaModif.setPlantilla(plantilla);
			
			Long codPlantillaIdioma = DelegateUtil.getPlantillaIdiomaDelegate().grabarPlantillaIdioma(plantillaIdiomaModif, plantilla.getCodigo());		
			
			plantillaIdiomaModif.setCodigo(codPlantillaIdioma);
			plantillaIdioma = plantillaIdiomaModif;
			archivo.setValue(plantillaIdiomaModif.getNombreFichero());						
		
		} catch (DelegateException ex) {					
			ConsolaUtil.generaDelegateException(ex);
		}
    }
           
    /**
     * Ayuda.
     */
    public final void onClick$btnAyuda() {
        GTTUtilsWeb.onClickBotonAyuda("gestor/plantillaIdioma");
    }
    
}
