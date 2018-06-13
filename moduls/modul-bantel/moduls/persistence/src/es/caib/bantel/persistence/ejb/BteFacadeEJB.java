package es.caib.bantel.persistence.ejb;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import es.caib.bantel.model.DocumentoBandeja;
import es.caib.bantel.model.GestorBandeja;
import es.caib.bantel.model.ReferenciaTramiteBandeja;
import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TramiteBandeja;
import es.caib.bantel.modelInterfaz.DatosDocumentoPresencial;
import es.caib.bantel.modelInterfaz.DatosDocumentoTelematico;
import es.caib.bantel.modelInterfaz.DocumentoBTE;
import es.caib.bantel.modelInterfaz.ExcepcionBTE;
import es.caib.bantel.modelInterfaz.ReferenciaEntradaBTE;
import es.caib.bantel.modelInterfaz.TramiteBTE;
import es.caib.bantel.persistence.delegate.DelegateException;
import es.caib.bantel.persistence.delegate.DelegateUtil;
import es.caib.bantel.persistence.delegate.GestorBandejaDelegate;
import es.caib.bantel.persistence.delegate.TramiteBandejaDelegate;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.persistence.delegate.DelegateRDSUtil;
import es.caib.redose.persistence.delegate.RdsDelegate;

//TODO: Referenciar localmente a los ejbs
/**
 * SessionBean que implementa la interfaz de la BTE para la integración con BackOffices
 *
 * @ejb.bean
 *  name="bantel/persistence/BteFacade"
 *  jndi-name="es.caib.bantel.persistence.BteFacade"
 *  type="Stateless"
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.env-entry name="role.auto" type="java.lang.String" value="${role.auto}"
 * 
 * @ejb.transaction type="Required"
 * 
 * @ejb.security-role-ref role-name="${role.auto}" role-link="${role.auto}"
 */
public abstract class BteFacadeEJB implements SessionBean  {

	private SessionContext context;
	private String roleAuto;
	
	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException{
		this.context = ctx;
	}
	
	/**
     * @ejb.create-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
	public void ejbCreate() throws CreateException {	
		try
		{
			javax.naming.InitialContext initialContext = new javax.naming.InitialContext();
			roleAuto = ( String ) initialContext.lookup( "java:comp/env/role.auto" );
		}
		catch( Exception exc )
		{
			exc.printStackTrace();
		}
	}
	
	
	
	
    // --------------------------------------------------------------------
    // INTERFAZ PARA INTEGRACIÓN CON BACKOFFICES
	// --------------------------------------------------------------------	

    /**
     * Obtiene números de entradas de la BTE pendientes de procesar
     * 
     * @param identificadorProcedimiento Identificador de procedimiento a consultar
     * @param identificadorTramite Identificador de trámite a consultar
     * @return Números de entradas
     * @throws ExcepcionBTE
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String[] obtenerNumerosEntradas(Long codigoProcedimiento, String identificadorTramite) throws ExcepcionBTE{
    	return obtenerNumerosEntradas(codigoProcedimiento, identificadorTramite,"N",null,null); 
    }
       
    /**
     * Obtiene números de entradas de la BTE pendientes de procesar
     * 
     * @param identificadorProcedimiento Identificador de procedimiento a consultar
     * @param identificadorTramite Identificador de trámite a consultar
     * @return Números de entradas
     * @throws ExcepcionBTE
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public ReferenciaEntradaBTE[] obtenerReferenciasEntradas(String identificadorProcedimiento, String identificadorTramite,String procesada,Date desde,Date hasta) throws ExcepcionBTE{
    	
    	// Comprobamos que el usuario tiene acceso al tramite
    	Procedimiento procedimiento = null;
    	try{
	    	 procedimiento = DelegateUtil.getTramiteDelegate().obtenerProcedimiento(identificadorProcedimiento);
	    	 if (procedimiento == null) {
	    		 throw new ExcepcionBTE("No existe procedimiento con identificador: " + identificadorProcedimiento);
	    	 }
    	}catch (Exception ex){
     		throw new ExcepcionBTE("No se ha podido recuperar los números de entrada",ex);
     	}	 
    	
    	 // Comprobamos que el usuario tiene acceso al tramite
    	if (!this.context.isCallerInRole(roleAuto)){
	    	 String usu = this.context.getCallerPrincipal().getName();
	    	 boolean permitido = false;
	    	 for (Iterator it = procedimiento.getGestores().iterator();it.hasNext();){
	    		 GestorBandeja g = (GestorBandeja) it.next();
	    		 if (g.getSeyconID().equals(usu)) {
	    			 permitido = true;
	    			 break;
	    		 }
	    	 }    	
	    	 if (!permitido) {
	    		 throw new ExcepcionBTE("Usuario " + usu + " no tiene acceso al tramite con identificador: " + identificadorTramite);
	    	 }
    	}
    	
    	// Devolvemos referencias a entradas    
     	try{
     		TramiteBandejaDelegate td = DelegateUtil.getTramiteBandejaDelegate();  
     		List refs = td.obtenerReferenciasEntradas(procedimiento.getCodigo(),identificadorTramite,procesada,desde,hasta);
     		ReferenciaEntradaBTE[] result = new ReferenciaEntradaBTE[refs.size()] ;
     		int i=0;	
     		for (Iterator it=refs.iterator();it.hasNext();){
     			ReferenciaTramiteBandeja ref = (ReferenciaTramiteBandeja) it.next();
     			result[i] = new ReferenciaEntradaBTE();
     			result[i].setNumeroEntrada(ref.getNumeroEntrada());
     			result[i].setClaveAcceso(ref.getClaveAcceso());
     			i++;
     		}    
     		
     		return result;
     		
     	}catch (Exception ex){
     		throw new ExcepcionBTE("No se ha podido recuperar los números de entrada",ex);
     	}
    }
    
    
    /**
     * Obtiene números de entradas de la BTE
     * @param codigoProcedimiento Identificador de procedimiento a consultar (obligatorio)
     * @param identificadorTramite Identificador de trámite a consultar (obligatorio)
     * @param procesada	Indica si recupera entradas procesadas / no procesadas / con error ( "S" / "N" / "X"). Si este parametro es nulo recupera todas las entradas. 
     * @param desde Permite establecer la fecha inicial a partir de la cual se recuperarán las entradas. Si este parametro es nulo no se toma en cuenta.
     * @param hasta Permite establecer la fecha final hasta la cual se recuperarán las entradas. Si este parametro es nulo no se toma en cuenta.
     * @return Números de entradas
     * @throws ExcepcionBTE
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public String[] obtenerNumerosEntradas(Long codigoProcedimiento, String identificadorTramite,String procesada,Date desde,Date hasta) throws ExcepcionBTE{
    	
    	// Obtenemos información del trámite para establecer validación de acceso
    	// TODO Verificar comprobación acceso desde BackOffices: comprobación de rol o comprobación de usuario seycon
    	
    	// Obtenemos números entradas    	
    	try{
    		TramiteBandejaDelegate td = DelegateUtil.getTramiteBandejaDelegate();        	        	
    		return td.obtenerNumerosEntradas(codigoProcedimiento, identificadorTramite,procesada,desde,hasta);
    	}catch (Exception ex){
    		throw new ExcepcionBTE("No se ha podido recuperar los números de entrada",ex);
    	}
    }
    
    /**     
     * Obtiene entrada de la BTE comprobando la clave de acceso
     * @param numeroEntrada Número de entrada 
     * @return Información entrada
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public TramiteBTE obtenerEntrada(ReferenciaEntradaBTE refEntrada) throws ExcepcionBTE{
    	return obtenerEntradaImpl(refEntrada.getNumeroEntrada(),refEntrada.getClaveAcceso(),true);
    }
    
    
    /**     
     * Obtiene entrada de la BTE
     * @param numeroEntrada Número de entrada 
     * @return Información entrada
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public TramiteBTE obtenerEntrada(String numeroEntrada) throws ExcepcionBTE{
    	   return obtenerEntradaImpl(numeroEntrada,null,false);
    }
        
    
    /**     
     * Establece el flag de procesada en entrada de la BTE
     * @param numeroEntrada Número de entrada 
     * @param procesada Flag de procesada: "S" Procesada / "N" Pendiente procesar / "X" Procesada con error
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void procesarEntrada(String numeroEntrada,String procesada) throws ExcepcionBTE{    	
    	procesarEntrada(numeroEntrada,procesada,null);         
    }
    
    /**     
     * Establece el flag de procesada en entrada de la BTE
     * @param numeroEntrada Número de entrada 
     * @param procesada Flag de procesada: "S" Procesada / "N" Pendiente procesar
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void procesarEntrada(String numeroEntrada,String procesada,String resultadoProcesamiento) throws ExcepcionBTE{    	
    	try{
    		TramiteBandejaDelegate td = DelegateUtil.getTramiteBandejaDelegate();        	        	
    		td.procesarEntrada(numeroEntrada,procesada,resultadoProcesamiento); 
    	}catch (Exception ex){
    		throw new ExcepcionBTE("No se ha podido establecer flag de procesada para entrada : " + numeroEntrada,ex);
    	}             
    }
    
    /**     
     * Establece el flag de procesada en entrada de la BTE
     * @param numeroEntrada Número de entrada 
     * @param procesada Flag de procesada: "S" Procesada / "N" Pendiente procesar
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.gestor}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void procesarEntrada(ReferenciaEntradaBTE referenciaEntrada,String procesada,String resultadoProcesamiento) throws ExcepcionBTE{    	
    	try{
    		TramiteBandejaDelegate td = DelegateUtil.getTramiteBandejaDelegate();    
    		
    		// Intentamos obtener la entrada con la clave de acceso
    		this.obtenerEntradaImpl(referenciaEntrada.getNumeroEntrada(),referenciaEntrada.getClaveAcceso(),true);
    		
    		td.procesarEntrada(referenciaEntrada.getNumeroEntrada(),procesada,resultadoProcesamiento); 
    	}catch (Exception ex){
    		throw new ExcepcionBTE("No se ha podido establecer flag de procesada para entrada : " + referenciaEntrada.getNumeroEntrada(),ex);
    	}             
    }
    
        
     /**
     * 
     * @param t Entrada recuperada de las tablas de BTE
     * @param recuperarRDS Indica si a los documentos con presentación telemática se le añade la información del RDS
     * @return
     * @throws ExcepcionBTE
     */
    private TramiteBTE tramiteBandejaToTramiteBTE(TramiteBandeja t, boolean recuperarRDS) throws ExcepcionBTE{    	
    	try{
    		RdsDelegate rds = DelegateRDSUtil.getRdsDelegate();
    		
	    	TramiteBTE tramBte = new TramiteBTE();	  
	    	tramBte.setNumeroEntrada(t.getNumeroEntrada());
	    	tramBte.setClaveAcceso(t.getClaveAcceso());
	    	tramBte.setCodigoEntrada(t.getCodigo());
	    	tramBte.setUnidadAdministrativa(t.getUnidadAdministrativa());
	    	tramBte.setFecha(t.getFecha());
	    	tramBte.setTipo(t.getTipo());
	    	tramBte.setProcesada(t.getProcesada());
	    	tramBte.setIdioma(t.getIdioma());
	    	tramBte.setIdentificadorProcedimiento(t.getProcedimiento().getIdentificador());
	    	tramBte.setIdentificadorTramite(t.getIdentificadorTramite());
	    	tramBte.setVersionTramite(t.getVersionTramite());
	    	tramBte.setNivelAutenticacion(t.getNivelAutenticacion());
	    	tramBte.setUsuarioSeycon(t.getUsuarioSeycon());
	    	tramBte.setDescripcionTramite(t.getDescripcionTramite());
	    	tramBte.setRepresentadoNif(t.getRepresentadoNif());
	    	tramBte.setRepresentadoNombre(t.getRepresentadoNombre());	
	    	tramBte.setDelegadoNif(t.getDelegadoNif());
	    	tramBte.setDelegadoNombre(t.getDelegadoNombre());
	    	tramBte.setCodigoReferenciaRDSAsiento(t.getCodigoRdsAsiento().longValue());
	    	tramBte.setClaveReferenciaRDSAsiento(t.getClaveRdsAsiento());	    	
	    	
	    	DocumentoRDS docRDSAsiento = rds.consultarDocumento(new ReferenciaRDS(t.getCodigoRdsAsiento().longValue(),t.getClaveRdsAsiento()),false);	    				
	    	tramBte.setReferenciaGestorDocumentalAsiento(docRDSAsiento.getReferenciaGestorDocumental());
	    	tramBte.setCodigoDocumentoCustodiaAsiento(docRDSAsiento.getCodigoDocumentoCustodia());
	    	
	    	tramBte.setCodigoReferenciaRDSJustificante(t.getCodigoRdsJustificante().longValue());
	    	tramBte.setClaveReferenciaRDSJustificante(t.getClaveRdsJustificante());	 
	    	
	    	DocumentoRDS docRDSJustificante = rds.consultarDocumento(new ReferenciaRDS(t.getCodigoRdsJustificante().longValue(),t.getClaveRdsJustificante()),false);	    				
	    	tramBte.setReferenciaGestorDocumentalJustificante(docRDSJustificante.getReferenciaGestorDocumental());
	    	tramBte.setCodigoDocumentoCustodiaJustificante(docRDSJustificante.getCodigoDocumentoCustodia());	    	
	    	
	    	tramBte.setNumeroRegistro(t.getNumeroRegistro());	    
	    	tramBte.setFechaRegistro(t.getFechaRegistro());
	    	tramBte.setNumeroPreregistro(t.getNumeroPreregistro());
	    	tramBte.setFechaPreregistro(t.getFechaPreregistro());	    	
	    	tramBte.setUsuarioNif(t.getUsuarioNif());
	    	tramBte.setUsuarioNombre(t.getUsuarioNombre());
	    	tramBte.setTipoConfirmacionPreregistro(t.getTipoConfirmacionPreregistro());
	    	tramBte.setHabilitarAvisos(t.getHabilitarAvisos());
	    	tramBte.setAvisoEmail(t.getAvisoEmail());
	    	tramBte.setAvisoSMS(t.getAvisoSMS());
	    	tramBte.setHabilitarNotificacionTelematica(t.getHabilitarNotificacionTelematica());
	    	tramBte.setFirmadaDigitalmente(t.getFirmada() == 'S');
	    	tramBte.setSubsanacionExpedienteCodigo(t.getSubsanacionExpedienteId());
	    	tramBte.setSubsanacionExpedienteUnidadAdministrativa(t.getSubsanacionExpedienteUA());
	    	
	    	for (Iterator it = t.getDocumentos().iterator();it.hasNext();){    		
	    		DocumentoBandeja d = (DocumentoBandeja) it.next();
	    		
	    		DocumentoBTE dbantel = new DocumentoBTE();	    		
	    		dbantel.setNombre(d.getNombre());
	    		
	    		// Datos telemáticos
	    		if (d.getRdsCodigo() != null){
	    			DatosDocumentoTelematico fic = new DatosDocumentoTelematico();
	    			fic.setIdentificador(d.getIdentificador());
	    			fic.setNumeroInstancia(d.getNumeroInstancia());	    			    	
		    		ReferenciaRDS ref = new ReferenciaRDS();
	    			ref.setCodigo(d.getRdsCodigo().longValue());
	    			ref.setClave(d.getRdsClave());	    			
	    			fic.setCodigoReferenciaRds(ref.getCodigo());
	    			fic.setClaveReferenciaRds(ref.getClave());
	    				    			
	    			// Recuperamos información del RDS
	    			if (recuperarRDS){
	    				DocumentoRDS docRDS = rds.consultarDocumento(ref);	    				
	    				fic.setNombre(docRDS.getNombreFichero());
	    				fic.setExtension(docRDS.getExtensionFichero());
	    				fic.setEstructurado(docRDS.isEstructurado());
	    				fic.setContent(docRDS.getDatosFichero());
	    				fic.setFirmas(docRDS.getFirmas());	    
	    				fic.setReferenciaGestorDocumental(docRDS.getReferenciaGestorDocumental());
	    				fic.setCodigoDocumentoCustodia(docRDS.getCodigoDocumentoCustodia());
	    			}
	    			
	    			dbantel.setPresentacionTelematica(fic);
	    		}
	    		
	    		// Datos presenciales
	    		if (d.getPresencial().charAt(0) == 'S'){
	    			DatosDocumentoPresencial presencial = new DatosDocumentoPresencial();
	    			presencial.setTipoDocumento(d.getTipoDocumento().charAt(0));
	    			presencial.setCompulsarDocumento(d.getCompulsarDocumento().charAt(0));
	    			presencial.setFotocopia(d.getFotocopia().charAt(0));
	    			presencial.setFirma(d.getFirma().charAt(0));
	    			presencial.setIdentificador(d.getIdentificador());
	    			presencial.setNumeroInstancia(d.getNumeroInstancia());
	    			dbantel.setPresentacionPresencial(presencial);
	    		}
	    		    			
	    		tramBte.getDocumentos().add(dbantel);
	    	}    	
	    	return tramBte;    	
    	}catch (Exception e){
    		throw new ExcepcionBTE("No se ha podido convertir TramiteBandeja a TramiteBTE",e);
    	}
    }
   
    
    /**
     * Obtiene entrada bandeja con check de si tiene que comprobar la clave de acceso
     * 
     * @param numEntrada
     * @param claveEntrada
     * @param comprobarEntrada
     * @return
     * @throws ExcepcionBTE
     */
    private TramiteBTE obtenerEntradaImpl(String numeroEntrada,String claveEntrada,boolean comprobarEntrada) throws ExcepcionBTE{
    	//  Cargamos tramitePersistente      	
    	TramiteBandeja tramite;
    	try{
    		TramiteBandejaDelegate td = DelegateUtil.getTramiteBandejaDelegate();        	        	
    		tramite = td.obtenerTramiteBandeja(numeroEntrada);    		
    	}catch (Exception ex){
    		throw new ExcepcionBTE("No se ha podido recuperar entrada : " + numeroEntrada,ex);
    	}
        	
    	// Si no existe entrada devolvemos nulo
    	if (tramite == null) return null;
    	
    	// Comprobamos si debemos checkear la clave de acceso
    	if (comprobarEntrada){
    		if (claveEntrada != null && !claveEntrada.equals(tramite.getClaveAcceso())){
    			throw new ExcepcionBTE("No concuerda la clave de acceso a la entrada");
    		}
    		if (claveEntrada == null && tramite.getClaveAcceso() != null){
    			throw new ExcepcionBTE("No concuerda la clave de acceso a la entrada");
    		}
    	}
    	
    	// Comprobamos que gestor tenga acceso al procedimiento al que pertenece el tramite (excepto para auto)
    	if (!this.context.isCallerInRole(roleAuto)){
    		try{
	    		GestorBandejaDelegate gd = DelegateUtil.getGestorBandejaDelegate();    		
	    		GestorBandeja gestor = gd.obtenerGestorBandeja(this.context.getCallerPrincipal().getName());
	    		if (gestor == null){
	    			throw new ExcepcionBTE("Gestor " + this.context.getCallerPrincipal().getName() + " no esta registrado");
	    		}
				boolean pertenece=false;
				if (gestor.getProcedimientosGestionados() != null) {
					for (Iterator it=gestor.getProcedimientosGestionados().iterator();it.hasNext();){
						Procedimiento procedimiento = (Procedimiento) it.next();
						if (procedimiento.getIdentificador().equals(tramite.getProcedimiento().getIdentificador())){
							pertenece = true;
							break;
						}
					}
				}
				if (!pertenece) throw new ExcepcionBTE("Gestor " + this.context.getCallerPrincipal().getName() + " no tiene acceso al procedimiento " + tramite.getProcedimiento().getIdentificador() );	    		    	
    		}catch(DelegateException dex){
    			throw new ExcepcionBTE("No se puede comprobar el acceso para gestor " + this.context.getCallerPrincipal().getName(),dex);
    		}
    	}
    	
        // Pasamos a TramitePersistenteBTE recuperando los ficheros asociados del RDS
    	return tramiteBandejaToTramiteBTE(tramite,true);        	     
    }
    
    
   
}
