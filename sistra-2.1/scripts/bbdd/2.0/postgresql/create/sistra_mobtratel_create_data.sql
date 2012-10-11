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

SELECT pg_catalog.setval('mob_seqenv', 1, false);
SELECT pg_catalog.setval('mob_seqmse', 1, false);
SELECT pg_catalog.setval('mob_seqmss', 1, false);
SELECT pg_catalog.setval('mob_seqper', 1, false);

-- Inserts.

INSERT INTO mob_cuenta VALUES ('TEST', 'CUENTA DE TEST (Para eliminar posteriormente)', 'es.caib.mobtratel.mailTest', 'ID SMS', 1);
