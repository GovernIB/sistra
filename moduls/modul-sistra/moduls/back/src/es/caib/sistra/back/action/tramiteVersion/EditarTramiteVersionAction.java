package es.caib.sistra.back.action.tramiteVersion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.back.form.TramiteVersionForm;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.util.CifradoUtil;

/**
 * Action para editar una TramiteVersion.
 *
 * @struts.action
 *  name="tramiteVersionForm"
 *  scope="session"
 *  validate="true"
 *  input=".tramiteVersion.editar"
 *  path="/back/tramiteVersion/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/tramiteVersion/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".tramiteVersion.editar"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteVersion.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".tramite.editar"
 *
 */
public class EditarTramiteVersionAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarTramiteVersionAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarTramiteVersion");
        TramiteVersionDelegate tramiteVersionDelegate = DelegateUtil.getTramiteVersionDelegate();
        TramiteVersionForm tramiteVersionForm = (TramiteVersionForm) form;
        TramiteVersion tramiteVersion = (TramiteVersion) tramiteVersionForm.getValues();
        Long idTramite = tramiteVersionForm.getIdTramite();
        
        if (isCancelled(request)) 
        {
            log.info("isCancelled");        
            guardarTramite(mapping, request, idTramite);                       
            return mapping.findForward("cancel");
        }
        
        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");
        
            // Establecer fechas de inicio y de fin
            String fechaInicio = tramiteVersionForm.getInicioPlazo();
            String fechaFin = tramiteVersionForm.getFinPlazo();
            
            if ( !Util.esCadenaVacia( fechaInicio  ) )
            {
            	tramiteVersion.setInicioPlazo( Util.cadenaASqlTimestamp( fechaInicio ) );
            }else{
            	tramiteVersion.setInicioPlazo(null);
            }
            if ( !Util.esCadenaVacia( fechaFin  ) )
            {
            	tramiteVersion.setFinPlazo( Util.cadenaASqlTimestamp( fechaFin ));
            }else{
            	tramiteVersion.setFinPlazo(null);
            }         
            
            String idiomas ="";
            if (tramiteVersionForm.getIdiomas() != null){
	            for (int i=0;i<tramiteVersionForm.getIdiomas().length;i++){
	            	if (i>0) idiomas = idiomas + ",";
	            	idiomas = idiomas + tramiteVersionForm.getIdiomas()[i];
	            }
            }
            tramiteVersion.setIdiomasSoportados(idiomas);
                             
            
            String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
            tramiteVersion.setConsultaAuthUser(CifradoUtil.cifrar(claveCifrado,tramiteVersionForm.getUserPlain()));
            tramiteVersion.setConsultaAuthPwd(CifradoUtil.cifrar(claveCifrado,tramiteVersionForm.getPassPlain()));
            
            
            boolean recargarArbol = false;            
            if ( isAlta( request ) )
            {
            	recargarArbol =  true;
            }
            else
            {
            	TramiteVersion tramiteVersionAntesGrabar = tramiteVersionDelegate.obtenerTramiteVersion( tramiteVersion.getCodigo() );
            	recargarArbol = tramiteVersionAntesGrabar.getVersion() != tramiteVersion.getVersion();
            }
            
            
            tramiteVersionDelegate.grabarTramiteVersion(tramiteVersion, idTramite);

            //actualizaPath(request.getSession(true), 2, tramiteVersion.getId().toString());
            log.info("Creat/Actualitzat " + tramiteVersion.getCodigo());

            guardarTramiteVersion(mapping, request, tramiteVersion.getCodigo());
            
            //request.setAttribute("reloadMenu", "true");
            
            if ( recargarArbol )
            {
            	this.setReloadTree( request, Nodo.IR_A_DEFINICION_TRAMITE_VERSION, tramiteVersion.getCodigo() );
            }
            return mapping.findForward("success");

        }
        
        //log.info( "EDICION DE PLANTILLA; BEFORE FORWARD " + tramiteVersion );       
        return mapping.findForward("reload");
    }
    


}
