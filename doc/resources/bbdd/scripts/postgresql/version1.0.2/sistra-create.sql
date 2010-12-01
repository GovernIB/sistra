create table STR_TRADOC (
   TRD_CODDOC int8 not null,
   TRD_DESC varchar(200),
   TRD_INFO varchar(1000),
   TRD_CODIDI varchar(2) not null,
   primary key (TRD_CODDOC, TRD_CODIDI)
);
create table STR_TRAMTR (
   TMT_CODMTR int8 not null,
   TMT_DESC varchar(1000),
   TMT_CODIDI varchar(2) not null,
   primary key (TMT_CODMTR, TMT_CODIDI)
);
create table STR_ESPNIV (
   ETN_CODIGO int8 not null,
   ETN_ACTIVO varchar(1),
   ETN_FLUTRA varchar(1),
   ETN_VALINI bytea,
   ETN_DIAPER int4,
   ETN_CODPRV bytea,
   ETN_CODLOC bytea,
   ETN_CODPAI bytea,
   ETN_RTENIF bytea,
   ETN_RTENOM bytea,
   ETN_RDONIF bytea,
   ETN_RDONOM bytea,
   ETN_CHKENV bytea,
   ETN_URLFIN bytea,
   ETN_NOTTEL varchar(1),
   ETN_AVISOS bytea,
   ETN_AVISMS bytea,
   ETN_AVIEMA bytea,
   ETN_DIAPRE int4,
   primary key (ETN_CODIGO)
);
create table STR_DOMIN (
   DOM_CODIGO int8 not null,
   DOM_IDENTI varchar(10) not null,
   DOM_DESC varchar(100) not null,
   DOM_TIPO char(1) not null,
   DOM_URL varchar(200),
   DOM_SQL varchar(4000),
   DOM_EJBREM char(1),
   DOM_EJBSTD char(1),
   DOM_JNDI varchar(100),
   DOM_USR varchar(500),
   DOM_PWD varchar(500),
   DOM_CACHE char(1) not null,
   DOM_CODORG int8 not null,
   primary key (DOM_CODIGO)
);
create table STR_TRANIV (
   TNV_CODIGO int8 not null,
   TNV_NIVAUT varchar(3) not null,
   TNV_CODETN int8 not null,
   TNV_CODTRV int8 not null,
   primary key (TNV_CODIGO)
);
create table STR_TRAETN (
   TET_CODETN int8 not null,
   TET_INSINI bytea,
   TET_MENINA bytea,
   TET_INSFIN bytea,
   TET_INSENT bytea,
   TET_FECLIM bytea,
   TET_CODIDI varchar(2) not null,
   primary key (TET_CODETN, TET_CODIDI)
);
create table STR_ORGRES (
   ORG_CODIGO int8 not null,
   ORG_DESC varchar(200) not null,
   primary key (ORG_CODIGO)
);
create table STR_DOCUM (
   DOC_CODIGO int8 not null,
   DOC_IDENTI varchar(5) not null,
   DOC_PAD int8,
   DOC_MODELO varchar(15) not null,
   DOC_ANEGCO char(1) not null,
   DOC_ANEGMA int4,
   DOC_TIPO char(1) not null,
   DOC_ORDEN int4 not null,
   DOC_FORPRG char(1),
   DOC_FORJUS char(1),
   DOC_ANETEL char(1),
   DOC_ANEEXT varchar(50),
   DOC_ANETAM int4,
   DOC_ANEPLA varchar(500),
   DOC_ANEDPL char(1),
   DOC_ANECOM char(1),
   DOC_ANEFOT char(1),
   DOC_CODTRV int8 not null,
   primary key (DOC_CODIGO)
);
create table STR_TRATRA (
   TTR_CODTRA int8 not null,
   TTR_DESC varchar(200),
   TTR_CODIDI varchar(2) not null,
   primary key (TTR_CODTRA, TTR_CODIDI)
);
create table STR_TRAVER (
   TRV_CODIGO int8 not null,
   TRV_VERSIO int4 not null,
   TRV_DESVER varchar(500),
   TRV_ORGDES varchar(25) not null,
   TRV_UNIADM int8 not null,
   TRV_INIPLA timestamp,
   TRV_FINPLA timestamp,
   TRV_DESTIN char(1) not null,
   TRV_PRENVA char(1) not null,
   TRV_FIRMA char(1) not null,
   TRV_REGOFI varchar(25),
   TRV_REGAST varchar(25),
   TRV_CONTIP char(1) not null,
   TRV_CONEJB varchar(500),
   TRV_CONREM char(1) not null,
   TRV_CONURL varchar(500),
   TRV_CONAUT char(1) not null,
   TRV_CONUSU varchar(500),
   TRV_CONPWD varchar(500),
   TRV_BLOQUE varchar(1) not null,
   TRV_BLOUSU varchar(255),
   TRV_IDISOP varchar(100) not null,
   TRV_REDUCI char(1) not null,
   TRV_REDFIN char(1) not null,
   TRV_TAGCAR varchar(255),
   TRV_FECCAR timestamp,
   TRV_ANODEF char(1) not null,
   TRV_CODETN int8 not null,
   TRV_CODTRA int8 not null,
   primary key (TRV_CODIGO)
);
create table STR_GESFRM (
   GSF_IDENT varchar(255) not null,
   GSF_DESC varchar(255) not null,
   GSF_URL varchar(255) not null,
   GSF_URLTRA varchar(255) not null,
   GSF_URLRED varchar(255) not null,
   primary key (GSF_IDENT)
);
create table STR_DOCNIV (
   DNV_CODIGO int8 not null,
   DNV_NIVAUT varchar(3) not null,
   DNV_VERSIO int4 not null,
   DNV_OBLIGA char(1) not null,
   DNV_OBLSCR bytea,
   DNV_FIRMAR char(1) not null,
   DNV_FIRMTE varchar(500),
   DNV_FORGST varchar(20),
   DNV_FORFOR varchar(3),
   DNV_FORVER int4,
   DNV_FORINI bytea,
   DNV_FORCON bytea,
   DNV_FORPOS bytea,
   DNV_FORMOD bytea,
   DNV_FORPLA bytea,
   DNV_PAGDAT bytea,
   DNV_PAGTIP char(1),
   DNV_FLUTRA bytea,
   DNV_FIRCTY varchar(500),
   DNV_CODDOC int8 not null,
   primary key (DNV_CODIGO)
);
create table STR_IMPFIC (
   IMF_CODIGO int8 not null,
   IMF_TIPO varchar(1) not null,
   IMF_NOMFIC varchar(100) not null,
   IMF_XML bytea,
   IMF_CODIMP int8 not null,
   primary key (IMF_CODIGO)
);
create table STR_IMPORT (
   IMP_CODIGO int8 not null,
   IMP_DESC varchar(100) not null,
   IMP_FECHA timestamp not null,
   IMP_AUDITA char(1) not null,
   IMP_FECAUD timestamp,
   IMP_COMENT varchar(1000),
   IMP_IMPOR char(1),
   IMP_FECCAR timestamp not null,
   IMP_FECENV timestamp,
   primary key (IMP_CODIGO)
);
create table STR_DATJUS (
   DJS_CODIGO int8 not null,
   DJS_BLQCAM char(1) not null,
   DJS_ORDEN int4 not null,
   DJS_CAMPO varchar(500),
   DJS_CAMPS bytea,
   DJS_VISIBLE bytea,
   DJS_CODETN int8 not null,
   primary key (DJS_CODIGO)
);
create table STR_MENPLA (
   MPL_CODIGO int8 not null,
   MPL_IDENTI varchar(10) not null unique,
   MPL_ACTIVO char(1) not null,
   primary key (MPL_CODIGO)
);
create table STR_IDIOMA (
   IDI_CODIGO varchar(255) not null,
   IDI_ORDEN int4 not null,
   primary key (IDI_CODIGO)
);
create table STR_TRADNV (
   TDN_CODDNV int8 not null,
   TDN_INFO varchar(1000),
   TDN_CODIDI varchar(2) not null,
   primary key (TDN_CODDNV, TDN_CODIDI)
);
create table STR_TRADJS (
   TDJ_CODDJS int8 not null,
   TDJ_DESC varchar(200),
   TDJ_CODIDI varchar(2) not null,
   primary key (TDJ_CODDJS, TDJ_CODIDI)
);
create table STR_TRAMIT (
   TRA_CODIGO int8 not null,
   TRA_IDENTI varchar(10) not null unique,
   TRA_CODORG int8 not null,
   primary key (TRA_CODIGO)
);
create table STR_TRAMPL (
   TMP_CODMPL int8 not null,
   TMP_DESC varchar(255),
   TMP_CODIDI varchar(2) not null,
   primary key (TMP_CODMPL, TMP_CODIDI)
);
create table STR_MENTRA (
   MTR_CODIGO int8 not null,
   MTR_IDENTI varchar(25) not null,
   MTR_CODTRV int8 not null,
   primary key (MTR_CODIGO)
);
alter table STR_TRADOC add constraint FK55CDA6A356127999 foreign key (TRD_CODDOC) references STR_DOCUM;
alter table STR_TRAMTR add constraint FK55CDC916EECF3EF7 foreign key (TMT_CODMTR) references STR_MENTRA;
alter table STR_DOMIN add constraint FKBFD16619FB98D829 foreign key (DOM_CODORG) references STR_ORGRES;
alter table STR_TRANIV add constraint FK55CDCB8652D621C3 foreign key (TNV_CODTRV) references STR_TRAVER;
alter table STR_TRANIV add constraint FK55CDCB8652D5E9AA foreign key (TNV_CODETN) references STR_ESPNIV;
alter table STR_TRAETN add constraint FK55CDAB0A4CABA8E3 foreign key (TET_CODETN) references STR_ESPNIV;
alter table STR_DOCUM add constraint FKBFD14202ECCD2A47 foreign key (DOC_CODTRV) references STR_TRAVER;
alter table STR_TRATRA add constraint FK55CDE30E2CEC0838 foreign key (TTR_CODTRA) references STR_TRAMIT;
alter table STR_TRAVER add constraint FK55CDE90EA3E7DDB2 foreign key (TRV_CODTRA) references STR_TRAMIT;
alter table STR_TRAVER add constraint FK55CDE90EA3E7A5AE foreign key (TRV_CODETN) references STR_ESPNIV;
alter table STR_DOCNIV add constraint FK3A56E3D1E3ED353 foreign key (DNV_CODDOC) references STR_DOCUM;
alter table STR_IMPFIC add constraint FK42C8BAA2C156DE91 foreign key (IMF_CODIMP) references STR_IMPORT;
alter table STR_DATJUS add constraint FK3999477F85899519 foreign key (DJS_CODETN) references STR_ESPNIV;
alter table STR_TRADNV add constraint FK55CDA69749202835 foreign key (TDN_CODDNV) references STR_DOCNIV;
alter table STR_TRADJS add constraint FK55CDA618A99B743A foreign key (TDJ_CODDJS) references STR_DATJUS;
alter table STR_TRAMIT add constraint FK55CDC7C31E6F1CA8 foreign key (TRA_CODORG) references STR_ORGRES;
alter table STR_TRAMPL add constraint FK55CDC8944F4A8AF9 foreign key (TMP_CODMPL) references STR_MENPLA;
alter table STR_MENTRA add constraint FK492AADFB7EE9F074 foreign key (MTR_CODTRV) references STR_TRAVER;
create sequence STR_SEQDNV;
create sequence STR_SEQTRV;
create sequence STR_SEQDOM;
create sequence STR_SEQIMP;
create sequence STR_SEQIFI;
create sequence STR_SEQDJS;
create sequence STR_SEQMTR;
create sequence STR_SEQMPL;
create sequence STR_SEQTNV;
create sequence STR_SEQORG;
create sequence STR_SEQDOC;
create sequence STR_SEQETN;
create sequence STR_SEQTRA;

-- Tables addicionals per dominis comuns

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

