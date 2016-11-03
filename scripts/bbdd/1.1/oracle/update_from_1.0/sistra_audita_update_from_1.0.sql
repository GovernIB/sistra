
-- From version 1.0.3

-- Inserta evento: inicio pago telematico

UPDATE AUD_TIPOEV  SET   TIP_ORDEN  = 10
WHERE  TIP_TIPO = 'PAGO';

INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATEIN', 'CATALG', 'S', 'Iniciar pago telemático', 7, NULL, NULL, NULL, 'Inicio pago telemático'
, 'Inici pagament telemàtic');

-- To version 1.1.0

ALTER TABLE AUD_AUDIT MODIFY AUD_MODTRA VARCHAR2(20);

