Insert into RFR_VERSIO
   (VER_CODIGO, VER_NOMBRE, VER_FECHA, VER_SUFIX)
 Values
   (2, 'VERSION 2: ACTUALIZACION FRONTAL (OCTUBRE 2013)', '2013-01-10', 'V2');
COMMIT;

ALTER table RFR_COMPON ADD  COM_COLSPN           BIGINT                      default 1 not null;
ALTER table RFR_COMPON ADD  COM_SINETQ           BIGINT                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_CUADRO           BIGINT                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_CUACAB           BIGINT                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_ALINEACION       VARCHAR(1)                    default 'I' not null;
ALTER table RFR_COMPON ADD  COM_SECLET           VARCHAR(2);
ALTER table RFR_COMPON ADD  COM_TXTIPO           VARCHAR(2);
   
create table RFR_TRASEC  (
   TRS_CODSEC           BIGINT                      not null,
   TRS_CODIDI           VARCHAR(2)                     not null,
   TRS_ETIQUE           VARCHAR(4000)                  not null
);

comment on table RFR_TRASEC is
'TRADUCCION COMPONENTE SECCION';

alter table RFR_TRASEC
   add constraint RFR_TRS_PK primary key (TRS_CODSEC, TRS_CODIDI);

alter table RFR_TRASEC
   add constraint RFR_SECTRS_FK foreign key (TRS_CODSEC)
      references RFR_COMPON (COM_CODI);

      
      