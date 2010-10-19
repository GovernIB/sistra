package es.caib.sistra.persistence.ejb;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import es.caib.sistra.model.GrupoUsuario;
import es.caib.sistra.model.GrupoUsuarioId;
import es.caib.sistra.model.Grupos;
import es.caib.sistra.model.RolGrupoTramite;
import es.caib.sistra.model.RolGrupoTramiteId;
import es.caib.sistra.model.RolUsuarioTramite;
import es.caib.sistra.model.RolUsuarioTramiteId;

/**
 * SessionBean para consultar idiomas.
 *
 * @ejb.bean
 *  name="sistra/persistence/GruposFacade"
 *  jndi-name="es.caib.sistra.persistence.GruposFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 *  @ejb.env-entry name="role.operador" type="java.lang.String" value="${role.operador}"
 *
 * @ejb.transaction type="Required"
 */
public abstract class GruposFacadeEJB extends HibernateEJB {

	private String roleOperador;
	
    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void ejbCreate() throws CreateException {
        super.ejbCreate();
        
        try
		{
			javax.naming.InitialContext initialContext = new javax.naming.InitialContext();
			roleOperador = ( String ) initialContext.lookup( "java:comp/env/role.operador" );			
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
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
     * lista de grupos asociados a un tramite.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarGruposByTramite(Long id){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select grp from RolGrupoTramite rol, Grupos grp " +
        										"where rol.id.codiGrup = grp.codigo and rol.id.codiTra = ? order by grp.nombre");
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
     * lista de usuarios asociados a un tramite.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarUsuariosByTramite(Long id){
    	Session session = getSession();
        try {
        	
        	Query query = session.createQuery("from RolUsuarioTramite rol where rol.id.codiTra=? order by rol.id.codiUsuario");
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
     * lista de grupos no asociados a un tramite.
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public List listarGruposByNotTramite(Long id){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("from Grupos g where g.codigo not in (select grp.codigo from RolGrupoTramite rol," +
        										" Grupos grp where rol.id.codiGrup = grp.codigo and rol.id.codiTra = ?) order by g.nombre");
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
     * Asociar un grupo a un tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void asociarGrupo(String grupoAsociar, String tramite){
    	Session session = getSession();
        try {
        	RolGrupoTramiteId id = new RolGrupoTramiteId();
        	id.setCodiGrup(grupoAsociar);
        	id.setCodiTra(new Long(tramite));
        	session.save(new RolGrupoTramite(id));
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    	
    }
    /**
     * Desasociar un grupo a un tramite 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void desAsociarGrupo(String grupoAsociar, String tramite){
    	Session session = getSession();
        try {
        	RolGrupoTramiteId id = new RolGrupoTramiteId();
        	id.setCodiGrup(grupoAsociar);
        	id.setCodiTra(new Long(tramite));
        	session.delete(new RolGrupoTramite(id));
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
     * Asociar usuario a un tramite
     * Se da permisos al operador para que en un nuevo tramite este de alta como usuario del tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.operador}"
     */
    public void asociarUsuarioTramite(RolUsuarioTramite userTra){
    	
    	// Si es operador, comprobamos que el tramite sea nuevo (no tenga asociado ningun permiso)
    	if (this.ctx.isCallerInRole(roleOperador)){
    		if( this.listarUsuariosByTramite(userTra.getId().getCodiTra()).size() > 0 ||
    				this.listarGruposByTramite(userTra.getId().getCodiTra()).size() > 0
    		   ){
    				throw new EJBException("Usuario no puede darse permisos de acceso sobre tramite");
    			}
    	}
    	
    	// Asociamos usuario a tramite
    	Session session = getSession();
        try {
        	session.save(userTra);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * obtener usuario tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public RolUsuarioTramite obtenerUsuarioTramite(RolUsuarioTramiteId id){
    	Session session = getSession();
        try {
        	return (RolUsuarioTramite) session.get(RolUsuarioTramite.class,id);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * eliminar usuario tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void eliminarUsuarioTramite(RolUsuarioTramite userTra){
    	Session session = getSession();
        try {
        	session.delete(userTra);
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
     * Existen usuarios relacionados con el tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean existeUsuarioByTramite(String usuario, Long tramite){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select count(g.id.codiUsuario) from RolUsuarioTramite g where g.id.codiUsuario=? and g.id.codiTra =?");
            query.setString(0,usuario);
            query.setLong(1,tramite);
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
     * Existe usuario en el grupo relacionado con el tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public boolean existeUsuarioByGruposTramite(String usuario, Long tramite){
    	Session session = getSession();
        try {
        	Query query = session.createQuery("select count(gu.id.usuario) from RolGrupoTramite gt, GrupoUsuario gu where gu.id.usuario=? " +
        			"and gt.id.codiTra = ? and gt.id.codiGrup = gu.id.codiGrup" );
            query.setString(0,usuario);
            query.setLong(1,tramite);
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
     * Borra todas las referencias de los grupos con este tramite
     * Se da permiso al operador, porque si borra el tramite se deben borrar las referencias
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarTramiteGrupos(Long tramite){
    	
    	// Si es operador, comprobamos que tiene permisos de acceso al tramite
    	if (this.ctx.isCallerInRole(roleOperador)){
    		if(!this.existeUsuarioByGruposTramite(this.ctx.getCallerPrincipal().getName(),tramite) &&
    		   !this.existeUsuarioByTramite(this.ctx.getCallerPrincipal().getName(),tramite)
    		   ){
    				throw new EJBException("Usuario no tiene permisos de acceso al tramite");
    			}
    	}
    	
    	Session session = getSession();
        try {
        	List grupos = listarGruposByTramite(tramite);
        	for(int i=0;i<grupos.size();i++){
        		RolGrupoTramiteId id = new RolGrupoTramiteId();
        		id.setCodiGrup(((Grupos)grupos.get(i)).getCodigo());
        		id.setCodiTra(new Long(tramite));
        		session.delete(new RolGrupoTramite(id));
        	}        	
	    } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }
	
    }
    
    
    /**
     * Borra todas las referencias de los usuaris con este tramite
     * Se da permiso al operador, porque si borra el tramite se deben borrar las referencias
     * @ejb.interface-method
     * @ejb.permission role-name="${role.operador}"
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarTramiteUsuarios(Long tramite){
    	
    	// Si es operador, comprobamos que tiene permisos de acceso al tramite
    	if (this.ctx.isCallerInRole(roleOperador)){
    		if(!this.existeUsuarioByGruposTramite(this.ctx.getCallerPrincipal().getName(),tramite) &&
    		   !this.existeUsuarioByTramite(this.ctx.getCallerPrincipal().getName(),tramite)
    		   ){
    				throw new EJBException("Usuario no tiene permisos de acceso al tramite");
    			}
    	}
    	
    	// Borramos referencias tramite
    	Session session = getSession();
        try {
        	List usuarios = listarUsuariosByTramite(tramite);
        	for(int i=0;i<usuarios.size();i++){
        		session.delete((RolUsuarioTramite)usuarios.get(i));
        	}
        } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }
	    	
    }
}
