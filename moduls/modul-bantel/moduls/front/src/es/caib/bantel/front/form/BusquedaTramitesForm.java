package es.caib.bantel.front.form;

import org.apache.struts.validator.ValidatorForm;

import es.caib.util.DataUtil;

public class BusquedaTramitesForm extends ValidatorForm
{
	private static final char TODOS = 'T';
		
	
	private String fechaDesde;
	private String fechaHasta;
	private String usuarioNif;
	private String usuarioNombre;
	private char tipo = TODOS;
	private char procesada = TODOS;
	private char nivelAutenticacion = TODOS;
	private String identificadorProcedimiento = "-1";
	private String identificadorTramite;
	private int pagina;
	private String numeroEntrada = "";
	private int longitudPagina = 10;
	
	public BusquedaTramitesForm() {
		super();
		// Establecemos por defecto fecha desde a 3 meses antes
		fechaDesde = DataUtil.fechaSuma(2, -3, DataUtil.avui(), DataUtil.DEFAULT_FORMAT);		
	}
	
	public String getIdentificadorProcedimiento()
	{
		return identificadorProcedimiento;
	}
	public void setIdentificadorProcedimiento(String idProc)
	{
		this.identificadorProcedimiento = idProc;
	}
	
	public char getNivelAutenticacion()
	{
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion)
	{
		this.nivelAutenticacion = nivelAutenticacion;
	}
	public int getPagina()
	{
		return pagina;
	}
	public void setPagina(int pagina)
	{
		this.pagina = pagina;
	}
	public char getProcesada()
	{
		return procesada;
	}
	public void setProcesada(char procesada)
	{
		this.procesada = procesada;
	}
	public char getTipo()
	{
		return tipo;
	}
	public void setTipo(char tipo)
	{
		this.tipo = tipo;
	}
	public String getUsuarioNif()
	{
		return usuarioNif;
	}
	public void setUsuarioNif(String usuarioNif)
	{
		this.usuarioNif = usuarioNif;
	}
	public String getUsuarioNombre()
	{
		return usuarioNombre;
	}
	public void setUsuarioNombre(String usuarioNombre)
	{
		this.usuarioNombre = usuarioNombre;
	}
	public String getNumeroEntrada() {
		return numeroEntrada;
	}
	public void setNumeroEntrada(String numeroEntrada) {
		this.numeroEntrada = numeroEntrada;
	}
	public String getIdentificadorTramite() {
		return identificadorTramite;
	}
	public void setIdentificadorTramite(String identificadorTramite) {
		this.identificadorTramite = identificadorTramite;
	}
	public int getLongitudPagina() {
		return longitudPagina;
	}
	public void setLongitudPagina(int longitudPagina) {
		this.longitudPagina = longitudPagina;
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
	
}
