package org.ibit.rol.form.persistence.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.FormularioSeguro;
import org.ibit.rol.form.model.ValidadorFirma;

/**
 * SessionBean para matener y consultar validadores de firma
 *
 * @ejb.bean
 *  name="form/persistence/ValidadorFirmaFacade"
 *  jndi-name="org.ibit.rol.form.persistence.ValidadorFirmaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class ValidadorFirmaFacadeEJB extends HibernateEJB{

    protected static Log log = LogFactory.getLog(ValidadorFirmaFacadeEJB.class);
     /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Crea o actualiza un validador de firma
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public Long gravarValidadorFirma(ValidadorFirma validador) {
        Session session = getSession();
        try {
            session.saveOrUpdate(validador);
            return validador.getId();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

     /**
     * Lista todos los validadores
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarValidadoresFirma() {
        Session session = getSession();
        try {
            Query query = session.createQuery("from ValidadorFirma v order by v.nombre");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los validadores a partir de un array de identificadores
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List obtenerValidadoresFirma(Long[] ids) {
        Session session = getSession();
        try {

            Query query = session.createQuery("from ValidadorFirma v where v.id in (:ids)");
            query.setParameterList("ids", ids);
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * Lista todos los ids  de los validadores de un formularioSeguro
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public List listarIdsValidadoresFirmaFormulario(Long formulario_id) {
        Session session = getSession();
        List ids = new ArrayList();
        try {
            FormularioSeguro formulario = (FormularioSeguro)session.load(FormularioSeguro.class, formulario_id);
            Set validadores = formulario.getValidadores();
            for( Iterator iter = validadores.iterator(); iter.hasNext();){
                ValidadorFirma validador = (ValidadorFirma)iter.next();
                ids.add(validador.getId());

            }
            return ids;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }


    /**
     * Obtiene un validador
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public ValidadorFirma obtenerValidadorFirma(Long id) {
        Session session = getSession();
        try {
            ValidadorFirma validador = (ValidadorFirma) session.load(ValidadorFirma.class, id);
            return validador;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

     /**
     * Borra un validador firma
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarValidadorFirma(Long id) {
        Session session = getSession();
        try {
            ValidadorFirma validador = (ValidadorFirma)session.load(ValidadorFirma.class, id);
            Query query = session.createQuery("from  FormularioSeguro f where :validador in elements(f.validadores)");
            query.setParameter("validador", validador);
            if(!query.list().isEmpty()){
                throw new EJBException("El validador tiene formularios asociados");
            }
            session.delete(validador);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
