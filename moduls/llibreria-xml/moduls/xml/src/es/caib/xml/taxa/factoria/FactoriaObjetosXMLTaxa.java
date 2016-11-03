package es.caib.xml.taxa.factoria;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.FactoriaObjetosXML;
import es.caib.xml.GuardaObjetoXMLException;
import es.caib.xml.taxa.factoria.impl.Declarant;
import es.caib.xml.taxa.factoria.impl.Domicili;
import es.caib.xml.taxa.factoria.impl.Taxa;


public interface FactoriaObjetosXMLTaxa extends FactoriaObjetosXML {	
	public Taxa crearTaxa ();
				
	public Taxa crearTaxa (InputStream datosXMLTaxa) 
	throws es.caib.xml.CargaObjetoXMLException;
	
	
	public Taxa crearTaxa (File ficheroXMLTaxa)
	throws es.caib.xml.CargaObjetoXMLException;	
	
	
	public void guardarTaxa (Taxa taxa, OutputStream datosXMLTaxa) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
			
	public void guardarTaxa (Taxa taxa, File ficheroXMLTaxa)
	throws GuardaObjetoXMLException, EstablecerPropiedadException;				
	
	public String guardarTaxa (Taxa taxa) 
	throws GuardaObjetoXMLException, EstablecerPropiedadException;
						
	public boolean isIdentacion ();
			
	public void setIndentacion (boolean indentacion);
					
	public Declarant crearDeclarant ();
	
	public Domicili crearDomicili ();
}