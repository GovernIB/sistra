package es.caib.zonaper.persistence.ejb;

import javax.ejb.CreateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;


/**
 * SessionBean para realizar backup de entradas de prerregistros y tramites persistentes caducados
 * 
 *
 * @ejb.bean
 *  name="zonaper/persistence/ProcesoBackupFacade"
 *  jndi-name="es.caib.zonaper.persistence.ProcesoBackupFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="RequiresNew"
 */
public abstract class ProcesoBackupFacadeEJB extends HibernateEJB
{
	private static Log backupLog = LogFactory.getLog( ProcesoBackupFacadeEJB.class );
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}	
	
	/**
	 * Realiza el backup de un tramite persistente: elimina de tabla de persistencia y lo pasa a la tabla backup 
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.auto}"
     * 
     */
	public void backupTramitePersistente( TramitePersistente tramitePersistente ) throws DelegateException
	{
			TramitePersistenteDelegate tramitePersistenteDelegate = DelegateUtil.getTramitePersistenteDelegate();
			backupLog.debug( "Pasando tramite persistente ["  + tramitePersistente.getIdPersistencia() + "] a tabla de backup" );
			tramitePersistenteDelegate.backupTramitePersistente( tramitePersistente );
			tramitePersistenteDelegate.borrarTramitePersistente( tramitePersistente.getIdPersistencia() );		
	}
	
	/**
	 * Realiza el backup de un tramite de preregistro: elimina de tabla de preregistro y lo pasa a la tabla backup
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.auto}"
     * 
     */
	public void backupTramitePreregistro(EntradaPreregistro entradaPrerregistro) throws DelegateException
	{
			EntradaPreregistroDelegate prerregistroDelegate = DelegateUtil.getEntradaPreregistroDelegate();
			backupLog.debug( "Pasando entrada prerregistro ["  + entradaPrerregistro.getIdPersistencia() + "] a tabla de backup" );
			prerregistroDelegate.backupEntradaPreregistro( entradaPrerregistro );
			prerregistroDelegate.borrarEntradaPreregistro( entradaPrerregistro.getCodigo()  );		
	}
    
}
