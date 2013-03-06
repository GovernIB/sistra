package es.caib.sistra.back.action.documentoNivel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.sistra.back.action.BaseController;
import es.caib.sistra.back.form.DocumentoNivelForm;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.GestorFormulario;
import es.caib.sistra.modelInterfaz.ConstantesDominio;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.DocumentoDelegate;
import es.caib.sistra.plugins.PluginFactory;

public class DocumentoNivelController extends BaseController
{

	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
        {
			DocumentoDelegate delegate = DelegateUtil.getDocumentoDelegate();
			DocumentoNivelForm formulario = ( DocumentoNivelForm ) request.getSession().getAttribute("documentoNivelForm");			
			Documento documento = delegate.obtenerDocumento( formulario.getIdDocumento()  ); 						
			
			// Obtener la lista de versiones RDS
			List arlParametros = new ArrayList();
        	arlParametros.add( documento.getModelo() );       	   	
       	 	request.setAttribute( "listaversionesrds", ajustarTamListaDesplegable( obtenerValoresDominio( ConstantesDominio.DOMINIO_RDS_VERSIONES_MODELO, arlParametros ), MAX_COMBO_DESC, "DESCRIPCION" ) );
       	 	
       	 	// Lista de gestores de formularios
       	 	List gestores = DelegateUtil.getGestorFormularioDelegate().listar();
       	 	Properties props = new Properties();
       	 	for (Iterator it=gestores.iterator();it.hasNext();){
       	 		GestorFormulario gestor = (GestorFormulario) it.next();
       	 		props.put(gestor.getIdentificador(),gestor.getDescripcion());
       	 	}
       	 	request.setAttribute("gestoresFormularios", props);  
       	 	
       	 	// Obtener la lista de plugins de pago
       	 	String [] plgsPago = PluginFactory.getInstance().getIdsPluginsAdicionalesPago();
       	 	ValoresDominio valoresDominio = new ValoresDominio();
       	 	int fila = valoresDominio.addFila();
       	 	valoresDominio.setValor(fila, "CODIGO", PluginFactory.ID_PLUGIN_DEFECTO);
       	 	valoresDominio.setValor(fila, "DESCRIPCION", "DEFECTO");
       	 	for (int i = 0; i < plgsPago.length; i++) {
	       	 	fila = valoresDominio.addFila();
	       	 	valoresDominio.setValor(fila, "CODIGO", plgsPago[i]);
	       	 	valoresDominio.setValor(fila, "DESCRIPCION", plgsPago[i]);
       	 	}
			request.setAttribute( "listaPluginsPago", ajustarTamListaDesplegable( valoresDominio.getFilas(), MAX_COMBO_DESC, "DESCRIPCION" , false) );
       	 	
       	 	
        }
        catch (Exception e) 
        {
            throw new ServletException(e);
        }

	}

}




