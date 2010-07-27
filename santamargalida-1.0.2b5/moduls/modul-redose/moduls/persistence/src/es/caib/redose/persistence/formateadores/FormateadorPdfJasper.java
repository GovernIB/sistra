package es.caib.redose.persistence.formateadores;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.util.StringUtil;

/**
 * Generador de PDFs basado en Jasper Reports
 *
 */
public class FormateadorPdfJasper implements FormateadorDocumento{
	
	private Log _log = LogFactory.getLog( FormateadorPdfJasper.class );


	public DocumentoRDS formatearDocumento(DocumentoRDS documento, PlantillaIdioma plantilla,List usos) throws Exception {
		
		InputStream isPlantilla = null;		
		InputStream isXML = null;
				
		try{
			// Obtenemos plantilla
			_log.debug("Preparamos plantilla para jasper reports");			
			isPlantilla = new ByteArrayInputStream(plantilla.getArchivo().getDatos());
			JasperReport jasperReport = JasperCompileManager.compileReport(isPlantilla);		
			
			// Pasamos como parametro el xml del documento
			isXML = new ByteArrayInputStream(documento.getDatosFichero());
			JRXmlDataSource xml = new JRXmlDataSource(isXML);
			
			// Generamos pdf
			_log.debug("Generamos pdf con jasper reports");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), xml);		
			byte[] pdf=JasperExportManager.exportReportToPdf(jasperPrint);
			
			
			// Retornamos documento formateado
			_log.debug("PDF generado con jasper reports, devolvemos resultado");
			DocumentoRDS documentoF = UtilRDS.cloneDocumentoRDS(documento);
			documentoF.setNombreFichero(StringUtil.normalizarNombreFichero(documento.getTitulo()) + ".pdf");
			documentoF.setDatosFichero(pdf);
			return documentoF;

		} finally{			
			// Cerramos recursos
			try{ if (isPlantilla != null) isPlantilla.close();}catch (Exception ex){}								
		}		
	}
		
}
