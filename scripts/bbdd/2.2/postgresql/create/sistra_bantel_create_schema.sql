
create sequence BTE_SEQDOC;

create sequence BTE_SEQE09;

create sequence BTE_SEQORG;

create sequence BTE_SEQTRA;

create table BTE_DOCUM  (
   DOC_CODIGO           BIGINT                      not null,
   DOC_CODTRA           BIGINT                      not null,
   DOC_PRESE            VARCHAR(1)                     not null,
   DOC_DESC             VARCHAR(500)                   not null,
   DOC_DOCIDE           VARCHAR(5),
   DOC_DOCNUM           BIGINT,
   DOC_RDSCOD           BIGINT,
   DOC_RDSCLA           VARCHAR(10),
   DOC_TIPDOC           VARCHAR(1),
   DOC_COMPUL           VARCHAR(1),
   DOC_FOTPIA           VARCHAR(1),
   DOC_FIRMA            VARCHAR(1)
);

comment on table BTE_DOCUM is
'DOCUMENTOS ASOCIADOS A LA ENTRADA';

comment on column BTE_DOCUM.DOC_CODIGO is
'CÓDIGO INTERNO';

comment on column BTE_DOCUM.DOC_CODTRA is
'CÓDIGO ENTRADA';

comment on column BTE_DOCUM.DOC_PRESE is
'Indica si el documento requiere acción presencial';

comment on column BTE_DOCUM.DOC_DESC is
'NOMBRE DEL DOCUMENTO';

comment on column BTE_DOCUM.DOC_DOCIDE is
'Identificador documento  (Para documentos anexados telemáticamente sirve para asociar con documento del asiento)';

comment on column BTE_DOCUM.DOC_DOCNUM is
'Número instancia  (Para documentos anexados telemáticamente sirve para asociar con documento del asiento)';

comment on column BTE_DOCUM.DOC_RDSCOD is
'Para documentos anexados telemáticamente: Código RDS del documento';

comment on column BTE_DOCUM.DOC_RDSCLA is
'Para documentos anexados telemáticamente: Clave RDS del documento';

comment on column BTE_DOCUM.DOC_TIPDOC is
'Para documentos a presentar presencialmente: Indica tipo de documento J/F/A/P (Justificante/Formulario/Anexo/Pago)';

comment on column BTE_DOCUM.DOC_COMPUL is
'Para documentos a presentar presencialmente: Indica si se debe compulsar';

comment on column BTE_DOCUM.DOC_FOTPIA is
'Para documentos a presentar presencialmente: Indica si se debe presentar una fotocopia';

comment on column BTE_DOCUM.DOC_FIRMA is
'Para documentos a presentar presencialmente: Indica si debe ir firmado';

alter table BTE_DOCUM
   add constraint BTE_DOC_PK primary key (DOC_CODIGO);

create index BTE_DOCTRA_FK_I on BTE_DOCUM (
   DOC_CODTRA ASC
);

create table BTE_FICEXP  (
   FIC_IDETRA           VARCHAR(20)                    not null,
   FIC_NOMFIC           VARCHAR(500)
);

comment on column BTE_FICEXP.FIC_IDETRA is
'IDENTIFICADOR DEL TRÁMITE';

alter table BTE_FICEXP
   add constraint BTE_FICEXP_PK primary key (FIC_IDETRA);

create table BTE_GESTOR  (
   GES_SEYCON           VARCHAR(1536)                  not null,
   GES_EMAIL            VARCHAR(500),
   GES_INFORM           BIGINT,
   GES_AVISO            TIMESTAMP,
   GES_UPDEST           VARCHAR(1)                    default 'N' not null,
   GES_UPDESM           VARCHAR(1)                    default 'N' not null,
   GES_GESEXP           VARCHAR(1)                    DEFAULT 'N' NOT NULL
);

comment on table BTE_GESTOR is
'Gestores asociados a un organo.';

comment on column BTE_GESTOR.GES_SEYCON is
'USUARIO SEYCON DEL GESTOR';

comment on column BTE_GESTOR.GES_EMAIL is
'EMAIL DEL GESTOR.';

comment on column BTE_GESTOR.GES_INFORM is
'PERMITE INDICAR CADA CUANTO TIEMPO (EN HORAS) SE GENERARÁ UN CORREO INFORMANDO CON LOS TRAMITES RECIBIDOS (NO PROCESADOS). SI TIENE VALOR 0 NO SE INFORMARÁ.';

comment on column BTE_GESTOR.GES_AVISO is
'FECHA ULTIMO AVISO REALIZADO';

comment on column BTE_GESTOR.GES_UPDEST is
'PERMITE CAMBIAR ESTADO';

comment on column BTE_GESTOR.GES_UPDESM is
'PERMITE CAMBIAR ESTADO MASIVO';

COMMENT ON COLUMN BTE_GESTOR.GES_GESEXP IS 'PERMITE GESTIONAR EXPEDIENTES';

alter table BTE_GESTOR
   add constraint BTE_GES_PK primary key (GES_SEYCON);

create table BTE_GESPRO  (
   GAP_CODGES           VARCHAR(1536)                  not null,
   GAP_IDEPRO           VARCHAR(100)                    not null
);

comment on table BTE_GESPRO is
'TRAMITES ASOCIADOS AL GESTOR';

comment on column BTE_GESPRO.GAP_CODGES is
'USUARIO SEYCON DEL GESTOR';

comment on column BTE_GESPRO.GAP_IDEPRO is
'IDENTIFICADOR DEL PROCEDIMIENTO';

alter table BTE_GESPRO
   add constraint BTE_GAP_PK primary key (GAP_CODGES, GAP_IDEPRO);
   
create table BTE_PROAPL  (
   TAP_IDEPRO           VARCHAR(100)                    not null,
   TAP_DESC             VARCHAR(100)                   not null,
   TAP_INMED            VARCHAR(1)                    default 'N' not null,
   TAP_INFORM           BIGINT,
   TAP_TIPACC           VARCHAR(1)                    default 'E' not null,
   TAP_URL              VARCHAR(200),
   TAP_JNDI             VARCHAR(100),
   TAP_EJBREM           VARCHAR(1)                    default 'L' not null,
   TAP_EJBAUT           VARCHAR(1)                    default 'N' not null,
   TAP_USR              VARCHAR(500),
   TAP_PWD              VARCHAR(500),
   TAP_ROL              VARCHAR(100),
   TAP_AVISO            TIMESTAMP,
-- removed in 1.1.6
--   TAP_NOMFIC           VARCHAR(500),
   TAP_WSVER            VARCHAR(10),
   TAP_ERRORS           BYTEA,
   TAP_UNIADM 			BIGINT,
   TAP_SMS              VARCHAR(1)
);




comment on table BTE_PROAPL is
'LISTA DE TRAMITES QUE SE PUEDEN REGISTRAR EN LA BANDEJA. PARA CADA TRAMITE SE INDICA QUE APLICACIÓN LO GESTIONA.';

comment on column BTE_PROAPL.TAP_IDEPRO is
'IDENTIFICADOR DEL PROCEDIMIENTO';

comment on column BTE_PROAPL.TAP_DESC is
'DESCRIPCION APLICACION';

comment on column BTE_PROAPL.TAP_INMED is
'INDICA SI TRAS UNA ENTRADA SE REALIZARÁ UN AVISO PARA DICHA ENTRADA. ESTE AVISO SE REALIZARÁ DE FORMA ASINCRONA.';

comment on column BTE_PROAPL.TAP_INFORM is
'PERMITE INDICAR CADA CUANTO TIEMPO (EN HORAS) SE AVISARA AL EJB CON LOS TRAMITES RECIBIDOS (NO PROCESADOS). SI TIENE VALOR 0 NO SE INFORMARÁ.';

comment on column BTE_PROAPL.TAP_TIPACC is
'TIPO DE ACCESO AL BACKOFFICE: EJB (E) / WEBSERVICE (W)';

comment on column BTE_PROAPL.TAP_URL is
'INDICA SEGÚN TIPO DE ACCESO: 
- EJB: JNDI EJB
- WEBSERVICE: URL WEBSERVICE
';

comment on column BTE_PROAPL.TAP_JNDI is
'PARA TIPO DOMINIO EJB INDICA JNDI NAME DEL EJB A INVOCAR';

comment on column BTE_PROAPL.TAP_EJBREM is
'PARA TIPO DOMINIO EJB INDICA SI EL EJB ES REMOTO: REMOTO (R) / LOCAL (L)';

comment on column BTE_PROAPL.TAP_EJBAUT is
'PARA TIPO DOMINIO EJB/WS INDICA:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password 
 - C: explícita a través plugin autenticación del organismo';

comment on column BTE_PROAPL.TAP_USR is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL USUARIO';

comment on column BTE_PROAPL.TAP_PWD is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL PASSWORD';

comment on column BTE_PROAPL.TAP_ROL is
'PERMITE ESTABLECER LA COMPROBACIÓN DE ROL QUE TIENE QUE TENER LA APLICACIÓN QUE INVOCA AL EJB DE BANDEJA PARA OBTENER ENTRADAS PENDIENTES';

comment on column BTE_PROAPL.TAP_AVISO is
'FECHA ULTIMO AVISO REALIZADO';

-- removed in 1.1.6
-- comment on column BTE_PROAPL.TAP_NOMFIC is 'NOMBRE FICHERO GUIA EXPORTACION';

COMMENT ON COLUMN BTE_PROAPL.TAP_ERRORS IS 'MUESTRA LOS DOS ULTIMOS ERRORES QUE HAN SUCEDIDO DURANTE LAS INTEGACIONES';

comment on column BTE_PROAPL.TAP_UNIADM is
'UNIDAD ADMINISTRATIVA';

comment on column BTE_PROAPL.TAP_SMS is
'INDICA SI SE PERMITE INDICAR EL SMS EN LOS AVISOS DE EXPEDIENTE';


alter table BTE_PROAPL
   add constraint BTE_TAP_PK primary key (TAP_IDEPRO);
   
alter table BTE_GESPRO add constraint BTE_GAPTAP_FK foreign key (GAP_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);  

create table BTE_TRAMIT  (
   TRA_CODIGO           BIGINT                      not null,
   TRA_NUMENT           VARCHAR(50)                    not null,
   TRA_FECHA            TIMESTAMP                            not null,
   TRA_TIPO             VARCHAR(1)                     not null,
   TRA_PROCES           VARCHAR(1)                     not null,
   TRA_RESPRO           VARCHAR(2000),
   TRA_FECPRO           TIMESTAMP,
   TRA_IDETRA           VARCHAR(20)                    not null,
   TRA_VERTRA           BIGINT                       not null,
   TRA_UNIADM           BIGINT                      not null,
   TRA_NIVAUT           VARCHAR(1)                     not null,
   TRA_DESTRA           VARCHAR(500)                   not null,
   TRA_CODASI           BIGINT                      not null,
   TRA_CLAASI           VARCHAR(10)                    not null,
   TRA_CODJUS           BIGINT                      not null,
   TRA_CLAJUS           VARCHAR(10)                    not null,
   TRA_NUMREG           VARCHAR(50)                    not null,
   TRA_FECREG           TIMESTAMP                            not null,
   TRA_NUMPRE           VARCHAR(50),
   TRA_FECPRE           TIMESTAMP,
   TRA_NIFRTE           VARCHAR(12),
   TRA_NOMRTE           VARCHAR(500),
   TRA_SEYCON           VARCHAR(50),
   TRA_NIFRDO           VARCHAR(12),
   TRA_NOMRDO           VARCHAR(500),
   TRA_IDIOMA           VARCHAR(2)                     not null,
   TRA_TICOPR           VARCHAR(1),
   TRA_NOTTEL           VARCHAR(1),
   TRA_FIRMAD           VARCHAR(1)                     not null,
   TRA_AVISOS           VARCHAR(1),
   TRA_AVISMS           VARCHAR(10),
   TRA_AVIEMA           VARCHAR(500),
   TRA_CLAVE            VARCHAR(50),
   TRA_NIFDLG           VARCHAR(12),
   TRA_NOMDLG           VARCHAR(500),
   TRA_SBEXID           VARCHAR(50),
   TRA_SBEXUA           BIGINT,
   TRA_IDEPRO           VARCHAR(100)                   not null,
   TRA_INIPRO           TIMESTAMP                            not null 
);


comment on table BTE_TRAMIT is
'TRAMITES TELEMÁTICOS DEPOSITADOS EN LA BANDEJA POR EL SISTEMA DE TRAMITACIÓN';

comment on column BTE_TRAMIT.TRA_CODIGO is
'CÓDIGO ENTRADA';

comment on column BTE_TRAMIT.TRA_NUMENT is
'NUMERO DE ENTRADA EN BANDEJA';

comment on column BTE_TRAMIT.TRA_FECHA is
'FECHA ENTRADA';

comment on column BTE_TRAMIT.TRA_TIPO is
'TIPO ENTRADA: E (Registro) / P (Preregistro confirmado) / B (Envío) / N (Preenvio confirmado)';

comment on column BTE_TRAMIT.TRA_PROCES is
'INDICA SI HA SIDO PROCESADA POR EL BACKOFFICE (S/N)';

comment on column BTE_TRAMIT.TRA_RESPRO is
'TEXTO DESCRIPTIVO QUE PERMITE INDICAR RESULTADO DEL PROCESAMIENTO (ESTABLECIDO POR BACKOFFICE)';

comment on column BTE_TRAMIT.TRA_FECPRO is
'FECHA DE PROCESAMIENTO';

comment on column BTE_TRAMIT.TRA_IDETRA is
'IDENTIFICADOR DEL TRAMITE';

comment on column BTE_TRAMIT.TRA_VERTRA is
'VERSIÓN DEL TRAMITE';

comment on column BTE_TRAMIT.TRA_UNIADM is
'UNIDAD ADMINISTRATIVA RESPONSABLE DEL TRAMITE';

comment on column BTE_TRAMIT.TRA_NIVAUT is
'NIVEL DE AUTENTICACIÓN CON EL QUE SE HA REALIZADO EL TRÁMITE';

comment on column BTE_TRAMIT.TRA_DESTRA is
'DESCRIPCION DEL TRAMITE. SE RECOGE EN L A TABLA PARA OPTIMIZAR ACCESO.';

comment on column BTE_TRAMIT.TRA_CODASI is
'CODIGO DE LA REFERENCIA RDS DEL ASIENTO';

comment on column BTE_TRAMIT.TRA_CLAASI is
'CLAVE DE LA REFERENCIA RDS DEL ASIENTO';

comment on column BTE_TRAMIT.TRA_CODJUS is
'CODIGO DE LA REFERENCIA RDS DEL JUSTIFICANTE';

comment on column BTE_TRAMIT.TRA_CLAJUS is
'CLAVE DE LA REFERENCIA RDS DEL JUSTIFICANTE';

comment on column BTE_TRAMIT.TRA_NUMREG is
'INDICA EL NÚMERO DE REGISTRO / ENVIO';

comment on column BTE_TRAMIT.TRA_FECREG is
'FECHA DE REGISTRO';

comment on column BTE_TRAMIT.TRA_NUMPRE is
'PARA TIPO P INDICA NUMERO DE PREREGISTRO';

comment on column BTE_TRAMIT.TRA_FECPRE is
'PARA TIPO P INDICA FECHA DE PREREGISTRO';

comment on column BTE_TRAMIT.TRA_NIFRTE is
'NIF REPRESENTANTE QUE REALIZA TRÁMITE QUE REALIZA TRÁMITE. SE RECOGE EN L A TABLA PARA OPTIMIZAR ACCESO.';

comment on column BTE_TRAMIT.TRA_NOMRTE is
'NOMBRE REPRESENTANTE QUE REALIZA TRÁMITE. SE RECOGE EN L A TABLA PARA OPTIMIZAR ACCESO.';

comment on column BTE_TRAMIT.TRA_SEYCON is
'USUARIO SEYCON EN CASO DE QUE EL ACCESO HAYA SIDO AUTENTICADO';

comment on column BTE_TRAMIT.TRA_NIFRDO is
'NIF REPRESENTADO';

comment on column BTE_TRAMIT.TRA_NOMRDO is
'NOMBRE REPRESENTADO';

comment on column BTE_TRAMIT.TRA_IDIOMA is
'IDIOMA EN EL QUE SE HA REALIZADO EL TRAMITE';

comment on column BTE_TRAMIT.TRA_TICOPR is
'TIPO DE CONFIRMACION DEL PREREGISTRO:
   R -  Entrada confirmada mediante el módulo de confirmación de preregistros/preenvios en el registro presencial (circuito normal)
   G -  Entrada confirmada por el gestor: la documentación no ha sido entregada en un punto de registro o bien porque en el registro presencial no se ha confirmado en el módulo de confirmación de preregistros/preenvios.
   A -  Entrada que se confirmada automáticamente por la plataforma tras realizarse el preenvio (SOLO PARA PREENVIOS)';

comment on column BTE_TRAMIT.TRA_NOTTEL is
'Indica si se ha habilitado la notificacion telematica (en caso de que el tramite la permita). Si el tramite no la permite tendra valor nulo.';

comment on column BTE_TRAMIT.TRA_FIRMAD is
'INDICA SI LA ENTRADA HA SIDO FIRMADA DIGITALMENTE';

comment on column BTE_TRAMIT.TRA_AVISOS is
'INDICA SI LA ENTRADA HA SIDO CONFIGURADA PARA RECIBIR AVISOS DE TRAMITACION';

comment on column BTE_TRAMIT.TRA_AVISMS is
'INDICA EL SMS PARA RECIBIR AVISOS DE TRAMITACION';

comment on column BTE_TRAMIT.TRA_AVIEMA is
'INDICA EL EMAIL PARA RECIBIR AVISOS DE TRAMITACION';

comment on column BTE_TRAMIT.TRA_CLAVE is
'CLAVE DE ACCESO A LA ENTRADA';

comment on column BTE_TRAMIT.TRA_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column BTE_TRAMIT.TRA_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

comment on column BTE_TRAMIT.TRA_SBEXID is
'EN CASO DE TRAMITE DE SUBSANACION INDICA ID EXPEDIENTE ASOCIADO';

comment on column BTE_TRAMIT.TRA_SBEXUA is
'EN CASO DE TRAMITE DE SUBSANACION INDICA UNIDAD ADMINISTRATIVA EXPEDIENTE';

comment on column BTE_TRAMIT.TRA_INIPRO is
'INDICA FECHA DE INICIO DE PROCESO. SE REINICIARA CADA VEZ QUE PASE A ESTADO NO PROCESADA';

alter table BTE_TRAMIT
   add constraint BTE_TRA_PK primary key (TRA_CODIGO);

alter table BTE_TRAMIT
   add constraint BTE_TRANUM_UNI unique (TRA_NUMENT);

alter table BTE_TRAMIT
   add constraint BTE_NUMREG_UNI unique (TRA_NUMREG);

alter table BTE_TRAMIT
   add constraint BTE_NUMPRE_UNI unique (TRA_NUMPRE);

create index BTE_TRATRA_I on BTE_TRAMIT (
   TRA_IDETRA ASC
);

create index BTE_TRATAP_FK_I on BTE_TRAMIT (
   TRA_IDEPRO ASC
);

alter table BTE_TRAMIT add constraint BTE_TRATAP_FK foreign key (TRA_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);

	  
alter table BTE_DOCUM
   add constraint BTE_DOCTRA_FK foreign key (DOC_CODTRA)
      references BTE_TRAMIT (TRA_CODIGO);

-- Removed in 1.1.6
-- alter table BTE_FICEXP
--   add constraint BTE_FICTAP_FK foreign key (FIC_IDETRA)
--      references BTE_TRAAPL (TAP_IDETRA);

alter table BTE_GESPRO
   add constraint BTE_GAPGES_FK foreign key (GAP_CODGES)
      references BTE_GESTOR (GES_SEYCON);

-- Removed in 1.1.6
-- alter table BTE_GESTRA
--   add constraint BTE_GAPTAP_FK foreign key (GAP_IDETRA)
--      references BTE_TRAAPL (TAP_IDETRA);

-- Removed in 1.1.6
-- alter table BTE_TRAMIT
--   add constraint BTE_TRATAP_FK foreign key (TRA_IDETRA)
--      references BTE_TRAAPL (TAP_IDETRA);

CREATE INDEX BTE_TRA_BUSENT_I ON BTE_TRAMIT (TRA_IDETRA, TRA_PROCES, TRA_FECHA);


create table BTE_ARCFEX  (
   AFE_IDEFIC           VARCHAR(20)                    not null,
   AFE_DATOS            BYTEA                            not null
);

comment on table BTE_ARCFEX is
'Archivo fichero exportacion';

comment on column BTE_ARCFEX.AFE_IDEFIC is
'IDENTIFICADOR DEL TRÁMITE';

comment on column BTE_ARCFEX.AFE_DATOS is
'DATOS FICHERO';

alter table BTE_ARCFEX
   add constraint BTE_AFE_PK primary key (AFE_IDEFIC);

alter table BTE_ARCFEX
   add constraint BTE_AFEFIC_FK foreign key (AFE_IDEFIC)
      references BTE_FICEXP (FIC_IDETRA);      

--INSERT INTO BTE_ARCFEX 
--	SELECT BTE_FICEXP.FIC_IDETRA, BTE_FICEXP.FIC_DATOS
--		FROM BTE_FICEXP;

      
-- V2.1.0

create table BTE_AVISOS  (
   AVI_IDENT            VARCHAR(50)                    not null,
   AVI_FCAVIS           TIMESTAMP                            not null
);

comment on table BTE_AVISOS is
'CONTIENE FECHAS ULTIMOS AVISOS';

comment on column BTE_AVISOS.AVI_IDENT is
'Identificador aviso (GESTOR / MONITORIZACION)';

comment on column BTE_AVISOS.AVI_FCAVIS is
'Fecha ultimo aviso';

alter table BTE_AVISOS
   add constraint BTE_AVI_PK primary key (AVI_IDENT);


ALTER table BTE_GESTOR ADD GES_AVIENT           VARCHAR(1)                    default 'N' not null;

comment on column BTE_GESTOR.GES_AVIENT is
'INDICA SI SE GENERA MENSAJE DE AVISO AL GESTOR PARA AVISAR NUEVAS ENTRADAS';

ALTER table BTE_GESTOR ADD GES_AVIMON           VARCHAR(1)                    default 'N' not null;

comment on column BTE_GESTOR.GES_AVIMON is
'INDICA SI SE GENERA AVISO DE MONITORIZACION AL GESTOR';


alter table BTE_GESTOR  add    GES_AVINOT           VARCHAR(1)                    default 'N' not null;

comment on column BTE_GESTOR.GES_AVINOT is
'INDICA SI SE GENERA MENSAJE DE AVISO AL GESTOR PARA AVISAR ESTADO NOTIFICACIONES';


ALTER table BTE_PROAPL  ADD 
   TAP_AVINOT           VARCHAR(1)                    default 'N' not null;

comment on column BTE_PROAPL.TAP_AVINOT is
	'INDICA SI SE DEBEN AVISAR A LOS GESTORES DE LAS NOTIFICACIONES';


ALTER TABLE BTE_GESTOR DROP COLUMN GES_INFORM;

ALTER TABLE BTE_GESTOR DROP COLUMN GES_AVISO;

      
--- upTIMESTAMP 2.2.0

create sequence BTE_SEQCFU;

create sequence BTE_SEQFIF;

create sequence BTE_SEQFUE;

create sequence BTE_SEQVCF;

create table BTE_CAMFUE  (
   CFU_CODIGO           BIGINT                      not null,
   CFU_CODFUE           BIGINT                      not null,
   CFU_IDENT            VARCHAR(20)                    not null,   
   CFU_ESPK             VARCHAR(1)                    default 'N' not null
);

comment on table BTE_CAMFUE is
'Definicion campos fuente datos';

comment on column BTE_CAMFUE.CFU_CODIGO is
'Codigo interno';

comment on column BTE_CAMFUE.CFU_CODFUE is
'Codigo interno fuente';

comment on column BTE_CAMFUE.CFU_IDENT is
'Id campo';


alter table BTE_CAMFUE
   add constraint BTE_CFU_PK primary key (CFU_CODIGO);

create unique index BTE_CFUIDENT_AK on BTE_CAMFUE (
   CFU_CODFUE ASC,
   CFU_IDENT ASC
);

create index BTE_CODFUE_I on BTE_CAMFUE (
   CFU_CODFUE ASC
);


create table BTE_FILFUE  (
   FIF_CODIGO           BIGINT                      not null,
   FIF_CODFUE           BIGINT                      not null
);

comment on table BTE_FILFUE is
'Filas de datos fuente datos';

comment on column BTE_FILFUE.FIF_CODIGO is
'Codigo interno';

comment on column BTE_FILFUE.FIF_CODFUE is
'Codigo interno fuente datos';

alter table BTE_FILFUE
   add constraint BTE_FIF_PK primary key (FIF_CODIGO);

create index BTE_FIFFUE_I on BTE_FILFUE (
   FIF_CODFUE ASC
);

create table BTE_FUEDAT  (
   FUE_CODIGO           BIGINT                      not null,
   FUE_IDENT            VARCHAR(20)                    not null,
   FUE_DESC             VARCHAR(500),
   FUE_IDEPRO           VARCHAR(100)                   not null
);

comment on table BTE_FUEDAT is
'Fuentes de datos para dominios';

comment on column BTE_FUEDAT.FUE_CODIGO is
'Codigo interno';

comment on column BTE_FUEDAT.FUE_IDENT is
'Identificador fuente datos';

comment on column BTE_FUEDAT.FUE_DESC is
'Descripcion';

comment on column BTE_FUEDAT.FUE_IDEPRO is
'IDENTIFICADOR DEL PROCEDIMIENTO';

alter table BTE_FUEDAT
   add constraint BTE_FUE_PK primary key (FUE_CODIGO);

create unique index BTE_FUEIDE_AK on BTE_FUEDAT (
   FUE_IDENT ASC
);

create index BTE_FUEIDP_I on BTE_FUEDAT (
   FUE_IDEPRO ASC
);


create table BTE_VALCFU  (
   VCF_CODIGO           BIGINT                      not null,
   VCF_CODFIF          BIGINT                      not null,
   VCF_CODCFU           BIGINT                      not null,
   VCF_VALOR            VARCHAR(4000)
);

comment on table BTE_VALCFU is
'Valores fuente datos';

comment on column BTE_VALCFU.VCF_CODIGO is
'Codigo interno';

comment on column BTE_VALCFU.VCF_CODFIF is
'Codigo interno fila fuente datos';

comment on column BTE_VALCFU.VCF_CODCFU is
'Codigo interno campo fuente datos';

comment on column BTE_VALCFU.VCF_VALOR is
'Valor';

alter table BTE_VALCFU
   add constraint BTE_VCF_PK primary key (VCF_CODIGO);

create index BTE_VCFCFU_I on BTE_VALCFU (
   VCF_CODCFU ASC
);

create index BTE_VCFFIF_I on BTE_VALCFU (
   VCF_CODFIF ASC
);

alter table BTE_CAMFUE
   add constraint BTE_CFUFUE_FK foreign key (CFU_CODFUE)
      references BTE_FUEDAT (FUE_CODIGO);

alter table BTE_FILFUE
   add constraint BTE_FIFFUE_FK foreign key (FIF_CODFUE)
      references BTE_FUEDAT (FUE_CODIGO);

alter table BTE_FUEDAT
   add constraint BTE_FUETAP_FK foreign key (FUE_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);

alter table BTE_VALCFU
   add constraint BTE_VCFCFU_FK foreign key (VCF_CODCFU)
      references BTE_CAMFUE (CFU_CODIGO);

alter table BTE_VALCFU
   add constraint BTE_VCFFIF_FK foreign key (VCF_CODFIF)
      references BTE_FILFUE (FIF_CODIGO);


