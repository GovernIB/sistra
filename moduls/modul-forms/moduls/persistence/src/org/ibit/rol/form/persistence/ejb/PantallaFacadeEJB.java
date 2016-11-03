package org.ibit.rol.form.persistence.ejb;

import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.Pantalla;

/**
 * SessionBean para mantener y consultar pantallas.
 *
 * @ejb.bean
 *  name="form/persistence/PantallaFacade"
 *  jndi-name="org.ibit.rol.form.persistence.PantallaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class PantallaFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public Long gravarPantalla(Pantalla pantalla, Long formulario_id) {
        Session session = getSession();
        try {
        	
        	Formulario formulario = (Formulario) session.load(Formulario.class,formulario_id);
        	
        	if (!formulario.isBloqueado()){
	           	throw new HibernateException("No tiene bloqueado el formulario " + formulario.getId());
	        }
        	
        	if (formulario.isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(formulario.getBloqueadoPor())){
  	           	throw new HibernateException("Formulario " + formulario.getId() + " bloqueado por otro usuario (" + formulario.getBloqueadoPor() + ")");
  	        }  
        	
            if (pantalla.getId() == null) {
                formulario.addPantalla(pantalla);
                session.flush();
            } else {
            	
            	Query query = session.createQuery("SELECT p.nombre FROM Pantalla AS p WHERE p.id = :id");
            	query.setParameter("id",pantalla.getId());
            	String nomOriginal = query.uniqueResult().toString();
            	            	
            	session.update(pantalla);            	
            	
            	 // -- LISTA ELEMENTOS
                // Si se modifica el nombre de una pantalla que contiene listas de elementos se deben actualizar
            	// las referencias de las pantallas detalle asociadas            	
            	if (!nomOriginal.equals(pantalla.getNombre())){        			
        		    for (Iterator it=pantalla.getCampos().iterator();it.hasNext();){
                    	Campo campo = (Campo) it.next();
                    	if (campo instanceof ListaElementos){
                    		Pantalla detalle = formulario.findPantalla(nomOriginal+"#@#"+campo.getNombreLogico());
                    		if (detalle == null) throw new HibernateException("No se encuentra pantalla detalle lista de elementos : " + nomOriginal+"#@#"+campo.getNombreLogico());
                    		detalle.setNombre(pantalla.getNombre()+"#@#"+campo.getNombreLogico());
                			detalle.setComponenteListaElementos(detalle.getNombre());
                			session.update(detalle);                     			
                    	}
                    }        			            		
        		}
            	// -- LISTA ELEMENTOS            	
            }
            
            return pantalla.getId();
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
    public Pantalla obtenerPantalla(Long id) {
        Session session = getSession();
        try {
            Pantalla pantalla = (Pantalla) session.load(Pantalla.class, id);
            //Hibernate.initialize(pantalla.getComponentes());
            return pantalla;
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
    public Pantalla obtenerPantalla(String modeloFormulario,int versionFormulario,String nombrePantalla) {
        Session session = getSession();
        try {
        	Query query = session.createQuery("SELECT p FROM Formulario f, Pantalla AS p WHERE f.modelo =:modelo and f.version = :version and p.formulario = f and p.nombre = :nombre");
        	query.setParameter("modelo",modeloFormulario);
        	query.setParameter("version",new Integer(versionFormulario));
        	query.setParameter("nombre",nombrePantalla);        	
        	Pantalla pantalla = (Pantalla)  query.uniqueResult();
        	Hibernate.initialize(pantalla.getComponentes());
        	return pantalla;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public List listarPantallasFormulario(Long formulario_id) {
        Session session = getSession();
        try {        	
            Formulario formulario = (Formulario) session.load(Formulario.class, formulario_id);
            List pantallas = formulario.getPantallas();                                   
            Hibernate.initialize(pantallas);
            return pantallas;            
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public void cambiarOrden(Long pantalla1_id, Long pantalla2_id) {
        Session session = getSession();
        try {
            Pantalla p1 = (Pantalla) session.load(Pantalla.class, pantalla1_id);
            Pantalla p2 = (Pantalla) session.load(Pantalla.class, pantalla2_id);
            
            if (!p1.getFormulario().isBloqueado()){
	           	throw new HibernateException("No tiene bloqueado el formulario " + p1.getFormulario().getId());
	        }
            
            if (p1.getFormulario().isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(p1.getFormulario().getBloqueadoPor())){
  	           	throw new HibernateException("Formulario " + p1.getFormulario().getId() + " bloqueado por otro usuario (" + p1.getFormulario().getBloqueadoPor() + ")");
  	        }  

            int aux = p1.getOrden();
            p1.setOrden(p2.getOrden());
            p2.setOrden(aux);

            List pantallas = p1.getFormulario().getPantallas();
            pantallas.set(p1.getOrden(), p1);
            pantallas.set(p2.getOrden(), p2);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     */
    public void borrarPantalla(Long id) {
        Session session = getSession();
        try {
            Pantalla pantalla = (Pantalla) session.load(Pantalla.class, id);
            
            if (!pantalla.getFormulario().isBloqueado()){
	           	throw new HibernateException("No tiene bloqueado el formulario " + pantalla.getFormulario().getId());
	        }
            
            if (pantalla.getFormulario().isBloqueado() && !this.ctx.getCallerPrincipal().getName().equals(pantalla.getFormulario().getBloqueadoPor())){
	           	throw new HibernateException("Formulario " + pantalla.getFormulario().getId() + " bloqueado por otro usuario (" + pantalla.getFormulario().getBloqueadoPor() + ")");
	        }   
            
            // -- LISTA ELEMENTOS
            // Si se borra una pantalla que contiene listas de elementos se debe borrar las pantallas detalle asociadas
            Formulario formulario = pantalla.getFormulario();
            for (Iterator it=pantalla.getCampos().iterator();it.hasNext();){
            	Campo campo = (Campo) it.next();
            	if (campo instanceof ListaElementos){
            		Pantalla detalle = formulario.findPantalla(pantalla.getNombre()+"#@#"+campo.getNombreLogico());
            		formulario.removePantalla(detalle);
            		session.delete(detalle);
            	}
            }
            // -- LISTA ELEMENTOS
            
            
            pantalla.getFormulario().removePantalla(pantalla);
            session.delete(pantalla);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
