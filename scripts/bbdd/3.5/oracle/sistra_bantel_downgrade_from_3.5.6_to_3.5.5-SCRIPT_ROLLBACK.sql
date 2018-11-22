/* Añadir PK */
alter table BTE_PROAPL drop constraint BTE_TAP_PK cascade;
drop index BTE_TAP_AK;
alter table BTE_PROAPL add constraint BTE_TAP_PK primary key (TAP_IDEPRO);
create unique index BTE_TAP_PK on BTE_PROAPL (TAP_IDEPRO ASC);

/* BTE_TRAPRO */
alter table BTE_TRAPRO drop constraint BTE_PRO_PK;
update BTE_TRAPRO set PRO_IDEPRO = (select TAP_IDEPRO from BTE_PROAPL where TAP_CODIGO = PRO_CODPRO) where PRO_IDEPRO is null;
commit;
alter table BTE_TRAPRO modify PRO_IDEPRO not null;
alter table BTE_TRAPRO add constraint BTE_PRO_PK primary key (PRO_IDEPRO, PRO_CODIDI);
alter table BTE_TRAPRO add constraint BTE_TPROIDI_FK foreign key (PRO_CODIDI) references BTE_IDIOMA (IDI_CODIGO);

/* BTE_FUEDAT */
update BTE_FUEDAT set FUE_IDEPRO = (select TAP_IDEPRO from BTE_PROAPL where TAP_CODIGO = FUE_CODPRO) where FUE_IDEPRO is null;
commit;
alter table BTE_FUEDAT modify FUE_IDEPRO not null;
alter table BTE_FUEDAT add constraint BTE_FUETAP_FK foreign key (FUE_IDEPRO) references BTE_PROAPL (TAP_IDEPRO);
drop index BTE_FUEIDP_I;
create index BTE_FUEIDP_I on BTE_FUEDAT (
   FUE_IDEPRO ASC
);

/* BTE_TRAMIT  */
update BTE_TRAMIT set TRA_IDEPRO = (select TAP_IDEPRO from BTE_PROAPL where TAP_CODIGO = TRA_CODPRO) where TRA_IDEPRO is null;
commit;
alter table BTE_TRAMIT modify TRA_IDEPRO not null;
alter table BTE_TRAMIT add constraint BTE_TRATAP_FK foreign key (TRA_IDEPRO) references BTE_PROAPL (TAP_IDEPRO);
drop index BTE_TRATAP_FK_I;
create index BTE_TRATAP_FK_I on BTE_TRAMIT (
   TRA_IDEPRO ASC
);

/* BTE_GESPRO */
alter table BTE_GESPRO drop constraint BTE_GAP_PK;
update BTE_GESPRO set GAP_IDEPRO = (select TAP_IDEPRO from BTE_PROAPL where TAP_CODIGO = GAP_CODPRO) where GAP_IDEPRO is null;
commit;
alter table BTE_GESPRO add constraint BTE_GAP_PK primary key (GAP_CODGES, GAP_IDEPRO);
alter table BTE_GESPRO modify GAP_IDEPRO not null;
alter table BTE_GESPRO add constraint BTE_GAPTAP_FK foreign key (GAP_IDEPRO) references BTE_PROAPL (TAP_IDEPRO);

/* Borrar TAP_CODIGO */
alter table BTE_FUEDAT drop column FUE_CODPRO;
alter table BTE_PROAPL drop column TAP_CODIGO;
alter table BTE_GESPRO drop column GAP_CODPRO;
alter table BTE_TRAMIT drop column TRA_CODPRO;
alter table BTE_TRAPRO drop column PRO_CODPRO;

drop sequence BTE_SEQPRO;