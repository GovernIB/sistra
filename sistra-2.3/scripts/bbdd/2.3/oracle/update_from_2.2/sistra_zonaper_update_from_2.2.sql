alter table ZPE_RPAGOS add PAG_FECINI           DATE;
alter table ZPE_RPAGOS add PAG_FECMAX           DATE;
alter table ZPE_RPAGOS add PAG_MSGMAX           VARCHAR2(4000);

comment on column ZPE_RPAGOS.PAG_FECINI is
'Fecha inicio sesion de pago';

comment on column ZPE_RPAGOS.PAG_FECMAX is
'Fecha limite para realizar el pago (nulo si no hay limite)';

comment on column ZPE_RPAGOS.PAG_MSGMAX is
'Mensaje a mostrar en caso de que se exceda el tiempo de pago';

