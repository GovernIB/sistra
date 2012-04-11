alter table STR_DOCUM  add DOC_FORAJU VARCHAR2(1) default 'N';
comment on column STR_DOCUM.DOC_FORAJU is
'Para Formulario: se indica si el formulario debe anexarse al justificante';

alter table STR_DOCNIV modify DNV_FORFOR VARCHAR2(20 char);