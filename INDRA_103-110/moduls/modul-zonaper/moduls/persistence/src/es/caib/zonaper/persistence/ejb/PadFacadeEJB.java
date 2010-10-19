package es.caib.zonaper.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import net.sf.hibernate.SessionFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.audita.modelInterfaz.ConstantesAuditoria;
import es.caib.audita.modelInterfaz.Evento;
import es.caib.audita.persistence.delegate.DelegateAUDUtil;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.persistence.delegate.BteSistraDelegate;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.StringUtil;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.datospropios.factoria.impl.TramiteSubsanacion;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.Justificante;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.DocumentoEntradaTelematica;
import es.caib.zonaper.model.DocumentoNotificacionTelematica;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.DocumentoRegistro;
import es.caib.zonaper.model.ElementoExpediente;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.Expediente;
import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.LogRegistroId;
import es.caib.zonaper.model.NotificacionTelematica;
import es.caib.zonaper.model.ParametrosSubsanacion;
import es.caib.zonaper.model.RegistroExterno;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.ExcepcionPAD;
import es.caib.zonaper.modelInterfaz.ParametrosTramiteSubsanacionPAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.PreregistroPAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.EntradaTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.ExpedienteDelegate;
import es.caib.zonaper.persistence.delegate.LogRegistroDelegate;
import es.caib.zonaper.persistence.delegate.NotificacionTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.PadAplicacionDelegate;
import es.caib.zonaper.persistence.delegate.RegistroExternoDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;
import es.caib.zonaper.persistence.util.LoggerRegistro;

/**
 * SessionBean con funciones a ser invocadas desde los demás módulos de la plataforma
 * Nota: el control de acceso a tramites se implementa en los ejbs de acceso a datos
 *
 * @ejb.bean
 *  name="zonaper/persistence/PadFacade"
 *  jndi-name="es.caib.zonaper.persistence.PadFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *  
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleAuto" type="java.lang.String" value="${role.auto}"
 * @ejb.env-entry name="roleGestor" type="java.lang.String" value="${role.gestor}"
 * 
 */
public abstract class PadFacadeEJB implements SessionBean{
	
	protected static Log log = LogFactory.getLog(HibernateEJB.class);

    protected SessionFactory sf = null;
    protected SessionContext ctx = null;
	
	private String ROLE_AUTO,ROLE_GESTOR;
    
    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }
    
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"
     */
	public void ejbCreate() throws CreateException {		
		try
		{
			javax.naming.InitialContext initialContext = new javax.naming.InitialContext();
			
			ROLE_AUTO = (String) initialContext.lookup( "java:comp/env/roleAuto" );
			ROLE_GESTOR = (String) initialContext.lookup( "java:comp/env/roleGestor" );
			
		}
		catch( Exception exc )
		{			
			log.error( exc );
			throw new CreateException( exc.getLocalizedMessage() );
		}
	}
	  
    /**
     * Obtiene tramite persistencia por identificador de persistencia
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public TramitePersistentePAD obtenerTramitePersistente(String idPersistencia) throws ExcepcionPAD{
    	// Cargamos tramitePersistente      	
    	TramitePersistente tramitePersistente;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();        	        	
    		tramitePersistente = td.obtenerTramitePersistente(idPersistencia);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámite persistente con id: " + idPersistencia,ex);
    	}
        	
        // Pasamos a TramitePersistentePAD
    	return tramitePersistenteToTramitePersistentePAD(tramitePersistente);        	        
    }
           
    
    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario 
     * 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesUsuario() throws ExcepcionPAD{
    	// Cargamos tramitePersistente  
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesUsuario();    		
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + this.ctx.getCallerPrincipal().getName(),ex);
    	}
        	
        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    	}
    	return tramitesPAD;    	
    }
    
    
    /**
     * Obtiene lista de tramites persistentes que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario 
     * 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesEntidadDelegada(String nifEntidad) throws ExcepcionPAD{
    	
    	// Cargamos tramitePersistente  
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesEntidadDelegada(nifEntidad);    		
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + nifEntidad,ex);
    	}
        	
        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    	}
    	return tramitesPAD;    	
    }
    
    /**
     * Obtiene lista de tramites persistentes (de un determinado tramite/version) que tiene pendientes por completar el usuario,
     * o bien ha remitido a otro usuario. 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public List obtenerTramitesPersistentesUsuario(String modelo,int version) throws ExcepcionPAD{
    	// Cargamos tramitePersistente  
    	List tramites;
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		tramites = td.listarTramitePersistentesUsuario(modelo,version);    		
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido recuperar trámites persistentes del usuario: " + this.ctx.getCallerPrincipal().getName(),ex);
    	}
        	
        // Pasamos a TramitePersistentePAD
    	List tramitesPAD = new ArrayList(tramites.size());
    	for (Iterator it = tramites.iterator();it.hasNext();){
    		TramitePersistente tramitePersistente = (TramitePersistente) it.next();
    		tramitesPAD.add(tramitePersistenteToTramitePersistentePAD(tramitePersistente));
    	}
    	return tramitesPAD;    	
    }
    
    /**
     * Obtiene estado de un trámite mediante identificador de persistencia (para los trámites que afectan un usuario)
     * Se tiene en cuenta la representación y el flujo de tramitación 
     * @return No existe ("N") / Terminado ("T") /  Pendiente confirmación ("C") / Pendiente en persistencia ("P") / Remitido a otro usuario por flujo tramitación ("F") 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerEstadoTramiteUsuario(String idPersistencia) throws ExcepcionPAD{
    	return obtenerEstadoTramiteAutenticadoImpl(idPersistencia,null);	
    	}    	
    	
    	
    /**
     * Obtiene estado de un trámite mediante identificador de persistencia para los trámites de la entidad de la que
     * es delegado el usuario
     * 
     * Se tiene en cuenta la representación y el flujo de tramitación 
     * @return No existe ("N") / Terminado ("T") /  Pendiente confirmación ("C") / Pendiente en persistencia ("P") / Remitido a otro usuario por flujo tramitación ("F") 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerEstadoTramiteEntidadDelegada(String idPersistencia, String nifEntidad) throws ExcepcionPAD{
    	return obtenerEstadoTramiteAutenticadoImpl(idPersistencia,nifEntidad);    	
    }
    
    
    /**
     * Obtiene estado de un trámite mediante identificador de persistencia (para los trámites anónimos)
     * @return No existe ("N") / Terminado ("T") / Pendiente confirmación ("C") / Pendiente en persistencia ("P") 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String obtenerEstadoTramiteAnonimo(String idPersistencia) throws ExcepcionPAD{

    	// Buscamos en Zona Persistencia      	
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		TramitePersistente tp = td.obtenerTramitePersistente(idPersistencia);
    		if (tp != null) {
    			if (tp.getNivelAutenticacion() == 'A') return "P"; 
    			// Tramite pertenece a otro usuario
    			return "N";
    		}
    	}catch (Exception ex){
    		// Pasamos a buscar en preregistro
    	}
        	
        // Buscamos en preregistro
    	try{
    		EntradaPreregistroDelegate  epd = DelegateUtil.getEntradaPreregistroDelegate();
    		EntradaPreregistro ep = epd.obtenerEntradaPreregistro(idPersistencia);    	
    		if (ep != null){
	    		if (ep.getNivelAutenticacion() == 'A') {
	    			if (ep.getFechaConfirmacion() != null) return "T";
	    			else return "C";
	    		}
	    		// Tramite pertenece a otro usuario
	    		return "N";
    		}
    	}catch (Exception ex){
    		// Pasamos a buscar en enviados 
    	}    	
    	
    	// Buscamos en entradas telemática
    	try{
    	 	EntradaTelematicaDelegate  etd = DelegateUtil.getEntradaTelematicaDelegate();
    	 	EntradaTelematica et = etd.obtenerEntradaTelematica(idPersistencia); 
    	 	if (et != null) {
	    		if (et.getNivelAutenticacion() == 'A') return "T";
	    		// Tramite pertenece a otro usuario
	    		return "N";
    	 	}
    	}catch (Exception ex){
    		 
    	}
    	
    	// No existe
    	return "N";    	
    }
    
    
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String grabarTramitePersistente(TramitePersistentePAD obj)  throws ExcepcionPAD{            	
    	
    	TramitePersistenteDelegate td;
    	TramitePersistente tramitePersistente;
    	
    	// Realizamos comprobaciones
    	comprobarTramitePersistentePAD(obj);
    	
    	// Construimos objeto tramite 
    	tramitePersistente = tramitePersistentePADToTramitePersistente(obj);    	    	
    	
    	// Guardamos tramite
    	try{
    		td = DelegateUtil.getTramitePersistenteDelegate();
    		String idPersistencia = td.grabarTramitePersistente(tramitePersistente);
    		return idPersistencia;    		
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido guardar trámite persistente",ex);
    	}
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void borrarTramitePersistente(String idPersistencia) throws ExcepcionPAD{     
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();        	        	
    		td.borrarTramitePersistente(idPersistencia);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido borrar trámite persistente con id: " + idPersistencia,ex);
    	}        	        	       
    }
    
      
        
    /**
     * Realiza apuntes de entradas (trámites telemáticos) y salidas (notificaciones telemáticas) en la
     * zona personal del usuario
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     */
    public void logPad(ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws ExcepcionPAD{
    	try{
 		
	    	// Obtenemos asiento del RDS
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	
	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
	    	    	
	    	// Parseamos asiento
	    	DocumentoRDS docRDSAsiento = rds.consultarDocumento(refAsiento);	    	
	    	AsientoRegistral asiento = factoria.crearAsientoRegistral(new ByteArrayInputStream (docRDSAsiento.getDatosFichero()));
						
			// Para acuse de recibo no hay justificante, ya que no se registra
	    	Justificante justificante = null;
			if (asiento.getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_ACUSE_RECIBO){
				DocumentoRDS docRDS = rds.consultarDocumento(refJustificante);
		    	justificante = factoria.crearJustificanteRegistro(new ByteArrayInputStream (docRDS.getDatosFichero()));				
			}	    	 
	    		    	
	    	// Según el tipo de asiento realizamos log	    	
	    	switch (asiento.getDatosOrigen().getTipoRegistro().charValue()){
	    		case ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA:
	    			if(StringUtils.isNotEmpty(asiento.getDatosAsunto().getIdentificadorTramite())){
	    				logPadEntradaTelematica(justificante,refAsiento,refJustificante,refDocumentos);
	    			}else{
	    				logPadRegistroExterno(justificante,refAsiento,refJustificante,refDocumentos);
	    			}
	    			break;
	    		case ConstantesAsientoXML.TIPO_ENVIO:
	    			logPadEntradaTelematica(justificante,refAsiento,refJustificante,refDocumentos);
	    			break;	    		
	    		case ConstantesAsientoXML.TIPO_PREREGISTRO:	    			
	    		case ConstantesAsientoXML.TIPO_PREENVIO:
	    			logPadEntradaPreregistro(justificante,refAsiento,refJustificante,refDocumentos);
	    			break;
	    		case ConstantesAsientoXML.TIPO_REGISTRO_SALIDA:
	    			logPadNotificacionTelematica(justificante,refAsiento,refJustificante,refDocumentos);
	    			break;	    			    			    		    		
	    		default:
	    			throw new Exception("El tipo asiento no es válido para actualizar el log de la Pad: " + asiento.getDatosOrigen().getTipoRegistro());	    			
	    	}	    	    		    		    	
	    		    	    	
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido realizar el log",ex);
    	}
    }
    
    /**
     * Metodo para confirmar un prerregistro en el modulo de preregistro
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public void confirmarPreregistro( Long codigoPreregistro, String oficina, String codProvincia,String codMunicipio, String descMunicipio) throws ExcepcionPAD
    {    		
    		EntradaPreregistro entrada = null;
			try {
				entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroReg( codigoPreregistro );
			} catch (es.caib.zonaper.persistence.delegate.DelegateException e) {
				throw new ExcepcionPAD("No se puede acceder a la entrada de preregistro " + codigoPreregistro, e);
			}
	    	confirmarPreregistroImpl(entrada,ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO,oficina,codProvincia, codMunicipio, descMunicipio,null,null);
    
    }
    
    /**
     * Metodo para confirmar un preenvio que anteriormente se habia confirmado automaticamente
     * 
     * Unicamente debe realizar el registro y actualizar el numero/fecha registro
     *  
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     */
    public void confirmarPreenvioAutomatico( Long codigoPreregistro, String oficina, String codProvincia,String codMunicipio, String descMunicipio) throws ExcepcionPAD
    {
    	EntradaPreregistro entrada = null;
		try {
			entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistroReg( codigoPreregistro );
		} catch (es.caib.zonaper.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("No se puede acceder a la entrada de preregistro " + codigoPreregistro, e);
		}
    	confirmarPreregistroImpl( entrada,ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO,oficina, codProvincia, codMunicipio, descMunicipio,null,null);
    }
    
    
    /**
     * Metodo para obtener los datos de un preregistro no confirmado
     * Lo invocara el gestor desde la BTE para ver los datos de un preregistro no confirmado que le ha llegado.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public PreregistroPAD obtenerInformacionPreregistro( String numeroPreregistro) throws ExcepcionPAD
    {
    	try
    	{
	    	EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
	    	EntradaPreregistro e = epd.obtenerEntradaPreregistroPorNumero(numeroPreregistro);
	    	
	    	// Si no existe devolvemos nulo
	    	if (e == null) return null;
	    	
	    	// Obtenemos asiento para ver el codigo del tramite
	    	ReferenciaRDS refRDSAsiento = construirReferenciaRDS( e.getCodigoRdsAsiento(), e.getClaveRdsAsiento() );
			
	    	// Leemos asiento para obtener código / versión trámite
	    	// TODO SI SE METE EN LAS TABLAS EL IDENTIFICADOR DEL TRAMITE NO HACE FALTA PARSEAR ASIENTO 
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	DocumentoRDS docRDS = rds.consultarDocumento(refRDSAsiento);
	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
	    	AsientoRegistral asiento = factoria.crearAsientoRegistral(new ByteArrayInputStream (docRDS.getDatosFichero()));
			String identificadorTramite = asiento.getDatosAsunto().getIdentificadorTramite();
	    		    		    	
	    	// Devolvemos informacion preregistro
	    	PreregistroPAD p = new PreregistroPAD();
	    	p.setCodigoPreregistro(e.getCodigo());
	    	p.setIdentificadorTramite(identificadorTramite);
	    	p.setNumeroPreregistro(e.getNumeroPreregistro());
	    	p.setFechaPreregistro(e.getFecha());
	    	p.setFechaCaducidad(e.getFechaCaducidad());
	    	p.setNif(e.getNifRepresentante());
	    	p.setNombre(e.getNombreRepresentante());
	    	p.setAsunto(e.getDescripcionTramite());
	    	p.setFechaConfirmacion(e.getFechaConfirmacion());
	    	p.setNumeroRegistro(e.getNumeroRegistro());
	    		    	
	    	return p;	    	
    	}
    	catch( Exception exc )
    	{
    		throw new ExcepcionPAD( "No se ha podido obtener datos del  preregistro", exc);
    	} 	
    }
    
    /**
     * Metodo para confirmar un preeenvio de forma automatica tras enviar el tramite.
     * Se comprobará el usuario de acceso y el id de persistencia
     * Lo único que realiza es realizar el pase a Bandeja. 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void confirmacionAutomaticaPreenvio(String idPersistencia, String numeroPreregistro,Timestamp fechaPreregistro) throws ExcepcionPAD
    {    	
    	
    	// Obtenemos entrada preregistro
    	EntradaPreregistro ep = null;
    	try{    		
    		ep = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro(idPersistencia);
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se puede acceder al tramite");
    	}
    	
    	if (ep == null)
    		throw new ExcepcionPAD("El tramite no existe");
    	
    	if (!ep.getNumeroPreregistro().equals(numeroPreregistro))
    		throw new ExcepcionPAD("Numero de preregistro no coincide");
    	
    	confirmarPreregistroImpl(ep,ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA,null,null,null,null,numeroPreregistro,fechaPreregistro);
    }
    
    
    /**
     * Metodo para confirmar un prerregistro que no se ha confirmado en punto de registro
     * (se ha presentado por registro normal o en otro lugar) y ha llegado al gestor
     *
     * Lo único que realiza es realizar el pase a Bandeja. En caso de que se haya eliminado por
     * fecha de caducidad lo restaura.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public void confirmarPreregistroIncorrecto(Long codigoPreregistro,String numeroRegistro,Timestamp fechaRegistro) throws ExcepcionPAD
    {    
    	EntradaPreregistro entrada = null;
		try {
			entrada = DelegateUtil.getEntradaPreregistroDelegate().obtenerEntradaPreregistro( codigoPreregistro );
		} catch (es.caib.zonaper.persistence.delegate.DelegateException e) {
			throw new ExcepcionPAD("No se puede acceder a la entrada de preregistro " + codigoPreregistro, e);
		}
    	confirmarPreregistroImpl( entrada,ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR,null,null,null,null,numeroRegistro,fechaRegistro);
    }            
    
    
    /**
     * Obtiene datos persona almacenados en PAD buscando por usuario
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try{
    		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
    		return padAplic.obtenerDatosPersonaPADporUsuario(usuario);
    	}catch(Exception ex){
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    }
    
    /**
     * Verifica si existe usuario seycon en la PAD 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public boolean existePersonaPADporUsuario( String usuario ) throws ExcepcionPAD
    {
    	try{
    		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();    		
    		return padAplic.existePersonaPADporUsuario(usuario);
    	}catch(Exception ex){
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    }
    
    /**
     * Obtiene datos persona almacenados en PAD buscando por nif
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public PersonaPAD obtenerDatosPersonaPADporNif( String nif ) throws ExcepcionPAD
    {
    	try{
    		PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
    		return padAplic.obtenerDatosPersonaPADporNif(nif);    		
    	}catch(Exception ex){
    		throw new ExcepcionPAD("Error obteniendo datos de la PAD",ex);
    	}
    	
    }
    
    /**
     * 
     * Da de alta una persona en la PAD
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public PersonaPAD altaPersona( PersonaPAD personaPAD ) throws ExcepcionPAD
    {
    	try
    	{
	    	PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
	    	return padAplic.altaPersona( personaPAD );
    	}
    	catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error modificando datos de la PAD",ex);
    	}
    }
    
    /**
     * 
     * Actualiza usuario de una persona en la PAD
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * 
     * @param personaPAD
     * @return
     * @throws ExcepcionPAD
     */
    public void actualizarCodigoUsuario( String usuOld, String usuNew) throws ExcepcionPAD
    {
    	try
    	{
	    	PadAplicacionDelegate padAplic = DelegateUtil.getPadAplicacionDelegate();
	    	padAplic.modificarHelpdeskCodigoUsuario( usuOld, usuNew );
    	}
    	catch(Exception ex)
    	{
    		throw new ExcepcionPAD("Error modificando datos de la PAD",ex);
    	}
    }
    
    
    /**
     * Obtiene fecha de acuse de recibo
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     */
    public Date obtenerAcuseRecibo(String numeroRegistro) throws ExcepcionPAD
    {	
    	try{
    		NotificacionTelematicaDelegate td = DelegateUtil.getNotificacionTelematicaDelegate();
    		NotificacionTelematica not =td.obtenerNotificacionTelematica(numeroRegistro);
    		if (not == null) return null;
    		return not.getFechaAcuse();
    	}catch (Exception ex){
    		throw new ExcepcionPAD("No se ha podido obtener notificacion con numero de registro: " + numeroRegistro,ex);
    	}
            	        
    }
    
    /**
     * Realiza el log de los registros efectuados para despues comprobar si estan enlazados a elementos de
     * la plataforma.
     * Este log se ejecuta en una nueva transaccion para asegurar que se apunta el numero de registro aunque 
     * se haga un rollback global del proceso
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.transaction type = "RequiresNew"
     * 
     * @param tipo Entrada (E) / Salida (S)
     * @param numeroRegistro Numero de registro
     * @param fechaRegistro Fecha de registro
     */
    public void logRegistro(char tipo, String numeroRegistro, Date fechaRegistro, String error) throws ExcepcionPAD{
    	try{
    		LogRegistroDelegate dlg = DelegateUtil.getLogRegistroDelegate();
    		LogRegistro logReg = new LogRegistro();
    		LogRegistroId logRegId = new LogRegistroId(tipo+"",numeroRegistro);
    		logReg.setId(logRegId);
    		logReg.setFechaRegistro(fechaRegistro);
    		logReg.setDescripcionError(error);
    		logReg.setAnulado("N");
    		dlg.grabarLogRegistro(logReg);
    	}catch(Exception e){
    		throw new ExcepcionPAD("No se ha podido guardar el log de Registro con numero: " + numeroRegistro,e);
    	}
    
    }
    
    
    /**
     * Indica si el usuario autenticado es delegado o representante de alguna entidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public boolean esDelegado() throws ExcepcionPAD
    {	
    	try{    		 
    		return (DelegateUtil.getDelegacionDelegate().obtenerDelegacionesUsuario().size() > 0);    	
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error comprobando si el usuario es delegado de alguna entidad",ex);
    	}
            	        
    }
    
    
    /**
     * Obtiene permisos de delegacion sobre la entidad del usuario autenticado
     * 
     * @param nif nif/cif entidad
     * @return permisos entidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerPermisosDelegacion(String nifEntidad) throws ExcepcionPAD
	{
		// NOTA: Damos permiso al role auto porque puede provenir de una consulta hecha a traves
		//       de ConsultaPADEJB que se ejecuta con role auto
		try{    		 
    		return DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad);    	
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo permisos delegacion",ex);
    	}          
	}
   
	
	/**
     * Obtiene permisos de delegacion sobre la entidad del nif indicado
     * 
     * @param nif nif/cif entidad
     * @param nif nif usuario
     * @return permisos entidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerPermisosDelegacion(String nifEntidad, String nifUsuario) throws ExcepcionPAD
	{
		try{    		 
    		return DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad,nifUsuario);    	
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo permisos delegacion",ex);
    	}          
	}
    
	
	/**
     * Indica si la entidad tiene habilitada la delegacion
     * 
     * @param nif nif/cif entidad
     * @return boolean
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public boolean habilitadaDelegacion(String nifEntidad) throws ExcepcionPAD
	{
		try{    		 
    		return DelegateUtil.getDelegacionDelegate().habilitadaDelegacion(nifEntidad);    	
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo permisos delegacion",ex);
    	}          
	}
	
	/**
     * Recupera parametros tramite subsanacion a partir de la clave de acceso
     * 
     * @param key clave acceso
     * @return ParametrosTramiteSubsanacionPAD
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public ParametrosTramiteSubsanacionPAD recuperaParametrosTramiteSubsanacion(String key) throws ExcepcionPAD
	{
		try{    		 
    		ParametrosSubsanacion p = DelegateUtil.getNotificacionTelematicaDelegate().recuperarParametrosTramiteSubsanacion(key);
    		
    		ParametrosTramiteSubsanacionPAD res = new ParametrosTramiteSubsanacionPAD();
    		res.setExpedienteCodigo(p.getExpedienteCodigo());
    		res.setExpedienteUnidadAdministrativa(p.getExpedienteUnidadAdministrativa());
    		res.setParametros(StringUtil.deserializarMap(p.getParametros()));
    		return res;
    		
    	}catch (Exception ex){
    		throw new ExcepcionPAD("Error obteniendo parametros tramite subsanacion",ex);
    	}          
	}
	
    // ------------------------ Funciones utilidad ----------------------------------------------------------------   
    /**
     * Crea entrada en el log de entradas telemáticas
     * @param idPersistencia
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws ExcepcionPAD
     */
    private void logPadEntradaTelematica(Justificante justificante, ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws Exception{
    	
    	AsientoRegistral asiento = justificante.getAsientoRegistral();
    	
    	EntradaTelematica entrada = new EntradaTelematica();
    	entrada.setTipo(asiento.getDatosOrigen().getTipoRegistro().charValue());
    	    	
    	// Obtenemos datos de representante/representado
    	Iterator it =asiento.getDatosInteresado().iterator(); 
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			entrada.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
    			entrada.setUsuario(di.getUsuarioSeycon()); 
    			entrada.setNifRepresentante(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentante(di.getIdentificacionInteresado());    			
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			entrada.setNifRepresentado(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentado(di.getIdentificacionInteresado());    		
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO)){
    			entrada.setNifDelegado(di.getNumeroIdentificacion());
    			entrada.setNombreDelegado(di.getIdentificacionInteresado());    		
    		}
    	}
    	    	
    	// Obtenemos documentos
    	String idPersistencia = null;
    	String habilitarAvisos= null,avisoSMS= null,avisoEmail= null,habilitarNotificacionTelematica=null;
    	it = asiento.getDatosAnexoDocumentacion().iterator();    	
    	TramiteSubsanacion tramiteSubsanacion = null;
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
    		DocumentoEntradaTelematica doc = new DocumentoEntradaTelematica();
    		doc.setEntradaTelematica(entrada);
    		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
    		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
    		doc.setDescripcion(da.getExtractoDocumento());
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		doc.setCodigoRDS(ref.getCodigo());    		
    		doc.setClaveRDS(ref.getClave());
    		
    		// Si el documento es el de datos propios obtenemos id persistencia
    		if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
    			FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
    			DatosPropios datosPropios = factoria.crearDatosPropios(new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));
    			idPersistencia = datosPropios.getInstrucciones().getIdentificadorPersistencia();
    			habilitarAvisos = datosPropios.getInstrucciones().getHabilitarAvisos();
    			avisoEmail = datosPropios.getInstrucciones().getAvisoEmail();
    			avisoSMS = datosPropios.getInstrucciones().getAvisoSMS();
    			habilitarNotificacionTelematica = datosPropios.getInstrucciones().getHabilitarNotificacionTelematica();
    			if(datosPropios.getInstrucciones().getTramiteSubsanacion() !=  null){
    				tramiteSubsanacion = datosPropios.getInstrucciones().getTramiteSubsanacion();
    				entrada.setSubsanacionExpedienteCodigo(tramiteSubsanacion.getExpedienteCodigo());
    				entrada.setSubsanacionExpedienteUA(tramiteSubsanacion.getExpedienteUnidadAdministrativa());
    			}
    		}
    			    		
    		entrada.addDocumento(doc);    		
    	}
    	
    	// Comprobamos que se haya establecido el identificador de persistencia en el documento 
    	// de datos propios
    	if (StringUtils.isEmpty(idPersistencia)){
    		throw new Exception("No se ha establecido el identificador de persistencia en el documento de datos propios");
    	}
    	
    	// Establecemos datos entrada
    	entrada.setIdPersistencia(idPersistencia);
    	entrada.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
    	entrada.setCodigoRdsAsiento(refAsiento.getCodigo());
    	entrada.setClaveRdsAsiento(refAsiento.getClave());
    	entrada.setCodigoRdsJustificante(refJustificante.getCodigo());
    	entrada.setClaveRdsJustificante(refJustificante.getClave());
    	entrada.setIdioma( asiento.getDatosAsunto().getIdiomaAsunto() );
    	entrada.setNumeroRegistro(justificante.getNumeroRegistro());    	
    	entrada.setFecha(justificante.getFechaRegistro());   
    	entrada.setTramite(StringUtil.getModelo(justificante.getAsientoRegistral().getDatosAsunto().getIdentificadorTramite()));
    	entrada.setVersion(new Integer(StringUtil.getVersion(justificante.getAsientoRegistral().getDatosAsunto().getIdentificadorTramite())));
    	entrada.setHabilitarAvisos(habilitarAvisos);
    	entrada.setAvisoEmail(avisoEmail);
    	entrada.setAvisoSMS(avisoSMS);
    	entrada.setHabilitarNotificacionTelematica(habilitarNotificacionTelematica);
    	
    	// Guardamos en log de entrada telematica    	
    	EntradaTelematicaDelegate td = DelegateUtil.getEntradaTelematicaDelegate();
    	Long codEntrada = td.grabarEntradaTelematica(entrada);    
    	entrada.setCodigo(codEntrada);
    	
    	// Si es un tramite de subsanacion, creamos elemento expediente asociado y actualizamos expediente
    	if (tramiteSubsanacion != null){
    		DelegateUtil.getProcesosAutoDelegate().actualizarExpedienteTramiteSubsanacion(entrada.getCodigo(),ElementoExpediente.TIPO_ENTRADA_TELEMATICA);    		
    	}    	
    	    	    	
    }
    
    
    /**
     * Realiza log de un registro telematico que no procede de sistra
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws Exception
     */
	private void logPadRegistroExterno(Justificante justificante, ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos) throws Exception{
    	AsientoRegistral asiento = justificante.getAsientoRegistral();
    	Principal sp = this.ctx.getCallerPrincipal();
    	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
    	RegistroExterno registro = new RegistroExterno();

    	// Obtenemos datos de representante/representado
    	Iterator it =asiento.getDatosInteresado().iterator(); 
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			//Informamos si existe info de nivel de autenticacion de como se ha autenticado el usuario (opcional)
    			if(di.getNivelAutenticacion() != null){
    				registro.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
    			}
    			registro.setUsuario(di.getUsuarioSeycon()); 
    			registro.setNifRepresentante(di.getNumeroIdentificacion());
    			registro.setNombreRepresentante(di.getIdentificacionInteresado());    			
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			registro.setNifRepresentado(di.getNumeroIdentificacion());
    			registro.setNombreRepresentado(di.getIdentificacionInteresado());    		
    		}
    	}
    	    	
    	// Obtenemos documentos
    	String idPersistencia = null;
    	String habilitarAvisos= null,avisoSMS= null,avisoEmail= null,habilitarNotificacionTelematica=null;
    	it = asiento.getDatosAnexoDocumentacion().iterator();    	
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
    		DocumentoRegistro doc = new DocumentoRegistro();
    		doc.setRegistroExterno(registro);
    		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
    		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
    		doc.setDescripcion(da.getExtractoDocumento());
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		doc.setCodigoRDS(ref.getCodigo());    		
    		doc.setClaveRDS(ref.getClave());
    		registro.addDocumento(doc);    		
    	}
    
    	// Establecemos datos entrada
    	registro.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
    	registro.setCodigoRdsAsiento(refAsiento.getCodigo());
    	registro.setClaveRdsAsiento(refAsiento.getClave());
    	registro.setCodigoRdsJustificante(refJustificante.getCodigo());
    	registro.setClaveRdsJustificante(refJustificante.getClave());
    	registro.setIdioma( asiento.getDatosAsunto().getIdiomaAsunto() );
    	registro.setNumeroRegistro(justificante.getNumeroRegistro());    	
    	registro.setFecha(justificante.getFechaRegistro());   
    	    	    	
    	// Guardamos en log de entrada telematica    	
    	RegistroExternoDelegate td = DelegateUtil.getRegistroExternoDelegate();
    	td.grabarRegistroExterno(registro);
    }
        
    /**
     * Crea entrada en el log de entradas telemáticas (solo para gestores y auto)
     * @param idPersistencia
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws ExcepcionPAD
     */
    private void logPadNotificacionTelematica(Justificante justificante, ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,Map refDocumentos)  throws Exception{
    	
    	// Comprobamos que el usuario pertenezca al role gestor o auto
    	if (!this.ctx.isCallerInRole(ROLE_AUTO) && !this.ctx.isCallerInRole(ROLE_GESTOR))
			throw new Exception("El usuario debe tener el role auto o gestor para realizar el apunte de la notificacion");
    	
    	NotificacionTelematica notificacion = new NotificacionTelematica();
    	Expediente expe = null;
    	
    	// Establecemos usuario gestor que da de alta la notificacion
    	notificacion.setGestorSeycon(this.ctx.getCallerPrincipal().getName());
    	
    	// Obtenemos datos de representante/representado
    	AsientoRegistral asiento = justificante.getAsientoRegistral();
    	Iterator it =asiento.getDatosInteresado().iterator(); 
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			if (StringUtils.isNotEmpty(di.getUsuarioSeycon())){
    				notificacion.setUsuarioSeycon(di.getUsuarioSeycon()); 
    			}
    			notificacion.setNifRepresentante(di.getNumeroIdentificacion());
    			notificacion.setNombreRepresentante(di.getIdentificacionInteresado());    			
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			notificacion.setNifRepresentado(di.getNumeroIdentificacion());
    			notificacion.setNombreRepresentado(di.getIdentificacionInteresado());    		
    		}
    	}
    	    	
    	// Obtenemos documentos: datos aviso, datos oficio y documentos anexos
    	int index=0;
    	it = asiento.getDatosAnexoDocumentacion().iterator();    	
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		
    		switch (da.getTipoDocumento().charValue()){
    			// Datos aviso notificacion: establecemos datos aviso en notificacion    		
    			case ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION:
    				// Capturamos el titulo de aviso como descripción de la notificación
    				FactoriaObjetosXMLAvisoNotificacion factoria = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
    				AvisoNotificacion avisoNotificacion = factoria.crearAvisoNotificacion(new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));        			
    				
    				// Obtenemos expediente asociado a la notificacion    				    			
    				ExpedienteDelegate ed = DelegateUtil.getExpedienteDelegate();
    				expe = ed.obtenerExpediente(Long.parseLong(avisoNotificacion.getExpediente().getUnidadAdministrativa()),avisoNotificacion.getExpediente().getIdentificadorExpediente());
    				if (expe == null){
    					throw new Exception("No existe expediente indicado en la notificacion: " + avisoNotificacion.getExpediente().getIdentificadorExpediente() + " - " + avisoNotificacion.getExpediente().getUnidadAdministrativa());
    				}
    				if (expe.getClaveExpediente() != null && !expe.getClaveExpediente().equals(avisoNotificacion.getExpediente().getClaveExpediente())){
    					throw new Exception("Clave de acceso al expediente incorrecta: " + avisoNotificacion.getExpediente().getIdentificadorExpediente() + " - " + avisoNotificacion.getExpediente().getUnidadAdministrativa());
    				}

    				// A PARTIR V1.1.0 COMPROBAMOS POR NIF, NO POR USUARIO SEYCON 
    				/* 
    				if (
    					(expe.getSeyconCiudadano() != null && !expe.getSeyconCiudadano().equals(notificacion.getUsuarioSeycon())) ||
    					(expe.getSeyconCiudadano() == null && notificacion.getUsuarioSeycon() != null)
    					){
    						throw new Exception("No concuerda usuario seycon indicado en la notificacion con el del expediente");
    				}
    				*/
    				if (
    						( expe.getSeyconCiudadano() != null && !expe.getNifRepresentante().equals(notificacion.getNifRepresentante())) 
    						||
    						( expe.getSeyconCiudadano() == null && notificacion.getUsuarioSeycon() != null)
    					){
    						throw new Exception("No concuerda usuario seycon indicado en la notificacion con el del expediente");
    				}
    					    					
    				// Establecemos datos    				
    				notificacion.setCodigoRdsAviso(ref.getCodigo());
    				notificacion.setClaveRdsAviso(ref.getClave());
    				notificacion.setTituloAviso(avisoNotificacion.getTitulo().length()<500?avisoNotificacion.getTitulo():avisoNotificacion.getTitulo().substring(0,500));    				
    				notificacion.setFirmarAcuse(avisoNotificacion.getAcuseRecibo().booleanValue());
    				break;
    				
    		    //  Datos oficio remision: establecemos datos oficio en notificacion
    			case ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION:
    				FactoriaObjetosXMLOficioRemision factoriaOR = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
    				OficioRemision oficioRemision = factoriaOR.crearOficioRemision (new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));
    				
    				notificacion.setCodigoRdsOficio(ref.getCodigo());
    				notificacion.setClaveRdsOficio(ref.getClave());
    				if (oficioRemision.getTramiteSubsanacion() != null){
    					// Verificamos datos obligatorios
    					if (StringUtils.isEmpty(oficioRemision.getTramiteSubsanacion().getDescripcionTramite())){
    						throw new Exception("No se ha indicado la descripcion del tramite de subsanacion");
    					}
    					if (StringUtils.isEmpty(oficioRemision.getTramiteSubsanacion().getIdentificadorTramite())){
    						throw new Exception("No se ha indicado el id del tramite de subsanacion");
    					}
    					if (oficioRemision.getTramiteSubsanacion().getVersionTramite() == null){
    						throw new Exception("No se ha indicado la version del tramite de subsanacion");
    					}
    					// Establecemos datos
    					notificacion.setTramiteSubsanacionDescripcion(oficioRemision.getTramiteSubsanacion().getDescripcionTramite());
    					notificacion.setTramiteSubsanacionIdentificador(oficioRemision.getTramiteSubsanacion().getIdentificadorTramite());
    					notificacion.setTramiteSubsanacionVersion(oficioRemision.getTramiteSubsanacion().getVersionTramite());
    					notificacion.setTramiteSubsanacionParametros(StringUtil.serializarMap(oficioRemision.getTramiteSubsanacion().getParametrosTramite()));
    				}
    				
    				break;	    		
    			// Anexo asociado a notificacion	
    			default:
    	    		DocumentoNotificacionTelematica doc = new DocumentoNotificacionTelematica();
	        		doc.setOrden(new Integer(index));       		
	        		doc.setNotificacionTelematica(notificacion);
	        		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));
	        		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));
	        		doc.setDescripcion(da.getExtractoDocumento());
	        		doc.setCodigoRDS(ref.getCodigo());    		
	        		doc.setClaveRDS(ref.getClave());
	
    				notificacion.addDocumento(doc);  
	        		index++;
    		}
    		  		
    	}
    	
    	// Establecemos datos notificacion
    	notificacion.setNumeroRegistro(asiento.getDatosOrigen().getNumeroRegistro());
    	notificacion.setFechaRegistro(justificante.getFechaRegistro());
    	
    	notificacion.setCodigoRdsAsiento(refAsiento.getCodigo());
    	notificacion.setClaveRdsAsiento(refAsiento.getClave());
    	notificacion.setCodigoRdsJustificante(refJustificante.getCodigo());
    	notificacion.setClaveRdsJustificante(refJustificante.getClave());
    	
    	notificacion.setIdioma( asiento.getDatosAsunto().getIdiomaAsunto() );
    	notificacion.setNumeroRegistro(justificante.getNumeroRegistro());

    	// Guardamos en log de notificacion telematica
    	NotificacionTelematicaDelegate td = DelegateUtil.getNotificacionTelematicaDelegate();    	
    	Long codigoNotificacion = td.grabarNotificacionTelematica(notificacion);
    	notificacion.setCodigo(codigoNotificacion);
    	
    	// Creamos elemento expediente asociado a la notificacion y actualizamos expediente
    	ElementoExpediente el = new ElementoExpediente();
    	el.setExpediente(expe);
    	el.setTipoElemento(ElementoExpediente.TIPO_NOTIFICACION);
    	el.setFecha(notificacion.getFechaRegistro());
    	el.setCodigoElemento(notificacion.getCodigo());
    	expe.addElementoExpediente(el,notificacion);
    	DelegateUtil.getExpedienteDelegate().grabarExpediente(expe);
    	
    	// Realizamos aviso de movilidad
    	DelegateUtil.getProcesosAutoDelegate().avisoCreacionElementoExpediente(el);    	
    	        	
    }
    
        
    
    /**
     * Crea entrada en el log de preregistro
     * @param idPersistencia
     * @param justificante
     * @param refAsiento
     * @param refJustificante
     * @param refDocumentos
     * @throws ExcepcionPAD
     */
    private void logPadEntradaPreregistro(Justificante justificante,ReferenciaRDS refAsiento,  ReferenciaRDS refJustificante,Map refDocumentos)  throws Exception{
    	AsientoRegistral asiento = justificante.getAsientoRegistral();
    	
    	EntradaPreregistro entrada = new EntradaPreregistro();
    	entrada.setTipo(asiento.getDatosOrigen().getTipoRegistro().charValue());
    	    	
    	
    	// Obtenemos informacion de representante/representado
    	Iterator it = asiento.getDatosInteresado().iterator();
    	while (it.hasNext()){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			entrada.setNivelAutenticacion(di.getNivelAutenticacion().charValue());
    			entrada.setUsuario(di.getUsuarioSeycon());    				    		
    			entrada.setNifRepresentante(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentante(di.getIdentificacionInteresado());    			
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO)){
    			entrada.setNifRepresentado(di.getNumeroIdentificacion());
    			entrada.setNombreRepresentado(di.getIdentificacionInteresado());    		
    		}else if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO)){
    			entrada.setNifDelegado(di.getNumeroIdentificacion());
    			entrada.setNombreDelegado(di.getIdentificacionInteresado());    		
    		}
    	}
    	
    	// Establecemos datos de la entrada    	
    	entrada.setDescripcionTramite(asiento.getDatosAsunto().getExtractoAsunto());
    	entrada.setCodigoRdsAsiento(refAsiento.getCodigo());
    	entrada.setClaveRdsAsiento(refAsiento.getClave());
    	entrada.setCodigoRdsJustificante(refJustificante.getCodigo());
    	entrada.setClaveRdsJustificante(refJustificante.getClave());
    	entrada.setIdioma(asiento.getDatosAsunto().getIdiomaAsunto());
    	entrada.setTramite(StringUtil.getModelo(asiento.getDatosAsunto().getIdentificadorTramite()));
    	entrada.setVersion(new Integer(StringUtil.getVersion(asiento.getDatosAsunto().getIdentificadorTramite())));
    	
    	entrada.setNumeroPreregistro(justificante.getNumeroRegistro());    	
    	entrada.setFecha(justificante.getFechaRegistro());    	
    	    	
    	// Documentos asiento    	
    	String idPersistencia = null;
    	Iterator itd = asiento.getDatosAnexoDocumentacion().iterator();    	
    	DatosPropios datosPropios = null;    	
    	HashMap docsPre = new HashMap(); // Hash auxiliar para asociar documentos asiento con documentos presenciales
    	while (itd.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) itd.next();    	    		
    		DocumentoEntradaPreregistro doc = new DocumentoEntradaPreregistro();
    		doc.setEntradaPreregistro(entrada);
    		doc.setPresencial('N');
    		doc.setDescripcion(da.getExtractoDocumento());    		
    		doc.setIdentificador(StringUtil.getModelo(da.getIdentificadorDocumento()));    	
    		doc.setNumeroInstancia(StringUtil.getVersion(da.getIdentificadorDocumento()));    		
    		ReferenciaRDS ref = (ReferenciaRDS) refDocumentos.get(da.getIdentificadorDocumento());
    		doc.setCodigoRDS(ref.getCodigo());    		
    		doc.setClaveRDS(ref.getClave());
    		
    		entrada.addDocumento(doc);    		
    		    		    		
    		// Si el documento es el de datos propios obtenemos id persistencia
    		
    		if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
    			// Obtenemos id persistencia
    			FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
    			datosPropios = factoria.crearDatosPropios(new ByteArrayInputStream (consultarDocumentoRDS(ref.getCodigo(),ref.getClave())));
    			idPersistencia = datosPropios.getInstrucciones().getIdentificadorPersistencia();    			    	
    			
    		}
    		
    		docsPre.put(da.getIdentificadorDocumento(),doc);
    	}
    	
    	// Comprobacion de que exista documento de datos propios
    	if (datosPropios == null) throw new Exception("No se encuentra documento de datos propios");
    	
    	// Comprobamos que se haya establecido el identificador de persistencia en el documento de datos propios
    	if (StringUtils.isEmpty(idPersistencia)){
    		throw new Exception("No se ha establecido el identificador de persistencia en el documento de datos propios");
    	}
    	entrada.setIdPersistencia(idPersistencia);
    	
    	// Fecha limite de entrega
    	entrada.setFechaCaducidad(datosPropios.getInstrucciones().getFechaTopeEntrega());
    	
    	// Configuracion de avisos    	
    	entrada.setHabilitarAvisos(datosPropios.getInstrucciones().getHabilitarAvisos());
    	entrada.setAvisoEmail(datosPropios.getInstrucciones().getAvisoEmail());
    	entrada.setAvisoSMS(datosPropios.getInstrucciones().getAvisoSMS());
    	
    	// Configuracion notificacion telematica
    	entrada.setHabilitarNotificacionTelematica(datosPropios.getInstrucciones().getHabilitarNotificacionTelematica());
    	
    	// Documentos presenciales
    	for (it = datosPropios.getInstrucciones().getDocumentosEntregar().getDocumento().iterator();it.hasNext();){
    		Documento docPres = (Documento) it.next();
    		// Obviamos el justificante, ya que en la entrada se establecera la referencia y clave RDS de dicho justificante
    		if ( docPres.getTipo().charValue() ==  ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE )
    		{
    			continue;
    		}
    		    		
    		// Comprobamos si esta asociado a un documento presentado en el asiento
    		// Si no esta asociado creamos nuevo documento
    		DocumentoEntradaPreregistro doc = null;     		
    		if (docPres.getIdentificador() != null ) {
    			doc = (DocumentoEntradaPreregistro) docsPre.get(docPres.getIdentificador());
    		}
    		
    		if (doc == null) {
    			doc = new DocumentoEntradaPreregistro();
    			doc.setDescripcion( docPres.getTitulo() );
    			doc.setEntradaPreregistro(entrada);   
    			if (docPres.getIdentificador() != null ){
    				doc.setIdentificador(StringUtil.getModelo(docPres.getIdentificador()));    	
    	    		doc.setNumeroInstancia(StringUtil.getVersion(docPres.getIdentificador()));
    			}
    		}
    		
    		// Establecemos parametros presenciales
    		doc.setPresencial('S');    		
    		doc.setTipoDocumento(docPres.getTipo().charValue());    		    		    		
    		doc.setCompulsarDocumento((docPres.isCompulsar().booleanValue()?'S':'N'));
    		doc.setFirma((docPres.isFirmar().booleanValue()?'S':'N'));
    		doc.setFotocopia((docPres.isFotocopia().booleanValue()?'S':'N'));	
    		
    		entrada.addDocumento(doc);    	
    	}    	
    	    	
    	// Si es un tramite de subsanacion, creamos elemento expediente asociado y actualizamos expediente
    	TramiteSubsanacion tramiteSubsanacion = null;
    	if( datosPropios.getInstrucciones().getTramiteSubsanacion() != null){
    		tramiteSubsanacion = datosPropios.getInstrucciones().getTramiteSubsanacion();
    		entrada.setSubsanacionExpedienteCodigo(tramiteSubsanacion.getExpedienteCodigo());
    		entrada.setSubsanacionExpedienteUA(tramiteSubsanacion.getExpedienteUnidadAdministrativa());
    	}
    	// Guardamos en log de entrada preregistro    	
    	EntradaPreregistroDelegate td = DelegateUtil.getEntradaPreregistroDelegate();
    	Long codEntrada = td.grabarEntradaPreregistro(entrada);
    	entrada.setCodigo(codEntrada);
    	
    	
    	// Si es un tramite de subsanacion actualizamos expediente
    	if (tramiteSubsanacion != null){
    		DelegateUtil.getProcesosAutoDelegate().actualizarExpedienteTramiteSubsanacion(entrada.getCodigo(),ElementoExpediente.TIPO_ENTRADA_PREREGISTRO);    		
    	}
    }   
    
    /**
     * Convierte TramitePersistente en TramitePersistentePAD
     * @param t
     * @return
     */
    private TramitePersistentePAD tramitePersistenteToTramitePersistentePAD(TramitePersistente t) throws ExcepcionPAD{    	
    	try{
	    	TramitePersistentePAD tpad = new TramitePersistentePAD();
	    	tpad.setIdPersistencia(t.getIdPersistencia());
	    	tpad.setTramite(t.getTramite());
	    	tpad.setVersion(t.getVersion());
	    	tpad.setDescripcion( t.getDescripcion() );
	    	tpad.setNivelAutenticacion(t.getNivelAutenticacion());
	    	tpad.setUsuario(t.getUsuario());
	    	tpad.setUsuarioFlujoTramitacion(t.getUsuarioFlujoTramitacion());
	    	tpad.setFechaCreacion(t.getFechaCreacion());
	    	tpad.setFechaModificacion(t.getFechaModificacion());
	    	tpad.setFechaCaducidad(t.getFechaCaducidad());
	    	tpad.setIdioma(t.getIdioma());
	    	tpad.setParametrosInicio(t.getParametrosInicioMap());	    	
	    	tpad.setDelegado(t.getDelegado());
	    	tpad.setEstadoDelegacion(t.getEstadoDelegacion());
	    	
	    	for (Iterator it = t.getDocumentos().iterator();it.hasNext();){    		
	    		DocumentoPersistente d = (DocumentoPersistente) it.next();
	    		DocumentoPersistentePAD dpad = new DocumentoPersistentePAD();
	    		
	    		dpad.setIdentificador(d.getIdentificador());
	    		dpad.setNumeroInstancia(d.getNumeroInstancia());
	    		dpad.setEstado(d.getEstado());
	    		dpad.setNombreFicheroAnexo(d.getNombreFicheroAnexo());
	    		dpad.setDescripcionGenerico(d.getDescripcionGenerico());
	    		
	    		if (d.getRdsClave() != null && d.getRdsCodigo() != null){
	    			ReferenciaRDS ref = new ReferenciaRDS();
	    			ref.setCodigo(d.getRdsCodigo().longValue());
	    			ref.setClave(d.getRdsClave());
	    			dpad.setReferenciaRDS(ref);
	    		}
	    		
	    		dpad.setDelegacionEstado(d.getDelegacionEstado());
	    		dpad.setDelegacionFirmantes(d.getDelegacionFirmantes());
	    		dpad.setDelegacionFirmantesPendientes(d.getDelegacionFirmantesPendientes());
	    		
	    		tpad.getDocumentos().put(dpad.getIdentificador() + "-"+d.getNumeroInstancia(),dpad);
	    	}    	
	    	return tpad;    	
    	}catch (Exception e){
    		throw new ExcepcionPAD("No se ha podido convertir TramitePersistente en TramitePersistentePAD",e);
    	}
    }
    
    
    /**
     * Convierte TramitePersistentePAD en TramitePersistente
     * @param t
     * @return
     */
    private TramitePersistente tramitePersistentePADToTramitePersistente(TramitePersistentePAD tpad) throws ExcepcionPAD{
    	try{
    		
    		TramitePersistenteDelegate td;
    		try{
        		td = DelegateUtil.getTramitePersistenteDelegate();    		
        	}catch (Exception ex){
        		throw new ExcepcionPAD("No se ha podido obtener delegado",ex);
        	}    	
        	        	     	
        	// Comprobamos si es nuevo
        	TramitePersistente t;
    		Timestamp ahora = new Timestamp(System.currentTimeMillis());
    		if (tpad.getIdPersistencia() != null){
    			// Si no es nuevo recuperamos tramite y eliminamos documentos anteriores
    			td.borrarDocumentosTramitePersistente(tpad.getIdPersistencia());
    			t = td.obtenerTramitePersistente(tpad.getIdPersistencia());
    			
    			// Comprobamos que tramite no haya variado
    			if (!t.getTramite().equals(tpad.getTramite())) throw new ExcepcionPAD("El tramite no coincide");
    			if (t.getVersion() != tpad.getVersion()) throw new ExcepcionPAD("La versión no coincide");
    			if (t.getNivelAutenticacion() != tpad.getNivelAutenticacion()) throw new ExcepcionPAD("El nivel de autenticación no coincide");
    			
    			if (!t.getFechaModificacion().equals(tpad.getFechaModificacion()))  throw new ExcepcionPAD("Se ha modificado el trámite en otra sesión");
    			
    		}else{    			
    			// Si es nuevo creamos objeto   
    			t = new TramitePersistente();
    			// Establecemos datos tramite
    			t.setTramite(tpad.getTramite());
    	    	t.setVersion(tpad.getVersion());
    	    	t.setDescripcion( tpad.getDescripcion());
    	    	t.setNivelAutenticacion(tpad.getNivelAutenticacion());
    	    	t.setUsuario(tpad.getUsuario());
    	    	t.setFechaCreacion(ahora);    	    	
    	    	t.setIdioma(tpad.getIdioma());
    	    	t.setParametrosInicioMap(tpad.getParametrosInicio());
    		}
    		
    		// Establecemos usuario que tiene actualmente el trámite
    		t.setUsuarioFlujoTramitacion(tpad.getUsuarioFlujoTramitacion());
    		
    		// En caso de delegacion establecemos delegado
    		t.setDelegado(tpad.getDelegado());
    		t.setEstadoDelegacion(tpad.getEstadoDelegacion());
    		
    		// Establecemos fecha modificacion y caducidad
    		t.setFechaModificacion(ahora);
    		t.setFechaCaducidad(tpad.getFechaCaducidad());
    		
    		// Establecemos documentos	    	
	    	for (Iterator it = tpad.getDocumentos().keySet().iterator();it.hasNext();){
	    		String ls_key = (String) it.next();
	    		DocumentoPersistentePAD dpad = (DocumentoPersistentePAD) tpad.getDocumentos().get(ls_key);
	    		DocumentoPersistente d = new DocumentoPersistente();
	    		
	    		d.setIdentificador(dpad.getIdentificador());
	    		d.setNumeroInstancia(dpad.getNumeroInstancia());
	    		d.setEstado(dpad.getEstado());
	    		d.setNombreFicheroAnexo(dpad.getNombreFicheroAnexo());
	    		d.setDescripcionGenerico(dpad.getDescripcionGenerico());
	    		
	    		if (dpad.getRefRDS() != null ){
	    			d.setRdsCodigo(new Long(dpad.getRefRDS().getCodigo()));
	    			d.setRdsClave(dpad.getRefRDS().getClave());    			
	    		}
	    		
	    		d.setDelegacionEstado(dpad.getDelegacionEstado());
	    		d.setDelegacionFirmantes(dpad.getDelegacionFirmantes());
	    		d.setDelegacionFirmantesPendientes(dpad.getDelegacionFirmantesPendientes());
	    		
	    		t.addDocumento(d);
	    	}    	
	    	return t;  
	    }catch (Exception e){
			throw new ExcepcionPAD("No se ha podido convertir TramitePersistente en TramitePersistentePAD",e);
		}
    }
    
    
  
    
    /**
     * Comprobaciones sobre tramite persistente
     * @param obj
     * @throws ExcepcionPAD
     */
    private void comprobarTramitePersistentePAD(TramitePersistentePAD obj) throws ExcepcionPAD{            	    	    
	    	// Realizamos comprobaciones
	    	switch (obj.getNivelAutenticacion()){
	    		case TramitePersistentePAD.AUTENTICACION_ANONIMO:
	    			if (obj.getUsuario() != null || obj.getUsuarioFlujoTramitacion() != null){
	    				throw new ExcepcionPAD("Si el nivel de autenticación es anónimo no se puede indicar usuario");
	    			}
	    			break;
	    		case TramitePersistentePAD.AUTENTICACION_CERTIFICADO:
	    		case TramitePersistentePAD.AUTENTICACION_USUARIOPASSWORD:
	    			if (obj.getUsuario() == null || obj.getUsuarioFlujoTramitacion() == null) {
	    				throw new ExcepcionPAD("El nivel de autenticación requiere indicar usuario");
	    			}    		
	    			break;
	    		default:
	    			throw new ExcepcionPAD("Nivel de autenticación no definido (" + obj.getNivelAutenticacion() + ")");
	    	}    	
    }
  	
    private ReferenciaRDS construirReferenciaRDS( long codigo, String clave )
    {
    	ReferenciaRDS referenciaRDS = new ReferenciaRDS();
		referenciaRDS.setCodigo( codigo );
		referenciaRDS.setClave( clave );
		return referenciaRDS;
    }
    
    private Map construirMapReferenciasRDSDocsEntPrerr( Set setDocumentosEntradaPrerregistro )
    {
    	Map mReturn = new HashMap();
    	for ( Iterator it = setDocumentosEntradaPrerregistro.iterator(); it.hasNext(); )
    	{    		    	
    		DocumentoEntradaPreregistro documento = ( DocumentoEntradaPreregistro ) it.next();
    		if (documento.getCodigoRDS() > 0 && documento.getClaveRDS() != null){
    			mReturn.put( documento.getIdentificador() + "-" + documento.getNumeroInstancia(), construirReferenciaRDS ( documento.getCodigoRDS(), documento.getClaveRDS()  ) );
    		}
    	}
    	return mReturn;
    }
	
	private byte[] consultarDocumentoRDS( long codigo, String clave ) throws DelegateException
	{
		ReferenciaRDS referenciaRDS = construirReferenciaRDS( codigo, clave );
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS 	= rdsDelegate.consultarDocumento( referenciaRDS );
		return documentoRDS.getDatosFichero();
	}
	
	 /**
	  * Realiza log
	  * @param nivelAutenticacion
	  * @param seyconUser
	  * @param idDocumentoIdPersonal
	  * @param nombre
	  * @param lang
	  * @param result
	  * @param descripcion
	  * @throws Exception
	  */
	 private void logEvento(
			 String evento,char nivelAutenticacion, String seyconUser, String idDocumentoIdPersonal,
			 String nombre, String lang, String result, String descripcion,String modeloTramite,int versionTramite ) throws Exception
	{
		try{
			Evento eventoAuditado = new Evento();
			eventoAuditado.setTipo( evento );
			if (nivelAutenticacion == 'A' || nivelAutenticacion == 'U' || nivelAutenticacion == 'C' ) {
				eventoAuditado.setNivelAutenticacion( String.valueOf( nivelAutenticacion ) );
				if ( nivelAutenticacion != 'A' )
				{
					eventoAuditado.setUsuarioSeycon( seyconUser );
					eventoAuditado.setNumeroDocumentoIdentificacion( idDocumentoIdPersonal );
					eventoAuditado.setNombre( nombre );
				}
			}
			eventoAuditado.setDescripcion( descripcion );
			eventoAuditado.setIdioma( lang );
			eventoAuditado.setResultado( result );
			eventoAuditado.setModeloTramite(modeloTramite);
			eventoAuditado.setVersionTramite(versionTramite);
			DelegateAUDUtil.getAuditaDelegate().logEvento( eventoAuditado );
		}catch(Exception ex){
			// logger.error("Excepción auditoria en Zona Personal: " + ex.getMessage(),ex);
		}
	}
	
	/**
	 * Genera usos RDS en confirmacion preregistro para asiento, justificante y documentos asociados
	 */
	private void generarUsosRDS(String tipoUso,AsientoRegistral asientoXML,Map referenciasRDS,
			ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,
			String numero) throws Exception{
		
		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
		UsoRDS uso;
		
		// Creamos uso para asiento					
		uso = new UsoRDS();
		uso.setReferenciaRDS(refAsiento);
		uso.setReferencia(numero);
		uso.setTipoUso(tipoUso);
		rds.crearUso(uso);
		
		// Creamos uso para justificante					
		uso = new UsoRDS();
		uso.setReferenciaRDS(refJustificante);
		uso.setReferencia(numero);
		uso.setTipoUso(tipoUso);		
		rds.crearUso(uso);
		
		// Creamos usos para documentos asiento
		Iterator it = asientoXML.getDatosAnexoDocumentacion().iterator();
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
			uso.setReferenciaRDS((ReferenciaRDS) referenciasRDS.get(da.getIdentificadorDocumento()));
			uso.setReferencia(numero);
			uso.setTipoUso(tipoUso);		
			rds.crearUso(uso);
    	}    	
		
	}
	
	
	// Realiza proceso de confirmacion de registro teniendo en cuenta si debe hacer registro o no (para confirmacion de incorrectos)
	private void confirmarPreregistroImpl( EntradaPreregistro entrada, String tipoConfirmacionPreregistro,String oficina, String codProvincia,String codMunicipio, String descMunicipio, String numeroRegistroPI,Date fechaRegistroPI) throws ExcepcionPAD
    {
    	ResultadoRegistro resultadoRegistro = null;
    	boolean exitoProceso = false;
    	try
    	{
	    	// Creamos referencias RDS
	    	ReferenciaRDS refRDSAsiento 		= construirReferenciaRDS( entrada.getCodigoRdsAsiento(), entrada.getClaveRdsAsiento() );
			ReferenciaRDS refRDSJustificante 	= construirReferenciaRDS( entrada.getCodigoRdsJustificante(), entrada.getClaveRdsJustificante() );
			Map mapRefRDSDocumentos 			= construirMapReferenciasRDSDocsEntPrerr( entrada.getDocumentos() );
	    	
	    	// Leemos asiento para obtener código / versión trámite
	    	RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
	    	DocumentoRDS docRDS = rds.consultarDocumento(refRDSJustificante);
	    	FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			Justificante justificante = factoria.crearJustificanteRegistro(new ByteArrayInputStream (docRDS.getDatosFichero()));
			AsientoRegistral asiento = justificante.getAsientoRegistral();
	    		    	
	    	// En caso de ser una confirmación de un preregistro normal generamos registro de entrada
			String numeroRegistro;
			Date fechaRegistro;
			
			/**
			 * Confirmacion en registro presencial: 
			 * 	- tanto para preregistros como para preenvios, se hará una llamada al sistema de registro
			 *  - tambien para preenvios confirmados automaticamente
			 */
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) ||
				tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO) ){							
			    	resultadoRegistro = DelegateRegtelUtil.getRegistroTelematicoDelegate().confirmarPreregistro(oficina, codProvincia,codMunicipio,descMunicipio,justificante,refRDSJustificante,refRDSAsiento,mapRefRDSDocumentos);
					numeroRegistro = resultadoRegistro.getNumeroRegistro();
					fechaRegistro = StringUtil.cadenaAFecha( resultadoRegistro.getFechaRegistro(), StringUtil.FORMATO_REGISTRO ); 
			}else{
					// En caso de ser una confirmación de un preregistro incorrecto no generamos registro de entrada
					// En este caso si se pasa el numero/fecha registro la alimentamos si no establecemos el num. preregistro y 
					// la fecha actual
					if (numeroRegistroPI!=null && fechaRegistroPI!=null){
						numeroRegistro= numeroRegistroPI;
						fechaRegistro = fechaRegistroPI;
					}else{
						numeroRegistro=entrada.getNumeroPreregistro();
						fechaRegistro = new Date();
					}
			}
			
	    	// Creamos la entrada de preregistro en la BTE	    	
			BteSistraDelegate bteDelegate = DelegateBTEUtil.getBteSistraDelegate();
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO)){
				bteDelegate.crearEntradaPreregistro( refRDSAsiento, refRDSJustificante, mapRefRDSDocumentos, numeroRegistro, fechaRegistro );				
			}else if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR)){
				bteDelegate.crearEntradaPreregistroIncorrecto(refRDSAsiento, refRDSJustificante, mapRefRDSDocumentos, numeroRegistro, fechaRegistro );
			}else if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA)){
				bteDelegate.crearEntradaPreenvioAutomatico(refRDSAsiento, refRDSJustificante, mapRefRDSDocumentos, numeroRegistro, fechaRegistro );
			}else if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)){
				bteDelegate.confirmacionEntradaPreenvioAutomatico(entrada.getNumeroPreregistro(), numeroRegistro, fechaRegistro );
			}else{
				throw new Exception("Tipo de confirmacion no soportada");
			}
						
			//  En caso de ser un preregistro incorrecto la fecha de confirmación es diferente a la que se registro
			if (!tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_GESTOR)){
				entrada.setFechaConfirmacion( new Date() );
			}else{
				entrada.setFechaConfirmacion( fechaRegistro );
			}
			entrada.setNumeroRegistro( numeroRegistro );
						
			// Si la confirmacion es automatica la marcamos
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA)){
				entrada.setConfirmadoAutomaticamente('S');
			}				 
			
			EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
			delegate.grabarEntradaPreregistro( entrada );
			
			// Generamos usos en RDS en caso de que se genere registro de entrada
			if (tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) ||
				tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)){
					generarUsosRDS(ConstantesRDS.TIPOUSO_REGISTROENTRADA,asiento,mapRefRDSDocumentos,refRDSAsiento,refRDSJustificante,numeroRegistro);
			}
			exitoProceso = true;
			
			
			// En caso de que pertenezca a un expediente, acualizamos estado expediente
			DelegateUtil.getProcesosAutoDelegate().actualizaEstadoExpedienteDelElementoExpediente(
							ElementoExpediente.TIPO_ENTRADA_PREREGISTRO,
							entrada.getCodigo());
			
			
			// Realizamos log en AUDITA			
			//	Si es la confirmacion de un preenvio con confirmacion automatica no lo auditamos ya que se audito al hacerlo automaticamente	
			if (!tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO)){
				PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
				Principal sp = this.ctx.getCallerPrincipal();
				String nif = plgLogin.getNif(sp);
				char metodoAut = plgLogin.getMetodoAutenticacion( sp );
				String nombre = plgLogin.getNombreCompleto(sp);
				logEvento(ConstantesAuditoria.EVENTO_CONFIRMACION_PREREGISTRO,metodoAut,sp.getName(),
						(StringUtils.isNotEmpty(nif)?nif.toUpperCase():null),nombre,entrada.getIdioma(), "S","Preregistro confirmado: " + entrada.getNumeroPreregistro(),
						StringUtil.getModelo(asiento.getDatosAsunto().getIdentificadorTramite()),StringUtil.getVersion(asiento.getDatosAsunto().getIdentificadorTramite()) );
			}
	    	
    	}catch (Exception ex){
    		log.error( "Excepcion que causa la no confirmacion del preregistro", ex );
    		throw new ExcepcionPAD("No se ha podido confirmar el preregistro",ex);
    	}
    	finally
    	{
    		// Log de registro de entrada (solo para confirmacion de preregistros normales)
    		if ( 	(tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_REGISTRO) || tipoConfirmacionPreregistro.equals(ConstantesBTE.CONFIRMACIONPREREGISTRO_AUTOMATICA_REGISTRO))		
    				&& resultadoRegistro != null )
    		{
    			try
    			{
    				LoggerRegistro.logResultadoRegistro( resultadoRegistro.getNumeroRegistro(), resultadoRegistro.getFechaRegistro(), exitoProceso );
    			}
    			catch( Exception exc )
    			{
    				log.error( exc );
    			}
    		}
    	}
    }
	

	/**
	 *  Obtiene el estado de un tramite autenticado
	 * @param idPersistencia Id persistencia del tramite
	 * @param nifEntidad En caso de delegacion se indica la entidad delegada
	 * @return
	 * @throws ExcepcionPAD
	 */
	private String obtenerEstadoTramiteAutenticadoImpl(String idPersistencia, String nifEntidad) throws ExcepcionPAD{

			// Obtenemos datos usuario autenticado
	    	Principal sp = this.ctx.getCallerPrincipal();
	    	PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new ExcepcionPAD("Error al crear plugin login",e);
			}
	    	String usuario = sp.getName();
	    	String nif = plgLogin.getNif(sp);
	    	if (nif != null) nif = nif.toUpperCase();    	
	    	
	    	// En caso de delegacion obtenemos datos de la entidad
	    	PersonaPAD entidad = null;
	    	if (nifEntidad != null){
	    		try {
					entidad = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nifEntidad);
				} catch (es.caib.zonaper.persistence.delegate.DelegateException e) {
					throw new ExcepcionPAD("Error al consultar datos entidad delegada: " + nifEntidad);
				}
	    	}
	    	
	    	/*
	    	 * Al acceder a tramite persistente, entrada preregistro o entrada telematica
	    	 * se controla si es el propio usuario o si el usuario es delegado
	    	 * 
	    	 * A la hora de comprobar si el tramite es del usuario, hay que tener en cuenta
	    	 * que si se accede de forma delegado el tramite pertenece a la entidad, no al usuario autenticado
	    	 * 
	    	 */ 
	    	String nifTramite, usuarioTramite;
			if (nifEntidad != null){
				nifTramite = nifEntidad;
				usuarioTramite = entidad.getUsuarioSeycon();
			}else{
				nifTramite = nif;
				usuarioTramite = usuario;
			}
	    	
	    	
	    	// Buscamos en Zona Persistencia      	
	    	try{
	    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
	    		TramitePersistente tp = td.obtenerTramitePersistente(idPersistencia);
	    		
	    		if (tp.getNivelAutenticacion() != 'A') {
	    			// Tramite pendiente de completar por el usuario 
		    		if (tp.getUsuarioFlujoTramitacion().equals(usuarioTramite)) return "P";	    			
		    		// Tramite remitido a otro usuario 
		    		if (tp.getUsuarioFlujoTramitacion().equals(usuarioTramite) && !tp.getUsuario().equals(usuarioTramite)) return "R";    				    			
	    		}
	    				   
	    		// Tramite pertenece a otro usuario
	    		return "N";
	    	}catch (Exception ex){
	    		// Pasamos a buscar en preregistro
	    	}
	        	
	        // Buscamos en preregistro
	    	try{
	    		EntradaPreregistroDelegate  epd = DelegateUtil.getEntradaPreregistroDelegate();
	    		EntradaPreregistro ep = epd.obtenerEntradaPreregistro(idPersistencia);    		
	    		if (ep.getNivelAutenticacion() != 'A'){
	    			// Comprobamos que el trámite pertenezca al usuario (es quien lo ha enviado o es el representado)
	    			if (ep.getNifRepresentante().equals(nifTramite) ||  
	    				(ep.getNifRepresentado() != null && ep.getNifRepresentado().equals(nifTramite))) {
	    				// Comprobamos si esta confirmado
			    		if (ep.getFechaConfirmacion() != null) return "T";
			    			else return "C";	    		
	    			}
    			}
	    		// Tramite pertenece a otro usuario
	    		return "N";
	    	}catch (Exception ex){
	    		// Pasamos a buscar en registro/envio
	    	}    	
	    	
	    	// Buscamos en registro/envio
	    	try{
	    	 	EntradaTelematicaDelegate  etd = DelegateUtil.getEntradaTelematicaDelegate();
	    	 	EntradaTelematica et = etd.obtenerEntradaTelematica(idPersistencia);    		
	    		if (et.getNivelAutenticacion() != 'A'){
	    			// Comprobamos que el trámite pertenezca al usuario (es quien lo ha enviado o es el representado)
	    			if (et.getNifRepresentante().equals(nifTramite) || 
	    				(et.getNifRepresentado() != null && et.getNifRepresentado().equals(nifTramite))) {
	    				return "T";
	    			}
	    		}
	    		// Tramite pertenece a otro usuario
	    		return "N";
	    	}catch (Exception ex){    		
	    	}
	    	
	    	// No existe
	    	return "N";    	
	    }
	
		
	    
}
