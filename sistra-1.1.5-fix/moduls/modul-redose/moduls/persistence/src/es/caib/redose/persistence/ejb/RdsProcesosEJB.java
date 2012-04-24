package es.caib.redose.persistence.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.model.Documento;
import es.caib.redose.model.VersionCustodia;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.ConfiguracionDelegate;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsAdminDelegate;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.redose.persistence.delegate.VersionCustodiaDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;


/**
 * SessionBean que implementa los procesos automaticos del RDS
 *
 * @ejb.bean
 *  name="redose/persistence/RdsProcesos"
 *  jndi-name="es.caib.redose.persistence.RdsProcesos"
 *  type="Stateless"
 *  view-type="remote" 
 *  transaction-type="Container"
 *
 * @ejb.security-identity run-as = "${role.auto}"
 *
 * @ejb.transaction type="NotSupported"
 * 
 * 
 */
public abstract class RdsProcesosEJB implements SessionBean {

	private static Log log = LogFactory.getLog( RdsProcesosEJB.class );
	
	private static final long VENTANA_TIEMPO_CONSOLIDACION = 5 * 60 * 1000;
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {		
	}
	
	/**
	 * Proceso de borrado de documentos sin usos
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void borradoDocumentosSinUsos() throws ExcepcionRDS{    	
    	
    	log.debug("Empieza proceso borrado de documentos sin usos");    	
    	RdsAdminDelegate rd = DelegateUtil.getRdsAdminDelegate();
    	
    	// Recuperamos documentos sin usos
    	List docs = null;
    	try{
    		docs = rd.listarDocumentosSinUsos();
    		log.debug("Se recuperan " + docs.size() + " documentos sin usos");
    	}catch(Exception ex){
    		log.error("Error obteniendo los documentos sin usos",ex);
    		throw new ExcepcionRDS("Error obteniendo los documentos sin usos",ex);
    	}
    	
    	// Borramos los docs sin usos que lleven sin modificarse más de 24 horas
    	long ahora = (new Date()).getTime();
    	long periodo = (24 * 60 * 60 * 1000);  
    	for (Iterator it=docs.iterator();it.hasNext();){
    		Documento d = (Documento) it.next();
    		try {
    			// Si se ha cumplido plazo de seguridad eliminamos documento
    			if ((d.getFecha().getTime() + periodo) < ahora){
    				log.debug("Documento " + d.getCodigo() + " ha superado periodo sin usos. Se procedera a eliminarse.");
    				rd.eliminarDocumento(new ReferenciaRDS(d.getCodigo().longValue(),d.getClave()));
    			}
			} catch (DelegateException e) {
				// Capturamos error para poder continuar el proceso de borrado
				log.error("Error al eliminar documento " + d.getCodigo(),e);
			}
    	}
    	
    }   

    /**
	 * Proceso de borrado de documentos en custodia
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void borradoDocumentosCustodia() throws ExcepcionRDS{
    	log.debug( "Job borrado documentos en custodia");
		VersionCustodiaDelegate delegate = DelegateUtil.getVersionCustodiaDelegate();
		try{
			boolean existepluginCustodia = true;
			PluginCustodiaIntf pluginCustodia = null;
			try{
				pluginCustodia = PluginFactory.getInstance().getPluginCustodia();
			}catch(Exception e){
				existepluginCustodia = false;
			}
			if(existepluginCustodia){
			List documentosParaBorrar = delegate.listarVersionesCustodiaParaBorrar();
			if(documentosParaBorrar != null){
				for(int i=0;i<documentosParaBorrar.size();i++){
					VersionCustodia vc = (VersionCustodia)documentosParaBorrar.get(i);
					try{
						pluginCustodia.eliminarDocumento(vc.getCodigo()+"");
						delegate.borrarVersion(vc.getCodigo());
					}catch(Exception e){
						log.error("No se ha podido borrar el documento de Custodia con id= "+vc.getCodigo());
					}
				}				
			}
			}
		}catch(Exception e){
			throw new ExcepcionRDS("Error al eliminar los docuemntso de custodia ", e);
		}
    }

    /**
	 * Proceso de consolidacion de documentos en gestor documental
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void consolidacionGestorDocumental() throws ExcepcionRDS{
    	log.debug( "Job consolidacion Gestor Documental");
		try{
			// Recuperamos documentos a consolidar
			List docs = DelegateUtil.getRdsAdminDelegate().listarDocumentosPendientesConsolidar();
			if (docs == null) {
				log.debug( "Job consolidacion Gestor Documental: no existen documentos a consolidar");
				return;
			}
			
			// Consolidamos documentos
			log.debug( "Job consolidacion Gestor Documental: hay " + docs.size() + " documentos a consolidar");
			RdsDelegate rdsDelegate = DelegateUtil.getRdsDelegate();
			int i=0;
			for (Iterator it = docs.iterator();it.hasNext();){
				Documento doc = (Documento) it.next();
				ReferenciaRDS refRDS = new ReferenciaRDS(doc.getCodigo().longValue(),doc.getClave());
				List usos = rdsDelegate.listarUsos(refRDS);
				// Si tiene un uso de tipo bandeja de entrada esperamos una ventana de tiempo por si
				// tiene activada el aviso inmediato
				UsoRDS uso = existeUsoBTE(usos);
				if (uso!=null && uso.getFechaUso().getTime() > (System.currentTimeMillis() - VENTANA_TIEMPO_CONSOLIDACION ) ) {
					// Pasamos al siguiente
					continue;
				}
				try{
					rdsDelegate.consolidarDocumento(refRDS);
					i++;
				}catch (Exception ex){
					// Pasamos al siguiente
					continue;
				}
			}	
			log.debug( "Job consolidacion Gestor Documental: se han consolidado " + i + " documentos");
		}catch(Exception e){
			throw new ExcepcionRDS("Error al consolidar los docuemntos en el gestor documental", e);
		}
    }
    
    /**
     * Comprueba si existe un uso de tipo BTE
     * @param usos
     * @return
     */
    private UsoRDS existeUsoBTE(List usos){
    	for (Iterator it = usos.iterator();it.hasNext();){
    		UsoRDS uso = (UsoRDS) it.next();
    		if (uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_BANDEJA)){
    			return uso;
    		}    			
    	}
    	return null;
    }
    
    
	/**
	 * Proceso de borrado de documentos sin usos
	 * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public void borradoDocumentosDefinitivamente() throws ExcepcionRDS{    	
    	
    	log.debug("Empieza proceso borrado definitivo de documentos marcados para borrar");    	
    	RdsAdminDelegate rd = DelegateUtil.getRdsAdminDelegate();
    	ConfiguracionDelegate confDelg = DelegateUtil.getConfiguracionDelegate();
    	// Recuperamos documentos marcados para borrar y lleven sin modificarse más de 'scheduler.jobBorrarDocumentosDefinitivamente.meses' (propiedad del fichero redose.properties) meses borrados
    	List docs = null;
    	String meses = "";
    	try{
    		meses = confDelg.obtenerConfiguracion().getProperty("scheduler.jobBorrarDocumentosDefinitivamente.meses");
			Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MONTH, new Integer("-"+meses).intValue());
    		docs = rd.listarDocumentosBorrados(cal.getTime());
    		log.debug("Se recuperan " + docs.size() + " documentos marcados para borrar");
    	}catch(Exception ex){
    		log.error("Error obteniendo los documentos marcados para borrar",ex);
    		throw new ExcepcionRDS("Error obteniendo los documentos marcados para borrar",ex);
    	}
    	
    	// Borramos los docs que tengan la marca de borrados.  
    	for (Iterator it=docs.iterator();it.hasNext();){
    		Documento d = (Documento) it.next();
    		try {
   				log.debug("Documento " + d.getCodigo() + " ha superado el periodo de "+meses+" meses marcados para borrar. Se procedera a eliminarse.");
   				rd.eliminarDocumentoDefinitivamente(new ReferenciaRDS(d.getCodigo().longValue(),d.getClave()));
			} catch (DelegateException e) {
				// Capturamos error para poder continuar el proceso de borrado
				log.error("Error al eliminar documento " + d.getCodigo(),e);
			}
    	}
    	
    }   
}
