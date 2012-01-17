package es.caib.zonaper.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;

import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;

import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.login.PluginLoginIntf;
import es.caib.util.NifCif;
import es.caib.xml.ConstantesXML;
import es.caib.xml.delegacion.factoria.FactoriaObjetosXMLDelegacion;
import es.caib.xml.delegacion.factoria.ServicioDelegacionXML;
import es.caib.xml.delegacion.factoria.impl.AutorizacionDelegacion;
import es.caib.zonaper.model.Delegacion;
import es.caib.zonaper.model.Persona;
import es.caib.zonaper.modelInterfaz.ConstantesZPE;
import es.caib.zonaper.modelInterfaz.PersonaPAD;
import es.caib.zonaper.persistence.delegate.ConsultaPADDelegate;
import es.caib.zonaper.persistence.delegate.DelegateException;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.util.ConfigurationUtil;

/**
 * SessionBean para mantener y consultar Delegacion
 *
 * @ejb.bean
 *  name="zonaper/persistence/DelegacionFacade"
 *  jndi-name="es.caib.zonaper.persistence.DelegacionFacade"
 *  type="Stateless"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleDelegacion" type="java.lang.String" value="${role.delegacion}"
 */
public abstract class DelegacionFacadeEJB extends HibernateEJB
{
	private String roleDelegacion;
	
	private boolean firmarDelegacionRepresentante;
	
	/**
     * @ejb.create-method     
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
		try{
			InitialContext initialContext = new InitialContext();			 
			roleDelegacion = (( String ) initialContext.lookup( "java:comp/env/roleDelegacion" ));
			
			String firmarDlgRpte = ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("delega.firmarDelegacionRepresentante");
			if (firmarDlgRpte != null && firmarDlgRpte.equals("false")) {
				firmarDelegacionRepresentante = false;
			} else {
				firmarDelegacionRepresentante = true;
			}
			
		}catch(Exception ex){
			log.error(ex);
		}
	}
	
    /**
     * Obtiene delegaciones válidas que tiene el usuario autenticado
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public List obtenerDelegacionesUsuario()
	{
		Session session = getSession();
		try
		{			
			Query query = session.createQuery( "SELECT d FROM Delegacion d, Persona p where d.nifDelegado = :nifUsuario and d.nifDelegante = p.documentoIdLegal and p.habilitarDelegacion = 'S' and d.anulada <> 'S' and d.fechaFinDelegacion > :ahora"  );
			query.setParameter( "nifUsuario", PluginFactory.getInstance().getPluginLogin().getNif(this.ctx.getCallerPrincipal()));
			query.setParameter( "ahora", new Date() );
			query.setCacheable( true );
			List dlgs = query.list();
			return dlgs;		
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
     * Obtiene delegaciones válidas que tiene una entidad
     * @ejb.interface-method
     * @ejb.permission role-name="${role.delegacion}"
     * @ejb.permission role-name="${role.todos}"
     */
	public List obtenerDelegacionesEntidad(String nifEntidad)
	{
		Session session = getSession();
		try
		{			
			String nifRpte = this.obtenerRepresentanteEntidad(nifEntidad);
			String permisos = obtenerPermisosDelegacion(nifEntidad);
			PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new EJBException("No puede comprobarse si es el representante");
			}
	//  	 Solo puede acceder si eres usuario con rol delegacion, el representante o la entidad.
			String nifUsuario = plgLogin.getNif(this.ctx.getCallerPrincipal());
			if (!this.ctx.isCallerInRole(roleDelegacion) && !nifUsuario.equals(nifRpte) && !nifUsuario.equals(nifEntidad) && StringUtils.isBlank(permisos)){
				throw new EJBException("Solo pueden obtener las delegaciones de una entidad el representante, la entidad o un delegado de la entidad.");	
			}

			Query query = session.createQuery( "SELECT d FROM Delegacion d, Persona p where d.nifDelegante = :nifEntidad and d.nifDelegante = p.documentoIdLegal and p.habilitarDelegacion = 'S' and d.anulada <> 'S' and d.fechaFinDelegacion > :ahora"  );
			query.setParameter( "nifEntidad", nifEntidad);
			query.setParameter( "ahora", new Date() );
			query.setCacheable( true );
			List dlgs = query.list();
			return dlgs;		
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
     * Obtiene permisos de delegacion sobre la entidad del usuario autenticado 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     *
     * @param nif nif/cif entidad
     * @return permisos entidad
     */
	public String obtenerPermisosDelegacion(String nifEntidad)
	{
		// NOTA: Se da permiso acceso a role auto ya que puede venir con este role una consulta
		//       de permisos a traves de ConsultaPADEJB que se ejecuta con role auto
		
		String permisos = "";
		Session session = getSession();
		try
		{			
			String nifUsuario = PluginFactory.getInstance().getPluginLogin().getNif(this.ctx.getCallerPrincipal());
			if (StringUtils.isEmpty(nifUsuario)){
				return permisos;
			}
			
			Query query = session.createQuery("SELECT d FROM Delegacion d, Persona p where d.nifDelegado = :nifUsuario and d.nifDelegante = :nifEntidad  and d.nifDelegante = p.documentoIdLegal and p.habilitarDelegacion = 'S' and d.anulada <> 'S' and d.fechaInicioDelegacion <= :ahora and d.fechaFinDelegacion > :ahora"); 
			query.setParameter( "nifEntidad", nifEntidad);
			query.setParameter( "nifUsuario",nifUsuario);
			query.setParameter( "ahora", new Date() );
			query.setCacheable( true );
			List dlgs = query.list();
			
			if (dlgs != null){			
				for (Iterator it=dlgs.iterator();it.hasNext();){
					Delegacion d = (Delegacion) it.next();
					permisos += d.getPermisos();
				}
			}
			
			return permisos;		
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
     * Obtiene permisos de delegacion sobre la entidad para el usuario indicado
     * 
     * @param nif nif/cif entidad
     * @param nif nif usuario
     * @return permisos entidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
	public String obtenerPermisosDelegacion(String nifEntidad, String nifUsuario)
	{
		Session session = getSession();
		try
		{			
			Query query = session.createQuery("SELECT d FROM Delegacion d, Persona p where d.nifDelegado = :nifUsuario and d.nifDelegante = :nifEntidad  and d.nifDelegante = p.documentoIdLegal and p.habilitarDelegacion = 'S' and d.anulada <> 'S' and d.fechaInicioDelegacion <= :ahora and d.fechaFinDelegacion > :ahora"); 
			query.setParameter( "nifEntidad", nifEntidad);
			query.setParameter( "nifUsuario", nifUsuario);
			query.setParameter( "ahora", new Date() );
			query.setCacheable( true );
			List dlgs = query.list();
			
			String permisos = "";
			if (dlgs != null){			
				for (Iterator it=dlgs.iterator();it.hasNext();){
					Delegacion d = (Delegacion) it.next();
					permisos += d.getPermisos();
				}
			}
			
			return permisos;		
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
     * Indica si la entidad tiene habilitada la delegacion
     * 
     * @param nif nif/cif entidad
     * @return boolean
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"   
     * @ejb.permission role-name="${role.auto}"    
     */
	public boolean habilitadaDelegacion(String nifEntidad){
		Session session = getSession();
		try
		{			
			Query query = session.createQuery("FROM Persona p where p.documentoIdLegal = :nifEntidad"); 
			query.setParameter( "nifEntidad", nifEntidad);
			query.setCacheable( true );
			Persona per = (Persona) query.uniqueResult();
			return "S".equals(per.getHabilitarDelegacion());					
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
	 * habilita la delegacion de las entidades
     * 
     * @param nif nif/cif entidad
     * @param habilitar inidica si queremos habilitar(S) o deshabilitar (N)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.delegacion}"
   	 */
	public void habilitarDelegacion(String nifEntidad, String habilitar){
		Session session = getSession();
		try
		{			
			Query query = session.createQuery("FROM Persona p where p.documentoIdLegal = :nifEntidad"); 
			query.setParameter( "nifEntidad", nifEntidad);
			query.setCacheable( true );
			Persona per = (Persona) query.uniqueResult();
			if(per != null && !StringUtils.isBlank(per.getDocumentoIdLegal())){
				per.setHabilitarDelegacion(habilitar);
				session.update(per);
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
	
	
	/**
     * Obtiene la delegacion con el codigo que nos llega
     *
     * @param codigo codigo de la entidad
     * @return la delegación que tiene el codigo que nos pasan
     *  
     * @ejb.interface-method
     * @ejb.permission role-name="${role.delegacion}"
     * @ejb.permission role-name="${role.todos}"
     */
	public Delegacion obtenerDelegacion(long codigo)
	{
		Session session = getSession();
		try
		{			
			// Query query = session.createQuery( "SELECT d FROM Delegacion d where d.codigo = :codigo and d.anulada <> 'S' and d.fechaInicioDelegacion <= :ahora and d.fechaFinDelegacion > :ahora"  );
			Query query = session.createQuery( "SELECT d FROM Delegacion d where d.codigo = :codigo"  );
			query.setParameter( "codigo", new Long(codigo));
			// query.setParameter( "ahora", new Date() );
			query.setCacheable( true );
			Delegacion del = (Delegacion) query.uniqueResult();
			String nifRpte = this.obtenerRepresentanteEntidad(del.getNifDelegante());
			PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new EJBException("No puede comprobarse si es el representante");
			}
	//  	 Solo puede acceder si eres usuario con rol delegacion, el representante o la entidad.
			String nifUsuario = plgLogin.getNif(this.ctx.getCallerPrincipal());
			if (!this.ctx.isCallerInRole(roleDelegacion) && !nifUsuario.equals(nifRpte) && !nifUsuario.equals(del.getNifDelegante())){
				throw new EJBException("Solo puede anular una delegacion el representante o la entidad");	
			}
			return del;		
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
     * Anular la delegacion con el codigo que nos llega
     * Una delegacion de tipo representante solo puede anularse con role delegacion
     * Una delegacion de otro tipo solo puede anularla el representante
     *
     * @param codigo codigo de la entidad
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.delegacion}"
     * @ejb.permission role-name="${role.todos}"
     */
	public void anularDelegacion(long codigo)
	{
		// Obtenemos delegacion
		Delegacion d = obtenerDelegacion(codigo);
		if (d==null){
			throw new EJBException("No existe delegacion");
		}
		
		// Si la delegacion es de rpte solo puede anularla el role delegacion
		if (d.getPermisos().indexOf(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) != -1){
			if (!this.ctx.isCallerInRole(this.roleDelegacion)){
				throw new EJBException("Solo puede anularse una delegacion de representacion con role delegacion");
			}
		}else{					
		// Si la delegacion es de otro tipo solo puede anularla el rpte o la propia entidad
			String nifRpte = this.obtenerRepresentanteEntidad(d.getNifDelegante());
			PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new EJBException("No puede comprobarse si es el representante");
			}
			String nifUsuario = plgLogin.getNif(this.ctx.getCallerPrincipal());
			if (!nifUsuario.equals(nifRpte) && !nifUsuario.equals(d.getNifDelegante())){
				throw new EJBException("Solo puede anular una delegacion el representante o la entidad");	
			}
		}
		
		// Anulamos delegacion
		d.setAnulada("S");
		
		// Grabamos delegacion
		this.grabarDelegacion(d);
		
	}
	
	
	/**
     * Devuelve representante activo de la entidad
     * 
     * @param nifEntidad nif/cif entidad
     * @return Nif representante activo. Nulo si no hay.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public String obtenerRepresentanteEntidad(String nifEntidad)
	{
		Session session = getSession();
		try
		{			
			Query query = session.createQuery("SELECT d FROM Delegacion d, Persona p where d.nifDelegante = :nifEntidad  and d.nifDelegante = p.documentoIdLegal and p.habilitarDelegacion = 'S' and d.anulada <> 'S' and d.fechaFinDelegacion > :ahora"); 
			query.setParameter( "nifEntidad", nifEntidad);
			query.setParameter( "ahora", new Date() );
			query.setCacheable( true );
			List dlgs = query.list();
			
			if (dlgs != null){			
				for (Iterator it=dlgs.iterator();it.hasNext();){
					Delegacion d = (Delegacion) it.next();
					if(d.getPermisos().contains(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD)){
						return d.getNifDelegado();
					}
				}
				return null;
			}else{
				return null;
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
	
	
	/**
     * Crea una delegacion a partir de un documento de autorizacion de delegacion y la firma asociada
     * 
     * @param refRDS Referencia RDS del documento de delegacion
     * @param firma Firma del documento
     * @return codigo de error: 0:Ok / -1: Documento incorrecto / -2:Datos no válidos / -3:No esta habilitada delegacion /
     * 							-4: Alta representante solo para role delegacion / -5: Ya existe representante / 
     * 							-6: Firmante incorrecto / -7: Error verificacion firma
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
	public int altaDelegacion(ReferenciaRDS refRDS, FirmaIntf firma)
	{				
		try{
			RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
			
			// Obtenemos datos de delegacion
			AutorizacionDelegacion delegXml = null;
			try{
				// Obtenemos documento RDS
				DocumentoRDS doc = rds.consultarDocumento(refRDS);
				// No debe tener usos asociados, ya que se usa para esta nueva delegacion
				List usos = rds.listarUsos(refRDS);
				if (usos.size() > 0){
					log.error("Documento ya esta siendo utilizado");
					return -1;
				}
				// Extraemos datos documento
				ByteArrayInputStream bis = new ByteArrayInputStream(doc.getDatosFichero());
				FactoriaObjetosXMLDelegacion factoria = ServicioDelegacionXML.crearFactoriaObjetosXML();
				factoria.setEncoding(ConstantesXML.ENCODING);
				delegXml = factoria.crearAutorizacionDelegacion(bis);					
				bis.close();
			}catch(Exception ex){
				log.error("Error accediendo a documento delegacion",ex);
				return -1;
			}
			
			// Validamos datos autorizacion
			if (!validarDatosAutorizacion(delegXml)){
				return -2;
			}
			
			// Validamos que este habilitada la delegacion
			if (!habilitadaDelegacion(delegXml.getEntidadId())){
				return -3;
			}
			
			// Obtenemos representante entidad
			String nifRpte = obtenerRepresentanteEntidad(delegXml.getEntidadId());
			
			// Obtenemos nif usuario autenticado
			PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new EJBException("No puede comprobarse si es el representante");
			}
			String nifUsuario = plgLogin.getNif(this.ctx.getCallerPrincipal());
			
			// En caso de que la delegacion sea de representacion solo puede darla de alta con role delegacion,
			// no debe existir un representante activo
			if (delegXml.getPermisos().indexOf(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) != -1){
				if (!this.ctx.isCallerInRole(this.roleDelegacion)){
					return -4;
				}
				if(nifRpte != null){
					return -5;
				}				
			}else{
			// En otro caso debe ser el representante o la propia entidad
				if (!nifUsuario.equals(nifRpte) && !nifUsuario.equals(delegXml.getEntidadId())){
					return -6;
				}				
			}
			
			// Asociamos firma al documento
			// (Si es role delegacion para asignar representante no hace falta firma si esta configurado que no se firme)
			boolean requiereFirma = true;
			if (delegXml.getPermisos().indexOf(ConstantesZPE.DELEGACION_PERMISO_REPRESENTANTE_ENTIDAD) != -1 
					&& !firmarDelegacionRepresentante) {
				requiereFirma = false;
			}
			if (requiereFirma) {
				// Comprobamos que quien firma es el usuario autenticado
				if (!firma.getNif().equals(nifUsuario)){
					return -6;
				}
				// Asociamos firma a documento
				try{					
					rds.asociarFirmaDocumento(refRDS, firma);
				}catch (Exception ex){
					if (!this.ctx.getRollbackOnly()){
						this.ctx.setRollbackOnly();
					}
					log.error("Error validando firma",ex);
					return -7;	
				}
			}
			
			// Creamos delegacion
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Delegacion delegacion = new Delegacion();
			delegacion.setAnulada("N");
			delegacion.setClaveRdsDocDelegacion(refRDS.getClave());
			delegacion.setCodigoRdsDocDelegacion(new Long(refRDS.getCodigo()));
			Date dateFi = sdf.parse(delegXml.getFechaFin());
			delegacion.setFechaFinDelegacion(new Timestamp(dateFi.getTime()));
			Date dateIni = sdf.parse(delegXml.getFechaInicio());
			delegacion.setFechaInicioDelegacion(new Timestamp(dateIni.getTime()));
			delegacion.setNifDelegado(delegXml.getDelegadoId());
			delegacion.setNifDelegante(delegXml.getEntidadId());
			delegacion.setPermisos(delegXml.getPermisos());
			Long codDelegacion = grabarDelegacion(delegacion);
			
			// Asociamos uso de delegacion al documento
			UsoRDS uso = new UsoRDS();
			uso.setReferenciaRDS(refRDS);
			uso.setTipoUso(ConstantesRDS.TIPOUSO_DELEGACION);
			uso.setReferencia(codDelegacion.toString());
			rds.crearUso(uso);	
			
			return 0;
		}catch (Exception he) 
		{	        
			// Lanzamos exc xa hacer rollback
			throw new EJBException(he);
		 } 
	}
		
		
	private boolean validarDatosAutorizacion(AutorizacionDelegacion delegXml){
		
		Date fechaIni = null;
		Date fechaFi = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		if(StringUtils.isBlank(delegXml.getEntidadId())){
			return false;
		}
		
		int res = NifCif.validaDocumento(delegXml.getEntidadId());
		if (res != NifCif.TIPO_DOCUMENTO_NIF && res != NifCif.TIPO_DOCUMENTO_CIF){
			return false;
		}
		
		if(StringUtils.isBlank(delegXml.getDelegadoId())){
			return false;
		}
		
		if(NifCif.validaDocumento(delegXml.getDelegadoId()) != NifCif.TIPO_DOCUMENTO_NIF){
			return false;
		}
		
		if (delegXml.getDelegadoId().equals(delegXml.getEntidadId())){
			return false;
		}
		
		ConsultaPADDelegate consultaPAD = DelegateUtil.getConsultaPADDelegate();
		PersonaPAD persona = null;
		try {
			persona = consultaPAD.obtenerDatosPADporNif(delegXml.getDelegadoId());
		} catch (DelegateException e1) {
			persona = null;
		}
		if (persona == null){
			return false;
		}
				
		if(StringUtils.isBlank(delegXml.getFechaInicio())){
			return false;
		}
		
		try{
			fechaIni = sdf.parse(delegXml.getFechaInicio());
		}catch(Exception e){
			return false;
		}
		
		if(StringUtils.isBlank(delegXml.getFechaFin())){
			return false;
		}
				
		try{
			fechaFi = sdf.parse(delegXml.getFechaFin());
		}catch(Exception e){
			return false;
		}
		
		
		if(fechaIni.after(fechaFi)){
			return false;
		}
				
		return true;
	}
	
	 /**
     * Obtiene delegaciones entidad
     * 
     * @param deleg Delegación que queremos dar de alta.
     *
     */
	private Long grabarDelegacion(Delegacion deleg)
	{
    	Session session = getSession();
        try {
        	String nifRpte = this.obtenerRepresentanteEntidad(deleg.getNifDelegante());
			PluginLoginIntf plgLogin;
			try {
				plgLogin = PluginFactory.getInstance().getPluginLogin();
			} catch (Exception e) {
				throw new EJBException("No puede comprobarse si es el representante");
			}
//  		 Solo puede acceder si eres usuario con rol delegacion, el representante o la entidad.
			String nifUsuario = plgLogin.getNif(this.ctx.getCallerPrincipal());
			if (!this.ctx.isCallerInRole(roleDelegacion) && !nifUsuario.equals(nifRpte) && !nifUsuario.equals(deleg.getNifDelegante())){
				throw new EJBException("Solo puede anular una delegacion el representante o la entidad");	
			}

        	if (deleg.getCodigo() == null){
        		session.save(deleg);
        	}else{
        		session.update(deleg);
        	}
        	
        	return deleg.getCodigo();
        } catch (Exception ex) {
            throw new EJBException(ex);
        } finally {
            close(session);
        }
	}



}
