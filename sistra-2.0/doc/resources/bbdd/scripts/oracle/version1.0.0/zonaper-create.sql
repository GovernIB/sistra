ALTER SESSION SET NLS_LENGTH_SEMANTICS = 'CHAR';

create sequence ZPE_SEQDEN;

create sequence ZPE_SEQDHE;

create sequence ZPE_SEQDNO;

create sequence ZPE_SEQDPE;

create sequence ZPE_SEQDPR;

create sequence ZPE_SEQELE;

create sequence ZPE_SEQENT;

create sequence ZPE_SEQEXP;

create sequence ZPE_SEQHIE;

create sequence ZPE_SEQNOT;

create sequence ZPE_SEQPER;

create sequence ZPE_SEQPRE;

create sequence ZPE_SEQTPE;

create table ZPE_DOCENT  (
   DEN_CODIGO           NUMBER(20)                      not null,
   DEN_CODENT           NUMBER(20)                      not null,
   DEN_DOCIDE           VARCHAR2(5)                     not null,
   DEN_DOCNUM           NUMBER(2)                       not null,
   DEN_DESC             VARCHAR2(500)                   not null,
   DEN_RDSCOD           NUMBER(20)                      not null,
   DEN_RDSCLA           VARCHAR2(10)                    not null
);

comment on column ZPE_DOCENT.DEN_CODIGO is
'Código interno';

comment on column ZPE_DOCENT.DEN_CODENT is
'Código entrada';

comment on column ZPE_DOCENT.DEN_DOCIDE is
'Identificador documento';

comment on column ZPE_DOCENT.DEN_DOCNUM is
'Número instancia';

comment on column ZPE_DOCENT.DEN_DESC is
'Descripción documento';

comment on column ZPE_DOCENT.DEN_RDSCOD is
'Código RDS del documento';

comment on column ZPE_DOCENT.DEN_RDSCLA is
'Clave RDS del documento';

alter table ZPE_DOCENT
   add constraint ZPE_DEN_PK primary key (DEN_CODIGO);

create index ZPE_DENENT_FK_I on ZPE_DOCENT (
   DEN_CODENT ASC
);

create table ZPE_DOCHIE  (
   DHE_CODIGO           NUMBER(20)                      not null,
   DHE_CODHIE           NUMBER(20)                      not null,
   DHE_RDSCOD           NUMBER(20)                      not null,
   DHE_RDSCLA           VARCHAR2(10)                    not null,
   DHE_TITULO           VARCHAR2(256)                   not null
);

comment on table ZPE_DOCHIE is
'Documento perteneciente a un evento del histórico de ';

comment on column ZPE_DOCHIE.DHE_CODIGO is
'Identificador único del documento';

comment on column ZPE_DOCHIE.DHE_CODHIE is
'Identificador único del evento';

comment on column ZPE_DOCHIE.DHE_RDSCOD is
'Codigo rds del documento';

comment on column ZPE_DOCHIE.DHE_RDSCLA is
'Clave rds del documento';

comment on column ZPE_DOCHIE.DHE_TITULO is
'Titulo del documento';

alter table ZPE_DOCHIE
   add constraint ZPE_DHE_PK primary key (DHE_CODIGO);

create index ZPE_DHEHIE_FK_I on ZPE_DOCHIE (
   DHE_CODHIE ASC
);

create table ZPE_DOCNOT  (
   DNO_CODIGO           NUMBER(20)                      not null,
   DNO_CODNOT           NUMBER(20)                      not null,
   DNO_DOCIDE           VARCHAR2(5)                     not null,
   DNO_DOCNUM           NUMBER(2)                       not null,
   DNO_DESC             VARCHAR2(500)                   not null,
   DNO_RDSCOD           NUMBER(20)                      not null,
   DNO_RDSCLA           VARCHAR2(10)                    not null
);

comment on column ZPE_DOCNOT.DNO_CODIGO is
'Código interno';

comment on column ZPE_DOCNOT.DNO_CODNOT is
'Código notificacion';

comment on column ZPE_DOCNOT.DNO_DOCIDE is
'Identificador documento';

comment on column ZPE_DOCNOT.DNO_DOCNUM is
'Número instancia';

comment on column ZPE_DOCNOT.DNO_DESC is
'Descripción documento';

comment on column ZPE_DOCNOT.DNO_RDSCOD is
'Código RDS del documento';

comment on column ZPE_DOCNOT.DNO_RDSCLA is
'Clave RDS del documento';

alter table ZPE_DOCNOT
   add constraint ZPE_DNO_PK primary key (DNO_CODIGO);

create index ZPE_DNONOT_FK_I on ZPE_DOCNOT (
   DNO_CODNOT ASC
);

create table ZPE_DOCPER  (
   DPE_CODIGO           NUMBER(20)                      not null,
   DPE_CODTPE           NUMBER(20)                      not null,
   DPE_DOCIDE           VARCHAR2(5)                     not null,
   DPE_DOCNUM           NUMBER(2)                       not null,
   DPE_ESTADO           VARCHAR2(1)                     not null,
   DPE_RDSCOD           NUMBER(20),
   DPE_RDSCLA           VARCHAR2(10),
   DPE_NOMFIC           VARCHAR2(255),
   DPE_GENDES           VARCHAR2(255)
);

comment on table ZPE_DOCPER is
'DOCUMENTO PERSISTENTE';

comment on column ZPE_DOCPER.DPE_CODIGO is
'CODIGO';

comment on column ZPE_DOCPER.DPE_CODTPE is
'CODIGO TRAMITE PERSISTENTE';

comment on column ZPE_DOCPER.DPE_DOCIDE is
'IDENTIFICADOR DOCUMENTO';

comment on column ZPE_DOCPER.DPE_DOCNUM is
'NUMERO INSTANCIA DOCUMENTO. TENDRA SIGNIFICADO PARA LOS DOCUMENTOS GENERICOS, PARA LOS DEMÁS SERÁ 1.';

comment on column ZPE_DOCPER.DPE_ESTADO is
'ESTADO: CORRECTO (S) / INCORRECTO (N)  / VACIO (V)';

comment on column ZPE_DOCPER.DPE_RDSCOD is
'EN CASO DE ESTAR RELLENO INDICARA CODIGO DE DOCUMENTO DE LA REF RDS';

comment on column ZPE_DOCPER.DPE_RDSCLA is
'EN CASO DE ESTAR RELLENO INDICARA CLAVE DE DOCUMENTO DE LA REF RDS';

comment on column ZPE_DOCPER.DPE_NOMFIC is
'NOMBRE DE ULTIMO FICHERO ANEXADO PARA DOCUMENTOS ANEXOS';

comment on column ZPE_DOCPER.DPE_GENDES is
'DESCRIPCION PERSONALIZADA PARA GENÉRICOS';

alter table ZPE_DOCPER
   add constraint ZPE_DPEIDE_UNI unique (DPE_CODTPE, DPE_DOCIDE, DPE_DOCNUM);

alter table ZPE_DOCPER
   add constraint ZPE_DPE_PK primary key (DPE_CODIGO);

create index ZPE_DPETPE_FK_I on ZPE_DOCPER (
   DPE_CODTPE ASC
);

create table ZPE_DOCPRE  (
   DPR_CODIGO           NUMBER(20)                      not null,
   DPR_CODPRE           NUMBER(20)                      not null,
   DPR_PRESE            VARCHAR2(1)                     not null,
   DPR_DESC             VARCHAR2(500)                   not null,
   DPR_DOCIDE           VARCHAR2(5),
   DPR_DOCNUM           NUMBER(2),
   DPR_RDSCOD           NUMBER(20),
   DPR_RDSCLA           VARCHAR2(10),
   DPR_TIPDOC           VARCHAR2(1),
   DPR_COMPUL           VARCHAR2(1),
   DPR_FOTPIA           VARCHAR2(1),
   DPR_FIRMA            VARCHAR2(1),
   DPR_OK               VARCHAR2(1)
);

comment on table ZPE_DOCPRE is
'Documentos asociados al preregistro';

comment on column ZPE_DOCPRE.DPR_CODIGO is
'Código interno';

comment on column ZPE_DOCPRE.DPR_CODPRE is
'Código entrada';

comment on column ZPE_DOCPRE.DPR_PRESE is
'Indica si el documento requiere acción presencial';

comment on column ZPE_DOCPRE.DPR_DESC is
'Descripción documento';

comment on column ZPE_DOCPRE.DPR_DOCIDE is
'Identificador documento  (Para documentos anexados telemáticamente sirve para asociar con documento del asiento)';

comment on column ZPE_DOCPRE.DPR_DOCNUM is
'Número instancia  (Para documentos anexados telemáticamente sirve para asociar con documento del asiento)';

comment on column ZPE_DOCPRE.DPR_RDSCOD is
'Para documentos anexados telemáticamente: Código RDS del documento';

comment on column ZPE_DOCPRE.DPR_RDSCLA is
'Para documentos anexados telemáticamente: Clave RDS del documento';

comment on column ZPE_DOCPRE.DPR_TIPDOC is
'Para documentos a presentar presencialmente: Indica tipo de documento J/F/A/P (Justificante/Formulario/Anexo/Pago)';

comment on column ZPE_DOCPRE.DPR_COMPUL is
'Para documentos a presentar presencialmente: Indica si se debe compulsar';

comment on column ZPE_DOCPRE.DPR_FOTPIA is
'Para documentos a presentar presencialmente: Indica si se debe presentar una fotocopia';

comment on column ZPE_DOCPRE.DPR_FIRMA is
'Para documentos a presentar presencialmente: Indica si debe ir firmado';

comment on column ZPE_DOCPRE.DPR_OK is
'Para documentos a presentar presencialmente: Indica si  se da como entregado';

alter table ZPE_DOCPRE
   add constraint ZPE_DPR_PK primary key (DPR_CODIGO);

create index ZPE_DPRPRE_FK_I on ZPE_DOCPRE (
   DPR_CODPRE ASC
);

create table ZPE_DPEBCK  (
   DPB_CODIGO           NUMBER(20)                      not null,
   DPB_CODTPB           NUMBER(20)                      not null,
   DPB_DOCIDE           VARCHAR2(5)                     not null,
   DPB_DOCNUM           NUMBER(2)                       not null,
   DPB_ESTADO           VARCHAR2(1)                     not null,
   DPB_RDSCOD           NUMBER(20),
   DPB_RDSCLA           VARCHAR2(10),
   DPB_NOMFIC           VARCHAR2(255)
);

comment on table ZPE_DPEBCK is
'DOCUMENTO PERSISTENTE backup';

comment on column ZPE_DPEBCK.DPB_CODIGO is
'CODIGO';

comment on column ZPE_DPEBCK.DPB_CODTPB is
'CODIGO TRAMITE PERSISTENTE';

comment on column ZPE_DPEBCK.DPB_DOCIDE is
'IDENTIFICADOR DOCUMENTO';

comment on column ZPE_DPEBCK.DPB_DOCNUM is
'NUMERO INSTANCIA DOCUMENTO. TENDRA SIGNIFICADO PARA LOS DOCUMENTOS GENERICOS, PARA LOS DEMÁS SERÁ 1.';

comment on column ZPE_DPEBCK.DPB_ESTADO is
'ESTADO: CORRECTO (S) / INCORRECTO (N)';

comment on column ZPE_DPEBCK.DPB_RDSCOD is
'EN CASO DE ESTAR RELLENO INDICARA CODIGO DE DOCUMENTO DE LA REF RDS';

comment on column ZPE_DPEBCK.DPB_RDSCLA is
'EN CASO DE ESTAR RELLENO INDICARA CLAVE DE DOCUMENTO DE LA REF RDS';

comment on column ZPE_DPEBCK.DPB_NOMFIC is
'NOMBRE DE ULTIMO FICHERO ANEXADO PARA DOCUMENTOS ANEXOS';

alter table ZPE_DPEBCK
   add constraint ZPE_DPB_PK primary key (DPB_CODIGO);

create index ZPE_DPBTPB_FK_I on ZPE_DPEBCK (
   DPB_CODTPB ASC
);

create table ZPE_DPRBCK  (
   DRB_CODIGO           NUMBER(20)                      not null,
   DRB_CODPRE           NUMBER(20)                      not null,
   DRB_PRESE            VARCHAR2(1)                     not null,
   DRB_DESC             VARCHAR2(500)                   not null,
   DRB_DOCIDE           VARCHAR2(5),
   DRB_DOCNUM           NUMBER(2),
   DRB_RDSCOD           NUMBER(20),
   DRB_RDSCLA           VARCHAR2(10),
   DRB_TIPDOC           VARCHAR2(1),
   DRB_COMPUL           VARCHAR2(1),
   DRB_FOTPIA           VARCHAR2(1),
   DRB_FIRMA            VARCHAR2(1),
   DRB_OK               VARCHAR2(1)
);

comment on table ZPE_DPRBCK is
'Documentos asociados al preregistro backup';

comment on column ZPE_DPRBCK.DRB_CODIGO is
'Código interno';

comment on column ZPE_DPRBCK.DRB_CODPRE is
'Código entrada';

comment on column ZPE_DPRBCK.DRB_PRESE is
'Indica si el documento requiere acción presencial';

comment on column ZPE_DPRBCK.DRB_DESC is
'Descripción documento';

comment on column ZPE_DPRBCK.DRB_DOCIDE is
'Identificador documento  (sirve para asociar con documento del asiento)';

comment on column ZPE_DPRBCK.DRB_DOCNUM is
'Número instancia  (sirve para asociar con documento del asiento)';

comment on column ZPE_DPRBCK.DRB_RDSCOD is
'Para documentos anexados telemáticamente: Código RDS del documento';

comment on column ZPE_DPRBCK.DRB_RDSCLA is
'Para documentos anexados telemáticamente: Clave RDS del documento';

comment on column ZPE_DPRBCK.DRB_TIPDOC is
'Para documentos a presentar presencialmente: Indica tipo de documento J/F/A/P (Justificante/Formulario/Anexo/Pago)';

comment on column ZPE_DPRBCK.DRB_COMPUL is
'Para documentos a presentar presencialmente: Indica si se debe compulsar';

comment on column ZPE_DPRBCK.DRB_FOTPIA is
'Para documentos a presentar presencialmente: Indica si se debe presentar una fotocopia';

comment on column ZPE_DPRBCK.DRB_FIRMA is
'Para documentos a presentar presencialmente: Indica si debe ir firmado';

comment on column ZPE_DPRBCK.DRB_OK is
'Para documentos a presentar presencialmente: Indica si  se da como entregado';

alter table ZPE_DPRBCK
   add constraint ZPE_DRB_PK primary key (DRB_CODIGO);

create index ZPE_DRBPRB_FK_I on ZPE_DPRBCK (
   DRB_CODPRE ASC
);

create table ZPE_ELEEX  (
   ELE_CODIGO           NUMBER(20)                      not null,
   ELE_CODEXP           NUMBER(20)                      not null,
   ELE_FECHA            DATE                            not null,
   ELE_TIPO             VARCHAR2(1)                     not null,
   ELE_CODELE           NUMBER(20)                      not null
);

comment on table ZPE_ELEEX is
'Elementos del expediente';

comment on column ZPE_ELEEX.ELE_CODIGO is
'Identificador único del elemento de expediente';

comment on column ZPE_ELEEX.ELE_CODEXP is
'Identificador interno único del expediente';

comment on column ZPE_ELEEX.ELE_FECHA is
'Fecha creación';

comment on column ZPE_ELEEX.ELE_TIPO is
'Tipo evento (T Tramite telematic/ P Tramite preregistro / N Notificacion / A Aviso)';

comment on column ZPE_ELEEX.ELE_CODELE is
'Codigo elemento destino (según tipo evento)';

alter table ZPE_ELEEX
   add constraint ZPE_ELE_PK primary key (ELE_CODIGO);

create unique index ZPE_ELEELE_UNI on ZPE_ELEEX (
   ELE_TIPO ASC,
   ELE_CODELE ASC
);

create index ZPE_EXPELE_FK_I on ZPE_ELEEX (
   ELE_CODEXP ASC
);

create table ZPE_ENTTEL  (
   ENT_CODIGO           NUMBER(20)                      not null,
   ENT_TIPO             VARCHAR2(1)                     not null,
   ENT_IDEPER           VARCHAR2(50)                    not null,
   ENT_NIVAUT           VARCHAR2(1)                     not null,
   ENT_DESC             VARCHAR2(200)                   not null,
   ENT_USER             VARCHAR2(1536),
   ENT_NUMREG           VARCHAR2(50)                    not null,
   ENT_FECHA            DATE                            not null,
   ENT_CODASI           NUMBER(20)                      not null,
   ENT_CLAASI           VARCHAR2(10)                    not null,
   ENT_CODJUS           NUMBER(20)                      not null,
   ENT_CLAJUS           VARCHAR2(10)                    not null,
   ENT_IDIOMA           VARCHAR2(2)                     not null,
   ENT_NIFRTE           VARCHAR2(12),
   ENT_NOMRTE           VARCHAR2(500),
   ENT_NIFRDO           VARCHAR2(12),
   ENT_NOMRDO           VARCHAR2(500),
   ENT_TRAMOD           VARCHAR2(10),
   ENT_TRAVER           NUMBER(2),
   ENT_AVISOS           VARCHAR2(1),
   ENT_AVISMS           VARCHAR2(10),
   ENT_AVIEMA           VARCHAR2(500),
   ENT_NOTTEL           VARCHAR2(1)
);

comment on table ZPE_ENTTEL is
'Log de entradas telemáticas';

comment on column ZPE_ENTTEL.ENT_CODIGO is
'Código interno entrada';

comment on column ZPE_ENTTEL.ENT_TIPO is
'Tipo de entrada: E (Registro Entrada) / B (Envío Bandeja)';

comment on column ZPE_ENTTEL.ENT_IDEPER is
'IDENTIFICADOR DE PERSISTENCIA. ';

comment on column ZPE_ENTTEL.ENT_NIVAUT is
'Nivel autenticación U (Usuario) / C (Certificado) / A (Anónimo)';

comment on column ZPE_ENTTEL.ENT_DESC is
'Descripción del trámite';

comment on column ZPE_ENTTEL.ENT_USER is
'En caso de estar autenticado referencia usuario Seycon que realiza la entrada';

comment on column ZPE_ENTTEL.ENT_NUMREG is
'Indica el nº de registro/nº de envío';

comment on column ZPE_ENTTEL.ENT_FECHA is
'Fecha de registro / envío';

comment on column ZPE_ENTTEL.ENT_CODASI is
'Código de la referencia RDS del asiento';

comment on column ZPE_ENTTEL.ENT_CLAASI is
'Clave de la referencia RDS del asiento';

comment on column ZPE_ENTTEL.ENT_CODJUS is
'Código de la referencia RDS del justificante';

comment on column ZPE_ENTTEL.ENT_CLAJUS is
'Clave de la referencia RDS del justificante';

comment on column ZPE_ENTTEL.ENT_IDIOMA is
'Idioma en el que se ha realizado';

comment on column ZPE_ENTTEL.ENT_NIFRTE is
'Indica NIF del representante';

comment on column ZPE_ENTTEL.ENT_NOMRTE is
'Indica nombre del representante';

comment on column ZPE_ENTTEL.ENT_NIFRDO is
'En caso de existir representado, indica NIF del representado';

comment on column ZPE_ENTTEL.ENT_NOMRDO is
'En caso de existir representado, indica nombre del representado';

comment on column ZPE_ENTTEL.ENT_TRAMOD is
'MODELO TRAMITE (Permite nulos ya que es un campo añadido a posteriori)';

comment on column ZPE_ENTTEL.ENT_TRAVER is
'VERSION TRAMITE (Permite nulos ya que es un campo añadido a posteriori)';

comment on column ZPE_ENTTEL.ENT_AVISOS is
'Indica si se habilitan los avisos para expediente';

comment on column ZPE_ENTTEL.ENT_AVISMS is
'Telefono para avisos SMS (nulo no aviso)';

comment on column ZPE_ENTTEL.ENT_AVIEMA is
'Email para avisos por email (nulo no aviso)';

comment on column ZPE_ENTTEL.ENT_NOTTEL is
'Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.';

alter table ZPE_ENTTEL
   add constraint ZPE_ENT_PK primary key (ENT_CODIGO);

create unique index ZPE_ENTIDP_UNI on ZPE_ENTTEL (
   ENT_IDEPER ASC
);

create table ZPE_EXPEDI  (
   EXP_CODIGO           NUMBER(20)                      not null,
   EXP_UNIADM           NUMBER(19)                      not null,
   EXP_IDEXP            VARCHAR2(50)                    not null,
   EXP_CLAVE            VARCHAR2(50),
   EXP_FECEXP           DATE                            not null,
   EXP_DESC             VARCHAR2(512)                   not null,
   EXP_IDIOMA           VARCHAR2(2)                     not null,
   EXP_SEYCIU           VARCHAR2(1536),
   EXP_NIFRDO           VARCHAR2(10),
   EXP_NOMRDO           VARCHAR2(500),
   EXP_USER             VARCHAR2(1536)                  not null,
   EXP_NUMBTE           VARCHAR2(50),
   EXP_FECCON           DATE,
   EXP_FECINI           DATE,
   EXP_FECULT           DATE,
   EXP_ESTADO           VARCHAR2(2),
   EXP_AVISOS           VARCHAR2(1),
   EXP_AVISMS           VARCHAR2(10),
   EXP_AVIEMA           VARCHAR2(500)
);

comment on column ZPE_EXPEDI.EXP_CODIGO is
'Identificador interno único del expediente';

comment on column ZPE_EXPEDI.EXP_UNIADM is
'Unidad administrativa que da de alta el expediente';

comment on column ZPE_EXPEDI.EXP_IDEXP is
'Identificador del expediente';

comment on column ZPE_EXPEDI.EXP_CLAVE is
'Clave de acceso al expediente';

comment on column ZPE_EXPEDI.EXP_FECEXP is
'Fecha de alta del expediente';

comment on column ZPE_EXPEDI.EXP_DESC is
'Descripción del expediente';

comment on column ZPE_EXPEDI.EXP_IDIOMA is
'Idioma de tramitacion del expediente';

comment on column ZPE_EXPEDI.EXP_SEYCIU is
'Para expedientes ''autenticados'' contiene el identificador seycon del ciudadano propietario del expediente (o en caso de representacion el representante)';

comment on column ZPE_EXPEDI.EXP_NIFRDO is
'En caso de existir representacion indica el nif del representado';

comment on column ZPE_EXPEDI.EXP_NOMRDO is
'En caso de existir representacion indica el nombre del representado';

comment on column ZPE_EXPEDI.EXP_USER is
'Identificador seycon del usuario que da de alta el expediente';

comment on column ZPE_EXPEDI.EXP_NUMBTE is
'Numero de entrada BTE que origina el expediente';

comment on column ZPE_EXPEDI.EXP_FECCON is
'Fecha consulta expediente';

comment on column ZPE_EXPEDI.EXP_FECINI is
'Fecha primer elemento expediente (calculado)';

comment on column ZPE_EXPEDI.EXP_FECULT is
'Fecha ultimo elemento expediente (calculado)';

comment on column ZPE_EXPEDI.EXP_ESTADO is
'Estado expediente: calculado a partir del ultimo expediente:

SE: Solicitud enviada
AP: Aviso pendiente
AR: Aviso recibido
NP: Notificación pendiente
NR: Notificación recibida';

comment on column ZPE_EXPEDI.EXP_AVISOS is
'Indica si se habilitan los avisos para expediente';

comment on column ZPE_EXPEDI.EXP_AVISMS is
'Indica telefono para avisos SMS (nulo no aviso)';

comment on column ZPE_EXPEDI.EXP_AVIEMA is
'Indica email para avisos por email (nulo no aviso)';

alter table ZPE_EXPEDI
   add constraint ZPE_EXP_PK primary key (EXP_CODIGO);

alter table ZPE_EXPEDI
   add constraint ZPE_EXPIDE_UNI unique (EXP_UNIADM, EXP_IDEXP);

create table ZPE_HISTEX  (
   HIE_CODIGO           NUMBER(20)                      not null,
   HIE_FECEVE           DATE                            not null,
   HIE_FECCON           DATE,
   HIE_TITULO           VARCHAR2(500)                   not null,
   HIE_TEXTO            VARCHAR2(4000)                  not null,
   HIE_TXTSMS           VARCHAR2(255),
   HIE_ENLCON           VARCHAR2(512),
   HIE_USER             VARCHAR2(1536)                  not null
);

comment on table ZPE_HISTEX is
'Histórico de eventos para un expediente';

comment on column ZPE_HISTEX.HIE_CODIGO is
'Identificador único del evento';

comment on column ZPE_HISTEX.HIE_FECEVE is
'Fecha del evento';

comment on column ZPE_HISTEX.HIE_FECCON is
'Fecha de consulta del evento, null si no se ha consultado';

comment on column ZPE_HISTEX.HIE_TITULO is
'Titulo del evento';

comment on column ZPE_HISTEX.HIE_TEXTO is
'Texto del evento';

comment on column ZPE_HISTEX.HIE_TXTSMS is
'Texto SMS para aviso evento';

comment on column ZPE_HISTEX.HIE_ENLCON is
'Enlace de consulta';

comment on column ZPE_HISTEX.HIE_USER is
'Identificador seycon del usuario que da de alta el evento';

alter table ZPE_HISTEX
   add constraint ZPE_HIE_PK primary key (HIE_CODIGO);

create table ZPE_NOTTEL  (
   NOT_CODIGO           NUMBER(20)                      not null,
   NOT_USER             VARCHAR2(1536),
   NOT_NUMREG           VARCHAR2(50)                    not null,
   NOT_FECHA            DATE                            not null,
   NOT_CODASI           NUMBER(20)                      not null,
   NOT_CLAASI           VARCHAR2(10)                    not null,
   NOT_CODJUS           NUMBER(20)                      not null,
   NOT_CLAJUS           VARCHAR2(10)                    not null,
   NOT_CODAVI           NUMBER(20)                      not null,
   NOT_CLAAVI           VARCHAR2(10)                    not null,
   NOT_TITAVI           VARCHAR2(500)                   not null,
   NOT_CODOFR           NUMBER(20)                      not null,
   NOT_CLAOFR           VARCHAR2(10)                    not null,
   NOT_IDIOMA           VARCHAR2(2)                     not null,
   NOT_NIFRTE           VARCHAR2(12)                    not null,
   NOT_NOMRTE           VARCHAR2(500)                   not null,
   NOT_NIFRDO           VARCHAR2(12),
   NOT_NOMRDO           VARCHAR2(500),
   NOT_CODACU           NUMBER(20),
   NOT_CLAACU           VARCHAR2(10),
   NOT_FECACU           DATE,
   NOT_FIRACU           NUMBER(1)                      default 1 not null,
   NOT_SEYGES           VARCHAR2(1536)                  not null
);

comment on table ZPE_NOTTEL is
'Log de notificaciones telemáticas';

comment on column ZPE_NOTTEL.NOT_CODIGO is
'Código interno salida';

comment on column ZPE_NOTTEL.NOT_USER is
'Referencia usuario Seycon que debe recoger la notificación';

comment on column ZPE_NOTTEL.NOT_NUMREG is
'Indica el nº de registro de salida';

comment on column ZPE_NOTTEL.NOT_FECHA is
'Fecha de registro de salida';

comment on column ZPE_NOTTEL.NOT_CODASI is
'Código de la referencia RDS del asiento';

comment on column ZPE_NOTTEL.NOT_CLAASI is
'Clave de la referencia RDS del asiento';

comment on column ZPE_NOTTEL.NOT_CODJUS is
'Código de la referencia RDS del justificante';

comment on column ZPE_NOTTEL.NOT_CLAJUS is
'Clave de la referencia RDS del justificante';

comment on column ZPE_NOTTEL.NOT_CODAVI is
'Código de la referencia RDS del aviso de notificacion';

comment on column ZPE_NOTTEL.NOT_CLAAVI is
'Clave de la referencia  RDS del aviso de notificacion';

comment on column ZPE_NOTTEL.NOT_TITAVI is
'Titulo aviso (cacheamos para optimizar visualizacion listas)';

comment on column ZPE_NOTTEL.NOT_CODOFR is
'Código de la referencia RDS del oficio de remision';

comment on column ZPE_NOTTEL.NOT_CLAOFR is
'Clave de la referencia RDS del oficio de remision';

comment on column ZPE_NOTTEL.NOT_IDIOMA is
'Idioma en el que se ha realizado';

comment on column ZPE_NOTTEL.NOT_NIFRTE is
'Indica NIF del representante (Quien debe recoger la notificación)';

comment on column ZPE_NOTTEL.NOT_NOMRTE is
'Indica nombre del representante (Quien debe recoger la notificación)';

comment on column ZPE_NOTTEL.NOT_NIFRDO is
'En caso de existir representado, indica NIF del representado';

comment on column ZPE_NOTTEL.NOT_NOMRDO is
'En caso de existir representado, indica nombre del representado';

comment on column ZPE_NOTTEL.NOT_CODACU is
'En caso de que se haya firmado el acuse de recibo, indica el código de la referencia RDS del aviso de notificacion';

comment on column ZPE_NOTTEL.NOT_CLAACU is
'En caso de que se haya firmado el acuse de recibo, indica la clave de la referencia RDS del aviso de notificacion';

comment on column ZPE_NOTTEL.NOT_FECACU is
'En caso de que se haya firmado el acuse de recibo, indica la fecha de la firma del acuse';

comment on column ZPE_NOTTEL.NOT_FIRACU is
'Indica si es necesario la firma del acuse de recibo';

comment on column ZPE_NOTTEL.NOT_SEYGES is
'Indica usuario seycon del gestor que da de alta la notificacion';

alter table ZPE_NOTTEL
   add constraint ZPE_NOT_PK primary key (NOT_CODIGO);

create unique index ZPE_NOTNRG_UNI on ZPE_NOTTEL (
   NOT_NUMREG ASC
);

create table ZPE_PERSON  (
   PER_CODIGO           NUMBER(20)                      not null,
   PER_SEYCON           VARCHAR2(1536)                  not null,
   PER_IDENTI           VARCHAR2(12)                    not null,
   PER_TIPPER           VARCHAR2(1)                     not null,
   PER_NOMBRE           VARCHAR2(50)                    not null,
   PER_APELL1           VARCHAR2(50),
   PER_APELL2           VARCHAR2(50),
   PER_FECALT           DATE                            not null,
   PER_FECULM           DATE                            not null,
   PER_DIR              VARCHAR2(200),
   PER_CPOSTAL          VARCHAR2(5),
   PER_PROV             VARCHAR2(2),
   PER_MUNI             VARCHAR2(10),
   PER_TELF             VARCHAR2(20),
   PER_TELM             VARCHAR2(20),
   PER_EMAIL            VARCHAR2(50),
   PER_AVIEXP           NUMBER(1)                      default 0,
   PER_AVITRA           NUMBER(1)                      default 0
);

comment on table ZPE_PERSON is
'Información personal de los usuarios de la zona personal. Dirigida tanto a personas físicas como jurídicas.';

comment on column ZPE_PERSON.PER_CODIGO is
'Identificador único de la persona en la pad';

comment on column ZPE_PERSON.PER_SEYCON is
'Codigo seycon de la persona. Es único.';

comment on column ZPE_PERSON.PER_IDENTI is
'Documento legal de identificación de la persona ( NIF, CIF, NIE ).';

comment on column ZPE_PERSON.PER_TIPPER is
'F - Persona fisica, J - Persona jurídica.';

comment on column ZPE_PERSON.PER_NOMBRE is
'Nombre de la persona. En caso de personas jurídicas, nombre de la empresa o entidad';

comment on column ZPE_PERSON.PER_APELL1 is
'Primer apellido de la persona.';

comment on column ZPE_PERSON.PER_APELL2 is
'Segundo apellido de la persona.';

comment on column ZPE_PERSON.PER_FECALT is
'Fecha de alta de la persona';

comment on column ZPE_PERSON.PER_FECULM is
'Fecha de última modificación de la persona';

comment on column ZPE_PERSON.PER_DIR is
'Dirección';

comment on column ZPE_PERSON.PER_CPOSTAL is
'Código postal';

comment on column ZPE_PERSON.PER_PROV is
'Código provincia';

comment on column ZPE_PERSON.PER_MUNI is
'Código municipio';

comment on column ZPE_PERSON.PER_TELF is
'Teléfono fijo';

comment on column ZPE_PERSON.PER_TELM is
'Teléfono móvil';

comment on column ZPE_PERSON.PER_EMAIL is
'Correo electrónico';

comment on column ZPE_PERSON.PER_AVIEXP is
'Avisos sobre expedienes (notificaciones,eventos,etc)';

comment on column ZPE_PERSON.PER_AVITRA is
'Avisos 3 días antes del plazo borrado trámite persistente';

alter table ZPE_PERSON
   add constraint PK_ZPE_PERSON primary key (PER_CODIGO);

alter table ZPE_PERSON
   add constraint ZPE_PERSEY_UNI unique (PER_SEYCON);

create index ZPE_PERIDE_IDX on ZPE_PERSON (
   PER_IDENTI ASC
);

create table ZPE_PREBCK  (
   PRB_CODIGO           NUMBER(20)                      not null,
   PRB_NUMPRE           VARCHAR2(50)                    not null,
   PRB_IDIOMA           VARCHAR2(2)                     not null,
   PRB_FECHA            DATE                            not null,
   PRB_FECCAD           DATE                            not null,
   PRB_IDEPER           VARCHAR2(50)                    not null,
   PRB_DESC             VARCHAR2(200)                   not null,
   PRB_TIPO             VARCHAR2(1)                     not null,
   PRB_NIVAUT           VARCHAR2(1)                     not null,
   PRB_USER             VARCHAR2(1536),
   PRB_CODASI           NUMBER(20)                      not null,
   PRB_CLAASI           VARCHAR2(10)                    not null,
   PRB_CODJUS           NUMBER(20)                      not null,
   PRB_CLAJUS           VARCHAR2(10)                    not null,
   PRB_NUMREG           VARCHAR2(50),
   PRB_FECREG           DATE,
   PRB_NIFRTE           VARCHAR2(12),
   PRB_NOMRTE           VARCHAR2(500),
   PRB_NIFRDO           VARCHAR2(12),
   PRB_NOMRDO           VARCHAR2(500),
   PRB_TRAMOD           VARCHAR2(10),
   PRB_TRAVER           NUMBER(2),
   PRB_CONAUT           VARCHAR2(1)                    default 'N' not null,
   PRB_AVISOS           VARCHAR2(1),
   PRB_AVISMS           VARCHAR2(10),
   PRB_AVIEMA           VARCHAR2(500),
   PRB_NOTTEL           VARCHAR2(1)
);

comment on table ZPE_PREBCK is
'Log de preregistro (regsitros y envíos) backup';

comment on column ZPE_PREBCK.PRB_CODIGO is
'Código interno entrada';

comment on column ZPE_PREBCK.PRB_NUMPRE is
'Número de preregistro / preenvio generado por Sistema de Tramitación';

comment on column ZPE_PREBCK.PRB_IDIOMA is
'Idioma en el que se ha realizado';

comment on column ZPE_PREBCK.PRB_FECHA is
'Fecha de preregistro';

comment on column ZPE_PREBCK.PRB_FECCAD is
'Fecha tope para realizar la confirmación';

comment on column ZPE_PREBCK.PRB_IDEPER is
'IDENTIFICADOR DE PERSISTENCIA. ';

comment on column ZPE_PREBCK.PRB_DESC is
'Descripción del trámite';

comment on column ZPE_PREBCK.PRB_TIPO is
'Tipo de entrada: P (PreRegistro Entrada) / N (PreEnvío Bandeja)';

comment on column ZPE_PREBCK.PRB_NIVAUT is
'Nivel autenticación U (Usuario) / C (Certificado) / A (Anónimo)';

comment on column ZPE_PREBCK.PRB_USER is
'En caso de estar autenticado referencia usuario Seycon';

comment on column ZPE_PREBCK.PRB_CODASI is
'Código de la referencia RDS del asiento';

comment on column ZPE_PREBCK.PRB_CLAASI is
'Clave de la referencia RDS del asiento';

comment on column ZPE_PREBCK.PRB_CODJUS is
'Código de la referencia RDS del justificante';

comment on column ZPE_PREBCK.PRB_CLAJUS is
'Clave de la referencia RDS del justificante';

comment on column ZPE_PREBCK.PRB_NUMREG is
'Cuando se confirme el preregistro indica el nº de registro / envío confirmado ';

comment on column ZPE_PREBCK.PRB_FECREG is
'Fecha de la confirmación del registro / envío. Un preregistro estará confirmado si tiene alimentada esta fecha.';

comment on column ZPE_PREBCK.PRB_NIFRTE is
'Indica NIF del representante';

comment on column ZPE_PREBCK.PRB_NOMRTE is
'Indica nombre del representante';

comment on column ZPE_PREBCK.PRB_NIFRDO is
'En caso de existir representado, indica NIF del representado';

comment on column ZPE_PREBCK.PRB_NOMRDO is
'En caso de existir representado, indica nombre del representado';

comment on column ZPE_PREBCK.PRB_TRAMOD is
'MODELO TRAMITE (Permite nulos ya que es un campo añadido a posteriori)';

comment on column ZPE_PREBCK.PRB_TRAVER is
'VERSION TRAMITE (Permite nulos ya que es un campo añadido a posteriori)';

comment on column ZPE_PREBCK.PRB_CONAUT is
'Indica si el preregistro se ha confirmado automaticamente';

comment on column ZPE_PREBCK.PRB_AVISOS is
'Indica si se habilitan los avisos para expediente';

comment on column ZPE_PREBCK.PRB_AVISMS is
'Telefono para avisos SMS (nulo no aviso)';

comment on column ZPE_PREBCK.PRB_AVIEMA is
'Email para avisos por email (nulo no aviso)';

comment on column ZPE_PREBCK.PRB_NOTTEL is
'Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.';

alter table ZPE_PREBCK
   add constraint ZPE_PRB_PK primary key (PRB_CODIGO);

create table ZPE_PREREG  (
   PRE_CODIGO           NUMBER(20)                      not null,
   PRE_NUMPRE           VARCHAR2(50)                    not null,
   PRE_IDIOMA           VARCHAR2(2)                     not null,
   PRE_FECHA            DATE                            not null,
   PRE_FECCAD           DATE                            not null,
   PRE_IDEPER           VARCHAR2(50)                    not null,
   PRE_DESC             VARCHAR2(200)                   not null,
   PRE_TIPO             VARCHAR2(1)                     not null,
   PRE_NIVAUT           VARCHAR2(1)                     not null,
   PRE_USER             VARCHAR2(1536),
   PRE_CODASI           NUMBER(20)                      not null,
   PRE_CLAASI           VARCHAR2(10)                    not null,
   PRE_CODJUS           NUMBER(20)                      not null,
   PRE_CLAJUS           VARCHAR2(10)                    not null,
   PRE_NUMREG           VARCHAR2(50),
   PRE_FECREG           DATE,
   PRE_NIFRTE           VARCHAR2(12),
   PRE_NOMRTE           VARCHAR2(500),
   PRE_NIFRDO           VARCHAR2(12),
   PRE_NOMRDO           VARCHAR2(500),
   PRE_TRAMOD           VARCHAR2(10),
   PRE_TRAVER           NUMBER(2),
   PRE_CONAUT           VARCHAR2(1)                    default 'N' not null,
   PRE_AVISOS           VARCHAR2(1),
   PRE_AVISMS           VARCHAR2(10),
   PRE_AVIEMA           VARCHAR2(500),
   PRE_NOTTEL           VARCHAR2(1)
);

comment on table ZPE_PREREG is
'Log de preregistro (regsitros y envíos)';

comment on column ZPE_PREREG.PRE_CODIGO is
'Código interno entrada';

comment on column ZPE_PREREG.PRE_NUMPRE is
'Número de preregistro / preenvio generado por Sistema de Tramitación';

comment on column ZPE_PREREG.PRE_IDIOMA is
'Idioma en el que se ha realizado';

comment on column ZPE_PREREG.PRE_FECHA is
'Fecha de preregistro';

comment on column ZPE_PREREG.PRE_FECCAD is
'Fecha tope para realizar la confirmación';

comment on column ZPE_PREREG.PRE_IDEPER is
'IDENTIFICADOR DE PERSISTENCIA. ';

comment on column ZPE_PREREG.PRE_DESC is
'Descripción del trámite';

comment on column ZPE_PREREG.PRE_TIPO is
'Tipo de entrada: P (PreRegistro Entrada) / N (PreEnvío Bandeja)';

comment on column ZPE_PREREG.PRE_NIVAUT is
'Nivel autenticación U (Usuario) / C (Certificado) / A (Anónimo)';

comment on column ZPE_PREREG.PRE_USER is
'En caso de estar autenticado referencia usuario Seycon';

comment on column ZPE_PREREG.PRE_CODASI is
'Código de la referencia RDS del asiento';

comment on column ZPE_PREREG.PRE_CLAASI is
'Clave de la referencia RDS del asiento';

comment on column ZPE_PREREG.PRE_CODJUS is
'Código de la referencia RDS del justificante';

comment on column ZPE_PREREG.PRE_CLAJUS is
'Clave de la referencia RDS del justificante';

comment on column ZPE_PREREG.PRE_NUMREG is
'Cuando se confirme el preregistro indica el nº de registro / envío confirmado ';

comment on column ZPE_PREREG.PRE_FECREG is
'Fecha de la confirmación del registro / envío. Un preregistro estará confirmado si tiene alimentada esta fecha.';

comment on column ZPE_PREREG.PRE_NIFRTE is
'Indica NIF del representante';

comment on column ZPE_PREREG.PRE_NOMRTE is
'Indica nombre del representante';

comment on column ZPE_PREREG.PRE_NIFRDO is
'En caso de existir representado, indica NIF del representado';

comment on column ZPE_PREREG.PRE_NOMRDO is
'En caso de existir representado, indica nombre del representado';

comment on column ZPE_PREREG.PRE_TRAMOD is
'MODELO TRAMITE (Permite nulos ya que es un campo añadido a posteriori)';

comment on column ZPE_PREREG.PRE_TRAVER is
'VERSION TRAMITE (Permite nulos ya que es un campo añadido a posteriori)';

comment on column ZPE_PREREG.PRE_CONAUT is
'Indica si el preregistro se ha confirmado automaticamente';

comment on column ZPE_PREREG.PRE_AVISOS is
'Indica si se habilitan los avisos para expediente';

comment on column ZPE_PREREG.PRE_AVISMS is
'Telefono para avisos SMS (nulo no aviso)';

comment on column ZPE_PREREG.PRE_AVIEMA is
'Email para avisos por email (nulo no aviso)';

comment on column ZPE_PREREG.PRE_NOTTEL is
'Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.';

alter table ZPE_PREREG
   add constraint ZPE_PRE_PK primary key (PRE_CODIGO);

create unique index ZPE_PREIDP_UNI on ZPE_PREREG (
   PRE_IDEPER ASC
);

create unique index ZPE_PRENPR_UNI on ZPE_PREREG (
   PRE_NUMPRE ASC
);

create table ZPE_RPAGOS  (
   PAG_LOCALI           VARCHAR2(100)                   not null,
   PAG_NOMTRA           VARCHAR2(100),
   PAG_MODELO           VARCHAR2(100),
   PAG_IDTASA           VARCHAR2(100),
   PAG_CONCEP           VARCHAR2(100),
   PAG_DATADV           DATE,
   PAG_IMPORT           VARCHAR2(100),
   PAG_NIFDEC           VARCHAR2(10),
   PAG_NOMDEC           VARCHAR2(200),
   PAG_ESTADO           NUMBER(1),
   PAG_TIPO             VARCHAR2(1),
   PAG_IDENTP           VARCHAR2(100),
   PAG_DATAPG           DATE,
   PAG_RECIBO           VARCHAR2(4000),
   PAG_URLSTR           VARCHAR2(200),
   PAG_URLMNT           VARCHAR2(200),
   PAG_TOKEN            VARCHAR2(100),
   PAG_DATALI           DATE,
   PAG_NOMUSU           VARCHAR2(200),
   PAG_TIPOPG           VARCHAR2(1),
   PAG_IDIOMA           VARCHAR2(2),
   PAG_FINAL            VARCHAR2(1)
);

comment on table ZPE_RPAGOS is
'PLUGIN PAGOS CAIB';

comment on column ZPE_RPAGOS.PAG_LOCALI is
'Localizador sesion de pago';

comment on column ZPE_RPAGOS.PAG_NOMTRA is
'Nombre del trámite';

comment on column ZPE_RPAGOS.PAG_MODELO is
'Modelo tasa';

comment on column ZPE_RPAGOS.PAG_IDTASA is
'Identificador tasa';

comment on column ZPE_RPAGOS.PAG_CONCEP is
'Concepto tasa';

comment on column ZPE_RPAGOS.PAG_DATADV is
'Fecha devengo';

comment on column ZPE_RPAGOS.PAG_IMPORT is
'Importe (cents)';

comment on column ZPE_RPAGOS.PAG_NIFDEC is
'Nif declarante';

comment on column ZPE_RPAGOS.PAG_NOMDEC is
'Nombre declarante';

comment on column ZPE_RPAGOS.PAG_ESTADO is
'Estado pago';

comment on column ZPE_RPAGOS.PAG_TIPO is
'Tipo pago';

comment on column ZPE_RPAGOS.PAG_IDENTP is
'Identificador pago (NRC)';

comment on column ZPE_RPAGOS.PAG_DATAPG is
'Fecha pago';

comment on column ZPE_RPAGOS.PAG_RECIBO is
'Recibo pago portal contribuent';

comment on column ZPE_RPAGOS.PAG_URLSTR is
'Url retorno a SISTRA';

comment on column ZPE_RPAGOS.PAG_URLMNT is
'Url mantenimiento sesión SISTRA';

comment on column ZPE_RPAGOS.PAG_TOKEN is
'Token sesion pago portal contribuent';

comment on column ZPE_RPAGOS.PAG_DATALI is
'Fecha limite inicio sesion pago';

comment on column ZPE_RPAGOS.PAG_NOMUSU is
'Nombre usuario';

comment on column ZPE_RPAGOS.PAG_TIPOPG is
'Tipo pago';

comment on column ZPE_RPAGOS.PAG_IDIOMA is
'Idioma';

comment on column ZPE_RPAGOS.PAG_FINAL is
'Indica si el pago ha finalizado';

alter table ZPE_RPAGOS
   add constraint ZPE_PAG_PK primary key (PAG_LOCALI);

create table ZPE_TPEBCK  (
   TPB_CODIGO           NUMBER(20)                      not null,
   TPB_IDEPER           VARCHAR2(50)                    not null,
   TPB_TRAMOD           VARCHAR2(10)                    not null,
   TPB_TRAVER           NUMBER(2)                       not null,
   TPB_TRADES           VARCHAR2(200)                   not null,
   TPB_NIVAUT           VARCHAR2(1)                     not null,
   TPB_USER             VARCHAR2(1536),
   TPB_FECINI           DATE                            not null,
   TPB_FECMOD           DATE                            not null,
   TPB_PARINI           VARCHAR2(4000),
   TPB_FECCAD           DATE                            not null,
   TPB_IDIOMA           VARCHAR2(2)                     not null,
   TPB_FLUTRA           VARCHAR2(1536)
);

comment on table ZPE_TPEBCK is
'Tramite persistente backup';

comment on column ZPE_TPEBCK.TPB_CODIGO is
'CODIGO TRAMITE PERSISTENTE';

comment on column ZPE_TPEBCK.TPB_IDEPER is
'IDENTIFICADOR DE PERSISTENCIA. GENERADO POR PAD AL INSERTAR.';

comment on column ZPE_TPEBCK.TPB_TRAMOD is
'MODELO TRAMITE';

comment on column ZPE_TPEBCK.TPB_TRAVER is
'VERSION TRAMITE';

comment on column ZPE_TPEBCK.TPB_TRADES is
'DESCRIPCION TRAMITE';

comment on column ZPE_TPEBCK.TPB_NIVAUT is
'NIVEL AUTENTICACION (C/U/A)';

comment on column ZPE_TPEBCK.TPB_USER is
'USUARIO (SOLO PARA NIVELES C Y U)';

comment on column ZPE_TPEBCK.TPB_FECINI is
'FECHA CREACIÓN';

comment on column ZPE_TPEBCK.TPB_FECMOD is
'FECHA ULTIMA MODIFICACION';

comment on column ZPE_TPEBCK.TPB_PARINI is
'PARAMETROS DE INICIO DEL TRAMITE (PARAMETROS QUE APARECEN EN LA REQUEST AL INICIAR EL TRÁMITE)';

comment on column ZPE_TPEBCK.TPB_FECCAD is
'FECHA DE CADUCIDAD CALCULADA A PARTIR DE LOS DIAS DE PERSISTENCIA';

comment on column ZPE_TPEBCK.TPB_IDIOMA is
'IDIOMA DEL TRAMITE';

comment on column ZPE_TPEBCK.TPB_FLUTRA is
'USUARIO QUE ESTA COMPLETANDO EL TRÁMITE (SOLO PARA NIVELES C Y U) ';

alter table ZPE_TPEBCK
   add constraint ZPE_TPB_PK primary key (TPB_CODIGO);

create table ZPE_TRAPER  (
   TPE_CODIGO           NUMBER(20)                      not null,
   TPE_IDEPER           VARCHAR2(50)                    not null,
   TPE_TRAMOD           VARCHAR2(10)                    not null,
   TPE_TRAVER           NUMBER(2)                       not null,
   TPE_TRADES           VARCHAR2(200)                   not null,
   TPE_NIVAUT           VARCHAR2(1)                     not null,
   TPE_USER             VARCHAR2(1536),
   TPE_FECINI           DATE                            not null,
   TPE_FECMOD           DATE                            not null,
   TPE_PARINI           VARCHAR2(4000),
   TPE_FECCAD           DATE                            not null,
   TPE_IDIOMA           VARCHAR2(2)                     not null,
   TPE_FLUTRA           VARCHAR2(1536)
);

comment on table ZPE_TRAPER is
'Tramite persistente';

comment on column ZPE_TRAPER.TPE_CODIGO is
'CODIGO TRAMITE PERSISTENTE';

comment on column ZPE_TRAPER.TPE_IDEPER is
'IDENTIFICADOR DE PERSISTENCIA. GENERADO POR PAD AL INSERTAR.';

comment on column ZPE_TRAPER.TPE_TRAMOD is
'MODELO TRAMITE';

comment on column ZPE_TRAPER.TPE_TRAVER is
'VERSION TRAMITE';

comment on column ZPE_TRAPER.TPE_TRADES is
'DESCRIPCION TRAMITE';

comment on column ZPE_TRAPER.TPE_NIVAUT is
'NIVEL AUTENTICACION (C/U/A)';

comment on column ZPE_TRAPER.TPE_USER is
'USUARIO QUE INICIA EL TRÁMITE (SOLO PARA NIVELES C Y U) ';

comment on column ZPE_TRAPER.TPE_FECINI is
'FECHA CREACIÓN';

comment on column ZPE_TRAPER.TPE_FECMOD is
'FECHA ULTIMA MODIFICACION';

comment on column ZPE_TRAPER.TPE_PARINI is
'PARAMETROS DE INICIO DEL TRAMITE (PARAMETROS QUE APARECEN EN LA REQUEST AL INICIAR EL TRÁMITE)';

comment on column ZPE_TRAPER.TPE_FECCAD is
'FECHA DE CADUCIDAD CALCULADA A PARTIR DE LOS DIAS DE PERSISTENCIA';

comment on column ZPE_TRAPER.TPE_IDIOMA is
'IDIOMA DEL TRAMITE';

comment on column ZPE_TRAPER.TPE_FLUTRA is
'USUARIO QUE ESTA COMPLETANDO EL TRÁMITE (SOLO PARA NIVELES C Y U) ';

alter table ZPE_TRAPER
   add constraint ZPE_TPEIDE_UNI unique (TPE_IDEPER);

alter table ZPE_TRAPER
   add constraint ZPE_TPE_PK primary key (TPE_CODIGO);

alter table ZPE_DOCENT
   add constraint ZPE_DENENT_FK foreign key (DEN_CODENT)
      references ZPE_ENTTEL (ENT_CODIGO);

alter table ZPE_DOCHIE
   add constraint ZPE_DHEHIE_FK foreign key (DHE_CODHIE)
      references ZPE_HISTEX (HIE_CODIGO);

alter table ZPE_DOCNOT
   add constraint ZPE_DNONOT_FK foreign key (DNO_CODNOT)
      references ZPE_NOTTEL (NOT_CODIGO);

alter table ZPE_DOCPER
   add constraint ZPE_DPETPE_FK foreign key (DPE_CODTPE)
      references ZPE_TRAPER (TPE_CODIGO);

alter table ZPE_DOCPRE
   add constraint ZPE_DPRPRE_FK foreign key (DPR_CODPRE)
      references ZPE_PREREG (PRE_CODIGO);

alter table ZPE_DPEBCK
   add constraint ZPE_DPBTPB_FK foreign key (DPB_CODTPB)
      references ZPE_TPEBCK (TPB_CODIGO);

alter table ZPE_DPRBCK
   add constraint ZPE_DRBPRB_FK foreign key (DRB_CODPRE)
      references ZPE_PREBCK (PRB_CODIGO);

alter table ZPE_ELEEX
   add constraint ZPE_EXPELE_FK foreign key (ELE_CODEXP)
      references ZPE_EXPEDI (EXP_CODIGO);


CREATE OR REPLACE VIEW ZPE_ESTEXP
(EST_ID, EST_TIPO, EST_CODIGO, EST_DESC, EST_FECINI, 
 EST_FECFIN, EST_ESTADO, EST_AUTENT, EST_USER, EST_NIFRDO, 
 EST_IDIOMA, EST_CODEXP, EST_UNIADM)
AS 
(
SELECT 'T' ||TO_CHAR(ENT_CODIGO), 'T',ENT_CODIGO,ENT_DESC,ENT_FECHA,ENT_FECHA,'SE', DECODE(ENT_NIVAUT,'A','N','S'), 

DECODE(ENT_NIVAUT,'A',ENT_IDEPER,ENT_USER), ENT_NIFRDO,ENT_IDIOMA,NULL,NULL
FROM ZPE_ENTTEL
WHERE 
ENT_CODIGO NOT IN 
(
 SELECT ELE_CODELE FROM ZPE_ELEEX WHERE ELE_TIPO = 'T'
)

UNION

SELECT  'P' ||TO_CHAR(PRE_CODIGO),'P',PRE_CODIGO,PRE_DESC,PRE_FECHA,PRE_FECHA,'SE',DECODE(PRE_NIVAUT,'A','N','S'),  

DECODE(PRE_NIVAUT,'A',PRE_IDEPER,PRE_USER), PRE_NIFRDO,PRE_IDIOMA,NULL,NULL
FROM ZPE_PREREG
WHERE 
PRE_CODIGO NOT IN 
(
 SELECT ELE_CODELE FROM ZPE_ELEEX WHERE ELE_TIPO = 'P'
)

UNION

SELECT  'E' ||TO_CHAR(EXP_CODIGO),'E',EXP_CODIGO,EXP_DESC,EXP_FECINI,EXP_FECULT,EXP_ESTADO,'S',EXP_SEYCIU, EXP_NIFRDO,EXP_IDIOMA,EXP_IDEXP,EXP_UNIADM
FROM ZPE_EXPEDI
WHERE EXP_SEYCIU IS NOT NULL

UNION

SELECT 'E' ||TO_CHAR(EXP_CODIGO),'E',EXP_CODIGO,EXP_DESC,EXP_FECINI,EXP_FECULT,EXP_ESTADO,'N',ENT_IDEPER, EXP_NIFRDO,EXP_IDIOMA,EXP_IDEXP,EXP_UNIADM
FROM ZPE_EXPEDI,ZPE_ELEEX,ZPE_ENTTEL
WHERE EXP_SEYCIU IS NULL AND
	  EXP_CODIGO = ELE_CODEXP AND
	  ELE_TIPO = 'T' AND ELE_CODELE = ENT_CODIGO
      
UNION
	  
SELECT 'E' ||TO_CHAR(EXP_CODIGO),'E',EXP_CODIGO,EXP_DESC,EXP_FECINI,EXP_FECULT,EXP_ESTADO,'N',PRE_IDEPER, EXP_NIFRDO,EXP_IDIOMA,EXP_IDEXP,EXP_UNIADM
FROM ZPE_EXPEDI,ZPE_ELEEX,ZPE_PREREG
WHERE EXP_SEYCIU IS NULL AND
	  EXP_CODIGO = ELE_CODEXP AND
	  ELE_TIPO = 'P' AND ELE_CODELE = PRE_CODIGO      	  
);