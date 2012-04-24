package es.caib.sistra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import es.caib.zonaper.modelInterfaz.PersonaPAD;

/**
 * Clase que se utiliza para pasar al front la 
 * información del trámite
 */
public class TramiteFront  implements Serializable{
	
	/**
	 * Modelo del trámite
	 */
	private String modelo;
	
	/**
	 * Versión del trámite
	 */
	private int version;
	
	/**
	 * Datos de la sesión
	 */
	private DatosSesion datosSesion;	
	/**
	 * Descripcion del trámite
	 */
	private String descripcion;
	/**
	 * Identificador de persistencia (deberá mostrarse para anónimo)
	 */
	private String idPersistencia;
	/**
	 * Pasos de tramitación
	 */
	private ArrayList pasos;
	/**
	 * Indice del paso actual
	 */
	private int pasoActual;
	/**
	 * Tipo de circuito de tramitación (según configuración trámite)0: Telemático (T) / Presencial (P) / Depende (D)
	 */
	private char tipoTramitacion;
	/**
	 * Para el tipo de circuito de tramitación dependiente se calcula esta dependencia según los datos
	 * actuales: Telemático (T) / Presencial (P) / Depende (D)
	 */
	private char tipoTramitacionDependiente;
	/**
	 * Permite flujo de tramitacion 
	 */
	private boolean flujoTramitacion=false;
	/**
	 * Pasar a Nif Flujo: Si tiene valor indica que el trámite esta pendiente de pasar a ese nif 
	 */
	private String flujoTramitacionNif;
	/**
	 * Datos de la persona que actualmente tiene el flujo del trámite
	 */
	private PersonaPAD flujoTramitacionDatosPersonaActual;
	/**
	 * Datos de la persona que inició el trámite
	 */
	private PersonaPAD flujoTramitacionDatosPersonaIniciador;
	/**
	 * En caso de delegacion, indica si se puede remitir el tramite a la bandeja de firma para que se firmen
	 * los documentos pendientes
	 */
	private boolean remitirDelegacionFirma=false;
	/**
	 * En caso de delegacion, indica si hay que remitir el tramite para su presentacion
	 */
	private boolean remitirDelegacionPresentacion=false;
	/**
	 * Descargar plantillas 
	 */
	private boolean descargarPlantillas=false;
	/**
	 * Instrucciones inicio del trámite 
	 */
	private String informacionInicio;
	/**
	 * Instrucciones finalización del trámite 
	 */
	private String instruccionesFin;
	/**
	 * Instrucciones entrega del trámite 
	 */
	private String instruccionesEntrega;
	/**
	 * Mensaje fecha limite para entrega presencial 
	 */
	private String mensajeFechaLimiteEntregaPresencial;
	/**
	 * Indica si el trámite debe firmarse
	 */
	private boolean firmar;
	/**
	 * Indica si el trámite se debe registrar
	 */
	private boolean registrar;
	/**
	 * Indica si el trámite es de tipo consulta
	 */
	private boolean consultar;
	/**
	 * Indica si el trámite es de tipo asistente de rellenado
	 */
	private boolean asistente;
	/**
	 * Dias de persistencia
	 */
	private int diasPersistencia;
	/**
	 * Indica si se deben compulsar documentos (S/N/D)
	 */
	private char compulsarDocumentos='N';
	/**
	 * Lista de formularios
	 */
	private ArrayList formularios=new ArrayList();
	/**
	 * Lista de pagos
	 */
	private ArrayList pagos=new ArrayList();
	/**
	 * Lista de anexos
	 */
	private ArrayList anexos=new ArrayList();
	
	/**
	 * Indica si es un trámite con circuito reducido
	 */
	private boolean circuitoReducido = false;
	
	/**
	 * Indica si tras enviar un trámite se redirige a la url de finalización sin mostrar pantalla justificante
	 */
	private boolean redireccionFin = false;
	
	/**
	 * Plazo inicio presentacion tramite
	 */
	private Date fechaInicioPlazo=null;
	
	/**
	 * Plazo fin presentacion tramite
	 */
	private Date fechaFinPlazo=null;
	
	/**
	 * Tag cuaderno de carga al que pertenece
	 */
	private String tagCuadernoCarga;
	
	/**
	 * Map con los mensajes genéricos de plataforma
	 */
	private Map mensajesPlataforma = new HashMap();
	
	/**
	 * Fecha de exportación del xml del cual se ha importado
	 */
	private Date fechaExportacion;
	
	/**
	 * Indica el trámite permite notificación telemática ( N No / S Si  / O Obligatoria)
	 */
	private String habilitarNotificacionTelematica;
	
	/**
	 * Indica la seleccion del ciudadano en caso de que el trámite permita notificación telemática
	 */
	private Boolean seleccionNotificacionTelematica;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getDiasPersistencia() {
		return diasPersistencia;
	}
	public void setDiasPersistencia(int diasPersistencia) {
		this.diasPersistencia = diasPersistencia;
	}
	
	public boolean getFirmar() {
		return firmar;
	}
	public void setFirmar(boolean firmar) {
		this.firmar = firmar;
	}
	public String getIdPersistencia() {
		return idPersistencia;
	}
	public void setIdPersistencia(String idPersistencia) {
		this.idPersistencia = idPersistencia;
	}
	public ArrayList getPasos() {
		return pasos;
	}
	public void setPasos(ArrayList pasos) {
		this.pasos = pasos;
	}
	public char getTipoTramitacion() {
		return tipoTramitacion;
	}
	public void setTipoTramitacion(char tipoTramitacion) {
		this.tipoTramitacion = tipoTramitacion;
	}
	public ArrayList getAnexos() {
		return anexos;
	}
	public void setAnexos(ArrayList anexos) {
		this.anexos = anexos;
	}
	public ArrayList getFormularios() {
		return formularios;
	}
	public void setFormularios(ArrayList formularios) {
		this.formularios = formularios;
	}
	public ArrayList getPagos() {
		return pagos;
	}
	public void setPagos(ArrayList pagos) {
		this.pagos = pagos;
	}
	public int getPasoActual() {
		return pasoActual;
	}
	public void setPasoActual(int pasoActual) {
		this.pasoActual = pasoActual;
	}
	public String getInformacionInicio() {
		return informacionInicio;
	}
	public void setInformacionInicio(String informacionInicio) {
		this.informacionInicio = informacionInicio;
	}
	public String getInstruccionesEntrega() {
		return instruccionesEntrega;
	}
	public void setInstruccionesEntrega(String instruccionesEntrega) {
		this.instruccionesEntrega = instruccionesEntrega;
	}
	public String getInstruccionesFin() {
		return instruccionesFin;
	}
	public void setInstruccionesFin(String instruccionesFin) {
		this.instruccionesFin = instruccionesFin;
	}
	public boolean getRegistrar() {
		return registrar;
	}
	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}
	public boolean isDescargarPlantillas()
	{
		return descargarPlantillas;
	}
	public void setDescargarPlantillas(boolean descargarPlantillas)
	{
		this.descargarPlantillas = descargarPlantillas;
	}
	public char getCompulsarDocumentos() {
		return compulsarDocumentos;
	}
	public void setCompulsarDocumentos(char compulsarDocumentos) {
		this.compulsarDocumentos = compulsarDocumentos;
	}
	
	/**
	 * Indice del paso que representa el punto de no retorno.
	 * ( el indice del paso PASO_REGISTRAR ) 
	 */
	public int getPasoNoRetorno()
	{
		ArrayList pasos = this.getPasos();
		if ( pasos != null && pasos.size() != 0 )
		{
			for ( int i = 0; i < pasos.size(); i++ )
			{
				PasoTramitacion paso = ( PasoTramitacion ) pasos.get( i );
				if ( paso.getTipoPaso() == PasoTramitacion.PASO_REGISTRAR )
				{
					return i;
				}
			}
			return pasos.size();
		}
		return 0;
	}
	public DatosSesion getDatosSesion() {
		return datosSesion;
	}
	public void setDatosSesion(DatosSesion datosSesion) {
		this.datosSesion = datosSesion;
	}
	public char getTipoTramitacionDependiente() {
		return tipoTramitacionDependiente;
	}
	public void setTipoTramitacionDependiente(char tipoTramitacionDependiente) {
		this.tipoTramitacionDependiente = tipoTramitacionDependiente;
	}	
	
	public PasoTramitacion getPasoTramitacion()
	{
		ArrayList pasos = this.getPasos();
		if ( pasos!= null )
		{
			return ( PasoTramitacion ) pasos.get( this.getPasoActual() );
		}
		return null;
	}
	
	private DocumentoFront getDocumentoPorIdentificadorEInstancia( java.util.List lDocumentos, String identificador, int instancia )
	{
		for ( Iterator it = lDocumentos.iterator(); it.hasNext(); )
		{
			DocumentoFront documento = ( DocumentoFront ) it.next();
			if ( documento.getIdentificador().equals( identificador ) && documento.getInstancia() == instancia )
			{
				return documento;
			}
		}
		return null;
	}
	
	/**
	 * Obtiene numero de instancias correctas para un identificador
	 * @param lDocumentos
	 * @param identificador
	 * @return
	 */
	private int getNumeroInstanciasPorIdentificador( java.util.List lDocumentos, String identificador )
	{
		int num = 0;
		for ( Iterator it = lDocumentos.iterator(); it.hasNext(); )
		{
			DocumentoFront documento = ( DocumentoFront ) it.next();
			if ( documento.getIdentificador().equals( identificador ) && documento.getEstado() == DocumentoFront.ESTADO_CORRECTO  )
			{
				num ++;
			}
		}
		return num;
	}
		
	
	public DocumentoFront getPago( String identificador, int instancia )
	{
		return getDocumentoPorIdentificadorEInstancia( getPagos(), identificador, instancia );
	}
	
	public DocumentoFront getAnexo( String identificador, int instancia )
	{
		return getDocumentoPorIdentificadorEInstancia( getAnexos(), identificador, instancia );
	}
	
	public DocumentoFront getFormulario( String identificador, int instancia )
	{
		return getDocumentoPorIdentificadorEInstancia( getFormularios(), identificador, instancia );
	}
	
	/**
	 * Obtiene numero de instancias para un documento anexo
	 * @param identificador
	 * @return
	 */
	public int getAnexoNumeroInstancias( String identificador)
	{
		return getNumeroInstanciasPorIdentificador( getAnexos(), identificador);
	}
	
	/**
	 * Indica si se ha iniciado el proceso de pagos y no dejamos modificar formularios
	 */
	public boolean iniciadoPagos(){
		for (Iterator it = this.getPagos().iterator();it.hasNext();){
			if (((DocumentoFront) it.next()).getEstado() != DocumentoFront.ESTADO_NORELLENADO) return true;
		}
		return false;
	}
	public boolean isFlujoTramitacion() {
		return flujoTramitacion;
	}
	public void setFlujoTramitacion(boolean flujoTramitacion) {
		this.flujoTramitacion = flujoTramitacion;
	}
	public String getFlujoTramitacionNif() {
		return flujoTramitacionNif;
	}
	public void setFlujoTramitacionNif(String flujoTramitacionNif) {
		this.flujoTramitacionNif = flujoTramitacionNif;
	}
	public PersonaPAD getFlujoTramitacionDatosPersonaActual() {
		return flujoTramitacionDatosPersonaActual;
	}
	public void setFlujoTramitacionDatosPersonaActual(
			PersonaPAD flujoTramitacionDatosPersona) {
		this.flujoTramitacionDatosPersonaActual = flujoTramitacionDatosPersona;
	}
	public PersonaPAD getFlujoTramitacionDatosPersonaIniciador() {
		return flujoTramitacionDatosPersonaIniciador;
	}
	public void setFlujoTramitacionDatosPersonaIniciador(
			PersonaPAD flujoTramitacionDatosPersonaIniciador) {
		this.flujoTramitacionDatosPersonaIniciador = flujoTramitacionDatosPersonaIniciador;
	}
	public boolean isCircuitoReducido()
	{
		return circuitoReducido;
	}
	public void setCircuitoReducido(boolean circuitoReducido)
	{
		this.circuitoReducido = circuitoReducido;
	}
	public String getModelo()
	{
		return modelo;
	}
	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}
	public int getVersion()
	{
		return version;
	}
	public void setVersion(int version)
	{
		this.version = version;
	}
	public boolean isConsultar() {
		return consultar;
	}
	public void setConsultar(boolean consulta) {
		this.consultar = consulta;
	}
	public Date getFechaFinPlazo() {
		return fechaFinPlazo;
	}
	public void setFechaFinPlazo(Date fechaFinPlazo) {
		this.fechaFinPlazo = fechaFinPlazo;
	}
	public Date getFechaInicioPlazo() {
		return fechaInicioPlazo;
	}
	public void setFechaInicioPlazo(Date fechaInicioPlazo) {
		this.fechaInicioPlazo = fechaInicioPlazo;
	}
	public Date getFechaExportacion() {
		return fechaExportacion;
	}
	public void setFechaExportacion(Date fechaExportacion) {
		this.fechaExportacion = fechaExportacion;
	}
	public String getTagCuadernoCarga() {
		return tagCuadernoCarga;
	}
	public void setTagCuadernoCarga(String tagCuadernoCarga) {
		this.tagCuadernoCarga = tagCuadernoCarga;
	}
	public boolean isAsistente() {
		return asistente;
	}
	public void setAsistente(boolean asistente) {
		this.asistente = asistente;
	}
	public Map getMensajesPlataforma() {
		return mensajesPlataforma;
	}
	public void setMensajesPlataforma(Map mensajesPlataforma) {
		this.mensajesPlataforma = mensajesPlataforma;
	}
	public boolean isRedireccionFin() {
		return redireccionFin;
	}
	public void setRedireccionFin(boolean circuitoReducidoIrAFin) {
		this.redireccionFin = circuitoReducidoIrAFin;
	}
	public String getMensajeFechaLimiteEntregaPresencial() {
		return mensajeFechaLimiteEntregaPresencial;
	}
	public void setMensajeFechaLimiteEntregaPresencial(
			String mensajeFechaLimiteEntregaPresencial) {
		this.mensajeFechaLimiteEntregaPresencial = mensajeFechaLimiteEntregaPresencial;
	}
	public String getHabilitarNotificacionTelematica() {
		return habilitarNotificacionTelematica;
	}
	public void setHabilitarNotificacionTelematica(
			String habilitarNotificacionTelematica) {
		this.habilitarNotificacionTelematica = habilitarNotificacionTelematica;
	}
	public Boolean getSeleccionNotificacionTelematica() {
		return seleccionNotificacionTelematica;
	}
	public void setSeleccionNotificacionTelematica(
			Boolean seleccionNotificacionTelematica) {
		this.seleccionNotificacionTelematica = seleccionNotificacionTelematica;
	}
	public boolean isRemitirDelegacionFirma() {
		return remitirDelegacionFirma;
	}
	public void setRemitirDelegacionFirma(boolean remitirDelegacionFirma) {
		this.remitirDelegacionFirma = remitirDelegacionFirma;
	}
	public boolean isRemitirDelegacionPresentacion() {
		return remitirDelegacionPresentacion;
	}
	public void setRemitirDelegacionPresentacion(
			boolean remitirDelegacionPresentacion) {
		this.remitirDelegacionPresentacion = remitirDelegacionPresentacion;
	}
	
}
