package org.ibit.rol.form.front.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.TokenProcessor;
import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

/**
 * Mantiene un registro de InstanciaDelegate.
 * @deprecated Ara s'empra la classe {@link org.ibit.rol.form.front.registro.RegistroManager}.
 */
public class InstanciaManager {

    private static final String REGISTRE = "org.ibit.rol.from.front.util.registre";
    private static final String PASSIVATED_PREFIX = "passivated@";

    public static final String ID_INSTANCIA = "ID_INSTANCIA";

    private static final Log log = LogFactory.getLog(InstanciaManager.class);

    public static synchronized void registrarInstancia(HttpServletRequest request, InstanciaDelegate delegate) {
        String idInstancia = TokenProcessor.getInstance().generateToken(request);
        request.setAttribute(ID_INSTANCIA, idInstancia);
        getRegistro(request).put(idInstancia, delegate);
        log.debug("Registrar instancia: " + idInstancia);
    }

    public static InstanciaDelegate recuperarInstancia(HttpServletRequest request) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        if (idInstancia == null) {
            return null;
        }
        final Map registro = getRegistro(request);
        InstanciaDelegate delegate = (InstanciaDelegate) registro.get(idInstancia);
        if (delegate == null) {
            if (activarDelegate(idInstancia, registro)) {
                delegate = (InstanciaDelegate) registro.get(idInstancia);
            }
        }
        return delegate;
    }

    public static boolean tieneInstancias(HttpServletRequest request) {
        return !(getRegistro(request).isEmpty());
    }

    public static synchronized void desregistrarInstancia(HttpServletRequest request) {
        String idInstancia = (String) request.getAttribute(ID_INSTANCIA);
        log.debug("Desregistrar instancia: " + idInstancia);
        InstanciaDelegate delegate = (InstanciaDelegate) getRegistro(request).remove(idInstancia);
        request.removeAttribute(ID_INSTANCIA);
        if (delegate != null) {
            delegate.destroy();
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

    private static synchronized boolean activarDelegate(String idInstancia, Map registro) {
        InstanciaBean bean = (InstanciaBean) registro.remove(PASSIVATED_PREFIX + idInstancia);
        if (bean != null) {
            try {
                InstanciaDelegate delegate = DelegateUtil.getInstanciaDelegate(true);
                delegate.create(bean);
                registro.put(idInstancia, delegate);
                return true;
            } catch (DelegateException e) {
                log.error("Error activando instancia.");
            }
        }
        return false;
    }

    private static class BindingHashMap extends HashMap implements HttpSessionBindingListener, HttpSessionActivationListener {

        public void valueBound(HttpSessionBindingEvent event) {
        }

        public void valueUnbound(HttpSessionBindingEvent event) {
            Set keys = new HashSet(keySet());
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String idInstancia = (String) iterator.next();
                Object o = remove(idInstancia);
                if (o != null && o instanceof InstanciaDelegate) {
                    InstanciaDelegate delegate = (InstanciaDelegate) o;
                    delegate.destroy();
                }
            }
        }

        public void sessionWillPassivate(HttpSessionEvent event) {
            log.debug("Guardando registro " + event.getSession().getId());
            Set keys = new HashSet(keySet());
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String idInstancia = (String) iterator.next();
                Object o = get(idInstancia);
                if (o != null && o instanceof InstanciaDelegate) {
                    InstanciaDelegate delegate = (InstanciaDelegate) o;
                    remove(idInstancia);
                    try {
                        InstanciaBean instanciaBean = delegate.obtenerInstanciaBean();
                        put(PASSIVATED_PREFIX + idInstancia, instanciaBean);
                        log.debug(" - " + idInstancia);
                    } catch (DelegateException e) {
                        log.error("Error obtinguent instància bean.");
                    }
                    delegate.destroy();
                }
            }
        }

        public void sessionDidActivate(HttpSessionEvent event) {
            log.debug("Recuperando registro " + event.getSession().getId());
            Set keys = new HashSet(keySet());
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                String idInstanciaBean = (String) iterator.next();
                log.debug(" - " + idInstanciaBean);
                /* No activar les instàncies, s'activaran quan sigui el moment */
                /*
                if (idInstanciaBean.startsWith(PASSIVATED_PREFIX)) {
                    InstanciaBean bean = (InstanciaBean) get(idInstanciaBean);
                    if (bean != null) {
                        try {
                            InstanciaDelegate delegate = DelegateUtil.getInstanciaDelegate(true);
                            delegate.create(bean);
                            String idInstancia = idInstanciaBean.substring(idInstanciaBean.indexOf("@") + 1);
                            put(idInstancia, delegate);
                            remove(idInstanciaBean);
                        } catch (DelegateException e) {
                            log.error("Recreant delegate.");
                        }
                    }
                } else {
                    log.warn("Valor " + idInstanciaBean + "=" + get(idInstanciaBean) + " no esperat.");
                }
                */
            }
        }
    }
}

