package es.caib.mock.loginModule;

import java.security.Principal;
import java.security.acl.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;
import javax.transaction.Transaction;

import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import org.jboss.tm.TransactionDemarcationSupport;

public class MockDatabaseLoginModule extends DatabaseServerLoginModule {

	/**
	 * Modificado para obtener principal customizado
	 */
	protected Principal createIdentity(String username) throws Exception {
		
		// Usuario unautenticado
		if (username != null && username.equals( (String) options.get("unauthenticatedIdentity"))){
			log.debug ("Creating MockPrincipal para "+username);
			return new MockPrincipal(username,"","",'A');
		}
		
		// Usuario autenticado
		UserInfo ui = getUserInfo(username);
		if ( ui == null)
		{			
			// Se llama para crear principals asociados a los roles
			return new SimplePrincipal (username);
		}
		else
		{			
			log.debug ("Creating MockPrincipal para "+username);
			return new MockPrincipal(username,ui.fullName,ui.nif,'U');
		}
	
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
	 * Obtiene info customizada del usuario
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	protected UserInfo getUserInfo (String username) throws Exception {
	      
		if( username == null ) return null;  
		
		  Connection conn = null;
	      PreparedStatement ps = null;
	      ResultSet rs = null;
	      
	      Transaction tx = null;
	      if ( suspendResume)
	         tx = TransactionDemarcationSupport.suspendAnyTransaction();       
	      
	      try {
	          InitialContext ctx = new InitialContext();
	          DataSource ds = (DataSource) ctx.lookup(dsJndiName);
	          conn = ds.getConnection();
	          ps = conn.prepareStatement(principalsQuery);
	          ps.setString(1, username);
	          rs = ps.executeQuery();
	          if( !rs.next() ) return null; // no encontrado 	          
	          UserInfo ui = new UserInfo ();
  	          ui.user = username;
  	          ui.fullName = rs.getString(2);
  	          ui.nif = rs.getString(3);
  	          return ui;	          
	       }finally
	       {
	    	  if( rs != null )  try{rs.close();}catch(SQLException e){}
	    	  if( ps != null )  try{ps.close();}catch(SQLException e){}
	          if( conn != null ) try { conn.close();} catch (SQLException ex){}
	          
	          if (suspendResume)
	              TransactionDemarcationSupport.resumeAnyTransaction(tx); 
	        }
	      
	   }		
	
	
	/**
	 * Obtiene roles asociados al usuario
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	*/
	protected List getUserRoles (String username) throws Exception {
	    if( username == null ) return null;  
		  Connection conn = null;
	      PreparedStatement ps = null;
	      ResultSet rs = null;
	      List roles = new ArrayList();
	      
	      Transaction tx = null;
	      if ( suspendResume)
	         tx = TransactionDemarcationSupport.suspendAnyTransaction();       

	      try {
	          InitialContext ctx = new InitialContext();
	          DataSource ds = (DataSource) ctx.lookup(dsJndiName);
	          conn = ds.getConnection();
	          ps = conn.prepareStatement(rolesQuery);
	          ps.setString(1, username);
	          rs = ps.executeQuery();	          
	          while (rs.next()){
	        	  roles.add(rs.getString(1));
	          }	          
	          return roles;	          
	       }finally
	       {
	    	  if( rs != null )  try{rs.close();}catch(SQLException e){}
	    	  if( ps != null )  try{ps.close();}catch(SQLException e){}
	          if( conn != null ) try { conn.close();} catch (SQLException ex){}
	          
	          if (suspendResume)
	              TransactionDemarcationSupport.resumeAnyTransaction(tx); 
	        }	     
	   }
	
	
	/**
	 * Clase privada para la obtención de los datos personalizados del usuario
	 */
	private class UserInfo {
	   	 String user;  	 
	   	 String nif;
	   	 String fullName;
	 }
}
