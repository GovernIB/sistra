package es.caib.bantel.persistence.ejb;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.expression.Order;
import net.sf.hibernate.impl.SessionFactoryImpl;

import org.apache.commons.lang.StringUtils;

import es.caib.bantel.model.CriteriosBusquedaTramite;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.Page;
import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.modelInterfaz.ConstantesBTE;
import es.caib.bantel.modelInterfaz.DocumentoBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.delegate.DelegateBTEUtil;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.util.DataUtil;
import es.caib.util.PropertiesOrdered;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
import es.caib.xml.analiza.Analizador;
import es.caib.xml.analiza.Nodo;
import es.caib.xml.analiza.Par;
import es.caib.xml.analiza.formdoc.AnalizadorDoc;
import es.caib.xml.registro.factoria.ConstantesAsientoXML;
import es.caib.xml.util.HashMapIterable;


/**
 * SessionBean para mantener y consultar TramiteBandeja
 *
 * @ejb.bean
 *  name="bantel/persistence/TramiteBandejaFacade"
 *  jndi-name="es.caib.bantel.persistence.TramiteBandejaFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * @ejb.env-entry name="roleAuto" value="${role.auto}"
 * 
 * @ejb.security-role-ref role-name="${role.auto}" role-link="${role.auto}"
 * 
 */
public abstract class TramiteBandejaFacadeEJB extends HibernateEJB {

	protected static final String CODIGO_LISTAS = "[CODIGO]";
	protected static final String INDICE_LISTAS = "indice";
	
	private String ROLE_AUTO;
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

		try {
			InitialContext ctx = new InitialContext();
			ROLE_AUTO = (String) ctx.lookup("java:comp/env/roleAuto");
		} catch (NamingException e) {
			throw new CreateException("No se ha especificado role auto");
		}
		
    	
	}
	  
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public TramiteBandeja obtenerTramiteBandeja(Long id) {
        Session session = getSession();
        try {
        	// Cargamos TramiteBandeja        	
        	TramiteBandeja t = (TramiteBandeja) session.load(TramiteBandeja.class, id);        	
        	// Cargamos documentos
        	Hibernate.initialize(t.getDocumentos());        	
            return t;           
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public TramiteBandeja obtenerTramiteBandeja(String numeroEntrada) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM TramiteBandeja AS m WHERE m.numeroEntrada = :entrada");
            query.setParameter("entrada", numeroEntrada);
            
            List result = query.list();
            if (result.isEmpty()) {
                return null;
            }

            TramiteBandeja t = (TramiteBandeja) result.get(0);            
            // Cargamos documentos
        	Hibernate.initialize(t.getDocumentos());     	
            return t;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.registro}"
     * @ejb.permission role-name="${role.gestor}"
     * */
    public TramiteBandeja obtenerTramiteBandejaPorNumeroPreregistro(String numeroPreregistro) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM TramiteBandeja AS m WHERE m.numeroPreregistro = :numeroPreregistro");
            query.setParameter("numeroPreregistro", numeroPreregistro);
            
            List result = query.list();
            if (result.isEmpty()) {
                return null;
            }

            TramiteBandeja t = (TramiteBandeja) result.get(0);            
            // Cargamos documentos
        	Hibernate.initialize(t.getDocumentos());     	
            return t;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * */
    public TramiteBandeja obtenerTramiteBandejaPorNumeroRegistro(String numeroRegistro) {
        Session session = getSession();
        try {
            Query query = session.createQuery("FROM TramiteBandeja AS m WHERE m.numeroRegistro = :numeroRegistro");
            query.setParameter("numeroRegistro", numeroRegistro);
            
            List result = query.list();
            if (result.isEmpty()) {
                return null;
            }

            TramiteBandeja t = (TramiteBandeja) result.get(0);            
            // Cargamos documentos
        	Hibernate.initialize(t.getDocumentos());     	
            return t;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
 	/**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public Long grabarTramiteBandeja(TramiteBandeja obj) {        
    	Session session = getSession();
        try {        	
        	if (obj.getCodigo() == null){ 
        		session.save(obj);
        	}else{
        		session.update(obj);
        	}
        	                    	
            return obj.getCodigo();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
        	
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarTramiteBandeja(Long id) {
        Session session = getSession();
        try {
            TramiteBandeja t = (TramiteBandeja) session.load(TramiteBandeja.class, id);
            session.delete(t);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /*
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     
    public List listarTramitesOrganoDestino(Long id) {
        Session session = getSession();
        try {
        	// Cargamos OrganoDestino
        	OrganoDestino org = (OrganoDestino) session.load(OrganoDestino.class,id);        	
        	// Listamos tramites
        	Query query = session
            .createQuery("FROM TramiteBandeja AS t WHERE t.organoDestino = :org")
            .setParameter("org",org);
            query.setCacheable(true);              
            return query.list();
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    */
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     */
    public void borrarDocumentosTramite(Long id) {
        Session session = getSession();
        try {
        	TramiteBandeja t = (TramiteBandeja) session.load(TramiteBandeja.class, id);
        	t.getDocumentos().removeAll(t.getDocumentos());
        	session.update(t);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.gestor}"
     */
    public Page busquedaPaginadaTramites( CriteriosBusquedaTramite criteriosBusqueda, int pagina, int longitudPagina )
    {
    	GestorBandeja gestor = null;
    	try{
	    	// Obtenemos datos gestor por Usuario Seycon
	    	GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
	    	gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
	    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
    	}catch (Exception he) 
		{
	        throw new EJBException(he);
	    } 
    	    	
    	// Es posible que haya que sustituir Criteria por Query, con la from clause construida de forma dinámica
    	// Seria necesario si en la paginacion se necesita saber el numero maximo de paginas para 
		Session session = getSession();
		try 
		{			
			// Realizamos busqueda paginada
			Criteria criteria =createCriteriaFromCriteriosBusquedaTramite(criteriosBusqueda,session);	
			Page page = new Page( criteria, pagina, longitudPagina );
			
			// Calculamos manualmente total de tramites
			int total = countFromCriteriosBusquedaTramite(criteriosBusqueda,session);
			page.setTotalResults(total); 
			log.debug("TOTAL QUERY: " + total);
			
			
			return page;			
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
     * @ejb.interface-method
     * @ejb.permission role-name="${role.auto}"
     * @ejb.permission role-name="${role.gestor}"    
     */
    public List obtenerReferenciasEntradas(Long codigoProcedimiento, String identificadorTramite,String procesada,Date desde,Date hasta)
    {
    	return this.obtenerReferenciasEntradaImpl(codigoProcedimiento, identificadorTramite,procesada,desde,hasta);
    	
    }
   
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String[] obtenerNumerosEntradas(Long codigoProcedimiento, String identificadorTramite,String procesada,Date desde,Date hasta)
    {
    	 List results = this.obtenerReferenciasEntradaImpl(codigoProcedimiento, identificadorTramite,procesada,desde,hasta);    	
    	 String [] numeros = new String[results.size()];
		 for (int i=0;i<results.size();i++){
			 numeros[i] = ((ReferenciaTramiteBandeja) results.get(i)).getNumeroEntrada();
		 }			 
		 return numeros;	
    	
    }
   
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void procesarEntrada(String numeroEntrada,String procesada) throws ExcepcionBTE{
    	procesarEntrada(numeroEntrada,procesada,null);
    }
    
    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void procesarEntrada(String numeroEntrada,String procesada,String resultadoProcesamiento) throws ExcepcionBTE{
    	Session session = getSession();
        try {
        	if (!procesada.equals(ConstantesBTE.ENTRADA_PROCESADA) &&
        		!procesada.equals(ConstantesBTE.ENTRADA_NO_PROCESADA) &&
        		!procesada.equals(ConstantesBTE.ENTRADA_PROCESADA_ERROR) )
        			throw new HibernateException("Valor no válido para atributo Procesada: " + procesada);          	
       	    
        	// Comprobamos si gestor tiene permiso para cambiar entrada (role proceso automatico si)
        	try{        		
        		if (!this.ctx.isCallerInRole(ROLE_AUTO)){
            		GestorBandeja gestor = null;
        	    	GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
        	    	gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
        	    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
        	    	if (gestor.getPermitirCambioEstado() == 'N') throw new Exception("Gestor no tiene permiso de cambio de estado");
            	}            	            	
        	}catch (Exception he) 
    		{
    	        throw new EJBException(he);
    	    } 
        	
        	
        	
        	Query query = session.createQuery("FROM TramiteBandeja AS m WHERE m.numeroEntrada = :entrada");
            query.setParameter("entrada", numeroEntrada);
            query.setCacheable(false);
            TramiteBandeja t = (TramiteBandeja) query.uniqueResult();
        	t.setProcesada(procesada.charAt(0));
        	
        	// Si el estado es 'No procesada' reseteamos detalle y reseteamos fecha procesamiento
        	if (procesada.equals(ConstantesBTE.ENTRADA_NO_PROCESADA)){
        		resultadoProcesamiento = null;
        		t.setFechaProcesamiento(null);
        		t.setFechaInicioProcesamiento(new Date());
        	}else{
        		// Si marcamos como procesada o con error indicamos la fecha de procesamiento
        		t.setFechaProcesamiento(new Date());
        	}
        	
        	if (resultadoProcesamiento != null && resultadoProcesamiento.length() > 2000) 
        		resultadoProcesamiento = resultadoProcesamiento.substring(0,2000);
        	t.setResultadoProcesamiento(resultadoProcesamiento);
        	
        	session.update(t);
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
    

    /**
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void procesarEntradas(CriteriosBusquedaTramite criteriosBusqueda,String procesada,String resultadoProcesamiento) throws ExcepcionBTE{
    	Session session = getSession();
        try {
        	
        	// Comprobamos estado a establecer        	
        	if (!procesada.equals(ConstantesBTE.ENTRADA_PROCESADA) &&
        		!procesada.equals(ConstantesBTE.ENTRADA_NO_PROCESADA) &&
        		!procesada.equals(ConstantesBTE.ENTRADA_PROCESADA_ERROR) )
        			throw new HibernateException("Valor no válido para atributo Procesada: " + procesada);        	
       	    
        	// Comprobamos si gestor tiene permiso de cambio de estado masivo        	
        	try{    	    
        		if (!this.ctx.isCallerInRole(ROLE_AUTO)){
	        		GestorBandeja gestor = null;
	    	    	GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
	    	    	gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
	    	    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
	    	    	if (gestor.getPermitirCambioEstadoMasivo() == 'N') throw new Exception("Gestor no tiene permiso de cambio de estado masivo");
        		}
        	}catch (Exception he) 
    		{
    	        throw new EJBException(he);
    	    } 
        	
        	// Por seguridad no dejamos hacer un cambio de estado para todos los procedimientos
        	if (StringUtils.isBlank(criteriosBusqueda.getIdentificadorProcedimiento())){
        		throw new EJBException("No se puede realizar un cambio masivo para todos los procedimientos.");
        	}
        	        	
        	Criteria criteria =createCriteriaFromCriteriosBusquedaTramite(criteriosBusqueda,session);
        	List results = criteria.list();
			
			// Cambiamos estado
			for (Iterator it=results.iterator();it.hasNext();){
				TramiteBandeja t = ((TramiteBandeja) it.next());
				t.setProcesada(procesada.charAt(0));
				// Si marcamos como procesada o con error indicamos la fecha de procesamiento        	
				if (!procesada.equals(ConstantesBTE.ENTRADA_NO_PROCESADA)){
					t.setFechaProcesamiento(new Date());
					t.setResultadoProcesamiento(resultadoProcesamiento);
				}else{
					t.setFechaProcesamiento(null);
					t.setResultadoProcesamiento(null);
					t.setFechaInicioProcesamiento(new Date());
				}
				session.update(t);
			}
        	
        } catch (Exception e) {
            throw new EJBException(e);
        } finally {
            close(session);
        }
    }
    
    
    /**
     * Genera Número de Entrada con el siguiente formato: "BTE"/NUMEROENTRADA/AÑO
     * donde: 
     * 	- Año: año actual
     *  - Numero entrada: Secuencia por año
     * @return Numero de entrada
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public String generarNumeroEntrada(){
    	 Session session = getSession();
    	 PreparedStatement pst=null;
         try {
        	// Obtenemos secuencia del año actual
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        	String anyo = sdf.format(new Date());
        	 
         	// Obtenemos secuencia del año actual
        	//String sql = "SELECT BTE_SEQE" + anyo.substring(2) + ".NEXTVAL FROM DUAL";
        	String sql = ((SessionFactoryImpl)session.getSessionFactory()).getDialect().getSequenceNextValString("BTE_SEQE" + anyo.substring(2));
            pst = session.connection().prepareStatement(sql);
        	pst.execute(); 
        	ResultSet rs = pst.getResultSet();
        	rs.next();
        	int num = rs.getInt(1);

        	// Devolvemos numero entrada
        	return "BTE/"+num+"/"+anyo;
         } catch (Exception he) {
        	 throw new EJBException("No se ha podido generar numero de entrada. Verifique que exista secuencia BTE_SEQEyy (yy:Año actual)",he);
         } finally {
        	 try{if(pst != null) pst.close();}catch(Exception ex){}
             close(session);
         }    	
    }
    
    /**
     * Obtiene numero de entradas de un procedimiento con un determinado estado para un rango de fechas (si la fecha desde o fin es nula no la toma en cuenta)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public long obtenerTotalEntradasProcedimiento(String identificadorProcedimiento,String procesada,Date desde,Date hasta) throws ExcepcionBTE{
    	Session session = getSession();
		try 				
		{	
			String select = "SELECT COUNT(m) FROM TramiteBandeja AS m " + 
							" WHERE m.procedimiento.identificador = :identificadorProcedimiento " + 
							" and m.procesada= :procesada "; 
			
			if (desde != null) select+=" and m.fecha >= :desde";
			if (hasta != null) select+=" and m.fecha <= :hasta";
			
			Query query = session.createQuery(select);
			query.setParameter("identificadorProcedimiento",identificadorProcedimiento);
			query.setParameter("procesada",procesada);
			if (desde != null) query.setParameter("desde",desde);
			if (hasta != null) query.setParameter("hasta",hasta);
			
			return Long.parseLong(query.uniqueResult().toString());						 
	    } 
		catch (HibernateException he) 
		{
	        throw new EJBException(he);
	    } 
		finally 
	    {
	        close(session);
	    }
    }    
    
    
    /**
     * Obtiene ids de tramites de un procedimiento con un determinado estado para un rango de fechas (si el estado o la fecha desde o fin es nula no la toma en cuenta)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String[] obtenerIdTramitesProcedimiento(String identificadorProcedimiento,String procesada,Date desde,Date hasta) throws ExcepcionBTE{
    	Session session = getSession();
		try 				
		{	
			String select = "SELECT distinct m.identificadorTramite FROM TramiteBandeja AS m " + 
							" WHERE m.procedimiento.identificador = :identificadorProcedimiento "; 
			
							 
			if (procesada != null) select += " and m.procesada= :procesada "; 
			if (desde != null) select+=" and m.fecha >= :desde";
			if (hasta != null) select+=" and m.fecha <= :hasta";
			
			Query query = session.createQuery(select);
			query.setParameter("identificadorProcedimiento",identificadorProcedimiento);
			if (procesada != null) query.setParameter("procesada",procesada);
			if (desde != null) query.setParameter("desde",desde);
			if (hasta != null) query.setParameter("hasta",hasta);
			
			String [] result = null;
			if (query.list() != null) {
				List listaIds = query.list();
				result = new String[listaIds.size()];
				for (int i = 0; i < listaIds.size(); i++) {
					result[i] = (String) listaIds.get(i);
				}
			}
			
			return result;						 
	    } 
		catch (HibernateException he) 
		{
	        throw new EJBException(he);
	    } 
		finally 
	    {
	        close(session);
	    }
    }    
    
    
    /**
     * Genera linea de exportacion csv de una entrada de la bandeja
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.admin}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String[] exportarCSV(String numEntrada,PropertiesOrdered configuracionExportacion) throws ExcepcionBTE{
    	
    	try{	    
	    	// Obtenemos entrada (con ficheros asociados)
	    	TramiteBTE entrada = DelegateBTEUtil.getBteDelegate().obtenerEntrada(numEntrada);
	    		    	
	    	// A partir del fichero de configuracion de exportacion generamos csv
	    	String columnXPath,xpath,idForm,idInstancia,keyForm;
	    	Map forms = new HashMap();
	    	String[] csv = new String[configuracionExportacion.size()];
	    	HashMapIterable valuesForm;
	    	int i=0;
	    	for (Iterator it = configuracionExportacion.keySet().iterator();it.hasNext();){
	    		columnXPath =	(String) it.next();	    		
	    		
	    		// Verificamos si el XPath corresponde a formulario o a la informacion de la entrada
	    		if (columnXPath.startsWith("FORMULARIO.")) {
	    			csv[i] = extractCSVDocumento("FORMULARIO", entrada, columnXPath, forms);
	    		} else if (columnXPath.startsWith("DOCUMENTO.")) {
	    			csv[i] = extractCSVDocumento("DOCUMENTO", entrada, columnXPath, forms);
	    		} else if (columnXPath.startsWith("PAGO.")){
	    			csv[i] = extractCSVDocumento("PAGO", entrada, columnXPath, forms);
	    		} else if (columnXPath.startsWith("TRAMITE.")) {
	    			// Valor obtenido de la entrada
	    			xpath= columnXPath.substring(("TRAMITE.").length());	
	    			csv[i] = getValorTramite(xpath,entrada);
	    		} else {
	    			throw new Exception("XPath no valido: " + columnXPath);
	    		}
	    			  
	    		i++;
	    	}	    	
	    	return csv;	    	
    	}catch ( Exception ex){
    		throw new ExcepcionBTE("Excepcion generando csv para la entrada " + numEntrada,ex);
    	}
    	
    }

    /**
     * Extrae valor csv de un xpath de documento.
     * @param tipoDocumento
     * @param entrada
     * @param columnXPath
     * @param forms
     * @return csv
     * @throws Exception
     */
	private String extractCSVDocumento(String tipoDocumento, TramiteBTE entrada, String columnXPath,
			Map forms) throws Exception {
		String xpath;
		String idForm;
		String idInstancia;
		String keyForm;
		HashMapIterable valuesForm;
		
		String prefix = tipoDocumento + ".";
		
		idForm = columnXPath.substring(prefix.length(),columnXPath.indexOf(".",prefix.length()));	    			
		idInstancia=columnXPath.substring((prefix+idForm+".").length(),columnXPath.indexOf(".",(prefix+idForm+".").length()));	    				
		xpath= columnXPath.substring((prefix+idForm+"."+idInstancia+".").length());	    			
			
		keyForm = idForm + "-" + idInstancia;
		
		// Parseamos XML Formulario / anexo
		if (!forms.containsKey(keyForm)){
			DocumentoBTE doc = entrada.getDocumento(idForm,Integer.parseInt(idInstancia));
			if (doc != null && doc.getPresentacionTelematica() != null){	  
				if (tipoDocumento.equals("FORMULARIO") || tipoDocumento.equals("PAGO")) {
					Analizador analiza = new Analizador ();
			    	HashMapIterable hti = analiza.analizar (new String(doc.getPresentacionTelematica().getContent(),ConstantesXML.ENCODING));
			    	forms.put(keyForm,hti);	    			    			
				} else if (tipoDocumento.equals("DOCUMENTO")) {
					AnalizadorDoc analizaDoc = new AnalizadorDoc();
					HashMapIterable hti = analizaDoc.analizar (new ByteArrayInputStream(doc.getPresentacionTelematica().getContent()),doc.getPresentacionTelematica().getExtension());
			    	forms.put(keyForm,hti);					
				} else {
	    			throw new Exception("XPath no valido: " + columnXPath);
	    		}			
			}
		}
		
		// Obtenemos valor 
		valuesForm = (HashMapIterable) forms.get(keyForm);
		String csv = "";
		if (valuesForm != null){
			
			Object o = valuesForm.get(referenciaXPath(xpath, tipoDocumento));
			List valores;
			
			if (o != null){
				
				if (o instanceof Nodo){
					Nodo n = (Nodo) o;
					valores = new ArrayList();
					valores.add(n);	    					    						    					
				}else{
					valores = (List) o;	    						
				}
				
				for (int j=0;j<valores.size();j++){
					
					if (j>0) {
						csv += ", ";
					}
					
					Nodo nodo = (Nodo) valores.get(j);
					if (xpath.endsWith(TramiteBandejaFacadeEJB.CODIGO_LISTAS)){
			    		if (nodo.getAtributos() != null && nodo.getAtributos().size() > 0){
			    			for (Iterator it2 = nodo.getAtributos().iterator();it2.hasNext();){
			    				Par atributo = (Par) it2.next();
			    				if (atributo.getNombre().equals(INDICE_LISTAS)){
			    					csv += atributo.getValor();
			    					break;
			    				}
			    			}	
			    		}  	    						
					}else{
						csv += nodo.getValor();
					}
				}
				
			}
		}		
		return csv;
	}    
	
	         
    private String getValorTramite(String xpath,TramiteBTE entrada){
    	
    	if (xpath.equals("NUMEROENTRADA"))
    		return entrada.getNumeroEntrada();    			
		else if (xpath.equals("FECHAENTRADA"))
			return StringUtil.fechaACadena(entrada.getFecha(),StringUtil.FORMATO_TIMESTAMP);			
		else if (xpath.equals("TIPO")){
			switch (entrada.getTipo()){
				case  ConstantesAsientoXML.TIPO_ENVIO: return "ENVIO";
				case  ConstantesAsientoXML.TIPO_PREENVIO: return "PREENVIO";
				case  ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA: return "REGISTRO";
				case  ConstantesAsientoXML.TIPO_PREREGISTRO: return "PREREGISTRO";
				default: return "";
			}
		}else if (xpath.equals("PROCESADA")){
			switch (entrada.getProcesada()){
				case  'S': return "PROCESADA";
				case  'N': return "NO PROCESADA";
				case  'X': return "PROCESADA CON ERROR";				
				default: return "";
			}		
		}else if (xpath.equals("IDENTIFICADORTRAMITE"))
			return entrada.getIdentificadorTramite();			
		else if (xpath.equals("VERSIONTRAMITE"))
			return ""+entrada.getVersionTramite();			
		else if (xpath.equals("NIVELAUTENTICACION")){
			switch (entrada.getNivelAutenticacion()){
				case  'A': return "ANONIMO";
				case  'U': return "USUARIO";
				case  'C': return "CERTIFICADO";				
				default: return "";
			}		
		}else if (xpath.equals("USUARIOSEYCON"))
			return entrada.getUsuarioSeycon();			
		else if (xpath.equals("NUMEROREGISTRO"))
			return entrada.getNumeroRegistro();			
		else if (xpath.equals("FECHAREGISTRO"))
			return StringUtil.fechaACadena(entrada.getFechaRegistro(),StringUtil.FORMATO_TIMESTAMP);			
		else if (xpath.equals("NUMEROPREREGISTRO"))
			return entrada.getNumeroPreregistro();			
		else if (xpath.equals("FECHAPREREGISTRO"))
			return StringUtil.fechaACadena(entrada.getFechaPreregistro(),StringUtil.FORMATO_TIMESTAMP);			
		else if (xpath.equals("USUARIONIF"))
			return entrada.getUsuarioNif();			
		else if (xpath.equals("USUARIONOMBRE"))
			return entrada.getUsuarioNombre();			
		else if (xpath.equals("REPRESENTADONIF"))
			return entrada.getRepresentadoNif();			
		else if (xpath.equals("REPRESENTADONOMBRE"))
			return entrada.getRepresentadoNombre();
		else if (xpath.equals("IDIOMA"))
			return entrada.getIdioma();
		else		
			return "";    	
    }
   
    
    /**
	 * Convierte referencia campo de expresión XPath /instancia/seccion1/campo1 al tipo seccion1.campo1 
	 * @param referenciaCampo
	 * @return
	 */
	protected String referenciaXPath(String referenciaCampo, String tipoDocumento){
		String xpath = "/"+ tipoDocumento + "/"+referenciaCampo.replaceAll("\\.","/");
		if (xpath.endsWith(TramiteBandejaFacadeEJB.CODIGO_LISTAS)) xpath = xpath.substring(0,xpath.indexOf(TramiteBandejaFacadeEJB.CODIGO_LISTAS));
		return xpath;
	}
	
    /*
     * 
     * 
     * SIN PROBAR
     *     
     *     
     *     private Query createQueryFromCriteriosBusquedaTramite(CriteriosBusquedaTramite criteriosBusqueda,Session session) throws Exception{
		// Recuperamos información gestor
    	GestorBandeja gestor = null;
    	try{    	    
	    	GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
	    	gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
	    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
    	}catch (Exception he) 
		{
	        throw new EJBException(he);
	    } 
    	
    	String sql = "FROM TramiteBandeja AS tb WHERE ";
    	
    	List paramNames = new ArrayList();
    	List paramValues= new ArrayList();
    	
    	// Especificamos tramite: debe haber uno!!!
		if ( criteriosBusqueda.getIdentificadorTramite() != null && !criteriosBusqueda.getIdentificadorTramite().equals( "-1" ))
		{
			// Comprobamos que el gestor tenga asociado el trámite
			boolean pertenece=false;
			for (Iterator it=gestor.getTramitesGestionados().iterator();it.hasNext();){
				Tramite tramite = (Tramite) it.next();
				if (tramite.getIdentificador().equals(criteriosBusqueda.getIdentificadorTramite())){
					pertenece = true;break;
				}
			}
			if (!pertenece) throw new Exception("Tramite no pertenece al gestor");	
			
			sql += "tb.tramite.identificador=:identificadorTramite";
			paramNames.add("identificador");
			paramValues.add(criteriosBusqueda.getIdentificadorTramite());				
		}else{
			// Tramites a los que esta asociado el gestor
			throw new Exception("Debe especificarse un identificador de tramite");							
		}
		 
		 // Especificamos nivel autenticacion
		 if ( criteriosBusqueda.getNivelAutenticacion() != CriteriosBusquedaTramite.TODOS )
		 {
			 sql += " AND tb.nivelAutenticacion=:nivelAutenticacion";
			 paramNames.add("nivelAutenticacion");
			 paramValues.add(Character.toString(criteriosBusqueda.getNivelAutenticacion()));
		 }
		 
		 //Especificamos estado procesamiento entrada
		 if ( criteriosBusqueda.getProcesada() != CriteriosBusquedaTramite.TODOS )
		 {
			 sql += " AND tb.procesada=:procesada";
			 paramNames.add("procesada");
			 paramValues.add(Character.toString(criteriosBusqueda.getProcesada()));				 
		 }
		 
		 // Especificamos tipo entrada
		 if ( criteriosBusqueda.getTipo() != CriteriosBusquedaTramite.TODOS )
		 {
			 sql += " AND tb.tipo=:tipo";
			 paramNames.add("tipo");
			 paramValues.add(Character.toString(criteriosBusqueda.getTipo()));						 
		 }
		 
		 // Especificamos nif solicitante
		 if ( !StringUtils.isEmpty( criteriosBusqueda.getUsuarioNif() ) )
		 {
			 sql += " AND tb.usuarioNif=:usuarioNif";
			 paramNames.add("usuarioNif");
			 paramValues.add(criteriosBusqueda.getUsuarioNif());						 
		 }
		 
		 // Especificamos nombre solicitante
		 if ( !StringUtils.isEmpty(criteriosBusqueda.getUsuarioNombre()) )
		 {
			 sql += " AND tb.usuarioNombre like %:usuarioNif%";
			 paramNames.add("usuarioNif");
			 paramValues.add(criteriosBusqueda.getUsuarioNombre());				 
		 }
		
		 // Especificamos fecha
		 if ( criteriosBusqueda.getAnyo() != 0 )
		 {
			 GregorianCalendar gregorianCalendar1 = null;
			 GregorianCalendar gregorianCalendar2 = null;
			 if ( criteriosBusqueda.getMes() == -1 )
			 {
			 	 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 0, 1 );
			 	 gregorianCalendar2 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 11, 31 );
			 }
			 else
			 {
				 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), criteriosBusqueda.getMes(), 1 );
				 int year =  criteriosBusqueda.getAnyo();
				 int month = criteriosBusqueda.getMes();
				 gregorianCalendar2 = new GregorianCalendar( year, month, gregorianCalendar1.getMaximum( GregorianCalendar.DAY_OF_MONTH ) );
			 }
			 
			 sql += " AND tb.fecha between :fechaInicio and :fechaFin";
			 paramNames.add("fechaInicio");
			 paramValues.add(new java.sql.Date(gregorianCalendar1.getTime().getTime()));	
			 paramNames.add("fechaFin");
			 paramValues.add(new java.sql.Date(gregorianCalendar2.getTime().getTime()));				 
		 }
        
		             
		 // Recuperamos trámites seleccionados
		 Query query = session.createQuery(sql);			
		 for (int i=0;i<paramNames.size();i++){
			 query.setParameter((String) paramNames.get(i), paramNames.get(i));				 
		 }
        query.setCacheable(false);		
        return query;
    }
    */
    
    
    private Criteria createCriteriaFromCriteriosBusquedaTramite(CriteriosBusquedaTramite criteriosBusqueda,Session session) throws Exception{
    	
    	// Recuperamos información gestor (en caso de que no sea usuario auto)
    	GestorBandeja gestor = null;
    	if (!this.ctx.isCallerInRole(ROLE_AUTO)){
	    	try{    	    
		    	GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
		    	gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
		    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
	    	}catch (Exception he) 
			{
		        throw new EJBException(he);
		    } 
    	}
    	
    	Criteria criteria = session.createCriteria( TramiteBandeja.class );
    	criteria.setCacheable( false );
				 
		 // Especificamos procedimiento
		 if ( StringUtils.isNotBlank(criteriosBusqueda.getIdentificadorProcedimiento()))
		 {
			 Query query = session.createQuery("FROM Procedimiento AS m WHERE m.identificador = :idProcedimiento");
			 query.setParameter("idProcedimiento", criteriosBusqueda.getIdentificadorProcedimiento());
			 Procedimiento procedimiento = (Procedimiento) query.uniqueResult();
			 criteria.add( Expression.eq("procedimiento",procedimiento ) );
		 }else{
			 // procedimientos a los que esta asociado el gestor
			 if (gestor != null) {
				 criteria.add( Expression.in( "procedimiento",gestor.getProcedimientosGestionados()) );
			 }		 				
		 }
		 // Especificamos tramite
		 if ( StringUtils.isNotBlank(criteriosBusqueda.getIdentificadorTramite()))
		 {
			 criteria.add( Expression.eq("identificadorTramite",criteriosBusqueda.getIdentificadorTramite() ) );
		 }
		 //	 Especificamos número de entrada
		 if ( !StringUtils.isEmpty(criteriosBusqueda.getNumeroEntrada()) )
		 {
			 criteria.add( Expression.eq( "numeroEntrada", criteriosBusqueda.getNumeroEntrada()));
		 }
		 // Especificamos nivel autenticacion
		 if ( criteriosBusqueda.getNivelAutenticacion() != CriteriosBusquedaTramite.TODOS )
		 {
			 criteria.add( Expression.eq( "nivelAutenticacion" , new Character(criteriosBusqueda.getNivelAutenticacion() ) ) );
		 }
		 //Especificamos estado procesamiento entrada
		 if ( criteriosBusqueda.getProcesada() != CriteriosBusquedaTramite.TODOS )
		 {
			 criteria.add( Expression.eq( "procesada" , new Character(criteriosBusqueda.getProcesada() ) ) );
		 }
		 // Especificamos tipo entrada
		 if ( criteriosBusqueda.getTipo() != CriteriosBusquedaTramite.TODOS )
		 {
			 criteria.add( Expression.eq( "tipo" , new Character(criteriosBusqueda.getTipo() ) ) );
		 }
		 // Especificamos nif solicitante
		 if ( !StringUtils.isEmpty( criteriosBusqueda.getUsuarioNif() ) )
		 {
			 criteria.add( Expression.eq( "usuarioNif", criteriosBusqueda.getUsuarioNif() ) );
		 }
		 // Especificamos nombre solicitante
		 if ( !StringUtils.isEmpty(criteriosBusqueda.getUsuarioNombre()) )
		 {
			 criteria.add( Expression.ilike( "usuarioNombre", "%" + criteriosBusqueda.getUsuarioNombre() + "%"  ));
		 }
		 // Especificamos año / mes
		 if ( criteriosBusqueda.getAnyo() != 0 )
		 {
			 GregorianCalendar gregorianCalendar1 = null;
			 GregorianCalendar gregorianCalendar2 = null;
			 if ( criteriosBusqueda.getMes() == -1 )
			 {
			 	 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 0, 1 );
			 	 gregorianCalendar2 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 11, 31 );
			 }
			 else
			 {
				 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), criteriosBusqueda.getMes(), 1 );
				 int year =  criteriosBusqueda.getAnyo();
				 int month = criteriosBusqueda.getMes();
				 gregorianCalendar2 = new GregorianCalendar( year, month, gregorianCalendar1.getMaximum( GregorianCalendar.DAY_OF_MONTH ) );				 
			 }
			 criteria.add( Expression.between( "fecha", new java.sql.Date(gregorianCalendar1.getTime().getTime()), new java.sql.Date( DataUtil.obtenerUltimaHora(gregorianCalendar2.getTime()).getTime() )) );			
		 }
		 
		// Especificamos fecha inicio / fecha fin
		 if ( criteriosBusqueda.getFechaEntradaMinimo() != null) {
			 criteria.add( Expression.gt("fecha", criteriosBusqueda.getFechaEntradaMinimo()) );
		 }
		 if ( criteriosBusqueda.getFechaEntradaMaximo() != null) {
			 criteria.add( Expression.lt("fecha", criteriosBusqueda.getFechaEntradaMaximo()) );
		 }		
		 
		// Especificamos fecha maxima inicio procesamiento 
		  if ( criteriosBusqueda.getFechaInicioProcesamientoMaximo() != null) {
			 criteria.add( Expression.lt("fechaInicioProcesamiento", criteriosBusqueda.getFechaInicioProcesamientoMaximo()) );
		 }		
		
		 // Ordenación
		 criteria.addOrder( Order.desc("fecha") );
		 return criteria;
    }    
    
    private int countFromCriteriosBusquedaTramite(CriteriosBusquedaTramite criteriosBusqueda,Session session) throws Exception{
    	
    	// Recuperamos información gestor (en caso de que no sea usuario auto)
    	GestorBandeja gestor = null;
    	if (!this.ctx.isCallerInRole(ROLE_AUTO)){
	    	try{    	    
		    	GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();
		    	gestor = gd.obtenerGestorBandeja(this.ctx.getCallerPrincipal().getName());
		    	if (gestor == null) throw new Exception("No se encuentra gestor para usuario seycon " + this.ctx.getCallerPrincipal().getName());
		    	if (gestor.getProcedimientosGestionados() == null || gestor.getProcedimientosGestionados().size() == 0) {
		    		return 0;
		    	}
	    	}catch (Exception he) 
			{
		        throw new EJBException(he);
		    } 
    	}

    	List params = new ArrayList(); 
    	StringBuffer sb = new StringBuffer(4000);
    	sb.append("select count(t) from TramiteBandeja t where ") ; 
    			 
		 // Especificamos procedimiento
		 if ( StringUtils.isNotBlank(criteriosBusqueda.getIdentificadorProcedimiento()))
		 {
			 Query query = session.createQuery("FROM Procedimiento AS m WHERE m.identificador = :idProcedimiento");
			 query.setParameter("idProcedimiento", criteriosBusqueda.getIdentificadorProcedimiento());
			 Procedimiento procedimiento = (Procedimiento) query.uniqueResult();
			 sb.append(" t.procedimiento = ? and ");			 
			 params.add(procedimiento);	
		 }else{
			 // procedimientos a los que esta asociado el gestor
			 if (gestor != null) {
				 sb.append("( ");
				 boolean primer = true;
				 if (gestor.getProcedimientosGestionados() != null) {
					 for (Iterator it = gestor.getProcedimientosGestionados().iterator(); it.hasNext();) {
						Procedimiento p =  (Procedimiento) it.next();
						 if (primer) {
							 primer = false;
						 } else {
							 sb.append(" or ");
						 }
						 sb.append("  t.procedimiento.identificador = ? ");
						 params.add(p.getIdentificador());
					 }
				 }
				 sb.append(") and ");						
			 }		 				
		 }
		 // Especificamos tramite
		 if ( StringUtils.isNotBlank(criteriosBusqueda.getIdentificadorTramite()))
		 {
			 sb.append(" t.identificadorTramite = ? and ");
			 params.add(criteriosBusqueda.getIdentificadorTramite());			 
		 }
		 //	 Especificamos número de entrada
		 if ( !StringUtils.isEmpty(criteriosBusqueda.getNumeroEntrada()) )
		 {
			 sb.append(" t.numeroEntrada = ? and ");
			 params.add(criteriosBusqueda.getNumeroEntrada());					 
		 }
		 // Especificamos nivel autenticacion
		 if ( criteriosBusqueda.getNivelAutenticacion() != CriteriosBusquedaTramite.TODOS )
		 {
			 sb.append(" t.nivelAutenticacion = ? and ");
			 params.add(new Character(criteriosBusqueda.getNivelAutenticacion()));			 
		 }
		 //Especificamos estado procesamiento entrada
		 if ( criteriosBusqueda.getProcesada() != CriteriosBusquedaTramite.TODOS )
		 {
			 sb.append(" t.procesada = ? and ");
			 params.add(new Character(criteriosBusqueda.getProcesada() ) );				 
		 }
		 // Especificamos tipo entrada
		 if ( criteriosBusqueda.getTipo() != CriteriosBusquedaTramite.TODOS )
		 {
			 sb.append(" t.tipo = ? and ");
			 params.add(new Character(criteriosBusqueda.getTipo()) );			
		 }
		 // Especificamos nif solicitante
		 if ( !StringUtils.isEmpty( criteriosBusqueda.getUsuarioNif() ) )
		 {
			 sb.append(" t.usuarioNif = ? and ");
			 params.add(criteriosBusqueda.getUsuarioNif());				 
		 }
		 // Especificamos nombre solicitante
		 if ( !StringUtils.isEmpty(criteriosBusqueda.getUsuarioNombre()) )
		 {
			 sb.append(" t.usuarioNombre like ? and ");
			 params.add("%" + criteriosBusqueda.getUsuarioNombre() + "%");					 
		 }
		 // Especificamos año / mes
		 if ( criteriosBusqueda.getAnyo() != 0 )
		 {
			 GregorianCalendar gregorianCalendar1 = null;
			 GregorianCalendar gregorianCalendar2 = null;
			 if ( criteriosBusqueda.getMes() == -1 )
			 {
			 	 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 0, 1 );
			 	 gregorianCalendar2 = new GregorianCalendar( criteriosBusqueda.getAnyo(), 11, 31 );
			 }
			 else
			 {
				 gregorianCalendar1 = new GregorianCalendar( criteriosBusqueda.getAnyo(), criteriosBusqueda.getMes(), 1 );
				 int year =  criteriosBusqueda.getAnyo();
				 int month = criteriosBusqueda.getMes();
				 gregorianCalendar2 = new GregorianCalendar( year, month, gregorianCalendar1.getMaximum( GregorianCalendar.DAY_OF_MONTH ) );				 
			 }
			 sb.append(" (t.fecha >= ? and t.fecha <= ? ) and ");
			 params.add(new java.sql.Date(gregorianCalendar1.getTime().getTime()));
			 params.add(new java.sql.Date( DataUtil.obtenerUltimaHora(gregorianCalendar2.getTime()).getTime() ));			 		
		 }
		 
		// Especificamos fecha inicio / fecha fin
		 if ( criteriosBusqueda.getFechaEntradaMinimo() != null) {
			 sb.append(" t.fecha > ? and ");
			 params.add(criteriosBusqueda.getFechaEntradaMinimo());			 
		 }
		 if ( criteriosBusqueda.getFechaEntradaMaximo() != null) {
			 sb.append(" t.fecha < ? and ");
			 params.add(criteriosBusqueda.getFechaEntradaMaximo());			 
		 }		
		 
		 // Especificamos fecha maxima inicio procesamiento 
		  if ( criteriosBusqueda.getFechaInicioProcesamientoMaximo() != null) {
			 sb.append(" t.fechaInicioProcesamiento < ? and ");
			 params.add(criteriosBusqueda.getFechaInicioProcesamientoMaximo());	
		 }		
		  
		  // Cerramos sentencia
		  sb.append(" t.codigo = t.codigo ");
		
		 
		 // Ejecutamos count
		 String sqlCount = sb.toString();
		 Query query = session.createQuery(sqlCount);		 
		 for (int i=0; i < params.size(); i++) {
			 query.setParameter(i, params.get(i));			 
		 }
		 Integer total = (Integer) query.uniqueResult();
		
		 return total.intValue();
    }    
    
    
    private List obtenerReferenciasEntradaImpl(Long codigoProcedimiento,String identificadorTramite,String procesada, Date desde,Date hasta){
    	
		Session session = getSession();
		boolean desdeBool = false;
		boolean hastaBool = false;
		boolean procesadaBool = false;
		List  numeros = new  ArrayList();
        try {
        	String sql = "Select {TB.*} FROM BTE_TRAMIT {TB} WHERE {TB}.TRA_IDETRA = :idtramite AND {TB}.TRA_CODPRO = :codigoProcedimiento";

        	//Especificamos estado procesamiento entrada
        	if ( !StringUtils.isEmpty(procesada) ){
        		sql = sql + " and {TB}.TRA_PROCES = :procesada ";
        		procesadaBool = true;
        	}
        	
        	//Especificamos fechas
        	if ( desde != null && hasta != null ){
        		sql = sql + " and {TB}.TRA_FECHA between :desde and :hasta ";
        		desdeBool = true;
        		hastaBool = true;
   			}
   			if ( desde != null && hasta == null ){
   				sql = sql + " and {TB}.TRA_FECHA >= :desde ";
   				desdeBool = true;
   			}
   			if ( desde == null && hasta != null ){
   				sql = sql + " and {TB}.TRA_FECHA <= :hasta ";
   				hastaBool = true;
			}
        		
        	//Ordenación
   			sql = sql + " order by {TB}.TRA_FECHA desc";
        	//"Select {TB.*} FROM BTE_TRAMIT {TB} WHERE {TB}.TRA_IDETRA = :idtramite and {TB}.TRA_PROCES = :procesada and {TB}.TRA_FECHA >= :desde and {TB}.TRA_FECHA <= :hasta order by {TB}.TRA_FECHA desc"
        	Query query = session.createSQLQuery(sql,"TB",TramiteBandeja.class );
            query.setParameter("codigoProcedimiento", codigoProcedimiento);
            query.setParameter("idtramite", identificadorTramite);
            if(procesadaBool){
            	query.setParameter("procesada", procesada);
            }
            if(desdeBool){
            	query.setParameter("desde", desde);
            }
            if(hastaBool){
            	query.setParameter("hasta", hasta);
            }
            List result = query.list();
            if (result.isEmpty()) {
                return numeros;
            }
            
             // Devolvemos  referencias entradas			 
			 for (int i=0;i<result.size();i++){
				 TramiteBandeja t = (TramiteBandeja) result.get(i);
				 ReferenciaTramiteBandeja r = new ReferenciaTramiteBandeja();
				 r.setNumeroEntrada(t.getNumeroEntrada());
				 r.setClaveAcceso(t.getClaveAcceso());
				 numeros.add(r);
			 }			 
			 return numeros;
        } catch (HibernateException he) {
            throw new EJBException(he);
        } finally {
            close(session);
        }
    }
}
