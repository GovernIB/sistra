package es.caib.redose.back.action.plantilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.redose.back.action.BaseAction;
import es.caib.redose.back.form.PlantillaForm;
import es.caib.redose.model.ArchivoPlantilla;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.PlantillaDelegate;
import es.caib.redose.persistence.delegate.PlantillaIdiomaDelegate;

/**
 * Action para editar una Plantilla.
 *
 * @struts.action
 *  name="plantillaForm"
 *  scope="session"
 *  validate="true"
 *  input=".plantilla.editar"
 *  path="/back/plantilla/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/plantilla/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".plantilla.editar"
 *
 * @struts.action-forward
 *  name="success" path=".plantilla.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".version.editar"
 *
 */
public class EditarPlantillaAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarPlantillaAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarPlantilla");
        PlantillaDelegate plantillaDelegate = DelegateUtil.getPlantillaDelegate();
        PlantillaIdiomaDelegate plantillaIdiomaDelegate = DelegateUtil.getPlantillaIdiomaDelegate();
        PlantillaForm plantillaForm = (PlantillaForm) form;
        Plantilla plantilla = (Plantilla) plantillaForm.getValues();
        PlantillaIdioma plantillaIdioma = ( PlantillaIdioma ) plantilla.getTraduccion( plantillaForm.getLang() );
        //PlantillaIdioma plantillaIdioma = ( PlantillaIdioma ) plantilla.getTraduccion(  );

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idVersion = plantillaForm.getIdVersion();
            guardarVersion(mapping, request, idVersion);
            return mapping.findForward("cancel");
        }
        
        /*
        if ( plantillaIdioma == null )
        {
        	plantillaIdioma = new PlantillaIdioma();
        }
        */
        /*TODO: aqui hem ficat aquest codi, per borrar totes les traduccions que ens arribin sense arxius. 
         * Ja que al grabar la plantilla ens petarien*/
        borrarPlantillasSinArchivos(plantilla);
        
        if (request.getParameter("borrarPlantilla") != null) 
        {	
        	plantilla.removePlantillaIdioma( plantillaIdioma );
        	plantillaDelegate.grabarPlantilla(plantilla, plantillaForm.getIdVersion());
            return mapping.findForward("reload");
        }
        else if (archivoValido(plantillaForm.getPlantilla())) 
        {
            plantillaIdioma.setNombreFichero( plantillaForm.getPlantilla().getFileName() );
            
            
            ArchivoPlantilla archivo = populateArchivo( plantillaIdioma.getArchivo(), plantillaForm.getPlantilla() ); 
            archivo.setPlantillaIdioma( plantillaIdioma );
            plantillaIdioma.setArchivo( archivo  );
            
            /*
            ArchivoPlantilla archivo = new ArchivoPlantilla();
            archivo.setPlantillaIdioma( plantillaIdioma );
            archivo.setDatos( plantillaForm.getPlantilla().getFileData() );
            plantillaIdioma.setArchivo( archivo );
            */
            
        	//plantillaIdioma.setFichero(  plantillaForm.getPlantilla().getFileData()  );
        }
        
        plantillaIdioma.setIdioma( plantillaForm.getLang() );

        // Elimina traducciones que no son validas
        plantillaForm.validaTraduccion(mapping, request);
        
        
        log.info( "EDICION DE PLANTILLA; PLANTILLA PERSISTENTE " + plantilla );

        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");
            
            if(plantillaIdioma.getArchivo() != null){
            
            
           	setPlantillaIdioma( plantilla, plantillaIdioma );
            
            log.info( "EDICION DE PLANTILLA; BEFORE SAVE " + plantilla );

            Long idVersion = plantillaForm.getIdVersion();
            plantillaDelegate.grabarPlantilla(plantilla, idVersion);
            //plantillaIdiomaDelegate.grabarPlantillaIdioma( plantillaIdioma, plantilla.getCodigo() );
            }
            //actualizaPath(request.getSession(true), 2, plantilla.getId().toString());
            log.info("Creat/Actualitzat " + plantilla.getCodigo());

            guardarPlantilla(mapping, request, plantilla.getCodigo());
            
            plantilla = (Plantilla) plantillaForm.getValues();
            
            log.info( "EDICION DE PLANTILLA; AFTER SAVE " + plantilla );

            request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        
        
        // Cambio de idioma
        plantillaForm.reloadLang();
        
        
        log.info( "EDICION DE PLANTILLA; BEFORE FORWARD " + plantilla );
        
        
        return mapping.findForward("reload");
    }
    
    /**
     * Método que añade la plantilla para el idioma o la sustituye si ya existe
     * @param plantilla
     * @param plantillaIdioma
     */
    private void setPlantillaIdioma( Plantilla plantilla, PlantillaIdioma plantillaIdioma )
    {
    	log.info( "ADICION O SUSTITUCION DE PLANTILLA " + plantillaIdioma );
    	plantilla.addPlantillaIdioma( plantillaIdioma );
    }

    /**
     * Método que borra las plantillas para los idiomas que no tienen archivos asignados de la plantilla actual
     * @param plantilla
     */
    private void borrarPlantillasSinArchivos(Plantilla plantilla){
    	Map plantillasIdiomaMap = plantilla.getPlantillasIdioma();
    	if(plantillasIdiomaMap != null){
    		ArrayList plantillasIdioma = new ArrayList (plantillasIdiomaMap.values());
    		for(int i=0; i<plantillasIdioma.size(); i++){
    			PlantillaIdioma plantillaIdioma = (PlantillaIdioma) plantillasIdioma.get(i);
    			if(plantillaIdioma.getArchivo() == null){
    				plantilla.removePlantillaIdioma( plantillaIdioma );
    			}
    		}
    	}
    	//plantilla.removePlantillaIdioma( plantillaIdioma );
    	
    }

}
