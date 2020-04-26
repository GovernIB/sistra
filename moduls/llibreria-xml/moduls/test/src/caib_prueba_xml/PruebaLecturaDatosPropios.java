package caib_prueba_xml;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.AlertasTramitacion;
import es.caib.xml.datospropios.factoria.impl.Dato;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Documento;
import es.caib.xml.datospropios.factoria.impl.DocumentosEntregar;
import es.caib.xml.datospropios.factoria.impl.FormulariosJustificante;
import es.caib.xml.datospropios.factoria.impl.Instrucciones;
import es.caib.xml.datospropios.factoria.impl.PersonalizacionJustificante;
import es.caib.xml.datospropios.factoria.impl.Solicitud;
import es.caib.xml.datospropios.factoria.impl.TramiteSubsanacion;

public class PruebaLecturaDatosPropios {

	private static void imprimirAlertasTramitacion (AlertasTramitacion fj){
		System.out.println ("ALERTAS TRAMITACION");
		System.out.println ("email: " + fj.getEmail());
		System.out.println ("sms: " + fj.getSms());
	}

	private static void imprimirFormulariosJustificante (FormulariosJustificante fj){
		System.out.println ("FORMULARIOS JUSTIFICANTE");
		for (int i=0; i < fj.getFormularios().size(); i++) {
			System.out.println (fj.getFormularios().get(i));
		}
	}

	private static void imprimirPersonalizacionJustificante (PersonalizacionJustificante fj){
		System.out.println ("FORMULARIOS JUSTIFICANTE");
		System.out.println ("Ocultar clave: " + fj.getOcultarClaveTramitacion());
		System.out.println ("Ocultar nif nombre: " + fj.getOcultarNifNombre());
	}

	private static void imprimirTramiteSubsanacion (TramiteSubsanacion ts){
		System.out.println ("TRAMITE SUBSANACION");
		System.out.println ("Expe codigo: " + ts.getExpedienteCodigo());
		System.out.println ("Expe UA: " + ts.getExpedienteUnidadAdministrativa());
	}

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
		System.out.println ("Identificador procedimiento: " + inst.getIdentificadorProcedimiento());
		System.out.println ("Identificador sia: " + inst.getIdentificadorSIA());
		System.out.println ("Habilitar notificacion telematica: " + inst.getHabilitarNotificacionTelematica());
		System.out.println ("Habilitar avisos: " + inst.getHabilitarAvisos());
		System.out.println ("Aviso sms: " + inst.getAvisoSMS());
		System.out.println ("Aviso email: " + inst.getAvisoEmail());

		DocumentosEntregar docs = inst.getDocumentosEntregar();
		if (docs != null) imprimirDocumentosEntregar (docs);

		if (inst.getTramiteSubsanacion() != null){
			imprimirTramiteSubsanacion(inst.getTramiteSubsanacion());
		}

		if (inst.getFormulariosJustificante() != null){
			imprimirFormulariosJustificante(inst.getFormulariosJustificante());
		}

		if (inst.getPersonalizacionJustificante() != null){
			imprimirPersonalizacionJustificante(inst.getPersonalizacionJustificante());
		}

		if (inst.getAlertasTramitacion() != null){
			imprimirAlertasTramitacion(inst.getAlertasTramitacion());
		}
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
