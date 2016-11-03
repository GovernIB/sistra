package es.caib.xml.justificantepago.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.justificantepago.factoria.impl.DatosPago;
import es.caib.xml.justificantepago.factoria.impl.JustificantePago;


public interface FactoriaObjetosXMLJustificantePago extends FactoriaObjetosXML {	
	public JustificantePago crearJustificantePago ();
	
			
	public JustificantePago crearJustificantePago (InputStream datosXMLJustificantePago) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	
	public JustificantePago crearJustificantePago (File ficheroXMLJustificantePago)
	throws es.caib.xml.CargaObjetoXMLException;	
	
	
	public void guardarJustificantePago (JustificantePago justificantePago, OutputStream datosXMLJustificantePago) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
			
	public void guardarJustificantePago (JustificantePago justificantePago, File ficheroXMLJustificantePago)
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
				
	
	public String guardarJustificantePago (JustificantePago justificantePago) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
						
	public boolean isIdentacion ();
			
	public void setIndentacion (boolean indentacion);
					
	public DatosPago crearDatosPago ();	
}