package es.caib.zonaper.persistence.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.EntradaPreregistroBackup;
import es.caib.zonaper.model.TramitePersistente;
import es.caib.zonaper.model.TramitePersistenteBackup;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.caib.zonaper.persistence.delegate.TramitePersistenteDelegate;
import es.caib.zonaper.persistence.util.ConfigurationUtil;


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
     * @ejb.permission role-name = "${role.auto}"
     *
     */
	public void procesaTramitesCaducados( Date fechaEjecucion, boolean borradoPreregistro )
	{
			try
			{
				// Obtenemos numero maximo de elementos a tratar
				int maxTram = 5000;
				try {
					String maxTramStr = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("scheduler.backup.maxElementos");
					if (StringUtils.isNotBlank(maxTramStr)) {
						maxTram = Integer.parseInt(maxTramStr);
					}
				} catch (Exception e) {
					log.error("No se ha podido obtener propiedad scheduler.backup.maxElementos", e);
				}

				// Borramos tramites persistentes caducados pasandolos a backup
				eliminaTramitesPersistentesCaducados( fechaEjecucion, maxTram );

				// Borramos preregistros caducados pasandolos a backup
				if ( borradoPreregistro )
				{
					String meses = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("scheduler.backup.borradoPreregistro.meses");
					Calendar cal = Calendar.getInstance();
		            cal.setTime(fechaEjecucion);
		            cal.add(Calendar.MONTH, new Integer("-"+meses).intValue());
		            eliminaEntradasPrerregistroCaducadas( cal.getTime() , maxTram );
				}
			}
			catch( Exception exc )
			{
				throw new EJBException( exc );
			}
	}


	/**
	 * Elimina los tramits persistentes caducados
	 * @param fechaEjecucion
	 * @param maxTram
	 * @param delegate
	 * @throws DelegateException
	 */
	private void eliminaTramitesPersistentesCaducados( Date fechaEjecucion, int maxTram ) throws DelegateException
	{
		long inicio, fin;
		int count = 0;
		inicio = System.currentTimeMillis();
		backupLog.debug( "Eliminando tramites persistentes caducados. Fecha [ " + fechaEjecucion + "]" );
		TramitePersistenteDelegate tramitePersistenteDelegate = DelegateUtil.getTramitePersistenteDelegate();
		List lstTramitesPersistentesCaducados = tramitePersistenteDelegate.listarTramitePersistentesCaducados( fechaEjecucion, maxTram );

		for ( int i = 0; i < lstTramitesPersistentesCaducados.size(); i++ )
		{
			// Limitamos maximo de documentos
			if (count > maxTram){
				//Dejamos para siguiente ejecucion del proceso de backup
				break;
			}

			TramitePersistente tramitePersistente = ( TramitePersistente ) lstTramitesPersistentesCaducados.get( i );

			// Realizamos backup del tramite controlando la excepcion para que siga el proceso de backup
			try{
				DelegateUtil.getProcesoBackupDelegate().backupTramitePersistente(tramitePersistente);
			}catch (Exception ex){
				log.error( "Excepcion realizando backup de tramite persistente caducado con codigo " + tramitePersistente.getCodigo(),ex);
			}
			count ++;

		}
		fin = System.currentTimeMillis();
		backupLog.debug( "Se han trasladado a la tabla de backup " + lstTramitesPersistentesCaducados.size() + " tramites persistentes en " + ( fin - inicio ) + " milisegundos");
	}

	/**
	 * Elimina los prerregistros caducados
	 * @param fechaEjecucion
	 * @param maxTram
	 * @param delegate
	 * @throws DelegateException
	 */
	private void eliminaEntradasPrerregistroCaducadas( Date fechaEjecucion, int maxTram ) throws DelegateException
	{
		long inicio, fin;
		int count = 0;
		inicio = System.currentTimeMillis();
		backupLog.debug( "Eliminando entradas prerregistro caducadas. Fecha [ " + fechaEjecucion + "]" );
		EntradaPreregistroDelegate prerregistroDelegate = DelegateUtil.getEntradaPreregistroDelegate();
		List lstEntradasPrerregistroCaducados = prerregistroDelegate.listarEntradaPreregistrosCaducados( fechaEjecucion, maxTram );
		for ( int i = 0; i < lstEntradasPrerregistroCaducados.size(); i++ )
		{

			// Limitamos maximo de documentos
			if (count > maxTram){
				//Dejamos para siguiente ejecucion del proceso de backup
				break;
			}
			EntradaPreregistro entradaPrerregistro = ( EntradaPreregistro ) lstEntradasPrerregistroCaducados.get( i );

			//  Realizamos backup del tramite controlando la excepcion para que siga el proceso de backup
			try{
				DelegateUtil.getProcesoBackupDelegate().backupTramitePreregistro(entradaPrerregistro);
			}catch (Exception ex){
				log.error( "Excepcion realizando backup de tramite preregistro caducado con codigo " + entradaPrerregistro.getCodigo(),ex);
			}
			count ++;
		}
		fin = System.currentTimeMillis();
		backupLog.debug( "Se han trasladado a la tabla de backup " + lstEntradasPrerregistroCaducados.size() + " entradas de prerregistro caducadas en " + ( fin - inicio ) + " milisegundos");		
	}

	/**
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.auto}"
     *
     */
	public void procesaEliminarTramitesBackup( Date fechaMaxima )
	{
			try
			{
				// Obtenemos numero maximo de elementos a tratar
				int maxTram = 5000;
				try {
					String maxTramStr = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("scheduler.borradoBackup.maxElementos");
					if (StringUtils.isNotBlank(maxTramStr)) {
						maxTram = Integer.parseInt(maxTramStr);
					}
				} catch (Exception e) {
					log.error("No se ha podido obtener propiedad scheduler.backup.maxElementos", e);
				}


				eliminaTramitesPersistentesBackup( fechaMaxima, maxTram );
				eliminaEntradasPrerregistroBackup( fechaMaxima, maxTram );
			}
			catch( DelegateException exc )
			{
				throw new EJBException( exc );
			}
	}

	/**
	 * Elimina los tramites existentes en la tabla de backup
	 * @param fechaEjecucion
	 * @param maxTram
	 * @param delegate
	 * @throws DelegateException
	 */
	private void eliminaTramitesPersistentesBackup( Date fechaEjecucion, int maxTram ) throws DelegateException
	{
		long inicio, fin;
		int count = 0;
		inicio = System.currentTimeMillis();
		backupLog.debug( "Eliminando tramites persistentes de la tabla de tramites backup. Fecha [ " + fechaEjecucion + "]" );
		TramitePersistenteDelegate tramitePersistenteDelegate = DelegateUtil.getTramitePersistenteDelegate();
		List lstTramitesPersistentesBackup = tramitePersistenteDelegate.listarTramitePersistentesBackup( fechaEjecucion, maxTram );
		for ( int i = 0; i < lstTramitesPersistentesBackup.size(); i++ )
		{
			// Limitamos maximo de documentos
			if (count > maxTram){
				//Dejamos para siguiente ejecucion del proceso de backup
				break;
			}
			
			TramitePersistenteBackup tramitePersistenteBackup = ( TramitePersistenteBackup ) lstTramitesPersistentesBackup.get( i );

			try{
				// Eliminamos referencias en el RDS
    			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    			rds.eliminarUsos(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE, tramitePersistenteBackup.getIdPersistencia() );
				// Eliminamos tramite backup
				tramitePersistenteDelegate.borrarTramitePersistenteBackup(tramitePersistenteBackup);
			}catch (Exception ex){
				log.error( "Excepcion realizando la eliminación del tramite de la tabla de backup con codigo: " + tramitePersistenteBackup.getCodigo(),ex);
			}
			count ++;
		}
		fin = System.currentTimeMillis();
		backupLog.debug( "Se han eliminado " + lstTramitesPersistentesBackup.size() + " tramites persistentes de la tabla de backup en " + ( fin - inicio ) + " milisegundos");
	}

	/**
	 * Elimina los prerregistros de la tabla de backup
	 * @param fechaEjecucion
	 * @param maxTram
	 * @param delegate
	 * @throws DelegateException
	 */
	private void eliminaEntradasPrerregistroBackup( Date fechaEjecucion, int maxTram ) throws DelegateException
	{
		long inicio, fin;
		int count = 0;
		inicio = System.currentTimeMillis();
		backupLog.debug( "Eliminando entradas prerregistro de la trabla de backup. Fecha [ " + fechaEjecucion + "]" );
		EntradaPreregistroDelegate prerregistroDelegate = DelegateUtil.getEntradaPreregistroDelegate();
		List lstEntradasPrerregistroBackup = prerregistroDelegate.listarEntradaPreregistroBackup( fechaEjecucion, maxTram );
		for ( int i = 0; i < lstEntradasPrerregistroBackup.size(); i++ )
		{

			// Limitamos maximo de documentos
			if (count > maxTram){
				//Dejamos para siguiente ejecucion del proceso de backup
				break;
			}
			
			EntradaPreregistroBackup entradaPreregistroBackup = ( EntradaPreregistroBackup ) lstEntradasPrerregistroBackup.get( i );

			try{
				prerregistroDelegate.borrarEntradaPreregistroBackup(entradaPreregistroBackup);
			}catch (Exception ex){
				log.error( "Excepcion realizando backup de tramite preregistro de la tabla de backup " + entradaPreregistroBackup.getCodigo(),ex);
			}
			count ++;
		}
		fin = System.currentTimeMillis();
		backupLog.debug( "Se han eliminado " + lstEntradasPrerregistroBackup.size() + " entradas de prerregistro de la tabla de backup en " + ( fin - inicio ) + " milisegundos");
	}
}
