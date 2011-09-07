create table RFR_FORMUL (
   FOR_CODI int8 not null,
   FOR_MODELO varchar(3) not null,
   FOR_ULNUSE int8 not null,
   FOR_URLEN1 varchar(256),
   FOR_URLEN2 varchar(256),
   FOR_VERSIO int4 not null,
   FOR_LASTVE bool not null,
   FOR_HASBCO bool not null,
   FOR_BCODEX int4,
   FOR_BCODEY int4,
   FOR_ESBLOQ bool not null,
   FOR_MTVBLQ varchar(255),
   FOR_TAGCAR varchar(255),
   FOR_FECCAR timestamp,
   FOR_VERFUN int8,
   FOR_DTD int8,
   FOR_LOGTI1 int8,
   FOR_LOGTI2 int8,
   primary key (FOR_CODI)
);
create table RFR_VERSIO (
   VER_CODIGO int8 not null,
   VER_NOMBRE varchar(255),
   VER_FECHA timestamp,
   VER_SUFIX varchar(255),
   primary key (VER_CODIGO)
);
create table RFR_PROSAL (
   PRS_CODI int8 not null,
   PRS_NOMBRE varchar(128) not null,
   PRS_VALOR varchar(4000),
   PRS_EXPRES bool,
   PRS_CODSAL int8 not null,
   PRS_CODPLA int8,
   primary key (PRS_CODI)
);
create table RFR_MASCAR (
   MAS_CODI int8 not null,
   MAS_NOMBRE varchar(128),
   MAS_DESCRI varchar(4000),
   MAS_VARIAB bytea,
   primary key (MAS_CODI)
);
create table RFR_PANTAL (
   PAN_CODI int8 not null,
   PAN_NOMBRE varchar(128) not null,
   PAN_ORDEN int4 not null,
   PAN_EXPRES varchar(4000),
   PAN_ULTIMA bool,
   PAN_INICIA bool,
   PAN_DETALL varchar(300),
   PAN_CODFOR int8 not null,
   primary key (PAN_CODI)
);
create table RFR_IDIOMA (
   IDI_CODI varchar(2) not null,
   IDI_ORDEN int4 not null,
   primary key (IDI_CODI)
);
create table RFR_PALETA (
   PAL_CODI int8 not null,
   PAL_NOMBRE varchar(128) not null,
   primary key (PAL_CODI)
);
create table RFR_PUNSAL (
   PSA_CODI int8 not null,
   PSA_NOMBRE varchar(128) not null,
   PSA_IMPLEM varchar(1024),
   primary key (PSA_CODI)
);
create table RFR_PATRON (
   PAT_CODI int8 not null,
   PAT_NOMBRE varchar(128) not null,
   PAT_DESCRI varchar(4000),
   PAT_EJECUT bool not null,
   PAT_CODIGO varchar(4000),
   primary key (PAT_CODI)
);
create table RFR_MASVAR (
   MVA_CODMAS int8 not null,
   MVA_VALOR varchar(255),
   MVA_ORDEN int4 not null,
   primary key (MVA_CODMAS, MVA_ORDEN)
);
create table RFR_TRAPAN (
   TRP_CODPAN int8 not null,
   TRP_TITULO varchar(256) not null,
   TRP_CODIDI varchar(2) not null,
   primary key (TRP_CODPAN, TRP_CODIDI)
);
create table RFR_VALIDA (
   VAL_CODI int8 not null,
   VAL_ORDEN int4 not null,
   VAL_VALORE bytea,
   VAL_CODMAS int8 not null,
   VAL_CODCAM int8 not null,
   primary key (VAL_CODI)
);
create table RFR_TRAYPA (
   TRA_CODAYU int8 not null,
   TRA_DESCOR varchar(1024),
   TRA_DESLAR varchar(4000),
   TRA_URLWEB varchar(1024),
   TRA_URLVID varchar(1024),
   TRA_URLSON varchar(1024),
   TRA_CODIDI varchar(2) not null,
   primary key (TRA_CODAYU, TRA_CODIDI)
);
create table RFR_TRACAM (
   TRC_CODCAM int8 not null,
   TRC_NOMBRE varchar(256) not null,
   TRC_AYUDA varchar(4000),
   TRC_MENVAL varchar(256),
   TRC_CODIDI varchar(2) not null,
   primary key (TRC_CODCAM, TRC_CODIDI)
);
create table RFR_COMPON (
   COM_CODI int8 not null,
   COM_TYPE varchar(128) not null,
   COM_CODPAN int8,
   COM_CODPAL int8,
   COM_NOMLOG varchar(128) not null,
   COM_ORDEN int4 not null,
   COM_POSICI int4 not null,
   COM_ESTILO varchar(128),
   COM_ETIPDF varchar(128),
   COM_NUMERO int4,
   COM_OCULTO bool,
   COM_EXPAUR varchar(4000),
   COM_EXPAUT varchar(4000),
   COM_EXPDEP varchar(4000),
   COM_EXPVAL varchar(4000),
   COM_EXPVPO varchar(4000),
   COM_EXPPOS varchar(4000),
   COM_TIPVAL varchar(128),
   COM_MOSTAB bool,
   COM_ANCCOL int4,
   COM_CODPAT int8,
   COM_FILAS int4,
   COM_COLUMN int4,
   COM_MULTIL bool,
   COM_OBLIGA bool,
   COM_ALTURA int4,
   COM_SELMUL bool,
   COM_EXTREE bool,
   COM_VALDEF bool,
   COM_MAXSIZ int4,
   COM_MULTIF bool,
   COM_ANCMAX bool,
   primary key (COM_CODI)
);
create table RFR_FORSEG (
   FSG_CODI int8 not null,
   FSG_HTTPS bool not null,
   FSG_REQLOG bool not null,
   FSG_REQCER bool not null,
   FSG_REQFIR bool not null,
   primary key (FSG_CODI)
);
create table RFR_AYUPAN (
   AYU_CODI int8 not null,
   AYU_CODPAN int8 not null,
   AYU_CODPER int8 not null,
   primary key (AYU_CODI)
);
create table RFR_VALPOS (
   VAP_CODI int8 not null,
   VAP_CODCAM int8,
   VAP_ORDEN int4 not null,
   VAP_VALOR varchar(128),
   VAP_DEFECT bool,
   primary key (VAP_CODI)
);
create table RFR_TRPEUS (
   TPE_CODPER int8 not null,
   TPE_NOMBRE varchar(256) not null,
   TPE_DESCRI varchar(4000),
   TPE_CODIDI varchar(2) not null,
   primary key (TPE_CODPER, TPE_CODIDI)
);
create table RFR_TRVAPO (
   TRV_CODVAP int8 not null,
   TRV_ETIQUE varchar(4000),
   TRV_ARCHIV int8,
   TRV_CODIDI varchar(2) not null,
   primary key (TRV_CODVAP, TRV_CODIDI)
);
create table RFR_TRALAB (
   TRL_CODLAB int8 not null,
   TRL_ETIQUE varchar(4000) not null,
   TRL_CODIDI varchar(2) not null,
   primary key (TRL_CODLAB, TRL_CODIDI)
);
create table RFR_ARCHIV (
   ARC_CODI int8 not null,
   ARC_NOMBRE varchar(128) not null,
   ARC_MIME varchar(128) not null,
   ARC_PESOB int4 not null,
   ARC_DATOS bytea,
   primary key (ARC_CODI)
);
create table RFR_FSGVFI (
   FSV_CODFSG int8 not null,
   FSV_CODVFI int8 not null,
   primary key (FSV_CODFSG, FSV_CODVFI)
);
create table RFR_PERUSU (
   PER_CODI int8 not null,
   PER_CODEST varchar(10) not null,
   PER_PATICO varchar(1024) not null,
   primary key (PER_CODI)
);
create table RFR_SALIDA (
   SAL_CODI int8 not null,
   SAL_ORDEN int4 not null,
   SAL_CODPSA int8 not null,
   SAL_CODFOR int8 not null,
   primary key (SAL_CODI)
);
create table RFR_FSGROL (
   FSR_CODFSG int8 not null,
   FSR_ROL varchar(128) not null,
   primary key (FSR_CODFSG, FSR_ROL)
);
create table RFR_TRAFOR (
   TRF_CODFOR int8 not null,
   TRF_TITULO varchar(256) not null,
   TRF_DESCRI varchar(4000),
   TRF_NOMEN1 varchar(256),
   TRF_NOMEN2 varchar(256),
   TRF_PLANTI int8,
   TRF_CODIDI varchar(2) not null,
   primary key (TRF_CODFOR, TRF_CODIDI)
);
create table RFR_VALFIR (
   VFI_CODI int8 not null,
   VFI_NOMBRE varchar(128) not null,
   VFI_IMPLEM varchar(1024),
   primary key (VFI_CODI)
);
alter table RFR_FORMUL add constraint RFR_FORLO2_FK foreign key (FOR_LOGTI2) references RFR_ARCHIV;
alter table RFR_FORMUL add constraint RFR_FORDTD_FK foreign key (FOR_DTD) references RFR_ARCHIV;
alter table RFR_FORMUL add constraint RFR_FORVER_FK foreign key (FOR_VERFUN) references RFR_VERSIO;
alter table RFR_FORMUL add constraint RFR_FORLO1_FK foreign key (FOR_LOGTI1) references RFR_ARCHIV;
alter table RFR_PROSAL add constraint RFR_PRSARC_FK foreign key (PRS_CODPLA) references RFR_ARCHIV;
alter table RFR_PROSAL add constraint RFR_PRSSAL_FK foreign key (PRS_CODSAL) references RFR_SALIDA;
alter table RFR_PANTAL add constraint RFR_PANFOR_FK foreign key (PAN_CODFOR) references RFR_FORMUL;
alter table RFR_MASVAR add constraint RFR_MASMVA_FK foreign key (MVA_CODMAS) references RFR_MASCAR;
alter table RFR_TRAPAN add constraint RFR_PANTRA_FK foreign key (TRP_CODPAN) references RFR_PANTAL;
alter table RFR_VALIDA add constraint RFR_VALCAM_FK foreign key (VAL_CODCAM) references RFR_COMPON;
alter table RFR_VALIDA add constraint RFR_VALMAS_FK foreign key (VAL_CODMAS) references RFR_MASCAR;
alter table RFR_TRAYPA add constraint RFR_AYUTRA_FK foreign key (TRA_CODAYU) references RFR_AYUPAN;
alter table RFR_TRACAM add constraint RFR_CAMTRC_FK foreign key (TRC_CODCAM) references RFR_COMPON;
alter table RFR_COMPON add constraint RFR_CAMPAT_FK foreign key (COM_CODPAT) references RFR_PATRON;
alter table RFR_COMPON add constraint RFR_COMPAN_FK foreign key (COM_CODPAN) references RFR_PANTAL;
alter table RFR_COMPON add constraint RFR_COMPAL_FK foreign key (COM_CODPAL) references RFR_PALETA;
alter table RFR_FORSEG add constraint RFR_FSGFOR_FK foreign key (FSG_CODI) references RFR_FORMUL;
alter table RFR_AYUPAN add constraint RFR_AYUPAN_FK foreign key (AYU_CODPAN) references RFR_PANTAL;
alter table RFR_AYUPAN add constraint RFR_AYUPER_FK foreign key (AYU_CODPER) references RFR_PERUSU;
alter table RFR_VALPOS add constraint RFR_VAPCAM_FK foreign key (VAP_CODCAM) references RFR_COMPON;
alter table RFR_TRPEUS add constraint RFR_PERTRP_FK foreign key (TPE_CODPER) references RFR_PERUSU;
alter table RFR_TRVAPO add constraint RFR_TRVARC_FK foreign key (TRV_ARCHIV) references RFR_ARCHIV;
alter table RFR_TRVAPO add constraint RFR_VAPTRV_FK foreign key (TRV_CODVAP) references RFR_VALPOS;
alter table RFR_TRALAB add constraint RFR_LABTRA_FK foreign key (TRL_CODLAB) references RFR_COMPON;
alter table RFR_FSGVFI add constraint RFR_FSVFSG_FK foreign key (FSV_CODFSG) references RFR_FORSEG;
alter table RFR_FSGVFI add constraint RFR_FSVVFI_FK foreign key (FSV_CODVFI) references RFR_VALFIR;
alter table RFR_SALIDA add constraint RFR_SALFOR_FK foreign key (SAL_CODFOR) references RFR_FORMUL;
alter table RFR_SALIDA add constraint RFR_SALPSA_FK foreign key (SAL_CODPSA) references RFR_PUNSAL;
alter table RFR_FSGROL add constraint RFR_FSRFSG_FK foreign key (FSR_CODFSG) references RFR_FORSEG;
alter table RFR_TRAFOR add constraint RFR_FORTRF_FK foreign key (TRF_CODFOR) references RFR_FORMUL;
alter table RFR_TRAFOR add constraint RFR_TRFARC_FK foreign key (TRF_PLANTI) references RFR_ARCHIV;
create sequence RFR_SECAYU;
create sequence RFR_SECSAL;
create sequence RFR_SECPAL;
create sequence RFR_SECPER;
create sequence RFR_SECARC;
create sequence RFR_SECMAS;
create sequence RFR_SECFOR;
create sequence RFR_SECPRS;
create sequence RFR_SECVAP;
create sequence RFR_SEQVAL;
create sequence RFR_SECPAN;
create sequence RFR_SECPAT;
create sequence RFR_SECCOM;
create sequence RFR_SECPSA;
create sequence RFR_SECVFI;
