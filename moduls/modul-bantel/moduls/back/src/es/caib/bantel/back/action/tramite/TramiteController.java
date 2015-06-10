package es.caib.bantel.back.action.tramite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.tiles.Controller;

import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.VersionWSDelegate;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.regtel.ConstantesPluginRegistro;
import es.caib.sistra.plugins.regtel.PluginRegistroIntf;


public class TramiteController implements Controller
{
	protected static Log log = LogFactory.getLog(TramiteController.class);
	
	public void perform(ComponentContext tileContext,
            HttpServletRequest request, HttpServletResponse response,
            ServletContext servletContext)
	throws ServletException, IOException
	{
		try 
		{
            log.debug("Entramos en versionWSController");
            
            // Obtenemos lista de versiones
            VersionWSDelegate vWSdelegate = DelegateUtil.getVersionWSDelegate();
            List versiones = vWSdelegate.obtenerVersiones();
            request.setAttribute( "listaVersionesWS", versiones);
            
            // Obtenemos lista de u.a.
            List unidades=listarUnidadesAdministrativas();
    		request.setAttribute("listaUnidadesAdministrativa",unidades);
    		
    		 // Obtenemos oficinas y organos
            List oficinas=listarOficinasSalida();
    		request.setAttribute("listaOficinas",oficinas);
    		List organos=listarOrganos();
    		request.setAttribute("listaOrganos",organos);

        } catch (Exception e) {
            throw new ServletException(e);
        }
		

	}

	/**
	 * Listar organos.
	 * @return organos
	 */
	private List listarOrganos()  throws Exception {
		PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
		List servicios = plgRegistro.obtenerServiciosDestino();
		return servicios;
	}

	/**
	 * Lista oficinas registro salida.
	 * @return oficinas
	 */
	private List listarOficinasSalida() throws Exception {
		PluginRegistroIntf plgRegistro = PluginFactory.getInstance().getPluginRegistro();
		List oficinas = plgRegistro.obtenerOficinasRegistro(ConstantesPluginRegistro.REGISTRO_SALIDA);
		return oficinas;
	}


	/**
	 * Lista unidades administrativas
	 * 
	 * @return List de unidades administrativas
	 * @throws Exception
	 */
	public static List listarUnidadesAdministrativas() throws Exception{
		// Obtenemos valores dominio del EJB
		List unidades = new ArrayList();			
		ValoresDominio dom = DelegateSISTRAUtil.getSistraDelegate().obtenerDominio( "GESACUNADM" , null);
		
		UnidadAdministrativa pVacia = new UnidadAdministrativa();
		pVacia.setCodigo("");
		pVacia.setDescripcion("");
		unidades.add(pVacia);
		
		String desc;
		for (int i=1;i<=dom.getNumeroFilas();i++){
			UnidadAdministrativa p = new UnidadAdministrativa();
			p.setCodigo(dom.getValor(i,"CODIGO"));
			desc=StringUtils.defaultString(dom.getValor(i,"DESCRIPCION"));
			if (desc.length() > 35) desc = desc.substring(0,33) + "...";
			p.setDescripcion(desc);
			unidades.add(p);			
		}					
		return unidades;
	}
	
	
}
