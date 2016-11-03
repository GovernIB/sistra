alter table STR_DOCNIV  add  DNV_PAGPLG           VARCHAR2(50) default '.' NOT NULL;

comment on column STR_DOCNIV.DNV_PAGPLG is
	'Para Pagos: indica si se usa un plugin adicional de pagos. Si no usara el defecto.';