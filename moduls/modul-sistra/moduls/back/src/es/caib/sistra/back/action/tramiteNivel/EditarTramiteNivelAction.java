package es.caib.sistra.back.action.tramiteNivel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.action.menu.Nodo;
import es.caib.sistra.back.form.TramiteNivelForm;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.TramiteNivelDelegate;
import es.caib.xml.ConstantesXML;

/**
 * Action para editar una TramiteNivel.
 *
 * @struts.action
 *  name="tramiteNivelForm"
 *  scope="session"
 *  validate="true"
 *  input=".tramiteNivel.editar"
 *  path="/back/tramiteNivel/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/tramiteNivel/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".tramiteNivel.editar"
 *
 * @struts.action-forward
 *  name="success" path=".tramiteNivel.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".tramiteNivel.lista"
 *
 */
public class EditarTramiteNivelAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarTramiteNivelAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarTramiteNivel");
        TramiteNivelDelegate tramiteNivelDelegate = DelegateUtil.getTramiteNivelDelegate();
        TramiteNivelForm tramiteNivelForm = (TramiteNivelForm) form;
        TramiteNivel tramiteNivel = (TramiteNivel) tramiteNivelForm.getTramite();

        if (isCancelled(request)) 
        {
            log.info("isCancelled");
            Long idTramiteVersion = tramiteNivelForm.getIdTramiteVersion();
            guardarTramiteVersion(mapping, request, idTramiteVersion);
            return mapping.findForward("cancel");
        }
        
        // Elimina traducciones que no son validas
        tramiteNivelForm.validaTraduccion(mapping, request);
        

        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");

            Long idTramiteVersion = tramiteNivelForm.getIdTramiteVersion();
            // Establecer blob con la validacion de inicio de script
            tramiteNivel.getEspecificaciones().setValidacionInicioScript( tramiteNivelForm.getValidacionInicioScript().getBytes(ConstantesXML.ENCODING) );
            
            tramiteNivel.getEspecificaciones().setCampoCodigoLocalidad(tramiteNivelForm.getLocalidadScript().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCampoCodigoProvincia(tramiteNivelForm.getProvinciaScript().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCampoCodigoPais(tramiteNivelForm.getPaisScript().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCampoRteNif( tramiteNivelForm.getCampoRteNif().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCampoRteNom( tramiteNivelForm.getCampoRteNom().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCampoRdoNif( tramiteNivelForm.getCampoRdoNif().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCampoRdoNom( tramiteNivelForm.getCampoRdoNom().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setDatosRpteScript(tramiteNivelForm.getDatosRpte().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setDatosRpdoScript(tramiteNivelForm.getDatosRpdo().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setUrlFin( tramiteNivelForm.getUrlFin().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setAvisoEmail( StringUtils.defaultString(tramiteNivelForm.getAvisoEmail()).getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setAvisoSMS( StringUtils.defaultString(tramiteNivelForm.getAvisoSMS()).getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setCheckEnvio( tramiteNivelForm.getCheckEnvio().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setDestinatarioTramite( tramiteNivelForm.getDestinatarioTramite().getBytes(ConstantesXML.ENCODING) );
            tramiteNivel.getEspecificaciones().setProcedimientoDestinoTramite( tramiteNivelForm.getProcedimientoDestinoTramite().getBytes(ConstantesXML.ENCODING) );
            
            String [] nivelesAutenticacion = tramiteNivelForm.getNivelesAutenticacionSelected();
            if ( nivelesAutenticacion != null )
            {
            	tramiteNivel.setNivelAutenticacion( Util.concatArrString( nivelesAutenticacion ) );
            }
            
            boolean recargarArbol = false;
            if ( isAlta( request ) )
            {
            	recargarArbol =  true;
            }
            else
            {
            	TramiteNivel tramiteNivelAntesGrabar = tramiteNivelDelegate.obtenerTramiteNivel( tramiteNivel.getCodigo() );
            	recargarArbol = ! tramiteNivelAntesGrabar.getNivelAutenticacion().equals( tramiteNivel.getNivelAutenticacion() );
            }
            
            tramiteNivelDelegate.grabarTramiteNivel(tramiteNivel, idTramiteVersion);

            //actualizaPath(request.getSession(true), 2, tramiteNivel.getId().toString());
            log.info("Creat/Actualitzat " + tramiteNivel.getCodigo());

            guardarTramiteNivel(mapping, request, tramiteNivel.getCodigo());
            
            if ( recargarArbol )
            {
            	this.setReloadTree( request, Nodo.IR_A_TRAMITENIVEL, tramiteNivel.getCodigo() );
            }
            return mapping.findForward("success");

        }
        
        
        // Cambio de idioma
        tramiteNivelForm.reloadLang();
        
        
        //log.info( "EDICION DE PLANTILLA; BEFORE FORWARD " + tramiteNivel );
        
        
        return mapping.findForward("reload");
    }
    


}
