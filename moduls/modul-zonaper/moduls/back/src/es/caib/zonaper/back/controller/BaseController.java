package es.caib.zonaper.back.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;
import org.apache.struts.util.MessageResources;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.registro.factoria.impl.AsientoRegistral;
import es.caib.xml.registro.factoria.impl.DatosInteresado;
import es.caib.zonaper.back.util.ZonaperbackRequestHelper;

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
    public Locale getLocale(HttpServletRequest request) {
       return ZonaperbackRequestHelper.getLocale( request );
    }
    
	public Principal getPrincipal( HttpServletRequest request )
    {
		return request.getUserPrincipal();
    }
	
    public void setMessage(  HttpServletRequest request, String messageKey )
    {
    	setMessage( request, messageKey, null );
    }
    
    public void setMessage( HttpServletRequest request, String messageKey, Object [] args )
    {
    	ZonaperbackRequestHelper.setMessage( request, messageKey, args );
    }
    
    public boolean isSetMessage( HttpServletRequest request )
    {
    	return ZonaperbackRequestHelper.isSetMessage( request );
    }
    
	protected byte[] consultarDocumentoRDS( long codigo, String clave ) throws DelegateException
	{
		ReferenciaRDS referenciaRDS = new ReferenciaRDS();
		referenciaRDS.setCodigo( codigo );
		referenciaRDS.setClave( clave );
		
		RdsDelegate rdsDelegate 	= DelegateRDSUtil.getRdsDelegate();
		DocumentoRDS documentoRDS 	= rdsDelegate.consultarDocumento( referenciaRDS );
		return documentoRDS.getDatosFichero();
	}
	
	 protected DatosInteresado obtenerRepresentate( AsientoRegistral asiento )
	 {
		DatosInteresado datosInteresado = null;
		for ( Iterator it = asiento.getDatosInteresado().iterator(); it.hasNext(); )
		{
			datosInteresado = ( DatosInteresado ) it.next();
			if ( ConstantesAsientoXML.DATOSINTERESADO_TIPO_REPRESENTANTE.equals( datosInteresado.getTipoInteresado() ) )
			{
				return datosInteresado;
			}
		}
		return datosInteresado;
	 }
}
