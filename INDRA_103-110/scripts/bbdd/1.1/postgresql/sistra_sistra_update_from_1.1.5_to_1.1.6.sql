ALTER TABLE STR_TRAMIT ADD TRA_IDPROC VARCHAR(100);

comment on column STR_TRAMIT.TRA_IDPROC is
'Identificador del procedimiento al que pertenece el trámite';

UPDATE STR_TRAMIT SET TRA_IDPROC = TRA_IDENTI;
COMMIT;

ALTER TABLE STR_TRAMIT  alter column  TRA_IDPROC  set not null;
 