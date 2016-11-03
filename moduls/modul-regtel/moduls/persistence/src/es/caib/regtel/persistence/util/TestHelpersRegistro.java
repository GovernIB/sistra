package es.caib.regtel.persistence.util;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.regtel.model.ReferenciaRDSAsientoRegistral;
import es.caib.util.ConfigurationLogin;
import es.caib.util.UsernamePasswordCallbackHandler;

public class TestHelpersRegistro {

	public final static String URL_SISTRA = "jnp://rsanz:1099";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		LoginContext lc = null;
		
		try{
			
			String user = "gestor";
			String pass = "gestor";
			
			Configuration c = new ConfigurationLogin("client-login {org.jboss.security.ClientLoginModule required;};");
			CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
			lc = new LoginContext("client-login", null,handler,c);
			lc.login();
			
		
			//testRegistroEntrada();
			testRegistroSalida();
		
		}finally{
			if (lc!=null) try {lc.logout();}catch(Exception ex){}
		}
		

	}
	
	public static void testRegistroEntrada() throws Exception{
		RegistroEntradaHelper reh = new RegistroEntradaHelper();
		reh.setOficinaRegistro("1100","13");
		reh.setDatosInteresado("33456299Q","Sanz Villanueva Rafael","rsanz","es","España","46","Valencia","130","Massamagrell");
		reh.setDatosAsunto("1","es","OT","Prueba registro entrada");
		reh.addDocumento(ConstantesRDS.MODELO_ANEXO_GENERICO,1,"Doc de prueba".getBytes("UTF-8"),"Doc de prueba","txt",null,null);
		reh.addDocumento(ConstantesRDS.MODELO_ANEXO_GENERICO,1,"Doc de prueba 2".getBytes("UTF-8"),"Doc de prueba 2","txt",null,null);
		
		System.out.println("Generando asiento registral ...");
		ReferenciaRDSAsientoRegistral ar = reh.generarAsientoRegistral(URL_SISTRA);
		System.out.println("Efectuando registro ...");
		reh.registrar(URL_SISTRA,ar);
		System.out.println("Registro realizado");
		
	}
	
	public static void testRegistroSalida() throws Exception{
		RegistroSalidaHelper rsh = new RegistroSalidaHelper();
		rsh.setExpediente(1,"EXP/TEST/2010/1","xxx");
		rsh.setOficinaRegistro("1100","13");
		rsh.setDatosInteresado("33456299Q","Sanz Villanueva Rafael","rsanz","es","España","46","Valencia","130","Massamagrell");
		rsh.setDatosNotificacion("es","OT","Titulo aviso","Texto aviso","Texto SMS Aviso","Titulo oficio","Texto oficio",true);
		rsh.addDocumento(ConstantesRDS.MODELO_ANEXO_GENERICO,1,"Doc de prueba".getBytes("UTF-8"),"Doc de prueba","txt",null,null);
		rsh.addDocumento(ConstantesRDS.MODELO_ANEXO_GENERICO,1,"Doc de prueba 2".getBytes("UTF-8"),"Doc de prueba 2","txt",null,null);
		
		System.out.println("Generando asiento registral ...");
		ReferenciaRDSAsientoRegistral ar = rsh.generarAsientoRegistral(URL_SISTRA);
		System.out.println("Efectuando registro ...");
		rsh.registrar(URL_SISTRA,ar);
		System.out.println("Registro realizado");		
	}

}
