package es.caib.sistra.front.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.tiles.ComponentContext;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.InstanciaManager;
import es.caib.sistra.model.AsientoCompleto;
import es.caib.sistra.model.DocumentoFront;
import es.caib.sistra.model.TramiteFront;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.datospropios.factoria.FactoriaObjetosXMLDatosPropios;
import es.caib.xml.datospropios.factoria.ServicioDatosPropiosXML;
import es.caib.xml.datospropios.factoria.impl.DatosPropios;
import es.caib.xml.datospropios.factoria.impl.Instrucciones;
import es.caib.xml.registro.factoria.FactoriaObjetosXMLRegistro;
import es.caib.xml.registro.factoria.ServicioRegistroXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosAnexoDocumentacion;
import es.caib.xml.registro.factoria.impl.Justificante;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegatePADUtil;
import es.caib.zonaper.persistence.delegate.PadDelegate;

public class FinalizacionController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Obtener los datos del resultado del registro y construir los objetos que estructuran
		// las instrucciones a partir del xml de datos propios que viene en el resultado
		
		TramiteFront tramite 			= this.getTramiteFront( request );
		Map params = this.getParametros( request );
		String idPersistencia = tramite.getIdPersistencia();
		
		AsientoCompleto resultado = ( AsientoCompleto ) params.get( Constants.RESULTADO_REGISTRO_KEY );
		
		if ( resultado == null )
		{			
			return;
		}
		
		boolean registro = tramite.getRegistrar();
		
		String textoJustificante = registro ? "pasoJustificante.guardarJustificante.informacion.registro" : "pasoJustificante.guardarJustificante.informacion.envio";
		
		// Obtenemos instrucciones de finalizacion
		request.setAttribute( "textoJustificante", textoJustificante );
		
		// Obtenemos instrucciones de finalizacion
		request.setAttribute( "instrucciones", obtenerInstrucciones( resultado ) );

		// Obtener lista documentos
		List documentacion = obtenerDocumentacion(resultado );
		
		request.setAttribute( "documentacion", documentacion );
		
		// Detectamos si los documentos formularios de la solicitud tienen plantilla asociada para saber si mostrarlo en el frontal
		Map documentacionLink = new HashMap();

		InstanciaDelegate delegate = InstanciaManager.recuperarInstancia( request.getParameter("ID_INSTANCIA"), request );
		
		for (Iterator it=documentacion.iterator();it.hasNext();){
			DatosAnexoDocumentacion doc = (DatosAnexoDocumentacion) it.next();

			DocumentoRDS documentoRDS = delegate.recuperaInfoDocumento(StringUtil.getModelo(doc.getIdentificadorDocumento()), StringUtil.getVersion(doc.getIdentificadorDocumento()));
			
			if ("F".equals(doc.getTipoDocumento().toString())){
				if(documentoRDS.isPlantillaVisualizacion()){
					documentacionLink.put(doc.getIdentificadorDocumento(),"true");
				}else{
					documentacionLink.put(doc.getIdentificadorDocumento(),"false");
				}
					
			}
		}
		
		request.setAttribute( "documentacionLink", documentacionLink );
		
		// Comprobamos si se va a redirigir a la zona personal
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
		Justificante asientoRegistral = factoriaRT.crearJustificanteRegistro(
                new ByteArrayInputStream(asientoCompleto.getAsiento().getBytes(ConstantesXML.ENCODING)));
		return asientoRegistral.getAsientoRegistral().getDatosAnexoDocumentacion();
		/*AsientoRegistral asientoRegistral = factoriaRT.crearAsientoRegistral (
				new ByteArrayInputStream(asientoCompleto.getAsiento().getBytes(ConstantesXML.ENCODING)));
		return asientoRegistral.getDatosAnexoDocumentacion();	*/

	}
	
	

}
