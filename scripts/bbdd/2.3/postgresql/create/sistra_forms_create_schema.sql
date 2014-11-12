--
-- PostgreSQL database dump
--

--
-- TOC entry 156 (class 1259 OID 188010)
-- Dependencies: 3
-- Name: rfr_archiv; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_archiv (
    arc_codi bigint NOT NULL,
    arc_nombre character varying(128) NOT NULL,
    arc_mime character varying(128) NOT NULL,
    arc_pesob bigint NOT NULL,
    arc_datos bytea
);




--
-- TOC entry 157 (class 1259 OID 188018)
-- Dependencies: 3
-- Name: rfr_ayupan; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_ayupan (
    ayu_codi bigint NOT NULL,
    ayu_codpan bigint NOT NULL,
    ayu_codper bigint NOT NULL
);




--
-- TOC entry 158 (class 1259 OID 188023)
-- Dependencies: 1953 1954 1955 1956 1957 1958 1959 3
-- Name: rfr_compon; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_compon (
    com_codi bigint NOT NULL,
    com_type character varying(128) NOT NULL,
    com_codpan bigint,
    com_codpal bigint,
    com_nomlog character varying(128) NOT NULL,
    com_orden bigint NOT NULL,
    com_posici bigint NOT NULL,
    com_estilo character varying(128),
    com_etipdf character varying(128),
    com_numero bigint,
    com_oculto bigint,
    com_expaur character varying(4000),
    com_expaut character varying(4000),
    com_expdep character varying(4000),
    com_expval character varying(4000),
    com_expvpo character varying(4000),
    com_exppos character varying(4000),
    com_tipval character varying(128),
    com_codpat bigint,
    com_filas bigint,
    com_column bigint,
    com_multil bigint,
    com_obliga bigint,
    com_altura bigint,
    com_valdef bigint,
    com_maxsiz bigint,
    com_multif bigint,
    com_selmul bigint,
    com_extree bigint,
    com_mostab bigint DEFAULT 0 NOT NULL,
    com_anccol bigint,
    com_ancmax bigint DEFAULT 0 NOT NULL,
    com_colspn bigint DEFAULT 1 NOT NULL,
    com_sinetq bigint DEFAULT 0 NOT NULL,
    com_cuadro bigint DEFAULT 0 NOT NULL,
    com_cuacab bigint DEFAULT 0 NOT NULL,
    com_alineacion character varying(1) DEFAULT 'I'::character varying NOT NULL,
    com_seclet character varying(2),
    com_txtipo character varying(2),
    COM_LBLTIPO VARCHAR(2) default 'NO' not null,
    COM_ORIENT  VARCHAR(1) default 'H' not null,
    COM_LDEIND bigint default 0 not null,
    TRC_PLACEH VARCHAR(100)    
);




--
-- TOC entry 159 (class 1259 OID 188033)
-- Dependencies: 1960 3
-- Name: rfr_formul; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_formul (
    for_codi bigint NOT NULL,
    for_modelo character varying(3) NOT NULL,
    for_ulnuse bigint NOT NULL,
    for_urlen1 character varying(256),
    for_urlen2 character varying(256),
    for_hasbco bigint NOT NULL,
    for_bcodex bigint,
    for_bcodey bigint,
    for_dtd bigint,
    for_logti1 bigint,
    for_logti2 bigint,
    for_verfun bigint DEFAULT 0 NOT NULL,
    for_esbloq bigint NOT NULL,
    for_mtvblq character varying(2048),
    for_versio bigint NOT NULL,
    for_lastve bigint NOT NULL,
    for_tagcar character varying(100),
    for_feccar date
);




--
-- TOC entry 2075 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN rfr_formul.for_verfun; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_formul.for_verfun IS 'Codigo de version';


--
-- TOC entry 160 (class 1259 OID 188044)
-- Dependencies: 3
-- Name: rfr_forseg; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_forseg (
    fsg_codi bigint NOT NULL,
    fsg_https bigint NOT NULL,
    fsg_reqlog bigint NOT NULL,
    fsg_reqcer bigint NOT NULL,
    fsg_reqfir bigint NOT NULL
);




--
-- TOC entry 161 (class 1259 OID 188049)
-- Dependencies: 3
-- Name: rfr_fsgrol; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_fsgrol (
    fsr_codfsg bigint NOT NULL,
    fsr_rol character varying(128) NOT NULL
);




--
-- TOC entry 162 (class 1259 OID 188054)
-- Dependencies: 3
-- Name: rfr_fsgvfi; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_fsgvfi (
    fsv_codfsg bigint NOT NULL,
    fsv_codvfi bigint NOT NULL
);




--
-- TOC entry 188 (class 1259 OID 188406)
-- Dependencies: 3
-- Name: rfr_grpfor; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_grpfor (
    grf_codgrp character varying(50) NOT NULL,
    grf_codfor bigint NOT NULL
);




--
-- TOC entry 2076 (class 0 OID 0)
-- Dependencies: 188
-- Name: TABLE rfr_grpfor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rfr_grpfor IS 'Formularios a los que el grupo tiene acceso';


--
-- TOC entry 2077 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN rfr_grpfor.grf_codgrp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grpfor.grf_codgrp IS 'CODIGO GRUPO';


--
-- TOC entry 2078 (class 0 OID 0)
-- Dependencies: 188
-- Name: COLUMN rfr_grpfor.grf_codfor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grpfor.grf_codfor IS 'CODIGO FORMULARIO';


--
-- TOC entry 187 (class 1259 OID 188396)
-- Dependencies: 3
-- Name: rfr_grpusu; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_grpusu (
    gru_codgrp character varying(50) NOT NULL,
    gru_codusu character varying(200) NOT NULL
);




--
-- TOC entry 2079 (class 0 OID 0)
-- Dependencies: 187
-- Name: TABLE rfr_grpusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rfr_grpusu IS 'Asignación de usuarios a grupos';


--
-- TOC entry 2080 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN rfr_grpusu.gru_codgrp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grpusu.gru_codgrp IS 'CODIGO GRUPO';


--
-- TOC entry 2081 (class 0 OID 0)
-- Dependencies: 187
-- Name: COLUMN rfr_grpusu.gru_codusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grpusu.gru_codusu IS 'CODIGO USUARIO';


--
-- TOC entry 186 (class 1259 OID 188391)
-- Dependencies: 3
-- Name: rfr_grupos; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_grupos (
    grp_codigo character varying(50) NOT NULL,
    grp_nombre character varying(100) NOT NULL,
    grp_descp character varying(300)
);




--
-- TOC entry 2082 (class 0 OID 0)
-- Dependencies: 186
-- Name: TABLE rfr_grupos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rfr_grupos IS 'Grupos de usuarios para establecer permisos de acceso a formularios';


--
-- TOC entry 2083 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN rfr_grupos.grp_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grupos.grp_codigo IS 'Código grupo';


--
-- TOC entry 2084 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN rfr_grupos.grp_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grupos.grp_nombre IS 'Nombre grupo';


--
-- TOC entry 2085 (class 0 OID 0)
-- Dependencies: 186
-- Name: COLUMN rfr_grupos.grp_descp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_grupos.grp_descp IS 'Descripción grupo';


--
-- TOC entry 163 (class 1259 OID 188059)
-- Dependencies: 3
-- Name: rfr_idioma; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_idioma (
    idi_codi character varying(2) NOT NULL,
    idi_orden bigint NOT NULL
);




--
-- TOC entry 164 (class 1259 OID 188064)
-- Dependencies: 3
-- Name: rfr_mascar; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_mascar (
    mas_codi bigint NOT NULL,
    mas_nombre character varying(128),
    mas_descri character varying(4000),
    mas_variab bytea
);




--
-- TOC entry 165 (class 1259 OID 188072)
-- Dependencies: 3
-- Name: rfr_masvar; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_masvar (
    mva_codmas bigint NOT NULL,
    mva_valor character varying(255),
    mva_orden bigint NOT NULL
);




--
-- TOC entry 166 (class 1259 OID 188077)
-- Dependencies: 3
-- Name: rfr_paleta; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_paleta (
    pal_codi bigint NOT NULL,
    pal_nombre character varying(128) NOT NULL
);




--
-- TOC entry 167 (class 1259 OID 188082)
-- Dependencies: 3
-- Name: rfr_pantal; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_pantal (
    pan_codi bigint NOT NULL,
    pan_nombre character varying(128) NOT NULL,
    pan_orden bigint NOT NULL,
    pan_expres character varying(4000),
    pan_ultima bigint,
    pan_inicia bigint,
    pan_codfor bigint NOT NULL,
    pan_detall character varying(300)
);




--
-- TOC entry 168 (class 1259 OID 188090)
-- Dependencies: 3
-- Name: rfr_patron; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_patron (
    pat_codi bigint NOT NULL,
    pat_nombre character varying(128) NOT NULL,
    pat_descri character varying(4000),
    pat_ejecut bigint NOT NULL,
    pat_codigo character varying(4000)
);




--
-- TOC entry 169 (class 1259 OID 188100)
-- Dependencies: 3
-- Name: rfr_perusu; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_perusu (
    per_codi bigint NOT NULL,
    per_codest character varying(10) NOT NULL,
    per_patico character varying(1024) NOT NULL
);




--
-- TOC entry 170 (class 1259 OID 188108)
-- Dependencies: 3
-- Name: rfr_prosal; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_prosal (
    prs_codi bigint NOT NULL,
    prs_nombre character varying(128) NOT NULL,
    prs_valor character varying(4000),
    prs_expres bigint,
    prs_codsal bigint NOT NULL,
    prs_codpla bigint
);




--
-- TOC entry 171 (class 1259 OID 188116)
-- Dependencies: 3
-- Name: rfr_punsal; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_punsal (
    psa_codi bigint NOT NULL,
    psa_nombre character varying(128) NOT NULL,
    psa_implem character varying(1024)
);




--
-- TOC entry 172 (class 1259 OID 188124)
-- Dependencies: 3
-- Name: rfr_salida; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_salida (
    sal_codi bigint NOT NULL,
    sal_orden bigint NOT NULL,
    sal_codpsa bigint NOT NULL,
    sal_codfor bigint NOT NULL
);




--
-- TOC entry 140 (class 1259 OID 187978)
-- Dependencies: 3
-- Name: rfr_secarc; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secarc
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 141 (class 1259 OID 187980)
-- Dependencies: 3
-- Name: rfr_secayu; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secayu
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 142 (class 1259 OID 187982)
-- Dependencies: 3
-- Name: rfr_seccom; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_seccom
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 143 (class 1259 OID 187984)
-- Dependencies: 3
-- Name: rfr_secfor; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secfor
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 144 (class 1259 OID 187986)
-- Dependencies: 3
-- Name: rfr_secmas; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secmas
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 145 (class 1259 OID 187988)
-- Dependencies: 3
-- Name: rfr_secpal; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secpal
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 146 (class 1259 OID 187990)
-- Dependencies: 3
-- Name: rfr_secpan; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secpan
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 147 (class 1259 OID 187992)
-- Dependencies: 3
-- Name: rfr_secpat; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secpat
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 148 (class 1259 OID 187994)
-- Dependencies: 3
-- Name: rfr_secper; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secper
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 149 (class 1259 OID 187996)
-- Dependencies: 3
-- Name: rfr_secprs; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secprs
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 150 (class 1259 OID 187998)
-- Dependencies: 3
-- Name: rfr_secpsa; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secpsa
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 151 (class 1259 OID 188000)
-- Dependencies: 3
-- Name: rfr_secsal; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secsal
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 152 (class 1259 OID 188002)
-- Dependencies: 3
-- Name: rfr_sectnf; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_sectnf
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 153 (class 1259 OID 188004)
-- Dependencies: 3
-- Name: rfr_secvap; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secvap
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 154 (class 1259 OID 188006)
-- Dependencies: 3
-- Name: rfr_secvfi; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_secvfi
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 155 (class 1259 OID 188008)
-- Dependencies: 3
-- Name: rfr_seqval; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE rfr_seqval
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 173 (class 1259 OID 188129)
-- Dependencies: 3
-- Name: rfr_tracam; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_tracam (
    trc_codcam bigint NOT NULL,
    trc_nombre character varying(256) NOT NULL,
    trc_ayuda character varying(4000),
    trc_codidi character varying(2) NOT NULL,
    trc_menval character varying(256)
);




--
-- TOC entry 174 (class 1259 OID 188137)
-- Dependencies: 3
-- Name: rfr_trafor; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_trafor (
    trf_codfor bigint NOT NULL,
    trf_titulo character varying(256) NOT NULL,
    trf_descri character varying(4000),
    trf_nomen1 character varying(256),
    trf_nomen2 character varying(256),
    trf_planti bigint,
    trf_codidi character varying(2) NOT NULL
);




--
-- TOC entry 175 (class 1259 OID 188145)
-- Dependencies: 3
-- Name: rfr_tralab; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_tralab (
    trl_codlab bigint NOT NULL,
    trl_etique character varying(4000) NOT NULL,
    trl_codidi character varying(2) NOT NULL
);




--
-- TOC entry 176 (class 1259 OID 188153)
-- Dependencies: 3
-- Name: rfr_transf; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_transf (
    tnf_codi bigint NOT NULL,
    tnf_codpan bigint NOT NULL,
    tnf_codper bigint NOT NULL,
    tnf_xslt text
);




--
-- TOC entry 177 (class 1259 OID 188161)
-- Dependencies: 3
-- Name: rfr_trapan; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_trapan (
    trp_codpan bigint NOT NULL,
    trp_titulo character varying(256) NOT NULL,
    trp_codidi character varying(2) NOT NULL
);




--
-- TOC entry 189 (class 1259 OID 188462)
-- Dependencies: 3
-- Name: rfr_trasec; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_trasec (
    trs_codsec bigint NOT NULL,
    trs_codidi character varying(2) NOT NULL,
    trs_etique character varying(4000) NOT NULL
);




--
-- TOC entry 2086 (class 0 OID 0)
-- Dependencies: 189
-- Name: TABLE rfr_trasec; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rfr_trasec IS 'TRADUCCION COMPONENTE SECCION';


--
-- TOC entry 178 (class 1259 OID 188166)
-- Dependencies: 3
-- Name: rfr_traypa; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_traypa (
    tra_codayu bigint NOT NULL,
    tra_descor character varying(1024),
    tra_deslar character varying(4000),
    tra_urlweb character varying(1024),
    tra_urlvid character varying(1024),
    tra_urlson character varying(1024),
    tra_codidi character varying(2) NOT NULL
);




--
-- TOC entry 179 (class 1259 OID 188174)
-- Dependencies: 3
-- Name: rfr_trpeus; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_trpeus (
    tpe_codper bigint NOT NULL,
    tpe_nombre character varying(256) NOT NULL,
    tpe_descri character varying(4000),
    tpe_codidi character varying(2) NOT NULL
);




--
-- TOC entry 180 (class 1259 OID 188182)
-- Dependencies: 3
-- Name: rfr_trvapo; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_trvapo (
    trv_codvap bigint NOT NULL,
    trv_etique character varying(4000),
    trv_archiv bigint,
    trv_codidi character varying(2) NOT NULL
);




--
-- TOC entry 185 (class 1259 OID 188381)
-- Dependencies: 3
-- Name: rfr_usufor; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_usufor (
    ust_codusu character varying(200) NOT NULL,
    ust_codfor bigint NOT NULL
);




--
-- TOC entry 2087 (class 0 OID 0)
-- Dependencies: 185
-- Name: TABLE rfr_usufor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE rfr_usufor IS 'Permisos individuales para un usuario de acceso a formularios';


--
-- TOC entry 2088 (class 0 OID 0)
-- Dependencies: 185
-- Name: COLUMN rfr_usufor.ust_codusu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_usufor.ust_codusu IS 'CODIGO USUARIO';


--
-- TOC entry 2089 (class 0 OID 0)
-- Dependencies: 185
-- Name: COLUMN rfr_usufor.ust_codfor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_usufor.ust_codfor IS 'CODIGO FORMULARIO';


--
-- TOC entry 181 (class 1259 OID 188190)
-- Dependencies: 3
-- Name: rfr_valfir; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_valfir (
    vfi_codi bigint NOT NULL,
    vfi_nombre character varying(128) NOT NULL,
    vfi_implem character varying(1024)
);




--
-- TOC entry 182 (class 1259 OID 188198)
-- Dependencies: 3
-- Name: rfr_valida; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_valida (
    val_codi bigint NOT NULL,
    val_orden bigint NOT NULL,
    val_valore bytea,
    val_codmas bigint NOT NULL,
    val_codcam bigint NOT NULL
);




--
-- TOC entry 183 (class 1259 OID 188206)
-- Dependencies: 3
-- Name: rfr_valpos; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_valpos (
    vap_codi bigint NOT NULL,
    vap_codcam bigint,
    vap_orden bigint NOT NULL,
    vap_valor character varying(128),
    vap_defect bigint
);




--
-- TOC entry 184 (class 1259 OID 188211)
-- Dependencies: 3
-- Name: rfr_versio; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE rfr_versio (
    ver_codigo bigint NOT NULL,
    ver_nombre character varying(100) NOT NULL,
    ver_fecha date NOT NULL,
    ver_sufix character varying(10)
);




--
-- TOC entry 2090 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN rfr_versio.ver_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_versio.ver_codigo IS 'Codigo de version';


--
-- TOC entry 2091 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN rfr_versio.ver_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_versio.ver_nombre IS 'Nombre de version';


--
-- TOC entry 2092 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN rfr_versio.ver_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_versio.ver_fecha IS 'echa';


--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 184
-- Name: COLUMN rfr_versio.ver_sufix; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN rfr_versio.ver_sufix IS 'Sufijo para acceder a pantallas,actions,etc especificas';


--
-- TOC entry 1962 (class 2606 OID 188017)
-- Dependencies: 156 156
-- Name: rfr_arc_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_archiv
    ADD CONSTRAINT rfr_arc_pk PRIMARY KEY (arc_codi);


--
-- TOC entry 1964 (class 2606 OID 188022)
-- Dependencies: 157 157
-- Name: rfr_ayu_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_ayupan
    ADD CONSTRAINT rfr_ayu_pk PRIMARY KEY (ayu_codi);


--
-- TOC entry 1966 (class 2606 OID 188032)
-- Dependencies: 158 158
-- Name: rfr_com_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_compon
    ADD CONSTRAINT rfr_com_pk PRIMARY KEY (com_codi);


--
-- TOC entry 1968 (class 2606 OID 188043)
-- Dependencies: 159 159
-- Name: rfr_for_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_formul
    ADD CONSTRAINT rfr_for_pk PRIMARY KEY (for_codi);


--
-- TOC entry 1970 (class 2606 OID 188041)
-- Dependencies: 159 159 159
-- Name: rfr_for_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_formul
    ADD CONSTRAINT rfr_for_uni UNIQUE (for_modelo, for_versio);


--
-- TOC entry 1972 (class 2606 OID 188048)
-- Dependencies: 160 160
-- Name: rfr_fsg_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_forseg
    ADD CONSTRAINT rfr_fsg_pk PRIMARY KEY (fsg_codi);


--
-- TOC entry 1974 (class 2606 OID 188053)
-- Dependencies: 161 161 161
-- Name: rfr_fsr_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_fsgrol
    ADD CONSTRAINT rfr_fsr_pk PRIMARY KEY (fsr_codfsg, fsr_rol);


--
-- TOC entry 1976 (class 2606 OID 188058)
-- Dependencies: 162 162 162
-- Name: rfr_fsv_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_fsgvfi
    ADD CONSTRAINT rfr_fsv_pk PRIMARY KEY (fsv_codfsg, fsv_codvfi);


--
-- TOC entry 2030 (class 2606 OID 188410)
-- Dependencies: 188 188 188
-- Name: rfr_grf_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_grpfor
    ADD CONSTRAINT rfr_grf_pk PRIMARY KEY (grf_codgrp, grf_codfor);


--
-- TOC entry 2026 (class 2606 OID 188395)
-- Dependencies: 186 186
-- Name: rfr_grp_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_grupos
    ADD CONSTRAINT rfr_grp_pk PRIMARY KEY (grp_codigo);


--
-- TOC entry 2028 (class 2606 OID 188400)
-- Dependencies: 187 187 187
-- Name: rfr_gru_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_grpusu
    ADD CONSTRAINT rfr_gru_pk PRIMARY KEY (gru_codgrp, gru_codusu);


--
-- TOC entry 1978 (class 2606 OID 188063)
-- Dependencies: 163 163
-- Name: rfr_idi_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_idioma
    ADD CONSTRAINT rfr_idi_pk PRIMARY KEY (idi_codi);


--
-- TOC entry 1980 (class 2606 OID 188071)
-- Dependencies: 164 164
-- Name: rfr_mas_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_mascar
    ADD CONSTRAINT rfr_mas_pk PRIMARY KEY (mas_codi);


--
-- TOC entry 1982 (class 2606 OID 188076)
-- Dependencies: 165 165 165
-- Name: rfr_mva_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_masvar
    ADD CONSTRAINT rfr_mva_pk PRIMARY KEY (mva_codmas, mva_orden);


--
-- TOC entry 1984 (class 2606 OID 188081)
-- Dependencies: 166 166
-- Name: rfr_pal_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_paleta
    ADD CONSTRAINT rfr_pal_pk PRIMARY KEY (pal_codi);


--
-- TOC entry 1986 (class 2606 OID 188089)
-- Dependencies: 167 167
-- Name: rfr_pan_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_pantal
    ADD CONSTRAINT rfr_pan_pk PRIMARY KEY (pan_codi);


--
-- TOC entry 1988 (class 2606 OID 188097)
-- Dependencies: 168 168
-- Name: rfr_pat_fk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_patron
    ADD CONSTRAINT rfr_pat_fk PRIMARY KEY (pat_codi);


--
-- TOC entry 1990 (class 2606 OID 188099)
-- Dependencies: 168 168
-- Name: rfr_pat_nombre_fk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_patron
    ADD CONSTRAINT rfr_pat_nombre_fk UNIQUE (pat_nombre);


--
-- TOC entry 1992 (class 2606 OID 188107)
-- Dependencies: 169 169
-- Name: rfr_per_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_perusu
    ADD CONSTRAINT rfr_per_pk PRIMARY KEY (per_codi);


--
-- TOC entry 1994 (class 2606 OID 188115)
-- Dependencies: 170 170
-- Name: rfr_prs_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_prosal
    ADD CONSTRAINT rfr_prs_pk PRIMARY KEY (prs_codi);


--
-- TOC entry 1996 (class 2606 OID 188123)
-- Dependencies: 171 171
-- Name: rfr_psa_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_punsal
    ADD CONSTRAINT rfr_psa_pk PRIMARY KEY (psa_codi);


--
-- TOC entry 1998 (class 2606 OID 188128)
-- Dependencies: 172 172
-- Name: rfr_sal_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_salida
    ADD CONSTRAINT rfr_sal_pk PRIMARY KEY (sal_codi);


--
-- TOC entry 2006 (class 2606 OID 188160)
-- Dependencies: 176 176
-- Name: rfr_tnf_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_transf
    ADD CONSTRAINT rfr_tnf_pk PRIMARY KEY (tnf_codi);


--
-- TOC entry 2012 (class 2606 OID 188181)
-- Dependencies: 179 179 179
-- Name: rfr_tpe_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_trpeus
    ADD CONSTRAINT rfr_tpe_pk PRIMARY KEY (tpe_codper, tpe_codidi);


--
-- TOC entry 2010 (class 2606 OID 188173)
-- Dependencies: 178 178 178
-- Name: rfr_tra_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_traypa
    ADD CONSTRAINT rfr_tra_pk PRIMARY KEY (tra_codayu, tra_codidi);


--
-- TOC entry 2000 (class 2606 OID 188136)
-- Dependencies: 173 173 173
-- Name: rfr_trc_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_tracam
    ADD CONSTRAINT rfr_trc_pk PRIMARY KEY (trc_codcam, trc_codidi);


--
-- TOC entry 2002 (class 2606 OID 188144)
-- Dependencies: 174 174 174
-- Name: rfr_trf_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_trafor
    ADD CONSTRAINT rfr_trf_pk PRIMARY KEY (trf_codfor, trf_codidi);


--
-- TOC entry 2004 (class 2606 OID 188152)
-- Dependencies: 175 175 175
-- Name: rfr_trl_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_tralab
    ADD CONSTRAINT rfr_trl_pk PRIMARY KEY (trl_codlab, trl_codidi);


--
-- TOC entry 2008 (class 2606 OID 188165)
-- Dependencies: 177 177 177
-- Name: rfr_trp_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_trapan
    ADD CONSTRAINT rfr_trp_pk PRIMARY KEY (trp_codpan, trp_codidi);


--
-- TOC entry 2032 (class 2606 OID 188469)
-- Dependencies: 189 189 189
-- Name: rfr_trs_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_trasec
    ADD CONSTRAINT rfr_trs_pk PRIMARY KEY (trs_codsec, trs_codidi);


--
-- TOC entry 2014 (class 2606 OID 188189)
-- Dependencies: 180 180 180
-- Name: rfr_trv_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_trvapo
    ADD CONSTRAINT rfr_trv_pk PRIMARY KEY (trv_codvap, trv_codidi);


--
-- TOC entry 2024 (class 2606 OID 188385)
-- Dependencies: 185 185 185
-- Name: rfr_ust_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_usufor
    ADD CONSTRAINT rfr_ust_pk PRIMARY KEY (ust_codusu, ust_codfor);


--
-- TOC entry 2018 (class 2606 OID 188205)
-- Dependencies: 182 182
-- Name: rfr_val_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_valida
    ADD CONSTRAINT rfr_val_pk PRIMARY KEY (val_codi);


--
-- TOC entry 2020 (class 2606 OID 188210)
-- Dependencies: 183 183
-- Name: rfr_vap_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_valpos
    ADD CONSTRAINT rfr_vap_pk PRIMARY KEY (vap_codi);


--
-- TOC entry 2022 (class 2606 OID 188215)
-- Dependencies: 184 184
-- Name: rfr_ver_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_versio
    ADD CONSTRAINT rfr_ver_pk PRIMARY KEY (ver_codigo);


--
-- TOC entry 2016 (class 2606 OID 188197)
-- Dependencies: 181 181
-- Name: rfr_vfi_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY rfr_valfir
    ADD CONSTRAINT rfr_vfi_pk PRIMARY KEY (vfi_codi);


--
-- TOC entry 2033 (class 2606 OID 188216)
-- Dependencies: 1985 167 157
-- Name: rfr_ayupan_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_ayupan
    ADD CONSTRAINT rfr_ayupan_fk FOREIGN KEY (ayu_codpan) REFERENCES rfr_pantal(pan_codi);


--
-- TOC entry 2034 (class 2606 OID 188221)
-- Dependencies: 157 1991 169
-- Name: rfr_ayuper_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_ayupan
    ADD CONSTRAINT rfr_ayuper_fk FOREIGN KEY (ayu_codper) REFERENCES rfr_perusu(per_codi);


--
-- TOC entry 2059 (class 2606 OID 188346)
-- Dependencies: 178 157 1963
-- Name: rfr_ayutra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_traypa
    ADD CONSTRAINT rfr_ayutra_fk FOREIGN KEY (tra_codayu) REFERENCES rfr_ayupan(ayu_codi);


--
-- TOC entry 2035 (class 2606 OID 188226)
-- Dependencies: 1987 168 158
-- Name: rfr_campat_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_compon
    ADD CONSTRAINT rfr_campat_fk FOREIGN KEY (com_codpat) REFERENCES rfr_patron(pat_codi);


--
-- TOC entry 2052 (class 2606 OID 188311)
-- Dependencies: 158 173 1965
-- Name: rfr_camtrc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_tracam
    ADD CONSTRAINT rfr_camtrc_fk FOREIGN KEY (trc_codcam) REFERENCES rfr_compon(com_codi);


--
-- TOC entry 2036 (class 2606 OID 188231)
-- Dependencies: 166 1983 158
-- Name: rfr_compal_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_compon
    ADD CONSTRAINT rfr_compal_fk FOREIGN KEY (com_codpal) REFERENCES rfr_paleta(pal_codi);


--
-- TOC entry 2037 (class 2606 OID 188236)
-- Dependencies: 167 158 1985
-- Name: rfr_compan_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_compon
    ADD CONSTRAINT rfr_compan_fk FOREIGN KEY (com_codpan) REFERENCES rfr_pantal(pan_codi);


--
-- TOC entry 2038 (class 2606 OID 188241)
-- Dependencies: 159 1961 156
-- Name: rfr_fordtd_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_formul
    ADD CONSTRAINT rfr_fordtd_fk FOREIGN KEY (for_dtd) REFERENCES rfr_archiv(arc_codi);


--
-- TOC entry 2039 (class 2606 OID 188246)
-- Dependencies: 156 159 1961
-- Name: rfr_forlo1_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_formul
    ADD CONSTRAINT rfr_forlo1_fk FOREIGN KEY (for_logti1) REFERENCES rfr_archiv(arc_codi);


--
-- TOC entry 2040 (class 2606 OID 188251)
-- Dependencies: 1961 156 159
-- Name: rfr_forlo2_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_formul
    ADD CONSTRAINT rfr_forlo2_fk FOREIGN KEY (for_logti2) REFERENCES rfr_archiv(arc_codi);


--
-- TOC entry 2053 (class 2606 OID 188316)
-- Dependencies: 174 159 1967
-- Name: rfr_fortrf_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trafor
    ADD CONSTRAINT rfr_fortrf_fk FOREIGN KEY (trf_codfor) REFERENCES rfr_formul(for_codi);


--
-- TOC entry 2041 (class 2606 OID 188256)
-- Dependencies: 159 184 2021
-- Name: rfr_forver_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_formul
    ADD CONSTRAINT rfr_forver_fk FOREIGN KEY (for_verfun) REFERENCES rfr_versio(ver_codigo);


--
-- TOC entry 2042 (class 2606 OID 188261)
-- Dependencies: 1967 160 159
-- Name: rfr_fsgfor_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_forseg
    ADD CONSTRAINT rfr_fsgfor_fk FOREIGN KEY (fsg_codi) REFERENCES rfr_formul(for_codi);


--
-- TOC entry 2043 (class 2606 OID 188266)
-- Dependencies: 161 160 1971
-- Name: rfr_fsrfsg_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_fsgrol
    ADD CONSTRAINT rfr_fsrfsg_fk FOREIGN KEY (fsr_codfsg) REFERENCES rfr_forseg(fsg_codi);


--
-- TOC entry 2044 (class 2606 OID 188271)
-- Dependencies: 162 1971 160
-- Name: rfr_fsvfsg_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_fsgvfi
    ADD CONSTRAINT rfr_fsvfsg_fk FOREIGN KEY (fsv_codfsg) REFERENCES rfr_forseg(fsg_codi);


--
-- TOC entry 2045 (class 2606 OID 188276)
-- Dependencies: 162 2015 181
-- Name: rfr_fsvvfi_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_fsgvfi
    ADD CONSTRAINT rfr_fsvvfi_fk FOREIGN KEY (fsv_codvfi) REFERENCES rfr_valfir(vfi_codi);


--
-- TOC entry 2068 (class 2606 OID 188411)
-- Dependencies: 1967 188 159
-- Name: rfr_grffor_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_grpfor
    ADD CONSTRAINT rfr_grffor_fk FOREIGN KEY (grf_codfor) REFERENCES rfr_formul(for_codi);


--
-- TOC entry 2069 (class 2606 OID 188416)
-- Dependencies: 186 188 2025
-- Name: rfr_grfgrp_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_grpfor
    ADD CONSTRAINT rfr_grfgrp_fk FOREIGN KEY (grf_codgrp) REFERENCES rfr_grupos(grp_codigo);


--
-- TOC entry 2067 (class 2606 OID 188401)
-- Dependencies: 186 2025 187
-- Name: rfr_grugrp_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_grpusu
    ADD CONSTRAINT rfr_grugrp_fk FOREIGN KEY (gru_codgrp) REFERENCES rfr_grupos(grp_codigo);


--
-- TOC entry 2055 (class 2606 OID 188326)
-- Dependencies: 1965 158 175
-- Name: rfr_labtra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_tralab
    ADD CONSTRAINT rfr_labtra_fk FOREIGN KEY (trl_codlab) REFERENCES rfr_compon(com_codi);


--
-- TOC entry 2046 (class 2606 OID 188281)
-- Dependencies: 1979 165 164
-- Name: rfr_masmva_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_masvar
    ADD CONSTRAINT rfr_masmva_fk FOREIGN KEY (mva_codmas) REFERENCES rfr_mascar(mas_codi);


--
-- TOC entry 2047 (class 2606 OID 188286)
-- Dependencies: 159 1967 167
-- Name: rfr_panfor_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_pantal
    ADD CONSTRAINT rfr_panfor_fk FOREIGN KEY (pan_codfor) REFERENCES rfr_formul(for_codi);


--
-- TOC entry 2058 (class 2606 OID 188341)
-- Dependencies: 177 167 1985
-- Name: rfr_pantra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trapan
    ADD CONSTRAINT rfr_pantra_fk FOREIGN KEY (trp_codpan) REFERENCES rfr_pantal(pan_codi);


--
-- TOC entry 2060 (class 2606 OID 188351)
-- Dependencies: 179 169 1991
-- Name: rfr_pertrp_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trpeus
    ADD CONSTRAINT rfr_pertrp_fk FOREIGN KEY (tpe_codper) REFERENCES rfr_perusu(per_codi);


--
-- TOC entry 2048 (class 2606 OID 188291)
-- Dependencies: 170 1961 156
-- Name: rfr_prsarc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_prosal
    ADD CONSTRAINT rfr_prsarc_fk FOREIGN KEY (prs_codpla) REFERENCES rfr_archiv(arc_codi);


--
-- TOC entry 2049 (class 2606 OID 188296)
-- Dependencies: 172 170 1997
-- Name: rfr_prssal_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_prosal
    ADD CONSTRAINT rfr_prssal_fk FOREIGN KEY (prs_codsal) REFERENCES rfr_salida(sal_codi);


--
-- TOC entry 2050 (class 2606 OID 188301)
-- Dependencies: 1967 172 159
-- Name: rfr_salfor_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_salida
    ADD CONSTRAINT rfr_salfor_fk FOREIGN KEY (sal_codfor) REFERENCES rfr_formul(for_codi);


--
-- TOC entry 2051 (class 2606 OID 188306)
-- Dependencies: 171 172 1995
-- Name: rfr_salpsa_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_salida
    ADD CONSTRAINT rfr_salpsa_fk FOREIGN KEY (sal_codpsa) REFERENCES rfr_punsal(psa_codi);


--
-- TOC entry 2070 (class 2606 OID 188470)
-- Dependencies: 158 1965 189
-- Name: rfr_sectrs_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trasec
    ADD CONSTRAINT rfr_sectrs_fk FOREIGN KEY (trs_codsec) REFERENCES rfr_compon(com_codi);


--
-- TOC entry 2056 (class 2606 OID 188331)
-- Dependencies: 176 1985 167
-- Name: rfr_tnfpan_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_transf
    ADD CONSTRAINT rfr_tnfpan_fk FOREIGN KEY (tnf_codpan) REFERENCES rfr_pantal(pan_codi);


--
-- TOC entry 2057 (class 2606 OID 188336)
-- Dependencies: 176 169 1991
-- Name: rfr_tnfper_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_transf
    ADD CONSTRAINT rfr_tnfper_fk FOREIGN KEY (tnf_codper) REFERENCES rfr_perusu(per_codi);


--
-- TOC entry 2054 (class 2606 OID 188321)
-- Dependencies: 1961 156 174
-- Name: rfr_trfarc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trafor
    ADD CONSTRAINT rfr_trfarc_fk FOREIGN KEY (trf_planti) REFERENCES rfr_archiv(arc_codi);


--
-- TOC entry 2061 (class 2606 OID 188356)
-- Dependencies: 180 156 1961
-- Name: rfr_trvarc_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trvapo
    ADD CONSTRAINT rfr_trvarc_fk FOREIGN KEY (trv_archiv) REFERENCES rfr_archiv(arc_codi);


--
-- TOC entry 2066 (class 2606 OID 188386)
-- Dependencies: 185 1967 159
-- Name: rfr_ustfor_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_usufor
    ADD CONSTRAINT rfr_ustfor_fk FOREIGN KEY (ust_codfor) REFERENCES rfr_formul(for_codi);


--
-- TOC entry 2063 (class 2606 OID 188366)
-- Dependencies: 182 158 1965
-- Name: rfr_valcam_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_valida
    ADD CONSTRAINT rfr_valcam_fk FOREIGN KEY (val_codcam) REFERENCES rfr_compon(com_codi);


--
-- TOC entry 2064 (class 2606 OID 188371)
-- Dependencies: 182 164 1979
-- Name: rfr_valmas_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_valida
    ADD CONSTRAINT rfr_valmas_fk FOREIGN KEY (val_codmas) REFERENCES rfr_mascar(mas_codi);


--
-- TOC entry 2065 (class 2606 OID 188376)
-- Dependencies: 183 158 1965
-- Name: rfr_vapcam_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_valpos
    ADD CONSTRAINT rfr_vapcam_fk FOREIGN KEY (vap_codcam) REFERENCES rfr_compon(com_codi);


--
-- TOC entry 2062 (class 2606 OID 188361)
-- Dependencies: 180 183 2019
-- Name: rfr_vaptrv_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY rfr_trvapo
    ADD CONSTRAINT rfr_vaptrv_fk FOREIGN KEY (trv_codvap) REFERENCES rfr_valpos(vap_codi);


--
-- TOC entry 2074 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-01-27 09:07:08

--
-- PostgreSQL database dump complete
--

