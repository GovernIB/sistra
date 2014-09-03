package org.ibit.rol.form.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.naming.NamingException;

import org.ibit.rol.form.model.Anexo;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.PerfilUsuario;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.intf.InstanciaProcessor;
import org.ibit.rol.form.persistence.util.InstanciaProcessorUtil;

/**
 * Business delegate para operar con componentes.
 */
public class InstanciaRemoteDelegate implements InstanciaDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public synchronized void create(String modelo, Locale locale, String perfil) throws DelegateException {
        try {
            InstanciaProcessor remote = InstanciaProcessorUtil.getHome().create(modelo, locale, perfil);
            remoteHandle = remote.getHandle();
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void create(String modelo, Locale locale, String perfil, int version) throws DelegateException {
        try {
            InstanciaProcessor remote = InstanciaProcessorUtil.getHome().create(modelo, locale, perfil);
            remoteHandle = remote.getHandle();
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void create(InstanciaBean bean) throws DelegateException {
        try {
            InstanciaProcessor remote = InstanciaProcessorUtil.getHome().create(bean);
            remoteHandle = remote.getHandle();
        } catch (CreateException e) {
            throw new DelegateException(e);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        } catch (NamingException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Formulario obtenerFormulario() throws DelegateException {
        try {
            return getRemote().obtenerFormulario();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }

    }

    public synchronized Locale obtenerIdioma() throws DelegateException {
        try {
            return getRemote().obtenerIdioma();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }

    }

    public synchronized PerfilUsuario obtenerPerfil() throws DelegateException {
        try {
            return getRemote().obtenerPerfil();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }

    }

    public synchronized Pantalla obtenerPantalla() throws DelegateException {
        try {
            return getRemote().obtenerPantalla();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized AyudaPantalla obtenerAyudaPantalla() throws DelegateException {
        try {
            return getRemote().obtenerAyudaPantalla();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerDatosPantalla() throws DelegateException {
        try {
            return getRemote().obtenerDatosPantalla();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerDatosAnteriores() throws DelegateException {
        try {
            return getRemote().obtenerDatosAnteriores();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized List obtenerPantallasProcesadas() throws DelegateException {
        try {
            return getRemote().obtenerPantallasProcesadas();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Archivo obtenerLogotipo1() throws DelegateException {
        try {
            return getRemote().obtenerLogotipo1();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Archivo obtenerLogotipo2() throws DelegateException {
        try {
            return getRemote().obtenerLogotipo2();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Archivo obtenerPlantilla() throws DelegateException {
        try {
            return getRemote().obtenerPlantilla();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized String obtenerPathIconografia() throws DelegateException {
        try {
            return getRemote().obtenerPathIconografia();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void introducirDatosPantalla(Map valores) throws DelegateException {
        try {
            getRemote().introducirDatosPantalla(valores);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void introducirAnexo(String nombre, Anexo anexo) throws DelegateException {
        try {
            getRemote().introducirAnexo(nombre, anexo);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Object expresionAutorellenableCampo(String nombre) throws DelegateException {
        try {
            return getRemote().expresionAutorellenableCampo(nombre);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }
    
    public synchronized Object expresionAutocalculoCampo(String nombre) throws DelegateException {
        try {
            return getRemote().expresionAutocalculoCampo(nombre);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized boolean expresionDependenciaCampo(String nombre) throws DelegateException {
        try {
            return getRemote().expresionDependenciaCampo(nombre);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }
    
    public synchronized String expresionDependenciaCampoV2(String nombre) throws DelegateException {
        try {
            return  getRemote().expresionDependenciaCampoV2(nombre);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public synchronized List expresionValoresPosiblesCampo(String nombre) throws DelegateException {
        try {
            return getRemote().expresionValoresPosiblesCampo(nombre);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Anexo obtenerAnexo(String nombre) throws DelegateException {
        try {
            return getRemote().obtenerAnexo(nombre);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void avanzarPantalla() throws DelegateException {
        try {
            getRemote().avanzarPantalla();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void retrocederPantalla() throws DelegateException {
        try {
            getRemote().retrocederPantalla();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized void retrocederPantalla(String hasta) throws DelegateException {
        try {
            getRemote().retrocederPantalla(hasta);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized InstanciaBean obtenerInstanciaBean() throws DelegateException {
        try {
            return getRemote().obtenerInstanciaBean();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public synchronized Map obtenerDatosCompletos() throws DelegateException {
        try {
            return getRemote().obtenerDatosCompletos();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }


    public synchronized String generarNumeroJustificante() throws DelegateException {
        try {
            return getRemote().generarNumeroJustificante();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }

    public Result[] ejecutarSalidas() throws DelegateException {
        try {
            return getRemote().ejecutarSalidas();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
    }
    
    // --- INDRA: LISTA ELEMENTOS 
    public synchronized List obtenerDatosListaElementos(String nombreCampo) throws DelegateException {
    	  try {
              return getRemote().obtenerDatosListaElementos(nombreCampo);
          } catch (RemoteException e) {
              throw new DelegateException(e);
          }
	}

	public synchronized List obtenerCamposTablaListaElementos(String nombreCampo) throws DelegateException {
		  try {
	            return getRemote().obtenerCamposTablaListaElementos(nombreCampo);
	        } catch (RemoteException e) {
	            throw new DelegateException(e);
	        }
	}
	
	public synchronized void avanzarPantallaDetalle(String campo, String accion, String index) throws DelegateException {
		try {
			getRemote().avanzarPantallaDetalle(campo,accion,index);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }
	}
	
	 public synchronized void retrocederPantallaDetalle(boolean saveData) throws DelegateException{
			try {
				getRemote().retrocederPantallaDetalle(saveData);
	        } catch (RemoteException e) {
	            throw new DelegateException(e);
	        }
		}	
	
	public synchronized void eliminarElemento(String campo, String indice) throws DelegateException {
		try {
			getRemote().eliminarElemento(campo,indice);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
		
	}
	public synchronized Map obtenerDatosListasElementos() throws DelegateException {
		try {
			return getRemote().obtenerDatosListasElementos();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }			
	}
	public synchronized void subirElemento(String campo, String indice) throws DelegateException {
		try {
			getRemote().subirElemento(campo,indice);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
		
	}

	public synchronized void bajarElemento(String campo, String indice) throws DelegateException {
		try {
			getRemote().bajarElemento(campo,indice);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
		
	}
		
	// --- INDRA: LISTA ELEMENTOS
	
	
	
	//	 -- INDRA: LOG SCRIPTS
    public List obtenerLogScripts() throws DelegateException{
    	try {
           return getRemote().obtenerLogScripts();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
    }
    
    public void limpiarLogScripts() throws DelegateException{
    	try {
    		getRemote().limpiarLogScripts();
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
    }
    // -- INDRA: LOG SCRIPTS
	
    public synchronized String obtenerCaptcha(String fieldName) throws DelegateException {
    	try {
    		return getRemote().obtenerCaptcha(fieldName);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
    }
    
    public synchronized void regenerarCaptcha(String fieldName) throws DelegateException{
    	try {
    		getRemote().regenerarCaptcha(fieldName);
        } catch (RemoteException e) {
            throw new DelegateException(e);
        }	
    }
    
    public synchronized void destroy() {
        try {
            getRemote().remove();
        } catch (RemoteException e) {
            ;
        } catch (RemoveException e) {
            ;
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */

    private Handle remoteHandle;

    private InstanciaProcessor getRemote() throws RemoteException {
        return (InstanciaProcessor) remoteHandle.getEJBObject();
    }

    protected InstanciaRemoteDelegate() {
    }

	

	

}
