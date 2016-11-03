--
-- PostgreSQL database dump
--


--
-- TOC entry 141 (class 1259 OID 187725)
-- Dependencies: 3
-- Name: aud_audit; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE aud_audit (
    aud_codi bigint NOT NULL,
    aud_fecha timestamp without time zone,
    aud_tipo character varying(6),
    aud_desc character varying(4000),
    aud_nivaut character varying(1),
    aud_seycon character varying(1536),
    aud_nif character varying(12),
    aud_nombre character varying(256),
    aud_idi character varying(2),
    aud_result character varying(1),
    aud_modtra character varying(20),
    aud_vertra bigint,
    aud_idper character varying(50),
    aud_clave character varying(256),
    aud_proced character varying(100)
);




--
-- TOC entry 1805 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_codi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_codi IS 'ID INTERNO SECUENCIAL';


--
-- TOC entry 1806 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_fecha; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_fecha IS 'FECHA ENTRADA EVENTO (PROPORCIONADA POR SISTEMA AUDITORIA)';


--
-- TOC entry 1807 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_tipo IS 'TIPO EVENTO';


--
-- TOC entry 1808 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_desc IS 'DESCRIPCION EVENTO';


--
-- TOC entry 1809 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_nivaut; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_nivaut IS 'NIVEL AUTENTICACION';


--
-- TOC entry 1810 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_seycon; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_seycon IS 'USUARIO SEYCON';


--
-- TOC entry 1811 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_nif; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_nif IS 'NIF';


--
-- TOC entry 1812 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_nombre; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_nombre IS 'NOMBRE';


--
-- TOC entry 1813 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_idi; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_idi IS 'IDIOMA';


--
-- TOC entry 1814 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_result; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_result IS 'RESULTADO EVENTO';


--
-- TOC entry 1815 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_modtra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_modtra IS 'MODELO DEL TRAMITE';


--
-- TOC entry 1816 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_vertra; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_vertra IS 'VERSION DEL TRAMITE';


--
-- TOC entry 1817 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_idper; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_idper IS 'IDENTIFICADOR PERSISTENCIA';


--
-- TOC entry 1818 (class 0 OID 0)
-- Dependencies: 141
-- Name: COLUMN aud_audit.aud_clave; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_audit.aud_clave IS 'CLAVE (UTILIZADA SEGÚN TIPO EVENTO)';


--
-- TOC entry 140 (class 1259 OID 187723)
-- Dependencies: 3
-- Name: aud_audseq; Type: SEQUENCE; Schema: public; Owner: sistra
--

CREATE SEQUENCE aud_audseq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;




--
-- TOC entry 142 (class 1259 OID 187733)
-- Dependencies: 3
-- Name: aud_inici; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE aud_inici (
    ini_fecha timestamp without time zone,
    ini_ptlsrv bigint,
    ini_usat1 character varying(4000),
    ini_ptlsrt bigint,
    ini_ptlbz bigint,
    ini_usat2 character varying(4000),
    ini_trarr bigint,
    ini_trapr bigint,
    ini_usat3 character varying(4000),
    ini_mtrt1 character varying(4000),
    ini_mtro1 character varying(4000),
    ini_usat4 character varying(4000),
    ini_mtrn1 bigint,
    ini_mtrt2 character varying(4000),
    ini_usat5 character varying(4000),
    ini_mtro2 character varying(4000),
    ini_mtrn2 bigint,
    ini_mtrt3 character varying(4000),
    ini_mtrt4 character varying(4000),
    ini_mtro4 character varying(4000),
    ini_mtro3 character varying(4000),
    ini_mtrn3 bigint,
    ini_mtrn4 bigint,
    ini_mtrn5 bigint,
    ini_mtrt5 character varying(4000),
    ini_mtro5 character varying(4000),
    ini_mact1 character varying(4000),
    ini_maco1 character varying(4000),
    ini_macn1 bigint,
    ini_mact2 character varying(4000),
    ini_maco2 character varying(4000),
    ini_macn2 bigint,
    ini_mact3 character varying(4000),
    ini_maco3 character varying(4000),
    ini_macn3 bigint,
    ini_mact4 character varying(4000),
    ini_maco4 character varying(4000),
    ini_macn4 bigint,
    ini_mact5 character varying(4000),
    ini_maco5 character varying(4000),
    ini_macn5 bigint,
    ini_usao1 character varying(4000),
    ini_usan1 bigint,
    ini_usao2 character varying(4000),
    ini_usan2 bigint,
    ini_usao3 character varying(4000),
    ini_usan3 bigint,
    ini_usao4 character varying(4000),
    ini_usan4 bigint,
    ini_usao5 character varying(4000),
    ini_usan5 bigint,
    ini_usaf1 character varying(100),
    ini_usaf2 character varying(100),
    ini_usaf3 character varying(100),
    ini_usaf4 character varying(100),
    ini_usaf5 character varying(100),
    ini_rescrt bigint,
    ini_resusu bigint,
    ini_resan bigint,
    ini_resreg bigint,
    ini_resbd bigint,
    ini_rescs bigint,
    ini_respag bigint,
    ini_ressrv bigint,
    ini_restmx bigint,
    ini_resfmx character varying(100),
    ini_ressrt bigint,
    ini_mtrtc1 character varying(4000),
    ini_mtrtc2 character varying(4000),
    ini_mtrtc3 character varying(4000),
    ini_mtrtc4 character varying(4000),
    ini_mtrtc5 character varying(4000),
    ini_mactc1 character varying(4000),
    ini_mactc2 character varying(4000),
    ini_mactc3 character varying(4000),
    ini_mactc4 character varying(4000),
    ini_mactc5 character varying(4000),
    ini_usatc1 character varying(4000),
    ini_usatc2 character varying(4000),
    ini_usatc3 character varying(4000),
    ini_usatc4 character varying(4000),
    ini_usatc5 character varying(4000)
);




--
-- TOC entry 1819 (class 0 OID 0)
-- Dependencies: 142
-- Name: TABLE aud_inici; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE aud_inici IS 'CAMPOS CALCULADOS PERIODICAMENTE PARA LA PAGINA DE INICIO';


--
-- TOC entry 143 (class 1259 OID 187739)
-- Dependencies: 3
-- Name: aud_modul; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE aud_modul (
    mod_modul character varying(6) NOT NULL,
    mod_desc character varying(100) NOT NULL,
    mod_orden bigint NOT NULL,
    mod_descca character varying(100) NOT NULL
);




--
-- TOC entry 1820 (class 0 OID 0)
-- Dependencies: 143
-- Name: COLUMN aud_modul.mod_modul; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_modul.mod_modul IS 'Identificador del módulo';


--
-- TOC entry 1821 (class 0 OID 0)
-- Dependencies: 143
-- Name: COLUMN aud_modul.mod_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_modul.mod_desc IS 'Descripcion del módulo que aparecerá en la aplicación de auditoria.';


--
-- TOC entry 1822 (class 0 OID 0)
-- Dependencies: 143
-- Name: COLUMN aud_modul.mod_orden; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_modul.mod_orden IS 'Orden en el que aparecerá el módulo en la aplicación de auditoría';


--
-- TOC entry 1823 (class 0 OID 0)
-- Dependencies: 143
-- Name: COLUMN aud_modul.mod_descca; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_modul.mod_descca IS 'Descripcion del módulo que aparecerá en la aplicación de auditoria.';


--
-- TOC entry 144 (class 1259 OID 187744)
-- Dependencies: 3
-- Name: aud_tabs; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE aud_tabs (
    tab_codi character varying(10) NOT NULL,
    tab_desc character varying(100) NOT NULL,
    tab_handlr character varying(100) NOT NULL,
    tab_orden bigint NOT NULL
);




--
-- TOC entry 1824 (class 0 OID 0)
-- Dependencies: 144
-- Name: TABLE aud_tabs; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON TABLE aud_tabs IS 'TABS PERSONALIZADOS DEL CUADRO DE MANDO';


--
-- TOC entry 145 (class 1259 OID 187749)
-- Dependencies: 3
-- Name: aud_tipoev; Type: TABLE; Schema: public; Owner: sistra; Tablespace: 
--

CREATE TABLE aud_tipoev (
    tip_tipo character varying(6) NOT NULL,
    tip_modul character varying(6) NOT NULL,
    tip_audit character varying(1),
    tip_desc character varying(256),
    tip_orden bigint,
    tip_prpcls character varying(30),
    tip_handlr character varying(100),
    tip_ayuda character varying(256),
    tip_descca character varying(256),
    tip_ayudac character varying(256)
);




--
-- TOC entry 1825 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_tipo; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_tipo IS 'TIPO DE EVENTO';


--
-- TOC entry 1826 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_modul; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_modul IS 'IDENTIFICADOR DEL MÓDULO';


--
-- TOC entry 1827 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_audit; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_audit IS 'INDICA SI SE DEBE AUDITAR (S/N)';


--
-- TOC entry 1828 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_desc; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_desc IS 'DESCRIPCION EVENTO';


--
-- TOC entry 1829 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_orden; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_orden IS 'ORDEN DE PRESENTACIÓN DENTRO DE SU MÓDULO, EN EL FRONT DE AUDITORIA';


--
-- TOC entry 1830 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_prpcls; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_prpcls IS 'PROPIEDADES DE LAS COLUMNAS A MOSTRAR:
T: INDICA QUE SE QUIERE VISUALIZAR EL NUMERO DE EVENTOS DEL EVENTO EN CUESTION
I: INDICA QUE SE QUIERE VISUALIZAR EL TOTAL POR IDIOMA
N: INDICA QUE SE QUIERE VISUALIZAR EL TOTAL POR NIVEL DE AUTENTICACION
D:INDICA QUE SE QUIERE MOSTRAR EL DETALLE DEL EVENTO EN QUESTION
G: INDICA QUE SE QUIERE MOSTRAR UN GRÁFICO DEL EVENTO EN QUESTION
X: INDICA QUE EL EVENTO EN QUESTION TIENE VISUALIZACION PARTICULAR (PERMITE INTRODUCIR HTML LIBRE) 
';


--
-- TOC entry 1831 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_handlr; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_handlr IS 'NOMBRE DE LA CLASE JAVA HANDLER PARA GESTIONAR CARACTERÍSTICAS ESPECIALES DEL TIPO DE EVENTO AUDITADO (TEXTO PARTICULARIZADO, DETALLE, GRAFICO,...)
';


--
-- TOC entry 1832 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_ayuda; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_ayuda IS 'TEXTO DE AYUDA QUE APARECERÁ EN EL FRONT DE AUDITORIA.';


--
-- TOC entry 1833 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_descca; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_descca IS 'DESCRIPCION EVENTO';


--
-- TOC entry 1834 (class 0 OID 0)
-- Dependencies: 145
-- Name: COLUMN aud_tipoev.tip_ayudac; Type: COMMENT; Schema: public; Owner: sistra
--

COMMENT ON COLUMN aud_tipoev.tip_ayudac IS 'TEXTO DE AYUDA QUE APARECERÁ EN EL FRONT DE AUDITORIA.';


--
-- TOC entry 1792 (class 2606 OID 187732)
-- Dependencies: 141 141
-- Name: aud_aud_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY aud_audit
    ADD CONSTRAINT aud_aud_pk PRIMARY KEY (aud_codi);


--
-- TOC entry 1794 (class 2606 OID 187743)
-- Dependencies: 143 143
-- Name: aud_mod_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY aud_modul
    ADD CONSTRAINT aud_mod_pk PRIMARY KEY (mod_modul);


--
-- TOC entry 1796 (class 2606 OID 187748)
-- Dependencies: 144 144
-- Name: aud_tab_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY aud_tabs
    ADD CONSTRAINT aud_tab_pk PRIMARY KEY (tab_codi);


--
-- TOC entry 1798 (class 2606 OID 187756)
-- Dependencies: 145 145
-- Name: aud_tip_pk; Type: CONSTRAINT; Schema: public; Owner: sistra; Tablespace: 
--

ALTER TABLE ONLY aud_tipoev
    ADD CONSTRAINT aud_tip_pk PRIMARY KEY (tip_tipo);


--
-- TOC entry 1799 (class 2606 OID 187757)
-- Dependencies: 145 1797 141
-- Name: aud_audtip_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY aud_audit
    ADD CONSTRAINT aud_audtip_fk FOREIGN KEY (aud_tipo) REFERENCES aud_tipoev(tip_tipo);


--
-- TOC entry 1800 (class 2606 OID 187762)
-- Dependencies: 145 1793 143
-- Name: aud_tipmod_fk; Type: FK CONSTRAINT; Schema: public; Owner: sistra
--

ALTER TABLE ONLY aud_tipoev
    ADD CONSTRAINT aud_tipmod_fk FOREIGN KEY (tip_modul) REFERENCES aud_modul(mod_modul);


--
-- TOC entry 1804 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-01-27 08:29:11

--
-- PostgreSQL database dump complete
--

