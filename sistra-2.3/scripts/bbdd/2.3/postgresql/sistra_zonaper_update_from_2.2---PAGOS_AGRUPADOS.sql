-- Tabla de pagos plugin caib
alter table ZPE_RPAGOS  add PAG_LOCPAS           VARCHAR(200);
alter table ZPE_RPAGOS  add PAG_TOKPAS           VARCHAR(200);
alter table ZPE_RPAGOS  add PAG_IDAGRU           VARCHAR(100);
alter table ZPE_RPAGOS  add PAG_DIFERI           VARCHAR(1) default 'N' not null;

comment on column ZPE_RPAGOS.PAG_LOCPAS is
'Localizador pasarela';

comment on column ZPE_RPAGOS.PAG_TOKPAS is
'Token acceso pasarela';

comment on column ZPE_RPAGOS.PAG_IDAGRU is
'Indica si es un pago agrupado';

comment on column ZPE_RPAGOS.PAG_DIFERI is
'Indica si es un pago diferido desde la zona personal';


update ZPE_RPAGOS set PAG_LOCPAS = PAG_IDENTP;
commit;