package es.caib.consola.util;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import es.caib.consola.ConstantesWEB;
import es.caib.consola.model.Usuario;
import es.caib.consola.model.types.TypeModoAcceso;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.sistra.model.OrganoResponsable;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.zkutils.ConstantesZK;
import es.caib.zkutils.zk.composer.BaseComposer;

/**
 * Utilidades consola.
 * @author rsanz
 *
 */
public class ConsolaUtil {

	/**
	 * Simulamos usuario logado.
	 */
	private static Usuario usuarioLogado = null;
	
	
	/**
	 * Recupera usuario logado.
	 * @return Usuario
	 */
	public static Usuario recuperarUsuarioLogado() {
		
		if (usuarioLogado == null) {
			String nombreCompleto = null;
			String userName = null;
			String idioma = null;
			try {
				Principal principal = Executions.getCurrent().getUserPrincipal();
				nombreCompleto = PluginFactory.getInstance().getPluginLogin().getNombreCompleto(principal);
				userName = principal.getName();
				// TODO IDIOMA X DEFECTO
				idioma = ConstantesWEB.ESPANYOL;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
	        }
			
			usuarioLogado = new Usuario();
			usuarioLogado.setUsername(userName);
	        usuarioLogado.setNombreCompleto(nombreCompleto);	        
	        usuarioLogado.setIdioma(idioma);
	        
	        try {
		        List organos = DelegateUtil.getOrganoResponsableDelegate().listarOrganoResponsables();
		        if (organos != null && organos.size() > 0) {
		        	usuarioLogado.setOrganismo( ((OrganoResponsable) organos.get(0)).getCodigo());
		        }
	        } catch (Exception ex) {
	        	ConsolaUtil.generaDelegateException(ex);
	        }
	        
	        usuarioLogado.setModoAcceso(TypeModoAcceso.EDICION);
	        
	        try {
				String entorno = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("entorno");
				usuarioLogado.setEntorno(entorno);
			} catch (DelegateException e) {
				ConsolaUtil.generaDelegateException(e);				
			}
	        
		}
        return usuarioLogado;
	}	
	
	/**
	 * Logout.
	 */
	public static void logout(){
		// TODO PENDIENTE LOGOUT
		Executions.sendRedirect("http:///www.google.es");
	}
	
	/**
	 * Convierte DelegateException en RuntimeException.
	 * @param ex Exception 
	 */
	public static void generaDelegateException(Exception ex) {
		// TODO VER QUE PASA CON LAS DELEGATE EXCEPTION
		throw new RuntimeException(ex);
	}
	
	/**
	 * Genera excepcion operacion no permitido.
	 * @return RuntimeException
	 */
	public static void generaOperacionNoPermitidaException() {
		// TODO VER COMO GESTIONAR ERRORES
		throw new RuntimeException(Labels.getLabel("mensaje.operacionNoPermitida"));
	}
	
	/**
	 * Obtiene evento segun modo acceso.
	 * @param modo Modo acceso
	 * @return evento
	 */
	public static String eventoModoAcesso(TypeModoAcceso modo) {
		return (modo == TypeModoAcceso.ALTA?ConstantesZK.EVENTO_POST_ALTA : ConstantesZK.EVENTO_POST_MODIFICACION);
	}
	
	/**
	 * Convierte set to list.
	 * @param set Set
	 * @return List
	 */
	public static List setToList(Set set) {
		List list = new ArrayList();
	 	if (set != null) {
			for (Iterator it = set.iterator();it.hasNext();) {				
				list.add(it.next());
			}
		}
	 	return list;
	}
	
	/**
	 * Convierte set to list.
	 * @param set Set
	 * @return List
	 */
	public static Set listToSet(List list) {
		Set set = new HashSet(0);
	 	if (list != null) {
			for (Iterator it = list.iterator();it.hasNext();) {				
				set.add(it.next());
			}
		}
	 	return set;
	}

	/**
	 * Genera excepcion no permitido borrar.
	 * @return RuntimeException
	 */
	public static void generaNoPermitidoBorrarException() {
		throw new RuntimeException(Labels.getLabel("mensaje.eliminarTieneElementosAsociados"));
	}
	
	/**
	 * Abre selector organismo. Se debera implementar onSeleccionOrganismo para recuperar el valor seleccionado.
	 * @param comp Composer
	 */
	public static void abrirSelectorOrganismo(BaseComposer comp) {
		final Window ventana = (Window) comp.creaComponente(
				"/selectores/sel-organismo-win.zul", comp.getComponent(), null);			
        comp.abreVentanaModal(ventana);
	}
	 
	/**
	 * Abre selector UA. Se debera implementar onSeleccionOrganismo para recuperar el valor seleccionado.
	 * @param comp Composer
	 */
	public static void abrirSelectorUnidadAdministrativa(BaseComposer comp) {
		final Window ventana = (Window) comp.creaComponente(
				"/selectores/sel-unidadAdministrativa-win.zul", comp.getComponent(), null);			
        comp.abreVentanaModal(ventana);
	}
	
	/**
	 * Abre selector Gestor. Se debera implementar onSeleccionOrganismo para recuperar el valor seleccionado.
	 * @param comp Composer
	 */
	public static void abrirSelectorGestor(BaseComposer comp) {
		final Window ventana = (Window) comp.creaComponente(
				"/selectores/sel-gestor-win.zul", comp.getComponent(), null);			
        comp.abreVentanaModal(ventana);
	}
	
	/**
	 * Abre selector Procedimiento. Se debera implementar onSeleccionOrganismo para recuperar el valor seleccionado.
	 * @param comp Composer
	 */
	public static void abrirSelectorProcedimiento(BaseComposer comp) {
		final Window ventana = (Window) comp.creaComponente(
				"/selectores/sel-procedimiento-win.zul", comp.getComponent(), null);			
        comp.abreVentanaModal(ventana);
	}

	/**
	 * Convierte map a list.
	 * @param map map
	 * @return list
	 */
	public static List<PlantillaIdioma> mapToList(
			Map map) {
		List list = new ArrayList();
	 	if (map != null) {
			for (Iterator it = map.keySet().iterator();it.hasNext();) {				
				list.add(map.get(it.next()));
			}
		}
	 	return list;
	}
	
}
