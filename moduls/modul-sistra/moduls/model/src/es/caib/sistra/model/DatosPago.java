package es.caib.sistra.model;

import java.io.Serializable;
import java.util.Date;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.xml.pago.XmlDatosPago;


/**
 * Modeliza el XML de datos de un pago
 */
public class DatosPago implements Serializable{

	private XmlDatosPago xmlPago;
	
	public DatosPago(){
		xmlPago = new XmlDatosPago();
		xmlPago.setModeloRDSPago(ConstantesRDS.MODELO_PAGO);
		xmlPago.setVersionRDSPago(ConstantesRDS.VERSION_PAGO);		
	}
	
	
	/**
	 * Obtiene los datos en bytes
	 * @return byte[]
	 */
	public byte[] getBytes() throws Exception{
		return xmlPago.getBytes();	
	}
	
	/**
	 * Establece los datos a partir de una array de bytes
	 * @param datos
	 */
	public void setBytes(byte[] datos) throws Exception {
		xmlPago.setBytes(datos);			
	}
	
	/**
	 * Establece los datos a partir de un String
	 * @param datos
	 */
	public void setString(String datos) throws Exception{
		xmlPago.setString(datos);		
	}
	
	/**
	 * Obtiene datos como un String
	 * @param datos
	 */
	public String getString() throws Exception{
		return xmlPago.getString();
	}
		
	/**
	 * Obtiene valor del campo en formato referencia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getValorCampo(String referenciaCampo) throws Exception{
		return xmlPago.getValorCampo(referenciaCampo);					
	}
	
	/**
	 * Convierte referencia campo del tipo seccion1.campo1 a expresión XPath /instancia/seccion1/campo1 
	 * @param referenciaCampo
	 * @return
	 */
	public static String referenciaXPath(String referenciaCampo){
		return XmlDatosPago.referenciaXPath(referenciaCampo);		
	}
	
	// --------- GETTERS / SETTERS  ------------------------------	
	public String getCodigoEntidad() {
		return xmlPago.getCodigoEntidad();
	}
	public void setCodigoEntidad(String codigo) {
		xmlPago.setCodigoEntidad(codigo);
	}
	public String getIdTasa() {
		return xmlPago.getIdTasa();
	}
	public void setIdTasa(String codigo) {
		xmlPago.setIdTasa(codigo);
	}
	public String getConcepto() {
		return xmlPago.getConcepto();		
	}
	public void setConcepto(String concepto) {
		xmlPago.setConcepto(concepto);
	}
	public Date getFechaDevengo() {
		return xmlPago.getFechaDevengo();		
	}
	public void setFechaDevengo(Date fecha) {
		xmlPago.setFechaDevengo(fecha);		
	}
	public String getImporte() {
		return xmlPago.getImporte();
	}
	public void setImporte(String importe) {
		xmlPago.setImporte(importe);
	}
	public String getModelo() {
		return xmlPago.getModelo();
	}
	public void setModelo(String modelo) {
		xmlPago.setModelo(modelo);
	}
	public String getNumeroDUI() {
		return xmlPago.getNumeroDUI();
	}
	public void setNumeroDUI(String referenciaPagos) {
		xmlPago.setNumeroDUI(referenciaPagos);
	}
	public String getLocalizador() {
		return xmlPago.getLocalizador();
	}
	public void setLocalizador(String referenciaSistra) {
		xmlPago.setLocalizador(referenciaSistra);
	}
	
	public char getTipoPago() {
		return xmlPago.getTipoPago();
	}
	public void setTipoPago(char tipoPago) {
		xmlPago.setTipoPago(tipoPago);
	}
	public Date getFechaPago() {
		return xmlPago.getFechaPago();
	}
	public void setFechaPago(Date fechaPago) {
		xmlPago.setFechaPago(fechaPago);
	}
	public String getJustificantePago() {
		return xmlPago.getJustificantePago();
	}
	public void setJustificantePago(String justificantePago) {
		xmlPago.setJustificantePago(justificantePago);
	}

	public char getEstado() {
		return xmlPago.getEstado();
	}

	public void setEstado(char estado) {
		xmlPago.setEstado(estado);
	}

	public String getCodigoPostal() {
		return xmlPago.getCodigoPostal();
	}

	public void setCodigoPostal(String codigoPostal) {
		xmlPago.setCodigoPostal(codigoPostal);
	}

	public String getDomicilioEscalera() {
		return xmlPago.getDomicilioEscalera();
	}

	public void setDomicilioEscalera(String domicilioEscalera) {
		xmlPago.setDomicilioEscalera(domicilioEscalera);
	}

	public String getDomicilioLetra() {
		return xmlPago.getDomicilioLetra();
	}

	public void setDomicilioLetra(String domicilioLetra) {
		xmlPago.setDomicilioLetra(domicilioLetra);
	}

	public String getDomicilioNumero() {
		return xmlPago.getDomicilioNumero();
	}

	public void setDomicilioNumero(String domicilioNumero) {
		xmlPago.setDomicilioNumero(domicilioNumero);
	}

	public String getDomicilioPiso() {
		return xmlPago.getDomicilioPiso();
	}

	public void setDomicilioPiso(String domicilioPiso) {
		xmlPago.setDomicilioPiso(domicilioPiso);
	}

	public String getDomicilioPuerta() {
		return xmlPago.getDomicilioPuerta();
	}

	public void setDomicilioPuerta(String domicilioPuerta) {
		xmlPago.setDomicilioPuerta(domicilioPuerta);
	}

	public String getDomicilioSiglas() {
		return xmlPago.getDomicilioSiglas();
	}

	public void setDomicilioSiglas(String domicilioSiglas) {
		xmlPago.setDomicilioSiglas(domicilioSiglas);
	}

	public String getDomicilioVia() {
		return xmlPago.getDomicilioVia();
	}

	public void setDomicilioVia(String domicilioVia) {
		xmlPago.setDomicilioVia(domicilioVia);
	}

	public String getFax() {
		return xmlPago.getFax();
	}

	public void setFax(String fax) {
		xmlPago.setFax(fax);
	}

	public String getLocalidad() {
		return xmlPago.getLocalidad();
	}

	public void setLocalidad(String localidad) {
		xmlPago.setLocalidad(localidad);
	}

	public String getNif() {
		return xmlPago.getNif();
	}

	public void setNif(String nif) {
		xmlPago.setNif(nif);
	}

	public String getNombre() {
		return xmlPago.getNombre();
	}

	public void setNombre(String nombre) {
		xmlPago.setNombre(nombre);
	}

	public String getProvincia() {
		return xmlPago.getProvincia();
	}

	public void setProvincia(String provincia) {
		xmlPago.setProvincia(provincia);
	}

	public String getTelefono() {
		return xmlPago.getTelefono();
	}

	public void setTelefono(String telefono) {
		xmlPago.setTelefono(telefono);
	}

	public String getPluginId() {
		return xmlPago.getPluginId();
	}

	public void setPluginId(String pluginId) {
		xmlPago.setPluginId(pluginId);
		xmlPago.setPluginDefecto(this.getPluginId() == null || PluginFactory.ID_PLUGIN_DEFECTO.equals(this.getPluginId()));
	}

	public String getOrganoEmisor() {
		return xmlPago.getOrganoEmisor();
	}

	public void setOrganoEmisor(String organoEmisor) {
		xmlPago.setOrganoEmisor(organoEmisor);
	}
	
	public Date getFechaLimitePago() {
		return xmlPago.getFechaLimitePago();
	}

	public void setFechaLimitePago(Date fecha) {
		xmlPago.setFechaLimitePago(fecha);
	}
	
}
