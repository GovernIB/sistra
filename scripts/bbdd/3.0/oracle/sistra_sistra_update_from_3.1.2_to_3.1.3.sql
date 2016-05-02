-- SISTRA: DEBUG POR TRAMITE
alter table STR_TRAVER  add  TRV_DEBUG            VARCHAR2(1)                    default 'N' not null;
comment on column STR_TRAVER.TRV_DEBUG is 'Indica si el debug esta habilitado';