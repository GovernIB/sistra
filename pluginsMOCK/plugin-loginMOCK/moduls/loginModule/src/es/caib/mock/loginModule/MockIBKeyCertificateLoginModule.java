package es.caib.mock.loginModule;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.Base64Utils;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;


import es.caib.signatura.api.ParsedCertificate;
import es.caib.signatura.api.Signature;
import es.caib.signatura.api.SignerFactory;
import es.caib.signatura.impl.CertificateProvider;



/**
 * Login module mock para acceso por certificado (accesos ciudadano)
 * Siempre se le asociara el role tothom
 * 
 * OJO no verifica la firma, es un modulo mock solo para pruebas
 * 
 * 
 * Username=NIF
 * Password={FIRMA:FIRMAB64}
 *
 */
public class MockIBKeyCertificateLoginModule extends UsernamePasswordLoginModule {

	/**
	 * Principal customizado
	 */
	private MockPrincipal caller;
	
	/**
	 * Role de acceso publico
	 */
	private String roleTothom;


	/**
	 * Inicializacion
	 */
	public void initialize(Subject subject, CallbackHandler callbackHandler,Map sharedState, Map options)
	{
		super.initialize(subject, callbackHandler, sharedState, options);
		roleTothom = (String) options.get("roleTothom");
	}
	
	 
	/**
	 * Login 
	 */
	 public boolean login() throws LoginException
	 {
		 // Comprobamos si esta habilitado UseFirstPass
		 if (getUseFirstPass() == true){
			 Object identity = sharedState.get("javax.security.auth.login.name");
	         Object credential = sharedState.get("javax.security.auth.login.password");
	         if( identity != null && credential != null ){
	        	 super.login();	
	         }
		 }
		 
		 return loginCertificate();
	 }
	 
	 
	 
	 public boolean loginCertificate() throws LoginException{
		 log.debug("enter: login()");
	     super.loginOk = false;
	     
	     // Obtenemos usuario y password
	     String[] userPass = this.getUsernameAndPassword();
	     
	     String username = userPass[0]; // Usuario: Document Original
	     String firma = userPass[1]; // Password: firma
	     //if (username == null || password == null) return false;
	     //if (!firma.startsWith("{FIRMA:")) return false;
	     log.debug("enter: login() USER = " + username);
	     log.debug("enter: login() FIRMA = " + firma);
	     
	     // Creamos principal y Verificamos firma
	     try {
			caller = crearMockPrincipal(username, firma);
		} catch (Exception e) {
			log.error("Error creando MockPrincipal a partir de la firma",e);
			throw new LoginException("Error creando MockPrincipal a partir de la firma");			
		}
	     
	     // Establecemos shared state 
	     if (getUseFirstPass() == true)
	      {
	         // Add authentication info to shared state map
	         sharedState.put("javax.security.auth.login.name", caller.getName());
	         sharedState.put("javax.security.auth.login.password", firma);
	      }
	     
	     
	     // Damos login por realizado
	      super.loginOk = true;
	      log.debug("Login OK para " + caller.getName());
	      return true;
	      
	 }

	private MockPrincipal crearMockPrincipal(String dadesOriginals,  String firmaStr) throws Exception {
		// Calcular username, nombre y nif a partir de firma
	  /*
		String firmaB64 = firma.substring("{FIRMA:".length(),firma.length() - 1);
		DatosFirma df = new DatosFirma(firmaB64);
		X509Certificate certificate = df.getCertificado();
		DatosCertificado dc = new DatosCertificado(certificate);
		
		// Tomamos como username el nif
		String username,nombre,nif;
		nif = dc.getNif();
		username = nif;
		nombre = dc.getFullName();
	  */
	  

	      
	        // Llegir certificat com un Stream
	        byte[] firma = Base64Utils.fromb64(firmaStr);
	        
	        
	        ByteArrayInputStream bais = new ByteArrayInputStream(firma);
	        
	        
	        ObjectInputStream signatureStream = new ObjectInputStream(bais);
	        Signature signData = (Signature) signatureStream.readObject();
	        signatureStream.close();
	        
	        
	         /*
	        SignerFactory sf = new SignerFactory();
	        
	        
	        //es.caib.signatura.impl.CertificateProviderFactory.getCertificateProvider(arg0) CMSSignature cms = new          es.caib.signatura.impl.CMSSignature();
        
	        es.caib.signatura.api.Signer signer = sf.getSigner();
	        
	        signer.
	        */
	        
	        
	        // Verificar els certificats i el seu estat de revocació        
	        signData.verify();
	        
  	      // Verificam la firma
	        signData.verify(new ByteArrayInputStream(dadesOriginals.getBytes()));
	        
	        
	        byte[] pkcs7 = signData.getCert().getEncoded();
	        
	        
	        
	      // Missatge de Informació
	        ParsedCertificate pc = signData.getParsedCertificate(); 
	        
	        /*
	        msg.append("  + CommonName  = " + pc.getCommonName() + "\n");
	        msg.append("  + Name  = " + pc.getName() + "\n");
	        msg.append("  + Nif  = " + pc.getNif() + "\n");

	        msg.append("  + ValidSince  = " + sdf.format(pc.getValidSince()) + "\n");
	        msg.append("  + ValidUntil  = " + sdf.format(pc.getValidUntil()) + "\n");

	      */
	  
	        
	        String username,nombre,nif;
	        nif = pc.getNif();
	        username = nif;
	        nombre = pc.getName();
	  
	  
		return new MockPrincipal(username,nombre,nif,'C');		
	}
	
	
	
	/**
   * Deserializa firma. 
   * @param serializada
   * @return
   */
  public static Signature deserializaFirmaFromString(String serializada) throws Exception
  { 
    byte b[] = new byte[serializada.length() / 2];
        int i = 0;
        int j = 0;
        while(i < serializada.length()) 
        {
            int c = getHexValue(serializada.charAt(i++)) * 16;
            c += getHexValue(serializada.charAt(i++));
            b[j++] = (byte)c;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        ObjectInputStream objIn = new ObjectInputStream(in);
        Signature signature;
        try
        {
            signature = (Signature)objIn.readObject();
        }
        catch(ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        objIn.close();
        in.close();
        return signature;
  }
  
  private static int getHexValue(char c)
  {
      if(c >= '0' && c <= '9')
          return c - 48;
      if(c >= 'A' && c <= 'F')
          return (c - 65) + 10;
      if(c >= 'a' && c <= 'f')
          return (c - 97) + 10;
      else
          throw new RuntimeException("Invalid hex character " + c);
  }
	
	
	
	
	
	
	
	

	protected Principal getIdentity()
	   {
	      Principal identity = caller;
	      if( identity == null )
	         identity = super.getIdentity();
	      return identity;
	   }


	/**
	 * No utilizada se sobreescribe login
	 */
	protected String getUsersPassword() throws LoginException {
		return null;
	}

	
	/**
	 * Obtiene roles usuario (modificado para que no llame a createIdentity al crear cada role)
	 */
	 protected Group[] getRoleSets() throws LoginException
	   {
	 	  Principal principal = getIdentity ();
	      
	      if ( ! (principal instanceof MockPrincipal) )
	      {
	      	if (log.isTraceEnabled()) log.trace("Principal "+principal+" not a MockPrincipal");
	      	return new Group[0];
	      }
	      
	      String username = getUsername();
	      
	      List roles = null;
	      try {
				roles = getUserRoles(username);	    	  	
		  } catch (Exception e) {			
			log.error("Excepcion obteniendo roles",e);  
			throw new LoginException("Excepcion obteniendo roles");
		  }
	      
		  Group rolesGroup = new SimpleGroup("Roles");            
	      for (Iterator iterator = roles.iterator();iterator.hasNext();){
	        	String roleName = (String) iterator.next();	        		       
	        	rolesGroup.addMember(new SimplePrincipal(roleName));
	      }
	      HashMap setsMap = new HashMap();
	      setsMap.put("Roles", rolesGroup);            

		  // Montamos grupo "CallerPrincipal"
			Group principalGroup = new SimpleGroup("CallerPrincipal");
			principalGroup.addMember(principal);
			setsMap.put("CallerPrincipal", principalGroup);
	        
	        // Devolvemos respuesta
          Group roleSets[] = new Group[setsMap.size()];
          setsMap.values().toArray(roleSets);
          return roleSets;
	   }


	/**
	 * Obtiene roles asociados al usuario. En este caso serán accesos por ciudadanos que tendrán el role tothom
	 * @param username
	 * @return
	 */ 
	private List getUserRoles(String username) {
		List userRoles = new ArrayList();		
		userRoles.add(roleTothom);
		return userRoles;
	}


	
}
