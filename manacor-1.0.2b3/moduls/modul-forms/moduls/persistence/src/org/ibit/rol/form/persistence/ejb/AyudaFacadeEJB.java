package org.ibit.rol.form.persistence.ejb;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.PerfilUsuario;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.util.List;

/**
 * SessionBean para mantener y consultar ayudas de pantalla.
 *
 * @ejb.bean
 *  name="form/persistence/AyudaFacade"
 *  jndi-name="org.ibit.rol.form.persistence.AyudaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class AyudaFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public Long gravarAyuda(AyudaPantalla ayuda, Long pantalla_id, Long perfil_id) {
        Session session = getSession();
        try {
            if (ayuda.getId() == null) {
                Pantalla pantalla = (Pantalla) session.load(Pantalla.class, pantalla_id);
                PerfilUsuario perfil = (PerfilUsuario) session.load(PerfilUsuario.class, perfil_id);
                ayuda.setPerfil(perfil);
                pantalla.addAyuda(ayuda);
                session.flush();
            } else {
                if (ayuda.getLangs().size() == 0) {
                    borrarAyuda(ayuda.getId());
                } else {
                    session.update(ayuda);
                }
            }
            return ayuda.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public AyudaPantalla obtenerAyuda(Long id) {
        Session session = getSession();
        try {
            AyudaPantalla ayuda = (AyudaPantalla) session.load(AyudaPantalla.class, id);
            return ayuda;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    /**
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public AyudaPantalla obtenerAyudaPantallaPerfil(Long pantalla_id, Long perfil_id) {
        Session session = getSession();
        try {
            /*
            List results = session.find("from AyudaPantalla ayuda where ayuda.pantalla.id = ? and ayuda.perfil.id = ?",
                    new Object[]{pantalla_id, perfil_id},
                    new Type[]{Hibernate.LONG, Hibernate.LONG});
            */

            Query query = session.createQuery("from AyudaPantalla ayuda where ayuda.pantalla.id = :pantalla and ayuda.perfil.id = :perfil");
            query.setParameter("pantalla", pantalla_id);
            query.setParameter("perfil", perfil_id);
            query.setCacheable(true);
            List results = query.list();

            if (results.isEmpty()) {
                return null;
            }

            return (AyudaPantalla) results.get(0);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public List listarAyudasPantalla(Long pantalla_id) {
        Session session = getSession();
        try {
            Pantalla pantalla = (Pantalla) session.load(Pantalla.class, pantalla_id);
            Query query = session.createFilter(pantalla.getAyudas(), "");
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form}"
     */
    public void borrarAyuda(Long id) {
        Session session = getSession();
        try {
            AyudaPantalla ayuda = (AyudaPantalla) session.load(AyudaPantalla.class, id);
            ayuda.getPantalla().getAyudas().remove(ayuda);
            session.delete(ayuda);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
