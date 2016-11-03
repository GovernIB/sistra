alter table STR_ESPNIV  add ETN_ALETRA VARCHAR(1) default 'N' not null;
alter table STR_ESPNIV  add ETN_ALESMS VARCHAR(1) default 'N' not null;   

comment on column STR_ESPNIV.ETN_ALETRA is
'Indica si se generan alertas de tramitacion (previas envio tramite): N: No permite /  S Si permite, el ciudadano elige / O: Obligatoria notificación telemática / X: No especificada';
comment on column STR_ESPNIV.ETN_ALESMS is
'Indica si se permiten sms en los avisos de notificacion telematica (S/N)';


alter table STR_TRAVER add TRV_REGAUT VARCHAR(1) default 'N' not null;

comment on column STR_TRAVER.TRV_REGAUT is
'Registro automatico: al llegar al paso registrar se dispara automaticamente el envio';