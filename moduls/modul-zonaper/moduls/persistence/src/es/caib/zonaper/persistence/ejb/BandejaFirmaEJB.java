package es.caib.zonaper.persistence.ejb;


import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.CredentialUtil;
import es.caib.zonaper.model.DocumentoPersistente;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;
import es.caib.zonaper.persistence.util.CacheProcesamiento;

/**
 * SessionBean que sirve para acceder a los documentos pendientes de firmar de los delegados.
 *
 * @ejb.bean
 *  name="zonaper/persistence/BandejaFirmaFacade"
 *  local-jndi-name = "es.caib.zonaper.persistence.BandejaFirmaFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 */
public abstract class BandejaFirmaEJB extends HibernateEJB{

	private static Log log = LogFactory.getLog(BandejaFirmaEJB.class);
	protected SessionContext ctx;
	
	 public void setSessionContext(SessionContext ctx) {
	        this.ctx = ctx;
	 }

    /**
     * @ejb.create-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void ejbCreate() throws CreateException 
    {     
    	super.ejbCreate();
    }
	  
      
    /**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public List obtenerDocumentosPendientesFirma(String nifEntidad)
	{	
		Session session = getSession();
		try
		{
			PluginLoginIntf pluginLogin = PluginFactory.getInstance().getPluginLogin();
			
			// Solo de forma autenticada
			if (pluginLogin.getMetodoAutenticacion(this.ctx.getCallerPrincipal()) == CredentialUtil.NIVEL_AUTENTICACION_ANONIMO){
				throw new Exception("Solo se permite acceso autenticado");
			}
			
			// Controlamos si se accede en modo delegado
			String nifUsuario = pluginLogin.getNif(this.ctx.getCallerPrincipal());
			String usuarioEntidad;
			if (nifEntidad.equals(nifUsuario)){
				// Accede la propia entidad	
				usuarioEntidad = this.ctx.getCallerPrincipal().getName();
			}else{
				// Accede un delegado. Comprobamos que es un delegado.
				String permisos = DelegateUtil.getDelegacionDelegate().obtenerPermisosDelegacion(nifEntidad);
				if (StringUtils.isEmpty(permisos)){
					throw new Exception("El usuario no es delegado de la entidad");
				}
				
				// Obtenemos datos entidad
				PersonaPAD entidad = DelegateUtil.getConsultaPADDelegate().obtenerDatosPADporNif(nifEntidad);
				usuarioEntidad = entidad.getUsuarioSeycon();
			}
			
			Query query = session.createQuery( 
					"SELECT d FROM DocumentoPersistente d where d.tramitePersistente.usuario = :usuarioEntidad and " +
					"d.delegacionEstado = :pendienteFirmar and " +
					"d.tramitePersistente.estadoDelegacion = :estadoDelegacionTramite " +
					"order by d.tramitePersistente.fechaCreacion, d.tramitePersistente.codigo"  );
			query.setParameter( "usuarioEntidad",usuarioEntidad );
			query.setParameter( "pendienteFirmar", DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA );
			query.setParameter( "estadoDelegacionTramite", TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA );
			query.setCacheable( true );
			List docs = query.list();
			return docs;		
		} 
		catch (Exception he) 
		{	        
			throw new EJBException(he);
	    } 
		finally 
		{
	        close(session);
	    }
	}	
	
	 /**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void firmarDocumentoBandejaFirma( Long codigo, FirmaIntf firma)
	{	
		String idPersistencia = null;
		try {
			String estadoDelegacion=null,firmantes=null,firmantesPendientes=null;
			
			TramitePersistenteDelegate tpDelegate = DelegateUtil.getTramitePersistenteDelegate();
			
			// Obtenemos nif usuario autenticado
			String nifUsuario = PluginFactory.getInstance().getPluginLogin().getNif(this.ctx.getCallerPrincipal());
			
			// Obtenemos documento 
			// Al acceder al documento del tramite se validar el acceso por parte del usuario 
			DocumentoPersistente doc = tpDelegate.obtenerDocumentoTramitePersistente(codigo);
			estadoDelegacion = doc.getDelegacionEstado();
			firmantes = doc.getDelegacionFirmantes();
			firmantesPendientes = doc.getDelegacionFirmantesPendientes();	
			
			// Comprobamos que el doc este en estado de pendiente de firma
			if (doc.getDelegacionEstado() == null || !doc.getDelegacionEstado().equals(DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA)){
				throw new Exception("El documento no esta en estado de pendiente de firma");
			}
						
			// Validamos que la firma sea la del usuario autenticado
			if (!nifUsuario.equals(firma.getNif())){
				throw new Exception("La firma no pertenece al usuario autenticado");
			}
			
			// Quitamos al firmante de los firmantes pendientes de firmar.
			if(StringUtils.isNotBlank(firmantesPendientes) && firmantesPendientes.contains(nifUsuario)){
				firmantesPendientes = StringUtils.remove(firmantesPendientes,nifUsuario);
			}else{
				throw new Exception("El usuario no esta entre los firmantes pendientes de firmar.");
			}
			
			// Si no existen mas firmantes para firmar establecemos reseteamos estado delegacion documento
			if(StringUtils.isEmpty(firmantesPendientes) || StringUtils.containsOnly(firmantesPendientes, "#")){
				estadoDelegacion = null;
				firmantes = null;
				firmantesPendientes = null;				
			}
			
			// Bloqueamos tramite al que pertenece el documento antes de empezar los cambios
			idPersistencia = doc.getTramitePersistente().getIdPersistencia();
			if (!CacheProcesamiento.guardar(idPersistencia)){
				throw new Exception("Se estan firmando documentos de este tramite en este momento");
			}
			
			//Asociamos la firma al documento
			RdsDelegate rdsDelg = DelegateRDSUtil.getRdsDelegate();
			ReferenciaRDS ref = new ReferenciaRDS();
			ref.setClave(doc.getRdsClave());
			ref.setCodigo(doc.getRdsCodigo().longValue());
			if(firma.getNif().equals(nifUsuario)){
				rdsDelg.asociarFirmaDocumento(ref,firma);
			}else{
				throw new Exception("El firmante no es válido.");
			}
			
			// Actualizamos info delegacion documento
			tpDelegate.actualizarInfoDelegacionDocumentoTramitePersistente(codigo,estadoDelegacion,firmantes,firmantesPendientes);
						
			//Si no quedan documentos pendientes de firma en el tramite resetemos info delegacion tramite
			if(!this.quedanDocumentosPendientesFirma(doc.getTramitePersistente())){
				tpDelegate.actualizarInfoDelegacionTramitePersistente(doc.getTramitePersistente().getIdPersistencia(),null);
			}
			
		} catch (Exception e) {
			throw new EJBException(e);
		}finally{
			if (idPersistencia != null){
				CacheProcesamiento.borrar(idPersistencia);
			}
		}					
	}	

	/**
	 * Acceso autenticado. Solo accesible por usuario y delegados.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public void rechazarDocumentoBandejaFirma( Long codigo )
	{	
		
		String idPersistencia = null;
		try {
			
			TramitePersistenteDelegate tpDelegate = DelegateUtil.getTramitePersistenteDelegate();
			
			// Obtenemos documento 
			// Al acceder al documento del tramite se validar el acceso por parte del usuario 
			DocumentoPersistente doc = tpDelegate.obtenerDocumentoTramitePersistente(codigo);
			
			// Comprobamos que el doc este en estado de pendiente de firma
			if (doc.getDelegacionEstado() == null || !doc.getDelegacionEstado().equals(DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA)){
				throw new Exception("El documento no esta en estado de pendiente de firma");
			}
			
			// Bloqueamos tramite al que pertenece el documento antes de empezar los cambios
			idPersistencia = doc.getTramitePersistente().getIdPersistencia();
			if (!CacheProcesamiento.guardar(idPersistencia)){
				throw new Exception("Se estan firmando documentos de este tramite en este momento");
			}
			
			// Borramos firmas que se hayan hecho sobre el documento
			this.borrarFirmasDocumento(doc);
			
			// Reseteamos info delegacion documento
			tpDelegate.actualizarInfoDelegacionDocumentoTramitePersistente(codigo,null,null,null);
			
			//Si no quedan documentos pendientes de firma en el tramite resetemos info delegacion tramite
			if(!this.quedanDocumentosPendientesFirma(doc.getTramitePersistente())){
				tpDelegate.actualizarInfoDelegacionTramitePersistente(doc.getTramitePersistente().getIdPersistencia(),null);
			}
						
		} catch (Exception e) {
			throw new EJBException(e);
		}finally{
			if (idPersistencia != null){
				CacheProcesamiento.borrar(idPersistencia);
			}
		}
	}	
	
	private boolean quedanDocumentosPendientesFirma(TramitePersistente tramite)
	{	
		Session session = getSession();
		try
		{			
			Query query = session.createQuery( "SELECT d FROM DocumentoPersistente d where d.delegacionEstado = :pendienteFirmar  and  d.tramitePersistente.estadoDelegacion = :estadoDelegacionTramite and d.tramitePersistente.codigo = :idTramite"  );
			query.setParameter( "pendienteFirmar", DocumentoPersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA );
			query.setParameter( "estadoDelegacionTramite", TramitePersistentePAD.ESTADO_PENDIENTE_DELEGACION_FIRMA );
			query.setParameter( "idTramite", tramite.getCodigo() );
			query.setCacheable( true );
			List docs = query.list();
			if(docs != null && docs.size() > 0){
				return true;
			}else{
				return false;	
			}
		} 
		catch (Exception he) 
		{	        
			throw new EJBException(he);
	    } 
		finally 
		{
	        close(session);
	    }
	}
	
	private void borrarFirmasDocumento(DocumentoPersistente doc) throws Exception
	{	
		RdsDelegate rdsDelg = DelegateRDSUtil.getRdsDelegate();
		ReferenciaRDS ref = new ReferenciaRDS();
		ref.setClave(doc.getRdsClave());
		ref.setCodigo(doc.getRdsCodigo().longValue());
		DocumentoRDS docRds = rdsDelg.consultarDocumento(ref);
        if(docRds.getFirmas() != null && docRds.getFirmas().length > 0){
           	docRds.setFirmas( new FirmaIntf[]{} );
           	rdsDelg.actualizarDocumento(docRds);
		}
	}	
	
	
}
