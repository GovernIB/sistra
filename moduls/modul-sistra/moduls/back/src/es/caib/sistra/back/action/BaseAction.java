package es.caib.sistra.back.action;

import java.sql.Timestamp;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.RequestUtils;

import es.caib.sistra.back.form.DatoJustificanteForm;
import es.caib.sistra.back.form.DocumentoForm;
import es.caib.sistra.back.form.DocumentoNivelForm;
import es.caib.sistra.back.form.DominioForm;
import es.caib.sistra.back.form.EspecificacionesGenericasForm;
import es.caib.sistra.back.form.GestorFormulariosForm;
import es.caib.sistra.back.form.MensajePlataformaForm;
import es.caib.sistra.back.form.MensajeTramiteForm;
import es.caib.sistra.back.form.OrganoForm;
import es.caib.sistra.back.form.TramiteForm;
import es.caib.sistra.back.form.TramiteNivelForm;
import es.caib.sistra.back.form.TramiteVersionForm;
import es.caib.sistra.back.taglib.Constants;
import es.caib.sistra.back.util.Util;
import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.model.MensajePlataforma;
import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.model.Tramite;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.persistence.delegate.DatoJustificanteDelegate;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;
import es.caib.sistra.persistence.delegate.DocumentoNivelDelegate;
import es.caib.sistra.persistence.delegate.DominioDelegate;
import es.caib.sistra.persistence.delegate.EspecTramiteNivelDelegate;
import es.caib.sistra.persistence.delegate.GestorFormularioDelegate;
import es.caib.sistra.persistence.delegate.MensajePlataformaDelegate;
import es.caib.sistra.persistence.delegate.MensajeTramiteDelegate;
import es.caib.sistra.persistence.delegate.OrganoResponsableDelegate;
import es.caib.sistra.persistence.delegate.TramiteDelegate;
import es.caib.sistra.persistence.delegate.TramiteNivelDelegate;
import es.caib.sistra.persistence.delegate.TramiteVersionDelegate;
import es.caib.util.CifradoUtil;
import es.caib.xml.ConstantesXML;


/**
 * Action con métodos de utilidad.
 */
public abstract class BaseAction extends Action {

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

    protected boolean archivoValido(FormFile formFile) {
        return (formFile != null && formFile.getFileSize() > 0);
    }

    
    protected void actualizaPath(HttpServletRequest request, int nivel, Long id)
            throws DelegateException {
        
        HttpSession sesion = request.getSession(true);

        sesion.removeAttribute("plantilla");
        sesion.removeAttribute("version");
        sesion.removeAttribute("modelo");

        if (nivel > 2){
        }

        if (nivel > 1){
        }

        if (nivel > 0){
        	
        }

    }
    

    protected ActionForm obtenerActionForm(ActionMapping mapping, HttpServletRequest request, String path) 
    {
        ModuleConfig config = mapping.getModuleConfig();
        ActionMapping newMapping = (ActionMapping) config.findActionConfig(path);
        ActionForm newForm = RequestUtils.createActionForm(request, newMapping, config, this.servlet);
        if ("session".equals(newMapping.getScope())) {
            request.getSession(true).setAttribute(newMapping.getAttribute(), newForm);
        } else {
            request.setAttribute(newMapping.getAttribute(), newForm);
        }
        newForm.reset(newMapping, request);
        return newForm;
    }
    
    protected OrganoResponsable guardarOrgano(ActionMapping mapping, HttpServletRequest request, Long idOrgano)
    throws DelegateException 
    {
		OrganoForm pForm = (OrganoForm) obtenerActionForm(mapping, request, "/back/organo/editar");
		
		OrganoResponsableDelegate delegate = DelegateUtil.getOrganoResponsableDelegate();
		OrganoResponsable organo = delegate.obtenerOrganoResponsable(idOrgano);
		pForm.setValues(organo);
		
		request.setAttribute("idOrgano", idOrgano);
		actualizaPath(request, 1, idOrgano);
		
		return organo;
	}
    
    protected Tramite guardarTramite(ActionMapping mapping, HttpServletRequest request, Long idTramite )
    throws DelegateException 
    {
    	TramiteForm pForm = ( TramiteForm ) obtenerActionForm( mapping, request, "/back/tramite/editar" );
    	TramiteDelegate delegate = DelegateUtil.getTramiteDelegate();
    	Tramite tramite = delegate.obtenerTramite( idTramite );
    	pForm.setIdOrgano( tramite.getOrganoResponsable().getCodigo() );
    	pForm.setValues(tramite);
    	
    	request.setAttribute("idTramite", idTramite);
    	actualizaPath(request, 2, idTramite);
    	return tramite;
    	
    }
    
    protected MensajePlataforma guardarMensajePlataforma(ActionMapping mapping, HttpServletRequest request, Long idMensajePlataforma )
    throws DelegateException 
    {																								  
    	MensajePlataformaForm pForm = ( MensajePlataformaForm ) obtenerActionForm( mapping, request, "/back/mensajePlataforma/editar" );
    	MensajePlataformaDelegate delegate = DelegateUtil.getMensajePlataformaDelegate();
    	MensajePlataforma mensajePlataforma = delegate.obtenerMensajePlataforma( idMensajePlataforma );    	
    	pForm.setValues(mensajePlataforma);
    	
    	request.setAttribute("idMensajePlataforma", idMensajePlataforma);
    	actualizaPath(request, 2, idMensajePlataforma);
    	return mensajePlataforma;
    	
    }
    
    protected EspecTramiteNivel guardarEspecTramiteNivel (ActionMapping mapping, HttpServletRequest request, Long idEspecTramiteNivel )
    throws DelegateException 
    {
    	EspecificacionesGenericasForm pForm = (EspecificacionesGenericasForm ) obtenerActionForm( mapping, request, "/back/especificacionesGenericas/editar" );
    	EspecTramiteNivelDelegate delegate = DelegateUtil.getEspecTramiteNivelDelegate();
    	EspecTramiteNivel espec = delegate.obtenerEspecTramiteNivel( idEspecTramiteNivel );
    	pForm.setValues( espec );
    	//    	Establecer en el formulario la validacion de inicio
    	
    	try
    	{
	    	byte [] validacionInicioScript =  espec.getValidacionInicioScript() ;
   			pForm.setValidacionInicioScript( validacionInicioScript != null ? new String( validacionInicioScript,ConstantesXML.ENCODING ) : null );    			
	    	
   			byte[] campoCodigoLocalidad = espec.getCampoCodigoLocalidad();
    		pForm.setLocalidadScript( campoCodigoLocalidad != null ? new String( campoCodigoLocalidad, ConstantesXML.ENCODING) : null );
	    	
    		byte[] campoCodigoProvincia = espec.getCampoCodigoProvincia();
    		pForm.setProvinciaScript( campoCodigoProvincia != null ? new String( campoCodigoProvincia, ConstantesXML.ENCODING ) : null );
    		
    		byte[] campoCodigoPais = espec.getCampoCodigoPais();
	    	pForm.setPaisScript( campoCodigoPais != null ? new String( campoCodigoPais, ConstantesXML.ENCODING ) : null );
	    	
	    	byte[] campoRteNif = espec.getCampoRteNif();
    		pForm.setCampoRteNif( campoRteNif != null ? new String( campoRteNif, ConstantesXML.ENCODING ) : null );
	    	
	    	byte[] campoRteNom = espec.getCampoRteNom();
    		pForm.setCampoRteNom( campoRteNom != null ? new String( campoRteNom, ConstantesXML.ENCODING ) : null );
    		
	    	byte[] campoRdoNif = espec.getCampoRdoNif();
    		pForm.setCampoRdoNif( campoRdoNif != null ? new String( campoRdoNif, ConstantesXML.ENCODING ) : null );
    		
	    	byte[] campoRdoNom = espec.getCampoRdoNom();
    		pForm.setCampoRdoNom( campoRdoNom != null ? new String( campoRdoNom, ConstantesXML.ENCODING ) : null);
    		
    		byte[] datosRpte = espec.getDatosRpteScript();
    		pForm.setDatosRpte(datosRpte != null ? new String( datosRpte, ConstantesXML.ENCODING ) : null );
    		
    		byte[] datosRpdo = espec.getDatosRpdoScript();
    		pForm.setDatosRpdo(datosRpdo != null ? new String( datosRpdo, ConstantesXML.ENCODING ) : null );
    	
    		byte[] urlFin = espec.getUrlFin();
    		pForm.setUrlFin( urlFin != null ? new String( urlFin, ConstantesXML.ENCODING ) : null);
    		
    		byte[] avisoEmail = espec.getAvisoEmail();
    		pForm.setAvisoEmail( avisoEmail != null ? new String( avisoEmail, ConstantesXML.ENCODING ) : null);
    		
    		byte[] avisoSMS = espec.getAvisoSMS();
    		pForm.setAvisoSMS( avisoSMS != null ? new String( avisoSMS, ConstantesXML.ENCODING ) : null);
    		
    		byte[] checkEnvio = espec.getCheckEnvio();
    		pForm.setCheckEnvio( checkEnvio != null ? new String( checkEnvio, ConstantesXML.ENCODING ) : null);
    	
    		byte[] destinatarioTramite = espec.getDestinatarioTramite();
    		pForm.setDestinatarioTramite( destinatarioTramite != null ? new String( destinatarioTramite, ConstantesXML.ENCODING ) : null);
    		
    		byte[] procedimientoDestinoTramite = espec.getProcedimientoDestinoTramite();
    		pForm.setProcedimientoDestinoTramite( procedimientoDestinoTramite != null ? new String( procedimientoDestinoTramite, ConstantesXML.ENCODING ) : null);
    		
    	}catch (Exception ex){
			throw new DelegateException(ex);
		}
    	
    	request.setAttribute( "idEspecTramiteNivel", espec.getCodigo() );
    	return espec;
    	
    }
    
    protected TramiteVersion guardarTramiteVersion(ActionMapping mapping, HttpServletRequest request, Long idTramiteVersion )
    throws DelegateException 
    {
    	TramiteVersionForm pForm = ( TramiteVersionForm ) obtenerActionForm( mapping, request, "/back/tramiteVersion/editar" );
    	TramiteVersionDelegate delegate = DelegateUtil.getTramiteVersionDelegate();
    	TramiteVersion tramiteVersion = delegate.obtenerTramiteVersion( idTramiteVersion );
    	pForm.setIdTramite( tramiteVersion.getTramite().getCodigo() );
    	//EspecTramiteNivel espec = tramiteVersion.getEspecificaciones();
    	pForm.setValues( tramiteVersion );
    	
		
    	//Establecer las fechas de inicio plazo y fin de plazo en el formulario.
    	Timestamp inicioPlazo = tramiteVersion.getInicioPlazo();
    	Timestamp finPlazo = tramiteVersion.getFinPlazo();
   		pForm.setInicioPlazo( inicioPlazo != null ? Util.sqlTimestampACadena( inicioPlazo ) : null );
   		pForm.setFinPlazo( finPlazo != null ? Util.sqlTimestampACadena( finPlazo ) : null );
   		
   		StringTokenizer st = new StringTokenizer(tramiteVersion.getIdiomasSoportados(),",");
   		String[] idiomas = new String[st.countTokens()];
   		int i=0;
   		while (st.hasMoreTokens()){
   			idiomas[i] = st.nextToken();
   			i++;
   		}
   		pForm.setIdiomas(idiomas);
   		
   		
   		try{
			String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
	        String userPlain = CifradoUtil.descifrar(claveCifrado,tramiteVersion.getConsultaAuthUser());
	        String pasPlain = CifradoUtil.descifrar(claveCifrado,tramiteVersion.getConsultaAuthPwd()); 
			
			pForm.setUserPlain(userPlain);
			pForm.setPassPlain(pasPlain);
		}catch (Exception ex){
			throw new DelegateException(ex);
		}
   		
    	// Habria que ver si este setValues se podría obviar.
    	//pForm.setValues( espec );
    	
    	request.setAttribute( "idTramiteVersion", idTramiteVersion );
    	request.setAttribute( "idEspecTramiteNivel", tramiteVersion.getEspecificaciones().getCodigo() );
    	
    	actualizaPath( request, 3, idTramiteVersion );
    	return tramiteVersion;
    }
    
    protected TramiteNivel guardarTramiteNivel(ActionMapping mapping, HttpServletRequest request, Long idTramiteNivel )
    throws DelegateException 
    {
    	TramiteNivelForm pForm = ( TramiteNivelForm ) obtenerActionForm( mapping, request, "/back/tramiteNivel/editar" );
    	TramiteNivelDelegate delegate = DelegateUtil.getTramiteNivelDelegate();
    	TramiteNivel tramiteNivel = delegate.obtenerTramiteNivel( idTramiteNivel );
    	pForm.setIdTramiteVersion( tramiteNivel.getTramiteVersion().getCodigo() );
    	//tramiteNivel.setCurrentLang( pForm.getLang() );
    	EspecTramiteNivel espec = tramiteNivel.getEspecificaciones();
    	//espec.setCurrentLang( pForm.getLang() );
    	pForm.setTramite( tramiteNivel );
    	
    	try
    	{
    		byte [] validacionInicioScript =  espec.getValidacionInicioScript() ;
   			pForm.setValidacionInicioScript( validacionInicioScript != null ? new String( validacionInicioScript,ConstantesXML.ENCODING ) : null );    			
	    	
   			byte[] campoCodigoLocalidad = espec.getCampoCodigoLocalidad();
    		pForm.setLocalidadScript( campoCodigoLocalidad != null ? new String( campoCodigoLocalidad, ConstantesXML.ENCODING) : null );
	    	
    		byte[] campoCodigoProvincia = espec.getCampoCodigoProvincia();
    		pForm.setProvinciaScript( campoCodigoProvincia != null ? new String( campoCodigoProvincia, ConstantesXML.ENCODING ) : null );
    		
    		byte[] campoCodigoPais = espec.getCampoCodigoPais();
	    	pForm.setPaisScript( campoCodigoPais != null ? new String( campoCodigoPais, ConstantesXML.ENCODING ) : null );
	    	
	    	byte[] campoRteNif = espec.getCampoRteNif();
    		pForm.setCampoRteNif( campoRteNif != null ? new String( campoRteNif, ConstantesXML.ENCODING ) : null );
	    	
	    	byte[] campoRteNom = espec.getCampoRteNom();
    		pForm.setCampoRteNom( campoRteNom != null ? new String( campoRteNom, ConstantesXML.ENCODING ) : null );
    		
	    	byte[] campoRdoNif = espec.getCampoRdoNif();
    		pForm.setCampoRdoNif( campoRdoNif != null ? new String( campoRdoNif, ConstantesXML.ENCODING ) : null );
    		
	    	byte[] campoRdoNom = espec.getCampoRdoNom();
    		pForm.setCampoRdoNom( campoRdoNom != null ? new String( campoRdoNom, ConstantesXML.ENCODING ) : null);
    		
    		byte[] datosRpte = espec.getDatosRpteScript();
    		pForm.setDatosRpte(datosRpte != null ? new String( datosRpte, ConstantesXML.ENCODING ) : null );
    		
    		byte[] datosRpdo = espec.getDatosRpdoScript();
    		pForm.setDatosRpdo(datosRpdo != null ? new String( datosRpdo, ConstantesXML.ENCODING ) : null );
    		
    		byte[] urlFin = espec.getUrlFin();
    		pForm.setUrlFin( urlFin != null ? new String( urlFin, ConstantesXML.ENCODING ) : null);
    		
    		byte[] avisoEmail = espec.getAvisoEmail();
    		pForm.setAvisoEmail( avisoEmail != null ? new String( avisoEmail, ConstantesXML.ENCODING ) : null);
    		
    		byte[] avisoSMS = espec.getAvisoSMS();
    		pForm.setAvisoSMS( avisoSMS != null ? new String( avisoSMS, ConstantesXML.ENCODING ) : null);
    	
    		byte[] checkEnvio = espec.getCheckEnvio();
    		pForm.setCheckEnvio( checkEnvio != null ? new String( checkEnvio, ConstantesXML.ENCODING ) : null);
    		
    		byte[] destinatarioTramite = espec.getDestinatarioTramite();
    		pForm.setDestinatarioTramite( destinatarioTramite != null ? new String( destinatarioTramite, ConstantesXML.ENCODING ) : null);
    		
    		byte[] procedimientoDestinoTramite = espec.getProcedimientoDestinoTramite();
    		pForm.setProcedimientoDestinoTramite( procedimientoDestinoTramite != null ? new String( procedimientoDestinoTramite, ConstantesXML.ENCODING ) : null);
    		
    	}catch (Exception ex){
			throw new DelegateException(ex);
		}
    	
    	String nivelesAutenticacion = tramiteNivel.getNivelAutenticacion();
    	
    	if ( nivelesAutenticacion != null )
    	{
    		pForm.setNivelesAutenticacionSelected( Util.splitString( nivelesAutenticacion ) );
    	}
    	
    	// Habria que ver si este setValues se podría obviar.
    	pForm.setValues( espec );
    	
    	request.setAttribute( "idTramiteNivel", idTramiteNivel );
    	request.setAttribute( "idEspecTramiteNivel", espec.getCodigo() );
    	
    	actualizaPath( request, 4, idTramiteNivel );
    	return tramiteNivel;
    }
    
    protected Documento guardarDocumento(ActionMapping mapping, HttpServletRequest request, Long idDocumento )
    throws DelegateException
    {
    	DocumentoForm pForm = ( DocumentoForm ) obtenerActionForm( mapping, request, "/back/documento/editar" );
    	DocumentoDelegate delegate = DelegateUtil.getDocumentoDelegate();
    	Documento documento = delegate.obtenerDocumento( idDocumento );
    	pForm.setIdTramiteVersion( documento.getTramiteVersion().getCodigo() );
    	pForm.setValues(documento);
    	
    	request.setAttribute("idDocumento", idDocumento);
    	actualizaPath(request, 5, idDocumento);
    	return documento;
    }
    
    protected DocumentoNivel guardarDocumentoNivel(ActionMapping mapping, HttpServletRequest request, Long idDocumentoNivel )
    throws DelegateException
    {
    	DocumentoNivelForm pForm = ( DocumentoNivelForm ) obtenerActionForm( mapping, request, "/back/documentoNivel/editar" );
    	DocumentoNivelDelegate delegate = DelegateUtil.getDocumentoNivelDelegate();
    	DocumentoNivel documentoNivel = delegate.obtenerDocumentoNivel( idDocumentoNivel );
    	
    	try
    	{
   			pForm.setFormularioDatosInicialesScript( documentoNivel.getFormularioDatosInicialesScript() != null ? new String ( documentoNivel.getFormularioDatosInicialesScript() ,ConstantesXML.ENCODING) : null );    			    			    		
   			pForm.setObligatorioScript( documentoNivel.getObligatorioScript() != null ? new String ( documentoNivel.getObligatorioScript() ,ConstantesXML.ENCODING) : null );
  			pForm.setFormularioValidacionPostFormScript( documentoNivel.getFormularioValidacionPostFormScript() != null ? new String ( documentoNivel.getFormularioValidacionPostFormScript() ,ConstantesXML.ENCODING) : null );
  			pForm.setFormularioModificacionPostFormScript( documentoNivel.getFormularioModificacionPostFormScript() != null ? new String ( documentoNivel.getFormularioModificacionPostFormScript() ,ConstantesXML.ENCODING) : null );
   			pForm.setPagoCalcularPagoScript( documentoNivel.getPagoCalcularPagoScript() != null ? new String ( documentoNivel.getPagoCalcularPagoScript()  ,ConstantesXML.ENCODING ) : null );    			    			    		
   			pForm.setFormularioConfiguracionScript( documentoNivel.getFormularioConfiguracionScript() != null ? new String( documentoNivel.getFormularioConfiguracionScript() ,ConstantesXML.ENCODING) : null );    			    			    		
   			pForm.setFormularioPlantillaScript( documentoNivel.getFormularioPlantillaScript() != null ? new String( documentoNivel.getFormularioPlantillaScript() ,ConstantesXML.ENCODING) : null );
   			pForm.setFlujoTramitacionScript( documentoNivel.getFlujoTramitacionScript() != null ? new String( documentoNivel.getFlujoTramitacionScript() ,ConstantesXML.ENCODING) : null );
   			String nivelesAutenticacion = documentoNivel.getNivelAutenticacion();
    		pForm.setNivelesAutenticacionSelected( nivelesAutenticacion != null ? Util.splitString( nivelesAutenticacion ) : null );    		
    	}catch (Exception ex){
			throw new DelegateException(ex);
		}
    	
    	
    	pForm.setIdDocumento( documentoNivel.getDocumento().getCodigo() );
    	pForm.setValues(documentoNivel);
    	
    	request.setAttribute("idDocumentoNivel", idDocumentoNivel);
    	actualizaPath(request, 6, idDocumentoNivel);
    	return documentoNivel;
    }
    
    protected MensajeTramite guardarMensajeTramite(ActionMapping mapping, HttpServletRequest request, Long idMensaje )
    throws DelegateException
    {
    	MensajeTramiteForm pForm = ( MensajeTramiteForm ) obtenerActionForm( mapping, request, "/back/mensajeTramite/editar" );
    	MensajeTramiteDelegate delegate = DelegateUtil.getMensajeTramiteDelegate();
    	MensajeTramite mensaje = delegate.obtenerMensajeTramite( idMensaje );
    	pForm.setIdTramiteVersion( mensaje.getTramiteVersion().getCodigo() );
    	pForm.setValues(mensaje);
    	
    	request.setAttribute("idMensajeTramite", idMensaje);
    	actualizaPath(request, 7, idMensaje);
    	return mensaje;
    }
        
    
    protected DatoJustificante guardarDatoJustificante(ActionMapping mapping, HttpServletRequest request, Long idDatoJustificante )
    throws DelegateException
    {
    	
    	DatoJustificanteForm pForm = ( DatoJustificanteForm ) obtenerActionForm( mapping, request, "/back/datoJustificante/editar" );
    	DatoJustificanteDelegate delegate = DelegateUtil.getDatoJustificanteDelegate();
    	DatoJustificante datoJustificante = delegate.obtenerDatoJustificante( idDatoJustificante );
    	
    	if ( datoJustificante.getVisibleScript() != null )
    	{	
    		try{    			
    			pForm.setVisibleScript( new String( datoJustificante.getVisibleScript(),ConstantesXML.ENCODING) );
    		}catch (Exception ex){
    			throw new DelegateException(ex);
    		}
    	}
    	else
    	{    		
    		pForm.setVisibleScript( null );
    	}
    	
    	if ( datoJustificante.getValorCampoScript() != null )
    	{	
    		try{    			
    			pForm.setValorCampoScript( new String( datoJustificante.getValorCampoScript(),ConstantesXML.ENCODING) );
    		}catch (Exception ex){
    			throw new DelegateException(ex);
    		}
    	}
    	else
    	{    		
    		pForm.setValorCampoScript( null );
    	}
    	
    	
    	pForm.setIdEspecTramiteNivel( datoJustificante.getEspecTramiteNivel().getCodigo() );
    	
    	String idTramiteNivel = request.getParameter( "idTramiteNivel" );
    	if ( !StringUtils.isEmpty( idTramiteNivel ) )
    	{
    		Long longIdTramiteNivel = new Long( idTramiteNivel );
    		pForm.setIdTramiteNivel( longIdTramiteNivel );
    		request.setAttribute( "idTramiteNivel", idTramiteNivel );
    	}
    	
    	pForm.setValues(datoJustificante);
    	
    	request.setAttribute("idDatoJustificante", idDatoJustificante);
    	actualizaPath(request, 8, idDatoJustificante);
    	return datoJustificante;
    } 
    
    protected Dominio guardarDominio(ActionMapping mapping, HttpServletRequest request, Long idDominio)
    throws DelegateException 
    {
		DominioForm pForm = (DominioForm) obtenerActionForm(mapping, request, "/back/dominio/editar");
		
		DominioDelegate delegate = DelegateUtil.getDominioDelegate();
		Dominio dominio = delegate.obtenerDominio(idDominio);
		pForm.setIdOrgano( dominio.getOrganoResponsable().getCodigo() );
		
		try{
			String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
	        String userPlain = CifradoUtil.descifrar(claveCifrado,dominio.getUsr());
	        String pasPlain = CifradoUtil.descifrar(claveCifrado,dominio.getPwd()); 
			
			pForm.setUserPlain(userPlain);
			pForm.setPassPlain(pasPlain);
		}catch (Exception ex){
			throw new DelegateException(ex);
		}
		
		pForm.setValues(dominio);
		
		request.setAttribute("idDominio", idDominio);
		actualizaPath(request, 9, idDominio);
		
		return dominio;
	}
    
    protected GestorFormulario guardarGestorFormulario(ActionMapping mapping, HttpServletRequest request, String idFormulario)
    throws DelegateException 
    {
		GestorFormulariosForm pForm = (GestorFormulariosForm) obtenerActionForm(mapping, request, "/back/gestorFormularios/editar");
		
		GestorFormularioDelegate delegate = DelegateUtil.getGestorFormularioDelegate();
		GestorFormulario gFormulario = delegate.obtener(idFormulario);
		if(gFormulario != null){
			pForm.setValues(gFormulario);
		}
		
		request.setAttribute("idFormulario", idFormulario);
		actualizaPath(request, 9, idFormulario);
		
		return gFormulario;
	}

    protected void actualizaPath(HttpServletRequest request, int nivel, String id)
    	throws DelegateException {

		HttpSession sesion = request.getSession(true);
		
		sesion.removeAttribute("plantilla");
		sesion.removeAttribute("version");
		sesion.removeAttribute("modelo");
		
		if (nivel > 2){
		}
		
		if (nivel > 1){
		}
		
		if (nivel > 0){
			
		}
		
	}
		    
    protected void setReloadTree( HttpServletRequest request, String nodeName, Long entidadId )
    {
        request.setAttribute( "nodoId", nodeName + entidadId );
        request.setAttribute( "reloadMenu", "true");
    }
    
    protected void setBloqueado(HttpServletRequest request,String bloqueado,String bloqueadoPor){
    	String bloq = getBloqueado(request,bloqueado,bloqueadoPor);
        if (bloq != null){	        	
	        	request.setAttribute("bloqueado",bloq);
        }        
    }
    
    protected String getBloqueado(HttpServletRequest request,String bloqueado,String bloqueadoPor){
    	if (bloqueado.equals("S")){
        	if (!request.getUserPrincipal().getName().equals(bloqueadoPor)){	        	
	        	return bloqueadoPor;
        	}
        }else{
        	return "";
        }
    	return null;
    }
}
