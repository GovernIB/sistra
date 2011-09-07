ALTER TABLE RDS_MODELO
   ADD MOD_CUSTOD CHAR(1) default 'N' not null;

create table RDS_VERCUS  (
   CUS_CODIGO           VARCHAR(100)                    not null,
   CUS_CODDOC           INT8                            not null,
   CUS_FECHA            TIMESTAMP                       not null,
   CUS_BORRAR           CHAR(1)                      not null
);

alter table RDS_VERCUS
   add constraint RDS_CUS_PK primary key (CUS_CODIGO);

alter table RDS_VERCUS
   add constraint RDS_CUSDOC_FK foreign key (CUS_CODDOC)
      references RDS_DOCUM (DOC_CODIGO);

-- Noves dades inicials

INSERT INTO RDS_MODELO (MOD_CODIGO, MOD_MODELO, MOD_NOMBRE,MOD_DESC, MOD_ESTRUC) 
VALUES ( nextval('RDS_SEQMOD'), 'GE0011NOTIFICA', 'DOCUMENTO DE NOTIFICACION', 'DOCUMENTO ASOCIADO A UN REGISTRO DE SALIDA' , 'N');

INSERT INTO RDS_VERS ( VER_CODIGO, VER_CODMOD, VER_VERSIO, VER_DESC) 
VALUES ( nextval('RDS_SEQVER'), (SELECT MOD_CODIGO FROM RDS_MODELO WHERE MOD_MODELO='GE0011NOTIFICA'),1,'NOTIFICACION');
