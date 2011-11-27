package org.ibit.rol.form.front.registro;

import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.model.InstanciaBean;

import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * Registro para mantener los datos necesarios de una instancia en la session.
 */
public class RegistroInstancia implements HttpSessionBindingListener, HttpSessionActivationListener, Serializable {

    private InstanciaDelegate delegate;
    private InstanciaBean bean;
    private Result[] results;

    public RegistroInstancia(InstanciaDelegate delegate) {
        this.delegate = delegate;
    }

    public InstanciaDelegate getDelegate() {
        if (delegate == null) {
            if (bean != null) {
                try {
                    delegate = DelegateUtil.getInstanciaDelegate(true);
                    delegate.create(bean);
                } catch (DelegateException e) {
                    delegate = null;
                }
            }
        }
        return delegate;
    }

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }

    // HttpSessionBindingListener

    // No hay necesidad de hacer nada cuando se añade a la session.
    public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
        ;
    }

    // Cuando se elimina de la session és necesario destruir el delegate si aun existe.
    public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (delegate != null) {
            delegate.destroy();
            delegate = null;
        }
    }

    // HttpSessionActivationListener

    // Transformamos el delegate en un bean
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        if (delegate != null) {
            try {
                bean = delegate.obtenerInstanciaBean();
            } catch (DelegateException e) {
                ;
            }
            delegate.destroy();
            delegate = null;
        }
    }

    // No hacemos nada #getDelegate ya se encargará.
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        ;
    }
}
