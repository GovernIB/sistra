--
-- PostgreSQL database dump
--



--
-- TOC entry 156 (class 1259 OID 188764)
-- Dependencies: 3
-- Name: str_datjus; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_datjus (
    djs_codigo bigint NOT NULL,
    djs_codetn bigint,
    djs_blqcam character varying(1) NOT NULL,
    djs_orden bigint,
    djs_campo character varying(500),
    djs_camps bytea,
    djs_visible bytea
);




--
-- TOC entry 2062 (class 0 OID 0)
-- Dependencies: 156
-- Name: TABLE str_datjus; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_datjus IS 'Datos para generar el justificante. A partir de estos datos se generará la parte especifica de datos particulares del trámite en el asiento del trámite.';


--
-- TOC entry 2063 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_codigo IS 'Código';


--
-- TOC entry 2064 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_codetn; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_codetn IS 'Código especificaciones';


--
-- TOC entry 2065 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_blqcam; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_blqcam IS 'Indica si es un separador de bloques (B) o es un campo (C)';


--
-- TOC entry 2066 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_orden; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_orden IS 'Orden';


--
-- TOC entry 2067 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_campo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_campo IS 'Para tipo campo indica referencia al campo del formulario que contiene el valor';


--
-- TOC entry 2068 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_camps; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_camps IS 'Para tipo campo indica permite establecer el valor mediante un script (tendrá prereferencia DJS_CAMPO)';


--
-- TOC entry 2069 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN str_datjus.djs_visible; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_datjus.djs_visible IS 'Permite configurar si el elemento será visible en función de datos del formulario';


--
-- TOC entry 157 (class 1259 OID 188772)
-- Dependencies: 3
-- Name: str_dmunic; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_dmunic (
    mun_codigo bigint NOT NULL,
    mun_provin bigint NOT NULL,
    mun_denofi character varying(50) NOT NULL
);




--
-- TOC entry 2070 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN str_dmunic.mun_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_dmunic.mun_codigo IS 'Codigo de municipio';


--
-- TOC entry 2071 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN str_dmunic.mun_provin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_dmunic.mun_provin IS 'Codigo de provincia';


--
-- TOC entry 2072 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN str_dmunic.mun_denofi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_dmunic.mun_denofi IS 'Denominacion Oficial';


--
-- TOC entry 158 (class 1259 OID 188778)
-- Dependencies: 1928 1929 1930 3
-- Name: str_docniv; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_docniv (
    dnv_codigo bigint NOT NULL,
    dnv_coddoc bigint NOT NULL,
    dnv_nivaut character varying(3) NOT NULL,
    dnv_versio bigint NOT NULL,
    dnv_obliga character varying(1) NOT NULL,
    dnv_oblscr bytea,
    dnv_firmar character varying(1) NOT NULL,
    dnv_firmte character varying(500),
    dnv_forgst character varying(20) DEFAULT 'forms'::character varying NOT NULL,
    dnv_forini bytea,
    dnv_forfor character varying(20),
    dnv_forver bigint,
    dnv_forcon bytea,
    dnv_forpos bytea,
    dnv_formod bytea,
    dnv_forpla bytea,
    dnv_pagdat bytea,
    dnv_pagtip character varying(1) DEFAULT 'A'::character varying NOT NULL,
    dnv_flutra bytea,
    dnv_fircty character varying(500),
    dnv_pagplg character varying(50) DEFAULT '.'::character varying NOT NULL,
    dnv_forgua character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2073 (class 0 OID 0)
-- Dependencies: 158
-- Name: TABLE str_docniv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_docniv IS 'Especificaciones documentos por nivel de autenticación';


--
-- TOC entry 2074 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_codigo IS 'Código interno';


--
-- TOC entry 2075 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_coddoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_coddoc IS 'Código interno documento';


--
-- TOC entry 2076 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_nivaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_nivaut IS 'Nivel autenticación: Certificado (C) / Ususario-pass (U) / Anónimo (A)';


--
-- TOC entry 2077 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_versio; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_versio IS 'Versión documento';


--
-- TOC entry 2078 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_obliga; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_obliga IS 'Especificación obligatoriedad: Obligatorio (S) / Opcional (N) / Dependiente según script (D).';


--
-- TOC entry 2079 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_oblscr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_oblscr IS 'Script para indicar la obligatoriedad en documentos dependientes';


--
-- TOC entry 2080 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_firmar; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_firmar IS 'Indica si el documento se debe firmar digitalmente individualmente. En el script se puede indicar quien debe firmar el documento, si no existe script o si devuelve vacío será el iniciador del trámite.No se puede indicar que debe firmarse y que el documento sea presencial.';


--
-- TOC entry 2081 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_firmte; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_firmte IS 'Script que indica quien debe firmar el documento';


--
-- TOC entry 2082 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forgst; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forgst IS 'Indica gestor de formularios a utilizar: FORMS,...';


--
-- TOC entry 2083 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forini; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forini IS 'Para Formulario: script para carga de datos iniciales';


--
-- TOC entry 2084 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forfor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forfor IS 'Para Formulario: indica enlace a Forms (Formulario)';


--
-- TOC entry 2085 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forver; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forver IS 'Para Formulario: indica enlace a Forms (Version)';


--
-- TOC entry 2086 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forcon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forcon IS 'Para Formulario: script para establecer configuración formulario (campos solo lectura,etc)';


--
-- TOC entry 2087 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forpos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forpos IS 'Para Formulario: validación datos formularios tras volver de Forms';


--
-- TOC entry 2088 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_formod; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_formod IS 'Para Formulario: script que permite modificar datos de otros formularios tras el salvado (no debe haber errores de validación)';


--
-- TOC entry 2089 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_forpla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_forpla IS 'Para Formulario: script para indicar plantilla de visualización (si no se especifica script se utiliza la por defecto)';


--
-- TOC entry 2090 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_pagdat; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_pagdat IS 'Para Pagos: script para calcular datos del pago';


--
-- TOC entry 2091 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_pagtip; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_pagtip IS 'Para Pagos: indica el tipo de pago que se permite: Telematico (T) / Presencial (P) / Ambos (A)';


--
-- TOC entry 2092 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_flutra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_flutra IS 'Indica usuario que debe aportar este documento (solo para autenticado y si esta habilitado el flujo de tramitación). Si es nulo o devuelve vacío debe aportarlo quién inicia el trámite.';


--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_fircty; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_fircty IS 'Indica el content type del documento a firmar. Si no se especifica se utilizará uno por defecto.';


--
-- TOC entry 2094 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN str_docniv.dnv_pagplg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docniv.dnv_pagplg IS 'Para Pagos: indica si se usa un plugin adicional de pagos. Si no usara el defecto.';


--
-- TOC entry 159 (class 1259 OID 188790)
-- Dependencies: 1931 1932 1933 3
-- Name: str_docum; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_docum (
    doc_codigo bigint NOT NULL,
    doc_codtrv bigint NOT NULL,
    doc_identi character varying(5) NOT NULL,
    doc_tipo character varying(1) NOT NULL,
    doc_orden bigint NOT NULL,
    doc_pad bigint,
    doc_modelo character varying(20) NOT NULL,
    doc_forprg character varying(1),
    doc_forjus character varying(1) DEFAULT 'N'::character varying,
    doc_aneext character varying(50),
    doc_anetam bigint,
    doc_anepla character varying(500),
    doc_anedpl character varying(1),
    doc_anetel character varying(1) NOT NULL,
    doc_anecom character varying(1) NOT NULL,
    doc_anefot character varying(1) NOT NULL,
    doc_anegco character varying(1) NOT NULL,
    doc_anegma bigint,
    doc_anepdf character varying(1) DEFAULT 'N'::character varying,
    doc_foraju character varying(1) DEFAULT 'N'::character varying
);




--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 159
-- Name: TABLE str_docum; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_docum IS 'Documentos del trámite';


--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_codigo IS 'Código interno documento';


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_identi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_identi IS 'Identificador documento dentro del trámite';


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_tipo IS 'Tipo de documento: Formulario (F) / Anexo (A) / Pago (P)';


--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_orden; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_orden IS 'Orden de presentación';


--
-- TOC entry 2100 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_pad; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_pad IS 'Indica número de documento equivalente en la PAD';


--
-- TOC entry 2101 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_modelo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_modelo IS 'Modelo del documento';


--
-- TOC entry 2102 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_forprg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_forprg IS 'Para Formulario: en caso de preregistro indica si se debe imprimir este formulario para firmar y entregar';


--
-- TOC entry 2103 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_forjus; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_forjus IS 'Para Formulario: en caso de preregistro indica si se debe utilizar el formulario como justificante';


--
-- TOC entry 2104 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_aneext; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_aneext IS 'Para Anexos: lista de extensiones posibles separadas por ;. Lista vacía implica que no hay restricciones.';


--
-- TOC entry 2105 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anetam; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anetam IS 'Para Anexos: tamaño en Kb permitido';


--
-- TOC entry 2106 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anepla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anepla IS 'Para Anexos: URL plantilla';


--
-- TOC entry 2107 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anedpl; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anedpl IS 'Para Anexos: Indica si se indica al usuario que debe descargar la plantilla';


--
-- TOC entry 2108 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anetel; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anetel IS 'Para Anexos: Indica si se debe anexar telemáticamente';


--
-- TOC entry 2109 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anecom; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anecom IS 'Para Anexos: indica si se debe indicar al usuario que debe compulsar el documento';


--
-- TOC entry 2110 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anefot; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anefot IS 'Para Anexos: Indica si para un documento se deberá presentar una fotocopia';


--
-- TOC entry 2111 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anegco; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anegco IS 'Para Anexos: Indica si el documento es Genérico. Si es genérico se permitirá indicar al usuario indicar una descripción y anexar tantos documentos como se indique en el campo DOC_GCOMAX';


--
-- TOC entry 2112 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anegma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anegma IS 'Para Anexos: Para documento Genérico indica el número máximo de instancias de documentos genéricos que se pueden anexar. Si se marca el documento como obligatorio deberán anexarse este número de instancias.';


--
-- TOC entry 2113 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_anepdf; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_anepdf IS 'Para Anexos: indica si hay que convertir el anexo a PDF (extensiones doc y odt)';


--
-- TOC entry 2114 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN str_docum.doc_foraju; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_docum.doc_foraju IS 'Para Formulario: se indica si el formulario debe anexarse al justificante';


--
-- TOC entry 160 (class 1259 OID 188803)
-- Dependencies: 1934 1935 3
-- Name: str_domin; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_domin (
    dom_codigo bigint NOT NULL,
    dom_identi character varying(20) NOT NULL,
    dom_tipo character varying(1) NOT NULL,
    dom_url character varying(500),
    dom_sql character varying(4000),
    dom_jndi character varying(100),
    dom_ejbrem character varying(1),
    dom_ejbstd character varying(1) DEFAULT 'N'::character varying,
    dom_usr character varying(500),
    dom_pwd character varying(500),
    dom_cache character varying(1) DEFAULT 'N'::character varying NOT NULL,
    dom_codorg bigint NOT NULL,
    dom_desc character varying(100) NOT NULL,
    dom_wsver character varying(10),
    dom_wssoa character varying(100)
);




--
-- TOC entry 2115 (class 0 OID 0)
-- Dependencies: 160
-- Name: TABLE str_domin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_domin IS 'Dominios';


--
-- TOC entry 2116 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_codigo IS 'Código';


--
-- TOC entry 2117 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_identi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_identi IS 'Identificador funcional del dominio';


--
-- TOC entry 2118 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_tipo IS 'Tipo de dominio: Sql (S) / Ejb (E) / Webservice (W)';


--
-- TOC entry 2119 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_url; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_url IS 'Indica según tipo de dominio:
- Ejb: JNDI ejb que resuelve dominio
- Sql: JNDI datasource origen de datos
- Webservice: Url webservice';


--
-- TOC entry 2120 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_sql; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_sql IS 'Para tipo dominio Sql indica sql a ejecutar';


--
-- TOC entry 2121 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_jndi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_jndi IS 'Para tipo dominio Ejb indica jndi name del ejb a invocar';


--
-- TOC entry 2122 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_ejbrem; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_ejbrem IS 'Para tipo dominio Ejb indica si el ejb es remoto: Remoto (R) / Local (L)';


--
-- TOC entry 2123 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_ejbstd; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_ejbstd IS 'Para tipo dominio Ejb/Ws indica si se debe realizar:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password
 - C: explícita a través plugin autenticación del organismo';


--
-- TOC entry 2124 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_usr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_usr IS 'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL USUARIO';


--
-- TOC entry 2125 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_pwd; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_pwd IS 'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL PASSWORD';


--
-- TOC entry 2126 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_cache; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_cache IS 'Indica si el dominio debe cachearse';


--
-- TOC entry 2127 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_codorg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_codorg IS 'Organo responsable del dominio';


--
-- TOC entry 2128 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_desc IS 'Descripcion dominio';


--
-- TOC entry 2129 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN str_domin.dom_wssoa; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_domin.dom_wssoa IS 'WS: Soap action';


--
-- TOC entry 161 (class 1259 OID 188815)
-- Dependencies: 3
-- Name: str_dpais; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_dpais (
    pai_codigo bigint NOT NULL,
    pai_codalf character(3) NOT NULL,
    pai_dencas character varying(50) NOT NULL,
    pai_dencat character varying(50) NOT NULL,
    pai_vigenc character(1) NOT NULL,
    pai_cod2af character(3)
);




--
-- TOC entry 162 (class 1259 OID 188820)
-- Dependencies: 3
-- Name: str_dprovi; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_dprovi (
    pro_codigo bigint NOT NULL,
    pro_codcau bigint,
    pro_dencas character varying(25),
    pro_dencat character varying(25)
);




--
-- TOC entry 163 (class 1259 OID 188825)
-- Dependencies: 1936 1937 1938 1939 1940 1941 1942 1943 3
-- Name: str_espniv; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_espniv (
    etn_codigo bigint NOT NULL,
    etn_activo character varying(1) NOT NULL,
    etn_valini bytea,
    etn_diaper bigint,
    etn_codprv bytea,
    etn_codloc bytea,
    etn_codpai bytea,
    etn_rtenif bytea,
    etn_rtenom bytea,
    etn_rdonif bytea,
    etn_rdonom bytea,
    etn_diapre bigint DEFAULT 0,
    etn_flutra character varying(1) DEFAULT 'N'::character varying NOT NULL,
    etn_urlfin bytea,
    etn_avisms bytea,
    etn_aviema bytea,
    etn_nottel character varying(1) DEFAULT 'N'::character varying NOT NULL,
    etn_chkenv bytea,
    etn_dsttra bytea,
    etn_persms character varying(1) DEFAULT 'N'::character varying NOT NULL,
    etn_jnocla character varying(1) DEFAULT 'N'::character varying NOT NULL,
    etn_jnonn character varying(1) DEFAULT 'N'::character varying NOT NULL,
    etn_aletra character varying(1) DEFAULT 'N'::character varying NOT NULL,
    etn_alesms character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2130 (class 0 OID 0)
-- Dependencies: 163
-- Name: TABLE str_espniv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_espniv IS 'Especificación parámetros trámite que se especifican por defecto y pueden ser sobreescritas según nivel autenticación.';


--
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_codigo IS 'Código';


--
-- TOC entry 2132 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_activo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_activo IS 'Indica si el trámite esta activo';


--
-- TOC entry 2133 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_valini; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_valini IS 'Validación al iniciar trámite. Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2134 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_diaper; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_diaper IS 'Días persistencia. Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2135 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_codprv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_codprv IS 'Script para indicar el código de la provincia. Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2136 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_codloc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_codloc IS 'Script para indicar el código de la localidad. Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2137 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_codpai; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_codpai IS 'Script para indicar el código del país. Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2138 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_rtenif; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_rtenif IS 'Script que indica el NIF representante (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2139 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_rtenom; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_rtenom IS 'Script que indica el Nombre representante (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2140 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_rdonif; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_rdonif IS 'Script que indica NIF representado (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2141 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_rdonom; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_rdonom IS 'Script que indica Nombre representado (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';


--
-- TOC entry 2142 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_diapre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_diapre IS 'Dias de prerregistro. Puede ser sobreescrita por nivel de autenticación. Junto a la fecha limite de entrega, indica hasta que fecha se pueden entregar los datos de un prerregistro o preenvio';


--
-- TOC entry 2143 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_flutra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_flutra IS 'Indica si el trámite permite flujo de tramitación: permite que el trámite se vaya remitiendo a diferentes usuarios para que sea completado (sólo con autenticación).';


--
-- TOC entry 2144 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_urlfin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_urlfin IS 'Establece url de finalización';


--
-- TOC entry 2145 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_avisms; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_avisms IS 'En caso de que esten habilitados los avisos para el expediente indicará el telefono para avisos SMS';


--
-- TOC entry 2146 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_aviema; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_aviema IS 'En caso de que esten habilitados los avisos para el expediente indicará el email para avisos Email';


--
-- TOC entry 2147 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_nottel; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_nottel IS 'Indica si el tramite permite notificacion telematica: N: No permite /  S Si permite, el ciudadano elige / O: Obligatoria notificación telemática / X: No especificada';


--
-- TOC entry 2148 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_chkenv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_chkenv IS 'Script para chequear antes de enviar';


--
-- TOC entry 2149 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_dsttra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_dsttra IS 'Script que permite cambiar dinámicamente la información del destinatario del trámite (oficina registral, organo destino y unidad administrativa)';


--
-- TOC entry 2150 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_jnocla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_jnocla IS 'Indica si se oculta la clave de tramitacion en el pdf de justificante estandard';


--
-- TOC entry 2151 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_jnonn; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_jnonn IS 'Indica si se oculta el nif y nombre en el pdf de justificante estandard';


--
-- TOC entry 2152 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_aletra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_aletra IS 'Indica si se generan alertas de tramitacion (previas envio tramite): N: No permite /  S Si permite, el ciudadano elige / O: Obligatoria notificación telemática / X: No especificada';


--
-- TOC entry 2153 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN str_espniv.etn_alesms; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_espniv.etn_alesms IS 'Indica si se permiten sms en los avisos de notificacion telematica (S/N)';


--
-- TOC entry 164 (class 1259 OID 188837)
-- Dependencies: 3
-- Name: str_gesfrm; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_gesfrm (
    gsf_ident character varying(15) NOT NULL,
    gsf_desc character varying(50) NOT NULL,
    gsf_url character varying(500) NOT NULL,
    gsf_urltra character varying(500) NOT NULL,
    gsf_urlred character varying(500) NOT NULL
);




--
-- TOC entry 2154 (class 0 OID 0)
-- Dependencies: 164
-- Name: TABLE str_gesfrm; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_gesfrm IS 'Gestores de formularios';


--
-- TOC entry 2155 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN str_gesfrm.gsf_ident; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_gesfrm.gsf_ident IS 'IDENTIFICADOR GESTOR FORMULARIOS';


--
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN str_gesfrm.gsf_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_gesfrm.gsf_desc IS 'DESCRIPCION';


--
-- TOC entry 2157 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN str_gesfrm.gsf_url; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_gesfrm.gsf_url IS 'URL BASE DEL GESTOR';


--
-- TOC entry 2158 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN str_gesfrm.gsf_urltra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_gesfrm.gsf_urltra IS 'URL TRAMITACION FORMULARIO (PUEDE CONTENER COMO VARIABLE LA URL BASE @forms.server@) ';


--
-- TOC entry 2159 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN str_gesfrm.gsf_urlred; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_gesfrm.gsf_urlred IS 'URL REDIRECCION FORMULARIO (PUEDE CONTENER COMO VARIABLE LA URL BASE @forms.server@) ';


--
-- TOC entry 184 (class 1259 OID 189122)
-- Dependencies: 3
-- Name: str_grptra; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_grptra (
    grt_codgrp character varying(50) NOT NULL,
    grt_codtra bigint NOT NULL
);




--
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 184
-- Name: TABLE str_grptra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_grptra IS 'Trámites a los que el grupo tiene acceso';


--
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN str_grptra.grt_codgrp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grptra.grt_codgrp IS 'CODIGO GRUPO';


--
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN str_grptra.grt_codtra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grptra.grt_codtra IS 'CODIGO TRAMITE';


--
-- TOC entry 183 (class 1259 OID 189112)
-- Dependencies: 3
-- Name: str_grpusu; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_grpusu (
    gru_codgrp character varying(50) NOT NULL,
    gru_codusu character varying(200) NOT NULL
);




--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 183
-- Name: TABLE str_grpusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_grpusu IS 'Asignación de usuarios a grupos';


--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN str_grpusu.gru_codgrp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grpusu.gru_codgrp IS 'CODIGO GRUPO';


--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN str_grpusu.gru_codusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grpusu.gru_codusu IS 'CODIGO USUARIO';


--
-- TOC entry 182 (class 1259 OID 189107)
-- Dependencies: 3
-- Name: str_grupos; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_grupos (
    grp_codigo character varying(50) NOT NULL,
    grp_nombre character varying(100) NOT NULL,
    grp_descp character varying(300)
);




--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 182
-- Name: TABLE str_grupos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_grupos IS 'Grupos de usuarios para establecer permisos de acceso a tramites';


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN str_grupos.grp_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grupos.grp_codigo IS 'Código grupo';


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN str_grupos.grp_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grupos.grp_nombre IS 'Nombre grupo';


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN str_grupos.grp_descp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_grupos.grp_descp IS 'Descripción grupo';


--
-- TOC entry 165 (class 1259 OID 188845)
-- Dependencies: 3
-- Name: str_idioma; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_idioma (
    idi_codigo character varying(2) NOT NULL,
    idi_orden bigint NOT NULL
);




--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 165
-- Name: TABLE str_idioma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_idioma IS 'Tabla de idiomas';


--
-- TOC entry 166 (class 1259 OID 188850)
-- Dependencies: 3
-- Name: str_impfic; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_impfic (
    imf_codigo bigint NOT NULL,
    imf_codimp bigint NOT NULL,
    imf_tipo character varying(1) NOT NULL,
    imf_nomfic character varying(100) NOT NULL,
    imf_xml bytea NOT NULL
);




--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 166
-- Name: TABLE str_impfic; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_impfic IS 'Ficheros a importar';


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN str_impfic.imf_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_impfic.imf_codigo IS 'Codigo interno';


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN str_impfic.imf_codimp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_impfic.imf_codimp IS 'Codigo importacion';


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN str_impfic.imf_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_impfic.imf_tipo IS 'Tipo: Tramite (T) / Formulario (F) / Dominio (D)';


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN str_impfic.imf_nomfic; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_impfic.imf_nomfic IS 'Nombre del fichero del cuaderno';


--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN str_impfic.imf_xml; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_impfic.imf_xml IS 'XML';


--
-- TOC entry 167 (class 1259 OID 188858)
-- Dependencies: 3
-- Name: str_import; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_import (
    imp_codigo bigint NOT NULL,
    imp_desc character varying(100) NOT NULL,
    imp_fecha timestamp without time zone NOT NULL,
    imp_audita character varying(1) NOT NULL,
    imp_fecaud timestamp without time zone,
    imp_coment character varying(1000),
    imp_impor character varying(1),
    imp_feccar timestamp without time zone NOT NULL,
    imp_fecenv timestamp without time zone
);




--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 167
-- Name: TABLE str_import; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_import IS 'Cuadernos de carga que deben pasar la auditoria';


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_codigo IS 'Identificador interno';


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_desc IS 'Descripción cuaderno de carga';


--
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_fecha IS 'Fecha/hora  alta cuaderno carga';


--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_audita; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_audita IS 'Indica si la importación ha sido auditada por sistemas: Inicial (I) / No requiere auditoria (N) / Aceptada (S) / Pendiente (P) / Rechazada (R)
Al insertar un cuaderno de carga estará en estado I.
Una vez insertados todos los ficheros asociados se calculará automáticamente el tipo, que quedará en N o P.
Si queda en P posteriormente un auditor deberá pasar al estado S o R.
';


--
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_fecaud; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_fecaud IS 'Fecha auditoria';


--
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_coment; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_coment IS 'Comentario de auditoria';


--
-- TOC entry 2184 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_impor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_impor IS 'Indica si se ha importado tras la auditoria (S/N)';


--
-- TOC entry 2185 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_feccar; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_feccar IS 'Fecha de carga en la cual se requiere ser puesto en funcionamiento';


--
-- TOC entry 2186 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN str_import.imp_fecenv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_import.imp_fecenv IS 'Fecha de envio a auditoria por parte del desarrollador';


--
-- TOC entry 168 (class 1259 OID 188866)
-- Dependencies: 1944 3
-- Name: str_menpla; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_menpla (
    mpl_codigo bigint NOT NULL,
    mpl_identi character varying(10) NOT NULL,
    mpl_activo character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2187 (class 0 OID 0)
-- Dependencies: 168
-- Name: TABLE str_menpla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_menpla IS 'Mensajes plataforma:

Segun el id del mensaje tendra una funcionalidad:
TODOS: Mensaje que saldra para todos los tramites
ANONIMOS: Mensaje que saldra para todos los tramites anonimos
AUTENTICA: Mensaje que saldra para todos los tramites autenticados
PAGOS: Mensaje que saldra para todos los tramites con pagos
';


--
-- TOC entry 2188 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN str_menpla.mpl_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_menpla.mpl_codigo IS 'CODIGO INTERNO';


--
-- TOC entry 2189 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN str_menpla.mpl_identi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_menpla.mpl_identi IS 'IDENTIFICADOR FUNCIONAL DEL MENSAJE';


--
-- TOC entry 2190 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN str_menpla.mpl_activo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_menpla.mpl_activo IS 'INDICA SI EL MENSAJE ESTA ACTIVADO';


--
-- TOC entry 169 (class 1259 OID 188872)
-- Dependencies: 3
-- Name: str_mentra; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_mentra (
    mtr_codigo bigint NOT NULL,
    mtr_codtrv bigint NOT NULL,
    mtr_identi character varying(25) NOT NULL
);




--
-- TOC entry 2191 (class 0 OID 0)
-- Dependencies: 169
-- Name: TABLE str_mentra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_mentra IS 'Mensajes definidos para un trámite que se podrán emplear en las validaciones para mostrar mensajes particularizados al usuario';


--
-- TOC entry 2192 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN str_mentra.mtr_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_mentra.mtr_codigo IS 'Código interno mensaje';


--
-- TOC entry 2193 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN str_mentra.mtr_codtrv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_mentra.mtr_codtrv IS 'Código versión trámite';


--
-- TOC entry 2194 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN str_mentra.mtr_identi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_mentra.mtr_identi IS 'Código funcional mensaje';


--
-- TOC entry 170 (class 1259 OID 188879)
-- Dependencies: 3
-- Name: str_orgres; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_orgres (
    org_codigo bigint NOT NULL,
    org_desc character varying(200) NOT NULL
);




--
-- TOC entry 2195 (class 0 OID 0)
-- Dependencies: 170
-- Name: TABLE str_orgres; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_orgres IS 'Organo Responsable. Sirve para agrupar los trámites';


--
-- TOC entry 140 (class 1259 OID 188732)
-- Dependencies: 3
-- Name: str_seqdjs; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqdjs
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 141 (class 1259 OID 188734)
-- Dependencies: 3
-- Name: str_seqdnv; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqdnv
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 142 (class 1259 OID 188736)
-- Dependencies: 3
-- Name: str_seqdoc; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqdoc
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 143 (class 1259 OID 188738)
-- Dependencies: 3
-- Name: str_seqdom; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqdom
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 144 (class 1259 OID 188740)
-- Dependencies: 3
-- Name: str_seqe09; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqe17
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 145 (class 1259 OID 188742)
-- Dependencies: 3
-- Name: str_seqetn; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqetn
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 146 (class 1259 OID 188744)
-- Dependencies: 3
-- Name: str_seqetr; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqetr
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 147 (class 1259 OID 188746)
-- Dependencies: 3
-- Name: str_seqifi; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqifi
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 148 (class 1259 OID 188748)
-- Dependencies: 3
-- Name: str_seqimp; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqimp
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 149 (class 1259 OID 188750)
-- Dependencies: 3
-- Name: str_seqmpl; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqmpl
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 150 (class 1259 OID 188752)
-- Dependencies: 3
-- Name: str_seqmtr; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqmtr
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 151 (class 1259 OID 188754)
-- Dependencies: 3
-- Name: str_seqorg; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqorg
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 152 (class 1259 OID 188756)
-- Dependencies: 3
-- Name: str_seqp09; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqp17
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 153 (class 1259 OID 188758)
-- Dependencies: 3
-- Name: str_seqtnv; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqtnv
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 154 (class 1259 OID 188760)
-- Dependencies: 3
-- Name: str_seqtra; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqtra
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 155 (class 1259 OID 188762)
-- Dependencies: 3
-- Name: str_seqtrv; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE str_seqtrv
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 171 (class 1259 OID 188884)
-- Dependencies: 3
-- Name: str_tradjs; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_tradjs (
    tdj_coddjs bigint NOT NULL,
    tdj_codidi character varying(2) NOT NULL,
    tdj_desc character varying(200) NOT NULL
);




--
-- TOC entry 2196 (class 0 OID 0)
-- Dependencies: 171
-- Name: TABLE str_tradjs; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_tradjs IS 'Traducción datos justificante';


--
-- TOC entry 2197 (class 0 OID 0)
-- Dependencies: 171
-- Name: COLUMN str_tradjs.tdj_coddjs; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradjs.tdj_coddjs IS 'Código';


--
-- TOC entry 2198 (class 0 OID 0)
-- Dependencies: 171
-- Name: COLUMN str_tradjs.tdj_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradjs.tdj_codidi IS 'Idioma';


--
-- TOC entry 2199 (class 0 OID 0)
-- Dependencies: 171
-- Name: COLUMN str_tradjs.tdj_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradjs.tdj_desc IS 'Descripción campo/bloque';


--
-- TOC entry 172 (class 1259 OID 188889)
-- Dependencies: 3
-- Name: str_tradnv; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_tradnv (
    tdn_coddnv bigint NOT NULL,
    tdn_codidi character varying(2) NOT NULL,
    tdn_info character varying(1000)
);




--
-- TOC entry 2200 (class 0 OID 0)
-- Dependencies: 172
-- Name: TABLE str_tradnv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_tradnv IS 'Traducción especificaciones documentos por nivel de autenticación';


--
-- TOC entry 2201 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN str_tradnv.tdn_coddnv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradnv.tdn_coddnv IS 'Código interno';


--
-- TOC entry 2202 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN str_tradnv.tdn_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradnv.tdn_codidi IS 'Código idioma';


--
-- TOC entry 2203 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN str_tradnv.tdn_info; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradnv.tdn_info IS 'Información documento. Puede sobreescribirse por nivel de autenticación.';


--
-- TOC entry 173 (class 1259 OID 188897)
-- Dependencies: 3
-- Name: str_tradoc; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_tradoc (
    trd_coddoc bigint NOT NULL,
    trd_codidi character varying(2) NOT NULL,
    trd_desc character varying(200) NOT NULL,
    trd_info character varying(1000)
);




--
-- TOC entry 2204 (class 0 OID 0)
-- Dependencies: 173
-- Name: TABLE str_tradoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_tradoc IS 'Traducciones documento';


--
-- TOC entry 2205 (class 0 OID 0)
-- Dependencies: 173
-- Name: COLUMN str_tradoc.trd_coddoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradoc.trd_coddoc IS 'Código interno documento';


--
-- TOC entry 2206 (class 0 OID 0)
-- Dependencies: 173
-- Name: COLUMN str_tradoc.trd_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradoc.trd_codidi IS 'Código idioma';


--
-- TOC entry 2207 (class 0 OID 0)
-- Dependencies: 173
-- Name: COLUMN str_tradoc.trd_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradoc.trd_desc IS 'Descripción documento';


--
-- TOC entry 2208 (class 0 OID 0)
-- Dependencies: 173
-- Name: COLUMN str_tradoc.trd_info; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tradoc.trd_info IS 'Información documento. Puede sobreescribirse por nivel de autenticación.';


--
-- TOC entry 174 (class 1259 OID 188905)
-- Dependencies: 3
-- Name: str_traetn; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_traetn (
    tet_codetn bigint NOT NULL,
    tet_codidi character varying(2) NOT NULL,
    tet_insini bytea,
    tet_menina bytea,
    tet_insfin bytea,
    tet_insent bytea,
    tet_feclim bytea
);




--
-- TOC entry 2209 (class 0 OID 0)
-- Dependencies: 174
-- Name: TABLE str_traetn; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_traetn IS 'Traducción especificaciones trámite';


--
-- TOC entry 2210 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_codetn; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_codetn IS 'Código especificaciones tramite nivel';


--
-- TOC entry 2211 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_codidi IS 'Código idioma';


--
-- TOC entry 2212 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_insini; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_insini IS 'Instrucciones inicio trámite';


--
-- TOC entry 2213 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_menina; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_menina IS 'Mensaje particularizado a mostrar cuando el trámite este inactivo.';


--
-- TOC entry 2214 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_insfin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_insfin IS 'Instrucciones fin trámite. ';


--
-- TOC entry 2215 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_insent; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_insent IS 'Instrucciones entrega. Se mostrarán cuando el trámite se tenga que presentar presencialmente ( firma presencial o justificante de pago presencial)';


--
-- TOC entry 2216 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN str_traetn.tet_feclim; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traetn.tet_feclim IS 'Mensaje de fecha limite particularizado. En caso de ser rellenado y de que el tramite sea presencial se mostrara este mensaje en lugar del por defecto';


--
-- TOC entry 175 (class 1259 OID 188913)
-- Dependencies: 3
-- Name: str_tramit; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_tramit (
    tra_codigo bigint NOT NULL,
    tra_codorg bigint NOT NULL,
    tra_identi character varying(20) NOT NULL,
    tra_idproc character varying(100) NOT NULL
);




--
-- TOC entry 2217 (class 0 OID 0)
-- Dependencies: 175
-- Name: TABLE str_tramit; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_tramit IS 'DEFINICION DE TRAMITE TELEMÁTICO';


--
-- TOC entry 2218 (class 0 OID 0)
-- Dependencies: 175
-- Name: COLUMN str_tramit.tra_identi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tramit.tra_identi IS 'Identificador funcional del trámite';


--
-- TOC entry 2219 (class 0 OID 0)
-- Dependencies: 175
-- Name: COLUMN str_tramit.tra_idproc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tramit.tra_idproc IS 'Identificador del procedimiento al que pertenece el trámite';


--
-- TOC entry 176 (class 1259 OID 188920)
-- Dependencies: 3
-- Name: str_trampl; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_trampl (
    tmp_codmpl bigint NOT NULL,
    tmp_codidi character varying(2) NOT NULL,
    tmp_desc character varying(1000) NOT NULL
);




--
-- TOC entry 2220 (class 0 OID 0)
-- Dependencies: 176
-- Name: TABLE str_trampl; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_trampl IS 'Traducciones mensajes plataforma';


--
-- TOC entry 2221 (class 0 OID 0)
-- Dependencies: 176
-- Name: COLUMN str_trampl.tmp_codmpl; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_trampl.tmp_codmpl IS 'Código mensaje';


--
-- TOC entry 2222 (class 0 OID 0)
-- Dependencies: 176
-- Name: COLUMN str_trampl.tmp_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_trampl.tmp_codidi IS 'Código idioma';


--
-- TOC entry 2223 (class 0 OID 0)
-- Dependencies: 176
-- Name: COLUMN str_trampl.tmp_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_trampl.tmp_desc IS 'Descripción mensaje';


--
-- TOC entry 177 (class 1259 OID 188928)
-- Dependencies: 3
-- Name: str_tramtr; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_tramtr (
    tmt_codmtr bigint NOT NULL,
    tmt_codidi character varying(2) NOT NULL,
    tmt_desc character varying(1000)
);




--
-- TOC entry 2224 (class 0 OID 0)
-- Dependencies: 177
-- Name: TABLE str_tramtr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_tramtr IS 'Traducción de los mensajes del trámite';


--
-- TOC entry 2225 (class 0 OID 0)
-- Dependencies: 177
-- Name: COLUMN str_tramtr.tmt_codmtr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tramtr.tmt_codmtr IS 'Código interno mensaje';


--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 177
-- Name: COLUMN str_tramtr.tmt_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tramtr.tmt_codidi IS 'Código idioma';


--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 177
-- Name: COLUMN str_tramtr.tmt_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tramtr.tmt_desc IS 'Mensaje';


--
-- TOC entry 178 (class 1259 OID 188936)
-- Dependencies: 3
-- Name: str_traniv; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_traniv (
    tnv_codigo bigint NOT NULL,
    tnv_codtrv bigint NOT NULL,
    tnv_nivaut character varying(3) NOT NULL,
    tnv_codetn bigint NOT NULL
);




--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 178
-- Name: TABLE str_traniv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_traniv IS 'Especificaciones del trámite por nivel de autenticación';


--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN str_traniv.tnv_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traniv.tnv_codigo IS 'Código interno';


--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN str_traniv.tnv_codtrv; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traniv.tnv_codtrv IS 'Código versión trámite';


--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN str_traniv.tnv_nivaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traniv.tnv_nivaut IS 'Nivel autenticación: Certificado (C) / Ususario-pass (U) / Anónimo (A)';


--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN str_traniv.tnv_codetn; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traniv.tnv_codetn IS 'Especificaciones trámite que pueden ser establecidas por defecto y sobreescritas según nivel autenticación';


--
-- TOC entry 179 (class 1259 OID 188943)
-- Dependencies: 3
-- Name: str_tratra; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_tratra (
    ttr_codtra bigint NOT NULL,
    ttr_codidi character varying(2) NOT NULL,
    ttr_desc character varying(200) NOT NULL
);




--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 179
-- Name: TABLE str_tratra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_tratra IS 'Traducción de Trámites';


--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 179
-- Name: COLUMN str_tratra.ttr_codtra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tratra.ttr_codtra IS 'Código trámite';


--
-- TOC entry 2235 (class 0 OID 0)
-- Dependencies: 179
-- Name: COLUMN str_tratra.ttr_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tratra.ttr_codidi IS 'Código idioma';


--
-- TOC entry 2236 (class 0 OID 0)
-- Dependencies: 179
-- Name: COLUMN str_tratra.ttr_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_tratra.ttr_desc IS 'Descripción del trámite';


--
-- TOC entry 180 (class 1259 OID 188948)
-- Dependencies: 1945 1946 1947 1948 1949 1950 1951 1952 1953 1954 3
-- Name: str_traver; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_traver (
    trv_codigo bigint NOT NULL,
    trv_codtra bigint NOT NULL,
    trv_versio bigint NOT NULL,
    trv_codetn bigint NOT NULL,
    trv_desver character varying(500),
    trv_orgdes character varying(25) NOT NULL,
    trv_uniadm bigint NOT NULL,
    trv_inipla timestamp without time zone,
    trv_finpla timestamp without time zone,
    trv_destin character varying(1) NOT NULL,
    trv_prenva character varying(1) DEFAULT 'N'::character varying NOT NULL,
    trv_firma character varying(1) NOT NULL,
    trv_regofi character varying(25),
    trv_regast character varying(25),
    trv_conejb character varying(500),
    trv_conrem character varying(1) DEFAULT 'L'::character varying NOT NULL,
    trv_conurl character varying(500),
    trv_conaut character varying(1) DEFAULT 'N'::character varying NOT NULL,
    trv_conusu character varying(500),
    trv_conpwd character varying(500),
    trv_bloque character varying(1) DEFAULT 'N'::character varying NOT NULL,
    trv_blousu character varying(1536),
    trv_idisop character varying(100) DEFAULT 'es,ca'::character varying NOT NULL,
    trv_reduci character varying(1) DEFAULT 'N'::character varying NOT NULL,
    trv_redfin character varying(1) DEFAULT 'N'::character varying NOT NULL,
    trv_tagcar character varying(100),
    trv_feccar timestamp without time zone,
    trv_anodef character varying(1) DEFAULT 'N'::character varying NOT NULL,
    trv_contip character varying(1) DEFAULT 'E'::character varying NOT NULL,
    trv_conwsv character varying(10),
    trv_regaut character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2237 (class 0 OID 0)
-- Dependencies: 180
-- Name: TABLE str_traver; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_traver IS 'Versión de trámites';


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_codigo IS 'Código interno';


--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_codtra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_codtra IS 'Código trámite';


--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_versio; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_versio IS 'Versión trámite';


--
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_codetn; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_codetn IS 'Especificaciones trámite que pueden ser establecidas por defecto y sobreescritas según nivel autenticación';


--
-- TOC entry 2242 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_desver; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_desver IS 'Motivo de la versión';


--
-- TOC entry 2243 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_orgdes; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_orgdes IS 'Identificador del órgano destino del trámite. Mapeado a tabla BORGANI de Registro ';


--
-- TOC entry 2244 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_uniadm; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_uniadm IS 'Unidad Administrativa responsable trámite. Mapeado a tabla Unidades administrativas del SAC';


--
-- TOC entry 2245 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_inipla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_inipla IS 'Fecha inicio del plazo de presentación';


--
-- TOC entry 2246 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_finpla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_finpla IS 'Fecha fin del plazo de presentación';


--
-- TOC entry 2247 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_destin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_destin IS 'Indica destino trámite: Registro/Preregistro (R) - Bandeja (B) - Consulta (C)';


--
-- TOC entry 2248 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_prenva; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_prenva IS 'Para preenvio indica si se realiza la confirmacion automatica de las entradas';


--
-- TOC entry 2249 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_firma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_firma IS 'Indica si un trámite debe firmarse: (si autenticación certificado se realizará firma digital asiento sino se realizará firma manuscrita del justificante que deberá presentarse presencialmente)
 - para destino Registro: opcional
 - para destino Bandeja: opcional
 - para destino Consulta: no procede';


--
-- TOC entry 2250 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_regofi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_regofi IS 'Para trámite con destino Registro indica Oficina Registro (mapeado a bagecom_oficines)';


--
-- TOC entry 2251 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_regast; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_regast IS 'Para trámite con destino Registro indica Tipo Asunto (bztdocu)';


--
-- TOC entry 2252 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_conejb; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_conejb IS 'Para trámite con destino Consulta indica JNDI del Ejb que sirve la consulta';


--
-- TOC entry 2253 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_conrem; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_conrem IS 'Para trámite con destino Consulta indica si el Ejb que sirve la consulta es remoto (R) o local (L)';


--
-- TOC entry 2254 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_conurl; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_conurl IS 'Para trámite con destino Consulta si  el Ejb que sirve la consulta es remoto indica la URL de acceso';


--
-- TOC entry 2255 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_conaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_conaut IS 'Para trámite con destino Consulta indica si se debe realizar:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password
 - C: explícita a través plugin autenticación del organismo';


--
-- TOC entry 2256 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_conusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_conusu IS 'Para trámite con destino Consulta y con autenticación explicíta con usuario/password se indica usuario';


--
-- TOC entry 2257 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_conpwd; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_conpwd IS 'Para trámite con destino Consulta y con autenticación explicíta  con usuario/password  se indica password';


--
-- TOC entry 2258 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_bloque; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_bloque IS 'Permite habilitar sistema de bloqueo para modificación de trámites en el sistraback de manera que no permite la modificación simultanea';


--
-- TOC entry 2259 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_blousu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_blousu IS 'En caso de estar bloqueado indica el usuario seycon que lo tiene bloqueado';


--
-- TOC entry 2260 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_idisop; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_idisop IS 'Idiomas soportados: lista de idiomas soportados separados por comas';


--
-- TOC entry 2261 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_reduci; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_reduci IS 'Indica si un trámite sigue el circuito reducido S - si  N - no';


--
-- TOC entry 2262 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_redfin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_redfin IS 'Indica para un trámite se salta automaticamente a la url de fin ( S - si  N - no) sin mostrar la pantalla de justificante';


--
-- TOC entry 2263 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_tagcar; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_tagcar IS 'Tag que indica el cuaderno de carga en el que se ha generado';


--
-- TOC entry 2264 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_feccar; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_feccar IS 'Fecha en la que se ha exportado (al importar el xml se alimentará este campo del xml origen)';


--
-- TOC entry 2265 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_anodef; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_anodef IS 'Autenticacion anonima por defecto: Si esta activado y en caso de que este seleccionado A y existan otros niveles de autenticacion (C o U)  en el tramite,
si se accede al tramite sin estar autenticado se realizara una autenticacion anonima automatica. Si se esta autenticado se respetara la autenticacion actual
(siempre que este permitida en el tramite)';


--
-- TOC entry 2266 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN str_traver.trv_regaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_traver.trv_regaut IS 'Registro automatico: al llegar al paso registrar se dispara automaticamente el envio';


--
-- TOC entry 181 (class 1259 OID 189097)
-- Dependencies: 3
-- Name: str_usutra; Type: TABLE; Schema: public; Owner: sistra; Tablespace:
--

CREATE TABLE str_usutra (
    ust_codusu character varying(200) NOT NULL,
    ust_codtra bigint NOT NULL
);




--
-- TOC entry 2267 (class 0 OID 0)
-- Dependencies: 181
-- Name: TABLE str_usutra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE str_usutra IS 'Permisos individuales para un usuario de acceso a tramites';


--
-- TOC entry 2268 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN str_usutra.ust_codusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_usutra.ust_codusu IS 'CODIGO USUARIO';


--
-- TOC entry 2269 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN str_usutra.ust_codtra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN str_usutra.ust_codtra IS 'CODIGO TRAMITE';


--
-- TOC entry 1956 (class 2606 OID 188771)
-- Dependencies: 156 156
-- Name: str_djs_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_datjus
    ADD CONSTRAINT str_djs_pk PRIMARY KEY (djs_codigo);


--
-- TOC entry 1961 (class 2606 OID 188789)
-- Dependencies: 158 158
-- Name: str_dnv_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_docniv
    ADD CONSTRAINT str_dnv_pk PRIMARY KEY (dnv_codigo);


--
-- TOC entry 1963 (class 2606 OID 188787)
-- Dependencies: 158 158 158
-- Name: str_dnvniv_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_docniv
    ADD CONSTRAINT str_dnvniv_uni UNIQUE (dnv_coddoc, dnv_nivaut);


--
-- TOC entry 1965 (class 2606 OID 188802)
-- Dependencies: 159 159
-- Name: str_doc_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_docum
    ADD CONSTRAINT str_doc_pk PRIMARY KEY (doc_codigo);


--
-- TOC entry 1967 (class 2606 OID 188800)
-- Dependencies: 159 159 159
-- Name: str_docide_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_docum
    ADD CONSTRAINT str_docide_uni UNIQUE (doc_codtrv, doc_identi);


--
-- TOC entry 1969 (class 2606 OID 188814)
-- Dependencies: 160 160
-- Name: str_dom_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_domin
    ADD CONSTRAINT str_dom_pk PRIMARY KEY (dom_codigo);


--
-- TOC entry 1971 (class 2606 OID 188812)
-- Dependencies: 160 160
-- Name: str_domide_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_domin
    ADD CONSTRAINT str_domide_uni UNIQUE (dom_identi);


--
-- TOC entry 1977 (class 2606 OID 188836)
-- Dependencies: 163 163
-- Name: str_etn_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_espniv
    ADD CONSTRAINT str_etn_pk PRIMARY KEY (etn_codigo);


--
-- TOC entry 2023 (class 2606 OID 189111)
-- Dependencies: 182 182
-- Name: str_grp_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_grupos
    ADD CONSTRAINT str_grp_pk PRIMARY KEY (grp_codigo);


--
-- TOC entry 2027 (class 2606 OID 189126)
-- Dependencies: 184 184 184
-- Name: str_grt_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_grptra
    ADD CONSTRAINT str_grt_pk PRIMARY KEY (grt_codgrp, grt_codtra);


--
-- TOC entry 2025 (class 2606 OID 189116)
-- Dependencies: 183 183 183
-- Name: str_gru_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_grpusu
    ADD CONSTRAINT str_gru_pk PRIMARY KEY (gru_codgrp, gru_codusu);


--
-- TOC entry 1979 (class 2606 OID 188844)
-- Dependencies: 164 164
-- Name: str_gsf_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_gesfrm
    ADD CONSTRAINT str_gsf_pk PRIMARY KEY (gsf_ident);


--
-- TOC entry 1981 (class 2606 OID 188849)
-- Dependencies: 165 165
-- Name: str_idi_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_idioma
    ADD CONSTRAINT str_idi_pk PRIMARY KEY (idi_codigo);


--
-- TOC entry 1983 (class 2606 OID 188857)
-- Dependencies: 166 166
-- Name: str_imf_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_impfic
    ADD CONSTRAINT str_imf_pk PRIMARY KEY (imf_codigo);


--
-- TOC entry 1985 (class 2606 OID 188865)
-- Dependencies: 167 167
-- Name: str_imp_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_import
    ADD CONSTRAINT str_imp_pk PRIMARY KEY (imp_codigo);


--
-- TOC entry 1987 (class 2606 OID 188871)
-- Dependencies: 168 168
-- Name: str_menpla_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_menpla
    ADD CONSTRAINT str_menpla_pk PRIMARY KEY (mpl_codigo);


--
-- TOC entry 1989 (class 2606 OID 188878)
-- Dependencies: 169 169
-- Name: str_mtr_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_mentra
    ADD CONSTRAINT str_mtr_pk PRIMARY KEY (mtr_codigo);


--
-- TOC entry 1991 (class 2606 OID 188876)
-- Dependencies: 169 169 169
-- Name: str_mtride_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_mentra
    ADD CONSTRAINT str_mtride_uni UNIQUE (mtr_codtrv, mtr_identi);


--
-- TOC entry 1958 (class 2606 OID 188776)
-- Dependencies: 157 157 157
-- Name: str_mun_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_dmunic
    ADD CONSTRAINT str_mun_pk PRIMARY KEY (mun_codigo, mun_provin);


--
-- TOC entry 1993 (class 2606 OID 188883)
-- Dependencies: 170 170
-- Name: str_org_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_orgres
    ADD CONSTRAINT str_org_pk PRIMARY KEY (org_codigo);


--
-- TOC entry 1973 (class 2606 OID 188819)
-- Dependencies: 161 161
-- Name: str_pai_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_dpais
    ADD CONSTRAINT str_pai_pk PRIMARY KEY (pai_codigo);


--
-- TOC entry 1975 (class 2606 OID 188824)
-- Dependencies: 162 162
-- Name: str_pro_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_dprovi
    ADD CONSTRAINT str_pro_pk PRIMARY KEY (pro_codigo);


--
-- TOC entry 1995 (class 2606 OID 188888)
-- Dependencies: 171 171 171
-- Name: str_tdj_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tradjs
    ADD CONSTRAINT str_tdj_pk PRIMARY KEY (tdj_coddjs, tdj_codidi);


--
-- TOC entry 1997 (class 2606 OID 188896)
-- Dependencies: 172 172 172
-- Name: str_tdn_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tradnv
    ADD CONSTRAINT str_tdn_pk PRIMARY KEY (tdn_coddnv, tdn_codidi);


--
-- TOC entry 2001 (class 2606 OID 188912)
-- Dependencies: 174 174 174
-- Name: str_tet_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_traetn
    ADD CONSTRAINT str_tet_pk PRIMARY KEY (tet_codetn, tet_codidi);


--
-- TOC entry 2009 (class 2606 OID 188935)
-- Dependencies: 177 177 177
-- Name: str_tmt_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tramtr
    ADD CONSTRAINT str_tmt_pk PRIMARY KEY (tmt_codmtr, tmt_codidi);


--
-- TOC entry 2011 (class 2606 OID 188942)
-- Dependencies: 178 178
-- Name: str_tnv_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_traniv
    ADD CONSTRAINT str_tnv_pk PRIMARY KEY (tnv_codigo);


--
-- TOC entry 2013 (class 2606 OID 188940)
-- Dependencies: 178 178 178
-- Name: str_tnvniv_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_traniv
    ADD CONSTRAINT str_tnvniv_uni UNIQUE (tnv_codtrv, tnv_nivaut);


--
-- TOC entry 2003 (class 2606 OID 188919)
-- Dependencies: 175 175
-- Name: str_tra_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tramit
    ADD CONSTRAINT str_tra_pk PRIMARY KEY (tra_codigo);


--
-- TOC entry 2005 (class 2606 OID 188917)
-- Dependencies: 175 175
-- Name: str_traide_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tramit
    ADD CONSTRAINT str_traide_uni UNIQUE (tra_identi);


--
-- TOC entry 2007 (class 2606 OID 188927)
-- Dependencies: 176 176 176
-- Name: str_trampl_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_trampl
    ADD CONSTRAINT str_trampl_pk PRIMARY KEY (tmp_codmpl, tmp_codidi);


--
-- TOC entry 1999 (class 2606 OID 188904)
-- Dependencies: 173 173 173
-- Name: str_trd_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tradoc
    ADD CONSTRAINT str_trd_pk PRIMARY KEY (trd_coddoc, trd_codidi);


--
-- TOC entry 2017 (class 2606 OID 188966)
-- Dependencies: 180 180
-- Name: str_trv_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_traver
    ADD CONSTRAINT str_trv_pk PRIMARY KEY (trv_codigo);


--
-- TOC entry 2019 (class 2606 OID 188964)
-- Dependencies: 180 180 180
-- Name: str_trvver_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_traver
    ADD CONSTRAINT str_trvver_uni UNIQUE (trv_codtra, trv_versio);


--
-- TOC entry 2015 (class 2606 OID 188947)
-- Dependencies: 179 179 179
-- Name: str_ttr_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_tratra
    ADD CONSTRAINT str_ttr_pk PRIMARY KEY (ttr_codtra, ttr_codidi);


--
-- TOC entry 2021 (class 2606 OID 189101)
-- Dependencies: 181 181 181
-- Name: str_ust_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace:
--

ALTER TABLE ONLY str_usutra
    ADD CONSTRAINT str_ust_pk PRIMARY KEY (ust_codusu, ust_codtra);


--
-- TOC entry 1959 (class 1259 OID 188777)
-- Dependencies: 157
-- Name: str_munpro_idx; Type: INDEX; Schema: public; Owner: sistra; Tablespace:
--

CREATE INDEX str_munpro_idx ON str_dmunic USING btree (mun_provin);


--
-- TOC entry 2028 (class 2606 OID 188967)
-- Dependencies: 1976 163 156
-- Name: str_djsetn_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_datjus
    ADD CONSTRAINT str_djsetn_fk FOREIGN KEY (djs_codetn) REFERENCES str_espniv(etn_codigo);


--
-- TOC entry 2030 (class 2606 OID 188977)
-- Dependencies: 158 1964 159
-- Name: str_dnvdoc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_docniv
    ADD CONSTRAINT str_dnvdoc_fk FOREIGN KEY (dnv_coddoc) REFERENCES str_docum(doc_codigo);


--
-- TOC entry 2031 (class 2606 OID 188982)
-- Dependencies: 2016 180 159
-- Name: str_doctrv_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_docum
    ADD CONSTRAINT str_doctrv_fk FOREIGN KEY (doc_codtrv) REFERENCES str_traver(trv_codigo);


--
-- TOC entry 2032 (class 2606 OID 188987)
-- Dependencies: 1992 160 170
-- Name: str_domorg_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_domin
    ADD CONSTRAINT str_domorg_fk FOREIGN KEY (dom_codorg) REFERENCES str_orgres(org_codigo);


--
-- TOC entry 2056 (class 2606 OID 189127)
-- Dependencies: 182 184 2022
-- Name: str_grtgrp_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_grptra
    ADD CONSTRAINT str_grtgrp_fk FOREIGN KEY (grt_codgrp) REFERENCES str_grupos(grp_codigo);


--
-- TOC entry 2057 (class 2606 OID 189132)
-- Dependencies: 2002 175 184
-- Name: str_grttra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_grptra
    ADD CONSTRAINT str_grttra_fk FOREIGN KEY (grt_codtra) REFERENCES str_tramit(tra_codigo);


--
-- TOC entry 2055 (class 2606 OID 189117)
-- Dependencies: 2022 182 183
-- Name: str_grugrp_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_grpusu
    ADD CONSTRAINT str_grugrp_fk FOREIGN KEY (gru_codgrp) REFERENCES str_grupos(grp_codigo);


--
-- TOC entry 2033 (class 2606 OID 188992)
-- Dependencies: 166 167 1984
-- Name: str_imfimp_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_impfic
    ADD CONSTRAINT str_imfimp_fk FOREIGN KEY (imf_codimp) REFERENCES str_import(imp_codigo);


--
-- TOC entry 2034 (class 2606 OID 188997)
-- Dependencies: 169 2016 180
-- Name: str_mtrtrv_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_mentra
    ADD CONSTRAINT str_mtrtrv_fk FOREIGN KEY (mtr_codtrv) REFERENCES str_traver(trv_codigo);


--
-- TOC entry 2029 (class 2606 OID 188972)
-- Dependencies: 157 162 1974
-- Name: str_munpro_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_dmunic
    ADD CONSTRAINT str_munpro_fk FOREIGN KEY (mun_provin) REFERENCES str_dprovi(pro_codigo);


--
-- TOC entry 2035 (class 2606 OID 189002)
-- Dependencies: 1955 156 171
-- Name: str_tdjdjs_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tradjs
    ADD CONSTRAINT str_tdjdjs_fk FOREIGN KEY (tdj_coddjs) REFERENCES str_datjus(djs_codigo);


--
-- TOC entry 2036 (class 2606 OID 189007)
-- Dependencies: 165 1980 171
-- Name: str_tdjidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tradjs
    ADD CONSTRAINT str_tdjidi_fk FOREIGN KEY (tdj_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2037 (class 2606 OID 189012)
-- Dependencies: 158 1960 172
-- Name: str_tdndnv_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tradnv
    ADD CONSTRAINT str_tdndnv_fk FOREIGN KEY (tdn_coddnv) REFERENCES str_docniv(dnv_codigo);


--
-- TOC entry 2038 (class 2606 OID 189017)
-- Dependencies: 165 1980 172
-- Name: str_tdnidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tradnv
    ADD CONSTRAINT str_tdnidi_fk FOREIGN KEY (tdn_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2041 (class 2606 OID 189032)
-- Dependencies: 163 174 1976
-- Name: str_tetetn_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_traetn
    ADD CONSTRAINT str_tetetn_fk FOREIGN KEY (tet_codetn) REFERENCES str_espniv(etn_codigo);


--
-- TOC entry 2042 (class 2606 OID 189037)
-- Dependencies: 1980 174 165
-- Name: str_tetidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_traetn
    ADD CONSTRAINT str_tetidi_fk FOREIGN KEY (tet_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2044 (class 2606 OID 189047)
-- Dependencies: 176 1980 165
-- Name: str_tmpidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_trampl
    ADD CONSTRAINT str_tmpidi_fk FOREIGN KEY (tmp_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2045 (class 2606 OID 189052)
-- Dependencies: 168 176 1986
-- Name: str_tmpmpl_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_trampl
    ADD CONSTRAINT str_tmpmpl_fk FOREIGN KEY (tmp_codmpl) REFERENCES str_menpla(mpl_codigo);


--
-- TOC entry 2046 (class 2606 OID 189057)
-- Dependencies: 177 165 1980
-- Name: str_tmtidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tramtr
    ADD CONSTRAINT str_tmtidi_fk FOREIGN KEY (tmt_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2047 (class 2606 OID 189062)
-- Dependencies: 177 1988 169
-- Name: str_tmtmtr_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tramtr
    ADD CONSTRAINT str_tmtmtr_fk FOREIGN KEY (tmt_codmtr) REFERENCES str_mentra(mtr_codigo);


--
-- TOC entry 2048 (class 2606 OID 189067)
-- Dependencies: 163 178 1976
-- Name: str_tnvetr_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_traniv
    ADD CONSTRAINT str_tnvetr_fk FOREIGN KEY (tnv_codetn) REFERENCES str_espniv(etn_codigo);


--
-- TOC entry 2049 (class 2606 OID 189072)
-- Dependencies: 180 178 2016
-- Name: str_tnvtrv_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_traniv
    ADD CONSTRAINT str_tnvtrv_fk FOREIGN KEY (tnv_codtrv) REFERENCES str_traver(trv_codigo);


--
-- TOC entry 2043 (class 2606 OID 189042)
-- Dependencies: 175 1992 170
-- Name: str_traorg_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tramit
    ADD CONSTRAINT str_traorg_fk FOREIGN KEY (tra_codorg) REFERENCES str_orgres(org_codigo);


--
-- TOC entry 2039 (class 2606 OID 189022)
-- Dependencies: 1964 173 159
-- Name: str_trddoc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tradoc
    ADD CONSTRAINT str_trddoc_fk FOREIGN KEY (trd_coddoc) REFERENCES str_docum(doc_codigo);


--
-- TOC entry 2040 (class 2606 OID 189027)
-- Dependencies: 165 173 1980
-- Name: str_trdidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tradoc
    ADD CONSTRAINT str_trdidi_fk FOREIGN KEY (trd_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2052 (class 2606 OID 189087)
-- Dependencies: 180 163 1976
-- Name: str_trvetr_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_traver
    ADD CONSTRAINT str_trvetr_fk FOREIGN KEY (trv_codetn) REFERENCES str_espniv(etn_codigo);


--
-- TOC entry 2053 (class 2606 OID 189092)
-- Dependencies: 180 175 2002
-- Name: str_trvtra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_traver
    ADD CONSTRAINT str_trvtra_fk FOREIGN KEY (trv_codtra) REFERENCES str_tramit(tra_codigo);


--
-- TOC entry 2050 (class 2606 OID 189077)
-- Dependencies: 165 179 1980
-- Name: str_ttridi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tratra
    ADD CONSTRAINT str_ttridi_fk FOREIGN KEY (ttr_codidi) REFERENCES str_idioma(idi_codigo);


--
-- TOC entry 2051 (class 2606 OID 189082)
-- Dependencies: 175 2002 179
-- Name: str_ttrtra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_tratra
    ADD CONSTRAINT str_ttrtra_fk FOREIGN KEY (ttr_codtra) REFERENCES str_tramit(tra_codigo);


--
-- TOC entry 2054 (class 2606 OID 189102)
-- Dependencies: 181 2002 175
-- Name: str_usttra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY str_usutra
    ADD CONSTRAINT str_usttra_fk FOREIGN KEY (ust_codtra) REFERENCES str_tramit(tra_codigo);


--
-- TOC entry 2061 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-01-27 09:22:56

--
-- PostgreSQL database dump complete
--


-- v3.0.2
-- SISTRA: DIRECCION INTERESADOS
alter table STR_ESPNIV add ETN_RTEDAT bytea;
alter table STR_ESPNIV add ETN_RDODAT bytea;

comment on column STR_ESPNIV.ETN_RTEDAT is
'Datos desglosados representante (nif, nombre, direccion, email,...)';
comment on column STR_ESPNIV.ETN_RDODAT is
'Datos desglosados representado (nif, nombre, direccion, email,...)';

-- V3.1.3
-- SISTRA: DEBUG POR TRAMITE
alter table STR_TRAVER  add  TRV_DEBUG      character varying(1)                    default 'N' not null;
comment on column STR_TRAVER.TRV_DEBUG is 'Indica si el debug esta habilitado';

-- SISTRA: FIN TRAMITE AUTO PARA TRAMITES CON PAGO FINALIZADO
alter table STR_ESPNIV  add ETN_ALEFIN  character varying(1) default 'N' not null;
comment on column STR_ESPNIV.ETN_ALEFIN is
'Indica si se intenta finalizar autom�ticamente el tr�mite antes de realizar la alerta de tr�mites inacabados con pago realizado';


-- V3.1.4
alter table STR_ESPNIV add ETN_CHKSMS  character varying(1)  default 'N' not null;