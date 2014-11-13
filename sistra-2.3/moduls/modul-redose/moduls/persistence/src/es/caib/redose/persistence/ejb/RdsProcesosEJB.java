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
import es.caib.redose.model.FicheroExterno;
import es.caib.redose.model.VersionCustodia;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.ConfiguracionDelegate;
import es.caib.redose.persistence.delegate.DelegateException;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.RdsAdminDelegate;
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
 *  @ejb.transaction type="NotSupported"
 * 
 * 
 */
public abstract class RdsProcesosEJB implements SessionBean {

	private static Log log = LogFactory.getLog( RdsProcesosEJB.class );
	
	private static boolean existepluginCustodia;
	
	private int limitePurgado = 1000;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {		
		existepluginCustodia = true;
		PluginCustodiaIntf pluginCustodia = null;
		try{
			pluginCustodia = PluginFactory.getInstance().getPluginCustodia();				
		}catch(Exception e){
			existepluginCustodia = false;
		}		
		try{
			String limiteStr = es.caib.redose.persistence.util.ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("scheduler.jobPurgadoDocumentos.limite");
			if (limiteStr != null) {
				limitePurgado = Integer.parseInt(limiteStr);
			}
		}catch(Exception e){
			limitePurgado = 1000;
		}	
	}
	
	/**
	 * Proceso de borrado de documentos sin usos.
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void purgadoDocumentos() throws ExcepcionRDS{
    	// Marcamos para borrar documentos sin usos
    	borradoDocumentosSinUsos();
    	// Borramos documentos marcados para borrar si se ha cumplido el plazo de espera
    	borradoDocumentosDefinitivamente();
    	// Purgamos documentos custodia
    	borradoDocumentosCustodia();
    	// Purgamos definitivamente los ficheros externos marcados para borrar.
         borradoDocumentosExternos(); 
    }
	
    
   /**
	 * Proceso de consolidacion de documentos en gestor documental
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void consolidacionGestorDocumental() throws ExcepcionRDS{
    	log.debug( "Proceso consolidacion Gestor Documental");
		try{
			RdsAdminDelegate delegate = DelegateUtil.getRdsAdminDelegate();
			
			// Recuperamos documentos a consolidar
			List docs = delegate.listarDocumentosPendientesConsolidar();
			if (docs == null) {
				log.debug( "Job consolidacion Gestor Documental: no existen documentos a consolidar");
				return;
			}
			
			// Consolidamos documentos
			log.debug( "Job consolidacion Gestor Documental: hay " + docs.size() + " documentos a consolidar");
			
			int i=0;
			for (Iterator it = docs.iterator();it.hasNext();){
				Documento doc = (Documento) it.next();
				ReferenciaRDS refRDS = new ReferenciaRDS(doc.getCodigo().longValue(),doc.getClave());
				
				try{
					delegate.consolidarDocumentoCustodia(refRDS);
					i++;
				}catch (Exception ex){
					// Pasamos al siguiente
					log.error("No se ha podido consolidar en custodia el documento " + doc.getCodigo() + ". Se intenta consolidar el siguiente.", ex);
					continue;
				}
				
			}	
			log.debug( "Proceso consolidacion Gestor Documental: se han consolidado " + i + " documentos");
		}catch(Exception e){
			throw new ExcepcionRDS("Error al consolidar los docuemntos en el gestor documental", e);
		}
    }
	
	// -----------------------------------------------------------------------------------------------
    //  FUNCIONES PRIVADAS
    // -----------------------------------------------------------------------------------------------
	/**
	 * Proceso de borrado de documentos sin usos
	 *   
     */
    private void borradoDocumentosSinUsos() throws ExcepcionRDS{    	
    	
    	log.debug("Proceso borrado de documentos sin usos");    	
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
    	int i=0;
    	for (Iterator it=docs.iterator();it.hasNext();){
    		if ( i > limitePurgado) {
    			log.debug("Limite de docs a purgar alcanzado. Se continuará en siguiente purga.");
    			i = limitePurgado;
    			break;
    		}
    		Documento d = (Documento) it.next();
    		try {
    			// Si se ha cumplido plazo de seguridad eliminamos documento
    			if ((d.getFecha().getTime() + periodo) < ahora){
    				log.debug("Documento " + d.getCodigo() + " ha superado periodo sin usos. Se procedera a eliminarse.");
    				rd.eliminarDocumento(new ReferenciaRDS(d.getCodigo().longValue(),d.getClave()));
    				i++;
    			}
			} catch (DelegateException e) {
				// Capturamos error para poder continuar el proceso de borrado
				log.error("Error al eliminar documento " + d.getCodigo(),e);
			}
    	}
    	log.debug( "Proceso borrado de documentos sin usos: se han marcado para borrar " + i + " documentos");
    }   

    /**
	 * Proceso de borrado de documentos en custodia
	 *     
     */
    private void borradoDocumentosCustodia() throws ExcepcionRDS{
    	log.debug( "Proceso borrado documentos en custodia");
    	RdsAdminDelegate rd = DelegateUtil.getRdsAdminDelegate();
		try{
			if(existepluginCustodia){
				int num = 0;
				List documentosParaBorrar = rd.listarVersionesCustodiaParaBorrar();
				if(documentosParaBorrar != null){					
					for(int i=0;i<documentosParaBorrar.size();i++){
						if ( num > limitePurgado) {
			    			log.debug("Limite de docs a purgar alcanzado. Se continuará en siguiente purga.");
			    			num = limitePurgado;
			    			break;
			    		}
						VersionCustodia vc = (VersionCustodia)documentosParaBorrar.get(i);
						try{
							rd.eliminaVersionDocumentoCustodia(vc.getCodigo());
							num++;
						}catch(Exception e){
							log.error("No se ha podido borrar el documento de Custodia con id= "+vc.getCodigo());
						}
					}				
				}
				log.debug( "Proceso borrado documentos en custodia: se han borrado " + num + " documentos");
			}			
		}catch(Exception e){
			throw new ExcepcionRDS("Error al eliminar los docuemntso de custodia ", e);
		}
    }

   /**
	 * Proceso de borrado de documentos sin usos
	 *     
     */
    private void borradoDocumentosDefinitivamente() throws ExcepcionRDS{    	
    	
    	log.debug("Proceso borrado definitivo de documentos marcados para borrar");    	
    	RdsAdminDelegate rd = DelegateUtil.getRdsAdminDelegate();
    	ConfiguracionDelegate confDelg = DelegateUtil.getConfiguracionDelegate();
    	// Recuperamos documentos marcados para borrar y lleven sin modificarse más de 'scheduler.jobBorrarDocumentosDefinitivamente.meses' (propiedad del fichero redose.properties) meses borrados
    	List docs = null;
    	String meses = "";
    	try{
    		meses = confDelg.obtenerConfiguracion().getProperty("scheduler.jobPurgadoDocumentos.mesesAntesBorradoDefinitivo");
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
    	int num = 0;
    	for (Iterator it=docs.iterator();it.hasNext();){
    		if ( num > limitePurgado) {
    			log.debug("Limite de docs a purgar alcanzado. Se continuará en siguiente purga.");
    			num = limitePurgado;
    			break;
    		}
    		Documento d = (Documento) it.next();
    		try {
   				log.debug("Documento " + d.getCodigo() + " ha superado el periodo de "+meses+" meses marcados para borrar. Se procedera a eliminarse.");
   				rd.eliminarDocumentoDefinitivamente(new ReferenciaRDS(d.getCodigo().longValue(),d.getClave()));
   				num++;
			} catch (DelegateException e) {
				// Capturamos error para poder continuar el proceso de borrado
				log.error("Error al eliminar documento " + d.getCodigo(),e);
			}
    	}    	
    	log.debug( "Proceso borrado definitivo de documentos marcados para borrar: se han borrado " + num + " documentos");
    }
    
    /**
     * Proceso de eliminar definitivamente los ficheros externos marcados para borrar.
     * @throws ExcepcionRDS
     */
    private void borradoDocumentosExternos() throws ExcepcionRDS {    
    	log.debug( "Proceso borrado definitivo documentos externos");
    	RdsAdminDelegate rd = DelegateUtil.getRdsAdminDelegate();
    	try{    		
			List documentosParaBorrar = rd.listarFicherosExternosParaBorrar();
			int num = 0;
			if(documentosParaBorrar != null){
				log.debug("Se recuperan " + documentosParaBorrar.size() + " ficheros externos marcados para borrar");
				for(int i=0;i<documentosParaBorrar.size();i++){
					if ( num > limitePurgado) {
		    			log.debug("Limite de docs a purgar alcanzado. Se continuará en siguiente purga.");
		    			num = limitePurgado;
		    			break;
		    		}
					FicheroExterno fe = (FicheroExterno)documentosParaBorrar.get(i);
					log.debug("El fichero externo " + fe.getReferenciaExterna() + " se procede a borrarse");
					try{
						rd.eliminaFicheroExterno(fe.getReferenciaExterna());
						num++;
					}catch(Exception e){
						log.error("No se ha podido eliminar el fichero externo " + fe.getReferenciaExterna() + ". Se intenta borrar el siguiente.", e);
					}
				}				
			}
			log.debug( "Proceso definitivo documentos externos: se han borrado " + num + " documentos");
		}catch(Exception e){
			throw new ExcepcionRDS("Error al eliminar los ficheros externos marcados para borrar", e);
		}
		
	}

}
