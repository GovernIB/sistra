package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.admin.CuadernoCarga;
import es.caib.sistra.model.admin.FicheroCuaderno;

public abstract class FicheroCuadernoFacadeEJB extends HibernateEJB
{
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
	public Long grabarFicheroCuaderno( FicheroCuaderno obj, Long idCuadernoCarga )
	{
		Session session = getSession();
		try
		{
			if ( obj.getCodigo() == null )
			{
				CuadernoCarga cuadernoCarga = ( CuadernoCarga ) session.load( CuadernoCarga.class, idCuadernoCarga );
				cuadernoCarga.addFichero( obj );
				session.flush();
			}
			else
			{
				session.update( obj );
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
     * @ejb.permission role-name="${role.sistra}"
     */
	public void borrarFicheroCuaderno( Long id )
	{
		Session session = getSession();
		try
		{
			FicheroCuaderno ficheroCuaderno = ( FicheroCuaderno ) session.load( FicheroCuaderno.class, id );
			ficheroCuaderno.getCuadernoCarga().removeFichero( ficheroCuaderno );
		} catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	    	
	        close(session);
	    }
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.auditor}"
     */
	public FicheroCuaderno obtenerFicheroCuaderno( Long id )
	{
		Session session = getSession();
		try
		{
			return ( FicheroCuaderno ) session.load( FicheroCuaderno.class, id );
				
		} catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	    	
	        close(session);
	    }
	}

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.auditor}"
     */
	public List listarFicherosCuaderno( Long idCuadernoCarga )
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM FicheroCuaderno f where f.cuadernoCarga.codigo = :idCuadernoCarga order by f.codigo desc" );
			query.setParameter( "idCuadernoCarga", idCuadernoCarga );
			return query.list();
		}
		catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	    	
	        close(session);
	    } 
	}

}
