package org.ibit.rol.form.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.ibit.rol.form.model.GrupoUsuario;
import org.ibit.rol.form.model.GrupoUsuarioId;
import org.ibit.rol.form.model.Grupos;
import org.ibit.rol.form.model.RolGrupoFormulario;
import org.ibit.rol.form.model.RolGrupoFormularioId;
import org.ibit.rol.form.model.RolUsuarioFormulario;
import org.ibit.rol.form.model.RolUsuarioFormularioId;

/**
 * SessionBean para consultar idiomas.
 *
 * @ejb.bean
 *  name="form/persistence/GruposFacade"
 *  jndi-name="org.ibit.rol.form.persistence.GruposFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 */
public abstract class GruposFacadeEJB extends HibernateEJB {

    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
    }

    /**
     * Devuelve una lista de grupos.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarGrupos() {
        Session session = getSession();
        try {
            Query query = session.createQuery("from Grupos as grup order by grup.nombre");
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Devuelve un grupo por su código.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public Grupos obtenerGrupo(String codigoGrupo){
    	Session session = getSession();
        try {
        	Grupos grupo = (Grupos) session.get(Grupos.class,codigoGrupo);
            return grupo;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * Guarda el contenido de un grupo.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void guardarGrupo(Grupos grupo){
    	Session session = getSession();
        try {
        	session.save(grupo);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * Modificar el contenido de un grupo.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void modificarGrupo(Grupos grupo){
    	Session session = getSession();
        try {
        	session.update(grupo);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    /**
     * Eliminar un grupo.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void eliminarGrupo(Grupos grupo){
    	Session session = getSession();
        try {
        	session.delete(grupo);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }

    /**
     * lista de grupos asociados a un formulario.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarGruposByForm(Long id){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select grp from RolGrupoFormulario rol, Grupos grp " +
        										"where rol.id.codiGrup = grp.codigo and rol.id.codiForm = ? order by grp.nombre");
        	query.setLong(0,id);
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * lista de usuarios asociados a un formulario.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarUsuariosByForm(Long id){
    	Session session = getSession();
        try {
        	
        	Query query = session.createQuery("from RolUsuarioFormulario rol where rol.id.codiForm=? order by rol.id.codiUsuario");
        	query.setLong(0,id);
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * lista de grupos no asociados a un formulario.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarGruposByNotForm(Long id){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("from Grupos g where g.codigo not in (select grp.codigo from RolGrupoFormulario rol," +
        										" Grupos grp where rol.id.codiGrup = grp.codigo and rol.id.codiForm = ?) order by g.nombre");
        	query.setLong(0,id);
            query.setCacheable(true);
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * Existen grupos
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean existenGrupos(){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select count(g) from Grupos g");
            query.setCacheable(true);
            List valores =  query.list();
            Integer valor = (Integer) valores.get(0);
            if(valor == 0)
            	return false;
            else
            	return true;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * Asociar un grupo a un formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void asociarGrupo(String grupoAsociar, String formulario){
    	Session session = getSession();
        try {
        	RolGrupoFormularioId id = new RolGrupoFormularioId();
        	id.setCodiGrup(grupoAsociar);
        	id.setCodiForm(new Long(formulario));
        	session.save(new RolGrupoFormulario(id));
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    /**
     * Desasociar un grupo a un formulario 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void desAsociarGrupo(String grupoAsociar, String formulario){
    	Session session = getSession();
        try {
        	RolGrupoFormularioId id = new RolGrupoFormularioId();
        	id.setCodiGrup(grupoAsociar);
        	id.setCodiForm(new Long(formulario));
        	session.delete(new RolGrupoFormulario(id));
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
       
    /**
     * lista de usuarios asociados a un grupo
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List obtenerUsuariosByGrupo(String codigo){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("from GrupoUsuario g where g.id.codiGrup = ? order by g.id.usuario");
        	query.setString(0,codigo);
            query.setCacheable(true);
            List usuarios = query.list();
            return usuarios;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    /**
     * comprueba si existe usuarios asociados a un grupo.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean existeUsuariosByGrupo(String codigo){
    	Session session = getSession();
        try {
        	List usuarios = obtenerUsuariosByGrupo(codigo);
        	int valor = usuarios.size();
            if(valor == 0)
            	return false;
            else
            	return true;
        } finally {
            close(session);
        }
    	
    }
    
    /**
     * Asociar usuario a un grupo
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void asociarUsuario(GrupoUsuario grpUser){
    	Session session = getSession();
        try {
        	session.save(grpUser);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Asociar usuario a un formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void asociarUsuarioFormulario(RolUsuarioFormulario userForm){
    	Session session = getSession();
        try {
        	session.save(userForm);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * obtener usuario formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public RolUsuarioFormulario obtenerUsuarioForm(RolUsuarioFormularioId id){
    	Session session = getSession();
        try {
        	return (RolUsuarioFormulario) session.get(RolUsuarioFormulario.class,id);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * eliminar usuario formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void eliminarUsuarioFormulario(RolUsuarioFormulario userForm){
    	Session session = getSession();
        try {
        	session.delete(userForm);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * Obtener asociación usuario grupo
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public GrupoUsuario obtenerUsuarioGrupo(GrupoUsuarioId id){
    	Session session = getSession();
        try {
        	return (GrupoUsuario) session.get(GrupoUsuario.class,id);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    /**
     * Eliminar asociación usuario grupo
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void eliminarUsuarioGrupo(GrupoUsuario grpUser){
    	Session session = getSession();
        try {
        	session.delete(grpUser);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
   
    
    /**
     * Existen usuarios relacionados con el formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean existeUsuarioByForm(String usuario, Long formulario){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select count(g.id.codiUsuario) from RolUsuarioFormulario g where g.id.codiUsuario=? and g.id.codiForm=? ");
            query.setString(0,usuario);
            query.setLong(1,formulario);
        	query.setCacheable(true);
            List valores =  query.list();
            Integer valor = (Integer) valores.get(0);
            if(valor == 0)
            	return false;
            else
            	return true;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    /**
     * Existe usuario en el grupo relacionado con el formulario
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean existeUsuarioByGruposForm(String usuario, Long formulario){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select count(gu.id.usuario) from RolGrupoFormulario gf, GrupoUsuario gu where gu.id.usuario=? " +
        			"and gf.id.codiForm =? and gf.id.codiGrup = gu.id.codiGrup" );
            query.setString(0,usuario);
            query.setLong(1,formulario);
            query.setCacheable(true);
            List valores =  query.list();
            Integer valor = (Integer) valores.get(0);
            if(valor == 0)
            	return false;
            else
            	return true;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    
    
    /**
     * Borra todas las referencias de los grupos con este formulario
     * Se da permiso al operador, porque si borra el formulario se deben borrar las referencias
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarFormularioGrupos(Long formulario){
    	Session session = getSession();
        try {
        	List grupos = listarGruposByForm(formulario);
        	for(int i=0;i<grupos.size();i++){
        		RolGrupoFormularioId id = new RolGrupoFormularioId();
        		id.setCodiGrup(((Grupos)grupos.get(i)).getCodigo());
        		id.setCodiForm(new Long(formulario));
        		session.delete(new RolGrupoFormulario(id));
        	}        	
	    } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }
	
    }
    
    
    /**
     * Borra todas las referencias de los usuaris con este formulario
     * Se da permiso al operador, porque si borra el formulario se deben borrar las referencias
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarFormularioUsuarios(Long formulario){
    	// Borramos referencias formulario
    	Session session = getSession();
        try {
        	List usuarios = listarUsuariosByForm(formulario);
        	for(int i=0;i<usuarios.size();i++){
        		session.delete((RolUsuarioFormulario)usuarios.get(i));
        	}
        } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }
	    	
    }
    
}
