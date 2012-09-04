package es.caib.sistra.model;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Generador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.util.HashMapIterable;


/**
 * Modeliza el XML de datos de un pago
 */
public class DatosPago implements Serializable{

	private char tipoPago; // Tipo de pago: Presencial (P) / Telemático (T)
	
	// Datos del pago establecidos por el script
	private String modelo; // Modelo
	private char estado; // Estado del pago
	private String idTasa;	// Identificador tasa
	private String concepto; // Concepto	
	private Date fechaDevengo; // Fecha en la que se calcula el pago
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
	
	// Formato en la que se guarda la fecha del pago en el xml
	private final static String FORMATO_FECHAS = "yyyyMMddHHmmss";	
	
	// Constantes para la generación del xml
	public final static String XML_ROOT = "/PAGO";	
	private final static String XML_TIPO = XML_ROOT + "/DATOS_PAGO/TIPO";
	private final static String XML_ESTADO = XML_ROOT + "/DATOS_PAGO/ESTADO";
	private final static String XML_MODELO = XML_ROOT + "/DATOS_PAGO/MODELO";
	private final static String XML_ID_TASA = XML_ROOT + "/DATOS_PAGO/ID_TASA";
	private final static String XML_IMPORTE = XML_ROOT + "/DATOS_PAGO/IMPORTE";
	private final static String XML_FECHA_DEVENGO = XML_ROOT + "/DATOS_PAGO/FECHA_DEVENGO";
	private final static String XML_CONCEPTO = XML_ROOT + "/DATOS_PAGO/CONCEPTO";	
	private final static String XML_NIF = XML_ROOT + "/DATOS_PAGO/DECLARANTE/NIF";
	private final static String XML_NOMBRE = XML_ROOT + "/DATOS_PAGO/DECLARANTE/NOMBRE";	
	private final static String XML_DOMICILIO_SIGLAS = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/SIGLAS";
	private final static String XML_DOMICILIO_VIA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/VIA";
	private final static String XML_DOMICILIO_NUMERO = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/NUMERO";
	private final static String XML_DOMICILIO_LETRA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/LETRA";
	private final static String XML_DOMICILIO_ESCALERA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/ESCALERA";
	private final static String XML_DOMICILIO_PISO = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/PISO";
	private final static String XML_DOMICILIO_PUERTA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/DOMICILIO/PUERTA";
	private final static String XML_TELEFONO = XML_ROOT + "/DATOS_PAGO/DECLARANTE/TELEFONO";
	private final static String XML_FAX = XML_ROOT + "/DATOS_PAGO/DECLARANTE/FAX";
	private final static String XML_LOCALIDAD = XML_ROOT + "/DATOS_PAGO/DECLARANTE/LOCALIDAD";
	private final static String XML_PROVINCIA = XML_ROOT + "/DATOS_PAGO/DECLARANTE/PROVINCIA";
	private final static String XML_CODIGOPOSTAL = XML_ROOT + "/DATOS_PAGO/DECLARANTE/CODIGOPOSTAL";
	private final static String XML_NUMERO_DUI = XML_ROOT + "/DATOS_PASARELA/NUMERO_DUI";
	private final static String XML_LOCALIZADOR = XML_ROOT + "/DATOS_PASARELA/LOCALIZADOR";
	private final static String XML_FECHA_PAGO = XML_ROOT + "/DATOS_PASARELA/FECHA_PAGO";
	private final static String XML_JUSTIFICANTE = XML_ROOT + "/DATOS_PASARELA/JUSTIFICANTE";
	
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
					" modelo=\"" + ConstantesRDS.MODELO_PAGO + "\" version=\"" + ConstantesRDS.VERSION_PAGO + "\" " + xml.substring(pos + root.length());
		}else{
			xml = "<?xml version=\"1.0\" encoding=\""+ ConstantesXML.ENCODING +"\"?>\n<"+ root +
					" modelo=\"" + ConstantesRDS.MODELO_PAGO + "\" version=\"" + ConstantesRDS.VERSION_PAGO + "\"></" + root + ">";					
		}		
		return xml;
	}
		
	/**
	 * Genera Hashmap con las keys necesarias para poder generar el XML de pago
	 * @return
	 */
	private HashMapIterable toHashMapIterable() throws Exception{			
		
		HashMapIterable map = new HashMapIterable();	
		SimpleDateFormat sdf = new SimpleDateFormat( DatosPago.FORMATO_FECHAS );
		
		Nodo nodo = new Nodo(XML_TIPO, Character.toString(this.getTipoPago()));		
		nodo.setXpath(XML_TIPO);
		map.put(nodo.getXpath(),nodo);
				
		nodo = new Nodo(XML_ESTADO, Character.toString(this.getEstado()));		
		nodo.setXpath(XML_ESTADO);
		map.put(nodo.getXpath(),nodo);
		
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
			justifB64 = new String(Base64.encodeBase64(this.getJustificantePago().getBytes(ConstantesXML.ENCODING)),ConstantesXML.ENCODING);			
		}
		nodo = new Nodo(XML_JUSTIFICANTE, justifB64);		
		nodo.setXpath(XML_JUSTIFICANTE);
		map.put(nodo.getXpath(),nodo);
		    	
		return map;		
	}
	
	/**
	 * Carga datos pago a partir de Hashmap con las keys del XML de pago
	 * @return
	 */
	private void fromHashMapIterable(HashMapIterable map) throws Exception{		
		SimpleDateFormat sdf = new SimpleDateFormat( DatosPago.FORMATO_FECHAS );
		Nodo nodo;
		this.setTipoPago( ((Nodo) map.get(XML_TIPO)).getValor().charAt(0));
		this.setEstado( ((Nodo) map.get(XML_ESTADO)).getValor().charAt(0));		
		this.setModelo(  map.get(XML_MODELO) != null? ((Nodo) map.get(XML_MODELO)).getValor():null);
		this.setIdTasa( map.get(XML_ID_TASA) != null? ((Nodo) map.get(XML_ID_TASA)).getValor():null);
		this.setImporte( map.get(XML_IMPORTE) != null? ((Nodo) map.get(XML_IMPORTE)).getValor():null);
		nodo = (Nodo) map.get(XML_FECHA_DEVENGO);
		if ( nodo != null){
			this.setFechaDevengo( StringUtils.isNotEmpty(nodo.getValor())?sdf.parse(nodo.getValor()):null);
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
			String justif =  new String(Base64.decodeBase64(justifB64.getBytes(ConstantesXML.ENCODING)),ConstantesXML.ENCODING);
			this.setJustificantePago(justif);
		}
						
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
	
}
