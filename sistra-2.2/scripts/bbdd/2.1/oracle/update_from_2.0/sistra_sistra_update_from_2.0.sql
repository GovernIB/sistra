alter table STR_ESPNIV  add ETN_JNOCLA           VARCHAR2(1)       DEFAULT 'N'     not null ;

alter table STR_ESPNIV  add ETN_JNONN            VARCHAR2(1)      DEFAULT 'N'       not null ;

comment on column STR_ESPNIV.ETN_JNOCLA is
'Indica si se oculta la clave de tramitacion en el pdf de justificante estandard';

comment on column STR_ESPNIV.ETN_JNONN is
'Indica si se oculta el nif y nombre en el pdf de justificante estandard';