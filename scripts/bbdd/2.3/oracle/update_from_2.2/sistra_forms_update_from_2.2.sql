Insert into RFR_VERSIO
   (VER_CODIGO, VER_NOMBRE, VER_FECHA, VER_SUFIX)
 Values
   (2, 'VERSION 2: ACTUALIZACION FRONTAL (OCTUBRE 2013)', TO_DATE('10/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'V2');
COMMIT;

ALTER table RFR_COMPON ADD  COM_COLSPN           NUMBER(1)                      default 1 not null;
ALTER table RFR_COMPON ADD  COM_SINETQ           NUMBER(1)                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_CUADRO           NUMBER(1)                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_CUACAB           NUMBER(1)                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_ALINEACION       VARCHAR2(1)                    default 'I' not null;
ALTER table RFR_COMPON ADD  COM_SECLET           VARCHAR2(2);
ALTER table RFR_COMPON ADD  COM_TXTIPO           VARCHAR2(2);
   
create table RFR_TRASEC  (
   TRS_CODSEC           NUMBER(19)                      not null,
   TRS_CODIDI           VARCHAR2(2)                     not null,
   TRS_ETIQUE           VARCHAR2(4000)                  not null
);

comment on table RFR_TRASEC is
'TRADUCCION COMPONENTE SECCION';

alter table RFR_TRASEC
   add constraint RFR_TRS_PK primary key (TRS_CODSEC, TRS_CODIDI);

alter table RFR_TRASEC
   add constraint RFR_SECTRS_FK foreign key (TRS_CODSEC)
      references RFR_COMPON (COM_CODI);

      
      