ALTER TABLE STR_TRAVER ADD TRV_CONWSV VARCHAR(10);
update str_traver set trv_conwsv = 'v1' where trv_contip = 'W';

ALTER TABLE STR_DOMIN ADD DOM_WSVER VARCHAR(10);
update str_domin set dom_wsver = 'v1' where dom_tipo = 'W';

ALTER TABLE STR_TRAMIT ALTER COLUMN TRA_IDENTI TYPE VARCHAR(20);
ALTER TABLE STR_DOMIN ALTER COLUMN DOM_IDENTI TYPE VARCHAR(20);
ALTER TABLE STR_DOCUM ALTER COLUMN DOC_MODELO TYPE VARCHAR(20);
