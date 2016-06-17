ALTER SESSION SET NLS_LENGTH_SEMANTICS = 'CHAR';

create sequence STR_SEQDJS;

create sequence STR_SEQDNV;

create sequence STR_SEQDOC;

create sequence STR_SEQDOM;

create sequence STR_SEQE09;

create sequence STR_SEQETN;

create sequence STR_SEQETR;

create sequence STR_SEQIFI;

create sequence STR_SEQIMP;

create sequence STR_SEQMPL;

create sequence STR_SEQMTR;

create sequence STR_SEQORG;

create sequence STR_SEQP09;

create sequence STR_SEQTNV;

create sequence STR_SEQTRA;

create sequence STR_SEQTRV;

create table STR_DATJUS  (
   DJS_CODIGO           NUMBER(20)                      not null,
   DJS_CODETN           NUMBER(20),
   DJS_BLQCAM           VARCHAR2(1)                     not null,
   DJS_ORDEN            NUMBER(4),
   DJS_CAMPO            VARCHAR2(500),
   DJS_CAMPS            BLOB,
   DJS_VISIBLE          BLOB
);


comment on table STR_DATJUS is
'Datos para generar el justificante. A partir de estos datos se generará la parte especifica de datos particulares del trámite en el asiento del trámite.';

comment on column STR_DATJUS.DJS_CODIGO is
'Código';

comment on column STR_DATJUS.DJS_CODETN is
'Código especificaciones';

comment on column STR_DATJUS.DJS_BLQCAM is
'Indica si es un separador de bloques (B) o es un campo (C)';

comment on column STR_DATJUS.DJS_ORDEN is
'Orden';

comment on column STR_DATJUS.DJS_CAMPO is
'Para tipo campo indica referencia al campo del formulario que contiene el valor';

comment on column STR_DATJUS.DJS_CAMPS is
'Para tipo campo indica permite establecer el valor mediante un script (tendrá prereferencia DJS_CAMPO)';

comment on column STR_DATJUS.DJS_VISIBLE is
'Permite configurar si el elemento será visible en función de datos del formulario';

alter table STR_DATJUS
   add constraint STR_DJS_PK primary key (DJS_CODIGO);

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

create table STR_DOCNIV  (
   DNV_CODIGO           NUMBER(20)                      not null,
   DNV_CODDOC           NUMBER(20)                      not null,
   DNV_NIVAUT           VARCHAR2(3)                     not null,
   DNV_VERSIO           NUMBER(2)                       not null,
   DNV_OBLIGA           VARCHAR2(1)                     not null,
   DNV_OBLSCR           BLOB,
   DNV_FIRMAR           VARCHAR2(1)                     not null,
   DNV_FIRMTE           VARCHAR2(500),
   DNV_FORGST           VARCHAR2(20)                   default 'forms' not null,
   DNV_FORINI           BLOB,
   DNV_FORFOR           VARCHAR2(20),
   DNV_FORVER           NUMBER(2),
   DNV_FORCON           BLOB,
   DNV_FORPOS           BLOB,
   DNV_FORMOD           BLOB,
   DNV_FORPLA           BLOB,
   DNV_PAGDAT           BLOB,
   DNV_PAGTIP           VARCHAR2(1)                    default 'A' not null,
   DNV_FLUTRA           BLOB,
   DNV_FIRCTY           VARCHAR2(500)
);

comment on table STR_DOCNIV is
'Especificaciones documentos por nivel de autenticación';

comment on column STR_DOCNIV.DNV_CODIGO is
'Código interno';

comment on column STR_DOCNIV.DNV_CODDOC is
'Código interno documento';

comment on column STR_DOCNIV.DNV_NIVAUT is
'Nivel autenticación: Certificado (C) / Ususario-pass (U) / Anónimo (A)';

comment on column STR_DOCNIV.DNV_VERSIO is
'Versión documento';

comment on column STR_DOCNIV.DNV_OBLIGA is
'Especificación obligatoriedad: Obligatorio (S) / Opcional (N) / Dependiente según script (D).';

comment on column STR_DOCNIV.DNV_OBLSCR is
'Script para indicar la obligatoriedad en documentos dependientes';

comment on column STR_DOCNIV.DNV_FIRMAR is
'Indica si el documento se debe firmar digitalmente individualmente. En el script se puede indicar quien debe firmar el documento, si no existe script o si devuelve vacío será el iniciador del trámite.No se puede indicar que debe firmarse y que el documento sea presencial.';

comment on column STR_DOCNIV.DNV_FIRMTE is
'Script que indica quien debe firmar el documento';

comment on column STR_DOCNIV.DNV_FORGST is
'Indica gestor de formularios a utilizar: FORMS,...';

comment on column STR_DOCNIV.DNV_FORINI is
'Para Formulario: script para carga de datos iniciales';

comment on column STR_DOCNIV.DNV_FORFOR is
'Para Formulario: indica enlace a Forms (Formulario)';

comment on column STR_DOCNIV.DNV_FORVER is
'Para Formulario: indica enlace a Forms (Version)';

comment on column STR_DOCNIV.DNV_FORCON is
'Para Formulario: script para establecer configuración formulario (campos solo lectura,etc)';

comment on column STR_DOCNIV.DNV_FORPOS is
'Para Formulario: validación datos formularios tras volver de Forms';

comment on column STR_DOCNIV.DNV_FORMOD is
'Para Formulario: script que permite modificar datos de otros formularios tras el salvado (no debe haber errores de validación)';

comment on column STR_DOCNIV.DNV_FORPLA is
'Para Formulario: script para indicar plantilla de visualización (si no se especifica script se utiliza la por defecto)';

comment on column STR_DOCNIV.DNV_PAGDAT is
'Para Pagos: script para calcular datos del pago';

comment on column STR_DOCNIV.DNV_PAGTIP is
'Para Pagos: indica el tipo de pago que se permite: Telematico (T) / Presencial (P) / Ambos (A)';

comment on column STR_DOCNIV.DNV_FLUTRA is
'Indica usuario que debe aportar este documento (solo para autenticado y si esta habilitado el flujo de tramitación). Si es nulo o devuelve vacío debe aportarlo quién inicia el trámite.';

comment on column STR_DOCNIV.DNV_FIRCTY is
'Indica el content type del documento a firmar. Si no se especifica se utilizará uno por defecto.';

alter table STR_DOCNIV
   add constraint STR_DNVNIV_UNI unique (DNV_CODDOC, DNV_NIVAUT);

alter table STR_DOCNIV
   add constraint STR_DNV_PK primary key (DNV_CODIGO);

create table STR_DOCUM  (
   DOC_CODIGO           NUMBER(20)                      not null,
   DOC_CODTRV           NUMBER(20)                      not null,
   DOC_IDENTI           VARCHAR2(5)                     not null,
   DOC_TIPO             VARCHAR2(1)                     not null,
   DOC_ORDEN            NUMBER(2)                       not null,
   DOC_PAD              NUMBER(20),
   DOC_MODELO           VARCHAR2(20)                    not null,
   DOC_FORPRG           VARCHAR2(1),
   DOC_FORJUS           VARCHAR2(1)                     default 'N',
   DOC_ANEEXT           VARCHAR2(50),
   DOC_ANETAM           NUMBER(5),
   DOC_ANEPLA           VARCHAR2(500),
   DOC_ANEDPL           VARCHAR2(1),
   DOC_ANETEL           VARCHAR2(1)                     not null,
   DOC_ANECOM           VARCHAR2(1)                     not null,
   DOC_ANEFOT           VARCHAR2(1)                     not null,
   DOC_ANEGCO           VARCHAR2(1)                     not null,
   DOC_ANEGMA           NUMBER(2),
   DOC_ANEPDF           VARCHAR2(1)                     default 'N',
   DOC_FORAJU           VARCHAR2(1)                     default 'N'
);

comment on table STR_DOCUM is
'Documentos del trámite';

comment on column STR_DOCUM.DOC_CODIGO is
'Código interno documento';

comment on column STR_DOCUM.DOC_IDENTI is
'Identificador documento dentro del trámite';

comment on column STR_DOCUM.DOC_TIPO is
'Tipo de documento: Formulario (F) / Anexo (A) / Pago (P)';

comment on column STR_DOCUM.DOC_ORDEN is
'Orden de presentación';

comment on column STR_DOCUM.DOC_PAD is
'Indica número de documento equivalente en la PAD';

comment on column STR_DOCUM.DOC_MODELO is
'Modelo del documento';

comment on column STR_DOCUM.DOC_FORPRG is
'Para Formulario: en caso de preregistro indica si se debe imprimir este formulario para firmar y entregar';

comment on column STR_DOCUM.DOC_FORJUS is
'Para Formulario: en caso de preregistro indica si se debe utilizar el formulario como justificante';

comment on column STR_DOCUM.DOC_ANEEXT is
'Para Anexos: lista de extensiones posibles separadas por ;. Lista vacía implica que no hay restricciones.';

comment on column STR_DOCUM.DOC_ANETAM is
'Para Anexos: tamaño en Kb permitido';

comment on column STR_DOCUM.DOC_ANEPLA is
'Para Anexos: URL plantilla';

comment on column STR_DOCUM.DOC_ANEDPL is
'Para Anexos: Indica si se indica al usuario que debe descargar la plantilla';

comment on column STR_DOCUM.DOC_ANETEL is
'Para Anexos: Indica si se debe anexar telemáticamente';

comment on column STR_DOCUM.DOC_ANECOM is
'Para Anexos: indica si se debe indicar al usuario que debe compulsar el documento';

comment on column STR_DOCUM.DOC_ANEFOT is
'Para Anexos: Indica si para un documento se deberá presentar una fotocopia';

comment on column STR_DOCUM.DOC_ANEGCO is
'Para Anexos: Indica si el documento es Genérico. Si es genérico se permitirá indicar al usuario indicar una descripción y anexar tantos documentos como se indique en el campo DOC_GCOMAX';

comment on column STR_DOCUM.DOC_ANEGMA is
'Para Anexos: Para documento Genérico indica el número máximo de instancias de documentos genéricos que se pueden anexar. Si se marca el documento como obligatorio deberán anexarse este número de instancias.';

comment on column STR_DOCUM.DOC_ANEPDF is
'Para Anexos: indica si hay que convertir el anexo a PDF (extensiones doc y odt)';

comment on column STR_DOCUM.DOC_FORAJU is
'Para Formulario: se indica si el formulario debe anexarse al justificante';

alter table STR_DOCUM
   add constraint STR_DOCIDE_UNI unique (DOC_CODTRV, DOC_IDENTI);

alter table STR_DOCUM
   add constraint STR_DOC_PK primary key (DOC_CODIGO);

create table STR_DOMIN  (
   DOM_CODIGO           NUMBER(20)                      not null,
   DOM_IDENTI           VARCHAR2(20)                    not null,
   DOM_TIPO             VARCHAR2(1)                     not null,
   DOM_URL              VARCHAR2(200),
   DOM_SQL              VARCHAR2(4000),
   DOM_JNDI             VARCHAR2(100),
   DOM_EJBREM           VARCHAR2(1),
   DOM_EJBSTD           VARCHAR2(1)                    default 'N',
   DOM_USR              VARCHAR2(500),
   DOM_PWD              VARCHAR2(500),
   DOM_CACHE            VARCHAR2(1)                    default 'N' not null,
   DOM_CODORG           NUMBER(20)                      not null,
   DOM_DESC             VARCHAR2(100)                   not null,
   DOM_WSVER            VARCHAR2(10 BYTE)
);


comment on table STR_DOMIN is
'Dominios';

comment on column STR_DOMIN.DOM_CODIGO is
'Código';

comment on column STR_DOMIN.DOM_IDENTI is
'Identificador funcional del dominio';

comment on column STR_DOMIN.DOM_TIPO is
'Tipo de dominio: Sql (S) / Ejb (E) / Webservice (W)';

comment on column STR_DOMIN.DOM_URL is
'Indica según tipo de dominio:
- Ejb: JNDI ejb que resuelve dominio
- Sql: JNDI datasource origen de datos
- Webservice: Url webservice';

comment on column STR_DOMIN.DOM_SQL is
'Para tipo dominio Sql indica sql a ejecutar';

comment on column STR_DOMIN.DOM_JNDI is
'Para tipo dominio Ejb indica jndi name del ejb a invocar';

comment on column STR_DOMIN.DOM_EJBREM is
'Para tipo dominio Ejb indica si el ejb es remoto: Remoto (R) / Local (L)';

comment on column STR_DOMIN.DOM_EJBSTD is
'Para tipo dominio Ejb/Ws indica si se debe realizar:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password
 - C: explícita a través plugin autenticación del organismo';

 comment on column STR_DOMIN.DOM_USR is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL USUARIO';

comment on column STR_DOMIN.DOM_PWD is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL PASSWORD';

comment on column STR_DOMIN.DOM_CACHE is
'Indica si el dominio debe cachearse';

comment on column STR_DOMIN.DOM_CODORG is
'Organo responsable del dominio';

comment on column STR_DOMIN.DOM_DESC is
'Descripcion dominio';


alter table STR_DOMIN
   add constraint STR_DOMIDE_UNI unique (DOM_IDENTI);

alter table STR_DOMIN
   add constraint STR_DOM_PK primary key (DOM_CODIGO);

create table STR_DPAIS  (
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

create table STR_ESPNIV  (
   ETN_CODIGO           NUMBER(20)                      not null,
   ETN_ACTIVO           VARCHAR2(1)                     not null,
   ETN_VALINI           BLOB,
   ETN_DIAPER           NUMBER(2),
   ETN_CODPRV           BLOB,
   ETN_CODLOC           BLOB,
   ETN_CODPAI           BLOB,
   ETN_RTENIF           BLOB,
   ETN_RTENOM           BLOB,
   ETN_RDONIF           BLOB,
   ETN_RDONOM           BLOB,
   ETN_DIAPRE           NUMBER(3)                      default 0,
   ETN_FLUTRA           VARCHAR2(1)                    default 'N' not null,
   ETN_URLFIN           BLOB,
   ETN_AVISMS           BLOB,
   ETN_AVIEMA           BLOB,
   ETN_NOTTEL           VARCHAR2(1)                    default 'N' not null,
   ETN_CHKENV           BLOB,
   ETN_DSTTRA           BLOB,
   ETN_PERSMS 			VARCHAR2(1) 					default 'N' not null
);

comment on table STR_ESPNIV is
'Especificación parámetros trámite que se especifican por defecto y pueden ser sobreescritas según nivel autenticación.';

comment on column STR_ESPNIV.ETN_CODIGO is
'Código';

comment on column STR_ESPNIV.ETN_ACTIVO is
'Indica si el trámite esta activo';

comment on column STR_ESPNIV.ETN_VALINI is
'Validación al iniciar trámite. Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_DIAPER is
'Días persistencia. Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_CODPRV is
'Script para indicar el código de la provincia. Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_CODLOC is
'Script para indicar el código de la localidad. Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_CODPAI is
'Script para indicar el código del país. Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_RTENIF is
'Script que indica el NIF representante (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_RTENOM is
'Script que indica el Nombre representante (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_RDONIF is
'Script que indica NIF representado (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_RDONOM is
'Script que indica Nombre representado (Obligatorio cuando trámite no es de tipo consulta). Puede ser sobreescrita por nivel autenticación';

comment on column STR_ESPNIV.ETN_DIAPRE is
'Dias de prerregistro. Puede ser sobreescrita por nivel de autenticación. Junto a la fecha limite de entrega, indica hasta que fecha se pueden entregar los datos de un prerregistro o preenvio';

comment on column STR_ESPNIV.ETN_FLUTRA is
'Indica si el trámite permite flujo de tramitación: permite que el trámite se vaya remitiendo a diferentes usuarios para que sea completado (sólo con autenticación).';

comment on column STR_ESPNIV.ETN_URLFIN is
'Establece url de finalización';

comment on column STR_ESPNIV.ETN_AVISMS is
'En caso de que esten habilitados los avisos para el expediente indicará el telefono para avisos SMS';

comment on column STR_ESPNIV.ETN_AVIEMA is
'En caso de que esten habilitados los avisos para el expediente indicará el email para avisos Email';

comment on column STR_ESPNIV.ETN_NOTTEL is
'Indica si el tramite permite notificacion telematica: N: No permite /  S Si permite, el ciudadano elige / O: Obligatoria notificación telemática / X: No especificada';

comment on column STR_ESPNIV.ETN_CHKENV is
'Script para chequear antes de enviar';

comment on column STR_ESPNIV.ETN_DSTTRA is
'Script que permite cambiar dinámicamente la información del destinatario del trámite (oficina registral, organo destino y unidad administrativa)';


alter table STR_ESPNIV
   add constraint STR_ETN_PK primary key (ETN_CODIGO);

create table STR_GESFRM  (
   GSF_IDENT            VARCHAR2(15)                    not null,
   GSF_DESC             VARCHAR2(50)                    not null,
   GSF_URL              VARCHAR2(500)                   not null,
   GSF_URLTRA           VARCHAR2(500)                   not null,
   GSF_URLRED           VARCHAR2(500)                   not null
);

comment on table STR_GESFRM is
'Gestores de formularios';

comment on column STR_GESFRM.GSF_IDENT is
'IDENTIFICADOR GESTOR FORMULARIOS';

comment on column STR_GESFRM.GSF_DESC is
'DESCRIPCION';

comment on column STR_GESFRM.GSF_URL is
'URL BASE DEL GESTOR';

comment on column STR_GESFRM.GSF_URLTRA is
'URL TRAMITACION FORMULARIO (PUEDE CONTENER COMO VARIABLE LA URL BASE @forms.server@) ';

comment on column STR_GESFRM.GSF_URLRED is
'URL REDIRECCION FORMULARIO (PUEDE CONTENER COMO VARIABLE LA URL BASE @forms.server@) ';

alter table STR_GESFRM
   add constraint STR_GSF_PK primary key (GSF_IDENT);

create table STR_IDIOMA  (
   IDI_CODIGO           VARCHAR2(2)                     not null,
   IDI_ORDEN            NUMBER(2)                       not null
);

comment on table STR_IDIOMA is
'Tabla de idiomas';

alter table STR_IDIOMA
   add constraint STR_IDI_PK primary key (IDI_CODIGO);

create table STR_IMPFIC  (
   IMF_CODIGO           NUMBER(10)                      not null,
   IMF_CODIMP           NUMBER(10)                      not null,
   IMF_TIPO             VARCHAR2(1)                     not null,
   IMF_NOMFIC           VARCHAR2(100)                   not null,
   IMF_XML              BLOB                            not null
);

comment on table STR_IMPFIC is
'Ficheros a importar';

comment on column STR_IMPFIC.IMF_CODIGO is
'Codigo interno';

comment on column STR_IMPFIC.IMF_CODIMP is
'Codigo importacion';

comment on column STR_IMPFIC.IMF_TIPO is
'Tipo: Tramite (T) / Formulario (F) / Dominio (D)';

comment on column STR_IMPFIC.IMF_NOMFIC is
'Nombre del fichero del cuaderno';

comment on column STR_IMPFIC.IMF_XML is
'XML';

alter table STR_IMPFIC
   add constraint STR_IMF_PK primary key (IMF_CODIGO);

create table STR_IMPORT  (
   IMP_CODIGO           NUMBER(10)                      not null,
   IMP_DESC             VARCHAR2(100)                   not null,
   IMP_FECHA            DATE                            not null,
   IMP_AUDITA           VARCHAR2(1)                     not null,
   IMP_FECAUD           DATE,
   IMP_COMENT           VARCHAR2(1000),
   IMP_IMPOR            VARCHAR2(1),
   IMP_FECCAR           DATE                            not null,
   IMP_FECENV           DATE
);

comment on table STR_IMPORT is
'Cuadernos de carga que deben pasar la auditoria';

comment on column STR_IMPORT.IMP_CODIGO is
'Identificador interno';

comment on column STR_IMPORT.IMP_DESC is
'Descripción cuaderno de carga';

comment on column STR_IMPORT.IMP_FECHA is
'Fecha/hora  alta cuaderno carga';

comment on column STR_IMPORT.IMP_AUDITA is
'Indica si la importación ha sido auditada por sistemas: Inicial (I) / No requiere auditoria (N) / Aceptada (S) / Pendiente (P) / Rechazada (R)
Al insertar un cuaderno de carga estará en estado I.
Una vez insertados todos los ficheros asociados se calculará automáticamente el tipo, que quedará en N o P.
Si queda en P posteriormente un auditor deberá pasar al estado S o R.
';

comment on column STR_IMPORT.IMP_FECAUD is
'Fecha auditoria';

comment on column STR_IMPORT.IMP_COMENT is
'Comentario de auditoria';

comment on column STR_IMPORT.IMP_IMPOR is
'Indica si se ha importado tras la auditoria (S/N)';

comment on column STR_IMPORT.IMP_FECCAR is
'Fecha de carga en la cual se requiere ser puesto en funcionamiento';

comment on column STR_IMPORT.IMP_FECENV is
'Fecha de envio a auditoria por parte del desarrollador';

alter table STR_IMPORT
   add constraint STR_IMP_PK primary key (IMP_CODIGO);

create table STR_MENPLA  (
   MPL_CODIGO           NUMBER(20)                      not null,
   MPL_IDENTI           VARCHAR2(10)                    not null,
   MPL_ACTIVO           VARCHAR2(1)                    default 'N' not null
);

comment on table STR_MENPLA is
'Mensajes plataforma:

Segun el id del mensaje tendra una funcionalidad:
TODOS: Mensaje que saldra para todos los tramites
ANONIMOS: Mensaje que saldra para todos los tramites anonimos
AUTENTICA: Mensaje que saldra para todos los tramites autenticados
PAGOS: Mensaje que saldra para todos los tramites con pagos
';

comment on column STR_MENPLA.MPL_CODIGO is
'CODIGO INTERNO';

comment on column STR_MENPLA.MPL_IDENTI is
'IDENTIFICADOR FUNCIONAL DEL MENSAJE';

comment on column STR_MENPLA.MPL_ACTIVO is
'INDICA SI EL MENSAJE ESTA ACTIVADO';

alter table STR_MENPLA
   add constraint STR_MENPLA_PK primary key (MPL_CODIGO);

create table STR_MENTRA  (
   MTR_CODIGO           NUMBER(20)                      not null,
   MTR_CODTRV           NUMBER(20)                      not null,
   MTR_IDENTI           VARCHAR2(25)                    not null
);

comment on table STR_MENTRA is
'Mensajes definidos para un trámite que se podrán emplear en las validaciones para mostrar mensajes particularizados al usuario';

comment on column STR_MENTRA.MTR_CODIGO is
'Código interno mensaje';

comment on column STR_MENTRA.MTR_CODTRV is
'Código versión trámite';

comment on column STR_MENTRA.MTR_IDENTI is
'Código funcional mensaje';

alter table STR_MENTRA
   add constraint STR_MTRIDE_UNI unique (MTR_CODTRV, MTR_IDENTI);

alter table STR_MENTRA
   add constraint STR_MTR_PK primary key (MTR_CODIGO);

create table STR_ORGRES  (
   ORG_CODIGO           NUMBER(20)                      not null,
   ORG_DESC             VARCHAR2(200)                   not null
);

comment on table STR_ORGRES is
'Organo Responsable. Sirve para agrupar los trámites';

alter table STR_ORGRES
   add constraint STR_ORG_PK primary key (ORG_CODIGO);

create table STR_TRADJS  (
   TDJ_CODDJS           NUMBER(20)                      not null,
   TDJ_CODIDI           VARCHAR2(2)                     not null,
   TDJ_DESC             VARCHAR2(200)                   not null
);

comment on table STR_TRADJS is
'Traducción datos justificante';

comment on column STR_TRADJS.TDJ_CODDJS is
'Código';

comment on column STR_TRADJS.TDJ_CODIDI is
'Idioma';

comment on column STR_TRADJS.TDJ_DESC is
'Descripción campo/bloque';

alter table STR_TRADJS
   add constraint STR_TDJ_PK primary key (TDJ_CODDJS, TDJ_CODIDI);

create table STR_TRADNV  (
   TDN_CODDNV           NUMBER(20)                      not null,
   TDN_CODIDI           VARCHAR2(2)                     not null,
   TDN_INFO             VARCHAR2(1000)
);

comment on table STR_TRADNV is
'Traducción especificaciones documentos por nivel de autenticación';

comment on column STR_TRADNV.TDN_CODDNV is
'Código interno';

comment on column STR_TRADNV.TDN_CODIDI is
'Código idioma';

comment on column STR_TRADNV.TDN_INFO is
'Información documento. Puede sobreescribirse por nivel de autenticación.';

alter table STR_TRADNV
   add constraint STR_TDN_PK primary key (TDN_CODDNV, TDN_CODIDI);

create table STR_TRADOC  (
   TRD_CODDOC           NUMBER(20)                      not null,
   TRD_CODIDI           VARCHAR2(2)                     not null,
   TRD_DESC             VARCHAR2(200)                   not null,
   TRD_INFO             VARCHAR2(1000)
);

comment on table STR_TRADOC is
'Traducciones documento';

comment on column STR_TRADOC.TRD_CODDOC is
'Código interno documento';

comment on column STR_TRADOC.TRD_CODIDI is
'Código idioma';

comment on column STR_TRADOC.TRD_DESC is
'Descripción documento';

comment on column STR_TRADOC.TRD_INFO is
'Información documento. Puede sobreescribirse por nivel de autenticación.';

alter table STR_TRADOC
   add constraint STR_TRD_PK primary key (TRD_CODDOC, TRD_CODIDI);

create table STR_TRAETN  (
   TET_CODETN           NUMBER(20)                      not null,
   TET_CODIDI           VARCHAR2(2)                     not null,
   TET_INSINI           BLOB,
   TET_MENINA           BLOB,
   TET_INSFIN           BLOB,
   TET_INSENT           BLOB,
   TET_FECLIM           BLOB
);

comment on table STR_TRAETN is
'Traducción especificaciones trámite';

comment on column STR_TRAETN.TET_CODETN is
'Código especificaciones tramite nivel';

comment on column STR_TRAETN.TET_CODIDI is
'Código idioma';

comment on column STR_TRAETN.TET_INSINI is
'Instrucciones inicio trámite';

comment on column STR_TRAETN.TET_MENINA is
'Mensaje particularizado a mostrar cuando el trámite este inactivo.';

comment on column STR_TRAETN.TET_INSFIN is
'Instrucciones fin trámite. ';

comment on column STR_TRAETN.TET_INSENT is
'Instrucciones entrega. Se mostrarán cuando el trámite se tenga que presentar presencialmente ( firma presencial o justificante de pago presencial)';

comment on column STR_TRAETN.TET_FECLIM is
'Mensaje de fecha limite particularizado. En caso de ser rellenado y de que el tramite sea presencial se mostrara este mensaje en lugar del por defecto';

alter table STR_TRAETN
   add constraint STR_TET_PK primary key (TET_CODETN, TET_CODIDI);

create table STR_TRAMIT  (
   TRA_CODIGO           NUMBER(20)                      not null,
   TRA_CODORG           NUMBER(20)                      not null,
   TRA_IDENTI           VARCHAR2(20)                    not null,
   TRA_IDPROC           VARCHAR2(100)                   not null
);

comment on table STR_TRAMIT is
'DEFINICION DE TRAMITE TELEMÁTICO';

comment on column STR_TRAMIT.TRA_IDENTI is
'Identificador funcional del trámite';

comment on column STR_TRAMIT.TRA_IDPROC is
'Identificador del procedimiento al que pertenece el trámite';


alter table STR_TRAMIT
   add constraint STR_TRAIDE_UNI unique (TRA_IDENTI);

alter table STR_TRAMIT
   add constraint STR_TRA_PK primary key (TRA_CODIGO);

create table STR_TRAMPL  (
   TMP_CODMPL           NUMBER(20)                      not null,
   TMP_CODIDI           VARCHAR2(2)                     not null,
   TMP_DESC             VARCHAR2(1000)                  not null
);

comment on table STR_TRAMPL is
'Traducciones mensajes plataforma';

comment on column STR_TRAMPL.TMP_CODMPL is
'Código mensaje';

comment on column STR_TRAMPL.TMP_CODIDI is
'Código idioma';

comment on column STR_TRAMPL.TMP_DESC is
'Descripción mensaje';

alter table STR_TRAMPL
   add constraint STR_TRAMPL_PK primary key (TMP_CODMPL, TMP_CODIDI);

create table STR_TRAMTR  (
   TMT_CODMTR           NUMBER(20)                      not null,
   TMT_CODIDI           VARCHAR2(2)                     not null,
   TMT_DESC             VARCHAR2(1000)
);

comment on table STR_TRAMTR is
'Traducción de los mensajes del trámite';

comment on column STR_TRAMTR.TMT_CODMTR is
'Código interno mensaje';

comment on column STR_TRAMTR.TMT_CODIDI is
'Código idioma';

comment on column STR_TRAMTR.TMT_DESC is
'Mensaje';

alter table STR_TRAMTR
   add constraint STR_TMT_PK primary key (TMT_CODMTR, TMT_CODIDI);

create table STR_TRANIV  (
   TNV_CODIGO           NUMBER(20)                      not null,
   TNV_CODTRV           NUMBER(20)                      not null,
   TNV_NIVAUT           VARCHAR2(3)                     not null,
   TNV_CODETN           NUMBER(20)                      not null
);

comment on table STR_TRANIV is
'Especificaciones del trámite por nivel de autenticación';

comment on column STR_TRANIV.TNV_CODIGO is
'Código interno';

comment on column STR_TRANIV.TNV_CODTRV is
'Código versión trámite';

comment on column STR_TRANIV.TNV_NIVAUT is
'Nivel autenticación: Certificado (C) / Ususario-pass (U) / Anónimo (A)';

comment on column STR_TRANIV.TNV_CODETN is
'Especificaciones trámite que pueden ser establecidas por defecto y sobreescritas según nivel autenticación';

alter table STR_TRANIV
   add constraint STR_TNVNIV_UNI unique (TNV_CODTRV, TNV_NIVAUT);

alter table STR_TRANIV
   add constraint STR_TNV_PK primary key (TNV_CODIGO);

create table STR_TRATRA  (
   TTR_CODTRA           NUMBER(20)                      not null,
   TTR_CODIDI           VARCHAR2(2)                     not null,
   TTR_DESC             VARCHAR2(200)                   not null
);

comment on table STR_TRATRA is
'Traducción de Trámites';

comment on column STR_TRATRA.TTR_CODTRA is
'Código trámite';

comment on column STR_TRATRA.TTR_CODIDI is
'Código idioma';

comment on column STR_TRATRA.TTR_DESC is
'Descripción del trámite';

alter table STR_TRATRA
   add constraint STR_TTR_PK primary key (TTR_CODTRA, TTR_CODIDI);

create table STR_TRAVER  (
   TRV_CODIGO           NUMBER(20)                      not null,
   TRV_CODTRA           NUMBER(20)                      not null,
   TRV_VERSIO           NUMBER(2)                       not null,
   TRV_CODETN           NUMBER(20)                      not null,
   TRV_DESVER           VARCHAR2(500),
   TRV_ORGDES           VARCHAR2(25)                    not null,
   TRV_UNIADM           NUMBER(19)                      not null,
   TRV_INIPLA           DATE,
   TRV_FINPLA           DATE,
   TRV_DESTIN           VARCHAR2(1)                     not null,
   TRV_PRENVA           VARCHAR2(1)                    default 'N' not null,
   TRV_FIRMA            VARCHAR2(1)                     not null,
   TRV_REGOFI           VARCHAR2(25),
   TRV_REGAST           VARCHAR2(25),
   TRV_CONEJB           VARCHAR2(500),
   TRV_CONREM           VARCHAR2(1)                    default 'L' not null,
   TRV_CONURL           VARCHAR2(500),
   TRV_CONAUT           VARCHAR2(1)                    default 'N' not null,
   TRV_CONUSU           VARCHAR2(500),
   TRV_CONPWD           VARCHAR2(500),
   TRV_BLOQUE           VARCHAR2(1)                    default 'N' not null,
   TRV_BLOUSU           VARCHAR2(1536),
   TRV_IDISOP           VARCHAR2(100)                  default 'es,ca' not null,
   TRV_REDUCI           VARCHAR2(1)                    default 'N' not null,
   TRV_REDFIN           VARCHAR2(1)                    default 'N' not null,
   TRV_TAGCAR           VARCHAR2(100),
   TRV_FECCAR           DATE,
   TRV_ANODEF           VARCHAR2(1)                    default 'N' not null,
   TRV_CONTIP           VARCHAR2(1)                    default 'E' not null,
   TRV_CONWSV           VARCHAR2(10 BYTE)
);


comment on table STR_TRAVER is
'Versión de trámites';

comment on column STR_TRAVER.TRV_CODIGO is
'Código interno';

comment on column STR_TRAVER.TRV_CODTRA is
'Código trámite';

comment on column STR_TRAVER.TRV_VERSIO is
'Versión trámite';

comment on column STR_TRAVER.TRV_CODETN is
'Especificaciones trámite que pueden ser establecidas por defecto y sobreescritas según nivel autenticación';

comment on column STR_TRAVER.TRV_DESVER is
'Motivo de la versión';

comment on column STR_TRAVER.TRV_ORGDES is
'Identificador del órgano destino del trámite. Mapeado a tabla BORGANI de Registro ';

comment on column STR_TRAVER.TRV_UNIADM is
'Unidad Administrativa responsable trámite. Mapeado a tabla Unidades administrativas del SAC';

comment on column STR_TRAVER.TRV_INIPLA is
'Fecha inicio del plazo de presentación';

comment on column STR_TRAVER.TRV_FINPLA is
'Fecha fin del plazo de presentación';

comment on column STR_TRAVER.TRV_DESTIN is
'Indica destino trámite: Registro/Preregistro (R) - Bandeja (B) - Consulta (C)';

comment on column STR_TRAVER.TRV_PRENVA is
'Para preenvio indica si se realiza la confirmacion automatica de las entradas';

comment on column STR_TRAVER.TRV_FIRMA is
'Indica si un trámite debe firmarse: (si autenticación certificado se realizará firma digital asiento sino se realizará firma manuscrita del justificante que deberá presentarse presencialmente)
 - para destino Registro: opcional
 - para destino Bandeja: opcional
 - para destino Consulta: no procede';

comment on column STR_TRAVER.TRV_REGOFI is
'Para trámite con destino Registro indica Oficina Registro (mapeado a bagecom_oficines)';

comment on column STR_TRAVER.TRV_REGAST is
'Para trámite con destino Registro indica Tipo Asunto (bztdocu)';

comment on column STR_TRAVER.TRV_CONEJB is
'Para trámite con destino Consulta indica JNDI del Ejb que sirve la consulta';

comment on column STR_TRAVER.TRV_CONREM is
'Para trámite con destino Consulta indica si el Ejb que sirve la consulta es remoto (R) o local (L)';

comment on column STR_TRAVER.TRV_CONURL is
'Para trámite con destino Consulta si  el Ejb que sirve la consulta es remoto indica la URL de acceso';

comment on column STR_TRAVER.TRV_CONAUT is
'Para trámite con destino Consulta indica si se debe realizar:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password
 - C: explícita a través plugin autenticación del organismo';

comment on column STR_TRAVER.TRV_CONUSU is
'Para trámite con destino Consulta y con autenticación explicíta con usuario/password se indica usuario';

comment on column STR_TRAVER.TRV_CONPWD is
'Para trámite con destino Consulta y con autenticación explicíta  con usuario/password  se indica password';

comment on column STR_TRAVER.TRV_BLOQUE is
'Permite habilitar sistema de bloqueo para modificación de trámites en el sistraback de manera que no permite la modificación simultanea';

comment on column STR_TRAVER.TRV_BLOUSU is
'En caso de estar bloqueado indica el usuario seycon que lo tiene bloqueado';

comment on column STR_TRAVER.TRV_IDISOP is
'Idiomas soportados: lista de idiomas soportados separados por comas';

comment on column STR_TRAVER.TRV_REDUCI is
'Indica si un trámite sigue el circuito reducido S - si  N - no';

comment on column STR_TRAVER.TRV_REDFIN is
'Indica para un trámite se salta automaticamente a la url de fin ( S - si  N - no) sin mostrar la pantalla de justificante';

comment on column STR_TRAVER.TRV_TAGCAR is
'Tag que indica el cuaderno de carga en el que se ha generado';

comment on column STR_TRAVER.TRV_FECCAR is
'Fecha en la que se ha exportado (al importar el xml se alimentará este campo del xml origen)';

comment on column STR_TRAVER.TRV_ANODEF is
'Autenticacion anonima por defecto: Si esta activado y en caso de que este seleccionado A y existan otros niveles de autenticacion (C o U)  en el tramite,
si se accede al tramite sin estar autenticado se realizara una autenticacion anonima automatica. Si se esta autenticado se respetara la autenticacion actual
(siempre que este permitida en el tramite)';


alter table STR_TRAVER
   add constraint STR_TRVVER_UNI unique (TRV_CODTRA, TRV_VERSIO);

alter table STR_TRAVER
   add constraint STR_TRV_PK primary key (TRV_CODIGO);

alter table STR_DATJUS
   add constraint STR_DJSETN_FK foreign key (DJS_CODETN)
      references STR_ESPNIV (ETN_CODIGO);

alter table STR_DMUNIC
   add constraint STR_MUNPRO_FK foreign key (MUN_PROVIN)
      references STR_DPROVI (PRO_CODIGO);

alter table STR_DOCNIV
   add constraint STR_DNVDOC_FK foreign key (DNV_CODDOC)
      references STR_DOCUM (DOC_CODIGO);

alter table STR_DOCUM
   add constraint STR_DOCTRV_FK foreign key (DOC_CODTRV)
      references STR_TRAVER (TRV_CODIGO);

alter table STR_DOMIN
   add constraint STR_DOMORG_FK foreign key (DOM_CODORG)
      references STR_ORGRES (ORG_CODIGO);

alter table STR_IMPFIC
   add constraint STR_IMFIMP_FK foreign key (IMF_CODIMP)
      references STR_IMPORT (IMP_CODIGO);

alter table STR_MENTRA
   add constraint STR_MTRTRV_FK foreign key (MTR_CODTRV)
      references STR_TRAVER (TRV_CODIGO);

alter table STR_TRADJS
   add constraint STR_TDJDJS_FK foreign key (TDJ_CODDJS)
      references STR_DATJUS (DJS_CODIGO);

alter table STR_TRADJS
   add constraint STR_TDJIDI_FK foreign key (TDJ_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRADNV
   add constraint STR_TDNDNV_FK foreign key (TDN_CODDNV)
      references STR_DOCNIV (DNV_CODIGO);

alter table STR_TRADNV
   add constraint STR_TDNIDI_FK foreign key (TDN_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRADOC
   add constraint STR_TRDDOC_FK foreign key (TRD_CODDOC)
      references STR_DOCUM (DOC_CODIGO);

alter table STR_TRADOC
   add constraint STR_TRDIDI_FK foreign key (TRD_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRAETN
   add constraint STR_TETETN_FK foreign key (TET_CODETN)
      references STR_ESPNIV (ETN_CODIGO);

alter table STR_TRAETN
   add constraint STR_TETIDI_FK foreign key (TET_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRAMIT
   add constraint STR_TRAORG_FK foreign key (TRA_CODORG)
      references STR_ORGRES (ORG_CODIGO);

alter table STR_TRAMPL
   add constraint STR_TMPIDI_FK foreign key (TMP_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRAMPL
   add constraint STR_TMPMPL_FK foreign key (TMP_CODMPL)
      references STR_MENPLA (MPL_CODIGO);

alter table STR_TRAMTR
   add constraint STR_TMTIDI_FK foreign key (TMT_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRAMTR
   add constraint STR_TMTMTR_FK foreign key (TMT_CODMTR)
      references STR_MENTRA (MTR_CODIGO);

alter table STR_TRANIV
   add constraint STR_TNVETR_FK foreign key (TNV_CODETN)
      references STR_ESPNIV (ETN_CODIGO);

alter table STR_TRANIV
   add constraint STR_TNVTRV_FK foreign key (TNV_CODTRV)
      references STR_TRAVER (TRV_CODIGO);

alter table STR_TRATRA
   add constraint STR_TTRIDI_FK foreign key (TTR_CODIDI)
      references STR_IDIOMA (IDI_CODIGO);

alter table STR_TRATRA
   add constraint STR_TTRTRA_FK foreign key (TTR_CODTRA)
      references STR_TRAMIT (TRA_CODIGO);

alter table STR_TRAVER
   add constraint STR_TRVETR_FK foreign key (TRV_CODETN)
      references STR_ESPNIV (ETN_CODIGO);

alter table STR_TRAVER
   add constraint STR_TRVTRA_FK foreign key (TRV_CODTRA)
      references STR_TRAMIT (TRA_CODIGO);



create table STR_USUTRA  (
   UST_CODUSU           VARCHAR2(200)                   not null,
   UST_CODTRA           NUMBER(20)                      not null
);

comment on table STR_USUTRA is
'Permisos individuales para un usuario de acceso a tramites';

comment on column STR_USUTRA.UST_CODUSU is
'CODIGO USUARIO';

comment on column STR_USUTRA.UST_CODTRA is
'CODIGO TRAMITE';

alter table STR_USUTRA
   add constraint STR_UST_PK primary key (UST_CODUSU, UST_CODTRA);

alter table STR_USUTRA
   add constraint STR_USTTRA_FK foreign key (UST_CODTRA)
      references STR_TRAMIT (TRA_CODIGO);


create table STR_GRUPOS  (
   GRP_CODIGO           VARCHAR2(50)                    not null,
   GRP_NOMBRE           VARCHAR2(100)                   not null,
   GRP_DESCP            VARCHAR2(300)
);

comment on table STR_GRUPOS is
'Grupos de usuarios para establecer permisos de acceso a tramites';

comment on column STR_GRUPOS.GRP_CODIGO is
'Código grupo';

comment on column STR_GRUPOS.GRP_NOMBRE is
'Nombre grupo';

comment on column STR_GRUPOS.GRP_DESCP is
'Descripción grupo';

alter table STR_GRUPOS
   add constraint STR_GRP_PK primary key (GRP_CODIGO);

create table STR_GRPUSU  (
   GRU_CODGRP           VARCHAR2(50)                    not null,
   GRU_CODUSU           VARCHAR2(200)                   not null
);

comment on table STR_GRPUSU is
'Asignación de usuarios a grupos';

comment on column STR_GRPUSU.GRU_CODGRP is
'CODIGO GRUPO';

comment on column STR_GRPUSU.GRU_CODUSU is
'CODIGO USUARIO';

alter table STR_GRPUSU
   add constraint STR_GRU_PK primary key (GRU_CODGRP, GRU_CODUSU);

alter table STR_GRPUSU
   add constraint STR_GRUGRP_FK foreign key (GRU_CODGRP)
      references STR_GRUPOS (GRP_CODIGO);

create table STR_GRPTRA  (
   GRT_CODGRP           VARCHAR2(50)                    not null,
   GRT_CODTRA           NUMBER(20)                      not null
);

comment on table STR_GRPTRA is
'Trámites a los que el grupo tiene acceso';

comment on column STR_GRPTRA.GRT_CODGRP is
'CODIGO GRUPO';

comment on column STR_GRPTRA.GRT_CODTRA is
'CODIGO TRAMITE';

alter table STR_GRPTRA
   add constraint STR_GRT_PK primary key (GRT_CODGRP, GRT_CODTRA);

alter table STR_GRPTRA
   add constraint STR_GRTGRP_FK foreign key (GRT_CODGRP)
      references STR_GRUPOS (GRP_CODIGO);

alter table STR_GRPTRA
   add constraint STR_GRTTRA_FK foreign key (GRT_CODTRA)
      references STR_TRAMIT (TRA_CODIGO);


--- V2.1.0
alter table STR_ESPNIV  add ETN_JNOCLA           VARCHAR2(1)       DEFAULT 'N'     not null ;

alter table STR_ESPNIV  add ETN_JNONN            VARCHAR2(1)      DEFAULT 'N'       not null ;

comment on column STR_ESPNIV.ETN_JNOCLA is
'Indica si se oculta la clave de tramitacion en el pdf de justificante estandard';

comment on column STR_ESPNIV.ETN_JNONN is
'Indica si se oculta el nif y nombre en el pdf de justificante estandard';



-- V2.2.2
alter table STR_DOCNIV  add  DNV_PAGPLG           VARCHAR2(50) default '.' NOT NULL;

comment on column STR_DOCNIV.DNV_PAGPLG is
	'Para Pagos: indica si se usa un plugin adicional de pagos. Si no usara el defecto.';

-- V2.2.6 to 2.2.7
alter table STR_ESPNIV  add ETN_ALETRA VARCHAR2(1) default 'N' not null;
alter table STR_ESPNIV  add ETN_ALESMS VARCHAR2(1) default 'N' not null;

comment on column STR_ESPNIV.ETN_ALETRA is
'Indica si se generan alertas de tramitacion (previas envio tramite): N: No permite /  S Si permite, el ciudadano elige / O: Obligatoria notificación telemática / X: No especificada';
comment on column STR_ESPNIV.ETN_ALESMS is
'Indica si se permiten sms en los avisos de notificacion telematica (S/N)';


alter table STR_TRAVER add TRV_REGAUT VARCHAR2(1) default 'N' not null;

comment on column STR_TRAVER.TRV_REGAUT is
'Registro automatico: al llegar al paso registrar se dispara automaticamente el envio';

-- V2.3.0
alter table STR_DOMIN  add  DOM_WSSOA            VARCHAR2(100);
comment on column STR_DOMIN.DOM_WSSOA is
'WS: Soap action';

-- V2.3.4
ALTER table STR_DOMIN MODIFY DOM_URL VARCHAR2(500);

-- V2.3.7
ALTER table STR_DOCNIV  ADD DNV_FORGUA VARCHAR2(1) default 'N' not null;
comment on column STR_DOCNIV.DNV_FORGUA is 'Para Formulario: indica si se permite guardar sin terminar';

-- V3.0.2
-- SISTRA: DIRECCION INTERESADOS
alter table STR_ESPNIV add ETN_RTEDAT BLOB;
alter table STR_ESPNIV add ETN_RDODAT BLOB;

comment on column STR_ESPNIV.ETN_RTEDAT is
'Datos desglosados representante (nif, nombre, direccion, email,...)';
comment on column STR_ESPNIV.ETN_RDODAT is
'Datos desglosados representado (nif, nombre, direccion, email,...)';

-- V3.1.3
-- SISTRA: DEBUG POR TRAMITE
alter table STR_TRAVER  add  TRV_DEBUG            VARCHAR2(1)                    default 'N' not null;
comment on column STR_TRAVER.TRV_DEBUG is 'Indica si el debug esta habilitado';

-- SISTRA: FIN TRAMITE AUTO PARA TRAMITES CON PAGO FINALIZADO
alter table STR_ESPNIV  add ETN_ALEFIN  VARCHAR2(1) default 'N' not null;
comment on column STR_ESPNIV.ETN_ALEFIN is
'Indica si se intenta finalizar automáticamente el trámite antes de realizar la alerta de trámites inacabados con pago realizado';

