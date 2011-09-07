package caib_prueba_xml;

import java.io.File;
import java.util.Date;

import es.caib.xml.justificantepago.factoria.FactoriaObjetosXMLJustificantePago;
import es.caib.xml.justificantepago.factoria.ServicioJustificantePagoXML;
import es.caib.xml.justificantepago.factoria.impl.DatosPago;
import es.caib.xml.justificantepago.factoria.impl.JustificantePago;

public class PruebaEscrituraJustificantePago {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoriaObjetosXMLJustificantePago factoria = null;
		
		try {
			factoria = ServicioJustificantePagoXML.crearFactoriaObjetosXML();
			factoria.setIndentacion (true);
			JustificantePago just = factoria.crearJustificantePago ();
			
			just.setFirma("la firma");
			
			DatosPago datosPago = factoria.crearDatosPago();
			datosPago.setDui ("el dui");
			datosPago.setFechaPago (new Date());
			datosPago.setEstado("PA");
			datosPago.setLocalizador ("el localizador");
			just.setDatosPago (datosPago);
			
			System.out.println (factoria.guardarJustificantePago (just));
			factoria.guardarJustificantePago (just, new File ("moduls/llibreria-xml/moduls/test/justificante_pago_generado.xml"));
					
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
