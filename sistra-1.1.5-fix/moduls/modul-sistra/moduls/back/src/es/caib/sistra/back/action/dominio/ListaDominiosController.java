package es.caib.sistra.back.action.dominio;
import org.apache.struts.tiles.ComponentContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.Controller;

import org.apache.commons.lang.StringUtils;

import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DominioDelegate;


public class ListaDominiosController implements Controller
{
	protected static Log log = LogFactory.getLog(ListaDominiosController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.info("Entramos en ListaDominiosController");
            
            String strCodigoOrganoOrigen = StringUtils.defaultString( request.getParameter( "codigoOrganoOrigen" ), ""  );
            
            strCodigoOrganoOrigen = StringUtils.isEmpty( strCodigoOrganoOrigen ) ? 
            		( request.getAttribute( "idOrgano" ) != null ? request.getAttribute( "idOrgano" ).toString() : "" ) 
            		: strCodigoOrganoOrigen ;
            
            DominioDelegate dominioDelegate = DelegateUtil.getDominioDelegate();
            
            if ( StringUtils.isEmpty( strCodigoOrganoOrigen ))
            {
            	request.setAttribute("dominioOptions", dominioDelegate.listarDominios() );
            }
            else
            {
            	request.setAttribute("dominioOptions", dominioDelegate.listarDominios( new Long( strCodigoOrganoOrigen )) );
            }
            request.setAttribute( "codigoOrganoOrigen", strCodigoOrganoOrigen );

        } catch (DelegateException e) {
            throw new ServletException(e);
        }
		

	}

}
