-- SISTRA: Script que permite cambiar din�micamente el procedimiento al que est� asociado el tr�mite
alter table STR_ESPNIV add ETN_PRODST BLOB;

comment on column STR_ESPNIV.ETN_PRODST is
'Script que permite cambiar din�micamente el procedimiento al que est� asociado el tr�mite';