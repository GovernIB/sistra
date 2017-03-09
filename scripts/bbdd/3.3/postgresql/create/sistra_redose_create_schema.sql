--
-- PostgreSQL database dump
--


--
-- TOC entry 154 (class 1259 OID 188508)
-- Dependencies: 3
-- Name: rds_arcpli; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_arcpli (
    arp_codpli bigint NOT NULL,
    arp_datos bytea NOT NULL
);




--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 154
-- Name: TABLE rds_arcpli; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_arcpli IS 'Archivos asociados a una plantilla';


--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 154
-- Name: COLUMN rds_arcpli.arp_codpli; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_arcpli.arp_codpli IS 'CODIGO';


--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 154
-- Name: COLUMN rds_arcpli.arp_datos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_arcpli.arp_datos IS 'Contenido del fichero de la plantilla';


--
-- TOC entry 155 (class 1259 OID 188516)
-- Dependencies: 3
-- Name: rds_docum; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_docum (
    doc_codigo bigint NOT NULL,
    doc_codver bigint NOT NULL,
    doc_clave character varying(10) NOT NULL,
    doc_titulo character varying(200) NOT NULL,
    doc_fecha timestamp with time zone NOT NULL,
    doc_nif character varying(11),
    doc_seycon character varying(1536),
    doc_uniadm bigint NOT NULL,
    doc_codubi bigint NOT NULL,
    doc_ficher character varying(255) NOT NULL,
    doc_ext character varying(4) NOT NULL,
    doc_hash character varying(500) NOT NULL,
    doc_codpla bigint,
    doc_delete character varying(1),
    doc_idioma character varying(2),
    doc_refgd character varying(4000)
);




--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 155
-- Name: TABLE rds_docum; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_docum IS 'FICHA CON LOS DATOS DE UN DOCUMENTO';


--
-- TOC entry 1962 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_codigo IS 'CODIGO';


--
-- TOC entry 1963 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_codver; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_codver IS 'CODIGO VERSION MODELO';


--
-- TOC entry 1964 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_clave; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_clave IS 'CLAVE DE ACCESO AL DOCUMENTO';


--
-- TOC entry 1965 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_titulo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_titulo IS 'TITULO DOCUMENTO';


--
-- TOC entry 1966 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_fecha IS 'FECHA INCORPORACION AL RDS. PROPORCIONADA POR RDS.';


--
-- TOC entry 1967 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_nif; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_nif IS 'PERSONA QUE HA SUMINISTRADO EL DOCUMENTO';


--
-- TOC entry 1968 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_seycon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_seycon IS 'USUARIO SEYCON DE LA PERSONA QUE HA SUMINISTRADO EL DOCUMENTO (EN CASO DE QUE ESTE AUTENTICADO)';


--
-- TOC entry 1969 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_uniadm; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_uniadm IS 'UNIDAD ADMINISTRATIVA RESPONSABLE DEL DOCUMENTO. ESTE CÓDIGO PROVIENE DEL SAC (TABLA RSC_UNIADM)';


--
-- TOC entry 1970 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_codubi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_codubi IS 'CODIGO UBICACIÓN DONDE ESTA ALMACENADO';


--
-- TOC entry 1971 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_ficher; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_ficher IS 'NOMBRE FICHERO';


--
-- TOC entry 1972 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_ext; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_ext IS 'EXTENSIÓN DEL FICHERO';


--
-- TOC entry 1973 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_hash; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_hash IS 'HASH DEL DOCUMENTO. SE CALCULARA POR EL RDS AL INCORPORAR EL FICHERO.';


--
-- TOC entry 1974 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_codpla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_codpla IS 'PLANTILLA  ESPECIFICA DE VISUALIZACIÓN. PODEMOS INDICAR QUE UN DOCUMENTO ESTRUCTURADO SE VISUALIZE CON UNA PLANTILLA DETERMINADA';


--
-- TOC entry 1975 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_delete; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_delete IS 'MARCADO PARA BORRAR POR PROCESO DE BORRADO DE DOCUMENTOS SIN USOS';


--
-- TOC entry 1976 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_idioma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_idioma IS 'PARA DOCUMENTOS ESTRUCTURADOS INDICA EL IDIOMA POR DEFECTO DE VISUALIZACIÓN. SI NO SE ESPECIFICA COGERA "CA".';


--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 155
-- Name: COLUMN rds_docum.doc_refgd; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_docum.doc_refgd IS 'EN CASO DE QUE EL RDS SE SINCRONIZE CON UN GESTOR DOCUMENTAL, INDICA LA REFERENCIA DEL DOCUMENTO EN EL GESTOR DOCUMENTAL.
PARA QUE NO SE MIGREN LOS DOCUMENTOS ANTIGUOS SE MARCARAN CON "#NOCONSOLIDABLE#", INDICANDO QUE NO SE MIGRARAN.';


--
-- TOC entry 172 (class 1259 OID 188713)
-- Dependencies: 1888 3
-- Name: rds_ficext; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_ficext (
    fie_refdoc character varying(500) NOT NULL,
    fie_reffec timestamp without time zone NOT NULL,
    fie_coddoc bigint NOT NULL,
    fie_borrar character varying(1) DEFAULT 'N'::character varying NOT NULL,
    fie_codubi bigint NOT NULL
);




--
-- TOC entry 1978 (class 0 OID 0)
-- Dependencies: 172
-- Name: TABLE rds_ficext; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_ficext IS 'UBICACIӓN EN REPOSITORIO DE FICHEROS EXTERNOS (PLUGIN UBICACION NO DEFECTO)';


--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN rds_ficext.fie_refdoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ficext.fie_refdoc IS 'REFERENCIA EXTERNA';


--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN rds_ficext.fie_reffec; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ficext.fie_reffec IS 'FECHA REFERENCIA (SOLO SERA VALIDA LA ULTIMA, EL RESTO SE BORRARAN)';


--
-- TOC entry 1981 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN rds_ficext.fie_coddoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ficext.fie_coddoc IS 'CODIGO DOCUMENTO';


--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN rds_ficext.fie_borrar; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ficext.fie_borrar IS 'INDICA SI ESTA MARCADO PARA BORRAR (S/N)';


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN rds_ficext.fie_codubi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ficext.fie_codubi IS 'INDICA CODIGO DE UBICACION';


--
-- TOC entry 156 (class 1259 OID 188524)
-- Dependencies: 3
-- Name: rds_ficher; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_ficher (
    fic_codigo bigint NOT NULL,
    fic_datos bytea NOT NULL
);




--
-- TOC entry 1984 (class 0 OID 0)
-- Dependencies: 156
-- Name: TABLE rds_ficher; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_ficher IS 'UBICACI�N EN BD DE REPOSITORIO DE FICHEROS';


--
-- TOC entry 157 (class 1259 OID 188532)
-- Dependencies: 3
-- Name: rds_firmas; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_firmas (
    fir_codigo bigint NOT NULL,
    fir_coddoc bigint NOT NULL,
    fir_firma bytea NOT NULL,
    fir_format character varying(50)
);




--
-- TOC entry 1985 (class 0 OID 0)
-- Dependencies: 157
-- Name: TABLE rds_firmas; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_firmas IS 'FIRMAS DE UN DOCUMENTO';


--
-- TOC entry 1986 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN rds_firmas.fir_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_firmas.fir_codigo IS 'CODIGO ';


--
-- TOC entry 1987 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN rds_firmas.fir_coddoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_firmas.fir_coddoc IS 'CODIGO FICHA DOCUMENTO';


--
-- TOC entry 1988 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN rds_firmas.fir_firma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_firmas.fir_firma IS 'FIRMA';


--
-- TOC entry 1989 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN rds_firmas.fir_format; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_firmas.fir_format IS 'FORMATO DE LA FIRMA (EN CASO DE QUE SE PERMITAN VARIOS FORMATOS)';


--
-- TOC entry 158 (class 1259 OID 188541)
-- Dependencies: 3
-- Name: rds_format; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_format (
    for_id bigint NOT NULL,
    for_class character varying(300) NOT NULL,
    for_desc character varying(300) NOT NULL
);




--
-- TOC entry 1990 (class 0 OID 0)
-- Dependencies: 158
-- Name: TABLE rds_format; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_format IS 'FORMATEADORES';


--
-- TOC entry 1991 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN rds_format.for_id; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_format.for_id IS 'IDENTIFICADOR DEL FORMATEADOR';


--
-- TOC entry 1992 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN rds_format.for_class; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_format.for_class IS 'CLASE DONDE ESTA IMPLEMENTADO EL FORMATEADOR';


--
-- TOC entry 1993 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN rds_format.for_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_format.for_desc IS 'DESCRIPCION DEL FORMATEADOR';


--
-- TOC entry 159 (class 1259 OID 188549)
-- Dependencies: 3
-- Name: rds_idioma; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_idioma (
    idi_codigo character varying(2) NOT NULL,
    idi_nombre character varying(50) NOT NULL
);




--
-- TOC entry 1994 (class 0 OID 0)
-- Dependencies: 159
-- Name: TABLE rds_idioma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_idioma IS 'IDIOMAS';


--
-- TOC entry 1995 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN rds_idioma.idi_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_idioma.idi_codigo IS 'CODIGO DE IDIOMA';


--
-- TOC entry 1996 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN rds_idioma.idi_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_idioma.idi_nombre IS 'NOMBRE IDIOMA';


--
-- TOC entry 170 (class 1259 OID 188698)
-- Dependencies: 3
-- Name: rds_logegd; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_logegd (
    log_codigo bigint NOT NULL,
    log_seycon character varying(1536) NOT NULL,
    log_deserr character varying(4000) NOT NULL,
    log_error bytea NOT NULL,
    log_fecha timestamp with time zone NOT NULL,
    log_coddoc bigint NOT NULL
);




--
-- TOC entry 1997 (class 0 OID 0)
-- Dependencies: 170
-- Name: TABLE rds_logegd; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_logegd IS 'LOG DE ERRORES DEL GESTOR DOCUMENTAL';


--
-- TOC entry 1998 (class 0 OID 0)
-- Dependencies: 170
-- Name: COLUMN rds_logegd.log_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logegd.log_codigo IS 'CODIGO';


--
-- TOC entry 1999 (class 0 OID 0)
-- Dependencies: 170
-- Name: COLUMN rds_logegd.log_seycon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logegd.log_seycon IS 'USUARIO SEYCON';


--
-- TOC entry 2000 (class 0 OID 0)
-- Dependencies: 170
-- Name: COLUMN rds_logegd.log_deserr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logegd.log_deserr IS 'DESCRIPCION DEL ERROR';


--
-- TOC entry 2001 (class 0 OID 0)
-- Dependencies: 170
-- Name: COLUMN rds_logegd.log_error; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logegd.log_error IS 'TRAZA DEL ERROR';


--
-- TOC entry 2002 (class 0 OID 0)
-- Dependencies: 170
-- Name: COLUMN rds_logegd.log_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logegd.log_fecha IS 'FECHA DEL ERROR';


--
-- TOC entry 160 (class 1259 OID 188554)
-- Dependencies: 3
-- Name: rds_logope; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_logope (
    log_codigo bigint NOT NULL,
    log_codtop character varying(4) NOT NULL,
    log_desope character varying(1000) NOT NULL,
    log_fecha timestamp with time zone NOT NULL,
    log_seycon character varying(1536) NOT NULL
);




--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 160
-- Name: TABLE rds_logope; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_logope IS 'LOG DE OPERACIONES';


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN rds_logope.log_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logope.log_codigo IS 'CODIGO';


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN rds_logope.log_codtop; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logope.log_codtop IS 'CODIGO OPERACIÓN';


--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN rds_logope.log_desope; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logope.log_desope IS 'DESCRIPCIÓN OPERACIÓN';


--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN rds_logope.log_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logope.log_fecha IS 'FECHA OPERACIÓN';


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 160
-- Name: COLUMN rds_logope.log_seycon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_logope.log_seycon IS 'USUARIO SEYCON';


--
-- TOC entry 161 (class 1259 OID 188562)
-- Dependencies: 1882 1883 3
-- Name: rds_modelo; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_modelo (
    mod_codigo bigint NOT NULL,
    mod_modelo character varying(20) NOT NULL,
    mod_nombre character varying(100) NOT NULL,
    mod_desc character varying(400),
    mod_estruc character varying(1) DEFAULT 'N'::character varying NOT NULL,
    mod_custod character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 161
-- Name: TABLE rds_modelo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_modelo IS 'MODELOS DE DOCUMENTOS';


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 161
-- Name: COLUMN rds_modelo.mod_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_modelo.mod_codigo IS 'CODIGO';


--
-- TOC entry 2011 (class 0 OID 0)
-- Dependencies: 161
-- Name: COLUMN rds_modelo.mod_modelo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_modelo.mod_modelo IS 'IDENTIFICADOR MODELO';


--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 161
-- Name: COLUMN rds_modelo.mod_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_modelo.mod_nombre IS 'NOMBRE MODELO';


--
-- TOC entry 2013 (class 0 OID 0)
-- Dependencies: 161
-- Name: COLUMN rds_modelo.mod_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_modelo.mod_desc IS 'DESCRIPCION MODELO';


--
-- TOC entry 2014 (class 0 OID 0)
-- Dependencies: 161
-- Name: COLUMN rds_modelo.mod_estruc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_modelo.mod_estruc IS 'ESTRUCTURADO (S) : XML / NO ESTRUCTURADO (N): FICHERO';


--
-- TOC entry 2015 (class 0 OID 0)
-- Dependencies: 161
-- Name: COLUMN rds_modelo.mod_custod; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_modelo.mod_custod IS 'INDICA SI SE DEBE CUSTODIAR EL DOCUMENTO FIRMADO';


--
-- TOC entry 162 (class 1259 OID 188574)
-- Dependencies: 3
-- Name: rds_plaidi; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_plaidi (
    pli_codigo bigint NOT NULL,
    pli_codpla bigint NOT NULL,
    pli_codidi character varying(2) NOT NULL,
    pli_nomfic character varying(100) NOT NULL
);




--
-- TOC entry 2016 (class 0 OID 0)
-- Dependencies: 162
-- Name: COLUMN rds_plaidi.pli_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_plaidi.pli_codigo IS 'CODIGO';


--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 162
-- Name: COLUMN rds_plaidi.pli_codpla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_plaidi.pli_codpla IS 'CODIGO PLANTILLA';


--
-- TOC entry 2018 (class 0 OID 0)
-- Dependencies: 162
-- Name: COLUMN rds_plaidi.pli_codidi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_plaidi.pli_codidi IS 'CODIGO DE IDIOMA';


--
-- TOC entry 163 (class 1259 OID 188579)
-- Dependencies: 1884 1885 1886 3
-- Name: rds_planti; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_planti (
    pla_codigo bigint NOT NULL,
    pla_codver bigint NOT NULL,
    pla_tipo character varying(3) NOT NULL,
    pla_format bigint NOT NULL,
    pla_defect character varying(1) DEFAULT 'N'::character varying NOT NULL,
    pla_barcod character varying(1) DEFAULT 'N'::character varying NOT NULL,
    pla_sello character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2019 (class 0 OID 0)
-- Dependencies: 163
-- Name: TABLE rds_planti; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_planti IS 'PLANTILLAS PARA DOCUMENTOS ESTRUCTURADOS';


--
-- TOC entry 2020 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_codigo IS 'CODIGO';


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_codver; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_codver IS 'CODIGO VERSIÓN MODELO';


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_tipo IS 'TIPO PLANTILLA: HTM / PDF';


--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_format; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_format IS 'FORMATEADOR: Clase que va a realizar la transformación a partir de la plantilla';


--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_defect; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_defect IS 'DEFECTO: Indica si es la plantilla por defecto';


--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_barcod; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_barcod IS 'BARCODE: Indica si se debe establecer un codigo de barras con url de validacion';


--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 163
-- Name: COLUMN rds_planti.pla_sello; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_planti.pla_sello IS 'SELLO: Indica si se debe stampar sello preregistro/registro (se mirara en los usos del documento)';


--
-- TOC entry 142 (class 1259 OID 188484)
-- Dependencies: 3
-- Name: rds_seqapl; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqapl
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 143 (class 1259 OID 188486)
-- Dependencies: 3
-- Name: rds_seqdoc; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqdoc
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 144 (class 1259 OID 188488)
-- Dependencies: 3
-- Name: rds_seqfir; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqfir
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 145 (class 1259 OID 188490)
-- Dependencies: 3
-- Name: rds_seqfor; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqfor
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 171 (class 1259 OID 188711)
-- Dependencies: 3
-- Name: rds_seqlgd; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqlgd
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 146 (class 1259 OID 188492)
-- Dependencies: 3
-- Name: rds_seqlog; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqlog
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 147 (class 1259 OID 188494)
-- Dependencies: 3
-- Name: rds_seqmod; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqmod
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 148 (class 1259 OID 188496)
-- Dependencies: 3
-- Name: rds_seqpla; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqpla
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 149 (class 1259 OID 188498)
-- Dependencies: 3
-- Name: rds_seqpli; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqpli
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 150 (class 1259 OID 188500)
-- Dependencies: 3
-- Name: rds_seqtiu; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqtiu
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 151 (class 1259 OID 188502)
-- Dependencies: 3
-- Name: rds_sequbi; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_sequbi
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 152 (class 1259 OID 188504)
-- Dependencies: 3
-- Name: rds_sequso; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_sequso
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 153 (class 1259 OID 188506)
-- Dependencies: 3
-- Name: rds_seqver; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rds_seqver
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 164 (class 1259 OID 188589)
-- Dependencies: 3
-- Name: rds_tioper; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_tioper (
    top_codigo character varying(4) NOT NULL,
    top_nombre character varying(50) NOT NULL
);




--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 164
-- Name: TABLE rds_tioper; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_tioper IS 'TIPOS DE OPERACIONES PERMITIDAS EN EL RDS: 
 - CREAR DOCUMENTO
 - ASOCIAR FICHERO
 - CREAR USO
 - ....
';


--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN rds_tioper.top_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_tioper.top_codigo IS 'CODIGO OPERACIÓN';


--
-- TOC entry 2029 (class 0 OID 0)
-- Dependencies: 164
-- Name: COLUMN rds_tioper.top_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_tioper.top_nombre IS 'NOMBRE OPERACIÓN';


--
-- TOC entry 165 (class 1259 OID 188594)
-- Dependencies: 3
-- Name: rds_tiuso; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_tiuso (
    tiu_codigo character varying(3) NOT NULL,
    tiu_nombre character varying(50) NOT NULL
);




--
-- TOC entry 2030 (class 0 OID 0)
-- Dependencies: 165
-- Name: TABLE rds_tiuso; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_tiuso IS 'TIPOS DE USO: 
 - PAD: PAD
 - TRA: IDENTIFICADOR TRAMITE PERSISTENTE
 - RDS: REPOSITORIO DE DOCUMENTOS SEGURO
 - RTE: REGISTRO TELEMATICO DE ENTRADA
 - RTS: REGISTRO TELEMATICO DE SALIDA
 - EXP: EXPEDIENTE
 - BTE: BANDEJA TELEMÁTICA DE ENTRADA
 - PRE: PREREGISTRO
 - ENV: ENVIO
';


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 165
-- Name: COLUMN rds_tiuso.tiu_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_tiuso.tiu_codigo IS 'CODIGO TIPO USO';


--
-- TOC entry 2032 (class 0 OID 0)
-- Dependencies: 165
-- Name: COLUMN rds_tiuso.tiu_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_tiuso.tiu_nombre IS 'NOMBRE DEL USO';


--
-- TOC entry 166 (class 1259 OID 188599)
-- Dependencies: 1887 3
-- Name: rds_ubica; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_ubica (
    ubi_codigo bigint NOT NULL,
    ubi_ident character varying(5) NOT NULL,
    ubi_nombre character varying(50) NOT NULL,
    ubi_plugin character varying(500) NOT NULL,
    ubi_defect character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 2033 (class 0 OID 0)
-- Dependencies: 166
-- Name: TABLE rds_ubica; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_ubica IS 'UBICACIÓN DE LOS DOCUMENTOS';


--
-- TOC entry 2034 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN rds_ubica.ubi_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ubica.ubi_codigo IS 'CODIGO';


--
-- TOC entry 2035 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN rds_ubica.ubi_ident; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ubica.ubi_ident IS 'IDENTIFICADOR UBICACIÓN';


--
-- TOC entry 2036 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN rds_ubica.ubi_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ubica.ubi_nombre IS 'NOMBRE UBICACIÓN';


--
-- TOC entry 2037 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN rds_ubica.ubi_plugin; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ubica.ubi_plugin IS 'IDENTIFICA EL PLUGIN QUE IMPLEMENTA EL ALMACENAMIENTO';


--
-- TOC entry 2038 (class 0 OID 0)
-- Dependencies: 166
-- Name: COLUMN rds_ubica.ubi_defect; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_ubica.ubi_defect IS 'INDICA SI ES LA UBICACION POR DEFECTO (S/N)';


--
-- TOC entry 167 (class 1259 OID 188610)
-- Dependencies: 3
-- Name: rds_usos; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_usos (
    uso_codigo bigint NOT NULL,
    uso_codtiu character varying(3) NOT NULL,
    uso_coddoc bigint NOT NULL,
    uso_fecha timestamp with time zone NOT NULL,
    uso_ref character varying(100) NOT NULL,
    uso_fcsell timestamp with time zone
);




--
-- TOC entry 2039 (class 0 OID 0)
-- Dependencies: 167
-- Name: TABLE rds_usos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_usos IS 'USOS DEL DOCUMENTO';


--
-- TOC entry 2040 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN rds_usos.uso_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_usos.uso_codigo IS 'CODIGO';


--
-- TOC entry 2041 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN rds_usos.uso_codtiu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_usos.uso_codtiu IS 'CODIGO TIPO USO';


--
-- TOC entry 2042 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN rds_usos.uso_coddoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_usos.uso_coddoc IS 'CODIGO';


--
-- TOC entry 2043 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN rds_usos.uso_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_usos.uso_fecha IS 'FECHA ALTA USO. PROPORCIONADA POR RDS';


--
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN rds_usos.uso_ref; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_usos.uso_ref IS 'REFERENCIA PROPORCIONADA SEGÚN TIPO DE USO: RTE/TRS -> NUM.REGISTRO, EXP -> NUM EXPEDIENTE, TRA -> ID. PERSISTENCIA, ...';


--
-- TOC entry 2045 (class 0 OID 0)
-- Dependencies: 167
-- Name: COLUMN rds_usos.uso_fcsell; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_usos.uso_fcsell IS 'PARA TIPO DE USO QUE PERMITEN ESTABLECER SELLO INDICA LA FECHA DE SELLO';


--
-- TOC entry 169 (class 1259 OID 188688)
-- Dependencies: 3
-- Name: rds_vercus; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_vercus (
    cus_codigo character varying(100) NOT NULL,
    cus_coddoc bigint NOT NULL,
    cus_fecha timestamp with time zone NOT NULL,
    cus_borrar character varying(1) NOT NULL
);




--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 169
-- Name: TABLE rds_vercus; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_vercus IS 'VERSIONES DE LA CUSTODIA DE UN DOCUMENTO FIRMADO';


--
-- TOC entry 2047 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN rds_vercus.cus_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vercus.cus_codigo IS 'CODIGO DEL DOCUMENTO EN LA CUSTODIA ';


--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN rds_vercus.cus_coddoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vercus.cus_coddoc IS 'CODIGO DEL DOCUMENTO';


--
-- TOC entry 2049 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN rds_vercus.cus_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vercus.cus_fecha IS 'FECHA EN LA QUE SE REALIZA LA CUSTODIA';


--
-- TOC entry 2050 (class 0 OID 0)
-- Dependencies: 169
-- Name: COLUMN rds_vercus.cus_borrar; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vercus.cus_borrar IS 'INDICA SI EL DOCUMENTO DEBE SER BORRADO DE CUSTODIA (S/N)';


--
-- TOC entry 168 (class 1259 OID 188616)
-- Dependencies: 3
-- Name: rds_vers; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rds_vers (
    ver_codigo bigint NOT NULL,
    ver_codmod bigint NOT NULL,
    ver_versio bigint NOT NULL,
    ver_desc character varying(100)
);




--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 168
-- Name: TABLE rds_vers; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rds_vers IS 'VERSIONES DE MODELOS';


--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN rds_vers.ver_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vers.ver_codigo IS 'CODIGO';


--
-- TOC entry 2053 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN rds_vers.ver_codmod; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vers.ver_codmod IS 'CODIGO DEL MODELO VERSIONADO';


--
-- TOC entry 2054 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN rds_vers.ver_versio; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vers.ver_versio IS 'IDENTIFICADOR VERSIÓN';


--
-- TOC entry 2055 (class 0 OID 0)
-- Dependencies: 168
-- Name: COLUMN rds_vers.ver_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rds_vers.ver_desc IS 'DESCRIPCIÓN VERSIÓN';


--
-- TOC entry 140 (class 1259 OID 188475)
-- Dependencies: 3
-- Name: sc_wl_usuari; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE sc_wl_usuari (
    usu_codi character varying(200) NOT NULL,
    usu_pass character varying(50),
    usu_nom character varying(50),
    usu_nif character varying(10)
);




--
-- TOC entry 141 (class 1259 OID 188478)
-- Dependencies: 3
-- Name: sc_wl_usugru; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE sc_wl_usugru (
    ugr_codusu character varying(200),
    ugr_codgru character varying(50)
);




--
-- TOC entry 1902 (class 2606 OID 188548)
-- Dependencies: 158 158
-- Name: pk_rds_format; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_format
    ADD CONSTRAINT pk_rds_format PRIMARY KEY (for_id);


--
-- TOC entry 1893 (class 2606 OID 188515)
-- Dependencies: 154 154
-- Name: rds_arp_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_arcpli
    ADD CONSTRAINT rds_arp_pk PRIMARY KEY (arp_codpli);


--
-- TOC entry 1933 (class 2606 OID 188692)
-- Dependencies: 169 169
-- Name: rds_cus_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_vercus
    ADD CONSTRAINT rds_cus_pk PRIMARY KEY (cus_codigo);


--
-- TOC entry 1895 (class 2606 OID 188523)
-- Dependencies: 155 155
-- Name: rds_doc_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_docum
    ADD CONSTRAINT rds_doc_pk PRIMARY KEY (doc_codigo);


--
-- TOC entry 1897 (class 2606 OID 188531)
-- Dependencies: 156 156
-- Name: rds_fic_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_ficher
    ADD CONSTRAINT rds_fic_pk PRIMARY KEY (fic_codigo);


--
-- TOC entry 1937 (class 2606 OID 188721)
-- Dependencies: 172 172
-- Name: rds_fie_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_ficext
    ADD CONSTRAINT rds_fie_pk PRIMARY KEY (fie_refdoc);


--
-- TOC entry 1899 (class 2606 OID 188539)
-- Dependencies: 157 157
-- Name: rds_fir_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_firmas
    ADD CONSTRAINT rds_fir_pk PRIMARY KEY (fir_codigo);


--
-- TOC entry 1904 (class 2606 OID 188553)
-- Dependencies: 159 159
-- Name: rds_idi_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_idioma
    ADD CONSTRAINT rds_idi_pk PRIMARY KEY (idi_codigo);


--
-- TOC entry 1906 (class 2606 OID 188561)
-- Dependencies: 160 160
-- Name: rds_log_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_logope
    ADD CONSTRAINT rds_log_pk PRIMARY KEY (log_codigo);


--
-- TOC entry 1935 (class 2606 OID 188705)
-- Dependencies: 170 170
-- Name: rds_logegd_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_logegd
    ADD CONSTRAINT rds_logegd_pk PRIMARY KEY (log_codigo);


--
-- TOC entry 1908 (class 2606 OID 188571)
-- Dependencies: 161 161
-- Name: rds_mod_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_modelo
    ADD CONSTRAINT rds_mod_pk PRIMARY KEY (mod_codigo);


--
-- TOC entry 1910 (class 2606 OID 188573)
-- Dependencies: 161 161
-- Name: rds_modmod_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_modelo
    ADD CONSTRAINT rds_modmod_uni UNIQUE (mod_modelo);


--
-- TOC entry 1914 (class 2606 OID 188588)
-- Dependencies: 163 163
-- Name: rds_pla_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_planti
    ADD CONSTRAINT rds_pla_pk PRIMARY KEY (pla_codigo);


--
-- TOC entry 1916 (class 2606 OID 188586)
-- Dependencies: 163 163 163
-- Name: rds_platip_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_planti
    ADD CONSTRAINT rds_platip_uni UNIQUE (pla_codver, pla_tipo);


--
-- TOC entry 1912 (class 2606 OID 188578)
-- Dependencies: 162 162
-- Name: rds_pli_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_plaidi
    ADD CONSTRAINT rds_pli_pk PRIMARY KEY (pli_codigo);


--
-- TOC entry 1920 (class 2606 OID 188598)
-- Dependencies: 165 165
-- Name: rds_tiu_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_tiuso
    ADD CONSTRAINT rds_tiu_pk PRIMARY KEY (tiu_codigo);


--
-- TOC entry 1918 (class 2606 OID 188593)
-- Dependencies: 164 164
-- Name: rds_top_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_tioper
    ADD CONSTRAINT rds_top_pk PRIMARY KEY (top_codigo);


--
-- TOC entry 1922 (class 2606 OID 188609)
-- Dependencies: 166 166
-- Name: rds_ubi_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_ubica
    ADD CONSTRAINT rds_ubi_pk PRIMARY KEY (ubi_codigo);


--
-- TOC entry 1924 (class 2606 OID 188607)
-- Dependencies: 166 166
-- Name: rds_ubiide_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_ubica
    ADD CONSTRAINT rds_ubiide_uni UNIQUE (ubi_ident);


--
-- TOC entry 1926 (class 2606 OID 188614)
-- Dependencies: 167 167
-- Name: rds_uso_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_usos
    ADD CONSTRAINT rds_uso_pk PRIMARY KEY (uso_codigo);


--
-- TOC entry 1929 (class 2606 OID 188622)
-- Dependencies: 168 168
-- Name: rds_ver_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_vers
    ADD CONSTRAINT rds_ver_pk PRIMARY KEY (ver_codigo);


--
-- TOC entry 1931 (class 2606 OID 188620)
-- Dependencies: 168 168 168
-- Name: rds_vermod_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rds_vers
    ADD CONSTRAINT rds_vermod_uni UNIQUE (ver_codmod, ver_versio);


--
-- TOC entry 1891 (class 2606 OID 188483)
-- Dependencies: 140 140
-- Name: usuari_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY sc_wl_usuari
    ADD CONSTRAINT usuari_pk PRIMARY KEY (usu_codi);


--
-- TOC entry 1889 (class 1259 OID 188481)
-- Dependencies: 140
-- Name: dni_ak; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE UNIQUE INDEX dni_ak ON sc_wl_usuari USING btree (usu_nif);


--
-- TOC entry 1938 (class 1259 OID 188722)
-- Dependencies: 172
-- Name: rds_fiedoc_fk_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX rds_fiedoc_fk_i ON rds_ficext USING btree (fie_coddoc);


--
-- TOC entry 1900 (class 1259 OID 188540)
-- Dependencies: 157
-- Name: rds_firdoc_fk_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX rds_firdoc_fk_i ON rds_firmas USING btree (fir_coddoc);


--
-- TOC entry 1927 (class 1259 OID 188615)
-- Dependencies: 167
-- Name: rds_usodoc_ind; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX rds_usodoc_ind ON rds_usos USING btree (uso_coddoc);


--
-- TOC entry 1939 (class 2606 OID 188623)
-- Dependencies: 162 1911 154
-- Name: rds_arppli_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_arcpli
    ADD CONSTRAINT rds_arppli_fk FOREIGN KEY (arp_codpli) REFERENCES rds_plaidi(pli_codigo);


--
-- TOC entry 1952 (class 2606 OID 188693)
-- Dependencies: 169 1894 155
-- Name: rds_cusdoc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_vercus
    ADD CONSTRAINT rds_cusdoc_fk FOREIGN KEY (cus_coddoc) REFERENCES rds_docum(doc_codigo);


--
-- TOC entry 1940 (class 2606 OID 188628)
-- Dependencies: 163 155 1913
-- Name: rds_docpla_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_docum
    ADD CONSTRAINT rds_docpla_fk FOREIGN KEY (doc_codpla) REFERENCES rds_planti(pla_codigo);


--
-- TOC entry 1941 (class 2606 OID 188633)
-- Dependencies: 155 166 1921
-- Name: rds_docubi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_docum
    ADD CONSTRAINT rds_docubi_fk FOREIGN KEY (doc_codubi) REFERENCES rds_ubica(ubi_codigo);


--
-- TOC entry 1942 (class 2606 OID 188638)
-- Dependencies: 1928 155 168
-- Name: rds_ficver_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_docum
    ADD CONSTRAINT rds_ficver_fk FOREIGN KEY (doc_codver) REFERENCES rds_vers(ver_codigo);


--
-- TOC entry 1943 (class 2606 OID 188643)
-- Dependencies: 157 1894 155
-- Name: rds_firdoc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_firmas
    ADD CONSTRAINT rds_firdoc_fk FOREIGN KEY (fir_coddoc) REFERENCES rds_docum(doc_codigo);


--
-- TOC entry 1953 (class 2606 OID 188706)
-- Dependencies: 155 170 1894
-- Name: rds_logegd_log_coddoc_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_logegd
    ADD CONSTRAINT rds_logegd_log_coddoc_fkey FOREIGN KEY (log_coddoc) REFERENCES rds_docum(doc_codigo);


--
-- TOC entry 1944 (class 2606 OID 188648)
-- Dependencies: 160 1917 164
-- Name: rds_logtop_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_logope
    ADD CONSTRAINT rds_logtop_fk FOREIGN KEY (log_codtop) REFERENCES rds_tioper(top_codigo);


--
-- TOC entry 1947 (class 2606 OID 188663)
-- Dependencies: 158 1901 163
-- Name: rds_plafor_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_planti
    ADD CONSTRAINT rds_plafor_fk FOREIGN KEY (pla_format) REFERENCES rds_format(for_id);


--
-- TOC entry 1948 (class 2606 OID 188668)
-- Dependencies: 163 168 1928
-- Name: rds_plaver_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_planti
    ADD CONSTRAINT rds_plaver_fk FOREIGN KEY (pla_codver) REFERENCES rds_vers(ver_codigo);


--
-- TOC entry 1945 (class 2606 OID 188653)
-- Dependencies: 162 159 1903
-- Name: rds_pliidi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_plaidi
    ADD CONSTRAINT rds_pliidi_fk FOREIGN KEY (pli_codidi) REFERENCES rds_idioma(idi_codigo);


--
-- TOC entry 1946 (class 2606 OID 188658)
-- Dependencies: 163 162 1913
-- Name: rds_plipla_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_plaidi
    ADD CONSTRAINT rds_plipla_fk FOREIGN KEY (pli_codpla) REFERENCES rds_planti(pla_codigo);


--
-- TOC entry 1949 (class 2606 OID 188673)
-- Dependencies: 1894 155 167
-- Name: rds_usodoc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_usos
    ADD CONSTRAINT rds_usodoc_fk FOREIGN KEY (uso_coddoc) REFERENCES rds_docum(doc_codigo);


--
-- TOC entry 1950 (class 2606 OID 188678)
-- Dependencies: 165 167 1919
-- Name: rds_usotiu_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_usos
    ADD CONSTRAINT rds_usotiu_fk FOREIGN KEY (uso_codtiu) REFERENCES rds_tiuso(tiu_codigo);


--
-- TOC entry 1951 (class 2606 OID 188683)
-- Dependencies: 1907 168 161
-- Name: rds_vermod_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rds_vers
    ADD CONSTRAINT rds_vermod_fk FOREIGN KEY (ver_codmod) REFERENCES rds_modelo(mod_codigo);


--
-- TOC entry 1957 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-01-27 09:17:29

--
-- PostgreSQL database dump complete
--

-- V2.3.9
create index RDS_USODOC_IDX on RDS_USOS (
   USO_CODTIU ASC,
   USO_REF ASC
);

-- V3.0.2
alter table RDS_DOCUM add DOC_CSV character varying(50);
comment on column RDS_DOCUM.DOC_CSV is 'CSV';

CREATE UNIQUE INDEX RDS_DOC_AK ON RDS_DOCUM USING btree (DOC_CSV);

create table RDS_TABCSV  (
   TCS_CODIGO          character varying((1)                     not null,
   TCS_CLAVES          bytea                   not null
);

comment on table RDS_TABCSV is
'TABLA TRANSFORMACION CSV';

comment on column RDS_TABCSV.TCS_CODIGO is
'CODIGO';

comment on column RDS_TABCSV.TCS_CLAVES is
'CLAVES';

alter table RDS_TABCSV
   add constraint RDS_TCS_PK primary key (TCS_CODIGO);

