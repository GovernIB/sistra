package caib_prueba_xml;

import java.util.Date;

import es.caib.xml.pago.XmlDatosPago;

public class PruebaEscrituraDatosPago {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Generamos xml de pago
		XmlDatosPago xmlPago = new XmlDatosPago();
		xmlPago.setModeloRDSPago("modelo");
		xmlPago.setVersionRDSPago(1);
		xmlPago.setNif("33456299Q");
		xmlPago.setNombre("Rafael Sanz Villanueva");
		xmlPago.setOrganoEmisor("ieb");
		xmlPago.setModelo("modelo1");
		xmlPago.setConcepto("concepto pago");
		xmlPago.setIdTasa("tasa1");
		xmlPago.setFechaDevengo(new Date());
		xmlPago.setTipoPago('P');
		xmlPago.setLocalizador("loca123");
		xmlPago.setEstado('S');
		xmlPago.setNumeroDUI("dui123");
		xmlPago.setFechaPago(null);
		xmlPago.setImporte("100");
		xmlPago.setInstruccionesPresencialTexto("texto de instrucciones");
		xmlPago.setInstruccionesPresencialEntidad1Nombre("Sa Nostra");
		xmlPago.setInstruccionesPresencialEntidad1Cuenta("1111 1111 11 1111111111");
		xmlPago.setInstruccionesPresencialEntidad2Nombre("La Caixa");
		xmlPago.setInstruccionesPresencialEntidad2Cuenta("2222 2222 22 2222222222");
		xmlPago.setInstruccionesPresencialEntidad3Nombre("Banca March");
		xmlPago.setInstruccionesPresencialEntidad3Cuenta("3333 3333 33 3333333333");
				
		String strXml = xmlPago.getString();
		
		System.out.println(strXml);
		

	}

}
