package caib_prueba_xml;

import java.io.File;
import es.caib.xml.justificantepago.factoria.FactoriaObjetosXMLJustificantePago;
import es.caib.xml.justificantepago.factoria.ServicioJustificantePagoXML;
import es.caib.xml.justificantepago.factoria.impl.DatosPago;
import es.caib.xml.justificantepago.factoria.impl.JustificantePago;

public class PruebaLecturaJustificantePago {
	
	private static void imprimirJustificantePago (JustificantePago j){
		System.out.println ("JUSTIFICANTE_PAGO");
		
		if (j != null){
			System.out.println ("Firma: " + j.getFirma());
			imprimirDatosPago (j.getDatosPago());
		}
		
	}
	
	private static void imprimirDatosPago (DatosPago d){
		System.out.println ("DATOS_PAGO");
		
		if ( d!= null){
			System.out.println ("Localizador: " + d.getLocalizador());
			System.out.println ("Dui: " + d.getDui());									 
			System.out.println ("Fecha pago: " + d.getFechaPago());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoriaObjetosXMLJustificantePago factoria = null;
		
		try {
			factoria = ServicioJustificantePagoXML.crearFactoriaObjetosXML();
			JustificantePago just = factoria.crearJustificantePago (new File ("moduls/llibreria-xml/moduls/test/justificante_pago_generado.xml"));
			
			imprimirJustificantePago (just);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
