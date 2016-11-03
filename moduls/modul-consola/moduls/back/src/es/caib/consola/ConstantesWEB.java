package es.caib.consola;

/**
 * Class ConstantesWEB.
 */
public final class ConstantesWEB {

    /**
     * Instancia un nuevo constantes web de ConstantesWEB.
     */
    private ConstantesWEB() {
    }

    
    // TODO QUITARLO Y USAR TYPEMODOACCESO ¿FICHA?
    // MODOS DE EDICION
    /** Modo ficha (visualizacion como ficha). */
    public static final String MODOFICHA = "ficha";   
    /** Modo consulta. */
    public static final String MODOCONSULTA = "consulta";
    /** Modo edicion. */
    public static final String MODOEDICION = "edicion";
    /** Modo nuevo. */
    public static final String MODONUEVO = "nuevo";
    
    // SELECTORES
    /** Selector de organismo. */
    public static final String SELECTOR_ORGANISMO = "SELECTOR_ORGANISMO";
    /** Selector de unidad administratica. */
    public static final String SELECTOR_UNIDAD_ADMINISTRATIVA = "SELECTOR_UNIDAD_ADMINISTRATIVA";
    /** Selector de gestor. */
    public static final String SELECTOR_GESTOR = "SELECTOR_GESTOR";
    /** Selector de procedimiento. */
    public static final String SELECTOR_PROCEDIMIENTO = "SELECTOR_PROCEDMIENTO";
    
    
    // ATRIBUTOS SESION 
    // TODO RENOMBRAR A ATTRIBUTE_XXX
    
    /** Atributo sesion para idiomas soportados. */
    public static final String IDIOMAS_PLATAFORMA = "idiomasPlataforma";
    
    /** Atributo sesion para establecer código trámite que se esta editando. */
    public static final String TRAMITE = "tramite";
    
    /** Atributo sesion para establecer id trámite que se esta editando. */
    public static final String TRAMITE_ID = "tramite_id";
    
    /** Atributo sesión para establecer codigo version tramite que se esta editando. */
    public static final String VERSION = "version";
    
    /** Atributo sesión para establecer numero version tramite que se esta editando. */
    public static final String VERSION_NUM = "version_num";
    
    // PARAMETROS
    // TODO RENOMBRAR A PARAMETER_XXX
    
    /** Parametro modo de edicion. */
    public static final String PARAM_MODO_ACCESO = "modo";
    
    // TODO QUITAR TODOS ANTERIORES Y USAR ESTE UNICO!!!!!!!!!!!!!!
    /** Parámetro objeto editado. */
    public static final String PARAM_OBJETO_EDICION = "parametroObjetoEdicion";
    
    /** Parámetro objeto padre. */
    public static final String PARAM_OBJETO_PADRE = "parametroObjetoPadre";
    
    /** Parámetro SRC de ventana de ayuda. */
    public static final String SRC = "src";
    
    /** Parámetro ventana padre de la ventana modal. */
    public static final String PADRE = "padre";
    
    
    // TODO MIRAR DE QUITAR LOS Q SOBREN
    
    /** Parámetro dominio (objeto). */
    public static final String DOMINIO = "dominio";
    
    /** Parámetro procedimiento (objeto). */
    public static final String PROCEDIMIENTO = "procedimiento";
    
    /** Parámetro gestor bandeja (objeto). */
    public static final String GESTOR_BANDEJA = "gestorBandeja";
    
        
    /** Parámetro controller pantalla version tramite. */
    public static final String VERSIONTRAMITE_CONTROLLER = "versionTramiteController";

    /** Parámetro anexo (codigo). */
    public static final String ANEXO = "anexo";
    
    /** Parámetro pago (codigo). */
    public static final String PAGO = "pago";
    
    /** Parámetro formulario (codigo). */
    public static final String FORMULARIOTRAMITE = "formularioTramite";

    /** Parámetro error plataforma (objeto). */
    public static final String ERROR_PLATAFORMA = "errorPlataforma";
    
    /** Parámetro version tramite (objeto).*/
    public static final String PARAMETER_VERSION_TRAMITE = "versionTramite";
    
    /** Parámetro procedimiento (objeto). */
    public static final String PARAMETER_FORMULARIO_REUSABLE = "formularioReusable";
    
    /** Parámetro id elemento formulario (codigo). */
    public static final String PARAMETER_ID_ELEMENTO_FORMULARIO = "idElementoFormulario";
    
    // CONSTANTES VISUALIZACION
    
    /** Altura tabs. */
    public static final int TABS_HEIGHT = 65;
    
    /** Altura banda de paginacion. */
    public static final int PAGINACION_HEIGHT = 70;
    
    /** Altura fila tabla. */
    public static final int FILA_HEIGHT = 27;
    
    /**Altura fila con check. */
    public static final int FILA_CHECK_HEIGHT = 32;

    /** Altura header. */
    public static final int HEADER_HEIGHT = 170;
    
    /** Altura formulario. */
    public static final int FORM_HEIGHT = 400;
    
    /** Altura include header. */
    public static final int HEADER_INCLUDE_HEIGHT = 35;

    /** Altura div titulo. */
    public static final int DIV_TITULO = 25;
    
    // LABELS GENERICOS
    
    /** Boton editar. */
    public static final String EDITAR = "boton.editar";

    /** Boton añadir. */
    public static final String ANYADIR = "boton.anyadir";

    /** Boton consultar. */
    public static final String CONSULTAR = "boton.consultar";
    
    /** Titulo mensaje alerta. */
    public static final String WARNING = "titulo.warning";
    
    /** Titulo mensaje informacion. */
    public static final String INFO = "titulo.info";

    /** Titulo mensaje error. */
    public static final String ERROR = "titulo.error";

   
    // IDIOMAS
    
    /** Idioma español. */
    public static final String ESPANYOL = "es";

    /** Idioma ingles. */
    public static final String INGLES = "en";

    /** Idioma catalan. */
    public static final String CATALAN = "ca";

        
    // EVENTOS

    /** Evento click arbol version tramite. */
    public static final String EVENTO_TREEITEM_CLICK = "onClickTreeItem";

    // PATHS ZULS
    /** Propiedades version tramite */
    public static final String ZUL_PROPÌEDADES_VERSION = "/gestor/windows/ges-versionTramite-propiedades-win.zul";

    /** Paso registar.*/
    public static final String ZUL_PASO_REGISTRAR = "/gestor/windows/ges-versionTramite-pasoRegistrar-win.zul";

    /** Pago.*/
    public static final String ZUL_PAGO = "/gestor/windows/ges-versionTramite-pago-win.zul";
    
    /** Paso pagar.*/
    public static final String ZUL_PASO_PAGAR = "/gestor/windows/ges-versionTramite-pasoPagar-win.zul";

    /** Anexo.*/
    public static final String ZUL_ANEXO = "/gestor/windows/ges-versionTramite-anexo-win.zul";

    /** Paso anexar.*/
    public static final String ZUL_PASO_ANEXAR = "/gestor/windows/ges-versionTramite-pasoAnexar-win.zul";

    /** Formulario.*/
    public static final String ZUL_FORMULARIO = "/gestor/windows/ges-versionTramite-formulario-win.zul";

    /** Paso rellenar.*/
    public static final String ZUL_PASO_RELLENAR = "/gestor/windows/ges-versionTramite-pasoRellenar-win.zul";

    /** Paso debe saber.*/
    public static final String ZUL_PASO_DEBESABER = "/gestor/windows/ges-versionTramite-pasoDebeSaber-win.zul";

    /** Pasos version.*/
    public static final String ZUL_PASOS_VERSION = "/gestor/windows/ges-versionTramite-pasos-win.zul";

    /** Mensajes version.*/
    public static final String ZUL_MENSAJES_VERSION = "/gestor/windows/ges-versionTramite-mensajes-win.zul";

    /** Control acceso version.*/
    public static final String ZUL_ACCESO_VERSION = "/gestor/windows/ges-versionTramite-acceso-win.zul";
    
    /** Script. */
    public static final String ZUL_SCRIPT = "/gestor/windows/ges-script-win.zul";

    // PATH VENTANAS
    /** Versiones tramite. */
    public static final String WPATH_VERSIONES = "/priWindow/contenedorPrincipal/verTramWindow";
    /** Paso rellenar. */
    public static final String WPATH_PASO_RELLENAR = "/priWindow/contenedorPrincipal/verTramWindow/detalle/wRellenarFormularios";
    
    
    
    // OTRAS CONSTANTES
    
    /** Formato por defecto para fechas. */
    public static final String FORMAT_FECHAS = "dd/MM/yyyy HH:mm";
    
    
    
    // ------- NO USADOS
    
   

    /** Atributo constante MAX_UPLOAD de ConstantesWEB. */
    public static final int MAX_UPLOAD = 5120;

  

   

  

    /** Atributo constante FOOTER_HEIGHT de ConstantesWEB. */
    public static final int FOOTER_HEIGHT = 35;

    

    /** Atributo constante HEADER_FOOTER_WIN_HEIGHT de ConstantesWEB. */
    public static final int HEADER_FOOTER_WIN_HEIGHT = 90;

    

    /** Atributo constante FORM_CAPTURA_HEIGHT de ConstantesWEB. */
    public static final int FORM_CAPTURA_HEIGHT = 230;

    /** Atributo constante CONTROLACCESO_HEIGHT de ConstantesWEB. */
    public static final int CONTROLACCESO_HEIGHT = 580;

    /** Atributo constante ANEXO_HEIGHT de ConstantesWEB. */
    public static final int ANEXO_HEIGHT = 415;

    /** Atributo constante ANEXO_HEIGHT_ALTO de ConstantesWEB. */
    public static final int ANEXO_HEIGHT_ALTO = 555;

    /** Atributo constante PAGO_HEIGHT de ConstantesWEB. */
    public static final int PAGO_HEIGHT = 270;

    /**
     * Atributo constante DATOS_IDENTIFICATIVOS_NO_FINAL_HEIGHT de
     * ConstantesWEB.
     */
    public static final int DATOS_IDENTIFICATIVOS_NO_FINAL_HEIGHT = 240;

    /** Atributo constante DATOS_IDENTIFICATIVOS_HEIGHT de ConstantesWEB. */
    public static final int DATOS_IDENTIFICATIVOS_HEIGHT = 260;

    /** Atributo constante DETALLE_PASO_LISTADO_HEIGHT de ConstantesWEB. */
    public static final int DETALLE_PASO_LISTADO_HEIGHT = 70;

    /** Atributo constante DETALLE_PASO_HEIGHT de ConstantesWEB. */
    public static final int DETALLE_PASO_HEIGHT = 36;

    /** Atributo constante RELLENAR_HEIGHT de ConstantesWEB. */
    public static final int RELLENAR_HEIGHT = 500;

    /** Atributo constante ANEXAR_HEIGHT de ConstantesWEB. */
    public static final int ANEXAR_HEIGHT = 525;

    /** Atributo constante PAGAR_HEIGHT de ConstantesWEB. */
    public static final int PAGAR_HEIGHT = 525;

    /** Atributo constante REGISTRAR_PERSONAL_HEIGHT de ConstantesWEB. */
    public static final int REGISTRAR_PERSONAL_HEIGHT = 715;

    /** Atributo constante REGISTRAR_NORMAL_HEIGHT de ConstantesWEB. */
    public static final int REGISTRAR_NORMAL_HEIGHT = 470;

    /** Atributo constante INFORMACION_HEIGHT de ConstantesWEB. */
    public static final int INFORMACION_HEIGHT = 585;

   

    
    /** Atributo constante MINIMO_INFORMACION de ConstantesWEB. */
    public static final int MINIMO_INFORMACION = 100;

    

   

    /** Atributo constante SERVICIO_TRAMITE de ConstantesWEB. */
    public static final String SERVICIO_TRAMITE = "servicioTramite";

    

    /** Atributo constante VERSION_INCORRECTO de ConstantesWEB. */
    public static final String VERSION_INCORRECTO = "version.incorrecto";

   

    /** Atributo constante REGISTRO de ConstantesWEB. */
    public static final String REGISTRO = "registro";

    /** Atributo constante ESTILO de ConstantesWEB. */
    public static final String ESTILO = "estilo";

    /** Atributo constante FORMULARIOREUSABLE de ConstantesWEB. */
    public static final String FORMULARIOREUSABLE = "formularioReusable";

    /** Atributo constante GESTOR de ConstantesWEB. */
    public static final String GESTOR = "gestor";

    /** Atributo constante FORMULARIO de ConstantesWEB. */
    public static final String FORMULARIO = "formulario";

    /** Atributo constante IDTEXTO de ConstantesWEB. */
    public static final String IDTEXTO = "idTexto";

    /** Atributo constante TRADUCCION de ConstantesWEB. */
    public static final String TRADUCCION = "traduccion";

    /** Atributo constante PARAMETROS de ConstantesWEB. */
    public static final String PARAMETROS = "parametros";

    /** Atributo constante ID_CIO de ConstantesWEB. */
    public static final String ID_CIO = "idCio";

    /** Atributo constante ID_DOMINIO de ConstantesWEB. */
    public static final String ID_DOMINIO = "idDominio";

    /** Atributo constante TIPOCONSULTA de ConstantesWEB. */
    public static final String TIPOCONSULTA = "TIPOCONSULTA";

    /** Atributo constante CONSULTA_SQL de ConstantesWEB. */
    public static final String CONSULTA_SQL = "CONSULTASQL";

    /** Atributo constante CONSULTA_ORGANISMO de ConstantesWEB. */
    public static final String CONSULTA_ORGANISMO = "CONSULTAORGANISMO";

    /** Atributo constante VALOR de ConstantesWEB. */
    public static final String VALOR = "valor";

    /** Atributo constante IDIOMASOCUPADOS de ConstantesWEB. */
    public static final String IDIOMASOCUPADOS = "idiomasOcupados";

    /** Atributo constante IDIOMA de ConstantesWEB. */
    public static final String IDIOMA = "idioma";

    /** Atributo constante HTML de ConstantesWEB. */
    public static final String HTML = "html";

   

    /** Atributo constante OBJETO de ConstantesWEB. */
    public static final String OBJETO = "objeto";

    /** Atributo constante TIPOOBJETO de ConstantesWEB. */
    public static final String TIPOOBJETO = "tipoObjeto";

    /** Atributo constante TIPOLOCAL de ConstantesWEB. */
    public static final String TIPOLOCAL = "tipoLocal";

    /** Atributo constante TIPOANYADIR de ConstantesWEB. */
    public static final String TIPOANYADIR = "tipoAnyadir";

    /** Atributo constante ROL_ADMIN_GEN de ConstantesWEB. */
    public static final String ROL_ADMIN_GEN = "AG";

    /** Atributo constante ROL_DESARROLADOR de ConstantesWEB. */
    public static final String ROL_DESARROLADOR = "DE";

    /** Atributo constante ROL_ADMIN_ORG de ConstantesWEB. */
    public static final String ROL_ADMIN_ORG = "AC";

    /** Atributo constante ENTORNO de ConstantesWEB. */
    public static final String ENTORNO = "entorno";

    

    /** Atributo constante VALORES de ConstantesWEB. */
    public static final String VALORES = "valores";

    /** Atributo constante MOSTRARVALOR de ConstantesWEB. */
    public static final String MOSTRARVALOR = "mostrarValor";

   

    

    

    

    

   
    /** Atributo constante DOM_ORGANISMO de ConstantesWEB. */
    public static final String DOM_ORGANISMO = "DO";

    /** Atributo constante DOM_TRAMITE de ConstantesWEB. */
    public static final String DOM_TRAMITE = "DT";

    /** Atributo constante ELEMENTO_REGISTRO de ConstantesWEB. */
    public static final String ELEMENTO_REGISTRO = "R";

    /** Atributo constante ELEMENTO_GRUPO_ACCESO de ConstantesWEB. */
    public static final String ELEMENTO_GRUPO_ACCESO = "GA";

    /** Atributo constante ELEMENTO_ESTILO de ConstantesWEB. */
    public static final String ELEMENTO_ESTILO = "E";

    /** Atributo constante ELEMENTO_REUSABLE de ConstantesWEB. */
    public static final String ELEMENTO_REUSABLE = "F";

    /** Atributo constante ELEMENTO_GESTOR de ConstantesWEB. */
    public static final String ELEMENTO_GESTOR = "G";

    /** Atributo constante MODIFICADO de ConstantesWEB. */
    public static final String MODIFICADO = "M";

    /** Atributo constante NO_MODIFICADO de ConstantesWEB. */
    public static final String NO_MODIFICADO = "N";

    /** Atributo constante NO_EXISTE de ConstantesWEB. */
    public static final String NO_EXISTE = "E";

    /** Atributo constante LOCAL de ConstantesWEB. */
    public static final String LOCAL = "local";

    /** Atributo constante NORMAL de ConstantesWEB. */
    public static final String NORMAL = "N";

    /** Atributo constante PERSONALIZADO de ConstantesWEB. */
    public static final String PERSONALIZADO = "P";

    /** Atributo constante RELLENAR_INCORRECTO de ConstantesWEB. */
    public static final String RELLENAR_INCORRECTO = "rellenarFormularios.incorrecto";

    /** Atributo constante RELLENAR_FORMULARIOS de ConstantesWEB. */
    public static final String RELLENAR_FORMULARIOS = "RF";

    /** Atributo constante ANEXAR_DOCUMENTOS de ConstantesWEB. */
    public static final String ANEXAR_DOCUMENTOS = "AD";

    /** Atributo constante ANEXAR_INCORRECTO de ConstantesWEB. */
    public static final String ANEXAR_INCORRECTO = "anexarDocumentos.incorrecto";

    /** Atributo constante PAGAR_TASAS de ConstantesWEB. */
    public static final String PAGAR_TASAS = "PT";

    /** Atributo constante CAPTURA_DATOS de ConstantesWEB. */
    public static final String CAPTURA_DATOS = "CD";

    /** Atributo constante REGISTRAR_TRAMITE de ConstantesWEB. */
    public static final String REGISTRAR_TRAMITE = "RT";

    /** Atributo constante INFORMACION de ConstantesWEB. */
    public static final String INFORMACION = "IN";

    /** Atributo constante RAMAPROPIEDADES de ConstantesWEB. */
    public static final String RAMAPROPIEDADES = "RP";

    /** Atributo constante RAMACONTROL de ConstantesWEB. */
    public static final String RAMACONTROL = "RC";

    /** Atributo constante RAMADOMINIOS de ConstantesWEB. */
    public static final String RAMADOMINIOS = "RD";

    /** Atributo constante DATASOURCE de ConstantesWEB. */
    public static final String DATASOURCE = "datasource";

    /** Atributo constante QUERY de ConstantesWEB. */
    public static final String QUERY = "query";

    /** Atributo constante ID_VENTANA_DISENYO_FORM de ConstantesWEB. */
    public static final String ID_VENTANA_DISENYO_FORM = "wDisenyoFormularioVersion";

    /** Atributo constante ID_VENTANA_PROPIEDADES_FORM de ConstantesWEB. */
    public static final String ID_VENTANA_PROPIEDADES_FORM = "wPropiedadesFormularioVersion";

    /** Atributo constante MODO_GENERACION_FORM de ConstantesWEB. */
    public static final String MODO_GENERACION_FORM = "modoGeneracion";

    /** Atributo constante MODO_GENERACION_XDP de ConstantesWEB. */
    public static final String MODO_GENERACION_XDP = "XDP";

    /** Atributo constante MODO_GENERACION_HTML de ConstantesWEB. */
    public static final String MODO_GENERACION_HTML = "HTML";

    /** Atributo constante FICHERO_XDP de ConstantesWEB. */
    public static final String FICHERO_XDP = "ficheroXDP";

    /** Atributo constante AUTENTICACIONANONIMA de ConstantesWEB. */
    public static final String AUTENTICACIONANONIMA = "A";

    /** Atributo constante AUTENTICACIONCERTIFICADA de ConstantesWEB. */
    public static final String AUTENTICACIONCERTIFICADA = "C";

    /** Atributo constante AUTENTICACIONAMBAS de ConstantesWEB. */
    public static final String AUTENTICACIONAMBAS = "CE";

    /** Atributo constante EXCLUIDOS de ConstantesWEB. */
    public static final String EXCLUIDOS = "E";

    /** Atributo constante ACEPTADOS de ConstantesWEB. */
    public static final String ACEPTADOS = "A";

    /** Atributo constante GRUPOS de ConstantesWEB. */
    public static final String GRUPOS = "grupos";

    /** Atributo constante EDITOR_ELEMENTOS_IMAGEN_FORMULARIO de ConstantesWEB. */
    public static final String EDITOR_ELEMENTOS_IMAGEN_FORMULARIO = "elementosImagenFormulario";

    /** Atributo constante ELEMENTO_FORMULARIO de ConstantesWEB. */
    public static final String ELEMENTO_FORMULARIO = "elementoFormulario";

    /** Atributo constante PAGINA de ConstantesWEB. */
    public static final String PAGINA = "pagina";

    /** Atributo constante DOMINIOS de ConstantesWEB. */
    public static final String DOMINIOS = "dominios";

    /** Atributo constante DOMINIO_ORGANISMO de ConstantesWEB. */
    public static final String DOMINIO_ORGANISMO = "O";

    /** Atributo constante DOMINIO_TRAMITE de ConstantesWEB. */
    public static final String DOMINIO_TRAMITE = "T";

    /** Atributo constante DOMINIO_GENERAL de ConstantesWEB. */
    public static final String DOMINIO_GENERAL = "G";

  
    /** Atributo constante TIPOFORMULARIO de ConstantesWEB. */
    public static final String TIPOFORMULARIO = "tipoFormulario";

    /** Atributo constante IDIOMA_PREVISUALIZACION_FORM de ConstantesWEB. */
    public static final String IDIOMA_PREVISUALIZACION_FORM = "idiomaPrevisualizacionForm";

    /** Atributo constante PASOS de ConstantesWEB. */
    public static final String PASOS = "pasosTramitacion";

    /** Atributo constante NUMEROPASOS de ConstantesWEB. */
    public static final String NUMEROPASOS = "numeroPasos";

    /** Atributo constante PASO de ConstantesWEB. */
    public static final String PASO = "paso";

    /** Atributo constante PASO_PERSONALIZADO_INCORRECTO de ConstantesWEB. */
    public static final String PASO_PERSONALIZADO_INCORRECTO = "pasoPersonalizado.incorrecto";

    /** Atributo constante OBLIGATORIO de ConstantesWEB. */
    public static final String OBLIGATORIO = "S";

    /** Atributo constante OPCIONAL de ConstantesWEB. */
    public static final String OPCIONAL = "N";

    /** Atributo constante DEPENDIENTE de ConstantesWEB. */
    public static final String DEPENDIENTE = "D";

    /** Atributo constante INTERNO de ConstantesWEB. */
    public static final String INTERNO = "I";

    /** Atributo constante EXTERNO de ConstantesWEB. */
    public static final String EXTERNO = "E";

    /** Atributo constante REUSABLE de ConstantesWEB. */
    public static final String REUSABLE = "R";

    /** Atributo constante MENSAJESELIMINARORGANISMO de ConstantesWEB. */
    public static final String MENSAJESELIMINARORGANISMO = "mensajes";

    /** Atributo constante MODOORGANISMOADMIN de ConstantesWEB. */
    public static final String MODOORGANISMOADMIN = "organismoadmin";

    /** Atributo constante MODOORGANISMODESA de ConstantesWEB. */
    public static final String MODOORGANISMODESA = "organismodesa";

    /** Atributo constante MODOORGANISMOTRAM de ConstantesWEB. */
    public static final String MODOORGANISMOTRAM = "organismotram";

    /** Atributo constante MODOORGANISMOMENSAJE de ConstantesWEB. */
    public static final String MODOORGANISMOMENSAJE = "organismomens";

    /** Atributo constante MODOORGANISMOPERMISO de ConstantesWEB. */
    public static final String MODOORGANISMOPERMISO = "organismoperm";

    /** Atributo constante PAGE_SIZE de ConstantesWEB. */
    public static final int PAGE_SIZE = 10;

    /** Atributo constante ADMINISTRADOR_CONSELLERIA de ConstantesWEB. */
    public static final String ADMINISTRADOR_CONSELLERIA = "AC";

    /** Atributo constante DESARROLLADOR_CONSELLERIA de ConstantesWEB. */
    public static final String DESARROLLADOR_CONSELLERIA = "DC";

    /** Atributo constante DESARROLLADOR_TRAMITE de ConstantesWEB. */
    public static final String DESARROLLADOR_TRAMITE = "DT";

    /** Atributo constante IMAGEN_SCRIPT de ConstantesWEB. */
    public static final String IMAGEN_SCRIPT = "images/script.png";

    /** Atributo constante IMAGEN_NO_SCRIPT de ConstantesWEB. */
    public static final String IMAGEN_NO_SCRIPT = "images/script-empty.png";

    /** Atributo constante SCRIPT de ConstantesWEB. */
    public static final String SCRIPT = "script";

    /** Atributo constante CAPTURAR_INCORRECTO de ConstantesWEB. */
    public static final String CAPTURAR_INCORRECTO = "capturarDatos.incorrecto";

    /** Atributo constante FORMULARIO_CAPTURA de ConstantesWEB. */
    public static final String FORMULARIO_CAPTURA = "C";

    /** Atributo constante FORMULARIO_RELLENAR de ConstantesWEB. */
    public static final String FORMULARIO_RELLENAR = "T";

    /** Atributo constante ORDEN de ConstantesWEB. */
    public static final String ORDEN = "orden";

    /** Atributo constante DC de ConstantesWEB. */
    public static final String DC = "DC";

    /** Atributo constante DT de ConstantesWEB. */
    public static final String DT = "DT";

    /** Atributo constante AC de ConstantesWEB. */
    public static final String AC = "AC";

       /** Atributo constante ZUL_VACIO de ConstantesWEB. */
    public static final String ZUL_VACIO = "/ventanaVaciaParaInclude.zul";

    
    /** Atributo constante ZUL_INFO_ELIMINAR de ConstantesWEB. */
    public static final String ZUL_INFO_ELIMINAR = "/gestor/windows/ges-infoEliminarTramite-win.zul";

   

    /** Atributo constante ANEXO_INCORRECTO de ConstantesWEB. */
    public static final String ANEXO_INCORRECTO = "anexo.incorrecto";

    
    /** Atributo constante ACCION_DESBLOQUEAR de ConstantesWEB. */
    public static final String ACCION_DESBLOQUEAR = "onDesbloquear";

    /** Atributo constante ACCION_DUPLICAR_VERSION de ConstantesWEB. */
    public static final String ACCION_DUPLICAR_VERSION = "onDuplicarVersion";

    /** Atributo constante ACCION_DUPLICAR_TRAMITE de ConstantesWEB. */
    public static final String ACCION_DUPLICAR_TRAMITE = "onDuplicarTramite";

    /** Atributo constante ACCION_GUARDAR_TRAMITE de ConstantesWEB. */
    public static final String ACCION_GUARDAR_TRAMITE = "onGuardarTramite";

    /** Atributo constante TEXTBOX_ID de ConstantesWEB. */
    public static final String TEXTBOX_ID = "id";

    /** Atributo constante TEXTBOX_TIPO de ConstantesWEB. */
    public static final String TEXTBOX_TIPO = "tipo";

    /** Atributo constante TEXTBOX_DESCRIPCION de ConstantesWEB. */
    public static final String TEXTBOX_DESCRIPCION = "descripcion";

    /** Atributo constante INCLUDE_TRAM de ConstantesWEB. */
    public static final String INCLUDE_TRAM = "tram";

    /** Atributo constante LIST_VERSIONES de ConstantesWEB. */
    public static final String LIST_VERSIONES = "versiones";

    /** Atributo constante LISTBOX_VERSIONES de ConstantesWEB. */
    public static final String LISTBOX_VERSIONES = "listVersiones";

    /** Atributo constante LIST_DOMINIOS de ConstantesWEB. */
    public static final String LIST_DOMINIOS = "dominios";

    /** Atributo constante LISTBOX_DOMINIOS de ConstantesWEB. */
    public static final String LISTBOX_DOMINIOS = "listDominios";

    /** Atributo constante SERVICIO_DOMINIO de ConstantesWEB. */
    public static final String SERVICIO_DOMINIO = "servicioDominio";

    /** Atributo constante EVENTO_VERSION_GUARDAR_PROPIEDADES de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_PROPIEDADES = "onGuardarPropiedades";

    /** Atributo constante EVENTO_VERSION_GUARDAR_CONTROL de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_CONTROL = "onGuardarControlAccesos";

    /** Atributo constante EVENTO_VERSION_CAMBIAR_PASOS de ConstantesWEB. */
    public static final String EVENTO_VERSION_CAMBIAR_PASOS = "onCambiarPasos";

    /** Atributo constante EVENTO_VERSION_GUARDAR_RELLENAR de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_RELLENAR = "onGuardarRellenarFormularios";

    /** Atributo constante EVENTO_VERSION_GUARDAR_CAPTURAR de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_CAPTURAR = "onGuardarCapturarDatos";

    /** Atributo constante EVENTO_VERSION_GUARDAR_FORMULARIO de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_FORMULARIO = "onGuardarFormulario";

    /** Atributo constante EVENTO_VERSION_GUARDAR_ANEXAR de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_ANEXAR = "onGuardarAnexarDocumentos";

    /** Atributo constante EVENTO_VERSION_GUARDAR_ANEXO de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_ANEXO = "onGuardarAnexo";

    /** Atributo constante EVENTO_VERSION_GUARDAR_PAGAR de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_PAGAR = "onGuardarPagarTasas";

    /** Atributo constante EVENTO_VERSION_GUARDAR_PAGO de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_PAGO = "onGuardarPago";

    /** Atributo constante EVENTO_VERSION_GUARDAR_REGISTRAR de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_REGISTRAR = "onGuardarRegistrarTramite";

    /** Atributo constante EVENTO_VERSION_GUARDAR_FICHERO de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_FICHERO = "onGuardarFichero";

    /** Atributo constante EVENTO_VERSION_GUARDAR_INFORMACION de ConstantesWEB. */
    public static final String EVENTO_VERSION_GUARDAR_INFORMACION = "onGuardarInformacion";

   
    /** Atributo constante EVENTO_GUARDAR_MULTIDIOMA de ConstantesWEB. */
    public static final String EVENTO_GUARDAR_MULTIDIOMA = "onGuardarMultidioma";

    /** Atributo constante ACCION_PERSONALIZADA de ConstantesWEB. */
    public static final String ACCION_PERSONALIZADA = "accionPersonalizada";

    /** Atributo constante MENSAJE_SCRIPT de ConstantesWEB. */
    public static final String MENSAJE_SCRIPT = "mensajeScript";

    /** Atributo constante LISTA_MENSAJE_SCRIPT de ConstantesWEB. */
    public static final String LISTA_MENSAJE_SCRIPT = "ListaMensajeScript";

    /** Atributo constante LISTA_ACCIONES_PERSONALIZADAS de ConstantesWEB. */
    public static final String LISTA_ACCIONES_PERSONALIZADAS = "accionesPersonalizadas";

    /** Atributo constante LISTA_VALORES_CAMPO_FORM de ConstantesWEB. */
    public static final String LISTA_VALORES_CAMPO_FORM = "listaValoresCampoForm";

    /** Atributo constante VALOR_CAMPO_FORM de ConstantesWEB. */
    public static final String VALOR_CAMPO_FORM = "valorCampoForm";

    /** Atributo constante ORGANISMO de ConstantesWEB. */
    public static final String ORGANISMO = "organismo";

    /** Atributo constante CONFIGURACION de ConstantesWEB. */
    public static final String CONFIGURACION = "configuracion";

    /** Atributo constante MENSAJE de ConstantesWEB. */
    public static final String MENSAJE = "mensaje";

    /** Atributo constante GRUPO de ConstantesWEB. */
    public static final String GRUPO = "grupo";

    /** Atributo constante AUTORIDAD de ConstantesWEB. */
    public static final String AUTORIDAD = "autoridad";

    /** Atributo constante CERTIFICADO de ConstantesWEB. */
    public static final String CERTIFICADO = "certificado";

    /** Atributo constante ZUL_PASOS de ConstantesWEB. */
    public static final String ZUL_PASOS = "/priWindow/contenedorPrincipal/verTramWindow/detalle/wPasosVersion";

    

    /** Atributo constante PAGOS de ConstantesWEB. */
    public static final String PAGOS = "pagos";

    
    /** Atributo constante PAGAR_INCORRECTO de ConstantesWEB. */
    public static final String PAGAR_INCORRECTO = "pagarTasas.incorrecto";

    /** Atributo constante PAGO_INCORRECTO de ConstantesWEB. */
    public static final String PAGO_INCORRECTO = "pago.incorrecto";

    /** Atributo constante TELEMATICO de ConstantesWEB. */
    public static final String TELEMATICO = "T";

    /** Atributo constante SARA de ConstantesWEB. */
    public static final String SARA = "S";

    /** Atributo constante NIVELLOPD_ALTO de ConstantesWEB. */
    public static final String NIVELLOPD_ALTO = "A";

    /** Atributo constante NIVELLOPD_MEDIO de ConstantesWEB. */
    public static final String NIVELLOPD_MEDIO = "M";

    /** Atributo constante NIVELLOPD_BAJO de ConstantesWEB. */
    public static final String NIVELLOPD_BAJO = "B";

    /** Atributo constante ENTIDAD_MENSAJE_PERMISOS de ConstantesWEB. */
    public static final String ENTIDAD_MENSAJE_PERMISOS = "entidad.mensaje.permisos";

    /** Atributo constante REGISTRAR_INCORRECTO de ConstantesWEB. */
    public static final String REGISTRAR_INCORRECTO = "registros.incorrecto";

    /** Atributo constante FICHERO de ConstantesWEB. */
    public static final String FICHERO = "fichero";

    /** Atributo constante FICHEROS de ConstantesWEB. */
    public static final String FICHEROS = "ficheros";

    /** Atributo constante TIPO_ENTRADA_APD de ConstantesWEB. */
    public static final String TIPO_ENTRADA_APD = "APD";

    /** Atributo constante TIPO_ENTRADA_COR de ConstantesWEB. */
    public static final String TIPO_ENTRADA_COR = "COR";

    /** Atributo constante TIPO_ENTRADA_NUE de ConstantesWEB. */
    public static final String TIPO_ENTRADA_NUE = "NUE";

    /** Atributo constante EXP_REG_ID de ConstantesWEB. */
    public static final String EXP_REG_ID = "[-_A-Z0-9]+";

    /** Atributo constante TIPO_SCRIPT de ConstantesWEB. */
    public static final String TIPO_SCRIPT = "TIPOSCRIPT";

    /** Atributo constante TIPO_SCRIPT_PERSONALIZADO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_PERSONALIZADO = "PERSONALIZADO";

    /** Atributo constante TIPO_SCRIPT_VALIDACION_ANEXO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_VALIDACION_ANEXO = "VALIDACIONANEXO";

    /** Atributo constante TIPO_SCRIPT_LISTA_VALIDACION_ANEXO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_LISTA_VALIDACION_ANEXO = "LISTAVALIDACIONANEXO";

    /** Atributo constante TIPO_SCRIPT_LISTA_DINAMICA_PAGO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_LISTA_DINAMICA_PAGO = "LISTADINAMICAPAGO";

    /** Atributo constante TIPO_SCRIPT_DEPENDIENTE de ConstantesWEB. */
    public static final String TIPO_SCRIPT_DEPENDIENTE = "DEPENDIENTE";

    /** Atributo constante TIPO_SCRIPT_FIRMA de ConstantesWEB. */
    public static final String TIPO_SCRIPT_FIRMA = "FIRMA";

    /** Atributo constante TIPO_SCRIPT_DATOS_PAGO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_DATOS_PAGO = "DATOSPAGO";

    /** Atributo constante TIPO_SCRIPT_PARAMETROS_REGISTRO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_PARAMETROS_REGISTRO = "PARAMETROSREGISTRO";

    /** Atributo constante TIPO_SCRIPT_VALIDACION_REGISTRO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_VALIDACION_REGISTRO = "VALIDACIONREGISTRO";

    /** Atributo constante TIPO_SCRIPT_PRESENTADOR_REGISTRO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_PRESENTADOR_REGISTRO = "PRESENTADORREGISTRO";

    /** Atributo constante TIPO_SCRIPT_REPRESENTADO_REGISTRO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_REPRESENTADO_REGISTRO = "REPRESENTADOREGISTRO";

    /**
     * Atributo constante TIPO_SCRIPT_FORMULARIO_DATOS_INICIALES de
     * ConstantesWEB.
     */
    public static final String TIPO_SCRIPT_FORMULARIO_DATOS_INICIALES = "FORMULARIODATOSINICIALES";

    /** Atributo constante TIPO_SCRIPT_FORMULARIO_PARAMETROS de ConstantesWEB. */
    public static final String TIPO_SCRIPT_FORMULARIO_PARAMETROS = "FORMULARIOPARAMETROS";

    /** Atributo constante TIPO_SCRIPT_FORMULARIO_POST de ConstantesWEB. */
    public static final String TIPO_SCRIPT_FORMULARIO_POST = "FORMULARIOPOST";

    /** Atributo constante TIPO_SCRIPT_NAVEGACION de ConstantesWEB. */
    public static final String TIPO_SCRIPT_NAVEGACION = "NAVEGACION";

    /** Atributo constante TIPO_SCRIPT_DATOS_PLANTILLA de ConstantesWEB. */
    public static final String TIPO_SCRIPT_DATOS_PLANTILLA = "DATOSPLANTILLA";

    /** Atributo constante TIPO_SCRIPT_AUTORELLENABLE de ConstantesWEB. */
    public static final String TIPO_SCRIPT_AUTORELLENABLE = "AUTORELLENABLE";

    /** Atributo constante TIPO_SCRIPT_ESTADO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_ESTADO = "ESTADO";

    /** Atributo constante TIPO_SCRIPT_VALIDACION_CAMPO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_VALIDACION_CAMPO = "VALIDACIONCAMPO";

    /** Atributo constante TIPO_SCRIPT_VALIDACION_PAGINA de ConstantesWEB. */
    public static final String TIPO_SCRIPT_VALIDACION_PAGINA = "VALIDACIONPAGINA";

    /** Atributo constante TIPO_SCRIPT_VALORES_POSIBLES de ConstantesWEB. */
    public static final String TIPO_SCRIPT_VALORES_POSIBLES = "VALORESPOSIBLES";

    /**
     * Atributo constante TIPO_SCRIPT_PARAMETROS_REPRESENTACION_REGISTRO de
     * ConstantesWEB.
     */
    public static final String TIPO_SCRIPT_PARAMETROS_REPRESENTACION_REGISTRO = "PARAMETROSREPRESENTACIONREGISTRO";

    /** Atributo constante TIPO_SCRIPT_ACCION_PASO de ConstantesWEB. */
    public static final String TIPO_SCRIPT_ACCION_PASO = "ACCIONPASO";

    /** Atributo constante SCRIPT_ZUL de ConstantesWEB. */
    public static final String SCRIPT_ZUL = "/gestor/windows/ges-versiones-script-win.zul";

    /** Atributo constante TIPO_SCRIPT_INICIO_TRAMITE de ConstantesWEB. */
    public static final String TIPO_SCRIPT_INICIO_TRAMITE = "INICIOTRAMITE";

    /** Atributo constante SCRIPT_MENSAJES_ZUL de ConstantesWEB. */
    public static final String SCRIPT_MENSAJES_ZUL = "/gestor/windows/ges-versiones-mensajesScript-win.zul";

    /** Atributo constante SCRIPT_TITULO de ConstantesWEB. */
    public static final String SCRIPT_TITULO = "titulo";

    /** Atributo constante SCRIPT_DATO de ConstantesWEB. */
    public static final String SCRIPT_DATO = "Dato";

    /** Atributo constante SCRIPT_SIRVE de ConstantesWEB. */
    public static final String SCRIPT_SIRVE = "Sirve";

    /** Atributo constante SCRIPT_EJECUTA de ConstantesWEB. */
    public static final String SCRIPT_EJECUTA = "Ejecuta";

    /** Atributo constante SCRIPT_DEVUELVE de ConstantesWEB. */
    public static final String SCRIPT_DEVUELVE = "Devuelve";

    /** Atributo constante SCRIPT_SCRIPT de ConstantesWEB. */
    public static final String SCRIPT_SCRIPT = "Script";

    /** Atributo constante LISTA_VALORES_TIPO de ConstantesWEB. */
    public static final String LISTA_VALORES_TIPO = "listaValoresTipo";

    /** Atributo constante TIPO_VALORES_INDEXADO de ConstantesWEB. */
    public static final String TIPO_VALORES_INDEXADO = "tipoValoresIndexado";

    /** Atributo constante TIPO_VALORES_SELEC_UNICA de ConstantesWEB. */
    public static final String TIPO_VALORES_SELEC_UNICA = "tipoValoresSelecUnica";

    /** Atributo constante CONTENEDOR_PRINCIPAL de ConstantesWEB. */
    public static final String CONTENEDOR_PRINCIPAL = "contenedorPrincipal";

    /** Atributo constante LISTA_PARAMS_DOMINIO_FORM de ConstantesWEB. */
    public static final String LISTA_PARAMS_DOMINIO_FORM = "listaParametrosDominioForm";

    /** Atributo constante DOMINIO_CAMPO_CODIGO de ConstantesWEB. */
    public static final String DOMINIO_CAMPO_CODIGO = "codigoDominio";

    /** Atributo constante DOMINIO_CAMPO_TIPO de ConstantesWEB. */
    public static final String DOMINIO_CAMPO_TIPO = "tipo";

    /** Atributo constante PLANTILLA de ConstantesWEB. */
    public static final String PLANTILLA = "plantilla";

    /** Atributo constante URL de ConstantesWEB. */
    public static final String URL = "url";

    /** Atributo constante DATO de ConstantesWEB. */
    public static final String DATO = "DATOS";

    /** Atributo constante PLUGIN de ConstantesWEB. */
    public static final String PLUGIN = "PLUGIN";

    /** Atributo constante NUMEROINSTANCIAS de ConstantesWEB. */
    public static final String NUMEROINSTANCIAS = "NUMEROINSTANCIAS";

    /** Atributo constante INPUT_LIST_CONTAINER de ConstantesWEB. */
    public static final String INPUT_LIST_CONTAINER = "input-list-container";

    /**
     * Atributo constante GENERAR_DISENYO_VALIDACIONES_ERROR_FICHERO de
     * ConstantesWEB.
     */
    public static final String GENERAR_DISENYO_VALIDACIONES_ERROR_FICHERO = "generarDisenyo.validaciones.error.fichero";

    /**
     * Atributo constante COMPOSER_PROPIEDADES_VERSION_TRAMITE de ConstantesWEB.
     */
    public static final String COMPOSER_PROPIEDADES_VERSION_TRAMITE = "COMPOSER_PROPIEDADES_VERSION_TRAMITE";

    /** Atributo constante COMPOSER_ANEXAR_DOCUMENTOS de ConstantesWEB. */
    public static final String COMPOSER_ANEXAR_DOCUMENTOS = "COMPOSERANEXARDOCUMENTOS";

    /** Atributo constante COMPOSER_ANEXO_VERSION de ConstantesWEB. */
    public static final String COMPOSER_ANEXO_VERSION = "COMPOSERANEXOVERSION";

    /** Atributo constante COMPOSER_PAGAR_TASAS de ConstantesWEB. */
    public static final String COMPOSER_PAGAR_TASAS = "COMPOSERPAGARTASAS";

    /** Atributo constante COMPOSER_PAGAR_VERSION de ConstantesWEB. */
    public static final String COMPOSER_PAGAR_VERSION = "COMPOSERPAGARVERSION";

    /** Atributo constante COMPOSER_REGISTRAR_TRAMIE de ConstantesWEB. */
    public static final String COMPOSER_REGISTRAR_TRAMIE = "COMPOSERREGISTRARTRAMIE";

    /** Atributo constante COMPOSER_INFORMACION de ConstantesWEB. */
    public static final String COMPOSER_INFORMACION = "COMPOSERINFORMACION";

    /** Atributo constante COMPOSER_RELLENAR_FORMULARIOS de ConstantesWEB. */
    public static final String COMPOSER_RELLENAR_FORMULARIOS = "COMPOSERRELLENARFORMULARIOS";

    /** Atributo constante COMPOSER_FORMULARIO_TRAMITE de ConstantesWEB. */
    public static final String COMPOSER_FORMULARIO_TRAMITE = "COMPOSERFORMULARIOTRAMITE";

    /** Atributo constante MODIFICAR_FICHERO_ERROR de ConstantesWEB. */
    public static final String MODIFICAR_FICHERO_ERROR = "boton.modificar.fichero.error";

    /** Atributo constante USUARIO_LOGADO de ConstantesWEB. */
    public static final String USUARIO_LOGADO = "USUARIOLOGADO";

    /** Atributo constante PR de ConstantesWEB. */
    public static final String PR = "PR";

    /** Atributo constante EP de ConstantesWEB. */
    public static final String EP = "EP";

    /** Atributo constante CIO de ConstantesWEB. */
    public static final String CIO = "CIO";

    /** Atributo constante SEPARADORHTML de ConstantesWEB. */
    public static final String SEPARADORHTML = "<br><br>";

    /** Atributo constante EJEMPLOSCRIPT de ConstantesWEB. */
    public static final String EJEMPLOSCRIPT = "script.datos.ejemplo";

    /** Atributo constante EXTENSIONHTML de ConstantesWEB. */
    public static final String EXTENSIONHTML = ".html";


    /** Atributo constante WHITE_SPACE_NOWRAP de ConstantesWEB. */
    public static final String WHITE_SPACE_NOWRAP = "white-space: nowrap;";

    /** Atributo constante QUERYLARGA de ConstantesWEB. */
    public static final String QUERYLARGA = "dominio.query.largo";
    

    /** Atributo constante ERROR_OBLIGATORIO de ConstantesWEB. */
    public static final String ERROR_OBLIGATORIO = "error.obligatorio";

	    
    
}
