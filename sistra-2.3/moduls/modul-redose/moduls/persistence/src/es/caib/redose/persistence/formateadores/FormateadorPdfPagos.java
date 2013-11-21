package es.caib.redose.persistence.formateadores;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.Par;
import es.caib.xml.util.HashMapIterable;
import es.indra.util.pdf.PDFDocumentTemplate;

public class FormateadorPdfPagos extends FormateadorPdfFormularios
{

	private Log _log = LogFactory.getLog( FormateadorPdfPagos.class );
	
	private static String DATOSPAGO_IMPORTE = "DATOS_PAGO.IMPORTE";
	private static String DATOSPAGO_FECHA_DEVENGO = "DATOS_PAGO.FECHA_DEVENGO";
	private static String DATOS_PASARELA_FECHA_PAGO = "DATOS_PASARELA.FECHA_PAGO";
	private static String DATOS_PASARELA_NUMERO_DUI = "DATOS_PASARELA.NUMERO_DUI";
	private static String DATOS_PAGO_TIPO = "DATOS_PAGO.TIPO";
	
	public DocumentoRDS formatearDocumento(DocumentoRDS documento,
			PlantillaIdioma plantilla, List usos) throws Exception
	{
		// Parseamos xml
		Analizador analizador = new Analizador ();					
    	HashMapIterable datosFormulario = analizador.analizar(new ByteArrayInputStream(documento.getDatosFichero()),ConstantesXML.ENCODING);	
		
    	// Creamos Map de valores
    	HashMap datos = new HashMap();
    	for (Iterator it=datosFormulario.iterator();it.hasNext();){
    		Nodo nodo = (Nodo) it.next();
    		datos.put(referenciaCampo(nodo.getXpath()),nodo.getValor());
    		
    		// En caso de ser lista de valores buscamos atributo de codigo
    		if (nodo.getAtributos() != null && nodo.getAtributos().size() > 0){
    			for (Iterator it2 = nodo.getAtributos().iterator();it2.hasNext();){
    				Par atributo = (Par) it2.next();
    				if (atributo.getNombre().equals(INDICE_LISTAS)){
    					datos.put(referenciaCampo(nodo.getXpath()) + CODIGO_LISTAS,atributo.getValor());
    				}
    			}	
    		}    		
    	}
    	String importe = ( String ) datos.get( DATOSPAGO_IMPORTE );
    	if ( !StringUtils.isEmpty(importe ) )
    	{
    		double dImporte = new Double( importe ).doubleValue();
    		dImporte = dImporte / 100;
    		String nuevoImporte = "" + dImporte;
    		nuevoImporte = nuevoImporte.replaceAll( "\\.", "," );
    		datos.put( DATOSPAGO_IMPORTE, "" + nuevoImporte );
    	}
    	String fechaDevengo = ( String ) datos.get( DATOSPAGO_FECHA_DEVENGO );
    	if ( !StringUtils.isEmpty(fechaDevengo ) )
    	{
    		String fechaDevengoFormat = fechaDevengo.substring(6,8) + "/" + fechaDevengo.substring(4,6) +  "/" + fechaDevengo.substring(0,4); 
    		datos.put( DATOSPAGO_FECHA_DEVENGO, fechaDevengoFormat );
    	}
    	String fechaPago = ( String ) datos.get( DATOS_PASARELA_FECHA_PAGO );
    	if ( !StringUtils.isEmpty(fechaPago ) )
    	{
    		String fechaPagoFormat = fechaPago.substring(6,8) + "/" + fechaPago.substring(4,6) +  "/" + fechaPago.substring(0,4) + " " + fechaPago.substring(8,10) + ":" + fechaPago.substring(10,12) + ":" + fechaPago.substring(12,14); 
    		datos.put( DATOS_PASARELA_FECHA_PAGO, fechaPagoFormat );
    	}
    	
    	// Para pagos presenciales no mostramos datos pasarela
    	if ( "P".equals( (String) datos.get(DATOS_PAGO_TIPO)) ) {
    		datos.remove(DATOS_PASARELA_NUMERO_DUI);
    		datos.remove(DATOS_PASARELA_FECHA_PAGO);
    	}
    	    	
		// Creamos pdf a partir plantilla y establecemos valores
		PDFDocumentTemplate pdf = new PDFDocumentTemplate(plantilla.getArchivo().getDatos());
		pdf.establecerSoloImpresion();
		pdf.ponerValor(datos);
		
		// Devolvemos pdf generado
		DocumentoRDS documentoF = UtilRDS.cloneDocumentoRDS(documento);
		documentoF.setDatosFichero(pdf.guardarEnMemoria());		
		documentoF.setNombreFichero(StringUtil.normalizarNombreFichero(documento.getTitulo()) + ".pdf");		
		
		return documentoF;
	}
}
