package es.caib.audita.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CuadroMandoInicio implements Serializable {
	
	private String ultimaActualizacion;
	private String historial;
	private InicioPortal portal = new InicioPortal();
	private InicioTramitacion tramitacion = new InicioTramitacion();
	private List detalleTramitados = new ArrayList();
	private List detalleAccedidos = new ArrayList();
	private List detalleUltimos = new ArrayList();
	public List getDetalleAccedidos() {
		return detalleAccedidos;
	}
	public void setDetalleAccedidos(List detalleAccedidos) {
		this.detalleAccedidos = detalleAccedidos;
	}
	public List getDetalleTramitados() {
		return detalleTramitados;
	}
	public void setDetalleTramitados(List detalleTramitados) {
		this.detalleTramitados = detalleTramitados;
	}
	public List getDetalleUltimos() {
		return detalleUltimos;
	}
	public void setDetalleUltimos(List detalleUltimos) {
		this.detalleUltimos = detalleUltimos;
	}
	public InicioPortal getPortal() {
		return portal;
	}
	public void setPortal(InicioPortal portal) {
		this.portal = portal;
	}
	public InicioTramitacion getTramitacion() {
		return tramitacion;
	}
	public void setTramitacion(InicioTramitacion tramitacion) {
		this.tramitacion = tramitacion;
	}
	public String getUltimaActualizacion() {
		return ultimaActualizacion;
	}
	public void setUltimaActualizacion(String ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}
	public void addDetalleTramitados(LineaDetalleTramitados tramitado)
	{
		this.detalleTramitados.add(tramitado);
	}
	public void addDetalleAccedidos(LineaDetalleAccedidos accedido)
	{
		this.detalleAccedidos.add(accedido);
	}
	public void addDetalleUltimos(LineaDetalleUltimos ultimo)
	{
		this.detalleUltimos.add(ultimo);
	}
	public String getHistorial() {
		return historial;
	}
	public void setHistorial(String historial) {
		this.historial = historial;
	}
	
	
}
