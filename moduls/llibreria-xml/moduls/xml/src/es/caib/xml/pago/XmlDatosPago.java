package es.caib.xml.pago;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Generador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;


/**
 * Modeliza el XML de datos de un pago
 */
public class XmlDatosPago implements Serializable{

	private String modeloRDSPago; 
	private int versionRDSPago;
	
	private char tipoPago; // Tipo de pago: Presencial (P) / Telemático (T)
	
	// Plugin usado (si es el por defecto, no aparece en el XML)
	private String pluginId;
	private boolean pluginDefecto = true;
	
	// Datos del pago establecidos por el script
	private String organoEmisor;
	private String modelo; // Modelo
	private char estado; // Estado del pago
	private String idTasa;	// Identificador tasa
	private String concepto; // Concepto	
	private Date fechaDevengo; // Fecha en la que se calcula el pago
	private Date fechaLimitePago; // Fecha limite en la que se puede iniciar el pago
	private String importe; // Importe del pago
	private String nif; //  Datos declarante
	private String nombre; 
	private String domicilioSiglas;
	private String domicilioVia;
	private String domicilioNumero; 
	private String domicilioLetra;
	private String domicilioEscalera;
	private String domicilioPiso;
	private String domicilioPuerta;
	private String telefono;
	private String fax;
	private String localidad;
	private String provincia;
	private String codigoPostal;
	
	// Datos del pago generados por la plataforma
	private String localizador; // Referencia plataforma tramitación
	private String numeroDUI; // Referencia plataforma pagos (para Telemático)
	private Date fechaPago; // Fecha en la que se realiza el pago (para Telemático)
	private String justificantePago; // Justificante de pago (en Base 64) devuelto por la plataforma (para Telemático)
	
	// Instrucciones entrega presencial (para documento pago presencial)
	private String instruccionesPresencialTexto;
	private String instruccionesPresencialEntidad1Nombre;
	private String instruccionesPresencialEntidad1Cuenta;
	private String instruccionesPresencialEntidad2Nombre;
	private String instruccionesPresencialEntidad2Cuenta;
	private String instruccionesPresencialEntidad3Nombre;
	private String instruccionesPresencialEntidad3Cuenta;
	private String instruccionesPresencialEntidad4Nombre;
	private String instruccionesPresencialEntidad4Cuenta;
	private String instruccionesPresencialEntidad5Nombre;
	private String instruccionesPresencialEntidad5Cuenta;
	private String instruccionesPresencialEntidad6Nombre;
	private String instruccionesPresencialEntidad6Cuenta;
	private String instruccionesPresencialEntidad7Nombre;
	private String instruccionesPresencialEntidad7Cuenta;
	private String instruccionesPresencialEntidad8Nombre;
	private String instruccionesPresencialEntidad8Cuenta;
	private String instruccionesPresencialEntidad9Nombre;
	private String instruccionesPresencialEntidad9Cuenta;
	private String instruccionesPresencialEntidad10Nombre;
	private String instruccionesPresencialEntidad10Cuenta;
	
	
	// Formato en la que se guarda la fecha del pago en el xml
	public final static String FORMATO_FECHAS = "yyyyMMddHHmmss";	
	
	// Constantes para la generación del xml
	public final static String XML_ROOT = "/PAGO";
	public final static String XML_PLUGIN_ID = XML_ROOT + "/DATOS_PAGO/PLUGIN_ID";
	public final static String XML_TIPO = XML_ROOT + "/DATOS_PAGO/TIPO";
	public final static String XML_ESTADO = XML_ROOT + "/DATOS_PAGO/ESTADO";
	public final static String XML_ORGANO_EMISOR = XML_ROOT + "/DATOS_PAGO/ORGANO_EMISOR";
	public final static String XML_MODELO = XML_ROOT + "/DATOS_PAGO/MODELO";
	public final static String XML_ID_TASA = XML_ROOT + "/DATOS_PAGO/ID_TASA";
	public final static String XML_IMPORTE = XML_ROOT + "/DATOS_PAGO/IMPORTE";
	public final static String XML_FECHA_DEVENGO = XML_ROOT + "/DATOS_PAGO/FECHA_DEVENGO";
	public final static String XML_FECHA_LIMITE_PAGO = XML_ROOT + "/DATOS_PAGO/FECHA_LIMITE_PAGO";
	public final static String XML_CONCEPTO = XML_ROOT + "/DATOS_PAGO/CONCEPTO";	
	public final static String XML_NIF = XML_ROOT + "/DATOS_PAGO/DECLARANTE/NIF";
	public final static String XML_NOMBRE = XML_ROOT + "/DATOS_PAGO/DECLARANTE/NOMBRE";	
	public final static String XML_DOMICILIO_SIGLAS = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/SIGLAS";
	public final static String XML_DOMICILIO_VIA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/VIA";
	public final static String XML_DOMICILIO_NUMERO = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/NUMERO";
	public final static String XML_DOMICILIO_LETRA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/LETRA";
	public final static String XML_DOMICILIO_ESCALERA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/ESCALERA";
	public final static String XML_DOMICILIO_PISO = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/PISO";
	public final static String XML_DOMICILIO_PUERTA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/PUERTA";
	public final static String XML_TELEFONO = XML_ROOT + "/DATOS_PAGO/DECLARANTE/TELEFONO";
	public final static String XML_FAX = XML_ROOT + "/DATOS_PAGO/DECLARANTE/FAX";
	public final static String XML_LOCALIDAD = XML_ROOT + "/DATOS_PAGO/DECLARANTE/LOCALIDAD";
	public final static String XML_PROVINCIA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/PROVINCIA";
	public final static String XML_CODIGOPOSTAL = XML_ROOT + "/DATOS_PAGO/DECLARANTE/CODIGOPOSTAL";
	public final static String XML_NUMERO_DUI = XML_ROOT + "/DATOS_PASARELA/NUMERO_DUI";
	public final static String XML_LOCALIZADOR = XML_ROOT + "/DATOS_PASARELA/LOCALIZADOR";
	public final static String XML_FECHA_PAGO = XML_ROOT + "/DATOS_PASARELA/FECHA_PAGO";
	public final static String XML_JUSTIFICANTE = XML_ROOT + "/DATOS_PASARELA/JUSTIFICANTE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_TEXTO = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/TEXTO";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD1/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD1/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD2/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD2/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD3/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD3/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD4/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD4/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD5/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD5/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD6/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD6/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD7/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD7/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD8/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD8/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD9/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD9/CUENTA";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_NOMBRE = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD10/NOMBRE";
	public final static String XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_CUENTA = XML_ROOT + "/INSTRUCCIONES_PRESENCIAL/ENTIDAD10/CUENTA";
	
	/**
	 * Obtiene los datos en bytes
	 * @return byte[]
	 */
	public byte[] getBytes() throws Exception{
		String datos = getString();
		return datos.getBytes(ConstantesXML.ENCODING);	
	}
	
	/**
	 * Establece los datos a partir de una array de bytes
	 * @param datos
	 */
	public void setBytes(byte[] datos) throws Exception {
			try{
				// Parseamos los datos del formulario a la hash			
				Analizador analizador = new Analizador ();			
		    	HashMapIterable map = analizador.analizar ( new ByteArrayInputStream(datos), ConstantesXML.ENCODING );		
		    	// Cargamos los datos desde la hash			
		    	fromHashMapIterable(map);
		    	// Cacheamos datos
		    	
			}catch (Exception e){			
				throw new Exception("Error al establecer datos de formulario: " + e.toString());
			}
	}
	
	/**
	 * Establece los datos a partir de un String
	 * @param datos
	 */
	public void setString(String datos) throws Exception{
		// Parseamos los datos del formulario a la hash					  
		setBytes(datos.getBytes(ConstantesXML.ENCODING));		
	}
	
	/**
	 * Obtiene datos como un String
	 * @param datos
	 */
	public String getString() throws Exception{
		// Generamos XML a partir de la hash
		Generador generador = new Generador();
		generador.setEncoding(ConstantesXML.ENCODING);
								
		// Generamos XML
		String xml = generador.generarXML(this.toHashMapIterable());
		
		// Generamos cabecera (substituimos <XMLROOT> por <XMLROOT modelo="xx" version="y">		
		String root = XML_ROOT.substring(1);
		if (xml.length() > 0){
			int pos = xml.indexOf(root); 
			xml = xml.substring(0,pos) + root +
					" modelo=\"" + modeloRDSPago + "\" version=\"" + versionRDSPago + "\" " + xml.substring(pos + root.length());
		}else{
			xml = "<?xml version=\"1.0\" encoding=\""+ ConstantesXML.ENCODING +"\"?>\n<"+ root +
					" modelo=\"" + modeloRDSPago + "\" version=\"" + versionRDSPago + "\"></" + root + ">";					
		}		
		return xml;
	}
		
	/**
	 * Genera Hashmap con las keys necesarias para poder generar el XML de pago
	 * @return
	 */
	private HashMapIterable toHashMapIterable() throws Exception{			
		
		HashMapIterable map = new HashMapIterable();	
		SimpleDateFormat sdf = new SimpleDateFormat( XmlDatosPago.FORMATO_FECHAS );
		
		Nodo nodo;
		
		if (this.getPluginId() != null) {
			nodo = new Nodo(XML_PLUGIN_ID, this.getPluginId());		
			nodo.setXpath(XML_PLUGIN_ID);
			map.put(nodo.getXpath(),nodo);
		}
		
		
		nodo = new Nodo(XML_TIPO, Character.toString(this.getTipoPago()));		
		nodo.setXpath(XML_TIPO);
		map.put(nodo.getXpath(),nodo);
				
		nodo = new Nodo(XML_ESTADO, Character.toString(this.getEstado()));		
		nodo.setXpath(XML_ESTADO);
		map.put(nodo.getXpath(),nodo);
		
		if (StringUtils.isNotEmpty(this.getOrganoEmisor())) {
			nodo = new Nodo(XML_ORGANO_EMISOR, this.getOrganoEmisor());		
			nodo.setXpath(XML_ORGANO_EMISOR);
			map.put(nodo.getXpath(),nodo);
		}
		
		nodo = new Nodo(XML_MODELO, this.getModelo());		
		nodo.setXpath(XML_MODELO);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_ID_TASA, this.getIdTasa());		
		nodo.setXpath(XML_ID_TASA);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_IMPORTE, this.getImporte());		
		nodo.setXpath(XML_IMPORTE);
		map.put(nodo.getXpath(),nodo);
					
		nodo = new Nodo(XML_FECHA_DEVENGO,this.getFechaDevengo()!=null?sdf.format(this.getFechaDevengo()):null);		
		nodo.setXpath(XML_FECHA_DEVENGO);
		map.put(nodo.getXpath(),nodo);
		
		if (this.getFechaLimitePago() != null) {
			nodo = new Nodo(XML_FECHA_LIMITE_PAGO,  sdf.format(this.getFechaLimitePago()));		
			nodo.setXpath(XML_FECHA_LIMITE_PAGO);
			map.put(nodo.getXpath(),nodo);
		}
		
		nodo = new Nodo(XML_CONCEPTO, this.getConcepto());		
		nodo.setXpath(XML_CONCEPTO);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_NIF, this.getNif());		
		nodo.setXpath(XML_NIF);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_NOMBRE, this.getNombre());		
		nodo.setXpath(XML_NOMBRE);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_DOMICILIO_SIGLAS, this.getDomicilioSiglas());		
		nodo.setXpath(XML_DOMICILIO_SIGLAS);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_DOMICILIO_VIA, this.getDomicilioVia());		
		nodo.setXpath(XML_DOMICILIO_VIA);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_DOMICILIO_NUMERO, this.getDomicilioNumero());		
		nodo.setXpath(XML_DOMICILIO_NUMERO);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_DOMICILIO_LETRA, this.getDomicilioLetra());		
		nodo.setXpath(XML_DOMICILIO_LETRA);
		map.put(nodo.getXpath(),nodo);
				
		nodo = new Nodo(XML_DOMICILIO_ESCALERA, this.getDomicilioEscalera());		
		nodo.setXpath(XML_DOMICILIO_ESCALERA);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_DOMICILIO_PISO, this.getDomicilioPiso());		
		nodo.setXpath(XML_DOMICILIO_PISO);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_DOMICILIO_PUERTA, this.getDomicilioPuerta());		
		nodo.setXpath(XML_DOMICILIO_PUERTA);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_TELEFONO, this.getTelefono());		
		nodo.setXpath(XML_TELEFONO);
		map.put(nodo.getXpath(),nodo);
				
		nodo = new Nodo(XML_FAX, this.getFax());		
		nodo.setXpath(XML_FAX);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_LOCALIDAD, this.getLocalidad());		
		nodo.setXpath(XML_LOCALIDAD);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_LOCALIDAD, this.getLocalidad());		
		nodo.setXpath(XML_LOCALIDAD);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_LOCALIDAD, this.getLocalidad());		
		nodo.setXpath(XML_LOCALIDAD);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_PROVINCIA, this.getProvincia());		
		nodo.setXpath(XML_PROVINCIA);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_CODIGOPOSTAL, this.getCodigoPostal());		
		nodo.setXpath(XML_CODIGOPOSTAL);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_NUMERO_DUI, this.getNumeroDUI());		
		nodo.setXpath(XML_NUMERO_DUI);
		map.put(nodo.getXpath(),nodo);
		
		nodo = new Nodo(XML_LOCALIZADOR, this.getLocalizador());		
		nodo.setXpath(XML_LOCALIZADOR);
		map.put(nodo.getXpath(),nodo);
		
		
		nodo = new Nodo(XML_FECHA_PAGO, this.getFechaPago()!=null?sdf.format(this.getFechaPago()):null);		
		nodo.setXpath(XML_FECHA_PAGO);
		map.put(nodo.getXpath(),nodo);	
		
		// Pasamos el justificante a B64 para que no existan problemas
		String justifB64 =  null;
		if (StringUtils.isNotEmpty(this.getJustificantePago())){
			// PATCH: YA DEBE VENIR EN B64
			// justifB64 = new String(Base64.encodeBase64(this.getJustificantePago().getBytes(ConstantesXML.ENCODING)),ConstantesXML.ENCODING);
			justifB64 = this.getJustificantePago();
		}
		nodo = new Nodo(XML_JUSTIFICANTE, justifB64);		
		nodo.setXpath(XML_JUSTIFICANTE);
		map.put(nodo.getXpath(),nodo);
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialTexto())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_TEXTO, this.getInstruccionesPresencialTexto());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_TEXTO);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad1Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_NOMBRE, this.getInstruccionesPresencialEntidad1Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad1Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_CUENTA, this.getInstruccionesPresencialEntidad1Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad2Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_NOMBRE, this.getInstruccionesPresencialEntidad2Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad2Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_CUENTA, this.getInstruccionesPresencialEntidad2Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad3Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_NOMBRE, this.getInstruccionesPresencialEntidad3Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad3Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_CUENTA, this.getInstruccionesPresencialEntidad3Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad4Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_NOMBRE, this.getInstruccionesPresencialEntidad4Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad4Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_CUENTA, this.getInstruccionesPresencialEntidad4Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad5Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_NOMBRE, this.getInstruccionesPresencialEntidad5Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad5Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_CUENTA, this.getInstruccionesPresencialEntidad5Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad6Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_NOMBRE, this.getInstruccionesPresencialEntidad6Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad6Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_CUENTA, this.getInstruccionesPresencialEntidad6Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad7Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_NOMBRE, this.getInstruccionesPresencialEntidad7Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad7Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_CUENTA, this.getInstruccionesPresencialEntidad7Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad8Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_NOMBRE, this.getInstruccionesPresencialEntidad8Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad8Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_CUENTA, this.getInstruccionesPresencialEntidad8Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad9Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_NOMBRE, this.getInstruccionesPresencialEntidad9Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad9Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_CUENTA, this.getInstruccionesPresencialEntidad9Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad10Nombre())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_NOMBRE, this.getInstruccionesPresencialEntidad10Nombre());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_NOMBRE);
			map.put(nodo.getXpath(),nodo);
		}
		
		if (StringUtils.isNotEmpty(this.getInstruccionesPresencialEntidad10Cuenta())) {
			nodo = new Nodo(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_CUENTA, this.getInstruccionesPresencialEntidad10Cuenta());		
			nodo.setXpath(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_CUENTA);
			map.put(nodo.getXpath(),nodo);
		}
		
		    	
		return map;		
	}
	
	/**
	 * Carga datos pago a partir de Hashmap con las keys del XML de pago
	 * @return
	 */
	private void fromHashMapIterable(HashMapIterable map) throws Exception{		
		SimpleDateFormat sdf = new SimpleDateFormat( XmlDatosPago.FORMATO_FECHAS );
		Nodo nodo;
		this.setPluginId(  map.get(XML_PLUGIN_ID) != null? ((Nodo) map.get(XML_PLUGIN_ID)).getValor():null);
		this.setTipoPago( ((Nodo) map.get(XML_TIPO)).getValor().charAt(0));
		this.setEstado( ((Nodo) map.get(XML_ESTADO)).getValor().charAt(0));		
		this.setOrganoEmisor( map.get(XML_ORGANO_EMISOR) != null? ((Nodo) map.get(XML_ORGANO_EMISOR)).getValor():null);
		this.setModelo(  map.get(XML_MODELO) != null? ((Nodo) map.get(XML_MODELO)).getValor():null);
		this.setIdTasa( map.get(XML_ID_TASA) != null? ((Nodo) map.get(XML_ID_TASA)).getValor():null);
		this.setImporte( map.get(XML_IMPORTE) != null? ((Nodo) map.get(XML_IMPORTE)).getValor():null);
		nodo = (Nodo) map.get(XML_FECHA_DEVENGO);
		if ( nodo != null){
			this.setFechaDevengo( StringUtils.isNotEmpty(nodo.getValor())?sdf.parse(nodo.getValor()):null);
		}
		nodo = (Nodo) map.get(XML_FECHA_LIMITE_PAGO);
		if ( nodo != null){
			this.setFechaLimitePago(StringUtils.isNotEmpty(nodo.getValor())?sdf.parse(nodo.getValor()):null);
		}
		this.setConcepto( map.get(XML_CONCEPTO) != null? ((Nodo) map.get(XML_CONCEPTO)).getValor():null);		
		this.setNif( map.get(XML_NIF) != null? ((Nodo) map.get(XML_NIF)).getValor():null);
		this.setNombre( map.get(XML_NOMBRE) != null? ((Nodo) map.get(XML_NOMBRE)).getValor():null);
		this.setDomicilioSiglas( map.get(XML_DOMICILIO_SIGLAS) != null? ((Nodo) map.get(XML_DOMICILIO_SIGLAS)).getValor():null);
		this.setDomicilioVia( map.get(XML_DOMICILIO_VIA) != null? ((Nodo) map.get(XML_DOMICILIO_VIA)).getValor():null);
		this.setDomicilioNumero( map.get(XML_DOMICILIO_NUMERO) != null? ((Nodo) map.get(XML_DOMICILIO_NUMERO)).getValor():null);
		this.setDomicilioLetra( map.get(XML_DOMICILIO_LETRA) != null? ((Nodo) map.get(XML_DOMICILIO_LETRA)).getValor():null);
		this.setDomicilioEscalera( map.get(XML_DOMICILIO_ESCALERA) != null? ((Nodo) map.get(XML_DOMICILIO_ESCALERA)).getValor():null);
		this.setDomicilioPiso( map.get(XML_DOMICILIO_PISO) != null? ((Nodo) map.get(XML_DOMICILIO_PISO)).getValor():null);
		this.setDomicilioPuerta( map.get(XML_DOMICILIO_PUERTA) != null? ((Nodo) map.get(XML_DOMICILIO_PUERTA)).getValor():null);
		this.setTelefono( map.get(XML_TELEFONO) != null? ((Nodo) map.get(XML_TELEFONO)).getValor():null);
		this.setFax( map.get(XML_FAX) != null? ((Nodo) map.get(XML_FAX)).getValor():null);
		this.setLocalidad( map.get(XML_LOCALIDAD) != null? ((Nodo) map.get(XML_LOCALIDAD)).getValor():null);
		this.setProvincia( map.get(XML_PROVINCIA) != null? ((Nodo) map.get(XML_PROVINCIA)).getValor():null);
		this.setCodigoPostal( map.get(XML_CODIGOPOSTAL) != null? ((Nodo) map.get(XML_CODIGOPOSTAL)).getValor():null);		
		this.setNumeroDUI( map.get(XML_NUMERO_DUI) != null? ((Nodo) map.get(XML_NUMERO_DUI)).getValor():null);
		this.setLocalizador( map.get(XML_LOCALIZADOR) != null? ((Nodo) map.get(XML_LOCALIZADOR)).getValor():null);
		nodo = (Nodo) map.get(XML_FECHA_PAGO);		
		if ( nodo != null){
			this.setFechaPago( StringUtils.isNotEmpty(nodo.getValor())?sdf.parse(nodo.getValor()):null);
		}				
		
		// Decodificamos Justificante en B64 
		if (map.get(XML_JUSTIFICANTE) != null){
			String justifB64 = ((Nodo) map.get(XML_JUSTIFICANTE)).getValor();
			// PATCH: DEBE ESTAR EN B64
			// String justif =  new String(Base64.decodeBase64(justifB64.getBytes(ConstantesXML.ENCODING)),ConstantesXML.ENCODING);			
			// this.setJustificantePago(justif);
			this.setJustificantePago(justifB64);
		}
		
		this.setInstruccionesPresencialTexto(map.get(XML_INSTRUCCIONES_PRESENCIAL_TEXTO) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_TEXTO)).getValor():null);
		this.setInstruccionesPresencialEntidad1Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad1Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD1_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad2Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad2Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD2_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad3Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad3Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD3_CUENTA)).getValor():null);		
		this.setInstruccionesPresencialEntidad4Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad4Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD4_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad5Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad5Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD5_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad6Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad6Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD6_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad7Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad7Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD7_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad8Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad8Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD8_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad9Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad9Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD9_CUENTA)).getValor():null);
		this.setInstruccionesPresencialEntidad10Nombre(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_NOMBRE) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_NOMBRE)).getValor():null);
		this.setInstruccionesPresencialEntidad10Cuenta(map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_CUENTA) != null? ((Nodo) map.get(XML_INSTRUCCIONES_PRESENCIAL_ENTIDAD10_CUENTA)).getValor():null);
		
						
	}
	
	/**
	 * Obtiene valor del campo en formato referencia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getValorCampo(String referenciaCampo) throws Exception{
		HashMapIterable datos = toHashMapIterable();
		Nodo val = (Nodo) datos.get(referenciaXPath(referenciaCampo));
		if (val != null) return val.getValor();
		return "";				
	}
	
	/**
	 * Convierte referencia campo del tipo seccion1.campo1 a expresión XPath /instancia/seccion1/campo1 
	 * @param referenciaCampo
	 * @return
	 */
	public static String referenciaXPath(String referenciaCampo){
		return referenciaCampo = XML_ROOT + "/" + referenciaCampo.replaceAll("\\.","/");
	}
	
	// --------- GETTERS / SETTERS  ------------------------------	
	public String getIdTasa() {
		return idTasa;
	}
	public void setIdTasa(String codigo) {
		this.idTasa = codigo;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Date getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(Date fecha) {
		this.fechaDevengo = fecha;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getNumeroDUI() {
		return numeroDUI;
	}
	public void setNumeroDUI(String referenciaPagos) {
		this.numeroDUI = referenciaPagos;
	}
	public String getLocalizador() {
		return localizador;
	}
	public void setLocalizador(String referenciaSistra) {
		this.localizador = referenciaSistra;
	}
	
	public char getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(char tipoPago) {
		this.tipoPago = tipoPago;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getJustificantePago() {
		return justificantePago;
	}
	public void setJustificantePago(String justificantePago) {
		this.justificantePago = justificantePago;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDomicilioEscalera() {
		return domicilioEscalera;
	}

	public void setDomicilioEscalera(String domicilioEscalera) {
		this.domicilioEscalera = domicilioEscalera;
	}

	public String getDomicilioLetra() {
		return domicilioLetra;
	}

	public void setDomicilioLetra(String domicilioLetra) {
		this.domicilioLetra = domicilioLetra;
	}

	public String getDomicilioNumero() {
		return domicilioNumero;
	}

	public void setDomicilioNumero(String domicilioNumero) {
		this.domicilioNumero = domicilioNumero;
	}

	public String getDomicilioPiso() {
		return domicilioPiso;
	}

	public void setDomicilioPiso(String domicilioPiso) {
		this.domicilioPiso = domicilioPiso;
	}

	public String getDomicilioPuerta() {
		return domicilioPuerta;
	}

	public void setDomicilioPuerta(String domicilioPuerta) {
		this.domicilioPuerta = domicilioPuerta;
	}

	public String getDomicilioSiglas() {
		return domicilioSiglas;
	}

	public void setDomicilioSiglas(String domicilioSiglas) {
		this.domicilioSiglas = domicilioSiglas;
	}

	public String getDomicilioVia() {
		return domicilioVia;
	}

	public void setDomicilioVia(String domicilioVia) {
		this.domicilioVia = domicilioVia;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	public String getOrganoEmisor() {
		return organoEmisor;
	}

	public void setOrganoEmisor(String organoEmisor) {
		this.organoEmisor = organoEmisor;
	}

	public String getModeloRDSPago() {
		return modeloRDSPago;
	}

	public void setModeloRDSPago(String modeloRDSPago) {
		this.modeloRDSPago = modeloRDSPago;
	}

	public boolean isPluginDefecto() {
		return pluginDefecto;
	}

	public void setPluginDefecto(boolean pluginDefecto) {
		this.pluginDefecto = pluginDefecto;
	}

	public int getVersionRDSPago() {
		return versionRDSPago;
	}

	public void setVersionRDSPago(int versionRDSPago) {
		this.versionRDSPago = versionRDSPago;
	}

	public String getInstruccionesPresencialTexto() {
		return instruccionesPresencialTexto;
	}

	public void setInstruccionesPresencialTexto(String instruccionesPresencialTexto) {
		this.instruccionesPresencialTexto = instruccionesPresencialTexto;
	}

	public String getInstruccionesPresencialEntidad1Nombre() {
		return instruccionesPresencialEntidad1Nombre;
	}

	public void setInstruccionesPresencialEntidad1Nombre(String instruccionesEntidad1Nombre) {
		this.instruccionesPresencialEntidad1Nombre = instruccionesEntidad1Nombre;
	}

	public String getInstruccionesPresencialEntidad1Cuenta() {
		return instruccionesPresencialEntidad1Cuenta;
	}

	public void setInstruccionesPresencialEntidad1Cuenta(String instruccionesEntidad1Cuenta) {
		this.instruccionesPresencialEntidad1Cuenta = instruccionesEntidad1Cuenta;
	}

	public String getInstruccionesPresencialEntidad2Nombre() {
		return instruccionesPresencialEntidad2Nombre;
	}

	public void setInstruccionesPresencialEntidad2Nombre(String instruccionesEntidad2Nombre) {
		this.instruccionesPresencialEntidad2Nombre = instruccionesEntidad2Nombre;
	}

	public String getInstruccionesPresencialEntidad2Cuenta() {
		return instruccionesPresencialEntidad2Cuenta;
	}

	public void setInstruccionesPresencialEntidad2Cuenta(String instruccionesEntidad2Cuenta) {
		this.instruccionesPresencialEntidad2Cuenta = instruccionesEntidad2Cuenta;
	}

	public String getInstruccionesPresencialEntidad3Nombre() {
		return instruccionesPresencialEntidad3Nombre;
	}

	public void setInstruccionesPresencialEntidad3Nombre(String instruccionesEntidad3Nombre) {
		this.instruccionesPresencialEntidad3Nombre = instruccionesEntidad3Nombre;
	}

	public String getInstruccionesPresencialEntidad3Cuenta() {
		return instruccionesPresencialEntidad3Cuenta;
	}

	public void setInstruccionesPresencialEntidad3Cuenta(String instruccionesEntidad3Cuenta) {
		this.instruccionesPresencialEntidad3Cuenta = instruccionesEntidad3Cuenta;
	}

	public String getInstruccionesPresencialEntidad4Nombre() {
		return instruccionesPresencialEntidad4Nombre;
	}

	public void setInstruccionesPresencialEntidad4Nombre(
			String instruccionesPresencialEntidad4Nombre) {
		this.instruccionesPresencialEntidad4Nombre = instruccionesPresencialEntidad4Nombre;
	}

	public String getInstruccionesPresencialEntidad4Cuenta() {
		return instruccionesPresencialEntidad4Cuenta;
	}

	public void setInstruccionesPresencialEntidad4Cuenta(
			String instruccionesPresencialEntidad4Cuenta) {
		this.instruccionesPresencialEntidad4Cuenta = instruccionesPresencialEntidad4Cuenta;
	}

	public String getInstruccionesPresencialEntidad5Nombre() {
		return instruccionesPresencialEntidad5Nombre;
	}

	public void setInstruccionesPresencialEntidad5Nombre(
			String instruccionesPresencialEntidad5Nombre) {
		this.instruccionesPresencialEntidad5Nombre = instruccionesPresencialEntidad5Nombre;
	}

	public String getInstruccionesPresencialEntidad5Cuenta() {
		return instruccionesPresencialEntidad5Cuenta;
	}

	public void setInstruccionesPresencialEntidad5Cuenta(
			String instruccionesPresencialEntidad5Cuenta) {
		this.instruccionesPresencialEntidad5Cuenta = instruccionesPresencialEntidad5Cuenta;
	}

	public String getInstruccionesPresencialEntidad6Nombre() {
		return instruccionesPresencialEntidad6Nombre;
	}

	public void setInstruccionesPresencialEntidad6Nombre(
			String instruccionesPresencialEntidad6Nombre) {
		this.instruccionesPresencialEntidad6Nombre = instruccionesPresencialEntidad6Nombre;
	}

	public String getInstruccionesPresencialEntidad6Cuenta() {
		return instruccionesPresencialEntidad6Cuenta;
	}

	public void setInstruccionesPresencialEntidad6Cuenta(
			String instruccionesPresencialEntidad6Cuenta) {
		this.instruccionesPresencialEntidad6Cuenta = instruccionesPresencialEntidad6Cuenta;
	}

	public String getInstruccionesPresencialEntidad7Nombre() {
		return instruccionesPresencialEntidad7Nombre;
	}

	public void setInstruccionesPresencialEntidad7Nombre(
			String instruccionesPresencialEntidad7Nombre) {
		this.instruccionesPresencialEntidad7Nombre = instruccionesPresencialEntidad7Nombre;
	}

	public String getInstruccionesPresencialEntidad7Cuenta() {
		return instruccionesPresencialEntidad7Cuenta;
	}

	public void setInstruccionesPresencialEntidad7Cuenta(
			String instruccionesPresencialEntidad7Cuenta) {
		this.instruccionesPresencialEntidad7Cuenta = instruccionesPresencialEntidad7Cuenta;
	}

	public String getInstruccionesPresencialEntidad8Nombre() {
		return instruccionesPresencialEntidad8Nombre;
	}

	public void setInstruccionesPresencialEntidad8Nombre(
			String instruccionesPresencialEntidad8Nombre) {
		this.instruccionesPresencialEntidad8Nombre = instruccionesPresencialEntidad8Nombre;
	}

	public String getInstruccionesPresencialEntidad8Cuenta() {
		return instruccionesPresencialEntidad8Cuenta;
	}

	public void setInstruccionesPresencialEntidad8Cuenta(
			String instruccionesPresencialEntidad8Cuenta) {
		this.instruccionesPresencialEntidad8Cuenta = instruccionesPresencialEntidad8Cuenta;
	}

	public String getInstruccionesPresencialEntidad9Nombre() {
		return instruccionesPresencialEntidad9Nombre;
	}

	public void setInstruccionesPresencialEntidad9Nombre(
			String instruccionesPresencialEntidad9Nombre) {
		this.instruccionesPresencialEntidad9Nombre = instruccionesPresencialEntidad9Nombre;
	}

	public String getInstruccionesPresencialEntidad9Cuenta() {
		return instruccionesPresencialEntidad9Cuenta;
	}

	public void setInstruccionesPresencialEntidad9Cuenta(
			String instruccionesPresencialEntidad9Cuenta) {
		this.instruccionesPresencialEntidad9Cuenta = instruccionesPresencialEntidad9Cuenta;
	}

	public String getInstruccionesPresencialEntidad10Nombre() {
		return instruccionesPresencialEntidad10Nombre;
	}

	public void setInstruccionesPresencialEntidad10Nombre(
			String instruccionesPresencialEntidad10Nombre) {
		this.instruccionesPresencialEntidad10Nombre = instruccionesPresencialEntidad10Nombre;
	}

	public String getInstruccionesPresencialEntidad10Cuenta() {
		return instruccionesPresencialEntidad10Cuenta;
	}

	public void setInstruccionesPresencialEntidad10Cuenta(
			String instruccionesPresencialEntidad10Cuenta) {
		this.instruccionesPresencialEntidad10Cuenta = instruccionesPresencialEntidad10Cuenta;
	}

	public Date getFechaLimitePago() {
		return fechaLimitePago;
	}

	public void setFechaLimitePago(Date fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}
	
}
