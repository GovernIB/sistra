ALTER TABLE STR_TRAMIT ADD TRA_IDPROC VARCHAR2(100);

comment on column STR_TRAMIT.TRA_IDPROC is
'Identificador del procedimiento al que pertenece el trámite';

UPDATE STR_TRAMIT SET TRA_IDPROC = TRA_IDENTI;
COMMIT;

ALTER TABLE STR_TRAMIT  MODIFY  TRA_IDPROC  not null;
 

alter table STR_ESPNIV add ETN_PERSMS VARCHAR2(1) default 'N' not null;

alter table STR_ESPNIV add ETN_AVISO2 BLOB;

update STR_ESPNIV set ETN_AVISO2 = ETN_AVISOS;
commit;

alter table STR_ESPNIV drop column ETN_AVISOS;

alter table STR_ESPNIV add ETN_AVISOS VARCHAR2(1) default 'N' not null;

update  STR_ESPNIV set ETN_AVISOS = 'X' where ETN_CODIGO   IN ( 
SELECT  TNV_CODETN
FROM STR_TRANIV);

commit;