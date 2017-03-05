-- BANTEL: IDENTIFICADOR DE PROCEDIMIENTO EXTERNO
alter table BTE_PROAPL add TAP_AVIINC VARCHAR2(255);

comment on column BTE_PROAPL.TAP_AVIINC is
'Email para envío de incidencias';
