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

import es.caib.regtel.model.ValorOrganismo;
import es.caib.regtel.persistence.delegate.DelegateRegtelUtil;
import es.caib.regtel.persistence.delegate.RegistroTelematicoDelegate;
import es.caib.bantel.back.form.TramiteForm;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.EntidadDelegate;
import es.caib.bantel.persistence.delegate.VersionWSDelegate;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.delegate.DelegateSISTRAUtil;
import es.caib.sistra.plugins.regtel.ConstantesPluginRegistro;
import es.caib.sistra.plugins.regtel.OficinaRegistro;
import es.caib.sistra.plugins.regtel.ServicioDestinatario;


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
            
            if ("true".equals(request.getParameter("readOnly"))) {
            	request.setAttribute("idReadOnly", "true");
            } 
            
            if (request.getAttribute("idReadOnly") == null) {
            	request.setAttribute("idReadOnly", "false");
            }
            
            List versiones = new ArrayList();
            List unidades= new ArrayList();
            List oficinas= new ArrayList();
            List organos= new ArrayList();
            List listaEntidades= new ArrayList();
            String entidad = "";
             
            // Entidades
    		EntidadDelegate entidadDelegate = DelegateUtil.getEntidadDelegate();
    		listaEntidades= entidadDelegate.listarEntidades();	   
            
    		
    		
            // Mostramos solo para modificaciones
    		if ("true".equals(request.getAttribute("idReadOnly").toString())) {
            	
            	// Obtenemos entidad asociada           
            	TramiteForm formulario = ( TramiteForm ) request.getSession().getAttribute("tramiteForm");
            	Procedimiento procedimiento = ( Procedimiento ) formulario.getValues();
            	entidad = procedimiento.getEntidad();
            	
            	// Obtenemos lista de versiones
	            VersionWSDelegate vWSdelegate = DelegateUtil.getVersionWSDelegate();
	            versiones = vWSdelegate.obtenerVersiones();
	            // Obtenemos lista de u.a.
	            unidades=listarUnidadesAdministrativas();
	         	// Obtenemos oficinas y organos
	            oficinas=listarOficinasSalida(entidad);
	    		organos=listarOrganos(entidad);
	    		         
            }            
            request.setAttribute( "listaVersionesWS", versiones);
    		request.setAttribute("listaUnidadesAdministrativa",unidades);
    		request.setAttribute("listaOficinas",oficinas);
    		request.setAttribute("listaOrganos",organos);
    		request.setAttribute("listaEntidades", listaEntidades );
    		request.setAttribute("entidad", entidad );
    		
        } catch (Exception e) {
            throw new ServletException(e);
        }
		

	}
	
	private List listarOrganos(String entidad)  throws Exception {
		RegistroTelematicoDelegate dlgRte = DelegateRegtelUtil.getRegistroTelematicoDelegate();
		List organosDestino = dlgRte.obtenerServiciosDestino(entidad);
		List servicios = new ArrayList();
		
		ValorOrganismo sVacio = new ValorOrganismo();
		sVacio.setCodigoPadre("");
		sVacio.setCodigo("");
		sVacio.setDescripcion("");
		servicios.add(sVacio);
		
		for(int i=0;i<organosDestino.size();i++){
			// Obtenemos valor
			ValorOrganismo vo = new ValorOrganismo();
			vo.setCodigo(((es.caib.regtel.model.ValorOrganismo)organosDestino.get(i)).getCodigo());
			vo.setDescripcion(((es.caib.regtel.model.ValorOrganismo)organosDestino.get(i)).getCodigo() + " - " + ((es.caib.regtel.model.ValorOrganismo)organosDestino.get(i)).getDescripcion());
			servicios.add(vo);
		}
		
		return servicios;
	}

	/**
	 * Lista oficinas registro salida.
	 * @param entidad 
	 * @return oficinas
	 */
	private List listarOficinasSalida(String entidad) throws Exception {
		
		List oficinas = new ArrayList();
		
		OficinaRegistro ofVacia = new OficinaRegistro();
		ofVacia.setCodigo("");
		ofVacia.setDescripcion("");
		oficinas.add(ofVacia);
		oficinas.addAll(es.caib.regtel.persistence.delegate.DelegateUtil.getRegistroOrganismoDelegate().obtenerOficinasRegistro(entidad, ConstantesPluginRegistro.REGISTRO_SALIDA));
			
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
