package es.caib.audita.model;

import java.io.Serializable;

public class InicioPortal implements Serializable
{
//	private int servicios;
//	private int serviciosTelematicos;
	private int accesosBuzon;
	private int serviciosActivosTelematicos;
//	private int serviciosActivos;
	private int nivelCertificados;
	private int nivelUsuario;
	private int nivelAnonimos;
	private int envioRegistro;
	private int envioBandeja;
	private int envioConsulta;
	private int documentoTipoPago;

	public int getAccesosBuzon() {
		return accesosBuzon;
	}
	public void setAccesosBuzon(int accesosBuzon) {
		this.accesosBuzon = accesosBuzon;
	}
//	public int getServicios() {
//		return servicios;
//	}
//	public void setServicios(int servicios) {
//		this.servicios = servicios;
//	}
//	public int getServiciosTelematicos() {
//		return serviciosTelematicos;
//	}
//	public void setServiciosTelematicos(int serviciosTelematicos) {
//		this.serviciosTelematicos = serviciosTelematicos;
//	}
	public int getDocumentoTipoPago() {
		return documentoTipoPago;
	}
	public void setDocumentoTipoPago(int documentoTipoPago) {
		this.documentoTipoPago = documentoTipoPago;
	}
	public int getEnvioBandeja() {
		return envioBandeja;
	}
	public void setEnvioBandeja(int envioBandeja) {
		this.envioBandeja = envioBandeja;
	}
	public int getEnvioConsulta() {
		return envioConsulta;
	}
	public void setEnvioConsulta(int envioConsulta) {
		this.envioConsulta = envioConsulta;
	}
	public int getEnvioRegistro() {
		return envioRegistro;
	}
	public void setEnvioRegistro(int envioRegistro) {
		this.envioRegistro = envioRegistro;
	}
	public int getNivelAnonimos() {
		return nivelAnonimos;
	}
	public void setNivelAnonimos(int nivelAnonimos) {
		this.nivelAnonimos = nivelAnonimos;
	}
	public int getNivelCertificados() {
		return nivelCertificados;
	}
	public void setNivelCertificados(int nivelCertificados) {
		this.nivelCertificados = nivelCertificados;
	}
	public int getNivelUsuario() {
		return nivelUsuario;
	}
	public void setNivelUsuario(int nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}
//	public int getServiciosActivos() {
//		return serviciosActivos;
//	}
//	public void setServiciosActivos(int serviciosActivos) {
//		this.serviciosActivos = serviciosActivos;
//	}
	public int getServiciosActivosTelematicos() {
		return serviciosActivosTelematicos;
	}
	public void setServiciosActivosTelematicos(int serviciosActivosTelematicos) {
		this.serviciosActivosTelematicos = serviciosActivosTelematicos;
	}

	
}