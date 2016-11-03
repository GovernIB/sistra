create table ZPE_RGPEXT  (
   RGP_RDSCOD           int8                      not null,
   RGP_RDSCLA           varchar(10)               not null,
   RGP_RDSANE           varchar(4000),
   RGP_IDEPER           varchar(50)               not null,
   RGP_FECINI           timestamp                 not null,
   RGP_FECFIN           timestamp                 not null,
   primary key (RGP_RDSCOD)
);