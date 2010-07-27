package es.caib.xml.formsconf.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.formsconf.factoria.impl.ConfiguracionForms;
import es.caib.xml.formsconf.factoria.impl.Datos;
import es.caib.xml.formsconf.factoria.impl.Propiedad;

/** Factoria para la obtencion de objetos que encapsulan el acceso a
 *  la informacion proveniente de los ficheros XML
 * @author magroig
 */
public interface FactoriaObjetosXMLConfForms extends FactoriaObjetosXML {
	/** Crea una nuevo objeto representando un confForms (sin datos)
	 * @return Formulario Objeto de confForms creado
	 */
	public ConfiguracionForms crearConfiguracionForms ();
	
	/** Crea un nuevo objeto representando un confForms
	 *  y carga los datos a partir del flujo de datos de entrada.
	 *  El flujo de datos de entrada deberá cumplir con el esquema xml
	 *  correspondiente 
	 *  
	 * @param datosXMLJustificante Flujo de datos de entrada
	 * @return Objeto de formnulario con los datos leidos
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos
	 */		
	public ConfiguracionForms crearConfiguracionForms (InputStream datosXMLConfForms) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea un nuevo objeto representando un confForms
	 *  y carga los datos a partir del fichero de datos de entrada.
	 *  El fichero de datos de entrada deberá cumplir con el esquema xml
	 *  correspondiente
	 *  
	 * @param ficheroXMLFormulario Fichero de datos de entrada 
	 * @return Objeto de formnulario con los datos leidos
	 * @throws es.caib.xml.CargaObjetoXMLException
	 */
	public ConfiguracionForms crearConfiguracionForms (File ficheroXMLConfiguracionForms)
	throws es.caib.xml.CargaObjetoXMLException;	
	
	/** Guarda un objeto representando un confForms
	 *  en un flujo de datos de salida.
	 *  El flujo de datos de salida deberá cumplir con el esquema xml
	 *  correspondiente
	 *  
	 * @param confForms Objeto representando un confForms
	 * @param datosXMLFormulario Flujo de datos de salida
	 * @throws GuardaObjetoXMLException Se ha producido un error al escribir
	 * en el flujo de datos de salida
	 * @throws EstablecerPropiedadException Alguno de los datos de confForms
	 * son inválidos	 
	 */
	public void guardarConfiguracionForms (ConfiguracionForms confForms, OutputStream datosXMLConfiguracionForms) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
		
	/** Guarda un objeto representando un confForms
	 *  en un fichero.
	 *  El fichero deberá cumplir con el esquema xml correspondiente
	 * 
	 * @param confForms Objeto representando un confForms
	 * @param ficheroXMLFormulario Fichero de salida
	 * @throws GuardaObjetoXMLException Se ha producido un error al escribir
	 * en el fichero
	 * @throws EstablecerPropiedadException Alguno de los datos de confForms
	 * son inválidos	 
	 */
	public void guardarConfiguracionForms (ConfiguracionForms confForms, File ficheroXMLConfiguracionForms)
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
				
	/** Guarda un objeto representando un confForms en una cadena
	 * @param confForms Objeto representando un confForms
	 * @return Cadena con el confForms XML
	 * @throws GuardaObjetoXMLException Se ha producido un error al escribir
	 * los datos
	 * @throws EstablecerPropiedadException Alguno de los datos de confForms
	 * son inválidos
	 */
	public String guardarConfiguracionForms (ConfiguracionForms confForms) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
					
	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#isIdentacion()
	 */
	public boolean isIdentacion ();
		
	/* (non-Javadoc)
	 * @see es.caib.xml.FactoriaObjetosXML#setIndentacion(boolean)
	 */
	public void setIndentacion (boolean indentacion);
				
	
	public Datos crearDatos ();

	public Propiedad crearPropiedad ();		
}