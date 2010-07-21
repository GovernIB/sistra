
create table AUD_AUDIT  (
   AUD_CODI             int8                    not null,
   AUD_FECHA            timestamp,
   AUD_TIPO             varchar(6),
   AUD_DESC             varchar(4000),
   AUD_NIVAUT           char(1),
   AUD_SEYCON           varchar(1536),
   AUD_NIF              varchar(12),
   AUD_NOMBRE           varchar(256),
   AUD_IDI              varchar(2),
   AUD_RESULT           char(1),
   AUD_MODTRA           varchar(15),
   AUD_VERTRA           int4,
   AUD_IDPER            varchar(50),
   AUD_CLAVE            varchar(256)
);

alter table AUD_AUDIT
   add constraint AUD_AUD_PK primary key (AUD_CODI);

create table AUD_INICI  (
   INI_FECHA            timestamp,
   INI_PTLSRV           int8,
   INI_USAT1            varchar(4000),
   INI_PTLSRT           int8,
   INI_PTLBZ            int8,
   INI_USAT2            varchar(4000),
   INI_TRARR            int8,
   INI_TRAPR            int8,
   INI_USAT3            varchar(4000),
   INI_MTRT1            varchar(4000),
   INI_MTRO1            varchar(4000),
   INI_USAT4            varchar(4000),
   INI_MTRN1            int8,
   INI_MTRT2            varchar(4000),
   INI_USAT5            varchar(4000),
   INI_MTRO2            varchar(4000),
   INI_MTRN2            int8,
   INI_MTRT3            varchar(4000),
   INI_MTRT4            varchar(4000),
   INI_MTRO4            varchar(4000),
   INI_MTRO3            varchar(4000),
   INI_MTRN3            int8,
   INI_MTRN4            int8,
   INI_MTRN5            int8,
   INI_MTRT5            varchar(4000),
   INI_MTRO5            varchar(4000),
   INI_MACT1            varchar(4000),
   INI_MACO1            varchar(4000),
   INI_MACN1            int8,
   INI_MACT2            varchar(4000),
   INI_MACO2            varchar(4000),
   INI_MACN2            int8,
   INI_MACT3            varchar(4000),
   INI_MACO3            varchar(4000),
   INI_MACN3            int8,
   INI_MACT4            varchar(4000),
   INI_MACO4            varchar(4000),
   INI_MACN4            int8,
   INI_MACT5            varchar(4000),
   INI_MACO5            varchar(4000),
   INI_MACN5            int8,
   INI_USAO1            varchar(4000),
   INI_USAN1            int8,
   INI_USAO2            varchar(4000),
   INI_USAN2            int8,
   INI_USAO3            varchar(4000),
   INI_USAN3            int8,
   INI_USAO4            varchar(4000),
   INI_USAN4            int8,
   INI_USAO5            varchar(4000),
   INI_USAN5            int8,
   INI_USAF1            varchar(100),
   INI_USAF2            varchar(100),
   INI_USAF3            varchar(100),
   INI_USAF4            varchar(100),
   INI_USAF5            varchar(100),
   INI_RESCRT           int8,
   INI_RESUSU           int8,
   INI_RESAN            int8,
   INI_RESREG           int8,
   INI_RESBD            int8,
   INI_RESCS            int8,
   INI_RESPAG           int8,
   INI_RESSRV           int8,
   INI_RESTMX           int8,
   INI_RESFMX           varchar(100),
   INI_RESSRT           int8,
   INI_MTRTC1           varchar(4000),
   INI_MTRTC2           varchar(4000),
   INI_MTRTC3           varchar(4000),
   INI_MTRTC4           varchar(4000),
   INI_MTRTC5           varchar(4000),
   INI_MACTC1           varchar(4000),
   INI_MACTC2           varchar(4000),
   INI_MACTC3           varchar(4000),
   INI_MACTC4           varchar(4000),
   INI_MACTC5           varchar(4000),
   INI_USATC1           varchar(4000),
   INI_USATC2           varchar(4000),
   INI_USATC3           varchar(4000),
   INI_USATC4           varchar(4000),
   INI_USATC5           varchar(4000)
);

create table AUD_MODUL  (
   MOD_MODUL            varchar(6)                     not null,
   MOD_DESC             varchar(100)                   not null,
   MOD_ORDEN            int4                       not null,
   MOD_DESCCA           varchar(100)                   not null
);

alter table AUD_MODUL
   add constraint AUD_MOD_PK primary key (MOD_MODUL);

create table AUD_TABS  (
   TAB_CODI             varchar(10)                    not null,
   TAB_DESC             varchar(100)                   not null,
   TAB_HANDLR           varchar(100)                   not null,
   TAB_ORDEN            int4                       not null
);

alter table AUD_TABS
   add constraint AUD_TAB_PK primary key (TAB_CODI);

create table AUD_TIPOEV  (
   TIP_TIPO             varchar(6)                     not null,
   TIP_MODUL            varchar(6)                     not null,
   TIP_AUDIT            char(1),
   TIP_DESC             varchar(256),
   TIP_ORDEN            int4,
   TIP_PRPCLS           varchar(30),
   TIP_HANDLR           varchar(100),
   TIP_AYUDA            varchar(256),
   TIP_DESCCA           varchar(256),
   TIP_AYUDAC           varchar(256)
);

alter table AUD_TIPOEV
   add constraint AUD_TIP_PK primary key (TIP_TIPO);

alter table AUD_AUDIT
   add constraint AUD_AUDTIP_FK foreign key (AUD_TIPO)
      references AUD_TIPOEV (TIP_TIPO);

alter table AUD_TIPOEV
   add constraint AUD_TIPMOD_FK foreign key (TIP_MODUL)
      references AUD_MODUL (MOD_MODUL);

create sequence AUD_AUDSEQ;