
create sequence RDS_SEQAPL;

create sequence RDS_SEQDOC;

create sequence RDS_SEQFIR;

create sequence RDS_SEQFOR;

create sequence RDS_SEQLOG;

create sequence RDS_SEQMOD;

create sequence RDS_SEQPLA;

create sequence RDS_SEQPLI;

create sequence RDS_SEQTIU;

create sequence RDS_SEQUBI;

create sequence RDS_SEQUSO;

create sequence RDS_SEQVER;

create table RDS_ARCPLI  (
   ARP_CODPLI           BIGINT                      not null,
   ARP_DATOS            BYTEA                            not null
);

comment on table RDS_ARCPLI is
'Archivos asociados a una plantilla';

comment on column RDS_ARCPLI.ARP_CODPLI is
'CODIGO';

comment on column RDS_ARCPLI.ARP_DATOS is
'Contenido del fichero de la plantilla';

alter table RDS_ARCPLI
   add constraint RDS_ARP_PK primary key (ARP_CODPLI);

create table RDS_DOCUM  (
   DOC_CODIGO           BIGINT                      not null,
   DOC_CODVER           BIGINT                      not null,
   DOC_CLAVE            VARCHAR(10)                    not null,
   DOC_TITULO           VARCHAR(200)                   not null,
   DOC_FECHA            DATE                            not null,
   DOC_NIF              VARCHAR(11),
   DOC_SEYCON           VARCHAR(1536),
   DOC_UNIADM           BIGINT                      not null,
   DOC_CODUBI           BIGINT                      not null,
   DOC_FICHER           VARCHAR(255)                   not null,
   DOC_EXT              VARCHAR(4)                     not null,
   DOC_HASH             VARCHAR(500)                   not null,
   DOC_CODPLA           BIGINT,
   DOC_DELETE           VARCHAR(1),
   DOC_IDIOMA           VARCHAR(2),
   DOC_REFGD            VARCHAR(4000)
);


comment on table RDS_DOCUM is
'FICHA CON LOS DATOS DE UN DOCUMENTO';

comment on column RDS_DOCUM.DOC_CODIGO is
'CODIGO';

comment on column RDS_DOCUM.DOC_CODVER is
'CODIGO VERSION MODELO';

comment on column RDS_DOCUM.DOC_CLAVE is
'CLAVE DE ACCESO AL DOCUMENTO';

comment on column RDS_DOCUM.DOC_TITULO is
'TITULO DOCUMENTO';

comment on column RDS_DOCUM.DOC_FECHA is
'FECHA INCORPORACION AL RDS. PROPORCIONADA POR RDS.';

comment on column RDS_DOCUM.DOC_NIF is
'PERSONA QUE HA SUMINISTRADO EL DOCUMENTO';

comment on column RDS_DOCUM.DOC_SEYCON is
'USUARIO SEYCON DE LA PERSONA QUE HA SUMINISTRADO EL DOCUMENTO (EN CASO DE QUE ESTE AUTENTICADO)';

comment on column RDS_DOCUM.DOC_UNIADM is
'UNIDAD ADMINISTRATIVA RESPONSABLE DEL DOCUMENTO. ESTE CÓDIGO PROVIENE DEL SAC (TABLA RSC_UNIADM)';

comment on column RDS_DOCUM.DOC_CODUBI is
'CODIGO UBICACIÓN DONDE ESTA ALMACENADO';

comment on column RDS_DOCUM.DOC_FICHER is
'NOMBRE FICHERO';

comment on column RDS_DOCUM.DOC_EXT is
'EXTENSIÓN DEL FICHERO';

comment on column RDS_DOCUM.DOC_HASH is
'HASH DEL DOCUMENTO. SE CALCULARA POR EL RDS AL INCORPORAR EL FICHERO.';

comment on column RDS_DOCUM.DOC_CODPLA is
'PLANTILLA  ESPECIFICA DE VISUALIZACIÓN. PODEMOS INDICAR QUE UN DOCUMENTO ESTRUCTURADO SE VISUALIZE CON UNA PLANTILLA DETERMINADA';

comment on column RDS_DOCUM.DOC_DELETE is
'MARCADO PARA BORRAR POR PROCESO DE BORRADO DE DOCUMENTOS SIN USOS';

comment on column RDS_DOCUM.DOC_IDIOMA is
'PARA DOCUMENTOS ESTRUCTURADOS INDICA EL IDIOMA POR DEFECTO DE VISUALIZACIÓN. SI NO SE ESPECIFICA COGERA "CA".';

comment on column RDS_DOCUM.DOC_REFGD is
'EN CASO DE QUE EL RDS SE SINCRONIZE CON UN GESTOR DOCUMENTAL, INDICA LA REFERENCIA DEL DOCUMENTO EN EL GESTOR DOCUMENTAL.
PARA QUE NO SE MIGREN LOS DOCUMENTOS ANTIGUOS SE MARCARAN CON "#NOCONSOLIDABLE#", INDICANDO QUE NO SE MIGRARAN.';

alter table RDS_DOCUM
   add constraint RDS_DOC_PK primary key (DOC_CODIGO);

create table RDS_FICHER  (
   FIC_CODIGO           BIGINT                      not null,
   FIC_DATOS            BYTEA                            not null
);

comment on table RDS_FICHER is
'UBICACIÓN EN BD DE REPOSITORIO DE FICHEROS';

alter table RDS_FICHER
   add constraint RDS_FIC_PK primary key (FIC_CODIGO);

create table RDS_FIRMAS  (
   FIR_CODIGO           BIGINT                      not null,
   FIR_CODDOC           BIGINT                      not null,
   FIR_FIRMA            BYTEA                            not null,
   FIR_FORMAT           VARCHAR(50)
);

comment on table RDS_FIRMAS is
'FIRMAS DE UN DOCUMENTO';

comment on column RDS_FIRMAS.FIR_CODIGO is
'CODIGO ';

comment on column RDS_FIRMAS.FIR_CODDOC is
'CODIGO FICHA DOCUMENTO';

comment on column RDS_FIRMAS.FIR_FIRMA is
'FIRMA';

comment on column RDS_FIRMAS.FIR_FORMAT is
'FORMATO DE LA FIRMA (EN CASO DE QUE SE PERMITAN VARIOS FORMATOS)';

alter table RDS_FIRMAS
   add constraint RDS_FIR_PK primary key (FIR_CODIGO);

create index RDS_FIRDOC_FK_I on RDS_FIRMAS (
   FIR_CODDOC ASC
);

create table RDS_FORMAT  (
   FOR_ID               BIGINT                      not null,
   FOR_CLASS            VARCHAR(300)                   not null,
   FOR_DESC             VARCHAR(300)                   not null
);

comment on table RDS_FORMAT is
'FORMATEADORES';

comment on column RDS_FORMAT.FOR_ID is
'IDENTIFICADOR DEL FORMATEADOR';

comment on column RDS_FORMAT.FOR_CLASS is
'CLASE DONDE ESTA IMPLEMENTADO EL FORMATEADOR';

comment on column RDS_FORMAT.FOR_DESC is
'DESCRIPCION DEL FORMATEADOR';

alter table RDS_FORMAT
   add constraint PK_RDS_FORMAT primary key (FOR_ID);

create table RDS_IDIOMA  (
   IDI_CODIGO           VARCHAR(2)                     not null,
   IDI_NOMBRE           VARCHAR(50)                    not null
);

comment on table RDS_IDIOMA is
'IDIOMAS';

comment on column RDS_IDIOMA.IDI_CODIGO is
'CODIGO DE IDIOMA';

comment on column RDS_IDIOMA.IDI_NOMBRE is
'NOMBRE IDIOMA';

alter table RDS_IDIOMA
   add constraint RDS_IDI_PK primary key (IDI_CODIGO);

create table RDS_LOGOPE  (
   LOG_CODIGO           BIGINT                      not null,
   LOG_CODTOP           VARCHAR(4)                     not null,
   LOG_DESOPE           VARCHAR(1000)                  not null,
   LOG_FECHA            DATE                            not null,
   LOG_SEYCON           VARCHAR(1536)                  not null
);

comment on table RDS_LOGOPE is
'LOG DE OPERACIONES';

comment on column RDS_LOGOPE.LOG_CODIGO is
'CODIGO';

comment on column RDS_LOGOPE.LOG_CODTOP is
'CODIGO OPERACIÓN';

comment on column RDS_LOGOPE.LOG_DESOPE is
'DESCRIPCIÓN OPERACIÓN';

comment on column RDS_LOGOPE.LOG_FECHA is
'FECHA OPERACIÓN';

comment on column RDS_LOGOPE.LOG_SEYCON is
'USUARIO SEYCON';

alter table RDS_LOGOPE
   add constraint RDS_LOG_PK primary key (LOG_CODIGO);

create table RDS_MODELO  (
   MOD_CODIGO           BIGINT                      not null,
   MOD_MODELO           VARCHAR(20)                    not null,
   MOD_NOMBRE           VARCHAR(100)                   not null,
   MOD_DESC             VARCHAR(400),
   MOD_ESTRUC           VARCHAR(1)                    default 'N' not null,
   MOD_CUSTOD           VARCHAR(1)                    default 'N' not null
);


comment on table RDS_MODELO is
'MODELOS DE DOCUMENTOS';

comment on column RDS_MODELO.MOD_CODIGO is
'CODIGO';

comment on column RDS_MODELO.MOD_MODELO is
'IDENTIFICADOR MODELO';

comment on column RDS_MODELO.MOD_NOMBRE is
'NOMBRE MODELO';

comment on column RDS_MODELO.MOD_DESC is
'DESCRIPCION MODELO';

comment on column RDS_MODELO.MOD_ESTRUC is
'ESTRUCTURADO (S) : XML / NO ESTRUCTURADO (N): FICHERO';

comment on column RDS_MODELO.MOD_CUSTOD is
'INDICA SI SE DEBE CUSTODIAR EL DOCUMENTO FIRMADO';



alter table RDS_MODELO
   add constraint RDS_MOD_PK primary key (MOD_CODIGO);

alter table RDS_MODELO
   add constraint RDS_MODMOD_UNI unique (MOD_MODELO);

create table RDS_PLAIDI  (
   PLI_CODIGO           BIGINT                      not null,
   PLI_CODPLA           BIGINT                      not null,
   PLI_CODIDI           VARCHAR(2)                     not null,
   PLI_NOMFIC           VARCHAR(100)                   not null
);

comment on column RDS_PLAIDI.PLI_CODIGO is
'CODIGO';

comment on column RDS_PLAIDI.PLI_CODPLA is
'CODIGO PLANTILLA';

comment on column RDS_PLAIDI.PLI_CODIDI is
'CODIGO DE IDIOMA';

alter table RDS_PLAIDI
   add constraint RDS_PLI_PK primary key (PLI_CODIGO);

create table RDS_PLANTI  (
   PLA_CODIGO           BIGINT                      not null,
   PLA_CODVER           BIGINT                      not null,
   PLA_TIPO             VARCHAR(3)                     not null,
   PLA_FORMAT           BIGINT                      not null,
   PLA_DEFECT           VARCHAR(1)                    default 'N' not null,
   PLA_BARCOD           VARCHAR(1)                    default 'N' not null,
   PLA_SELLO            VARCHAR(1)                    default 'N' not null
);

comment on table RDS_PLANTI is
'PLANTILLAS PARA DOCUMENTOS ESTRUCTURADOS';

comment on column RDS_PLANTI.PLA_CODIGO is
'CODIGO';

comment on column RDS_PLANTI.PLA_CODVER is
'CODIGO VERSIÓN MODELO';

comment on column RDS_PLANTI.PLA_TIPO is
'TIPO PLANTILLA: HTM / PDF';

comment on column RDS_PLANTI.PLA_FORMAT is
'FORMATEADOR: Clase que va a realizar la transformación a partir de la plantilla';

comment on column RDS_PLANTI.PLA_DEFECT is
'DEFECTO: Indica si es la plantilla por defecto';

comment on column RDS_PLANTI.PLA_BARCOD is
'BARCODE: Indica si se debe establecer un codigo de barras con url de validacion';

comment on column RDS_PLANTI.PLA_SELLO is
'SELLO: Indica si se debe stampar sello preregistro/registro (se mirara en los usos del documento)';

alter table RDS_PLANTI
   add constraint RDS_PLATIP_UNI unique (PLA_CODVER, PLA_TIPO);

alter table RDS_PLANTI
   add constraint RDS_PLA_PK primary key (PLA_CODIGO);

create table RDS_TIOPER  (
   TOP_CODIGO           VARCHAR(4)                     not null,
   TOP_NOMBRE           VARCHAR(50)                    not null
);

comment on table RDS_TIOPER is
'TIPOS DE OPERACIONES PERMITIDAS EN EL RDS: 
 - CREAR DOCUMENTO
 - ASOCIAR FICHERO
 - CREAR USO
 - ....
';

comment on column RDS_TIOPER.TOP_CODIGO is
'CODIGO OPERACIÓN';

comment on column RDS_TIOPER.TOP_NOMBRE is
'NOMBRE OPERACIÓN';

alter table RDS_TIOPER
   add constraint RDS_TOP_PK primary key (TOP_CODIGO);

create table RDS_TIUSO  (
   TIU_CODIGO           VARCHAR(3)                     not null,
   TIU_NOMBRE           VARCHAR(50)                    not null
);

comment on table RDS_TIUSO is
'TIPOS DE USO: 
 - PAD: PAD
 - TRA: IDENTIFICADOR TRAMITE PERSISTENTE
 - RDS: REPOSITORIO DE DOCUMENTOS SEGURO
 - RTE: REGISTRO TELEMATICO DE ENTRADA
 - RTS: REGISTRO TELEMATICO DE SALIDA
 - EXP: EXPEDIENTE
 - BTE: BANDEJA TELEMÁTICA DE ENTRADA
 - PRE: PREREGISTRO
 - ENV: ENVIO
';

comment on column RDS_TIUSO.TIU_CODIGO is
'CODIGO TIPO USO';

comment on column RDS_TIUSO.TIU_NOMBRE is
'NOMBRE DEL USO';

alter table RDS_TIUSO
   add constraint RDS_TIU_PK primary key (TIU_CODIGO);

create table RDS_UBICA  (
   UBI_CODIGO           BIGINT                      not null,
   UBI_IDENT            VARCHAR(5)                     not null,
   UBI_NOMBRE           VARCHAR(50)                    not null,
   UBI_PLUGIN           VARCHAR(500)                   not null,
   UBI_DEFECT 			VARCHAR(1) 					default 'N' not null
);

comment on table RDS_UBICA is
'UBICACIÓN DE LOS DOCUMENTOS';

comment on column RDS_UBICA.UBI_CODIGO is
'CODIGO';

comment on column RDS_UBICA.UBI_IDENT is
'IDENTIFICADOR UBICACIÓN';

comment on column RDS_UBICA.UBI_NOMBRE is
'NOMBRE UBICACIÓN';

comment on column RDS_UBICA.UBI_PLUGIN is
'IDENTIFICA EL PLUGIN QUE IMPLEMENTA EL ALMACENAMIENTO';

comment on column RDS_UBICA.UBI_DEFECT is
'INDICA SI ES LA UBICACION POR DEFECTO (S/N)';

alter table RDS_UBICA
   add constraint RDS_UBIIDE_UNI unique (UBI_IDENT);

alter table RDS_UBICA
   add constraint RDS_UBI_PK primary key (UBI_CODIGO);

create table RDS_USOS  (
   USO_CODIGO           BIGINT                      not null,
   USO_CODTIU           VARCHAR(3)                     not null,
   USO_CODDOC           BIGINT                      not null,
   USO_FECHA            DATE                            not null,
   USO_REF              VARCHAR(100)                    not null,
   USO_FCSELL           DATE
);

comment on table RDS_USOS is
'USOS DEL DOCUMENTO';

comment on column RDS_USOS.USO_CODIGO is
'CODIGO';

comment on column RDS_USOS.USO_CODTIU is
'CODIGO TIPO USO';

comment on column RDS_USOS.USO_CODDOC is
'CODIGO';

comment on column RDS_USOS.USO_FECHA is
'FECHA ALTA USO. PROPORCIONADA POR RDS';

comment on column RDS_USOS.USO_REF is
'REFERENCIA PROPORCIONADA SEGÚN TIPO DE USO: RTE/TRS -> NUM.REGISTRO, EXP -> NUM EXPEDIENTE, TRA -> ID. PERSISTENCIA, ...';

comment on column RDS_USOS.USO_FCSELL is
'PARA TIPO DE USO QUE PERMITEN ESTABLECER SELLO INDICA LA FECHA DE SELLO';

alter table RDS_USOS
   add constraint RDS_USO_PK primary key (USO_CODIGO);

create index RDS_USODOC_IND on RDS_USOS (
   USO_CODDOC ASC
);

create table RDS_VERS  (
   VER_CODIGO           BIGINT                      not null,
   VER_CODMOD           BIGINT                      not null,
   VER_VERSIO           BIGINT                       not null,
   VER_DESC             VARCHAR(100)
);

comment on table RDS_VERS is
'VERSIONES DE MODELOS';

comment on column RDS_VERS.VER_CODIGO is
'CODIGO';

comment on column RDS_VERS.VER_CODMOD is
'CODIGO DEL MODELO VERSIONADO';

comment on column RDS_VERS.VER_VERSIO is
'IDENTIFICADOR VERSIÓN';

comment on column RDS_VERS.VER_DESC is
'DESCRIPCIÓN VERSIÓN';

alter table RDS_VERS
   add constraint RDS_VERMOD_UNI unique (VER_CODMOD, VER_VERSIO);

alter table RDS_VERS
   add constraint RDS_VER_PK primary key (VER_CODIGO);

alter table RDS_ARCPLI
   add constraint RDS_ARPPLI_FK foreign key (ARP_CODPLI)
      references RDS_PLAIDI (PLI_CODIGO);

alter table RDS_DOCUM
   add constraint RDS_DOCPLA_FK foreign key (DOC_CODPLA)
      references RDS_PLANTI (PLA_CODIGO);

alter table RDS_DOCUM
   add constraint RDS_DOCUBI_FK foreign key (DOC_CODUBI)
      references RDS_UBICA (UBI_CODIGO);

alter table RDS_DOCUM
   add constraint RDS_FICVER_FK foreign key (DOC_CODVER)
      references RDS_VERS (VER_CODIGO);

alter table RDS_FIRMAS
   add constraint RDS_FIRDOC_FK foreign key (FIR_CODDOC)
      references RDS_DOCUM (DOC_CODIGO);

alter table RDS_LOGOPE
   add constraint RDS_LOGTOP_FK foreign key (LOG_CODTOP)
      references RDS_TIOPER (TOP_CODIGO);

alter table RDS_PLAIDI
   add constraint RDS_PLIIDI_FK foreign key (PLI_CODIDI)
      references RDS_IDIOMA (IDI_CODIGO);

alter table RDS_PLAIDI
   add constraint RDS_PLIPLA_FK foreign key (PLI_CODPLA)
      references RDS_PLANTI (PLA_CODIGO);

alter table RDS_PLANTI
   add constraint RDS_PLAFOR_FK foreign key (PLA_FORMAT)
      references RDS_FORMAT (FOR_ID);

alter table RDS_PLANTI
   add constraint RDS_PLAVER_FK foreign key (PLA_CODVER)
      references RDS_VERS (VER_CODIGO);

alter table RDS_USOS
   add constraint RDS_USODOC_FK foreign key (USO_CODDOC)
      references RDS_DOCUM (DOC_CODIGO);

alter table RDS_USOS
   add constraint RDS_USOTIU_FK foreign key (USO_CODTIU)
      references RDS_TIUSO (TIU_CODIGO);

alter table RDS_VERS
   add constraint RDS_VERMOD_FK foreign key (VER_CODMOD)
      references RDS_MODELO (MOD_CODIGO);


	  
create table RDS_VERCUS  (
   CUS_CODIGO           VARCHAR(100)                   not null,
   CUS_CODDOC           BIGINT                      not null,
   CUS_FECHA            DATE                            not null,
   CUS_BORRAR           VARCHAR(1)                     not null
);

comment on table RDS_VERCUS is
'VERSIONES DE LA CUSTODIA DE UN DOCUMENTO FIRMADO';

comment on column RDS_VERCUS.CUS_CODIGO is
'CODIGO DEL DOCUMENTO EN LA CUSTODIA ';

comment on column RDS_VERCUS.CUS_CODDOC is
'CODIGO DEL DOCUMENTO';

comment on column RDS_VERCUS.CUS_FECHA is
'FECHA EN LA QUE SE REALIZA LA CUSTODIA';

comment on column RDS_VERCUS.CUS_BORRAR is
'INDICA SI EL DOCUMENTO DEBE SER BORRADO DE CUSTODIA (S/N)';

alter table RDS_VERCUS
   add constraint RDS_CUS_PK primary key (CUS_CODIGO);

alter table RDS_VERCUS
   add constraint RDS_CUSDOC_FK foreign key (CUS_CODDOC)
      references RDS_DOCUM (DOC_CODIGO);
	  
	  
CREATE TABLE RDS_LOGEGD
(
  LOG_CODIGO  BIGINT                        NOT NULL,
  LOG_SEYCON  VARCHAR(1536)               		NOT NULL,
  LOG_DESERR  VARCHAR(4000)               		NOT NULL,
  LOG_ERROR   BYTEA                              NOT NULL,
  LOG_FECHA   DATE                              NOT NULL,
  LOG_CODDOC  BIGINT                        NOT NULL
);
COMMENT ON TABLE RDS_LOGEGD IS 'LOG DE ERRORES DEL GESTOR DOCUMENTAL';
COMMENT ON COLUMN RDS_LOGEGD.LOG_CODIGO IS 'CODIGO';
COMMENT ON COLUMN RDS_LOGEGD.LOG_SEYCON IS 'USUARIO SEYCON';
COMMENT ON COLUMN RDS_LOGEGD.LOG_DESERR IS 'DESCRIPCION DEL ERROR';
COMMENT ON COLUMN RDS_LOGEGD.LOG_ERROR IS 'TRAZA DEL ERROR';
COMMENT ON COLUMN RDS_LOGEGD.LOG_FECHA IS 'FECHA DEL ERROR';

ALTER TABLE RDS_LOGEGD ADD CONSTRAINT RDS_LOGEGD_PK PRIMARY KEY (LOG_CODIGO);

ALTER TABLE RDS_LOGEGD ADD FOREIGN KEY (LOG_CODDOC)  REFERENCES RDS_DOCUM (DOC_CODIGO);
commit;

CREATE SEQUENCE RDS_SEQLGD;

create table RDS_FICEXT  (
   FIE_REFDOC           VARCHAR(500)                   not null,
   FIE_REFFEC           DATE                            not null,
   FIE_CODDOC           BIGINT                      not null,
   FIE_BORRAR           VARCHAR(1)                    default 'N' not null
);

comment on table RDS_FICEXT is
'UBICACIÓN EN REPOSITORIO DE FICHEROS EXTERNOS (PLUGIN UBICACION NO DEFECTO)';

comment on column RDS_FICEXT.FIE_REFDOC is
'REFERENCIA EXTERNA';

comment on column RDS_FICEXT.FIE_REFFEC is
'FECHA REFERENCIA (SOLO SERA VALIDA LA ULTIMA, EL RESTO SE BORRARAN)';

comment on column RDS_FICEXT.FIE_CODDOC is
'CODIGO DOCUMENTO';

comment on column RDS_FICEXT.FIE_BORRAR is
'INDICA SI ESTA MARCADO PARA BORRAR (S/N)';

alter table RDS_FICEXT
   add constraint RDS_FIE_PK primary key (FIE_REFDOC);

create index RDS_FIEDOC_FK_I on RDS_FICEXT (
   FIE_CODDOC ASC
);

-- From 2.2.4 to 2.2.5

alter table RDS_FICEXT  add FIE_CODUBI  BIGINT;

comment on column RDS_FICEXT.FIE_CODUBI is 'INDICA CODIGO DE UBICACION';

COMMIT;

alter table RDS_FICEXT alter column FIE_CODUBI set not null;

