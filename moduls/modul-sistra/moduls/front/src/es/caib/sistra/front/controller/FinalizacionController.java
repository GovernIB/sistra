package es.caib.sistra.front.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.xml.ConstantesXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Instrucciones;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;

public class FinalizacionController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Obtener los datos del resultado del registro y construir los objetos que estructuran
		// las instrucciones a partir del xml de datos propios que viene en el resultado
		
		Map params = this.getParametros( request );
		
		AsientoCompleto resultado = ( AsientoCompleto ) params.get( Constants.RESULTADO_REGISTRO_KEY );
		
		if ( resultado == null )
		{			
			return;
		}
		
		// Obtenemos instrucciones de finalizacion
		request.setAttribute( "instrucciones", obtenerInstrucciones( resultado ) );

		// Obtener lista documentos
		// request.setAttribute( "documentacion", obtenerDocumentacion(resultado ) );
		
		// Comprobamos si se va a redirigir a la zona personal
		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request.getParameter("ID_INSTANCIA"), request );
		String urlFin = delegate.obtenerUrlFin();
		request.setAttribute( "irAZonaPersonal", new Boolean("[ZONAPER]".equals(urlFin)));
		
	}
	
	public Instrucciones obtenerInstrucciones( AsientoCompleto asientoCompleto ) throws Exception
	{
		String xmlDatosPropios = asientoCompleto.getDatosPropios();
		
		FactoriaObjetosXMLDatosPropios factoriaDatosPropios = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
		factoriaDatosPropios.setEncoding( ConstantesXML.ENCODING );
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream ( xmlDatosPropios.getBytes( ConstantesXML.ENCODING ) );
		DatosPropios datosPropios = factoriaDatosPropios.crearDatosPropios ( inputStream );
		
		Instrucciones instrucciones = datosPropios.getInstrucciones();
		
		return instrucciones;
		
	}
	
	public List obtenerDocumentacion(AsientoCompleto asientoCompleto ) throws Exception {
		FactoriaObjetosXMLRegistro factoriaRT = ServicioRegistroXML.crearFactoriaObjetosXML();
		AsientoRegistral asientoRegistral = factoriaRT.crearAsientoRegistral (
				new ByteArrayInputStream(asientoCompleto.getAsiento().getBytes(ConstantesXML.ENCODING)));
		return asientoRegistral.getDatosAnexoDocumentacion();			
	}
	
	

}
