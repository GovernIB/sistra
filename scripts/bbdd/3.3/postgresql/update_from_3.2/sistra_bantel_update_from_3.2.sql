--- ES NECESARIO HACER UN INSERT CON EL CODIGO DE ENTIDAD DEPENDIENDO INSTALACION!!!
--- HAY QUE MODIFICAR EL INSERT/UPDATE QUE ESTA ABAJO SEGUN INSTALACION

alter table BTE_PROAPL add TAP_IDEENT character varying(100);
comment on column BTE_PROAPL.TAP_IDEENT is 'IDENTIFICADOR ENTRADA';


alter table BTE_TRAMIT drop constraint BTE_NUMPRE_UNI;

create table BTE_ENTIDAD  (
   ENT_IDEENT           character varying(100)                   not null,
   ENT_NOMBRE           character varying(200)                   not null
);

comment on table BTE_ENTIDAD is
'ENTIDAD';

comment on column BTE_ENTIDAD.ENT_IDEENT is
'IDENTIFICADOR';

comment on column BTE_ENTIDAD.ENT_NOMBRE is
'NOMBRE';

alter table BTE_ENTIDAD
   add constraint BTE_ENT_PK primary key (ENT_IDEENT);

alter table BTE_PROAPL
   add constraint BTE_TAPENT_FK foreign key (TAP_IDEENT)
      references BTE_ENTIDAD (ENT_IDEENT);



-- MODIFICAR SEGUN ENTIDAD
insert into BTE_ENTIDAD (ENT_IDEENT, ENT_NOMBRE) values ('XXXX','NOMBRE ENTIDAD');
update BTE_PROAPL set TAP_IDEENT = 'XXXX';
commit;
-- MODIFICAR SEGUN ENTIDAD


alter table BTE_PROAPL modify TAP_IDEENT character varying(100) not null;





