package es.caib.sistra.persistence.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * Permite establecer los datos iniciales a precargar en
 * un formulario
 */
public class DatosIniciales {

	HashMap datos = new HashMap();
	
	/**
	 * Establece un dato inicial para un formulario
	 * @param campo Campo
	 * @param valor Valor	
	 */
	public void setDatoInicial(String campo,String valor){
		datos.put(campo,valor);		
	}
	
	/**
	 * Obtiene Map con los datos iniciales (campo/valor)
	 * @return
	 */
	public Map getDatosIniciales(){
		return datos;
	}
	
}
