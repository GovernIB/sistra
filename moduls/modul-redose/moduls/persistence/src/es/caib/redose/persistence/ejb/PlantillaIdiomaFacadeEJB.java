package es.caib.redose.persistence.ejb;

import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;

/**
 * SessionBean para mantener y consultar PlantillaIdiomaes.
 *
 * @ejb.bean
 *  name="redose/persistence/PlantillaIdiomaFacade"
 *  jndi-name="es.caib.redose.persistence.PlantillaIdiomaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PlantillaIdiomaFacadeEJB extends HibernateEJB {

	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public PlantillaIdioma obtenerPlantillaIdioma(Long id) {
        Session session = getSession();
        try {        	
        	PlantillaIdioma plantillaIdioma = (PlantillaIdioma) session.load(PlantillaIdioma.class, id);
        	// Inicializamos
        	Hibernate.initialize( plantillaIdioma.getArchivo() );
        	return plantillaIdioma;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
        
	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Long grabarPlantillaIdioma(PlantillaIdioma obj,Long idPlantilla) {    	
        Session session = getSession();
        try {
            if (obj.getCodigo() == null) {
                Plantilla plantilla = (Plantilla) session.load(Plantilla.class, idPlantilla);
                plantilla.addPlantillaIdioma(obj);
                session.flush();
            } else {
                session.update(obj);
            }
            return obj.getCodigo();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public Map listarPlantillasIdiomaPlantilla(Long id) {    	
    	Session session = getSession();
        try {
            Plantilla plantilla = (Plantilla) session.load(Plantilla.class, id);
            Hibernate.initialize(plantilla.getPlantillasIdioma());
            return plantilla.getPlantillasIdioma();            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }        
    }
        
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarPlantillaIdioma(Long id) {
    	Session session = getSession();
        try {
            PlantillaIdioma plantillaIdioma = (PlantillaIdioma) session.load(PlantillaIdioma.class, id);
            plantillaIdioma.getPlantilla().removePlantillaIdioma(plantillaIdioma);            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
}