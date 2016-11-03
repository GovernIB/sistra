package es.caib.zonaper.front.form;

import org.apache.struts.validator.ValidatorForm;

public class ActualizarAlertasForm extends ValidatorForm
{	
	
	// Avisos de tramitacion
	/**
	 * Indica si se habilita de forma general que se avise cuando se actualize un expediente (aviso o notificacion)
	 */
	private boolean habilitarAvisosExpediente;

	/**
	 * Indica si se habilita de forma general que se avise cuando se actualize un expediente (aviso o notificacion)
	 */
	public boolean getHabilitarAvisosExpediente() {
		return habilitarAvisosExpediente;
	}

	/**
	 * Indica si se habilita de forma general que se avise cuando se actualize un expediente (aviso o notificacion)
	 */
	public void setHabilitarAvisosExpediente(boolean habilitarAvisosExpediente) {
		this.habilitarAvisosExpediente = habilitarAvisosExpediente;
	}
    
	// Avisos de portal (POR DEFINIR)
	
	
}
