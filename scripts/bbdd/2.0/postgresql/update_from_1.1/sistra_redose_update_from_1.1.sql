alter table RDS_UBICA  alter column UBI_PLUGIN type VARCHAR(500);
alter table RDS_UBICA  add  UBI_DEFECT VARCHAR(1) default 'N' not null;

comment on column RDS_UBICA.UBI_DEFECT is
'INDICA SI ES LA UBICACION POR DEFECTO (S/N)';

create table RDS_FICEXT  (
   FIE_REFDOC           VARCHAR(500)                   not null,
   FIE_REFFEC           TIMESTAMP                           not null,
   FIE_CODDOC           BIGINT                      not null,
   FIE_BORRAR           VARCHAR(1)                    default 'N' not null
);

comment on table RDS_FICEXT is
'UBICACIÓN EN REPOSITORIO DE FICHEROS EXTERNOS (PLUGIN UBICACION NO DEFECTO)';

comment on column RDS_FICEXT.FIE_REFDOC is
'REFERENCIA EXTERNA';

comment on column RDS_FICEXT.FIE_REFFEC is
'FECHA REFERENCIA (SOLO SERA VALIDA LA ULTIMA, EL RESTO SE BORRARAN)';

comment on column RDS_FICEXT.FIE_CODDOC is
'CODIGO DOCUMENTO';

comment on column RDS_FICEXT.FIE_BORRAR is
'INDICA SI ESTA MARCADO PARA BORRAR (S/N)';

alter table RDS_FICEXT
   add constraint RDS_FIE_PK primary key (FIE_REFDOC);

create index RDS_FIEDOC_FK_I on RDS_FICEXT (
   FIE_CODDOC ASC
);


insert into RDS_UBICA (UBI_CODIGO, UBI_IDENT, UBI_NOMBRE, UBI_PLUGIN, UBI_DEFECT)  
 values (NEXTVAL('RDS_SEQUBI'), 'FILE', 'Almacenamiento externo en ficheros', 
 'es.caib.redose.persistence.plugin.PluginAlmacenamientoFileSystem','N');
 
 update RDS_UBICA set  UBI_DEFECT = 'S' where UBI_IDENT = 'RDS';
 commit;