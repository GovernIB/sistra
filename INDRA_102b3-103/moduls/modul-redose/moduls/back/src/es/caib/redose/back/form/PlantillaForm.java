package es.caib.redose.back.form;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.redose.back.taglib.Constants;
import es.caib.redose.model.Formateador;
import es.caib.redose.model.Plantilla;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;


public class PlantillaForm extends TraduccionValidatorForm  
{
	private Long idVersion = null;
	private transient FormFile plantilla;

    public Long getIdVersion() {
        return this.idVersion;
    }

    public void setIdVersion(Long idVersion) {
        this.idVersion = idVersion;
    }
    
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        Plantilla plantilla = (Plantilla) getValues();
        if ( plantilla != null )
        {
        	plantilla.setDefecto('N');
        	plantilla.setBarcode('N');
        	plantilla.setSello('N');
        	plantilla.setFormateador(new Formateador());
        }
    }
    
    /*

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        if (mapping.getPath().endsWith("alta") || mapping.getPath().endsWith("editar")) {
            Plantilla plantilla = (Plantilla) getValues();
            if ( plantilla != null )
            {
            	Version version = plantilla.getVersion();
            	if ( version != null )
            	setIdVersion( version.getCodigo() );
            }
            
        }
    }
    */
    
    
    /** Retorna true si se ha pulsado un botón submit de alta */
    protected boolean isAlta(HttpServletRequest request) {
      return (request.getParameter(Constants.ALTA_PROPERTY) != null);
    }

    /** Retorna true si se ha pulsado un botón submit de baja */
    protected boolean isBaja(HttpServletRequest request) {
      return (request.getParameter(Constants.BAIXA_PROPERTY) != null);
    }

    /** Retorna true si se ha pulsado un botón submit de modificación */
    protected boolean isModificacion(HttpServletRequest request) {
      return (request.getParameter(Constants.MODIFICACIO_PROPERTY) != null);
    }

     /** Retorna true si se ha pulsado un botón submit de selección */
    protected boolean isSeleccion(HttpServletRequest request) {
      return (request.getParameter(Constants.SELECCIO_PROPERTY) != null);
    }
    
    /* NO USED
    private boolean isUpdateAction( HttpServletRequest request )
    {
    	return ( isAlta ( request ) || isModificacion( request )) ;
    }
    */
    
    
    protected boolean archivoValido(FormFile formFile) {
        return (formFile != null && formFile.getFileSize() > 0);
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) 
    {
        ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
        

        try 
        {
            Plantilla plantilla = (Plantilla) getValues();
            //PlantillaIdioma plantillaIdioma = ( PlantillaIdioma ) plantilla.getTraduccion( getLang() );
                       
            if ( isAlta(request ) && !archivoValido ( getPlantilla() ) )
            {
            	errors.add("plantilla", new ActionError("errors.plantilla.nula" ));
            	return errors;
            }            
            
            if ( isAlta( request ) )
            {
	            Set plantillas = DelegateUtil.getPlantillaDelegate().listarPlantillasVersion(idVersion);
	            Iterator it = plantillas.iterator();
	            while( it.hasNext() )
	            {
	            	Plantilla plaTmp = ( Plantilla ) it.next();
	            	if ( plantilla.getTipo().equals( plaTmp.getTipo() ) )
	            	{
	            		errors.add("values.tipo", new ActionError("errors.plantilla.duplicado", plantilla.getTipo() ));
	            	}
	            }
            }
            // Comprovar que el nombre de plantilla no esta repetido.
        
        } catch (DelegateException e) {
            log.error(e);
        }
        return errors;
    }

	public FormFile getPlantilla()
	{
		return plantilla;
	}

	public void setPlantilla(FormFile plantilla)
	{
		this.plantilla = plantilla;
	}


}
