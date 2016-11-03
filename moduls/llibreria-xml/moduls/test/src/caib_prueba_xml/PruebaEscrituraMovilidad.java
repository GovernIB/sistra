package caib_prueba_xml;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import es.caib.xml.movilidad.factoria.FactoriaObjetosXMLMovilidad;
import es.caib.xml.movilidad.factoria.ServicioMovilidadXML;
import es.caib.xml.movilidad.factoria.impl.Envio;
import es.caib.xml.movilidad.factoria.impl.MensajeEMAIL;
import es.caib.xml.movilidad.factoria.impl.MensajeSMS;

public class PruebaEscrituraMovilidad {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLMovilidad factoria = ServicioMovilidadXML.crearFactoriaObjetosXML();
			factoria.setEncoding("UTF-8");

			
			Envio envio = factoria.crearEnvio();		
			factoria.setIndentacion(true);
			
			envio.setNombre("nombre");
			envio.setCuenta("cuenta");
			envio.setFechaCaducidad("200811100a");
			envio.setFechaProgramacion("2008121200");
			envio.setInmediato("S");
			
			// Email
			for (int i=0;i<5;i++){
				MensajeEMAIL m = factoria.crearMensajeEMAIL();
				m.setEmails(i+"@email.com");
				m.setTexto("titulo"+i);
				m.setTitulo("titulo"+i);
				envio.getMensajesEMAIL().add(m);
			}
			
			// Sms
			for (int i=0;i<5;i++){
				MensajeSMS m = factoria.crearMensajeSMS();				
				m.setTexto("titulo"+i);
				m.setTelefonos(i+";"+i);
				envio.getMensajesSMS().add(m);
			}
			
			String xml = factoria.guardarEnvio(envio);
			System.out.println(xml);
			
			Envio c = factoria.crearEnvio(new ByteArrayInputStream(xml.getBytes("UTF-8")));	
			factoria.guardarEnvio(c,new FileOutputStream("moduls/llibreria-xml/moduls/test/movilidad_generado.xml"));			
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
