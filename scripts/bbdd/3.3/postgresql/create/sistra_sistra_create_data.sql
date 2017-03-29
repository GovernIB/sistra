--
-- PostgreSQL database dump
--


--
-- TOC entry 2089 (class 0 OID 0)
-- Dependencies: 140
-- Name: str_seqdjs; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqdjs', 1, false);


--
-- TOC entry 2090 (class 0 OID 0)
-- Dependencies: 141
-- Name: str_seqdnv; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqdnv', 1, false);


--
-- TOC entry 2091 (class 0 OID 0)
-- Dependencies: 142
-- Name: str_seqdoc; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqdoc', 1, false);


--
-- TOC entry 2092 (class 0 OID 0)
-- Dependencies: 143
-- Name: str_seqdom; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqdom', 12, true);


--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 144
-- Name: str_seqe09; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqe17', 1, false);


--
-- TOC entry 2094 (class 0 OID 0)
-- Dependencies: 145
-- Name: str_seqetn; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqetn', 1, false);


--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 146
-- Name: str_seqetr; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqetr', 1, false);


--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 147
-- Name: str_seqifi; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqifi', 1, false);


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 148
-- Name: str_seqimp; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqimp', 1, false);


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 149
-- Name: str_seqmpl; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqmpl', 1, false);


--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 150
-- Name: str_seqmtr; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqmtr', 1, false);


--
-- TOC entry 2100 (class 0 OID 0)
-- Dependencies: 151
-- Name: str_seqorg; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqorg', 1, true);


--
-- TOC entry 2101 (class 0 OID 0)
-- Dependencies: 152
-- Name: str_seqp09; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqp17', 1, false);


--
-- TOC entry 2102 (class 0 OID 0)
-- Dependencies: 153
-- Name: str_seqtnv; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqtnv', 1, false);


--
-- TOC entry 2103 (class 0 OID 0)
-- Dependencies: 154
-- Name: str_seqtra; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqtra', 1, false);


--
-- TOC entry 2104 (class 0 OID 0)
-- Dependencies: 155
-- Name: str_seqtrv; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('str_seqtrv', 1, false);


--
-- TOC entry 2065 (class 0 OID 188825)
-- Dependencies: 163
-- Data for Name: str_espniv; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2058 (class 0 OID 188764)
-- Dependencies: 156 2065
-- Data for Name: str_datjus; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2064 (class 0 OID 188820)
-- Dependencies: 162
-- Data for Name: str_dprovi; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2059 (class 0 OID 188772)
-- Dependencies: 157 2064
-- Data for Name: str_dmunic; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2072 (class 0 OID 188879)
-- Dependencies: 170
-- Data for Name: str_orgres; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO str_orgres (org_codigo, org_desc) VALUES (1, 'GE-Genérico');


--
-- TOC entry 2077 (class 0 OID 188913)
-- Dependencies: 175 2072
-- Data for Name: str_tramit; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2082 (class 0 OID 188948)
-- Dependencies: 180 2065 2077
-- Data for Name: str_traver; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2061 (class 0 OID 188790)
-- Dependencies: 159 2082
-- Data for Name: str_docum; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2060 (class 0 OID 188778)
-- Dependencies: 158 2061
-- Data for Name: str_docniv; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2062 (class 0 OID 188803)
-- Dependencies: 160 2072
-- Data for Name: str_domin; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (1, 'GESACARBUA', 'S', 'es.caib.sistra.db', 'select ''1'' codigo, null parent, ''Unidad Test'' descripcion from dual', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Arbol unidadades administrativas', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (2, 'GESACUADES', 'S', 'es.caib.sistra.db', 'SELECT  ''1'' CODIGO,''Unidad Test'' DESCRIPCION  FROM dual WHERE ''XX'' <> ?', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Descripción de unidad administrativa. Parametrizado por código unidad.', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (3, 'GESACUNADM', 'S', 'es.caib.sistra.db', 'SELECT  ''1'' CODIGO,''Unidad Test'' DESCRIPCION  FROM dual', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Lista de unidades administrativas', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (4, 'GERDSMODE', 'S', 'es.caib.redose.db', 'SELECT 	MOD_MODELO CODIGO,MOD_MODELO || '' - '' || MOD_NOMBRE DESCRIPCION FROM RDS_MODELO ORDER BY 1', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Lista de modelos de documentos del RDS', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (5, 'GERDSVERS', 'S', 'es.caib.redose.db', 'SELECT RDS_VERS.VER_VERSIO CODIGO, TO_CHAR(RDS_VERS.VER_VERSIO) DESCRIPCION FROM RDS_VERS,RDS_MODELO WHERE RDS_MODELO.MOD_CODIGO =  RDS_VERS.VER_CODMOD AND RDS_MODELO.MOD_MODELO = ? ORDER BY 1', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Lista de versiones de un modelo de documento del RDS. Parametrizado por código modelo.', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (6, 'GEFORMMODE', 'S', 'es.caib.rolforms.db', 'SELECT DISTINCT RFR_FORMUL.FOR_MODELO CODIGO,RFR_FORMUL.FOR_MODELO || '' - '' || RFR_TRAFOR.TRF_TITULO DESCRIPCION
FROM RFR_FORMUL,RFR_TRAFOR
WHERE RFR_TRAFOR.TRF_CODFOR = RFR_FORMUL.FOR_CODI AND RFR_TRAFOR.TRF_CODIDI = ''es''
ORDER BY 1', NULL, 'R', 'N', NULL, NULL, 'N', 1, 'Lista de modelos de formularios', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (7, 'GEFORMMOVE', 'S', 'es.caib.rolforms.db', 'SELECT RFR_FORMUL.FOR_MODELO MODELO,
	   RFR_FORMUL.FOR_MODELO || '' - '' || RFR_TRAFOR.TRF_TITULO DESCRIPCION,
	   RFR_FORMUL.FOR_VERSIO VERSION	   	   
FROM RFR_FORMUL,RFR_TRAFOR
WHERE RFR_TRAFOR.TRF_CODFOR = RFR_FORMUL.FOR_CODI AND RFR_TRAFOR.TRF_CODIDI = ''es''
ORDER BY 1,3', NULL, 'R', 'N', NULL, NULL, 'N', 1, 'Arbol de formularios y versiones', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (8, 'GEFORMVERS', 'S', 'es.caib.rolforms.db', 'SELECT  RFR_FORMUL.FOR_VERSIO CODIGO, RFR_FORMUL.FOR_VERSIO DESCRIPCION 
FROM RFR_FORMUL 
WHERE RFR_FORMUL.FOR_MODELO = ?
ORDER BY 1', NULL, 'R', 'N', NULL, NULL, 'N', 1, 'Lista de versiones de un formulario. Parametrizado por código de formulario.', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (9, 'GEPAISES', 'S', 'es.caib.sistra.db', 'SELECT TRIM( PAI_CODALF ) CODIGO, UPPER( PAI_DENCAS ) DESCRIPCION FROM STR_DPAIS WHERE PAI_VIGENC = ''S'' ORDER BY DESCRIPCION', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Lista de paises', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (10, 'GEPROVINCI', 'S', 'es.caib.sistra.db', 'SELECT PRO_CODIGO CODIGO, PRO_DENCAT DESCRIPCION FROM STR_DPROVI ORDER BY CODIGO', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Lista de provincias', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (11, 'GEGMUNICI', 'S', 'es.caib.sistra.db', 'SELECT MUN_CODIGO CODIGO, MUN_DENOFI DESCRIPCION FROM STR_DMUNIC WHERE MUN_PROVIN = ? ORDER BY DESCRIPCION', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Lista de municipios de una provincia. Parametrizado por código provincia.', NULL, NULL);
INSERT INTO str_domin (dom_codigo, dom_identi, dom_tipo, dom_url, dom_sql, dom_jndi, dom_ejbrem, dom_ejbstd, dom_usr, dom_pwd, dom_cache, dom_codorg, dom_desc, dom_wsver, dom_wssoa) VALUES (12, 'GEMUNIDESC', 'S', 'es.caib.sistra.db', 'SELECT MUN_CODIGO CODIGO, MUN_DENOFI DESCRIPCION FROM STR_DMUNIC WHERE MUN_PROVIN = ? AND MUN_CODIGO = ?', NULL, 'L', 'N', NULL, NULL, 'N', 1, 'Descripción de un municipio. Parametrizado por código provincia y por código municipio.', NULL, NULL);


--
-- TOC entry 2063 (class 0 OID 188815)
-- Dependencies: 161
-- Data for Name: str_dpais; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2066 (class 0 OID 188837)
-- Dependencies: 164
-- Data for Name: str_gesfrm; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO str_gesfrm (gsf_ident, gsf_desc, gsf_url, gsf_urltra, gsf_urlred) VALUES ('forms', 'FORMS', '@sistra.url@', '@forms.server@/formfront/iniciTelematic.do', '/formfront/continuacioTelematic.do');


--
-- TOC entry 2084 (class 0 OID 189107)
-- Dependencies: 182
-- Data for Name: str_grupos; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2086 (class 0 OID 189122)
-- Dependencies: 184 2084 2077
-- Data for Name: str_grptra; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2085 (class 0 OID 189112)
-- Dependencies: 183 2084
-- Data for Name: str_grpusu; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2067 (class 0 OID 188845)
-- Dependencies: 165
-- Data for Name: str_idioma; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO str_idioma (idi_codigo, idi_orden) VALUES ('es', 1);
INSERT INTO str_idioma (idi_codigo, idi_orden) VALUES ('ca', 2);
INSERT INTO str_idioma (idi_codigo, idi_orden) VALUES ('en', 3);


--
-- TOC entry 2069 (class 0 OID 188858)
-- Dependencies: 167
-- Data for Name: str_import; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2068 (class 0 OID 188850)
-- Dependencies: 166 2069
-- Data for Name: str_impfic; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2070 (class 0 OID 188866)
-- Dependencies: 168
-- Data for Name: str_menpla; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2071 (class 0 OID 188872)
-- Dependencies: 169 2082
-- Data for Name: str_mentra; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2073 (class 0 OID 188884)
-- Dependencies: 171 2058 2067
-- Data for Name: str_tradjs; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2074 (class 0 OID 188889)
-- Dependencies: 172 2060 2067
-- Data for Name: str_tradnv; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2075 (class 0 OID 188897)
-- Dependencies: 173 2061 2067
-- Data for Name: str_tradoc; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2076 (class 0 OID 188905)
-- Dependencies: 174 2065 2067
-- Data for Name: str_traetn; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2078 (class 0 OID 188920)
-- Dependencies: 176 2067 2070
-- Data for Name: str_trampl; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2079 (class 0 OID 188928)
-- Dependencies: 177 2067 2071
-- Data for Name: str_tramtr; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2080 (class 0 OID 188936)
-- Dependencies: 178 2065 2082
-- Data for Name: str_traniv; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2081 (class 0 OID 188943)
-- Dependencies: 179 2067 2077
-- Data for Name: str_tratra; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2083 (class 0 OID 189097)
-- Dependencies: 181 2077
-- Data for Name: str_usutra; Type: TABLE DATA; Schema: public; Owner: sistra
--



-- Completed on 2014-01-27 09:23:15

--
-- PostgreSQL database dump complete
--

