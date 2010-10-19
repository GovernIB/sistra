package es.caib.xml.oficioremision.factoria.impl;

import javax.xml.bind.JAXBException;

import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.oficioremision.modelo.OFICIOREMISION;
import es.caib.xml.oficioremision.modelo.ObjectFactory;

/** Objeto de Instrucciones que encapsula el nodo XML INSTRUCCIONES de los documentos
 * XML de oficio remision. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * Campos requeridos: TextoInstrucciones
 * 
 * @author magroig
 *
 */
public class OficioRemision extends NodoBaseOficioRemision  {
			
	private String titulo;
	private String texto;
	private TramiteSubsanacion tramiteSubsanacion;
	
	
	OficioRemision (){
		titulo = null;
		texto = null;
		tramiteSubsanacion = null;
	}
			

	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		validaCampoObligatorio("AvisoNotificacion", "Titulo", this.getTitulo());
		validaCampoObligatorio("AvisoNotificacion", "Texto", this.getTexto());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof OficioRemision){
			
			if (obj == null) return false;
			
			OficioRemision inst = (OficioRemision) obj;
						
			if (!objetosIguales (getTitulo(), inst.getTitulo())) return false;
			if (!objetosIguales (getTexto(), inst.getTexto())) return false;
			if (!objetosIguales (getTramiteSubsanacion(), inst.getTramiteSubsanacion())) return false;
			
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	// étodos para realizar la conversión JAXB -> Jerarquía propia de objetos
	public static OficioRemision fromJAXB (OFICIOREMISION oficioRemisionJAXB) throws JAXBException{
		OficioRemision oficioRemision = null;
		if ( oficioRemisionJAXB != null){
			oficioRemision = new OficioRemision();
			oficioRemision.setTitulo(oficioRemisionJAXB.getTITULO());
			oficioRemision.setTexto(oficioRemisionJAXB.getTEXTO());
			oficioRemision.setTramiteSubsanacion(TramiteSubsanacion.fromJAXB(oficioRemisionJAXB.getTRAMITESUBSANACION()));
		}
		return oficioRemision;
	}
	
	
	//	Métodos para realizar la conversión Jerarquía propia de objetos -> JAXB  
	public static  OFICIOREMISION toJAXB (OficioRemision oficioRemision) throws JAXBException{
		OFICIOREMISION oficioRemisionJAXB = null;
		if ( (oficioRemision != null)){
			oficioRemisionJAXB = (new ObjectFactory()).createOFICIOREMISION();
			oficioRemisionJAXB.setTITULO(oficioRemision.getTitulo());
			oficioRemisionJAXB.setTEXTO(oficioRemision.getTexto());
			oficioRemisionJAXB.setTRAMITESUBSANACION(TramiteSubsanacion.toJAXB(oficioRemision.getTramiteSubsanacion()));
		}
		return oficioRemisionJAXB;
	}


	public TramiteSubsanacion getTramiteSubsanacion() {
		return tramiteSubsanacion;
	}


	public void setTramiteSubsanacion(TramiteSubsanacion tramiteSubsanacion) {
		this.tramiteSubsanacion = tramiteSubsanacion;
	}
}
