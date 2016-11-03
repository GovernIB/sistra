package es.caib.xml.registro.factoria.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.caib.xml.CampoObligatorioException;
import es.caib.xml.EstablecerPropiedadException;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;

/**  Objeto de AsientoRegistral que encapsula el nodo XML ASIENTO_REGISTRAL de los documentos
 * XML de justificante. Para la implementación se usa un objeto
 * generado mediante el API JAXB (Java Application XML Binding).
 * 
 * @author magroig
 *
 */
public class AsientoRegistral extends NodoRegistroBase {
	
	private String version;
	private DatosOrigen datosOrigen;
	private DatosDestino datosDestino;	
	private DatosAsunto datosAsunto;
	private List datosInteresado = new ArrayList ();
	private List datosAnexoDocumentacion = new ArrayList ();
			
	/**
	 * Construye el objeto sin datos
	 */
	public AsientoRegistral (){}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#getDatosOrigen()
	 */
	public DatosOrigen getDatosOrigen() {				
		return this.datosOrigen;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#setDatosOrigen(es.caib.xml.registro.factoria.DatosOrigen)
	 */
	public void setDatosOrigen(DatosOrigen datosOrigen) throws EstablecerPropiedadException {
		// Validar campo requerido
		validaCampoObligatorio("AsientoRegistral", "DatosOrigen", datosOrigen);
				
		this.datosOrigen = 	datosOrigen;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#getDatosAsunto()
	 */
	public DatosAsunto getDatosAsunto() {
		return this.datosAsunto;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#setDatosAsunto(es.caib.xml.registro.factoria.DatosAsunto)
	 */
	public void setDatosAsunto(DatosAsunto datosAsunto) throws EstablecerPropiedadException {
		// Validar campo requerido
		validaCampoObligatorio("AsientoRegistral", "DatosAsunto", datosAsunto);
		
		this.datosAsunto = datosAsunto;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#getDatosDestino()
	 */
	public DatosDestino getDatosDestino() {
		return this.datosDestino;	
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#setDatosDestino(es.caib.xml.registro.factoria.DatosDestino)
	 */
	public void setDatosDestino(DatosDestino datosDestino) throws EstablecerPropiedadException {
		this.datosDestino = datosDestino;		
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#getVersion()
	 */
	public String getVersion() {		
		return this.version;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.registro.factoria.AsientoRegistral#setVersion(java.lang.String)
	 */
	public void setVersion(String version) throws EstablecerPropiedadException {
		// Validar campo requerido
		validaCampoObligatorio("AsientoRegistral", "Version", version);
		
		this.version = version;	
	}
	
	public List getDatosInteresado() {
		return this.datosInteresado;
	}

	public List getDatosAnexoDocumentacion() { 
		return this.datosAnexoDocumentacion;
	}

	/* (non-Javadoc)
	 * @see es.caib.xml.NodoXMLObj#comprobarDatosRequeridos()
	 */
	public void comprobarDatosRequeridos() throws EstablecerPropiedadException {
		DatosOrigen datosOrigen = null;		
		DatosAsunto datosAsunto = null;		
		String version = null;
		
		datosOrigen = getDatosOrigen ();
		if (datosOrigen == null) throw new CampoObligatorioException ("AsientoRegistral", "DatosOrigen");
		else datosOrigen.comprobarDatosRequeridos();
											
		if (!this.getDatosInteresado().iterator().hasNext()) throw new CampoObligatorioException ("AsientoRegistral", "DatosInteresado");
		
		datosAsunto = getDatosAsunto ();		
		if (datosAsunto == null) throw new CampoObligatorioException ("AsientoRegistral", "DatosAsunto");
		else datosAsunto.comprobarDatosRequeridos();
		
		if (!getDatosAnexoDocumentacion().iterator().hasNext()) throw new CampoObligatorioException ("AsientoRegistral", "DatosAnexoDocumentacion"); 		
		
		version = getVersion ();
		if ( (version == null) || (version.trim().equals("")))
			throw new CampoObligatorioException ("AsientoRegistral", "Version");	
		
		
		// Si el registro es de entrada y tiene especificado IDENTIFICADOR_TRAMITE debe existir documento de Datos Propios
		if ( (
				this.datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA ||
				this.datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_ENVIO ||
				this.datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREENVIO ||
				this.datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_PREREGISTRO
			  )
			  && this.datosAsunto.getIdentificadorTramite() != null && this.datosAsunto.getIdentificadorTramite().length() > 0  ){
				 boolean existe = false;
				 for (Iterator it = this.datosAnexoDocumentacion.iterator();it.hasNext();){
					 DatosAnexoDocumentacion da = (DatosAnexoDocumentacion)	it.next();
					 if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS){
						 existe = true;
						 break;
					 }
				 }
				 if (!existe) 
					 throw new EstablecerPropiedadException("Si se especifica registro de entrada e identificador de trámite debe existir documento de datos propios", "DATOS_ORIGEN","TIPO_REGISTRO",Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA));				 
			
		}
		
		// Si el registro es de salida debe existir documento de Aviso y de Oficio de Remision
		if (this.datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_REGISTRO_SALIDA){
			boolean existeAviso = false;
			boolean existeOficio = false;
			 for (Iterator it = this.datosAnexoDocumentacion.iterator();it.hasNext();){
				 DatosAnexoDocumentacion da = (DatosAnexoDocumentacion)	it.next();
				 if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION){
					 existeAviso = true;
				 }
				 if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION){
					 existeOficio = true;
				 }
			 }
			 if (!existeAviso) 
				 throw new EstablecerPropiedadException("Si se especifica registro de salida debe existir documento de aviso de notificación", "DATOS_ORIGEN","TIPO_REGISTRO",Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_SALIDA));
			 if (!existeOficio) 
				 throw new EstablecerPropiedadException("Si se especifica registro de salida debe existir documento de oficio de remisión", "DATOS_ORIGEN","TIPO_REGISTRO",Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_SALIDA));
		}
		
		// Si el registro es de registro de acuse de recibo debe existir documento de aviso y debe estar alimentado 
		// el numero de registro y la fecha de registro de la notificacion
		if (this.datosOrigen.getTipoRegistro().charValue() == ConstantesAsientoXML.TIPO_ACUSE_RECIBO){
			boolean existe = false;
			 for (Iterator it = this.datosAnexoDocumentacion.iterator();it.hasNext();){
				 DatosAnexoDocumentacion da = (DatosAnexoDocumentacion)	it.next();
				 if (da.getTipoDocumento().charValue() == ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION){
					 existe = true;
					 break;
				 }
			 }
			 if (!existe) 
				 throw new EstablecerPropiedadException("Si se especifica registro de acuse de recibo debe existir documento de aviso de notificación", "DATOS_ORIGEN","TIPO_REGISTRO",Character.toString(ConstantesAsientoXML.TIPO_ACUSE_RECIBO));
			 
			 if (this.getDatosOrigen().getNumeroRegistro() == null || this.getDatosOrigen().getNumeroRegistro().length() <= 0)
				 throw new EstablecerPropiedadException("Si se especifica registro de acuse de recibo debe especificar número de registro", "DATOS_ORIGEN","TIPO_REGISTRO",Character.toString(ConstantesAsientoXML.TIPO_ACUSE_RECIBO));
			 
			 if (this.getDatosOrigen().getFechaEntradaRegistro() == null)
				 throw new EstablecerPropiedadException("Si se especifica registro de acuse de recibo debe especificar fecha de registro", "DATOS_ORIGEN","TIPO_REGISTRO",Character.toString(ConstantesAsientoXML.TIPO_ACUSE_RECIBO));
		}
		
		
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals (Object obj){
		if (obj instanceof AsientoRegistral){
			
			if (obj == null) return false;
			
			AsientoRegistral asiento = (AsientoRegistral) obj;						
			
			// Comprobar version
			String version = getVersion ();
			String versionExt = asiento.getVersion();
			
			if ((version != null) || (versionExt != null))
				if ( (versionExt != null) && (version != null) ){
					if (!version.equals (versionExt)) return false;
				}
				else
					if ((version != null) || (versionExt != null)) return false;
			
			// Comprobar datos origen
			DatosOrigen datosOrigen = getDatosOrigen ();
			DatosOrigen datosOrigenExt = asiento.getDatosOrigen();
			
			if ((datosOrigen != null) || (datosOrigenExt != null))
				if ( (datosOrigenExt != null) && (datosOrigen != null) ){
					if (!datosOrigen.equals (datosOrigenExt)) return false;
				}
				else
					if ((datosOrigen != null) || (datosOrigenExt != null)) return false;
			
			
			// Comprobar datos interesado
			Iterator itDatosInteresado =  (getDatosInteresado() != null) ? getDatosInteresado().iterator() : null;
			Iterator itDatosInteresadoExt = (asiento.getDatosInteresado() != null) ? asiento.getDatosInteresado().iterator() : null;
			
			if ( 
				( (itDatosInteresado == null) && (itDatosInteresadoExt != null) ) ||
				((itDatosInteresado != null) && (itDatosInteresadoExt == null))
				)
			{
				return false;				
			}
			
			if (itDatosInteresado.hasNext() != itDatosInteresadoExt.hasNext()) return false;
									
			while (itDatosInteresado.hasNext()){
				DatosInteresado datosInteresado = (DatosInteresado) itDatosInteresado.next();
				DatosInteresado datosInteresadoExt = (DatosInteresado) itDatosInteresadoExt.next();
				
				if ( !datosInteresado.equals(datosInteresadoExt) ) return false;
				
				if (itDatosInteresado.hasNext() != itDatosInteresadoExt.hasNext()) return false;
			}
			
			// Comprobar datos asunto
			DatosAsunto datosAsunto = getDatosAsunto ();
			DatosAsunto datosAsuntoExt = asiento.getDatosAsunto();
			
			if ((datosAsunto != null) || (datosAsuntoExt != null))
				if ( (datosAsuntoExt != null) && (datosAsunto != null) ){
					if (!datosAsunto.equals (datosAsuntoExt)) return false;
				}
				else
					if ((datosAsunto != null) || (datosAsuntoExt != null)) return false;
			
			// Comprobar datos anexo documentacion			
			Iterator itAnexos = (getDatosAnexoDocumentacion () != null) ? getDatosAnexoDocumentacion ().iterator() : null;
			Iterator itAnexosExt = (asiento.getDatosAnexoDocumentacion() != null) ? asiento.getDatosAnexoDocumentacion().iterator() : null;
			
			if ( 
					( (itAnexos == null) && (itAnexosExt != null) ) ||
					((itAnexos != null) && (itAnexosExt == null))
				)
			{
				return false;				
			}
			
			
			if (itAnexos.hasNext() != itAnexosExt.hasNext()) return false;
			
			while (itAnexos.hasNext()){
				DatosAnexoDocumentacion anexo = (DatosAnexoDocumentacion) itAnexos.next();
				DatosAnexoDocumentacion anexoExt = (DatosAnexoDocumentacion) itAnexosExt.next();
				
				if (!anexo.equals (anexoExt)) return false;
				if (itAnexos.hasNext() != itAnexosExt.hasNext()) return false;				
			}
			
			// Comprobar datos destino
			DatosDestino datosDestino = getDatosDestino ();
			DatosDestino datosDestinoExt = asiento.getDatosDestino();
			
			if ((datosDestino != null) || (datosDestinoExt != null))
				if ( (datosDestinoExt != null) && (datosDestino != null) ){
					if (!datosDestino.equals (datosDestinoExt)) return false;
				}
				else
					if ((datosDestino != null) || (datosDestinoExt != null)) return false;
						
			// OK los objetos son equivalentes
			return true;
		}
		
		return super.equals (obj);
	}
	
}
