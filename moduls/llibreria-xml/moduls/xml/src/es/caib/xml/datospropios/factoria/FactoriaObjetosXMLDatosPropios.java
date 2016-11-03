package es.caib.xml.datospropios.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.datospropios.factoria.impl.AlertasTramitacion;
import es.caib.xml.datospropios.factoria.impl.Dato;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.datospropios.factoria.impl.DocumentosEntregar;
import es.caib.xml.datospropios.factoria.impl.FormulariosJustificante;
import es.caib.xml.datospropios.factoria.impl.Instrucciones;
import es.caib.xml.datospropios.factoria.impl.PersonalizacionJustificante;
import es.caib.xml.datospropios.factoria.impl.Solicitud;
import es.caib.xml.datospropios.factoria.impl.TramiteSubsanacion;

/** Objeto que crea una factoria capaz de crear objetos para manejar
 * datos del documento XML de datos propios
 * 
 * @author magroig
 *
 */
public interface FactoriaObjetosXMLDatosPropios extends FactoriaObjetosXML {
		
	/** Crea un objeto de datos propios vacío
	 * @return Objeto de datos propios
	 */
	public DatosPropios crearDatosPropios ();
		
	/** Crea ub objeto de datos propios que contenga los datos (XML) definidos en el
	 * flujo de datos
	 * @param datosXMLDatosPropios Flujo de datos con el contenido XML origen
	 * @return Objeto de datos propios
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el flujo
	 */
	public DatosPropios crearDatosPropios (InputStream datosXMLDatosPropios) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Crea ub objeto de datos propios que contenga los datos (XML) definidos en el
	 * fichero de datos
	 * @param ficheroXMLDatosPropios Fichero con el contenido XML origen
	 * @return Objeto de datos propios
	 * @throws es.caib.xml.CargaObjetoXMLException Se ha producido un error al cargar los datos desde el fichero
	 */
	public DatosPropios crearDatosPropios (File ficheroXMLDatosPropios)
	throws es.caib.xml.CargaObjetoXMLException;
	
	/** Guarda el contenido de un objeto de datos propios en un flujo de datos de salida (en formato XML)
	 * @param datosPropios Objeto de datos propios
	 * @param datosXMLdatosPropios Flujo de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el flujo
	 * @throws EstablecerPropiedadException Los datos del objeto de datos propios no son correctos
	 */
	public void guardarDatosPropios (DatosPropios datosPropios, OutputStream datosXMLdatosPropios) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido de un objeto de datos propios en un fichero de datos de salida (en formato XML)
	 * @param datosPropios Objeto de datos propios
	 * @param ficheroXMLDatosPropios Fichero de datos de salida
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de datos propios no son correctos
	 */
	public void guardarDatosPropios (DatosPropios datosPropios, File ficheroXMLDatosPropios)
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Guarda el contenido del objeto de datos propios en un string (en formato XML)
	 * @param datosPropios Objeto de datos propios
	 * @return Cadena con el contenido del objeto de datos propios en formato XML
	 * @throws es.caib.xml.GuardaObjetoXMLException Se ha producido un error al guardar los datos en el fichero
	 * @throws EstablecerPropiedadException Los datos del objeto de datos propios no son correctos
	 */
	public String guardarDatosPropios (DatosPropios datosPropios) 
	throws es.caib.xml.GuardaObjetoXMLException, EstablecerPropiedadException;
	
	/** Crea un objeto de Solicitud sin datos (asociada a una solicitud de un documento de datos propios)
	 * @return Objeto de solicitud
	 */
	public Solicitud crearSolicitud ();
	
	/** Crea un objeto de Dato sin contenido (asociada a un dato de un documento de datos propios)
	 * @return Objeto de dato
	 */
	public Dato crearDato ();
	
	/** Crea un objeto de instrucciones sin datos (asociado a las instrucciones de un documento de datos propios)
	 * @return Objeto de instrucciones
	 */
	public Instrucciones crearInstrucciones ();		
	
	/** Crea un Documento sin datos (asociado a documento de datos propios)
	 * @return Objeto de documento
	 */
	public Documento crearDocumento ();
	
	/** Crea un DocumentosEntregar (asociado a un DocumentosEntregar de un documento de datos propios)
	 * @return Objeto de DocumentosEntregar
	 */
	public DocumentosEntregar crearDocumentosEntregar ();				
	
	/**
	 * Crea tramite subsanacion.
	 * @return Tramite subsanacion
	 */
	public TramiteSubsanacion crearTramiteSubsanacion ();	
	
	/**
	 * Crea FormulariosJustificante.
	 * @return FormulariosJustificante
	 */
	public FormulariosJustificante crearFormulariosJustificante();
	
	/**
	 * Crea PersonalizacionJustificante.
	 * @return PersonalizacionJustificante
	 */
	public PersonalizacionJustificante crearPersonalizacionJustificante();
	
	/**
	 * Crea AlertasTramitacion
	 * @return AlertasTramitacion
	 */
	public AlertasTramitacion crearAlertasTramitacion();
	
}
