package es.caib.sistra.model;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

import es.caib.sistra.model.betwixt.Configurator;

public class TramiteVersion  implements TramiteEspecificado, Serializable {

	public final static char CONSULTA_EJB='E';
	public final static char CONSULTA_WEBSERVICE='W';

	public final static char EJB_REMOTO = 'R';
	public final static char EJB_LOCAL  = 'L';

	public final static char AUTENTICACION_EXPLICITA_SINAUTENTICAR 	= 'N';
	public final static char AUTENTICACION_EXPLICITA_ESTANDAR 	= 'S';
	public final static char AUTENTICACION_EXPLICITA_ORGANISMO 	= 'C';

	public final static char LIMITE_TRAMITACION_NO_APLICAR 	= 'N';
	public final static char LIMITE_TRAMITACION_INICIOS 	= 'I';

    // Fields
     private Long codigo;
     private EspecTramiteNivel especificaciones;
     private Tramite tramite;
     private int version;
     private String motivo;
     private String organoDestino;
     private Long unidadAdministrativa;
     private Timestamp InicioPlazo;
     private Timestamp FinPlazo;
     private char destino;
     private char preenvioConfirmacionAutomatica='N';
     private char firmar='N';
     private String registroOficina;
     private String registroAsunto;
     private char consultaTipoAcceso=CONSULTA_EJB;
     private String consultaEJB;
     private char consultaLocalizacion = EJB_LOCAL;
     private String consultaUrl;
     private String consultaWSVersion;
     private char consultaAuth=AUTENTICACION_EXPLICITA_SINAUTENTICAR ;
     private String consultaAuthUser;
     private String consultaAuthPwd;
     private String bloqueadoModificacion="N";
     private String bloqueadoModificacionPor;
     private String idiomasSoportados="es,ca";
     private char reducido='N';
     private char redireccionFin='N';
     private char registroAutomatico='N';

     private String cuadernoCargaTag;
     private Date fechaExportacion;

     private char anonimoDefecto = 'N';

     private Set niveles = new HashSet(0);
     //private Map niveles = new HashMap(0);
     private Map mensajes= new HashMap(0);
     private Set documentos = new HashSet(0);

     private char debugEnabled='N';

     /** Limite tramitacion: N, I (sin limite, iniciados x intervalo, ...) */
     private char limiteTipo = LIMITE_TRAMITACION_NO_APLICAR;

     /** Limite tramitacion: número x intervalo */
     private Integer limiteNumero;

     /** Limite tramitacion: minutos intervalo */
     private Integer limiteIntervalo;


    // Constructors

	/** default constructor */
    public TramiteVersion() {
    }

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Set getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set documentos) {
		this.documentos = documentos;
	}

	public EspecTramiteNivel getEspecificaciones() {
		return especificaciones;
	}

	public void setEspecificaciones(EspecTramiteNivel especificaciones) {
		this.especificaciones = especificaciones;
	}

	public Timestamp getFinPlazo() {
		return FinPlazo;
	}

	public void setFinPlazo(Timestamp finPlazo) {
		FinPlazo = finPlazo;
	}

	public Timestamp getInicioPlazo() {
		return InicioPlazo;
	}

	public void setInicioPlazo(Timestamp inicioPlazo) {
		InicioPlazo = inicioPlazo;
	}

	public Map getMensajes() {
		return mensajes;
	}

	public void setMensajes(Map mensajes) {
		this.mensajes = mensajes;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}


	/*
	public void addMensaje(MensajeTramite mens) {
		mens.setTramiteVersion(this);
    	mensajes.put(mens.getIdentificador(),mens);
    }
    */

	/**
	 *	Para compatibilidad con betwixt
	 * @param identificador
	 * @param mens
	 */
	public void addMensaje( String identificador, MensajeTramite mens )
	{
		mens.setTramiteVersion( this );
		mensajes.put( identificador, mens );
	}

    public void removeMensaje(MensajeTramite mens) {
    	mensajes.remove(mens.getIdentificador());
    }

    public void addDocumento(Documento doc) {
    	doc.setTramiteVersion(this);
    	documentos.add(doc);
    }

    public void removeDocumento(Documento doc) {
    	documentos.remove(doc);
    }

    public Set getNiveles() {
		return niveles;
	}

	public void setNiveles(Set niveles) {
		this.niveles = niveles;
	}

	/**
	 * Metodo por compatibilidad con betwixt
	 * @param nivel
	 */
	public void addNivele(TramiteNivel nivel) {
	    	this.addTramiteNivel( nivel );
	 }

	 public void addTramiteNivel(TramiteNivel nivel) {
	    	nivel.setTramiteVersion(this);
	        niveles.add(nivel);
	 }

    public void removeNivel(TramiteNivel nivel) {
    	niveles.remove(nivel);
    }

	public void setCurrentLang(String currentLang) {

        for (Iterator iterator = niveles.iterator();iterator.hasNext();) {
            TramiteNivel tramiteNivel = (TramiteNivel) iterator.next();
            tramiteNivel.setCurrentLang(currentLang);
        }

        for (Iterator iterator = documentos.iterator();iterator.hasNext();) {
            Documento documento = (Documento) iterator.next();
            documento.setCurrentLang(currentLang);
        }

        for (Iterator iterator = mensajes.keySet().iterator();iterator.hasNext();) {
        	String ls_key = (String) iterator.next();
            MensajeTramite mensajeTramite = (MensajeTramite) mensajes.get(ls_key);
            mensajeTramite.setCurrentLang(currentLang);
        }

        especificaciones.setCurrentLang(currentLang);
    }


	public TramiteNivel getTramiteNivel(char nivel) {
		// Buscamos tramite que tenga especificado el nivel
		for (Iterator it=niveles.iterator();it.hasNext();){
			TramiteNivel tn = (TramiteNivel) it.next();
			if (tn.getNivelAutenticacion().indexOf(nivel) != -1) return tn;
		}
		return null;
 	}

    /*
    public Map getNiveles() {
		return niveles;
	}

	public void setNiveles(Map niveles) {
		this.niveles = niveles;
	}

	public TramiteNivel getTramiteNivel(char nivel) {
		return (TramiteNivel) this.niveles.get(Character.toString(nivel));
	}

	public void addTramiteNivel(TramiteNivel nivel) {
    	nivel.setTramiteVersion(this);
    	niveles.put(nivel.getNivelAutenticacion(),nivel);
	}

	public void removeNivel(TramiteNivel nivel) {
		niveles.remove(nivel.getNivelAutenticacion());
	}

	public void setCurrentLang(String currentLang) {

        for (Iterator iterator = niveles.keySet().iterator();iterator.hasNext();) {
        	String ls_key = (String) iterator.next();
            TramiteNivel tramiteNivel = (TramiteNivel) niveles.get(ls_key);
            tramiteNivel.setCurrentLang(currentLang);
        }

        for (Iterator iterator = documentos.iterator();iterator.hasNext();) {
            Documento documento = (Documento) iterator.next();
            documento.setCurrentLang(currentLang);
        }

        for (Iterator iterator = mensajes.keySet().iterator();iterator.hasNext();) {
        	String ls_key = (String) iterator.next();
            MensajeTramite mensajeTramite = (MensajeTramite) mensajes.get(ls_key);
            mensajeTramite.setCurrentLang(currentLang);
        }

        especificaciones.setCurrentLang(currentLang);
    }

	*/

	public String getConsultaEJB() {
		return consultaEJB;
	}

	public void setConsultaEJB(String consultaEJB) {
		this.consultaEJB = consultaEJB;
	}

	public char getDestino() {
		return destino;
	}

	public void setDestino(char destino) {
		this.destino = destino;
	}

	public char getFirmar() {
		return firmar;
	}

	public void setFirmar(char firmar) {
		this.firmar = firmar;
	}

	public String getRegistroAsunto() {
		return registroAsunto;
	}

	public void setRegistroAsunto(String registroAsunto) {
		this.registroAsunto = registroAsunto;
	}

	public String getRegistroOficina() {
		return registroOficina;
	}

	public void setRegistroOficina(String registroOficina) {
		this.registroOficina = registroOficina;
	}

	public String getOrganoDestino() {
		return organoDestino;
	}

	public void setOrganoDestino(String organoDestino) {
		this.organoDestino = organoDestino;
	}

	public Long getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(Long unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	public String getBloqueadoModificacion() {
		return bloqueadoModificacion;
	}

	public void setBloqueadoModificacion(String bloqueadoModificacion) {
		this.bloqueadoModificacion = bloqueadoModificacion;
	}

	public String getBloqueadoModificacionPor() {
		return bloqueadoModificacionPor;
	}

	public void setBloqueadoModificacionPor(String bloqueadoModificacionPor) {
		this.bloqueadoModificacionPor = bloqueadoModificacionPor;
	}

	public String getIdiomasSoportados() {
		return idiomasSoportados;
	}

	public void setIdiomasSoportados(String idiomasSoportados) {
		this.idiomasSoportados = idiomasSoportados;
	}

	public char getReducido()
	{
		return reducido;
	}

	public void setReducido(char reducido)
	{
		this.reducido = reducido;
	}

	public char getConsultaLocalizacion() {
		return consultaLocalizacion;
	}

	public void setConsultaLocalizacion(char consultaLocalizacion) {
		this.consultaLocalizacion = consultaLocalizacion;
	}

	public String getConsultaUrl() {
		return consultaUrl;
	}

	public void setConsultaUrl(String consultaUrl) {
		this.consultaUrl = consultaUrl;
	}

	public String getConsultaWSVersion() {
		return consultaWSVersion;
	}

	public void setConsultaWSVersion(String consultaWSVersion) {
		this.consultaWSVersion = consultaWSVersion;
	}

	public char getConsultaAuth() {
		return consultaAuth;
	}

	public void setConsultaAuth(char consultaAuth) {
		this.consultaAuth = consultaAuth;
	}

	public String getConsultaAuthPwd() {
		return consultaAuthPwd;
	}

	public void setConsultaAuthPwd(String consultaAuthPwd) {
		this.consultaAuthPwd = consultaAuthPwd;
	}

	public String getConsultaAuthUser() {
		return consultaAuthUser;
	}

	public void setConsultaAuthUser(String consultaAuthUser) {
		this.consultaAuthUser = consultaAuthUser;
	}

	public static TramiteVersion fromXml( byte[] xml )
	{
		TramiteVersion t = null;
		try
		{
			BeanReader beanReader = new BeanReader();
	        Configurator.configure(beanReader);
	        t = ( TramiteVersion ) beanReader.parse( new ByteArrayInputStream( xml ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( IntrospectionException ie )
		{
			ie.printStackTrace();
		}
		catch ( SAXException saxe )
		{
			saxe.printStackTrace();
		}
		return t;
	}

	public byte[] toXml()
    {
		byte[] result = null;
    	try
    	{
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	BeanWriter beanWriter = new BeanWriter(baos, "UTF-8");
	        beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	        Configurator.configure(beanWriter);
	        beanWriter.write(this);
	        beanWriter.close();
	        result = baos.toByteArray();
    	}
        catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( IntrospectionException ie )
		{
			ie.printStackTrace();
		}
		catch ( SAXException saxe )
		{
			saxe.printStackTrace();
		}
    	return result;
    }

	public String getCuadernoCargaTag() {
		return cuadernoCargaTag;
	}

	public void setCuadernoCargaTag(String cuadernoCargaTag) {
		this.cuadernoCargaTag = cuadernoCargaTag;
	}

	public Date getFechaExportacion() {
		return fechaExportacion;
	}

	public void setFechaExportacion(Date fechaExportacion) {
		this.fechaExportacion = fechaExportacion;
	}

	public char getPreenvioConfirmacionAutomatica() {
		return preenvioConfirmacionAutomatica;
	}

	public void setPreenvioConfirmacionAutomatica(
			char preenvioConfirmacionAutomatica) {
		this.preenvioConfirmacionAutomatica = preenvioConfirmacionAutomatica;
	}

	public char getRedireccionFin() {
		return redireccionFin;
	}

	public void setRedireccionFin(char redireccionFin) {
		this.redireccionFin = redireccionFin;
	}

	public char getAnonimoDefecto() {
		return anonimoDefecto;
	}

	public void setAnonimoDefecto(char anonimoDefecto) {
		this.anonimoDefecto = anonimoDefecto;
	}

	public char getConsultaTipoAcceso() {
		return consultaTipoAcceso;
	}

	public void setConsultaTipoAcceso(char consultaTipoAcceso) {
		this.consultaTipoAcceso = consultaTipoAcceso;
	}

	public char getRegistroAutomatico() {
		return registroAutomatico;
	}

	public void setRegistroAutomatico(char registroAutomatico) {
		this.registroAutomatico = registroAutomatico;
	}

	public char getDebugEnabled() {
		return debugEnabled;
	}

	public void setDebugEnabled(char debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	/**
	 *	Devuelve limiteTipo.
	 * @return limiteTipo
	 */
	public char getLimiteTipo() {
		return limiteTipo;
	}

	/**
	 * Establece limiteTipo.
	 * @param limiteTipo limiteTipo
	 */
	public void setLimiteTipo(char limiteTipo) {
		this.limiteTipo = limiteTipo;
	}

	/**
	 *	Devuelve limiteNumero.
	 * @return limiteNumero
	 */
	public Integer getLimiteNumero() {
		return limiteNumero;
	}

	/**
	 * Establece limiteNumero.
	 * @param limiteNumero limiteNumero
	 */
	public void setLimiteNumero(Integer limiteNumero) {
		this.limiteNumero = limiteNumero;
	}

	/**
	 *	Devuelve limiteIntervalo.
	 * @return limiteIntervalo
	 */
	public Integer getLimiteIntervalo() {
		return limiteIntervalo;
	}

	/**
	 * Establece limiteIntervalo.
	 * @param limiteIntervalo limiteIntervalo
	 */
	public void setLimiteIntervalo(Integer limiteIntervalo) {
		this.limiteIntervalo = limiteIntervalo;
	}



}
