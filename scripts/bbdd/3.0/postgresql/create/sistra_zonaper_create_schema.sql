
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
   DEN_CODIGO           BIGINT                      not null,
   DEN_CODENT           BIGINT                      not null,
   DEN_DOCIDE           VARCHAR(5)                     not null,
   DEN_DOCNUM           BIGINT                       not null,
   DEN_DESC             VARCHAR(500)                   not null,
   DEN_RDSCOD           BIGINT                      not null,
   DEN_RDSCLA           VARCHAR(10)                    not null
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
   DHE_CODIGO           BIGINT                      not null,
   DHE_CODHIE           BIGINT                      not null,
   DHE_RDSCOD           BIGINT                      not null,
   DHE_RDSCLA           VARCHAR(10)                    not null,
   DHE_TITULO           VARCHAR(256)                   not null,
   DHE_ORDEN            BIGINT
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

comment on column ZPE_DOCHIE.DHE_ORDEN is 'Orden';

alter table ZPE_DOCHIE
   add constraint ZPE_DHE_PK primary key (DHE_CODIGO);

create index ZPE_DHEHIE_FK_I on ZPE_DOCHIE (
   DHE_CODHIE ASC
);

create table ZPE_DOCNOT  (
   DNO_CODIGO           BIGINT                      not null,
   DNO_CODNOT           BIGINT                      not null,
   DNO_DOCIDE           VARCHAR(5)                     not null,
   DNO_DOCNUM           BIGINT                       not null,
   DNO_DESC             VARCHAR(500)                   not null,
   DNO_RDSCOD           BIGINT                      not null,
   DNO_RDSCLA           VARCHAR(10)                    not null,
   DNO_ORDEN            BIGINT
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

comment on column ZPE_DOCNOT.DNO_ORDEN is 'Orden';

alter table ZPE_DOCNOT
   add constraint ZPE_DNO_PK primary key (DNO_CODIGO);

create index ZPE_DNONOT_FK_I on ZPE_DOCNOT (
   DNO_CODNOT ASC
);

create table ZPE_DOCPER  (
   DPE_CODIGO           BIGINT                      not null,
   DPE_CODTPE           BIGINT                      not null,
   DPE_DOCIDE           VARCHAR(5)                     not null,
   DPE_DOCNUM           BIGINT                       not null,
   DPE_ESTADO           VARCHAR(1)                     not null,
   DPE_RDSCOD           BIGINT,
   DPE_RDSCLA           VARCHAR(10),
   DPE_NOMFIC           VARCHAR(255),
   DPE_GENDES           VARCHAR(255),
   DPE_DLGEST VARCHAR(2),
   DPE_DLGFIR VARCHAR(4000),
   DPE_DLGFIP VARCHAR(4000)
);

comment on column ZPE_DOCPER.DPE_DLGEST is
'ESTADO DE DELEGACION: PASO A FIRMA DOCUMENTO';

comment on column ZPE_DOCPER.DPE_DLGFIR is
'NIFS QUE TIENEN QUE FIRMAR EL DOCUMENTO SEPARADOS POR #';

comment on column ZPE_DOCPER.DPE_DLGFIP is
'NIFS QUE QUEDAN POR FIRMAR EL DOCUMENTO SEPARADOS POR #';

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
   DPR_CODIGO           BIGINT                      not null,
   DPR_CODPRE           BIGINT                      not null,
   DPR_PRESE            VARCHAR(1)                     not null,
   DPR_DESC             VARCHAR(500)                   not null,
   DPR_DOCIDE           VARCHAR(5),
   DPR_DOCNUM           BIGINT,
   DPR_RDSCOD           BIGINT,
   DPR_RDSCLA           VARCHAR(10),
   DPR_TIPDOC           VARCHAR(1),
   DPR_COMPUL           VARCHAR(1),
   DPR_FOTPIA           VARCHAR(1),
   DPR_FIRMA            VARCHAR(1),
   DPR_OK               VARCHAR(1)
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
   DPB_CODIGO           BIGINT                      not null,
   DPB_CODTPB           BIGINT                      not null,
   DPB_DOCIDE           VARCHAR(5)                     not null,
   DPB_DOCNUM           BIGINT                       not null,
   DPB_ESTADO           VARCHAR(1)                     not null,
   DPB_RDSCOD           BIGINT,
   DPB_RDSCLA           VARCHAR(10),
   DPB_NOMFIC           VARCHAR(255)
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
   DRB_CODIGO           BIGINT                      not null,
   DRB_CODPRE           BIGINT                      not null,
   DRB_PRESE            VARCHAR(1)                     not null,
   DRB_DESC             VARCHAR(500)                   not null,
   DRB_DOCIDE           VARCHAR(5),
   DRB_DOCNUM           BIGINT,
   DRB_RDSCOD           BIGINT,
   DRB_RDSCLA           VARCHAR(10),
   DRB_TIPDOC           VARCHAR(1),
   DRB_COMPUL           VARCHAR(1),
   DRB_FOTPIA           VARCHAR(1),
   DRB_FIRMA            VARCHAR(1),
   DRB_OK               VARCHAR(1)
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
   ELE_CODIGO           BIGINT                      not null,
   ELE_CODEXP           BIGINT                      not null,
   ELE_FECHA            DATE                            not null,
   ELE_TIPO             VARCHAR(1)                     not null,
   ELE_CODELE           BIGINT                      not null,
   ELE_CODAVI           VARCHAR(50)
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

comment on column ZPE_ELEEX.ELE_CODAVI is
'Codigo aviso movilidad asociado al elemento';

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
   ENT_CODIGO           BIGINT                      not null,
   ENT_TIPO             VARCHAR(1)                     not null,
   ENT_IDEPER           VARCHAR(50)                    not null,
   ENT_NIVAUT           VARCHAR(1)                     not null,
   ENT_DESC             VARCHAR(200)                   not null,
   ENT_USER             VARCHAR(1536),
   ENT_NUMREG           VARCHAR(50)                    not null,
   ENT_FECHA            DATE                            not null,
   ENT_CODASI           BIGINT                      not null,
   ENT_CLAASI           VARCHAR(10)                    not null,
   ENT_CODJUS           BIGINT                      not null,
   ENT_CLAJUS           VARCHAR(10)                    not null,
   ENT_IDIOMA           VARCHAR(2)                     not null,
   ENT_NIFRTE           VARCHAR(12),
   ENT_NOMRTE           VARCHAR(500),
   ENT_NIFRDO           VARCHAR(12),
   ENT_NOMRDO           VARCHAR(500),
   ENT_TRAMOD           VARCHAR(20),
   ENT_TRAVER           BIGINT,
   ENT_AVISOS           VARCHAR(1),
   ENT_AVISMS           VARCHAR(10),
   ENT_AVIEMA           VARCHAR(500),
   ENT_NOTTEL           VARCHAR(1),
   ENT_NIFDLG           VARCHAR(12),
   ENT_NOMDLG           VARCHAR(500),
   ent_sbexid           VARCHAR(50),
   ent_sbexua           BIGINT,
   ENT_IDEPRO           VARCHAR(100)
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

comment on column ZPE_ENTTEL.ENT_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column ZPE_ENTTEL.ENT_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

COMMENT ON COLUMN zpe_enttel.ent_sbexid IS
'En caso de que sea un tramite de subsanacion indica el identificador del expediente';

COMMENT ON COLUMN zpe_enttel.ent_sbexua IS
'En caso de que sea un tramite de subsanacion indica la unidad administrativa que da de alta el expediente';

comment on column ZPE_ENTTEL.ENT_IDEPRO is 'IDENTIFICADOR PROCEDIMIENTO';

alter table ZPE_ENTTEL
   add constraint ZPE_ENT_PK primary key (ENT_CODIGO);

create unique index ZPE_ENTIDP_UNI on ZPE_ENTTEL (
   ENT_IDEPER ASC
);

create table ZPE_EXPEDI  (
   EXP_CODIGO           BIGINT                      not null,
   EXP_CLAVE            VARCHAR(50),
   EXP_IDIOMA           VARCHAR(2)                     not null,
   EXP_FECEXP           DATE                            not null,
   EXP_FECCON           DATE,
   EXP_IDEXP            VARCHAR(50)                    not null,
   EXP_DESC             VARCHAR(512)                   not null,
   EXP_SEYCIU           VARCHAR(1536),
   EXP_USER             VARCHAR(1536)                  not null,
   EXP_NIFRDO           VARCHAR(10),
   EXP_NOMRDO           VARCHAR(500),
   EXP_UNIADM           BIGINT                      not null,
   EXP_NUMBTE           VARCHAR(50),
   EXP_FECINI           DATE,
   EXP_FECULT           DATE,
   EXP_ESTADO           VARCHAR(2),
   EXP_AVISOS           VARCHAR(1)  NOT NULL,
   EXP_AVISMS           VARCHAR(10),
   EXP_AVIEMA           VARCHAR(500),
   EXP_NIFRTE           VARCHAR(12),
   EXP_IDEPRO           VARCHAR(100)                   not null
);




comment on column ZPE_EXPEDI.EXP_NIFRTE is
'Para expedientes ''autenticados'' contiene el nif del representante';


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
   HIE_CODIGO           BIGINT                      not null,
   HIE_FECEVE           DATE                            not null,
   HIE_FECCON           DATE,
   HIE_TITULO           VARCHAR(500)                   not null,
   HIE_TEXTO            VARCHAR(4000)                  not null,
   HIE_TXTSMS           VARCHAR(255),
   HIE_ENLCON           VARCHAR(512),
   HIE_USER             VARCHAR(1536)                  not null
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
   NOT_CODIGO           BIGINT                      not null,
   NOT_USER             VARCHAR(1536),
   NOT_NUMREG           VARCHAR(50)                    not null,
   NOT_FECHA            DATE                            not null,
   NOT_CODASI           BIGINT                      not null,
   NOT_CLAASI           VARCHAR(10)                    not null,
   NOT_CODJUS           BIGINT                      not null,
   NOT_CLAJUS           VARCHAR(10)                    not null,
   NOT_CODAVI           BIGINT                      not null,
   NOT_CLAAVI           VARCHAR(10)                    not null,
   NOT_TITAVI           VARCHAR(500)                   not null,
   NOT_CODOFR           BIGINT                      not null,
   NOT_CLAOFR           VARCHAR(10)                    not null,
   NOT_IDIOMA           VARCHAR(2)                     not null,
   NOT_NIFRTE           VARCHAR(12)                    not null,
   NOT_NOMRTE           VARCHAR(500)                   not null,
   NOT_NIFRDO           VARCHAR(12),
   NOT_NOMRDO           VARCHAR(500),
   NOT_CODACU           BIGINT,
   NOT_CLAACU           VARCHAR(10),
   NOT_FECACU           DATE,
   NOT_FIRACU           BIGINT                      default 1 not null,
   NOT_SEYGES           VARCHAR(1536)                  not null,
   NOT_SUBDES           VARCHAR(500),
   NOT_SUBIDE           VARCHAR(20),
   NOT_SUBVER           BIGINT,
   NOT_SUBPAR           VARCHAR(4000),
   NOT_FECPLZ           DATE,
   NOT_RECHAZ          BIGINT
);


comment on column ZPE_NOTTEL.NOT_FECPLZ is
'Indica fecha de plazo de fin de apertura de la notificación (en caso de que se controle fecha entrega)';

comment on column ZPE_NOTTEL.NOT_RECHAZ is
'Indica que la notificacion esta rechazada (en caso de que se controle fecha entrega)';

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

comment on column ZPE_NOTTEL.NOT_SUBDES is
'En caso de que haya trámite de subsanación indica la descripción del trámite';

comment on column ZPE_NOTTEL.NOT_SUBIDE is
'En caso de que haya trámite de subsanación indica el id del trámite';

comment on column ZPE_NOTTEL.NOT_SUBVER is
'En caso de que haya trámite de subsanación indica la versión del trámite';

comment on column ZPE_NOTTEL.NOT_SUBPAR is
'En caso de que haya trámite de subsanación indica los parámetros de inicio  del trámite';

alter table ZPE_NOTTEL
   add constraint ZPE_NOT_PK primary key (NOT_CODIGO);

create unique index ZPE_NOTNRG_UNI on ZPE_NOTTEL (
   NOT_NUMREG ASC
);

create table ZPE_PERSON  (
   PER_CODIGO           BIGINT                      not null,
   PER_SEYCON           VARCHAR(1536)                  not null,
   PER_IDENTI           VARCHAR(12)                    not null,
   PER_TIPPER           VARCHAR(1)                     not null,
   PER_NOMBRE           VARCHAR(50)                    not null,
   PER_APELL1           VARCHAR(50),
   PER_APELL2           VARCHAR(50),
   PER_FECALT           DATE                            not null,
   PER_FECULM           DATE                            not null,
   PER_DIR              VARCHAR(200),
   PER_CPOSTAL          VARCHAR(5),
   PER_PROV             VARCHAR(2),
   PER_MUNI             VARCHAR(10),
   PER_TELF             VARCHAR(20),
   PER_TELM             VARCHAR(20),
   PER_EMAIL            VARCHAR(50),
   PER_AVIEXP           BIGINT                      default 0,
   PER_AVITRA           BIGINT                      default 0,
   PER_DELEGA           VARCHAR(1)                    default 'N' not null,
   PER_SEYMOD VARCHAR(4000)
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

comment on column ZPE_PERSON.PER_DELEGA is
'Indica si esta habilitada la delegacion';

comment on column ZPE_PERSON.PER_SEYMOD is
'Ante cambios de usuario se almacenan los usuarios anteriores a modo de log';


alter table ZPE_PERSON
   add constraint PK_ZPE_PERSON primary key (PER_CODIGO);

alter table ZPE_PERSON
   add constraint ZPE_PERSEY_UNI unique (PER_SEYCON);

create index ZPE_PERIDE_IDX on ZPE_PERSON (
   PER_IDENTI ASC
);

create table ZPE_PREBCK  (
   PRB_CODIGO           BIGINT                      not null,
   PRB_NUMPRE           VARCHAR(50)                    not null,
   PRB_IDIOMA           VARCHAR(2)                     not null,
   PRB_FECHA            DATE                            not null,
   PRB_FECCAD           DATE                            not null,
   PRB_IDEPER           VARCHAR(50)                    not null,
   PRB_DESC             VARCHAR(200)                   not null,
   PRB_TIPO             VARCHAR(1)                     not null,
   PRB_NIVAUT           VARCHAR(1)                     not null,
   PRB_USER             VARCHAR(1536),
   PRB_CODASI           BIGINT                      not null,
   PRB_CLAASI           VARCHAR(10)                    not null,
   PRB_CODJUS           BIGINT                      not null,
   PRB_CLAJUS           VARCHAR(10)                    not null,
   PRB_NUMREG           VARCHAR(50),
   PRB_FECREG           DATE,
   PRB_NIFRTE           VARCHAR(12),
   PRB_NOMRTE           VARCHAR(500),
   PRB_NIFRDO           VARCHAR(12),
   PRB_NOMRDO           VARCHAR(500),
   PRB_TRAMOD           VARCHAR(20),
   PRB_TRAVER           BIGINT,
   PRB_CONAUT           VARCHAR(1)                    default 'N' not null,
   PRB_AVISOS           VARCHAR(1),
   PRB_AVISMS           VARCHAR(10),
   PRB_AVIEMA           VARCHAR(500),
   PRB_NOTTEL           VARCHAR(1),
   PRB_NIFDLG           VARCHAR(12),
   PRB_NOMDLG           VARCHAR(500),
   prb_sbexid           VARCHAR(50),
   prb_sbexua           BIGINT,
   PRB_IDEPRO           VARCHAR(100)
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

comment on column ZPE_PREBCK.PRB_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column ZPE_PREBCK.PRB_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

COMMENT ON COLUMN zpe_prebck.prb_sbexid IS
'En caso de que sea un tramite de subsanacion indica el identificador del expediente';

COMMENT ON COLUMN zpe_prebck.prb_sbexua IS
'En caso de que sea un tramite de subsanacion indica la unidad administrativa que da de alta el expediente';

comment on column ZPE_PREBCK.PRB_IDEPRO is 'IDENTIFICADOR PROCEDIMIENTO';

alter table ZPE_PREBCK
   add constraint ZPE_PRB_PK primary key (PRB_CODIGO);

create table ZPE_PREREG  (
   PRE_CODIGO           BIGINT                      not null,
   PRE_NUMPRE           VARCHAR(50)                    not null,
   PRE_IDIOMA           VARCHAR(2)                     not null,
   PRE_FECHA            DATE                            not null,
   PRE_FECCAD           DATE                            not null,
   PRE_IDEPER           VARCHAR(50)                    not null,
   PRE_DESC             VARCHAR(200)                   not null,
   PRE_TIPO             VARCHAR(1)                     not null,
   PRE_NIVAUT           VARCHAR(1)                     not null,
   PRE_USER             VARCHAR(1536),
   PRE_CODASI           BIGINT                      not null,
   PRE_CLAASI           VARCHAR(10)                    not null,
   PRE_CODJUS           BIGINT                      not null,
   PRE_CLAJUS           VARCHAR(10)                    not null,
   PRE_NUMREG           VARCHAR(50),
   PRE_FECREG           DATE,
   PRE_NIFRTE           VARCHAR(12),
   PRE_NOMRTE           VARCHAR(500),
   PRE_NIFRDO           VARCHAR(12),
   PRE_NOMRDO           VARCHAR(500),
   PRE_TRAMOD           VARCHAR(20),
   PRE_TRAVER           BIGINT,
   PRE_CONAUT           VARCHAR(1)                    default 'N' not null,
   PRE_AVISOS           VARCHAR(1),
   PRE_AVISMS           VARCHAR(10),
   PRE_AVIEMA           VARCHAR(500),
   PRE_NOTTEL           VARCHAR(1),
   PRE_NIFDLG           VARCHAR(12),
   PRE_NOMDLG           VARCHAR(500),
   pre_sbexid           VARCHAR(50),
   pre_sbexua           BIGINT,
   PRE_IDEPRO           VARCHAR(100)
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

comment on column ZPE_PREREG.PRE_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column ZPE_PREREG.PRE_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

COMMENT ON COLUMN zpe_prereg.pre_sbexid IS
'En caso de que sea un tramite de subsanacion indica el identificador del expediente';

COMMENT ON COLUMN zpe_prereg.pre_sbexua IS
'En caso de que sea un tramite de subsanacion indica la unidad administrativa que da de alta el expediente';

comment on column ZPE_PREREG.PRE_IDEPRO is 'IDENTIFICADOR PROCEDIMIENTO';

alter table ZPE_PREREG
   add constraint ZPE_PRE_PK primary key (PRE_CODIGO);

create unique index ZPE_PREIDP_UNI on ZPE_PREREG (
   PRE_IDEPER ASC
);

create unique index ZPE_PRENPR_UNI on ZPE_PREREG (
   PRE_NUMPRE ASC
);

create table ZPE_RPAGOS  (
   PAG_LOCALI           VARCHAR(100)                   not null,
   PAG_NOMTRA           VARCHAR(200),
   PAG_MODELO           VARCHAR(100),
   PAG_IDTASA           VARCHAR(100),
   PAG_CONCEP           VARCHAR(200),
   PAG_DATADV           DATE,
   PAG_IMPORT           VARCHAR(100),
   PAG_NIFDEC           VARCHAR(12),
   PAG_NOMDEC           VARCHAR(200),
   PAG_ESTADO           BIGINT,
   PAG_TIPO             VARCHAR(1),
   PAG_IDENTP           VARCHAR(200),
   PAG_DATAPG           DATE,
   PAG_RECIBO           TEXT,
   PAG_URLSTR           VARCHAR(400),
   PAG_URLMNT           VARCHAR(400),
   PAG_TOKEN            VARCHAR(100),
   PAG_DATALI           DATE,
   PAG_NOMUSU           VARCHAR(200),
   PAG_TIPOPG           VARCHAR(1),
   PAG_IDIOMA           VARCHAR(2),
   PAG_FINAL            VARCHAR(1),
   PAG_IDETRA           VARCHAR(50),
   PAG_MODTRA           VARCHAR(20),
   PAG_VERTRA           BIGINT
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

comment on column ZPE_RPAGOS.PAG_IDETRA is
'Identificador del trámite (Id persistencia)';

comment on column ZPE_RPAGOS.PAG_MODTRA is
'Modelo del trámite';

comment on column ZPE_RPAGOS.PAG_VERTRA is
'Versión del trámite';


alter table ZPE_RPAGOS
   add constraint ZPE_PAG_PK primary key (PAG_LOCALI);

create table ZPE_TPEBCK  (
   TPB_CODIGO           BIGINT                      not null,
   TPB_IDEPER           VARCHAR(50)                    not null,
   TPB_TRAMOD           VARCHAR(20)                    not null,
   TPB_TRAVER           BIGINT                       not null,
   TPB_TRADES           VARCHAR(200)                   not null,
   TPB_NIVAUT           VARCHAR(1)                     not null,
   TPB_USER             VARCHAR(1536),
   TPB_FECINI           DATE                            not null,
   TPB_FECMOD           DATE                            not null,
   TPB_PARINI           VARCHAR(4000),
   TPB_FECCAD           DATE                            not null,
   TPB_IDIOMA           VARCHAR(2)                     not null,
   TPB_FLUTRA           VARCHAR(1536)
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
   TPE_CODIGO           BIGINT                      not null,
   TPE_IDEPER           VARCHAR(50)                    not null,
   TPE_TRAMOD           VARCHAR(20)                    not null,
   TPE_TRAVER           BIGINT                       not null,
   TPE_TRADES           VARCHAR(200)                   not null,
   TPE_NIVAUT           VARCHAR(1)                     not null,
   TPE_USER             VARCHAR(1536),
   TPE_FECINI           DATE                            not null,
   TPE_FECMOD           DATE                            not null,
   TPE_PARINI           VARCHAR(4000),
   TPE_FECCAD           DATE                            not null,
   TPE_IDIOMA           VARCHAR(2)                     not null,
   TPE_FLUTRA           VARCHAR(1536),
   TPE_DELEGA           VARCHAR(1536),
   TPE_DLGEST           VARCHAR(2)
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

comment on column ZPE_TRAPER.TPE_DELEGA is
'USUARIO QUE  ESTA EFECTUANDO DE EL TRAMITE DE FORMA DELEGADA  (SOLO PARA NIVEL C Y U)';

comment on column ZPE_TRAPER.TPE_DLGEST is
'ESTADO DE DELEGACION: PASO A FIRMA DOCUMENTO, PASO A PRESENTACION TRAMITE';


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


create table ZPE_REGEXT  (
   REG_CODIGO           BIGINT                      not null,
   REG_NIVAUT           VARCHAR(1),
   REG_DESC             VARCHAR(200)                   not null,
   REG_USER             VARCHAR(1536),
   REG_NUMREG           VARCHAR(50)                    not null,
   REG_FECHA            DATE                            not null,
   REG_CODASI           BIGINT                      not null,
   REG_CLAASI           VARCHAR(10)                    not null,
   REG_CODJUS           BIGINT                      not null,
   REG_CLAJUS           VARCHAR(10)                    not null,
   REG_IDIOMA           VARCHAR(2)                     not null,
   REG_NIFRTE           VARCHAR(12),
   REG_NOMRTE           VARCHAR(500),
   REG_NIFRDO           VARCHAR(12),
   REG_NOMRDO           VARCHAR(500)
);
comment on table ZPE_REGEXT is
'Log de entradas telemáticas';
comment on column ZPE_REGEXT.REG_CODIGO is
'Código interno entrada';
comment on column ZPE_REGEXT.REG_NIVAUT is
'Nivel autenticación usuario para el que se realiza el registro(OPCIONAL): U (Usuario) / C (Certificado) / A (Anónimo)';
comment on column ZPE_REGEXT.REG_DESC is
'Descripción del trámite';
comment on column ZPE_REGEXT.REG_USER is
'En caso de estar autenticado referencia usuario Seycon que realiza la entrada';
comment on column ZPE_REGEXT.REG_NUMREG is
'Indica el nº de registro/nº de envío';
comment on column ZPE_REGEXT.REG_FECHA is
'Fecha de registro / envío';
comment on column ZPE_REGEXT.REG_CODASI is
'Código de la referencia RDS del asiento';
comment on column ZPE_REGEXT.REG_CLAASI is
'Clave de la referencia RDS del asiento';
comment on column ZPE_REGEXT.REG_CODJUS is
'Código de la referencia RDS del justificante';
comment on column ZPE_REGEXT.REG_CLAJUS is
'Clave de la referencia RDS del justificante';
comment on column ZPE_REGEXT.REG_IDIOMA is
'Idioma en el que se ha realizado';
comment on column ZPE_REGEXT.REG_NIFRTE is
'Indica NIF del representante';
comment on column ZPE_REGEXT.REG_NOMRTE is
'Indica nombre del representante';
comment on column ZPE_REGEXT.REG_NIFRDO is
'En caso de existir representado, indica NIF del representado';
comment on column ZPE_REGEXT.REG_NOMRDO is
'En caso de existir representado, indica nombre del representado';
alter table ZPE_REGEXT
   add constraint ZPE_REG_PK primary key (REG_CODIGO);


create table ZPE_DOCREG  (
   DRE_CODIGO           BIGINT                      not null,
   DRE_CODREG           BIGINT                      not null,
   DRE_DOCIDE           VARCHAR(5)                     not null,
   DRE_DOCNUM           BIGINT                       not null,
   DRE_DESC             VARCHAR(500)                   not null,
   DRE_RDSCOD           BIGINT                      not null,
   DRE_RDSCLA           VARCHAR(10)                    not null
);
comment on column ZPE_DOCREG.DRE_CODIGO is
'Código interno';
comment on column ZPE_DOCREG.DRE_CODREG is
'Código entrada registro tramites no sistra';
comment on column ZPE_DOCREG.DRE_DOCIDE is
'Identificador documento';
comment on column ZPE_DOCREG.DRE_DOCNUM is
'Número instancia';
comment on column ZPE_DOCREG.DRE_DESC is
'Descripción documento';
comment on column ZPE_DOCREG.DRE_RDSCOD is
'Código RDS del documento';
comment on column ZPE_DOCREG.DRE_RDSCLA is
'Clave RDS del documento';
alter table ZPE_DOCREG
   add constraint ZPE_DRE_PK primary key (DRE_CODIGO);
create index ZPE_DRERET_FK_I on ZPE_DOCREG (
   DRE_CODREG ASC
);
alter table ZPE_DOCREG
   add constraint ZPE_REREG_FK foreign key (DRE_CODREG)
      references ZPE_REGEXT (REG_CODIGO);


create table ZPE_REGLOG  (
   RLG_TIPREG           VARCHAR(1)                     not null,
   RLG_NUMREG           VARCHAR(50)                    not null,
   RLG_FECREG           DATE                            not null,
   RLG_ERROR            VARCHAR(2000),
   RLG_ANULAD           VARCHAR(1)
);

comment on table ZPE_REGLOG is
'Log de entradas telemáticas';
comment on column ZPE_REGLOG.RLG_TIPREG is
'Tipo de registro  Entrada (E) / Salida (S)';
comment on column ZPE_REGLOG.RLG_NUMREG is
'Numero de registro';
comment on column ZPE_REGLOG.RLG_FECREG is
'Fecha de registro';
comment on column ZPE_REGLOG.RLG_ERROR is
'descripción del error al dar de alta en registro';
alter table ZPE_REGLOG
   add constraint ZPE_RLG_PK primary key (RLG_TIPREG, RLG_NUMREG);

create sequence ZPE_SEQREG;
create sequence ZPE_SEQDRE;




create sequence ZPE_SEQDEL;


create table ZPE_DELEGA  (
   DEL_CODIGO           BIGINT                      not null,
   DEL_DLGTE            VARCHAR(12)                    not null,
   DEL_DLGDO            VARCHAR(12)                    not null,
   DEL_PERMIS           VARCHAR(10)                    not null,
   DEL_INIDLG           DATE                            not null,
   DEL_FINDLG           DATE                            not null,
   DEL_CODRDS           BIGINT                      not null,
   DEL_CLARDS           VARCHAR(10)                    not null,
   DEL_ANULAD           VARCHAR(1)                    default 'N' not null
);

comment on table ZPE_DELEGA is
'Delegaciones';

comment on column ZPE_DELEGA.DEL_CODIGO is
'CODIGO';

comment on column ZPE_DELEGA.DEL_DLGTE is
'NIF/CIF DEL QUE DELEGA';

comment on column ZPE_DELEGA.DEL_DLGDO is
'NIF USUARIO AL QUE SE DELEGA';

comment on column ZPE_DELEGA.DEL_PERMIS is
'PERMISOS DE DELEGACION:
 - T: TRAMITAR (SIN ENVIAR)
 - E: ENVIAR TRAMITES
 - N: ABRIR NOTIFICACIONES';

comment on column ZPE_DELEGA.DEL_INIDLG is
'FECHA INICIO DELEGACION';

comment on column ZPE_DELEGA.DEL_FINDLG is
'FECHA FIN DELEGACION';

comment on column ZPE_DELEGA.DEL_CODRDS is
'CODIGO RDS DOCUMENTO DELEGACION';

comment on column ZPE_DELEGA.DEL_CLARDS is
'CLAVE RDS DOCUMENTO DELEGACION';

comment on column ZPE_DELEGA.DEL_ANULAD is
'INDICA SI ESTA ANULADA LA DELEGACION (S/N)';

alter table ZPE_DELEGA
   add constraint ZPE_DEL_PK primary key (DEL_CODIGO);



   create table ZPE_SUBPAR  (
   SBP_CODIGO           VARCHAR(50)                    not null,
   SBP_UNIADM           BIGINT                      not null,
   SBP_IDEXP            VARCHAR(50)                    not null,
   SBP_PARAMS           VARCHAR(4000),
   SBP_FECHA            DATE                            not null
);

comment on table ZPE_SUBPAR is
'PARAMETROS DE INICIO DE TRAMITE DE SUBSANACION';

comment on column ZPE_SUBPAR.SBP_CODIGO is
'Codigo de acceso parámetros inicio trámite subsanación';

comment on column ZPE_SUBPAR.SBP_UNIADM is
'Unidad administrativa que da de alta el expediente';

comment on column ZPE_SUBPAR.SBP_IDEXP is
'Identificador del expediente';

comment on column ZPE_SUBPAR.SBP_PARAMS is
'Parametros de inicio';

comment on column ZPE_SUBPAR.SBP_FECHA is
'Fecha creacion';

alter table ZPE_SUBPAR
   add constraint ZPE_SBP_PK primary key (SBP_CODIGO);


create table ZPE_RGPEXT  (
   RGP_RDSCOD           BIGINT                      not null,
   RGP_RDSCLA           VARCHAR(10)                    not null,
   RGP_RDSANE           VARCHAR(4000),
   RGP_IDEPER           VARCHAR(50)                    not null,
   RGP_FECINI           date                            not null,
   RGP_FECFIN           DATE                            not null
);

comment on table ZPE_RGPEXT is
'Log de preparación de asientos para firmar y registrar con posterioridad (para registros de entrada externos, es decir no realizados por Sistra)';

comment on column ZPE_RGPEXT.RGP_RDSCOD is
'Código RDS del asiento';

comment on column ZPE_RGPEXT.RGP_RDSCLA is
'Clave RDS del asiento';

comment on column ZPE_RGPEXT.RGP_RDSANE is
'Lista de referencias de los anexos serializada en un string';

comment on column ZPE_RGPEXT.RGP_IDEPER is
'Identicador ';

comment on column ZPE_RGPEXT.RGP_FECINI is
'Fecha en la que se ha preparado el asiento';

comment on column ZPE_RGPEXT.RGP_FECFIN is
'Fecha en la que se si no es registrado el asiento se eliminará';

alter table ZPE_RGPEXT
   add constraint ZPE_RGP_PK primary key (RGP_RDSCOD);




CREATE SEQUENCE ZPE_SEQIND;

create table ZPE_INDELE  (
   IND_CODIGO           BIGINT                      not null,
   IND_NIF              VARCHAR(10)                    not null,
   IND_TIPEL            VARCHAR(1)                     not null,
   IND_CODEL            BIGINT                      not null,
   IND_DESCR            VARCHAR(1000)                  not null,
   IND_VALOR            VARCHAR(4000)
);

comment on table ZPE_INDELE is
'Indices de búsqueda asociadas a elementos de un expediente (solo para autenticados)';

comment on column ZPE_INDELE.IND_CODIGO is
'Codigo interno';

comment on column ZPE_INDELE.IND_NIF is
'Nif asociado';

comment on column ZPE_INDELE.IND_TIPEL is
'Tipo elemento: Expediente (E) / Tramite telematico (T) / Tramite preregistro (P) / Notificacion (N) / Aviso (A)';

comment on column ZPE_INDELE.IND_CODEL is
'Codigo elemento';

comment on column ZPE_INDELE.IND_DESCR is
'Descripcion campo';

comment on column ZPE_INDELE.IND_VALOR is
'Valor campo';

alter table ZPE_INDELE
   add constraint ZPE_INDELE_PK primary key (IND_CODIGO);

create index ZPE_INDELE_IDX on ZPE_INDELE (
   IND_NIF ASC
);

-- V2.1.0


alter table ZPE_EXPEDI add EXP_TIPO VARCHAR(1);

comment on column ZPE_EXPEDI.EXP_TIPO is
'Tipo expediente: E (Expediente real) / V (Expediente virtual de una entrada para la que aún no se ha generado expediente)';

alter table ZPE_EXPEDI alter column EXP_TIPO set not null;

alter table ZPE_EXPEDI alter column EXP_UNIADM drop not null;

alter table ZPE_EXPEDI alter column EXP_USER drop not null;


alter table ZPE_NOTTEL add NOT_IDEPER VARCHAR(50);

alter table ZPE_NOTTEL alter column NOT_IDEPER set  not null;


alter table ZPE_HISTEX add HIE_IDEPER VARCHAR(50);

alter table ZPE_HISTEX alter column HIE_IDEPER set not null;

alter table ZPE_ELEEX add ELE_IDPELE VARCHAR(50);

alter table ZPE_ELEEX alter column ELE_IDPELE set not null;

create unique index ZPE_ELEIDP_UNI on ZPE_ELEEX (
   ELE_IDPELE ASC
);

/* ACCESO ANONIMO A EXPEDIENTE A TRAVES DE TRAMITES ANONIMOS */
alter table ZPE_ELEEX add ELE_ACCEXP BOOLEAN default false not null;

comment on column ZPE_ELEEX.ELE_ACCEXP is
'Indica si elemento proporciona acceso a expediente de forma anónima a traves de su id persistencia';

/* ACCESO ANONIMO NOTIF POR CLAVE */
ALTER table ZPE_NOTTEL ADD NOT_ACCCLA  BOOLEAN default false not null;
comment on column ZPE_NOTTEL.NOT_ACCCLA is
'Indica si se permite que la notificacion sea accesible mediante clave (id persistencia notificacion)';

/* ACCESO ANONIMO EVENTO POR CLAVE */
ALTER table ZPE_HISTEX ADD   HIE_ACCCLA boolean default false not null;
comment on column ZPE_HISTEX.HIE_ACCCLA is
'Indica si se permite que el evento sea accesible mediante clave (id persistencia evento)';

/* TIPO FIRMA NOTIFICACION */
alter table ZPE_NOTTEL  add NOT_TIPFIR  VARCHAR(3);

comment on column ZPE_NOTTEL.NOT_TIPFIR is
'En caso de que se haya firmado el acuse de recibo indica el tipo de firma:
 CER: Certificado digital
 CLA: Clave de acceso';


 /* ACTUALIZAR PROPS TABLA BACKUP DE TRAMITES */
 ALTER table ZPE_TPEBCK  add  TPB_DLGEST VARCHAR(2);

 comment on column ZPE_TPEBCK.TPB_DLGEST is
'ESTADO DE DELEGACION: PASO A FIRMA DOCUMENTO, PASO A PRESENTACION TRAMITE';

 ALTER table ZPE_TPEBCK  add    TPB_DELEGA           VARCHAR(1536);

 comment on column ZPE_TPEBCK.TPB_DELEGA is
'INDICA QUE EL TRAMITE SE ESTA EFECTUANDO DE FORMA DELEGADA PARA ESTE USUARIO (SOLO PARA NIVEL C Y U)';

ALTER table ZPE_DPEBCK ADD DPB_GENDES           VARCHAR(255);

ALTER table ZPE_DPEBCK ADD DPB_DLGEST           VARCHAR(2);

ALTER table ZPE_DPEBCK ADD DPB_DLGFIR           VARCHAR(4000);

ALTER table ZPE_DPEBCK  ADD DPB_DLGFIP           VARCHAR(4000);

comment on column ZPE_DPEBCK.DPB_GENDES is
'DESCRIPCION PERSONALIZADA PARA GENÉRICOS';

comment on column ZPE_DPEBCK.DPB_DLGEST is
'ESTADO DE DELEGACION: PASO A FIRMA DOCUMENTO';

comment on column ZPE_DPEBCK.DPB_DLGFIR is
'NIFS QUE TIENEN QUE FIRMAR EL DOCUMENTO SEPARADOS POR #';

comment on column ZPE_DPEBCK.DPB_DLGFIP is
'NIFS QUE QUEDAN POR FIRMAR EL DOCUMENTO SEPARADOS POR #';

 -- V2.2.7

 ALTER TABLE ZPE_TRAPER ADD TPE_ALTGEN VARCHAR(1) default 'N' not null;
ALTER TABLE ZPE_TRAPER ADD TPE_ALTEMA VARCHAR(500);
ALTER TABLE ZPE_TRAPER ADD TPE_ALTSMS VARCHAR(10);
ALTER TABLE ZPE_TRAPER ADD TPE_ALTFEC TIMESTAMP;

comment on column ZPE_TRAPER.TPE_ALTGEN is 'INDICA SI SE GENERAN ALERTAS DE TRAMITACION (S/N)';
comment on column ZPE_TRAPER.TPE_ALTEMA is 'EMAIL PARA GENERAR ALERTAS';
comment on column ZPE_TRAPER.TPE_ALTSMS is 'SMS PARA GENERAR ALERTAS';
comment on column ZPE_TRAPER.TPE_ALTFEC is 'FECHA ULTIMA ALERTA';


ALTER table ZPE_DOCPER  ADD DPE_TIPO VARCHAR(1);
ALTER table ZPE_DOCPER  ADD DPE_PAGTEL VARCHAR(1) default 'N' not null;

comment on column ZPE_DOCPER.DPE_TIPO is 'TIPO DOCUMENTO: FORMULARIO (F) / PAGO (P) / ANEXO (A)';
comment on column ZPE_DOCPER.DPE_PAGTEL is 'EN CASO DE QUE SEA DE TIPO PAGO, INDICA SI ES TELEMATICO (S/N)';

ALTER table ZPE_TPEBCK ADD TPB_ALTGEN VARCHAR(1) default 'N' not null;
ALTER TABLE ZPE_TPEBCK ADD TPB_ALTEMA VARCHAR(500);
ALTER TABLE ZPE_TPEBCK ADD TPB_ALTSMS VARCHAR(10);
ALTER TABLE ZPE_TPEBCK ADD TPB_ALTFEC TIMESTAMP;

comment on column ZPE_TPEBCK.TPB_ALTGEN is 'INDICA SI SE GENERAN ALERTAS DE TRAMITACION (S/N)';
comment on column ZPE_TPEBCK.TPB_ALTEMA is 'EMAIL PARA GENERAR ALERTAS';
comment on column ZPE_TPEBCK.TPB_ALTSMS is 'SMS PARA GENERAR ALERTAS';
comment on column ZPE_TPEBCK.TPB_ALTFEC is 'FECHA ULTIMA ALERTA';


ALTER table ZPE_DPEBCK  ADD DPB_TIPO VARCHAR(1);
ALTER table ZPE_DPEBCK  ADD DPB_PAGTEL VARCHAR(1) default 'N' not null;

comment on column ZPE_DPEBCK.DPB_TIPO is 'TIPO DOCUMENTO: FORMULARIO (F) / PAGO (P) / ANEXO (A)';
comment on column ZPE_DPEBCK.DPB_PAGTEL is 'EN CASO DE QUE SEA DE TIPO PAGO, INDICA SI ES TELEMATICO (S/N)';


ALTER table ZPE_PREREG ADD PRE_ALTGEN VARCHAR(1) default 'N' not null;
ALTER TABLE ZPE_PREREG ADD PRE_ALTEMA VARCHAR(500);
ALTER TABLE ZPE_PREREG ADD PRE_ALTSMS VARCHAR(10);
ALTER TABLE ZPE_PREREG ADD PRE_ALTFEC TIMESTAMP;

comment on column ZPE_PREREG.PRE_ALTGEN is 'INDICA SI SE GENERAN ALERTAS DE TRAMITACION (S/N)';
comment on column ZPE_PREREG.PRE_ALTEMA is 'EMAIL PARA GENERAR ALERTAS';
comment on column ZPE_PREREG.PRE_ALTSMS is 'SMS PARA GENERAR ALERTAS';
comment on column ZPE_PREREG.PRE_ALTFEC is 'FECHA ULTIMA ALERTA';

ALTER table ZPE_PREBCK ADD PRB_ALTGEN VARCHAR(1) default 'N' not null;
ALTER TABLE ZPE_PREBCK ADD PRB_ALTEMA VARCHAR(500);
ALTER TABLE ZPE_PREBCK ADD PRB_ALTSMS VARCHAR(10);
ALTER TABLE ZPE_PREBCK ADD PRB_ALTFEC TIMESTAMP;

comment on column ZPE_PREBCK.PRB_ALTGEN is 'INDICA SI SE GENERAN ALERTAS DE TRAMITACION (S/N)';
comment on column ZPE_PREBCK.PRB_ALTEMA is 'EMAIL PARA GENERAR ALERTAS';
comment on column ZPE_PREBCK.PRB_ALTSMS is 'SMS PARA GENERAR ALERTAS';
comment on column ZPE_PREBCK.PRB_ALTFEC is 'FECHA ULTIMA ALERTA';

-- UPDATE FROM 2.3.3 TO 2.3.4

-- CONTROL SMS
ALTER table ZPE_TRAPER  ADD TPE_IDEPRO VARCHAR(100);
comment on column ZPE_TRAPER.TPE_IDEPRO is 'ID PROCEDIMIENTO';

-- PLAZO VBLE NOTIF
alter table ZPE_NOTTEL add NOT_DIAPLZ           BIGINT;
comment on column ZPE_NOTTEL.NOT_DIAPLZ is 'En caso de que se establezca un plazo distinto a 10 dias';

-- UPDATE FROM 2.3.4 TO 2.3.5
-- IMPRIMIR SELLO PREREG CONFIRMADO
ALTER table ZPE_PREREG ADD PRE_OFIREG VARCHAR(100);
comment on column ZPE_PREREG.PRE_OFIREG is 'Oficina registro en la que se ha confirmado';
ALTER table ZPE_PREBCK ADD PRB_OFIREG VARCHAR2(100);
comment on column ZPE_PREBCK.PRB_OFIREG is 'Oficina registro en la que se ha confirmado';

-- UPDATE FROM 2.3.5 TO 2.3.6
ALTER table ZPE_PERSON ADD PER_IDEMOD VARCHAR(4000);
comment on column ZPE_PERSON.PER_IDEMOD is 'Ante cambios de NIF se almacenan los NIF anteriores a modo de log';

-- V2.3.9
create index ZPE_INDELE_IDX2 on ZPE_INDELE (
   IND_TIPEL ASC,
   IND_CODEL ASC
);

-- UPDATE V2.3.9 TO V2.3.10
alter table ZPE_NOTTEL  add  NOT_FECREC DATE;

comment on column ZPE_NOTTEL.NOT_FECREC is
'Fecha en la que se rechaza la notificacion';

-- V3.1.3
-- FIN TRAMITE AUTO PARA TRAMITES CON PAGO FINALIZADO
ALTER table ZPE_TRAPER  ADD TPE_ALTFINA character varying(1) default 'N' not null;
comment on column ZPE_TRAPER.TPE_ALTFINA is
'INDICA SI SE INTENTA FINALIZAR TRAMITE ANONIMO CUANDO SE GENERA ALERTA DE TRAMITE INACABADO CON PAGO REALIZADO';

ALTER table ZPE_TPEBCK   ADD TPB_ALTFINA character varying(1) default 'N' not null;
comment on column ZPE_TPEBCK .TPB_ALTFINA is
'INDICA SI SE INTENTA FINALIZAR TRAMITE ANONIMO CUANDO SE GENERA ALERTA DE TRAMITE INACABADO CON PAGO REALIZADO';