--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- Name: rfr_secarc; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secarc', 1, false);


--
-- Name: rfr_secayu; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secayu', 1, false);


--
-- Name: rfr_seccom; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_seccom', 1, false);


--
-- Name: rfr_secfor; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secfor', 1, false);


--
-- Name: rfr_secmas; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secmas', 1, false);


--
-- Name: rfr_secpal; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secpal', 1, false);


--
-- Name: rfr_secpan; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secpan', 1, false);


--
-- Name: rfr_secpat; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secpat', 1, false);


--
-- Name: rfr_secper; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secper', 1, false);


--
-- Name: rfr_secprs; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secprs', 1, false);


--
-- Name: rfr_secpsa; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secpsa', 1, false);


--
-- Name: rfr_secsal; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secsal', 1, false);


--
-- Name: rfr_secvap; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secvap', 1, false);


--
-- Name: rfr_secvfi; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_secvfi', 1, false);


--
-- Name: rfr_seqval; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('rfr_seqval', 1, false);


--
-- Data for Name: rfr_archiv; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_versio; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_versio VALUES (0, 'VERSION 0: INICIAL IBIT', '2006-01-01 00:00:00', NULL);
INSERT INTO rfr_versio VALUES (1, 'VERSION 1: MODIFICACION INDRA (FEBRERO 08)', '2008-02-09 00:00:00', 'V1');


--
-- Data for Name: rfr_formul; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_pantal; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_perusu; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_perusu VALUES (1, 'JUVENIL', '/estilo_azul');
INSERT INTO rfr_perusu VALUES (2, 'CIUDADANO', '/estilo_naranja');
INSERT INTO rfr_perusu VALUES (3, 'CAIB_AZUL', '/estilo_caib');


--
-- Data for Name: rfr_ayupan; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_paleta; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_patron; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_patron VALUES (1, 'Moneda, euros', NULL, false, '#,##0.00 EUR');
INSERT INTO rfr_patron VALUES (2, 'Tanto porciento', NULL, false, '#0.0# ''%''');


--
-- Data for Name: rfr_compon; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_forseg; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_fsgrol; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_valfir; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_fsgvfi; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_idioma; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_idioma VALUES ('ca', 3);
INSERT INTO rfr_idioma VALUES ('es', 0);
INSERT INTO rfr_idioma VALUES ('it', 1);
INSERT INTO rfr_idioma VALUES ('pt', 2);


--
-- Data for Name: rfr_mascar; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_mascar VALUES (1, 'maxlength', NULL, NULL);
INSERT INTO rfr_mascar VALUES (2, 'integer', NULL, NULL);
INSERT INTO rfr_mascar VALUES (3, 'float', NULL, NULL);
INSERT INTO rfr_mascar VALUES (4, 'minlength', NULL, NULL);
INSERT INTO rfr_mascar VALUES (5, 'intRange', NULL, NULL);
INSERT INTO rfr_mascar VALUES (6, 'floatRange', NULL, NULL);
INSERT INTO rfr_mascar VALUES (7, 'date', NULL, NULL);
INSERT INTO rfr_mascar VALUES (8, 'required', NULL, NULL);
INSERT INTO rfr_mascar VALUES (9, 'email', NULL, NULL);
INSERT INTO rfr_mascar VALUES (10, 'mask', NULL, NULL);


--
-- Data for Name: rfr_masvar; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_masvar VALUES (1, 'maxlength', 0);
INSERT INTO rfr_masvar VALUES (4, 'minlength', 0);
INSERT INTO rfr_masvar VALUES (5, 'min', 0);
INSERT INTO rfr_masvar VALUES (5, 'max', 1);
INSERT INTO rfr_masvar VALUES (6, 'min', 0);
INSERT INTO rfr_masvar VALUES (6, 'max', 1);
INSERT INTO rfr_masvar VALUES (7, 'datePatternStrict', 0);
INSERT INTO rfr_masvar VALUES (10, 'mask', 0);


--
-- Data for Name: rfr_punsal; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_salida; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_prosal; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_tracam; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_trafor; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_tralab; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_trapan; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_traypa; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_trpeus; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO rfr_trpeus VALUES (1, 'Juvenil', NULL, 'es');
INSERT INTO rfr_trpeus VALUES (2, 'Normal', NULL, 'es');
INSERT INTO rfr_trpeus VALUES (3, 'CAIB_AZUL', NULL, 'es');


--
-- Data for Name: rfr_valpos; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_trvapo; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: rfr_valida; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- PostgreSQL database dump complete
--

