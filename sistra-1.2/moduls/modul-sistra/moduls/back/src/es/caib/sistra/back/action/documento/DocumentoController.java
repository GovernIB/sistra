package es.caib.sistra.back.action.documento;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.back.action.BaseController;
import es.caib.sistra.modelInterfaz.ConstantesDominio;

public class DocumentoController extends BaseController
{
	public void perform(ComponentContext tileContext,
             HttpServletRequest request, HttpServletResponse response,
             ServletContext servletContext)
	throws ServletException, IOException
	{
         try 
         {
        	 // Obtener la lista de modelos y versiones a traves de los dominios correspondientes
        	 request.setAttribute( "listamodelos", ajustarTamListaDesplegable( obtenerValoresDominio( ConstantesDominio.DOMINIO_RDS_MODELOS ), MAX_COMBO_DESC, "DESCRIPCION"  ) );
         }
         catch (Exception e) 
         {
             throw new ServletException(e);
         }

	}

}
