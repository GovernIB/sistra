package es.caib.redose.persistence.formateadores;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.util.StringUtil;
import es.caib.xml.pago.XmlDatosPago;
import es.indra.util.pdf.PDFDocument;
import es.indra.util.pdf.Propiedad;
import es.indra.util.pdf.Seccion;


/**
 * Generador de PDFs para Pagos (multientidad). 
 *
 */
public class FormateadorPdfPagosMultientidad implements FormateadorDocumento{
	
	/**
	 * Genera PDF a partir Xpath
	 */
	public DocumentoRDS formatearDocumento(DocumentoRDS documento, PlantillaIdioma plantilla,List usos) throws Exception {
				
		byte[] datosPlantilla = plantilla.getArchivo().getDatos();
		byte[] datosFichero = documento.getDatosFichero();
		
		byte[] pdfContent = generarPDF(datosPlantilla, datosFichero);
    	
		// Devolvemos pdf generado
    	DocumentoRDS documentoF = UtilRDS.cloneDocumentoRDS(documento);
    	documentoF.setDatosFichero(pdfContent);		
    	documentoF.setNombreFichero(StringUtil.normalizarNombreFichero(documento.getTitulo()) + ".pdf");	
		
		return documentoF;
	}

	public byte[] generarPDF(byte[] datosPlantilla, byte[] datosFichero)
			throws IOException, Exception {
		PDFDocument docPDF;
		String cabecera;
		String letras[] = {"A","B","C","D","E","F","G"};
		int numSecciones = 0;
		Seccion seccion;
				
		// Cargamos fichero de properties con los textos a utilizar
		Properties props = new Properties();		
		props.load(new ByteArrayInputStream(datosPlantilla));
		
		
		XmlDatosPago datosFormulario = new XmlDatosPago();		
		datosFormulario.setBytes(datosFichero);
		
    	// Codigo entidad
    	String codigoEntidad = datosFormulario.getCodigoEntidad();
    	
    	// Tipo pago (P/T)
    	String tipoPago = datosFormulario.getTipoPago() + "";
    	
		// CABECERA: TITULO Y LOGO
		cabecera = props.getProperty("cabecera.titulo");
		// Verificamos si tiene logo personalizado por entidad
		String urlLogo = props.getProperty("urlLogo");
		if (StringUtils.isNotBlank(codigoEntidad)) {
			String urlLogoEntidad = props.getProperty("urlLogo." + codigoEntidad);
			if (StringUtils.isNotBlank(urlLogoEntidad)) {
				urlLogo = urlLogoEntidad;
			}
		}
		
    	// Creamos documento con la imagen y el título
		docPDF = new PDFDocument(urlLogo,cabecera);		
		    	
		// SECCION DECLARANTE
		seccion = new Seccion(letras[numSecciones], props.getProperty("declarante.titulo"));
    	numSecciones ++;    		
		seccion.addCampo(new Propiedad(props.getProperty("declarante.nif"), datosFormulario.getNif()));
		seccion.addCampo(new Propiedad(props.getProperty("declarante.nombre"), datosFormulario.getNombre()));
		docPDF.addSeccion(seccion);
		
		// SECCION TASA		
		seccion = new Seccion(letras[numSecciones], props.getProperty("tasa.titulo"));
    	numSecciones ++;    		
		seccion.addCampo(new Propiedad(props.getProperty("tasa.modelo"), datosFormulario.getModelo()));
		seccion.addCampo(new Propiedad(props.getProperty("tasa.concepto"), datosFormulario.getConcepto()));		
		String importe = datosFormulario.getImporte();
		if ( !StringUtils.isEmpty(importe )) {
			double dImporte = new Double( importe ).doubleValue();
			dImporte = dImporte / 100;
			String importeFormat = "" + dImporte;
			importeFormat = importeFormat.replaceAll( "\\.", "," );
			seccion.addCampo(new Propiedad(props.getProperty("tasa.importe"), importeFormat));
		}		
		Date fechaDevengo = datosFormulario.getFechaDevengo();
    	if (fechaDevengo != null )
    	{
    		String fechaDevengoFormat = StringUtil.fechaACadena(fechaDevengo, StringUtil.FORMATO_FECHA); 
    		seccion.addCampo(new Propiedad(props.getProperty("tasa.fecha"), fechaDevengoFormat));
    	}		
		docPDF.addSeccion(seccion);
		
    	// SECCION PAGO
		seccion = new Seccion(letras[numSecciones], props.getProperty("pago.titulo"));
    	numSecciones ++;    		
    	if ( "P".equals(tipoPago)) {
    		seccion.addCampo(new Propiedad(props.getProperty("pago.tipo"), props.getProperty("pago.tipoPresencial")));
    	} else {
    		seccion.addCampo(new Propiedad(props.getProperty("pago.tipo"), props.getProperty("pago.tipoTelematico")));
    	}
    	seccion.addCampo(new Propiedad(props.getProperty("pago.localizador"), datosFormulario.getLocalizador()));
		docPDF.addSeccion(seccion);		
				
		// SECCION CONFIRMACION (solo telematicos)
		if ( "T".equals(tipoPago)) {
			seccion = new Seccion(letras[numSecciones], props.getProperty("confirmacion.titulo"));		
			numSecciones ++;    			
			seccion.addCampo(new Propiedad(props.getProperty("confirmacion.identificador"), datosFormulario.getNumeroDUI()));
			Date fechaPago = datosFormulario.getFechaPago();
	    	if ( fechaPago != null )
	    	{
	    		String fechaPagoFormat = StringUtil.fechaACadena(fechaPago, StringUtil.FORMATO_FECHA); 
	    		seccion.addCampo(new Propiedad(props.getProperty("confirmacion.fecha"), fechaPagoFormat));
	    	}
	    	
	    	// Instrucciones confirmacion entidad
	    	String instruccionesTitulo = props.getProperty("confirmacion.instruccionesEntidad." + codigoEntidad + ".titulo");
	    	String instruccionesTexto = props.getProperty("confirmacion.instruccionesEntidad." + codigoEntidad + ".texto");
	    	if (StringUtils.isNotBlank(instruccionesTitulo) && StringUtils.isNotBlank(instruccionesTexto) ) {
	    		seccion.addCampo(new Propiedad(instruccionesTitulo, instruccionesTexto));
	    	}
	    	docPDF.addSeccion(seccion);	
		}
	
		
    	// Generamos pdf
    	ByteArrayOutputStream bos;
    	bos = new ByteArrayOutputStream();    		
    	docPDF.generate(bos);    	
    	byte[] pdfContent = bos.toByteArray();
    	bos.close();
		return pdfContent;
	}

		
}

