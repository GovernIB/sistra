package es.caib.zonaper.persistence.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;

import org.apache.commons.lang.StringUtils;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;
import es.caib.zonaper.persistence.util.AvisosDelegacion;
import es.caib.zonaper.persistence.util.UsernamePasswordCallbackHandler;

/**
 * SessionBean para generar avisos sobre delegacion.
 * Este EJB sera invocado por el delegado que desencadena la accion 
 *
 * @ejb.bean
 *  name="zonaper/persistence/AvisosDelegacionFacade"
 *  jndi-name="es.caib.zonaper.persistence.AvisosDelegacionFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 */
public abstract class AvisosDelegacionFacadeEJB extends HibernateEJB
{
	/**
     * @ejb.create-method     
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();		
	}
	
    /**
     * Realiza aviso de que un tramite esta pendiente de registrarse
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void avisarPendientePresentacionTramite(String idPersistencia)
	{
		try{
			// Obtenemos tramite
			TramitePersistenteDelegate tpd = DelegateUtil.getTramitePersistenteDelegate();
			TramitePersistente tp = tpd.obtenerTramitePersistente(idPersistencia);
			// Obtenemos datos entidad
			PersonaPAD ent = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(tp.getUsuario());
			// Comprobamos si tiene habilitado avisos por email
			if (ent.isHabilitarAvisosExpediente()){
				if (StringUtils.isNotEmpty(ent.getEmail())){
					// Realizamos aviso con usuario auto
					LoginContext lc = null;		
					try{					
						Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
						String user = props.getProperty("auto.user");
						String pass = props.getProperty("auto.pass");
						CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
						lc = new LoginContext("client-login", handler);
						lc.login();
						
						// Realizamos aviso 
						AvisosDelegacion.getInstance().avisarPendientePresentacionTramite(ent.getEmail(),tp.getIdioma(),ent.getNombreCompleto(),tp.getDescripcion());
					}finally{				
						// Hacemos el logout
						if ( lc != null ){
							try{lc.logout();}catch(Exception exl){}
						}
					}				
				}
			}
		}catch(Exception ex){
			throw new EJBException("Exception al realizar aviso de delegacion", ex);
		}		
	}
	
	/**
     * Realiza aviso de que un tramite esta pendiente de firmar documentos
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void avisarPendienteFirmarDocumentos(String idPersistencia)
	{
		try{
			// Obtenemos tramite
			TramitePersistenteDelegate tpd = DelegateUtil.getTramitePersistenteDelegate();
			TramitePersistente tp = tpd.obtenerTramitePersistente(idPersistencia);
			// Obtenemos datos entidad
			PersonaPAD ent = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(tp.getUsuario());
			// Comprobamos si tiene habilitado avisos por email
			if (ent.isHabilitarAvisosExpediente()){
				if (StringUtils.isNotEmpty(ent.getEmail())){
					List documentos = new ArrayList();
					List firmantes  = new ArrayList();
					
					// Obtenemos documentos del tramite pendientes de firma
					List docs = DelegateUtil.getBandejaFirmaDelegate().obtenerDocumentosPendientesFirma(ent.getNif());
					for (Iterator it=docs.iterator();it.hasNext();){
						DocumentoPersistente dp = (DocumentoPersistente) it.next();
						documentos.add(this.getNombreDocumento(dp));
						firmantes.add(this.getFirmantesDocumento(dp,tp.getIdioma()));						
					}
					
					// Realizamos aviso con usuario auto
					LoginContext lc = null;		
					try{					
						Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
						String user = props.getProperty("auto.user");
						String pass = props.getProperty("auto.pass");
						CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
						lc = new LoginContext("client-login", handler);
						lc.login();
						
						// Realizamos aviso 
						AvisosDelegacion.getInstance().avisarPendienteFirmarDocumentos(ent.getEmail(),tp.getIdioma(),ent.getNombreCompleto(),tp.getDescripcion(),documentos,firmantes);
					}finally{				
						// Hacemos el logout
						if ( lc != null ){
							try{lc.logout();}catch(Exception exl){}
						}
					}
						
				}
			}
		}catch(Exception ex){
			throw new EJBException("Exception al realizar aviso de delegacion", ex);
		}	
	}
	
	/**
     * Realiza aviso de que un delegado ha rechazado un documento para firmar
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void avisarRechazoDocumento(String idPersistencia,Long codigo)
	{
		try{
			// Obtenemos tramite
			TramitePersistenteDelegate tpd = DelegateUtil.getTramitePersistenteDelegate();
			TramitePersistente tp = tpd.obtenerTramitePersistente(idPersistencia);
			// Obtenemos datos entidad
			PersonaPAD ent = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(tp.getUsuario());
			// Comprobamos si tiene habilitado avisos por email
			if (ent.isHabilitarAvisosExpediente()){
				if (StringUtils.isNotEmpty(ent.getEmail())){
					
					// Obtenemos nombre del documento
					DocumentoPersistente dp = DelegateUtil.getTramitePersistenteDelegate().obtenerDocumentoTramitePersistente(codigo);
					String nomDoc = this.getNombreDocumento(dp);
					
					// Obtenemos nombre del usuario que ha rechazado
					PluginLoginIntf pluginLogin = PluginFactory.getInstance().getPluginLogin();
					String nifUsuario = pluginLogin.getNif(this.ctx.getCallerPrincipal());
					String nomUsuario = pluginLogin.getNombreCompleto(this.ctx.getCallerPrincipal());
															
					// Realizamos aviso con usuario auto
					LoginContext lc = null;		
					try{					
						Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
						String user = props.getProperty("auto.user");
						String pass = props.getProperty("auto.pass");
						CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
						lc = new LoginContext("client-login", handler);
						lc.login();
						
						// Realizamos aviso 
						AvisosDelegacion.getInstance().avisarRechazoDocumento(ent.getEmail(),tp.getIdioma(),ent.getNombreCompleto(),tp.getDescripcion(),nomDoc,nifUsuario + " - " + nomUsuario);
					}finally{				
						// Hacemos el logout
						if ( lc != null ){
							try{lc.logout();}catch(Exception exl){}
						}
					}
						
				}
			}
		}catch(Exception ex){
			throw new EJBException("Exception al realizar aviso de delegacion", ex);
		}	
	}
	
	/**
     * Realiza aviso de que ya se han firmado/rechazado todos los documentos pendientes y se puede
     * retomar el tramite
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void avisarContinuarTramite(String idPersistencia)
	{
		try{
			// Obtenemos tramite
			TramitePersistenteDelegate tpd = DelegateUtil.getTramitePersistenteDelegate();
			TramitePersistente tp = tpd.obtenerTramitePersistente(idPersistencia);
			// Obtenemos datos entidad
			PersonaPAD ent = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporUsuarioSeycon(tp.getUsuario());
			// Comprobamos si tiene habilitado avisos por email
			if (ent.isHabilitarAvisosExpediente()){
				if (StringUtils.isNotEmpty(ent.getEmail())){
					// Realizamos aviso con usuario auto
					LoginContext lc = null;		
					try{					
						Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
						String user = props.getProperty("auto.user");
						String pass = props.getProperty("auto.pass");
						CallbackHandler handler = new UsernamePasswordCallbackHandler( user, pass ); 					
						lc = new LoginContext("client-login", handler);
						lc.login();
						
						// Realizamos aviso 
						AvisosDelegacion.getInstance().avisarContinuarTramite(ent.getEmail(),tp.getIdioma(),ent.getNombreCompleto(),tp.getDescripcion());
					}finally{				
						// Hacemos el logout
						if ( lc != null ){
							try{lc.logout();}catch(Exception exl){}
						}
					}				
				}
			}
		}catch(Exception ex){
			throw new EJBException("Exception al realizar aviso de delegacion", ex);
		}		
	}
	
	// ----------------------------------------------------------------------------
	// 			FUNCIONES PRIVADAS
	// ----------------------------------------------------------------------------
	private String getNombreDocumento(DocumentoPersistente dp) throws Exception{
		DocumentoRDS docRDS = DelegateRDSUtil.getRdsDelegate().consultarDocumento(new ReferenciaRDS(dp.getRdsCodigo().longValue(),dp.getRdsClave()));
		return docRDS.getTitulo();
	}
	
	private String getFirmantesDocumento(DocumentoPersistente dp, String idioma) throws Exception{
		String[] firmantes = dp.getDelegacionFirmantes().split("#");
		String res = "";
		
		String partY = " y ";
		if ("ca".equals(idioma)){
			partY = " i ";
		}else if ("en".equals(idioma)){
			partY = " and ";
		}
		
		for (int i=0;i<firmantes.length;i++){
			if (i>0){
				if (i == (firmantes.length - 1)){
					res +=  partY;
				}else{
					res += ", ";
				}
			}
			PersonaPAD p = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(firmantes[i]);
			res += firmantes[i] + ((p!=null)?" - " +p.getNombreCompleto():"");							
		}
		return res;
	}
}
