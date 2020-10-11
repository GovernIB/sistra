alter table ZPE_ELEEX  add    ELE_BANDEJA  BOOLEAN default FALSE not null;
comment on column ZPE_ELEEX.ELE_BANDEJA is 'Indica si destino bandeja para trámite telemático o preregistro';

update  ZPE_ELEEX set ELE_BANDEJA = true where ELE_TIPO = 'T' and ELE_CODELE in (select ENT_CODIGO from ZPE_ENTTEL where ENT_TIPO = 'B');
update  ZPE_ELEEX set ELE_BANDEJA = true where ELE_TIPO = 'P' and ELE_CODELE in (select PRE_CODIGO from ZPE_PREREG where PRE_TIPO = 'N');
commit;