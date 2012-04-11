alter table STR_DOCUM  add DOC_FORAJU VARCHAR(1) default 'N';

comment on column STR_DOCUM.DOC_FORAJU is
'Para Formulario: se indica si el formulario debe anexarse al justificante';

alter table STR_DOCNIV alter column DNV_FORFOR type VARCHAR(20);