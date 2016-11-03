-- IMPRIMIR SELLO PREREG CONFIRMADO
ALTER table ZPE_PREREG ADD PRE_OFIREG VARCHAR2(100);
comment on column ZPE_PREREG.PRE_OFIREG is 'Oficina registro en la que se ha confirmado';
ALTER table ZPE_PREBCK ADD PRB_OFIREG VARCHAR2(100);
comment on column ZPE_PREBCK.PRB_OFIREG is 'Oficina registro en la que se ha confirmado';
