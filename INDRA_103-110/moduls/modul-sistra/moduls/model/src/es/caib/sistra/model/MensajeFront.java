package es.caib.sistra.model;

import java.io.Serializable;

public class MensajeFront implements Serializable{
	// Tipos de mensaje
	// Error no continuable
	public static final int TIPO_ERROR=1;
	// Error que permite al usuario la posibilidad de continuar volviendo al paso actual
	public static final int TIPO_ERROR_CONTINUABLE=2;
	// Error que implica la introducción de datos incorrectos por parte del usuario
	public static final int TIPO_WARNING=3;
	// Error que implica la introducción de datos incorrectos por parte del usuario
	public static final int TIPO_INFO=4;
	
	// Mensajes predefinidos
	public static final String MENSAJE_ERRORDESCONOCIDO = "mensaje.error_desconocido";
	public static final String MENSAJE_TRAMITEBORRADO = "mensaje.tramite_borrado";	
	public static final String MENSAJE_TRAMITEINACTIVO = "mensaje.tramite_inactivo";
	public static final String MENSAJE_ERRORSCRIPT = "mensaje.error_script";
	public static final String MENSAJE_EXCEPCIONSCRIPT = "mensaje.excepcion_script";
	public static final String MENSAJE_NOEXISTENIVEL = "mensaje.no_existe_nivel";
	public static final String MENSAJE_DATOSPAGOINCORRECTOS = "mensaje.datos_pago_incorrectos";
	public static final String MENSAJE_ANULARPAGOTELEMATICOREALIZADO = "mensaje.anular_pago_telematico_realizado";
	public static final String MENSAJE_ANULARPAGOPENDIENTECONFIRMAR = "mensaje.anular_pago_pendiente_confirmar";
	public static final String MENSAJE_PAGOSINICIADOS = "mensaje.pagos_iniciados";
	public static final String MENSAJE_ASIENTOINCORRECTO = "mensaje.asiento_incorrecto";
	public static final String MENSAJE_ASIENTOFIRMANTENOCOINCIDE = "mensaje.asiento_firmante_no_coincide";
	public static final String MENSAJE_ANEXOEXTENSIONNOPERMITIDA = "mensaje.upload_anexo.extension_no_permitida";
	public static final String MENSAJE_ANEXOERRORCONVERSION = "mensaje.upload_anexo.error_conversion";
	public static final String MENSAJE_ANEXOVERIFICARCONVERSION = "mensaje.upload_anexo.verificar_conversion";
	public static final String MENSAJE_ANEXOTAMANYONOPERMITIDO = "mensaje.upload_anexo.tamanyo_no_permitido";
	public static final String MENSAJE_ANEXODATOSNULOS = "mensaje.upload_anexo.sin_datos";
	public static final String MENSAJE_PLAZONOABIERTO = "mensaje.plazo.no_abierto";
	public static final String MENSAJE_PLAZOCERRADO = "mensaje.plazo.cerrado";
	public static final String MENSAJE_NOEXISTETRAMITE = "mensaje.cargapersistente.no_existe_tramite";
	public static final String MENSAJE_DIFERENTENIVEL = "mensaje.cargapersistente.diferente_nivel";	
	public static final String MENSAJE_TRAMITETERMINADO = "mensaje.cargapersistente.tramite_terminado";
	public static final String MENSAJE_TRAMITEPENDIENTECONFIRMACION = "mensaje.cargapersistente.tramite_pendiente_confirmacion";
	public static final String MENSAJE_ERRORINICIOPAGO = "mensaje.error_pagos.inicio_pago";
	public static final String MENSAJE_ERRORCOMPROBARPAGO = "mensaje.error_pagos.comprobar_pago";
	public static final String MENSAJE_ERRORNOPAGADO = "mensaje.error_pagos.no_pagado";
	public static final String MENSAJE_ERRORJUSTIFICANTEPAGO = "mensaje.error_pagos.justificante";
	public static final String MENSAJE_ERRORSESIONPAGONOEXISTE = "mensaje.error_pagos.sesion_no_existe";
	public static final String MENSAJE_ERRORMODIFICACIONFORMPORPAGO = "mensaje.error_pagos.no_modif_form_por_pago";
	public static final String MENSAJE_ERRORMODIFICACIONPORFLUJO = "mensaje.error.no_modif_por_flujo";
	public static final String MENSAJE_PAGADO = "mensaje.pagos.pago_confirmado";
	public static final String MENSAJE_ERROR_REGISTRO="mensaje.registro.error";
	public static final String MENSAJE_ERROR_FIRMA="mensaje.error.firma";
	public static final String MENSAJE_ERROR_FIRMANTE_INCORRECTO="mensaje.error.firma.firmanteNoValido";
	public static final String MENSAJE_ERROR_DOCUMENTO_NO_FIRMADO="mensaje.error.firma.documentoNoFirmado";
	public static final String MENSAJE_ERROR_FLUJO_NO_USUARIO_SEYCON="mensaje.error.firma.flujoNoUsuarioSeycon";
	public static final String MENSAJE_FLUJO_TRAMITE_ENVIADO="mensaje.firma.flujoTramiteEnviado";	
			
		
	private String mensaje;	
	private int tipo=TIPO_ERROR;
	private String mensajeExcepcion;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getMensajeExcepcion() {
		return mensajeExcepcion;
	}
	public void setMensajeExcepcion(String mensajeExcepcion) {
		this.mensajeExcepcion = mensajeExcepcion;
	}
	
	
}
