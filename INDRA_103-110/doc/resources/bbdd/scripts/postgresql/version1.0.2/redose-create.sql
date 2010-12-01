create table RDS_PLANTI (
   PLA_CODIGO int8 not null,
   PLA_CODVER int8 not null,
   PLA_FORMAT int8 not null,
   PLA_TIPO varchar(3) not null,
   PLA_DEFECT char(1) not null,
   PLA_BARCOD char(1) not null,
   PLA_SELLO char(1) not null,
   primary key (PLA_CODIGO)
);
create table RDS_IDIOMA (
   IDI_CODIGO varchar(2) not null,
   IDI_NOMBRE varchar(50) not null,
   primary key (IDI_CODIGO)
);
create table RDS_TIOPER (
   TOP_CODIGO varchar(4) not null,
   TOP_NOMBRE varchar(50) not null,
   primary key (TOP_CODIGO)
);
create table RDS_VERS (
   VER_CODIGO int8 not null,
   VER_VERSIO int4 not null,
   VER_DESC varchar(100),
   VER_CODMOD int8 not null,
   primary key (VER_CODIGO)
);
create table RDS_FICHER (
   FIC_CODIGO int8 not null,
   FIC_DATOS bytea not null,
   primary key (FIC_CODIGO)
);
create table RDS_FIRMAS (
   FIR_CODIGO int8 not null,
   FIR_CODDOC int8,
   FIR_FIRMA bytea not null,
   FIR_FORMAT varchar(255),
   primary key (FIR_CODIGO)
);
create table RDS_PLAIDI (
   PLI_CODIGO int8 not null,
   PLI_CODPLA int8 not null,
   PLI_CODIDI varchar(255) not null,
   PLI_NOMFIC varchar(255) not null,
   primary key (PLI_CODIGO)
);
create table RDS_TIUSO (
   TIU_CODIGO varchar(3) not null,
   TIU_NOMBRE varchar(50) not null,
   primary key (TIU_CODIGO)
);
create table RDS_ARCPLI (
   ARP_CODPLI int8 not null,
   ARP_DATOS bytea not null,
   primary key (ARP_CODPLI)
);
create table RDS_MODELO (
   MOD_CODIGO int8 not null,
   MOD_MODELO varchar(15) not null unique,
   MOD_NOMBRE varchar(100) not null,
   MOD_DESC varchar(400),
   MOD_ESTRUC char(1) not null,
   primary key (MOD_CODIGO)
);
create table RDS_USOS (
   USO_CODIGO int8 not null,
   USO_CODDOC int8 not null,
   USO_CODTIU varchar(3) not null,
   USO_REF varchar(50) not null,
   USO_FCSELL timestamp,
   USO_FECHA timestamp not null,
   primary key (USO_CODIGO)
);
create table RDS_DOCUM (
   DOC_CODIGO int8 not null,
   DOC_CODUBI int8 not null,
   DOC_CODVER int8 not null,
   DOC_CLAVE varchar(10) not null,
   DOC_TITULO varchar(100) not null,
   DOC_FECHA timestamp not null,
   DOC_NIF varchar(10),
   DOC_SEYCON varchar(10),
   DOC_UNIADM int8 not null,
   DOC_FICHER varchar(100) not null,
   DOC_EXT varchar(4) not null,
   DOC_HASH varchar(500) not null,
   DOC_DELETE varchar(1),
   DOC_CODPLA int8,
   primary key (DOC_CODIGO)
);
create table RDS_LOGOPE (
   LOG_CODIGO int8 not null,
   LOG_CODTOP varchar(4) not null,
   LOG_DESOPE varchar(1000) not null,
   LOG_FECHA timestamp not null,
   LOG_SEYCON varchar(255) not null,
   primary key (LOG_CODIGO)
);
create table RDS_FORMAT (
   FOR_ID int8 not null,
   FOR_CLASS varchar(300) not null,
   FOR_DESC varchar(300) not null,
   primary key (FOR_ID)
);
create table RDS_UBICA (
   UBI_CODIGO int8 not null,
   UBI_IDENT varchar(5) not null unique,
   UBI_NOMBRE varchar(50) not null,
   UBI_PLUGIN varchar(100) not null,
   primary key (UBI_CODIGO)
);
alter table RDS_PLANTI add constraint FK7DF6A8FCD8D39BB1 foreign key (PLA_FORMAT) references RDS_FORMAT;
alter table RDS_PLANTI add constraint FK7DF6A8FCD3AED6E5 foreign key (PLA_CODVER) references RDS_VERS;
alter table RDS_VERS add constraint RDS_VERMOD_FK foreign key (VER_CODMOD) references RDS_MODELO;
alter table RDS_FIRMAS add constraint FK6CC3A08E71F6D710 foreign key (FIR_CODDOC) references RDS_DOCUM;
alter table RDS_PLAIDI add constraint FK7DF6944712B8281F foreign key (PLI_CODPLA) references RDS_PLANTI;
alter table RDS_ARCPLI add constraint FK64B373F9132D5B75 foreign key (ARP_CODPLI) references RDS_PLAIDI;
alter table RDS_USOS add constraint FKE64CF620554D474E foreign key (USO_CODDOC) references RDS_DOCUM;
alter table RDS_USOS add constraint FKE64CF620554D82B6 foreign key (USO_CODTIU) references RDS_TIUSO;
alter table RDS_DOCUM add constraint FKE2604052ECCD3032 foreign key (DOC_CODVER) references RDS_VERS;
alter table RDS_DOCUM add constraint FKE2604052ECCD1A74 foreign key (DOC_CODPLA) references RDS_PLANTI;
alter table RDS_DOCUM add constraint FKE2604052ECCD2C0B foreign key (DOC_CODUBI) references RDS_UBICA;
alter table RDS_LOGOPE add constraint FK77504A7E2E9D6658 foreign key (LOG_CODTOP) references RDS_TIOPER;
create sequence RDS_SEQLOG;
create sequence RDS_SEQUSO;
create sequence RDS_SEQMOD;
create sequence RDS_SEQVER;
create sequence RDS_SEQFOR;
create sequence RDS_SEQPLA;
create sequence RDS_SEQDOC;
create sequence RDS_SEQUBI;
create sequence RDS_SEQFIR;
create sequence RDS_SEQPLI;
