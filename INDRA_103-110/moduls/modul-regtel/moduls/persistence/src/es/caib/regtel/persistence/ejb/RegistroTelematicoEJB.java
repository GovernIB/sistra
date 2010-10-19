package es.caib.regtel.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ExcepcionRegistroTelematico;
import es.caib.regtel.model.ResultadoRegistro;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.persistence.delegate.DelegateUtil;
import es.caib.regtel.persistence.util.Configuracion;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

import es.caib.util.FirmaUtil;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
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
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;


/**
 * SessionBean de registro telemático 
 *
 * @ejb.bean
 *  name="regtel/persistence/RegistroTelematicoEJB"
 *  jndi-name="es.caib.regtel.persistence.RegistroTelematico"
 *  type="Stateless"
 *  view-type="remote"
 *  
 */
public abstract class RegistroTelematicoEJB  implements SessionBean
{
	
	private static Log log = LogFactory.getLog(RegistroTelematicoEJB.class);
	
	private boolean firmarEntrada,firmarSalida;
	private String nombreCertificado,pinCertificado;	
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.todos}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException 
	{
		log.info("ejbCreate: " + this.getClass());
		try
		{			
			 firmarEntrada = Boolean.parseBoolean(Configuracion.getProperty("firmar.entrada"));
			 firmarSalida = Boolean.parseBoolean(Configuracion.getProperty("firmar.salida"));
			 
			 nombreCertificado = Configuracion.getProperty("certificado.name");
			 pinCertificado = Configuracion.getProperty("certificado.pin");			 
			 			 
		}
		catch( Exception exc )
		{
			throw new CreateException( exc.getLocalizedMessage() );
		}
	}
	
   public void setSessionContext(javax.ejb.SessionContext ctx) 
   {
   }
   
    /**
	 * 
	 * Realiza un registro de salida pasando la referencia RDS del asiento y las referencias RDS de los documentos 
	 * indicados en el asiento
	 *    
	 * 
	 * @param refAsiento Referencia RDS del asiento
	 * @param refDocs Map con las referencias RDS de los documentos del asiento (key: Id documento / Value: Referencia RDS)
	 * @return ResultadoRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.gestor}"   
	 * @ejb.permission role-name = "${role.auto}"
	 */
   public ResultadoRegistroTelematico registroSalida( ReferenciaRDS refAsiento, Map refDocs ) throws ExcepcionRegistroTelematico
   {
	   return registro( refAsiento, refDocs, false );
   }

	/**
	 * 
	 * Realiza un registro de entrada pasando la referencia RDS del asiento y las referencias RDS de los documentos 
	 * indicados en el asiento
	 *    
	 * 
	 * @param refAsiento Referencia RDS del asiento
	 * @param refDocs Map con las referencias RDS de los documentos del asiento (key: Id documento / Value: Referencia RDS)
	 * @return ResultadoRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   
	 * @ejb.permission role-name = "${role.auto}"
	 */
	public ResultadoRegistroTelematico registroEntrada(ReferenciaRDS refAsiento, Map refDocs)  throws ExcepcionRegistroTelematico
	{
		return registro( refAsiento, refDocs, true );
	}
	
	
	/**
	 * 
	 * Obtiene fecha en la que se ha firmado el acuse de recibo de una notificación
	 *    
	 * 
	 * @param numeroRegistro Número de registro de salida de la notificacion
	 * @return Date Devuelve la fecha de la firma del acuse de recibo de la notificación. Nulo si no se ha entregado.
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   
	 * @ejb.permission role-name = "${role.auto}"
	 */
   public Date obtenerAcuseRecibo(String numeroRegistro) throws ExcepcionRegistroTelematico
   {	
	   try{
		   PadDelegate pad = DelegatePADUtil.getPadDelegate();
		   return pad.obtenerAcuseRecibo(numeroRegistro);   		   
	   }catch (Exception ex){
		   log.error("Excepcion obteniendo acuse de recibo con numero de registro: " + numeroRegistro,ex);
		   throw new ExcepcionRegistroTelematico("Excepcion obteniendo acuse de recibo con numero de registro: " + numeroRegistro ,ex);
	   }
   }
   
   /**
	 * 
	 * ESTA FUNCION SE MANTIENE POR COMPATIBILIDAD CON REGISTRO CAIB.
	 * NO SERÁ VALIDA PARA LOS DEMÁS ORGANISMOS.
	 * 
	 * Realiza una anulación de un registro de entrada pasando la referencia RDS del justificante de registro
	 * Marcará el registro como anulado en el registro   
	 * 
	 * @param refAsiento Referencia RDS del justificante de registro	  
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   
	 * @ejb.permission role-name = "${role.auto}"
	 */
	public void anularRegistroEntrada(ReferenciaRDS refJustificante) throws ExcepcionRegistroTelematico
	{
		
			if ( log.isDebugEnabled() ) log.debug( "anulando registro entrada en el registro telematico" );

			log.debug("Accedemos al RDS para obtener el justificante");
			// Obtenemos asiento registral
			DocumentoRDS documentoRDSAsiento = consultarDocumentoRDS( refJustificante );
			byte[] asientoBin = documentoRDSAsiento.getDatosFichero();
			
			// Parseamos justificante
			Justificante  justificanteXML = null;
			try{
				FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
				justificanteXML 		= factoria.crearJustificanteRegistro( new ByteArrayInputStream( asientoBin ) );
			}catch (Exception ex){
				throw new ExcepcionRegistroTelematico("El documento no es un justificante de registro o no tiene una estructura valida",ex);
			}
			
			// Comprobamos que se trata de un registro de entrada
			if ( justificanteXML.getAsientoRegistral().getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA )
			{
				log.error( "Se está intentando anular un registro que no es de entrada" );
				throw new ExcepcionRegistroTelematico( "Se está intentando anular un registro que no es de entrada" ); 
			}
			
			// Invocamos al registro organismo para anular
			try{
				log.debug("Llamamamos a registro organismo para anular");
				DelegateUtil.getRegistroOrganismoDelegate().anularRegistroEntrada(justificanteXML.getNumeroRegistro(),justificanteXML.getFechaRegistro());				
			}catch (Exception ex){
				throw new ExcepcionRegistroTelematico("Excepcion anulando registro en organismo",ex);
			}  
			
			if ( log.isDebugEnabled() ) log.debug( "Registro de entrada " + justificanteXML.getNumeroRegistro() + " anulado" );
		
	}
   
	
	/**
	 * Confirma un preregistro. Debe realizar un apunte registral indicando que se ha confirmado el preregistro.
	 *
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.registro}"
	 */
	public ResultadoRegistro confirmarPreregistro(String oficina,String codigoProvincia,String codigoMunicipio,String descripcionMunicipio,Justificante justificantePreregistro,ReferenciaRDS refJustificante,ReferenciaRDS refAsiento,Map refAnexos) throws ExcepcionRegistroTelematico{
	  try{
			return DelegateUtil.getRegistroOrganismoDelegate().confirmarPreregistro(oficina,codigoProvincia,codigoMunicipio,descripcionMunicipio,justificantePreregistro,refJustificante,refAsiento,refAnexos);				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista oficinas en registro organismo",ex);
		} 
	}
		
	
	/**
	 * Obtiene lista de oficinas de registro   
	 * 
	 * @return Lista de ValorOrganismo
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * @ejb.permission role-name = "${role.auto}"   	 
	 * 
	 */
	public List obtenerOficinasRegistro()  throws ExcepcionRegistroTelematico{
	   try{
			return DelegateUtil.getRegistroOrganismoDelegate().obtenerOficinasRegistro();				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista oficinas en registro organismo",ex);
		} 
   }
	
	
	/**
	 * Mira si existe o no una oficina de registro  
	 * 
	 * @param oficinaRegistro identificador de la oficina de registro
	 * @return boolean
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   	 
	 * 
	 */
	public boolean existeOficinaRegistro(String oficinaRegistro) throws ExcepcionRegistroTelematico { 
	    try{
			return DelegateUtil.getRegistroOrganismoDelegate().existeOficinaRegistro(oficinaRegistro);				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista oficinas en registro organismo",ex);
		} 
   }
	
	
	
	/**
	 * Obtiene lista de oficinas de registro   
	 * 
	 * @return Lista de ValorOrganismo
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   	 
	 * 
	 */
	public List obtenerOficinasRegistroUsuario(String usuario)  throws ExcepcionRegistroTelematico{
	   try{
			return DelegateUtil.getRegistroOrganismoDelegate().obtenerOficinasRegistroUsuario(usuario);				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista oficinas usuario en registro organismo",ex);
		} 
   }
		
	/**
	 * Obtiene lista de tipos de asunto
	 * 
	 * @return Lista de ValorOrganismo
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"
	 * @ejb.permission role-name = "${role.auto}"   	 
	 * 
	 */
	public List obtenerTiposAsunto()  throws ExcepcionRegistroTelematico{
	   try{
			return DelegateUtil.getRegistroOrganismoDelegate().obtenerTiposAsunto();				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista tipos asunto en registro organismo",ex);
		} 
   }
	
	
	/**
	 * Mira si existe o no un tipo de asunto  
	 * 
	 * @param tipoAsunto identificador del tipo de asunto
	 * @return boolean
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   	 
	 * 
	 */
	public boolean existeTipoAsunto(String tipoAsunto) throws ExcepcionRegistroTelematico { 
	    try{
			return DelegateUtil.getRegistroOrganismoDelegate().existeTipoAsunto(tipoAsunto);				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista oficinas en registro organismo",ex);
		} 
   }
	
	/**
	 * Obtiene lista de servicios destino
	 * 
	 * @return Lista de ValorOrganismo
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}" 
	 * @ejb.permission role-name = "${role.auto}"   	 
	 * 
	 */
	public List obtenerServiciosDestino()  throws ExcepcionRegistroTelematico{
	   try{
			return DelegateUtil.getRegistroOrganismoDelegate().obtenerServiciosDestino();				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista  servicios destino en registro organismo",ex);
		} 
   }
	
	/**
	 * Mira si existe o no un servicio de destino
	 * 
	 * @param servicioDestino identificador del servicio de destino
	 * @return boolean
	 * @throws ExcepcionRegistroTelematico
	 * 
	 * @ejb.interface-method
	 * @ejb.permission role-name = "${role.todos}"   	 
	 * 
	 */
	public boolean existeServicioDestino(String servicioDestino) throws ExcepcionRegistroTelematico { 
	    try{
			return DelegateUtil.getRegistroOrganismoDelegate().existeServicioDestino(servicioDestino);				
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo lista oficinas en registro organismo",ex);
		} 
   }
   
   // --------------------- 	FUNCIONES UTILIDAD ---------------------------------------------------------
		
	private ResultadoRegistroTelematico registro( ReferenciaRDS refAsiento, Map refDocs, boolean entrada ) throws ExcepcionRegistroTelematico
	{
			if ( log.isDebugEnabled() ) log.debug( "entrada en el registro telematico" );
			
			//
			//	---- OBTENER ASIENTO Y VALIDAR ESTRUCTURA
			//
			
			// 1 - OBTENEMOS ASIENTO
			DocumentoRDS documentoRDSAsiento = consultarDocumentoRDS( refAsiento );
			byte[] asientoBin = documentoRDSAsiento.getDatosFichero();
			AsientoRegistral asientoXML = null;
			try{
				FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
				asientoXML 		= factoria.crearAsientoRegistral( new ByteArrayInputStream( asientoBin ) );
			}catch(Exception ex){
				log.error("Excepcion parseando asiento registral",ex);
				throw new ExcepcionRegistroTelematico("Excepcion parseando asiento registral",ex);
			}
			
			// Comprobamos que, si se trata de un registro de entrada o de salida, el asiento registral es del mismo tipo
			if ( entrada && asientoXML.getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA )
			{
				log.error( "Se está intentando generar un registro de entrada para un asiento registral que no es de tipo registro de entrada" );
				throw new ExcepcionRegistroTelematico( "Se está intentando generar un registro de entrada para un asiento registral que no es de tipo registro de entrada" ); 
			}
			if ( !entrada && asientoXML.getDatosOrigen().getTipoRegistro().charValue() != ConstantesAsientoXML.TIPO_REGISTRO_SALIDA )
			{
				log.error( "Se está intentando generar un registro de salida para un asiento registral que no es de tipo registro de salida" );
				throw new ExcepcionRegistroTelematico( "Se está intentando generar un registro de salida para un asiento registral que no es de tipo registro de salida" ); 
			}
			
			// VERIFICAMOS QUE LOS REGISTROS DE ENTRADA QUE ESTEN FIRMADOS SE CORRESPONDA EL DNI DEL FIRMANTE CON EL DNI DEL REPRESENTANTE
			if ( entrada && documentoRDSAsiento.getFirmas() != null && documentoRDSAsiento.getFirmas().length > 0 )
			{
				chequeoIdentidadFirmanteASientoRegistral( documentoRDSAsiento.getFirmas(), asientoXML );				
			}
			
			// 2 - VERIFICAR QUE EXISTEN EN RDS TODOS LOS DOCS Y SU HASH ES CORRECTO
			chequeaIntegridadDocumentosAsiento( asientoXML, refDocs );
			
			
			// 3 - VALIDAMOS ANEXOS OBLIGATORIOS
			DatosPropios datosPropios = null;
			AvisoNotificacion avisoNotificacion = null;
			OficioRemision oficioRemision = null;
			if (entrada){
				// Si el registro de entrada es un tramite telematico implementado por la plataforma de tramitacion
				// debera tener asociado el anexo de Datos propios
				if (StringUtils.isNotEmpty(asientoXML.getDatosAsunto().getIdentificadorTramite())){
					datosPropios = validarDatosPropios(asientoXML, refDocs );
				}
			}else{
				// Para registros de salida chequeamos documento de aviso de notificacion y de oficio de remision
				avisoNotificacion = validarAvisoNotificacion(asientoXML, refDocs );
				oficioRemision = validarOficioRemision(asientoXML,refDocs);
			}
			
			
			//
			//	---- REGISTRAMOS EN REGISTRO DEL ORGANISMO
			log.debug( "Iniciamos registro en organismo" );
			ResultadoRegistro res = null;
			try{
				if (entrada){
					res = DelegateUtil.getRegistroOrganismoDelegate().registroEntrada(asientoXML,refAsiento, refDocs);
				}else{
					res = DelegateUtil.getRegistroOrganismoDelegate().registroSalida(asientoXML,refAsiento, refDocs);
				}				
				log.debug( "Registro realizado en organismo" );			
			}catch(Exception ex){
				log.error("Exception realizando registro organismo",ex);
				throw new ExcepcionRegistroTelematico("Exception realizando registro en organismo",ex);
			}
			
			//
			//	---- GENERAMOS JUSTIFICANTE DE REGISTRO	
			//
			log.debug("Generamos justificante registro");
			
			// Generamos justificante de registro creando los usos RDS correspondientes
			ReferenciaRDS refRDSJustificante = generarJustificante(asientoBin,asientoXML,res);
			
			// Genera usos RDS para asiento, justificante y documentos asociados
			generarUsosRDS(entrada ? ConstantesRDS.TIPOUSO_REGISTROENTRADA : ConstantesRDS.TIPOUSO_REGISTROSALIDA ,asientoXML,refDocs,refAsiento,refRDSJustificante,res,datosPropios);
			
			//
			//	---- REALIZAMOS LOG EN LA PAD
			//
			
			// Generamos log en la zona personal
					try{
						PadDelegate pad = DelegatePADUtil.getPadDelegate();
						pad.logPad(refAsiento,refRDSJustificante,refDocs);
					}catch(Exception ex){
						throw new ExcepcionRegistroTelematico("Excepcion generando log registro en la zona personal",ex);
					}
			
			//
			//	---- DEVOLVEMOS RESULTADO	
			//
			
			// Devolvemos resultado
			ResultadoRegistroTelematico resTel = new ResultadoRegistroTelematico();
			resTel.setResultadoRegistro(res);
			resTel.setReferenciaRDSJustificante(refRDSJustificante);			
			return resTel;
		
	}
	
	
	private void chequeaIntegridadDocumentosAsiento( AsientoRegistral asiento, Map referenciasRDS ) throws ExcepcionRegistroTelematico
	{
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		List lstDocumentos = asiento.getDatosAnexoDocumentacion();
		for ( int i = 0; i < lstDocumentos.size(); i++ )
		{
			DatosAnexoDocumentacion anexo = ( DatosAnexoDocumentacion ) lstDocumentos.get( i );
			
			// Consultamos el documento de RDS sin recuperar el fichero
			DocumentoRDS documentoRDS=null;
			ReferenciaRDS refRDS =( ReferenciaRDS ) referenciasRDS.get( anexo.getIdentificadorDocumento() ) ;
			if (refRDS==null){
				throw new ExcepcionRegistroTelematico("No existe referencia RDS para anexo " +  anexo.getIdentificadorDocumento() );
			}
			try { 			
				documentoRDS = rdsDelegate.consultarDocumento( refRDS, false );
			} catch (Exception e) {
				throw new ExcepcionRegistroTelematico( "Excepcion recuperando documento del RDS con codigo " +  refRDS.getCodigo() );
			}
			
			if ( documentoRDS == null )
			{
				log.error( "No se puede recuperar el documento del asiento registral con identificador " + anexo.getIdentificadorDocumento() + " y codigo rds " + anexo.getCodigoRDS() );
				throw new ExcepcionRegistroTelematico( "No existe en RDS el documento del asiento registral con identificador " + anexo.getIdentificadorDocumento() + " y codigo rds " + anexo.getCodigoRDS()  );
			}
			
			if ( !anexo.getHashDocumento().equals( documentoRDS.getHashFichero() ) )
			{
				log.error( "El hash del documento del asiento registral con identificador " + anexo.getIdentificadorDocumento() + " y codigo rds " + anexo.getCodigoRDS() + " no coincide con su valor en RDS");
				throw new ExcepcionRegistroTelematico( "El hash del documento del asiento registral con identificador " + anexo.getIdentificadorDocumento() + " y codigo rds " + anexo.getCodigoRDS() + " no coincide con su valor en RDS");
			}
		}
	}
	
	/**
	 * Si se trata de un registro de entrada, chequeamos que el nif del firmante coincide con el nif del representante
	 *  o bien coincide con el nif delegado 
	 * @param firmas
	 * @param asientoRegistral
	 */
	private void chequeoIdentidadFirmanteASientoRegistral( FirmaIntf[] firmas, AsientoRegistral asientoRegistral ) throws ExcepcionRegistroTelematico
	{
		String nifRpte = null;
		String nifDlgdo = null;
		List lstDatosInteresados = asientoRegistral.getDatosInteresado();
		for ( Iterator it = lstDatosInteresados.iterator(); it.hasNext(); )
		{
			DatosInteresado datosInteresado = ( DatosInteresado ) it.next();
			if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE.equals( datosInteresado.getTipoInteresado() ) )
			{
				nifRpte = datosInteresado.getNumeroIdentificacion();
			}else if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO.equals( datosInteresado.getTipoInteresado() ) )
			{
				nifDlgdo = datosInteresado.getNumeroIdentificacion();				
			}
		}
		if ( nifRpte == null )
		{
			throw new ExcepcionRegistroTelematico( "En el asiento registral no existe el numero de documento identificativo del representante" );
		}
		if ( firmas == null )
		{
			throw new ExcepcionRegistroTelematico( "El asiento registral no está firmado para el registro de entrada" );
		}
		if ( nifDlgdo != null){
			// Comprobamos que el delegado tenga permiso de presentar tramites para la entidad
			try {
				String permisos = DelegatePADUtil.getPadDelegate().obtenerPermisosDelegacion(nifRpte,nifDlgdo);
				if (StringUtils.isEmpty(permisos) || permisos.indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1){
					throw new ExcepcionRegistroTelematico("Delegado " + nifDlgdo + " no tiene permisos para presentar tramites para la entidad " + nifRpte);
				}
			} catch (es.caib.zonaper.persistence.delegate.DelegateException e) {
				throw new ExcepcionRegistroTelematico("No se puede comprobar permisos del delegado sobre la entidad");
			}
		}
		
		boolean existeFirma = false;
		for ( int i = 0 ; i < firmas.length; i++ )
		{
			FirmaIntf firma = firmas[ i ];			
			try{
				if (nifDlgdo != null){
					if (nifDlgdo.equals(firma.getNif())) {
						existeFirma = true;
						break;
					}
				}else {
					if (nifRpte.equals(firma.getNif())) {
						existeFirma = true;
						break;
					}
				}
			}catch(Exception ex){
				log.error("No se pudo comprobar si el numero de documento identificativo del firmante del asiento registral coincide con el nº de documento identificativo del representante en el asiento registral",ex );
				throw new ExcepcionRegistroTelematico( "No se pudo comprobar si el numero de documento identificativo del firmante del asiento registral coincide con el nº de documento identificativo del representante en el asiento registral",ex );
			}
		}
		
		// En caso de no encontrar dni generamos excepcion
		if (!existeFirma){
		log.error( "El numero de documento identificativo del firmante del asiento registral no coincide con el nº de documento identificativo del representante en el asiento registral" );
		throw new ExcepcionRegistroTelematico( "El numero de documento identificativo del firmante del asiento registral no coincide con el nº de documento identificativo del representante en el asiento registral" );
	}
	
	}
	
	private DocumentoRDS consultarDocumentoRDS( ReferenciaRDS referenciaRDS )
	{
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS = null;
		try
		{
			documentoRDS = rdsDelegate.consultarDocumento( referenciaRDS );
		}
		catch (DelegateException e)
		{
			throw new EJBException( e );
		}
		return documentoRDS;
	}
		
	/**
	 * Generar justificante de registro y lo almacena en el RDS
	 */
	private ReferenciaRDS generarJustificante(byte[] asientoBin,AsientoRegistral asientoXML,ResultadoRegistro resultadoRegistro) throws ExcepcionRegistroTelematico{
			
		// Generamos justificante a partir asiento
		String asiento=null;
		try {
			asiento = new String(asientoBin,ConstantesXML.ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new ExcepcionRegistroTelematico("Codificacion no soportada " + ConstantesXML.ENCODING );
		}
		
		String justificante = 
			"<?xml version=\"1.0\" encoding=\"" + ConstantesXML.ENCODING + "\" standalone=\"yes\"?>" +
			"<JUSTIFICANTE version=\"1.0\">" +  
			asiento.substring(asiento.indexOf("<ASIENTO_REGISTRAL")) +
			//asiento.substring(asiento.indexOf("?>") + 3) + 
			"  <NUMERO_REGISTRO>"+ resultadoRegistro.getNumeroRegistro() +"</NUMERO_REGISTRO> " + 
			"  <FECHA_REGISTRO>"+ resultadoRegistro.getFechaRegistro() + "</FECHA_REGISTRO> " +
			" </JUSTIFICANTE> ";									
		
		
		DocumentoRDS docRds = new DocumentoRDS();
		
		
		// Firmamos justificante (en caso de que se haya configurado)		
		if (
				(asientoXML.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA  
						&& this.firmarEntrada)
				||
				(asientoXML.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_SALIDA  
						&& this.firmarSalida)
			)
		{
			
				InputStream is = null;
				try {
					
					PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
					
					// Según implementación tendrá parámetros específicos
					Map params = new HashMap();
					if (plgFirma.getProveedor().equals(PluginFirmaIntf.PROVEEDOR_CAIB)){						
						// Establecemos content type correspondiente
						if (asientoXML.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_SALIDA){
							params.put(FirmaUtil.CAIB_PARAMETER_CONTENT_TYPE, FirmaUtil.obtenerContentTypeCAIB(FirmaUtil.CAIB_JUSTIFICANT_EIXIDA_CONTENT_TYPE) );
						}else{
							params.put(FirmaUtil.CAIB_PARAMETER_CONTENT_TYPE, FirmaUtil.obtenerContentTypeCAIB(FirmaUtil.CAIB_JUSTIFICANT_ENTRADA_CONTENT_TYPE) );
						}
						// Establecemos pin certificado
						params.put(FirmaUtil.CAIB_PARAMETER_PIN,this.pinCertificado);
					}else if(plgFirma.getProveedor().equals(PluginFirmaIntf.PROVEEDOR_AFIRMA)){
						if (asientoXML.getDatosOrigen().getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_SALIDA){
							params.put(FirmaUtil.AFIRMA_PARAMETER_ARCHIVO, "justificanteSalida.xml" );
						}else{
							params.put(FirmaUtil.AFIRMA_PARAMETER_ARCHIVO, "justificanteEntrada.xml" );
						}
					}
					
					// Firmamos
					is = FirmaUtil.cadenaToInputStream(justificante);
					FirmaIntf firma = plgFirma.firmar(is,this.nombreCertificado,params);
					
					// Asociamos firma al documento
					FirmaIntf [] firmas = new FirmaIntf[1];
					firmas[0]=firma;
					docRds.setFirmas(firmas);			
				} catch (Exception ex) {
					log.error("Excepcion firmando justificante registro",ex);
					throw new ExcepcionRegistroTelematico("Excepcion firmando justificante registro",ex);
				}finally{
					try{is.close();}catch(Exception e){}
				}
						
		}		
		
		// Insertamos justificante en RDS				
		docRds.setModelo(ConstantesRDS.MODELO_JUSTIFICANTE_REGISTRO);
		docRds.setVersion(ConstantesRDS.VERSION_JUSTIFICANTE);
		try {
			docRds.setDatosFichero(justificante.getBytes(ConstantesXML.ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new ExcepcionRegistroTelematico("Codificacion no soportada " + ConstantesXML.ENCODING );
		}		
		docRds.setTitulo( "ca".equals(  asientoXML.getDatosAsunto().getIdiomaAsunto() ) ? "Justificant" : "Justificante" );	    		
		docRds.setNombreFichero("justificante.xml");	    		
		docRds.setExtensionFichero("xml");
		docRds.setUnidadAdministrativa(Long.parseLong(asientoXML.getDatosAsunto().getCodigoUnidadAdministrativa()));	
		for (Iterator it = asientoXML.getDatosInteresado().iterator();it.hasNext();){
    		DatosInteresado di = (DatosInteresado) it.next();
    		if (di.getTipoInteresado().equals(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE)){
    			docRds.setNif(di.getNumeroIdentificacion());
    			docRds.setUsuarioSeycon(di.getUsuarioSeycon());
    			break;
    		}
    	}
		docRds.setIdioma(asientoXML.getDatosAsunto().getIdiomaAsunto());
				
		// Insertamos justificante en el RDS y devolvemos referencia
		try{
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			ReferenciaRDS ref =rds.insertarDocumento(docRds);
			return ref;
		}catch(Exception ex){
			log.error("Excepcion insertando justificante en el RDS",ex);
			throw new ExcepcionRegistroTelematico("Excepcion insertando justificante en el RDS",ex);
		}
	}
	
	 /**
	 * Genera usos RDS en preregistro para asiento, justificante y documentos asociados
	 * @param datosPropios 
	 */
	private void generarUsosRDS(String tipoUso,AsientoRegistral asientoXML,Map referenciasRDS,
			ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,
			ResultadoRegistro resRegistro, DatosPropios datosPropios) throws ExcepcionRegistroTelematico{
		
		try{
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			UsoRDS uso;
			
			Date fcReg = StringUtil.cadenaAFecha(resRegistro.getFechaRegistro(),StringUtil.FORMATO_REGISTRO);
			
			// Creamos uso para asiento					
			uso = new UsoRDS();
			uso.setReferenciaRDS(refAsiento);
			uso.setReferencia(resRegistro.getNumeroRegistro());
			uso.setTipoUso(tipoUso);
			uso.setFechaSello(fcReg);
			rds.crearUso(uso);
			
			// Creamos uso para justificante					
			uso = new UsoRDS();
			uso.setReferenciaRDS(refJustificante);
			uso.setReferencia(resRegistro.getNumeroRegistro());
			uso.setTipoUso(tipoUso);
			uso.setFechaSello(fcReg);		
			rds.crearUso(uso);
			
			// Creamos usos para documentos asiento
			Iterator it = asientoXML.getDatosAnexoDocumentacion().iterator();
	    	while (it.hasNext()){
	    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
	    		ReferenciaRDS refRDS = (ReferenciaRDS) referenciasRDS.get(da.getIdentificadorDocumento()); 
	    		
	    		// Comprobamos que el documento no se este siendo usado previamente 
	    		// (solo podria tener usos de persistencia en caso de registro de entrada de un tramite sistra)
	    		List usos = rds.listarUsos(refRDS);
	    		if (usos.size() > 0){
	    			if (tipoUso.equals(ConstantesRDS.TIPOUSO_REGISTROENTRADA) && usos.size() == 1){	    				
	    				UsoRDS usoPers = (UsoRDS) usos.get(0);
	    				if (usoPers.getTipoUso().equals(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE) &&
	    					usoPers.getReferencia().equals(datosPropios.getInstrucciones().getIdentificadorPersistencia())){
	    						// Uso correcto, es el proceso de registro y todavia tiene el uso de persistencia
	    				}else{
	    					throw new Exception("El documento con codigo " + refRDS.getCodigo() + " ya se esta utilizando en el sistema (existen usos asociados)");
	    				}
	    			}else{	    			
	    				throw new Exception("El documento con codigo " + refRDS.getCodigo() + " ya se esta utilizando en el sistema (existen usos asociados)");
	    			}
				}
				
	    		
	    		// Creamos uso
	    		uso.setReferenciaRDS(refRDS);
				uso.setReferencia(resRegistro.getNumeroRegistro());
				uso.setTipoUso(tipoUso);
				uso.setFechaSello(fcReg);	
				rds.crearUso(uso);
	    	}    	
		}catch(Exception ex){
			log.error("Excepcion generando usos RDS para registro",ex);
			throw new ExcepcionRegistroTelematico("Excepcion generando usos RDS para registro",ex);
		}
		
	}
	
	/**
	 * Realiza la validacion del documento de aviso de notificacion para registros de salida
	 * 
	 * @param asientoRegistralNotificacion
	 * @param referenciasRDS
	 * @return 
	 * @throws ExcepcionRegistroTelematico
	 */
	private AvisoNotificacion validarAvisoNotificacion( AsientoRegistral asientoRegistralNotificacion, Map referenciasRDS ) throws ExcepcionRegistroTelematico
	{		
		try
		{
			DatosAnexoDocumentacion anexoAvisoNotificacion = obtenerAnexoAsientoDeTipo( asientoRegistralNotificacion, ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION );
			ReferenciaRDS referenciaRDSAvisoNotificacion = ( ReferenciaRDS ) referenciasRDS.get( anexoAvisoNotificacion.getIdentificadorDocumento() );
			if (referenciaRDSAvisoNotificacion == null){
				throw new Exception("No existe Aviso de notificacion en el asiento");
			}			
			DocumentoRDS docRDSAvisoNotificacion = consultarDocumentoRDS( referenciaRDSAvisoNotificacion );			
			FactoriaObjetosXMLAvisoNotificacion factoriaAvisoNotificacion = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
			AvisoNotificacion avisoNotificacion = factoriaAvisoNotificacion.crearAvisoNotificacion( new ByteArrayInputStream( docRDSAvisoNotificacion.getDatosFichero() ) );
			if (avisoNotificacion == null) throw new Exception("Aviso de notificacion generado es nulo");
			return avisoNotificacion;
		}
		catch( Exception exc )
		{
			log.error("Aviso de notificacion no es valido",exc);
			throw new ExcepcionRegistroTelematico("Aviso de notificacion no es valido",exc);			
		}		
	}
	
	
	/**
	 * Realiza la validacion del documento de oficio de remision para registros de salida
	 * 
	 * @param asientoRegistralNotificacion
	 * @param referenciasRDS
	 * @return 
	 * @throws ExcepcionRegistroTelematico
	 */
	private OficioRemision validarOficioRemision( AsientoRegistral asientoRegistralNotificacion, Map referenciasRDS ) throws ExcepcionRegistroTelematico
	{		
		try
		{
			DatosAnexoDocumentacion anexoOficioRemision = obtenerAnexoAsientoDeTipo( asientoRegistralNotificacion, ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION );
			ReferenciaRDS referenciaRDSOficioRemision = ( ReferenciaRDS ) referenciasRDS.get( anexoOficioRemision.getIdentificadorDocumento() );
			if (referenciaRDSOficioRemision == null){
				throw new Exception("No existe Oficio de remision en el asiento");
			}			
			DocumentoRDS docRDSOficioRemision = consultarDocumentoRDS( referenciaRDSOficioRemision );			
			FactoriaObjetosXMLOficioRemision factoriaOficioRemision = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
			OficioRemision oficioRemision = factoriaOficioRemision.crearOficioRemision( new ByteArrayInputStream( docRDSOficioRemision.getDatosFichero() ) );
			if (oficioRemision == null) throw new Exception("Oficion de remision generado es nulo");
			return oficioRemision;
		}
		catch( Exception exc )
		{
			log.error("Oficio de remision no es valido",exc);
			throw new ExcepcionRegistroTelematico("Oficio de remision no es valido",exc);			
		}		
	}
	
	
	/**
	 * Realiza la validacion del documento de datos propios para registros de entrada
	 * 
	 * @param asientoRegistralNotificacion
	 * @param referenciasRDS
	 * @throws ExcepcionRegistroTelematico
	 */
	private DatosPropios validarDatosPropios( AsientoRegistral asientoRegistralNotificacion, Map referenciasRDS ) throws ExcepcionRegistroTelematico
	{		
		try
		{
			DatosAnexoDocumentacion anexoDatosPropios = obtenerAnexoAsientoDeTipo( asientoRegistralNotificacion, ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS );
			ReferenciaRDS referenciaRDSDatosPropios = ( ReferenciaRDS ) referenciasRDS.get( anexoDatosPropios.getIdentificadorDocumento() );
			if (referenciaRDSDatosPropios == null){
				throw new Exception("No existe Datos Propios en el asiento");
			}			
			DocumentoRDS docRDSDatosPropios = consultarDocumentoRDS( referenciaRDSDatosPropios );			
			FactoriaObjetosXMLDatosPropios factoriaDatosPropios = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
			DatosPropios datosPropios = factoriaDatosPropios.crearDatosPropios( new ByteArrayInputStream( docRDSDatosPropios.getDatosFichero() ) );
			if (datosPropios == null) throw new Exception("Datos propios generado es nulo");
			return datosPropios;
		}
		catch( Exception exc )
		{
			log.error("Datos propios no es valido",exc);
			throw new ExcepcionRegistroTelematico("Datos propios no es valido",exc);			
		}		
	}
	
	private DatosAnexoDocumentacion obtenerAnexoAsientoDeTipo( AsientoRegistral asiento, char tipoAnexo )
	{
		for( Iterator it = asiento.getDatosAnexoDocumentacion().iterator(); it.hasNext(); )
		{
			DatosAnexoDocumentacion tmp = ( DatosAnexoDocumentacion ) it.next();
			if ( tmp.getTipoDocumento().charValue() == tipoAnexo )
			{
				return tmp;
			}
		}
		return null;
	}
	
}
