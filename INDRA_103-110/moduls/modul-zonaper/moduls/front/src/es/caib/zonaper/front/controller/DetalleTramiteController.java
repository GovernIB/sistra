package es.caib.zonaper.front.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.zonaper.front.util.Util;
import es.caib.zonaper.model.DocumentoEntrada;
import es.caib.zonaper.model.DocumentoEntradaPreregistro;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;

public class DetalleTramiteController extends BaseController
{
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Recogemos parametros del action
		Entrada entrada = (Entrada) request.getAttribute("tramite");
		if (entrada == null) {
			throw new Exception("Falta parametro 'tramite'");
		}
		
		// En caso de ser preregistro obtenemos lista de docs presenciales
		Map codigosDocsPresenciales = new HashMap();
		if (entrada instanceof EntradaPreregistro){
			for (Iterator it=entrada.getDocumentos().iterator();it.hasNext();){
				DocumentoEntradaPreregistro d = (DocumentoEntradaPreregistro) it.next();
				codigosDocsPresenciales.put(d.getIdentificador() + "-" + d.getNumeroInstancia(),d.getCodigo());
			}
		}
				
		// Obtenemos asiento
		AsientoRegistral asiento = getAsientoRegistral(entrada);
		
		// Obtenemos datos propios
		DatosPropios datosPropios = getDatosPropios(entrada);
		
		// Establecemos parametros
		request.setAttribute("tipo",asiento.getDatosOrigen().getTipoRegistro().toString());
		request.setAttribute( "entrada", entrada );
		request.setAttribute( "asiento", asiento );
		request.setAttribute( "representado",Util.obtenerDatosRepresentado(asiento));
		request.setAttribute( "representante",Util.obtenerDatosRepresentante(asiento));
		request.setAttribute("delegado",Util.obtenerDatosDelegado(asiento));
		request.setAttribute( "datosPropios", datosPropios);
		request.setAttribute("telematico",(entrada instanceof EntradaTelematica?"S":"N"));
		request.setAttribute("codigosDocsPresenciales",codigosDocsPresenciales);
		
	}
	
	/**
	 * Obtiene datos propios
	 * 
	 * @param entrada
	 * @return
	 * @throws Exception
	 */
	private DatosPropios getDatosPropios( Entrada entrada ) throws Exception
	{
		// Obtenermos documento de datos propios
		DocumentoEntrada documento = null;
		for ( Iterator it = entrada.getDocumentos().iterator(); it.hasNext(); )
		{
			documento = ( DocumentoEntrada ) it.next();
			if ( documento.getIdentificador().startsWith( ConstantesAsientoXML.IDENTIFICADOR_DATOS_PROPIOS ))
			{
				break;
			}else{
				documento = null;
			}
		}
		
		if (documento == null){
			throw new Exception("No existe documento de datos propios");
		}
				
		// Obtenemos datos del RDS
		DocumentoRDS docRDSDatosPropios = null;
		try{
			docRDSDatosPropios = Util.consultarDocumentoRDS( documento.getCodigoRDS(), documento.getClaveRDS() );
		}catch (Exception ex){
			throw new Exception("Error consultando del RDS los datos propios",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDSDatosPropios.getDatosFichero());
		try{
			FactoriaObjetosXMLDatosPropios factoriaDatosPropios = ServicioDatosPropiosXML.crearFactoriaObjetosXML();			
			DatosPropios datosPropios = factoriaDatosPropios.crearDatosPropios (bis);			
			return datosPropios;
		}catch (Exception ex){
			throw new Exception("Error parseando datos propios",ex);
		}finally{
			bis.close();
		}
	}
	
	
	/**
	 * Obtiene asiento
	 * 
	 * @param entrada
	 * @return
	 * @throws Exception
	 */
	private AsientoRegistral getAsientoRegistral( Entrada entrada ) throws Exception
	{
		// Obtenemos datos del RDS
		DocumentoRDS docRDS = null;
		try{
			docRDS = Util.consultarDocumentoRDS( entrada.getCodigoRdsAsiento(), entrada.getClaveRdsAsiento());
		}catch (Exception ex){
			throw new Exception("Error consultando del RDS el asiento",ex);
		}
		
		// Parseo de los datos propios
		ByteArrayInputStream bis = new ByteArrayInputStream(docRDS.getDatosFichero());
		try{
			FactoriaObjetosXMLRegistro factoria = ServicioRegistroXML.crearFactoriaObjetosXML();
			AsientoRegistral asiento = factoria.crearAsientoRegistral( bis );			
			return asiento;
		}catch (Exception ex){
			throw new Exception("Error parseando asiento registral",ex);
		}finally{
			bis.close();
		}
	}
}
	
	
	

