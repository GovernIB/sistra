package es.caib.redose.modelInterfaz;

import java.io.Serializable;

/**
 * 
 * Permite indicar transformaciones a realizar sobre un documento a insertar en el RDS.
 * 
 */
public class TransformacionRDS implements Serializable{
	
	/**
	 * Indica si el documento se convertirá a PDF/A<br/>
	 * Debe ser un fichero no estructurado (no XML) y tener una extensión permitida: "doc","docx","ppt","xls","odt","jpg","txt"
	 */
	private boolean convertToPDF;
	
	/**
	 * Indica si el documento PDF se sellará con el barcode que permite verificar documentos en el RDS 
	 */
	private boolean barcodePDF;
	

	/**
	 * Indica si el documento PDF se sellará con el barcode que permite verificar documentos en el RDS 
	 */
	public boolean isBarcodePDF() {
		return barcodePDF;
	}

	/**
	 * Indica si el documento PDF se sellará con el barcode que permite verificar documentos en el RDS 
	 */
	public void setBarcodePDF(boolean barcodePDF) {
		this.barcodePDF = barcodePDF;
	}

	/**
	 * Indica si el documento se convertirá a PDF/A<br/>
	 * Debe ser un fichero no estructurado (no XML) y tener una extensión permitida: "doc","docx","ppt","xls","odt","jpg","txt"
	 */
	public boolean isConvertToPDF() {
		return convertToPDF;
	}

	/**
	 * Indica si el documento se convertirá a PDF/A<br/>
	 * Debe ser un fichero no estructurado (no XML) y tener una extensión permitida: "doc","docx","ppt","xls","odt","jpg","txt"
	 */
	public void setConvertToPDF(boolean convertToPDF) {
		this.convertToPDF = convertToPDF;
	}
	
	/**
	 * Indica si se ha habilitado alguna de las opciones de transformación
	 * @return
	 */
	public boolean existeTransformacion(){
		return (this.isBarcodePDF() || this.isConvertToPDF());
	}
	
}
