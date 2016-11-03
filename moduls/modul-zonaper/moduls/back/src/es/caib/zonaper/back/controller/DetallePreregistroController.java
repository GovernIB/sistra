package es.caib.zonaper.back.controller;

import java.io.ByteArrayInputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.zonaper.model.EntradaPreregistro;

public class DetallePreregistroController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		EntradaPreregistro preregistro = ( EntradaPreregistro ) request.getAttribute( "preregistro" );
		FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
		AsientoRegistral asiento 			= factoria.crearAsientoRegistral( new ByteArrayInputStream( consultarDocumentoRDS ( preregistro.getCodigoRdsAsiento(), preregistro.getClaveRdsAsiento() ) ) );
		DatosInteresado datosInteresado = obtenerRepresentate( asiento );
		request.setAttribute( "datosInteresado", datosInteresado );
		request.setAttribute( "datosAsunto", asiento.getDatosAsunto() );
		request.setAttribute( "tipoIdentificacion", getTipoIdentificacion( datosInteresado.getTipoIdentificacion().charValue() ) );
		
	}
	
	private String getTipoIdentificacion( char cTipoIdentificacion )
	{
		return ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIF == cTipoIdentificacion ? "NIF" : ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_CIF == cTipoIdentificacion ? "CIF" : ConstantesAsientoXML.DATOSINTERESADO_TIPO_IDENTIFICACION_NIE == cTipoIdentificacion ? "NIE" : ""; 
	}

}
