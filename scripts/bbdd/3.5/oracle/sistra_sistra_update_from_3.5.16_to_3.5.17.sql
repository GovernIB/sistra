alter table STR_TRAVER add TRV_LIMTIP VARCHAR2(1 char)  default 'N' not null;
alter table STR_TRAVER add TRV_LIMNUM NUMBER(5);
alter table STR_TRAVER add TRV_LIMINT NUMBER(3);

comment on column STR_TRAVER.TRV_LIMTIP is
'Liimite tramitacion: N, I (sin limite, iniciados x intervalo, ...)';

comment on column STR_TRAVER.TRV_LIMNUM is
'Limite tramitacion: número x intervalo';

comment on column STR_TRAVER.TRV_LIMINT is
'Limite tramitacion: minutos intervalo';