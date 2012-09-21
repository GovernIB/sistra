package es.caib.sistra.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.persistence.delegate.BteSistraDelegate;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.DatosFormulario;
import es.caib.sistra.model.MensajeFront;
import es.caib.sistra.model.ResultadoRegistrar;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.modelInterfaz.DocumentoConsulta;
import es.caib.sistra.modelInterfaz.FormularioConsulta;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.GeneradorDelegate;
import es.caib.sistra.persistence.intf.ConsultaEJBHome;
import es.caib.sistra.persistence.util.UsernamePasswordCallbackHandler;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.AutenticacionExplicitaInfo;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.CifradoUtil;
import es.caib.util.EjbUtil;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

/**
 * Realiza operaciones de registro según destino:
 * 	- Registro: Si registro telemático realiza apunte registral y apunte en bandeja. 
 * 				Si preregistro genera justificante preregistro.
 * 	- Envío: 	Si envio telemático realiza apunte en bandeja y genera justificante envío.
 * 				Si preregistro genera justificante preregistro.
 *  - Consulta:	Invoca a EJB de consulta y obtiene resultado 
 */
public class RegistroController 
{
	Log log = LogFactory.getLog( RegistroController.class );
	
	/**
	 * Realiza proceso de registro (para tramites con destino envio y registro)
	 * @param tipoDestino
	 * @param idPersistencia
	 * @param asientoCompleto
	 * @param referenciasRDS
	 * @return
	 * @throws Exception
	 */
	public ResultadoRegistrar registrar(char tipoDestino,String idPersistencia,AsientoCompleto asientoCompleto,Map referenciasRDS)throws Exception {
					
		log.debug("Realizamos registro");
		
		ReferenciaRDS refAsiento;						
							
		// Obtenemos referencia RDS del asiento
		refAsiento = (ReferenciaRDS) referenciasRDS.get(ConstantesAsientoXML.IDENTIFICADOR_ASIENTO + "-1");
		
		// Parseamos asiento y datos propios
    	FactoriaObjetosXMLRegistro factoriaRT = ServicioRegistroXML.crearFactoriaObjetosXML();
		AsientoRegistral asientoXML = factoriaRT.crearAsientoRegistral (
				new ByteArrayInputStream(asientoCompleto.getAsiento().getBytes(ConstantesXML.ENCODING)));
		FactoriaObjetosXMLDatosPropios factoriaDP = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
		DatosPropios datosPropiosXML = factoriaDP.crearDatosPropios (
				new ByteArrayInputStream(asientoCompleto.getDatosPropios().getBytes(ConstantesXML.ENCODING))
			);
		
		// Comprobamos si existe documentación a aportar
		boolean aportarDoc = false;									
		if (datosPropiosXML.getInstrucciones().getDocumentosEntregar() != null && datosPropiosXML.getInstrucciones().getDocumentosEntregar().getDocumento().size() > 0) {
			aportarDoc = true;
		}						
					
		// Si no hay que aportar documentación iniciamos proceso telemático
		if (!aportarDoc) {
			
			// Si el destino es registro realizamos registro telemático				
			if (tipoDestino == ConstantesSTR.DESTINO_REGISTRO)
			{
				return registrarImpl_Registro(tipoDestino,idPersistencia,asientoCompleto,referenciasRDS,refAsiento,asientoXML);					
			}else{
			// Si el destino es la bandeja realizamos envío
				return registrarImpl_Envio(tipoDestino,idPersistencia,asientoCompleto,referenciasRDS,refAsiento,asientoXML);
			}
																
		}else{	
			// Si hay que aportar documentación iniciamos proceso preregistro
			return registrarImpl_Preregistro(tipoDestino,idPersistencia,asientoCompleto,referenciasRDS,refAsiento,asientoXML);				
		}											
	}	
	
	
	public ResultadoRegistrar consultar(char tipoAcceso,String jndi,boolean local,String url,char autenticacion,String user,String pass,String identificadorTramite,Map datosFormulario, String versionWS)throws Exception {
	// Realiza proceso de consulta (para tramites con destino consulta)		
		log.debug("Realizamos consulta");
		
		//	En caso de requerir autenticación explícita obtenemos el usuario/password			
		String userAuth=null,passAuth=null;
		switch (autenticacion){
			// No requiere autenticacion explicita
			case TramiteVersion.AUTENTICACION_EXPLICITA_SINAUTENTICAR:
				break;
			// Requiere autenticacion explicita basada en usuario y password
			case TramiteVersion.AUTENTICACION_EXPLICITA_ESTANDAR:
				log.debug("Autenticación explicita STANDARD");
				
				String claveCifrado = (String) DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
				String userPlain = CifradoUtil.descifrar(claveCifrado,user);
				String passPlain = CifradoUtil.descifrar(claveCifrado,pass);
				
				userAuth = userPlain;
				passAuth = passPlain; 
				break;
			// Requiere autenticacion explicita manejada por organismo
			case TramiteVersion.AUTENTICACION_EXPLICITA_ORGANISMO:
				log.debug("Autenticación explicita manejada por organismo");
				AutenticacionExplicitaInfo infoAuth = null;
				try{
					infoAuth = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo(ConstantesLogin.TIPO_TRAMITE, identificadorTramite);
					log.debug("Usuario plugin autenticacion organismo: " + infoAuth.getUser());
				}catch (Exception ex){
					throw new Exception("Excepcion obteniendo informacion autenticacion explicita a traves de plugin organismo",ex);
				}
				userAuth = infoAuth.getUser();
				passAuth = infoAuth.getPassword(); 				
				break;
		}
		
		
		// Segun tipo acceso realizamos la consulta
		if (tipoAcceso == TramiteVersion.CONSULTA_EJB){
			return consultarImpl_EJB(jndi,local,url,userAuth,passAuth,datosFormulario,identificadorTramite);
		}else{
			return consultarImpl_WS(url,userAuth,passAuth,identificadorTramite,datosFormulario,versionWS);
		}
		
	}
	
	/**
	 * Realiza proceso de consulta a través de Web Service
	 * @param url
	 * @param user
	 * @param pass
	 * @param datosFormulario
	 * @return
	 * @throws Exception
	 */
	private ResultadoRegistrar consultarImpl_WS(String url,String user,String pass,String identificadorTramite,Map datosFormulario, String versionWS)throws Exception {
			List docRes = null;
			if(versionWS != null && "v1".equals(versionWS)){
				docRes = es.caib.sistra.wsClient.v1.client.ClienteWS.realizarConsulta(url,user,pass,identificadorTramite,datosFormulario);
			}else if(versionWS != null && "v2".equals(versionWS)){
				docRes = es.caib.sistra.wsClient.v2.client.ClienteWS.realizarConsulta(url,user,pass,identificadorTramite,datosFormulario);
			}else{
				throw new Exception("Excepcion obteniendo la versión "+versionWS+" del WS de consulta. ");				
			}
			// Devolvemos resultado consulta
			ResultadoRegistrar res = new ResultadoRegistrar();
    		res.setTipo(ResultadoRegistrar.CONSULTA);
    		res.setDocumentos(docRes);
    		return res;				
	}
		
	
	
	/**
	 * Realiza proceso de consulta a través de EJB
	 * @param jndi
	 * @param local
	 * @param url
	 * @param autenticacion
	 * @param user
	 * @param pass
	 * @param datosFormulario
	 * @return
	 * @throws Exception
	 */
	private ResultadoRegistrar consultarImpl_EJB(String jndi,boolean local,String url,String user,String pass,Map datosFormulario,String identificadorTramite)throws Exception {
		
		log.debug("Realizamos consulta via EJB");
		
		LoginContext lc = null;				
		try
		{
			// Realizamos autenticacion explicita en caso necesario			
			if (user != null){
				CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass );
				lc = new LoginContext("client-login", handler);
			    lc.login();
			    log.debug("Autenticación explicita realizada");
			}
							
			// Obtenemos destino consulta
			log.debug("Accedemos a BackOffice ...");
			es.caib.sistra.persistence.intf.ConsultaEJBHome home =(es.caib.sistra.persistence.intf.ConsultaEJBHome) EjbUtil.lookupHome(jndi,local,url,ConsultaEJBHome.class);
			es.caib.sistra.persistence.intf.ConsultaEJB ejb = home.create();
									
			// Obtenemos datos formularios			
			FormularioConsulta [] forms = new FormularioConsulta[datosFormulario.size()];
			int num=0;
			for (Iterator it = datosFormulario.keySet().iterator();it.hasNext();){				
				String key = (String) it.next();		
				forms[num] = new FormularioConsulta();
				forms[num].setIdentificador(StringUtil.getModelo(key));
				forms[num].setNumeroInstancia(StringUtil.getVersion(key));
				forms[num].setXml( ((DatosFormulario) datosFormulario.get(key)).getString() );
				num++;
			}			
			
			// Realizamos consulta
			DocumentoConsulta docs[] = ejb.realizarConsulta(identificadorTramite,forms);
			//DocumentoConsulta docs[] = ejb.realizarConsulta(forms);			
			log.debug("Consulta realizada: " + docs.length + "docs.");
			
			// Devolvemos resultado
			ArrayList docRes = new ArrayList();
			for (int i=0;i<docs.length;i++){
				docRes.add(docs[i]);
			}
			ResultadoRegistrar res = new ResultadoRegistrar();
			res.setTipo(ResultadoRegistrar.CONSULTA);
			res.setDocumentos(docRes);
			return res;				
		}
		catch( Exception exc )
		{
			log.error( "Error realizando consulta: " + exc.getMessage(), exc );
			throw exc;
		}
		finally
		{
			if ( lc != null )
			{
				lc.logout();
			}
		}
	}
			
	
	// Realiza proceso de registro telemático.
	// En caso de error se genera una excepción de registro telemático que contiene en caso de haberse invocado el RTE el resultado temporal del registro 
	private ResultadoRegistrar registrarImpl_Registro(char tipoDestino,String idPersistencia,AsientoCompleto asientoCompleto,Map referenciasRDS,ReferenciaRDS refAsiento,AsientoRegistral asientoXML)throws RegistroTelematicoException{
				
		ResultadoRegistroTelematico resultadoTemporal = null;
		
		try{
			log.debug("Iniciamos proceso de registro telemático...");
													
			// Registramos en RTE
			RegistroTelematicoDelegate ejbRegistro = DelegateRegtelUtil.getRegistroTelematicoDelegate();
			resultadoTemporal = ejbRegistro.registroEntrada( refAsiento, referenciasRDS );
			log.debug( "Se ha invocado al RTE con num. reg.:" + resultadoTemporal.getResultadoRegistro().getNumeroRegistro());																
			
			// Obtenemos referencia Justificante generado por el registro	
			ReferenciaRDS refJustificante = resultadoTemporal.getReferenciaRDSJustificante();
								
			// Generamos resultado
			String numeroRegistro = resultadoTemporal.getResultadoRegistro().getNumeroRegistro();
			Timestamp fechaRegistro = new Timestamp( StringUtil.cadenaAFecha( resultadoTemporal.getResultadoRegistro().getFechaRegistro(), "yyyyMMddHHmmss" ).getTime());						
			ResultadoRegistrar resultadoRegistrar = new ResultadoRegistrar();
			resultadoRegistrar.setTipo(ResultadoRegistrar.REGISTRO_TELEMATICO);
			resultadoRegistrar.setNumero(numeroRegistro);
			resultadoRegistrar.setFecha(fechaRegistro);			
			resultadoRegistrar.setRdsJustificante(refJustificante);
			
			// Realizamos apunte en Bandeja (el log en la PAD lo realiza el interfaz de registro telemático)
			generarEntradaBandeja(refAsiento,refJustificante,referenciasRDS); 
		
			// Devolvemos resultado
			log.debug("Registro telemático efectuado: " + resultadoRegistrar.getNumero());
			return resultadoRegistrar;
		}catch (Exception ex){
			// Si ha pasado una excepción, lanzamos una excepción de registro que contenga resultado temporal de registro
			throw new RegistroTelematicoException("No se puede comprobar el asiento",MensajeFront.MENSAJE_ASIENTOINCORRECTO,ex,resultadoTemporal);
		}		
	}
	
	
	// Realiza proceso de envio
	private ResultadoRegistrar registrarImpl_Envio(char tipoDestino,String idPersistencia,AsientoCompleto asientoCompleto,Map referenciasRDS,ReferenciaRDS refAsiento,AsientoRegistral asientoXML)throws Exception{
				
		log.debug("Iniciamos proceso de envío telemático...");
		
		String numeroEnvio;
		Timestamp fechaEnvio;
		ReferenciaRDS refJustificante;
		
		// Generamos justificante
		fechaEnvio = new Timestamp(System.currentTimeMillis());
		numeroEnvio = generarNumeroEnvio();
		refJustificante = generarJustificante(asientoCompleto,asientoXML,numeroEnvio,fechaEnvio);
		
		// Genera usos RDS para asiento, justificante y documentos asociados
		generarUsosRDS(ConstantesRDS.TIPOUSO_ENVIO,asientoXML,referenciasRDS,refAsiento,refJustificante,numeroEnvio,fechaEnvio);
		
		// Generamos resultado
		ResultadoRegistrar resultadoRegistrar = new ResultadoRegistrar();
		resultadoRegistrar.setTipo(ResultadoRegistrar.ENVIO);
		resultadoRegistrar.setNumero(numeroEnvio);
		resultadoRegistrar.setFecha(fechaEnvio);			
		resultadoRegistrar.setRdsJustificante(refJustificante);
		
		// Realizamos apunte en Bandeja
		generarEntradaBandeja(refAsiento,refJustificante,referenciasRDS); 
	
		// Generamos apunte en log PAD
		generarLogPAD(refAsiento,refJustificante,referenciasRDS);
		
		// Devolvemos resultado
		log.debug("Envío telemático efectuado: " + resultadoRegistrar.getNumero());
		return resultadoRegistrar;
		
	}
	
	
	// Realiza proceso de preregistro
	private ResultadoRegistrar registrarImpl_Preregistro(char tipoDestino,String idPersistencia,AsientoCompleto asientoCompleto,Map referenciasRDS,ReferenciaRDS refAsiento,AsientoRegistral asientoXML)throws Exception{
		
		log.debug("Iniciamos proceso de preregistro...");		
				
		//  Generamos número de preregistro
		String numeroPreregistro = generarNumeroPreregistro(tipoDestino);
		Timestamp fechaPreregistro = new Timestamp(System.currentTimeMillis());
				
		// Generamos justificante
		ReferenciaRDS refJustificante = generarJustificante(asientoCompleto,asientoXML,numeroPreregistro,fechaPreregistro);
		
		// Genera usos RDS para asiento, justificante y documentos asociados
		generarUsosRDS(ConstantesRDS.TIPOUSO_PREREGISTRO,asientoXML,referenciasRDS,refAsiento,refJustificante,numeroPreregistro,fechaPreregistro);
			
		// Generamos resultado
		ResultadoRegistrar resultadoRegistrar = new ResultadoRegistrar();
		resultadoRegistrar.setTipo(ResultadoRegistrar.PREREGISTRO);
		resultadoRegistrar.setNumero(numeroPreregistro);
		resultadoRegistrar.setFecha(fechaPreregistro);			
		resultadoRegistrar.setRdsJustificante(refJustificante);
				
		// Generamos apunte en log PAD
		generarLogPAD(refAsiento,refJustificante,referenciasRDS);
		
		// Devolvemos resultado
		log.debug("Preregistro efectuado: " + resultadoRegistrar.getNumero());
		return resultadoRegistrar;
		
	}
	
		
	/**
	 * Genera entrada en la bandeja telemática para registros y envíos
	 * 
	 * @param ReferenciaRDS Referencia Asiento
	 * @return String Número de entrada en Bandeja
	 */
	private void generarEntradaBandeja(ReferenciaRDS refAsiento,ReferenciaRDS refJustificante, Map refDocumentos) throws Exception{		
		BteSistraDelegate bte = DelegateBTEUtil.getBteSistraDelegate();
		bte.crearEntradaTelematica(refAsiento,refJustificante,refDocumentos);				
	}
		
	/**
	 * Generar justificante de registro y lo almacena en el RDS (SOLO PARA ENVIOS Y PREREGISTROS, PARA REGISTROS SE GENERA EN EL INTERFAZ DE REGISTRO)
	 * 
	 * @param ReferenciaRDS Referencia Asiento
	 * @return String Número de entrada en Bandeja
	 */
	private ReferenciaRDS generarJustificante(AsientoCompleto asiento,AsientoRegistral asientoXML,String numRegistro,Timestamp fechaRegistro) throws Exception{
		
		// Generamos justificante a partir asiento
		String justificante = 
			"<?xml version=\"1.0\" encoding=\"" + ConstantesXML.ENCODING + "\" standalone=\"yes\"?>" +
			"<JUSTIFICANTE version=\"1.0\">" +  
			asiento.getAsiento().substring(asiento.getAsiento().indexOf("<ASIENTO_REGISTRAL")) +			
			"  <NUMERO_REGISTRO>"+ numRegistro +"</NUMERO_REGISTRO> " + 
			"  <FECHA_REGISTRO>"+ StringUtil.fechaACadena(fechaRegistro,ConstantesAsientoXML.FECHA_REGISTRO_FORMATO) + "</FECHA_REGISTRO> " +
			" </JUSTIFICANTE> ";									
		
		// Insertamos justificante en RDS		
		DocumentoRDS docRds = new DocumentoRDS();
		docRds.setModelo(ConstantesRDS.MODELO_JUSTIFICANTE_REGISTRO);
		docRds.setVersion(ConstantesRDS.VERSION_JUSTIFICANTE);
		docRds.setDatosFichero(justificante.getBytes(ConstantesXML.ENCODING));
		docRds.setTitulo( ConstantesSTR.CAT_LANGUAGE.equals(  asientoXML.getDatosAsunto().getIdiomaAsunto() ) ? ConstantesSTR.PDF_FILE_TITLE_CAT : ConstantesSTR.PDF_FILE_TITLE_CAS );	    		
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
		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
		ReferenciaRDS ref =rds.insertarDocumento(docRds);		
		
		// Devolvemos referencia
		return ref;
	}
				
	/**
	 * Generar apuntes Log PAD
	 * @param idPersistencia ID Persistencia
	 * @param refAsiento	Referencia Asiento
	 * @param refJustificante Referencia Justificante
	 * @param referenciasRds Referencias rds documentos
	 * @throws Exception
	 */
	private void generarLogPAD(	ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,
								Map referenciasRds) throws Exception{	
		
		PadDelegate pad = DelegatePADUtil.getPadDelegate();  
		pad.logPad(refAsiento,refJustificante,referenciasRds);			
	}
	
	
	 /**
     * Genera Número de Preregistro
     * @return
     */
    private String generarNumeroPreregistro(char tipoDestino) throws Exception{
    	 GeneradorDelegate gen = DelegateUtil.getGeneradorDelegate();
    	 return gen.generarNumeroPreregistro(tipoDestino);    	 
    }
    
    /**
     * Genera Número de Envío
     * @return
     */
    private String generarNumeroEnvio() throws Exception{
    	 GeneradorDelegate gen = DelegateUtil.getGeneradorDelegate();
    	 return gen.generarNumeroEnvio();    	 
    }
    
    /**
	 * Genera usos RDS en preregistro para asiento, justificante y documentos asociados
	 */
	private void generarUsosRDS(String tipoUso,AsientoRegistral asientoXML,Map referenciasRDS,
			ReferenciaRDS refAsiento, ReferenciaRDS refJustificante,
			String numero, Date fecha) throws Exception{
		
		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
		UsoRDS uso;
		
		// Creamos uso para asiento					
		uso = new UsoRDS();
		uso.setReferenciaRDS(refAsiento);
		uso.setReferencia(numero);
		uso.setTipoUso(tipoUso);	
		uso.setFechaSello(fecha);
		rds.crearUso(uso);
		
		// Creamos uso para justificante					
		uso = new UsoRDS();
		uso.setReferenciaRDS(refJustificante);
		uso.setReferencia(numero);
		uso.setTipoUso(tipoUso);
		uso.setFechaSello(fecha);
		rds.crearUso(uso);
		
		// Creamos usos para documentos asiento
		Iterator it = asientoXML.getDatosAnexoDocumentacion().iterator();
    	while (it.hasNext()){
    		DatosAnexoDocumentacion da = (DatosAnexoDocumentacion) it.next();
			uso.setReferenciaRDS((ReferenciaRDS) referenciasRDS.get(da.getIdentificadorDocumento()));
			uso.setReferencia(numero);
			uso.setTipoUso(tipoUso);
			uso.setFechaSello(fecha);
			rds.crearUso(uso);
    	}    	
		
	}
    
}
