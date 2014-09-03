package org.ibit.rol.form.persistence.delegate;

import java.util.Map;

public interface InstanciaTelematicaDelegate extends InstanciaDelegate {

    void create(String xmlConfiguracion, String xmlInicializacion) throws DelegateException;

    void avanzarPantalla() throws DelegateException;

    Map obtenerPropiedadesFormulario() throws DelegateException;

    String obtenerLayOut() throws DelegateException;

    String tramitarFormulario(boolean guardarSinTerminar) throws DelegateException;

    String cancelarFormulario() throws DelegateException;
    
    String obtenerUrlSistraMantenimientoSesion()  throws DelegateException;
    
    boolean permitirGuardarSinTerminar() throws DelegateException;
    
}
