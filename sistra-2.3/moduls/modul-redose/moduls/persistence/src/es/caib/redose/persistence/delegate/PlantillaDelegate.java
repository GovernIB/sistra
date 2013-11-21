package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Set;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.Plantilla;
import es.caib.redose.model.Version;
import es.caib.redose.persistence.intf.PlantillaFacade;
import es.caib.redose.persistence.util.PlantillaFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class PlantillaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarPlantilla(Plantilla modelo,Long idVersion) throws DelegateException {
        try {
            return getFacade().grabarPlantilla(modelo,idVersion);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public Plantilla obtenerPlantilla(Long idPlantilla) throws DelegateException {
        try {
            return getFacade().obtenerPlantilla(idPlantilla);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Plantilla obtenerPlantilla(Version version, String idPlantilla) throws DelegateException {
        try {
            return getFacade().obtenerPlantilla(version,idPlantilla);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Set listarPlantillasVersion(Long idVersion) throws DelegateException {
        try {
            return getFacade().listarPlantillasVersion(idVersion);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarPlantilla(Long id) throws DelegateException {
        try {
            getFacade().borrarPlantilla(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public boolean puedoBorrarPlantilla(Long id) throws DelegateException {
        try {
            return getFacade().puedoBorrarPlantilla(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private PlantillaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PlantillaFacadeUtil.getHome( ).create();
    }

    protected PlantillaDelegate() throws DelegateException {        
    }       

}
