-- SISTRA: Script que permite cambiar dinámicamente el procedimiento al que está asociado el trámite
alter table STR_ESPNIV add ETN_PRODST BLOB;

comment on column STR_ESPNIV.ETN_PRODST is
'Script que permite cambiar dinámicamente el procedimiento al que está asociado el trámite';