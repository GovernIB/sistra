-- CONTROL SMS
ALTER table ZPE_TRAPER  ADD TPE_IDEPRO VARCHAR2(100);
comment on column ZPE_TRAPER.TPE_IDEPRO is 'ID PROCEDIMIENTO';

-- PLAZO VBLE NOTIF
alter table ZPE_NOTTEL add NOT_DIAPLZ           NUMBER(3);
comment on column ZPE_NOTTEL.NOT_DIAPLZ is 'En caso de que se establezca un plazo distinto a 10 dias';