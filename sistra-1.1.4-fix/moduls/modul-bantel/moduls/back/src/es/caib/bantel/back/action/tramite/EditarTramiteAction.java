package es.caib.bantel.back.action.tramite;

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
import es.caib.bantel.back.form.TramiteForm;
import es.caib.bantel.model.FicheroExportacion;
import es.caib.bantel.model.Tramite;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.TramiteDelegate;
import es.caib.util.CifradoUtil;

/**
 * Action para editar un Tramite.
 *
 * @struts.action
 *  name="tramiteForm"
 *  scope="session"
 *  validate="true"
 *  input=".tramite.editar"
 *  path="/back/tramite/editar"
 *
 * @struts.action-forward
 *  name="reload" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="success" path=".tramite.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".tramite.lista"
 *  
 */
public class EditarTramiteAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarTramiteAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setAttribute( "idReadOnly", new Boolean( true ) );
    	
        log.debug("Entramos en EditarTramite");

        TramiteDelegate tramiteDelegate = DelegateUtil.getTramiteDelegate();
        TramiteForm tramiteForm = (TramiteForm) form;
        Tramite tramite = (Tramite) tramiteForm.getValues();

        if (isCancelled(request)) {
            log.debug("isCancelled");
            return mapping.findForward("cancel");
        }

        if (request.getParameter("borrarTramite") != null) 
        {
            return mapping.findForward("reload");
        } 
        
        if (request.getParameter("borrarFicheroExportacion") != null) 
        {	        	      
        	tramiteDelegate.borrarFicheroExportacion(tramite.getIdentificador());
        	guardarTramite(mapping, request, tramite.getIdentificador());
            return mapping.findForward("reload");
        }else if (archivoValido(tramiteForm.getFicheroExportacion())) 
        {        	
            tramite.setNombreFicheroExportacion( tramiteForm.getFicheroExportacion().getFileName() );                        
            FicheroExportacion archivo = populateArchivo( tramite.getArchivoFicheroExportacion(), tramiteForm.getFicheroExportacion() ); 
            archivo.setTramite( tramite );
            tramite.setArchivoFicheroExportacion( archivo  );                      
        }
        
        
        if (isAlta(request) || isModificacion(request)) {
            log.debug("isAlta || isModificacio");                      
            
            String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
            tramite.setUsr(CifradoUtil.cifrar(claveCifrado,tramiteForm.getUserPlain()));
            tramite.setPwd(CifradoUtil.cifrar(claveCifrado,tramiteForm.getPassPlain()));
            
            tramiteDelegate.grabarTramite( tramite );
            //request.setAttribute("reloadMenu", "true");
            log.debug("Creat/Actualitzat " + tramite.getIdentificador());

            guardarTramite(mapping, request, tramite.getIdentificador());

            return mapping.findForward("success");

        }

        return mapping.findForward("reload");
    }
    
    private FicheroExportacion populateArchivo(FicheroExportacion archivo, FormFile formFile) throws IOException {
        if (archivo == null) archivo = new FicheroExportacion();
        archivo.setDatos(formFile.getFileData());
        return archivo;
    }

}
