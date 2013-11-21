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

SELECT pg_catalog.setval('rds_seqdoc', 1, false);
SELECT pg_catalog.setval('rds_seqfir', 1, false);
SELECT pg_catalog.setval('rds_seqfor', 6, false);
SELECT pg_catalog.setval('rds_seqlog', 1, false);
SELECT pg_catalog.setval('rds_seqmod', 9, false);
SELECT pg_catalog.setval('rds_seqpla', 3, false);
SELECT pg_catalog.setval('rds_seqpli', 3, false);
SELECT pg_catalog.setval('rds_sequbi', 2, false);
SELECT pg_catalog.setval('rds_sequso', 1, false);
SELECT pg_catalog.setval('rds_seqver', 9, false);

-- Inserts.

INSERT INTO rds_format VALUES (1, 'es.caib.redose.persistence.formateadores.FormateadorPdfFormularios', 'Formateador basado en plantillas PDF');
INSERT INTO rds_format VALUES (2, 'es.caib.redose.persistence.formateadores.FormateadorPdfPagos', 'Formateador basado en plantillas PDF para Justificantes de Pago');
INSERT INTO rds_format VALUES (3, 'es.caib.redose.persistence.formateadores.FormateadorPdfJustificante', 'Formateador específico para Justificante');
INSERT INTO rds_format VALUES (4, 'es.caib.redose.persistence.formateadores.FormateadorPdfJustificanteCopiaInteresado', 'Formateador específico para Justificante (copia interesado)');
INSERT INTO rds_format VALUES (5, 'es.caib.redose.persistence.formateadores.FormateadorPdfJasper', 'Formateador basado en reportes JasperReport');

INSERT INTO rds_modelo VALUES (1, 'GE0001JUSTIF', 'Justificant', 'Justificante', 'S');
INSERT INTO rds_modelo VALUES (2, 'GE0002ASIENTO', 'ASIENTO REGISTRAL', 'ASIENTO REGISTRAL', 'S');
INSERT INTO rds_modelo VALUES (3, 'GE0003DATPROP', 'DATOS PROPIOS TRAMITE', 'DATOS PROPIOS TRAMITE', 'S');
INSERT INTO rds_modelo VALUES (4, 'GE0004DOCID', 'Documento de Identidad', 'Documento genérico de identificación (NIF / NIE / CIF)', 'N');
INSERT INTO rds_modelo VALUES (5, 'GE0005ANEXGEN', 'Anexo genérico', 'Documento para anexos genéricos que no tienen un modelo particular o que no es interesante modelar de forma individual', 'N');
INSERT INTO RDS_MODELO (MOD_CODIGO, MOD_MODELO, MOD_NOMBRE,MOD_DESC, MOD_ESTRUC) VALUES ( nextval('RDS_SEQMOD'), 'GE0011NOTIFICA', 'DOCUMENTO DE NOTIFICACION', 'DOCUMENTO ASOCIADO A UN REGISTRO DE SALIDA' , 'N');

INSERT INTO rds_modelo VALUES (6, 'GE0006PAGO', 'Datos de Pago (Presencial y Telemático)', 'Documento para datos del pago (independiente del tipo de pago)', 'S');
INSERT INTO rds_modelo VALUES (7, 'GE0008AVINOT', 'Aviso de notificacion', 'Modelo para aviso de notificación', 'S');
INSERT INTO rds_modelo VALUES (8, 'GE0009OFIREM', 'Oficio de remisión', 'Oficio de remisión para notificaciones', 'S');

INSERT INTO rds_vers VALUES (1, 1, 'Versión 1', 1);
INSERT INTO rds_vers VALUES (2, 1, 'Versión 1', 2);
INSERT INTO rds_vers VALUES (3, 1, 'Versión 1', 3);
INSERT INTO rds_vers VALUES (4, 1, 'Versión 1', 4);
INSERT INTO rds_vers VALUES (5, 1, 'Versión 1', 5);
INSERT INTO rds_vers VALUES (6, 1, 'Versión 1', 6);
INSERT INTO rds_vers VALUES (7, 1, 'Versión 1', 7);
INSERT INTO rds_vers VALUES (8, 1, 'Versión 1', 8);
INSERT INTO RDS_VERS ( VER_CODIGO, VER_CODMOD, VER_VERSIO, VER_DESC)  VALUES ( nextval('RDS_SEQVER'), (SELECT MOD_CODIGO FROM RDS_MODELO WHERE MOD_MODELO='GE0011NOTIFICA'),1,'NOTIFICACION');

INSERT INTO rds_planti VALUES (1, 1, 3, 'PDF', 'S', 'S', 'N');
INSERT INTO rds_planti VALUES (2, 6, 2, 'PDF', 'S', 'S', 'N');

INSERT INTO rds_plaidi VALUES (1, 1, 'es', 'cambiar.txt');
INSERT INTO rds_plaidi VALUES (2, 2, 'es', 'cambiar.txt');

INSERT INTO rds_arcpli VALUES (1, '');
INSERT INTO rds_arcpli VALUES (2, '');

INSERT INTO rds_ubica VALUES (1, 'RDS', 'RDS defecto', 'es.caib.redose.persistence.plugin.PluginDefaultRDS');

insert into RDS_UBICA (UBI_CODIGO, UBI_IDENT, UBI_NOMBRE, UBI_PLUGIN, UBI_DEFECT)  
 values (NEXTVAL('RDS_SEQUBI'), 'FILE', 'Almacenamiento externo en ficheros', 
 'es.caib.redose.persistence.plugin.PluginAlmacenamientoFileSystem','N');
 
update RDS_UBICA set  UBI_DEFECT = 'S' where UBI_IDENT = 'RDS';

INSERT INTO rds_idioma VALUES ('ca', 'Catalán');
INSERT INTO rds_idioma VALUES ('es', 'Castellano');

INSERT INTO rds_tioper VALUES ('ACDO', 'ACTUALIZAR DOCUMENTO');
INSERT INTO rds_tioper VALUES ('ACFI', 'ACTUALIZAR FICHERO');
INSERT INTO rds_tioper VALUES ('AFDO', 'ASOCIAR FIRMA DOCUMENTO');
INSERT INTO rds_tioper VALUES ('BODO', 'BORRADO AUTOMATICO DE DOCUMENTO SIN USOS');
INSERT INTO rds_tioper VALUES ('CODF', 'CONSULTAR DOCUMENTO FORMATEADO');
INSERT INTO rds_tioper VALUES ('CODO', 'CONSULTAR DOCUMENTO');
INSERT INTO rds_tioper VALUES ('ELDO', 'ELIMINAR DOCUMENTO');
INSERT INTO rds_tioper VALUES ('ELUO', 'ELIMININAR USOS');
INSERT INTO rds_tioper VALUES ('ELUS', 'ELIMINAR USO');
INSERT INTO rds_tioper VALUES ('LIUS', 'LISTAR USOS');
INSERT INTO rds_tioper VALUES ('NUDO', 'NUEVO DOCUMENTO');
INSERT INTO rds_tioper VALUES ('NUUS', 'NUEVO USO');

INSERT INTO rds_tiuso VALUES ('BTE', 'BTE');
INSERT INTO rds_tiuso VALUES ('EDI', 'EDI');
INSERT INTO rds_tiuso VALUES ('ENV', 'ENVIO');
INSERT INTO rds_tiuso VALUES ('EXP', 'EXP');
INSERT INTO rds_tiuso VALUES ('PAD', 'PAD');
INSERT INTO rds_tiuso VALUES ('PRE', 'PRE');
INSERT INTO rds_tiuso VALUES ('RTE', 'RTE');
INSERT INTO rds_tiuso VALUES ('RTS', 'RTS');
INSERT INTO rds_tiuso VALUES ('TRA', 'TRA');

INSERT INTO RDS_MODELO (
   MOD_CODIGO, MOD_MODELO, MOD_NOMBRE, 
   MOD_DESC, MOD_ESTRUC, MOD_CUSTOD) 
VALUES (nextval('RDS_SEQMOD'), 'GE0012DELEGA','Autorización de delegación' ,
	   'Autorización de delegación', 'S', 'N');
	   
INSERT INTO RDS_VERS (
   VER_CODIGO, 
   VER_CODMOD, 
   VER_VERSIO, 
   VER_DESC) 
VALUES (
	nextval('RDS_SEQVER'), 
	(SELECT MOD_CODIGO FROM RDS_MODELO WHERE MOD_MODELO = 'GE0012DELEGA'),
	 1, 'Version 1');	   
	   
INSERT INTO RDS_TIUSO ( TIU_CODIGO, TIU_NOMBRE ) VALUES ('DLG', 'DELEGACION'); 

