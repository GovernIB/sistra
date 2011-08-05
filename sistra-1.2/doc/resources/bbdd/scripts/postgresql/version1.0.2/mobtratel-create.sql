create table MOB_MSSMS (
   MSS_CODIGO int8 not null,
   MSS_TELFS bytea not null,
   MSS_TELFSE bytea,
   MSS_MENSAJ bytea not null,
   MSS_ESTADO int4 not null,
   MSS_ERROR varchar(500),
   MSS_NDEST int4,
   MSS_NDESTE int4,
   MSS_INIENV timestamp,
   MSS_FINENV timestamp,
   MSS_IDENV int8 not null,
   primary key (MSS_CODIGO)
);
create table MOB_CUENTA (
   CUE_CODIGO varchar(255) not null,
   CUE_NOMBRE varchar(255) not null,
   CUE_EMAIL varchar(255),
   CUE_SMS varchar(255),
   CUE_DEFECT int4 not null,
   primary key (CUE_CODIGO)
);
create table MOB_MSEMAI (
   MSE_CODIGO int8 not null,
   MSE_EMAILS bytea not null,
   MSE_EMAILE bytea,
   MSE_TITULO varchar(255),
   MSE_MENSAJ bytea not null,
   MSE_HTML bool not null,
   MSE_ESTADO int4 not null,
   MSE_ERROR varchar(500),
   MSE_INIENV timestamp,
   MSE_FINENV timestamp,
   MSE_NDEST int4,
   MSE_NDESTE int4,
   MSE_IDENV int8 not null,
   primary key (MSE_CODIGO)
);
create table MOB_PERMIS (
   PER_CODIGO int8 not null,
   PER_SEYCON varchar(255),
   PER_SMS int4 not null,
   PER_EMAIL int4 not null,
   PER_CODCUE varchar(255),
   primary key (PER_CODIGO)
);
create table MOB_ENVIOS (
   ENV_ID int8 not null,
   ENV_NOMBRE varchar(255),
   ENV_FCPROG timestamp,
   ENV_FCENV timestamp,
   ENV_FCCAD timestamp,
   ENV_ESTADO int4 not null,
   ENV_SEYCON varchar(255),
   ENV_FCREG timestamp,
   ENV_INMED bool,
   ENV_CODCUE varchar(255) not null,
   primary key (ENV_ID)
);
alter table MOB_MSSMS add constraint MOB_MSSENV_FK foreign key (MSS_IDENV) references MOB_ENVIOS;
alter table MOB_MSEMAI add constraint MOB_MSEENV_FK foreign key (MSE_IDENV) references MOB_ENVIOS;
alter table MOB_PERMIS add constraint MOB_PERCUE_FK foreign key (PER_CODCUE) references MOB_CUENTA;
alter table MOB_ENVIOS add constraint MOB_ENVCUE_FK foreign key (ENV_CODCUE) references MOB_CUENTA;
create sequence MOB_SEQPER;
create sequence MOB_SEQMSS;
create sequence MOB_SEQENV;
create sequence MOB_SEQMSE;
