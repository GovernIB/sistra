
create sequence AUD_AUDSEQ;

create table AUD_AUDIT  (
   AUD_CODI             BIGINT                    not null,
   AUD_FECHA            TIMESTAMP,
   AUD_TIPO             VARCHAR(6),
   AUD_DESC             VARCHAR(4000),
   AUD_NIVAUT           VARCHAR(1),
   AUD_SEYCON           VARCHAR(1536),
   AUD_NIF              VARCHAR(12),
   AUD_NOMBRE           VARCHAR(256),
   AUD_IDI              VARCHAR(2),
   AUD_RESULT           VARCHAR(1),
   AUD_MODTRA           VARCHAR(20),
   AUD_VERTRA           BIGINT,
   AUD_IDPER            VARCHAR(50),
   AUD_CLAVE            VARCHAR(256)
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
   INI_FECHA            TIMESTAMP,
   INI_PTLSRV           BIGINT,
   INI_USAT1            VARCHAR(4000),
   INI_PTLSRT           BIGINT,
   INI_PTLBZ            BIGINT,
   INI_USAT2            VARCHAR(4000),
   INI_TRARR            BIGINT,
   INI_TRAPR            BIGINT,
   INI_USAT3            VARCHAR(4000),
   INI_MTRT1            VARCHAR(4000),
   INI_MTRO1            VARCHAR(4000),
   INI_USAT4            VARCHAR(4000),
   INI_MTRN1            BIGINT,
   INI_MTRT2            VARCHAR(4000),
   INI_USAT5            VARCHAR(4000),
   INI_MTRO2            VARCHAR(4000),
   INI_MTRN2            BIGINT,
   INI_MTRT3            VARCHAR(4000),
   INI_MTRT4            VARCHAR(4000),
   INI_MTRO4            VARCHAR(4000),
   INI_MTRO3            VARCHAR(4000),
   INI_MTRN3            BIGINT,
   INI_MTRN4            BIGINT,
   INI_MTRN5            BIGINT,
   INI_MTRT5            VARCHAR(4000),
   INI_MTRO5            VARCHAR(4000),
   INI_MACT1            VARCHAR(4000),
   INI_MACO1            VARCHAR(4000),
   INI_MACN1            BIGINT,
   INI_MACT2            VARCHAR(4000),
   INI_MACO2            VARCHAR(4000),
   INI_MACN2            BIGINT,
   INI_MACT3            VARCHAR(4000),
   INI_MACO3            VARCHAR(4000),
   INI_MACN3            BIGINT,
   INI_MACT4            VARCHAR(4000),
   INI_MACO4            VARCHAR(4000),
   INI_MACN4            BIGINT,
   INI_MACT5            VARCHAR(4000),
   INI_MACO5            VARCHAR(4000),
   INI_MACN5            BIGINT,
   INI_USAO1            VARCHAR(4000),
   INI_USAN1            BIGINT,
   INI_USAO2            VARCHAR(4000),
   INI_USAN2            BIGINT,
   INI_USAO3            VARCHAR(4000),
   INI_USAN3            BIGINT,
   INI_USAO4            VARCHAR(4000),
   INI_USAN4            BIGINT,
   INI_USAO5            VARCHAR(4000),
   INI_USAN5            BIGINT,
   INI_USAF1            VARCHAR(100),
   INI_USAF2            VARCHAR(100),
   INI_USAF3            VARCHAR(100),
   INI_USAF4            VARCHAR(100),
   INI_USAF5            VARCHAR(100),
   INI_RESCRT           BIGINT,
   INI_RESUSU           BIGINT,
   INI_RESAN            BIGINT,
   INI_RESREG           BIGINT,
   INI_RESBD            BIGINT,
   INI_RESCS            BIGINT,
   INI_RESPAG           BIGINT,
   INI_RESSRV           BIGINT,
   INI_RESTMX           BIGINT,
   INI_RESFMX           VARCHAR(100),
   INI_RESSRT           BIGINT,
   INI_MTRTC1           VARCHAR(4000),
   INI_MTRTC2           VARCHAR(4000),
   INI_MTRTC3           VARCHAR(4000),
   INI_MTRTC4           VARCHAR(4000),
   INI_MTRTC5           VARCHAR(4000),
   INI_MACTC1           VARCHAR(4000),
   INI_MACTC2           VARCHAR(4000),
   INI_MACTC3           VARCHAR(4000),
   INI_MACTC4           VARCHAR(4000),
   INI_MACTC5           VARCHAR(4000),
   INI_USATC1           VARCHAR(4000),
   INI_USATC2           VARCHAR(4000),
   INI_USATC3           VARCHAR(4000),
   INI_USATC4           VARCHAR(4000),
   INI_USATC5           VARCHAR(4000)
);

comment on table AUD_INICI is
'CAMPOS CALCULADOS PERIODICAMENTE PARA LA PAGINA DE INICIO';

create table AUD_MODUL  (
   MOD_MODUL            VARCHAR(6)                     not null,
   MOD_DESC             VARCHAR(100)                   not null,
   MOD_ORDEN            BIGINT                       not null,
   MOD_DESCCA           VARCHAR(100)                   not null
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
   TAB_CODI             VARCHAR(10)                    not null,
   TAB_DESC             VARCHAR(100)                   not null,
   TAB_HANDLR           VARCHAR(100)                   not null,
   TAB_ORDEN            BIGINT                       not null
);

comment on table AUD_TABS is
'TABS PERSONALIZADOS DEL CUADRO DE MANDO';

alter table AUD_TABS
   add constraint AUD_TAB_PK primary key (TAB_CODI);

create table AUD_TIPOEV  (
   TIP_TIPO             VARCHAR(6)                     not null,
   TIP_MODUL            VARCHAR(6)                     not null,
   TIP_AUDIT            VARCHAR(1),
   TIP_DESC             VARCHAR(256),
   TIP_ORDEN            BIGINT,
   TIP_PRPCLS           VARCHAR(30),
   TIP_HANDLR           VARCHAR(100),
   TIP_AYUDA            VARCHAR(256),
   TIP_DESCCA           VARCHAR(256),
   TIP_AYUDAC           VARCHAR(256)
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

-- From 2.2.7 to 2.2.8
alter table AUD_AUDIT  add   AUD_PROCED           VARCHAR(100);
