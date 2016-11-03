package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Filtro para buscar expedientes.
 * 
 */
public class FiltroBusquedaExpedientePAD implements Serializable {
	
	/**
	 * Lista de ids procedimiento.
	 */
	private List identificadorProcedimientos;
	/**
	 * Nif representante.
	 */
	private String nifRepresentante;	
	/**
	 * Año.
	 */
	private int anyo;
	/**
	 * Mes (1 - 12).
	 */
	private int mes;
	/**
	 * Id expediente.
	 */
	private String idExpediente = "";
	/**
	 * Numero entrada que crea el expediente.
	 */
	private String numeroEntradaBTE;
	/**
	 * Fecha desde (dd/mm/yyyy).
	 */
	private Date fechaDesde;
	/**
	 * Fecha hasta (dd/mm/yyyy).
	 */
	private Date fechaHasta;
	/**
	 * Estado.
	 */
	private String estado = "";
	
	public List getIdentificadorProcedimientos() {
		return identificadorProcedimientos;
	}
	public void setIdentificadorProcedimientos(List identificadorProcedimiento) {
		this.identificadorProcedimientos = identificadorProcedimiento;
	}
	public String getNifRepresentante() {
		return nifRepresentante;
	}
	public void setNifRepresentante(String nifRepresentante) {
		this.nifRepresentante = nifRepresentante;
	}
	public int getAnyo() {
		return anyo;
	}
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getNumeroEntradaBTE() {
		return numeroEntradaBTE;
	}
	public void setNumeroEntradaBTE(String numeroEntrada) {
		this.numeroEntradaBTE = numeroEntrada;
	}
	public Date getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public Date getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String numeroExpediente) {
		this.idExpediente = numeroExpediente;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
