package caib_prueba_xml;

import java.io.File;
import java.util.Date;

import es.caib.xml.ConstantesXML;
import es.caib.xml.datospropios.factoria.ConstantesDatosPropiosXML;
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


public class PruebaEscrituraDatosPropios {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoriaObjetosXMLDatosPropios factoria = null;

		System.out.println ("Creando factoria");

		try {
			factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();

			factoria.setEncoding(ConstantesXML.ENCODING);
			factoria.setIndentacion (true);

			DatosPropios datosPropios = factoria.crearDatosPropios();

			//
			// Rellenar datos del documento
			//
			Solicitud solicitud = factoria.crearSolicitud();
			Dato dato = factoria.crearDato();
			dato.setTipo (new Character (ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_CAMPO));
			dato.setDescripcion ("XXXX");
			dato.setValor ("YYYY");
			solicitud.getDato().add (dato);

			dato = factoria.crearDato();
			dato.setTipo (new Character (ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_BLOQUE));
			dato.setDescripcion ("XXXX2");
			dato.setValor ("YYYY2");
			solicitud.getDato().add (dato);

			dato = factoria.crearDato();
			dato.setTipo (new Character (ConstantesDatosPropiosXML.DATOSOLICITUD_TIPO_INDICE));
			dato.setDescripcion ("XXXX3");
			dato.setValor ("YYYY3");
			solicitud.getDato().add (dato);

			datosPropios.setSolicitud (solicitud);

			Instrucciones instrucciones = factoria.crearInstrucciones();
			instrucciones.setTextoInstrucciones ("Para que su solicitud etc etc");

			instrucciones.setFechaTopeEntrega(new Date());
			instrucciones.setTextoFechaTopeEntrega("Texto de fecha de entrega particularizado");
			instrucciones.setIdentificadorPersistencia("ENV/2006/002222");
			instrucciones.setIdentificadorProcedimiento("PROC1");
			instrucciones.setIdentificadorSIA("SIA1");

			instrucciones.setHabilitarNotificacionTelematica("N");

			instrucciones.setHabilitarAvisos("S");
			instrucciones.setAvisoEmail("kk@indra.es");
			instrucciones.setAvisoSMS("6161234567");

			DocumentosEntregar docsE = factoria.crearDocumentosEntregar();

			Documento doc = factoria.crearDocumento();

			doc.setTipo(new Character (ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE));
			doc.setTitulo("JUSTIFICANTE");
			docsE.getDocumento().add (doc);


			doc = factoria.crearDocumento();
			doc.setTipo(new Character (ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO));
			doc.setCompulsar (new Boolean (true));
			doc.setFotocopia (new Boolean (true));
			doc.setTitulo("FORMULARIO XXX");
			doc.setIdentificador("FORM-1");
			docsE.getDocumento().add (doc);

			doc = factoria.crearDocumento();
			doc.setTipo(new Character (ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO));
			doc.setFirmar (new Boolean (false));
			doc.setTitulo("FORMULARIO YYY");
			docsE.getDocumento().add (doc);

			doc = factoria.crearDocumento();
			doc.setTipo(new Character (ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO));
			doc.setTitulo("titulo");
			docsE.getDocumento().add (doc);

			instrucciones.setDocumentosEntregar(docsE);


			TramiteSubsanacion subs = factoria.crearTramiteSubsanacion();
			subs.setExpedienteCodigo("EXPE2");
			subs.setExpedienteUnidadAdministrativa(new Long(12));
			instrucciones.setTramiteSubsanacion(subs);

			FormulariosJustificante forms = factoria.crearFormulariosJustificante();
			forms.getFormularios().add("FORM1");
			forms.getFormularios().add("FORM2");
			forms.getFormularios().add("FORM3");
			instrucciones.setFormulariosJustificante(forms);

			PersonalizacionJustificante persJust = factoria.crearPersonalizacionJustificante();
			persJust.setOcultarClaveTramitacion(new Boolean(true));
			persJust.setOcultarNifNombre(new Boolean(true));
			instrucciones.setPersonalizacionJustificante(persJust);

			AlertasTramitacion alertas = factoria.crearAlertasTramitacion();
			alertas.setEmail("xxx@email.es");
			alertas.setSms("123456789");
			instrucciones.setAlertasTramitacion(alertas);

			datosPropios.setInstrucciones (instrucciones);

			// Guardar documento en fichero
			System.out.println ("Escribiendo en consola");
			factoria.guardarDatosPropios (datosPropios, new File ("moduls/llibreria-xml/moduls/test/datos_propios_generado.xml"));
			System.out.println (factoria.guardarDatosPropios (datosPropios));

			System.out.println ("Terminado correctamente");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
