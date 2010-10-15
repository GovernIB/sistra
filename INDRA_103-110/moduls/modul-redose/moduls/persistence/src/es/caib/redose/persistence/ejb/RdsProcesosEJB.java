package es.caib.redose.persistence.ejb;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.redose.model.Documento;
import es.caib.redose.model.VersionCustodia;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsAdminDelegate;
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
			PluginCustodiaIntf pluginCustodia = PluginFactory.getInstance().getPluginCustodia();
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
		}catch(Exception e){
			throw new ExcepcionRDS("Error al eliminar los docuemntso de custodia ", e);
		}
    }

}
