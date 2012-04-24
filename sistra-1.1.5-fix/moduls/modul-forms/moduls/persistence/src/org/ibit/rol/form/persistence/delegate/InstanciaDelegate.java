package org.ibit.rol.form.persistence.delegate;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ibit.rol.form.model.Anexo;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.PerfilUsuario;
import org.ibit.rol.form.persistence.conector.Result;

/**
 * Interficie per interactuar amb l'InstanciaProcesor.
 */
public interface InstanciaDelegate extends Delegate {

    /**
     * @deprecated
     */
    void create(String modelo, Locale locale, String perfil) throws DelegateException;

    void create(String modelo, Locale locale, String perfil, int version) throws DelegateException;

    void create(InstanciaBean bean) throws DelegateException;

    Formulario obtenerFormulario() throws DelegateException;

    Locale obtenerIdioma() throws DelegateException;

    PerfilUsuario obtenerPerfil() throws DelegateException;

    Pantalla obtenerPantalla() throws DelegateException;

    AyudaPantalla obtenerAyudaPantalla() throws DelegateException;

    Map obtenerDatosPantalla() throws DelegateException;

    Map obtenerDatosAnteriores() throws DelegateException;

    List obtenerPantallasProcesadas() throws DelegateException;

    Archivo obtenerLogotipo1() throws DelegateException;

    Archivo obtenerLogotipo2() throws DelegateException;

    Archivo obtenerPlantilla() throws DelegateException;

    String obtenerPathIconografia() throws DelegateException;

    void introducirDatosPantalla(Map valores) throws DelegateException;

    void introducirAnexo(String nombre, Anexo anexo) throws DelegateException;
    
    Object expresionAutorellenableCampo(String nombre) throws DelegateException;

    Object expresionAutocalculoCampo(String nombre) throws DelegateException;

    boolean expresionDependenciaCampo(String nombre) throws DelegateException;

    String expresionDependenciaCampoV2(String nombre)throws DelegateException;
    
    List expresionValoresPosiblesCampo(String nombre) throws DelegateException;

    Anexo obtenerAnexo(String nombre) throws DelegateException;

    void avanzarPantalla() throws DelegateException;

    void retrocederPantalla() throws DelegateException;

    void retrocederPantalla(String hasta) throws DelegateException;

    InstanciaBean obtenerInstanciaBean() throws DelegateException;

    Map obtenerDatosCompletos() throws DelegateException;

    String generarNumeroJustificante() throws DelegateException;

    Result[] ejecutarSalidas() throws DelegateException;

    void destroy();
    
    // -- INDRA: LISTA ELEMENTOS
    public List obtenerDatosListaElementos(String nombreCampo)throws DelegateException;
    public List obtenerCamposTablaListaElementos(String nombreCampo)throws DelegateException;
    public void avanzarPantallaDetalle(String campo,String accion,String index) throws DelegateException;
    public void retrocederPantallaDetalle(boolean saveData) throws DelegateException;        
    public void eliminarElemento(String campo,String indice)throws DelegateException;   
    public Map obtenerDatosListasElementos() throws DelegateException;           
    public void subirElemento(String campo,String indice)throws DelegateException;   
    public void bajarElemento(String campo,String indice) throws DelegateException;   
    // -- INDRA: FIN	
    
    // -- INDRA: LOG SCRIPT
    public List obtenerLogScripts() throws DelegateException;
    public void limpiarLogScripts() throws DelegateException; 
    // -- INDRA: LOG SCRIPT
    
}
