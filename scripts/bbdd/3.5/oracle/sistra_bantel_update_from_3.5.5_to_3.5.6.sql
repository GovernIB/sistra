/* FKs */
alter table BTE_TRAPRO drop constraint BTE_TPROIDI_FK;
alter table BTE_FUEDAT drop constraint BTE_FUETAP_FK;
alter table BTE_TRAMIT drop constraint BTE_TRATAP_FK;
alter table BTE_GESPRO drop constraint BTE_GAPTAP_FK;

/* Quitar PK */
alter table BTE_PROAPL drop constraint BTE_TAP_PK cascade;
drop index BTE_TAP_PK;

/* Add AK */
create unique index BTE_TAP_AK on BTE_PROAPL (TAP_IDEPRO ASC);

/* Add nuevo codigo */
alter table BTE_PROAPL add TAP_CODIGO  NUMBER(20);

/* Crear secuencia */
create sequence BTE_SEQPRO;

/* Establecer PKs */
update BTE_PROAPL set TAP_CODIGO = BTE_SEQPRO.nextval;
commit;
alter table BTE_PROAPL modify TAP_CODIGO not null;
alter table BTE_PROAPL add constraint BTE_TAP_PK primary key (TAP_CODIGO);

/* BTE_TRAPRO */
alter table BTE_TRAPRO drop constraint BTE_PRO_PK;
alter table BTE_TRAPRO add  PRO_CODPRO  NUMBER(20);
comment on column BTE_TRAPRO.PRO_CODPRO is 'Codigo procedimiento';
update BTE_TRAPRO set PRO_CODPRO = (select TAP_CODIGO from BTE_PROAPL where TAP_IDEPRO = PRO_IDEPRO);
commit;
alter table BTE_TRAPRO modify PRO_CODPRO not null;
alter table BTE_TRAPRO add constraint BTE_PRO_PK primary key (PRO_CODPRO, PRO_CODIDI);
alter table BTE_TRAPRO add constraint BTE_TPROTAP_FK foreign key (PRO_CODPRO) references BTE_PROAPL (TAP_CODIGO);

/* BTE_FUEDAT */
alter table BTE_FUEDAT add  FUE_CODPRO NUMBER(20);
comment on column BTE_FUEDAT.FUE_CODPRO is 'Codigo procedimiento';
update BTE_FUEDAT set FUE_CODPRO = (select TAP_CODIGO from BTE_PROAPL where TAP_IDEPRO = FUE_IDEPRO);
commit;
alter table BTE_FUEDAT modify  FUE_CODPRO not null;
alter table BTE_FUEDAT add constraint BTE_FUETAP_FK foreign key (FUE_CODPRO) references BTE_PROAPL (TAP_CODIGO);
drop index BTE_FUEIDP_I;
create index BTE_FUEIDP_I on BTE_FUEDAT (FUE_CODPRO ASC);

/* BTE_TRAMIT  */
alter table BTE_TRAMIT add TRA_CODPRO  NUMBER(20);
comment on column BTE_TRAMIT.TRA_CODPRO is 'CODIGO PROCEDIMIENTO';
update BTE_TRAMIT set TRA_CODPRO = (select TAP_CODIGO from BTE_PROAPL where TAP_IDEPRO = TRA_IDEPRO);
commit;
alter table BTE_TRAMIT modify TRA_CODPRO  not null;
alter table BTE_TRAMIT add constraint BTE_TRATAP_FK foreign key (TRA_CODPRO) references BTE_PROAPL (TAP_CODIGO);
drop index BTE_TRATAP_FK_I;
create index BTE_TRATAP_FK_I on BTE_TRAMIT (TRA_CODPRO ASC);

/* BTE_GESPRO */
alter table BTE_GESPRO drop constraint BTE_GAP_PK;
drop index BTE_GAP_PK;
alter table BTE_GESPRO add  GAP_CODPRO NUMBER(20);
comment on column BTE_GESPRO.GAP_CODPRO is 'CODIGO DEL PROCEDIMIENTO';
update BTE_GESPRO set GAP_CODPRO = (select TAP_CODIGO from BTE_PROAPL where TAP_IDEPRO = GAP_IDEPRO);
commit;
alter table BTE_GESPRO modify GAP_CODPRO not null;
alter table BTE_GESPRO add constraint BTE_GAP_PK primary key (GAP_CODPRO, GAP_CODGES);
alter table BTE_GESPRO add constraint BTE_GAPTAP_FK foreign key (GAP_CODPRO) references BTE_PROAPL (TAP_CODIGO);

/* Borrar IDEPRO (tras revisar) */
alter table BTE_TRAPRO drop column PRO_IDEPRO;
alter table BTE_FUEDAT drop column FUE_IDEPRO;
alter table BTE_TRAMIT drop column TRA_IDEPRO;
alter table BTE_GESPRO drop column GAP_IDEPRO;