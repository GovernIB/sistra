alter table RDS_DOCUM add DOC_CSV character varying(50);
comment on column RDS_DOCUM.DOC_CSV is 'CSV';

CREATE UNIQUE INDEX RDS_DOC_AK ON RDS_DOCUM USING btree (DOC_CSV);

create table RDS_TABCSV  (
   TCS_CODIGO          character varying(1)    not null,
   TCS_CLAVES          bytea                   not null
);

comment on table RDS_TABCSV is
'TABLA TRANSFORMACION CSV';

comment on column RDS_TABCSV.TCS_CODIGO is
'CODIGO';

comment on column RDS_TABCSV.TCS_CLAVES is
'CLAVES';

alter table RDS_TABCSV
   add constraint RDS_TCS_PK primary key (TCS_CODIGO);

