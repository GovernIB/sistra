package caib_prueba_xml;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.Dato;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.datospropios.factoria.impl.DocumentosEntregar;
import es.caib.xml.datospropios.factoria.impl.Instrucciones;
import es.caib.xml.datospropios.factoria.impl.Solicitud;

public class PruebaLecturaDatosPropios {
	
	private static void imprimirDato (Dato dato){
		System.out.println ("DATO");
		System.out.println ("tipo: " + dato.getTipo());
		System.out.println ("descripcion: " + dato.getDescripcion());
		System.out.println ("valor: " + dato.getValor());
	}
	
	private static void imprimirSolicitud (Solicitud solicitud){
		System.out.println ("SOLICITUD");		
		List datos = solicitud.getDato();
		Iterator itDatos = datos.iterator();
		while (itDatos.hasNext ()) imprimirDato ((Dato) itDatos.next ());
	}
	
	private static void imprimirDatosPropios (DatosPropios datosPropios){
		System.out.println ("DATOS_PROPIOS");
		
		Instrucciones inst = datosPropios.getInstrucciones();
		if (inst != null) imprimirInstrucciones (inst);
		
		Solicitud solicitud = datosPropios.getSolicitud();
		if (solicitud != null) imprimirSolicitud (solicitud);
		
	}
	
	private static void imprimirInstrucciones (Instrucciones inst){
		System.out.println ("INSTRUCCIONES");
		System.out.println ("Texto Instrucciones: " + inst.getTextoInstrucciones());
		System.out.println ("Fecha Tope Entrega: " + inst.getFechaTopeEntrega());
		System.out.println ("Texto Tope Entrega: " + inst.getTextoFechaTopeEntrega());
		System.out.println ("Identificador persistencia: " + inst.getIdentificadorPersistencia());
		System.out.println ("Habilitar notificacion telematica: " + inst.getHabilitarNotificacionTelematica());
		System.out.println ("Habilitar avisos: " + inst.getHabilitarAvisos());
		System.out.println ("Aviso sms: " + inst.getAvisoSMS());
		System.out.println ("Aviso email: " + inst.getAvisoEmail());
		
		DocumentosEntregar docs = inst.getDocumentosEntregar();
		if (docs != null) imprimirDocumentosEntregar (docs);
	}
	
	private static void imprimirDocumentosEntregar (DocumentosEntregar docs){
		System.out.println ("DOCUMENTOS ENTREGAR");
		Iterator itListaDocs = docs.getDocumento().iterator();
		
		while (itListaDocs.hasNext()){
			imprimirDocumento ((Documento) itListaDocs.next());
		}
	}
	
	private static void imprimirDocumento (Documento doc){
		System.out.println ("DOCUMENTO");
		System.out.println ("Tipo: " + doc.getTipo());
		System.out.println ("Firmar: " + doc.isFirmar());
		System.out.println ("Compulsar: " + doc.isCompulsar());
		System.out.println ("Fotocopia: " + doc.isFotocopia());
		System.out.println ("Titulo: " + doc.getTitulo());
		System.out.println ("Identificador: " + doc.getIdentificador());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println ("Creando factoria");
			FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
			DatosPropios datosPropios = factoria.crearDatosPropios (new FileInputStream ("moduls/llibreria-xml/moduls/test/datos_propios_generado.xml"));			
			
			imprimirDatosPropios (datosPropios);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
