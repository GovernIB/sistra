package es.caib.xml.formsconf.factoria.impl;

import java.util.ArrayList;
import java.util.List;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.util.HashtableIterable;

public class ConfiguracionForms extends NodoBaseConfForms  {
	private Datos datos;
	private HashtableIterable propiedades = new HashtableIterable ();
	private List bloqueo = new ArrayList ();
	
	ConfiguracionForms(){}
	
	public Datos getDatos() {								
		return datos;
	}

	public void setDatos(Datos datos) throws EstablecerPropiedadException {
		validaCampoObligatorio ("ConfiguracionForms", "Datos", datos);		
		
		this.datos = datos;
	}

	public HashtableIterable getPropiedades() {
		return propiedades;
	}
	
	public List getBloqueo() {
		return bloqueo;
	}
	

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		// Datos		
		validaCampoObligatorio ("ConfiguracionForms", "Datos", getDatos ());					
	}
	
	public boolean equals (Object obj){
		if (obj instanceof ConfiguracionForms){
			
			if (obj == null) return false;
			
			ConfiguracionForms conf = (ConfiguracionForms) obj;
			
			// Datos
			Datos datos 			= getDatos ();
			Datos datosExt 			= conf.getDatos();
			if (!objetosIguales (datos, datosExt)) return false;
			
			// Propiedades			
			if (!objetosIguales (getPropiedades (), conf.getPropiedades())) return false;
			
			// Bloqueo			
			if (!objetosIguales (getBloqueo (), getBloqueo ())) return false;
			
			// Ok consideramos los objetos equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

}
