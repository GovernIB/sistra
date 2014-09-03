
Ccreate table STR_DPAIS  (
   PAI_CODIGO           NUMBER(3)                       not null,
   PAI_CODALF           CHAR(3)                         not null,
   PAI_DENCAS           VARCHAR2(50)                    not null,
   PAI_DENCAT           VARCHAR2(50)                    not null,
   PAI_VIGENC           CHAR                            not null,
   PAI_COD2AF           CHAR(3)
);

alter table STR_DPAIS
   add constraint STR_PAI_PK primary key (PAI_CODIGO);

create table STR_DPROVI  (
   PRO_CODIGO           NUMBER(2)                       not null,
   PRO_CODCAU           NUMBER(2),
   PRO_DENCAS           VARCHAR2(25),
   PRO_DENCAT           VARCHAR2(25)
);

alter table STR_DPROVI
   add constraint STR_PRO_PK primary key (PRO_CODIGO);

create table STR_DMUNIC  (
   MUN_CODIGO           NUMBER(3)                       not null,
   MUN_PROVIN           NUMBER(2)                       not null,
   MUN_DENOFI           VARCHAR2(50)                    not null
);

comment on column STR_DMUNIC.MUN_CODIGO is
'Codigo de municipio';

comment on column STR_DMUNIC.MUN_PROVIN is
'Codigo de provincia';

comment on column STR_DMUNIC.MUN_DENOFI is
'Denominacion Oficial';

alter table STR_DMUNIC
   add constraint STR_MUN_PK primary key (MUN_CODIGO, MUN_PROVIN);

create index STR_MUNPRO_IDX on STR_DMUNIC (
   MUN_PROVIN ASC
);


alter table STR_DMUNIC
   add constraint STR_MUNPRO_FK foreign key (MUN_PROVIN)
      references STR_DPROVI (PRO_CODIGO);