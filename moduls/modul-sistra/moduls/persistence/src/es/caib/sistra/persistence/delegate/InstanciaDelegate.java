package es.caib.sistra.persistence.delegate;

import java.util.Locale;
import java.util.Map;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.sistra.model.InstanciaBean;
import es.caib.sistra.model.RespuestaFront;
import es.caib.sistra.plugins.firma.FirmaIntf;

public interface InstanciaDelegate extends Delegate
{
	void create(String tramite, int version, char nivel, Locale idioma,Map parametrosInicio, String perfilAcceso, String nifEntidad) throws DelegateException;
	public InstanciaBean obtenerInstanciaBean()throws DelegateException;
	RespuestaFront informacionInicial() throws DelegateException;
	RespuestaFront obtenerTramitesPersistencia(String tramite,int version) throws DelegateException;
	RespuestaFront iniciarTramite() throws DelegateException;
	RespuestaFront cargarTramite(String idPersistencia) throws DelegateException;
	RespuestaFront pasoActual() throws DelegateException;
	RespuestaFront siguientePaso() throws DelegateException;
	RespuestaFront anteriorPaso() throws DelegateException;
	RespuestaFront irAPaso(int paso) throws DelegateException;
	RespuestaFront irAFormulario(String identificador,int instancia) throws DelegateException;
	RespuestaFront guardarFormulario(String identificador,int instancia,String datosAnteriores,String datosNuevos, boolean guardadoSinFinalizar) throws DelegateException;
	RespuestaFront borrarAnexo(String identificador,int instancia) throws DelegateException;
	RespuestaFront uploadAnexo(String identificador,int instancia,byte[] datosDocumento,String nomFichero,String extension, String descPersonalizada) throws DelegateException;
	RespuestaFront downloadAnexo(String identificador,int instancia) throws DelegateException;
	RespuestaFront anexarDocumento(String identificador,int instancia, String descPersonalizada,  FirmaIntf firma, boolean firmaDelegada) throws DelegateException;
	RespuestaFront irAPago(String identificador,int instancia, String urlRetorno, String urlMantenimientoSesion) throws DelegateException;
	RespuestaFront confirmarPago(String identificador,int instancia) throws DelegateException;
	RespuestaFront anularPago(String identificador, int instancia) throws DelegateException;
	void finalizarSesionPago(String identificador,int instancia) throws DelegateException;
	RespuestaFront borrarTramite() throws DelegateException;
	RespuestaFront registrarTramite(String asiento,FirmaIntf firma) throws DelegateException;
	RespuestaFront mostrarDocumento(String idDocumento, int instancia) throws DelegateException;
	DocumentoRDS recuperaInfoDocumento(String idDocumento, int instancia) throws DelegateException;
	RespuestaFront mostrarJustificante() throws DelegateException;
	RespuestaFront irAFirmarFormulario(String identificador,int instancia) throws DelegateException;
	RespuestaFront firmarFormulario(String identificador,int instancia,FirmaIntf firma, boolean firmaDelegada) throws DelegateException;
	RespuestaFront remitirFlujoTramitacion() throws DelegateException;
	RespuestaFront mostrarDocumentoConsulta( int numDoc ) throws DelegateException;
	public String obtenerUrlFin() throws DelegateException;
	public String obtenerIdPersistencia() throws DelegateException;
	public void habilitarNotificacion(boolean habilitarNotificacion, String emailAviso, String smsAviso) throws DelegateException;
	public void resetHabilitarNotificacion() throws DelegateException;
	public RespuestaFront mostrarFormularioDebug( String idDocumento, int instancia ) throws DelegateException;
	public RespuestaFront remitirDelegacionPresentacionTramite()  throws DelegateException;
	public RespuestaFront remitirDelegacionFirmaDocumentos()  throws DelegateException;
	public RespuestaFront finalizarTramite() throws DelegateException;
	public boolean verificarMovil(String smsCodigo) throws DelegateException;
	public void resetCodigoSmsVerificarMovil() throws DelegateException;
	public boolean isDebugEnabled() throws DelegateException;
	public RespuestaFront obtenerInfoTramite() throws DelegateException;

	void destroy();
}
