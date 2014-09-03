package es.caib.zonaper.persistence.ejb;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.regtel.persistence.delegate.RegistroOrganismoDelegate;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.zonaper.model.LogRegistro;
import es.caib.zonaper.model.RegistroExternoPreparado;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.LogRegistroDelegate;
import es.caib.zonaper.persistence.delegate.RegistroExternoPreparadoDelegate;


/**
 * Proceso de rechazar una notificación en una transacción nueva.
 * 
 *
 * @ejb.bean
 *  name="zonaper/persistence/ProcesoRevisarRegistrosFacade"
 *  jndi-name="es.caib.zonaper.persistence.ProcesoRevisarRegistrosFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="RequiresNew"
 */
public abstract class ProcesoRevisarRegistrosFacadeEJB extends HibernateEJB
{
	private static Log backupLog = LogFactory.getLog( ProcesoRevisarRegistrosFacadeEJB.class );
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name = "${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

	}	
	
	/**
	 * Revisa registro realizado:
	 *  - Si existe lo borra del log de registros
	 *  - Si no existe, invoca a anular registro organismo y lo mantiene como anulado
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.auto}"
     * 
     */
	public void revisarRegistro( LogRegistro logReg)
	{
		try {
			backupLog.debug( "Revisando registro " + logReg.getId().getNumeroRegistro());			
			LogRegistroDelegate dlg = DelegateUtil.getLogRegistroDelegate();
			RegistroOrganismoDelegate dlgOrg = es.caib.regtel.persistence.delegate.DelegateUtil.getRegistroOrganismoDelegate();
			//Añadimos 15 minutos a la fecha de registro.
			Date dateAux = new Date();
            int minutesToAdd = 15;  // 15 minutos
            Calendar cal = Calendar.getInstance();
            cal.setTime(logReg.getFechaRegistro());
            cal.add(Calendar.MINUTE, minutesToAdd);
            /*  miramos si se ha registrado por lo menos 15 minutos antes de la ejecución del proceso, para dar tiempo
             *	a que termine la TX global
             */           
			if(cal.getTime().before(dateAux)){
				if(dlg.tieneUsos(logReg)){					
					dlg.borrarLogRegistro(logReg.getId());
					backupLog.debug( "Revisando registro " + logReg.getId().getNumeroRegistro() + ": registro confirmado");
				}else{
					if(logReg.getId().getTipoRegistro().equals(ConstantesAsientoXML.TIPO_REGISTRO_SALIDA+"")){
						dlgOrg.anularRegistroSalida(logReg.getId().getNumeroRegistro(), logReg.getFechaRegistro());
					}else{
						dlgOrg.anularRegistroEntrada(logReg.getId().getNumeroRegistro(), logReg.getFechaRegistro());
					}
					logReg.setAnulado("S");
					dlg.grabarLogRegistro(logReg);				
					backupLog.debug( "Revisando registro " + logReg.getId().getNumeroRegistro() + ": registro anulado");
				}
			}	else {
				backupLog.debug( "Revisando registro " + logReg.getId().getNumeroRegistro() + ": todavia no se ha cumplido el plazo para revisar");
			}
						
		} catch (Exception ex) {
			throw new EJBException(ex);
		}
					
	}
	
	
	
	/**
	 * Revisa registro externo preparado: elimina registros no usados. 
	
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.auto}"
     * 
     */
	public void revisarRegistroExternoPreparado( RegistroExternoPreparado r)
	{
		try {
			backupLog.debug( "Revisando registro externo preparado: " + r.getIdPersistencia());
			RegistroExternoPreparadoDelegate dlg = DelegateUtil.getRegistroExternoPreparadoDelegate();
			RdsDelegate rdsDlg = DelegateRDSUtil.getRdsDelegate();
			// Borramos usos de persistencia
			rdsDlg.eliminarUsos(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE, r.getIdPersistencia());
			// Borramos log registro
			dlg.borrarRegistroExternoPreparado(r.getCodigoRdsAsiento());						
			backupLog.debug( "Revisando registro externo preparado: " + r.getIdPersistencia() + " - Borrado");
		} catch (Exception ex) {
			throw new EJBException(ex);
		}
					
	}
	
	
	    
}
