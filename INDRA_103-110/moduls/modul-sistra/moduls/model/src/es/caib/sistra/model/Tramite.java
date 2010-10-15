package es.caib.sistra.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Tramite extends Traducible {

    // Fields    	
     private Long codigo;
     private String identificador;
     private Set versiones = new HashSet(0);     
     private OrganoResponsable organoResponsable;

    // Constructors
    /** default constructor */
    public Tramite() {
    }

    // Property accessors
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Set getVersiones() {
		return versiones;
	}

	public void setVersiones(Set versiones) {
		this.versiones = versiones;
	}

    public void addVersion(TramiteVersion version) {    	
    	version.setTramite(this);
        this.versiones.add(version);
    }

    public void removeVersion(TramiteVersion version) {    	
    	versiones.remove(version);    	
    }	
	
	public void setCurrentLang(String currentLang) {
        super.setCurrentLang(currentLang);
         for (Iterator iterator = versiones.iterator();iterator.hasNext();) {
            TramiteVersion tramiteVersion = (TramiteVersion) iterator.next();
            tramiteVersion.setCurrentLang(currentLang);
        }       
    }

    public void addTraduccion(String lang, TraTramite traduccion) {
        setTraduccion(lang, traduccion);
    }

	public OrganoResponsable getOrganoResponsable() {
		return organoResponsable;
	}

	public void setOrganoResponsable(OrganoResponsable organoResponsable) {
		this.organoResponsable = organoResponsable;
	}

}
