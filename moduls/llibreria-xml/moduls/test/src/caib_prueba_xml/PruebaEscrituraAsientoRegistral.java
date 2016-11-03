package caib_prueba_xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import es.caib.xml.ConstantesXML;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.DatosAsunto;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.DatosOrigen;
import es.caib.xml.registro.factoria.impl.DireccionCodificada;
import es.caib.xml.registro.factoria.impl.IdentificacionInteresadoDesglosada;

public class PruebaEscrituraAsientoRegistral {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoriaObjetosXMLRegistro factoria = null;
		
		System.out.println ("Creando factoria");
		
		try {
			factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			factoria.setEncoding(ConstantesXML.ENCODING);
			factoria.setIndentacion (true);			
			AsientoRegistral asiento = factoria.crearAsientoRegistral();
			
			//
			// Rellenar datos del documento
			//			
			
			// Crear asiento registral			
			asiento.setVersion("1.0");
			
			// Crear datos origen
			DatosOrigen dOrigen = factoria.crearDatosOrigen();
			dOrigen.setCodigoEntidadRegistralOrigen ("14");
			//dOrigen.setTipoRegistro (new Character (ConstantesAsientoXML.TIPO_ENVIO));
			// dOrigen.setTipoRegistro (new Character (ConstantesAsientoXML.TIPO_REGISTRO_SALIDA));
			//dOrigen.setTipoRegistro (new Character (ConstantesAsientoXML.TIPO_ENVIO));
			dOrigen.setTipoRegistro (new Character (ConstantesAsientoXML.TIPO_ACUSE_RECIBO));
			dOrigen.setNumeroRegistro ("XX");
		dOrigen.setFechaEntradaRegistro (new Date());
			asiento.setDatosOrigen (dOrigen);						
			
			// Crear datos interesado
			DatosInteresado dInteresado = factoria.crearDatosInteresado();
			dInteresado.setNivelAutenticacion(null);
			dInteresado.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE);
			dInteresado.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE));
			//dInteresado.setNumeroIdentificacion ("1111111Z");
			dInteresado.setNumeroIdentificacion ("");
			dInteresado.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);
			//dInteresado.setIdentificacionInteresado ("Nombre Apellido");											
			dInteresado.setIdentificacionInteresado ("Apellido1 Apellido2, Nombre");
			IdentificacionInteresadoDesglosada identificacionInteresadoDesglosada = factoria.crearIdentificacionInteresadoDesglosada();
			identificacionInteresadoDesglosada.setNombre("nombre");
			identificacionInteresadoDesglosada.setApellido1("apellido 1");
			identificacionInteresadoDesglosada.setApellido2("apellido 2");						
			dInteresado.setIdentificacionInteresadoDesglosada(identificacionInteresadoDesglosada );
			
			
			DireccionCodificada direccion = factoria.crearDireccionCodificada();
			direccion.setCodigoProvincia("01");
			direccion.setCodigoMunicipio("02");
			direccion.setPaisOrigen("es");
			//direccion.setNombreMunicipio("Municipio");
			direccion.setDomicilio ("Calle del Pez, num. 71");
			direccion.setCodigoPostal("46020");
			direccion.setTelefono("961234567");
			direccion.setEmail("email@aa.es");
			dInteresado.setDireccionCodificada (direccion);
			asiento.getDatosInteresado().add (dInteresado);								
									
			// Datos delegado
			DatosInteresado dDelegado = factoria.crearDatosInteresado();
			dDelegado.setNivelAutenticacion(null);
			dDelegado.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO);
			dDelegado.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE));
			//dInteresado.setNumeroIdentificacion ("1111111Z");
			dDelegado.setNumeroIdentificacion ("");
			dDelegado.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);			
			//dInteresado.setIdentificacionInteresado ("Nombre Apellido");											
			dDelegado.setIdentificacionInteresado ("Apellido1 Apellido2, Nombre");
			
			asiento.getDatosInteresado().add (dDelegado);
			
			// Crear datos asunto
			DatosAsunto dAsunto = factoria.crearDatosAsunto();
			dAsunto.setFechaAsunto(new Date());
			dAsunto.setIdiomaAsunto (ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_CA);
			dAsunto.setTipoAsunto ("SO");
			dAsunto.setExtractoAsunto ("Extracto del asunto");
			dAsunto.setCodigoOrganoDestino ("1440");
			dAsunto.setDescripcionOrganoDestino(null);
			dAsunto.setCodigoUnidadAdministrativa("1440");
			//dAsunto.setIdentificadorTramite ("TR0");
			asiento.setDatosAsunto (dAsunto);						
			
			// Crear datos asiento documentacion
			DatosAnexoDocumentacion dAnexoDoc1 = factoria.crearDatosAnexoDocumentacion();
			dAnexoDoc1.setEstucturado (Boolean.TRUE);
			dAnexoDoc1.setTipoDocumento (new Character (ConstantesAsientoXML.DATOSANEXO_OTROS));
			//dAnexoDoc1.setTipoDocumento (new Character (ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS));
			//dAnexoDoc1.setTipoDocumento (new Character (ConstantesAsientoXML.DATOSANEXO_OFICIO_REMISION));			
			dAnexoDoc1.setIdentificadorDocumento ("23451");
			dAnexoDoc1.setCodigoRDS("11");
			dAnexoDoc1.setNombreDocumento ("FormularioEntrada.xml");
			dAnexoDoc1.setExtractoDocumento ("Formulario que contiene los datos de entrada");
			dAnexoDoc1.setHashDocumento ("AA04EF010DAABB46");
			
			DatosAnexoDocumentacion dAnexoDoc2 = factoria.crearDatosAnexoDocumentacion();
			dAnexoDoc2.setEstucturado (Boolean.TRUE);
			//dAnexoDoc2.setTipoDocumento (new Character (ConstantesAsientoXML.DATOSANEXO_OTROS));
			dAnexoDoc2.setTipoDocumento (new Character (ConstantesAsientoXML.DATOSANEXO_AVISO_NOTIFICACION));
			dAnexoDoc2.setIdentificadorDocumento ("23456");
			dAnexoDoc2.setCodigoRDS("12");
			dAnexoDoc2.setNombreDocumento ("NIF.JPG");
			dAnexoDoc2.setExtractoDocumento ("NIF del solicitante");
			dAnexoDoc2.setHashDocumento ("AA04EF010DAABB45");
			dAnexoDoc2.setCodigoNormalizado ("NIF");
															
			asiento.getDatosAnexoDocumentacion().add (dAnexoDoc1);
			asiento.getDatosAnexoDocumentacion().add (dAnexoDoc2);
															
			factoria.guardarAsientoRegistral(asiento, new FileOutputStream ("moduls/llibreria-xml/moduls/test/asiento_registral_generado.xml"));
			System.out.println (factoria.guardarAsientoRegistral(asiento));
			 
			System.out.println ("Terminado correctamente"); 
			
		} catch (Exception e) {			
			e.printStackTrace();
		}

	}

}
