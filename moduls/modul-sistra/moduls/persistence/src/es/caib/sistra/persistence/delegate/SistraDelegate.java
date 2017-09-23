package es.caib.sistra.persistence.delegate;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.sistra.modelInterfaz.InformacionLoginTramite;
import es.caib.sistra.modelInterfaz.ValoresDominio;
import es.caib.sistra.persistence.intf.SistraFacade;
import es.caib.sistra.persistence.util.SistraFacadeUtil;

/**
 * Facade de sistra
 */
public class SistraDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

	public Map obtenerDescripcionTramites( String idioma ) throws DelegateException {
        try {
            return getFacade().obtenerDescripcionTramites(idioma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }


	public ValoresDominio obtenerDominio( String id,List parametros ) throws DelegateException {
        try {
            return getFacade().obtenerDominio(id,parametros, false);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public ValoresDominio obtenerDominio( String id,List parametros, boolean debugEnabled ) throws DelegateException {
        try {
            return getFacade().obtenerDominio(id,parametros, debugEnabled);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }


	public InformacionLoginTramite obtenerInfoLoginTramite( String modelo,int version, String idioma ) throws DelegateException {
        try {
            return getFacade().obtenerInfoLoginTramite(modelo,version,idioma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

	public boolean finalizarTramitePagadoAnonimo( String idPersistencia )  throws DelegateException {
        try {
            return getFacade().finalizarTramitePagadoAnonimo(idPersistencia);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
	
	public List obtenerListaTramites(String lang)  throws DelegateException {
        try {
            return getFacade().obtenerListaTramites(lang);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
    private SistraFacade getFacade() throws NamingException,RemoteException,CreateException {
    	return SistraFacadeUtil.getHome( ).create();
    }


    protected SistraDelegate() throws DelegateException {
    }
}

