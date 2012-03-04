package es.caib.util.ws.server;

import java.io.IOException;
import java.security.Principal;

import javax.naming.InitialContext;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.jboss.security.NobodyPrincipal;
import org.jboss.security.SecurityAssociation;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.SubjectSecurityManager;

public class ServerPasswordCallback implements CallbackHandler {

    private String securityDomain;

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

        try{
        	validarUsuario(pc.getIdentifier(),pc.getPassword());
        }catch(Exception ex){
        	throw new IOException("No se ha podido autenticar a usuario " + pc.getIdentifier() + ": " +  ex.getMessage());
        }
        
    }
	
	protected Principal validarUsuario(String userName,String passwd) throws Exception {
		Principal p;
		if (userName == null) {
	         p = NobodyPrincipal.NOBODY_PRINCIPAL;
	    } else {
	          p = new SimplePrincipal(userName);
	    }
		
		char[] passChars = passwd != null ? passwd.toCharArray() : null;
        SubjectSecurityManager authMgr = (SubjectSecurityManager) new InitialContext().lookup(securityDomain );      
	    if(!authMgr.isValid(p, passChars)) {
	    	throw new Exception( "Autentificació de l'usuari incorrecta."); 
	    }
	    SecurityAssociation.setPrincipal(p);
	    SecurityAssociation.setCredential(passwd!=null ? passwd.toCharArray() : null);		
	    
	    return p;
	}

	public String getSecurityDomain() {
		return securityDomain;
	}

	public void setSecurityDomain(String securityDomain) {
		this.securityDomain = securityDomain;
	}
	
 
}


