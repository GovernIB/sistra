--
-- Variables inicials sessio Postgresql
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

-- Seqüències

SELECT pg_catalog.setval('rfr_secarc', 1, false);
SELECT pg_catalog.setval('rfr_secayu', 1, false);
SELECT pg_catalog.setval('rfr_seccom', 1, false);
SELECT pg_catalog.setval('rfr_secfor', 1, false);
SELECT pg_catalog.setval('rfr_secmas', 11, false);
SELECT pg_catalog.setval('rfr_secpal', 1, false);
SELECT pg_catalog.setval('rfr_secpan', 1, false);
SELECT pg_catalog.setval('rfr_secpat', 3, false);
SELECT pg_catalog.setval('rfr_secper', 4, false);
SELECT pg_catalog.setval('rfr_secprs', 1, false);
SELECT pg_catalog.setval('rfr_secpsa', 1, false);
SELECT pg_catalog.setval('rfr_secsal', 1, false);
SELECT pg_catalog.setval('rfr_secvap', 1, false);
SELECT pg_catalog.setval('rfr_secvfi', 1, false);
SELECT pg_catalog.setval('rfr_seqval', 1, false);

-- Inserts.

INSERT INTO rfr_versio VALUES (0, 'VERSION 0: INICIAL IBIT', '2006-01-01 00:00:00', NULL);
INSERT INTO rfr_versio VALUES (1, 'VERSION 1: MODIFICACION INDRA (FEBRERO 08)', '2008-02-09 00:00:00', 'V1');

INSERT INTO rfr_perusu VALUES (1, 'JUVENIL', '/estilo_azul');
INSERT INTO rfr_perusu VALUES (2, 'CIUDADANO', '/estilo_naranja');
INSERT INTO rfr_perusu VALUES (3, 'CAIB_AZUL', '/estilo_caib');

INSERT INTO rfr_patron VALUES (1, 'Moneda, euros', NULL, false, '#,##0.00 EUR');
INSERT INTO rfr_patron VALUES (2, 'Tanto porciento', NULL, false, '#0.0# ''%''');

INSERT INTO rfr_idioma VALUES ('ca', 3);
INSERT INTO rfr_idioma VALUES ('es', 0);
INSERT INTO rfr_idioma VALUES ('it', 1);
INSERT INTO rfr_idioma VALUES ('pt', 2);

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

INSERT INTO rfr_masvar VALUES (1, 'maxlength', 0);
INSERT INTO rfr_masvar VALUES (4, 'minlength', 0);
INSERT INTO rfr_masvar VALUES (5, 'min', 0);
INSERT INTO rfr_masvar VALUES (5, 'max', 1);
INSERT INTO rfr_masvar VALUES (6, 'min', 0);
INSERT INTO rfr_masvar VALUES (6, 'max', 1);
INSERT INTO rfr_masvar VALUES (7, 'datePatternStrict', 0);
INSERT INTO rfr_masvar VALUES (10, 'mask', 0);

INSERT INTO rfr_trpeus VALUES (1, 'Juvenil', NULL, 'es');
INSERT INTO rfr_trpeus VALUES (2, 'Normal', NULL, 'es');
INSERT INTO rfr_trpeus VALUES (3, 'CAIB_AZUL', NULL, 'es');
