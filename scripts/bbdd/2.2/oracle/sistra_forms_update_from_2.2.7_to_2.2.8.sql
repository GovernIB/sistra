Insert into FORMROL.RFR_VERSIO
   (VER_CODIGO, VER_NOMBRE, VER_FECHA, VER_SUFIX)
 Values
   (2, 'VERSION 2: ACTUALIZACION FRONTAL (OCTUBRE 2013)', TO_DATE('10/01/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'V2');
COMMIT;

ALTER table RFR_COMPON ADD  COM_COLSPN           NUMBER(1)                      default 1 not null;
ALTER table RFR_COMPON ADD  COM_SINETQ           NUMBER(1)                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_CUADRO           NUMBER(1)                      default 0 not null;
ALTER table RFR_COMPON ADD  COM_ALINEACION       VARCHAR2(1)                    default 'I' not null;