package es.caib.bantel.front.form;

import org.apache.struts.validator.ValidatorForm;

import es.caib.util.DataUtil;

public class BusquedaExpedientesForm extends ValidatorForm
{
	private static final char TODOS = 'T';
	
	private String fechaDesde;
	private String fechaHasta;	
	private String usuarioNif;
	private String identificadorProcedimiento = "-1";
	private String numeroEntrada = "";
	private int pagina;
	private int longitudPagina = 10;
	
	public BusquedaExpedientesForm() {
		super();
		// Establecemos por defecto fecha desde a 3 meses antes
		fechaDesde = DataUtil.fechaSuma(2, -3, DataUtil.avui(), DataUtil.DEFAULT_FORMAT);		
	}
	
	public String getUsuarioNif() {
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif) {
		this.usuarioNif = usuarioNif;
	}
	public String getIdentificadorProcedimiento() {
		return identificadorProcedimiento;
	}
	public void setIdentificadorProcedimiento(String identificadorProcedimiento) {
		this.identificadorProcedimiento = identificadorProcedimiento;
	}
	public String getNumeroEntrada() {
		return numeroEntrada;
	}
	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public int getLongitudPagina() {
		return longitudPagina;
	}

	public void setLongitudPagina(int longitudPagina) {
		this.longitudPagina = longitudPagina;
	}
	
	
}
