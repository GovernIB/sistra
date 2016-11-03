package caib_prueba_xml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;


import es.caib.xml.pago.XmlDatosPago;

public class PruebaLecturaDatosPago {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.copy(new FileInputStream("moduls/llibreria-xml/moduls/test/datosPago.xml"), bos);
		
		XmlDatosPago xmlPago = new XmlDatosPago();
		xmlPago.setBytes(bos.toByteArray());
		
		System.out.println(xmlPago.getString());
		

	}

}
