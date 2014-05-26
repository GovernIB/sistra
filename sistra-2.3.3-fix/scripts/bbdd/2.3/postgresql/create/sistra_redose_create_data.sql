--
-- PostgreSQL database dump
--


--
-- TOC entry 1976 (class 0 OID 0)
-- Dependencies: 142
-- Name: rds_seqapl; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqapl', 1, false);


--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 143
-- Name: rds_seqdoc; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqdoc', 1, false);


--
-- TOC entry 1978 (class 0 OID 0)
-- Dependencies: 144
-- Name: rds_seqfir; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqfir', 1, false);


--
-- TOC entry 1979 (class 0 OID 0)
-- Dependencies: 145
-- Name: rds_seqfor; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqfor', 6, true);


--
-- TOC entry 1980 (class 0 OID 0)
-- Dependencies: 171
-- Name: rds_seqlgd; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqlgd', 1, false);


--
-- TOC entry 1981 (class 0 OID 0)
-- Dependencies: 146
-- Name: rds_seqlog; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqlog', 1, false);


--
-- TOC entry 1982 (class 0 OID 0)
-- Dependencies: 147
-- Name: rds_seqmod; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqmod', 11, true);


--
-- TOC entry 1983 (class 0 OID 0)
-- Dependencies: 148
-- Name: rds_seqpla; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqpla', 2, true);


--
-- TOC entry 1984 (class 0 OID 0)
-- Dependencies: 149
-- Name: rds_seqpli; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqpli', 2, true);


--
-- TOC entry 1985 (class 0 OID 0)
-- Dependencies: 150
-- Name: rds_seqtiu; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqtiu', 1, false);


--
-- TOC entry 1986 (class 0 OID 0)
-- Dependencies: 151
-- Name: rds_sequbi; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_sequbi', 2, true);


--
-- TOC entry 1987 (class 0 OID 0)
-- Dependencies: 152
-- Name: rds_sequso; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_sequso', 1, false);


--
-- TOC entry 1988 (class 0 OID 0)
-- Dependencies: 153
-- Name: rds_seqver; Type: SEQUENCE SET; Schema: public; Owner: sistra
--

SELECT pg_catalog.setval('rds_seqver', 11, true);


--
-- TOC entry 1960 (class 0 OID 188541)
-- Dependencies: 158
-- Data for Name: rds_format; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_format (for_id, for_class, for_desc) VALUES (1, 'es.caib.redose.persistence.formateadores.FormateadorPdfFormularios', 'Formateador basado en plantillas PDF');
INSERT INTO rds_format (for_id, for_class, for_desc) VALUES (2, 'es.caib.redose.persistence.formateadores.FormateadorPdfPagos', 'Formateador basado en plantillas PDF para Justificantes de Pago');
INSERT INTO rds_format (for_id, for_class, for_desc) VALUES (3, 'es.caib.redose.persistence.formateadores.FormateadorPdfJustificante', 'Formateador específico para Justificante');
INSERT INTO rds_format (for_id, for_class, for_desc) VALUES (4, 'es.caib.redose.persistence.formateadores.FormateadorPdfJustificanteCopiaInteresado', 'Formateador específico para Justificante (copia interesado)');
INSERT INTO rds_format (for_id, for_class, for_desc) VALUES (5, 'es.caib.redose.persistence.formateadores.FormateadorPdfJasper', 'Formateador basado en reportes JasperReport');
INSERT INTO rds_format (for_id, for_class, for_desc) VALUES (6, 'es.caib.redose.persistence.formateadores.FormateadorPdfAsiento', 'Formateador específico para Asiento');


--
-- TOC entry 1961 (class 0 OID 188549)
-- Dependencies: 159
-- Data for Name: rds_idioma; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_idioma (idi_codigo, idi_nombre) VALUES ('es', 'Castellano');
INSERT INTO rds_idioma (idi_codigo, idi_nombre) VALUES ('ca', 'Catalán');


--
-- TOC entry 1963 (class 0 OID 188562)
-- Dependencies: 161
-- Data for Name: rds_modelo; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (1, 'GE0001JUSTIF', 'Justificant', 'Justificante', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (2, 'GE0002ASIENTO', 'ASIENTO REGISTRAL', 'ASIENTO REGISTRAL', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (3, 'GE0003DATPROP', 'DATOS PROPIOS TRAMITE', 'DATOS PROPIOS TRAMITE', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (4, 'GE0004DOCID', 'Documento de Identidad', 'Documento genérico de identificación (NIF / NIE / CIF)', 'N', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (5, 'GE0005ANEXGEN', 'Anexo genérico', 'Documento para anexos genéricos que no tienen un modelo particular o que no es interesante modelar de forma individual', 'N', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (6, 'GE0006PAGO', 'Datos de Pago (Presencial y Telemático)', 'Documento para datos del pago (independiente del tipo de pago)', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (7, 'GE0008AVINOT', 'Aviso de notificacion', 'Modelo para aviso de notificación', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (8, 'GE0009OFIREM', 'Oficio de remisión', 'Oficio de remisión para notificaciones', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (9, 'GE0011NOTIFICA', 'DOCUMENTO DE NOTIFICACION', 'DOCUMENTO ASOCIADO A UN REGISTRO DE SALIDA', 'N', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (10, 'GE0012DELEGA', 'Autorización de delegación', 'Autorización de delegación', 'S', 'N');
INSERT INTO rds_modelo (mod_codigo, mod_modelo, mod_nombre, mod_desc, mod_estruc, mod_custod) VALUES (11, 'GE0013NOTIFEXT', 'Modelo documento externo notificacion', 'Usado para documentos externos de anexos de notificaciones y avisos en los que se indica una url al documento. En el redose se almacenara un xml con la url de acceso', 'S', 'N');


--
-- TOC entry 1970 (class 0 OID 188616)
-- Dependencies: 168 1963
-- Data for Name: rds_vers; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (1, 1, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (2, 2, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (3, 3, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (4, 4, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (5, 5, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (6, 6, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (7, 7, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (8, 8, 1, 'Versión 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (9, 9, 1, 'NOTIFICACION');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (10, 10, 1, 'Version 1');
INSERT INTO rds_vers (ver_codigo, ver_codmod, ver_versio, ver_desc) VALUES (11, 11, 1, 'VERSION 1');


--
-- TOC entry 1965 (class 0 OID 188579)
-- Dependencies: 163 1960 1970
-- Data for Name: rds_planti; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_planti (pla_codigo, pla_codver, pla_tipo, pla_format, pla_defect, pla_barcod, pla_sello) VALUES (1, 1, 'PDF', 3, 'S', 'S', 'N');
INSERT INTO rds_planti (pla_codigo, pla_codver, pla_tipo, pla_format, pla_defect, pla_barcod, pla_sello) VALUES (2, 6, 'PDF', 2, 'S', 'S', 'N');


--
-- TOC entry 1964 (class 0 OID 188574)
-- Dependencies: 162 1961 1965
-- Data for Name: rds_plaidi; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_plaidi (pli_codigo, pli_codpla, pli_codidi, pli_nomfic) VALUES (1, 1, 'es', 'cambiar.txt');
INSERT INTO rds_plaidi (pli_codigo, pli_codpla, pli_codidi, pli_nomfic) VALUES (2, 2, 'es', 'cambiar.txt');


--
-- TOC entry 1956 (class 0 OID 188508)
-- Dependencies: 154 1964
-- Data for Name: rds_arcpli; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_arcpli (arp_codpli, arp_datos) VALUES (1, '');
INSERT INTO rds_arcpli (arp_codpli, arp_datos) VALUES (2, '');


--
-- TOC entry 1968 (class 0 OID 188599)
-- Dependencies: 166
-- Data for Name: rds_ubica; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_ubica (ubi_codigo, ubi_ident, ubi_nombre, ubi_plugin, ubi_defect) VALUES (2, 'FILE', 'Almacenamiento externo en ficheros', 'es.caib.redose.persistence.plugin.PluginAlmacenamientoFileSystem', 'N');
INSERT INTO rds_ubica (ubi_codigo, ubi_ident, ubi_nombre, ubi_plugin, ubi_defect) VALUES (1, 'RDS', 'RDS defecto (BBDD)', 'es.caib.redose.persistence.plugin.PluginDefaultRDS', 'S');


--
-- TOC entry 1957 (class 0 OID 188516)
-- Dependencies: 155 1965 1968 1970
-- Data for Name: rds_docum; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1973 (class 0 OID 188713)
-- Dependencies: 172
-- Data for Name: rds_ficext; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1958 (class 0 OID 188524)
-- Dependencies: 156
-- Data for Name: rds_ficher; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1959 (class 0 OID 188532)
-- Dependencies: 157 1957
-- Data for Name: rds_firmas; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1972 (class 0 OID 188698)
-- Dependencies: 170 1957
-- Data for Name: rds_logegd; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1966 (class 0 OID 188589)
-- Dependencies: 164
-- Data for Name: rds_tioper; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('BODO', 'BORRADO AUTOMATICO DE DOCUMENTO SIN USOS');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('ELDO', 'ELIMINAR DOCUMENTO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('AFDO', 'ASOCIAR FIRMA DOCUMENTO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('ACDO', 'ACTUALIZAR DOCUMENTO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('CODO', 'CONSULTAR DOCUMENTO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('CODF', 'CONSULTAR DOCUMENTO FORMATEADO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('NUUS', 'NUEVO USO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('ELUS', 'ELIMINAR USO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('ELUO', 'ELIMININAR USOS');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('NUDO', 'NUEVO DOCUMENTO');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('LIUS', 'LISTAR USOS');
INSERT INTO rds_tioper (top_codigo, top_nombre) VALUES ('ACFI', 'ACTUALIZAR FICHERO');


--
-- TOC entry 1962 (class 0 OID 188554)
-- Dependencies: 160 1966
-- Data for Name: rds_logope; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1967 (class 0 OID 188594)
-- Dependencies: 165
-- Data for Name: rds_tiuso; Type: TABLE DATA; Schema: public; Owner: sistra
--

INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('ENV', 'ENVIO');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('EDI', 'EDI');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('PAD', 'PAD');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('RTE', 'RTE');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('EXP', 'EXP');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('RTS', 'RTS');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('TRA', 'TRA');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('PRE', 'PRE');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('BTE', 'BTE');
INSERT INTO rds_tiuso (tiu_codigo, tiu_nombre) VALUES ('DLG', 'DELEGACION');


--
-- TOC entry 1969 (class 0 OID 188610)
-- Dependencies: 167 1957 1967
-- Data for Name: rds_usos; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1971 (class 0 OID 188688)
-- Dependencies: 169 1957
-- Data for Name: rds_vercus; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1954 (class 0 OID 188475)
-- Dependencies: 140
-- Data for Name: sc_wl_usuari; Type: TABLE DATA; Schema: public; Owner: sistra
--



--
-- TOC entry 1955 (class 0 OID 188478)
-- Dependencies: 141
-- Data for Name: sc_wl_usugru; Type: TABLE DATA; Schema: public; Owner: sistra
--



-- Completed on 2014-01-27 09:18:32

--
-- PostgreSQL database dump complete
--

