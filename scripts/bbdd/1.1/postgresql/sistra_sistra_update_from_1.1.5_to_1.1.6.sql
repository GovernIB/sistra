ALTER TABLE STR_TRAMIT ADD TRA_IDPROC VARCHAR(100);

comment on column STR_TRAMIT.TRA_IDPROC is
'Identificador del procedimiento al que pertenece el trámite';

UPDATE STR_TRAMIT SET TRA_IDPROC = TRA_IDENTI;


ALTER TABLE STR_TRAMIT  alter column  TRA_IDPROC  set not null;

--

alter table STR_ESPNIV add ETN_PERSMS VARCHAR(1) default 'N' not null;

alter table STR_ESPNIV drop column ETN_AVISOS;

