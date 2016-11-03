alter table RDS_DOCUM add DOC_CSV VARCHAR2(50);
comment on column RDS_DOCUM.DOC_CSV is 'CSV';

create unique index RDS_DOC_AK on RDS_DOCUM (
   DOC_CSV ASC
);

create table RDS_TABCSV  (
   TCS_CODIGO           VARCHAR2(1)                     not null,
   TCS_CLAVES           BLOB                            not null
);

comment on table RDS_TABCSV is
'TABLA TRANSFORMACION CSV';

comment on column RDS_TABCSV.TCS_CODIGO is
'CODIGO';

comment on column RDS_TABCSV.TCS_CLAVES is
'CLAVES';

alter table RDS_TABCSV
   add constraint RDS_TCS_PK primary key (TCS_CODIGO);

