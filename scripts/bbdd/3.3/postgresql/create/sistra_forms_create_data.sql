--
-- PostgreSQL database dump
--


--
-- TOC entry 2107 (class 0 OID 0)
-- Dependencies: 140
-- Name: rfr_secarc; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secarc', 1, false);


--
-- TOC entry 2108 (class 0 OID 0)
-- Dependencies: 141
-- Name: rfr_secayu; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secayu', 1, false);


--
-- TOC entry 2109 (class 0 OID 0)
-- Dependencies: 142
-- Name: rfr_seccom; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_seccom', 1, false);


--
-- TOC entry 2110 (class 0 OID 0)
-- Dependencies: 143
-- Name: rfr_secfor; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secfor', 1, false);


--
-- TOC entry 2111 (class 0 OID 0)
-- Dependencies: 144
-- Name: rfr_secmas; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secmas', 10, true);


--
-- TOC entry 2112 (class 0 OID 0)
-- Dependencies: 145
-- Name: rfr_secpal; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secpal', 1, false);


--
-- TOC entry 2113 (class 0 OID 0)
-- Dependencies: 146
-- Name: rfr_secpan; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secpan', 1, false);


--
-- TOC entry 2114 (class 0 OID 0)
-- Dependencies: 147
-- Name: rfr_secpat; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secpat', 2, true);


--
-- TOC entry 2115 (class 0 OID 0)
-- Dependencies: 148
-- Name: rfr_secper; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secper', 3, true);


--
-- TOC entry 2116 (class 0 OID 0)
-- Dependencies: 149
-- Name: rfr_secprs; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secprs', 1, false);


--
-- TOC entry 2117 (class 0 OID 0)
-- Dependencies: 150
-- Name: rfr_secpsa; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secpsa', 1, false);


--
-- TOC entry 2118 (class 0 OID 0)
-- Dependencies: 151
-- Name: rfr_secsal; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secsal', 1, false);


--
-- TOC entry 2119 (class 0 OID 0)
-- Dependencies: 152
-- Name: rfr_sectnf; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_sectnf', 1, false);


--
-- TOC entry 2120 (class 0 OID 0)
-- Dependencies: 153
-- Name: rfr_secvap; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secvap', 1, false);


--
-- TOC entry 2121 (class 0 OID 0)
-- Dependencies: 154
-- Name: rfr_secvfi; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_secvfi', 1, false);


--
-- TOC entry 2122 (class 0 OID 0)
-- Dependencies: 155
-- Name: rfr_seqval; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rfr_seqval', 1, false);


--
-- TOC entry 2071 (class 0 OID 188010)
-- Dependencies: 156
-- Data for Name: rfr_archiv; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2099 (class 0 OID 188211)
-- Dependencies: 184
-- Data for Name: rfr_versio; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_versio (ver_codigo, ver_nombre, ver_fecha, ver_sufix) VALUES (0, 'VERSION 0: INICIAL IBIT', '2006-01-01', NULL);
INSERT INTO rfr_versio (ver_codigo, ver_nombre, ver_fecha, ver_sufix) VALUES (1, 'VERSION 1: MODIFICACION INDRA (FEBRERO 08)', '2008-02-09', 'V1');
INSERT INTO rfr_versio (ver_codigo, ver_nombre, ver_fecha, ver_sufix) VALUES (2, 'VERSION 2: ACTUALIZACION FRONTAL (OCTUBRE 2013)', '2013-01-10', 'V2');


--
-- TOC entry 2074 (class 0 OID 188033)
-- Dependencies: 159 2071 2071 2071 2099
-- Data for Name: rfr_formul; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2082 (class 0 OID 188082)
-- Dependencies: 167 2074
-- Data for Name: rfr_pantal; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2084 (class 0 OID 188100)
-- Dependencies: 169
-- Data for Name: rfr_perusu; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_perusu (per_codi, per_codest, per_patico) VALUES (1, 'JUVENIL', '/estilo_azul');
INSERT INTO rfr_perusu (per_codi, per_codest, per_patico) VALUES (2, 'CIUDADANO', '/estilo_naranja');
INSERT INTO rfr_perusu (per_codi, per_codest, per_patico) VALUES (3, 'CAIB_AZUL', '/estilo_caib');


--
-- TOC entry 2072 (class 0 OID 188018)
-- Dependencies: 157 2082 2084
-- Data for Name: rfr_ayupan; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2081 (class 0 OID 188077)
-- Dependencies: 166
-- Data for Name: rfr_paleta; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2083 (class 0 OID 188090)
-- Dependencies: 168
-- Data for Name: rfr_patron; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_patron (pat_codi, pat_nombre, pat_descri, pat_ejecut, pat_codigo) VALUES (1, 'Moneda, euros', '', false, '#,##0.00 EUR');
INSERT INTO rfr_patron (pat_codi, pat_nombre, pat_descri, pat_ejecut, pat_codigo) VALUES (2, 'Tanto porciento', '', false, '#0.0# ''%''');


--
-- TOC entry 2073 (class 0 OID 188023)
-- Dependencies: 158 2083 2081 2082
-- Data for Name: rfr_compon; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2075 (class 0 OID 188044)
-- Dependencies: 160 2074
-- Data for Name: rfr_forseg; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2076 (class 0 OID 188049)
-- Dependencies: 161 2075
-- Data for Name: rfr_fsgrol; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2096 (class 0 OID 188190)
-- Dependencies: 181
-- Data for Name: rfr_valfir; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2077 (class 0 OID 188054)
-- Dependencies: 162 2075 2096
-- Data for Name: rfr_fsgvfi; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2101 (class 0 OID 188391)
-- Dependencies: 186
-- Data for Name: rfr_grupos; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2103 (class 0 OID 188406)
-- Dependencies: 188 2074 2101
-- Data for Name: rfr_grpfor; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2102 (class 0 OID 188396)
-- Dependencies: 187 2101
-- Data for Name: rfr_grpusu; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2078 (class 0 OID 188059)
-- Dependencies: 163
-- Data for Name: rfr_idioma; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('es', 0);
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('ca', 1);
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('en', 2);


--
-- TOC entry 2079 (class 0 OID 188064)
-- Dependencies: 164
-- Data for Name: rfr_mascar; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (1, 'maxlength', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (2, 'integer', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (3, 'float', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (4, 'minlength', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (5, 'intRange', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (6, 'floatRange', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (7, 'date', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (8, 'required', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (9, 'email', '', NULL);
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri, mas_variab) VALUES (10, 'mask', '', NULL);


--
-- TOC entry 2080 (class 0 OID 188072)
-- Dependencies: 165 2079
-- Data for Name: rfr_masvar; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (1, 'maxlength', 0);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (4, 'minlength', 0);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (5, 'min', 0);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (5, 'max', 1);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (6, 'min', 0);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (6, 'max', 1);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (7, 'datePatternStrict', 0);
INSERT INTO rfr_masvar (mva_codmas, mva_valor, mva_orden) VALUES (10, 'mask', 0);


--
-- TOC entry 2086 (class 0 OID 188116)
-- Dependencies: 171
-- Data for Name: rfr_punsal; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2087 (class 0 OID 188124)
-- Dependencies: 172 2074 2086
-- Data for Name: rfr_salida; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2085 (class 0 OID 188108)
-- Dependencies: 170 2071 2087
-- Data for Name: rfr_prosal; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2088 (class 0 OID 188129)
-- Dependencies: 173 2073
-- Data for Name: rfr_tracam; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2089 (class 0 OID 188137)
-- Dependencies: 174 2074 2071
-- Data for Name: rfr_trafor; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2090 (class 0 OID 188145)
-- Dependencies: 175 2073
-- Data for Name: rfr_tralab; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2091 (class 0 OID 188153)
-- Dependencies: 176 2082 2084
-- Data for Name: rfr_transf; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2092 (class 0 OID 188161)
-- Dependencies: 177 2082
-- Data for Name: rfr_trapan; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2104 (class 0 OID 188462)
-- Dependencies: 189 2073
-- Data for Name: rfr_trasec; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2093 (class 0 OID 188166)
-- Dependencies: 178 2072
-- Data for Name: rfr_traypa; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2094 (class 0 OID 188174)
-- Dependencies: 179 2084
-- Data for Name: rfr_trpeus; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rfr_trpeus (tpe_codper, tpe_nombre, tpe_descri, tpe_codidi) VALUES (1, 'Juvenil', '', 'es');
INSERT INTO rfr_trpeus (tpe_codper, tpe_nombre, tpe_descri, tpe_codidi) VALUES (2, 'Normal', '', 'es');
INSERT INTO rfr_trpeus (tpe_codper, tpe_nombre, tpe_descri, tpe_codidi) VALUES (3, 'CAIB_AZUL', '', 'es');


--
-- TOC entry 2098 (class 0 OID 188206)
-- Dependencies: 183 2073
-- Data for Name: rfr_valpos; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2095 (class 0 OID 188182)
-- Dependencies: 180 2071 2098
-- Data for Name: rfr_trvapo; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2100 (class 0 OID 188381)
-- Dependencies: 185 2074
-- Data for Name: rfr_usufor; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 2097 (class 0 OID 188198)
-- Dependencies: 182 2073 2079
-- Data for Name: rfr_valida; Type: TABLE DATA; Schema: public; Owner: sistra
--



-- Completed on 2014-01-27 09:08:15

--
-- PostgreSQL database dump complete
--

