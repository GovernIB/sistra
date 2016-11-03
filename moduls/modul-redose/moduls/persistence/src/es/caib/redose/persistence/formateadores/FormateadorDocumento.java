package es.caib.redose.persistence.formateadores;

import java.util.List;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.modelInterfaz.DocumentoRDS;

public interface FormateadorDocumento {
	
	/**
	 * XPATH al numero de registro/envio/preregistro/preenvio asociado al documento a partir de los usos
	 */
	public final static String XPATH_REGISTRONUMERO = "REGISTRO.NUMERO";
	/**
	 * XPATH a la fecha de registro/envio/preregistro/preenvio asociado al documento a partir de los usos
	 */
	public final static String XPATH_FECHAREGISTRO = "REGISTRO.FECHA";
	/**
	 * XPATH al digito de control
	 */
	public final static String XPATH_DIGITOCONTROL = "REGISTRO.DC";
	
	/**
	 * Formatea documento de RDS en base a una plantilla. 
	 * 
	 * 
	 * @param documento Se le pasa el documento RDS original
	 * @param plantilla Plantilla con la que se formatea
	 * @param usos Usos del documento
	 * @return Debe devolver un documento RDS con las propiedades del original excepto nombreFichero y datosFichero acordes con el formato aplicado
	 * @throws Exception
	 */
	public DocumentoRDS formatearDocumento(DocumentoRDS documento, PlantillaIdioma plantilla,List usos) throws Exception;
	
}
