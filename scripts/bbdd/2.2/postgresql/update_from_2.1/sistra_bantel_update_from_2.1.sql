create sequence BTE_SEQCFU;

create sequence BTE_SEQFIF;

create sequence BTE_SEQFUE;

create sequence BTE_SEQVCF;

create table BTE_CAMFUE  (
   CFU_CODIGO           BIGINT                      not null,
   CFU_CODFUE           BIGINT                      not null,
   CFU_IDENT            VARCHAR(20)                    not null,
   CFU_ESPK             VARCHAR(1)                    default 'N' not null
);

comment on table BTE_CAMFUE is
'Definicion campos fuente datos';

comment on column BTE_CAMFUE.CFU_CODIGO is
'Codigo interno';

comment on column BTE_CAMFUE.CFU_CODFUE is
'Codigo interno fuente';

comment on column BTE_CAMFUE.CFU_IDENT is
'Id campo';


alter table BTE_CAMFUE
   add constraint BTE_CFU_PK primary key (CFU_CODIGO);

create unique index BTE_CFUIDENT_AK on BTE_CAMFUE (
   CFU_CODFUE ASC,
   CFU_IDENT ASC
);

create index BTE_CODFUE_I on BTE_CAMFUE (
   CFU_CODFUE ASC
);


create table BTE_FILFUE  (
   FIF_CODIGO           BIGINT                      not null,
   FIF_CODFUE           BIGINT                      not null
);

comment on table BTE_FILFUE is
'Filas de datos fuente datos';

comment on column BTE_FILFUE.FIF_CODIGO is
'Codigo interno';

comment on column BTE_FILFUE.FIF_CODFUE is
'Codigo interno fuente datos';

alter table BTE_FILFUE
   add constraint BTE_FIF_PK primary key (FIF_CODIGO);

create index BTE_FIFFUE_I on BTE_FILFUE (
   FIF_CODFUE ASC
);

create table BTE_FUEDAT  (
   FUE_CODIGO           BIGINT                      not null,
   FUE_IDENT            VARCHAR(20)                    not null,
   FUE_DESC             VARCHAR(500),
   FUE_IDEPRO           VARCHAR(100)                   not null
);

comment on table BTE_FUEDAT is
'Fuentes de datos para dominios';

comment on column BTE_FUEDAT.FUE_CODIGO is
'Codigo interno';

comment on column BTE_FUEDAT.FUE_IDENT is
'Identificador fuente datos';

comment on column BTE_FUEDAT.FUE_DESC is
'Descripcion';

comment on column BTE_FUEDAT.FUE_IDEPRO is
'IDENTIFICADOR DEL PROCEDIMIENTO';

alter table BTE_FUEDAT
   add constraint BTE_FUE_PK primary key (FUE_CODIGO);

create unique index BTE_FUEIDE_AK on BTE_FUEDAT (
   FUE_IDENT ASC
);

create index BTE_FUEIDP_I on BTE_FUEDAT (
   FUE_IDEPRO ASC
);


create table BTE_VALCFU  (
   VCF_CODIGO           BIGINT                      not null,
   VCF_CODFIF          BIGINT                      not null,
   VCF_CODCFU           BIGINT                      not null,
   VCF_VALOR            VARCHAR(4000)
);

comment on table BTE_VALCFU is
'Valores fuente datos';

comment on column BTE_VALCFU.VCF_CODIGO is
'Codigo interno';

comment on column BTE_VALCFU.VCF_CODFIF is
'Codigo interno fila fuente datos';

comment on column BTE_VALCFU.VCF_CODCFU is
'Codigo interno campo fuente datos';

comment on column BTE_VALCFU.VCF_VALOR is
'Valor';

alter table BTE_VALCFU
   add constraint BTE_VCF_PK primary key (VCF_CODIGO);

create index BTE_VCFCFU_I on BTE_VALCFU (
   VCF_CODCFU ASC
);

create index BTE_VCFFIF_I on BTE_VALCFU (
   VCF_CODFIF ASC
);

alter table BTE_CAMFUE
   add constraint BTE_CFUFUE_FK foreign key (CFU_CODFUE)
      references BTE_FUEDAT (FUE_CODIGO);

alter table BTE_FILFUE
   add constraint BTE_FIFFUE_FK foreign key (FIF_CODFUE)
      references BTE_FUEDAT (FUE_CODIGO);

alter table BTE_FUEDAT
   add constraint BTE_FUETAP_FK foreign key (FUE_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);

alter table BTE_VALCFU
   add constraint BTE_VCFCFU_FK foreign key (VCF_CODCFU)
      references BTE_CAMFUE (CFU_CODIGO);

alter table BTE_VALCFU
   add constraint BTE_VCFFIF_FK foreign key (VCF_CODFIF)
      references BTE_FILFUE (FIF_CODIGO);

