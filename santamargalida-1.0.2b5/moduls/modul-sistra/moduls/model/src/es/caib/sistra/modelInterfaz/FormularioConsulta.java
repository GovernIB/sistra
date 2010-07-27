package es.caib.sistra.modelInterfaz;

import java.io.Serializable;

import es.caib.xml.analiza.Analizador;
import es.caib.xml.util.HashMapIterable;


/**
 * Clase que modeliza los formularios que son pasados a los ejb que 
 * resuelven tramites de tipo consulta
 * 
 */
public class FormularioConsulta implements Serializable{
	
	
	/**
	 * Identificador del documento 
	 */
	private String identificador;
	/**
	 * N�mero de instancia del documento 
	 */
	private int numeroInstancia=1;
	
	/**
	 * XML con los datos del formulario
	 */
	private String xml;

	/**
	 * XML con los datos del formulario
	 * @return
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * XML con los datos del formulario
	 * @param xml
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}
	
	/**
	 * Identificador del documento en el tr�mite
	 * @return
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Identificador del documento en el tr�mite
	 * @param identificador
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	/**
	 * N�mero de instancia del documento en el tr�mite
	 * @return
	 */
	public int getNumeroInstancia() {
		return numeroInstancia;
	}

	/**
	 * N�mero de instancia del documento en el tr�mite
	 * @param numeroInstancia
	 */
	public void setNumeroInstancia(int numeroInstancia) {
		this.numeroInstancia = numeroInstancia;
	}
	
	/**
     * Construye una Hash que permite recorrer los campos del documento mediante su Xpath
     */
    public HashMapIterable toHashMapIterable() throws Exception{    	    	
    	Analizador analiza = new Analizador ();
    	return analiza.analizar(xml);	    	
    }
	
	
}
