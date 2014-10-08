
-- From version 1.0.3

create table RFR_USUFOR  (
   UST_CODUSU           VARCHAR2(200)                   not null,
   UST_CODFOR           NUMBER(19)                      not null
);

comment on table RFR_USUFOR is
'Permisos individuales para un usuario de acceso a formularios';

comment on column RFR_USUFOR.UST_CODUSU is
'CODIGO USUARIO';

comment on column RFR_USUFOR.UST_CODFOR is
'CODIGO FORMULARIO';

alter table RFR_USUFOR
   add constraint RFR_UST_PK primary key (UST_CODUSU, UST_CODFOR);

alter table RFR_USUFOR
   add constraint RFR_USTFOR_FK foreign key (UST_CODFOR)
      references RFR_FORMUL (FOR_CODI);

create table RFR_GRUPOS  (
   GRP_CODIGO           VARCHAR2(50)                    not null,
   GRP_NOMBRE           VARCHAR2(100)                   not null,
   GRP_DESCP            VARCHAR2(300)
);

comment on table RFR_GRUPOS is
'Grupos de usuarios para establecer permisos de acceso a formularios';

comment on column RFR_GRUPOS.GRP_CODIGO is
'Código grupo';

comment on column RFR_GRUPOS.GRP_NOMBRE is
'Nombre grupo';

comment on column RFR_GRUPOS.GRP_DESCP is
'Descripción grupo';

alter table RFR_GRUPOS
   add constraint RFR_GRP_PK primary key (GRP_CODIGO);

create table RFR_GRPUSU  (
   GRU_CODGRP           VARCHAR2(50)                    not null,
   GRU_CODUSU           VARCHAR2(200)                   not null
);

comment on table RFR_GRPUSU is
'Asignación de usuarios a grupos';

comment on column RFR_GRPUSU.GRU_CODGRP is
'CODIGO GRUPO';

comment on column RFR_GRPUSU.GRU_CODUSU is
'CODIGO USUARIO';

alter table RFR_GRPUSU
   add constraint RFR_GRU_PK primary key (GRU_CODGRP, GRU_CODUSU);

alter table RFR_GRPUSU
   add constraint RFR_GRUGRP_FK foreign key (GRU_CODGRP)
      references RFR_GRUPOS (GRP_CODIGO);

create table RFR_GRPFOR  (
   GRF_CODGRP           VARCHAR2(50)                    not null,
   GRF_CODFOR           NUMBER(19)                      not null
);

comment on table RFR_GRPFOR is
'Formularios a los que el grupo tiene acceso';

comment on column RFR_GRPFOR.GRF_CODGRP is
'CODIGO GRUPO';

comment on column RFR_GRPFOR.GRF_CODFOR is
'CODIGO FORMULARIO';

alter table RFR_GRPFOR
   add constraint RFR_GRF_PK primary key (GRF_CODGRP, GRF_CODFOR);

alter table RFR_GRPFOR
   add constraint RFR_GRFFOR_FK foreign key (GRF_CODFOR)
      references RFR_FORMUL (FOR_CODI);

alter table RFR_GRPFOR
   add constraint RFR_GRFGRP_FK foreign key (GRF_CODGRP)
      references RFR_GRUPOS (GRP_CODIGO);
	  
-- From version 1.1.0

alter table RFR_PATRON add constraint RFR_PAT_NOMBRE_FK unique (PAT_NOMBRE);   

-- To version 1.1.0 (ingles)

-- Dejamos solo castellano, catalan e ingles 
DELETE FROM rfr_idioma WHERE idi_codi = 'it';
DELETE FROM rfr_idioma WHERE idi_codi = 'pt';
UPDATE rfr_idioma SET idi_orden=1  WHERE idi_codi = 'ca';
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('en', 2);
	  