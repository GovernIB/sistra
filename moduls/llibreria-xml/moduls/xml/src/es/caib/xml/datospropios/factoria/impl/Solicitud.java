package es.caib.xml.datospropios.factoria.impl;

import java.util.Iterator;
import java.util.List;
import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;

public class Solicitud extends NodoBaseDatosPropios  {
	
	private List lstSolicitudes;
	
	Solicitud(){
		lstSolicitudes = new java.util.ArrayList ();
	}
				
	public List getDato (){
		return lstSolicitudes;
	}
	
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		// No hay datos requeridos
	}
	
	public boolean equals (Object obj){
		if (obj instanceof Solicitud){
			
			if (obj == null) return false;
			
			Solicitud solicitud = (Solicitud) obj;
			
			Iterator itDatos = lstSolicitudes.iterator();
			Iterator itDatosExt = ((Solicitud) solicitud).lstSolicitudes.iterator ();
			
			if (itDatos.hasNext() != itDatosExt.hasNext ()) return false;
			
			while (itDatos.hasNext()){
				Dato dato = (Dato) itDatos.next ();
				Dato datoExt = (Dato) itDatosExt.next ();
				
				if (!dato.equals(datoExt)) return false;
				
				if (itDatos.hasNext () != itDatosExt.hasNext ()) return false;
				
			}
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}
		
}
