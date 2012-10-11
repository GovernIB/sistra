create table ZPE_RGPEXT  (
   RGP_RDSCOD           NUMBER(20)                      not null,
   RGP_RDSCLA           VARCHAR2(10)                    not null,
   RGP_RDSANE           VARCHAR2(4000),
   RGP_IDEPER           VARCHAR2(50)                    not null,
   RGP_FECINI           DATE                            not null,
   RGP_FECFIN           DATE                            not null
);

comment on table ZPE_RGPEXT is
'Log de preparación de asientos para firmar y registrar con posterioridad (para registros de entrada externos, es decir no realizados por Sistra)';

comment on column ZPE_RGPEXT.RGP_RDSCOD is
'Código RDS del asiento';

comment on column ZPE_RGPEXT.RGP_RDSCLA is
'Clave RDS del asiento';

comment on column ZPE_RGPEXT.RGP_RDSANE is
'Lista de referencias de los anexos serializada en un string';

comment on column ZPE_RGPEXT.RGP_IDEPER is
'Identicador ';

comment on column ZPE_RGPEXT.RGP_FECINI is
'Fecha en la que se ha preparado el asiento';

comment on column ZPE_RGPEXT.RGP_FECFIN is
'Fecha en la que se si no es registrado el asiento se eliminará';

alter table ZPE_RGPEXT
   add constraint ZPE_RGP_PK primary key (RGP_RDSCOD);
