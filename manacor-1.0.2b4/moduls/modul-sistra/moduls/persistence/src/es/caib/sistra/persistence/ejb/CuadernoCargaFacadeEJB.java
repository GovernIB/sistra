package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.model.admin.CuadernoCarga;

/**
 *  @ejb.bean
 *  name="sistra/persistence/CuadernoCargaFacade"
 *  jndi-name="es.caib.sistra.persistence.CuadernoCargaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  
 *  @ejb.env-entry name="role.sistra" type="java.lang.String" value="${role.sistra}"
 *  @ejb.env-entry name="role.auditor" type="java.lang.String" value="${role.auditor}"
 *  
 * @author clmora
 *
 */
public abstract class CuadernoCargaFacadeEJB extends HibernateEJB
{
	
	private String roleSistra;
	private String roleAuditor;
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
		 
		try
		{
			javax.naming.InitialContext initialContext = new javax.naming.InitialContext();
			roleSistra = ( String ) initialContext.lookup( "java:comp/env/role.sistra" );
			roleAuditor =( String ) initialContext.lookup( "java:comp/env/role.auditor" );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
		

	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.auditor}"
     */
	public Long grabarCuadernoCarga( CuadernoCarga obj )
	{
		Session session = getSession();
		try
		{
			session.saveOrUpdate( obj );
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
     * @ejb.permission role-name="${role.auditor}"
     */
	public CuadernoCarga obtenerCuadernoCarga( Long id )
	{
		Session session = getSession();
		try
		{
			CuadernoCarga cuadernoCarga = ( CuadernoCarga ) session.load( CuadernoCarga.class, id );
			Hibernate.initialize( cuadernoCarga.getFicheros() );
			return cuadernoCarga;
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
	public void borrarCuadernoCarga( Long id )
	{
		Session session = getSession();
		try
		{
			CuadernoCarga cuaderno = ( CuadernoCarga ) session.load( CuadernoCarga.class, id );
			session.delete( cuaderno );
		}
		catch (HibernateException he) {
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
	public List listarCuadernosCarga()
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM CuadernoCarga c order by c.fechaCarga" );
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
	 * Método que, para un usuario con perfil desarrollador, devuelve los cuadernos 
	 * pendientes de envio o pendientes de auditar, o en su caso aquellos que no han sido importados y
	 * ( están auditados o no requieren auditoría )  
	 * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
	 * @return
	 */
	public List listarCuadernosPendientesDesarrollador()
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria IN ( '" + CuadernoCarga.PENDIENTE_ENVIO + "', '" + CuadernoCarga.PENDIENTE_AUDITAR + "' ) OR ( c.estadoAuditoria IN ('" + CuadernoCarga.AUDITADO+ "', '" + CuadernoCarga.NO_REQUIERE_AUDITORIA + "') AND c.importado = 'N' ) order by c.fechaCarga" );
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
	 * Método que devuelve todos los cuadernos pendientes de auditar para un usuario auditor
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auditor}"
     */
	public List listarCuadernosPendientesAuditoria()
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria = '" + CuadernoCarga.PENDIENTE_AUDITAR + "' order by c.fechaCarga" );
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	
	/**
	 * Método que obtiene cuadernos pendientes en función de que el usuario tenga rol de auditor o sólamente de desarrollador
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.auditor}"
     */
	public List listarCuadernosPendientes()
	{
		Session session = getSession();
		try
		{
			Query query = null;
			if ( isUserAuditor() )
			{
				query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria = '" + CuadernoCarga.PENDIENTE_AUDITAR + "' order by c.fechaCarga" );
			}
			else
			{
				query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria IN ( '" + CuadernoCarga.PENDIENTE_ENVIO + "', '" + CuadernoCarga.PENDIENTE_AUDITAR + "' ) OR ( c.estadoAuditoria IN ('" + CuadernoCarga.AUDITADO+ "', '" + CuadernoCarga.NO_REQUIERE_AUDITORIA + "') AND c.importado = 'N' ) order by c.fechaCarga" );
			}
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
	 * Método que obtiene para un usuario desarrollador los cuadernos finalizados, es decir,
	 * rechazados, o auditados e importados, o que no requieren auditoria e importados 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     */
	public List listarCuadernosFinalizados()
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria = '" + CuadernoCarga.RECHAZADO + "' OR ( c.estadoAuditoria IN ('" + CuadernoCarga.AUDITADO+ "', '" + CuadernoCarga.NO_REQUIERE_AUDITORIA + "') AND c.importado = 'S' ) order by c.fechaCarga DESC" );
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
	 * Método que devuelve los cuadernos auditados o rechazados para un usuario con rol auditor
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auditor}"
     */
	public List listarCuadernosAuditados()
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria IN ( '" + CuadernoCarga.AUDITADO + "', '" + CuadernoCarga.RECHAZADO + "' ) order by c.fechaCarga DESC" );
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	/**
	 * Método que en función del rol del usuario llamante depende los cuadernos que han finalizado su gestión
     * @ejb.interface-method
     * @ejb.permission role-name="${role.sistra}"
     * @ejb.permission role-name="${role.auditor}"
     */
	public List listarCuadernosGestionados()
	{
		Session session = getSession();
		try
		{
			Query query = null;
			// Para usuario con rol auditor auditados o rechazados
			if ( isUserAuditor() )
			{
				query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria IN ( '" + CuadernoCarga.AUDITADO + "', '" + CuadernoCarga.RECHAZADO + "' ) order by c.fechaCarga DESC" );
			}
			// Para el resto, rechazados, o auditados e importados, o que no requieren auditoria e importados
			else
			{
				query = session.createQuery( "FROM CuadernoCarga c where c.estadoAuditoria = '" + CuadernoCarga.RECHAZADO + "' OR ( c.estadoAuditoria IN ('" + CuadernoCarga.AUDITADO+ "', '" + CuadernoCarga.NO_REQUIERE_AUDITORIA + "') AND c.importado = 'S' ) order by c.fechaCarga DESC" );
			}
			return query.list();
		}
		catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
	}
	
	private boolean isUserAuditor()
    {
    	return this.ctx.isCallerInRole( roleAuditor );
    }
    
    private boolean isUserAdmin()
    {
    	return this.ctx.isCallerInRole( roleSistra ) && !this.ctx.isCallerInRole( roleAuditor );
    }

}
