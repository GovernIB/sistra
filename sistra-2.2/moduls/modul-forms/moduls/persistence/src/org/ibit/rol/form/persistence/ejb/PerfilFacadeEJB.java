package org.ibit.rol.form.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import org.ibit.rol.form.model.PerfilUsuario;

/**
 * SessionBean para matener y consultar perfiles de usuario.
 *
 * @ejb.bean
 *  name="form/persistence/PerfilFacade"
 *  jndi-name="org.ibit.rol.form.persistence.PerfilFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PerfilFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Crea o actualiza un perfil de usuario.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long gravarPerfil(PerfilUsuario perfil) {
        Session session = getSession();
        try {
            session.saveOrUpdate(perfil);
            return perfil.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los perfiles.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarPerfiles() {
        Session session = getSession();
        try {
            //Criteria criteri = session.createCriteria(PerfilUsuario.class);
            //return criteri.list();
            Query query = session.createQuery("from PerfilUsuario p");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene un perfil.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public PerfilUsuario obtenerPerfil(Long id) {
        Session session = getSession();
        try {
            PerfilUsuario perfil = (PerfilUsuario) session.load(PerfilUsuario.class, id);
            return perfil;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Obtiene un perfil.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public PerfilUsuario obtenerPerfil(String codigo) {
        Session session = getSession();
        try {
            /*
            Criteria criteri = session.createCriteria(PerfilUsuario.class);
            criteri.add(Expression.eqProperty("codigoEstandard", codigo));
            List result = criteri.list();
            */
            Query query = session.createQuery("from PerfilUsuario p where p.codigoEstandard = :codigo");
            query.setParameter("codigo", codigo);
            query.setCacheable(true);
            List result = query.list();

            if (result.isEmpty()) {
                return null;
            }

            return (PerfilUsuario) result.get(0);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Borra un perfil de usuario.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarPerfil(Long id) {
        Session session = getSession();
        try {
            session.delete("from AyudaPantalla ayuda where ayuda.perfil.id = ?", id, Hibernate.LONG);
            PerfilUsuario perfil = (PerfilUsuario) session.load(PerfilUsuario.class, id);
            session.delete(perfil);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

}
