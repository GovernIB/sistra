
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('es', 0);
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('it', 1);
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('pt', 2);
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('ca', 3);


INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'maxlength', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'integer', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'float', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'minlength', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'intRange', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'floatRange', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'date', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'required', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'email', '');
INSERT INTO rfr_mascar (mas_codi, mas_nombre, mas_descri) VALUES (RFR_SECMAS.NEXTVAL, 'mask', '');

INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (1,'maxlength',0);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (4,'minlength',0);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (5,'min',0);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (5,'max',1);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (6,'min',0);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (6,'max',1);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (7,'datePatternStrict',0);
INSERT INTO RFR_MASVAR (MVA_CODMAS, MVA_VALOR, MVA_ORDEN) VALUES (10,'mask',0);

INSERT INTO rfr_patron (pat_codi, pat_nombre, pat_descri, pat_ejecut, pat_codigo) VALUES (RFR_SECPAT.NEXTVAL, 'Moneda, euros', '', 0, '#,##0.00 EUR');
INSERT INTO rfr_patron (pat_codi, pat_nombre, pat_descri, pat_ejecut, pat_codigo) VALUES (RFR_SECPAT.NEXTVAL, 'Tanto porciento', '', 0, '#0.0# ''%''');


INSERT INTO rfr_perusu (per_codi, per_patico, per_codest) VALUES (RFR_SECPER.NEXTVAL, '/estilo_azul', 'JUVENIL');
INSERT INTO rfr_perusu (per_codi, per_patico, per_codest) VALUES (RFR_SECPER.NEXTVAL, '/estilo_naranja', 'CIUDADANO');
INSERT INTO rfr_perusu (per_codi, per_patico, per_codest) VALUES (RFR_SECPER.NEXTVAL, '/estilo_caib', 'CAIB_AZUL');

INSERT INTO rfr_trpeus (tpe_codper, tpe_nombre, tpe_descri, tpe_codidi) VALUES (1, 'Juvenil', '', 'es');
INSERT INTO rfr_trpeus (tpe_codper, tpe_nombre, tpe_descri, tpe_codidi) VALUES (2, 'Normal', '', 'es');
INSERT INTO rfr_trpeus (tpe_codper, tpe_nombre, tpe_descri, tpe_codidi) VALUES (3, 'CAIB_AZUL', '', 'es');

INSERT INTO RFR_VERSIO ( VER_CODIGO, VER_NOMBRE, VER_FECHA,
VER_SUFIX ) VALUES ( 
0, 'VERSION 0: INICIAL IBIT',  TO_Date( '01/01/2006 12:00:00 AM', 'MM/DD/YYYY HH:MI:SS AM')
, NULL); 
INSERT INTO RFR_VERSIO ( VER_CODIGO, VER_NOMBRE, VER_FECHA,
VER_SUFIX ) VALUES ( 
1, 'VERSION 1: MODIFICACION INDRA (FEBRERO 08)',  TO_Date( '02/09/2008 12:00:00 AM', 'MM/DD/YYYY HH:MI:SS AM')
, 'V1'); 

-- Dejamos solo castellano, catalan e ingles 
DELETE FROM rfr_idioma WHERE idi_codi = 'it';
DELETE FROM rfr_idioma WHERE idi_codi = 'pt';
UPDATE rfr_idioma SET idi_orden=1  WHERE idi_codi = 'ca';
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('en', 2);

commit;

-- V 2.3.0
Insert into FORMROL.RFR_VERSIO
   (VER_CODIGO, VER_NOMBRE, VER_FECHA, VER_SUFIX)
 Values
   (2, 'VERSION 2: ACTUALIZACION FRONTAL (OCTUBRE 2013)', TO_DATE('10/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'V2');
COMMIT;