drop index BTE_TRATAP_FK_I;

create index BTE_TRATRA_I on BTE_TRAMIT (
   TRA_IDETRA ASC
);

create index BTE_TRATAP_FK_I on BTE_TRAMIT (
   TRA_IDEPRO ASC
);