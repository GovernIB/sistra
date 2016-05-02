package org.ibit.rol.form.persistence.ejb;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.model.Anexo;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.AyudaPantalla;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Captcha;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.FileBox;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.LogsScripts;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.Patron;
import org.ibit.rol.form.model.PerfilUsuario;
import org.ibit.rol.form.model.PropiedadSalida;
import org.ibit.rol.form.model.Salida;
import org.ibit.rol.form.model.TraFormulario;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.TreeBox;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.conector.Conector;
import org.ibit.rol.form.persistence.conector.ConectorConfigurator;
import org.ibit.rol.form.persistence.conector.ConectorException;
import org.ibit.rol.form.persistence.conector.MessageResult;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.util.CampoUtils;
import org.ibit.rol.form.persistence.util.CaptchaUtils;
import org.ibit.rol.form.persistence.util.GeneradorNumSeq;
import org.ibit.rol.form.persistence.util.PantallaUtils;
import org.ibit.rol.form.persistence.util.ScriptUtil;
import org.mozilla.javascript.NativeArray;

/**
 * SessionBean para realizar el proceso de rellenar un
 * formulario.
 *
 * @ejb.bean
 *  name="form/persistence/InstanciaProcessor"
 *  jndi-name="org.ibit.rol.form.persistence.InstanciaProcessor"
 *  type="Stateful"
 *  view-type="both"
 *  transaction-type="Container"
 *
 * @ejb.permission unchecked="true"
 * @ejb.transaction type="Required"
 * @jboss.container-configuration name="InstanciaProcessor Stateful SessionBean"
 *
 */
public abstract class InstanciaProcessorEJB extends HibernateEJB {
    //
    protected static final Log log = LogFactory.getLog(InstanciaProcessorEJB.class);

    protected boolean debugEnabled;



	protected Boolean scriptDebug;

    // LOG SCRIPTS (Solo instanciado cuando se permita el debug de scripts)
    protected LogsScripts logScript;

    protected Session session = null;

    protected Locale locale = null;
    protected Formulario formulario = null;
    protected PerfilUsuario perfil = null;
    protected Pantalla pantallaActual = null;
    protected Map datosActual = null;

    protected String campoPantallaDetalleActual;
    protected String accionPantallaDetalleActual;
    protected String indexPantallaDetalleActual;

    protected Map anexos = new HashMap();

    protected Stack pilaPantallasAnteriores = new Stack();
    protected Stack pilaDatosAnteriores = new Stack();

    protected Map pantallasDatosPosteriores = new LinkedHashMap();

    //------------ INDRA ------------------------------------------------------------------------
    // Map de datos de componentes lista (key: referencia componentes / value: List de maps de datos)
    protected Map datosListasElementos = new HashMap();
    //------------ INDRA ------------------------------------------------------------------------

    /**
     * Prepara una nueva instancia para procesar un formulario del modelo indicado y la versión 1.
     * @ejb.create-method
     */
    public void ejbCreate(String modelo, Locale idioma, String codiPerfil) throws CreateException {
        ejbCreate(modelo, idioma, codiPerfil, 1);
    }

    /**
     * Prepara una nueva instancia para procesar un formulario del modelo indicado con la versión indicada.
     * @ejb.create-method
     */
    public void ejbCreate(String modelo, Locale idioma, String codiPerfil, int version) throws CreateException {
        super.ejbCreate();

    	session = getReadOnlySession();

        try {
            locale = idioma;

            if (permitirScriptDebug())
            	logScript = new LogsScripts();

            Query queryPerfil = session.createQuery("FROM PerfilUsuario AS p WHERE p.codigoEstandard = :codigo");
            queryPerfil.setParameter("codigo", codiPerfil);
            queryPerfil.setCacheable(true);
            List perfiles = queryPerfil.list();
            if (perfiles.isEmpty()) {
                closeReadOnly(session);
                throw new CreateException("El perfil " + codiPerfil + " no existe");
            }
            perfil = (PerfilUsuario) perfiles.get(0);

            debug("Carregat perfil");

            Query query = session.createQuery("FROM Formulario AS f WHERE f.modelo = :modelo and f.version = :version");
            query.setString("modelo", modelo);
            query.setInteger("version", version);
            query.setCacheable(true);
            List formularios = query.list();
            if (formularios.isEmpty()) {
                closeReadOnly(session);
                throw new CreateException("El formulario " + modelo + " no existe");
            }
            formulario = (Formulario) formularios.get(0);

            debug("Carregat formulari");

            //Inicialitzar estructura
            Hibernate.initialize(formulario.getPantallas());
            for (int i = 0; i < formulario.getPantallas().size(); i++) {
                Pantalla pantalla = (Pantalla) formulario.getPantallas().get(i);
                if (pantalla != null) {
                    Hibernate.initialize(pantalla.getAyudas());
                    Hibernate.initialize(pantalla.getComponentes());
                    for (int j = 0; j < pantalla.getCampos().size(); j++) {
                        Campo campo = (Campo) pantalla.getCampos().get(j);
                        Hibernate.initialize(campo.getValidaciones());
                        Hibernate.initialize(campo.getValoresPosibles());
                    }
                }
            }
            formulario.setCurrentLang(locale.getLanguage());

            debug("Inicialitzada estructura.");

            pantallaActual = formulario.findPantallaInicial();
            if (pantallaActual == null) {
                closeReadOnly(session);
                throw new CreateException("El formulario " + modelo + " no tiene pantalla inicial");
            }

            debug("Obtinguda pantalla inicial.");

            datosActual = PantallaUtils.valoresDefecto(pantallaActual, variablesScript(), debugEnabled);

            debug("Preparats valors per defecte.");

            session.disconnect();
        } catch (HibernateException e) {
            closeReadOnly(session);
            throw new EJBException(e);
        }
    }

    /**
     * Prepara una nueva instancia con los datos guardados.
     * @ejb.create-method
     */
    public void ejbCreate(InstanciaBean bean) throws CreateException {
        ejbCreate(bean.getModelo(), bean.getLocale(), bean.getPerfil(), bean.getVersion());
        for (int i = 0; i < bean.getDataMaps().size(); i++) {
            Map map = (Map) bean.getDataMaps().get(i);
            if (map == null) {
                throw new CreateException("Encontrado DataMap nulo en InstanciaBean");
            }
            introducirDatosPantalla(map);
            avanzarPantalla();
        }
        // Dejar la ultima pantalla en la que se habia estado.
        retrocederPantalla();
        // Afegir anexos.
        anexos.putAll(bean.getAnexos());
    }

    /**
     * Obtener el formulario que se esta procesando.
     * @ejb.interface-method
     */
    public Formulario obtenerFormulario() {
        return formulario;
    }

    /**
     * Obtener el formulario que se esta procesando.
     * @ejb.interface-method
     */
    public Locale obtenerIdioma() {
        return locale;
    }

    /**
     * Obtener el formulario que se esta procesando.
     * @ejb.interface-method
     */
    public PerfilUsuario obtenerPerfil() {
        return perfil;
    }

    /**
     * Obtiene la pantalla que esta en proceso.
     * Tambien se cargan los componentes de la pantalla.
     * @ejb.interface-method
     */
    public Pantalla obtenerPantalla() {
        return pantallaActual;
    }

    /**
     * Obtiene la ayuda de la pantalla que está en processo.
     * @ejb.interface-method
     */
    public AyudaPantalla obtenerAyudaPantalla() {
        try {
            session.reconnect();

            AyudaPantalla ayudaPantalla = null;

            if (pantallaActual != null) {
                Iterator iter = pantallaActual.getAyudas().iterator();
                while (iter.hasNext() && ayudaPantalla == null) {
                    AyudaPantalla ap = (AyudaPantalla) iter.next();
                    if (ap.getPerfil().equals(perfil)) {
                        ayudaPantalla = ap;
                    }
                }
            }

            session.disconnect();
            return ayudaPantalla;
        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }
    /**
     * @ejb.interface-method
     */
    public Archivo obtenerLogotipo1() {
        try {
            session.reconnect();

            Archivo logo1 = formulario.getLogotipo1();
            if (logo1 != null && !Hibernate.isInitialized(logo1)) {
                Hibernate.initialize(logo1);
            }

            session.disconnect();
            return logo1;
        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }

    /**
     * @ejb.interface-method
     */
    public Archivo obtenerLogotipo2() {
        try {
            session.reconnect();

            Archivo logo2 = formulario.getLogotipo2();
            if (logo2 != null && !Hibernate.isInitialized(logo2)) {
                Hibernate.initialize(logo2);
            }

            session.disconnect();
            return logo2;
        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }

    /**
     * @ejb.interface-method
     */
    public Archivo obtenerPlantilla() {
        try {
            session.reconnect();

            Archivo plantilla = ((TraFormulario) formulario.getTraduccion()).getPlantilla();
            if (plantilla != null && !Hibernate.isInitialized(plantilla)) {
                Hibernate.initialize(plantilla);
            }

            session.disconnect();
            return plantilla;
        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }

    /**
     * @ejb.interface-method
     */
    public String obtenerPathIconografia() {
        return perfil.getPathIconografia();
    }

    /**
     * Obtiene los datos de la pantalla actual
     * @ejb.interface-method
     */
    public Map obtenerDatosPantalla() {
        return datosActual;
    }

    /**
     * Obtiene los datos de pantallas anteriores, prefijados con f_&lt;nombre pantalla%gt;_
     * @ejb.interface-method
     */
    public Map obtenerDatosAnteriores() {
        Map vals = variablesScript();

        // Eliminamos var internas para log
       vals.remove(ScriptUtil.ID_LOG_SCRIPT);
       vals.remove(ScriptUtil.ID_PLUGIN_LOG);

        return vals;
    }

    /**
     * Obtiene las pantallas processadas, no incluida la actual.
     * @ejb.interface-method
     */
    public List obtenerPantallasProcesadas() {
        List listaNombres = new ArrayList(pilaPantallasAnteriores.size() + 1);

        for (Iterator iterator = pilaPantallasAnteriores.iterator(); iterator.hasNext();) {
            Pantalla pantalla = (Pantalla) iterator.next();
            listaNombres.add(pantalla);
        }

        return listaNombres;
    }
    /**
     * Actualiza los datos asociados a la pantalla actual, una vez esta se ha procesado.
     * Si un campo está bloqueado, entonces no se debe actualizar su valor, ya que este no
     * debe cambiar.()
     * @param datos-> Mapa con los valores de los campos de la página que se está procesando.
     * @ejb.interface-method
     */
    public void introducirDatosPantalla(Map datos) {
        /*
          Si un campo está en situación de bloqueo, entonces no debe ser posible modificar su valor,
          por lo que no se actualizará su valor.
        */
        eliminaDatosCamposBloqueados(datos);

        // Elimina campos de captcha
        eliminaDatosCamposCaptcha(datos);

        // Establece datos en pantalla
        datosActual.putAll(datos);
        Map variables = variablesScriptActuals();
        for (int j = 0; j < pantallaActual.getCampos().size(); j++) {
            Campo campo = (Campo) pantallaActual.getCampos().get(j);
            if (!campo.isBloqueado()) {

            	if (campo instanceof CheckBox) {
                    Object valor = datos.get(campo.getNombreLogico());
                    if (valor == null) {
                        datosActual.put(campo.getNombreLogico(), Boolean.FALSE);
                    }
                }

                if (campo.isIndexed()) {
                    // Para los campos indexados calculamos los textos asociados al codigo )campo_text)
                	CampoUtils.calcularValoresPosibles(campo, variables, this.isDebugEnabled());
                    Object valor = null;
                    Object index = datos.get(campo.getNombreLogico());
                    if (index != null) {
                        List valoresPosibles = campo.getAllValoresPosibles();
                        if (campo instanceof ListBox || campo instanceof TreeBox) {
                            String[] valores = (String[]) index;
                            Object[] etiquetas = new Object[valores.length];
                            for (int i = 0; i < valores.length; i++) {
                                ValorPosible vp = findValorPosible(valoresPosibles, valores[i]);
                                if (vp != null) {
                                    etiquetas[i] = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                }
                            }
                            valor = etiquetas;
                        } else {
                            ValorPosible vp = findValorPosible(valoresPosibles, (String) index);
                            if (vp != null) {
                                valor = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                            }
                        }
                    }
                    datosActual.put(campo.getNombreLogico() + "_text", valor);
                }
            }
        }
    }



    /**
     * Actualiza los datos asociados a la pantalla actual, una vez esta se ha procesado.
     * Si un campo está bloqueado, entonces no se debe actualizar su valor, ya que este no
     * debe cambiar.()
     * @param datos-> Mapa con los valores de los campos de la página que se está procesando.
     * @ejb.interface-method
     */
    public void INDRA_refrescarValoresPosibles(Map datos) {

    	Map variables = variablesScriptActuals();
        for (int j = 0; j < pantallaActual.getCampos().size(); j++) {

        	Campo campo = (Campo) pantallaActual.getCampos().get(j);
            if (!campo.isBloqueado()) {

                if (campo.isIndexed()) {
                    CampoUtils.calcularValoresPosibles(campo, variables, this.isDebugEnabled());
                    /*
                    Object valor = null;
                    Object index = datos.get(campo.getNombreLogico());
                    if (index != null) {
                        List valoresPosibles = campo.getAllValoresPosibles();
                        if (campo instanceof ListBox) {
                            String[] valores = (String[]) index;
                            Object[] etiquetas = new Object[valores.length];
                            for (int i = 0; i < valores.length; i++) {
                                ValorPosible vp = findValorPosible(valoresPosibles, valores[i]);
                                if (vp != null) {
                                    etiquetas[i] = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                }
                            }
                            valor = etiquetas;
                        } else {
                            ValorPosible vp = findValorPosible(valoresPosibles, (String) index);
                            if (vp != null) {
                                valor = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                            }
                        }
                    }
                    datosActual.put(campo.getNombreLogico() + "_text", valor);
                    */
                }
            }
        }
    }



    /**
     * Introduce un anexo.
     * @ejb.interface-method
     */
    public void introducirAnexo(String nombre, Anexo anexo) {
        FileBox campo = (FileBox) pantallaActual.findCampo(nombre);
        anexos.put(campo.getEtiquetaPDF(), anexo);
    }

    /**
     * Devuelve el valor de autocalcular un campo.
     *
     * Devuelve un string para campos monovaluados o una lista (de strings) para campos multivaluados
     *
     * @ejb.interface-method
     */
    public Object expresionAutocalculoCampo(String nombre) {
        Campo campo = pantallaActual.findCampo(nombre);
        if (campo == null) return new ArrayList();

        String script = campo.getExpresionAutocalculo();
        if (script == null || script.trim().length() == 0) {
            return new ArrayList();
        }

        Object result = ScriptUtil.evalScript(script, variablesScriptActuals(), this.isDebugEnabled());

        if (result instanceof NativeArray) {

        	List lstParams = new LinkedList();

        	// Array de strings
        	NativeArray params = (NativeArray) result;
    		if ( params != null )
    		{
    			Object [] ids = params.getIds();
    			for ( int i = 0; i < ids.length; i++ )
    			{
    				Object valorParametro = params.get( (( Integer ) ids[i] ).intValue() , params );
    				lstParams.add( valorParametro.toString() );
    			}
    		}

    		return lstParams;
        }else {
        	// Cadena simple
        	return ((result!=null?result.toString():""));
        }

    }

    /**
     * Devuelve el valor de autorellenar un campo.
     *
     * Devuelve:
     * 	-  un string para campos monovaluados
     *  -  una lista de strings para campos multivaluados
     *
     * @ejb.interface-method
     */
    public Object expresionAutorellenableCampo(String nombre) {
        Campo campo = pantallaActual.findCampo(nombre);
        if (campo == null) return new ArrayList();

        String script = campo.getExpresionAutorellenable();
        if (script == null || script.trim().length() == 0) {
            return new ArrayList();
        }

        // Obtenemos vbles actuales
        Map variables = variablesScriptActuals();

        // La expresion autorrellenable para un campo lista de elementos sólo esta
        // permitida si depende de datos de pantallas anteriores
    	if (campo instanceof ListaElementos){
    		return "";
    	}

        Object result = ScriptUtil.evalScript(script, variables, this.isDebugEnabled());

        if (result instanceof NativeArray) {

        	List resultList = new LinkedList();

        	// Array de strings
        	NativeArray params = (NativeArray) result;
    		if ( params != null )
    		{
    			Object [] ids = params.getIds();
    			for ( int i = 0; i < ids.length; i++ )
    			{
    				Object valorParametro = params.get( (( Integer ) ids[i] ).intValue() , params );
    				resultList.add( valorParametro.toString() );
    			}
    		}

    		return resultList;
        }else {
        	// Cadena simple
        	return ((result!=null?result.toString():""));
        }
    }


    /**
     * Devuelve el valor de la dependencia de un campo.
     * @ejb.interface-method
     */
    public boolean expresionDependenciaCampo(String nombre) {
        Campo campo = pantallaActual.findCampo(nombre);
        if (campo == null) return false;

        String script = campo.getExpresionDependencia();
        if (script == null || script.trim().length() == 0) {
            return false;
        }

        return ScriptUtil.evalBoolScript(script, variablesScriptActuals(), this.isDebugEnabled());
    }

    /**
     * Devuelve el valor de la dependencia de un campo (version nueva para readonly).
     * @ejb.interface-method
     */
    public String expresionDependenciaCampoV2(String nombre) {
        Campo campo = pantallaActual.findCampo(nombre);
        if (campo == null) return "";

        String script = campo.getExpresionDependencia();
        if (script == null || script.trim().length() == 0) {
            return "";
        }

        Object res = ScriptUtil.evalScript(script, variablesScriptActuals(), this.isDebugEnabled());
        return (res!=null?res.toString():"");
    }

    /**
     * Calcula los valors posibles de un campo.
     * @ejb.interface-method
     */
    public List /*ValorPosible*/ expresionValoresPosiblesCampo(String nombre) {
        Campo campo = pantallaActual.findCampo(nombre);
        if (campo == null) return null;

        CampoUtils.calcularValoresPosibles(campo, variablesScriptActuals(), this.isDebugEnabled());
        return campo.getAllValoresPosibles();
    }

    /**
     * Recupera un anexo.
     * @ejb.interface-method
     */
    public Anexo obtenerAnexo(String nombre) {
        FileBox campo = (FileBox) pantallaActual.findCampo(nombre);
        return (Anexo) anexos.get(campo.getEtiquetaPDF());
    }

    /**
     * Realiza el cambio de pantalla.
     * @ejb.interface-method
     */
    public void avanzarPantalla() {
        try {
            session.reconnect();

            // Procesar la pantalla
            pilaPantallasAnteriores.push(pantallaActual);
            pilaDatosAnteriores.push(datosActual);

            // Obtener la siguiente
            if (pantallaActual.isUltima()) { // Es la ultima pantalla!!!
                pantallaActual = null;
                datosActual = null;
            } else {
                // Evaluar expresion o seguir orden
                String expresion = pantallaActual.getExpresion();
                if (expresion == null || expresion.trim().length() == 0) { // Pantalla siguiente en orden
                	int orden = pantallaActual.getOrden() + 1;
                    pantallaActual = formulario.findPantalla(orden);
                    if (pantallaActual == null) log.warn("La pantalla con índice " + orden + " no existe!!");
                } else { // Evaluar script
                    Map variables = variablesScriptActuals();
                    String nombre = (String) ScriptUtil.evalScript(expresion, variables, this.isDebugEnabled());
                    pantallaActual = formulario.findPantalla(nombre);
                    if (pantallaActual == null) log.warn("La pantalla \"" + nombre + "\" no existe!!");
                }
            }

            if (pantallaActual != null) {
                if (pilaPantallasAnteriores.contains(pantallaActual)) {
                    // Avisar de que se esta reprocesando una pantalla!
                    log.warn("La pantalla \"" + pantallaActual.getNombre() + "\" ya ha sido procesada");
                }

                // Intetar recuperar datos introducidos anteriormente
                if (pantallasDatosPosteriores.containsKey(pantallaActual.getNombre())) {
                    datosActual = (Map) pantallasDatosPosteriores.get(pantallaActual.getNombre());
                    pantallasDatosPosteriores.remove(pantallaActual.getNombre());
                } else {
                    datosActual = PantallaUtils.valoresDefecto(pantallaActual, variablesScript(), debugEnabled);
                }
                debug("Pantalla Actual: " + pantallaActual.getNombre());
            } else {
                debug("Pantalla Actual es null");
            }

            session.disconnect();

        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }

    /**
     * Retrocede a la pantalla anterior.
     * @ejb.interface-method
     */
    public void retrocederPantalla() {
        if (pantallaActual != null) {
            pantallasDatosPosteriores.put(pantallaActual.getNombre(), datosActual);
        }

        if (pilaPantallasAnteriores.isEmpty()) {
            pantallaActual = null;
            datosActual = null;
        } else {
            pantallaActual = (Pantalla) pilaPantallasAnteriores.pop();
            datosActual = (Map) pilaDatosAnteriores.pop();
        }

        if (pantallaActual != null) {
            debug("Pantalla Actual: " + pantallaActual.getNombre());
        } else {
            debug("Pantalla Actual es null");
        }
    }

    /**
     * Retrocede a la pantalla anterior desde una pantalla detalle.
     * @ejb.interface-method
     */
    public void retrocederPantallaDetalle(boolean saveData) {

        // Guardamos datos elemento en map de datos listas elementos
    	if (saveData)
    		guardarElementoActual();

        // Desapilamos pantalla anterior con sus datos
    	if (pilaPantallasAnteriores.isEmpty()) {
            pantallaActual = null;
            datosActual = null;
        } else {
            pantallaActual = (Pantalla) pilaPantallasAnteriores.pop();
            datosActual = (Map) pilaDatosAnteriores.pop();
        }

        if (pantallaActual != null) {
            debug("Pantalla Actual: " + pantallaActual.getNombre());
        } else {
            debug("Pantalla Actual es null");
        }
    }
    /**
     * Retrocede a la pantalla anterior.
     * @ejb.interface-method
     */
    public void retrocederPantalla(String hasta) {
        boolean mesPantalles = !pilaPantallasAnteriores.isEmpty();
        boolean trobat = (pantallaActual != null && pantallaActual.getNombre().equals(hasta));
        while (!trobat && mesPantalles) {
            retrocederPantalla();
            trobat = (pantallaActual != null && pantallaActual.getNombre().equals(hasta));
            mesPantalles = !pilaPantallasAnteriores.isEmpty();
        }
    }
    /**
     * @ejb.interface-method
     */
    public InstanciaBean obtenerInstanciaBean() {
        InstanciaBean bean = new InstanciaBean();

        bean.setLocale(locale);
        bean.setModelo(formulario.getModelo());
        bean.setPerfil(perfil.getCodigoEstandard());
        bean.setDataMaps(new ArrayList(pilaDatosAnteriores));
        if (datosActual != null) {
            bean.getDataMaps().add(datosActual);
        }
        bean.setAnexos(new HashMap(anexos));

        return bean;
    }

    /**
     * Obtiene todos los datos rellenados.
     * @ejb.interface-method
     */
    public Map obtenerDatosCompletos() {
        try {
            session.reconnect();

            Map datosCompletos = new HashMap();

            for (int k = 0; k < pilaPantallasAnteriores.size(); k++) {
                Pantalla pantalla = (Pantalla) pilaPantallasAnteriores.get(k);
                Map datos = (Map) pilaDatosAnteriores.get(k);
            //}
            //
            //while (!pilaPantallasAnteriores.isEmpty()) {
            //    Pantalla pantalla = (Pantalla) pilaPantallasAnteriores.pop();
            //    Map datos = (Map) pilaDatosAnteriores.pop();

                for (int i = 0; i < pantalla.getCampos().size(); i++) {
                    Campo campo = (Campo) pantalla.getCampos().get(i);
                    if (campo.getEtiquetaPDF() != null) {
                        Object etiqueta = null;
                        if (datos.containsKey(campo.getNombreLogico())) {
                            Object valor = datos.get(campo.getNombreLogico());
                            if (valor != null) {
                                if (campo.getPatron() != null) {
                                    Patron patron = campo.getPatron();
                                    if (patron.isEjecutar()) {
                                        String script = patron.getCodigo();
                                        Map params = new HashMap();
                                        params.put("input", valor);
                                        setContextVariables(params);
                                        etiqueta = ScriptUtil.evalScript(script, params, this.isDebugEnabled());
                                    } else {
                                        String pattern = patron.getCodigo();
                                        Format format = null;
                                        if (valor instanceof String) {
                                            format = new MessageFormat(pattern, locale);
                                        } else if (valor instanceof Number) {
                                            format = new DecimalFormat(pattern);
                                            ((DecimalFormat) format).setDecimalFormatSymbols(new DecimalFormatSymbols(locale));
                                        }

                                        if (format != null) {
                                            etiqueta = format.format(valor);
                                        }
                                    }
                                } else {
                                    if (campo.isIndexed()) {
                                        List valoresPosibles = campo.getAllValoresPosibles();
                                        if (campo instanceof ListBox || campo instanceof TreeBox) {
                                            String[] valores = (String[]) valor;
                                            Object[] etiquetas = new Object[valores.length];
                                            for (int j = 0; j < valores.length; j++) {
                                                ValorPosible vp = findValorPosible(valoresPosibles, valores[j]);
                                                if (vp != null) {
                                                    TraValorPosible traVp = (TraValorPosible) vp.getTraduccion();
                                                    etiquetas[j] = traVp.getEtiqueta();
                                                }
                                            }
                                            etiqueta = StringUtils.join(etiquetas, "\n");
                                       } else {
                                            ValorPosible vp = findValorPosible(valoresPosibles, (String) valor);
                                            if (vp != null) {
                                                TraValorPosible traVp = (TraValorPosible) vp.getTraduccion();
                                                if (campo.isImagen()) {
                                                    etiqueta = traVp.getArchivo().getDatos();
                                                } else {
                                                    etiqueta = traVp.getEtiqueta();
                                                }
                                            }
                                        }
                                    } else if (valor.getClass().isArray()) {
                                        etiqueta = StringUtils.join((Object[]) valor, "\n");
                                    } else if (valor instanceof Boolean) {
                                        etiqueta = ((Boolean) valor).booleanValue() ? "X" : "";
                                    } else {
                                        etiqueta = valor.toString();
                                    }
                                }
                            }
                        } else {
                            // No hay valor para el campo
                            log.warn("No hay valor para la etiqueta '" + campo.getEtiquetaPDF() + "'");
                        }

                        if (etiqueta == null) {
                            etiqueta = "";
                        } else if (!"".equals(etiqueta)) {
                            String expresion = campo.getExpresionPostProceso();
                            if (expresion != null && expresion.trim().length() > 0) {
                                Map params = new HashMap();
                                params.put("input", etiqueta);
                                setContextVariables(params);
                                etiqueta = ScriptUtil.evalScript(expresion, params, this.isDebugEnabled());
                            }
                        }

                        datosCompletos.put(campo.getEtiquetaPDF(), etiqueta);
                    }
                }
            }

            session.disconnect();
            datosCompletos.putAll(anexos);
            return datosCompletos;
        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }

    /**
     * @ejb.interface-method
     */
    public String generarNumeroJustificante() {
        try {
            session.reconnect();

            StringBuffer numJus = new StringBuffer(13);
            numJus.append(formulario.getModelo()); // 1-3 caracteres modelo
            numJus.append('2'); // 4 caracter - 2 para 2002.
            numJus.append('8'); // 5 caracter - Organisme - 8 per oficina virtual

            long num = GeneradorNumSeq.getInstance().generar(session, formulario);
            String numSeq = String.valueOf(num); // Numero de seq en String.
            numSeq = StringUtils.leftPad(numSeq, 7, "0"); // Lo alargamos hasta 7 cifras con 0s al principio.

            numJus.append(numSeq); // 6-12 numero de sequencia.

            // Cálculo DSR-7
            int reste = (int) (Long.parseLong(numJus.toString()) % 7);
            int codi = (reste == 0 ? 0 : (7 - reste));

            numJus.append(codi); // 13 - digito de control

            session.disconnect();
            return numJus.toString();
        } catch (HibernateException e) {
            closeReadOnly(session);
            throw new EJBException(e);
        }
    }

    /**
     * Recorre las diferentes salidas, y ejecuta los conectores con las propiedades
     * indicadas.
     * @ejb.interface-method
     */
    public Result[] ejecutarSalidas() {
        Map variables = variablesScriptActuals();
        Map datosCompletos = obtenerDatosCompletos();
        try {
            session.reconnect();

            List salidas = formulario.getSalidas();
            Result[] resultados = new Result[salidas.size()];
            for (int i = 0; i < salidas.size(); i++) {
                Salida salida = (Salida) salidas.get(i);
                String conectorClass = salida.getPunto().getImplementacion();
                String conectorName = salida.getPunto().getNombre();
                debug("Processant conector " + conectorName);
                try {
                    Conector conector = ConectorConfigurator.initConector(conectorClass);

                    // Crear un map con (nombre, valor) de las propiedades configuradas
                    Set propiedades = salida.getPropiedades();
                    Map propMap = new HashMap(propiedades.size());
                    for (Iterator iterator = propiedades.iterator(); iterator.hasNext();) {
                        PropiedadSalida propSal = (PropiedadSalida) iterator.next();
                        String key = propSal.getNombre();
                        Object value;
                        if (propSal.getPlantilla() != null) { // La propiedad és una archivo.
                            Hibernate.initialize(propSal.getPlantilla());
                            value = propSal.getPlantilla();
                        } else if (propSal.isExpresion()) { // O una expresión a evaluar
                            value = ScriptUtil.evalScript(propSal.getValor(), variables, this.isDebugEnabled());
                        } else { // O directamente un valor (String)
                            value = propSal.getValor();
                        }
                        propMap.put(key, value);
                    }
                    // Configurar el conector con el map de propiedades
                    ConectorConfigurator.setConectorProperties(conector, propMap);

                    // Ejecutar el conector y guardar los resultados.
                    resultados[i] = conector.exec(datosCompletos);

                } catch (ConectorException e) {
                    log.error("Error amb el conector " + conectorName + ", clase: " + conectorClass, e);
                    resultados[i] = new MessageResult("Error en {0}", conectorName);
                }
            }

            session.disconnect();
            return resultados;
        } catch (HibernateException e) {
            closeReadOnly(session);
            throw new EJBException(e);
        }
    }

    /**
     * Libera los recursos, entre ellos la sesion.
     */
    public void ejbRemove() {
        try {
            super.ejbRemove();
            formulario = null;
            perfil = null;

            pantallaActual = null;
            datosActual = null;

            pilaPantallasAnteriores.clear();
            pilaDatosAnteriores.clear();

            pantallasDatosPosteriores.clear();
        } finally {
            closeReadOnly(session);
        }
    }

    public void ejbPassivate() throws EJBException, RemoteException {
        debug("passivated");
    }

    public void ejbActivate() throws EJBException, RemoteException {
        debug("activated");
    }


    /**
     * Obtiene un map de nombre, valor de las variables que se pueden
     * utilizar en el motor de script, incluyendo las ultimas variables introducidas.
     * @return map de nombre (String), valor (Object)
     */
    protected Map variablesScriptActuals() {
        Map variables = ScriptUtil.prefixMap(datosActual, "f_");
        variables.putAll(variablesScript());

        // --- INDRA: LISTA ELEMENTOS ----
        Map variablesListasElementos = this.variablesScriptListaElementos(pantallaActual);
        variables.putAll(variablesListasElementos);
        // --- INDRA: LISTA ELEMENTOS ----

        // ---- INDRA: VBLE ESPECIAL PARA LOGEAR SCRIPTS
        variables.put(ScriptUtil.ID_LOG_SCRIPT,logScript);
        // ---- INDRA: VBLE ESPECIAL PARA LOGEAR SCRIPTS

        return variables;
    }



    /**
     * Obtiene un map de nombre, valor de las variables que se pueden
     * utilizar en el motor de script.
     * @return map de nombre (String), valor (Object)
     */
    protected Map variablesScript() {
        Map variables = new HashMap();
        setContextVariables(variables);
        for (int i = 0; i < pilaPantallasAnteriores.size(); i++) {
            Pantalla pantalla = (Pantalla) pilaPantallasAnteriores.elementAt(i);
            Map datos = (Map) pilaDatosAnteriores.elementAt(i);
            Map vars = ScriptUtil.prefixMap(datos, "f_" + pantalla.getNombre() + "_");
            variables.putAll(vars);

            // --- INDRA: LISTA ELEMENTOS ----
            Map variablesListasElementos = this.variablesScriptListaElementos(pantalla);
            variables.putAll(variablesListasElementos);
            // --- INDRA: LISTA ELEMENTOS ----

            // ---- INDRA: VBLE ESPECIAL PARA LOGEAR SCRIPTS
            variables.put(ScriptUtil.ID_LOG_SCRIPT,logScript);
            // ---- INDRA: VBLE ESPECIAL PARA LOGEAR SCRIPTS
        }
        return variables;
    }

    /**
     * Fija en un Map de variables los valores que no dependen de
     * datos introducidos, sinó del contexto.
     * @param variables
     */
    private void setContextVariables(Map variables) {
        variables.put("lang", locale.getLanguage());
        variables.put("user", ctx.getCallerPrincipal().getName());
    }

    private ValorPosible findValorPosible(List valoresPosibles, String valor) {
        for (int i = 0; i < valoresPosibles.size(); i++) {
            ValorPosible valorPosible = (ValorPosible) valoresPosibles.get(i);
            if (valor.equals(valorPosible.getValor())) {
                return valorPosible;
            }
        }
        debug("Valor " + valor + " no trobat a la llista.");
        return null;
    }

    /**
     * Elimina del mapa que contiene los valores de los campos de una pantalla,
     * los valores asociados a los campos que están en estado de bloqueo.
     */
    private void eliminaDatosCamposBloqueados(Map datos) {
        for (Iterator i = pantallaActual.getCampos().iterator(); i.hasNext();) {
            Campo campo = (Campo) i.next();
            if (campo.isBloqueado()) {
                datos.remove(campo.getNombreLogico());
                if (campo.isIndexed()) {
                    datos.remove(campo.getNombreLogico() + "_text");
                }
            }
        }
    }

    /**
     * Elimina del mapa que contiene los valores de los campos de captcha (no se deben actualizar).
     */
    private void eliminaDatosCamposCaptcha(Map datos) {
        for (Iterator i = pantallaActual.getCampos().iterator(); i.hasNext();) {
            Campo campo = (Campo) i.next();
            if (campo instanceof Captcha) {
                datos.remove(campo.getNombreLogico());
            }
        }
    }


    // ---- INDRA: SOPORTE A LISTAS DE ELEMENTOS
    /**
     * Obtiene los elementos de una lista de elementos
     * @ejb.interface-method
     */
    public List obtenerDatosListaElementos(String nombreCampo) {
        String referencia = CampoUtils.getReferenciaListaElementos(this.pantallaActual.getNombre(),nombreCampo);
        return (List) this.datosListasElementos.get(referencia);
    }
    /**
     * Obtiene la lista de campos que se muestran en la tabla
     * @ejb.interface-method
     */
    public List obtenerCamposTablaListaElementos(String nombreCampo) {
        String referencia = CampoUtils.getReferenciaListaElementos(this.pantallaActual.getNombre(),nombreCampo);
        Pantalla p = encontrarPantallaDetalle(referencia);
        List camposTabla = new ArrayList();
        for (Iterator it=p.getCampos().iterator();it.hasNext();){
        	Campo c = (Campo) it.next();
        	if (c.isMostrarEnTabla()) camposTabla.add(c);
        }
        return camposTabla;
    }


    /**
     * Pasa a la pantalla de detalle
     * @ejb.interface-method
     */
    public void avanzarPantallaDetalle(String campo,String accion,String index) {
        try {
            session.reconnect();

            // Procesar la pantalla
            pilaPantallasAnteriores.push(pantallaActual);
            pilaDatosAnteriores.push(datosActual);

            // Obtener la pantalla detalle
            String referencia = CampoUtils.getReferenciaListaElementos(this.pantallaActual.getNombre(),campo);
            Pantalla p = encontrarPantallaDetalle(referencia);
            pantallaActual = p;

            if (accion.equals("insertar")){
            	// insertar --> obtenemos datos por defecto
            	datosActual = PantallaUtils.valoresDefecto(pantallaActual,null,variablesScript(),null, this.isDebugEnabled());
            }else{
            	// modificar --> obtenemos datos elemento
            	datosActual = obtenerElemento(index);
            	// Refrescamos valores posibles de los campos
            	INDRA_refrescarValoresPosibles(datosActual);
            }

            // Guardamos operacion a realizar en el detalle: campo,accion,index
            this.accionPantallaDetalleActual = accion;
            this.campoPantallaDetalleActual = campo;
            this.indexPantallaDetalleActual = index;

            debug("Pantalla Actual: " + pantallaActual.getNombre());

            session.disconnect();

        } catch (HibernateException he) {
            closeReadOnly(session);
            throw new EJBException(he);
        }
    }

    protected Pantalla encontrarPantallaDetalle(String referencia){
    	Pantalla p = null;
    	boolean enc=false;
        for (Iterator it = this.formulario.getPantallas().iterator();it.hasNext();){
        	p = (Pantalla) it.next();
        	if (referencia.equals(p.getComponenteListaElementos())) {
        		enc = true;
        		break;
        	}
        }
        if (!enc) throw new EJBException("No se encuentra pantalla detalle asociada a campo " + referencia);
        return p;
    }


    /**
     * Actualiza los datos asociados al elemento actual de una lista de elementos
     */
    private void guardarElementoActual() {

    	String accion=this.accionPantallaDetalleActual;
    	String indice=this.indexPantallaDetalleActual;
    	Map datos = this.datosActual;

    	// Obtenemos lista de elementos del campo
    	String referenciaCampoListaElementos = this.pantallaActual.getComponenteListaElementos();
    	if (StringUtils.isEmpty(referenciaCampoListaElementos)){
    		log.error("Pantalla '" + this.pantallaActual.getNombre() + "' no es una pagina de detalle de lista elementos" );
    		return;
    	}
    	List elementosCampoListaElementos = (List) this.datosListasElementos.get(referenciaCampoListaElementos);
    	if (elementosCampoListaElementos == null) {
    		elementosCampoListaElementos = new ArrayList();
    		this.datosListasElementos.put(referenciaCampoListaElementos,elementosCampoListaElementos);
    	}

    	// Validamos parametros
    	if (StringUtils.isEmpty(accion) || (!accion.equals("insertar") && !accion.equals("modificar"))){
    		log.error("Accion no valida para introducirDatosPantallaDetalle: "+ accion);
    		return;
    	}
    	int ind;
    	try{
    		ind = Integer.parseInt(indice);
    	}catch(NumberFormatException ne){
    		log.error("El parametro indice no es un numero");
    		return;
    	}

    	// Establece datos actuales elemento
    	if (accion.equals("modificar")){
    		if (ind < 0 || ind > elementosCampoListaElementos.size() - 1){
    			log.error("No existe elemento a modificar");
        		return;
    		}
    		elementosCampoListaElementos.set(ind,datos);
    	}else{
    		if (ind <0 || ind >= elementosCampoListaElementos.size() - 1){
    			elementosCampoListaElementos.add(datos);
    		}else{
    			elementosCampoListaElementos.add(ind + 1,datos);
    		}
    	}

    }


    /**
     * Elimina elemento de una lista de elementos
     * @ejb.interface-method
     */
    public void eliminarElemento(String campo,String indice) {

    	// Obtenemos lista de elementos del campo
    	String referenciaCampoListaElementos = CampoUtils.getReferenciaListaElementos(pantallaActual.getNombre(),campo);
    	if (StringUtils.isEmpty(referenciaCampoListaElementos)){
    		log.error("Pantalla '" + this.pantallaActual.getNombre() + "' no es una pagina de detalle de lista elementos" );
    		return;
    	}
    	List elementosCampoListaElementos = (List) this.datosListasElementos.get(referenciaCampoListaElementos);
    	if (elementosCampoListaElementos == null) {
    		elementosCampoListaElementos = new ArrayList();
    		this.datosListasElementos.put(referenciaCampoListaElementos,elementosCampoListaElementos);
    	}

    	// Validamos parametros
    	int ind;
    	try{
    		ind = Integer.parseInt(indice);
    	}catch(NumberFormatException ne){
    		log.error("El parametro indice no es un numero");
    		return;
    	}

    	// Obtenemos datos actuales elemento
		if (ind < 0 || ind > elementosCampoListaElementos.size() - 1){
			log.error("No existe elemento a modificar");
    		return;
		}
		elementosCampoListaElementos.remove(ind);

    }


    /**
     * Sube elemento de orden de una lista de elementos
     * @ejb.interface-method
     */
    public void subirElemento(String campo,String indice) {
    	cambiarOrden(campo,indice,true);
    }

    /**
     * Baja elemento de orden de una lista de elementos
     * @ejb.interface-method
     */
    public void bajarElemento(String campo,String indice) {
    	cambiarOrden(campo,indice,false);
    }

    private void cambiarOrden(String campo,String indice,boolean subir){
//    	 Obtenemos lista de elementos del campo
    	String referenciaCampoListaElementos = CampoUtils.getReferenciaListaElementos(pantallaActual.getNombre(),campo);
    	if (StringUtils.isEmpty(referenciaCampoListaElementos)){
    		log.error("Pantalla '" + this.pantallaActual.getNombre() + "' no es una pagina de detalle de lista elementos" );
    		return;
    	}
    	List elementosCampoListaElementos = (List) this.datosListasElementos.get(referenciaCampoListaElementos);
    	if (elementosCampoListaElementos == null) {
    		elementosCampoListaElementos = new ArrayList();
    		this.datosListasElementos.put(referenciaCampoListaElementos,elementosCampoListaElementos);
    	}

    	// Validamos parametros
    	int ind;
    	try{
    		ind = Integer.parseInt(indice);
    	}catch(NumberFormatException ne){
    		log.error("El parametro indice no es un numero");
    		return;
    	}

    	// Comprobamos que existe elemento
		if (ind < 0 || ind > elementosCampoListaElementos.size() - 1){
			log.error("No existe elemento a modificar");
    		return;
		}

		//	Cambiamos posicion
		if (subir){
			if (ind == 0) return; // Ya esta el primero
			Object o = elementosCampoListaElementos.get(ind);
			elementosCampoListaElementos.remove(ind);
			elementosCampoListaElementos.add(ind - 1,o);
		}else{
			if (ind == elementosCampoListaElementos.size() - 1) return; // Ya esta el ultimo
			Object o = elementosCampoListaElementos.get(ind);
			elementosCampoListaElementos.remove(ind);
			elementosCampoListaElementos.add(ind + 1,o);
		}
    }

    /**
     * Obtiene datos elemento
     */
    private Map obtenerElemento(String indice) {

    	// Obtenemos lista de elementos del campo
    	String referenciaCampoListaElementos = this.pantallaActual.getComponenteListaElementos();
    	if (StringUtils.isEmpty(referenciaCampoListaElementos)){
    		log.error("Pantalla '" + this.pantallaActual.getNombre() + "' no es una pagina de detalle de lista elementos" );
    		return new HashMap();
    	}
    	if (StringUtils.isEmpty(referenciaCampoListaElementos)){
    		log.error("Pantalla '" + this.pantallaActual.getNombre() + "' no es una pagina de detalle de lista elementos" );
    		return new HashMap();
    	}
    	List elementosCampoListaElementos = (List) this.datosListasElementos.get(referenciaCampoListaElementos);
    	if (elementosCampoListaElementos == null) {
    		elementosCampoListaElementos = new ArrayList();
    		this.datosListasElementos.put(referenciaCampoListaElementos,elementosCampoListaElementos);
    	}

    	// Validamos parametros
		int ind;
    	try{
    		ind = Integer.parseInt(indice);
    	}catch(NumberFormatException ne){
    		log.error("El parametro indice no es un numero");
    		return new HashMap();
    	}
    	// Obtenemos datos actuales elemento
		if (ind < 0 || ind > elementosCampoListaElementos.size() - 1){
			log.error("No existe elemento a modificar");
			return new HashMap();
		}
		Map datosElemento = (Map) elementosCampoListaElementos.get(ind);

		// Devolvemos una copia para que los valores no se modifiquen
		Map copia = new HashMap();
		copia.putAll(datosElemento);
		return copia;

    }

    /**
     * Obtiene map con variables asociadas a los campos de listas de elementos de una pantalla
     *
     * f_campoLista_size,f_campoLista_id1_campox .... f_campoLista_id1_campoy],f_campoLista_idn_campox .... f_campoLista_idn_campoy
     *
     * @param p
     * @return
     */
    protected Map variablesScriptListaElementos(Pantalla pantalla){
    	Map variables = new HashMap();

    	// Si no es la pantalla actual introducimos prefijo pantalla
    	String prefix="";
    	if (pantalla.getId().longValue() != pantallaActual.getId().longValue()) prefix = pantalla.getNombre()+"_";

        if (StringUtils.isEmpty(pantalla.getComponenteListaElementos())){
        	for (Iterator it=pantalla.getCampos().iterator();it.hasNext();){
        		Campo campo = (Campo) it.next();
        		if (!(campo instanceof ListaElementos)) continue;
        		List elementos = (List) this.datosListasElementos.get(CampoUtils.getReferenciaListaElementos(pantalla.getNombre(),campo.getNombreLogico()));
        		variables.put("f_"+prefix+campo.getNombreLogico() + "_size",(elementos!=null?""+elementos.size():"0"));
        		if (elementos == null)continue;
        		for (int j=0;j<elementos.size();j++){
        			Map variablesElemento = ScriptUtil.prefixMap((Map) elementos.get(j), "f_"+prefix+campo.getNombreLogico() + "_id" + (j+1) + "_");
        			variables.putAll(variablesElemento);
        		}
        	}
        }

        return variables;
    }

    /**
     * Obtiene los datos de las listas de elementos de la pantalla actual, prefijados con f_&lt;nombre pantalla%gt;_
     * @ejb.interface-method
     */
    public Map obtenerDatosListasElementos() {
        return variablesScriptListaElementos(pantallaActual);
    }

    /**
     * Obtiene la accion a realizar sobre el detalle y el indice del elemento
     * ejb.interface-method

     YA SE HARA SI HACE FALTA (HABRIA Q VER EN Q SCRIPTS SE PONE, igual basta con ponerlo en variablesScriptListaElementos detectando
     si es una pantalla de detalle

    public Map obtenerAccionIndicePantallaDetalle() throws DelegateException {
    	Map vars = new HashMap();
    	String prefix = CampoUtils.getPantallaListaElementos(pantallaActual.getComponenteListaElementos());

    	vars.put("f_"+);

    	return vars;
    }
    */

    // --- INDRA: FIN


    // -- INDRA: LOG SCRIPTS
    /**
     * Obtiene el log de los scripts
     * @ejb.interface-method
     */
    public List obtenerLogScripts() {
    	if (logScript == null) return null;
    	return logScript.getLogs();
    }

    /**
     * Resetea el log de los scripts
     * @ejb.interface-method
     */
    public void limpiarLogScripts() {
    	if (logScript != null) logScript.resetLogs();
    }

    protected boolean permitirScriptDebug(){

    	// Obtenemos entorno: desarrollo o produccion
    	if (scriptDebug == null) {
	        try{
	        	String entorno = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("entorno");
				if ("DESARROLLO".equals(entorno))
					scriptDebug = new Boolean(true);
				else
					scriptDebug = new Boolean(false);
	    	}catch (Exception ex){
	    		scriptDebug=new Boolean(false);
	    	}
    	}

    	return scriptDebug.booleanValue();
    }
    // -- INDRA: LOG SCRIPTS

    /**
     * Obtener captcha.
     * @param fieldName nombre campo
     * @return captcha
     *
     * @ejb.interface-method
     */
    public String obtenerCaptcha(String fieldName) {
    	if (this.pantallaActual == null || this.datosActual == null) {
    		return "";
    	}
    	Campo captcha = pantallaActual.findCampo(fieldName);
    	if (captcha == null || !(captcha instanceof Captcha)) {
    		return "";
    	}
    	return (String) this.datosActual.get(fieldName);
    }


    /**
     * Regenera captcha.
     * @param fieldName nombre campo
     *
     * @ejb.interface-method
     */
    public void regenerarCaptcha(String fieldName) {
    	if (this.pantallaActual == null || this.datosActual == null) {
    		return;
    	}
    	Campo captcha = pantallaActual.findCampo(fieldName);
    	if (captcha == null || !(captcha instanceof Captcha)) {
    		return;
    	}
    	String newValue = CaptchaUtils.generateCaptcha();
		this.datosActual.put(fieldName, newValue );
    }

    /**
     * Obtiene si debug enabled.
     * @ejb.interface-method
     */
    public boolean isDebugEnabled() {
		return debugEnabled;
	}

	protected void setDebugEnabled(boolean debugEnabled) {
		this.debugEnabled = debugEnabled;
	}

	protected void debug(String mensaje) {
		if (this.debugEnabled) {
			log.debug(mensaje);
		}
	}


}

