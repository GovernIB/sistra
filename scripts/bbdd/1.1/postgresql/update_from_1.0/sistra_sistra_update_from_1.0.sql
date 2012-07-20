ALTER TABLE STR_TRAVER ADD TRV_CONWSV VARCHAR(10);
update str_traver set trv_conwsv = 'v1' where trv_contip = 'W';

ALTER TABLE STR_DOMIN ADD DOM_WSVER VARCHAR(10);
update str_domin set dom_wsver = 'v1' where dom_tipo = 'W';

ALTER TABLE STR_TRAMIT ALTER COLUMN TRA_IDENTI TYPE VARCHAR(20);
ALTER TABLE STR_DOMIN ALTER COLUMN DOM_IDENTI TYPE VARCHAR(20);
ALTER TABLE STR_DOCUM ALTER COLUMN DOC_MODELO TYPE VARCHAR(20);


INSERT INTO STR_IDIOMA ( IDI_CODIGO, IDI_ORDEN ) VALUES ('en', 3);

-- From 1.1.5

alter table STR_DOCUM  add DOC_FORAJU VARCHAR(1) default 'N';

comment on column STR_DOCUM.DOC_FORAJU is
'Para Formulario: se indica si el formulario debe anexarse al justificante';


-- From 1.1.6

ALTER TABLE STR_TRAMIT ADD TRA_IDPROC VARCHAR(100);

comment on column STR_TRAMIT.TRA_IDPROC is
'Identificador del procedimiento al que pertenece el trámite';

UPDATE STR_TRAMIT SET TRA_IDPROC = TRA_IDENTI;
COMMIT;

ALTER TABLE STR_TRAMIT  alter column  TRA_IDPROC  set not null;

--

alter table STR_ESPNIV add ETN_PERSMS VARCHAR(1) default 'N' not null;

alter table STR_ESPNIV drop column ETN_AVISOS;
