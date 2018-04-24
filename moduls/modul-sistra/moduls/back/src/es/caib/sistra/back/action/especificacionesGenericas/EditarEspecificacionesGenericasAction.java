package es.caib.sistra.back.action.especificacionesGenericas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.sistra.back.action.BaseAction;
import es.caib.sistra.back.form.EspecificacionesGenericasForm;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;
import es.caib.xml.ConstantesXML;

/**
 * Action para editar una TramiteNivel.
 *
 * @struts.action
 *  name="especificacionesGenericasForm"
 *  scope="session"
 *  validate="true"
 *  input=".especificacionesGenericas.editar"
 *  path="/back/especificacionesGenericas/editar"
 *
 * @struts.action-forward
 *  name="alta" path="/back/especificacionesGenericas/alta.do"
 *
 * @struts.action-forward
 *  name="reload" path=".especificacionesGenericas.editar"
 *
 * @struts.action-forward
 *  name="success" path=".especificacionesGenericas.editar"
 *
 * @struts.action-forward
 *  name="cancel" path=".especificacionesGenericas.lista"
 *
 */
public class EditarEspecificacionesGenericasAction extends BaseAction{

    protected static Log log = LogFactory.getLog(EditarEspecificacionesGenericasAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        log.info("Entramos en EditarTramiteNivel");
        EspecTramiteNivelDelegate especTramiteNivelDelegate = DelegateUtil.getEspecTramiteNivelDelegate();
        EspecificacionesGenericasForm especificacionesGenericasForm = (EspecificacionesGenericasForm) form;
        EspecTramiteNivel espec = (EspecTramiteNivel) especificacionesGenericasForm.getValues();

        // Elimina traducciones que no son validas
        especificacionesGenericasForm.validaTraduccion(mapping, request);
        

        if (isAlta(request) || isModificacion(request)) 
        {
            log.info("isAlta || isModificacio");

            // Establecer blob con la validacion de inicio de script
            espec.setValidacionInicioScript( especificacionesGenericasForm.getValidacionInicioScript().getBytes(ConstantesXML.ENCODING) );
            
            espec.setCampoCodigoLocalidad(especificacionesGenericasForm.getLocalidadScript().getBytes(ConstantesXML.ENCODING) );
            espec.setCampoCodigoProvincia(especificacionesGenericasForm.getProvinciaScript().getBytes(ConstantesXML.ENCODING) );
            espec.setCampoCodigoPais(especificacionesGenericasForm.getPaisScript().getBytes(ConstantesXML.ENCODING) );
            espec.setCampoRteNif( especificacionesGenericasForm.getCampoRteNif().getBytes(ConstantesXML.ENCODING) );
            espec.setCampoRteNom( especificacionesGenericasForm.getCampoRteNom().getBytes(ConstantesXML.ENCODING) );
            espec.setCampoRdoNif( especificacionesGenericasForm.getCampoRdoNif().getBytes(ConstantesXML.ENCODING) );
            espec.setCampoRdoNom( especificacionesGenericasForm.getCampoRdoNom().getBytes(ConstantesXML.ENCODING) );
            espec.setDatosRpteScript(especificacionesGenericasForm.getDatosRpte().getBytes(ConstantesXML.ENCODING) );
            espec.setDatosRpdoScript(especificacionesGenericasForm.getDatosRpdo().getBytes(ConstantesXML.ENCODING) );
            espec.setUrlFin( especificacionesGenericasForm.getUrlFin().getBytes(ConstantesXML.ENCODING) );
            espec.setAvisoEmail( StringUtils.defaultString(especificacionesGenericasForm.getAvisoEmail()).getBytes(ConstantesXML.ENCODING) );
            espec.setAvisoSMS( StringUtils.defaultString(especificacionesGenericasForm.getAvisoSMS()).getBytes(ConstantesXML.ENCODING) );
            espec.setCheckEnvio(especificacionesGenericasForm.getCheckEnvio().getBytes(ConstantesXML.ENCODING) );
            espec.setDestinatarioTramite(especificacionesGenericasForm.getDestinatarioTramite().getBytes(ConstantesXML.ENCODING) );
            espec.setProcedimientoDestinoTramite(especificacionesGenericasForm.getProcedimientoDestinoTramite().getBytes(ConstantesXML.ENCODING));
            
            especTramiteNivelDelegate.grabarEspecTramiteNivel( espec );

            //actualizaPath(request.getSession(true), 2, especificacionesGenericas.getId().toString());
            log.info("Creat/Actualitzat " + espec.getCodigo());

            guardarEspecTramiteNivel(mapping, request, espec.getCodigo() );

            //request.setAttribute("reloadMenu", "true");
            return mapping.findForward("success");

        }
        
        
        // Cambio de idioma
        especificacionesGenericasForm.reloadLang();
        
        
        // Establecemos bloqueo
        EspecTramiteNivelDelegate espd = DelegateUtil.getEspecTramiteNivelDelegate();
        TramiteVersion tv = espd.obtenerTramiteVersion(espec.getCodigo());
        this.setBloqueado(request,tv.getBloqueadoModificacion(),tv.getBloqueadoModificacionPor());   
        
        //log.info( "EDICION DE PLANTILLA; BEFORE FORWARD " + especificacionesGenericas );
        
        
        return mapping.findForward("reload");
    }
    


}
