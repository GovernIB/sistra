package es.caib.zonaper.front.controller;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;

import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;

public class InfoTramiteAnonimoController extends BaseController
{

	private static String ACTION_DETALLE_TRAMITE = "/protected/detalleTramiteAnonimo.do";
	private static String URL_CONTINUACION_TRAMITACION = "/sistrafront/protected/init.do";
	private static Log log = LogFactory.getLog( InfoTramiteAnonimoController.class  );
	
	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		if ( request.getAttribute( "tramiteNoExiste" ) == null )
		{
			// Establecer url de detalle del tramite
			Entrada entrada = ( Entrada ) request.getAttribute( "tramite" );
			TramitePersistentePAD tramitePersistente = ( TramitePersistentePAD ) request.getAttribute( "tramitePersistente" );
		
			String urlTramite = 
				entrada != null ? request.getContextPath() + ACTION_DETALLE_TRAMITE + "?idPersistencia=" + entrada.getIdPersistencia() + "&tipo=" + entrada.getTipo()  
						:
				URL_CONTINUACION_TRAMITACION + "?language=" + this.getLocale( request ).getLanguage()  +"&modelo=" + tramitePersistente.getTramite() + "&version=" + tramitePersistente.getVersion() + "&idPersistencia=" + tramitePersistente.getIdPersistencia() ;
			
			Date fechaModificacion = entrada != null ? ( entrada.getFechaConfirmacion() != null ? entrada.getFechaConfirmacion() : entrada.getFecha() ) : tramitePersistente.getFechaModificacion();
			Date fechaCaducidad = "C".equals( ( String ) request.getAttribute( "estadoTramite" ) ) ?   ( ( EntradaPreregistro ) entrada ).getFechaCaducidad() : null;
			String descripcion = entrada != null ? entrada.getDescripcionTramite() : tramitePersistente.getDescripcion();
				
			log.debug( "urlTramite " + urlTramite );
			log.debug( "fechaModificacion " + fechaModificacion );
			log.debug( "descripcion " + descripcion );
			log.debug( "fechaCaducidad " + fechaCaducidad );
			
			request.setAttribute( "urlTramite", urlTramite );	
			request.setAttribute( "fechaModificacion", fechaModificacion );
			request.setAttribute( "descripcion", descripcion );
			if (  fechaCaducidad != null )
			{
				request.setAttribute( "fechaCaducidad", fechaCaducidad );
			}
		}

	}

}
