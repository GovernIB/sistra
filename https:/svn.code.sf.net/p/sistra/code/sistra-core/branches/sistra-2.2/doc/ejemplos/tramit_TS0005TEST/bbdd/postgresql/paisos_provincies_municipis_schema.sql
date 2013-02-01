

create table STR_DPAIS  (
   PAI_CODIGO           int4                            not null,
   PAI_CODALF           CHAR(3)                         not null,
   PAI_DENCAS           VARCHAR(50)                     not null,
   PAI_DENCAT           VARCHAR(50)                     not null,
   PAI_VIGENC           CHAR(1)                         not null,
   PAI_COD2AF           CHAR(3)
);
alter table STR_DPAIS
   add constraint STR_PAI_PK primary key (PAI_CODIGO);

create table STR_DPROVI  (
   PRO_CODIGO           int4                            not null,
   PRO_CODCAU           int4,
   PRO_DENCAS           VARCHAR(25),
   PRO_DENCAT           VARCHAR(25)
);

alter table STR_DPROVI
   add constraint STR_PRO_PK primary key (PRO_CODIGO);

create table STR_DMUNIC  (
   MUN_CODIGO           int4                           not null,
   MUN_PROVIN           int4                           not null,
   MUN_DENOFI           VARCHAR(50)                    not null
);

alter table STR_DMUNIC
   add constraint STR_MUN_PK primary key (MUN_CODIGO, MUN_PROVIN);
   
alter table STR_DMUNIC
   add constraint STR_MUNPRO_FK foreign key (MUN_PROVIN)
      references STR_DPROVI (PRO_CODIGO);