package es.caib.sistra.casClient.loginModule;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;


/**
 * 
 *  Login Module que recibe el principal validado por CAS en la capa web y lo autentica.
 *  No es funcion de este module validar los usuarios por CAS sino que se fía de la autenticación web
 *  realizada por CAS.
 *  
 *  Si no es un usuario validado por CAS (p.e. usuario auto o validado por webservices) lo autentica contra 
 *  el origen de datos de usuarios (esta parte es parametrizable por organismo a través de la interfaz  de 
 *  la que hay que proporcionar una implementación, que se especificará en el properties: sistra/plugins/plugin-login.properties)  
 *  
 * 
 */
public class CasInternalLoginModule extends UsernamePasswordLoginModule {

	private static Log log = LogFactory.getLog(CasInternalLoginModule.class);
	private String nameUnauthenticatedIdentity;	
	private String autenticadorClassName;
	private Properties propsPlugin; 
	private CasPrincipal cimPrincipal;
	private AutenticadorInt autenticador;
	
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		
		// Obtenemos unauthenticatedIdentity
		nameUnauthenticatedIdentity = (String)options.get("unauthenticatedIdentity");
		
		// Obtenemos propiedades plugin
		propsPlugin = readPropertiesPluginLogin();
		
		// Leeemos propiedades
       	autenticadorClassName = propsPlugin.getProperty("cas.loginModule.autenticador");
       	
       	// Obtenemos clase autenticadora
       	try{
       		autenticador = (AutenticadorInt) Class.forName(propsPlugin.getProperty("cas.loginModule.autenticador")).newInstance();       	
       	}catch(Exception ex){
       		log.error("No se puede crear clase autenticadora '" + autenticadorClassName + "' para CasInternalLoginModule",ex);
       		throw new RuntimeException("No se puede crear clase autenticadora '" + autenticadorClassName + "' para CasInternalLoginModule");
       	}
       	
       	// Inicializacion
		super.initialize(subject, callbackHandler, sharedState, options);	   		
	}
	
	@Override
	protected String getUsersPassword() throws LoginException {		
		// NO ACCEDEMOS DIRECTAMENTE AL PASSWORD REESCRIBIMOS VALIDATEPASSWORD
		return null;
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		try{
			
			// Obtenemos principal
	        Principal principal = getIdentity();        
	        
	        // Si no es SeyconPrincipal será el unathenticated por lo que devolvemos lista de roles vacíos
	        if(!(principal instanceof CasPrincipal))
	        {
	            if(log.isTraceEnabled()) log.trace("Principal " + principal + " not a CimPrincipal");
	            SimpleGroup rolesGroup = new SimpleGroup("Roles");
	            ArrayList groups = new ArrayList();
	            groups.add(rolesGroup);
	            Group roleSets[] = new Group[groups.size()];
	            groups.toArray(roleSets);
	            return roleSets;
	        }
	        
			// Recorremos lista roles para montar respuesta
	        HashMap setsMap = new HashMap();
	        Group rolesGroup = new SimpleGroup("Roles");  
	        if (cimPrincipal.getRoles() != null){
		        for (Iterator it=cimPrincipal.getRoles().iterator();it.hasNext();){
		        	String roleName = (String) it.next();	        		       
		        	rolesGroup.addMember(new SimplePrincipal(roleName));
		        }
	        }
	        setsMap.put("Roles", rolesGroup);            

			// Montamos grupo "CallerPrincipal"
			Group principalGroup = new SimpleGroup("CallerPrincipal");
			principalGroup.addMember(principal);
			setsMap.put("CallerPrincipal", principalGroup);
	        
	        // Devolvemos respuesta
            Group roleSets[] = new Group[setsMap.size()];
            setsMap.values().toArray(roleSets);
            return roleSets;            
        
		}catch (Exception e){
			log.error("Error al obtener roles",e);
			throw new LoginException("Error al obtener roles");
		}        
	}

	
	protected boolean validatePassword(String inputPassword, String expectedPassword){	
		// No validamos el passwd, se valida en createIdentity
		return true;		
	}
	
	protected Principal createIdentity(String username)
    throws Exception
	{	   	
		CasPrincipal cp = null;
		String[] up = this.getUsernameAndPassword();
		
		// Comprobamos si es:
		//	- un usuario serializado de CAS: creamos principal a partir de la info de cas sin validar el password
		//  - el usuario unauthenticated: creamos usuario unauthenticated sin validar el password
		//  - usuario / password
		if (up[1]!= null && up[1].startsWith(CasPrincipal.PREFIX_SERIALIZED)){
			// Creamos principal a partir del passwd q contiene el principal de CAS
			log.debug("Creamos identity a partir usuario cas serializado");
			cp=CasPrincipal.deserializar(up[1]);			
		}else if (username.equals(nameUnauthenticatedIdentity)){
			log.debug("Creamos identity para " + nameUnauthenticatedIdentity );
			List<String> roles = this.autenticador.obtenerRoles(this.propsPlugin,nameUnauthenticatedIdentity);
			cp = new CasPrincipal(nameUnauthenticatedIdentity,nameUnauthenticatedIdentity,null,'A',roles);			
		}else{
			log.debug("Creamos identity a partir usuario a partir usuario/password");
			UserInfo ui = this.autenticador.autenticar(this.propsPlugin,username,up[1]);
			if (ui == null){
				throw new LoginException("Error autenticando usuario: " + username);
			}
			cp = new CasPrincipal(username,ui.getNombreApellidos(),ui.getNif(),'U',ui.getRoles());					
		}
		
		this.cimPrincipal=cp;
		return this.cimPrincipal;
	}
	
	private Properties readPropertiesPluginLogin(){
		// Obtenemos propiedades login
		InputStream fisPlugin=null; 
		InputStream fisGlobal=null;
		try {
			Properties propiedades = new Properties();
			String pathConf = System.getProperty("ad.path.properties");
			// Propiedades globales
	       	fisGlobal = new FileInputStream(pathConf + "sistra/global.properties");
	       	propiedades.load(fisGlobal);
	       	// Propiedades plugin
	       	fisPlugin = new FileInputStream(pathConf + "sistra/plugins/plugin-login.properties");
	       	propiedades.load(fisPlugin);
	    
	       	return propiedades;
	       	
        } catch (Exception e) {
       	 	throw new RuntimeException("Excepcion accediendo a las propiedadades del plugin de login", e);
        } finally {
        	try{if (fisGlobal != null){fisGlobal.close();}}catch(Exception ex){}
            try{if (fisPlugin != null){fisPlugin.close();}}catch(Exception ex){}            
        }		
	}
}
