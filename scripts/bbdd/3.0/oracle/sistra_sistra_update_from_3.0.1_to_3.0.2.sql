-- SISTRA: DIRECCION INTERESADOS
alter table STR_ESPNIV add ETN_RTEDAT BLOB;
alter table STR_ESPNIV add ETN_RDODAT BLOB;

comment on column STR_ESPNIV.ETN_RTEDAT is
'Datos desglosados representante (nif, nombre, direccion, email,...)';
comment on column STR_ESPNIV.ETN_RDODAT is
'Datos desglosados representado (nif, nombre, direccion, email,...)';
