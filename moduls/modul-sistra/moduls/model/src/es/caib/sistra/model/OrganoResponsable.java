package es.caib.sistra.model;

import java.util.HashSet;
import java.util.Set;


public class OrganoResponsable implements java.io.Serializable {

    // Fields    	
     private Long codigo;
     private String descripcion;
     private Set tramites = new HashSet(0);
     private Set dominios = new HashSet(0);
     
    // Constructors
    /** default constructor */
    public OrganoResponsable() {
    }

    // Property accessors
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getTramites() {
		return tramites;
	}

	public void setTramites(Set tramites) {
		this.tramites = tramites;
	}

	 public void addTramite(Tramite tramite) {    	
	    	tramite.setOrganoResponsable(this);
	        this.tramites.add(tramite);
	 }

	 public void removeTramite(Tramite tramite) {    	
		 tramites.remove(tramite);    	
	 }

	public Set getDominios()
	{
		return dominios;
	}

	public void setDominios(Set dominios)
	{
		this.dominios = dominios;
	}
	
	public void addDominio( Dominio dominio )
	{
		dominio.setOrganoResponsable( this );
		this.dominios.add( dominio );
	}
	
	public void removeDominio( Dominio dominio ){
		this.dominios.remove( dominio );
	}
	 
}
