package es.caib.redose.persistence.delegate;

import java.rmi.RemoteException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.persistence.intf.PlantillaIdiomaFacade;
import es.caib.redose.persistence.util.PlantillaIdiomaFacadeUtil;

/**
 * Business delegate para operar con dummys.
 */
public class PlantillaIdiomaDelegate implements StatelessDelegate {

    /* ========================================================= */
    /* ======================== MÉTODOS DE NEGOCIO ============= */
    /* ========================================================= */

    public Long grabarPlantillaIdioma(PlantillaIdioma modelo,Long idPlantilla) throws DelegateException {
        try {
            return getFacade().grabarPlantillaIdioma(modelo,idPlantilla);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    public PlantillaIdioma obtenerPlantillaIdioma(Long idPlantillaIdioma) throws DelegateException {
        try {
            return getFacade().obtenerPlantillaIdioma(idPlantillaIdioma);
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }
    
    public Map listarPlantillasIdiomaPlantilla(Long idPlantilla) throws DelegateException {
        try {
            return getFacade().listarPlantillasIdiomaPlantilla(idPlantilla);
        } catch (Exception e) {
        	e.printStackTrace();	
            throw new DelegateException(e);
        }
    }

    public void borrarPlantillaIdioma(Long id) throws DelegateException {
        try {
            getFacade().borrarPlantillaIdioma(id);            
        } catch (Exception e) {
            throw new DelegateException(e);
        }
    }

    /* ========================================================= */
    /* ======================== REFERENCIA AL FACADE  ========== */
    /* ========================================================= */
   private PlantillaIdiomaFacade getFacade() throws NamingException,CreateException,RemoteException {
        return PlantillaIdiomaFacadeUtil.getHome( ).create();
    }

    protected PlantillaIdiomaDelegate() throws DelegateException {       
    }       

}
