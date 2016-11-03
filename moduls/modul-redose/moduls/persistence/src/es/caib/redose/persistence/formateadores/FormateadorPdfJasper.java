package es.caib.redose.persistence.formateadores;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import es.indra.util.pdf.UtilPDF;

/**
 * Generador de PDFs basado en Jasper Reports
 *
 */
public class FormateadorPdfJasper implements FormateadorDocumento{
	
	private Log _log = LogFactory.getLog( FormateadorPdfJasper.class );


	public DocumentoRDS formatearDocumento(DocumentoRDS documento, PlantillaIdioma plantilla,List usos) throws Exception {
		try{
			// Obtenemos plantilla
			_log.debug("Preparamos plantilla para jasper reports");
			
			// Comprobamos si tenemos un jrxml o un zip
			List files;
			if (plantilla.getNombreFichero().endsWith(".zip")){
				files = extractFilesFromZip(plantilla.getArchivo().getDatos());
			}else if (plantilla.getNombreFichero().endsWith(".jrxml")){
				files = new ArrayList();
				FileJasper fj = new FileJasper();
				fj.setFileName("report.jrxml");
				fj.setData(plantilla.getArchivo().getDatos());
				files.add(fj);
			}else{
				throw new Exception("Extension no permitida (debe ser jrxml o zip): " + plantilla.getNombreFichero());
			}
			
			// Compilamos plantillas (debe existir el report principal con nombre "report.jrmxl")
			_log.debug("Compilamos plantillas y añadimos como parametros subreports y recursos");
			JasperReport jasperReportMain = null;
			Map params = new HashMap();
			InputStream isData = null;		
			for (Iterator it=files.iterator();it.hasNext();){
				FileJasper fj = (FileJasper) it.next();
				isData = new ByteArrayInputStream(fj.getData());
				// Comprobamos si es un report o un recurso (imagen,etc.)
				if (fj.getFileName().endsWith(".jrxml")){
					// Comprobamos si el report principal	
					if ("report.jrxml".equals(fj.getFileName())){
						jasperReportMain = JasperCompileManager.compileReport(isData);	
					}else{
						// Si no es el main, lo compilamos y lo añadimos como parametro
						params.put(fj.getFileName().substring(0,fj.getFileName().lastIndexOf('.')),
									JasperCompileManager.compileReport(isData));
					}
				}else{
					// Añadimos el recurso como InputStream
					params.put(fj.getFileName().substring(0,fj.getFileName().lastIndexOf('.')),
							isData);
				}				
			}
			if (jasperReportMain == null){
				throw new Exception("No hay configurado ningun jrxml como report principal (report.jrxml)");
			}
			
			// Pasamos como parametro el xml del documento
			InputStream isXML = new ByteArrayInputStream(documento.getDatosFichero());
			JRXmlDataSource xml = new JRXmlDataSource(isXML);
			
			// Generamos pdf
			_log.debug("Generamos pdf con jasper reports");
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReportMain, params, xml);		
			byte[] pdf=JasperExportManager.exportReportToPdf(jasperPrint);
			
			// Establecemos permisos a solo impresion
			ByteArrayOutputStream bos = new ByteArrayOutputStream(pdf.length);
			UtilPDF.establecerSoloImpresion(bos, new ByteArrayInputStream(pdf));			
			pdf = bos.toByteArray();
			bos.close();
			
			
			// Retornamos documento formateado
			_log.debug("PDF generado con jasper reports, devolvemos resultado");
			DocumentoRDS documentoF = UtilRDS.cloneDocumentoRDS(documento);
			documentoF.setNombreFichero(StringUtil.normalizarNombreFichero(documento.getTitulo()) + ".pdf");
			documentoF.setDatosFichero(pdf);
			return documentoF;
		}catch(Exception ex){
			throw new Exception("Excepcion generando plantilla basada en jasper reports",ex);
		}
	}
	
	
	private List extractFilesFromZip(byte[] zipData) throws Exception{
		ByteArrayInputStream bis = null;
		ZipInputStream zipinputstream = null;
		try{
			bis = new ByteArrayInputStream(zipData);
			zipinputstream = new ZipInputStream(bis);
			byte[] buf = new byte[1024];
			List files = new ArrayList();
	             
			ZipEntry zipentry = null;
			do{
				zipentry = zipinputstream.getNextEntry();
				if (zipentry != null){
	                String entryName = zipentry.getName();
	                int n;
	                ByteArrayOutputStream bos = new ByteArrayOutputStream(Integer.parseInt(Long.toString(zipentry.getSize())));
	                FileJasper newFile = new FileJasper();
	                newFile.setFileName(entryName);
	                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
	                    bos.write(buf, 0, n);
	
	                newFile.setData(bos.toByteArray());
	                bos.close();
	                zipinputstream.closeEntry();
	                files.add(newFile);
				}
			}while(zipentry!=null);
			
			return files;
		}catch(Exception ex){
			throw new Exception("Excepcion al extraer ficheros del zip de la plantilla jasper",ex);
		}finally{
			try{bis.close();}catch(Exception ex){}
			try{zipinputstream.close();}catch(Exception ex){}			
		}
	}
	
	
	private class FileJasper{
		
		private String fileName;
		private byte[] data;
		
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		
	}
	
}

