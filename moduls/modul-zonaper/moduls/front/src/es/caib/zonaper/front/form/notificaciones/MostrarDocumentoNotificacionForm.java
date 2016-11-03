package es.caib.zonaper.front.form.notificaciones;

import es.caib.zonaper.front.form.InitForm;

public class MostrarDocumentoNotificacionForm extends InitForm
{
	private long codigoNotificacion;
	private long codigo;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public long getCodigoNotificacion() {
		return codigoNotificacion;
	}

	public void setCodigoNotificacion(long codigoNotificacion) {
		this.codigoNotificacion = codigoNotificacion;
	}
	
	
	
}
