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

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

import es.caib.redose.model.Documento;
import es.caib.redose.model.Firma;
import es.caib.redose.model.LogGestorDocumentalError;
import es.caib.redose.model.LogOperacion;
import es.caib.redose.model.Plantilla;
import es.caib.redose.model.PlantillaIdioma;
import es.caib.redose.model.TablaTransformacionCsv;
import es.caib.redose.model.TipoOperacion;
import es.caib.redose.model.TipoUso;
import es.caib.redose.model.Ubicacion;
import es.caib.redose.model.Uso;
import es.caib.redose.model.Version;
import es.caib.redose.model.VersionCustodia;
import es.caib.redose.modelInterfaz.ConstantesRDS;
import es.caib.redose.modelInterfaz.DocumentoRDS;
import es.caib.redose.modelInterfaz.DocumentoVerifier;
import es.caib.redose.modelInterfaz.ExcepcionRDS;
import es.caib.redose.modelInterfaz.KeyVerifier;
import es.caib.redose.modelInterfaz.ReferenciaRDS;
import es.caib.redose.modelInterfaz.TransformacionRDS;
import es.caib.redose.modelInterfaz.UsoRDS;
import es.caib.redose.persistence.delegate.DelegateUtil;
import es.caib.redose.persistence.delegate.LogGestorDocumentalErroresDelegate;
import es.caib.redose.persistence.delegate.PlantillaDelegate;
import es.caib.redose.persistence.delegate.UbicacionDelegate;
import es.caib.redose.persistence.delegate.VersionCustodiaDelegate;
import es.caib.redose.persistence.delegate.VersionDelegate;
import es.caib.redose.persistence.formateadores.FormateadorDocumento;
import es.caib.redose.persistence.formateadores.FormateadorDocumentoFactory;
import es.caib.redose.persistence.plugin.MetadaAlmacenamiento;
import es.caib.redose.persistence.plugin.PluginAlmacenamientoRDS;
import es.caib.redose.persistence.plugin.PluginClassCache;
import es.caib.redose.persistence.util.CacheSincronizacionGestorDocumental;
import es.caib.redose.persistence.util.ConversorOpenOffice;
import es.caib.redose.persistence.util.GeneradorCsv;
import es.caib.redose.persistence.util.UtilRDS;
import es.caib.sistra.plugins.PluginFactory;
import es.caib.sistra.plugins.custodia.PluginCustodiaIntf;
import es.caib.sistra.plugins.firma.FirmaIntf;
import es.caib.sistra.plugins.firma.PluginFirmaIntf;
import es.caib.util.CifradoUtil;
import es.caib.util.StringUtil;
import es.caib.xml.ConstantesXML;
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
 *  view-type="remote"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
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
	private String OPENOFFICE_HOST = null;
	private String OPENOFFICE_PUERTO = null;
	private boolean BARCODE_VERIFIER_MOSTRAR = false;
	private boolean USAR_CSV = false;
	private String URL_CSV = null;
	private String CLAVE_CIFRADO = null;

	private boolean existeCustodia = false;
	private boolean existeGestionDocumental = false;

	/**
     * @ejb.create-method
     * @ejb.permission unchecked = "true"
     */
	public void ejbCreate() throws CreateException {
		super.ejbCreate();

		// Obtenemos configuracion
		try{
			Properties props = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion();
			URL_VERIFIER = props.getProperty("sistra.url") + props.getProperty("sistra.contextoRaiz.front")+ "/redosefront/init.do?id=";
			TEXT_VERIFIER=props.getProperty("verifier.text");
			ENTORNO =props.getProperty("entorno");
			OPENOFFICE_HOST=props.getProperty("openoffice.host");
			OPENOFFICE_PUERTO=props.getProperty("openoffice.port");
			BARCODE_VERIFIER_MOSTRAR= "true".equals(StringUtils.defaultIfEmpty(props.getProperty("urlVerificacion.barcode.mostrar"),"true"));
			USAR_CSV= "true".equals(props.getProperty("urlVerificacion.csv"));
			URL_CSV = props.getProperty("sistra.url") + props.getProperty("sistra.contextoRaiz.front") + "/redosefront/init.do?csv=";
			CLAVE_CIFRADO = props.getProperty("clave.cifrado");

			// Comprobamos si hay que integrarse con sistema de custodia y gestion documental
	    	try{
	    		PluginFactory.getInstance().getPluginCustodia();
	    		existeCustodia=true;
	    	}catch(Exception nep){
	    		// En caso de que no este configurado el plugin no hay que hacer nada
	    		existeCustodia = false;
	    	}

			// Comprobamos si hay que integrarse con sistema de gestion documental
	    	try{
	    		PluginFactory.getInstance().getPluginGestionDocumental();
	    		existeGestionDocumental=true;
	    	}catch(Exception nep){
	    		// En caso de que no este configurado el plugin no hay que hacer nada
	    		existeGestionDocumental = false;
	    	}
		}catch(Exception ex){
			log.error("No se pueden acceder propiedades modulo",ex);
			throw new CreateException("No se pueden obtener propiedades modulo");
		}

	}

	/**
	 * Inserta un documento en el RDS
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public ReferenciaRDS insertarDocumento(DocumentoRDS documento) throws ExcepcionRDS{
    	// Realizamos operación
    	ReferenciaRDS ref = this.grabarDocumento(documento,true);
    	// Realizamos log
    	this.doLogOperacion(getUsuario(),NUEVO_DOCUMENTO,"inserción documento " + ref.getCodigo());
    	return ref;
    }

    /**
	 * Inserta un documento en el RDS permitiendo transformar el documento (p.e. convertir a PDF). <br/>
	 *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public ReferenciaRDS insertarDocumento(DocumentoRDS documento, TransformacionRDS transformacion) throws ExcepcionRDS{
    	try{

    		// 1.- Verificamos que se pueden realizar transformaciones
    		//  	-- Si se realiza transformacion el documento no puede llevar asociadas firmas, ya que se modifica el documento
    		if (transformacion.existeTransformacion() && documento.getFirmas() != null && documento.getFirmas().length > 0){
    			throw new Exception("Si se realiza transformacion el documento no puede llevar asociadas firmas, ya que se modifica el documento");
    		}
    		// 		-- Verificamos extensiones validas para conversion a pdf
    		if (transformacion.isConvertToPDF() && !verificarExtensionConversionPDF(documento.getExtensionFichero())){
    			throw new Exception("No se permite la conversion a PDF para la extension " + documento.getExtensionFichero().toLowerCase() );
    		}
    		//		-- Verificamos que para stampar el barcode sea un pdf
    		if (transformacion.isBarcodePDF() && !transformacion.isConvertToPDF() && !"pdf".equalsIgnoreCase(documento.getExtensionFichero()) ){
    			throw new Exception("El barcode solo se aplica sobre pdf");
    		}

    		// 2.- Aplicamos transformaciones previas
    		// 		-- En caso necesario realizamos conversion a PDF
    		if (transformacion.isConvertToPDF()){
		    	byte[] pdf = this.convertirFicheroAPDF(documento.getDatosFichero(),documento.getExtensionFichero());
		    	documento.setDatosFichero(pdf);
		    	documento.setExtensionFichero("pdf");
		    	documento.setNombreFichero(documento.getNombreFichero().substring(0,documento.getNombreFichero().lastIndexOf(".")) + ".pdf");
    		}

    		// 3.- Insertamos documento y obtenemos codigo RDS
        	ReferenciaRDS ref = this.grabarDocumento(documento,true);
    		documento.setReferenciaRDS(ref);


    		// 4.- Aplicamos transformaciones posteriores: stamp barcode
    		// 		-- En caso necesario ponemos barcode a PDF
    		if (transformacion.isBarcodePDF()){
	    		// Stampamos barcode
    			this.stampBarCodeVerifier(documento,null,null);
	    		// Actualizamos documento
	    		this.grabarDocumento(documento,false);
	    	}

	    	// 5.- Realizamos log de la operacion
	    	this.doLogOperacion(getUsuario(),NUEVO_DOCUMENTO,"inserción documento " + ref.getCodigo());
	    	return ref;


    	}catch(Exception ex){
    		throw new ExcepcionRDS("Error al insertar documento aplicando transformacion",ex);
	    }

    }

    /**
     * Actualiza un documento en el RDS
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void actualizarDocumento(DocumentoRDS documento) throws ExcepcionRDS {

    	// Solo permitimos actualizar fichero si tiene uso de persistencia
    	List usos = this.listarUsosDocumento(documento.getReferenciaRDS());
    	for (Iterator it=usos.iterator();it.hasNext();){
    		UsoRDS uso = (UsoRDS) it.next();
    		if (!uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE)){
    			throw new ExcepcionRDS("No se puede modificar un documento si no tiene tipo de uso de persistencia");
    		}
    	}

    	this.grabarDocumento(documento,false);
    	this.doLogOperacion(getUsuario(),ACTUALIZAR_DOCUMENTO,"actualización documento " + documento.getReferenciaRDS().getCodigo());
    }

    /**
     * Actualiza el fichero de un documento en el RDS. Recalcula el hash y elimina las firmas asociadas.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public void actualizarFichero(ReferenciaRDS ref, byte[] datos) throws ExcepcionRDS {

    	// Solo permitimos actualizar fichero si tiene uso de persistencia
    	List usos = this.listarUsosDocumento(ref);
    	for (Iterator it=usos.iterator();it.hasNext();){
    		UsoRDS uso = (UsoRDS) it.next();
    		if (!uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE)){
    			throw new ExcepcionRDS("No se puede modificar un documento si no tiene tipo de uso de persistencia");
    		}
    	}

    	// Obtenemos documento y realizamos cambios
    	DocumentoRDS doc = this.consultarDocumentoImpl(ref,false);
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
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public void asociarFirmaDocumento(ReferenciaRDS refRds,FirmaIntf firma) throws ExcepcionRDS {
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
        	DocumentoRDS documento = this.consultarDocumentoImpl(refRds,true);

    		if ( !this.verificarFirma( documento.getDatosFichero(), firma ) )
    		{
    			throw new ExcepcionRDS( "Error al verificar la firma del documento" );
    		}

        	Firma fir = new Firma();
        	fir.setFirma( bytesFirma );
        	fir.setFormato(firma.getFormatoFirma());
        	doc.addFirma(fir);
        	session.update(doc);

        	//Si el documento no tenia firmas creamos el array de firmas y la añadimos
        	//en caso contrario si ya existia la añadimos una vez añadida llamamos a custodiar
        	//documento

        	if(documento.getFirmas() == null){
        		FirmaIntf[] firmasIntf = {firma};
        		documento.setFirmas(firmasIntf);
        	}else{
        		FirmaIntf[] firmasIntf = new FirmaIntf[documento.getFirmas().length+1];
        		for(int i=0;i<documento.getFirmas().length;i++){
        			firmasIntf[i] = documento.getFirmas()[i];
        		}
        		firmasIntf[documento.getFirmas().length] = firma;
        		documento.setFirmas(firmasIntf);
        	}
        	custodiarDocumento(documento,doc,session);



        } catch (HibernateException he) {
        	log.error("Error asociando firma a documento",he);
            throw new ExcepcionRDS("Error asociando firma a documento",he);
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
     * @ejb.permission role-name="${role.todos}"
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
            throw new ExcepcionRDS("Error insertando uso",he);
        } finally {
            close(session);
        }

        this.doLogOperacion(getUsuario(),NUEVO_USO,"creación uso " + usoRDS.getTipoUso() + " para documento " + usoRDS.getReferenciaRDS().getCodigo() + "( referencia: " + usoRDS.getReferencia() + ")");
    }

    /**
     * Consulta un documento del RDS (datos del documento y fichero asociado)
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumento(ReferenciaRDS refRds)  throws ExcepcionRDS{
    	DocumentoRDS doc = consultarDocumentoImpl(refRds,true);
    	this.doLogOperacion(getUsuario(),CONSULTAR_DOCUMENTO,"consulta documento " + refRds.getCodigo() );
    	return doc;
    }

    /**
     * Consulta un documento del RDS. Permite indicar si sólo se recuperan los datos del documento o también el fichero asociado
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumento(ReferenciaRDS refRds,boolean recuperarFichero) throws ExcepcionRDS {
       	DocumentoRDS documentoRDS = consultarDocumentoImpl(refRds,
				recuperarFichero);
        this.doLogOperacion(getUsuario(),CONSULTAR_DOCUMENTO,"consulta documento " + refRds.getCodigo() );
        return documentoRDS;
    }

    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla.
     * Se mete parametro adicional para forzar que no meta la marca de agua de "Sin validez" en caso de que sea un documento que tenga sello de registro.
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumentoFormateadoRegistro(ReferenciaRDS refRds) throws ExcepcionRDS {
    	return consultarDocumentoFormateadoImpl(refRds,null,null, true);
    }

    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds) throws ExcepcionRDS {
    	return consultarDocumentoFormateadoImpl(refRds,null,null, false);
    }

    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds,String idioma) throws ExcepcionRDS {
    	return consultarDocumentoFormateadoImpl(refRds,null,idioma, false);
    }


    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     * generando una copia para el interesado y otra para la administracion
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
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
	        throw new ExcepcionRDS("No se ha podido obtener documento formateado ",he);
	    }
    }



    /**
     * Consulta un documento del RDS de tipo estructurado formateado con una plantilla
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public DocumentoRDS consultarDocumentoFormateado(ReferenciaRDS refRds,String tipoPlantilla,String idioma) throws ExcepcionRDS {
    	return consultarDocumentoFormateadoImpl(refRds, tipoPlantilla, idioma, false);

    }

	private DocumentoRDS consultarDocumentoFormateadoImpl(ReferenciaRDS refRds,
			String tipoPlantilla, String idioma, boolean paraRegistro) throws ExcepcionRDS {
		Session session = getSession();
    	DocumentoRDS documentoRDS;
	    try {
	    	// Realizamos consulta doc RDS
	    	documentoRDS = consultarDocumentoImpl(refRds, true);

	    	// Si no es estructurado devolvemos documento sin formatear
	    	if (!documentoRDS.isEstructurado()) return documentoRDS;

	    	// Obtenemos documento de bd y verificamos clave
	    	Documento documento = (Documento) session.load(Documento.class, new Long(refRds.getCodigo()));

	    	// Establecemos idioma de formateo: si no se especifica nada cogemos el de creacion del documento y si no esta alimentado ca
	    	if (idioma == null){
	    		idioma = documento.getIdioma();
	    		if (idioma == null){
	    			idioma = "ca";
	    		}
	    	}

	    	// Si no se especifica plantilla, comprobamos si el doc tiene una específica y sino cogemos la por defecto
	    	PlantillaIdioma plantilla = null;
	    	if (tipoPlantilla==null){
	    		// Si el doc no tiene plantillas, devolvemos el doc original
	    		if (documento.getVersion().getPlantillas().size() <= 0){
		    		return documentoRDS;
		    	}
	    		// Si el documento tiene una plantilla específica, usamos dicha plantilla
		    	if (documento.getPlantilla() != null ){
		    		plantilla = (PlantillaIdioma) documento.getPlantilla().getTraduccion(idioma);
		    	}else{
		    		// Si no tiene plantilla específica, buscamos plantilla por defecto
			    	for (Iterator it = documento.getVersion().getPlantillas().iterator();it.hasNext();){
			    		Plantilla p = (Plantilla) it.next();
			    		if (p.getDefecto() == 'S'){
			    			plantilla = (PlantillaIdioma) p.getTraduccion(idioma);
			    			break;
			    		}
			    	}
			    	// Si no hay una marcada por defecto, cogemos la primera
			    	if (plantilla == null) {
			    		plantilla =  (PlantillaIdioma) (
			    						((Plantilla) documento.getVersion().getPlantillas().iterator().next()).getTraduccion(idioma)
			    					 );
			    	}
		    	}
	    	}else{
	    	// Si se especifica una plantilla usamos dicha plantilla
	    		for (Iterator it = documento.getVersion().getPlantillas().iterator();it.hasNext();){
		    		Plantilla p = (Plantilla) it.next();
		    		if (p.getTipo().equals(tipoPlantilla)){
		    			plantilla = (PlantillaIdioma) p.getTraduccion(idioma);
		    			break;
		    		}
		    	}
	    	}

	    	if (plantilla == null) {
	    		throw new Exception("No existe plantilla para documento modelo " + documentoRDS.getModelo() + " version " + documentoRDS.getVersion() + " idioma " + idioma);
	    	}

	    	// Formateamos documento
	    	DocumentoRDS docFormateado = formatearDocumentoImpl(documentoRDS,plantilla, paraRegistro);

	    	// Realizamos apunte en el log de operaciones
	    	this.doLogOperacion(getUsuario(),CONSULTAR_DOCUMENTO_FORMATEADO,"consulta documento formateado " + refRds.getCodigo() );

	    	// Devolvemos documento RDS formateado
	    	return docFormateado;

	    } catch (Exception he) {
	    	log.error("No se ha podido obtener documento formateado ",he);
	        throw new ExcepcionRDS("No se ha podido obtener documento formateado ",he);
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
    * @ejb.permission role-name="${role.todos}"
    * @ejb.permission role-name="${role.auto}"
    */
    public DocumentoRDS formatearDocumento(DocumentoRDS documentoRDS,String modelo,int version,String tipoPlantilla,String idioma) throws ExcepcionRDS {
    	try{

    		// El documento no existe en el RDS
    		documentoRDS.setReferenciaRDS(null);

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
	    		throw new Exception("No existe plantilla para documento modelo " + documentoRDS.getModelo() + " version " + documentoRDS.getVersion() + " idioma " + idioma);
	    	}

	    	// Formateamos documento
	    	DocumentoRDS docFormateado = formatearDocumentoImpl(documentoRDS,plantilla, false);

		    // Devolvemos documento RDS formateado
		    return docFormateado;

	    } catch (Exception he) {
	    	log.error("No se ha podido formatear documento ",he);
	        throw new ExcepcionRDS("No se ha podido formatear documento ",he);
	    }
    }

    /**
     * Elimina uso para un documento del RDS
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
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
	    	log.error("No se ha podido eliminar uso", he);
	        throw new ExcepcionRDS("No se ha podido eliminar uso", he);
	    } finally {
	        close(session);
	    }

	    this.doLogOperacion(getUsuario(),ELIMINAR_USO,"eliminar uso " + usoRDS.getTipoUso() + " para documento " + usoRDS.getReferenciaRDS().getCodigo() + " ( referencia = " + usoRDS.getReferencia() + ")");
    }

    /**
     * Eliminar usos que tienen una determinada referencia para varios documentos del RDS
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
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
	    	log.error("No se ha podido eliminar usos documento",he);
	        throw new ExcepcionRDS("No se ha podido eliminar usos documento",he);
	    } finally {
	        close(session);
	    }

	    this.doLogOperacion(getUsuario(),ELIMININAR_USOS,"eliminar usos " + tipoUsoId + " ( referencia = " + referencia + ")");
    }

    /**
     * Consulta usos para un documento del RDS
     *
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     * @ejb.permission role-name="${role.auto}"
     */
    public List listarUsos(ReferenciaRDS refRDS) throws ExcepcionRDS {
    	List listaUsosRDS = listarUsosDocumento(refRDS);
	    this.doLogOperacion(getUsuario(),LISTAR_USOS,"listar usos documento " + refRDS.getCodigo());
	    return listaUsosRDS;
    }


    /**
     * Verifica documento formateado generado por la plataforma (con localizador).
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public DocumentoVerifier verificarDocumento(KeyVerifier key) throws ExcepcionRDS {
    	try{
	    	// Obtenemos referencia RDS a partir del codigo
	    	ReferenciaRDS referenciaRDS = ResolveRDS.getInstance().resuelveRDS(key.getIdDocumento().longValue());

	    	// Comprobamos si la clave codificada coincide
	    	if (!key.verifyClaveRDS(referenciaRDS.getClave())) throw new ExcepcionRDS("Clave de acceso incorrecta");

	    	// Generamos documento verificado
	       	DocumentoVerifier docVer = verificarDocumentoImpl(referenciaRDS,
	       			key.getPlantillaDocumento(), key.getIdiomaDocumento());

	        return docVer;
    	}catch(Exception ex){
    		throw new ExcepcionRDS("Error al verificar documento",ex);
    	}
    }


    /**
     * Verifica documento formateado generado por la plataforma (con CSV).
     * @ejb.interface-method
     * @ejb.permission role-name="${role.todos}"
     */
    public DocumentoVerifier verificarDocumento(String csv) throws ExcepcionRDS {
    	try{

    		// Obtenemos documento a partir CSV
	    	ReferenciaRDS referenciaRDS = consultarCsvDocumento(csv);
	    	if (referenciaRDS == null) {
	    		throw new ExcepcionRDS("No existe documento con csv: " + csv);
	    	}

	    	// Generamos documento verificado
	       	DocumentoVerifier docVer = verificarDocumentoImpl(referenciaRDS,
	       			null, null);

	        return docVer;
    	}catch(Exception ex){
    		throw new ExcepcionRDS("Error al verificar documento",ex);
    	}
    }

    /**
     * Cambia de UA un documento
     *
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     */
    public void cambiarUnidadAdministrativa(ReferenciaRDS refRDS, Long codUA) throws ExcepcionRDS {
    	Session session = getSession();
    	try{
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

    		// Modificamos UA
    		documento.setUnidadAdministrativa(codUA);

    		session.update(documento);

    	}catch(Exception ex){
    		throw new ExcepcionRDS("Error al cambiar UA del documento",ex);
    	}finally {
	        close(session);
	    }
    }


    /**
     * Convierte un fichero a PDF/A. Debe tener una extensión permitida: "doc","docx","ppt","xls","odt","jpg","txt"
     *
     *
     *
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     */
    public byte[] convertirFicheroAPDF(byte[] documento,String extension) throws ExcepcionRDS {
    	try{
    		// Verificamos si la extension se puede convertir
    		if (!verificarExtensionConversionPDF(extension)) {
    			throw new Exception("No se permite la conversion a PDF para la extension " + extension.toLowerCase() );
    		}

    		// Verficamos si se ha especificado la info de conexion
    		if ( StringUtils.isBlank(OPENOFFICE_HOST) || StringUtils.isBlank(OPENOFFICE_PUERTO) ) {
    			throw new Exception("No se ha configurado los parametros de conexion al OpenOffice");
    		}

    		// Realizamos conversion
    		ConversorOpenOffice cof = new ConversorOpenOffice(OPENOFFICE_HOST,OPENOFFICE_PUERTO);
    		byte[] documentoConvertido = cof.convertirFitxer(documento,extension.toLowerCase(),"pdf");
    		return documentoConvertido;
    	}catch(Exception ex){
    		throw new ExcepcionRDS("Error al convertir documento a PDF",ex);
	    }
    }

    /**
     * Consolida documento en gestion documental
     * @param refRDS Referencia RDS
     * @return String Referencia del documento en el Gestor Documental
     * @throws ExcepcionRDS
     *
     * @ejb.interface-method
     * @ejb.permission role-name = "${role.todos}"
     * @ejb.permission role-name = "${role.auto}"
     */
    public String consolidarDocumento(ReferenciaRDS refRDS) throws ExcepcionRDS{
    	boolean logError = true;
    	try{
    		// Si no hay gestion documental retornamos nulo
	    	if (!existeGestionDocumental){
	    		return null;
	    	}
	    	// Consultamos doc y usos asociados
    		DocumentoRDS doc = this.consultarDocumentoImpl(refRDS, true);
    		// Si esta consolidado devolvemos referencia
    		if (doc.getReferenciaGestorDocumental() != null){
    			return doc.getReferenciaGestorDocumental();
    		}
    		List usos = this.listarUsosDocumento(refRDS);

    		// Si no tiene uso, no se tiene que consolidar
    		if (usos.size() == 0){
    			throw new ExcepcionRDS("No se puede consolidar un documento si no tiene usos");
    		}

    		// Solo se pueden consolidar documentos de tramites que se hayan completado (tramites en persistencia
    		// y preregistros no confirmados)
    		boolean consolidar=false;
    		for (Iterator it=usos.iterator();it.hasNext();){
        		UsoRDS uso = (UsoRDS) it.next();
        		if (!uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_TRAMITEPERSISTENTE) &&
        			!uso.getTipoUso().equals(ConstantesRDS.TIPOUSO_PREREGISTRO)){
        			consolidar=true;
        			break;
        		}
        	}
    		if (!consolidar){
    			logError = false;
    			throw new Exception("No puede consolidarse el documento ya que el tramite no se ha completado");
    		}

    		// Consolidamos en Gestor Documental
    		Session session = getSession();
    		String referenciaGD = null;
    		try{
    			// Controlamos que no se intente consolidar a la vez el mismo documento
        		if (!CacheSincronizacionGestorDocumental.guardar(Long.toString(refRDS.getCodigo()))){
        			logError = false;
        			throw new Exception("El documento " + refRDS.getCodigo() + " esta siendo consolidado en estos momentos");
        		}
        		// Invocamos al plugin
        		referenciaGD = PluginFactory.getInstance().getPluginGestionDocumental().consolidarDocumento(doc,usos);
        		// Actualizamos documento
        		Documento documento = (Documento) session.load(Documento.class, new Long(refRDS.getCodigo()));
            	documento.setReferenciaGestorDocumental(referenciaGD);
            	session.update(documento);
            }finally{
            	CacheSincronizacionGestorDocumental.borrar(Long.toString(refRDS.getCodigo()));
            	close(session);
            }

	        return referenciaGD;

    	}catch(Exception ex){
    		if (logError){
    			doLogErrorGestorDocumental(ex,new Long(refRDS.getCodigo()),getUsuario());
    		}
	    	throw new ExcepcionRDS("Error al consolidar documento " + refRDS.getCodigo() + " en gestion documental",ex);
	    }
    }

    // ---------------------- Funciones auxiliares -------------------------------------------
    /**
     *  Guarda documento en el RDS
     */
    private ReferenciaRDS grabarDocumento(DocumentoRDS documentoRDS,boolean nuevo) throws ExcepcionRDS{
    	Session session = getSession();
    	Documento documentoH;
        try {
        	// Si no es nuevo recuperamos documentos
        	if (nuevo){
        		documentoH = new Documento();
        	}else{
		    	documentoH = (Documento) session.load(Documento.class, new Long(documentoRDS.getReferenciaRDS().getCodigo()));
		    	if ("S".equals(documentoH.getBorrado())) throw new ExcepcionRDS("El documento " + documentoRDS.getReferenciaRDS().getCodigo() + " ha sido borrado por no tener usos" );
        	}

        	// Establecemos campos documento
        	establecerCamposDocumento(documentoH,documentoRDS,nuevo);

        	// Realizamos salvado
        	if (nuevo) {
        		session.save(documentoH);
        	}else{
        		session.update(documentoH);
        	}

        	//Una vez se ha modificado el documento tambien lo modidifcamos en custodia,
        	//si hace falta
        	custodiarDocumento(documentoRDS,documentoH,session);

        } catch (HibernateException he) {
        	log.error("Error insertando documento",he);
            throw new ExcepcionRDS("Error insertando documento",he);
        } catch (Exception e) {
        	log.error("Error insertando documento",e);
            throw new ExcepcionRDS("Error insertando documento",e);
		} finally {
            close(session);
        }

        // Guardamos fichero
        try{
        	MetadaAlmacenamiento metadataFichero = new MetadaAlmacenamiento();
        	metadataFichero.setModelo(documentoRDS.getModelo());
        	metadataFichero.setVersion(documentoRDS.getVersion());
        	metadataFichero.setDescripcion(documentoRDS.getTitulo());
        	metadataFichero.setExtension(documentoRDS.getExtensionFichero());
        	metadataFichero.setFecha(documentoRDS.getFechaRDS());
        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(documentoH.getUbicacion());
        	plugin.guardarFichero(documentoH.getCodigo(),documentoRDS.getDatosFichero(), metadataFichero);
        }catch(Exception e){
        	log.error("No se ha podido guardar fichero en ubicación " + documentoRDS.getCodigoUbicacion(),e);
        	throw new ExcepcionRDS("Error al guardar fichero",e);
        }

	    // Devolvemos referencia
	    ReferenciaRDS ref = new ReferenciaRDS();
	    ref.setCodigo(documentoH.getCodigo().longValue());
	    ref.setClave(documentoH.getClave());
	    return ref;
    }

    /**
     * Sincroniza con custodia los documentos
     * @throws HibernateException
     */
    private void custodiarDocumento(DocumentoRDS documento, Documento doc, Session session) throws HibernateException, Exception{
    	//si existe el plugin de custodia y se tiene que custodiar el documento lo custodiamos
    	if(existeCustodia && "S".equals(doc.getVersion().getModelo().getCustodiar()+"")){

    		log.debug("Custodiando documento del tipo " + doc.getVersion().getModelo().getModelo() + " - " + doc.getVersion().getVersion() );

    		if(doc.getFirmas() != null && doc.getFirmas().size() > 0){
    			log.debug("Documento con firmas, insertamos en custodia");
    			if(documento.getReferenciaRDS() == null){
    				documento.setReferenciaRDS(new ReferenciaRDS(doc.getCodigo().longValue(),doc.getClave()));
    			}
				//Custodiamos el documento
    			PluginCustodiaIntf pluginCustodia = PluginFactory.getInstance().getPluginCustodia();
				String custodia = pluginCustodia.custodiarDocumento(documento);
				//vamos a buscar las versiones de las custodias de este documento que no esten borradas y sean diferentes a si misma si existen.
				List custodiasDocumento = null;
		       	Query query;
				query = session.createQuery("FROM VersionCustodia AS version WHERE version.documento.codigo = :codigoDocumento and version.codigo != :idVersion");
		        query.setParameter("codigoDocumento", doc.getCodigo());
		        query.setParameter("idVersion", custodia);
		        custodiasDocumento = query.list();
				if(custodiasDocumento != null){
					//las marcamos para borrar.
					log.debug("Marcamos custodias anteriores para borrar");
					for(int i=0;i<custodiasDocumento.size();i++){
						VersionCustodia cust = (VersionCustodia)custodiasDocumento.get(i);
						cust.setBorrar('S');
						cust.setFecha(new Date());
						session.update(cust);
					}
				}
				//vamos a buscar la version de la custodia de este documento que tenga el mismo id que estamos entrando y la actualizamos
				VersionCustodia custodiaDocumento = (VersionCustodia) session.get(VersionCustodia.class, custodia);
				log.debug("Custodiamos version actual");
				if(custodiaDocumento == null){
					// la creamos
					custodiaDocumento = new VersionCustodia();
					custodiaDocumento.setCodigo(custodia);
					custodiaDocumento.setDocumento(doc);
					custodiaDocumento.setFecha(new Date());
					custodiaDocumento.setBorrar('N');
					session.save(custodiaDocumento);
				}else{
					//la modificamos
					custodiaDocumento.setDocumento(doc);
					custodiaDocumento.setFecha(new Date());
					custodiaDocumento.setBorrar('N');
					session.update(custodiaDocumento);
				}
			}else{
				//Documento no tiene firmas, si existe id custodia marcarla para borrar
				log.debug("Documento sin firmas, borramos de custodia");
				Query query = session.createQuery("FROM VersionCustodia AS version WHERE version.documento.codigo = :codigoDocumento and version.borrar = 'N'");
				query.setParameter("codigoDocumento", doc.getCodigo());
				List custodiasDocumento = query.list();
				if(custodiasDocumento != null){
					//las marcamos para borrar.
					for(int i=0;i<custodiasDocumento.size();i++){
						VersionCustodia cust = (VersionCustodia)custodiasDocumento.get(i);
						cust.setBorrar('S');
						cust.setFecha(new Date());
						session.update(cust);
					}
				}
			}
    	}
    }

    /**
     * Verifica que están los campos obligatorios
     */
    private void establecerCamposDocumento(Documento documentoH,DocumentoRDS documentoRDS,boolean nuevo) throws ExcepcionRDS{
    	// 1- VERIFICAMOS CAMPOS OBLIGATORIOS
    	if (documentoRDS.getDatosFichero() == null || documentoRDS.getDatosFichero().length <= 0) throw new ExcepcionRDS("No se han establecido los datos del fichero");
    	if (documentoRDS.getExtensionFichero() == null) throw new ExcepcionRDS("No se ha indicado la extensión del fichero");
    	if (documentoRDS.getModelo() == null) throw new ExcepcionRDS("No se ha indicado el modelo del documento");
    	//if (documento.getNif() == null) throw new ExcepcionRDS("No se ha indicado el NIF del usuario que incorpora el documento");
    	if (documentoRDS.getNombreFichero() == null) throw new ExcepcionRDS("No se ha indicado el nombre del fichero");
    	if (documentoRDS.getTitulo() == null) throw new ExcepcionRDS("No se ha indicado el título del documento");
    	if (documentoRDS.getUnidadAdministrativa() == -1) throw new ExcepcionRDS("No se ha indicado la Unidad Administrativa responsable del documento");
    	if (documentoRDS.getVersion() == -1) throw new ExcepcionRDS("No se ha indicado la versión del documento");

    	// 2- OBTENEMOS VERSION Y UBICACION
    	// -------- Obtenemos version
    	Version version;
    	try{
    		VersionDelegate vd = DelegateUtil.getVersionDelegate();
    		version = vd.obtenerVersion(documentoRDS.getModelo(),documentoRDS.getVersion());
    		if (version == null) {
    			log.error("No existe versión " + documentoRDS.getModelo() + " - " + documentoRDS.getVersion());
    			throw new ExcepcionRDS("No existe modelo/version en RDS: " + documentoRDS.getModelo() + " / " + documentoRDS.getVersion());
    		}
    	}catch (Exception e){
    		log.error("No se ha podido obtener versión " + documentoRDS.getModelo() + " - " + documentoRDS.getVersion(),e);
    		throw new ExcepcionRDS("No se ha podido obtener modelo / version en RDS",e);
    	}

    	// --------- Obtenemos ubicación
    	Ubicacion ubicacion;
    	try{
    		UbicacionDelegate ud = DelegateUtil.getUbicacionDelegate();
    		if (nuevo) {
    			ubicacion = ud.obtenerUbicacionDefecto();
    			if (ubicacion == null) {
        			throw new ExcepcionRDS("No existe una ubicación por defecto configurada en el RDS");
        		}
        	} else {
	    		ubicacion = ud.obtenerUbicacion(documentoH.getUbicacion().getCodigoUbicacion());
	    		if (ubicacion == null) {
	    			throw new ExcepcionRDS("No existe ubicación en RDS: " + documentoRDS.getCodigoUbicacion());
	    		}
        	}
    		documentoRDS.setCodigoUbicacion(ubicacion.getCodigoUbicacion());
    	}catch (Exception e){
    		log.error("No se ha podido establecer ubicación", e);
    		throw new ExcepcionRDS("No se ha podido establecer ubicación en RDS",e);
    	}

    	// 3- ESTABLECEMOS PLANTILLA ESPECIFICA
    	if (StringUtils.isNotEmpty(documentoRDS.getPlantilla())){
	    	try{
	    		Plantilla plantilla;
	    		PlantillaDelegate pl = DelegateUtil.getPlantillaDelegate();
	    		plantilla = pl.obtenerPlantilla(version,documentoRDS.getPlantilla());
	    		if (plantilla == null) {
	    			log.error("No existe plantilla " + documentoRDS.getPlantilla() + " para modelo " + version.getModelo() + " - version " + version.getVersion());
	    			throw new ExcepcionRDS("No existe plantilla " + documentoRDS.getPlantilla() + " para modelo " + version.getModelo() + " - version " + version.getVersion()) ;
	    		}
	    		documentoH.setPlantilla(plantilla);
	    	}catch (Exception e){
	    		log.error("No se ha podido obtener plantilla especifica " + documentoRDS.getPlantilla(),e);
	    		throw new ExcepcionRDS("No se ha podido obtener plantilla especifica ",e);
	    	}
    	}else{
    		documentoH.setPlantilla(null);
    	}

    	// 4- ESTABLECEMOS CAMPOS EN DOCUMENTO
    	documentoH.setVersion(version);
    	// ---- Si el documento no es nuevo no dejamos cambiar de ubicación
    	if (!nuevo){
	    	if (ubicacion.getCodigo().longValue() != documentoH.getUbicacion().getCodigo().longValue()){
	    		throw new ExcepcionRDS("No se permite cambiar de ubicación al actualizar documento");
	    	}
    	}else{
    		documentoH.setUbicacion(ubicacion);
    	}
    	// 	---- Si no es nuevo comprobamos que la clave coincida
    	if (!nuevo){
    		if (documentoRDS.getReferenciaRDS() == null) throw new ExcepcionRDS("No se ha indicado la referencia RDS");
    		// Comprobamos que la clave coincida
	    	if (!documentoH.getClave().equals(documentoRDS.getReferenciaRDS().getClave())){
	    		throw new ExcepcionRDS("La clave no coincide");
	    	}
    	}
    	documentoH.setTitulo(documentoRDS.getTitulo());

    	// Normalizamos NIEs
    	if (documentoRDS.getNif() != null){
    		documentoH.setNif(documentoRDS.getNif().replaceAll("-",""));
    	}

    	documentoH.setUsuarioSeycon(documentoRDS.getUsuarioSeycon());
    	documentoH.setUnidadAdministrativa(new Long(documentoRDS.getUnidadAdministrativa()));
    	documentoH.setNombreFichero(documentoRDS.getNombreFichero());
    	documentoH.setExtensionFichero(documentoRDS.getExtensionFichero());
    	// --- Firmas documento
    	// ----------- Si no es nuevo eliminamos firmas anteriores
    	if (!nuevo){
    		documentoH.getFirmas().removeAll(documentoH.getFirmas());
    	}
    	// ------------ Establecemos nuevas firmas (verificandolas)
    	if (documentoRDS.getFirmas() != null){
	    	for (int i=0;i<documentoRDS.getFirmas().length;i++){
	    		// Verificamos firma
	    		// Añadimos firma
	    		Firma firma = new Firma();
	    		FirmaIntf signature = documentoRDS.getFirmas()[i];
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
	    		if ( !this.verificarFirma( documentoRDS.getDatosFichero(), signature ) )
	    		{
	    			throw new ExcepcionRDS( "Error al verificar la firma del documento" );
	    		}
	    		documentoH.addFirma(firma);
	    	}
    	}

    	// Idioma visualizacion: solo para docs estructurados
    	if (version.getModelo().getEstructurado() == 'S'){
    		//Si el modelo es estructurado, debemos indicar el idioma
        	if (documentoRDS.getIdioma() == null){
        		// Por motivos de compatibilidad con integraciones ejb anteriores, ponemos idioma por defecto
        		documentoH.setIdioma("ca");
        		// throw new ExcepcionRDS("Si el documento es estructurado debe indicarse el idioma de visualizacion del documento");
        	}else{
        		documentoH.setIdioma(documentoRDS.getIdioma());
        	}
    	}


    	// --- Establecemos campos calculados por RDS ---
    	// ------- Establecemos fecha
    	documentoH.setFecha(new Timestamp(System.currentTimeMillis()));
    	// ------- Calculamos hash
    	try{
    		documentoH.setHashFichero(generaHash(documentoRDS.getDatosFichero()));
    	}catch (Exception e){
    		log.error("No se ha podido calcular el hash",e);
    		throw new ExcepcionRDS("No se ha podido calcular el hash",e);
    	}
    	// ------- Generamos clave
    	if (nuevo) {
    		documentoH.setClave(generarClave());
    	}
    	// ------- Generamos CSV en caso de ser nuevo
    	if (nuevo && USAR_CSV) {
    		documentoH.setCsv(generarCSV());
    	}

    }


    /**
     * Obtiene plugin almacenamiento
     * @param classNamePlugin
     * @return
     */
    private PluginAlmacenamientoRDS obtenerPluginAlmacenamiento(Ubicacion ubicacion) throws Exception{
    	return PluginClassCache.getInstance().getPluginAlmacenamientoRDS(ubicacion);
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
    	documentoRDS.setIdioma(doc.getIdioma());
    	documentoRDS.setReferenciaGestorDocumental(doc.getReferenciaGestorDocumental());
    	documentoRDS.setCsv(doc.getCsv());
    	documentoRDS.setPlantillaVisualizacion(doc.getVersion().getPlantillas().size() > 0);

    	VersionCustodiaDelegate vcDelg = DelegateUtil.getVersionCustodiaDelegate();
    	try {
			documentoRDS.setCodigoDocumentoCustodia(vcDelg.obtenerCodigoVersionCustodia(doc.getCodigo()));
		} catch (Exception e) {
			throw new ExcepcionRDS( "Error obteniendo el codigo de custodia desde el codigo del documento.", e );
		}


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
    	Ubicacion ubicacion;
	    Query query = session.createQuery("FROM Uso AS u WHERE u.documento = :documento");
	    query.setParameter("documento", documento);
	    //query.setCacheable(true);
	    List result = query.list();
	    if (result.isEmpty() && !documentoEnCustodia(session, documento)) {

	    	// Obtenemos plugin almacenamiento
	    	ubicacion = documento.getUbicacion();

	    	// Borramos documento
	        session.delete(documento);

	        // Borramos fichero asociado
	        try{
	        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(ubicacion);
	        	plugin.eliminarFichero(documento.getCodigo());
	        }catch(Exception e){
	        	log.error("No se ha podido eliminar fichero en ubicación " + ubicacion.getCodigoUbicacion(),e);
	        	throw new ExcepcionRDS("Error al eliminar fichero",e);
	        }

	        // Realizamos apunte en el log
	        doLogOperacionImpl(getUsuario(),BORRADO_AUTOMATICO_DOCUMENTO_SIN_USOS,"Borrado automático de documento " + documento.getCodigo() + " por no tener usos",session);
	    }
    }

    //Funcion que se llama desde documentoSinUsos para poder eliminar el documentos
    //si el documento esta en custodia lo marca para eliminar de custodia
    //y te devuelve que no se puede eliminar
    private boolean documentoEnCustodia(Session session,Documento documento) throws Exception{
    	Query query = session.createQuery("FROM VersionCustodia AS version WHERE version.documento.codigo = :codigoDocumento");
		query.setParameter("codigoDocumento", documento.getCodigo());
		List custodiasDocumento = query.list();
		if(custodiasDocumento != null && custodiasDocumento.size() > 0){
			//las marcamos para borrar.
			for(int i=0;i<custodiasDocumento.size();i++){
				VersionCustodia cust = (VersionCustodia)custodiasDocumento.get(i);
				if("N".equals(cust.getBorrar()+"")){
					cust.setBorrar('S');
					cust.setFecha(new Date());
					session.update(cust);
				}
			}
			return true;
		}
		return false;

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
    	// Vemos si el doc tiene que usar csv o localizador
    	boolean tieneCSV = USAR_CSV &&  StringUtils.isNotBlank(doc.getCsv());
    	// Obtenemos url verificacion
		String url = (tieneCSV ? URL_CSV : URL_VERIFIER);
		String urlVerificacion = (tieneCSV ? url + doc.getCsv() : url + key.getKeyEncoded());


		String text = TEXT_VERIFIER;
		doc.setUrlVerificacion(urlVerificacion);

    	int numStamps = 3;
    	float posTitulo = 44;
    	if (!BARCODE_VERIFIER_MOSTRAR) {
    		numStamps = 2;
    		posTitulo = 23;
    	}

    	// Realizamos stamp
		ObjectStamp stamps [] = new ObjectStamp[numStamps];

		if (BARCODE_VERIFIER_MOSTRAR) {
			BarcodeStamp bc = new BarcodeStamp();
			bc.setTexto(urlVerificacion);
			//bc.setTexto("12345");
			bc.setTipo(BarcodeStamp.BARCODE_PDF417);
			bc.setPage(0);
			bc.setX(340);
			bc.setY(19);
			bc.setRotation(0);
			bc.setOverContent(true);
			bc.setXScale(new Float(100));
			bc.setYScale(new Float(100));
			stamps[0] = bc;
		}

		TextoStamp tx = new TextoStamp();
		tx.setTexto(urlVerificacion);
		tx.setFontName("Helvetica-Bold");
		tx.setFontSize(7);
		tx.setX(280);
		tx.setY(13);
		stamps[numStamps - 2] = tx;


		TextoStamp tx2 = new TextoStamp();
		tx2.setTexto(text);
		tx2.setFontSize(6);
		tx2.setX(320);
		tx2.setY(posTitulo);
		stamps[numStamps - 1] = tx2;


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
    	int x = 330;
    	if (usoSello.getReferencia().startsWith("PRE")){
    		txtNumRegistro = "Num. Preregistre: "+usoSello.getReferencia();
    		txtDC="DC:" + StringUtil.calculaDC(usoSello.getReferencia());
    		txtData="Data Preregistre: ";

    		// Calculo posicion inicio
    		int longPre = 35;
    		int iniPre = 385;
    		int inc = ((txtNumRegistro + " " + txtDC).length() - longPre) / 2;
    		x = iniPre;
    		if (inc > 0) {
    			x = iniPre - (inc * 10);
    		}

    		numText++;
    	}else{
    		String txtPrefixNumRegistro = "Num. Registre: ";
    		txtData="Data Registre: ";
    		if (ConstantesRDS.TIPOUSO_ENVIO.equals(usoSello.getTipoUso())) {
    			txtPrefixNumRegistro = "Num. Enviament: ";
    			txtData="Data Enviament: ";
    		}
    		txtNumRegistro = txtPrefixNumRegistro + usoSello.getReferencia();
    		txtDC="";
    	}
    	if (usoSello.getFechaSello() != null) {
    		numText++;
    	}

    	// Creamos textos a stampar
    	textos = new ObjectStamp[numText];

    		// Texto xa num registro
    	textos[0] = new TextoStamp();
		((TextoStamp) textos[0]).setTexto(txtNumRegistro + (txtDC.length() > 0? " " + txtDC : ""));
		textos[0].setPage(0);
		textos[0].setX(x);
		textos[0].setY(805);
		textos[0].setOverContent(true);

			// Texto xa fecha registro
		if (usoSello.getFechaSello() != null) {
			numText--;
			textos[numText] = new TextoStamp();
			((TextoStamp) textos[numText]).setTexto(txtData + StringUtil.fechaACadena(usoSello.getFechaSello(),"dd/MM/yyyy HH:mm"));
			textos[numText].setPage(0);
			textos[numText].setX(x);
			textos[numText].setY(815);
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


    /**
     * Verifica si la extension puede convertirse a PDF
     * @param extension
     * @return
     */
    private boolean verificarExtensionConversionPDF(String extension){
    	String [] extensiones = {"doc","docx","ppt","xls","odt","jpg","txt"};
		for (int i=0;i<extensiones.length;i++){
			if (extension.toLowerCase().equals(extensiones[i])){
				return true;
			}
		}
	    return false;
    }


    /**
     * Formatea el fichero en base a la plantilla
     *
     * @param documentoRDS  Documento a formatear
     * @param plantilla Plantilla con la que se debe formatear
     * @param paraRegistro Si es para registro y no debe meter el sense validesa
     * @param usos Usos del documento por si es necesario stampar sello de registro
     * @throws Exception
     */
    private DocumentoRDS formatearDocumentoImpl(DocumentoRDS documentoRDS, PlantillaIdioma plantilla, boolean paraRegistro) throws Exception{

    	// Comprobaciones formateador
    	if (plantilla == null){
    		throw new Exception("No existe plantilla para documento modelo " + documentoRDS.getModelo() + " version " + documentoRDS.getVersion());
    	}

    	// Consultamos usos del documento
    	List usos = null;
    	if (documentoRDS.getReferenciaRDS() != null){
    		// Documento existente en RDS, obtenemos sus usos
    		usos = listarUsosDocumento(documentoRDS.getReferenciaRDS());
    	}else{
    		// Documento no existente en el RDS. Usado para formatear docs que no existen en el RDS.
    		usos = new ArrayList();
    	}

    	// Generamos PDF
    	FormateadorDocumento format = FormateadorDocumentoFactory.getInstance().getFormateador( plantilla.getPlantilla().getFormateador().getClase() );
    	DocumentoRDS docFormateado = format.formatearDocumento(documentoRDS,plantilla,usos);

    	// En caso de que se haya establecido generar sello de registro/preregistro (envio/preenvio) lo generamos
    	boolean docValido=true;
    	if (plantilla.getPlantilla().getSello() == 'S'){
    		// Si no hay sello marcamos como doc no valido -> no barcode + marca de agua (EXCEPTO SI ES PARA REGISTRO, QUE NO LO TENDRA TODAVIA)
    		if (!stampSello(docFormateado,usos) && !paraRegistro ) docValido=false;
    	}

    	//	En caso de que se haya establecido generar codigo de barras lo generamos (el documento debe existir en
    	//  el RDS)
    	if (documentoRDS.getReferenciaRDS() != null && plantilla.getPlantilla().getBarcode() == 'S' && docValido){
    		stampBarCodeVerifier(docFormateado,plantilla.getPlantilla().getTipo(),plantilla.getIdioma());
    	}

    	// En caso de que no sea produccion lo marcamos como borrador
    	if (isBorrador()  || !docValido){
    		stampBorrador(docFormateado);
    	}

    	return docFormateado;
    }

    private void doLogErrorGestorDocumental(Exception excepcion, Long codigoDoc, String usuarioSeycon){
    	try{
			LogGestorDocumentalError logError = new LogGestorDocumentalError();
			logError.setDescripcionError(excepcion.getMessage());
			Documento doc = new Documento();
			doc.setCodigo(codigoDoc);
			logError.setDocumento(doc);
			String error = es.caib.util.StringUtil.stackTraceToString(excepcion);
			if (error.length() > 4000){
				error = error.substring(0,4000);
			}
			logError.setError(error.getBytes(ConstantesXML.ENCODING));
			logError.setFecha(new Timestamp(System.currentTimeMillis()));
			logError.setUsuarioSeycon(usuarioSeycon);
			LogGestorDocumentalErroresDelegate logDelegate = DelegateUtil.getLogErrorGestorDocumentalDelegate();
			logDelegate.grabarError(logError);
		}catch (Exception e){
    		log.error("No se ha podido guardar en el logGestorDocumentalErrores",e);
    	}
    }

    /**
     * Consulta usos documento.
     * @param refRDS
     * @return usos documento.
     * @throws ExcepcionRDS
     */
	private List listarUsosDocumento(ReferenciaRDS refRDS) throws ExcepcionRDS {
		List listaUsosRDS = null;
		Session session = getSession();
    	try {

	    	// Comprobamos documento
        	Documento documento;
        	try{
        		documento = (Documento) session.load(Documento.class,new Long(refRDS.getCodigo()));
        	}catch(Exception e){
        		//si no existe el documento devolvemos un arraylist vacio.
        		return new ArrayList();
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
            	usoRDS.setFechaUso(uso.getFecha());
            	listaUsosRDS.add(usoRDS);
            }

	    } catch (HibernateException he) {
	    	log.error("Error al listar usos",he);
	        throw new ExcepcionRDS("Error al listar usos" ,he);
	    } finally {
	        close(session);
	    }
		return listaUsosRDS;
	}

	/**
	 * Consulta referencia documento a partir csv.
	 * @param CSV
	 * @return referencia rds o nulo si no lo encuentra
	 * @throws ExcepcionRDS
	 */
	private ReferenciaRDS consultarCsvDocumento(String csv) throws ExcepcionRDS {
		// Obtenemos documento
    	Session session = getSession();
	    try {
	    	// Obtenemos documento
	    	Query query = session.createQuery("FROM Documento AS d WHERE d.csv = :csv");
            query.setParameter("csv", csv);
            Documento doc =  (Documento) query.uniqueResult();
            ReferenciaRDS refRds = null;
            if (doc != null){
            	refRds = new ReferenciaRDS(doc.getCodigo().longValue(), doc.getClave());
            }
            return refRds;
	    } catch (HibernateException he) {
	    	log.error("Error consultando documento a partir CSV",he);
	        throw new ExcepcionRDS("Error consultando documento a partir CSV",he);
	    } finally {
	        close(session);
	    }

	}

	/**
	 * Consulta documento
	 * @param refRds
	 * @param recuperarFichero
	 * @return
	 * @throws ExcepcionRDS
	 */
	private DocumentoRDS consultarDocumentoImpl(ReferenciaRDS refRds,
			boolean recuperarFichero) throws ExcepcionRDS {
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
	    	log.error("Error consultando documento",he);
	        throw new ExcepcionRDS("Error consultando documento",he);
	    } finally {
	        close(session);
	    }

	    // Consultamos fichero asociado
        try{
        	if (recuperarFichero){
        		// Obtenemos documento
	        	PluginAlmacenamientoRDS plugin = obtenerPluginAlmacenamiento(documento.getUbicacion());
	        	documentoRDS.setDatosFichero(plugin.obtenerFichero(documento.getCodigo()));
	        	// Verificamos hash
	        	if (!documentoRDS.getHashFichero().equals(generaHash(documentoRDS.getDatosFichero()))) {
	        		throw new Exception("No coincide el hash del documento almacenado. El fichero ha sido modificado.");
	        	}
        	}
        }catch(Exception e){
        	log.error("No se ha podido obtener fichero en ubicación " + documento.getUbicacion().getCodigoUbicacion(),e);
        	throw new ExcepcionRDS("Error al consultar documento: " + e.getMessage(),e);
        }
		return documentoRDS;
	}

	private DocumentoVerifier verificarDocumentoImpl(
			ReferenciaRDS referenciaRDS, String plantillaDocumento,
			String idiomaDocumento) throws ExcepcionRDS {
		// Obtenemos documento
		DocumentoRDS docRDS = null;
		docRDS = consultarDocumentoImpl(referenciaRDS,true);

		// Formateamos documento
		DocumentoRDS docRDSFormat = consultarDocumentoFormateadoImpl(referenciaRDS,plantillaDocumento,idiomaDocumento, false);

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
	}

	/**
	 * Genera CSV.
	 * @return
	 * @throws ExcepcionRDS
	 */
	private String generarCSV() throws ExcepcionRDS {
		// Caga tabla de trasnformacion y la crea si no existe
		if (!GeneradorCsv.existeTablaTransformacion()) {
			String idTabla = "1";
			Session session = getSession();
		    try {
		    		String claves = null;
		    		TablaTransformacionCsv tablaTransformacion = null;
		    		tablaTransformacion = (TablaTransformacionCsv) session.get(TablaTransformacionCsv.class, idTabla);
		    		if (tablaTransformacion != null) {
		    			log.info("Existe tabla transformacion - cargamos claves");
		    			String clavesCifradas = new String(tablaTransformacion.getClaves(), "UTF-8");
		    			claves = CifradoUtil.descifrar(CLAVE_CIFRADO,clavesCifradas);
		    		} else {
		    			log.info("No existe tabla transformacion - generamos claves");
		    			claves = GeneradorCsv.generarTablaTransformacion();
		    			String clavesCifradas =  CifradoUtil.cifrar(CLAVE_CIFRADO, claves);
		    			tablaTransformacion = new TablaTransformacionCsv();
		    			tablaTransformacion.setCodigo(idTabla);
		    			tablaTransformacion.setClaves(clavesCifradas.getBytes("UTF-8"));
		    			session.save(tablaTransformacion);
		    		}
		    		GeneradorCsv.establecerTablaTransformacion(claves);
		    } catch (Exception he) {
		    	log.error("Error inicializando tabla transformacion CSV",he);
		        throw new ExcepcionRDS("Error inicializando tabla transformacion CSV",he);
		    } finally {
		        close(session);
		    }
		}
	    // Genera CSV
		return GeneradorCsv.generarId();
	}

}
