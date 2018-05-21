package es.caib.zonaper.helpdesk.front.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.caib.zonaper.helpdesk.front.Constants;
import es.caib.zonaper.helpdesk.front.form.EstadoTramiteForm;
import es.caib.zonaper.helpdesk.front.plugins.PluginAudita;
import es.caib.zonaper.model.Entrada;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaTelematica;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.model.TramitePersistenteResumen;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.EntradaTelematicaDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;

/**
 * @struts.action
 *  name="estadoTramiteForm"
 *  path="/busquedaEstadoTramite"
 *  scope="request"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaEstadoTramite"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaEstadoTramiteAction extends BaseAction
{
	
	
	protected static String FIN_FORMULARIO = "</FORMULARIO>";
	
	protected static Log log = LogFactory.getLog(BusquedaEstadoTramiteAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		EstadoTramiteForm estadoForm = ( EstadoTramiteForm ) form;
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.ESTADO_TAB);		
		request.setAttribute("nivel", Character.toString(estadoForm.getNivelAutenticacion()));

		
		if(estadoForm.getNivelAutenticacion() == Constants.MODO_AUTENTICACION_ANONIMO)
			return busquedaEstadoTramiteAnonimo(mapping,form,request,response);
		else
			return busquedaEstadoTramiteAutenticado(mapping,form,request,response);
    }
	
	/**
	 * Busqueda estado tramite anonimo por clave de persistencia
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward busquedaEstadoTramiteAnonimo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	
		EstadoTramiteForm estadoForm = ( EstadoTramiteForm ) form;
		
		if(StringUtils.isEmpty(estadoForm.getClavePersistencia())){
			request.setAttribute( "estado", "N");
			return mapping.findForward( "success" );
		}
			
		String clave = estadoForm.getClavePersistencia();
		String res = obtenerEstadoTramiteAnonimo(clave);
		request.setAttribute("estado", res);
		return mapping.findForward( "success" );
		
	}
	
	/**
	 * Obtiene estado tramite anonimo por clave persistencia
	 * @param clave
	 * @return
	 */
	private String obtenerEstadoTramiteAnonimo(String idPersistencia) {
		// Buscamos en Zona Persistencia      	
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		TramitePersistente tp = td.obtenerTramitePersistente(idPersistencia);
    		if (tp != null) {
    			if (tp.getNivelAutenticacion() == 'A') return Constants.NO_FINALIZADO; 
    			// Tramite pertenece a otro usuario
    			return Constants.NO_EXISTE;
    		}
    	}catch (Exception ex){
    		log.error("Error obteniendo estado tramite anonimo en persistencia",ex);
    		return Constants.ERROR_BUSQUEDA;
    	}
        	
    	// Buscamos en Zona Persistencia Backup    	
    	try{
    		TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		TramitePersistente tp = td.obtenerTramitePersistenteBackup(idPersistencia);
    		if (tp != null) {
    			if (tp.getNivelAutenticacion() == 'A') return Constants.BORRADO; 
    			// Tramite pertenece a otro usuario
    			return Constants.NO_EXISTE;
    		}
    	}catch (Exception ex){
    		log.error("Error obteniendo estado tramite anonimo en persistencia backup",ex);
    		return Constants.ERROR_BUSQUEDA;
    	}
        	
        // Buscamos en preregistro
    	try{
    		EntradaPreregistroDelegate  epd = DelegateUtil.getEntradaPreregistroDelegate();
    		EntradaPreregistro ep = epd.obtenerEntradaPreregistro(idPersistencia);    	
    		if (ep != null){
	    		if (ep.getNivelAutenticacion() == 'A') {
	    			if (ep.getFechaConfirmacion() == null) return Constants.PENDIENTE_CONFIRMACION;
	    			else return Constants.FINALIZADO;
	    		}
	    		// Tramite pertenece a otro usuario
    			return Constants.NO_EXISTE;
    		}
    	}catch (Exception ex){
    		log.error("Error obteniendo estado tramite anonimo en preregistro",ex);
    		return Constants.ERROR_BUSQUEDA;
    	}    	
    	
    	// Buscamos en entradas telemática
    	try{
    	 	EntradaTelematicaDelegate  etd = DelegateUtil.getEntradaTelematicaDelegate();
    	 	EntradaTelematica et = etd.obtenerEntradaTelematica(idPersistencia); 
    	 	if (et != null) {
	    		if (et.getNivelAutenticacion() == 'A') return Constants.FINALIZADO;
	    		// Tramite pertenece a otro usuario
    			return Constants.NO_EXISTE;
    	 	}
    	}catch (Exception ex){
    		log.error("Error obteniendo estado tramite anonimo en entradas telematicas",ex);
    		return Constants.ERROR_BUSQUEDA; 
    	}
    	
    	// No existe
		return Constants.NO_EXISTE;
	}

	/**
	 * Busqueda estado tramite autenticado por nif
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward busquedaEstadoTramiteAutenticado(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
			try{
		
				List result = new ArrayList();
	
				// -------------------------------------------
				// 1 - PREPARAMOS PARAMETROS BUSQUEDA
				// -------------------------------------------
				// Comprobamos nif
				EstadoTramiteForm estadoForm = ( EstadoTramiteForm ) form;
				if((estadoForm.getUsuarioNif() == null) || estadoForm.getUsuarioNif().equals(""))
				{
					request.setAttribute( "estado", "N");
					return mapping.findForward( "success" );
				}
				
				// Buscamos usuario seycon asociado
				log.debug("Buscamos usuario seycon para nif " + estadoForm.getUsuarioNif());
				PersonaPAD persona = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(estadoForm.getUsuarioNif());
				if (persona == null){
					log.debug("No se encuentra usuario seycon para nif " + estadoForm.getUsuarioNif());
					request.setAttribute( "estado", "N");
					return mapping.findForward( "success" );
				}
				
				
				// Formateamos fecha inicio - fin 
				log.debug("Fecha: " + estadoForm.getFecha() + " HoraInicial: " +
						estadoForm.getHoraInicial() + " HoraFinal: " +
						estadoForm.getHoraFinal());			
				SimpleDateFormat df = new SimpleDateFormat(Constants.FORMATO_FECHAS);
				String fecha = estadoForm.getFecha();
				Date fechaInicial = df.parse(fecha + " " + estadoForm.getHoraInicial()+ ":" +
											 estadoForm.getMinInicial());
				Date fechaFinal = df.parse(fecha + " " + estadoForm.getHoraFinal() + ":" +
										   estadoForm.getMinFinal());
	
				// Obtenemos descripcion tramites
				Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(Constants.DEFAULT_LANG);
				
				
				// -------------------------------------------
				// 2 - REALIZAMOS BUSQUEDA DE TRAMITE
				// -------------------------------------------
				// * Buscamos en la zona de persistencia
				TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();
				List tramites = delegate.listarTramitesPersistentes(fechaInicial,fechaFinal,/* modelo */null,Character.toString(estadoForm.getNivelAutenticacion()));
				addToResults(result,tramites,persona.getUsuarioSeycon(),descTramites,false);
			
				// * Buscamos en Backup de persistencia
				tramites = delegate.listarTramitesPersistentesBackup(fechaInicial,fechaFinal,/* modelo */null,Character.toString(estadoForm.getNivelAutenticacion()));
				addToResults(result,tramites,persona.getUsuarioSeycon(),descTramites,true);
				
				// * Buscamos en Entregas Telematicas
				EntradaTelematicaDelegate etd = DelegateUtil.getEntradaTelematicaDelegate();
				tramites = etd.listarEntradaTelematicasNifModelo(estadoForm.getUsuarioNif(),null,fechaInicial,fechaFinal,
	                    										 Character.toString(estadoForm.getNivelAutenticacion()));
				addToResults(result,tramites,persona.getUsuarioSeycon(),descTramites,false);
	
				// * Buscamos en Pre-Registro
				EntradaPreregistroDelegate epd = DelegateUtil.getEntradaPreregistroDelegate();
				tramites = epd.listarEntradaPreregistrosNifModelo(estadoForm.getUsuarioNif(),null,fechaInicial,fechaFinal,
						                                          Character.toString(estadoForm.getNivelAutenticacion()));
				addToResults(result,tramites,persona.getUsuarioSeycon(),descTramites,false);
				
				
				// Devolvemos resultado
				request.setAttribute( "lstTramites", result);
				return mapping.findForward( "success" );
			}catch (Exception ex){
				log.error("Error obteniendo estado tramite autenticado",ex);
				request.setAttribute( "estado", Constants.ERROR_BUSQUEDA);
				return mapping.findForward( "success" );	    		
			}
    
	}

   
    
    /**
     * Añade resultado busqueda tramites autenticados (persistentes,preregistros y telmaticos) al resultado
     * @param result
     * @param tramites
     * @param usuarioSeycon
     * @param descTramites
     */
    private void addToResults(List result, List tramites, String usuarioSeycon, Map descTramites,boolean backup) {
    	String descTramite;
    	
    	for(Iterator it = tramites.iterator(); it.hasNext();)
    	{
    		Object o = it.next();
    		
    		if (o instanceof TramitePersistente){
    			TramitePersistente tp = (TramitePersistente) o;    			
    			if(usuarioSeycon.equals(tp.getUsuario())){
    				TramitePersistenteResumen tpr = new TramitePersistenteResumen();
					tpr.setDescripcion((String) descTramites.get(tp.getTramite()));
					tpr.setFecha(tp.getFechaModificacion());
					tpr.setIdioma(tp.getIdioma());
					tpr.setIdPersistencia(tp.getIdPersistencia());
					descTramite = (String) descTramites.get(tp.getTramite());
					if(descTramite == null){
						descTramite = tp.getTramite();
					}else{
						descTramite = tp.getTramite() + "-" + descTramite;
					}
					tpr.setTramite(descTramite);
					tpr.setVersion(tp.getVersion());
					if (backup)
						tpr.setEstado(Constants.BORRADO);	
					else
						tpr.setEstado(Constants.NO_FINALIZADO);
					result.add(tpr);
    			}    		    		
    		}else{
    			Entrada et = (Entrada) o;
    			TramitePersistenteResumen tpr = new TramitePersistenteResumen();
    			tpr.setDescripcion((String) descTramites.get(et.getTramite()));
				tpr.setFecha(new Timestamp(et.getFecha().getTime()));
				tpr.setIdioma(et.getIdioma());
				tpr.setIdPersistencia(et.getIdPersistencia());
				descTramite = (String) descTramites.get(et.getTramite());
				if(descTramite == null) descTramite = "";
				tpr.setTramite(descTramite);
				tpr.setVersion(et.getVersion().intValue());
				if (backup)
					tpr.setEstado(Constants.BORRADO);	
				else{
					if (et instanceof EntradaPreregistro){
						EntradaPreregistro prereg = (EntradaPreregistro) et;
						if (prereg.getFechaConfirmacion() != null)
							tpr.setEstado(Constants.FINALIZADO);
						else
							tpr.setEstado(Constants.PENDIENTE_CONFIRMACION);
					}else{
						tpr.setEstado(Constants.FINALIZADO);
					}
				}
				result.add(tpr);
    		}    		
    		     			
    	}
		
	}
}
