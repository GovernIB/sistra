package es.caib.redose.persistence.ejb;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import es.caib.redose.model.Documento;
import es.caib.redose.model.Firma;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.model.TipoOperacion;
import es.caib.redose.model.TipoUso;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.model.Uso;
import es.caib.redose.model.Version;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.DocumentoVerifier;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.KeyVerifier;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.PlantillaDelegate;
import es.caib.redose.persistence.delegate.UbicacionDelegate;
import es.caib.redose.persistence.delegate.VersionDelegate;
import es.caib.redose.persistence.formateadores.FormateadorDocumento;
import es.caib.redose.persistence.formateadores.FormateadorDocumentoFactory;
import es.caib.redose.persistence.plugin.PluginAlmacenamientoRDS;
import es.caib.redose.persistence.plugin.PluginClassCache;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;

import es.caib.util.StringUtil;
import es.indra.util.pdf.BarcodeStamp;
import es.indra.util.pdf.ObjectStamp;
import es.indra.util.pdf.SelloEntradaStamp;
import es.indra.util.pdf.TextoStamp;
import es.indra.util.pdf.UtilPDF;

/**
 * SessionBean que implementa la interfaz del RDS para los demás
 * módulos de la Plataforma Telemática.
 *
 * @ejb.bean
 *  name="redose/persistence/RdsFacade"
 *  jndi-name="es.caib.redose.persistence.RdsFacade"
 *  type="Stateless"
 *  view-type="remote" *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * 
 * 
 * 
 * TODO: Hay que implementar acceso local a los EJBs
 *
 */
public abstract class RdsFacadeEJB extends HibernateEJB {

	private final static String LISTAR_USOS = "LIUS";
	private final static String NUEVO_DOCUMENTO = "NUDO";
	private final static String ELIMININAR_USOS = "ELUO";
	private final static String ELIMINAR_USO = "ELUS";
	private final static String NUEVO_USO = "NUUS";
	private final static String CONSULTAR_DOCUMENTO_FORMATEADO = "CODF";
	private final static String CONSULTAR_DOCUMENTO = "CODO";
	private final static String ACTUALIZAR_DOCUMENTO = "ACDO";
	private final static String ASOCIAR_FIRMA = "AFDO";
	private final static String ACTUALIZAR_FICHERO = "ACFI";
	private final static String BORRADO_AUTOMATICO_DOCUMENTO_SIN_USOS = "BODO"; // Al eliminar ultimo uso del documento
	
	
	private String URL_VERIFIER = null;
	private String TEXT_VERIFIER = null;
	private String ENTORNO = null;
	
	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();
		
		// Obtenemos configuracion
		try{
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			URL_VERIFIER = props.getProperty("sistra.url") + "/redosefront/init.do?id=";
			TEXT_VERIFIER=props.getProperty("verifier.text");
			ENTORNO =props.getProperty("entorno");		
		}catch(Exception ex){
			log.error("No se pueden acceder propiedades modulo",ex);
			throw new CreateException("No se pueden obtener propiedades modulo");
		}
		
	}
	
	/**
	 * Inserta un documento en el RDS
	 * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public ReferenciaRDS insertarDocumento(DocumentoRDS documento) throws ExcepcionRDS{    	
    	//TODO: Faltaría proceso background que borre documentos sin usos
    	// Realizamos operación
    	ReferenciaRDS ref = this.grabarDocumento(documento,true);
    	// Realizamos log    	
    	this.doLogOperacion(getUsuario(),NUEVO_DOCUMENTO,"inserción documento " + ref.getCodigo());
    	return ref;
    }
	    
    /**
     * Actualiza un documento en el RDS
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public void actualizarDocumento(DocumentoRDS documento) throws ExcepcionRDS {
    	this.grabarDocumento(documento,false);	   	
    	this.doLogOperacion(getUsuario(),ACTUALIZAR_DOCUMENTO,"actualización documento " + documento.getReferenciaRDS().getCodigo());
    }   
    
    /**
     * Actualiza el fichero de un documento en el RDS. Recalcula el hash y elimina las firmas asociadas.
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public void actualizarFichero(ReferenciaRDS ref, byte[] datos) throws ExcepcionRDS {
    	
    	// Obtenemos documento y realizamos cambios
    	DocumentoRDS doc = this.consultarDocumento(ref,false);    	
    	doc.setDatosFichero(datos);
    	FirmaIntf[] firmas={};
    	doc.setFirmas(firmas);
    	
    	// Salvamos documento
    	this.grabarDocumento(doc,false);	   	
    	this.doLogOperacion(getUsuario(),ACTUALIZAR_FICHERO,"actualización fichero " + doc.getReferenciaRDS().getCodigo());
    }
    
    /**
     * Añadir firma a un documento en el RDS
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public void asociarFirmaDocumento(ReferenciaRDS refRds,FirmaIntf firma) throws ExcepcionRDS {
    	//TODO: Comprobar firma
    	Session session = getSession();     	
        try {        	          	        	
        	// Obtenemos documento
        	Documento doc = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));
        	
        	// Control de documento borrado
	    	if ("S".equals(doc.getBorrado())) throw new ExcepcionRDS("El documento " + doc.getCodigo() + " ha sido borrado por no tener usos" );
        	
        	Hibernate.initialize(doc.getFirmas());
        	
        	byte [] bytesFirma = getBytesFirma( firma ); 
        	
        	// Guardamos firma
    		// verificar firma
        	DocumentoRDS documento = this.consultarDocumento(refRds,true);
        	
    		if ( !this.verificarFirma( documento.getDatosFichero(), firma ) )
    		{
    			throw new ExcepcionRDS( "Error al verificar la firma del documento" );
    		}
        	
        	Firma fir = new Firma();
        	fir.setFirma( bytesFirma );
        	fir.setFormato(firma.getFormatoFirma());
        	doc.addFirma(fir);
        	session.update(doc);        	
        } catch (HibernateException he) {
        	log.error("Error insertando documento",he);
            throw new EJBException(he);
        }
        catch( ExcepcionRDS rdse )
        {
        	throw rdse;
        }
        catch( Exception exc )
        {
        	log.error( "Error obteniendo bytes de la firma ", exc );
        	throw new ExcepcionRDS( "Error obteniendo bytes de la firma ", exc );
        }
        finally {        	        	
            close(session);
        }
    	
    	this.doLogOperacion(getUsuario(),ASOCIAR_FIRMA,"asociar firma a documento " + refRds.getCodigo());
    }   
       
    /**
     *	Crea un uso para un documento 
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void crearUso(UsoRDS usoRDS) throws ExcepcionRDS {
    	Session session = getSession();     	
        try {        	                	
        	// Comprobamos campos 
        	if (usoRDS.getReferenciaRDS() == null){
        		log.error("No se ha indicado referencia RDS para crear uso");
        		throw new ExcepcionRDS("No se ha indicado referencia RDS para crear uso");
        	}
        	if (usoRDS.getTipoUso() == null){
        		log.error("No se ha indicado tipo de uso para crear uso");
        		throw new ExcepcionRDS("No se ha indicado tipo de uso para crear uso");
        	}
        	if (usoRDS.getReferencia() == null){
        		log.error("No se ha indicado referencia para crear uso");
        		throw new ExcepcionRDS("No se ha indicado referencia para crear uso");
        	}
        	
        	        	
        	// Comprobamos tipo uso
        	TipoUso tipoUso;
        	try{
        		tipoUso = (TipoUso) session.load(TipoUso.class,usoRDS.getTipoUso());
        	}catch(Exception e){
        		log.error("No existe tipo de uso " + usoRDS.getTipoUso());
        		throw new ExcepcionRDS("No existe tipo de uso " + usoRDS.getTipoUso(),e);
        	}
        	
        	// Comprobamos documento
        	Documento documento;
        	try{
        		documento = (Documento) session.load(Documento.class,new Long(usoRDS.getReferenciaRDS().getCodigo()));           		
        	}catch(Exception e){
        		log.error("No existe documento " + usoRDS.getReferenciaRDS().getCodigo());
        		throw new ExcepcionRDS("No existe documento " + usoRDS.getReferenciaRDS().getCodigo(),e);
        	}
        	
        	// Control de documento borrado
	    	if ("S".equals(documento.getBorrado())) throw new ExcepcionRDS("El documento " + documento.getCodigo() + " ha sido borrado por no tener usos" );
        	
        	// Comprobamos clave
    		if (!documento.getClave().equals(usoRDS.getReferenciaRDS().getClave())){
    			log.error("Clave de la referencia RDS no concuerda");
        		throw new ExcepcionRDS("Clave de la referencia RDS no concuerda"); 
    		}
        	
        	// Creamos uso
        	Uso uso = new Uso();        	
        	uso.setTipoUso(tipoUso);
        	uso.setDocumento(documento);
        	uso.setFecha(new Date());
        	uso.setReferencia(usoRDS.getReferencia());  
        	uso.setFechaSello(usoRDS.getFechaSello());
        	session.save(uso);
        	        	     
        } catch (HibernateException he) {
        	log.error("Error insertando uso",he);
            throw new EJBException(he);
        } finally {        	        	
            close(session);
        }
        
        this.doLogOperacion(getUsuario(),NUEVO_USO,"creación uso " + usoRDS.getTipoUso() + " para documento " + usoRDS.getReferenciaRDS().getCodigo() + "( referencia: " + usoRDS.getReferencia() + ")");
    }    
    
    /**
     * Consulta un documento del RDS (datos del documento y fichero asociado)
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumento(ReferenciaRDS refRds)  throws ExcepcionRDS{
    	DocumentoRDS doc = consultarDocumento(refRds,true);    	
    	return doc;
    }
    
    /**
     * Consulta un documento del RDS. Permite indicar si sólo se recuperan los datos del documento o también el fichero asociado
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumento(ReferenciaRDS refRds,boolean recuperarFichero) throws ExcepcionRDS {    	
       	// Obtenemos documento
    	Session session = getSession();
    	Documento documento;
    	DocumentoRDS documentoRDS;
	    try {	    	
	    	// Obtenemos documento
	    	documento = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));		    	
	    	
	    	// Control de documento borrado
	    	if ("S".equals(documento.getBorrado())) throw new ExcepcionRDS("El documento " + documento.getCodigo() + " ha sido borrado por no tener usos" );
	    	
	    	// Comprobamos que la clave coincida
	    	if (!documento.getClave().equals(refRds.getClave())){
	    		throw new ExcepcionRDS("La clave no coincide");
	    	}	    		    	
	    	
	    	
	    	Hibernate.initialize(documento.getFirmas());
	    	
	    	
	    	// Mapeamos a documentoRDS
	    	documentoRDS = establecerCamposDocumentoRDS(documento);	    		    	
	    } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            	
	    	    
	    // Consultamos fichero asociado	    
        try{        	
        	if (recuperarFichero){
	        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(documento.getUbicacion().getPluginAlmacenamiento());
	        	documentoRDS.setDatosFichero(plugin.obtenerFichero(documento.getCodigo()));
        	}
        }catch(Exception e){
        	log.error("No se ha podido obtener fichero en ubicación " + documento.getUbicacion().getCodigoUbicacion(),e);
        	throw new ExcepcionRDS("Error al guardar fichero",e);
        }
        
        this.doLogOperacion(getUsuario(),CONSULTAR_DOCUMENTO,"consulta documento " + refRds.getCodigo() );
        
        return documentoRDS;
    }
        
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */  
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds,String idioma) throws ExcepcionRDS {    	
    	Session session = getSession();
    	DocumentoRDS documentoRDS;
	    try {	    	
	    	// Obtenemos documento
	    	documentoRDS = consultarDocumento(refRds);
	    	    	
	    	// Si no es estructurado devolvemos documento sin formatear
	    	if (!documentoRDS.isEstructurado()) return documentoRDS;
	    	
	    	// Si el documento tiene una plantilla especifica la utilizamos
	    	// si no obtenemos plantilla por defecto
	    	// y si no tiene plantilla por defecto cogemos la primera	    	
	    	Documento documento = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));	   
	    	PlantillaIdioma plantilla = null;
	    	if (documento.getVersion().getPlantillas().size() <= 0){
	    		return documentoRDS;
	    		//throw new ExcepcionRDS("El documento no tiene plantillas");
	    	}	    		    	
	    	if (documento.getPlantilla() != null ){
	    		plantilla = (PlantillaIdioma) documento.getPlantilla().getTraduccion(idioma);
	    	}else{
		    	for (Iterator it = documento.getVersion().getPlantillas().iterator();it.hasNext();){
		    		Plantilla p = (Plantilla) it.next();	    			    		
		    		if (p.getDefecto() == 'S'){
		    			plantilla = (PlantillaIdioma) p.getTraduccion(idioma);
		    			break;
		    		}
		    	}
		    	if (plantilla == null) {
		    		plantilla =  (PlantillaIdioma) (
		    						((Plantilla) documento.getVersion().getPlantillas().iterator().next()).getTraduccion(idioma)
		    					 );	    		
		    	}
	    	}
	    	
	    	// Obtenemos usos
	    	List usos = listarUsos(refRds);
	    	
	    	// Generamos PDF	    	
	    	FormateadorDocumento format = FormateadorDocumentoFactory.getInstance().getFormateador( plantilla.getPlantilla().getFormateador().getClase() );
	    	DocumentoRDS docFormateado = format.formatearDocumento(documentoRDS,plantilla,usos);
	    
//	    	 En caso de que se haya establecido generar sello de registro/preregistro (envio/preenvio) lo generamos
	    	boolean docValido=true;
	    	if (plantilla.getPlantilla().getSello() == 'S'){
	    		// Si no hay sello marcamos como doc no valido -> no barcode + marca de agua
	    		if (!stampSello(docFormateado,usos)) docValido=false;
	    	}
	    	
	    	
	    	// En caso de que se haya establecido generar codigo de barras lo generamos
	    	if (plantilla.getPlantilla().getBarcode() == 'S' && docValido){
	    		stampBarCodeVerifier(docFormateado,plantilla.getPlantilla().getTipo(),idioma);
	    	}	
	    	
	    	// En caso de que no sea produccion lo marcamos como borrador
	    	if (isBorrador() || !docValido){
	    		stampBorrador(docFormateado);
	    	}
	    	
	    	// Realizamos apunte en el log de operaciones
	    	this.doLogOperacion(getUsuario(),CONSULTAR_DOCUMENTO_FORMATEADO,"consulta documento formateado " + refRds.getCodigo() );
	    	
	    	// Devolvemos documento RDS formateado
	    	return docFormateado;
	    	
	    } catch (Exception he) {
	    	log.error("No se ha podido obtener documento formateado ",he);
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            	
	            
    }
    
    
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     * generando una copia para el interesado y otra para la administracion
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */  
    public DocumentoRDS consultarDocumentoFormateadoCopiasInteresadoAdmon(ReferenciaRDS refRds,String idioma) throws ExcepcionRDS {    	
    	try {	    	
    		// Obtenemos pdf formateado
    		DocumentoRDS documentoRDS  = consultarDocumentoFormateado(refRds,idioma);
    		// Generamos copias interesado y administracion
    		documentoRDS.setDatosFichero(this.generarCopiasInteresadoAdministracion(documentoRDS.getDatosFichero()));
	    	return documentoRDS;
	    } catch (Exception he) {
	    	log.error("No se ha podido obtener documento formateado ",he);
	        throw new EJBException(he);
	    }
    }
    
    
    
    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */    
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds,String tipoPlantilla,String idioma) throws ExcepcionRDS {    	
    	Session session = getSession();
    	DocumentoRDS documentoRDS;
	    try {	    	
	    	// Obtenemos documento
	    	documentoRDS = consultarDocumento(refRds);
	    	
	    	// Obtenemos plantilla	    	
	    	Documento documento = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));	        
	    	// Comprobamos que la clave coincida
	    	if (!documento.getClave().equals(refRds.getClave())){
	    		throw new ExcepcionRDS("La clave no coincide");
	    	}	    	
	    	PlantillaIdioma plantilla = null;
	    	for (Iterator it = documento.getVersion().getPlantillas().iterator();it.hasNext();){
	    		Plantilla p = (Plantilla) it.next();
	    		if (p.getTipo().equals(tipoPlantilla)){
	    			plantilla = (PlantillaIdioma) p.getTraduccion(idioma);
	    			break;
	    		}
	    	}
	    	if (plantilla == null) {
	    		throw new Exception("No se encuentra plantilla");
	    	}
	    	
	    	// Obtenemos usos
	    	List usos = listarUsos(refRds);
	    	
	    	// Generamos PDF
	    	FormateadorDocumento format = FormateadorDocumentoFactory.getInstance().getFormateador( plantilla.getPlantilla().getFormateador().getClase() );
	    	DocumentoRDS docFormateado = format.formatearDocumento(documentoRDS,plantilla,usos);
	    	    		    	
	    	// En caso de que se haya establecido generar sello de registro/preregistro (envio/preenvio) lo generamos
	    	boolean docValido=true;
	    	if (plantilla.getPlantilla().getSello() == 'S'){
	    		// Si no hay sello marcamos como doc no valido -> no barcode + marca de agua
	    		if (!stampSello(docFormateado,usos)) docValido=false;
	    	}
	    	
	    	//	En caso de que se haya establecido generar codigo de barras lo generamos
	    	if (plantilla.getPlantilla().getBarcode() == 'S' && docValido){
	    		stampBarCodeVerifier(docFormateado,tipoPlantilla,idioma);
	    	}
	    	
	    	// En caso de que no sea produccion lo marcamos como borrador
	    	if (isBorrador()  || !docValido){
	    		stampBorrador(docFormateado);
	    	}
	    	
	    	// Realizamos apunte en el log de operaciones
	    	this.doLogOperacion(getUsuario(),CONSULTAR_DOCUMENTO_FORMATEADO,"consulta documento formateado " + refRds.getCodigo() );
	    	
	    	// Devolvemos documento RDS formateado
	    	return docFormateado;
	    	
	    } catch (Exception he) {
	    	log.error("No se ha podido obtener documento formateado ",he);
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            	
	            
    }
   
/**
    * 
    * Formatea un documento que no existe en el RDS a partir de una plantilla
    * 
    * @param documentoRDS XML a formatear. Debe tener establecidos los siguientes atributos: datosFichero,nombreFichero y titulo
    * @param modelo Modelo
    * @param version Version
    * @param tipoPlantilla Plantilla (si es nula se utiliza la por defecto)
    * @param idioma Idioma
    * @return
    * @throws ExcepcionRDS
    * 
    * @ejb.interface-method
    * @ejb.permission role-name="${role.user}"
    */
    public DocumentoRDS formatearDocumento(DocumentoRDS documentoRDS,String modelo,int version,String tipoPlantilla,String idioma) throws ExcepcionRDS {    	
    	try{    	
	    	// Obtenemos plantilla a utilizar
	    	VersionDelegate vd = DelegateUtil.getVersionDelegate();
	    	Version v = vd.obtenerVersionCompleta(modelo,version);
	    	
	    	if (v == null) {
	    		throw new Exception("No se encuentra versión documento");
	    	}
	    	
	    	
	    	PlantillaIdioma plantilla = null;
	    	for (Iterator it = v.getPlantillas().iterator();it.hasNext();){
	    		Plantilla p = (Plantilla) it.next();
	    	
	    		// Plantilla x defecto si tipoPlantilla esta vacia o nula
	    		if (StringUtils.isEmpty(tipoPlantilla)){
	    			if (p.getDefecto() == 'S'){
		    			plantilla = (PlantillaIdioma) p.getTraduccion(idioma);
		    			break;
		    		}
	    		}else{
	    		// Se especifica plantilla
	    			if (p.getTipo().equals(tipoPlantilla)){
		    			plantilla = (PlantillaIdioma) p.getTraduccion(idioma);
		    			break;
		    		}
	    		}
	    	}
	    	
	    	if (plantilla == null) {
	    		throw new Exception("No se encuentra plantilla");
	    	}
	    		

	    	// No existen usos ya que el documento no existe en el RDS
	    	List usos = new ArrayList();
	    	
			// Formateamos documento    
	    	FormateadorDocumento format = FormateadorDocumentoFactory.getInstance().getFormateador( plantilla.getPlantilla().getFormateador().getClase() );
	    	DocumentoRDS docFormateado = format.formatearDocumento(documentoRDS,plantilla,usos);
	    		
	    	// En caso de que se haya establecido generar sello de registro/preregistro (envio/preenvio) lo generamos
	    	//  (Sera siempre sin validez ya que no existen usos)
	    	boolean docValido=true;
	    	if (plantilla.getPlantilla().getSello() == 'S'){
	    		// Si no hay sello marcamos como doc no valido -> no barcode + marca de agua
	    		if (!stampSello(docFormateado,usos)) docValido=false;
	    	}

	    	/*	    	 
	    	 	NO PUEDEN LLEVAR CODIGO DE BARRAS YA QUE EL DOCUMENTO NO EXISTE 
	    	 	
	    	// En caso de que se haya establecido generar codigo de barras lo generamos
	    	if (plantilla.getPlantilla().getBarcode() == 'S' && docValido){
	    		stampBarCodeVerifier(docFormateado,plantilla.getPlantilla().getTipo(),idioma);
	    	}
	    	*/	    		    	
	    	
	    	// En caso de que no sea produccion lo marcamos como borrador
	    	if (isBorrador() || !docValido){
	    		stampBorrador(docFormateado);
	    	}
	    	
		    // Devolvemos documento RDS formateado
		    return docFormateado;
	    	
	    } catch (Exception he) {
	    	log.error("No se ha podido formatear documento ",he);
	        throw new EJBException(he);
	    }          		           
    }
            
    /**
     * Elimina uso para un documento del RDS
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public void eliminarUso(UsoRDS usoRDS) throws ExcepcionRDS {
    	// Borramos uso
    	Session session = getSession();    	   
	    try {	    		    	
	    	
	    	// Comprobamos tipo uso
        	TipoUso tipoUso;
        	try{
        		tipoUso = (TipoUso) session.load(TipoUso.class,usoRDS.getTipoUso());
        	}catch(Exception e){
        		log.error("No existe tipo de uso " + usoRDS.getTipoUso());
        		throw new ExcepcionRDS("No existe tipo de uso " + usoRDS.getTipoUso(),e);
        	}
        	
        	// Comprobamos documento
        	Documento documento;
        	try{
        		if (usoRDS.getReferenciaRDS() == null) throw new Exception("El uso no indica una refererencia RDS");
        		documento = (Documento) session.load(Documento.class,new Long(usoRDS.getReferenciaRDS().getCodigo()));        		
        	}catch(Exception e){
        		log.error("No existe documento " + usoRDS.getReferenciaRDS().getCodigo());
        		throw new ExcepcionRDS("No existe documento " + usoRDS.getReferenciaRDS().getCodigo(),e);
        	}
        	
        	// Comprobamos clave
    		if (!documento.getClave().equals(usoRDS.getReferenciaRDS().getClave())){
    			log.error("Clave de la referencia RDS no concuerda");
        		throw new ExcepcionRDS("Clave de la referencia RDS no concuerda"); 
    		}
	    	
	    	// Obtenemos usos
	    	Query query = session.createQuery("FROM Uso AS u WHERE u.documento = :documento and u.tipoUso = :tipoUso and u.referencia = :referencia");
            query.setParameter("documento", documento);
            query.setParameter("tipoUso", tipoUso);
            query.setParameter("referencia", usoRDS.getReferencia());
            //query.setCacheable(true);
            List result = query.list();
            if (result.isEmpty()) {
                return;
            }

            // Eliminamos usos
            for (int i=0;i<result.size();i++){
            	Uso uso = (Uso) result.get(i);
            	session.delete(uso);
            }
            
            // Si el documento no tiene usos lo borramos
            documentoSinUsos(session,documento);            
	    		    	
	    } catch (Exception he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            
	    
	    this.doLogOperacion(getUsuario(),ELIMINAR_USO,"eliminar uso " + usoRDS.getTipoUso() + " para documento " + usoRDS.getReferenciaRDS().getCodigo() + " ( referencia = " + usoRDS.getReferencia() + ")");
    }
    
    /**
     * Eliminar usos que tienen una determinada referencia para varios documentos del RDS
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     */
    public void eliminarUsos(String tipoUsoId,String referencia)throws ExcepcionRDS {
    	// Borramos uso
    	Session session = getSession();    	   
	    try {	    	
	    	
	    	// Comprobamos tipo uso
        	TipoUso tipoUso;
        	try{
        		tipoUso = (TipoUso) session.load(TipoUso.class,tipoUsoId);
        	}catch(Exception e){
        		log.error("No existe tipo de uso " + tipoUsoId);
        		throw new ExcepcionRDS("No existe tipo de uso " +tipoUsoId,e);
        	}
        	        	
	    	// Obtenemos usos
	    	Query query = session.createQuery("FROM Uso AS u WHERE u.tipoUso = :tipoUso and u.referencia = :referencia");            
            query.setParameter("tipoUso", tipoUso);
            query.setParameter("referencia", referencia);
            //query.setCacheable(true);
            List result = query.list();
            if (result.isEmpty()) {
                return;
            }

            // Eliminamos usos
            for (int i=0;i<result.size();i++){
            	Uso uso = (Uso) result.get(i);
            	Documento documento = uso.getDocumento();
            	
            	// Borramos uso
            	session.delete(uso);
            	
            	 // Si el documento no tiene usos lo borramos
                documentoSinUsos(session,documento);         
            }	    	            
            
	    } catch (Exception he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            
	    
	    this.doLogOperacion(getUsuario(),ELIMININAR_USOS,"eliminar usos " + tipoUsoId + " ( referencia = " + referencia + ")");
    }
    
    /**
     * Consulta usos para un documento del RDS
     * 
     * @ejb.interface-method
     * @ejb.permission role-name="${role.user}"
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarUsos(ReferenciaRDS refRDS) throws ExcepcionRDS {
    	// Borramos uso
    	Session session = getSession();   
    	List listaUsosRDS;
	    try {	    	
	    	
	    	// Comprobamos documento
        	Documento documento;
        	try{
        		documento = (Documento) session.load(Documento.class,new Long(refRDS.getCodigo()));        		
        	}catch(Exception e){
        		log.error("No existe documento " + refRDS.getCodigo());
        		throw new ExcepcionRDS("No existe documento " + refRDS.getCodigo(),e);
        	}
        	
        	// Control de documento borrado
	    	if ("S".equals(documento.getBorrado())) throw new ExcepcionRDS("El documento " + documento.getCodigo() + " ha sido borrado por no tener usos" );
        	
        	// Comprobamos clave
    		if (!documento.getClave().equals(refRDS.getClave())){
    			log.error("Clave de la referencia RDS no concuerda");
        		throw new ExcepcionRDS("Clave de la referencia RDS no concuerda"); 
    		}
	    	
	    	// Obtenemos usos
	    	Query query = session.createQuery("FROM Uso AS u WHERE u.documento = :documento");
            query.setParameter("documento", documento);
            //query.setCacheable(true);
            List result = query.list();
            
            
            // Devolvemos usos
            listaUsosRDS = new ArrayList();            
            if (result.isEmpty()) {
                return listaUsosRDS;
            }

            // Devolvemos usos
            for (int i=0;i<result.size();i++){
            	Uso uso = (Uso) result.get(i);
            	UsoRDS usoRDS = new UsoRDS();
            	usoRDS.setTipoUso(uso.getTipoUso().getCodigo());
            	usoRDS.setReferencia(uso.getReferencia());            	
            	usoRDS.setReferenciaRDS(refRDS);     
            	usoRDS.setFechaSello(uso.getFechaSello());
            	listaUsosRDS.add(usoRDS);
            }	    	
            
	    } catch (HibernateException he) {
	        throw new EJBException(he);
	    } finally {
	        close(session);
	    }            
	    
	    this.doLogOperacion(getUsuario(),LISTAR_USOS,"listar usos documento " + refRDS.getCodigo());
	    return listaUsosRDS;
    }
    
    
    /**
     * Verifica documento formateado generado por la plataforma
     * 
     * @ejb.interface-method
     * @ejb.permission unchecked = "true"
     */
    public DocumentoVerifier verificarDocumento(KeyVerifier key) throws ExcepcionRDS {       	
    	try{
	    	// Obtenemos referencia RDS a partir del codigo
	    	ReferenciaRDS referenciaRDS = ResolveRDS.getInstance().resuelveRDS(key.getIdDocumento().longValue());
	    	
	    	// Comprobamos si la clave codificada coincide
	    	if (!key.verifyClaveRDS(referenciaRDS.getClave())) throw new ExcepcionRDS("Clave de acceso incorrecta");
	    	    	
	       	// Obtenemos documento
	    	DocumentoRDS docRDS = null;	    	
	    	docRDS = consultarDocumento(referenciaRDS,true);
	    	
	    	// Formateamos documento
	    	DocumentoRDS docRDSFormat = consultarDocumentoFormateado(referenciaRDS,key.getPlantillaDocumento(),key.getIdiomaDocumento());
	    	
	    	// Devolvemos documento
	    	DocumentoVerifier docVer = new DocumentoVerifier();
	    	docVer.setTitulo(docRDS.getTitulo());
	    	docVer.setEstructurado(docRDS.isEstructurado());
	    	docVer.setNombreFichero(docRDS.getNombreFichero());
	    	docVer.setDatosFichero(docRDS.getDatosFichero());;
	    	docVer.setNombreFicheroFormateado(docRDSFormat.getNombreFichero());
	    	docVer.setDatosFicheroFormateado(docRDSFormat.getDatosFichero());
	    	docVer.setExtensionFichero(docRDS.getExtensionFichero());
	    	docVer.setFechaRDS(docRDS.getFechaRDS());
	    	docVer.setFirmas(docRDS.getFirmas());
	    	
	        return docVer;
    	}catch(Exception ex){
    		throw new ExcepcionRDS("Error al verificar documento",ex);
    	}
    }
    
    // ---------------------- Funciones auxiliares -------------------------------------------    
    /**
     *  Guarda documento en el RDS
     */
    private ReferenciaRDS grabarDocumento(DocumentoRDS documento,boolean nuevo) throws ExcepcionRDS{
    	Session session = getSession(); 
    	Documento doc;
        try {        	          	        	
        	// Si no es nuevo recuperamos documentos
        	if (nuevo){
        		doc = new Documento();
        	}else{
		    	doc = (Documento) session.load(Documento.class, new Long(documento.getReferenciaRDS().getCodigo()));	
		    	if ("S".equals(doc.getBorrado())) throw new ExcepcionRDS("El documento " + documento.getReferenciaRDS().getCodigo() + " ha sido borrado por no tener usos" );
        	}        	
        	
        	// Establecemos campos documento
        	establecerCamposDocumento(doc,documento,nuevo);
        	        	        	        	
        	// Realizamos salvado
        	if (nuevo) {
        		session.save(doc);
        	}else{
        		session.update(doc);
        	}        	     
        	
        } catch (HibernateException he) {
        	log.error("Error insertando documento",he);
            throw new EJBException(he);
        } finally {        	        	
            close(session);
        }
                        
        // Guardamos fichero                    
        try{        	
        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(doc.getUbicacion().getPluginAlmacenamiento());
        	plugin.guardarFichero(doc.getCodigo(),documento.getDatosFichero());
        }catch(Exception e){
        	log.error("No se ha podido guardar fichero en ubicación " + documento.getCodigoUbicacion(),e);
        	throw new ExcepcionRDS("Error al guardar fichero",e);
        }
                        	
	    // Devolvemos referencia
	    ReferenciaRDS ref = new ReferenciaRDS();
	    ref.setCodigo(doc.getCodigo().longValue());
	    ref.setClave(doc.getClave());            
	    return ref;
    }
    
    /**
     * Verifica que están los campos obligatorios
     */
    private void establecerCamposDocumento(Documento doc,DocumentoRDS documento,boolean nuevo) throws ExcepcionRDS{
    	// 1- VERIFICAMOS CAMPOS OBLIGATORIOS
    	if (documento.getCodigoUbicacion() == null) throw new ExcepcionRDS("No se ha indicado código de ubicación");
    	if (documento.getDatosFichero() == null || documento.getDatosFichero().length <= 0) throw new ExcepcionRDS("No se han establecido los datos del fichero");
    	if (documento.getExtensionFichero() == null) throw new ExcepcionRDS("No se ha indicado la extensión del fichero");
    	if (documento.getModelo() == null) throw new ExcepcionRDS("No se ha indicado el modelo del documento");
    	//if (documento.getNif() == null) throw new ExcepcionRDS("No se ha indicado el NIF del usuario que incorpora el documento");
    	if (documento.getNombreFichero() == null) throw new ExcepcionRDS("No se ha indicado el nombre del fichero");
    	if (documento.getTitulo() == null) throw new ExcepcionRDS("No se ha indicado el título del documento");    	
    	if (documento.getUnidadAdministrativa() == -1) throw new ExcepcionRDS("No se ha indicado la Unidad Administrativa responsable del documento");
    	if (documento.getVersion() == -1) throw new ExcepcionRDS("No se ha indicado la versión del documento");
    	
    	// 2- OBTENEMOS VERSION Y UBICACION
    	// -------- Obtenemos version        	
    	Version version;
    	try{
    		VersionDelegate vd = DelegateUtil.getVersionDelegate();
    		version = vd.obtenerVersion(documento.getModelo(),documento.getVersion());
    		if (version == null) {
    			log.error("No existe versión " + documento.getModelo() + " - " + documento.getVersion());
    			throw new ExcepcionRDS("No existe modelo/version en RDS: " + documento.getModelo() + " / " + documento.getVersion());
    		}
    	}catch (Exception e){
    		log.error("No se ha podido obtener versión " + documento.getModelo() + " - " + documento.getVersion(),e);
    		throw new ExcepcionRDS("No se ha podido obtener modelo / version en RDS",e);
    	}    	        	
    	// --------- Obtenemos ubicación        
    	Ubicacion ubicacion;
    	try{
    		UbicacionDelegate ud = DelegateUtil.getUbicacionDelegate();
    		ubicacion = ud.obtenerUbicacion(documento.getCodigoUbicacion());
    		if (ubicacion == null) {
    			log.error("No existe ubicación " + documento.getCodigoUbicacion());
    			throw new ExcepcionRDS("No existe ubicación en RDS");        			
    		}
    	}catch (Exception e){
    		log.error("No se ha podido obtener ubicación " + documento.getCodigoUbicacion(),e);
    		throw new ExcepcionRDS("No se ha podido obtener ubicación en RDS",e);
    	}        
    	    
    	// 3- ESTABLECEMOS PLANTILLA ESPECIFICA
    	if (StringUtils.isNotEmpty(documento.getPlantilla())){
	    	try{
	    		Plantilla plantilla;
	    		PlantillaDelegate pl = DelegateUtil.getPlantillaDelegate();
	    		plantilla = pl.obtenerPlantilla(version,documento.getPlantilla());
	    		if (plantilla == null) {
	    			log.error("No existe plantilla " + documento.getPlantilla() + " para modelo " + version.getModelo() + " - version " + version.getVersion());
	    			throw new ExcepcionRDS("No existe plantilla " + documento.getPlantilla() + " para modelo " + version.getModelo() + " - version " + version.getVersion()) ;      			
	    		}	    		
	    		doc.setPlantilla(plantilla);
	    	}catch (Exception e){
	    		log.error("No se ha podido obtener plantilla específica " + documento.getPlantilla(),e);
	    		throw new ExcepcionRDS("No se ha podido obtener plantilla específica ",e);
	    	}
    	}else{
    		doc.setPlantilla(null);
    	}
    	
    	// 4- ESTABLECEMOS CAMPOS EN DOCUMENTO    	
    	doc.setVersion(version);    	
    	// ---- Si el documento no es nuevo no dejamos cambiar de ubicación
    	if (!nuevo){
	    	if (ubicacion.getCodigo().longValue() != doc.getUbicacion().getCodigo().longValue()){
	    		throw new ExcepcionRDS("No se permite cambiar de ubicación al actualizar documento");
	    	}    	
    	}else{
    		doc.setUbicacion(ubicacion);
    	}
    	// 	---- Si no es nuevo comprobamos que la clave coincida
    	if (!nuevo){
    		if (documento.getReferenciaRDS() == null) throw new ExcepcionRDS("No se ha indicado la referencia RDS");
    		// Comprobamos que la clave coincida
	    	if (!doc.getClave().equals(documento.getReferenciaRDS().getClave())){
	    		throw new ExcepcionRDS("La clave no coincide");
	    	}	    	
    	}    	
    	doc.setTitulo(documento.getTitulo());
    	
    	// Normalizamos NIEs
    	if (documento.getNif() != null){
    		doc.setNif(documento.getNif().replaceAll("-",""));
    	}
    	
    	doc.setUsuarioSeycon(documento.getUsuarioSeycon());
    	doc.setUnidadAdministrativa(new Long(documento.getUnidadAdministrativa()));
    	doc.setNombreFichero(documento.getNombreFichero());
    	doc.setExtensionFichero(documento.getExtensionFichero());
    	// --- Firmas documento
    	// ----------- Si no es nuevo eliminamos firmas anteriores
    	if (!nuevo){    	    	
    		doc.getFirmas().removeAll(doc.getFirmas());
    	}
    	// ------------ Establecemos nuevas firmas (verificandolas)    
    	if (documento.getFirmas() != null){
	    	for (int i=0;i<documento.getFirmas().length;i++){
	    		// Verificamos firma
	    		// Añadimos firma
	    		Firma firma = new Firma();
	    		FirmaIntf signature = documento.getFirmas()[i];
	    		try
	    		{
	    			firma.setFirma( getBytesFirma( signature ) );
	    			firma.setFormato(signature.getFormatoFirma());
	    		}
	    		catch( Exception exc )
	    		{
	    			throw new ExcepcionRDS( "Error obteniendo bytes de la firma ", exc );
	    		}
	    		// Falta verificar firma
	    		if ( !this.verificarFirma( documento.getDatosFichero(), signature ) )
	    		{
	    			throw new ExcepcionRDS( "Error al verificar la firma del documento" );
	    		}
	    		doc.addFirma(firma);
	    	}
    	}
    	
    	// --- Establecemos campos calculados por RDS ---
    	// ------- Establecemos fecha    	
    	doc.setFecha(new Timestamp(System.currentTimeMillis()));
    	// ------- Calculamos hash    	
    	try{
    		doc.setHashFichero(generaHash(documento.getDatosFichero()));
    	}catch (Exception e){
    		log.error("No se ha podido calcular el hash",e);
    		throw new ExcepcionRDS("No se ha podido calcular el hash",e);
    	}  
    	// ------- Generamos clave
    	if (nuevo) doc.setClave(generarClave());   
    }
    
    
    /**
     * Obtiene plugin almacenamiento
     * @param classNamePlugin
     * @return
     */
    private PluginAlmacenamientoRDS obtenerPluginAlmacenamiento(String classNamePlugin) throws Exception{
    	return PluginClassCache.getInstance().getPluginAlmacenamientoRDS(classNamePlugin);    	
    }
    
    /**
     * Mapea Documento a DocumentoRDS
     * @param doc
     * @return
     * @throws ExcepcionRDS
     */
    private DocumentoRDS establecerCamposDocumentoRDS(Documento doc) throws ExcepcionRDS{
    	DocumentoRDS documentoRDS = new DocumentoRDS();    
    	ReferenciaRDS ref = new ReferenciaRDS();
    	ref.setCodigo(doc.getCodigo().longValue());
    	ref.setClave(doc.getClave());
    	documentoRDS.setReferenciaRDS(ref);
    	documentoRDS.setCodigoUbicacion(doc.getUbicacion().getCodigoUbicacion());    	
    	documentoRDS.setEstructurado(doc.getVersion().getModelo().getEstructurado() == 'S');
    	documentoRDS.setExtensionFichero(doc.getExtensionFichero());
    	documentoRDS.setFechaRDS(doc.getFecha());
    	documentoRDS.setHashFichero(doc.getHashFichero());
    	documentoRDS.setModelo(doc.getVersion().getModelo().getModelo());
    	documentoRDS.setVersion(doc.getVersion().getVersion());
    	documentoRDS.setNif(doc.getNif());
    	documentoRDS.setUsuarioSeycon(doc.getUsuarioSeycon());
    	documentoRDS.setTitulo(doc.getTitulo());
    	documentoRDS.setNombreFichero(doc.getNombreFichero());
    	documentoRDS.setUnidadAdministrativa(doc.getUnidadAdministrativa().longValue());
    	if (doc.getPlantilla() != null){
    		documentoRDS.setPlantilla(doc.getPlantilla().getTipo());
    	}
    	if (doc.getFirmas().size() > 0){ 
    		int i=0;
    		FirmaIntf ls_firmas[] = new FirmaIntf[doc.getFirmas().size()];
	    	for (Iterator it = doc.getFirmas().iterator();it.hasNext();){
	    		try
	    		{
	    			Firma f = (Firma) it.next();
		    		ls_firmas[i] = this.getFirma( f.getFirma(),f.getFormato() ) ;	    		
		    		i++;
	    		}
	    		catch ( Exception exc )
	    		{
	    			throw new ExcepcionRDS( "Error obteniendo string de la firma ", exc );
	    		}
	    	}
	    	documentoRDS.setFirmas(ls_firmas);
    	}    	
    	return documentoRDS;
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
    
    //  Si el documento no tiene más usos eliminamos documento
    private void documentoSinUsos(Session session,Documento documento) throws Exception{
    	String ls_plugin,ls_ubicacion;
	    Query query = session.createQuery("FROM Uso AS u WHERE u.documento = :documento");
	    query.setParameter("documento", documento);            	    
	    //query.setCacheable(true);
	    List result = query.list();	    
	    if (result.isEmpty()) {
	    	
	    	// Obtenemos plugin almacenamiento
	    	ls_plugin = documento.getUbicacion().getPluginAlmacenamiento();
	    	ls_ubicacion =documento.getUbicacion().getCodigoUbicacion();
	    	
	    	// Borramos documento
	        session.delete(documento);
	       	    	
	        // Borramos fichero asociado	        
	        try{        	
	        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(ls_plugin);
	        	plugin.eliminarFichero(documento.getCodigo());
	        }catch(Exception e){
	        	log.error("No se ha podido eliminar fichero en ubicación " + ls_ubicacion,e);
	        	throw new ExcepcionRDS("Error al eliminar fichero",e);
	        }
	        
	        // Realizamos apunte en el log
	        doLogOperacionImpl(getUsuario(),BORRADO_AUTOMATICO_DOCUMENTO_SIN_USOS,"Borrado automático de documento " + documento.getCodigo() + " por no tener usos",session);
	    }
    }
    
    /**
     * Genera clave de acceso al documento (Cadena de 10 carácteres)
     * @return
     */
    private String generarClave(){
    	Random r = new Random();    	
    	String clave="";
    	int ca = Character.getNumericValue('a');
    	for (int i=0;i<10;i++){
    		clave += Character.forDigit(ca + r.nextInt(26),Character.MAX_RADIX);    		
    	}
    	return clave;    	    	
    }
    
    /**
     * Genera hash documento
     */
    private String generaHash(byte[] datos) throws Exception{    	    	
    	MessageDigest dig = MessageDigest.getInstance(ConstantesRDS.HASH_ALGORITMO);        	
    	return  new String(Hex.encodeHex(dig.digest(datos)));    	
    }
    
    
    private byte[] getBytesFirma( FirmaIntf firma ) throws Exception
    {
    	PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
    	return plgFirma.parseFirmaToBytes( firma );
    }
    
    private FirmaIntf getFirma( byte []byteArrayFirma , String formatoFirma) throws Exception
    {
    	PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
    	return plgFirma.parseFirmaFromBytes( byteArrayFirma, formatoFirma );
    }
    
    /**
     * Verifica firma del documento (completa timestamp si es necesario)
     * 
     * @param datosDocumento
     * @param firma
     * @return
     */
    private boolean verificarFirma( byte [] datosDocumento, FirmaIntf firma )
    {
	    try{
	    	PluginFirmaIntf plgFirma = PluginFactory.getInstance().getPluginFirma();
	    	return plgFirma.verificarFirma( new ByteArrayInputStream( datosDocumento ), firma );
		}catch (Exception e){
			log.error ("Error al verificar firma: " + e.getMessage(),e);
			return false;
		}
    }
    
    /**
     * Realiza stamp 
     * @param doc
     * @throws Exception
     */
    private void stampBarCodeVerifier(DocumentoRDS doc,String plantilla,String idioma) throws Exception{
    	
    	// Generamos key
    	KeyVerifier key = new KeyVerifier(doc.getReferenciaRDS(),plantilla,idioma);
    	
    	// Obtenemos url y textos verifier    	
		String url = URL_VERIFIER;
		String text = TEXT_VERIFIER;
    	String urlVerificacion = url + key.getKeyEncoded();
    	doc.setUrlVerificacion(urlVerificacion);
    	
    	// Realizamos stamp
		ObjectStamp stamps [] = new ObjectStamp[3];
				
		BarcodeStamp bc = new BarcodeStamp();		
		bc.setTexto(urlVerificacion);
		//bc.setTexto("12345");
		bc.setTipo(BarcodeStamp.BARCODE_PDF417);
		bc.setPage(0);
		bc.setX(350);
		bc.setY(19);		
		bc.setRotation(0);
		bc.setOverContent(true);	
		bc.setXScale(new Float(100));
		bc.setYScale(new Float(100));		
		stamps[0] = bc;
		
		TextoStamp tx = new TextoStamp();		
		tx.setTexto(bc.getTexto());
		tx.setFontName("Helvetica-Bold");
		tx.setFontSize(7);
		tx.setX(290);
		tx.setY(13);
		stamps[1] = tx;
		
		TextoStamp tx2 = new TextoStamp();		
		tx2.setTexto(text);
		tx2.setFontSize(6);
		tx2.setX(330);
		tx2.setY(44);
		stamps[2] = tx2;
		
		
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();    	
    	UtilPDF.stamp(bos,new ByteArrayInputStream(doc.getDatosFichero()),stamps);    	
    	doc.setDatosFichero(bos.toByteArray());
    	bos.close();
    	    	
    }
    
    /**
     * Realiza stamp para indicar que es un borrador
     * @param doc
     * @throws Exception
     */
    private void stampBorrador(DocumentoRDS doc) throws Exception{
		ObjectStamp[] textos = new ObjectStamp[1];
		textos[0] = new TextoStamp();
		((TextoStamp) textos[0]).setTexto("Sense validesa");		
		((TextoStamp) textos[0]).setFontSize(85);	
		((TextoStamp) textos[0]).setFontColor(Color.LIGHT_GRAY);
		textos[0].setPage(0);
		textos[0].setX(100);
		textos[0].setY(300);
		textos[0].setRotation(45f);
		textos[0].setOverContent(false);
				
		ByteArrayOutputStream bos = new ByteArrayOutputStream();    	
    	UtilPDF.stamp(bos,new ByteArrayInputStream(doc.getDatosFichero()),textos);    	
    	doc.setDatosFichero(bos.toByteArray());
    	bos.close();
	}
    
    /**
     * Realiza stamp de sello (Toma en cuenta tambien registro salida):
     * 		- No hay uso prereg/reg o preenv/env: marcamos doc como no válido
     * 		- Hay uso prereg/preenv: introducimos espacio para sello en 1ª pag y en todas el num de prereg + dc
     * 		- Hay uso reg/env: en todas las pag el num de reg/env
     * @param docFormateado
     * @param usos
     * 
     * @return devuelve si se ha puesto el sello
     */
    private boolean stampSello(DocumentoRDS doc, List usos)  throws Exception{    	
    	ObjectStamp textos [];
    	UsoRDS usoSello;
    	
    	// Intentamos obtener numero entrada
    	usoSello = UtilRDS.obtenerNumeroEntrada(usos);
    	
    	// Intentamos obtener numero salida
    	if (usoSello == null){
    		usoSello = UtilRDS.obtenerNumeroSalida(usos);
    	}
    	
    	if (usoSello == null) return false;
    	    	
    	String txtNumRegistro,txtDC,txtData;
    	int numText = 1;
    	if (usoSello.getReferencia().startsWith("PRE")){
    		txtNumRegistro = "Num. Preregistre: "+usoSello.getReferencia();
    		txtDC="Dígit Control: " + StringUtil.calculaDC(usoSello.getReferencia());    
    		txtData="Data Preregistre: ";
    		numText++;
    	}else{
    		txtNumRegistro = "Num. Registre: "+usoSello.getReferencia();
    		txtData="Data Registre: ";
    		txtDC="";    		
    	}	    	    	
    	if (usoSello.getFechaSello() != null) {
    		numText++;
    	}
    	
    	// Creamos textos a stampar
    	textos = new ObjectStamp[numText];
    		// Texto xa num registro
    	textos[0] = new TextoStamp();
		((TextoStamp) textos[0]).setTexto(txtNumRegistro + "  " + txtDC);				
		textos[0].setPage(0);
		textos[0].setX(340);
		textos[0].setY(815);				
		textos[0].setOverContent(true);
			// Texto xa fecha registro
		if (usoSello.getFechaSello() != null) {
			numText--;
			textos[numText] = new TextoStamp();
			((TextoStamp) textos[numText]).setTexto(txtData + StringUtil.fechaACadena(usoSello.getFechaSello(),"dd/MM/yyyy HH:mm"));
			textos[numText].setPage(0);
			textos[numText].setX(340);
			textos[numText].setY(805);			
			textos[numText].setOverContent(true);
		}
			// Cuadro xa registro presencial
    	if (txtDC.length()>0){
    		numText --;
    		textos[numText] = new SelloEntradaStamp();	
    		textos[numText].setPage(1);
    		textos[numText].setX(565);
    		textos[numText].setY(802);	
    		textos[numText].setOverContent(true);
    	}
    	
		// Generamos PDF
		ByteArrayOutputStream bos = new ByteArrayOutputStream();    	
    	UtilPDF.stamp(bos,new ByteArrayInputStream(doc.getDatosFichero()),textos);    	
    	doc.setDatosFichero(bos.toByteArray());    	
    	bos.close();
    	
    	return true;
		
 	}
            
    private boolean isBorrador(){
    	try{
	    	String text = ENTORNO;
	    	if (text != null && text.equals("PRODUCCION")) return false;
	    	return true;
    	}catch (Exception ex){
    		return false;
    	}    	
    }
    
    private byte[] generarCopiasInteresadoAdministracion(byte[] pdf) throws Exception{

    	ObjectStamp[] textos = new ObjectStamp[1];
		textos[0] = new TextoStamp();				
		((TextoStamp) textos[0]).setFontSize(7);			
		((TextoStamp) textos[0]).setFontColor(Color.LIGHT_GRAY);
		textos[0].setPage(0);
		textos[0].setX(200);
		textos[0].setY(20);			
		textos[0].setOverContent(false);
		
		((TextoStamp) textos[0]).setTexto("Exemplar per a l'Administració");
		ByteArrayInputStream bis = new ByteArrayInputStream(pdf);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		UtilPDF.stamp(bos, bis, textos);
		byte[] contentPDF1 = bos.toByteArray();
		bis.close();
		bos.close();

		((TextoStamp) textos[0]).setTexto("Exemplar per al sol·licitant");
		bis = new ByteArrayInputStream(pdf);
		bos = new ByteArrayOutputStream();
		UtilPDF.stamp(bos, bis, textos);
		byte[] contentPDF2 = bos.toByteArray();
		bis.close();
		bos.close();

		bos = new ByteArrayOutputStream();
		InputStream pdfs[] = { new ByteArrayInputStream(contentPDF1),
				new ByteArrayInputStream(contentPDF2) };
		UtilPDF.concatenarPdf(bos, pdfs);
		byte[] content = bos.toByteArray();
		pdfs[0].close();
		pdfs[1].close();
		bos.close();
		
		return content;		
	 }
}
