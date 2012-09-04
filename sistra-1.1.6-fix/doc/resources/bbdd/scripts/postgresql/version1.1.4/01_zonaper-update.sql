CREATE SEQUENCE ZPE_SEQIND;

create table ZPE_INDELE  (
   IND_CODIGO           BIGINT                          not null,
   IND_NIF              VARCHAR(10)                    not null,
   IND_TIPEL            VARCHAR(1)                     not null,
   IND_CODEL            BIGINT                      not null,
   IND_DESCR            VARCHAR(1000)                  not null,
   IND_VALOR            VARCHAR(4000)
);

comment on table ZPE_INDELE is
'Indices de búsqueda asociadas a elementos de un expediente (solo para autenticados)';

comment on column ZPE_INDELE.IND_CODIGO is
'Codigo interno';

comment on column ZPE_INDELE.IND_NIF is
'Nif asociado';

comment on column ZPE_INDELE.IND_TIPEL is
'Tipo elemento: Expediente (E) / Tramite telematico (T) / Tramite preregistro (P) / Notificacion (N) / Aviso (A)';

comment on column ZPE_INDELE.IND_CODEL is
'Codigo elemento';

comment on column ZPE_INDELE.IND_DESCR is
'Descripcion campo';

comment on column ZPE_INDELE.IND_VALOR is
'Valor campo';

alter table ZPE_INDELE
   add constraint ZPE_INDELE_PK primary key (IND_CODIGO);

create index ZPE_INDELE_IDX on ZPE_INDELE (
   IND_NIF ASC
);

