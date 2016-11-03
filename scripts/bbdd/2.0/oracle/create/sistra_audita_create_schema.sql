ALTER SESSION SET NLS_LENGTH_SEMANTICS = 'CHAR';
create sequence AUD_AUDSEQ;

create table AUD_AUDIT  (
   AUD_CODI             NUMBER( 20 )                    not null,
   AUD_FECHA            DATE,
   AUD_TIPO             VARCHAR2(6),
   AUD_DESC             VARCHAR2(4000),
   AUD_NIVAUT           VARCHAR2(1),
   AUD_SEYCON           VARCHAR2(1536),
   AUD_NIF              VARCHAR2(12),
   AUD_NOMBRE           VARCHAR2(256),
   AUD_IDI              VARCHAR2(2),
   AUD_RESULT           VARCHAR2(1),
   AUD_MODTRA           VARCHAR2(20),
   AUD_VERTRA           NUMBER(2),
   AUD_IDPER            VARCHAR2(50),
   AUD_CLAVE            VARCHAR2(256)
);

comment on column AUD_AUDIT.AUD_CODI is
'ID INTERNO SECUENCIAL';

comment on column AUD_AUDIT.AUD_FECHA is
'FECHA ENTRADA EVENTO (PROPORCIONADA POR SISTEMA AUDITORIA)';

comment on column AUD_AUDIT.AUD_TIPO is
'TIPO EVENTO';

comment on column AUD_AUDIT.AUD_DESC is
'DESCRIPCION EVENTO';

comment on column AUD_AUDIT.AUD_NIVAUT is
'NIVEL AUTENTICACION';

comment on column AUD_AUDIT.AUD_SEYCON is
'USUARIO SEYCON';

comment on column AUD_AUDIT.AUD_NIF is
'NIF';

comment on column AUD_AUDIT.AUD_NOMBRE is
'NOMBRE';

comment on column AUD_AUDIT.AUD_IDI is
'IDIOMA';

comment on column AUD_AUDIT.AUD_RESULT is
'RESULTADO EVENTO';

comment on column AUD_AUDIT.AUD_MODTRA is
'MODELO DEL TRAMITE';

comment on column AUD_AUDIT.AUD_VERTRA is
'VERSION DEL TRAMITE';

comment on column AUD_AUDIT.AUD_IDPER is
'IDENTIFICADOR PERSISTENCIA';

comment on column AUD_AUDIT.AUD_CLAVE is
'CLAVE (UTILIZADA SEGÚN TIPO EVENTO)';

alter table AUD_AUDIT
   add constraint AUD_AUD_PK primary key (AUD_CODI);

create table AUD_INICI  (
   INI_FECHA            DATE,
   INI_PTLSRV           NUMBER(10),
   INI_USAT1            VARCHAR2(4000),
   INI_PTLSRT           NUMBER(10),
   INI_PTLBZ            NUMBER(10),
   INI_USAT2            VARCHAR2(4000),
   INI_TRARR            NUMBER(10),
   INI_TRAPR            NUMBER(10),
   INI_USAT3            VARCHAR2(4000),
   INI_MTRT1            VARCHAR2(4000),
   INI_MTRO1            VARCHAR2(4000),
   INI_USAT4            VARCHAR2(4000),
   INI_MTRN1            NUMBER(10),
   INI_MTRT2            VARCHAR2(4000),
   INI_USAT5            VARCHAR2(4000),
   INI_MTRO2            VARCHAR2(4000),
   INI_MTRN2            NUMBER(10),
   INI_MTRT3            VARCHAR2(4000),
   INI_MTRT4            VARCHAR2(4000),
   INI_MTRO4            VARCHAR2(4000),
   INI_MTRO3            VARCHAR2(4000),
   INI_MTRN3            NUMBER(10),
   INI_MTRN4            NUMBER(10),
   INI_MTRN5            NUMBER(10),
   INI_MTRT5            VARCHAR2(4000),
   INI_MTRO5            VARCHAR2(4000),
   INI_MACT1            VARCHAR2(4000),
   INI_MACO1            VARCHAR2(4000),
   INI_MACN1            NUMBER(10),
   INI_MACT2            VARCHAR2(4000),
   INI_MACO2            VARCHAR2(4000),
   INI_MACN2            NUMBER(10),
   INI_MACT3            VARCHAR2(4000),
   INI_MACO3            VARCHAR2(4000),
   INI_MACN3            NUMBER(10),
   INI_MACT4            VARCHAR2(4000),
   INI_MACO4            VARCHAR2(4000),
   INI_MACN4            NUMBER(10),
   INI_MACT5            VARCHAR2(4000),
   INI_MACO5            VARCHAR2(4000),
   INI_MACN5            NUMBER(10),
   INI_USAO1            VARCHAR2(4000),
   INI_USAN1            NUMBER(10),
   INI_USAO2            VARCHAR2(4000),
   INI_USAN2            NUMBER(10),
   INI_USAO3            VARCHAR2(4000),
   INI_USAN3            NUMBER(10),
   INI_USAO4            VARCHAR2(4000),
   INI_USAN4            NUMBER(10),
   INI_USAO5            VARCHAR2(4000),
   INI_USAN5            NUMBER(10),
   INI_USAF1            VARCHAR2(100),
   INI_USAF2            VARCHAR2(100),
   INI_USAF3            VARCHAR2(100),
   INI_USAF4            VARCHAR2(100),
   INI_USAF5            VARCHAR2(100),
   INI_RESCRT           NUMBER(10),
   INI_RESUSU           NUMBER(10),
   INI_RESAN            NUMBER(10),
   INI_RESREG           NUMBER(10),
   INI_RESBD            NUMBER(10),
   INI_RESCS            NUMBER(10),
   INI_RESPAG           NUMBER(10),
   INI_RESSRV           NUMBER(10),
   INI_RESTMX           NUMBER(10),
   INI_RESFMX           VARCHAR2(100),
   INI_RESSRT           NUMBER(10),
   INI_MTRTC1           VARCHAR2(4000),
   INI_MTRTC2           VARCHAR2(4000),
   INI_MTRTC3           VARCHAR2(4000),
   INI_MTRTC4           VARCHAR2(4000),
   INI_MTRTC5           VARCHAR2(4000),
   INI_MACTC1           VARCHAR2(4000),
   INI_MACTC2           VARCHAR2(4000),
   INI_MACTC3           VARCHAR2(4000),
   INI_MACTC4           VARCHAR2(4000),
   INI_MACTC5           VARCHAR2(4000),
   INI_USATC1           VARCHAR2(4000),
   INI_USATC2           VARCHAR2(4000),
   INI_USATC3           VARCHAR2(4000),
   INI_USATC4           VARCHAR2(4000),
   INI_USATC5           VARCHAR2(4000)
);

comment on table AUD_INICI is
'CAMPOS CALCULADOS PERIODICAMENTE PARA LA PAGINA DE INICIO';

create table AUD_MODUL  (
   MOD_MODUL            VARCHAR2(6)                     not null,
   MOD_DESC             VARCHAR2(100)                   not null,
   MOD_ORDEN            NUMBER(2)                       not null,
   MOD_DESCCA           VARCHAR2(100)                   not null
);

comment on column AUD_MODUL.MOD_MODUL is
'Identificador del módulo';

comment on column AUD_MODUL.MOD_DESC is
'Descripcion del módulo que aparecerá en la aplicación de auditoria.';

comment on column AUD_MODUL.MOD_ORDEN is
'Orden en el que aparecerá el módulo en la aplicación de auditoría';

comment on column AUD_MODUL.MOD_DESCCA is
'Descripcion del módulo que aparecerá en la aplicación de auditoria.';

alter table AUD_MODUL
   add constraint AUD_MOD_PK primary key (MOD_MODUL);

create table AUD_TABS  (
   TAB_CODI             VARCHAR2(10)                    not null,
   TAB_DESC             VARCHAR2(100)                   not null,
   TAB_HANDLR           VARCHAR2(100)                   not null,
   TAB_ORDEN            NUMBER(2)                       not null
);

comment on table AUD_TABS is
'TABS PERSONALIZADOS DEL CUADRO DE MANDO';

alter table AUD_TABS
   add constraint AUD_TAB_PK primary key (TAB_CODI);

create table AUD_TIPOEV  (
   TIP_TIPO             VARCHAR2(6)                     not null,
   TIP_MODUL            VARCHAR2(6)                     not null,
   TIP_AUDIT            VARCHAR2(1),
   TIP_DESC             VARCHAR2(256),
   TIP_ORDEN            NUMBER(2),
   TIP_PRPCLS           VARCHAR2(30),
   TIP_HANDLR           VARCHAR2(100),
   TIP_AYUDA            VARCHAR2(256),
   TIP_DESCCA           VARCHAR2(256),
   TIP_AYUDAC           VARCHAR2(256)
);

comment on column AUD_TIPOEV.TIP_TIPO is
'TIPO DE EVENTO';

comment on column AUD_TIPOEV.TIP_MODUL is
'IDENTIFICADOR DEL MÓDULO';

comment on column AUD_TIPOEV.TIP_AUDIT is
'INDICA SI SE DEBE AUDITAR (S/N)';

comment on column AUD_TIPOEV.TIP_DESC is
'DESCRIPCION EVENTO';

comment on column AUD_TIPOEV.TIP_ORDEN is
'ORDEN DE PRESENTACIÓN DENTRO DE SU MÓDULO, EN EL FRONT DE AUDITORIA';

comment on column AUD_TIPOEV.TIP_PRPCLS is
'PROPIEDADES DE LAS COLUMNAS A MOSTRAR:
T: INDICA QUE SE QUIERE VISUALIZAR EL NUMERO DE EVENTOS DEL EVENTO EN CUESTION
I: INDICA QUE SE QUIERE VISUALIZAR EL TOTAL POR IDIOMA
N: INDICA QUE SE QUIERE VISUALIZAR EL TOTAL POR NIVEL DE AUTENTICACION
D:INDICA QUE SE QUIERE MOSTRAR EL DETALLE DEL EVENTO EN QUESTION
G: INDICA QUE SE QUIERE MOSTRAR UN GRÁFICO DEL EVENTO EN QUESTION
X: INDICA QUE EL EVENTO EN QUESTION TIENE VISUALIZACION PARTICULAR (PERMITE INTRODUCIR HTML LIBRE) 
';

comment on column AUD_TIPOEV.TIP_HANDLR is
'NOMBRE DE LA CLASE JAVA HANDLER PARA GESTIONAR CARACTERÍSTICAS ESPECIALES DEL TIPO DE EVENTO AUDITADO (TEXTO PARTICULARIZADO, DETALLE, GRAFICO,...)
';

comment on column AUD_TIPOEV.TIP_AYUDA is
'TEXTO DE AYUDA QUE APARECERÁ EN EL FRONT DE AUDITORIA.';

comment on column AUD_TIPOEV.TIP_DESCCA is
'DESCRIPCION EVENTO';

comment on column AUD_TIPOEV.TIP_AYUDAC is
'TEXTO DE AYUDA QUE APARECERÁ EN EL FRONT DE AUDITORIA.';

alter table AUD_TIPOEV
   add constraint AUD_TIP_PK primary key (TIP_TIPO);

alter table AUD_AUDIT
   add constraint AUD_AUDTIP_FK foreign key (AUD_TIPO)
      references AUD_TIPOEV (TIP_TIPO);

alter table AUD_TIPOEV
   add constraint AUD_TIPMOD_FK foreign key (TIP_MODUL)
      references AUD_MODUL (MOD_MODUL);

