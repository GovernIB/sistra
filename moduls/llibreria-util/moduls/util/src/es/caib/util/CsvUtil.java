package es.caib.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;


/**
 * Utilidades para importar/exportar a/de CSV.
 * @author rsanz
 *
 */
public class CsvUtil {
	
	
	private static final char CHAR_DELIMITIER = ';';

	/**
	 * Importa csv.
	 * 
	 * @param is InputStream
	 * @throws Exception
	 */
	public static CsvDocumento importar(InputStream is) throws Exception{

		CsvDocumento csvDoc = new CsvDocumento();
		
		Reader r = new InputStreamReader(is, "ISO-8859-1");
        CsvReader products = new CsvReader(r, CHAR_DELIMITIER);

        products.readHeaders();

        String[] headers = products.getHeaders();
        csvDoc.setColumnas(headers);
         
        while (products.readRecord())
        {
        	int fila = csvDoc.addFila();
        	for (int i = 0; i < headers.length; i++) {
        		String columna = headers[i];
				String valor = products.get(columna);
        		csvDoc.setValor(fila, columna, valor);
        	}
        }
        
        products.close();

        return csvDoc;

	}
	
	/**
	 * Exporta a csv.
	 * 
	 * @param is InputStream
	 * @throws Exception
	 */
	public static byte[] exportar(CsvDocumento csv) throws Exception{
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
		Writer writer = new OutputStreamWriter(bos, "ISO-8859-1");
		CsvWriter csvOutput = new CsvWriter(writer,CHAR_DELIMITIER);

		// Headers
		for (int columna = 0; columna < csv.getColumnas().length; columna++) {
			csvOutput.write(csv.getColumnas()[columna]);		
		}	
		csvOutput.endRecord();
		
		// Datos
		for (int fila = 0; fila < csv.getNumeroFilas(); fila++) {			
			for (int columna = 0; columna < csv.getColumnas().length; columna++) {
			String col = csv.getColumnas()[columna];
			csvOutput.write(csv.getValor(fila, col));	
			}
			csvOutput.endRecord();
		}
            
        csvOutput.close();
        
        byte[] res = bos.toByteArray();
        return res;
	}

}
