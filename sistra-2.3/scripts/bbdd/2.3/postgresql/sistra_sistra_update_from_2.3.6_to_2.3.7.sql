-- GUARDAR FORMULARIO
ALTER table STR_DOCNIV  ADD DNV_FORGUA VARCHAR(1) default 'N' not null;

comment on column STR_DOCNIV.DNV_FORGUA is 'Para Formulario: indica si se permite guardar sin terminar';