alter table BTE_TRAMIT modify TRA_NIFRTE VARCHAR2(50);
alter table BTE_TRAMIT modify TRA_NIFRDO VARCHAR2(50);
alter table BTE_TRAMIT modify TRA_NIFDLG VARCHAR2(50);

create table BTE_TRAPRO  (
   PRO_IDEPRO           VARCHAR2(100)                      not null,
   PRO_CODIDI           VARCHAR2(2)                     not null,
   PRO_DESC             VARCHAR2(100)                   not null
);

comment on table BTE_TRAPRO is
'Traducción de Procedimientos';

comment on column BTE_TRAPRO.PRO_IDEPRO is
'Código trámite';

comment on column BTE_TRAPRO.PRO_CODIDI is
'Código idioma';

comment on column BTE_TRAPRO.PRO_DESC is
'Descripción del trámite';

alter table BTE_TRAPRO
   add constraint BTE_PRO_PK primary key (PRO_IDEPRO, PRO_CODIDI);
   
alter table BTE_TRAPRO
   add constraint STR_TPROIDI_FK foreign key (PRO_CODIDI)
      references BTE_IDIOMA (IDI_CODIGO);

alter table BTE_TRAPRO
   add constraint STR_TPROIDI_FK foreign key (PRO_IDEPRO)
      references BTE_PROAPL (TRA_IDEPRO);
   
create table BTE_IDIOMA  (
   IDI_CODIGO           VARCHAR2(2)                     not null,
   IDI_ORDEN            NUMBER(2)                       not null
);

comment on table BTE_IDIOMA is
'Tabla de idiomas';

alter table BTE_IDIOMA
   add constraint BTE_IDI_PK primary key (IDI_CODIGO);
 
/* IDIOMAS */
INSERT INTO BTE_IDIOMA ( IDI_CODIGO, IDI_ORDEN ) VALUES ( 
'es', 1); 
INSERT INTO BTE_IDIOMA ( IDI_CODIGO, IDI_ORDEN ) VALUES ( 
'ca', 2);  
   
INSERT INTO BTE_TRAPRO (PRO_IDEPRO, PRO_CODIDI, PRO_DESC)
SELECT TAP_IDEPRO, 'es', TAP_DESC FROM BTE_PROAPL;

INSERT INTO BTE_TRAPRO (PRO_IDEPRO, PRO_CODIDI, PRO_DESC)
SELECT TAP_IDEPRO, 'ca', TAP_DESC FROM BTE_PROAPL;

ALTER TABLE BTE_PROAPL DROP COLUMN TAP_DESC;


