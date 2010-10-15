package es.caib.regtel.persistence.util;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.intf.RdsFacade;
import es.caib.redose.persistence.intf.RdsFacadeHome;
import es.caib.regtel.model.ExcepcionRegistroTelematico;
import es.caib.regtel.model.ReferenciaRDSAsientoRegistral;
import es.caib.regtel.model.ResultadoRegistroTelematico;
import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.intf.RegistroTelematicoEJB;
import es.caib.regtel.persistence.intf.RegistroTelematicoEJBHome;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.util.NifCif;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.avisonotificacion.factoria.FactoriaObjetosXMLAvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.ServicioAvisoNotificacionXML;
import es.caib.xml.avisonotificacion.factoria.impl.AvisoNotificacion;
import es.caib.xml.avisonotificacion.factoria.impl.Expediente;
import es.caib.xml.oficioremision.factoria.FactoriaObjetosXMLOficioRemision;
import es.caib.xml.oficioremision.factoria.ServicioOficioRemisionXML;
import es.caib.xml.oficioremision.factoria.impl.OficioRemision;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosAsunto;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DatosOrigen;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;

/**
 * Helper que permite realizar el proceso de registro de salida
 *
 */
public class RegistroSalidaHelper{

	private static Log log = LogFactory.getLog(RegistroSalidaHelper.class);
	
	// Factorias para creacion objetos xml
	private FactoriaObjetosXMLRegistro factReg;
	private FactoriaObjetosXMLAvisoNotificacion factAvi;
	private FactoriaObjetosXMLOficioRemision factOfi;
	
	// Objetos xml
	private Expediente expediente;	
	private AsientoRegistral asiento;
	private DatosOrigen datosOrigen;	
	private DatosAsunto datosAsunto;	
	private DatosInteresado datosRpte;	
	private DatosInteresado datosRpdo;
	private AvisoNotificacion aviso;	
	private OficioRemision oficio;
	
	// Indicadores para controlar que se han llamado a las funciones obligatorias
	private boolean setExpediente;
	private boolean setOficina;
	private boolean setInteresado;
	private boolean setNotificacion;
	
	// Anexos RDS a insertar (pueden ser del tipo DocumentoRDS o ReferenciaRDS en funcion de si se deben insertar o ya existen)
	private List anexos;
	
	/**
	 * Constructor
	 * @throws Exception
	 */
	public RegistroSalidaHelper() throws Exception{
		factReg = ServicioRegistroXML.crearFactoriaObjetosXML();
		factReg.setEncoding(ConstantesXML.ENCODING);
		factAvi = ServicioAvisoNotificacionXML.crearFactoriaObjetosXML();
		factAvi.setEncoding(ConstantesXML.ENCODING);
		factOfi = ServicioOficioRemisionXML.crearFactoriaObjetosXML();
		factOfi.setEncoding(ConstantesXML.ENCODING);
		
		initData();
		
	}
	
	/**
	 * Indica expediente al que pertenece el registro de salida  (Obligatorio) 
	 * 
	 * @param unidadAdministrativa Unidad admistrativa(Obligatorio)
	 * @param identificadorExpediente Identificador expediente(Obligatorio)
	 * @param claveExpediente Clave de acceso (Opcional: depende si el expediente esta protegido)
	 */
	public void setExpediente(long unidadAdministrativa,String identificadorExpediente,String claveExpediente)  throws ExcepcionRegistroTelematico{
		try {	
			expediente.setUnidadAdministrativa(Long.toString(unidadAdministrativa));
			expediente.setIdentificadorExpediente(identificadorExpediente);
			expediente.setClaveExpediente(claveExpediente);
		
			// la unidad administrativa se especifica tb en datos asunto
			datosAsunto.setCodigoUnidadAdministrativa(expediente.getUnidadAdministrativa());
			
			setExpediente = true;
		} catch (EstablecerPropiedadException e) {
			throw new ExcepcionRegistroTelematico("Excepcion estableciendo propiedades",e);
		}
	}
	
	/**
	 * Establece oficina registro (Obligatorio) 
	 * 
	 * @param organo Organo emisor de la notificacion segun tablas maestras REGWEB (Obligatorio) 
	 * @param oficinaRegistro Oficina registro segun tablas maestras REGWEB (Obligatorio) 
	 */
	public void setOficinaRegistro(String organo,String oficinaRegistro) throws ExcepcionRegistroTelematico{
		try{	
			datosOrigen.setCodigoEntidadRegistralOrigen(oficinaRegistro);			
			datosAsunto.setCodigoOrganoDestino(organo);
			setOficina=true;
		} catch (EstablecerPropiedadException e) {
			throw new ExcepcionRegistroTelematico("Excepcion estableciendo propiedades",e);
		}		
	}
	
	/**
	 * Establece datos receptor notificacion (Obligatorio)
	 * @param nif Nif/Cif (Obligatorio)
	 * @param apellidosNombre Apellidos y nombre (Obligatorio)
	 * @param userSeycon Usuario seycon (Si se refiere a un expediente autenticado)
	 * @param codPais Codigo pais de la direccion del interesado (Obligatorio)
	 * @param nomPais nombre pais  de la direccion del interesado  (Obligatorio)
	 * @param codProvincia Codigo provincia de la direccion del interesado (Obligatorio)
	 * @param nomProvincia Nombre provincia de la direccion del interesado (Obligatorio)
	 * @param codLocalidad Codigo localidad de la direccion del interesado (Obligatorio)
	 * @param nomLocalidad Nombre localidad de la direccion del interesado (Obligatorio)
	 */
	public void setDatosInteresado(String nif,String apellidosNombre,String userSeycon,String codPais,String nomPais,String codProvincia,String nomProvincia,String codLocalidad,String nomLocalidad)  throws ExcepcionRegistroTelematico	{
		try{	
			datosRpte = factReg.crearDatosInteresado();			
			datosRpte.setTipoInteresado(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE);
			
			if (NifCif.esNIF(nif))
				datosRpte.setTipoIdentificacion(new Character(ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF));
			else if (NifCif.esCIF(nif))
				datosRpte.setTipoIdentificacion(new Character(ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF));
			else if (NifCif.esNIE(nif))
				datosRpte.setTipoIdentificacion(new Character(ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE));				
			
			datosRpte.setNumeroIdentificacion(nif);
			datosRpte.setFormatoDatosInteresado(ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);			
			datosRpte.setIdentificacionInteresado(apellidosNombre);
			datosRpte.setUsuarioSeycon(userSeycon);
			
			DireccionCodificada dir = factReg.crearDireccionCodificada();
			datosRpte.setDireccionCodificada(dir);
			dir.setPaisOrigen(codPais);			
			dir.setCodigoProvincia(codProvincia);
			dir.setNombreProvincia(nomProvincia);
			dir.setCodigoMunicipio(codLocalidad);
			dir.setNombreMunicipio(nomLocalidad);
			
			setInteresado=true;
		} catch (EstablecerPropiedadException e) {
			throw new ExcepcionRegistroTelematico("Excepcion estableciendo propiedades",e);
		}	
	}
	

	/**
	 * En caso de que el interesado sea el representante establece los datos del representado (Opcional: depende si hay representacion)
	 * @param nif Nif/Cif representado (Obligatorio)
	 * @param apellidosNombre Apellidos y nombre  (Obligatorio)
	 */
	public void setDatosRepresentado(String nif, String apellidosNombre) throws ExcepcionRegistroTelematico {
		try{	
			datosRpdo = factReg.crearDatosInteresado();
			datosRpdo.setTipoInteresado(ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO);
			datosRpdo.setNumeroIdentificacion(nif);
			datosRpdo.setFormatoDatosInteresado(ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);
			datosRpdo.setIdentificacionInteresado(apellidosNombre);			
		} catch (EstablecerPropiedadException e) {
			throw new ExcepcionRegistroTelematico("Excepcion estableciendo propiedades",e);
		}		
	}

	/**
	 * Datos de la notificacion
	 * 
	 * @param codigoIdioma  (Obligatorio)
	 * @param tipoAsunto  (Obligatorio)
	 * @param tituloAviso  (Obligatorio)
	 * @param textoAviso (Obligatorio)
	 * @param tituloOficioRemision (Obligatorio)
	 * @param textoOficioRemision (Obligatorio)
	 * @param acuseRecibo (Obligatorio)
	 */
	public void setDatosNotificacion(String codigoIdioma,String tipoAsunto,String tituloAviso, String textoAviso,String textoSMSAviso, String tituloOficioRemision, String textoOficioRemision, boolean acuseRecibo) throws ExcepcionRegistroTelematico {
		try{	
			aviso.setTitulo(tituloAviso);
			aviso.setTexto(textoAviso);
			aviso.setTextoSMS(textoSMSAviso);
			aviso.setAcuseRecibo(new Boolean(acuseRecibo));
			
			oficio.setTitulo(tituloOficioRemision);
			oficio.setTexto(textoOficioRemision);
			
			datosAsunto.setIdiomaAsunto(codigoIdioma);	
			datosAsunto.setTipoAsunto(tipoAsunto);
			
			setNotificacion=true;
		} catch (EstablecerPropiedadException e) {
			throw new ExcepcionRegistroTelematico("Excepcion estableciendo propiedades",e);
		}
	}

	/**
	 * Añade documento (Opcional: depende de si desea anexar un documento)
	 * 
	 * @param modelo (Obligatorio)
	 * @param version (Obligatorio)
	 * @param contenido (Obligatorio)
	 * @param nombre (Obligatorio)
	 * @param plantilla (Opcional. Solo tiene sentido para modelso estructurados.)
	 * @param firmas (Opcional)
	 */
	public void addDocumento(String modelo, int version, byte[] contenido, String nombre, String extension, String plantilla, FirmaIntf firmas[]) throws ExcepcionRegistroTelematico {
		DocumentoRDS doc = new DocumentoRDS();
		doc.setModelo(modelo);
		doc.setVersion(version);
		String nomfic = ""; 
		try{
			nomfic = StringUtil.normalizarNombreFichero(nombre)+"."+extension;
		}catch (Exception ex){
			nomfic = nombre+"."+extension;
		}		
		doc.setNombreFichero(nomfic);
		doc.setExtensionFichero(extension);
		doc.setDatosFichero(contenido);
		doc.setFirmas(firmas);
		doc.setPlantilla(plantilla);
		doc.setTitulo(nombre);
		anexos.add(doc);
	}

	/**
	 * Añade documento ya existente en RDS (Opcional: depende de si desea anexar un documento)
	 *
	 * @param doc (Obligatorio)
	 */
	public void addDocumento(ReferenciaRDS ref) throws ExcepcionRegistroTelematico {
		anexos.add(ref);
	}

	
	public ReferenciaRDSAsientoRegistral generarAsientoRegistral(String providerUrl) throws ExcepcionRegistroTelematico{
		try{
			
			// Comprobamos si se han invocado los metodos obligatorios
			if (!setExpediente){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setExpediente");
			}
			if (!setOficina){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setOficinaRegistro");
			}
			if (!setInteresado){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setDatosInteresado");
			}
			if (!setNotificacion){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setDatosNotificacion");
			}
			
			// Obtenemos referencias EJBs
			RegistroTelematicoEJB regtelEJB = getRegistroTelematicoEJB(providerUrl);
			RdsFacade redoseEJB = getRdsEJB(providerUrl);
			
			// Establecemos fecha asunto
			datosAsunto.setFechaAsunto(new Date());
			
			// Comprobamos organo destino
			boolean enc=false;
			try{				
				List svd = regtelEJB.obtenerServiciosDestino();				
				for (Iterator it=svd.iterator();it.hasNext();){
					ValorOrganismo vo =  (ValorOrganismo) it.next();
					if (vo.getCodigo().equals(datosAsunto.getCodigoOrganoDestino())){
						datosAsunto.setDescripcionOrganoDestino(vo.getDescripcion());
						enc=true;
						break;
					}
				}
			} catch (Exception e) {
				throw new ExcepcionRegistroTelematico("Error al consultar organos destino",e);
			}	
			if (!enc){
				throw new ExcepcionRegistroTelematico("No se encuentra organo destino con codigo " + datosAsunto.getCodigoOrganoDestino());
			}
			
			// Añadimos rpte/rpdo
			asiento.getDatosInteresado().add(datosRpte);
			if (datosRpdo!=null){
				asiento.getDatosInteresado().add(datosRpdo);
			}
			
			// Actualizamos datos de anexos (nif,nombre,unidad administrativa,etc)
			Object anexo;
			for (Iterator it=anexos.iterator();it.hasNext();){
					anexo = it.next();
					if (anexo instanceof DocumentoRDS){
						DocumentoRDS docRDSAnexo = (DocumentoRDS) anexo;
				docRDSAnexo.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
				docRDSAnexo.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
				docRDSAnexo.setNif(datosRpte.getNumeroIdentificacion());
			}			
			}			
			
			// Generamos xml de aviso			
			String xmlAviso = factAvi.guardarAvisoNotificacion(aviso);
			String nomDocAviso = datosAsunto.getIdiomaAsunto().equals("es")?"Aviso de notificación":"Avís de notificació";			
			
			DocumentoRDS docRDSAviso = new DocumentoRDS();
			docRDSAviso.setModelo(ConstantesRDS.MODELO_AVISO_NOTIFICACION);			
			docRDSAviso.setVersion(ConstantesRDS.VERSION_AVISO_NOTIFICACION);
			docRDSAviso.setNombreFichero(nomDocAviso+".xml");
			docRDSAviso.setExtensionFichero("xml");
			docRDSAviso.setDatosFichero(xmlAviso.getBytes(ConstantesXML.ENCODING));			
			docRDSAviso.setTitulo(nomDocAviso);			
			docRDSAviso.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
			docRDSAviso.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
			docRDSAviso.setNif(datosRpte.getNumeroIdentificacion());
			
			DatosAnexoDocumentacion anexoAviso = crearDatosAnexoDocumentacion(docRDSAviso,ConstantesAsientoXML.IDENTIFICADOR_AVISO_NOTIFICACION+"-1",ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION,true);
			
			
			// Generamos xml de oficio
			String xmlOficio = factOfi.guardarOficioRemision(oficio);
			String nomDocOficio = datosAsunto.getIdiomaAsunto().equals("es")?"Oficio de remisión":"Ofici de remissió";
			
			DocumentoRDS docRDSOficio = new DocumentoRDS();
			docRDSOficio.setModelo(ConstantesRDS.MODELO_OFICIO_REMISION);			
			docRDSOficio.setVersion(ConstantesRDS.VERSION_OFICIO_REMISION);
			docRDSOficio.setNombreFichero(nomDocOficio+".xml");
			docRDSOficio.setExtensionFichero("xml");
			docRDSOficio.setDatosFichero(xmlOficio.getBytes(ConstantesXML.ENCODING));			
			docRDSOficio.setTitulo(nomDocOficio);			
			docRDSOficio.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
			docRDSOficio.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
			docRDSOficio.setNif(datosRpte.getNumeroIdentificacion());
			
			DatosAnexoDocumentacion anexoOficio = crearDatosAnexoDocumentacion(docRDSOficio,ConstantesAsientoXML.IDENTIFICADOR_OFICIO_REMISION+"-1",ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION,true);
									
			//Generamos xml de asiento de prueba para detectar incoherencias
			testAsiento(anexoAviso,anexoOficio, redoseEJB);
			
			// Insertamos documentos en RDS en caso necesario y actualizamos asiento
			ReferenciaRDS ref;
			Map refDocs = new HashMap();
			//  - Documento de aviso
			ref = redoseEJB.insertarDocumento(docRDSAviso);
			docRDSAviso.setReferenciaRDS(ref);
			anexoAviso.setCodigoRDS(Long.toString(ref.getCodigo()));	
			asiento.getDatosAnexoDocumentacion().add(anexoAviso);
			refDocs.put(anexoAviso.getIdentificadorDocumento(),docRDSAviso.getReferenciaRDS());
			
			//  - Documento de oficio
			ref = redoseEJB.insertarDocumento(docRDSOficio);
			docRDSOficio.setReferenciaRDS(ref);
			anexoOficio.setCodigoRDS(Long.toString(ref.getCodigo()));
			asiento.getDatosAnexoDocumentacion().add(anexoOficio);
			refDocs.put(anexoOficio.getIdentificadorDocumento(),docRDSOficio.getReferenciaRDS());
			
			// - Documentos anexos (insertamos y añadimos a asiento)
			int i=0;
			DocumentoRDS docRDSAnexo;
			for (Iterator it=anexos.iterator();it.hasNext();){
				i++;
				// Insertamos rds en caso necesario
				anexo = it.next();
				docRDSAnexo = null;				
				if (anexo instanceof DocumentoRDS){
					// Insertamos en RDS
					docRDSAnexo = (DocumentoRDS) anexo;
				ref = redoseEJB.insertarDocumento(docRDSAnexo);
				docRDSAnexo.setReferenciaRDS(ref);
				}else if (anexo instanceof ReferenciaRDS){
					// Recuperamos del RDS
					ref = (ReferenciaRDS) anexo;
					docRDSAnexo = redoseEJB.consultarDocumento(ref);					
				}else{
					throw new Exception("La clase del anexo deber ser DocumentoRDS o ReferenciaRDS");
				}								
				
				// Añadimos a asiento
				DatosAnexoDocumentacion datosAnexo = crearDatosAnexoDocumentacion(docRDSAnexo,"ANEXO-"+i,ConstantesAsientoXML.DATOSANEXO_OTROS,docRDSAnexo.isEstructurado());
				datosAnexo.setCodigoRDS(Long.toString(ref.getCodigo()));
				asiento.getDatosAnexoDocumentacion().add(datosAnexo);
				refDocs.put(datosAnexo.getIdentificadorDocumento(),docRDSAnexo.getReferenciaRDS());
			}
			
			// Generamos xml de asiento e insertamos en RDS
			String xmlAsiento = factReg.guardarAsientoRegistral(asiento);					
			log.debug("Asiento registral generado: \n" + xmlAsiento);
			DocumentoRDS docRDSAsiento = new DocumentoRDS();
			docRDSAsiento.setModelo(ConstantesRDS.MODELO_ASIENTO_REGISTRAL);			
			docRDSAsiento.setVersion(ConstantesRDS.VERSION_ASIENTO);
			docRDSAsiento.setNombreFichero("Asiento.xml");
			docRDSAsiento.setExtensionFichero("xml");
			docRDSAsiento.setDatosFichero(xmlAsiento.getBytes(ConstantesXML.ENCODING));			
			docRDSAsiento.setTitulo("Asiento registral");			
			docRDSAsiento.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
			docRDSAsiento.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
			docRDSAsiento.setNif(datosRpte.getNumeroIdentificacion());
			ref = redoseEJB.insertarDocumento(docRDSAsiento);
			
			// Devolvemos referencia asiento registral y sus anexos
			ReferenciaRDSAsientoRegistral refAsiento = new ReferenciaRDSAsientoRegistral();
			refAsiento.setAsientoRegistral(ref);
			refAsiento.setAnexos(refDocs);
			
			return refAsiento;

		}catch(Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion generando asiento registral",ex);
		}
	}

			
	/**
	 * Realiza proceso de registro a partir de los datos suministrados
	 * @param providerUrl
	 * @return
	 * @throws ExcepcionRegistroTelematico
	 */
	public ResultadoRegistroTelematico registrar(String providerUrl, ReferenciaRDSAsientoRegistral refAsiento) throws ExcepcionRegistroTelematico {
		try{
			// Registramos
			RegistroTelematicoEJB regtelEJB = getRegistroTelematicoEJB(providerUrl);
			ResultadoRegistroTelematico res = regtelEJB.registroSalida(refAsiento.getAsientoRegistral(),refAsiento.getAnexos());
			log.debug("Registro salida efectuado: " + res.getResultadoRegistro().getNumeroRegistro());
			return res;
		}catch(Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion en proceso de registro",ex);
		}
	}

	/**
	 * Posteriormente a registrar podemos obtener las referencias RDS de los documentos anexados 
	 * @param index Indice del documento (empieza desde 0)
	 * @return
	 */
	public ReferenciaRDS getReferenciaDocumento(int index) throws ExcepcionRegistroTelematico {
		if (index >= anexos.size()) throw new ExcepcionRegistroTelematico("No existe documento con indice " + index);
		if(anexos.get(index) instanceof DocumentoRDS){
		DocumentoRDS doc = (DocumentoRDS) anexos.get(index);		
		return doc.getReferenciaRDS();
		}else if(anexos.get(index) instanceof ReferenciaRDS){
			return (ReferenciaRDS)anexos.get(index);
		}else{
			throw new ExcepcionRegistroTelematico("La clase del anexo deber ser DocumentoRDS o ReferenciaRDS");
		}
	}
	
	/**
	 * Resetea informacion para reutilizar helper para nuevo registro 
	 * @param index
	 * @return
	 */
	public void reset() throws ExcepcionRegistroTelematico {
		try {
			initData();
		}catch(Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion creando objetos",ex);
		}		
	}
	
	// ------------------------------------------------------------------------------------------------------
	//		FUNCIONES DE UTILIDAD
	// ------------------------------------------------------------------------------------------------------
	
	private void initData() throws Exception {
		//	Creamos objetos xml Asiento
		asiento = factReg.crearAsientoRegistral();
		datosOrigen = factReg.crearDatosOrigen();
		datosOrigen.setTipoRegistro(new Character(ConstantesAsientoXML.TIPO_REGISTRO_SALIDA));	
		datosOrigen.setNumeroRegistro("");		
		asiento.setDatosOrigen(datosOrigen);		
		datosAsunto = factReg.crearDatosAsunto();
		datosAsunto.setExtractoAsunto("Notificació telemática");
		asiento.setDatosAsunto(datosAsunto);
		asiento.setVersion("1.0");			
		
		// Creamos objectos xml Aviso
		aviso = factAvi.crearAvisoNotificacion();
		expediente = factAvi.crearExpediente();
		aviso.setExpediente(expediente);
		
		// Creamos objetos xml Oficio
		oficio = factOfi.crearOficioRemision();
		
		// Reseteamos anexos
		anexos = new ArrayList();
		
		//Reseteamos indicadores de llamada
		setExpediente=false;
		setOficina=false;
		setInteresado=false;
		setNotificacion=false;
	}
	
	
	/**
	 * Generamos asiento de test para detectar incoherencias
	 * @param anexoOficio 
	 * @param anexoAviso 
	 * @throws Exception
	 */
	private void testAsiento(DatosAnexoDocumentacion anexoAviso, DatosAnexoDocumentacion anexoOficio, RdsFacade redoseEJB) throws Exception{
		
		try{
			anexoAviso.setCodigoRDS("test");			
			anexoOficio.setCodigoRDS("test");
			
			asiento.getDatosAnexoDocumentacion().add(anexoAviso);
			asiento.getDatosAnexoDocumentacion().add(anexoOficio);
			
			// - Añadimos anexos al asiento
			int i = 0;
			Object anexoTipo;
			for (Iterator it=anexos.iterator();it.hasNext();){
				i++;
				anexoTipo = it.next();
				if (anexoTipo instanceof DocumentoRDS){
					DocumentoRDS anexo = (DocumentoRDS) anexoTipo;
				DatosAnexoDocumentacion datosAnexo = crearDatosAnexoDocumentacion(anexo,"ANEXO-"+i,ConstantesAsientoXML.DATOSANEXO_OTROS,anexo.isEstructurado());
				datosAnexo.setCodigoRDS("test");
				asiento.getDatosAnexoDocumentacion().add(datosAnexo);
				}else if(anexoTipo instanceof ReferenciaRDS){
					ReferenciaRDS refAnexo = (ReferenciaRDS)anexoTipo;
					DocumentoRDS anexo = redoseEJB.consultarDocumento(refAnexo,false);
					DatosAnexoDocumentacion datosAnexo = crearDatosAnexoDocumentacion(anexo,"ANEXO-"+i,ConstantesAsientoXML.DATOSANEXO_OTROS,anexo.isEstructurado());
					datosAnexo.setCodigoRDS("test");
					asiento.getDatosAnexoDocumentacion().add(datosAnexo);
				}
			}
			
			String xmlAsiento = factReg.guardarAsientoRegistral(asiento);
			factReg.crearAsientoRegistral(new ByteArrayInputStream(xmlAsiento.getBytes(ConstantesXML.ENCODING)));
			
		}finally{
			// Borramos anexos
			asiento.getDatosAnexoDocumentacion().clear();
			// Eliminamos referencias rds test
			anexoAviso.setCodigoRDS(null);
			anexoOficio.setCodigoRDS(null);
		}
		
	}

	/**
	 * Crea datos anexo a partir de la informacion suministrada
	 * @param anexo
	 * @param identificador
	 * @param tipo
	 * @param estructurado
	 * @return
	 * @throws EstablecerPropiedadException
	 */
	private DatosAnexoDocumentacion crearDatosAnexoDocumentacion(DocumentoRDS anexo,String identificador,char tipo,boolean estructurado) throws Exception{
		DatosAnexoDocumentacion datosAnexo;
		datosAnexo=factReg.crearDatosAnexoDocumentacion();
		datosAnexo.setTipoDocumento(new Character(tipo));
		datosAnexo.setIdentificadorDocumento(identificador);
		datosAnexo.setCodigoNormalizado(anexo.getModelo() + "-" + anexo.getVersion());
		datosAnexo.setNombreDocumento(anexo.getTitulo());
		datosAnexo.setEstucturado(new Boolean(estructurado));
		datosAnexo.setExtractoDocumento(anexo.getTitulo());		
		if(anexo.getHashFichero() != null && !"".equals(anexo.getHashFichero())){
			datosAnexo.setHashDocumento(anexo.getHashFichero());
		}else{
		datosAnexo.setHashDocumento(generaHash(anexo.getDatosFichero()));
		}
		return datosAnexo;
	}
	
	
	
	private RegistroTelematicoEJB getRegistroTelematicoEJB(String providerUrl) throws ExcepcionRegistroTelematico{
		try{
			Properties configuracion = getConfiguracion(providerUrl);						
			InitialContext initialContext = new InitialContext(configuracion);
			Object objRef = initialContext.lookup(RegistroTelematicoEJBHome.JNDI_NAME);
			RegistroTelematicoEJBHome homeRegistro = ( RegistroTelematicoEJBHome ) javax.rmi.PortableRemoteObject.narrow(objRef, RegistroTelematicoEJBHome.class);
			RegistroTelematicoEJB registroTelematicoEJB = homeRegistro.create();
			return registroTelematicoEJB;
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo referencia EJB del Registro Telematico",ex);
		}
	}

	private RdsFacade getRdsEJB(String providerUrl) throws ExcepcionRegistroTelematico{
		try{
			Properties configuracion = getConfiguracion(providerUrl);
			InitialContext initialContext = new InitialContext(configuracion);
			Object objRef = initialContext.lookup(RdsFacadeHome.JNDI_NAME);
			RdsFacadeHome homeRDS = ( RdsFacadeHome ) javax.rmi.PortableRemoteObject.narrow(objRef, RdsFacadeHome.class);
			RdsFacade rdsEJB = homeRDS.create();
			return rdsEJB;
		}catch (Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion obteniendo referencia EJB del REDOSE",ex);
		}
	}
	
	private Properties getConfiguracion(String providerUrl) throws ExcepcionRegistroTelematico{
		Properties configuracion = new Properties();
		if (!providerUrl.equals("LOCAL")){				
			if (providerUrl.startsWith("http")){
				configuracion.put("java.naming.factory.initial","org.jboss.naming.HttpNamingContextFactory");	
			}else if (providerUrl.startsWith("jnp")){
				configuracion.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
			}else{
				throw new ExcepcionRegistroTelematico("Provider debe ser jnp o http");
			}
			configuracion.put("java.naming.provider.url",providerUrl);
			configuracion.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");				
		}		
		return configuracion;
	}
	
	private String generaHash(byte[] datos) throws Exception{    	    	
    	MessageDigest dig = MessageDigest.getInstance(ConstantesRDS.HASH_ALGORITMO);        	
    	return  new String(Hex.encodeHex(dig.digest(datos)));    	
    }


			
	
	/**
	 * Realiza proceso de registro a partir de los datos suministrados
	 * @param providerUrl
	 * @return
	 * @throws ExcepcionRegistroTelematico
	
	public ResultadoRegistroTelematico registrarDocsEnRDS(String providerUrl) throws ExcepcionRegistroTelematico {
		try{
			
			// Comprobamos si se han invocado los metodos obligatorios
			if (!setExpediente){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setExpediente");
			}
			if (!setOficina){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setOficinaRegistro");
			}
			if (!setInteresado){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setDatosInteresado");
			}
			if (!setNotificacion){
				throw new ExcepcionRegistroTelematico("Debe establecerse el metodo setDatosNotificacion");
			}
			
			// Añadimos rpte/rpdo
			asiento.getDatosInteresado().add(datosRpte);
			if (datosRpdo!=null){
				asiento.getDatosInteresado().add(datosRpdo);
			}
			
			// Actualizamos datos de anexos (nif,nombre,unidad administrativa,etc)
			for (Iterator it=anexos.iterator();it.hasNext();){
				DocumentoRDS docRDSAnexo = (DocumentoRDS) it.next();
				docRDSAnexo.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
				docRDSAnexo.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
				docRDSAnexo.setNif(datosRpte.getNumeroIdentificacion());
				if(docRDSAnexo.getReferenciaRDS() == null){
					throw new ExcepcionRegistroTelematico("Debe establecerse la ReferenciaRDS en todos los documentos.");
				}
			}			
			
			// Generamos xml de aviso			
			String xmlAviso = factAvi.guardarAvisoNotificacion(aviso);
			String nomDocAviso = datosAsunto.getIdiomaAsunto().equals("es")?"Aviso de notificación":"Avís de notificació";			
			
			DocumentoRDS docRDSAviso = new DocumentoRDS();
			docRDSAviso.setModelo(ConstantesRDS.MODELO_AVISO_NOTIFICACION);			
			docRDSAviso.setVersion(ConstantesRDS.VERSION_AVISO_NOTIFICACION);
			docRDSAviso.setNombreFichero(nomDocAviso+".xml");
			docRDSAviso.setExtensionFichero("xml");
			docRDSAviso.setDatosFichero(xmlAviso.getBytes(ConstantesXML.ENCODING));			
			docRDSAviso.setTitulo(nomDocAviso);			
			docRDSAviso.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
			docRDSAviso.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
			docRDSAviso.setNif(datosRpte.getNumeroIdentificacion());
			
			DatosAnexoDocumentacion anexoAviso = crearDatosAnexoDocumentacion(docRDSAviso,ConstantesAsientoXML.IDENTIFICADOR_AVISO_NOTIFICACION+"-1",ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION,true);
			
			
			// Generamos xml de oficio
			String xmlOficio = factOfi.guardarOficioRemision(oficio);
			String nomDocOficio = datosAsunto.getIdiomaAsunto().equals("es")?"Oficio de remisión":"Ofici de remissió";
			
			DocumentoRDS docRDSOficio = new DocumentoRDS();
			docRDSOficio.setModelo(ConstantesRDS.MODELO_OFICIO_REMISION);			
			docRDSOficio.setVersion(ConstantesRDS.VERSION_OFICIO_REMISION);
			docRDSOficio.setNombreFichero(nomDocOficio+".xml");
			docRDSOficio.setExtensionFichero("xml");
			docRDSOficio.setDatosFichero(xmlOficio.getBytes(ConstantesXML.ENCODING));			
			docRDSOficio.setTitulo(nomDocOficio);			
			docRDSOficio.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
			docRDSOficio.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
			docRDSOficio.setNif(datosRpte.getNumeroIdentificacion());
			
			DatosAnexoDocumentacion anexoOficio = crearDatosAnexoDocumentacion(docRDSOficio,ConstantesAsientoXML.IDENTIFICADOR_OFICIO_REMISION+"-1",ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION,true);
									
			// Generamos xml de asiento de prueba para detectar incoherencias
			testAsiento(anexoAviso,anexoOficio);
							
			// Obtenemos referencias EJBs
			RegistroTelematicoEJB regtelEJB = getRegistroTelematicoEJB(providerUrl);
			RdsFacade redoseEJB = getRdsEJB(providerUrl);
			
			// Insertamos documentos en RDS y actualizamos asiento
			ReferenciaRDS ref;
			Map refDocs = new HashMap();
			//  - Documento de aviso
			ref = redoseEJB.insertarDocumento(docRDSAviso);
			docRDSAviso.setReferenciaRDS(ref);
			anexoAviso.setCodigoRDS(Long.toString(ref.getCodigo()));	
			asiento.getDatosAnexoDocumentacion().add(anexoAviso);
			refDocs.put(anexoAviso.getIdentificadorDocumento(),docRDSAviso.getReferenciaRDS());
			
			//  - Documento de oficio
			ref = redoseEJB.insertarDocumento(docRDSOficio);
			docRDSOficio.setReferenciaRDS(ref);
			anexoOficio.setCodigoRDS(Long.toString(ref.getCodigo()));
			asiento.getDatosAnexoDocumentacion().add(anexoOficio);
			refDocs.put(anexoOficio.getIdentificadorDocumento(),docRDSOficio.getReferenciaRDS());
			
			// - Documentos anexos (insertamos y añadimos a asiento)
			int i=0;
			for (Iterator it=anexos.iterator();it.hasNext();){
				i++;
				DocumentoRDS docRDSAnexo = (DocumentoRDS) it.next();
				// Añadimos a asiento
				DatosAnexoDocumentacion datosAnexo = crearDatosAnexoDocumentacion(docRDSAnexo,"ANEXO-"+i,ConstantesAsientoXML.DATOSANEXO_OTROS,docRDSAnexo.isEstructurado());
				datosAnexo.setCodigoRDS(Long.toString(docRDSAnexo.getReferenciaRDS().getCodigo()));
				asiento.getDatosAnexoDocumentacion().add(datosAnexo);
				refDocs.put(datosAnexo.getIdentificadorDocumento(),docRDSAnexo.getReferenciaRDS());
			}
			
			// Generamos xml de asiento e insertamos en RDS
			String xmlAsiento = factReg.guardarAsientoRegistral(asiento);					
			DocumentoRDS docRDSAsiento = new DocumentoRDS();
			docRDSAsiento.setModelo(ConstantesRDS.MODELO_ASIENTO_REGISTRAL);			
			docRDSAsiento.setVersion(ConstantesRDS.VERSION_ASIENTO);
			docRDSAsiento.setNombreFichero("Asiento.xml");
			docRDSAsiento.setExtensionFichero("xml");
			docRDSAsiento.setDatosFichero(xmlAsiento.getBytes(ConstantesXML.ENCODING));			
			docRDSAsiento.setTitulo("Asiento registral");			
			docRDSAsiento.setUnidadAdministrativa(Long.parseLong(expediente.getUnidadAdministrativa(),10));
			docRDSAsiento.setUsuarioSeycon(datosRpte.getUsuarioSeycon());			
			docRDSAsiento.setNif(datosRpte.getNumeroIdentificacion());
			ref = redoseEJB.insertarDocumento(docRDSAsiento);
			
			// Registramos
			ResultadoRegistroTelematico res = regtelEJB.registroSalida(ref,refDocs);
			
			return res;
		}catch(Exception ex){
			throw new ExcepcionRegistroTelematico("Excepcion en proceso de registro",ex);
		}
	}
	 */

}

