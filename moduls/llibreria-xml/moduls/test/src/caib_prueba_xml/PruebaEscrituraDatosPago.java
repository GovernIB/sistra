package caib_prueba_xml;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import es.caib.xml.ConstantesXML;
import es.caib.xml.pago.XmlDatosPago;

public class PruebaEscrituraDatosPago {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Generamos xml de pago
		XmlDatosPago xmlPago = new XmlDatosPago();
		xmlPago.setCodigoEntidad("XXXX");
		xmlPago.setModeloRDSPago("modelo");
		xmlPago.setVersionRDSPago(1);
		xmlPago.setNif("33456299Q");
		xmlPago.setNombre("Rafael Sanz Villanueva");
		xmlPago.setTelefono("616893324");
		xmlPago.setOrganoEmisor("ieb");
		xmlPago.setModelo("modelo1");
		xmlPago.setConcepto("concepto pago");
		xmlPago.setIdTasa("tasa1");
		xmlPago.setFechaDevengo(new Date());
		xmlPago.setTipoPago('T');
		xmlPago.setLocalizador("loca123");
		xmlPago.setEstado('S');
		xmlPago.setNumeroDUI("dui123");
		xmlPago.setFechaPago(new Date());
		xmlPago.setImporte("100");
		xmlPago.setInstruccionesPresencialTexto("texto de instrucciones");
		xmlPago.setInstruccionesPresencialEntidad1Nombre("Sa Nostra");
		xmlPago.setInstruccionesPresencialEntidad1Cuenta("1111 1111 11 1111111111");
		xmlPago.setInstruccionesPresencialEntidad2Nombre("La Caixa");
		xmlPago.setInstruccionesPresencialEntidad2Cuenta("2222 2222 22 2222222222");
		xmlPago.setInstruccionesPresencialEntidad3Nombre("Banca March");
		xmlPago.setInstruccionesPresencialEntidad3Cuenta("3333 3333 33 3333333333");
		xmlPago.setInstruccionesPresencialEntidad4Nombre("Banco 4");
		xmlPago.setInstruccionesPresencialEntidad4Cuenta("4444 4444 44 4444444444");
		xmlPago.setInstruccionesPresencialEntidad5Nombre("Banco 5");
		xmlPago.setInstruccionesPresencialEntidad5Cuenta("5555 5555 55 5555555555");
		xmlPago.setInstruccionesPresencialEntidad6Nombre("Banco 6");
		xmlPago.setInstruccionesPresencialEntidad6Cuenta("6666 6666 66 6666666666");
		xmlPago.setInstruccionesPresencialEntidad7Nombre("Banco 7");
		xmlPago.setInstruccionesPresencialEntidad7Cuenta("7777 7777 77 7777777777");
		xmlPago.setInstruccionesPresencialEntidad8Nombre("Banco 8");
		xmlPago.setInstruccionesPresencialEntidad8Cuenta("8888 8888 88 8888888888");
		xmlPago.setInstruccionesPresencialEntidad9Nombre("Banco 9");
		xmlPago.setInstruccionesPresencialEntidad9Cuenta("9999 9999 99 9999999999");
		xmlPago.setInstruccionesPresencialEntidad10Nombre("Banco 10");
		xmlPago.setInstruccionesPresencialEntidad10Cuenta("0000 0000 00 0000000000");
		
		
		
		String strXml = xmlPago.getString();
		
		IOUtils.copy(new ByteArrayInputStream(strXml.getBytes(ConstantesXML.ENCODING)), new FileOutputStream("moduls/llibreria-xml/moduls/test/datosPago.xml"));
		
		System.out.println(strXml);
		

	}

}
