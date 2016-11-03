package es.caib.xml.registro.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;

/** Objeto de IdentificacionInteresadoDesglosada.
 * 
 * @author magroig
 *
 */
public class IdentificacionInteresadoDesglosada extends NodoRegistroBase {
	//Ctes de maxima longitud de campos
	private static final int MAX_LON_NOMBRE = 255;
	private static final int MAX_LON_APELLIDO1 = 100;
	private static final int MAX_LON_APELLIDO2 = 100;
		
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	IdentificacionInteresadoDesglosada(){}	
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) throws EstablecerPropiedadException {
		validaLongitudCampo ("IdentificacionInteresadoDesglosada", "nombre", nombre, MAX_LON_NOMBRE);
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) throws EstablecerPropiedadException {
		validaLongitudCampo ("IdentificacionInteresadoDesglosada", "apellido1", apellido1, MAX_LON_APELLIDO1);
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) throws EstablecerPropiedadException {
		validaLongitudCampo ("IdentificacionInteresadoDesglosada", "apellido2", apellido2, MAX_LON_APELLIDO2);
		this.apellido2 = apellido2;
	}

	
	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {				
		if (getNombre() == null) throw new CampoObligatorioException ("IdentificacionInteresadoDesglosada", "nombre");		
	}

		
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof IdentificacionInteresadoDesglosada){
			
			if (obj == null) return false;
			
			IdentificacionInteresadoDesglosada identDesglosada = (IdentificacionInteresadoDesglosada) obj;
			
			// Comprobar nombre
			String nom = getNombre();
			String nomExt = identDesglosada.getNombre();
			
			if ((nom != null) || (nomExt != null))
				if ( (nomExt != null) && (nom != null) ){
					if (!nom.equals (nomExt)) return false;
				}
				else
					if ((nom != null) || (nomExt != null)) return false;
			
			// Comprobar ape1
			String ape1 = getApellido1();
			String ape1Ext = identDesglosada.getApellido1 ();
			
			if ((ape1 != null) || (ape1Ext != null))
				if ( (ape1Ext != null) && (ape1 != null) ){
					if (!ape1.equals (ape1Ext)) return false;
				}
				else
					if ((ape1 != null) || (ape1Ext != null)) return false;
			
			// Comprobar ape2
			String ape2 = getApellido2();
			String ape2Ext = identDesglosada.getApellido2 ();
			
			if ((ape2 != null) || (ape2Ext != null))
				if ( (ape2Ext != null) && (ape2 != null) ){
					if (!ape2.equals (ape2Ext)) return false;
				}
				else
					if ((ape2 != null) || (ape2Ext != null)) return false;

			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString (){
		StringBuffer str = new StringBuffer ("IdentificacionInteresadoDesglosada:");
		str.append ("Nombre-" + getNombre() + ";");
		str.append ("Apellido1-" + getApellido1() + ";");
		str.append ("Apellido2-" + getApellido2() + ";");
		return str.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode (){
		return toString ().hashCode();
	}

}
