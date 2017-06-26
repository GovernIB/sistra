-- BANTEL: IDENTIFICADOR DE PROCEDIMIENTO EXTERNO
alter table BTE_PROAPL add TAP_AVIINC character varying(255);

comment on column BTE_PROAPL.TAP_AVIINC is
'Email para envio de incidencias';
