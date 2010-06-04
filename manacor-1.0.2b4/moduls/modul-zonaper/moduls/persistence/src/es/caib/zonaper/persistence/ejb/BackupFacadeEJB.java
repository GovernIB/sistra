package es.caib.zonaper.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

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
 *  name="zonaper/persistence/BackupFacade"
 *  jndi-name="es.caib.zonaper.persistence.BackupFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="NotSupported"
 */
public abstract class BackupFacadeEJB extends HibernateEJB
{
	private static Log backupLog = LogFactory.getLog( BackupFacadeEJB.class );
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.admin}"
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}
	
	
	/**
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.admin}"
     * @ejb.permission role-name = "${role.auto}"
     * 
     */
	public void procesaTramitesCaducados( Date fechaEjecucion, boolean borradoPreregistro )
	{
			try
			{
				eliminaTramitesPersistentesCaducados( fechaEjecucion );
				if ( borradoPreregistro )
				{
					eliminaEntradasPrerregistroCaducadas( fechaEjecucion );
				}
			}
			catch( DelegateException exc )
			{
				throw new EJBException( exc );
			}
	}
	
	
	/**
	 * Elimina los tramits persistentes caducados
	 * @param fechaEjecucion
	 * @param delegate
	 * @throws DelegateException
	 */
	private void eliminaTramitesPersistentesCaducados( Date fechaEjecucion ) throws DelegateException
	{
		backupLog.debug( "Eliminando tramites persistentes caducados. Fecha [ " + fechaEjecucion + "]" );
		TramitePersistenteDelegate tramitePersistenteDelegate = DelegateUtil.getTramitePersistenteDelegate();		
		List lstTramitesPersistentesCaducados = tramitePersistenteDelegate.listarTramitePersistentesCaducados( fechaEjecucion );
		for ( int i = 0; i < lstTramitesPersistentesCaducados.size(); i++ )
		{
			TramitePersistente tramitePersistente = ( TramitePersistente ) lstTramitesPersistentesCaducados.get( i );
			
			// Realizamos backup del tramite controlando la excepcion para que siga el proceso de backup
			try{
				DelegateUtil.getProcesoBackupDelegate().backupTramitePersistente(tramitePersistente);
			}catch (Exception ex){
				log.error( "Excepcion realizando backup de tramite persistente caducado con codigo " + tramitePersistente.getCodigo(),ex);	
			}
			
		}
	}
	
	/**
	 * Elimina los prerregistros caducados
	 * @param fechaEjecucion
	 * @param delegate
	 * @throws DelegateException
	 */
	private void eliminaEntradasPrerregistroCaducadas( Date fechaEjecucion ) throws DelegateException
	{
		backupLog.debug( "Eliminando entradas prerregistro caducadas. Fecha [ " + fechaEjecucion + "]" );
		EntradaPreregistroDelegate prerregistroDelegate = DelegateUtil.getEntradaPreregistroDelegate();
		List lstEntradasPrerregistroCaducados = prerregistroDelegate.listarEntradaPreregistrosCaducados( fechaEjecucion );		
		for ( int i = 0; i < lstEntradasPrerregistroCaducados.size(); i++ )
		{
			EntradaPreregistro entradaPrerregistro = ( EntradaPreregistro ) lstEntradasPrerregistroCaducados.get( i );
			
			//  Realizamos backup del tramite controlando la excepcion para que siga el proceso de backup
			try{
				DelegateUtil.getProcesoBackupDelegate().backupTramitePreregistro(entradaPrerregistro);
			}catch (Exception ex){
				log.error( "Excepcion realizando backup de tramite preregistro caducado con codigo " + entradaPrerregistro.getCodigo(),ex);	
			}
		}
	}
    
}
