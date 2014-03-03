package es.caib.redose.persistence.ejb;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import es.caib.redose.model.Documento;
import es.caib.redose.model.FicheroExterno;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.TipoOperacion;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.FicheroExternoDelegate;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.redose.persistence.delegate.VersionCustodiaDelegate;
import es.caib.redose.persistence.plugin.MetadaAlmacenamiento;
import es.caib.redose.persistence.plugin.PluginAlmacenamientoRDS;
import es.caib.redose.persistence.plugin.PluginAlmacenamientoRDSExterno;
import es.caib.redose.persistence.plugin.PluginClassCache;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;

/**
 * SessionBean que implementa la interfaz del RDS para la administración del RDS (uso interno)
 *
 * @ejb.bean
 *  name="redose/persistence/RdsAdminFacade"
 *  jndi-name="es.caib.redose.persistence.RdsAdminFacade"
 *  type="Stateless"
 *  view-type="remote" 
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * 
 */
public abstract class RdsAdminFacadeEJB extends HibernateEJB {

	private final static String ELIMINAR_DOCUMENTO = "ELDO";
	
	private static final long VENTANA_TIEMPO_CONSOLIDACION = 5 * 60 * 1000;
	
	
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin},${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
	}
	    
    /**
     * Lista documentos sin usos
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarDocumentosSinUsos() throws ExcepcionRDS{   
    	Session session = this.getSession();
    	try{
    		// Obtenemos documentos sin usos
	    	Query query = session.createQuery("FROM Documento AS d WHERE size(d.usos) = 0 and (d.borrado is null or d.borrado='N')");            
            List result = query.list();
            return result;            
    	}catch(Exception ex){
    		throw new EJBException("Error listando documentos sin usos",ex);
    	} finally {
	        close(session);
	    }     	
    }
    	
    
    /**
     * Eliminar documento del RDS. 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin},${role.auto}"
     */
    public void eliminarDocumento(ReferenciaRDS refRds) throws ExcepcionRDS{    
    	
    	/*
    	 * DE MOMENTO NO REALIZAMOS EL BORRADO, SOLO REALIZAMOS BORRADO LOGICO ESTABLECIENDO ATRIBUTO
    	 * HASTA QUE SE VERIFIQUE QUE NO HAY INCIDENCIAS    	 
    	eliminarDocumento(refRds);
    	*/
    	bajaLogicaDocumentoImpl(refRds);
    	
    	// Realizamos log de la operacion
        this.doLogOperacion(getUsuario(),ELIMINAR_DOCUMENTO,"eliminar documento " + refRds.getCodigo() );
    }
        
 
    
    /**
     * Lista documentos borrados
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarDocumentosBorrados(Date fecha) throws ExcepcionRDS{   
    	Session session = this.getSession();
    	try{
    		// Obtenemos documentos borrados antes que la fecha que nos pasan.
	    	Query query = session.createQuery("FROM Documento AS d WHERE d.borrado='S' and d.fecha < :fecha")
	    	.setParameter("fecha", fecha );
    		List result = query.list();
            return result;            
    	}catch(Exception ex){
    		throw new EJBException("Error listando documentos borrados",ex);
    	} finally {
	        close(session);
	    }     	
    }
    
    /**
     * Eliminar documento del RDS definitivamente. 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void eliminarDocumentoDefinitivamente(ReferenciaRDS refRds) throws ExcepcionRDS{    

    	eliminarDocumentoImpl(refRds);
    	// Realizamos log de la operacion
        this.doLogOperacion(getUsuario(),ELIMINAR_DOCUMENTO,"eliminar documento " + refRds.getCodigo() );
    }
    
    /**
     * Lista documentos pendientes de consolidar
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarDocumentosPendientesConsolidar() throws ExcepcionRDS{   
    	Session session = this.getSession();
    	try{
    		// Buscamos documentos que no contengan uso de tipo persitente
	    	Query query = session.createQuery("SELECT DISTINCT d FROM Documento AS d, Uso AS u WHERE u.documento.referenciaGestorDocumental is null and u.documento.codigo = d.codigo AND u.tipoUso.codigo IN ('ENV','RTE','EXP','RTS','BTE')");            
            List result = query.list();
            return result;            
    	}catch(Exception ex){
    		throw new EJBException("Error listando documentos pendientes de consolidar",ex);
    	} finally {
	        close(session);
	    }     	
    }
    
    /**
     * Lista veresiones custodia para borrar
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarVersionesCustodiaParaBorrar() throws ExcepcionRDS{   
    	try {
    		VersionCustodiaDelegate delegate = DelegateUtil.getVersionCustodiaDelegate();
    		List documentosParaBorrar = delegate.listarVersionesCustodiaParaBorrar();
    		return documentosParaBorrar;    
	    } catch (Exception ex) {
			throw new ExcepcionRDS("Error al obtener versiones para borrar en custodia", ex);
		}		
	}
    
    /**
     * Elimina version en custodia
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void eliminaVersionDocumentoCustodia(String codigoVersion) throws ExcepcionRDS{   
    	try {
	    	VersionCustodiaDelegate delegate = DelegateUtil.getVersionCustodiaDelegate();
	    	
	    	// Obtenemos plugin custodia
	    	PluginCustodiaIntf pluginCustodia = null;
			boolean existepluginCustodia = true;
			try{
				pluginCustodia = PluginFactory.getInstance().getPluginCustodia();
			}catch(Exception e){
				existepluginCustodia  = false;
			}
			if(!existepluginCustodia){
				throw new Exception("No existe plugin custodia");
			}
	    	
			// Eliminamos version en custodia
	    	pluginCustodia.eliminarDocumento(codigoVersion);
	    	
	    	// Borramos version en BBDD
			delegate.borrarVersion(codigoVersion);
		} catch (Exception ex) {
			throw new ExcepcionRDS("Error al eliminar version " + codigoVersion + " de custodia", ex);
		}		
    }
    
    
    /**
     * Lista ficheros externos para borrar
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarFicherosExternosParaBorrar() throws ExcepcionRDS{   
    	try {
    		FicheroExternoDelegate delegate = DelegateUtil.getFicheroExternoDelegate();
    		List documentosParaBorrar = delegate.obtenerListaFicherosExternoBorrar();
    		return documentosParaBorrar;    
	    } catch (Exception ex) {
			throw new ExcepcionRDS("Error al obtener ficheros externos para borrar en custodia", ex);
		}		
	}
    
    /**
     * Elimina fichero externo
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void eliminaFicheroExterno(String referenciaFic) throws ExcepcionRDS{   
    	try {
    		FicheroExternoDelegate delegate = DelegateUtil.getFicheroExternoDelegate();
	    	
    		// Obtenemos fichero externo
    		FicheroExterno fe = delegate.obtenerFicheroExterno(referenciaFic);
    		
    		// Obtenemos plugin de almacenamiento    		
    		Ubicacion ubicacion = DelegateUtil.getUbicacionDelegate().obtenerUbicacion(fe.getIdUbicacion());
    		PluginAlmacenamientoRDSExterno plgAlmacenamiento = obtenerPluginAlmacenamientoExterno(ubicacion);
    		
			// Eliminamos definitivamente fichero externo
    		plgAlmacenamiento.purgarFichero(referenciaFic);
	    	
	    	// Borramos fichero en BBDD
			delegate.eliminarFicheroExterno(referenciaFic);
			
		} catch (Exception ex) {
			throw new ExcepcionRDS("Error al eliminar fichero externo " + referenciaFic, ex);
		}		
    }
    
    /**
     * Consolida documento en custodia.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     */
    public void consolidarDocumentoCustodia(ReferenciaRDS refRDS) throws ExcepcionRDS{   
    	try {
    		RdsDelegate rdsDelegate = DelegateUtil.getRdsDelegate();
    		
    		// Si tiene un uso de tipo bandeja de entrada esperamos una ventana de tiempo por si
			// tiene activada el aviso inmediato
    		List usos = rdsDelegate.listarUsos(refRDS);
			UsoRDS uso = existeUsoBTE(usos);
			if (uso!=null && uso.getFechaUso().getTime() > (System.currentTimeMillis() - VENTANA_TIEMPO_CONSOLIDACION ) ) {
				// Si todavia no se ha cumplido la ventana de consolidacion lo dejamos para mas adelante
				return;
			}
			
			// Consolidamos documento
			rdsDelegate.consolidarDocumento(refRDS);
				
		} catch (Exception ex) {
			throw new ExcepcionRDS("Error al consolidar documento con id " + refRDS.getCodigo(), ex);
		}	
    }
    
    
    /**
     * Cuenta documentos pendientes de migrar.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public long contarDocumentosMigracion(Long ubicacionOrigen, Date fechaDesde, Date fechaHasta) {	    	
    	try{  
    		Long res = (Long) recuperaDocumentosMigracion(ubicacionOrigen, fechaDesde, fechaHasta, true, null);
    		return res.longValue();            
    	}catch(Exception ex){
    		throw new EJBException("Error contando documentos pendientes de migrar",ex);
    	}  
    }

    /**
     * Lista documentos a migrar.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
   public List listarDocumentosMigracion(Long ubicacionOrigen, Date fechaDesde, Date fechaHasta, int limiteDocsMigrar) {
	   try{  
   		List res = (List) recuperaDocumentosMigracion(ubicacionOrigen, fechaDesde, fechaHasta, false, new Integer(limiteDocsMigrar));
   		return res;            
   	}catch(Exception ex){
   		throw new EJBException("Error contando documentos pendientes de migrar",ex);
   	}      	 
  }
   
   /**
    * Migra documento a nueva ubicacion.
    * 
    * @ejb.interface-method
    * @ejb.permission role-name="${role.admin}"
    */
   public void migrarDocumento(Long codigoDocumento, Long codigoUbicacionDestino, boolean borrarUbicacionOrigen){	
	   try{  
		   
		   // Obtenemos documento origen
		   Documento documentoRDS = recuperaDocumento(codigoDocumento);
		   
		   // Obtenemos ubicacion destino
		   Ubicacion ubicacionDestino = DelegateUtil.getUbicacionDelegate().obtenerUbicacion(codigoUbicacionDestino);
		   
		   // Obtenemos plugin de almacenamiento origen y destino
		   PluginAlmacenamientoRDS plgAlmacenamientoOrigen  = PluginClassCache.getInstance().getPluginAlmacenamientoRDS(documentoRDS.getUbicacion());
		   PluginAlmacenamientoRDS plgAlmacenamientoDestino = PluginClassCache.getInstance().getPluginAlmacenamientoRDS(ubicacionDestino);
		   
		   // Obtenemos documento ubicacion origen
		   byte[] datosFichero = plgAlmacenamientoOrigen.obtenerFichero(codigoDocumento);
		   
		   // Almacenamos documento en nueva ubicacion
		   MetadaAlmacenamiento metadataFichero = new MetadaAlmacenamiento();
	       metadataFichero.setModelo(documentoRDS.getVersion().getModelo().getModelo());
	       metadataFichero.setVersion(documentoRDS.getVersion().getVersion());
	       metadataFichero.setDescripcion(documentoRDS.getTitulo());
	       metadataFichero.setExtension(documentoRDS.getExtensionFichero());
	       plgAlmacenamientoDestino.guardarFichero(codigoDocumento,datosFichero, metadataFichero);

	       // Borramos documento en antigua ubicacion
	       if (borrarUbicacionOrigen) {
	    	   plgAlmacenamientoOrigen.eliminarFichero(codigoDocumento);
	       }
	       
	       // Modificamos documento para establecer nueva ubicacion
	       cambiarUbicacionDocumento(codigoDocumento, ubicacionDestino);
	       
		}catch(Exception ex){
	   		throw new EJBException("Error migrando documento " + codigoDocumento,ex);
	   	} 
   }
    
    // ---------------------- Funciones auxiliares -------------------------------------------    
    
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
     * Obtiene plugin almacenamiento
     * @param classNamePlugin
     * @return Plugin almacenamiento externo
     */
    private PluginAlmacenamientoRDSExterno obtenerPluginAlmacenamientoExterno(Ubicacion ubicacion) throws Exception{
    	PluginAlmacenamientoRDS plgAlmacenamiento = PluginClassCache.getInstance().getPluginAlmacenamientoRDS(ubicacion);
    	 if (!(plgAlmacenamiento instanceof PluginAlmacenamientoRDSExterno)) {
 			throw new Exception("El plugin de almacenamiento no es externo");
 		}
    	 return (PluginAlmacenamientoRDSExterno) plgAlmacenamiento;
    }
    
    /* 
     * Funcion que realiza el borrado de un documento en el RDS
     */
    private void eliminarDocumentoImpl(ReferenciaRDS refRds) throws ExcepcionRDS{
    	// Borramos documento
    	Session session = getSession();
    	Documento documento;
    	Ubicacion ubicacion;
	    try {	    	
	    	// Obtenemos documento
	    	documento = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));	        
	    	// Comprobamos que la clave coincida
	    	if (!documento.getClave().equals(refRds.getClave())){
	    		throw new ExcepcionRDS("La clave no coincide");
	    	}	
	    	
	    	// Borramos versiones de custodia
	    	DelegateUtil.getVersionCustodiaDelegate().borrarVersionesDocumento(documento.getCodigo());
	    	
	    	// Obtenemos ubicacion documento
	    	ubicacion = documento.getUbicacion();
	    	 
	    	// Eliminamos documento
	    	session.delete(documento);
	    } catch (Exception he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            		    
	    
	    // Borramos fichero asociado                     
        try{        	
        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(ubicacion);
        	plugin.eliminarFichero(new Long(refRds.getCodigo()));
        }catch(Exception e){
        	log.error("No se ha podido eliminar fichero "+refRds.getCodigo()+" en ubicación " + ubicacion.getCodigoUbicacion());
        	throw new EJBException(e);
        }
    }
     
    
    /**
     * Realiza baja lógica del documento estableciendo atributo
     * 
     * @param refRds
     * @throws ExcepcionRDS
     */
    private void bajaLogicaDocumentoImpl(ReferenciaRDS refRds) throws ExcepcionRDS{
    	// Borramos documento
    	Session session = getSession();
    	Documento documento;
	    try {	    	
	    	// Obtenemos documento
	    	documento = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));	        
	    	// Comprobamos que la clave coincida
	    	if (!documento.getClave().equals(refRds.getClave())){
	    		throw new ExcepcionRDS("La clave no coincide");
	    	}	
	    	
	    	// Establecemos indicador de borrado
	    	documento.setBorrado("S");
	    	
	    	// Eliminamos documento
	    	session.update(documento);
	    	
	    } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            		    	    
    }
    
    
    /* 
     * Obtiene plugin almacenamiento
     * @param classNamePlugin
     * @return
     */ 
    private PluginAlmacenamientoRDS obtenerPluginAlmacenamiento(Ubicacion ubicacion) throws Exception{
    	return PluginClassCache.getInstance().getPluginAlmacenamientoRDS(ubicacion);    	
    }
    
 
    
    private void doLogOperacion(String idAplicacion,String idTipoOperacion,String mensaje)throws ExcepcionRDS  {    	
    	Session session = getSession();
    	try{
    		doLogOperacionImpl(idAplicacion,idTipoOperacion,mensaje,session);    		
    	}catch (Exception e){    		
    		throw new ExcepcionRDS("No se ha podido grabar en log",e);
    	} finally{
    		close(session);
    	}
    }
    
    private void doLogOperacionImpl(String idUsuario,String idTipoOperacion,String mensaje,Session session)throws HibernateException  {    	    	
		TipoOperacion tipoOperacion = (TipoOperacion) session.load(TipoOperacion.class,idTipoOperacion);
		
		LogOperacion log = new LogOperacion();
		log.setUsuarioSeycon(idUsuario);
		log.setTipoOperacion(tipoOperacion);
		log.setDescripcionOperacion(mensaje);
		log.setFecha(new Timestamp(System.currentTimeMillis()));
		
		session.save(log);    		    	
    }
    
    /**
     * Obtiene usuario autenticado
     * @return
     */
    private String getUsuario(){
    	if (this.ctx.getCallerPrincipal() != null)
    		return this.ctx.getCallerPrincipal().getName();
    	else
    		return "";
    }
    
    /**
     * Consultar codigos docs de una ubicacion o los cuenta.
     * 
     * @param ubicacion ubicacion 
     * @param fechaDesde fecha desde
     * @param fechaHasta fecha hasta
     * @param fechaHasta fecha hasta  
     * @param count si true cuenta los docs, si false devuelve lista de codigos
     * @param numMaxDocs si count=false establece num max de docs
     * @return lista codigos o numero de documentos
     */
	private Object recuperaDocumentosMigracion(Long ubicacion, Date fechaDesde, Date fechaHasta, boolean count, Integer numMaxDocs) {		
		Session session = this.getSession();
    	try{  
    		String sqlSelectCount = "select count(d.codigo) ";
    		String sqlSelectList = "select d.codigo ";
			String sqlWhere = " FROM Documento AS d WHERE d.ubicacion.codigo = :ubicacion";
			String sqlOrderBy = " order by d.fecha asc";
			    	    		
			if (fechaDesde != null) {
				sqlWhere += " and d.fecha >= :fechaDesde";    			
			}
			if (fechaHasta != null) {
				sqlWhere += " and d.fecha <= :fechaHasta";
			}
			
			String sqlSelect = count?sqlSelectCount:sqlSelectList;
			Query query = session.createQuery(sqlSelect + sqlWhere + sqlOrderBy);  
			
			query.setLong("ubicacion", ubicacion.longValue());
			if (fechaDesde != null) {
				query.setDate("fechaDesde", fechaDesde);    			
			}
			if (fechaHasta != null) {
				query.setDate("fechaHasta", fechaHasta);
			}
			
			if (count) {
				Object res = query.uniqueResult();
				return new Long(Long.parseLong(res != null? res.toString() : "0"));
			} else {
				return query.setMaxResults(numMaxDocs.intValue()).list();
			}
    	}catch(Exception ex){
    		throw new EJBException("Error recuperando documentos pendientes de migrar",ex);
    	} finally {
	        close(session);
	    }  
	}
	
	 /**
     * Recupera documento y contenido.
     * 
     * @param codigoDocumento codigoDocumento 
     * @return documento
     */
	private Documento recuperaDocumento(Long codigoDocumento) {		
		Session session = this.getSession();
    	try{  
    		Documento doc = (Documento) session.get(Documento.class, codigoDocumento);
    		return doc;    		
    	}catch(Exception ex){
    		throw new EJBException("Error recuperando documento " + codigoDocumento,ex);
    	} finally {
	        close(session);
	    }  
	}
	
	/**
     * Recupera documento y contenido.
     * 
     * @param codigoDocumento codigoDocumento 
     * @return documento
     */
	private void cambiarUbicacionDocumento(Long codigoDocumento, Ubicacion ubicacion) {		
		Session session = this.getSession();
    	try{  
    		Documento doc = (Documento) session.get(Documento.class, codigoDocumento);
    		doc.setUbicacion(ubicacion);
    		session.update(doc);
    	}catch(Exception ex){
    		throw new EJBException("Error modificacion ubicacion documento " + codigoDocumento,ex);
    	} finally {
	        close(session);
	    }  
	}


  
}
