ALTER table ZPE_ELEEX  ADD   ELE_CODAVI           VARCHAR2(50);


comment on column ZPE_ELEEX.ELE_CODAVI is
'Codigo aviso movilidad asociado al elemento';

ALTER table ZPE_NOTTEL  ADD  NOT_FECPLZ           DATE;

comment on column ZPE_NOTTEL.NOT_FECPLZ is
'Indica fecha de plazo de fin de apertura de la notificación (en caso de que se controle fecha entrega)';

ALTER table ZPE_NOTTEL  ADD  NOT_RECHAZ          NUMBER(1);

comment on column ZPE_NOTTEL.NOT_RECHAZ is
'Indica que la notificacion esta rechazada (en caso de que se controle fecha entrega)';

UPDATE ZPE_NOTTEL SET    NOT_RECHAZ = 0;
COMMIT;