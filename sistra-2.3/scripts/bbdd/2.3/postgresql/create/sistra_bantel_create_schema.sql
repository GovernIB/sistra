--
-- PostgreSQL database dump
--


--
-- TOC entry 150 (class 1259 OID 187860)
-- Dependencies: 3
-- Name: bte_arcfex; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_arcfex (
    afe_idefic character varying(20) NOT NULL,
    afe_datos bytea NOT NULL
);




--
-- TOC entry 1906 (class 0 OID 0)
-- Dependencies: 150
-- Name: TABLE bte_arcfex; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_arcfex IS 'Archivo fichero exportacion';


--
-- TOC entry 1907 (class 0 OID 0)
-- Dependencies: 150
-- Name: COLUMN bte_arcfex.afe_idefic; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_arcfex.afe_idefic IS 'IDENTIFICADOR DEL TRÁMITE';


--
-- TOC entry 1908 (class 0 OID 0)
-- Dependencies: 150
-- Name: COLUMN bte_arcfex.afe_datos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_arcfex.afe_datos IS 'DATOS FICHERO';


--
-- TOC entry 151 (class 1259 OID 187873)
-- Dependencies: 3
-- Name: bte_avisos; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_avisos (
    avi_ident character varying(50) NOT NULL,
    avi_fcavis timestamp without time zone NOT NULL
);




--
-- TOC entry 1909 (class 0 OID 0)
-- Dependencies: 151
-- Name: TABLE bte_avisos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_avisos IS 'CONTIENE FECHAS ULTIMOS AVISOS';


--
-- TOC entry 1910 (class 0 OID 0)
-- Dependencies: 151
-- Name: COLUMN bte_avisos.avi_ident; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_avisos.avi_ident IS 'Identificador aviso (GESTOR / MONITORIZACION)';


--
-- TOC entry 1911 (class 0 OID 0)
-- Dependencies: 151
-- Name: COLUMN bte_avisos.avi_fcavis; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_avisos.avi_fcavis IS 'Fecha ultimo aviso';


--
-- TOC entry 156 (class 1259 OID 187918)
-- Dependencies: 1850 3
-- Name: bte_camfue; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_camfue (
    cfu_codigo bigint NOT NULL,
    cfu_codfue bigint NOT NULL,
    cfu_ident character varying(20) NOT NULL,
    cfu_espk character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 1912 (class 0 OID 0)
-- Dependencies: 156
-- Name: TABLE bte_camfue; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_camfue IS 'Definicion campos fuente datos';


--
-- TOC entry 1913 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN bte_camfue.cfu_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_camfue.cfu_codigo IS 'Codigo interno';


--
-- TOC entry 1914 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN bte_camfue.cfu_codfue; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_camfue.cfu_codfue IS 'Codigo interno fuente';


--
-- TOC entry 1915 (class 0 OID 0)
-- Dependencies: 156
-- Name: COLUMN bte_camfue.cfu_ident; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_camfue.cfu_ident IS 'Id campo';


--
-- TOC entry 144 (class 1259 OID 187775)
-- Dependencies: 3
-- Name: bte_docum; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_docum (
    doc_codigo bigint NOT NULL,
    doc_codtra bigint NOT NULL,
    doc_prese character varying(1) NOT NULL,
    doc_desc character varying(500) NOT NULL,
    doc_docide character varying(5),
    doc_docnum bigint,
    doc_rdscod bigint,
    doc_rdscla character varying(10),
    doc_tipdoc character varying(1),
    doc_compul character varying(1),
    doc_fotpia character varying(1),
    doc_firma character varying(1)
);




--
-- TOC entry 1916 (class 0 OID 0)
-- Dependencies: 144
-- Name: TABLE bte_docum; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_docum IS 'DOCUMENTOS ASOCIADOS A LA ENTRADA';


--
-- TOC entry 1917 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_codigo IS 'CÓDIGO INTERNO';


--
-- TOC entry 1918 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_codtra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_codtra IS 'CÓDIGO ENTRADA';


--
-- TOC entry 1919 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_prese; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_prese IS 'Indica si el documento requiere acción presencial';


--
-- TOC entry 1920 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_desc IS 'NOMBRE DEL DOCUMENTO';


--
-- TOC entry 1921 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_docide; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_docide IS 'Identificador documento  (Para documentos anexados telemáticamente sirve para asociar con documento del asiento)';


--
-- TOC entry 1922 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_docnum; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_docnum IS 'Número instancia  (Para documentos anexados telemáticamente sirve para asociar con documento del asiento)';


--
-- TOC entry 1923 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_rdscod; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_rdscod IS 'Para documentos anexados telemáticamente: Código RDS del documento';


--
-- TOC entry 1924 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_rdscla; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_rdscla IS 'Para documentos anexados telemáticamente: Clave RDS del documento';


--
-- TOC entry 1925 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_tipdoc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_tipdoc IS 'Para documentos a presentar presencialmente: Indica tipo de documento J/F/A/P (Justificante/Formulario/Anexo/Pago)';


--
-- TOC entry 1926 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_compul; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_compul IS 'Para documentos a presentar presencialmente: Indica si se debe compulsar';


--
-- TOC entry 1927 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_fotpia; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_fotpia IS 'Para documentos a presentar presencialmente: Indica si se debe presentar una fotocopia';


--
-- TOC entry 1928 (class 0 OID 0)
-- Dependencies: 144
-- Name: COLUMN bte_docum.doc_firma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_docum.doc_firma IS 'Para documentos a presentar presencialmente: Indica si debe ir firmado';


--
-- TOC entry 145 (class 1259 OID 187784)
-- Dependencies: 3
-- Name: bte_ficexp; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_ficexp (
    fic_idetra character varying(20) NOT NULL,
    fic_nomfic character varying(500)
);




--
-- TOC entry 1929 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN bte_ficexp.fic_idetra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_ficexp.fic_idetra IS 'IDENTIFICADOR DEL TRÁMITE';


--
-- TOC entry 157 (class 1259 OID 187926)
-- Dependencies: 3
-- Name: bte_filfue; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_filfue (
    fif_codigo bigint NOT NULL,
    fif_codfue bigint NOT NULL
);




--
-- TOC entry 1930 (class 0 OID 0)
-- Dependencies: 157
-- Name: TABLE bte_filfue; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_filfue IS 'Filas de datos fuente datos';


--
-- TOC entry 1931 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN bte_filfue.fif_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_filfue.fif_codigo IS 'Codigo interno';


--
-- TOC entry 1932 (class 0 OID 0)
-- Dependencies: 157
-- Name: COLUMN bte_filfue.fif_codfue; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_filfue.fif_codfue IS 'Codigo interno fuente datos';


--
-- TOC entry 158 (class 1259 OID 187932)
-- Dependencies: 3
-- Name: bte_fuedat; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_fuedat (
    fue_codigo bigint NOT NULL,
    fue_ident character varying(20) NOT NULL,
    fue_desc character varying(500),
    fue_idepro character varying(100) NOT NULL
);




--
-- TOC entry 1933 (class 0 OID 0)
-- Dependencies: 158
-- Name: TABLE bte_fuedat; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_fuedat IS 'Fuentes de datos para dominios';


--
-- TOC entry 1934 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN bte_fuedat.fue_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_fuedat.fue_codigo IS 'Codigo interno';


--
-- TOC entry 1935 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN bte_fuedat.fue_ident; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_fuedat.fue_ident IS 'Identificador fuente datos';


--
-- TOC entry 1936 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN bte_fuedat.fue_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_fuedat.fue_desc IS 'Descripcion';


--
-- TOC entry 1937 (class 0 OID 0)
-- Dependencies: 158
-- Name: COLUMN bte_fuedat.fue_idepro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_fuedat.fue_idepro IS 'IDENTIFICADOR DEL PROCEDIMIENTO';


--
-- TOC entry 147 (class 1259 OID 187803)
-- Dependencies: 3
-- Name: bte_gespro; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_gespro (
    gap_codges character varying(1536) NOT NULL,
    gap_idepro character varying(100) NOT NULL
);




--
-- TOC entry 1938 (class 0 OID 0)
-- Dependencies: 147
-- Name: TABLE bte_gespro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_gespro IS 'TRAMITES ASOCIADOS AL GESTOR';


--
-- TOC entry 1939 (class 0 OID 0)
-- Dependencies: 147
-- Name: COLUMN bte_gespro.gap_codges; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gespro.gap_codges IS 'USUARIO SEYCON DEL GESTOR';


--
-- TOC entry 1940 (class 0 OID 0)
-- Dependencies: 147
-- Name: COLUMN bte_gespro.gap_idepro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gespro.gap_idepro IS 'IDENTIFICADOR DEL PROCEDIMIENTO';


--
-- TOC entry 146 (class 1259 OID 187792)
-- Dependencies: 1839 1840 1841 1842 1843 1844 3
-- Name: bte_gestor; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_gestor (
    ges_seycon character varying(1536) NOT NULL,
    ges_email character varying(500),
    ges_updest character varying(1) DEFAULT 'N'::character varying NOT NULL,
    ges_updesm character varying(1) DEFAULT 'N'::character varying NOT NULL,
    ges_gesexp character varying(1) DEFAULT 'N'::character varying NOT NULL,
    ges_avient character varying(1) DEFAULT 'N'::character varying NOT NULL,
    ges_avimon character varying(1) DEFAULT 'N'::character varying NOT NULL,
    ges_avinot character varying(1) DEFAULT 'N'::character varying NOT NULL
);




--
-- TOC entry 1941 (class 0 OID 0)
-- Dependencies: 146
-- Name: TABLE bte_gestor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_gestor IS 'Gestores asociados a un organo.';


--
-- TOC entry 1942 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_seycon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_seycon IS 'USUARIO SEYCON DEL GESTOR';


--
-- TOC entry 1943 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_email; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_email IS 'EMAIL DEL GESTOR.';


--
-- TOC entry 1944 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_updest; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_updest IS 'PERMITE CAMBIAR ESTADO';


--
-- TOC entry 1945 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_updesm; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_updesm IS 'PERMITE CAMBIAR ESTADO MASIVO';


--
-- TOC entry 1946 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_gesexp; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_gesexp IS 'PERMITE GESTIONAR EXPEDIENTES';


--
-- TOC entry 1947 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_avient; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_avient IS 'INDICA SI SE GENERA MENSAJE DE AVISO AL GESTOR PARA AVISAR NUEVAS ENTRADAS';


--
-- TOC entry 1948 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_avimon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_avimon IS 'INDICA SI SE GENERA AVISO DE MONITORIZACION AL GESTOR';


--
-- TOC entry 1949 (class 0 OID 0)
-- Dependencies: 146
-- Name: COLUMN bte_gestor.ges_avinot; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_gestor.ges_avinot IS 'INDICA SI SE GENERA MENSAJE DE AVISO AL GESTOR PARA AVISAR ESTADO NOTIFICACIONES';


--
-- TOC entry 148 (class 1259 OID 187811)
-- Dependencies: 1845 1846 1847 1848 1849 3
-- Name: bte_proapl; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_proapl (
    tap_idepro character varying(100) NOT NULL,
    tap_desc character varying(100) NOT NULL,
    tap_inmed character varying(1) DEFAULT 'N'::character varying NOT NULL,
    tap_inform bigint,
    tap_tipacc character varying(1) DEFAULT 'E'::character varying NOT NULL,
    tap_url character varying(200),
    tap_jndi character varying(100),
    tap_ejbrem character varying(1) DEFAULT 'L'::character varying NOT NULL,
    tap_ejbaut character varying(1) DEFAULT 'N'::character varying NOT NULL,
    tap_usr character varying(500),
    tap_pwd character varying(500),
    tap_rol character varying(100),
    tap_aviso timestamp without time zone,
    tap_wsver character varying(10),
    tap_errors bytea,
    tap_uniadm bigint,
    tap_sms character varying(1) default 'N' not null,
    tap_avinot character varying(1) DEFAULT 'N'::character varying NOT NULL,
    tap_wssoa character varying(100)
);




--
-- TOC entry 1950 (class 0 OID 0)
-- Dependencies: 148
-- Name: TABLE bte_proapl; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_proapl IS 'LISTA DE TRAMITES QUE SE PUEDEN REGISTRAR EN LA BANDEJA. PARA CADA TRAMITE SE INDICA QUE APLICACIÓN LO GESTIONA.';


--
-- TOC entry 1951 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_idepro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_idepro IS 'IDENTIFICADOR DEL PROCEDIMIENTO';


--
-- TOC entry 1952 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_desc IS 'DESCRIPCION APLICACION';


--
-- TOC entry 1953 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_inmed; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_inmed IS 'INDICA SI TRAS UNA ENTRADA SE REALIZARÁ UN AVISO PARA DICHA ENTRADA. ESTE AVISO SE REALIZARÁ DE FORMA ASINCRONA.';


--
-- TOC entry 1954 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_inform; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_inform IS 'PERMITE INDICAR CADA CUANTO TIEMPO (EN HORAS) SE AVISARA AL EJB CON LOS TRAMITES RECIBIDOS (NO PROCESADOS). SI TIENE VALOR 0 NO SE INFORMARÁ.';


--
-- TOC entry 1955 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_tipacc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_tipacc IS 'TIPO DE ACCESO AL BACKOFFICE: EJB (E) / WEBSERVICE (W)';


--
-- TOC entry 1956 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_url; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_url IS 'INDICA SEGÚN TIPO DE ACCESO: 
- EJB: JNDI EJB
- WEBSERVICE: URL WEBSERVICE
';


--
-- TOC entry 1957 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_jndi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_jndi IS 'PARA TIPO DOMINIO EJB INDICA JNDI NAME DEL EJB A INVOCAR';


--
-- TOC entry 1958 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_ejbrem; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_ejbrem IS 'PARA TIPO DOMINIO EJB INDICA SI EL EJB ES REMOTO: REMOTO (R) / LOCAL (L)';


--
-- TOC entry 1959 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_ejbaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_ejbaut IS 'PARA TIPO DOMINIO EJB/WS INDICA:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password 
 - C: explícita a través plugin autenticación del organismo';


--
-- TOC entry 1960 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_usr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_usr IS 'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL USUARIO';


--
-- TOC entry 1961 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_pwd; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_pwd IS 'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL PASSWORD';


--
-- TOC entry 1962 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_rol; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_rol IS 'PERMITE ESTABLECER LA COMPROBACIÓN DE ROL QUE TIENE QUE TENER LA APLICACIÓN QUE INVOCA AL EJB DE BANDEJA PARA OBTENER ENTRADAS PENDIENTES';


--
-- TOC entry 1963 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_aviso; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_aviso IS 'FECHA ULTIMO AVISO REALIZADO';


--
-- TOC entry 1964 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_errors; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_errors IS 'MUESTRA LOS DOS ULTIMOS ERRORES QUE HAN SUCEDIDO DURANTE LAS INTEGACIONES';


--
-- TOC entry 1965 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_uniadm; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_uniadm IS 'UNIDAD ADMINISTRATIVA';


--
-- TOC entry 1966 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_sms; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_sms IS 'INDICA SI SE PERMITE INDICAR EL SMS EN LOS AVISOS DE EXPEDIENTE';


--
-- TOC entry 1967 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_avinot; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_avinot IS 'INDICA SI SE DEBEN AVISAR A LOS GESTORES DE LAS NOTIFICACIONES';


--
-- TOC entry 1968 (class 0 OID 0)
-- Dependencies: 148
-- Name: COLUMN bte_proapl.tap_wssoa; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_proapl.tap_wssoa IS 'WS: Soap action';


--
-- TOC entry 152 (class 1259 OID 187910)
-- Dependencies: 3
-- Name: bte_seqcfu; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqcfu
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 140 (class 1259 OID 187767)
-- Dependencies: 3
-- Name: bte_seqdoc; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqdoc
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 141 (class 1259 OID 187769)
-- Dependencies: 3
-- Name: bte_seqe09; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqe09
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 153 (class 1259 OID 187912)
-- Dependencies: 3
-- Name: bte_seqfif; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqfif
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 154 (class 1259 OID 187914)
-- Dependencies: 3
-- Name: bte_seqfue; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqfue
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 142 (class 1259 OID 187771)
-- Dependencies: 3
-- Name: bte_seqorg; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqorg
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 143 (class 1259 OID 187773)
-- Dependencies: 3
-- Name: bte_seqtra; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqtra
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 155 (class 1259 OID 187916)
-- Dependencies: 3
-- Name: bte_seqvcf; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE bte_seqvcf
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 149 (class 1259 OID 187828)
-- Dependencies: 3
-- Name: bte_tramit; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_tramit (
    tra_codigo bigint NOT NULL,
    tra_nument character varying(50) NOT NULL,
    tra_fecha timestamp without time zone NOT NULL,
    tra_tipo character varying(1) NOT NULL,
    tra_proces character varying(1) NOT NULL,
    tra_respro character varying(2000),
    tra_fecpro timestamp without time zone,
    tra_idetra character varying(20) NOT NULL,
    tra_vertra bigint NOT NULL,
    tra_uniadm bigint NOT NULL,
    tra_nivaut character varying(1) NOT NULL,
    tra_destra character varying(500) NOT NULL,
    tra_codasi bigint NOT NULL,
    tra_claasi character varying(10) NOT NULL,
    tra_codjus bigint NOT NULL,
    tra_clajus character varying(10) NOT NULL,
    tra_numreg character varying(50) NOT NULL,
    tra_fecreg timestamp without time zone NOT NULL,
    tra_numpre character varying(50),
    tra_fecpre timestamp without time zone,
    tra_nifrte character varying(12),
    tra_nomrte character varying(500),
    tra_seycon character varying(50),
    tra_nifrdo character varying(12),
    tra_nomrdo character varying(500),
    tra_idioma character varying(2) NOT NULL,
    tra_ticopr character varying(1),
    tra_nottel character varying(1),
    tra_firmad character varying(1) NOT NULL,
    tra_avisos character varying(1),
    tra_avisms character varying(10),
    tra_aviema character varying(500),
    tra_clave character varying(50),
    tra_nifdlg character varying(12),
    tra_nomdlg character varying(500),
    tra_sbexid character varying(50),
    tra_sbexua bigint,
    tra_idepro character varying(100) NOT NULL,
    tra_inipro timestamp without time zone NOT NULL
);




--
-- TOC entry 1969 (class 0 OID 0)
-- Dependencies: 149
-- Name: TABLE bte_tramit; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_tramit IS 'TRAMITES TELEMÁTICOS DEPOSITADOS EN LA BANDEJA POR EL SISTEMA DE TRAMITACIÓN';


--
-- TOC entry 1970 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_codigo IS 'CÓDIGO ENTRADA';


--
-- TOC entry 1971 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nument; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nument IS 'NUMERO DE ENTRADA EN BANDEJA';


--
-- TOC entry 1972 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_fecha IS 'FECHA ENTRADA';


--
-- TOC entry 1973 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_tipo IS 'TIPO ENTRADA: E (Registro) / P (Preregistro confirmado) / B (Envío) / N (Preenvio confirmado)';


--
-- TOC entry 1974 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_proces; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_proces IS 'INDICA SI HA SIDO PROCESADA POR EL BACKOFFICE (S/N)';


--
-- TOC entry 1975 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_respro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_respro IS 'TEXTO DESCRIPTIVO QUE PERMITE INDICAR RESULTADO DEL PROCESAMIENTO (ESTABLECIDO POR BACKOFFICE)';


--
-- TOC entry 1976 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_fecpro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_fecpro IS 'FECHA DE PROCESAMIENTO';


--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_idetra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_idetra IS 'IDENTIFICADOR DEL TRAMITE';


--
-- TOC entry 1978 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_vertra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_vertra IS 'VERSIÓN DEL TRAMITE';


--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_uniadm; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_uniadm IS 'UNIDAD ADMINISTRATIVA RESPONSABLE DEL TRAMITE';


--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nivaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nivaut IS 'NIVEL DE AUTENTICACIÓN CON EL QUE SE HA REALIZADO EL TRÁMITE';


--
-- TOC entry 1981 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_destra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_destra IS 'DESCRIPCION DEL TRAMITE. SE RECOGE EN L A TABLA PARA OPTIMIZAR ACCESO.';


--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_codasi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_codasi IS 'CODIGO DE LA REFERENCIA RDS DEL ASIENTO';


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_claasi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_claasi IS 'CLAVE DE LA REFERENCIA RDS DEL ASIENTO';


--
-- TOC entry 1984 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_codjus; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_codjus IS 'CODIGO DE LA REFERENCIA RDS DEL JUSTIFICANTE';


--
-- TOC entry 1985 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_clajus; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_clajus IS 'CLAVE DE LA REFERENCIA RDS DEL JUSTIFICANTE';


--
-- TOC entry 1986 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_numreg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_numreg IS 'INDICA EL NÚMERO DE REGISTRO / ENVIO';


--
-- TOC entry 1987 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_fecreg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_fecreg IS 'FECHA DE REGISTRO';


--
-- TOC entry 1988 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_numpre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_numpre IS 'PARA TIPO P INDICA NUMERO DE PREREGISTRO';


--
-- TOC entry 1989 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_fecpre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_fecpre IS 'PARA TIPO P INDICA FECHA DE PREREGISTRO';


--
-- TOC entry 1990 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nifrte; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nifrte IS 'NIF REPRESENTANTE QUE REALIZA TRÁMITE QUE REALIZA TRÁMITE. SE RECOGE EN L A TABLA PARA OPTIMIZAR ACCESO.';


--
-- TOC entry 1991 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nomrte; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nomrte IS 'NOMBRE REPRESENTANTE QUE REALIZA TRÁMITE. SE RECOGE EN L A TABLA PARA OPTIMIZAR ACCESO.';


--
-- TOC entry 1992 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_seycon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_seycon IS 'USUARIO SEYCON EN CASO DE QUE EL ACCESO HAYA SIDO AUTENTICADO';


--
-- TOC entry 1993 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nifrdo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nifrdo IS 'NIF REPRESENTADO';


--
-- TOC entry 1994 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nomrdo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nomrdo IS 'NOMBRE REPRESENTADO';


--
-- TOC entry 1995 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_idioma; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_idioma IS 'IDIOMA EN EL QUE SE HA REALIZADO EL TRAMITE';


--
-- TOC entry 1996 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_ticopr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_ticopr IS 'TIPO DE CONFIRMACION DEL PREREGISTRO:
   R -  Entrada confirmada mediante el módulo de confirmación de preregistros/preenvios en el registro presencial (circuito normal)
   G -  Entrada confirmada por el gestor: la documentación no ha sido entregada en un punto de registro o bien porque en el registro presencial no se ha confirmado en el módulo de confirmación de preregistros/preenvios.
   A -  Entrada que se confirmada automáticamente por la plataforma tras realizarse el preenvio (SOLO PARA PREENVIOS)';


--
-- TOC entry 1997 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nottel; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nottel IS 'Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.';


--
-- TOC entry 1998 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_firmad; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_firmad IS 'INDICA SI LA ENTRADA HA SIDO FIRMADA DIGITALMENTE';


--
-- TOC entry 1999 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_avisos; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_avisos IS 'INDICA SI LA ENTRADA HA SIDO CONFIGURADA PARA RECIBIR AVISOS DE TRAMITACION';


--
-- TOC entry 2000 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_avisms; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_avisms IS 'INDICA EL SMS PARA RECIBIR AVISOS DE TRAMITACION';


--
-- TOC entry 2001 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_aviema; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_aviema IS 'INDICA EL EMAIL PARA RECIBIR AVISOS DE TRAMITACION';


--
-- TOC entry 2002 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_clave; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_clave IS 'CLAVE DE ACCESO A LA ENTRADA';


--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nifdlg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nifdlg IS 'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_nomdlg; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_nomdlg IS 'En caso de existir delegacion indica nombre del delegado que presenta el tramite';


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_sbexid; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_sbexid IS 'EN CASO DE TRAMITE DE SUBSANACION INDICA ID EXPEDIENTE ASOCIADO';


--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_sbexua; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_sbexua IS 'EN CASO DE TRAMITE DE SUBSANACION INDICA UNIDAD ADMINISTRATIVA EXPEDIENTE';


--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 149
-- Name: COLUMN bte_tramit.tra_inipro; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_tramit.tra_inipro IS 'INDICA FECHA DE INICIO DE PROCESO. SE REINICIARA CADA VEZ QUE PASE A ESTADO NO PROCESADA';


--
-- TOC entry 159 (class 1259 OID 187942)
-- Dependencies: 3
-- Name: bte_valcfu; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE bte_valcfu (
    vcf_codigo bigint NOT NULL,
    vcf_codfif bigint NOT NULL,
    vcf_codcfu bigint NOT NULL,
    vcf_valor character varying(4000)
);




--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 159
-- Name: TABLE bte_valcfu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE bte_valcfu IS 'Valores fuente datos';


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN bte_valcfu.vcf_codigo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_valcfu.vcf_codigo IS 'Codigo interno';


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN bte_valcfu.vcf_codfif; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_valcfu.vcf_codfif IS 'Codigo interno fila fuente datos';


--
-- TOC entry 2011 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN bte_valcfu.vcf_codcfu; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_valcfu.vcf_codcfu IS 'Codigo interno campo fuente datos';


--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 159
-- Name: COLUMN bte_valcfu.vcf_valor; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN bte_valcfu.vcf_valor IS 'Valor';


--
-- TOC entry 1874 (class 2606 OID 187867)
-- Dependencies: 150 150
-- Name: bte_afe_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_arcfex
    ADD CONSTRAINT bte_afe_pk PRIMARY KEY (afe_idefic);


--
-- TOC entry 1876 (class 2606 OID 187877)
-- Dependencies: 151 151
-- Name: bte_avi_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_avisos
    ADD CONSTRAINT bte_avi_pk PRIMARY KEY (avi_ident);


--
-- TOC entry 1878 (class 2606 OID 187923)
-- Dependencies: 156 156
-- Name: bte_cfu_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_camfue
    ADD CONSTRAINT bte_cfu_pk PRIMARY KEY (cfu_codigo);


--
-- TOC entry 1852 (class 2606 OID 187782)
-- Dependencies: 144 144
-- Name: bte_doc_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_docum
    ADD CONSTRAINT bte_doc_pk PRIMARY KEY (doc_codigo);


--
-- TOC entry 1855 (class 2606 OID 187791)
-- Dependencies: 145 145
-- Name: bte_ficexp_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_ficexp
    ADD CONSTRAINT bte_ficexp_pk PRIMARY KEY (fic_idetra);


--
-- TOC entry 1882 (class 2606 OID 187930)
-- Dependencies: 157 157
-- Name: bte_fif_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_filfue
    ADD CONSTRAINT bte_fif_pk PRIMARY KEY (fif_codigo);


--
-- TOC entry 1885 (class 2606 OID 187939)
-- Dependencies: 158 158
-- Name: bte_fue_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_fuedat
    ADD CONSTRAINT bte_fue_pk PRIMARY KEY (fue_codigo);


--
-- TOC entry 1859 (class 2606 OID 187810)
-- Dependencies: 147 147 147
-- Name: bte_gap_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_gespro
    ADD CONSTRAINT bte_gap_pk PRIMARY KEY (gap_codges, gap_idepro);


--
-- TOC entry 1857 (class 2606 OID 187802)
-- Dependencies: 146 146
-- Name: bte_ges_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_gestor
    ADD CONSTRAINT bte_ges_pk PRIMARY KEY (ges_seycon);


--
-- TOC entry 1863 (class 2606 OID 187841)
-- Dependencies: 149 149
-- Name: bte_numpre_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_tramit
    ADD CONSTRAINT bte_numpre_uni UNIQUE (tra_numpre);


--
-- TOC entry 1865 (class 2606 OID 187839)
-- Dependencies: 149 149
-- Name: bte_numreg_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_tramit
    ADD CONSTRAINT bte_numreg_uni UNIQUE (tra_numreg);


--
-- TOC entry 1861 (class 2606 OID 187822)
-- Dependencies: 148 148
-- Name: bte_tap_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_proapl
    ADD CONSTRAINT bte_tap_pk PRIMARY KEY (tap_idepro);


--
-- TOC entry 1868 (class 2606 OID 187835)
-- Dependencies: 149 149
-- Name: bte_tra_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_tramit
    ADD CONSTRAINT bte_tra_pk PRIMARY KEY (tra_codigo);


--
-- TOC entry 1870 (class 2606 OID 187837)
-- Dependencies: 149 149
-- Name: bte_tranum_uni; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_tramit
    ADD CONSTRAINT bte_tranum_uni UNIQUE (tra_nument);


--
-- TOC entry 1889 (class 2606 OID 187949)
-- Dependencies: 159 159
-- Name: bte_vcf_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY bte_valcfu
    ADD CONSTRAINT bte_vcf_pk PRIMARY KEY (vcf_codigo);


--
-- TOC entry 1879 (class 1259 OID 187924)
-- Dependencies: 156 156
-- Name: bte_cfuident_ak; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE UNIQUE INDEX bte_cfuident_ak ON bte_camfue USING btree (cfu_codfue, cfu_ident);


--
-- TOC entry 1880 (class 1259 OID 187925)
-- Dependencies: 156
-- Name: bte_codfue_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_codfue_i ON bte_camfue USING btree (cfu_codfue);


--
-- TOC entry 1853 (class 1259 OID 187783)
-- Dependencies: 144
-- Name: bte_doctra_fk_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_doctra_fk_i ON bte_docum USING btree (doc_codtra);


--
-- TOC entry 1883 (class 1259 OID 187931)
-- Dependencies: 157
-- Name: bte_fiffue_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_fiffue_i ON bte_filfue USING btree (fif_codfue);


--
-- TOC entry 1886 (class 1259 OID 187940)
-- Dependencies: 158
-- Name: bte_fueide_ak; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE UNIQUE INDEX bte_fueide_ak ON bte_fuedat USING btree (fue_ident);


--
-- TOC entry 1887 (class 1259 OID 187941)
-- Dependencies: 158
-- Name: bte_fueidp_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_fueidp_i ON bte_fuedat USING btree (fue_idepro);


--
-- TOC entry 1866 (class 1259 OID 187859)
-- Dependencies: 149 149 149
-- Name: bte_tra_busent_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_tra_busent_i ON bte_tramit USING btree (tra_idetra, tra_proces, tra_fecha);


--
-- TOC entry 1871 (class 1259 OID 187843)
-- Dependencies: 149
-- Name: bte_tratap_fk_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_tratap_fk_i ON bte_tramit USING btree (tra_idepro);


--
-- TOC entry 1872 (class 1259 OID 187842)
-- Dependencies: 149
-- Name: bte_tratra_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_tratra_i ON bte_tramit USING btree (tra_idetra);


--
-- TOC entry 1890 (class 1259 OID 187950)
-- Dependencies: 159
-- Name: bte_vcfcfu_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_vcfcfu_i ON bte_valcfu USING btree (vcf_codcfu);


--
-- TOC entry 1891 (class 1259 OID 187951)
-- Dependencies: 159
-- Name: bte_vcffif_i; Type: INDEX; Schema: public; Owner: sistra; Tablespace: 
--

CREATE INDEX bte_vcffif_i ON bte_valcfu USING btree (vcf_codfif);


--
-- TOC entry 1896 (class 2606 OID 187868)
-- Dependencies: 145 150 1854
-- Name: bte_afefic_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_arcfex
    ADD CONSTRAINT bte_afefic_fk FOREIGN KEY (afe_idefic) REFERENCES bte_ficexp(fic_idetra);


--
-- TOC entry 1897 (class 2606 OID 187952)
-- Dependencies: 156 158 1884
-- Name: bte_cfufue_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_camfue
    ADD CONSTRAINT bte_cfufue_fk FOREIGN KEY (cfu_codfue) REFERENCES bte_fuedat(fue_codigo);


--
-- TOC entry 1892 (class 2606 OID 187849)
-- Dependencies: 1867 149 144
-- Name: bte_doctra_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_docum
    ADD CONSTRAINT bte_doctra_fk FOREIGN KEY (doc_codtra) REFERENCES bte_tramit(tra_codigo);


--
-- TOC entry 1898 (class 2606 OID 187957)
-- Dependencies: 1884 158 157
-- Name: bte_fiffue_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_filfue
    ADD CONSTRAINT bte_fiffue_fk FOREIGN KEY (fif_codfue) REFERENCES bte_fuedat(fue_codigo);


--
-- TOC entry 1899 (class 2606 OID 187962)
-- Dependencies: 1860 148 158
-- Name: bte_fuetap_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_fuedat
    ADD CONSTRAINT bte_fuetap_fk FOREIGN KEY (fue_idepro) REFERENCES bte_proapl(tap_idepro);


--
-- TOC entry 1894 (class 2606 OID 187854)
-- Dependencies: 146 1856 147
-- Name: bte_gapges_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_gespro
    ADD CONSTRAINT bte_gapges_fk FOREIGN KEY (gap_codges) REFERENCES bte_gestor(ges_seycon);


--
-- TOC entry 1893 (class 2606 OID 187823)
-- Dependencies: 148 147 1860
-- Name: bte_gaptap_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_gespro
    ADD CONSTRAINT bte_gaptap_fk FOREIGN KEY (gap_idepro) REFERENCES bte_proapl(tap_idepro);


--
-- TOC entry 1895 (class 2606 OID 187844)
-- Dependencies: 149 1860 148
-- Name: bte_tratap_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_tramit
    ADD CONSTRAINT bte_tratap_fk FOREIGN KEY (tra_idepro) REFERENCES bte_proapl(tap_idepro);


--
-- TOC entry 1900 (class 2606 OID 187967)
-- Dependencies: 159 156 1877
-- Name: bte_vcfcfu_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_valcfu
    ADD CONSTRAINT bte_vcfcfu_fk FOREIGN KEY (vcf_codcfu) REFERENCES bte_camfue(cfu_codigo);


--
-- TOC entry 1901 (class 2606 OID 187972)
-- Dependencies: 1881 159 157
-- Name: bte_vcffif_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY bte_valcfu
    ADD CONSTRAINT bte_vcffif_fk FOREIGN KEY (vcf_codfif) REFERENCES bte_filfue(fif_codigo);


--
-- TOC entry 1905 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-01-27 08:50:51

--
-- PostgreSQL database dump complete
--

-- UPDATE 2.3.3 TO 2.3.4

ALTER table BTE_PROAPL ADD  TAP_PLNOTV  VARCHAR(1) default 'N' not null;
comment on column BTE_PROAPL.TAP_PLNOTV is 'INDICA SI PERMITE PLAZO NOTIFICACIONES VARIABLE (S/N)';

ALTER table BTE_PROAPL ALTER COLUMN TAP_URL TYPE VARCHAR(500);
