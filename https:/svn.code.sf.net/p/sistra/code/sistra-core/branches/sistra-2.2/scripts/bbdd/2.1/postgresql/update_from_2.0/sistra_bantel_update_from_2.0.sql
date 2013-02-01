-- No s'ha provat !!!!!!

create table BTE_AVISOS  (
   AVI_IDENT            VARCHAR(50)                    not null,
   AVI_FCAVIS           TIMESTAMP                            not null
);

comment on table BTE_AVISOS is
'CONTIENE FECHAS ULTIMOS AVISOS';

comment on column BTE_AVISOS.AVI_IDENT is
'Identificador aviso (GESTOR / MONITORIZACION)';

comment on column BTE_AVISOS.AVI_FCAVIS is
'Fecha ultimo aviso';

alter table BTE_AVISOS
   add constraint BTE_AVI_PK primary key (AVI_IDENT);


ALTER table BTE_GESTOR ADD GES_AVIENT           VARCHAR(1)                    default 'N' not null;

comment on column BTE_GESTOR.GES_AVIENT is
'INDICA SI SE GENERA MENSAJE DE AVISO AL GESTOR PARA AVISAR NUEVAS ENTRADAS';

ALTER table BTE_GESTOR ADD GES_AVIMON           VARCHAR(1)                    default 'N' not null;

comment on column BTE_GESTOR.GES_AVIMON is
'INDICA SI SE GENERA AVISO DE MONITORIZACION AL GESTOR';


alter table BTE_GESTOR  add column    GES_AVINOT           VARCHAR(1)                    default 'N' not null;

comment on column BTE_GESTOR.GES_AVINOT is
'INDICA SI SE GENERA MENSAJE DE AVISO AL GESTOR PARA AVISAR ESTADO NOTIFICACIONES';


ALTER table BTE_PROAPL  ADD COLUMN    TAP_AVINOT           VARCHAR(1)                    default 'N' not null;

comment on column BTE_PROAPL.TAP_AVINOT is
'INDICA SI SE DEBEN AVISAR A LOS GESTORES DE LAS NOTIFICACIONES';


UPDATE BTE_GESTOR SET GES_AVIENT = 'S' WHERE GES_INFORM > 0;
COMMIT;

ALTER TABLE BTE_GESTOR DROP COLUMN GES_INFORM;

ALTER TABLE BTE_GESTOR DROP COLUMN GES_AVISO;

