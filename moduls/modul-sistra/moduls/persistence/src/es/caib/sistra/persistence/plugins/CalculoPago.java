package es.caib.sistra.persistence.plugins;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.plugins.PluginFactory;

/**
 * Permite establecer los datos de un pago en un script
 */
public class CalculoPago {

	public CalculoPago(String pIdPluginPago) {
		super();
		idPluginPago = pIdPluginPago;
	}
	private static Log log = LogFactory.getLog(CalculoPago.class);
	
	private Map parametrosCalculoTasa = new HashMap();
	
	public static final String FORMATO_FECHA_DEVENGO = "yyyyMMdd";
	
	private String idPluginPago;
	
	private String organoEmisor;
	private String modelo;
	private String idTasa;
	private String concepto;
	private String fechaDevengo;
	private String importe;
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
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(String fecha) {
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
	public String getIdTasa() {
		return idTasa;
	}
	public void setIdTasa(String idTasa) {
		this.idTasa = idTasa;
	}
	
	/**
	 * Función que permite establecer parámetros para el cálculo de la tasa
	 * @param nombreParametro Nombre parámetro
	 * @param valorParametro Valor parámetro
	 * 
	 */
	public void establecerParametroCalculoTasa(String nombreParametro,String valorParametro){
		parametrosCalculoTasa.put(nombreParametro,valorParametro);
	}
	
	/**
	 * Función que permite calcular el importe de una tasa
	 * @param idTasa
	 * @return -1 si error
	 */
	public long consultarImporteTasa(String idTasa){
		try{					
			long res = PluginFactory.getInstance().getPluginPagos(idPluginPago).consultarImporteTasa(idTasa,parametrosCalculoTasa);						
			return res;			
		}catch(Exception ex){
			log.error("Excepción consultando tasa: " + ex.getMessage(),ex);
			return -1;
		}		
	}
	public static void setLog(Log log) {
		CalculoPago.log = log;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public void setDomicilioEscalera(String domicilioEscalera) {
		this.domicilioEscalera = domicilioEscalera;
	}
	public void setDomicilioLetra(String domicilioLetra) {
		this.domicilioLetra = domicilioLetra;
	}
	public void setDomicilioNumero(String domicilioNumero) {
		this.domicilioNumero = domicilioNumero;
	}
	public void setDomicilioPiso(String domicilioPiso) {
		this.domicilioPiso = domicilioPiso;
	}
	public void setDomicilioPuerta(String domicilioPuerta) {
		this.domicilioPuerta = domicilioPuerta;
	}
	public void setDomicilioSiglas(String domicilioSiglas) {
		this.domicilioSiglas = domicilioSiglas;
	}
	public void setDomicilioVia(String domicilioVia) {
		this.domicilioVia = domicilioVia;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public static Log getLog() {
		return log;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public String getDomicilioEscalera() {
		return domicilioEscalera;
	}
	public String getDomicilioLetra() {
		return domicilioLetra;
	}
	public String getDomicilioNumero() {
		return domicilioNumero;
	}
	public String getDomicilioPiso() {
		return domicilioPiso;
	}
	public String getDomicilioPuerta() {
		return domicilioPuerta;
	}
	public String getDomicilioSiglas() {
		return domicilioSiglas;
	}
	public String getDomicilioVia() {
		return domicilioVia;
	}
	public String getFax() {
		return fax;
	}
	public String getLocalidad() {
		return localidad;
	}
	public String getNif() {
		return nif;
	}
	public String getNombre() {
		return nombre;
	}
	public String getProvincia() {
		return provincia;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getOrganoEmisor() {
		return organoEmisor;
	}
	public void setOrganoEmisor(String organoEmisor) {
		this.organoEmisor = organoEmisor;
	}
	
		 

}
