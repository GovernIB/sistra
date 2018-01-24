alter table ZPE_TICKET add   TCK_NIFRPT           VARCHAR2(50);
alter table ZPE_TICKET add   TCK_NOMRPT           VARCHAR2(500);
alter table ZPE_TICKET add   TCK_AP1RPT           VARCHAR2(500);
alter table ZPE_TICKET add   TCK_AP2RPT           VARCHAR2(500);

comment on column ZPE_TICKET.TCK_NIFRPT is
'Nif representante';

comment on column ZPE_TICKET.TCK_NOMRPT is
'Nombre representante';

comment on column ZPE_TICKET.TCK_AP1RPT is
'Apellido 1 representante';

comment on column ZPE_TICKET.TCK_AP2RPT is
'Apellido 2  representante';

alter table  ZPE_TICKEX add 	TCX_NIFRPT           VARCHAR2(50);
alter table  ZPE_TICKEX add    	TCX_NOMRPT           VARCHAR2(500);
alter table  ZPE_TICKEX add 	TCX_AP1RPT           VARCHAR2(500);
alter table  ZPE_TICKEX add 	TCX_AP2RPT           VARCHAR2(500);

comment on column ZPE_TICKEX.TCX_NIFRPT is
'Nif representante';

comment on column ZPE_TICKEX.TCX_NOMRPT is
'Nombre representante';

comment on column ZPE_TICKEX.TCX_AP1RPT is
'Apellido 1 representante';

comment on column ZPE_TICKEX.TCX_AP2RPT is
'Apellido 2  representante';

