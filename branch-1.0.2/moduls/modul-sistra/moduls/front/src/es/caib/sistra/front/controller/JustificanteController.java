package es.caib.sistra.front.controller;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.front.Constants;
import es.caib.sistra.model.AsientoCompleto;
import es.caib.xml.ConstantesXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.xml.registro.factoria.impl.Justificante;

public class JustificanteController extends TramiteController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		/** recibiremos en params :
		 * |
		 * 	  --resultado		
		 * 					--justificante
		 * 					--datosPropios
		 * 
		 * Para pintar los bloques
		 * BLOQUE						PARAMETRO DE PROCEDENCIA
		 * Datos Registro				justificante
		 * Resumen Registro				justificante
		 * Datos Solicitud				datosPropios -> Datos justificante
		 * Lista Documentos				justificante
		 * Instrucciones 				datosPropios
		 */
		
		
		Map params = this.getParametros( request );
		
		AsientoCompleto resultado = ( AsientoCompleto ) params.get( Constants.RESULTADO_REGISTRO_KEY );
		
		if ( resultado == null )
		{
			return;
		}
		
		//String xmlJustificante = resultado.getJustificante();
		String xmlJustificante = resultado.getAsiento();
		String xmlDatosPropios = resultado.getDatosPropios();
		
		FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
		factoria.setEncoding( ConstantesXML.ENCODING );
		
		FactoriaObjetosXMLDatosPropios factoriaDatosPropios = ServicioDatosPropiosXML.crearFactoriaObjetosXML();
		factoriaDatosPropios.setEncoding( ConstantesXML.ENCODING );
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream ( xmlJustificante.getBytes( ConstantesXML.ENCODING ) );
		Justificante just = factoria.crearJustificanteRegistro( inputStream );		
		//Justificante just = factoria.crearJustificanteRegistroEntrada(new FileInputStream ("d:/prj/codifont-sistra/justificante_generado.xml"));
		
		//DatosPropios datosPropios = factoriaDatosPropios.crearDatosPropios (new FileInputStream ("d:/prj/codifont-sistra/datos_propios_generado.xml"));
		inputStream = new ByteArrayInputStream ( xmlDatosPropios.getBytes( ConstantesXML.ENCODING ) );
		DatosPropios datosPropios = factoriaDatosPropios.crearDatosPropios ( inputStream );
		
		
		// Con el siguiente código se establecerán datos de representante y representado, si existen y son distintos, 
		// de lo contrario representante=representado
		AsientoRegistral asientoRegistral = just.getAsientoRegistral();
		DatosInteresado representante = null;
		DatosInteresado representado = null;
		
		//for ( Enumeration enumDatosInteresado = asientoRegistral.getDatosInteresado(); enumDatosInteresado.hasMoreElements(); )
		for (Iterator it = asientoRegistral.getDatosInteresado().iterator();it.hasNext();)
		{
			//DatosInteresado datosInteresado = ( DatosInteresado ) enumDatosInteresado.nextElement();
			DatosInteresado datosInteresado = ( DatosInteresado ) it.next();
			if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE.equals( datosInteresado.getTipoInteresado() ) )
			{
				representante = datosInteresado;
			}
			else
			{
				representado = datosInteresado;
			}
		}
		if ( representante != null && representado != null )
		{
			if ( representante.getTipoIdentificacion().equals( representado.getTipoIdentificacion() ) && representante.getNumeroIdentificacion().equals( representado.getNumeroIdentificacion() ) )
			{
				representado = null;
			}
		}
		if ( representado != null && representante == null )
		{
			representante = representado;
			representado = null;
		}
		if ( representante != null )
		{
			request.setAttribute( "representante", representante );
		}
		if ( representado != null )
		{
			request.setAttribute( "representado", representado );
		}
				
		request.setAttribute( "datosPropios", datosPropios );
		request.setAttribute( "justificante", just );
		
	}
}
