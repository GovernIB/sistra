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

