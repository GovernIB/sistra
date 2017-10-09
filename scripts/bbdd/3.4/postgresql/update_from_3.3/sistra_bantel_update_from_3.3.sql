alter table BTE_TRAMIT ALTER COLUMN TRA_NIFRTE TYPE character varying(50);
alter table BTE_TRAMIT ALTER COLUMN TRA_NIFRDO TYPE character varying(50);
alter table BTE_TRAMIT ALTER COLUMN TRA_NIFDLG TYPE character varying(50);

create table BTE_IDIOMA  (
   IDI_CODIGO           character varying(2)                     not null,
   IDI_ORDEN            bigint                       not null
);

comment on table BTE_IDIOMA is
'Tabla de idiomas';

ALTER TABLE ONLY BTE_IDIOMA
   add constraint BTE_IDI_PK primary key (IDI_CODIGO);

create table BTE_TRAPRO  (
   PRO_IDEPRO           character varying(100)                      not null,
   PRO_CODIDI           character varying(2)                     not null,
   PRO_DESC             character varying(1000)                   not null
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
   add constraint BTE_TPROIDI_FK foreign key (PRO_CODIDI)
      references BTE_IDIOMA (IDI_CODIGO);

alter table BTE_TRAPRO
   add constraint BTE_PROTAP_FK foreign key (PRO_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);

 
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


