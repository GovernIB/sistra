-- SISTRA: DEBUG POR TRAMITE
alter table STR_TRAVER  add  TRV_DEBUG            VARCHAR2(1)                    default 'N' not null;
comment on column STR_TRAVER.TRV_DEBUG is 'Indica si el debug esta habilitado';

-- SISTRA: FIN TRAMITE AUTO PARA TRAMITES CON PAGO FINALIZADO
alter table STR_ESPNIV  add ETN_ALEFIN  VARCHAR2(1) default 'N' not null;
comment on column STR_ESPNIV.ETN_ALEFIN is
'Indica si se intenta finalizar automáticamente el trámite antes de realizar la alerta de trámites inacabados con pago realizado';
