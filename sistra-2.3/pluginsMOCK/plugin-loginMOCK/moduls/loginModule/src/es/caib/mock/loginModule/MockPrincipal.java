package es.caib.mock.loginModule;

import java.io.Serializable;
import java.security.Principal;

public class MockPrincipal  implements Principal,Serializable {

	private String user;
	private String nombreCompleto;
	private String nif;
	private char metodoAutenticacion;
	
	public MockPrincipal(String user,String nombreCompleto,String nif, char metodoAutenticacion){
		this.user = user;
		this.nombreCompleto = nombreCompleto;
		this.nif = nif;
		this.metodoAutenticacion = metodoAutenticacion;		
	}
	

	public String getName() {
		return user;
	}

	public char getMetodoAutenticacion() {
		return metodoAutenticacion;
	}


	public void setMetodoAutenticacion(char metodoAutenticacion) {
		this.metodoAutenticacion = metodoAutenticacion;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getNif() {
		return nif;
	}

	 public boolean equals(Object another)
	  {
	    if( !(another instanceof Principal) )
	      return false;
	    String anotherName = ((Principal)another).getName();
	    boolean equals = false;
	    if( getName() == null )
	      equals = anotherName == null;
	    else
	      equals = getName().equals(anotherName);
	    return equals;
	  }

	  public int hashCode()
	  {
	    return (getName() == null ? 0 : getName().hashCode());
	  }

	  public String toString()
	  {
	    return getName();
	  }
	
}
