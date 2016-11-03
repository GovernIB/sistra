package es.caib.sistra.casClient.filterWeb;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.caib.sistra.casClient.loginModule.AutenticadorInt;
import es.caib.sistra.casClient.loginModule.CasPrincipal;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.validation.Assertion;

/**
 * Filtro que:
 * 	- monta un wrapper sobre la request para implementar el getPrincipal,
 *  - hace un login JAAS interno para autenticarse en la capa de EJB
 *  
 *  Parametros filtro:
 *  	- rolesAllowed: roles permitidos (separados por ":" )
 *  	- nifAttribute: nombre propiedad en la que se devolverá el nif del usuario autenticado
 *  	- nombreAttribute: nombre propiedad en la que se devolverá el nombre y apellidos del usuario autenticado
 *  	- metodoAutenticacionAttribute: nombre propiedad en la que se devolverá el método de autenticación del usuario autenticado 
 *  	- rolesAttribute: nombre propiedad en la que se devolverá la lista de roles del usuario autenticado. Si tiene el
 *  					valor INTERNAL indicara que los roles no estan centralizados en CAS y se resolveran los roles mediante el 
 *  					autenticador del login module.
 * 	
 */
public final class CasAuthenticationFilter extends AbstractConfigurationFilter {

	// Propiedades plugin de login
	Properties props = null;
	
	// Atributos usuario autenticado
	private String nifAttribute;
	private String nombreAttribute;
	private String metodoAutenticacionAttribute;
	private String rolesAttribute;
	// Roles permitidos por el filtro
	private String rolesAllowed;
	// En caso de que los roles no esten centralizados en CAS se resolveran a traves del autenticador del login module
	private AutenticadorInt autenticador;
	
	
	public void init(final FilterConfig filterConfig) throws ServletException {
		 // Roles permitidos
		 this.rolesAllowed = getPropertyFromInitParams(filterConfig, "rolesAllowed", null);
		
		 // Info persona proveniente de CAS (del fichero de plugin.properties)
		 props = readPropertiesPluginLogin();
		 this.nifAttribute = props.getProperty("cas.nifAttribute",null);
		 this.nombreAttribute = props.getProperty("cas.nombreAttribute",null);
		 this.metodoAutenticacionAttribute = props.getProperty("cas.metodoAutenticacionAttribute",null);
		 this.rolesAttribute = props.getProperty("cas.rolesAttribute", null);
		 
		 
		 // Los valores son obligatorios
		 if (this.rolesAllowed == null || this.rolesAllowed.trim().length() <= 0){
			 throw new ServletException("No se ha establecido propiedad 'rolesAllowedAtribute' del filtro de autenticación CAS");
		 }
		 if (this.nifAttribute == null || this.nifAttribute.trim().length() <= 0){
			 throw new ServletException("No se ha establecido propiedad 'nifAttribute' del filtro de autenticación CAS");
		 }
		 if (this.nombreAttribute == null || this.nombreAttribute.trim().length() <= 0){
			 throw new ServletException("No se ha establecido propiedad 'nombreAttribute' del filtro de autenticación CAS");
		 }
		 if (this.metodoAutenticacionAttribute == null || this.metodoAutenticacionAttribute.trim().length() <= 0){
			 throw new ServletException("No se ha establecido propiedad 'metodoAutenticacionAttribute' del filtro de autenticación CAS");
		 }
		 if (this.rolesAttribute == null || this.rolesAttribute.trim().length() <= 0){
			 throw new ServletException("No se ha establecido propiedad 'rolesAttribute' del filtro de autenticación CAS");
		 }
		 
		 // En caso de que los roles no esten centralizados en CAS creamos instancia de autenticador
		 if (this.rolesAttribute.equals("INTERNAL")){
			// Obtenemos clase autenticadora
			String autenticadorClassName = props.getProperty("cas.loginModule.autenticador");
	       	try{	       	
	       		autenticador = (AutenticadorInt) Class.forName(autenticadorClassName).newInstance();       	
	       	}catch(Exception ex){
	       		log.error("No se puede crear clase autenticadora '" + autenticadorClassName + "' para CasInternalLoginModule",ex);
	       		throw new ServletException("No se puede crear clase autenticadora '" + autenticadorClassName + "' para CasInternalLoginModule");
	       	}
		 }
    }
	
    public void destroy() {     
    }

    
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        
    	// Obtenemos principal de la info de CAS
    	final CasPrincipal principal = retrievePrincipalFromSessionOrRequest(servletRequest);

    	// Comprobamos si tiene permiso de acceso
    	if (!checkAcceso(principal)){
    		((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
    		return;
    	}
    	
        // Serializamos el principal para pasarlo a la capa de EJB    	
    	String principalStr;
		try {
			principalStr = CasPrincipal.serializar(principal);
		} catch (Exception e1) {
			throw new ServletException("Error al serializar principal",e1);
		}
    	
    	    	
        LoginContext lc = null;
		try{
			// Realiza login jaas para la capa ejb
			CallbackHandler handler = new UsernamePasswordCallbackHandler(principal.getName(),principalStr);
			lc = new LoginContext("client-login", handler);
			lc.login();
			
			// Realiza filtro con el wrapper para la capa web
			filterChain.doFilter(new CasHttpServletRequestWrapper((HttpServletRequest) servletRequest, principal), servletResponse);
		}catch (Exception ex){
			throw new ServletException("Error en filtro de autenticacion",ex);
		}finally{
			if (lc!=null) {
				try {
					lc.logout();
				} catch (LoginException e) {
					e.printStackTrace();
				}			
			}			
		}
        
    }

    
  	// -- FUNCIONES UTILIDAD
    
    /**
     * Crea principal a traves de la request
     */
    protected CasPrincipal retrievePrincipalFromSessionOrRequest(final ServletRequest servletRequest) {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = (Assertion) (session == null ? request.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION));
        
        if (assertion == null){
        	return null;
        }else{
        	List<String> roles = new ArrayList<String>();
        	String apellidosNombre,nif,listaRoles;
        	char metodoAutenticacion;
        	
        	// Recuperamos info de CAS
        	AttributePrincipal ap = assertion.getPrincipal();
        	apellidosNombre = (String) ap.getAttributes().get(this.nombreAttribute);
        	nif = (String) ap.getAttributes().get(this.nifAttribute);
        	metodoAutenticacion = ((String) ap.getAttributes().get(this.metodoAutenticacionAttribute)).charAt(0);
        	
        	// Obtenemos lista de roles
        	if (this.rolesAttribute.equals("INTERNAL")){
        		// Roles no están centralizados en CAS. Los obtenemos del autenticador.
        		roles = autenticador.obtenerRoles(props,ap.getName());
        		
        	}else{
        		// Roles centralizados en CAS
        		listaRoles = (String) ap.getAttributes().get(this.rolesAttribute);
            	if (listaRoles != null && listaRoles.length() > 0){
    	        	String[] rs = listaRoles.split(":");        	
    	        	for (int i=0;i<rs.length;i++){
    	        		roles.add(rs[i]);
    	        	}
            	}
        	}
        	
        	return new CasPrincipal(ap.getName(),apellidosNombre,nif,metodoAutenticacion,roles);
        	
        }
        
    }
    
    /**
     * Chequea si el principal tiene permisos de acceso
     * @param principal
     * @return
     */
    private boolean checkAcceso(CasPrincipal principal) {
		if (this.rolesAllowed!=null && this.rolesAllowed.length() > 0){
    		if (principal != null && principal.getRoles() != null){    			
	    		String[] rs = this.rolesAllowed.split(":");
	    		for (int i=0;i<rs.length;i++){
	        		if (principal.getRoles().contains(rs[i])){
	        			return true;
	        		}	        		
	        	}
    		}
		}    	
		return false;
	}
    
    /**
     * Lee propiedades del fichero de propiedades del plugin de login
     * @return
     */
    private Properties readPropertiesPluginLogin(){    	
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
    
    // --- WRAPPER DE LA REQUEST
    final class CasHttpServletRequestWrapper extends HttpServletRequestWrapper {

        private final CasPrincipal principal;

        CasHttpServletRequestWrapper(final HttpServletRequest request, final CasPrincipal principal) {
            super(request);
            this.principal = principal;
        }

        public Principal getUserPrincipal() {
            return this.principal;
        }

        public String getRemoteUser() {
            return principal != null ? this.principal.getName() : null;
        }

        public boolean isUserInRole(final String role) {
            if (StringUtils.isBlank(role)) {
                log.debug("No valid role provided.  Returning false.");
                return false;
            }

            if (this.principal == null) {
                log.debug("No Principal in Request.  Returning false.");
                return false;
            }

            for (final Iterator iter = this.principal.getRoles().iterator(); iter.hasNext();) {
                if (rolesEqual(role, iter.next())) {
                    log.debug("User [" + getRemoteUser() + "] is in role [" + role + "]: " + true);
                    return true;
                }
            }
           
            return false;

        }
        
        /**
         * Determines whether the given role is equal to the candidate
         * role attribute taking into account case sensitivity.
         *
         * @param given  Role under consideration.
         * @param candidate Role that the current user possesses.
         *
         * @return True if roles are equal, false otherwise.
         */
        private boolean rolesEqual(final String given, final Object candidate) {
            return given.equals(candidate);
        }
    }
}
