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

COMMIT;
