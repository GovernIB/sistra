package es.caib.sistra.front.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.TokenProcessor;

import es.caib.sistra.front.SesionCaducadaException;
import es.caib.sistra.model.InstanciaBean;
import es.caib.sistra.persistence.delegate.DelegateException;
import es.caib.sistra.persistence.delegate.DelegateUtil;
import es.caib.sistra.persistence.delegate.InstanciaDelegate;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;

/**
 * Mantiene un registro de InstanciaDelegate.
 */
public class InstanciaManager 
{
    private static final String REGISTRE = "es.caib.sistra.front.util.registre";
    public static final String ID_INSTANCIA = "ID_INSTANCIA";

    private static final Log log = LogFactory.getLog(InstanciaManager.class);
    
    public static String getIdInstancia( HttpServletRequest request )
    {
    	return (String) request.getAttribute(ID_INSTANCIA);
    }

    public static void registrarInstancia(HttpServletRequest request, InstanciaDelegate delegate) {
        String idInstancia = TokenProcessor.getInstance().generateToken(request);
        request.setAttribute(ID_INSTANCIA, idInstancia);
        getRegistro(request).put(idInstancia, delegate);
        log.info("Registrar instancia: " + idInstancia);
    }
    
    public static InstanciaDelegate recuperarInstancia( String idInstancia, HttpServletRequest request ) throws SesionCaducadaException
    {
    	if ( StringUtils.isEmpty( idInstancia ) )
    	{
    		idInstancia = (String) request.getAttribute(ID_INSTANCIA);
    	}
    	else
    	{
    		request.setAttribute( ID_INSTANCIA, idInstancia );
    	}
    	if (idInstancia == null) {
            log.error("Instancia no especificada como atributo ni presente en la request");
            throw new SesionCaducadaException(new Exception("Instancia no especificada como atributo ni presente en la request"));            
        }
    	InstanciaDelegate delegate = (InstanciaDelegate) getRegistro(request).get(idInstancia);
    	if (delegate == null){
    		log.error("No esta registrada la instancia especificada");
            throw new SesionCaducadaException(new Exception("No esta registrada la instancia especificada"));
    	}
    	return delegate;
    }

    public static InstanciaDelegate recuperarInstancia(HttpServletRequest request) throws SesionCaducadaException {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);        
        return recuperarInstancia( idInstancia, request );
    }

    public static void desregistrarInstancia(HttpServletRequest request) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        log.info("Desregistrar instancia: " + idInstancia);
        InstanciaDelegate delegate = (InstanciaDelegate) getRegistro(request).remove(idInstancia);
        request.removeAttribute(ID_INSTANCIA);
        if (delegate != null) {
            delegate.destroy();
        } else {
            log.warn("Delegate es null en desregistrar instancia");
        }
    }

    private static Map getRegistro(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Map map = (Map) session.getAttribute(REGISTRE);
        if (map == null) {
            map = new BindingHashMap();
            session.setAttribute(REGISTRE, map);
        }
        return map;
    }

    private static class BindingHashMap extends HashMap implements HttpSessionBindingListener, HttpSessionActivationListener {

        public void valueBound(HttpSessionBindingEvent event) {
        }

        public void valueUnbound(HttpSessionBindingEvent event) {
            Set keys = new HashSet(keySet());
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String idInstancia = (String) iterator.next();
                Object o = get(idInstancia);
                if (o != null && o instanceof InstanciaDelegate) {
                    InstanciaDelegate delegate = (InstanciaDelegate) o;
                    remove(idInstancia);
                    delegate.destroy();
                }
            }
        }

        public void sessionWillPassivate(HttpSessionEvent event) {
            Set keys = new HashSet(keySet());
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String idInstancia = (String) iterator.next();
                Object o = get(idInstancia);
                if (o != null && o instanceof InstanciaDelegate) {
                    InstanciaDelegate delegate = (InstanciaDelegate) o;
                    remove(idInstancia);
                    try {
                        InstanciaBean instanciaBean = delegate.obtenerInstanciaBean();
                        put("passivated@" + idInstancia, instanciaBean);
                    } catch (DelegateException e) {
                        log.error("Error obteniendo instancia bean",e);
                    }
                    delegate.destroy();
                }
            }
        }

        public void sessionDidActivate(HttpSessionEvent event) 
        {
            Set keys = new HashSet(keySet());
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) 
            {
                String idInstanciaBean = (String) iterator.next();
                InstanciaBean bean = (InstanciaBean) get(idInstanciaBean);
                if (bean != null) 
                {
                    try 
                    {
                        InstanciaDelegate delegate = DelegateUtil.getInstanciaDelegate( true );
                        
                        // Comprobamos si funcionamos en modo delegado
            			String perfilAcceso = (String) event.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_KEY);
            			String nifEntidad = null;
            			if (ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO.equals(perfilAcceso) ){
            				nifEntidad = (String) event.getSession().getAttribute(ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO_ENTIDAD_KEY);
            			}
                        
                        delegate.create(bean.getIdTramite(), bean.getVersion(),bean.getNivelAutenticacion(), bean.getLocale(),null, perfilAcceso, nifEntidad );
                        // Cargar el ejb de sesion con el estado correspondiente
                        delegate.cargarTramite( bean.getIdPersistencia() );
                        String idInstancia = idInstanciaBean.substring(idInstanciaBean.indexOf("@") + 1);
                        put(idInstancia, delegate);
                        remove(idInstanciaBean);
                    } 
                    catch (DelegateException e) 
                    {
                        log.error("Recreando delegate",e);
                    }
                }
            }
        }
    }

}

