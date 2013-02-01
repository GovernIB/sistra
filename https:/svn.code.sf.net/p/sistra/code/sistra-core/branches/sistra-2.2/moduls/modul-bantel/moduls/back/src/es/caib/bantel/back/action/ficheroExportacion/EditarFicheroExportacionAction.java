package es.caib.bantel.back.action.ficheroExportacion;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import es.caib.bantel.back.action.BaseAction;
import es.caib.bantel.back.form.FicheroExportacionForm;
import es.caib.bantel.model.ArchivoFicheroExportacion;
import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.FicheroExportacionDelegate;

/**
 * Action para editar un Fichero exportacion.
 *
 * @struts.action
 *  name="ficheroExportacionForm"
 *  scope="session"
 *  validate="true"
 *  input=".ficheroExportacion.editar"
 *  path="/back/ficheroExportacion/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".ficheroExportacion.editar"
 *
 * @struts.action-forward
 *  name="success" path=".ficheroExportacion.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".ficheroExportacion.lista"
 *  
 */
public class EditarFicheroExportacionAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarFicheroExportacionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setAttribute( "idReadOnly", new Boolean( true ) );
    	
        log.debug("Entramos en EditarFicheroExportacion");

        FicheroExportacionDelegate ficheroExportacionDelegate = DelegateUtil.getFicheroExportacionDelegate();
        FicheroExportacionForm ficheroExportacionForm = (FicheroExportacionForm) form;
        FicheroExportacion ficheroExportacion = (FicheroExportacion) ficheroExportacionForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarFicheroExportacion") != null) 
        {	        	      
        	ficheroExportacionDelegate.borrarArchivoFicheroExportacion(ficheroExportacion.getIdentificadorTramite());
        	guardarFicheroExportacion(mapping, request, ficheroExportacion.getIdentificadorTramite());
            return mapping.findForward("reload");
        }else if (archivoValido(ficheroExportacionForm.getFichero())) 
        {        	
            ficheroExportacion.setNombre( ficheroExportacionForm.getFichero().getFileName() );                        
            ArchivoFicheroExportacion archivoFicheroExportacion = populateArchivo( ficheroExportacion.getArchivoFicheroExportacion(), ficheroExportacionForm.getFichero() ); 
            archivoFicheroExportacion.setFicheroExportacion( ficheroExportacion );
            ficheroExportacion.setArchivoFicheroExportacion( archivoFicheroExportacion  );                      
        }
        
        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");                                             
            
            ficheroExportacionDelegate.guardarFicheroExportacion( ficheroExportacion );
            //request.setAttribute("reloadMenu", "true");
            log.debug("Creat/Actualitzat " + ficheroExportacion.getIdentificadorTramite());

            guardarFicheroExportacion(mapping, request, ficheroExportacion.getIdentificadorTramite());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }
    
    
    private ArchivoFicheroExportacion populateArchivo(ArchivoFicheroExportacion archivoFicheroExportacion, FormFile formFile) throws IOException {
        if (archivoFicheroExportacion == null) archivoFicheroExportacion = new ArchivoFicheroExportacion();
        archivoFicheroExportacion.setDatos(formFile.getFileData());
        return archivoFicheroExportacion;
    }

}
