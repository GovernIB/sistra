package es.caib.bantel.persistence.plugins;


import java.util.Iterator;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.model.Tramite;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.intf.BteConectorFacade;
import es.caib.bantel.persistence.intf.BteConectorFacadeHome;
import es.caib.bantel.wsClient.v1.client.ClienteWS;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.AutenticacionExplicitaInfo;
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
	private Tramite tramite;
	
	
	/**
	 * Crea plugin a partir configuración trámite
	 * @param tramite
	 */
	public PluginBackOffice(Tramite tramite){
		this.tramite = tramite;
	}
		
	/**
	 * Realiza el aviso de las nuevas entradas al BackOffice
	 * @param entradas
	 * @throws Exception
	 */
	public void avisarEntradas(List entradas, String usuAuto,String passAuto) throws Exception{				
		// Accedemos a BackOffice según metodo de acceso
		log.debug("[" + tramite.getIdentificador() + "] - Aviso entradas a BackOffice para tramite " + tramite.getIdentificador());
		switch (tramite.getTipoAcceso())
		{
			case Tramite.ACCESO_EJB:
				avisarEntradasEJB(entradas);	
				break;
			case Tramite.ACCESO_WEBSERVICE:
				avisarEntradasWS(entradas,usuAuto,passAuto);	
				break;
			default:
				throw new Exception("Tipo de acceso a BackOffice no soportado: " + tramite.getTipoAcceso());			
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
			switch (tramite.getAutenticacionEJB()){
				// No requiere autenticacion explicita (utilizara usuario/pass auto)
				case Tramite.AUTENTICACION_SIN:
					break;
				// Requiere autenticacion explicita basada en usr/pass
				case Tramite.AUTENTICACION_ESTANDAR:
					log.debug("Autenticacion explicita con usuario/password");
					String claveCifrada = (String)DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
					String user = CifradoUtil.descifrar(claveCifrada,tramite.getUsr());
					String pass = CifradoUtil.descifrar(claveCifrada,tramite.getPwd());
					handler = new UsernamePasswordCallbackHandler( user, pass ); 
					break;
                // Requiere autenticacion explicita basada en plugin autenticacion explicita organismo
				case Tramite.AUTENTICACION_ORGANISMO:
					log.debug("Autenticacion explicita con plugin organismo");
					AutenticacionExplicitaInfo authInfo = null;
					try{
						authInfo = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo();
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
			log.debug("[" + tramite.getIdentificador() + "] -Aviso a backoffice a través de EJB - Version original");
			
			// Pasamos a lista de string
			String ents[] = new String[entradas.size()];
			for (int i=0;i<entradas.size();i++){
				ReferenciaTramiteBandeja ref = (ReferenciaTramiteBandeja) entradas.get(i);
				ents[i] = new String(ref.getNumeroEntrada());
			}			
			
			// Procesamiento original: realizamos avisos de n numeros de entradas
			BteConectorFacadeHome homeCF = (BteConectorFacadeHome) EjbBackOfficeFactory.getInstance().getHome(tramite.getJndiEJB(),(tramite.getLocalizacionEJB() == Tramite.EJB_LOCAL?"LOCAL":tramite.getUrl()));
			BteConectorFacade ejbCF= homeCF.create();
			ejbCF.avisoEntradas(ents);					
			
			log.debug("[" + tramite.getIdentificador() + "] - Aviso entradas completado"); 
		}
		catch( Exception exc )
		{
			log.error( exc );
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
		log.debug("[" + tramite.getIdentificador() + "] -Aviso a backoffice a través de WS");
				
		//	 Comprobamos si requiere autenticacion explicita
		String user = null,pass=null; 
		switch (tramite.getAutenticacionEJB()){
			// No requiere autenticacion explicita (utilizara usr/pass auto)
			case Tramite.AUTENTICACION_SIN:
				log.debug("Autenticacion implicita con usuario auto");
				user = usuAuto;
				pass = passAuto; 
				break;
			// Requiere autenticacion explicita basada en usr/pass
			case Tramite.AUTENTICACION_ESTANDAR:
				log.debug("Autenticacion explicita con usuario/password");
				String claveCifrada = (String)DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().get("clave.cifrado");
				user = CifradoUtil.descifrar(claveCifrada,tramite.getUsr());
				pass = CifradoUtil.descifrar(claveCifrada,tramite.getPwd());
				break;
            // Requiere autenticacion explicita basada en plugin autenticacion explicita organismo
			case Tramite.AUTENTICACION_ORGANISMO:
				log.debug("Autenticacion explicita con plugin organismo");
				AutenticacionExplicitaInfo authInfo = null;
				try{
					authInfo = PluginFactory.getInstance().getPluginAutenticacionExplicita().getAutenticacionInfo();
					log.debug("Usuario plugin autenticacion organismo: " + authInfo.getUser());
				}catch (Exception ex){
					throw new Exception("Excepcion obteniendo informacion autenticacion explicita a traves de plugin organismo",ex);
				}
				user = authInfo.getUser();
				pass = authInfo.getPassword(); 
				break;				
		}
		
		ClienteWS.avisarEntradasWS(entradas,tramite.getUrl(),user,pass);
		
			
	}
					
	

}


