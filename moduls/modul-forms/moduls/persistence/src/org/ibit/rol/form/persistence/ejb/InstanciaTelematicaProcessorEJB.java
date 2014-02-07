package org.ibit.rol.form.persistence.ejb;
//
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.expression.Expression;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.ibit.rol.form.model.*;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.util.CampoUtils;
import org.ibit.rol.form.persistence.util.PantallaUtils;
import org.ibit.rol.form.persistence.util.ScriptUtil;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;


/**
 * SessionBean para realizar el proceso de rellenar un formulario desde un sistema de tramitación.
 *
 * @ejb.bean
 *  name="form/persistence/InstanciaTelematicaProcessor"
 *  jndi-name="org.ibit.rol.form.persistence.InstanciaTelematicaProcessor"
 *  type="Stateful"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.transaction type="Required"
 * @jboss.container-configuration name="InstanciaProcessor Stateful SessionBean"
 */

public abstract class InstanciaTelematicaProcessorEJB extends InstanciaProcessorEJB{

    protected static final Log log = LogFactory.getLog(InstanciaTelematicaProcessorEJB.class);
    //Mapas para mantener los datos del trámite y los datos genéricos del formulario.
    private Map datosTramite    = new HashMap();
    private Map propiedadesForm = new HashMap();

    //Variables para mantener en memoria los documentos XML de configuración y valores iniciales
    private Document docXMLConfig;
    private Document docXMLIni;

    //String que contiene el documento xml con los datos originales.
    private String XMLValoresIniOriginal = null;

    //Nombre de los tags dentro del documento xml de configuración
    public static final String URL_SIS_TRA_OK                 = "urlSisTraOK";
    public static final String URL_REDIRECCION_OK             = "urlRedireccionOK";
    public static final String URL_SIS_TRA_MANTENIMIENTO_SESION = "urlSisTraMantenimientoSesion";
    public static final String URL_SIS_TRA_CANCEL             = "urlSisTraCancel";
    public static final String URL_REDIRECCION_CANCEL         = "urlRedireccionCancel";
    public static final String CODIGO_PERFIL                  = "codigoPerfil";
    public static final String MODELO                         = "modelo";
    public static final String VERSION                        = "version";
    public static final String IDIOMA                         = "idioma";
    public static final String LAYOUT                         = "layout";
    public static final String NOM_PARAM_TOKEN_RETORNO        = "nomParamTokenRetorno";
    public static final String NOM_PARAM_XML_DATOS_INI        = "nomParamXMLDatosIni";
    public static final String NOM_PARAM_XML_DATOS_FIN        = "nomParamXMLDatosFin";
    public static final String NOM_ATRIB_INDEX_CAMPO_INDEXADO = "nomAtribIndexCampoIndex";

    //Expresiones xPath para obtener información del documento xml de configuración
    public static final String XPATH_DATOS_TRAMITE    = "/configuracion/datos/*";
    public static final String XPATH_PROPIEDADES_FORM = "/configuracion/propiedades/propiedad/*";
    public static final String XPATH_BLOQ_TODOS       = "/configuracion/bloqueo/todos";
    public static final String XPATH_BLOQ_CAMPO       = "/configuracion/bloqueo/xpath";

    //Valor devuelto por el sistema de tramitación al mandarle un get/post.
    private String respuesta;

    /**
     * @ejb.create-method
     * @ejb.permission unchecked="true"
     * 
     * @param XMLDatosConfig-> Documento XML con los datos de configuración del trámite.
     * @param XMLValoresInit-> Documento XML con los valores iniciales para prerellenar los campos del formulario.
     */
    public void ejbCreate(String XMLDatosConfig,
                          String XMLValoresInit) throws CreateException {
        super.ejbCreate();
        session = getReadOnlySession();
        try {
        	
        	if (permitirScriptDebug())
             	logScript = new LogsScripts();
        	
            if ( (XMLDatosConfig == null) || (XMLDatosConfig.trim().length() == 0) ) {
                closeReadOnly(session);
                throw new CreateException("No existe documento xml de configuración");
            }

            XMLValoresIniOriginal = XMLValoresInit;
            if ( (XMLValoresInit == null) || (XMLValoresInit.trim().length() == 0) ) {
                /*
                  No ponerlo a null, ya que no se puede enviar un null como valor de un
                  parámetro en una llamada http.
                */
                XMLValoresIniOriginal = "";
            }

            /*
              Cargo en memoria los documentos xml de configuración y inicialización de los campos.
            */
            cargarDocumentosXML(XMLDatosConfig, XMLValoresIniOriginal);

            /*
              Cargo los datos del trámite por defecto.
            */
            cargarDatosTramiteDefecto();

            /*
              Otengo los datos genéricos del formulario, a partir del fichero XML de configuración 'XMLConfig'.
            */
            cargarDatosTramite(docXMLConfig);
            if (datosTramite == null) {
                closeReadOnly(session);
                throw new CreateException("No existen los datos del tramite en el documento xml de configuracion");
            }

            /*
              Otengo las propiedades del formulario, a partir del fichero XML de inicializacion 'XMLValoresInit'.
            */
            cargarPropiedadesFormulario(docXMLConfig);

            locale = new Locale(obtenerValor(IDIOMA));

            /*
              Obtengo el perfil del Usuario.
            */
            /*Query queryPerfil = session.createQuery("FROM PerfilUsuario AS perfil WHERE perfil.codigoEstandard = :codigoPerfil");
            queryPerfil.setParameter("codigoPerfil", obtenerValor(codigoPerfil));
            queryPerfil.setCacheable(true);
            List perfiles = queryPerfil.list();*/
            Criteria critPerfil = session.createCriteria(PerfilUsuario.class);
            critPerfil.add(Expression.eq("codigoEstandard", obtenerValor(CODIGO_PERFIL)));
            critPerfil.setCacheable(true);
            List perfiles = critPerfil.list();
            if (perfiles.isEmpty()) {
                closeReadOnly(session);
                throw new CreateException("El perfil con código " + obtenerValor(CODIGO_PERFIL) + " no existe");
            }
            perfil = (PerfilUsuario) perfiles.get(0);
            log.debug("Perfil " + obtenerValor(CODIGO_PERFIL) + " cargado");
            /*
              Obtengo el formulario seleccionado.
            */
            /*Query queryForm = session.createQuery("FROM Formulario AS form WHERE form.modelo = :modelo and form.version = :version");
            queryForm.setParameter("modelo", obtenerValor(modelo));
            queryForm.setParameter("version", obtenerValor(version));
            queryForm.setCacheable(true);
            List formularios = queryForm.list();*/
            Criteria critForm = session.createCriteria(Formulario.class);
            critForm.add(Expression.eq("modelo", obtenerValor(MODELO)));
            critForm.add(Expression.eq("version", new Integer(obtenerValor(VERSION))));
            critForm.setCacheable(true);
            List formularios = critForm.list();
            if (formularios.isEmpty()) {
                closeReadOnly(session);
                throw new CreateException("El formulario " + obtenerValor(MODELO) + " versión " +obtenerValor(VERSION)+ " no existe");
            }
            formulario = (Formulario) formularios.get(0);
            log.debug("Formulario " + obtenerValor(MODELO) + " version " + obtenerValor(VERSION) + " cargado");
            /*
              Inicializo la estructura.
            */
            Hibernate.initialize(formulario.getPantallas());
            for (Iterator iterPant = formulario.getPantallas().iterator(); iterPant.hasNext();) {
                Pantalla pantalla = (Pantalla) iterPant.next();
                Hibernate.initialize(pantalla.getAyudas());
                Hibernate.initialize(pantalla.getComponentes());
                for (Iterator iterCamp = pantalla.getCampos().iterator(); iterCamp.hasNext();) {
                    Campo campo = (Campo) iterCamp.next();
                    Hibernate.initialize(campo.getValidaciones());
                    
                    // Para ComboBox añadimos validacion obligatorio
                    if (campo instanceof ComboBox && ((ComboBox) campo).isObligatorio()) {
                    	Validacion validacion = new Validacion();
                    	Mascara mascara = DelegateUtil.getMascaraDelegate().obtenerMascara("required");
						validacion.setMascara(mascara);					
						campo.addValidacion(validacion);
                    }
                    
                    Hibernate.initialize(campo.getValoresPosibles());
                    boolean bloquear = CampoUtils.bloquearCampo(campo, docXMLConfig, XPATH_BLOQ_TODOS, XPATH_BLOQ_CAMPO);
                    if (bloquear) {
                        campo.setBloqueado(true);
                    }
                }
            }
            log.debug("Estructura inicializada");

            /*
              Asigno el idioma al formulario.
            */
            formulario.setCurrentLang(obtenerValor(IDIOMA));

            /*
              Obtengo la pantalla inicial.
            */
            pantallaActual = formulario.findPantallaInicial();
            if (pantallaActual == null) {
                closeReadOnly(session);
                throw new CreateException("El formulario " + obtenerValor(MODELO) + " versión " + obtenerValor(VERSION) + " no tiene pantalla inicial");
            }
            log.debug("Obtenida pantalla inicial");

            /*
              Inicializo los campos con los valores iniciales, a partir del fichero XML 'XMLValoresInit'
            */
            //  INDRA: LISTA ELEMENTOS 
            Map variablesScript = variablesScript();
        	// Cargamos datos campos pantalla (excepto listas elementos)
            datosActual = PantallaUtils.valoresDefecto(pantallaActual, docXMLIni,variablesScript ,obtenerValor(NOM_ATRIB_INDEX_CAMPO_INDEXADO));
            // En caso de que la pantalla tenga un campo lista de elementos lo cargamos
            List listaLel  = PantallaUtils.buscarCamposListaElementos(pantallaActual);
            for (Iterator it = listaLel.iterator();it.hasNext();){
            	ListaElementos lel = (ListaElementos) it.next();
            	List valoresLista = PantallaUtils.valoresDefectoListaElementos(lel,this.formulario,pantallaActual,docXMLIni,variablesScript, obtenerValor(NOM_ATRIB_INDEX_CAMPO_INDEXADO));
            	this.datosListasElementos.put(CampoUtils.getReferenciaListaElementos(pantallaActual.getNombre(),lel.getNombreLogico()),valoresLista);    
            }
            
            // INDRA: LISTA ELEMENTOS 
            log.debug("Generados valores por defecto");

            session.disconnect();
        } catch (Exception e) {
            closeReadOnly(session);
            throw new EJBException(e);
        }
    }


    /******************************************************************************************
    *                                    LÓGICA DE NEGOCIO.                                   *
    ******************************************************************************************/

    /**
     * Cambia de pantalla, e inicializa los campos según fichero xml de valores iniciales por defecto.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public void avanzarPantalla() {
        /*
          Almaceno la pantalla actual en la pila de pantallas visitadas y almaceno los
          datos de la misma en la pila de datos de las pantallas visitadas.
        */
        pilaPantallasAnteriores.push(pantallaActual);
        pilaDatosAnteriores.push(datosActual);
        /*
          Solo avanzo pantalla si la actual no está marcada como la última del formulario.
        */
        if (pantallaActual.isUltima()) {
            pantallaActual = null;
            datosActual = null;
        } else {
            /*
              si la pantalla no contiene una expresión de avance entonces
                avanzar pantalla según orden.
              sino
                avanzar pantalla según evaluación de expresión.
            */
            String expresion = pantallaActual.getExpresion();
            if ( (expresion == null) || (expresion.trim().length() == 0) ) {
                int orden = pantallaActual.getOrden();
                pantallaActual = formulario.findPantalla(orden + 1);
                if (pantallaActual == null) {
                    log.warn("La pantalla con índice " + orden + " no existe!!");
                }
            } else {
                Map variables  = variablesScriptActuals();
                String nombre  = (String) ScriptUtil.evalScript(expresion, variables);
                
                // INDRA: AVANZAR HASTA UNA PANTALLA ANTERIOR ES EQUIVALENTE A RETROCEDER A ESA PANTALLA
                Pantalla destino = formulario.findPantalla(nombre);
                if (destino == null) {
                    log.warn("La pantalla \"" + nombre + "\" no existe!!");
                }
                if (pilaPantallasAnteriores.contains(destino)) {
                    log.debug("La pantalla \"" + nombre + "\" ya ha sido procesada. Retrocedemos hasta esa pantalla");
                	retrocederPantalla(nombre);
                	return;                                   
                }
                // INDRA: AVANZAR HASTA UNA PANTALLA ANTERIOR ES EQUIVALENTE A RETROCEDER A ESA PANTALLA
                
                // Si no es una pantalla anterior, la hacemos la actual
                pantallaActual = destino;                
            }
        }
        /*
          si pantalla ya fue visitada entonces
            cargar los datos realtivos a la última carga de la pantalla.
          sino
            cargar valores según fichero xml de valores iniciales por defecto.
          fsi
        */
        if (pantallaActual != null) {
            
            // Intentar recuperar datos introducidos en la última visita.
            if (pantallasDatosPosteriores.containsKey(pantallaActual.getNombre())) {
                datosActual = (Map) pantallasDatosPosteriores.get(pantallaActual.getNombre());
                pantallasDatosPosteriores.remove(pantallaActual.getNombre());

                
				// INDRA	: MODIFICACION SUPERVISADA POR IBIT PARA BUG DE DESPLEGABLES DEPENDIENTES                
                INDRA_refrescarValoresPosibles(datosActual);
				// INDRA	: FIN MODIFICACION

            } else {
            	
            	// Pantalla cargada por primera vez: cargamos datos (xml o x defecto)
            	// INDRA: LISTA ELEMENTOS            	
            	Map variablesScript = variablesScript();
            	// Cargamos datos campos pantalla (excepto listas elementos)
                datosActual = PantallaUtils.valoresDefecto(pantallaActual, docXMLIni,variablesScript ,obtenerValor(NOM_ATRIB_INDEX_CAMPO_INDEXADO));
                // En caso de que la pantalla tenga un campo lista de elementos lo cargamos
                List listaLel  = PantallaUtils.buscarCamposListaElementos(pantallaActual);
                for (Iterator it = listaLel.iterator();it.hasNext();){
                	ListaElementos lel = (ListaElementos) it.next();
	                if (lel != null){
	                	List valoresLista = PantallaUtils.valoresDefectoListaElementos(lel,this.formulario,pantallaActual,docXMLIni,variablesScript, obtenerValor(NOM_ATRIB_INDEX_CAMPO_INDEXADO));
	                	this.datosListasElementos.put(CampoUtils.getReferenciaListaElementos(pantallaActual.getNombre(),lel.getNombreLogico()),valoresLista);    
	                }
                }
                // INDRA: LISTA ELEMENTOS
            }
            log.debug("Pantalla Actual: " + pantallaActual.getNombre());
        } else {
            log.debug("Pantalla Actual es null");
        }
    }

    /**
     * Devuelve un Mapa con los datos genéricos del formulario.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public Map obtenerPropiedadesFormulario() {
        return propiedadesForm;
    }

    /**
     * Devuelve la cadena con el layout específico del trámite.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public String obtenerLayOut() {
        if (datosTramite == null) {
            return null;
        } else {
            String layout = obtenerValor(LAYOUT);
            return layout + StringUtils.defaultString(formulario.getModoFuncionamiento().getSufijo());
        }
    }

    /**
     * Envia los documentos xml con los valores iniciales y finales al sistema de tramitación y
     * redirecciona al cliente.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public String tramitarFormulario() {
        log.debug("voy a tramitar el formulario...");
        String urlSisTra = obtenerValor(URL_SIS_TRA_OK);
        if (urlSisTra == null) {
            throw new EJBException("urlSisTraOK no encontrada");
        }
        String xmlIni = obtenerXMLValoresOriginales();
        
        String xmlFin = obtenerXMLValoresFinales();
        boolean exito = enviarDocumentosXMLSisTra(urlSisTra,
                                                  xmlIni,
                                                  xmlFin,
                                                  obtenerValor(NOM_PARAM_XML_DATOS_INI),
                                                  obtenerValor(NOM_PARAM_XML_DATOS_FIN));
        if (!exito) {
            throw new EJBException("Error al enviar los documentos xml al sistema de tramitacion");
        } else {
            String urlRedireccion = obtenerValor(URL_REDIRECCION_OK);
            if (urlRedireccion == null) {
                throw new EJBException("urlRedireccionOK no encontrada");
            }
            urlRedireccion = insertarParametro(urlRedireccion,
                                               obtenerValor(NOM_PARAM_TOKEN_RETORNO),
                                               respuesta);
            log.debug("redirecciono a " + urlRedireccion);
            return urlRedireccion;
        }
    }

    /**
     * Indica al sistema de tramitación que se ha cancelado el proceso de relleno del formulario
     * y se redirecciona al cliente.
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public String cancelarFormulario() {
        log.debug("voy a cancelar el formulario...");
        String urlSisTra = obtenerValor(URL_SIS_TRA_CANCEL);
        if (urlSisTra == null) {
            throw new EJBException("urlSisTraKO no encontrada");
        }
        boolean exito = enviarCancelacionSisTra(urlSisTra);
        if (!exito) {
            throw new EJBException("Error al enviar cancelacion al sistema de tramitacion");
        } else {
            String urlRedireccion = obtenerValor(URL_REDIRECCION_CANCEL);
            if (urlRedireccion == null) {
                throw new EJBException("urlRedireccionKO no encontrada");
            }
            urlRedireccion = insertarParametro(urlRedireccion,
                                               obtenerValor(NOM_PARAM_TOKEN_RETORNO),
                                               respuesta);
            log.debug("redirecciono...");
            return urlRedireccion;
        }
    }
    
    
    /**
     * Obtiene url para mantener la sesion de sistra mientras se rellena el formulario
     * @ejb.interface-method
     * @ejb.permission unchecked="true"
     */
    public String obtenerUrlSistraMantenimientoSesion() {        
        String urlSisTra = obtenerValor(URL_SIS_TRA_MANTENIMIENTO_SESION);
        if (urlSisTra == null) {
            throw new EJBException(URL_SIS_TRA_MANTENIMIENTO_SESION + " no encontrada");
        }
        return urlSisTra;
    }

    /******************************************************************************************
     *                    FIN LÓGICA DE NEGOCIO. INICIO METODOS AUXILIARES.                   *
     ******************************************************************************************/

    /**
     * Obtiene una determina información sobre el trámite.
     * @param dato-> String con el dato del cual queremos hayar su valor.
    */
    private String obtenerValor(String dato) {
        if (datosTramite == null) {
            return null;
        } else {
            if (datosTramite.containsKey(dato)) {
                return (String) datosTramite.get(dato);
            } else {
                log.warn(dato + "no encontrado");
                return null;
            }
        }
    }

    /**
     * Carga en un Map, los datos del formulario telemático por defecto.
    */
    private void cargarDatosTramiteDefecto() {
        datosTramite.put(NOM_PARAM_XML_DATOS_INI, "xmlInicial");
        datosTramite.put(NOM_PARAM_XML_DATOS_FIN, "xmlFinal");
        datosTramite.put(NOM_PARAM_TOKEN_RETORNO, "TOKEN");
        datosTramite.put(NOM_ATRIB_INDEX_CAMPO_INDEXADO, "indice");
    }

    /**
     * Carga en un Map, los datos del formulario telemático, definidos en el documento xml de configuración.
     * @param doc-> Docuemento XML, con los valores de los campos que queremos prerellenar.
    */
    private void cargarDatosTramite(Document doc) {
        if (doc == null) {
            datosTramite = null;
        } else {
            List datos = doc.selectNodes(XPATH_DATOS_TRAMITE);
            if (datos.isEmpty()){
                datosTramite = null;
                log.warn("No se encontraron los datos del tramite en el documento xml de configuracion");
            } else {
                for (Iterator iterator = datos.iterator(); iterator.hasNext();) {
                    Node node = (Node) iterator.next();
                    datosTramite.put(node.getName(), node.getText());
                }
            }
        }
    }

    /**
     * Carga en un Map, las propiedades genéricas del formulario telemático, definidos en el documento xml de configuración.
     * @param doc-> Docuemento XML con las propiedades genéricas del trámite.
    */
    private void cargarPropiedadesFormulario(Document doc) {
        if (doc == null) {
            propiedadesForm = null;
        } else {
            List propiedades = doc.selectNodes(XPATH_PROPIEDADES_FORM);
            if (propiedades.isEmpty()) {
                propiedadesForm = null;
                log.warn("No se encontraron las propiedades genéricas del formulario en el documento xml de configuración");
            } else {
                String propNom = null;
                String propVal;
                for (int i = 0; i < propiedades.size(); i++){
                    Node nodo = (Node) propiedades.get(i);
                    if (i % 2 == 0) {
                        propNom = nodo.getText();
                    } else {
                        propVal = nodo.getText();
                        propiedadesForm.put(propNom, propVal);
                    }
                }
            }
        }
    }

    /**
     * Carga en memoria los documentos XML de los String de configuración e inicialización.
     * @param XMLConf-> cadena que contiene el documento XML de configuración del trámite.
     * @param XMLInit-> cadena que contiene el documento XML con los datos de los campos a prerellenar.
    */
    private void cargarDocumentosXML(String XMLConf,
                                     String XMLInit) {
        try {
            docXMLConfig = DocumentHelper.parseText(XMLConf);
        } catch (DocumentException e) {
            log.warn("Error al parsear el string con el xml de configuracion");
        }
        //
        try {
            if ( (XMLInit != null) && (XMLInit.trim().length() > 0) ) {
                docXMLIni = DocumentHelper.parseText(XMLInit);
            }
        } catch (DocumentException e) {
            log.warn("Error al parsear el string con el xml de valores iniciales");
            log.warn(e.toString());
        }
    }

    /**
     * Devuelve el documento XML con los valores iniciales de los campos del formulario.
     * @return String con el documento XML original, con los valores de los campos a prerellenar.
    */
    private String obtenerXMLValoresOriginales() {
        /*if (docXMLIni != null) {
            return documentToString(docXMLIni);
        } else {
            log.info("No se encontro el documento xml con los valores iniciales");
            return null;
        }/*/
        return XMLValoresIniOriginal;
    }

    /**
     * Genera el documento XML con los valores finales de los campos.
     * @return Devuelve el documento XML con los valores finales de los campos del formulario.
     * @throws Exception 
     */
    private String obtenerXMLValoresFinales() throws EJBException {
        Map datosPantalla;
        Document docXMLFin = DocumentHelper.createDocument();
        for (int i = 0; i < pilaPantallasAnteriores.size(); i++) {
            Pantalla pantalla = (Pantalla) pilaPantallasAnteriores.elementAt(i);
            datosPantalla = (Map) pilaDatosAnteriores.elementAt(i);
            generarNodosPantalla(docXMLFin,pantalla,datosPantalla,false,null);
        }
        return documentToString(docXMLFin);
    }

    
    private void generarNodosPantalla(Document docXMLFin,Pantalla pantalla,Map datosPantalla,boolean detalle,String xpathLista) throws EJBException{
    	for (Iterator j = pantalla.getCampos().iterator(); j.hasNext();) {
            Campo campo = (Campo) j.next();
            String xPath = campo.getEtiquetaPDF();                      
            
            if ( (xPath != null) && (xPath.trim().length() > 0)) {
            	
            	if (detalle) xPath = xpathLista +"/" + xPath; 
            	
                if (campo.isIndexed()) {
                    /*
                      Campos indexados   : TreeBox, ListBox, ComboBox, RadioButton.
                      Para estos campos hay que tener el cuenta el valor del índice y el valor asociado a ese índice.
                    */
                    if (campo instanceof ListBox || campo instanceof TreeBox) {
                        /*ListBox-> Campo de selección múltiple*/
                        String[] valoresIndex = (String[]) datosPantalla.get(campo.getNombreLogico());
                        Object[] valoresText  = (Object[]) datosPantalla.get(campo.getNombreLogico() + "_text");
                        
                        // INDRA: COMPROBACION OBLIGATORIO
                        // INDRA 2013:  NO TIENE EFECTO, YA QUE NO PUEDE ESTABLECERSE SI ES OBLIGATORIO 
                        if (campo.findValidacion("required") != null) {
                        	if (valoresIndex.length == 0) {
                        		log.error("Error generando XML: campo con XPATH " + xPath + " debe contener valor");
                        		throw new EJBException("Error generando XML: campo con XPATH " + xPath + " debe contener valor");
                        	}
                        }
                        // INDRA: COMPROBACION OBLIGATORIO
                        
                        for (int k = 0; k < valoresIndex.length; k++) {
                            if ( (valoresIndex[k] != null) && (valoresText[k] != null) ) {
                                docXMLFin = crearNodo(docXMLFin, xPath, valoresIndex[k], true, String.valueOf(valoresText[k]));
                            }
                        }                       
                    } else {
                    	
                    	// ComboBox, RadioButton
                    	
                    	// INDRA: COMPROBACION OBLIGATORIO
                        if ( 
                        	 (campo.findValidacion("required") != null) || 
                         	 ( 
                         		(campo instanceof ComboBox) && 
                         		(((ComboBox) campo).isObligatorio())) 
                         	){
                        	   	if ( (datosPantalla.get(campo.getNombreLogico()) == null) ||
	                                 (datosPantalla.get(campo.getNombreLogico() + "_text") == null) ) {
                        	   		log.error("Error generando XML: campo con XPATH " + xPath + " debe contener valor");
	                        		throw new EJBException("Error generando XML: campo con XPATH " + xPath + " debe contener valor");
	                        	}
                        }
                        // INDRA: COMPROBACION OBLIGATORIO
                    	
                    	
                        if ( (datosPantalla.get(campo.getNombreLogico()) != null) &&
                             (datosPantalla.get(campo.getNombreLogico() + "_text") != null) ){
                            String valorIndex = String.valueOf(datosPantalla.get(campo.getNombreLogico()));
                            String valorText  = String.valueOf(datosPantalla.get(campo.getNombreLogico() + "_text"));
                            docXMLFin = crearNodo(docXMLFin, xPath, valorIndex, true, valorText);
                        }
                    }
                } else {
                    /*Campos no indexados: CheckBox, TextBox.*/
                	if (campo instanceof ListaElementos){
                		                		
                		if (detalle) throw new EJBException("Una pantalla detalle no puede tener una lista de elementos");
                		
                		String referenciaCampo = CampoUtils.getReferenciaListaElementos(pantalla.getNombre(),campo.getNombreLogico());
                        List listaElementos = (List) this.datosListasElementos.get(referenciaCampo);
                        if (listaElementos != null){
	                		for (int numElemento=0;numElemento<listaElementos.size();numElemento++){
	                			Map datosElemento = (Map) listaElementos.get(numElemento);
	                			Pantalla pantallaDetalle = encontrarPantallaDetalle(referenciaCampo);
	                			generarNodosPantalla(docXMLFin,pantallaDetalle,datosElemento,true,xPath + "/ID" + (numElemento+1));
	                		}
                        }
                	}else{
                		String valorCampo = String.valueOf(datosPantalla.get(campo.getNombreLogico()));
                		
                		// INDRA: COMPROBACION OBLIGATORIO
                        if (campo.findValidacion("required") != null) {
                        	if (StringUtils.isBlank(valorCampo)) {
                        		log.error("Error generando XML: campo con XPATH " + xPath + " debe contener valor");
                        		throw new EJBException("Error generando XML: campo con XPATH " + xPath + " debe contener valor");
                        	}
                        }
                        // INDRA: COMPROBACION OBLIGATORIO
                		
                        if (valorCampo != null) {
                            docXMLFin = crearNodo(docXMLFin, xPath, valorCampo, false, null);
                        }
                	}
                }
            }
        }
    }
    
    
    
    /**
     * Devuelve la representación de un Document XML en String bien formateado
     * y con codificación UTF-8.
     * @param doc Documento.
     * @return string representando el documento formateado y en UTF-8.
     */
    private String documentToString(Document doc) {
        String result = null;
        StringWriter writer = new StringWriter();
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("UTF-8");
        XMLWriter xmlWriter = new XMLWriter(writer, of);
        try {
            xmlWriter.write(doc);
            xmlWriter.close();
            result = writer.toString();
        } catch (IOException e) {
            log.error("Error escribiendo xml", e);
        }
        return result;
    }

    /**
     * Actualiza el documento XML que contiene los valores finales de los campos.
     * @param doc-> Documento XML, en el que se insertan los valores finales de los campos.
     * @param xPath-> Expresión xPath, que permite crear la estructura de nodos para un determinado campo.  /nodo raíz[/nodo]*
     * @param valorCampo-> Si no es un campo indexado, es el valor del campo, si es indexado, es el valor del índice.
     * @param campoIndexado-> Indica si un campo es de tipo indexado.
     * @param valorTextCampo-> Si el campo es indexado, se trata del valor del campo, asociado al índice.
     * @return Documento XML actualizado con valor del campo.
     */
    private Document crearNodo(Document doc,          String xPath,          String valorCampo,
                               boolean campoIndexado, String valorTextCampo) throws EJBException {
        
    	String[] path = xPath.split("/");
        
        // Validamos que al menos tenga 2 elementos el xpath
        if (path.length < 2){
        	log.error("Campo configurado con xpath erroneo: '" + xPath + "'" );
        	throw new EJBException("Campo configurado con xpath erroneo: '" + xPath + "'" );
        }
                
        if (doc.getRootElement() == null) {
            doc.setRootElement(doc.addElement(path[1]));
        }
        Element nodoElement = doc.getRootElement();
        String  nodo = "/" + path[1];
        for (int i = 2; i < path.length; i++) {
            nodo += "/" + path[i];
            if (doc.selectSingleNode(nodo) == null) {
                nodoElement = nodoElement.addElement(path[i]);
                if (i == path.length - 1) {
                    if (campoIndexado){
                        nodoElement = nodoElement.addAttribute(obtenerValor(NOM_ATRIB_INDEX_CAMPO_INDEXADO), valorCampo);
                        nodoElement = addText(nodoElement,valorTextCampo);                        
                    } else {                    	
                        nodoElement = addText(nodoElement,valorCampo);
                    }
                }
            } else {
                nodoElement = (Element) doc.selectSingleNode(nodo);
                if (i == path.length -1) {
                    Element padre = nodoElement.getParent();
                    nodoElement = padre.addElement(path[i]);
                    if (campoIndexado) {
                        nodoElement = nodoElement.addAttribute(obtenerValor(NOM_ATRIB_INDEX_CAMPO_INDEXADO), valorCampo);
                        nodoElement = addText(nodoElement,valorTextCampo);                        
                    } else {
                        nodoElement = addText(nodoElement,valorCampo);
                    }
                }
            }
        }
        return doc;
    }    
    
    /*
     * Añade texto teniendo en cuenta saltos de linea
     */
    private Element addText(Element e,String text){
    	if (text.indexOf("\n") != -1) { 
            e = e.addCDATA(text); 
        } else { 
            e = e.addText(text);
        } 
    	return e;
    }

    /*
      Postea los documentos "xmlIni" y "xmlFin" a través de la "url"
    */
    private boolean enviarDocumentosXMLSisTra(String url, String xmlIni, String xmlFin, String nomParam1, String nomParam2) {
        log.debug("http post a " + url);
        HttpClient cliente = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.getParams().setContentCharset("UTF-8");
        post.addParameter(nomParam1, xmlIni);
        post.addParameter(nomParam2, xmlFin);
        try {
            int estado = cliente.executeMethod(post);
            if (estado != HttpStatus.SC_OK) {
                log.warn("post failed: " + post.getStatusText());
                respuesta = post.getResponseBodyAsString();
                return false;
            }
            log.debug("http post a " + url + "finalizado con exito");
            respuesta = post.getResponseBodyAsString();
            return true;
        } catch (HttpException e) {
            log.warn("HttpException:" + e.toString());
            return false;
        } catch (IOException e) {
            log.warn("IOException:" + e.toString());
            return false;
        } finally {
            post.releaseConnection();
        }
    }

    /*
      Genera una llamada http a la url especificada en el parámetro "url".
    */
    private boolean enviarCancelacionSisTra(String url) {
        log.debug("http get a " + url + "...");
        HttpClient cliente = new HttpClient();
        GetMethod get = new GetMethod(url);
        //Por defecto 3 reintentos ante errores recuperables.
        cliente.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        /*
          De esta manera redefinimos el número de reintentos http antre errores recuperables.
          cliente.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(5,true));
        */
        try {
            int estado = cliente.executeMethod(get);
            if (estado != HttpStatus.SC_OK) {
                log.warn("get Failed: " + get.getStatusText());
                respuesta = get.getResponseBodyAsString();
                return false;
            }
            log.debug("http get a " + url + "finalizado con exito");
            respuesta = get.getResponseBodyAsString();
            return true;
        } catch (HttpException e) {
            log.warn("HttpException:" + e.toString());
            return false;
        } catch (IOException e) {
            log.warn("IOException:" + e.toString());
            return false;
        } finally {
            get.releaseConnection();
        }
    }

    /**
     * Inserta un nuevo parámetro un una url.
     * @param url-> url a la que se le quiere insertar un parámetro.
     * @param nombre-> nombre del parámetro que se quiere insertar.
     * @param valor-> valor del parámetro que se quiere insertar.
     * @return cadena con la url original a la que se le ha añadido el parámtro.
    */
    private String insertarParametro(String url, String nombre, String valor) {
        StringBuffer sb = new StringBuffer(url);
		sb.append(url.indexOf('?') == -1 ? '?' : '&');
        sb.append(nombre);
        sb.append('=');
        sb.append(valor);
        return sb.toString();
    }

    /*
      CallBack method ejbRemove() para liberar los recursos, entre ellos la sesión.
    */
    public void ejbRemove() {
        try {
            super.ejbRemove();
            datosTramite.clear();
            propiedadesForm.clear();
            docXMLConfig = null;
            docXMLIni = null;
        } finally {
            log.debug("fin ejbRemove():" + this.getClass().getName());
        }
    }
    
   
}
