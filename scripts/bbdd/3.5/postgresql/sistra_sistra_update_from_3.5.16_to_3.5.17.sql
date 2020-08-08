alter table STR_TRAVER add TRV_LIMTIP VARCHAR2(1)  default 'N' not null;
alter table STR_TRAVER add TRV_LIMNUM bigint;
alter table STR_TRAVER add TRV_LIMINT bigint;

comment on column STR_TRAVER.TRV_LIMTIP is
'Liimite tramitacion: N, I (sin limite, iniciados x intervalo, ...)';

comment on column STR_TRAVER.TRV_LIMNUM is
'Limite tramitacion: número x intervalo';

comment on column STR_TRAVER.TRV_LIMINT is
'Limite tramitacion: minutos intervalo';