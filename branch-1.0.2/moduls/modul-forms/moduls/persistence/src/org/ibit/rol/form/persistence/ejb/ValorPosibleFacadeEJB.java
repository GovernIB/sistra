package org.ibit.rol.form.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.TraValorPosible;

/**
 * SessionBean para mantener y consultar valores posibles de campos.
 *
 * @ejb.bean
 *  name="form/persistence/ValorPosibleFacade"
 *  jndi-name="org.ibit.rol.form.persistence.ValorPosibleFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ValorPosibleFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public Long gravarValorPosible(ValorPosible valorPosible, Long campo_id) {
        Session session = getSession();
        try {
            if (valorPosible.getId() == null) {
                Campo campo = (Campo) session.load(Campo.class, campo_id);
                campo.addValorPosible(valorPosible);
                session.flush();
            } else {
                session.update(valorPosible);
            }
            return valorPosible.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public List listarValoresPosiblesCampo(Long campo_id) {
        Session session = getSession();
        try {
            Campo campo = (Campo) session.load(Campo.class, campo_id);
            Hibernate.initialize(campo.getValoresPosibles());
            return campo.getValoresPosibles();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public ValorPosible obtenerValorPosible(Long vp_id) {
        Session session = getSession();
        try {
            ValorPosible vp = (ValorPosible) session.load(ValorPosible.class, vp_id);
            return vp;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public Archivo obtenerImagenValorPosible(Long vp_id, String lang) {
        Session session = getSession();
        try {
            ValorPosible vp = (ValorPosible) session.load(ValorPosible.class, vp_id);
            if (!vp.getCampo().isImagen()) {
                return null;
            }
            TraValorPosible trvp = (TraValorPosible) vp.getTraduccion(lang);
            if (trvp == null || trvp.getArchivo() == null) {
                return null;
            }
            Hibernate.initialize(trvp.getArchivo());
            return trvp.getArchivo();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public void cambiarOrden(Long vp1_id, Long vp2_id) {
        Session session = getSession();
        try {
            ValorPosible vp1 = (ValorPosible) session.load(ValorPosible.class, vp1_id);
            ValorPosible vp2 = (ValorPosible) session.load(ValorPosible.class, vp2_id);
            int aux = vp1.getOrden();
            vp1.setOrden(vp2.getOrden());
            vp2.setOrden(aux);

            List valores = vp1.getCampo().getValoresPosibles();
            valores.set(vp1.getOrden(), vp1);
            valores.set(vp2.getOrden(), vp2);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.form},${role.admin}"
     */
    public void borrarValorPosible(Long id) {
        Session session = getSession();
        try {
            ValorPosible valorPosible = (ValorPosible) session.load(ValorPosible.class, id);
            valorPosible.getCampo().removeValorPosible(valorPosible);
            session.delete(valorPosible);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
