
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

-- To version 1.1.0

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

