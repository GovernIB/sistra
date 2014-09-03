package org.ibit.rol.form.persistence.delegate;

import org.ibit.rol.form.model.*;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.intf.InstanciaProcessorLocal;
import org.ibit.rol.form.persistence.util.InstanciaProcessorUtil;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.NamingException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Business delegate para operar con componentes.
 */
public class InstanciaLocalDelegate implements InstanciaDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public synchronized void create(String modelo, Locale locale, String perfil) throws DelegateException {
        try {
            local = InstanciaProcessorUtil.getLocalHome().create(modelo, locale, perfil);
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (EJBException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void create(String modelo, Locale locale, String perfil, int version) throws DelegateException {
        try {
            local = InstanciaProcessorUtil.getLocalHome().create(modelo, locale, perfil, version);
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (EJBException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void create(InstanciaBean bean) throws DelegateException {
        try {
            local = InstanciaProcessorUtil.getLocalHome().create(bean);
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (EJBException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Formulario obtenerFormulario() throws DelegateException {
        try {
            return local.obtenerFormulario();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Locale obtenerIdioma() throws DelegateException {
        try {
            return local.obtenerIdioma();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized PerfilUsuario obtenerPerfil() throws DelegateException {
        try {
            return local.obtenerPerfil();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }


    public synchronized Pantalla obtenerPantalla() throws DelegateException {
        try {
            return local.obtenerPantalla();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized AyudaPantalla obtenerAyudaPantalla() throws DelegateException {
        try {
            return local.obtenerAyudaPantalla();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerDatosPantalla() throws DelegateException {
        try {
            return local.obtenerDatosPantalla();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerDatosAnteriores() throws DelegateException {
        try {
            return local.obtenerDatosAnteriores();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized List obtenerPantallasProcesadas() throws DelegateException {
        try {
            return local.obtenerPantallasProcesadas();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Archivo obtenerLogotipo1() throws DelegateException {
        try {
            return local.obtenerLogotipo1();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Archivo obtenerLogotipo2() throws DelegateException {
        try {
            return local.obtenerLogotipo2();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Archivo obtenerPlantilla() throws DelegateException {
        try {
            return local.obtenerPlantilla();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized String obtenerPathIconografia() throws DelegateException {
        try {
            return local.obtenerPathIconografia();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void introducirDatosPantalla(Map valores) throws DelegateException {
        try {
            local.introducirDatosPantalla(valores);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void introducirAnexo(String nombre, Anexo anexo) throws DelegateException {
        try {
            local.introducirAnexo(nombre, anexo);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Object expresionAutocalculoCampo(String nombre) throws DelegateException {
        try {
            return local.expresionAutocalculoCampo(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public synchronized Object expresionAutorellenableCampo(String nombre) throws DelegateException {
        try {
            return local.expresionAutorellenableCampo(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }  

    public synchronized boolean expresionDependenciaCampo(String nombre) throws DelegateException {
        try {
            return local.expresionDependenciaCampo(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public synchronized String expresionDependenciaCampoV2(String nombre) throws DelegateException {
        try {
            return local.expresionDependenciaCampoV2(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public synchronized List expresionValoresPosiblesCampo(String nombre) throws DelegateException {
        try {
            return local.expresionValoresPosiblesCampo(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Anexo obtenerAnexo(String nombre) throws DelegateException {
        try {
            return local.obtenerAnexo(nombre);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void avanzarPantalla() throws DelegateException {
        try {
            local.avanzarPantalla();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void retrocederPantalla() throws DelegateException {
        try {
            local.retrocederPantalla();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void retrocederPantalla(String hasta) throws DelegateException {
        try {
            local.retrocederPantalla(hasta);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized InstanciaBean obtenerInstanciaBean() throws DelegateException {
        try {
            return local.obtenerInstanciaBean();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerDatosCompletos() throws DelegateException {
        try {
            return local.obtenerDatosCompletos();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized String generarNumeroJustificante() throws DelegateException {
        try {
            return local.generarNumeroJustificante();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized String obtenerCaptcha(String fieldName) throws DelegateException {
        try {
            return local.obtenerCaptcha(fieldName);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }
    
    public synchronized void regenerarCaptcha(String fieldName) throws DelegateException {
        try {
            local.regenerarCaptcha(fieldName);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }
    
    public Result[] ejecutarSalidas() throws DelegateException {
        try {
            return local.ejecutarSalidas();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
    }

    
    public synchronized void destroy() {
        try {
            if (local != null) {
                local.remove();
            }
        } catch (EJBException e) {
            ;
        } catch (RemoveException e) {
            ;
        }
    }
    
    // --- INDRA: LISTA ELEMENTOS
    public synchronized List obtenerDatosListaElementos(String nombreCampo) throws DelegateException {
    	try {
            return local.obtenerDatosListaElementos(nombreCampo);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
	}

	public synchronized List obtenerCamposTablaListaElementos(String nombreCampo) throws DelegateException {
		try {
            return local.obtenerCamposTablaListaElementos(nombreCampo);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
	}
	
	public synchronized void avanzarPantallaDetalle(String campo, String accion, String index) throws DelegateException {
		try {
            local.avanzarPantallaDetalle(campo,accion,index);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }
	}
	
	 public synchronized void retrocederPantallaDetalle(boolean saveData) throws DelegateException{
			try {
	            local.retrocederPantallaDetalle(saveData);
	        } catch (EJBException e) {
	            throw new DelegateException(e);
	        }
		}
	
	public synchronized void eliminarElemento(String campo,String indice) throws DelegateException {
		try {
            local.eliminarElemento(campo,indice);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }			
	}
	
	public synchronized Map obtenerDatosListasElementos() throws DelegateException {
		try {
            return local.obtenerDatosListasElementos();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }			
	}
	
	public synchronized void subirElemento(String campo, String indice) throws DelegateException {
		try {
            local.subirElemento(campo,indice);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }	
		
	}

	public synchronized void bajarElemento(String campo, String indice) throws DelegateException {
		try {
            local.bajarElemento(campo,indice);
        } catch (EJBException e) {
            throw new DelegateException(e);
        }	
		
	}
    // --- INDRA: LISTA ELEMENTOS
	
	
	
	// -- INDRA: LOG SCRIPTS
    public List obtenerLogScripts() throws DelegateException{
    	try {
           return local.obtenerLogScripts();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }	
    }
    
    public void limpiarLogScripts() throws DelegateException{
    	try {
            local.limpiarLogScripts();
        } catch (EJBException e) {
            throw new DelegateException(e);
        }	
    }
    // -- INDRA: LOG SCRIPTS   
    
    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    protected InstanciaProcessorLocal local;

    protected InstanciaLocalDelegate() {
    }

	

	

	
	

}
