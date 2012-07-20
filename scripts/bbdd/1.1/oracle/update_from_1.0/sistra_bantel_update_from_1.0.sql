
-- From version 1.0.1

alter table BTE_TRAMIT  add  TRA_CLAVE  VARCHAR2(50);
 
comment on column BTE_TRAMIT.TRA_CLAVE is
'CLAVE DE ACCESO A LA ENTRADA';

comment on column BTE_TRAAPL.TAP_EJBAUT is
'PARA TIPO DOMINIO EJB/WS INDICA:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password 
 - C: explícita a través plugin autenticación del organismo';

comment on column BTE_TRAAPL.TAP_USR is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL USUARIO';

comment on column BTE_TRAAPL.TAP_PWD is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL PASSWORD';

alter table BTE_TRAAPL  modify TAP_USR              VARCHAR2(500);
alter table BTE_TRAAPL  modify TAP_PWD              VARCHAR2(500);

update bte_traapl set tap_tipacc = 'E';

update bte_traapl  set TAP_USR = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where TAP_USR IS NOT NULL;
update bte_traapl  set TAP_PWD = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where TAP_PWD IS NOT NULL;

-- From version 1.0.3

ALTER TABLE BTE_GESTOR
 ADD (GES_GESEXP  VARCHAR2(1)                  DEFAULT 'N'                   NOT NULL);


COMMENT ON COLUMN BTE_GESTOR.GES_GESEXP IS 'PERMITE GESTIONAR EXPEDIENTES';



CREATE INDEX BTE_TRA_BUSENT_I ON BTE_TRAMIT (TRA_IDETRA, TRA_PROCES, TRA_FECHA);

-- From version 1.1.0

ALTER TABLE BTE_TRAAPL ADD (TAP_WSVER  VARCHAR2(10 BYTE));

update bte_traapl set tap_wsver = 'v1' where tap_tipacc = 'W';
commit;

ALTER TABLE BTE_TRAAPL
 ADD (TAP_ERRORS  BLOB);

COMMENT ON COLUMN BTE_TRAAPL.TAP_ERRORS IS 'MUESTRA LOS DOS ULTIMOS ERRORES QUE HAN SUCEDIDO DURANTE LAS INTEGACIONES';


ALTER table BTE_TRAMIT ADD TRA_NIFDLG           VARCHAR2(12);
ALTER table BTE_TRAMIT ADD TRA_NOMDLG           VARCHAR2(500);


comment on column BTE_TRAMIT.TRA_NIFDLG is
'En caso de existir delegacion, indica NIF del delegado que presenta el tramite';

comment on column BTE_TRAMIT.TRA_NOMDLG is
'En caso de existir delegacion indica nombre del delegado que presenta el tramite';

ALTER TABLE BTE_TRAMIT  ADD   TRA_SBEXID           VARCHAR2(50);
ALTER TABLE BTE_TRAMIT  ADD   TRA_SBEXUA           NUMBER(19);

comment on column BTE_TRAMIT.TRA_SBEXID is
'EN CASO DE TRAMITE DE SUBSANACION INDICA ID EXPEDIENTE ASOCIADO';

comment on column BTE_TRAMIT.TRA_SBEXUA is
'EN CASO DE TRAMITE DE SUBSANACION INDICA UNIDAD ADMINISTRATIVA EXPEDIENTE';

ALTER TABLE BTE_TRAAPL MODIFY TAP_IDETRA VARCHAR2(20);
ALTER TABLE BTE_TRAMIT MODIFY TRA_IDETRA VARCHAR2(20);

ALTER TABLE BTE_FICEXP  MODIFY FIC_IDETRA VARCHAR2(20); 
ALTER TABLE BTE_GESTRA  MODIFY GAP_IDETRA VARCHAR2(20); 

-- From version 1.1.5 to 1.1.6

alter table BTE_FICEXP drop constraint BTE_FICTAP_FK; 

alter table BTE_TRAMIT drop constraint BTE_TRATAP_FK ;

alter table BTE_GESTRA drop constraint BTE_GAPTAP_FK;

RENAME  BTE_TRAAPL TO BTE_PROAPL;

alter table BTE_PROAPL rename column TAP_IDETRA to TAP_IDEPRO;

alter table BTE_PROAPL modify TAP_IDEPRO VARCHAR2(100);

alter table BTE_FICEXP add FIC_NOMFIC VARCHAR2(500);

update BTE_FICEXP set FIC_NOMFIC = (SELECT TAP_NOMFIC FROM BTE_PROAPL WHERE TAP_IDEPRO = FIC_IDETRA);
COMMIT;

alter table BTE_PROAPL DROP COLUMN TAP_NOMFIC;

alter table BTE_GESTRA rename column GAP_IDETRA to GAP_IDEPRO;

alter table BTE_GESTRA modify GAP_IDEPRO VARCHAR2(100);

RENAME BTE_GESTRA TO BTE_GESPRO;

ALTER TABLE BTE_TRAMIT ADD TRA_IDEPRO VARCHAR2(100);

UPDATE BTE_TRAMIT SET TRA_IDEPRO = TRA_IDETRA;
COMMIT;

ALTER TABLE BTE_TRAMIT MODIFY TRA_IDEPRO NOT NULL;

alter table BTE_TRAMIT add constraint BTE_TRATAP_FK foreign key (TRA_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);

alter table BTE_GESPRO add constraint BTE_GAPTAP_FK foreign key (GAP_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);    
      
      
create table BTE_ARCFEX  (
   AFE_IDEFIC           VARCHAR2(20)                    not null,
   AFE_DATOS            BLOB                            not null
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

INSERT INTO BTE_ARCFEX 
	SELECT BTE_FICEXP.FIC_IDETRA, BTE_FICEXP.FIC_DATOS
		FROM BTE_FICEXP;

COMMIT;		

alter table BTE_FICEXP drop column FIC_DATOS;

alter table BTE_TRAMIT  add  TRA_INIPRO           DATE; 

comment on column BTE_TRAMIT.TRA_INIPRO is
'INDICA FECHA DE INICIO DE PROCESO. SE REINICIARA CADA VEZ QUE PASE A ESTADO NO PROCESADA';

update  BTE_TRAMIT  set TRA_INIPRO = TRA_FECHA;
commit;

alter table BTE_TRAMIT  modify  TRA_INIPRO           not null;

--

ALTER TABLE BTE_PROAPL ADD TAP_UNIADM NUMBER(20);

UPDATE BTE_PROAPL
SET  TAP_UNIADM = ( 
		    SELECT  TR.TRA_UNIADM  
				FROM BTE_TRAMIT TR
				WHERE TR.TRA_CODIGO =  
					( SELECT MAX(T.TRA_CODIGO)
					FROM BTE_TRAMIT T
					WHERE T.TRA_IDEPRO = TAP_IDEPRO
					GROUP BY T.TRA_IDEPRO)
			);
COMMIT;			

ALTER TABLE BTE_PROAPL ADD  TAP_SMS              VARCHAR2(1)                    default 'N' not null;

comment on column BTE_PROAPL.TAP_UNIADM is
'UNIDAD ADMINISTRATIVA';

comment on column BTE_PROAPL.TAP_SMS is
'INDICA SI SE PERMITE INDICAR EL SMS EN LOS AVISOS DE EXPEDIENTE';
