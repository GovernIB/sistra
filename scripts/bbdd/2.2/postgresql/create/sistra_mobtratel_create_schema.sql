
create sequence MOB_SEQENV;

create sequence MOB_SEQMSE;

create sequence MOB_SEQMSS;

create sequence MOB_SEQPER;

create table MOB_CUENTA  (
   CUE_CODIGO           VARCHAR(5)                     not null,
   CUE_NOMBRE           VARCHAR(100)                   not null,
   CUE_EMAIL            VARCHAR(500),
   CUE_SMS              VARCHAR(50),
   CUE_DEFECT           BIGINT                       not null
);

comment on table MOB_CUENTA is
'CUENTAS EMISORAS';

comment on column MOB_CUENTA.CUE_CODIGO is
'ID DE LA CUENTA';

comment on column MOB_CUENTA.CUE_NOMBRE is
'NOMBRE DE LA CUENTA';

comment on column MOB_CUENTA.CUE_EMAIL is
'CUENTA EMAIL UTILIZADA PARA ENVÍOS TIPO EMAIL';

comment on column MOB_CUENTA.CUE_SMS is
'CUENTA PROVATO UTILIZADA PARA ENVÍOS TIPO SMS';

comment on column MOB_CUENTA.CUE_DEFECT is
'CUENTA POR DEFECTO';

alter table MOB_CUENTA
   add constraint MOB_CUE_PK primary key (CUE_CODIGO);

create table MOB_ENVIOS  (
   ENV_ID               BIGINT                      not null,
   ENV_CODCUE           VARCHAR(5),
   ENV_NOMBRE           VARCHAR(100)                   not null,
   ENV_FCPROG           DATE,
   ENV_FCENV            DATE,
   ENV_FCCAD            DATE,
   ENV_SEYCON           VARCHAR(50),
   ENV_FCREG            DATE                            not null,
   ENV_ESTADO           BIGINT                       not null,
   ENV_INMED            BIGINT                      default 0 not null
);

comment on table MOB_ENVIOS is
'Módulo de movilidad';

comment on column MOB_ENVIOS.ENV_ID is
'IDENTIFICADOR DEL ENVIO';

comment on column MOB_ENVIOS.ENV_CODCUE is
'ID DE LA CUENTA';

comment on column MOB_ENVIOS.ENV_NOMBRE is
'NOMBRE DEL ENVIO';

comment on column MOB_ENVIOS.ENV_FCPROG is
'FECHA PROGRAMACION ENVIO';

comment on column MOB_ENVIOS.ENV_FCENV is
'FECHA ENVIO';

comment on column MOB_ENVIOS.ENV_FCCAD is
'FECHA CADUCIDAD (SI ES NULA, NO CADUCA)';

comment on column MOB_ENVIOS.ENV_SEYCON is
'USUARIO SEYCON QUE HA ENVIADO EL MENSAJE';

comment on column MOB_ENVIOS.ENV_FCREG is
'FECHA EN LA QUE SE HA ALMACENADO EL ENVIO';

comment on column MOB_ENVIOS.ENV_ESTADO is
'ESTADO DEL ENVIO.
0- Pendiente de envío
1- Enviado 
2- Con Errores
3- Cancelado
4- Enviandose';

comment on column MOB_ENVIOS.ENV_INMED is
'INDICA SI EL MENSAJE ES INMEDIATO Y NO PROGRAMADO (SOLO PARA DEMO)';

alter table MOB_ENVIOS
   add constraint MOB_ENV_PK primary key (ENV_ID);

create table MOB_MSEMAI  (
   MSE_CODIGO           BIGINT                      not null,
   MSE_IDENV            BIGINT                      not null,
   MSE_TITULO           VARCHAR(1000),
   MSE_MENSAJ           BYTEA,
   MSE_HTML             BIGINT                      default 0 not null,
   MSE_EMAILS           BYTEA                            not null,
   MSE_ERROR            VARCHAR(500),
   MSE_ESTADO           BIGINT                       not null,
   MSE_INIENV           DATE,
   MSE_FINENV           DATE,
   MSE_NDEST            BIGINT,
   MSE_NDESTE           BIGINT,
   MSE_EMAILE           BYTEA
);

comment on column MOB_MSEMAI.MSE_CODIGO is
'IDENTIFICADOR DEL MENSAJE DE EMAIL';

comment on column MOB_MSEMAI.MSE_IDENV is
'IDENTIFICADOR DEL ENVIO';

comment on column MOB_MSEMAI.MSE_TITULO is
'INDICA TITULO DE MENSAJE';

comment on column MOB_MSEMAI.MSE_MENSAJ is
'INDICA CONTENIDO MENSAJE';

comment on column MOB_MSEMAI.MSE_HTML is
'INDICA SI EL CONTENIDO DEL MENSAJE ES HTML';

comment on column MOB_MSEMAI.MSE_EMAILS is
'DESTINATARIOS (LISTA EMAILS SEPARADOS POR ";")';

comment on column MOB_MSEMAI.MSE_ERROR is
'DESCRIPCION DEL ERROR OBTENIDO AL REALIZAR EL ENVIO';

comment on column MOB_MSEMAI.MSE_ESTADO is
'INDICA EL ESTADO DE ENVIO DEL MENSAJE. 
"0" -  PENDIENTE DE ENVIO
"1" -  ENVIADO
"2" -  CON ERROR';

comment on column MOB_MSEMAI.MSE_NDEST is
'NUMERO EMAILS DESTINO';

comment on column MOB_MSEMAI.MSE_NDESTE is
'NUMERO DE MENSAJES A EMAILS ENVIADOS
';

comment on column MOB_MSEMAI.MSE_EMAILE is
'EMAILS  A LOS QUE YA SE LES HA ENVIADO EL MENSAJE, SEPARADOS POR ";"';

alter table MOB_MSEMAI
   add constraint MOB_MSE_PK primary key (MSE_CODIGO);

create index MOB_MSEENV_FK_I on MOB_MSEMAI (
   MSE_IDENV ASC
);

create table MOB_MSSMS  (
   MSS_CODIGO           BIGINT                      not null,
   MSS_IDENV            BIGINT,
   MSS_MENSAJ           BYTEA                            not null,
   MSS_TELFS            BYTEA                            not null,
   MSS_ERROR            VARCHAR(500),
   MSS_ESTADO           BIGINT                       not null,
   MSS_NDEST            BIGINT,
   MSS_NDESTE           BIGINT,
   MSS_TELFSE           BYTEA,
   MSS_INIENV           DATE,
   MSS_FINENV           DATE
);

comment on column MOB_MSSMS.MSS_CODIGO is
'IDENTIFICADOR DEL ENVIO';

comment on column MOB_MSSMS.MSS_IDENV is
'IDENTIFICADOR DEL ENVIO';

comment on column MOB_MSSMS.MSS_MENSAJ is
'INDICA CONTENIDO MENSAJE';

comment on column MOB_MSSMS.MSS_TELFS is
'DESTINATARIOS (LISTA TELEFONOS SEPARADOS POR ";")';

comment on column MOB_MSSMS.MSS_ERROR is
'DESCRIPCION DEL ERROR OBTENIDO AL REALIZAR EL ENVIO';

comment on column MOB_MSSMS.MSS_ESTADO is
'INDICA EL ESTADO DE ENVIO DEL MENSAJE. 
"0" -  PENDIENTE DE ENVIO
"1" -  ENVIADO
"2" -  CON ERROR';

comment on column MOB_MSSMS.MSS_NDEST is
'NUMERO TELEFONOS DESTINO';

comment on column MOB_MSSMS.MSS_NDESTE is
'NUMERO DE MENSAJES A TELEFONOS ENVIADOS
';

comment on column MOB_MSSMS.MSS_TELFSE is
'TELEFONOS A LOS QUE YA SE LES HA ENVIADO EL MENSAJE, SEPARADOS POR ";"';

comment on column MOB_MSSMS.MSS_INIENV is
'FECHA INICIO ENVIO';

comment on column MOB_MSSMS.MSS_FINENV is
'FECHA FIN ENVIO';

alter table MOB_MSSMS
   add constraint MOB_MSS_PK primary key (MSS_CODIGO);

create index MOB_MSSENV_FK_I on MOB_MSSMS (
   MSS_IDENV ASC
);

create table MOB_PERMIS  (
   PER_CODIGO           BIGINT                       not null,
   PER_SEYCON           VARCHAR(50)                    not null,
   PER_CODCUE           VARCHAR(5)                     not null,
   PER_SMS              BIGINT                       not null,
   PER_EMAIL            BIGINT                       not null
);

comment on table MOB_PERMIS is
'PERMISOS DE ENVIO';

comment on column MOB_PERMIS.PER_SEYCON is
'USUARIO SEYCON';

comment on column MOB_PERMIS.PER_CODCUE is
'ID DE LA CUENTA';

comment on column MOB_PERMIS.PER_SMS is
'PERMISO PARA ENVIAR SMS';

comment on column MOB_PERMIS.PER_EMAIL is
'PERMISO PARA ENVIAR EMAIL';

alter table MOB_PERMIS
   add constraint MOB_SEYCUE_UNI unique (PER_SEYCON, PER_CODCUE);

alter table MOB_PERMIS
   add constraint MOB_PER_PK primary key (PER_CODIGO);

alter table MOB_ENVIOS
   add constraint MOB_ENVCUE_FK foreign key (ENV_CODCUE)
      references MOB_CUENTA (CUE_CODIGO);

alter table MOB_MSEMAI
   add constraint MOB_MSEENV_FK foreign key (MSE_IDENV)
      references MOB_ENVIOS (ENV_ID);

alter table MOB_MSSMS
   add constraint MOB_MSSENV_FK foreign key (MSS_IDENV)
      references MOB_ENVIOS (ENV_ID);

alter table MOB_PERMIS
   add constraint MOB_PERCUE_FK foreign key (PER_CODCUE)
      references MOB_CUENTA (CUE_CODIGO);

 -- 1.1.4 TO 1.1.5
 alter table MOB_MSEMAI  add
   MSE_ACKENV           BIGINT                      default 0 not null;
alter table MOB_MSEMAI  add   
   MSE_ACKEST           VARCHAR(1);
alter table MOB_MSEMAI  add   
   MSE_ACKERR           VARCHAR(4000);

comment on column MOB_MSEMAI.MSE_ACKENV is
'INDICA SI SE CONTROLA SI SE HA REALIZADO EL ENVIO. SOLO PERMITIDO CUANDO HAYA UN UNICO DESTINATARIO.';

comment on column MOB_MSEMAI.MSE_ACKEST is
'EN CASO DE CONTROLAR LA REALIZACION DEL ENVIO INDICA EL ESTADO';

comment on column MOB_MSEMAI.MSE_ACKERR is
'EN CASO DE CONTROLAR LA REALIZACION DEL ENVIO Y QUE EL ESTADO SEA ERRONEO INDICA EL MOTIVO DEL ERROR';


alter table MOB_MSSMS  add
   MSS_ACKENV           BIGINT                      default 0 not null;
alter table MOB_MSSMS  add   
   MSS_ACKEST           VARCHAR(1);
alter table MOB_MSSMS  add   
   MSS_ACKERR           VARCHAR(4000);
   

comment on column MOB_MSSMS.MSS_ACKENV is
'INDICA SI SE CONTROLA SI SE HA REALIZADO EL ENVIO. SOLO PERMITIDO CUANDO HAYA UN UNICO DESTINATARIO.';

comment on column MOB_MSSMS.MSS_ACKEST is
'EN CASO DE CONTROLAR LA REALIZACION DEL ENVIO INDICA EL ESTADO';

comment on column MOB_MSSMS.MSS_ACKERR is
'EN CASO DE CONTROLAR LA REALIZACION DEL ENVIO Y QUE EL ESTADO SEA ERRONEO INDICA EL MOTIVO DEL ERROR';   
 
