package es.caib.bantel.model;

import java.io.Serializable;
import java.util.Date;

public class CriteriosBusquedaTramite implements Serializable
{
	public static final char TODOS = 'T';
	
	private int anyo;
	private int mes;
	private String usuarioNif;
	private String usuarioNombre;
	private char tipo = TODOS;
	private char procesada = TODOS;
	private char nivelAutenticacion = TODOS;
	private String identificadorProcedimiento;
	private String identificadorTramite;
	private String numeroEntrada;
	/**
	 * Entradas con fecha entrada mayor a esta fecha.
	 */
	private Date fechaEntradaMinimo;
	/**
	 * Entradas con fecha entrada menor a esta fecha. 
	 */
	private Date fechaEntradaMaximo;
	/**
	 * Entradas con fecha inicio procesamiento menor a la fecha. 
	 */
	private Date fechaInicioProcesamientoMaximo;
		
	public String getIdentificadorProcedimiento()
	{
		return identificadorProcedimiento;
	}
	public void setIdentificadorProcedimiento(String idProc)
	{
		this.identificadorProcedimiento = idProc;
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
	public void setAnyo(int anyo)
	{
		this.anyo = anyo;
	}
	public void setMes(int mes)
	{
		this.mes = mes;
	}
	public int getAnyo()
	{
		return anyo;
	}
	public int getMes()
	{
		return mes;
	}
	public char getNivelAutenticacion()
	{
		return nivelAutenticacion;
	}
	public void setNivelAutenticacion(char nivelAutenticacion)
	{
		this.nivelAutenticacion = nivelAutenticacion;
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
	public Date getFechaEntradaMaximo() {
		return fechaEntradaMaximo;
	}
	public void setFechaEntradaMaximo(Date fechaFin) {
		this.fechaEntradaMaximo = fechaFin;
	}
	public Date getFechaEntradaMinimo() {
		return fechaEntradaMinimo;
	}
	public void setFechaEntradaMinimo(Date fechaInicio) {
		this.fechaEntradaMinimo = fechaInicio;
	}
	public Date getFechaInicioProcesamientoMaximo() {
		return fechaInicioProcesamientoMaximo;
	}
	public void setFechaInicioProcesamientoMaximo(
			Date fechaInicioProcesamientoMaximo) {
		this.fechaInicioProcesamientoMaximo = fechaInicioProcesamientoMaximo;
	}	
}
