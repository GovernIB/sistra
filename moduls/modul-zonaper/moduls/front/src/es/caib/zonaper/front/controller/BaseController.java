package es.caib.zonaper.front.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.util.ContactoUtil;
import es.caib.util.StringUtil;
import es.caib.zonaper.front.Constants;
import es.caib.zonaper.front.util.ZonapersFrontRequestHelper;
import es.caib.zonaper.model.DatosSesion;
import es.caib.zonaper.model.OrganismoInfo;
import es.caib.zonaper.persistence.delegate.ConfiguracionDelegate;
import es.caib.zonaper.persistence.delegate.DelegateUtil;

/**
 * Controller con métodos de utilidad.
 */
public abstract class BaseController implements Controller {

    public final void perform(ComponentContext tileContext,
                              HttpServletRequest request, HttpServletResponse response,
                              ServletContext servletContext)
            throws ServletException, IOException {
    	
    	try {
            execute(tileContext, request, response, servletContext);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        
        // Gestion del menu molla pa
        Locale locale = this.getLocale( request);
		String entryKey = ( String ) tileContext.getAttribute( Constants.MENU_NAVEGACION_PREFFIX ); 
		request.getSession().setAttribute(Constants.MENU_NAVEGACION_LAST,entryKey);
		MessageResources resources = this.getResources( request );
		String title = resources.getMessage( locale, Constants.MENU_NAVEGACION_PREFFIX + "." + entryKey );
		
		// Obtenemos info organismo
		OrganismoInfo info = (OrganismoInfo) request.getSession().getServletContext().getAttribute(Constants.ORGANISMO_INFO_KEY);
		
		request.setAttribute( "title", title );
		request.setAttribute( "enlaces", buildEnlacesNavegacion( resources, entryKey, locale, info) );
		request.setAttribute( "backAction", tileContext.getAttribute( "backAction" ) );						
        
		if(request.getSession().getAttribute("urlSistraAFirma") == null || "".equals(request.getSession().getAttribute("urlSistraAFirma"))){
			String urlSistra = "";
			try{
				ConfiguracionDelegate delegate = DelegateUtil.getConfiguracionDelegate();
				Properties propsConfig = delegate.obtenerConfiguracion();
				urlSistra = propsConfig.getProperty("sistra.url");
			}catch (Exception e) {
				urlSistra = "";
			}
			request.getSession().setAttribute( "urlSistraAFirma", urlSistra );
		}
		
		// Generamos literal de contacto
		OrganismoInfo oi = (OrganismoInfo) servletContext.getAttribute(Constants.ORGANISMO_INFO_KEY);
		
		String telefono = oi.getTelefonoIncidencias();
		String email = oi.getEmailSoporteIncidencias();
		String url = oi.getUrlSoporteIncidencias();
		boolean formulario = oi.getFormularioIncidencias();
		String asunto = (String) oi.getReferenciaPortal().get(locale.getLanguage());
		
		String lang = locale.getLanguage();
		
		String literalContacto;
		try {
			literalContacto = ContactoUtil.generarLiteralContacto(telefono, email, url,
				asunto, formulario, lang);
		} catch (Exception e) {
		       throw new ServletException(e);	    
		}
		
		request.setAttribute("literalContacto", literalContacto);
		
    }

    abstract public void execute(ComponentContext tileContext,
                                 HttpServletRequest request, HttpServletResponse response,
                                 ServletContext servletContext) throws Exception;

    /**
     * Return the default message resources for the current module.
     * @param request The servlet request we are processing
     */
    protected MessageResources getResources(HttpServletRequest request) {
        return ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));

    }
        
	/**
     * Return the user's currently selected Locale.
     * @param request The request we are processing
     */
    public  Locale getLocale(HttpServletRequest request) 
    {
        return ZonapersFrontRequestHelper.getLocale( request );
    }
    
    public String getLang(HttpServletRequest request) {
        return  ZonapersFrontRequestHelper.getLang( request );
    }
    
    public Principal getPrincipal( HttpServletRequest request )
    {
		return ZonapersFrontRequestHelper.getPrincipal( request );
    }
    
    public DatosSesion getDatosSesion( HttpServletRequest request )
    {
    	return ZonapersFrontRequestHelper.getDatosSesion( request );
    }
	
    
    public boolean isControlarEntregaNotificaciones(HttpServletRequest request ) {
    	return ZonapersFrontRequestHelper.isControlarEntregaNotificaciones( request );    	
    }
    
    private List buildEnlacesNavegacion( MessageResources resources, String entryKey, Locale locale, OrganismoInfo info )
	{
		List arlResult = new ArrayList();
		StringTokenizer st = new StringTokenizer( entryKey , "." );
		String key =  Constants.MENU_NAVEGACION_PREFFIX;
		
		String texto 	= resources.getMessage( locale, key );
		String action 	= resources.getMessage( locale, key + ".action" );
		Map item = new HashMap();
		
		try {
			if (texto.equals("#ZONA_PERSONAL#")){
					texto = StringUtil.replace(texto,"#ZONA_PERSONAL#",(String) info.getTituloPortal().get(locale.getLanguage()));			
			}
		} catch (Exception e) {}
		
		item.put( "label", texto );
		item.put( "action", action );
		
		arlResult.add( item );
		
		if ( st.hasMoreTokens() )
		{
			item.put( "hasLink", Boolean.valueOf( true ) );
		}
		
		while( st.hasMoreTokens() )
		{
			key +=  "." + st.nextToken();
			texto 	= resources.getMessage( locale, key );
			
			try {
				if (texto.equals("#ZONA_PERSONAL#")){
						texto = StringUtil.replace(texto,"#ZONA_PERSONAL#",(String) info.getTituloPortal().get(locale.getLanguage()));			
				}
			} catch (Exception e) {}
			
			action 	= resources.getMessage( locale, key + ".action" );
			item = new HashMap();
			item.put( "label", texto );
			item.put( "action", action );
			item.put( "hasLink", Boolean.valueOf( st.hasMoreTokens() ) );
			arlResult.add( item );
		}
		return arlResult;
	}
    
    
    public String getIdPersistencia(HttpServletRequest request){
		return (String) request.getSession().getAttribute(Constants.KEY_ANONIMO_ID_PERSISTENCIA);
	}
}
