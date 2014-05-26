package es.caib.bantel.persistence.plugins;


import java.util.List;
import java.util.Properties;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.intf.BteConectorFacade;
import es.caib.bantel.persistence.intf.BteConectorFacadeHome;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.AutenticacionExplicitaInfo;
import es.caib.sistra.plugins.login.ConstantesLogin;
import es.caib.util.CifradoUtil;

/**
 * Plugin que permite el acceso al BackOffice
 * para realizar avisos
 */
public class PluginBackOffice {
	
	private static Log log = LogFactory.getLog(PluginBackOffice.class);
	/**
	 * Configuración del trámite con información del acceso al BackOffice
	 */
	private Procedimiento procedimiento;
	private String identificadorTramite;
	private String url;
	
	/**
	 * Crea plugin a partir configuración trámite
	 * @param procedimiento
	 */
	public PluginBackOffice(Procedimiento procedimiento, String idTramite) throws Exception{
		this.procedimiento = procedimiento;
		identificadorTramite = idTramite;
		try{
			url = procedimiento.getUrl();
    		if(url != null && !"".equals(url)){
    			url = StringUtils.replace(url,"@backoffice.url@",DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("backoffice.url"));
    		}
		}catch(Exception e){
			throw new Exception("No existe la propiedad backoffice.url en el global.properties.");
		}
	}
		
	/**
	 * Realiza el aviso de las nuevas entradas al BackOffice
	 * @param entradas
	 * @throws Exception
	 */
	public void avisarEntradas(List entradas, String usuAuto,String passAuto) throws Exception{				
		// Accedemos a BackOffice según metodo de acceso
		log.debug("[" + procedimiento.getIdentificador() + " - " + identificadorTramite + " ] - Aviso entradas a BackOffice para procedimiento " + procedimiento.getIdentificador());
		switch (procedimiento.getTipoAcceso())
		{
			case Procedimiento.ACCESO_EJB:
				avisarEntradasEJB(entradas);	
				break;
			case Procedimiento.ACCESO_WEBSERVICE:
				avisarEntradasWS(entradas,usuAuto,passAuto);	
				break;
			default:
				throw new Exception("Tipo de acceso a BackOffice no soportado: " + procedimiento.getTipoAcceso());			
		}				
	}
	
	/**
	 * Realiza el aviso de las nuevas entradas al BackOffice mediante EJB
	 * @param entradas
	 * @throws Exception
	 */
	private void avisarEntradasEJB(List entradas) throws Exception
	{
		LoginContext lc = null;		
		try
		{						 			
			// Realizamos autenticacion explicita si procede
			CallbackHandler handler = null; 
			switch (procedimiento.getAutenticacionEJB()){
				// No requiere autenticacion explicita (utilizara usuario/pass auto)
				case Procedimiento.AUTENTICACION_SIN:
					break;
				// Requiere autenticacion explicita basada en usr/pass
				case Procedimiento.AUTENTICACION_ESTANDAR:
					log.debug("Autenticacion explicita con usuario/password");
					String claveCifrada = (String)DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
					String user = CifradoUtil.descifrar(claveCifrada,procedimiento.getUsr());
					String pass = CifradoUtil.descifrar(claveCifrada,procedimiento.getPwd());
					handler = new UsernamePasswordCallbackHandler( user, pass ); 
					break;
                // Requiere autenticacion explicita basada en plugin autenticacion explicita organismo
				case Procedimiento.AUTENTICACION_ORGANISMO:
					log.debug("Autenticacion explicita con plugin organismo");
					AutenticacionExplicitaInfo authInfo = null;
					try{
						authInfo = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo(ConstantesLogin.TIPO_PROCEDIMIENTO, procedimiento.getIdentificador());
						log.debug("Usuario plugin autenticacion organismo: " + authInfo.getUser());
					}catch (Exception ex){
						throw new Exception("Excepcion obteniendo informacion autenticacion explicita a traves de plugin organismo",ex);
					}	
					handler = new UsernamePasswordCallbackHandler( authInfo.getUser(), authInfo.getPassword() );
					break;				
			}
			
			if ( handler != null){
				lc = new LoginContext("client-login", handler);
			    lc.login();
			}
			
			
			// Realiza el aviso			
			log.debug("[" + procedimiento.getIdentificador() + " - " + identificadorTramite  + "] -Aviso a backoffice a través de EJB - Version original");
			
			// Pasamos a lista de string
			String ents[] = new String[entradas.size()];
			for (int i=0;i<entradas.size();i++){
				ReferenciaTramiteBandeja ref = (ReferenciaTramiteBandeja) entradas.get(i);
				ents[i] = new String(ref.getNumeroEntrada());
			}			
			
			// Procesamiento original: realizamos avisos de n numeros de entradas
			BteConectorFacadeHome homeCF = (BteConectorFacadeHome) EjbBackOfficeFactory.getInstance().getHome(procedimiento.getJndiEJB(),(procedimiento.getLocalizacionEJB() == Procedimiento.EJB_LOCAL?"LOCAL":url));
			BteConectorFacade ejbCF= homeCF.create();
			ejbCF.avisoEntradas(ents);					
			
			log.debug("[" + procedimiento.getIdentificador() + " - " + identificadorTramite + "] - Aviso entradas completado"); 
		}
		catch( Exception exc )
		{
			log.debug("[" + procedimiento.getIdentificador() + " - " + identificadorTramite  + "] - " + exc );
			throw exc;
		}
		finally
		{
			if ( lc != null )
			{
				lc.logout();
			}
		}
	}
	
	/**
	 * Realiza el aviso de las nuevas entradas al BackOffice mediante Webservice
	 * @param entradas
	 * @throws Exception
	 */
	private void avisarEntradasWS(List entradas,String usuAuto,String passAuto)  throws Exception {
		log.debug("[" + procedimiento.getIdentificador() + " - " + identificadorTramite + "] -Aviso a backoffice a través de WS");
				
		Properties config = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
		
		//	 Comprobamos si requiere autenticacion explicita
		String user = null,pass=null; 
		switch (procedimiento.getAutenticacionEJB()){
			// No requiere autenticacion explicita 
			case Procedimiento.AUTENTICACION_SIN:
				log.debug("Autenticacion sin autenticacion");
				//user = usuAuto;
				//pass = passAuto; 
				break;
			// Requiere autenticacion explicita basada en usr/pass
			case Procedimiento.AUTENTICACION_ESTANDAR:
				log.debug("Autenticacion explicita con usuario/password");
				String claveCifrada = (String)config.get("clave.cifrado");
				user = CifradoUtil.descifrar(claveCifrada,procedimiento.getUsr());
				pass = CifradoUtil.descifrar(claveCifrada,procedimiento.getPwd());
				break;
            // Requiere autenticacion explicita basada en plugin autenticacion explicita organismo
			case Procedimiento.AUTENTICACION_ORGANISMO:
				log.debug("Autenticacion explicita con plugin organismo");
				AutenticacionExplicitaInfo authInfo = null;
				try{
					authInfo = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo(ConstantesLogin.TIPO_PROCEDIMIENTO, procedimiento.getIdentificador());
					log.debug("Usuario plugin autenticacion organismo: " + authInfo.getUser());
				}catch (Exception ex){
					throw new Exception("Excepcion obteniendo informacion autenticacion explicita a traves de plugin organismo",ex);
				}
				user = authInfo.getUser();
				pass = authInfo.getPassword(); 
				break;				
		}

		// Comprobamos si debemos hacer la llamada de forma asincrona: por defecto true
		String prop =config.getProperty("webService.cliente.asincrono");
		if (prop == null) prop = "true";
		
		if(procedimiento.getVersionWS() != null && "v1".equals(procedimiento.getVersionWS())){
			es.caib.bantel.wsClient.v1.client.ClienteWS.avisarEntradasWS(entradas,url, procedimiento.getSoapActionWS(), user,pass,Boolean.valueOf(prop).booleanValue());
		}else if(procedimiento.getVersionWS() != null && "v2".equals(procedimiento.getVersionWS())){
			es.caib.bantel.wsClient.v2.client.ClienteWS.avisarEntradasWS(entradas,url, procedimiento.getSoapActionWS(),user,pass,Boolean.valueOf(prop).booleanValue());
		}else{
			throw new Exception("Excepcion obteniendo la versión "+procedimiento.getVersionWS()+" del WS de aviso de entradas. ");
		}
			
	}
					
	

}


