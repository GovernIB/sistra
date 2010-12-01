alter table STR_DOCUM  add DOC_ANEPDF CHAR(1) default 'N';

alter table STR_ESPNIV  add ETN_DSTTRA  bytea;

create table STR_USUTRA  (
   UST_CODUSU           VARCHAR(200)                    not null,
   UST_CODTRA           INT8                            not null
);

alter table STR_USUTRA
   add constraint STR_UST_PK primary key (UST_CODUSU, UST_CODTRA);

alter table STR_USUTRA
   add constraint STR_USTTRA_FK foreign key (UST_CODTRA)
      references STR_TRAMIT (TRA_CODIGO);

create table STR_GRUPOS  (
   GRP_CODIGO           VARCHAR(50)                     not null,
   GRP_NOMBRE           VARCHAR(100)                    not null,
   GRP_DESCP            VARCHAR(300)
);

alter table STR_GRUPOS
   add constraint STR_GRP_PK primary key (GRP_CODIGO);

create table STR_GRPUSU  (
   GRU_CODGRP           VARCHAR(50)                     not null,
   GRU_CODUSU           VARCHAR(200)                    not null
);

alter table STR_GRPUSU
   add constraint STR_GRU_PK primary key (GRU_CODGRP, GRU_CODUSU);

alter table STR_GRPUSU
   add constraint STR_GRUGRP_FK foreign key (GRU_CODGRP)
      references STR_GRUPOS (GRP_CODIGO);

create table STR_GRPTRA  (
   GRT_CODGRP           VARCHAR(50)                     not null,
   GRT_CODTRA           INT8                            not null
);

alter table STR_GRPTRA
   add constraint STR_GRT_PK primary key (GRT_CODGRP, GRT_CODTRA);

alter table STR_GRPTRA
   add constraint STR_GRTGRP_FK foreign key (GRT_CODGRP)
      references STR_GRUPOS (GRP_CODIGO);

alter table STR_GRPTRA
   add constraint STR_GRTTRA_FK foreign key (GRT_CODTRA)
      references STR_TRAMIT (TRA_CODIGO);
