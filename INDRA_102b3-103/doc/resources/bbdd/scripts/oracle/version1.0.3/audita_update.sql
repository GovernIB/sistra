-- Inserta evento: inicio pago telematico

UPDATE AUD_TIPOEV  SET   TIP_ORDEN  = 10
WHERE  TIP_TIPO = 'PAGO';

INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATEIN', 'CATALG', 'S', 'Iniciar pago telem�tico', 7, NULL, NULL, NULL, 'Inicio pago telem�tico'
, 'Inici pagament telem�tic');

COMMIT;
