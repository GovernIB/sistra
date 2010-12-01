create table RFR_USUFOR  (
   UST_CODUSU           VARCHAR(200)                    not null,
   UST_CODFOR           INT8	                        not null
);

alter table RFR_USUFOR
   add constraint RFR_UST_PK primary key (UST_CODUSU, UST_CODFOR);

alter table RFR_USUFOR
   add constraint RFR_USTFOR_FK foreign key (UST_CODFOR)
      references RFR_FORMUL (FOR_CODI);

create table RFR_GRUPOS  (
   GRP_CODIGO           VARCHAR(50)                     not null,
   GRP_NOMBRE           VARCHAR(100)                    not null,
   GRP_DESCP            VARCHAR(300)
);

alter table RFR_GRUPOS
   add constraint RFR_GRP_PK primary key (GRP_CODIGO);

create table RFR_GRPUSU  (
   GRU_CODGRP           VARCHAR(50)                     not null,
   GRU_CODUSU           VARCHAR(200)                    not null
);

alter table RFR_GRPUSU
   add constraint RFR_GRU_PK primary key (GRU_CODGRP, GRU_CODUSU);

alter table RFR_GRPUSU
   add constraint RFR_GRUGRP_FK foreign key (GRU_CODGRP)
      references RFR_GRUPOS (GRP_CODIGO);

create table RFR_GRPFOR  (
   GRF_CODGRP           VARCHAR(50)                     not null,
   GRF_CODFOR           INT8                            not null
);

alter table RFR_GRPFOR
   add constraint RFR_GRF_PK primary key (GRF_CODGRP, GRF_CODFOR);

alter table RFR_GRPFOR
   add constraint RFR_GRFFOR_FK foreign key (GRF_CODFOR)
      references RFR_FORMUL (FOR_CODI);

alter table RFR_GRPFOR
   add constraint RFR_GRFGRP_FK foreign key (GRF_CODGRP)
      references RFR_GRUPOS (GRP_CODIGO);
   