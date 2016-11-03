package es.caib.sistra.front.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.front.Constants;
import es.caib.sistra.front.util.FlujoFormularioRequestHelper;

/**
 * 
 * @web:listener
 *
 */

public class SistraHttpSessionListener implements HttpSessionListener
{
	Log log = LogFactory.getLog( SistraHttpSessionListener.class );

	public void sessionCreated(HttpSessionEvent se)
	{
		log.debug( "Created session" );
	}

	public void sessionDestroyed(HttpSessionEvent se)
	{
		log.debug( "Destroying session" );
		HttpSession session = se.getSession();
		String keyParametro = ( String ) session.getAttribute( Constants.GESTOR_FORM_PARAM_ALMACENAMIENTO_GESTOR_FORMULARIO );
		// Si la key del parametro es nula
		if ( keyParametro != null )
		{
			log.debug( "Liberando estructura de gestion de formulario " + keyParametro );
			try
			{
				FlujoFormularioRequestHelper.resetGestorFormulario( session.getServletContext(), keyParametro );
			}
			catch( Exception exc )
			{
				log.error( exc );
			}
		}
	}

}
