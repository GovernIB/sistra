package es.caib.sistra.persistence.plugins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import es.caib.sistra.model.DatosFormulario;

/**
 * Permite establecer la configuración dinámica para un formulario: 
 * 		- datos sólo lectura
 * 		- propiedades específicas gestor formularios
 */
public class ConfiguracionDinamica implements Serializable{

	/**
	 * Campos solo lectura
	 */
	ArrayList readOnly = new ArrayList();
	
	/**
	 * Propiedades específicas del gestor de formularios
	 */
	private HashMap propiedades = new HashMap();
	
	/**
	 * Establece un dato inicial para un formulario
	 * @param campo Campo
	 * @param valor Valor
	 * @param soloLectura Indica si el campo será de sólo lectura
	 */
	public void setDatoSoloLectura(String campo){		
		readOnly.add(DatosFormulario.referenciaXPath(campo));
	}

	/**
	 * Obtiene lista de campos sólo lectura
	 */
	public ArrayList getReadOnly() {
		return readOnly;
	}
	
	public void setPropiedad(String propiedad,String valor){
		propiedades.put(propiedad,valor);
	}
	
	public String getPropiedad(String propiedad){
		if (propiedades.containsKey(propiedad)) return (String) propiedades.get(propiedad);
		return null;
	}

	public HashMap getPropiedades() {
		return propiedades;
	}
	
}
