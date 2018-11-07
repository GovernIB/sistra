package es.caib.sistra.persistence.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.modelInterfaz.ProcedimientoBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.sistra.model.ConstantesSTR;
import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.model.DatosDesglosadosInteresado;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.ReferenciaCampo;
import es.caib.sistra.model.TraDatoJustificante;
import es.caib.sistra.model.TraTramite;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.model.TramiteVersion;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.persistence.ejb.ProcessorException;
import es.caib.sistra.persistence.plugins.DestinatarioTramite;
import es.caib.sistra.persistence.plugins.PluginFormularios;
import es.caib.sistra.persistence.plugins.PluginPagos;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.NifCif;
import es.caib.util.StringUtil;
import es.caib.util.ValidacionesUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.EstablecerPropiedadException;
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
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;

/**
 * Helper para generar XML de asiento registral,justificante,datos propios,etc
 */
public class GeneradorAsiento {


	private static Log log = LogFactory.getLog( GeneradorAsiento.class );

	/**
	 *
	 * Genera asiento registral a partir de la información del trámite
	 *
	 * Si hay autenticación, comprueba que quién envia es el usuario autenticado en la sesión
	 *
	 * @param dt
	 * @return
	 */
	public static String generarAsientoRegistral(
			TramiteFront tramiteInfo,
			TramiteVersion tramiteVersion,TramitePersistentePAD tramitePAD,
			DatosDesglosadosInteresado datosRpte, DatosDesglosadosInteresado datosRpdo, DestinatarioTramite dt, boolean debugEnabled) throws Exception{

		try{
			
			// Obtenemos entidad procedimiento
			String entidad = obtenerEntidadProcedimiento(tramitePAD.getIdProcedimiento());
			
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			DocumentoPersistentePAD docPAD;
			DocumentoRDS docRDS;

			FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			factoria.setEncoding(ConstantesXML.ENCODING);

			// Crear asiento registral
			AsientoRegistral asiento = factoria.crearAsientoRegistral();
			asiento.setVersion("1.0");

			// Crear datos origen
			DatosOrigen dOrigen = factoria.crearDatosOrigen();
			dOrigen.setCodigoEntidad(entidad);
			dOrigen.setCodigoEntidadRegistralOrigen (dt.getOficinaRegistral());
			if (isTramitePresencial( tramiteInfo.getTipoTramitacion(), tramiteInfo.getTipoTramitacionDependiente() )){
				if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_REGISTRO){
					dOrigen.setTipoRegistro(new Character(ConstantesAsientoXML.TIPO_PREREGISTRO));
				}else{
					dOrigen.setTipoRegistro(new Character(ConstantesAsientoXML.TIPO_PREENVIO));
				}
			}else{
				if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_REGISTRO){
					dOrigen.setTipoRegistro(new Character(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA));
				}else{
					dOrigen.setTipoRegistro(new Character(ConstantesAsientoXML.TIPO_ENVIO));
				}
			}
			dOrigen.setNumeroRegistro ("");
			asiento.setDatosOrigen (dOrigen);

			// Crear datos representante
			DatosInteresado dInteresadoRpte,dInteresadoRpdo=null,dInteresadoDlgdo=null;
			dInteresadoRpte = factoria.crearDatosInteresado();

			// Si el trámite es autenticado se requiere nif y nombre
			boolean requiereRpte = (tramitePAD.getNivelAutenticacion() != 'A');
	    	if ( datosRpte.isAnonimo() && requiereRpte){
	    		throw new Exception("Debe indicarse los datos del representante");
	    	}

			dInteresadoRpte.setNivelAutenticacion(new Character(tramiteInfo.getDatosSesion().getNivelAutenticacion()));
			if (tramiteInfo.getDatosSesion().getNivelAutenticacion() != 'A'){
				dInteresadoRpte.setUsuarioSeycon(tramiteInfo.getDatosSesion().getCodigoUsuario());
			}
			dInteresadoRpte.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE);
			if (StringUtils.isNotEmpty(datosRpte.getNif())){
				switch (NifCif.validaDocumento(datosRpte.getNif())){
					case NifCif.TIPO_DOCUMENTO_NIF:
						dInteresadoRpte.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF));
						break;
					case NifCif.TIPO_DOCUMENTO_CIF:
						dInteresadoRpte.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF));
						break;
					case NifCif.TIPO_DOCUMENTO_NIE:
						dInteresadoRpte.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE));
						break;
					case NifCif.TIPO_DOCUMENTO_PASAPORTE:
						dInteresadoRpte.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_PASAPORTE));
						break;
					default:
						throw new Exception("El número de identificación del representante ni es nif, ni cif, ni nie, ni pasaporte");
				}
			}else{
				dInteresadoRpte.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF));
			}
						
			dInteresadoRpte.setNumeroIdentificacion (datosRpte.getNif());
			dInteresadoRpte.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);

			dInteresadoRpte.setIdentificacionInteresado (datosRpte.getApellidosNombre());

			if (StringUtils.isNotBlank(datosRpte.getNombre())) {
				IdentificacionInteresadoDesglosada identificacionInteresadoDesglosada = factoria.crearIdentificacionInteresadoDesglosada();
				identificacionInteresadoDesglosada.setNombre(datosRpte.getNombre());
				identificacionInteresadoDesglosada.setApellido1(datosRpte.getApellido1());
				identificacionInteresadoDesglosada.setApellido2(datosRpte.getApellido2());
				dInteresadoRpte.setIdentificacionInteresadoDesglosada(identificacionInteresadoDesglosada);
			}

			// Verificamos que si el destino es registro se haya especificado la info geografica
	    	if (tramiteVersion.getDestino() == ConstantesSTR.DESTINO_REGISTRO){
	    		if (StringUtils.isEmpty(datosRpte.getCodigoPais())) {
	    			throw new Exception("No se ha establecido correctamente la información geográfica del representante: no se ha especificado código país");
	    		}
	    		if (esEspanya(datosRpte.getCodigoPais())){
	    			if (StringUtils.isEmpty(datosRpte.getCodigoProvincia()) || StringUtils.isEmpty(datosRpte.getCodigoMunicipio())){
	    				throw new Exception("No se ha establecido correctamente la información geográfica del representante: no se ha especificado código localidad o de provincia");
	    			}
	    		}
	    	}

	    	// Establecemos info de direccion
	    	dInteresadoRpte.setDireccionCodificada(generarDireccionCodificada(factoria, datosRpte));

			asiento.getDatosInteresado().add(dInteresadoRpte);

	    	// Si hay autenticación:
			//		- si hay delegacion comprobamos que el delegado tiene permisos de enviar
			//		- si no hay delegacion comprobamos que quien envia es el usuario de la sesión
			if (tramiteInfo.getDatosSesion().getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO){
				if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(tramiteInfo.getDatosSesion().getPerfilAcceso())){
					if (tramiteInfo.getDatosSesion().getPermisosDelegacion().indexOf(ConstantesZPE.DELEGACION_PERMISO_PRESENTAR_TRAMITE) == -1){
						throw new Exception("El usuario no tiene permisos para presentar tramites en nombre de la entidad");
					}
				}else{
					if (!tramiteInfo.getDatosSesion().getNifUsuario().equals(dInteresadoRpte.getNumeroIdentificacion())){
						throw new Exception("El usuario que envia no es el que está indicado como representante");
					}
				}
			}


			// Creamos datos representado (si corresponde)
			if (datosRpdo != null && !datosRpte.getNif().equals(datosRpdo.getNif())){
				dInteresadoRpdo = factoria.crearDatosInteresado();
				dInteresadoRpdo.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTADO);
				switch (NifCif.validaDocumento(datosRpdo.getNif())){
					case NifCif.TIPO_DOCUMENTO_NIF:
						dInteresadoRpdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF));
						break;
					case NifCif.TIPO_DOCUMENTO_CIF:
						dInteresadoRpdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF));
						break;
					case NifCif.TIPO_DOCUMENTO_NIE:
						dInteresadoRpdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE));
						break;
					case NifCif.TIPO_DOCUMENTO_PASAPORTE:
						dInteresadoRpdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_PASAPORTE));
						break;
					default:
						throw new Exception("El número de identificación del representado ni es nif, ni cif, ni nie, ni pasaporte");
				}
				dInteresadoRpdo.setNumeroIdentificacion (datosRpdo.getNif());
				dInteresadoRpdo.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);
				if (StringUtils.isEmpty (datosRpdo.getApellidosNombre())){
		    		throw new Exception("Se ha indicado el nif del representado pero no el nombre");
		    	}

				dInteresadoRpdo.setIdentificacionInteresado (datosRpdo.getApellidosNombre());

				if (StringUtils.isNotBlank(datosRpdo.getNombre())) {
					IdentificacionInteresadoDesglosada identificacionInteresadoDesglosada = factoria.crearIdentificacionInteresadoDesglosada();
					identificacionInteresadoDesglosada.setNombre(datosRpdo.getNombre());
					identificacionInteresadoDesglosada.setApellido1(datosRpdo.getApellido1());
					identificacionInteresadoDesglosada.setApellido2(datosRpdo.getApellido2());
					dInteresadoRpdo.setIdentificacionInteresadoDesglosada(identificacionInteresadoDesglosada);
				}

				// Establecemos info de direccion
				dInteresadoRpdo.setDireccionCodificada(generarDireccionCodificada(factoria, datosRpdo));

				asiento.getDatosInteresado().add(dInteresadoRpdo);
			}

			// Creamos datos de delegado si hay delegacion
			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(tramiteInfo.getDatosSesion().getPerfilAcceso())){
				String dlgdoNif = tramiteInfo.getDatosSesion().getNifDelegado();
				String dlgdoNombre = StringUtil.formatearNombreApellidos(
						ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM,
						tramiteInfo.getDatosSesion().getNombreDelegado(),
						tramiteInfo.getDatosSesion().getApellido1Delegado(),
						tramiteInfo.getDatosSesion().getApellido2Delegado());

				dInteresadoDlgdo = factoria.crearDatosInteresado();
				dInteresadoDlgdo.setTipoInteresado (ConstantesAsientoXML.DATOSINTERESADO_TIPO_DELEGADO);
				switch (NifCif.validaDocumento(dlgdoNif)){
					case NifCif.TIPO_DOCUMENTO_NIF:
						dInteresadoDlgdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF));
						break;
					case NifCif.TIPO_DOCUMENTO_CIF:
						dInteresadoDlgdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF));
						break;
					case NifCif.TIPO_DOCUMENTO_NIE:
						dInteresadoDlgdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE));
						break;
					case NifCif.TIPO_DOCUMENTO_PASAPORTE:
						dInteresadoDlgdo.setTipoIdentificacion (new Character (ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_PASAPORTE));
						break;
					default:
						throw new Exception("El número de identificación del delegado ni es nif, ni cif, ni nie, ni pasaporte");
				}
				dInteresadoDlgdo.setNumeroIdentificacion (dlgdoNif);
				dInteresadoDlgdo.setFormatoDatosInteresado (ConstantesAsientoXML.DATOSINTERESADO_FORMATODATOSINTERESADO_APENOM);
				if (StringUtils.isEmpty (dlgdoNombre)){
		    		throw new Exception("Se ha indicado el nif del delegado pero no el nombre");
		    	}
				dInteresadoDlgdo.setIdentificacionInteresado (dlgdoNombre);
				asiento.getDatosInteresado().add(dInteresadoDlgdo);

			}

			// Crear datos asunto
			DatosAsunto dAsunto = factoria.crearDatosAsunto();
			dAsunto.setFechaAsunto(new Date());
			if (tramiteInfo.getDatosSesion().getLocale().getLanguage().equals("ca"))
				dAsunto.setIdiomaAsunto (ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_CA);
			else if (tramiteInfo.getDatosSesion().getLocale().getLanguage().equals("es"))
				dAsunto.setIdiomaAsunto (ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_ES);
			else if (tramiteInfo.getDatosSesion().getLocale().getLanguage().equals("en"))
				dAsunto.setIdiomaAsunto (ConstantesAsientoXML.DATOSASUNTO_IDIOMA_ASUNTO_EN);
			else
				throw new Exception("Idioma no soportado: " + tramiteInfo.getDatosSesion().getLocale().getLanguage());
			dAsunto.setTipoAsunto (tramiteVersion.getRegistroAsunto());
			dAsunto.setExtractoAsunto ( ((TraTramite) tramiteVersion.getTramite().getTraduccion(tramiteInfo.getDatosSesion().getLocale().getLanguage())).getDescripcion());
			dAsunto.setCodigoOrganoDestino (dt.getOrganoDestino());
			dAsunto.setDescripcionOrganoDestino(obtenerDescripcionOrgano(entidad, dt.getOrganoDestino()));

			dAsunto.setCodigoUnidadAdministrativa(dt.getUnidadAdministrativa().toString());

			dAsunto.setIdentificadorTramite(tramiteVersion.getTramite().getIdentificador() + "-" + tramiteVersion.getVersion());
			asiento.setDatosAsunto (dAsunto);

			// Crear anexo para datos propios
			// --- Obtenemos documento del RDS para obtener el Hash
			String id = ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS+"-1";
			docPAD = (DocumentoPersistentePAD) tramitePAD.getDocumentos().get(id);
			docRDS = rds.consultarDocumento(docPAD.getRefRDS(),false);
			DatosAnexoDocumentacion dAnexo;
			dAnexo = factoria.crearDatosAnexoDocumentacion();
			dAnexo.setEstucturado (new Boolean(true));
			dAnexo.setTipoDocumento (new Character(ConstantesAsientoXML.DATOSANEXO_DATOS_PROPIOS));
			dAnexo.setIdentificadorDocumento (id);
			dAnexo.setCodigoRDS(Long.toString(docPAD.getRefRDS().getCodigo()));
			dAnexo.setCodigoNormalizado(ConstantesRDS.MODELO_DATOS_PROPIOS + "-" + ConstantesRDS.VERSION_DATOS_PROPIOS);
			dAnexo.setNombreDocumento (ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS + ".xml");
			dAnexo.setExtractoDocumento ("Datos propios del trámite");
			dAnexo.setHashDocumento (docRDS.getHashFichero());
			asiento.getDatosAnexoDocumentacion().add(dAnexo);

			// Creamos anexos para documentos trámite: formularios, pagos y anexos
			//--- Creamos anexos para formularios trámite
			for (Iterator it = tramiteInfo.getFormularios().iterator();it.hasNext();){
				DocumentoFront docInfo = (DocumentoFront) it.next();
				addAnexo('F',factoria,asiento,docInfo,rds,tramitePAD);
			}
			//--- Creamos anexos para documentos anexos trámite
			for (Iterator it = tramiteInfo.getAnexos().iterator();it.hasNext();){
				DocumentoFront docInfo = (DocumentoFront) it.next();
				addAnexo('A',factoria,asiento,docInfo,rds,tramitePAD);
			}
			//--- Creamos anexos para pagos trámite
			for (Iterator it = tramiteInfo.getPagos().iterator();it.hasNext();){
				DocumentoFront docInfo = (DocumentoFront) it.next();
				addAnexo('P',factoria,asiento,docInfo,rds,tramitePAD);
			}

			// Convertimos a String
			String strAsiento = factoria.guardarAsientoRegistral(asiento);
			return strAsiento.trim();

		}catch(Exception ex){
			throw new Exception("Excepción al generar asiento a partir de los datos de un trámite: " + ex.toString(),ex);
		}

	}



	/**
	 * Genera XML de datos propios
	 *
	 * @param tramiteInfo
	 * @param tramiteVersion
	 * @param tramitePAD
	 * @param plgForms
	 * @param expeUA
	 * @param expeId
	 * @param dt
	 * @return
	 * @throws Exception
	 */
	public static String generarDatosPropios(
			TramiteFront tramiteInfo,
			TramiteVersion tramiteVersion,TramitePersistentePAD tramitePAD,
			PluginFormularios plgForms,PluginPagos plgPagos, String expeId, Long expeUA, DestinatarioTramite dt, boolean debugEnabled
			) throws Exception{
		try{
			EspecTramiteNivel especVersion = tramiteVersion.getEspecificaciones();
	    	EspecTramiteNivel especNivel = tramiteVersion.getTramiteNivel(tramiteInfo.getDatosSesion().getNivelAutenticacion()).getEspecificaciones();

			FactoriaObjetosXMLDatosPropios factoria = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
			factoria.setEncoding(ConstantesXML.ENCODING);
			DatosPropios datosPropios = factoria.crearDatosPropios();

			// Generar instrucciones
			Instrucciones instrucciones = generarInstrucciones(tramiteInfo,
					tramiteVersion, tramitePAD, plgForms, expeId, expeUA,
					especVersion, especNivel, dt, factoria);
			datosPropios.setInstrucciones(instrucciones);

			// Generamos datos solicitud
			Solicitud datosSolicitud = generarDatosSolicitud(tramiteInfo,
					tramiteVersion, tramitePAD, plgForms, plgPagos,
					especVersion, especNivel, factoria, debugEnabled);
			datosPropios.setSolicitud(datosSolicitud);

			// Devolvemos XML
			return factoria.guardarDatosPropios(datosPropios);
		}catch (ProcessorException pex){
			throw pex;
		}catch(Exception ex){
			log.error("Excepción al generar datos propios: " + ex.toString(),ex);
			throw new Exception("Excepción al generar datos propios: " + ex.toString());
		}

	}

	private static Instrucciones generarInstrucciones(TramiteFront tramiteInfo,
			TramiteVersion tramiteVersion, TramitePersistentePAD tramitePAD,
			PluginFormularios plgForms, String expeId, Long expeUA,
			EspecTramiteNivel especVersion, EspecTramiteNivel especNivel,
			DestinatarioTramite dt, FactoriaObjetosXMLDatosPropios factoria)
			throws EstablecerPropiedadException, Exception, ProcessorException {
		//--- Texto instrucciones
		// ¿Hay que presentar documentación?
		//	 -SI -> tipo circuito no es telemático:
		//		"Para que su solicitud sea completamente válida revise la documentación a aportar y
		//		presentela en los puntos de entrega indicados"

		//	-NO -> "Su solicitud ha sido recibida correctamente y será procesada"
		//
		//	En caso de que existan instrucciones específicas se muestran
		//

		// Personalizacion justificante estandar
		String ocultarClave = obtenerOcultarClaveTramitacion(especVersion,
				especNivel);
		String ocultarNifNombre = obtenerOcultarNifNombre(especVersion,
				especNivel);

		Instrucciones instrucciones = factoria.crearInstrucciones();
		String instruccionesTextCircuitoTelematico,instruccionesTextCircuitoPresencial,instruccionesTextCircuitoPresencialPuntos;
		if (StringUtils.isNotEmpty(tramiteInfo.getInstruccionesFin())){
			instruccionesTextCircuitoTelematico = tramiteInfo.getInstruccionesFin();
			instruccionesTextCircuitoPresencial = tramiteInfo.getInstruccionesFin();
		}else{
			instruccionesTextCircuitoTelematico = Literales.getLiteral(tramiteInfo.getDatosSesion().getLocale().getLanguage(),"mensaje.datospropios.telematico");
			instruccionesTextCircuitoPresencial = Literales.getLiteral(tramiteInfo.getDatosSesion().getLocale().getLanguage(),"mensaje.datospropios.presencial");
			instruccionesTextCircuitoPresencialPuntos = Literales.getLiteral(tramiteInfo.getDatosSesion().getLocale().getLanguage(),"mensaje.datospropios.presencial.puntosRegistro");
			
			String puntosRegistro = StringUtils.defaultString(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("organismo.puntosEntregaDoc.url"), "");
			
			if (!puntosRegistro.isEmpty()){
				instruccionesTextCircuitoPresencialPuntos = StringUtil.replace(instruccionesTextCircuitoPresencialPuntos, "#PUNTOS_REGISTRO#", puntosRegistro);
				instruccionesTextCircuitoPresencial = instruccionesTextCircuitoPresencial + " " + instruccionesTextCircuitoPresencialPuntos;
			}

			// Se muestran datos de seguimiento si:
			//	- es autenticado
			// 	- es anomimo y no se oculta la clave
			if (!"S".equals(ocultarClave) || tramiteInfo.getDatosSesion().getNivelAutenticacion() != ConstantesLogin.LOGIN_ANONIMO) {
				String instruccionesSeguimiento = null;
				if (tramiteInfo.getDatosSesion().getNivelAutenticacion() == ConstantesLogin.LOGIN_ANONIMO) {
					instruccionesSeguimiento = Literales.getLiteral(tramiteInfo.getDatosSesion().getLocale().getLanguage(),"mensaje.datospropios.seguimiento.anonimo");
					instruccionesSeguimiento = StringUtil.replace(instruccionesSeguimiento, "#CLAVE_TRAMITACION#", tramiteInfo.getIdPersistencia());
				} else {
					instruccionesSeguimiento = Literales.getLiteral(tramiteInfo.getDatosSesion().getLocale().getLanguage(),"mensaje.datospropios.seguimiento.autenticado");
				}

				instruccionesTextCircuitoTelematico = instruccionesTextCircuitoTelematico + " " + instruccionesSeguimiento;
				instruccionesTextCircuitoPresencial = instruccionesTextCircuitoPresencial + " " + instruccionesSeguimiento;
			}

		}

		char tipoTramitacion 			= tramiteInfo.getTipoTramitacion();
		char tipoTramitacionDependiente = tramiteInfo.getTipoTramitacionDependiente();


		String instruccionesText = isTramitePresencial( tipoTramitacion, tipoTramitacionDependiente ) ?
				instruccionesTextCircuitoPresencial : instruccionesTextCircuitoTelematico;

		instrucciones.setTextoInstrucciones( instruccionesText );


		// ---- Identificador de persistencia
		instrucciones.setIdentificadorPersistencia(tramiteInfo.getIdPersistencia());

		// ---- Identificador de procedimiento
		instrucciones.setIdentificadorProcedimiento(dt.getProcedimiento());

		// ---- Opciones notificacion telematica
		establecerOpcionesNotificacion(tramiteInfo, instrucciones);

		// ----- Si es tramite presencial establecemos propiedades entrega
		if ( isTramitePresencial( tipoTramitacion, tipoTramitacionDependiente ) )
		{
			// Fecha tope entrega
			Date dateTopeEntrega = obtenerFechaTopeEntrega(tramiteInfo,
					especVersion, especNivel);
			instrucciones.setFechaTopeEntrega( dateTopeEntrega );

			// Texto entrega
			if (StringUtils.isNotEmpty(tramiteInfo.getMensajeFechaLimiteEntregaPresencial())) {
				instrucciones.setTextoFechaTopeEntrega(tramiteInfo.getMensajeFechaLimiteEntregaPresencial());
			}

			// Añadimos lista de documentos a entregar
			DocumentosEntregar docsEntregar = obtenerDocumentosEntregar(tramiteInfo, factoria);
			instrucciones.setDocumentosEntregar( docsEntregar );
		} else {
		// ----  Si no es presencial comprobamos si debemos anexar formularios al justificante
			FormulariosJustificante fj = obtenerFormulariosJustificante(
					tramiteInfo, factoria);
			instrucciones.setFormulariosJustificante(fj);
		}

		// ---- Personalizacion justificante estandard
		if ("S".equals(ocultarClave) || "S".equals(ocultarNifNombre)) {
			PersonalizacionJustificante pj = new PersonalizacionJustificante();
			pj.setOcultarClaveTramitacion(new Boolean("S".equals(ocultarClave)));
			pj.setOcultarNifNombre(new Boolean("S".equals(ocultarNifNombre)));
			instrucciones.setPersonalizacionJustificante(pj);
		}

		// ----- Comprobamos si es un tramite de subsanacion y hay que añadir el expediente
		TramiteSubsanacion ts = generarTramiteSubsanacion(expeId, expeUA,
				factoria);
		instrucciones.setTramiteSubsanacion(ts);

		// ----- Alertas tramitacion
		if ("S".equals(tramitePAD.getAlertasTramitacionGenerar())) {
			AlertasTramitacion alertas = factoria.crearAlertasTramitacion();
			alertas.setEmail(tramitePAD.getAlertasTramitacionEmail());
			alertas.setSms(tramitePAD.getAlertasTramitacionSms());
			instrucciones.setAlertasTramitacion(alertas);
		}

		return instrucciones;
	}

	private static String obtenerOcultarNifNombre(
			EspecTramiteNivel especVersion, EspecTramiteNivel especNivel) {
		String ocultarNifNombre = null;
		ocultarNifNombre = especVersion.getOcultarNifNombreJustif();
		if (!"X".equals(especNivel.getOcultarNifNombreJustif())) {
			ocultarNifNombre = especNivel.getOcultarNifNombreJustif();
		}
		return ocultarNifNombre;
	}

	private static String obtenerOcultarClaveTramitacion(
			EspecTramiteNivel especVersion, EspecTramiteNivel especNivel) {
		String ocultarClave = null;
		ocultarClave = especVersion.getOcultarClaveTramitacionJustif();
		if (!"X".equals(especNivel.getOcultarClaveTramitacionJustif())) {
			ocultarClave = especNivel.getOcultarClaveTramitacionJustif();
		}
		return ocultarClave;
	}

	private static FormulariosJustificante obtenerFormulariosJustificante(
			TramiteFront tramiteInfo, FactoriaObjetosXMLDatosPropios factoria) {
		FormulariosJustificante fj = null;

		// Buscamos si se han configurado un formulario para que sea el justificante
		for ( Iterator it = tramiteInfo.getFormularios().iterator();it.hasNext();)
		{
			DocumentoFront doc = (DocumentoFront) it.next();
			if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO) continue;

			if ( doc.isFormularioJustificante()) {
				if (fj == null) {
					fj = factoria.crearFormulariosJustificante();
				}
				fj.getFormularios().add(doc.getIdentificador() + "-" + doc.getInstancia());
			}
		}

		// Si no se ha configurado un formulario justificante, buscamos si se han configurado formularios para anexar al justificante.
		if (fj == null) {
			for ( Iterator it = tramiteInfo.getFormularios().iterator();it.hasNext();)
			{
				DocumentoFront doc = (DocumentoFront) it.next();
				if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO) continue;

				if ( doc.isFormularioAnexarJustificante()) {
					if (fj == null) {
						fj = factoria.crearFormulariosJustificante();
					}
					fj.getFormularios().add(doc.getIdentificador() + "-" + doc.getInstancia());
				}
			}
		}

		return fj;
	}

	private static void establecerOpcionesNotificacion(
			TramiteFront tramiteInfo, Instrucciones instrucciones)
			throws Exception {
		if (!ConstantesSTR.NOTIFICACIONTELEMATICA_NOPERMITIDA.equals(tramiteInfo.getHabilitarNotificacionTelematica())){
			if (tramiteInfo.getSeleccionNotificacionTelematica() == null){
				throw new Exception("No se ha establecido seleccion para notificacion telematica");
			}
			if (ConstantesSTR.NOTIFICACIONTELEMATICA_OBLIGATORIA.equals(tramiteInfo.getHabilitarNotificacionTelematica()) &&
					!tramiteInfo.getSeleccionNotificacionTelematica().booleanValue()){
				throw new Exception("La notificacion telematica debe ser obligatoria");
			}

			instrucciones.setHabilitarNotificacionTelematica(tramiteInfo.getSeleccionNotificacionTelematica().booleanValue()?"S":"N");

			// Avisos: comprobamos si estan activados los avisos obligatorios para las notificaciones
			instrucciones.setHabilitarAvisos("N");
			if (tramiteInfo.getSeleccionNotificacionTelematica().booleanValue() &&
					tramiteInfo.isObligatorioAvisosNotificaciones()) {

				instrucciones.setHabilitarAvisos("S");

				if (!ValidacionesUtil.validarEmail(tramiteInfo.getSeleccionEmailAviso())) {
					throw new Exception("El email de aviso no es valido");
				}
				instrucciones.setAvisoEmail(tramiteInfo.getSeleccionEmailAviso());

				if (tramiteInfo.isPermiteSMS() && StringUtils.isNotBlank(tramiteInfo.getSeleccionSmsAviso())) {
					if (!ValidacionesUtil.validarMovil(tramiteInfo.getSeleccionSmsAviso())) {
						throw new Exception("El movil de aviso no es valido");
					}
					instrucciones.setAvisoSMS(tramiteInfo.getSeleccionSmsAviso());
				}
			}



		}
	}

	private static TramiteSubsanacion generarTramiteSubsanacion(String expeId,
			Long expeUA, FactoriaObjetosXMLDatosPropios factoria) {
		TramiteSubsanacion ts = null;
		if (expeId != null){
			ts = factoria.crearTramiteSubsanacion();
			ts.setExpedienteCodigo(expeId);
			ts.setExpedienteUnidadAdministrativa(expeUA);
		}
		return ts;
	}

	private static Solicitud generarDatosSolicitud(TramiteFront tramiteInfo,
			TramiteVersion tramiteVersion, TramitePersistentePAD tramitePAD,
			PluginFormularios plgForms, PluginPagos plgPagos,
			EspecTramiteNivel especVersion, EspecTramiteNivel especNivel,
			FactoriaObjetosXMLDatosPropios factoria, boolean debugEnabled) throws ProcessorException,
			EstablecerPropiedadException, Exception {
		Solicitud datosSolicitud = null;
		List datosJustificante = especNivel.getDatosJustificante();
		if (datosJustificante == null || datosJustificante.size() == 0){
			datosJustificante = especVersion.getDatosJustificante();
		}
		if (datosJustificante != null && datosJustificante.size() > 0){
			datosSolicitud = factoria.crearSolicitud();
			for (Iterator it = datosJustificante.iterator();it.hasNext();){
				DatoJustificante datJ = (DatoJustificante) it.next();

				// Validamos script de visibilidad
				boolean visible = true;
				if (datJ.getVisibleScript() != null && datJ.getVisibleScript().length > 0){
					HashMap params = new HashMap();
		    		params.put("PLUGIN_PAGOS",plgPagos);
					String res = ScriptUtil.evaluarScriptTramitacion(datJ.getVisibleScript(),params,tramiteVersion,(HashMap) plgForms.getDatosFormularios(),tramitePAD,tramiteInfo.getDatosSesion(), debugEnabled);
					visible = res.equals("S");
					if (!visible) continue;
				}

				Dato datS = factoria.crearDato();
				datS.setTipo(new Character(datJ.getTipo()));
				TraDatoJustificante tradatjus = ((TraDatoJustificante) datJ.getTraduccion(tramiteInfo.getDatosSesion().getLocale().getLanguage()));
				if (tradatjus == null){
					throw new Exception("No se ha establecido la traducción de un dato de justificante para idioma " + tramiteInfo.getDatosSesion().getLocale().getLanguage());
				}
				datS.setDescripcion(tradatjus.getDescripcion());
				if (datJ.getTipo() == DatoJustificante.TIPO_CAMPO || datJ.getTipo() == DatoJustificante.TIPO_INDICE){

					// Comprobamos si se ha especificado una referencia a campo o un script
					if (StringUtils.isNotEmpty(datJ.getReferenciaCampo())){
						// Comprobamos si es un dato de pagos  o de formulario
						ReferenciaCampo ref = new ReferenciaCampo(datJ.getReferenciaCampo());
						if (tramiteInfo.getFormulario(ref.getIdentificadorDocumento(),ref.getInstancia()) != null){
							datS.setValor(plgForms.getDatoFormulario(datJ.getReferenciaCampo()));
						}else if ( tramiteInfo.getPago( ref.getIdentificadorDocumento(), ref.getInstancia() ) != null){
							datS.setValor(plgPagos.getDatoPago(datJ.getReferenciaCampo()));
						}else{
							datS.setValor(""); // Protegemos ante referencias incorrectas
						}

						// Parche para reemplazar valores: true/false por Si/No
						if (datS.getValor().equalsIgnoreCase("true")) datS.setValor("Si");
						if (datS.getValor().equalsIgnoreCase("false")) datS.setValor("No");
					}else{
						// Ejecutamos script para calcular el valor
						HashMap params = new HashMap();
		        		params.put("PLUGIN_PAGOS",plgPagos);
						String res = ScriptUtil.evaluarScriptTramitacion(datJ.getValorCampoScript(),params,tramiteVersion,(HashMap) plgForms.getDatosFormularios(),tramitePAD,tramiteInfo.getDatosSesion(), debugEnabled);
						datS.setValor(res);
					}

				}
				datosSolicitud.getDato().add(datS);
			}

			if (datosSolicitud.getDato() == null || datosSolicitud.getDato().size() == 0){
				datosSolicitud = null;
			}
		}


		return datosSolicitud;
	}

	private static Date obtenerFechaTopeEntrega(TramiteFront tramiteInfo,
			EspecTramiteNivel especVersion, EspecTramiteNivel especNivel) {
		int dias = especVersion.getDiasPrerregistro();
		if ( especNivel != null && especNivel.getDiasPrerregistro() != 0 )
		{
			dias = especNivel.getDiasPrerregistro();
		}


		Date finPlazoPresentacion = tramiteInfo.getFechaFinPlazo();
		Date dateTopeEntrega = new Date();
		long timeTopeEntrega = dateTopeEntrega.getTime() + ( long ) dias * 24 * 3600 * 1000L;
		if ( finPlazoPresentacion != null )
		{
			timeTopeEntrega = finPlazoPresentacion.getTime() < timeTopeEntrega ? finPlazoPresentacion.getTime() : timeTopeEntrega;
		}
		dateTopeEntrega.setTime( timeTopeEntrega );
		return dateTopeEntrega;
	}

	private static DocumentosEntregar obtenerDocumentosEntregar(
			TramiteFront tramiteInfo, FactoriaObjetosXMLDatosPropios factoria)
			throws EstablecerPropiedadException {
		//--- Documentos a presentar ( sólo para trámites presenciales )
		//	- Justificante firmado -> siempre que tipo circuito no es telemático y no haya formulario justificante
		//	     o
		//  - Formulario justificante -> doc.isFormularioJustificante();
		//
		//	- Formularios firmados -> los marcados para firmar
		//					DocumentoFront doc = (DocumentoFront) tramiteInfo.getFormularios().get(1);
		//					doc.isFirmar();
		//	- Anexos -> Verificar que anexos deben compulsarse y/o entregar fotocopia
		//  - Pagos -> Verificar que pagos se han realizado de forma presencial (PENDIENTE IMPL)

		DocumentosEntregar docsEntregar = factoria.crearDocumentosEntregar();
		Documento instruccionesDocumento;
		boolean existeFormularioJustificante = false;

		for ( Iterator it = tramiteInfo.getFormularios().iterator();it.hasNext(); )
		{
			DocumentoFront doc = (DocumentoFront) it.next();
			if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO) continue;

			if ( doc.isFormularioJustificante()){
				existeFormularioJustificante = true;
				instruccionesDocumento = factoria.crearDocumento();
				instruccionesDocumento.setTipo( new Character(ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO_JUSTIFICANTE));
				instruccionesDocumento.setFirmar( new Boolean(true) );
				instruccionesDocumento.setIdentificador(doc.getIdentificador() + "-" + doc.getInstancia());
				instruccionesDocumento.setTitulo( doc.getDescripcion() );
				docsEntregar.getDocumento().add( 0,instruccionesDocumento );
			}else if ( doc.isPrerregistro()  )
			{
				instruccionesDocumento = factoria.crearDocumento();
				instruccionesDocumento.setTipo( new Character(ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_FORMULARIO));
				instruccionesDocumento.setFirmar( new Boolean(true) );
				instruccionesDocumento.setIdentificador(doc.getIdentificador() + "-" + doc.getInstancia());
				instruccionesDocumento.setTitulo( doc.getDescripcion() );
				docsEntregar.getDocumento().add( instruccionesDocumento );
			}

		}

		// Justificante: Si no existe formulario justificante, hay q entregar justificante
		if (!existeFormularioJustificante){
			instruccionesDocumento = factoria.crearDocumento();
			instruccionesDocumento.setTipo( new Character(ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_JUSTIFICANTE));
			instruccionesDocumento.setFirmar( new Boolean(true) );
			String tituloMultiidiomaJustificanteFirmado = Literales.getLiteral(tramiteInfo.getDatosSesion().getLocale().getLanguage(),"mensaje.datospropios.justificante");
			instruccionesDocumento.setTitulo( tituloMultiidiomaJustificanteFirmado );
			docsEntregar.getDocumento().add(0,instruccionesDocumento );
		}

		for (Iterator it = tramiteInfo.getAnexos().iterator();it.hasNext();)
		{
			DocumentoFront doc = (DocumentoFront) it.next();
			if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO) continue;

			if ( !doc.isAnexoPresentarTelematicamente() ||  doc.isAnexoCompulsar() )
			{
				instruccionesDocumento = factoria.crearDocumento();
				instruccionesDocumento.setTipo( new Character(ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_ANEXO) );
				instruccionesDocumento.setCompulsar( new Boolean(doc.isAnexoCompulsar()) );
				instruccionesDocumento.setFotocopia( new Boolean(doc.isAnexoFotocopia()) );

				// ANTES SOLO SE ESTABLECIA IDENTIFICADOR SI TENIA PARTE TELEMATICA, AHORA SIEMPRE.
				instruccionesDocumento.setIdentificador(doc.getIdentificador() + "-" + doc.getInstancia());
				/*
				if (doc.isAnexoPresentarTelematicamente()){
					// Si se ha presentado telemáticamente enlazamos con asiento
					instruccionesDocumento.setIdentificador(doc.getIdentificador() + "-" + doc.getInstancia());
				}
				*/

				if (doc.isAnexoGenerico()){
					instruccionesDocumento.setTitulo( doc.getAnexoGenericoDescripcion() );
				}else{
					instruccionesDocumento.setTitulo( doc.getDescripcion() );
				}

				docsEntregar.getDocumento().add( instruccionesDocumento );
			}
		}

		for (Iterator it = tramiteInfo.getPagos().iterator();it.hasNext();)
		{
			DocumentoFront doc = (DocumentoFront) it.next();
			if (doc.getEstado() != DocumentoFront.ESTADO_CORRECTO) continue;


			if (doc.getPagoTipo() == 'P'){
				instruccionesDocumento = factoria.crearDocumento();
				instruccionesDocumento.setTipo( new Character(ConstantesDatosPropiosXML.DOCUMENTOSENTREGAR_TIPO_PAGO) );
				instruccionesDocumento.setIdentificador(doc.getIdentificador() + "-" + doc.getInstancia());
				instruccionesDocumento.setTitulo( doc.getDescripcion() );
				docsEntregar.getDocumento().add( instruccionesDocumento );
			}
		}
		return docsEntregar;
	}

	/**
	 *  Convierte XML en strint a bytes utilizando el encoding correspondiente
	 */
	public static byte[] XMLtoBytes(String xml) throws Exception{
		return xml.getBytes(ConstantesXML.ENCODING);
	}

	//-------------------------------------------------------------------------------------------------
	//-------- Funciones auxiliares -------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------

	// Añade anexo
	private static void addAnexo(char tipo,FactoriaObjetosXMLRegistro factoria, AsientoRegistral asiento,
			DocumentoFront docInfo, RdsDelegate rds, TramitePersistentePAD tramitePAD ) throws Exception{

		DocumentoPersistentePAD docPAD;
		DocumentoRDS docRDS;
		DatosAnexoDocumentacion dAnexo;
		String id;

		// Si es anexo comprobamos si se anexa telemáticamente
		if (tipo=='A' && !docInfo.isAnexoPresentarTelematicamente()) return;

		//	--- Los obligatorios y los opcionales rellenados
		if (  docInfo.getObligatorio() == DocumentoFront.OBLIGATORIO ||
			 (docInfo.getObligatorio() == DocumentoFront.OPCIONAL && docInfo.getEstado() != DocumentoFront.ESTADO_NORELLENADO) )
		{
			//--- Si el estado no es correcto generamos excepción
			if (docInfo.getEstado() != DocumentoFront.ESTADO_CORRECTO){
				throw new Exception("El documento " + docInfo.getIdentificador() + " no tiene unestado correcto");
			}
			//--- Obtenemos documento del RDS para obtener el Hash
			id = docInfo.getIdentificador() + "-" + docInfo.getInstancia();
			docPAD = (DocumentoPersistentePAD) tramitePAD.getDocumentos().get(id);
			docRDS = rds.consultarDocumento(docPAD.getRefRDS(),false);

			dAnexo = factoria.crearDatosAnexoDocumentacion();
			dAnexo.setEstucturado (new Boolean(tipo != 'A'));
			if (tipo == 'F') {
				dAnexo.setTipoDocumento (new Character(ConstantesAsientoXML.DATOSANEXO_FORMULARIO));
			} else if (tipo == 'P') {
				dAnexo.setTipoDocumento (new Character(ConstantesAsientoXML.DATOSANEXO_PAGO));
			} else {
				dAnexo.setTipoDocumento (new Character(ConstantesAsientoXML.DATOSANEXO_OTROS));
			}
			dAnexo.setIdentificadorDocumento (id);
			dAnexo.setCodigoRDS(Long.toString(docPAD.getRefRDS().getCodigo()));
			dAnexo.setCodigoNormalizado(docInfo.getModelo() + "-" + docInfo.getVersion());
			dAnexo.setNombreDocumento (docRDS.getNombreFichero());

			// Si el documento es genérico utilizamos descripción personalizada
			if (docInfo.isAnexoGenerico()){
				dAnexo.setExtractoDocumento (docRDS.getTitulo());
			}else{
				dAnexo.setExtractoDocumento (docInfo.getDescripcion());
			}

			//--- Obtenemos documento del RDS para obtener el Hash
			docPAD = (DocumentoPersistentePAD) tramitePAD.getDocumentos().get(id);
			docRDS = rds.consultarDocumento(docPAD.getRefRDS(),false);
			dAnexo.setHashDocumento (docRDS.getHashFichero());
			//--- Añadimos a asiento
			asiento.getDatosAnexoDocumentacion().add(dAnexo);
		}
	}

	/*
	 ¿no se utiliza? para borrar
	private static ReferenciaRDS obtenerReferenciaRDS( DocumentoFront documentoFront, TramitePersistentePAD tramitePAD )
	{
		String idDoc = documentoFront.getIdentificador() + "-" + documentoFront.getInstancia();
		DocumentoPersistentePAD docPad = (DocumentoPersistentePAD) tramitePAD.getDocumentos().get(idDoc);
		ReferenciaRDS refRDS = docPad.getRefRDS();
		return refRDS;

	}
	*/

	private static boolean isTramitePresencial ( char tipoTramitacion, char tipoTramitacionDependiente )
	{
		//return true;
		return tipoTramitacion == ConstantesSTR.TIPO_TRAMITACION_PRESENCIAL || ( tipoTramitacion == ConstantesSTR.TIPO_TRAMITACION_DEPENDIENTE && tipoTramitacionDependiente == ConstantesSTR.TIPO_TRAMITACION_PRESENCIAL );
	}

	private static String obtenerDescripcionOrgano(String entidad, String codOrgano) throws Exception{

		String desc = DelegateRegtelUtil.getRegistroTelematicoDelegate().obtenerDescServiciosDestino(codOrgano);
		
		if (desc != null){
			return desc;
		}

		// Error: No se encuentra organo destino
		throw new Exception("No se encuentra organo destino en tablas de registro. Revisar configuración del trámite.");
	}

	private static String obtenerNombreMunicipio( String codigoProvincia, String codigoMunicipio )
	{
		try
		{
			List lstParametros = new ArrayList();
			lstParametros.add( codigoProvincia );
			lstParametros.add( codigoMunicipio );
			List lstValores = UtilDominios.obtenerValoresDominio( ConstantesDominio.DOMINIO_GTB_NOMBRE_MUNICIPIO, lstParametros );
			if ( lstValores.size() != 1 )
			{
				log.error( ConstantesDominio.DOMINIO_GTB_NOMBRE_MUNICIPIO + " retorna más de un valor para el codigo de municipio " + codigoMunicipio );
				return null;
			}
			Map fila = ( Map ) lstValores.get( 0 );
			return ( String ) fila.get( "DESCRIPCION" );
		}
		catch ( Exception exc )
		{
			log.error( exc );
			return null;
		}
	}

	private static String obtenerNombreProvincia( String codigoProvincia )
	{
		try
		{
			// Convertimos a numero
			int codProvincia=0;
			try{
				codProvincia= Integer.parseInt(codigoProvincia);
			}catch(NumberFormatException nfe){
				log.error("Codigo provincia " + codigoProvincia + " no es un numero");
				return null;
			}

			String codigoProvinciaStr= Integer.toString(codProvincia);

			List lstValores = UtilDominios.obtenerValoresDominio( ConstantesDominio.DOMINIO_GTB_PROVINCIAS );
			for ( int i = 0; i < lstValores.size(); i++ )
			{
				Map fila = ( Map ) lstValores.get( i );
				String codigo = ( String ) fila.get( "CODIGO" );
				if ( codigo.equals( codigoProvinciaStr ) )
				{
					return ( String ) fila.get( "DESCRIPCION" );
				}
			}
			log.error( "No se encuentra descripcion para codigo de provincia " + codigoProvincia );
			return null;
		}
		catch ( Exception exc )
		{
			log.error( exc );
			return null;
		}

	}

	/**
	 * Comprobamos si se ha establecido la informacion geografica (obligatoria para registro/preregistro).
	 * Si el país es España será obligatorio indicar la provincia y el municipio
	 *
	 * @param rptePais Código pais
	 * @param rpteProv Código provincia
	 * @param rpteMun Código municipio
	 *
	 * @return boolean indicando si se ha establecido la información geográfica
	 */
	public static boolean isSetInformacionGeografica( String rptePais, String rpteProv, String rpteMun )
	{
		if (ConstantesSTR.CODIGO_PAIS_ESPANYA.equals(rptePais)
				|| StringUtils.isEmpty(rptePais)) {

			// Si el país es España debe establecerse la provincia / municipio
			if (StringUtils.isEmpty(rpteProv) || StringUtils.isEmpty(rpteMun)) {
				return false;
			}

		}
		return true;
	}

	private static byte[] getScriptNivelOrGenerico(byte[] scriptNivel,byte[] scriptGenerico){
		if (scriptNivel != null && scriptNivel.length>0) return scriptNivel;
		else return scriptGenerico;
	}

	private static boolean esEspanya(String codigoPais) {
		return codigoPais != null && ("ES".equals(codigoPais.toUpperCase()) || "ESP".equals(codigoPais.toUpperCase()));
	}

	private static DireccionCodificada generarDireccionCodificada(
			FactoriaObjetosXMLRegistro factoria,
			DatosDesglosadosInteresado datosInt)
			throws Exception {
		DireccionCodificada direccion = null;
		boolean hayDireccion = (StringUtils.isNotEmpty(datosInt.getCodigoPais()) || StringUtils.isNotEmpty(datosInt.getTelefono()) ||
				 StringUtils.isNotEmpty(datosInt.getEmail()));
		if (hayDireccion){
			direccion = factoria.crearDireccionCodificada();
			if (StringUtils.isNotEmpty(datosInt.getCodigoPais())) {
				if (esEspanya(datosInt.getCodigoPais())) {
					direccion.setPaisOrigen(ConstantesSTR.CODIGO_PAIS_ESPANYA);
					direccion.setCodigoProvincia(datosInt.getCodigoProvincia());
					direccion.setCodigoMunicipio(datosInt.getCodigoMunicipio());
					direccion.setNombreMunicipio( obtenerNombreMunicipio ( datosInt.getCodigoProvincia(), datosInt.getCodigoMunicipio() ) );
					direccion.setNombreProvincia( obtenerNombreProvincia ( datosInt.getCodigoProvincia() ) );
				} else {
					if (datosInt.getCodigoPais().length() != 3) {
			    		throw new Exception("El código de país no contiene un código de 3 carácteres (ver tabla países): " + datosInt.getCodigoPais());
			    	}
					direccion.setPaisOrigen(datosInt.getCodigoPais().toUpperCase());
				}
			}
			direccion.setDomicilio(datosInt.getDireccion());
			direccion.setCodigoPostal(datosInt.getCodigoPostal());
			direccion.setTelefono(datosInt.getTelefono());
			direccion.setEmail(datosInt.getEmail());
		}
		return direccion;
	}
	
	private static String obtenerEntidadProcedimiento(String idProcedimiento) throws Exception {
		String entidad;
		ProcedimientoBTE proc = DelegateBTEUtil.getBteSistraDelegate().obtenerProcedimiento(idProcedimiento);
		entidad = proc.getEntidad().getIdentificador();
		return entidad;
	}
}
