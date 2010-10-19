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
import es.caib.zonaper.helpdesk.front.form.BusquedaTramiteBackupForm;
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
 *  name="busquedaTramiteBackupForm"
 *  path="/busquedaTramitesBackup"
 *  scope="request"
 *  validate="true"
 *  
 *  
 * @struts.action-forward
 *  name="success" path=".busquedaTramitesBackup"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class BusquedaTramitesBackupAction extends BaseAction
{
	
	
	protected static String FIN_FORMULARIO = "</FORMULARIO>";
	
	protected static Log log = LogFactory.getLog(BusquedaTramitesBackupAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {

		BusquedaTramiteBackupForm backupForm = ( BusquedaTramiteBackupForm ) form;
		
		request.getSession().setAttribute(Constants.OPCION_SELECCIONADA_KEY,Constants.BACKUP_TAB);		
		request.setAttribute("nivel", Character.toString(backupForm.getNivelAutenticacion()));

		
		if(backupForm.getNivelAutenticacion() == Constants.MODO_AUTENTICACION_ANONIMO)
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
	
		BusquedaTramiteBackupForm backupForm = ( BusquedaTramiteBackupForm ) form;
		List result = new ArrayList();
		
		if(StringUtils.isEmpty(backupForm.getClavePersistencia())){
			request.setAttribute( "estado", Constants.NO_EXISTE);
			return mapping.findForward( "success" );
		}
		
		try{
			String clave = backupForm.getClavePersistencia();
			TramitePersistenteDelegate td = DelegateUtil.getTramitePersistenteDelegate();
    		TramitePersistente tp = td.obtenerTramitePersistenteBackup(clave);
    		
//    		 Obtenemos descripcion tramites
			Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(Constants.DEFAULT_LANG);
			
			
    		if (tp != null) {
    			if (tp.getNivelAutenticacion() == 'A'){
    				List tramites = new ArrayList();
    				tramites.add(tp);
    				addToResults(result,tramites,null,descTramites,true);
    				request.setAttribute( "lstTramites", result);
    				return mapping.findForward( "success" );
    			} 
    		}
    	}catch (Exception ex){
    		log.error("Error obteniendo estado tramite anonimo en persistencia backup",ex);
			request.setAttribute( "estado", Constants.ERROR_BUSQUEDA);
			return mapping.findForward( "success" );
    	}	
    	
    	request.setAttribute( "estado", Constants.NO_EXISTE);
		return mapping.findForward( "success" );
		
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
				BusquedaTramiteBackupForm backupForm = ( BusquedaTramiteBackupForm ) form;
				if((backupForm.getUsuarioNif() == null) || backupForm.getUsuarioNif().equals(""))
				{
					request.setAttribute( "estado", "N");
					return mapping.findForward( "success" );
				}
				
				// Buscamos usuario seycon asociado
				log.debug("Buscamos usuario seycon para nif " + backupForm.getUsuarioNif());
				PersonaPAD persona = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(backupForm.getUsuarioNif());
				if (persona == null){
					log.debug("No se encuentra usuario seycon para nif " + backupForm.getUsuarioNif());
					request.setAttribute( "estado", "N");
					return mapping.findForward( "success" );
				}
				
				
				// Formateamos fecha inicio - fin 
				log.debug("Fecha: " + backupForm.getFecha() + " HoraInicial: " +
						backupForm.getHoraInicial() + " HoraFinal: " +
						backupForm.getHoraFinal());			
				SimpleDateFormat df = new SimpleDateFormat(Constants.FORMATO_FECHAS);
				String fecha = backupForm.getFecha();
				Date fechaInicial = df.parse(fecha + " " + backupForm.getHoraInicial()+ ":" +
						backupForm.getMinInicial());
				Date fechaFinal = df.parse(fecha + " " + backupForm.getHoraFinal() + ":" +
						backupForm.getMinFinal());
	
				// Obtenemos descripcion tramites
				Map descTramites = PluginAudita.getInstance().obtenerDescripcionTramites(Constants.DEFAULT_LANG);
				
				
				// -------------------------------------------
				// 2 - REALIZAMOS BUSQUEDA DE TRAMITE
				// -------------------------------------------
//				 * Buscamos en Backup de persistencia
				TramitePersistenteDelegate delegate = DelegateUtil.getTramitePersistenteDelegate();
				List tramites = delegate.listarTramitesPersistentesBackup(fechaInicial,fechaFinal,/* modelo */null,Character.toString(backupForm.getNivelAutenticacion()));
				addToResults(result,tramites,persona.getUsuarioSeycon(),descTramites,true);
				
				
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
    private void addToResults(List result, List tramites, String usuarioSeycon, Map descTramites, boolean backup) {
    	String descTramite;
    	
    	for(Iterator it = tramites.iterator(); it.hasNext();)
    	{
    		Object o = it.next();
    		
    		if (o instanceof TramitePersistente){
    			TramitePersistente tp = (TramitePersistente) o;    			
    			if("A".equals(tp.getNivelAutenticacion()+"") || (usuarioSeycon != null && usuarioSeycon.equals(tp.getUsuario()))){
    				TramitePersistenteResumen tpr = new TramitePersistenteResumen();
					tpr.setDescripcion((String) descTramites.get(tp.getTramite()));
					tpr.setFecha(tp.getFechaModificacion());
					tpr.setIdioma(tp.getIdioma());
					tpr.setIdPersistencia(tp.getIdPersistencia());
					descTramite = (String) descTramites.get(tp.getTramite());
					if(descTramite == null) descTramite = "";
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
