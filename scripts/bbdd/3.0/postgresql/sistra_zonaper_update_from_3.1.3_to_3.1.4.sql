create sequence ZPE_SEQVMV;

create table ZPE_LOGVMV  (
   VMV_CODIGO           bigint                   not null,
   VMV_IDPER            character varying(50)                    not null,
   VMV_MOVIL            character varying(10)                    not null,
   VMV_CODSMS           character varying(4)                     not null,
   VMV_FECHA            timestamp without time zone                            not null
);

comment on table ZPE_LOGVMV is
'Log verificacion movil';

comment on column ZPE_LOGVMV.VMV_CODIGO is
'ID SECUENCIAL';

comment on column ZPE_LOGVMV.VMV_IDPER is
'ID PERSISTENCIA';

comment on column ZPE_LOGVMV.VMV_MOVIL is
'MOVIL';

comment on column ZPE_LOGVMV.VMV_CODSMS is
'CODIGO SMS';

comment on column ZPE_LOGVMV.VMV_FECHA is
'FECHA VERIFICACION';

alter table ZPE_LOGVMV
   add constraint ZPE_VMV_PK primary key (VMV_CODIGO);
