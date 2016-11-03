package es.caib.xml.registro.factoria.impl;

import java.util.Date;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;

/** Objeto de Justificante que encapsula el nodo XML JUSTIFICANTE de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
/**
 * @author magroig
 *
 */
public class Justificante extends NodoRegistroBase {
	
	private String version;
	private String numeroRegistro;
	private Date fechaRegistro;
	private AsientoRegistral asientoRegistral;
	
	
	Justificante(){}	
	
	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#setVersion(java.lang.String)
	 */
	public void setVersion(String version) throws EstablecerPropiedadException {
		
		// Validar Campo obligatorio
		validaCampoObligatorio("Justificante", "Version", version);
		
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#getVersion()
	 */
	public String getVersion() {
		return version;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#getFechaRegistro()
	 */
	public Date getFechaRegistro() {				
		return fechaRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#setFechaRegistro(java.util.Date)
	 */
	public void setFechaRegistro(Date fechaRegistro) throws EstablecerPropiedadException {
		// Validar campo obligatorio
		validaCampoObligatorio ("Justificante", "FechaRegistro", fechaRegistro);
		
		this.fechaRegistro = fechaRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#getNumeroRegistro()
	 */
	public String getNumeroRegistro() {		
		return numeroRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#setNumeroRegistro(java.lang.String)
	 */
	public void setNumeroRegistro(String numeroRegistro) throws EstablecerPropiedadException {
		
		// Validar longitud de campo, campo obligatorio, formato de campo
		validaLongitudCampo("Justificante", "NumeroRegistro", numeroRegistro, ConstantesAsientoXML.NUMEROREGISTRO_MAX);
		validaCampoObligatorio ("Justificante", "NumeroRegistro", numeroRegistro);
		validaCampoObligatorio ("Justificante", "NumeroRegistro", numeroRegistro);
		// validaFormatoCampo ("Justificante", "NumeroRegistro", numeroRegistro, "^\\d\\d/\\d\\d\\d\\d\\d/\\d\\d\\d\\d$");				
		
		this.numeroRegistro = numeroRegistro;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#getAsientoRegistral()
	 */
	public AsientoRegistral getAsientoRegistral() {				
		return asientoRegistral;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.Justificante#setAsientoRegistral(es.caib.xml.registro.factoria.AsientoRegistral)
	 */
	public void setAsientoRegistral(AsientoRegistral asientoRegistral) throws EstablecerPropiedadException {
		// Validar campo obligatorio
		validaCampoObligatorio ("Justificante", "AsientoRegistral", asientoRegistral);
		
		this.asientoRegistral = asientoRegistral;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		AsientoRegistral asientoRegistral = null;
		String numeroRegistro = null;
		Date fechaRegistro = null;
		String version = null;
		
		asientoRegistral = getAsientoRegistral ();		
		if (asientoRegistral == null) throw new CampoObligatorioException ("Justificante", "AsientoRegistral");
		else asientoRegistral.comprobarDatosRequeridos();
		
		numeroRegistro = getNumeroRegistro ();
		if ( (numeroRegistro == null) || (numeroRegistro.trim().equals("")) )
			throw new CampoObligatorioException ("Justificante", "NumeroRegistro");
		
		fechaRegistro = getFechaRegistro ();
		if (fechaRegistro == null) throw new CampoObligatorioException ("Justificante", "FechaRegistro");
		
		version = getVersion ();
		if ( (version == null) || (version.trim().equals("")) )
			throw new CampoObligatorioException ("Justificante", "Version");		
	}	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof Justificante){
			
			if (obj == null) return false;
			
			Justificante justificante = (Justificante) obj;
			
			// Comprobar asiento registral
			AsientoRegistral asiento = getAsientoRegistral ();
			AsientoRegistral asientoExt = justificante.getAsientoRegistral ();
			
			if ((asiento != null) || (asientoExt != null))
				if ( (asientoExt != null) && (asiento != null) ){
					if (!asiento.equals (asientoExt)) return false;
				}
				else
					if ((asiento != null) || (asientoExt != null)) return false;
			
			
			// Comprobar fecha registro
			java.util.Date fechaRegistro = getFechaRegistro ();
			java.util.Date fechaRegistroExt = justificante.getFechaRegistro ();
			
			if ((fechaRegistro != null) || (fechaRegistroExt != null))
				if ( (fechaRegistroExt != null) && (fechaRegistro != null) ){
					if (!fechaRegistro.equals (fechaRegistroExt)) return false;
				}
				else
					if ((fechaRegistro != null) || (fechaRegistroExt != null)) return false;
			
			
			// Comprobar número registro
			String numeroRegistro = getNumeroRegistro ();
			String numeroRegistroExt = justificante.getNumeroRegistro ();
			
			if ((numeroRegistro != null) || (numeroRegistroExt != null))
				if ( (numeroRegistroExt != null) && (numeroRegistro != null) ){
					if (!numeroRegistro.equals (numeroRegistroExt)) return false;
				}
				else
					if ((numeroRegistro != null) || (numeroRegistroExt != null)) return false;
			
			// Comprobar versión
			String version = getVersion ();
			String versionExt = justificante.getVersion ();
			
			if ((version != null) || (versionExt != null))
				if ( (versionExt != null) && (version != null) ){
					if (!version.equals (versionExt)) return false;
				}
				else
					if ((version != null) || (versionExt != null)) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

}
