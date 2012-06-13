
-- From version 1.0.1

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


-- From version 1.0.2

alter table STR_DATJUS
	modify(DJS_ORDEN NUMBER(4));
	
	
-- From version 1.0.3

alter table STR_DOCUM  add DOC_ANEPDF VARCHAR2(1) default 'N';

comment on column STR_DOCUM.DOC_ANEPDF is
'Para Anexos: indica si hay que convertir el anexo a PDF (extensiones doc y odt)';

alter table STR_ESPNIV  add ETN_DSTTRA  BLOB;

comment on column STR_ESPNIV.ETN_DSTTRA is
'Script que permite cambiar dinámicamente la información del destinatario del trámite (oficina registral, organo destino y unidad administrativa)';


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

-- From 1.1.0

ALTER TABLE STR_TRAVER ADD (TRV_CONWSV  VARCHAR2(10 BYTE));
update str_traver set trv_conwsv = 'v1' where trv_contip = 'W';
ALTER TABLE STR_DOMIN ADD (DOM_WSVER  VARCHAR2(10 BYTE));
update str_domin set dom_wsver = 'v1' where dom_tipo = 'W';

commit;

ALTER TABLE STR_TRAMIT MODIFY TRA_IDENTI VARCHAR2(20);
ALTER TABLE STR_DOMIN MODIFY DOM_IDENTI VARCHAR2(20);
ALTER TABLE STR_DOCUM MODIFY DOC_MODELO VARCHAR2(20);

-- From 1.1.0 (ingles)

INSERT INTO STR_IDIOMA ( IDI_CODIGO, IDI_ORDEN ) VALUES ('en', 3); 

-- from 1.1.5

alter table STR_DOCUM  add DOC_FORAJU VARCHAR2(1) default 'N';
comment on column STR_DOCUM.DOC_FORAJU is
'Para Formulario: se indica si el formulario debe anexarse al justificante';

-- From 1.1.6

ALTER TABLE STR_TRAMIT ADD TRA_IDPROC VARCHAR2(100);

comment on column STR_TRAMIT.TRA_IDPROC is
'Identificador del procedimiento al que pertenece el trámite';

UPDATE STR_TRAMIT SET TRA_IDPROC = TRA_IDENTI;
COMMIT;

ALTER TABLE STR_TRAMIT  MODIFY  TRA_IDPROC  not null;

