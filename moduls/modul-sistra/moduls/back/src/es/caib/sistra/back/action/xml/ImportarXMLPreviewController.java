package es.caib.sistra.back.action.xml;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.regtel.model.ConstantesRegtel;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.sistra.back.action.BaseController;
import es.caib.sistra.modelInterfaz.ConstantesDominio;

public class ImportarXMLPreviewController extends BaseController
{
    public void perform(ComponentContext arg0, HttpServletRequest request, HttpServletResponse arg2, ServletContext arg3) throws ServletException, IOException
    {
        try {
        	 // Obtenemos info registro
            RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
            List organosDestino = dlgRte.obtenerServiciosDestino();
            List oficinasRegistro = dlgRte.obtenerOficinasRegistro(ConstantesRegtel.REGISTRO_ENTRADA);
            List tiposAsunto = dlgRte.obtenerTiposAsunto();
                       
            // Establecemos listas de valores
            request.setAttribute( "listaorganosdestino", ajustarTamListaDesplegable ( organosDestino, MAX_COMBO_DESC , "DESCRIPCION", true, true)  );
            request.setAttribute( "listaoficinasregistro", ajustarTamListaDesplegable ( oficinasRegistro, MAX_COMBO_DESC , "DESCRIPCION")  );
            request.setAttribute( "listatiposasunto", ajustarTamListaDesplegable ( tiposAsunto, MAX_COMBO_DESC , "DESCRIPCION")  );
            request.setAttribute( "listaunidadesadministrativas", ajustarTamListaDesplegable(  obtenerValoresDominio( ConstantesDominio.DOMINIO_SAC_UNIDADES_ADMINISTRATIVAS  ),  MAX_COMBO_DESC , "DESCRIPCION") );
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
    
    
    
}
