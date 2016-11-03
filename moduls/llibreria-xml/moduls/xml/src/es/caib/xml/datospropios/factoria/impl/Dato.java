package es.caib.xml.datospropios.factoria.impl;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.ValorFueraListaValoresPermitidosException;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;

/** Objeto de Dato que encapsula el nodo XML Dato de los documentos
 * XML de datos propios. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding). 
 * 
 * @author magroig
 *
 */
public class Dato extends NodoBaseDatosPropios {
	
	private String descripcion;
	private String valor;
	private Character tipo;
	
	Dato (){
		descripcion = null;
		valor = null;
		tipo = null;
	}
	
	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Dato#getTipo()
	 */
	public Character getTipo() {			
		return tipo;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Dato#setTipo(char)
	 */
	public void setTipo(Character tipo) throws EstablecerPropiedadException {
		Character valoresValidos[] = {
				new Character (ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_BLOQUE),
				new Character (ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_INDICE),
				new Character (ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_CAMPO)
		};
		validaCampoConListaValores("Dato", "Tipo", tipo, valoresValidos);
		
		this.tipo = tipo;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Dato#getDescripcion()
	 */
	public String getDescripcion() {		
		return descripcion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Dato#setDescripcion(java.lang.String)
	 */
	public void setDescripcion(String descripcion) throws EstablecerPropiedadException {
		validaCampoObligatorio("Dato", "Descripcion", descripcion);
		
		this.descripcion = descripcion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Dato#getValor()
	 */
	public String getValor() {
		return valor;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.datospropios.factoria.Dato#setValor(java.lang.String)
	 */
	public void setValor(String valor) throws EstablecerPropiedadException {
		this.valor = valor;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		Character tipo = getTipo ();
		if ( tipo == null ) throw new CampoObligatorioException ("Dato", "Tipo");
		
		if ( (tipo.charValue() != ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_BLOQUE) && (tipo.charValue() != ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_CAMPO) && (tipo.charValue() != ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_INDICE)){
			String valoresPermitidos[] = {
					"" + ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_BLOQUE,
					"" + ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_INDICE,
					"" + ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_CAMPO
			};
			throw new ValorFueraListaValoresPermitidosException ("Dato", "Tipo", "" + tipo, valoresPermitidos);
		}
		
		if ( (getDescripcion () == null) || (getDescripcion ().trim().equals("")) ) 
			throw new CampoObligatorioException ("Dato", "Descripcion");					
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof Dato){
			
			if (obj == null) return false;
			 
			Dato dato = (Dato) obj;
			
			String descripcion = getDescripcion ();
			String descripcionExt = dato.getDescripcion ();
			
			if ((descripcion != null) || (descripcionExt != null))
				if ( (descripcionExt != null) && (descripcion != null) ){
					if (!descripcion.equals (descripcionExt)) return false;
				}
				else
					if ((descripcion != null) || (descripcionExt != null)) return false;
			
			String valor = getValor ();
			String valorExt = dato.getValor();
			
			if ((valor != null) || (valorExt != null))
				if ( (valorExt != null) && (valor != null) ){
					if (!valor.equals (valorExt)) return false;
				}
				else
					if ((valor != null) || (valorExt != null)) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}																																																				

}
