alter table STR_DOMIN  modify DOM_USR              VARCHAR2(500);
alter table STR_DOMIN  modify DOM_PWD              VARCHAR2(500);
   
comment on column STR_DOMIN.DOM_EJBSTD is
'Para tipo dominio Ejb/Ws indica si se debe realizar:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password 
 - C: explícita a través plugin autenticación del organismo';
 
 comment on column STR_DOMIN.DOM_USR is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL USUARIO';

comment on column STR_DOMIN.DOM_PWD is
'PARA TIPO AUTENTICACION EXPLICITA POR USUARIO/PASSWORD INDICA EL PASSWORD';
 
alter table STR_TRAVER add TRV_CONTIP VARCHAR2(1) default 'E' not null;

alter table STR_TRAVER  modify TRV_CONUSU              VARCHAR2(500);
alter table STR_TRAVER  modify TRV_CONPWD              VARCHAR2(500);

comment on column STR_TRAVER.TRV_CONAUT is
'Para trámite con destino Consulta indica si se debe realizar:
 - N: autenticación implícita de forma que el contenedor EJBs traspasa autenticacion
 - S: explícita a traves de usuario/password 
 - C: explícita a través plugin autenticación del organismo';
 
 comment on column STR_TRAVER.TRV_CONUSU is
'Para trámite con destino Consulta y con autenticación explicíta con usuario/password se indica usuario';

comment on column STR_TRAVER.TRV_CONPWD is
'Para trámite con destino Consulta y con autenticación explicíta  con usuario/password  se indica password';

 
update STR_DOMIN  set DOM_USR = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where DOM_USR IS NOT NULL;
update STR_DOMIN  set DOM_PWD = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where DOM_PWD IS NOT NULL;

update STR_TRAVER  set TRV_CONUSU = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where TRV_CONUSU IS NOT NULL;
update STR_TRAVER  set TRV_CONPWD = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where TRV_CONPWD IS NOT NULL;

COMMIT;
