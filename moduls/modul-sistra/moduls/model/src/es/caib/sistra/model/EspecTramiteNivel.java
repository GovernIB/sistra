package es.caib.sistra.model;

import java.util.ArrayList;
import java.util.List;

public class EspecTramiteNivel  extends Traducible {


    // Fields
     private Long codigo;
     private String activo="N";
     private byte[] validacionInicioScript;
     private int diasPersistencia;
     private int diasPrerregistro;
     private byte[] campoCodigoProvincia;
     private byte[] campoCodigoLocalidad;
     private byte[] campoCodigoPais;
     private byte[] campoRteNif;
     private byte[] campoRteNom;
     private byte[] campoRdoNif;
     private byte[] campoRdoNom;
     private byte[] datosRpteScript;
     private byte[] datosRpdoScript;
     private byte[] urlFin;
     // X: Sin especificar (para sobreescribir x nivel) / N: No permite /  S: Permite / O: Obligatoria
     private String habilitarNotificacionTelematica = "X";
     private String permitirSMS = "N"; // S / N
     // X: Sin especificar (para sobreescribir x nivel) / N: No permite /  S: Permite
     private String habilitarAlertasTramitacion = "X";
     private String permitirSMSAlertasTramitacion = "N"; // S / N
     private String finalizarTramiteAuto = "N"; // S / N
     
     private String verificarMovil = "N"; // S / N
     

     private byte[] avisoSMS;
     private byte[] avisoEmail;
     private List datosJustificante = new ArrayList();
     private String flujoTramitacion="N";
     private byte[] checkEnvio;
     private String ocultarClaveTramitacionJustif = "X";
     private String ocultarNifNombreJustif = "X";
     /***
      * Script que permite cambiar el destinatario del trámite (oficina registro,organo destino y unidad administrativa)
      */
     private byte[] destinatarioTramite;
     /***
      * Script que permite cambiar el destinatario del trámite (procedimiento)
      */
     private byte[] procedimientoDestinoTramite;


	// Constructors
    /** default constructor */
    public EspecTramiteNivel() {
    }


	public String getActivo() {
		return activo;
	}


	public void setActivo(String activo) {
		this.activo = activo;
	}


	public Long getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public List getDatosJustificante() {
		return datosJustificante;
	}


	public void setDatosJustificante(List datosJustificante) {
		this.datosJustificante = datosJustificante;
	}


	public int getDiasPersistencia() {
		return diasPersistencia;
	}


	public void setDiasPersistencia(int diasPersistencia) {
		this.diasPersistencia = diasPersistencia;
	}


	/**
	 * Compatibilidad con betwixt
	 * @param dat
	 */

	public void addDatosJustificante( DatoJustificante dat )
	{
		addDatoJustificante( dat );
	}

	public void addDatoJustificante(DatoJustificante dat) {
		dat.setEspecTramiteNivel(this);
		dat.setOrden(datosJustificante.size());
    	datosJustificante.add(dat);
    }

    public void removeDatoJustificante(DatoJustificante dat) {
    	int ind = datosJustificante.indexOf(dat);
		datosJustificante.remove(ind);
        for (int i = ind; i < datosJustificante.size(); i++) {
        	DatoJustificante p = (DatoJustificante) datosJustificante.get(i);
            p.setOrden(i);
        }
    }

    public void upOrdenDatoJustificante( DatoJustificante dat )
    {
    	int ind = datosJustificante.indexOf(dat);
    	if ( ind > 0 )
    	{
        	DatoJustificante anterior = (DatoJustificante) datosJustificante.get(ind - 1 );
        	anterior.setOrden( ind );
        	datosJustificante.set( ind, anterior );
            dat.setOrden( ind - 1 );
            datosJustificante.set( ind - 1, dat );
    	}
    }

    public void downOrdenDatoJustificante( DatoJustificante dat )
    {
    	int ind = datosJustificante.indexOf(dat);
    	if ( ind < datosJustificante.size() - 1  )
    	{
        	DatoJustificante posterior = (DatoJustificante) datosJustificante.get( ind + 1 );
        	posterior.setOrden( ind );
        	datosJustificante.set( ind, posterior );
            dat.setOrden( ind + 1 );
            datosJustificante.set( ind + 1, dat );
    	}
    }

	public void setCurrentLang(String currentLang) {
        super.setCurrentLang(currentLang);
        for (int i = 0; i < datosJustificante.size(); i++) {
        	DatoJustificante dat = (DatoJustificante) datosJustificante.get(i);
        	dat.setCurrentLang(currentLang);
        }
    }

	public void addTraduccion(String lang, TraEspecTramiteNivel traduccion) {
        setTraduccion(lang, traduccion);
    }


	public byte[] getValidacionInicioScript() {
		return validacionInicioScript;
	}


	public void setValidacionInicioScript(byte[] validacionInicioScript) {
		this.validacionInicioScript = validacionInicioScript;
	}


	public int getDiasPrerregistro()
	{
		return diasPrerregistro;
	}


	public void setDiasPrerregistro(int diasPrerregistro)
	{
		this.diasPrerregistro = diasPrerregistro;
	}


	public byte[] getCampoCodigoLocalidad() {
		return campoCodigoLocalidad;
	}


	public void setCampoCodigoLocalidad(byte[] campoCodigoLocalidad) {
		this.campoCodigoLocalidad = campoCodigoLocalidad;
	}


	public byte[] getCampoCodigoProvincia() {
		return campoCodigoProvincia;
	}


	public void setCampoCodigoProvincia(byte[] campoCodigoProvincia) {
		this.campoCodigoProvincia = campoCodigoProvincia;
	}


	public byte[] getCampoRdoNif() {
		return campoRdoNif;
	}


	public void setCampoRdoNif(byte[] campoRdoNif) {
		this.campoRdoNif = campoRdoNif;
	}


	public byte[] getCampoRdoNom() {
		return campoRdoNom;
	}


	public void setCampoRdoNom(byte[] campoRdoNom) {
		this.campoRdoNom = campoRdoNom;
	}


	public byte[] getCampoRteNif() {
		return campoRteNif;
	}


	public void setCampoRteNif(byte[] campoRteNif) {
		this.campoRteNif = campoRteNif;
	}


	public byte[] getCampoRteNom() {
		return campoRteNom;
	}


	public void setCampoRteNom(byte[] campoRteNom) {
		this.campoRteNom = campoRteNom;
	}


	public byte[] getCampoCodigoPais() {
		return campoCodigoPais;
	}


	public void setCampoCodigoPais(byte[] campoCodigoPais) {
		this.campoCodigoPais = campoCodigoPais;
	}


	public String getFlujoTramitacion() {
		return flujoTramitacion;
	}


	public void setFlujoTramitacion(String flujoTramitacion) {
		this.flujoTramitacion = flujoTramitacion;
	}


	public byte[] getUrlFin() {
		return urlFin;
	}


	public void setUrlFin(byte[] urlFin) {
		this.urlFin = urlFin;
	}


	public byte[] getAvisoEmail() {
		return avisoEmail;
	}


	public void setAvisoEmail(byte[] avisoEmail) {
		this.avisoEmail = avisoEmail;
	}


	public byte[] getAvisoSMS() {
		return avisoSMS;
	}


	public void setAvisoSMS(byte[] avisoSMS) {
		this.avisoSMS = avisoSMS;
	}

	public String getHabilitarNotificacionTelematica() {
		return habilitarNotificacionTelematica;
	}


	public void setHabilitarNotificacionTelematica(
			String habilitarNotificacionTelematica) {
		this.habilitarNotificacionTelematica = habilitarNotificacionTelematica;
	}


	public byte[] getCheckEnvio() {
		return checkEnvio;
	}


	public void setCheckEnvio(byte[] checkEnvio) {
		this.checkEnvio = checkEnvio;
	}


	public byte[] getDestinatarioTramite() {
		return destinatarioTramite;
	}


	public void setDestinatarioTramite(byte[] destinatarioTramite) {
		this.destinatarioTramite = destinatarioTramite;
	}
	

    public byte[] getProcedimientoDestinoTramite() {
		return procedimientoDestinoTramite;
	}


	public void setProcedimientoDestinoTramite(byte[] procedimientoDestinoTramite) {
		this.procedimientoDestinoTramite = procedimientoDestinoTramite;
	}


	public String getPermitirSMS() {
		return permitirSMS;
	}


	public void setPermitirSMS(String permitirSMS) {
		this.permitirSMS = permitirSMS;
	}


	public String getOcultarClaveTramitacionJustif() {
		return ocultarClaveTramitacionJustif;
	}


	public void setOcultarClaveTramitacionJustif(String ocultarClaveTramitacion) {
		this.ocultarClaveTramitacionJustif = ocultarClaveTramitacion;
	}


	public String getOcultarNifNombreJustif() {
		return ocultarNifNombreJustif;
	}


	public void setOcultarNifNombreJustif(String ocultarNifNombre) {
		this.ocultarNifNombreJustif = ocultarNifNombre;
	}


	public String getHabilitarAlertasTramitacion() {
		return habilitarAlertasTramitacion;
	}


	public void setHabilitarAlertasTramitacion(String habilitarAlertasTramitacion) {
		this.habilitarAlertasTramitacion = habilitarAlertasTramitacion;
	}


	public String getPermitirSMSAlertasTramitacion() {
		return permitirSMSAlertasTramitacion;
	}


	public void setPermitirSMSAlertasTramitacion(
			String permitirSMSAlertasTramitacion) {
		this.permitirSMSAlertasTramitacion = permitirSMSAlertasTramitacion;
	}


	public byte[] getDatosRpteScript() {
		return datosRpteScript;
	}


	public void setDatosRpteScript(byte[] datosRpteScript) {
		this.datosRpteScript = datosRpteScript;
	}


	public byte[] getDatosRpdoScript() {
		return datosRpdoScript;
	}


	public void setDatosRpdoScript(byte[] datosRpdoScript) {
		this.datosRpdoScript = datosRpdoScript;
	}


	public String getFinalizarTramiteAuto() {
		return finalizarTramiteAuto;
	}


	public void setFinalizarTramiteAuto(String finalizarTramiteAuto) {
		this.finalizarTramiteAuto = finalizarTramiteAuto;
	}


	public String getVerificarMovil() {
		return verificarMovil;
	}


	public void setVerificarMovil(String verificarMovil) {
		this.verificarMovil = verificarMovil;
	}



}
