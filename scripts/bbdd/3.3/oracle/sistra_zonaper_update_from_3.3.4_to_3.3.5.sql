alter table ZPE_TRAPER  add  TPE_PERSIS           VARCHAR2(1)                    default 'S' not null;
comment on column ZPE_TRAPER.TPE_PERSIS is 'INDICA QUE ES PERSISTENTE ';

alter table ZPE_TPEBCK add  TPB_IDEPRO           VARCHAR2(100);
alter table ZPE_TPEBCK add  TPB_PERSIS           VARCHAR2(1)                    default 'S' not null;

comment on column ZPE_TPEBCK.TPB_IDEPRO is 'ID PROCEDIMIENTO';

comment on column ZPE_TPEBCK.TPB_PERSIS is  'INDICA QUE ES PERSISTENTE ';
