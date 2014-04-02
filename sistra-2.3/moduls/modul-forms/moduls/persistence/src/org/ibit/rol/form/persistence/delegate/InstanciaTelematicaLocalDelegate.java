package org.ibit.rol.form.persistence.delegate;

import org.ibit.rol.form.persistence.intf.InstanciaTelematicaProcessorLocal;
import org.ibit.rol.form.persistence.util.InstanciaTelematicaProcessorUtil;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import java.util.Map;

public class InstanciaTelematicaLocalDelegate extends InstanciaLocalDelegate implements InstanciaTelematicaDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public synchronized void create(String xmlConfiguracion, String xmlInicializacion) throws DelegateException {
        try {
            local = InstanciaTelematicaProcessorUtil.getLocalHome().create(xmlConfiguracion, xmlInicializacion);
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (EJBException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void avanzarPantalla() throws DelegateException {
        try {
            local.avanzarPantalla();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerPropiedadesFormulario() throws DelegateException {
        try {
           return ((InstanciaTelematicaProcessorLocal) local).obtenerPropiedadesFormulario();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public synchronized String obtenerLayOut()  throws DelegateException {
         try {
           return ((InstanciaTelematicaProcessorLocal) local).obtenerLayOut();
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public String tramitarFormulario(boolean guardarSinTerminar) throws DelegateException {
        try {
           return ((InstanciaTelematicaProcessorLocal) local).tramitarFormulario(guardarSinTerminar);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }
    
    public String cancelarFormulario() throws DelegateException {
        try {
           return ((InstanciaTelematicaProcessorLocal) local).cancelarFormulario();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }
    
    public String obtenerUrlSistraMantenimientoSesion()  throws DelegateException {
        try {
           return ((InstanciaTelematicaProcessorLocal) local).obtenerUrlSistraMantenimientoSesion();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }
    
    public  boolean permitirGuardarSinTerminar() throws DelegateException{
        try {
           return ((InstanciaTelematicaProcessorLocal) local).permitirGuardarSinTerminar();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    protected InstanciaTelematicaLocalDelegate() {}
}
