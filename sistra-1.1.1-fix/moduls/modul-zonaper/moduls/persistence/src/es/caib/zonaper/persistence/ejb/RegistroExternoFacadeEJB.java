package es.caib.zonaper.persistence.ejb;

import java.security.Principal;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;

import org.apache.commons.lang.StringUtils;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.RegistroExterno;

/**
 * SessionBean para mantener y consultar RegistroExterno
 * 
 *  @ejb.bean
 *  name="zonaper/persistence/RegistroExternoFacade"
 *  jndi-name="es.caib.zonaper.persistence.RegistroExternoFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleGestor" type="java.lang.String" value="${role.gestor}"
 * @ejb.env-entry name="roleAuto" type="java.lang.String" value="${role.auto}"
 */
public abstract class RegistroExternoFacadeEJB extends HibernateEJB {

	private String roleGestor;
	private String roleAuto;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
		try{
			InitialContext initialContext = new InitialContext();			 
			roleGestor = (( String ) initialContext.lookup( "java:comp/env/roleGestor" ));	
			roleAuto = (( String ) initialContext.lookup( "java:comp/env/roleAuto" ));	
		}catch(Exception ex){
			log.error(ex);
		}
	}
 
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public RegistroExterno obtenerRegistroExterno(Long id) {
        Session session = getSession();
        try {
        	// Cargamos RegistroExterno        	
        	RegistroExterno registro = (RegistroExterno) session.load(RegistroExterno.class, id);                       
        	// Cargamos documentos
        	Hibernate.initialize(registro.getDocumentos());        	
            return registro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public RegistroExterno obtenerRegistroExternoPorNumero(String numeroRegistro) {
        Session session = getSession();
        try {
        	// Cargamos RegistroExterno        	
        	Query query = session
            .createQuery("FROM RegistroExterno AS m WHERE m.numeroRegistro = :numeroRegistro")
            .setParameter("numeroRegistro",numeroRegistro);
            //query.setCacheable(true);
            if (query.list().isEmpty()){
            	return null;
            	//throw new HibernateException("No existe trámite con id " + id);
            }
            RegistroExterno registro = (RegistroExterno)  query.uniqueResult(); 
                        
        	// Cargamos documentos
        	Hibernate.initialize(registro.getDocumentos());        	
            return registro;
        } catch (HibernateException he) {
        	throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
 	/**
 	 * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public Long grabarRegistroExterno(RegistroExterno registro) {        
    	Session session = getSession();
        try {     
        	// Control acceso: 
        	//   - Si se accede por ws se filtra para que solo accesible por usuario auto y gestor
        	//   - Si se accede por ejb el usuario autenticado debe ser el que aparece en el registro
        	if (!ctx.isCallerInRole(roleGestor) && !ctx.isCallerInRole(roleAuto)){
        		// Si no es role gestor ni auto, el usuario autenticado debe ser el que registra
        		Principal sp = this.ctx.getCallerPrincipal();
            	PluginLoginIntf plgLogin = PluginFactory.getInstance().getPluginLogin();
            	// Acceso anonimo e intenta indica un usuario autenticado
            	if (plgLogin.getMetodoAutenticacion(sp) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO 
            			&& !StringUtils.isEmpty(registro.getUsuario()) ){
            		throw new HibernateException("Acceso anonimo intenta realizar registro autenticado");
            	}
            	// Acceso autenticado e intenta indicar un usuario distinto
            	if (plgLogin.getMetodoAutenticacion(sp) != CredentialUtil.NIVEL_AUTENTICACION_ANONIMO 
            			&&  !sp.getName().equals(registro.getUsuario())){
            		throw new HibernateException("Acceso autenticado intenta realizar registro para otro usuario: " + sp.getName());
            	}
        	}
        	        	
        	// Updateamos
        	if (registro.getCodigo() == null){
        		session.save(registro);
        	}else{        		
        		session.update(registro);
        	}
        	                    	
            return registro.getCodigo();
        } catch (Exception he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
}
