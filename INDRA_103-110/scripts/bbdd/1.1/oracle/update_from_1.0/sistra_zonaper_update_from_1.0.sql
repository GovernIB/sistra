
-- From 1.0.2

alter table ZPE_DOCHIE  add DHE_ORDEN            NUMBER(2);
comment on column ZPE_DOCHIE.DHE_ORDEN is 'Orden';

alter table ZPE_DOCNOT  add  DNO_ORDEN            NUMBER(2);
comment on column ZPE_DOCNOT.DNO_ORDEN is 'Orden';

-- Modificar tabla de pagos
ALTER TABLE ZONAPER.ZPE_RPAGOS
MODIFY(PAG_NOMTRA VARCHAR2(200 BYTE));

ALTER TABLE ZONAPER.ZPE_RPAGOS
MODIFY(PAG_CONCEP VARCHAR2(200 BYTE));

ALTER TABLE ZONAPER.ZPE_RPAGOS
MODIFY(PAG_IDENTP VARCHAR2(200 BYTE));

ALTER TABLE ZONAPER.ZPE_RPAGOS
MODIFY(PAG_URLSTR VARCHAR2(400 BYTE));

ALTER TABLE ZONAPER.ZPE_RPAGOS
MODIFY(PAG_URLMNT VARCHAR2(400 BYTE));

-- From 1.0.3

-- Actualizacion vista para contemplar estado de solicitud enviada pendiente de entregar doc presencial (preregistros)
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

SELECT  'P' ||TO_CHAR(PRE_CODIGO),'P',PRE_CODIGO,PRE_DESC,PRE_FECHA,PRE_FECHA, NVL2(PRE_FECREG,'SE','SP'),DECODE(PRE_NIVAUT,'A','N','S'),  
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


--- Actualizacion tabla pagos CAIB

alter table ZPE_RPAGOS add PAG_IDETRA VARCHAR2(50);
alter table ZPE_RPAGOS add PAG_MODTRA VARCHAR2(10);
alter table ZPE_RPAGOS add PAG_VERTRA NUMBER(2);
alter table ZPE_RPAGOS modify PAG_NIFDEC VARCHAR2(12);

comment on column ZPE_RPAGOS.PAG_IDETRA is
'Identificador del trámite (Id persistencia)';

comment on column ZPE_RPAGOS.PAG_MODTRA is
'Modelo del trámite';

comment on column ZPE_RPAGOS.PAG_VERTRA is
'Versión del trámite';

-- From 1.1.0

create table ZPE_REGEXT  (
   REG_CODIGO           NUMBER(20)                      not null,
   REG_NIVAUT           VARCHAR2(1),
   REG_DESC             VARCHAR2(200)                   not null,
   REG_USER             VARCHAR2(1536),
   REG_NUMREG           VARCHAR2(50)                    not null,
   REG_FECHA            DATE                            not null,
   REG_CODASI           NUMBER(20)                      not null,
   REG_CLAASI           VARCHAR2(10)                    not null,
   REG_CODJUS           NUMBER(20)                      not null,
   REG_CLAJUS           VARCHAR2(10)                    not null,
   REG_IDIOMA           VARCHAR2(2)                     not null,
   REG_NIFRTE           VARCHAR2(12),
   REG_NOMRTE           VARCHAR2(500),
   REG_NIFRDO           VARCHAR2(12),
   REG_NOMRDO           VARCHAR2(500)
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
   DRE_CODIGO           NUMBER(20)                      not null,
   DRE_CODREG           NUMBER(20)                      not null,
   DRE_DOCIDE           VARCHAR2(5)                     not null,
   DRE_DOCNUM           NUMBER(2)                       not null,
   DRE_DESC             VARCHAR2(500)                   not null,
   DRE_RDSCOD           NUMBER(20)                      not null,
   DRE_RDSCLA           VARCHAR2(10)                    not null
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
   RLG_TIPREG           VARCHAR2(1)                     not null,
   RLG_NUMREG           VARCHAR2(50)                    not null,
   RLG_FECREG           DATE                            not null,
   RLG_ERROR            VARCHAR2(2000)
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


ALTER TABLE ZPE_REGLOG
 ADD (RLG_ANULAD  VARCHAR2(1 BYTE));
 
 ALTER TABLE ZPE_EXPEDI ADD EXP_NIFRTE          VARCHAR2(12);

comment on column ZPE_EXPEDI.EXP_NIFRTE is
'Para expedientes ''autenticados'' contiene el nif del representante';

ALTER TABLE  ZPE_PERSON  ADD PER_DELEGA           VARCHAR2(1)                    default 'N' not null;

comment on column ZPE_PERSON.PER_DELEGA is
'Indica si esta habilitada la delegacion';

UPDATE ZPE_EXPEDI
SET 
	EXP_NIFRTE = (SELECT PER_IDENTI FROM ZPE_PERSON WHERE PER_SEYCON = EXP_SEYCIU )
WHERE 
	EXP_SEYCIU IS NOT NULL;

COMMIT;

create sequence ZPE_SEQDEL;


create table ZPE_DELEGA  (
   DEL_CODIGO           NUMBER(20)                      not null,
   DEL_DLGTE            VARCHAR2(12)                    not null,
   DEL_DLGDO            VARCHAR2(12)                    not null,
   DEL_PERMIS           VARCHAR2(10)                    not null,
   DEL_INIDLG           DATE                            not null,
   DEL_FINDLG           DATE                            not null,
   DEL_CODRDS           NUMBER(20)                      not null,
   DEL_CLARDS           VARCHAR2(10)                    not null,
   DEL_ANULAD           VARCHAR2(1)                    default 'N' not null
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
   
   
CREATE OR REPLACE VIEW ZPE_ESTEXP
(EST_ID, EST_TIPO, EST_CODIGO, EST_DESC, EST_FECINI, 
 EST_FECFIN, EST_ESTADO, EST_AUTENT, EST_USER, EST_NIFRTE, EST_NIFRDO, 
 EST_IDIOMA, EST_CODEXP, EST_UNIADM)
AS 
(
SELECT 'T' ||TO_CHAR(ENT_CODIGO), 'T',ENT_CODIGO,ENT_DESC,ENT_FECHA,ENT_FECHA,'SE', DECODE(ENT_NIVAUT,'A','N','S'), 
DECODE(ENT_NIVAUT,'A',ENT_IDEPER,ENT_USER), DECODE(ENT_NIVAUT,'A',NULL,ENT_NIFRTE), ENT_NIFRDO,ENT_IDIOMA,NULL,NULL
FROM ZPE_ENTTEL
WHERE 
ENT_CODIGO NOT IN 
(
 SELECT ELE_CODELE FROM ZPE_ELEEX WHERE ELE_TIPO = 'T'
)

UNION

SELECT  'P' ||TO_CHAR(PRE_CODIGO),'P',PRE_CODIGO,PRE_DESC,PRE_FECHA,PRE_FECHA, NVL2(PRE_FECREG,'SE','SP'),DECODE(PRE_NIVAUT,'A','N','S'),  
DECODE(PRE_NIVAUT,'A',PRE_IDEPER,PRE_USER), DECODE(PRE_NIVAUT,'A',NULL,PRE_NIFRTE), PRE_NIFRDO,PRE_IDIOMA,NULL,NULL
FROM ZPE_PREREG
WHERE 
PRE_CODIGO NOT IN 
(
 SELECT ELE_CODELE FROM ZPE_ELEEX WHERE ELE_TIPO = 'P'
)

UNION

SELECT  'E' ||TO_CHAR(EXP_CODIGO),'E',EXP_CODIGO,EXP_DESC,EXP_FECINI,EXP_FECULT,EXP_ESTADO,'S',EXP_SEYCIU, EXP_NIFRTE, EXP_NIFRDO,EXP_IDIOMA,EXP_IDEXP,EXP_UNIADM
FROM ZPE_EXPEDI
WHERE EXP_SEYCIU IS NOT NULL

UNION

SELECT 'E' ||TO_CHAR(EXP_CODIGO),'E',EXP_CODIGO,EXP_DESC,EXP_FECINI,EXP_FECULT,EXP_ESTADO,'N',ENT_IDEPER, NULL, EXP_NIFRDO,EXP_IDIOMA,EXP_IDEXP,EXP_UNIADM
FROM ZPE_EXPEDI,ZPE_ELEEX,ZPE_ENTTEL
WHERE EXP_SEYCIU IS NULL AND
	  EXP_CODIGO = ELE_CODEXP AND
	  ELE_TIPO = 'T' AND ELE_CODELE = ENT_CODIGO
      
UNION
	  
SELECT 'E' ||TO_CHAR(EXP_CODIGO),'E',EXP_CODIGO,EXP_DESC,EXP_FECINI,EXP_FECULT,EXP_ESTADO,'N',PRE_IDEPER, NULL, EXP_NIFRDO,EXP_IDIOMA,EXP_IDEXP,EXP_UNIADM
FROM ZPE_EXPEDI,ZPE_ELEEX,ZPE_PREREG
WHERE EXP_SEYCIU IS NULL AND
	  EXP_CODIGO = ELE_CODEXP AND
	  ELE_TIPO = 'P' AND ELE_CODELE = PRE_CODIGO      	  
)
;


ALTER table ZPE_TRAPER  ADD  TPE_DELEGA           VARCHAR2(1536);

comment on column ZPE_TRAPER.TPE_DELEGA is
'USUARIO QUE  ESTA EFECTUANDO DE EL TRAMITE DE FORMA DELEGADA  (SOLO PARA NIVEL C Y U)';

ALTER table ZPE_ENTTEL  ADD  ENT_NIFDLG           VARCHAR2(12);
ALTER table ZPE_ENTTEL  ADD   ENT_NOMDLG           VARCHAR2(500);

comment on column ZPE_ENTTEL.ENT_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column ZPE_ENTTEL.ENT_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

ALTER table ZPE_PREBCK  ADD  PRB_NIFDLG           VARCHAR2(12);
ALTER table ZPE_PREBCK  ADD  PRB_NOMDLG           VARCHAR2(500);

comment on column ZPE_PREBCK.PRB_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column ZPE_PREBCK.PRB_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

ALTER table ZPE_TRAPER  ADD  TPE_DLGEST           VARCHAR2(2);

comment on column ZPE_TRAPER.TPE_DLGEST is
'ESTADO DE DELEGACION: PASO A FIRMA DOCUMENTO, PASO A PRESENTACION TRAMITE';

 ALTER table ZPE_DOCPER  ADD DPE_DLGEST VARCHAR2(2);
 ALTER table ZPE_DOCPER  ADD DPE_DLGFIR VARCHAR2(4000);
 ALTER table ZPE_DOCPER  ADD DPE_DLGFIP VARCHAR2(4000);  


 comment on column ZPE_DOCPER.DPE_DLGEST is
'ESTADO DE DELEGACION: PASO A FIRMA DOCUMENTO';

comment on column ZPE_DOCPER.DPE_DLGFIR is
'NIFS QUE TIENEN QUE FIRMAR EL DOCUMENTO SEPARADOS POR #';

comment on column ZPE_DOCPER.DPE_DLGFIP is
'NIFS QUE QUEDAN POR FIRMAR EL DOCUMENTO SEPARADOS POR #';

ALTER TABLE ZPE_PREREG  ADD  PRE_NIFDLG  VARCHAR2(12);
ALTER TABLE ZPE_PREREG  ADD  PRE_NOMDLG  VARCHAR2(500);

comment on column ZPE_PREREG.PRE_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column ZPE_PREREG.PRE_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

create table ZPE_SUBPAR  (
   SBP_CODIGO           VARCHAR2(50)                    not null,
   SBP_UNIADM           NUMBER(19)                      not null,
   SBP_IDEXP            VARCHAR2(50)                    not null,
   SBP_PARAMS           VARCHAR2(4000),
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

   
   
ALTER TABLE  ZPE_NOTTEL ADD  NOT_SUBDES           VARCHAR2(500);
ALTER TABLE  ZPE_NOTTEL ADD   NOT_SUBIDE           VARCHAR2(10);
ALTER TABLE  ZPE_NOTTEL ADD   NOT_SUBVER           NUMBER(2);
ALTER TABLE  ZPE_NOTTEL ADD   NOT_SUBPAR           VARCHAR2(4000);

comment on column ZPE_NOTTEL.NOT_SUBDES is
'En caso de que haya trámite de subsanación indica la descripción del trámite';

comment on column ZPE_NOTTEL.NOT_SUBIDE is
'En caso de que haya trámite de subsanación indica el id del trámite';

comment on column ZPE_NOTTEL.NOT_SUBVER is
'En caso de que haya trámite de subsanación indica la versión del trámite';

comment on column ZPE_NOTTEL.NOT_SUBPAR is
'En caso de que haya trámite de subsanación indica los parámetros de inicio  del trámite';


ALTER TABLE zpe_enttel ADD ent_sbexid           VARCHAR2(50);
ALTER TABLE zpe_enttel ADD ent_sbexua           NUMBER(19);

COMMENT ON COLUMN zpe_enttel.ent_sbexid IS
'En caso de que sea un tramite de subsanacion indica el identificador del expediente';

COMMENT ON COLUMN zpe_enttel.ent_sbexua IS
'En caso de que sea un tramite de subsanacion indica la unidad administrativa que da de alta el expediente';

ALTER TABLE zpe_prebck ADD prb_sbexid           VARCHAR2(50);
ALTER TABLE zpe_prebck ADD prb_sbexua           NUMBER(19);

COMMENT ON COLUMN zpe_prebck.prb_sbexid IS
'En caso de que sea un tramite de subsanacion indica el identificador del expediente';

COMMENT ON COLUMN zpe_prebck.prb_sbexua IS
'En caso de que sea un tramite de subsanacion indica la unidad administrativa que da de alta el expediente';



ALTER TABLE zpe_prereg ADD pre_sbexid           VARCHAR2(50);
ALTER TABLE zpe_prereg ADD pre_sbexua           NUMBER(19);

COMMENT ON COLUMN zpe_prereg.pre_sbexid IS
'En caso de que sea un tramite de subsanacion indica el identificador del expediente';

COMMENT ON COLUMN zpe_prereg.pre_sbexua IS
'En caso de que sea un tramite de subsanacion indica la unidad administrativa que da de alta el expediente';

ALTER TABLE ZPE_PERSON ADD PER_SEYMOD VARCHAR2(4000);

comment on column ZPE_PERSON.PER_SEYMOD is
'Ante cambios de usuario se almacenan los usuarios anteriores a modo de log';


ALTER TABLE ZPE_TRAPER MODIFY TPE_TRAMOD VARCHAR2(20);
ALTER TABLE ZPE_TPEBCK MODIFY TPB_TRAMOD VARCHAR2(20);
ALTER TABLE ZPE_ENTTEL MODIFY ENT_TRAMOD VARCHAR2(20);
ALTER TABLE ZPE_PREREG MODIFY PRE_TRAMOD VARCHAR2(20);
ALTER TABLE ZPE_PREBCK MODIFY PRB_TRAMOD VARCHAR2(20);
ALTER TABLE ZPE_NOTTEL MODIFY NOT_SUBIDE VARCHAR2(20);


-- Modificar tabla de pagos CAIB
alter table ZPE_RPAGOS  add  temp clob ;
update  ZPE_RPAGOS  set temp=PAG_RECIBO;
alter table ZPE_RPAGOS drop column PAG_RECIBO;
alter table ZPE_RPAGOS  rename column temp to  PAG_RECIBO;


-- From version 1.1.2

create table ZPE_RGPEXT  (
   RGP_RDSCOD           NUMBER(20)                      not null,
   RGP_RDSCLA           VARCHAR2(10)                    not null,
   RGP_RDSANE           VARCHAR2(4000),
   RGP_IDEPER           VARCHAR2(50)                    not null,
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
   IND_CODIGO           NUMBER(20)                      not null,
   IND_NIF              VARCHAR2(10)                    not null,
   IND_TIPEL            VARCHAR2(1)                     not null,
   IND_CODEL            NUMBER(20)                      not null,
   IND_DESCR            VARCHAR2(1000)                  not null,
   IND_VALOR            VARCHAR2(4000)                 
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

-- From 1.1.5 to 1.1.6

lter table  ZPE_ENTTEL add ENT_IDEPRO  VARCHAR2(100);

comment on column ZPE_ENTTEL.ENT_IDEPRO is 'IDENTIFICADOR PROCEDIMIENTO';

alter table  ZPE_PREREG add PRE_IDEPRO  VARCHAR2(100);

comment on column ZPE_PREREG.PRE_IDEPRO is 'IDENTIFICADOR PROCEDIMIENTO';

alter table  ZPE_PREBCK add PRB_IDEPRO  VARCHAR2(100);

comment on column ZPE_PREBCK.PRB_IDEPRO is 'IDENTIFICADOR PROCEDIMIENTO';

alter table ZPE_EXPEDI  add  EXP_IDEPRO   VARCHAR2(100)  ;

UPDATE ZPE_ENTTEL SET ENT_IDEPRO = ENT_TRAMOD;
UPDATE  ZPE_PREREG SET PRE_IDEPRO = PRE_TRAMOD;
UPDATE ZPE_PREBCK SET PRB_IDEPRO = PRB_TRAMOD;

UPDATE ZPE_ENTTEL SET ENT_IDEPRO = '#DESCONOCIDO#' WHERE   ENT_IDEPRO IS NULL;
UPDATE  ZPE_PREREG SET PRE_IDEPRO = '#DESCONOCIDO#' WHERE  PRE_IDEPRO IS NULL;
UPDATE ZPE_PREBCK SET PRB_IDEPRO =  '#DESCONOCIDO#' WHERE  PRB_IDEPRO IS NULL; 

COMMIT;

CREATE VIEW TMP_TRAMITS_INI_EXPE (CODEXPE, TIPOTRAM, CODTRAM) AS
(
SELECT E1.ELE_CODEXP, E1.ELE_TIPO, E1.ELE_CODELE
FROM ZPE_ELEEX E1
WHERE E1.ELE_TIPO IN ('T', 'P') AND
 E1.ELE_FECHA = (SELECT MIN(E2.ELE_FECHA) FROM  ZPE_ELEEX E2 WHERE E2.ELE_CODEXP = E1.ELE_CODEXP) 
);

CREATE VIEW TMP_IDEPRO_EXPE (CODEXPE, IDEPRO) AS 
(
   select EXP_CODIGO, ENT_IDEPRO  FROM TMP_TRAMITS_INI_EXPE, ZPE_ENTTEL, ZPE_EXPEDI WHERE  CODEXPE =  EXP_CODIGO AND TIPOTRAM = 'T' AND ENT_CODIGO = CODTRAM
    UNION
     select EXP_CODIGO, PRE_IDEPRO  FROM TMP_TRAMITS_INI_EXPE, ZPE_PREREG, ZPE_EXPEDI WHERE  CODEXPE =  EXP_CODIGO AND TIPOTRAM = 'P'  AND PRE_CODIGO = CODTRAM  
);

update  ZPE_EXPEDI 
set EXP_IDEPRO = (SELECT IDEPRO FROM TMP_IDEPRO_EXPE WHERE CODEXPE =  EXP_CODIGO )
where EXP_NUMBTE IS NOT null;

COMMIT;

DROP VIEW TMP_IDEPRO_EXPE;
DROP VIEW TMP_TRAMITS_INI_EXPE;

update  ZPE_EXPEDI 
set EXP_IDEPRO = '#DESCONOCIDO#'
where EXP_IDEPRO IS NULL ;

COMMIT;

alter table ZPE_EXPEDI  modify  EXP_IDEPRO  not null;


