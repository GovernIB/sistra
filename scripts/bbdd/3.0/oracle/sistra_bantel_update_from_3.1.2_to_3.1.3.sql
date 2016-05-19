-- BANTEL: IDENTIFICADOR DE PROCEDIMIENTO EXTERNO
alter table BTE_PROAPL add TAP_IDEPEX VARCHAR2(20);

comment on column BTE_PROAPL.TAP_IDEPEX is
'Identificador del procedimiento administrativo externo';
