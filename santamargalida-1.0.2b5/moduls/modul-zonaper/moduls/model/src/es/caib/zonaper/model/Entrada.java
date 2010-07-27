package es.caib.zonaper.model;

import java.util.Date;
import java.util.Set;

public interface Entrada extends ElementoExpedienteItf
{
	public String getDescripcionTramite();
	
	public Date getFecha();
	
	public Date getFechaConfirmacion();

	public String getNumeroRegistro();
	
	public String getNumeroPreregistro();
	
	public String getClaveRdsAsiento();
	
	public String getClaveRdsJustificante();

	public Long getCodigo();

	public long getCodigoRdsAsiento();
	
	public long getCodigoRdsJustificante();

	public Set getDocumentos();
	
	public char getTipo();
	
	public String getIdPersistencia();
	
	public String getIdioma();
	
	public String getAvisoEmail();

	public String getAvisoSMS();
		
	public String getHabilitarAvisos();
	
	public String getHabilitarNotificacionTelematica();
	
	public String getTramite();
	
	public Integer getVersion();
	
}