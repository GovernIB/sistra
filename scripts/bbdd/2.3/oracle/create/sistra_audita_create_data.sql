INSERT INTO AUD_MODUL ( MOD_MODUL, MOD_DESC, MOD_ORDEN, MOD_DESCCA ) VALUES ( 
'CATALG', 'Tramitaci�n', 2, 'Tramitaci�'); 

INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATEAN', 'CATALG', 'S', 'Anular pago telem�tico', 8, NULL, NULL, 'Pagos telem�ticos anulados'
, 'Pagament telem�tic anul�lat', 'Pagaments telem�tics anul�lats'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATECO', 'CATALG', 'S', 'Confirmar pago telem�tico', 9, NULL, NULL, 'Pagos telem�ticos confirmados'
, 'Pagament telem�tic confirmat', 'Pagaments telem�tics confirmats'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'AZPER', 'CATALG', 'S', 'Acceso a la Zona Personal', 3, 'TING', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'N�mero total de Accesos al Buz�n Personal. Este contador representa los accesos a la Zona Personal de Usuarios Autenticados.'
, 'Acc�s a la Zona Personal', 'Nombre total d''Accesos a la Bustia Personal. Aquest contador representa els accesos a la Zona Personal d''Usuaris Autenticats.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'INITRA', 'CATALG', 'S', 'Inicio de un tr�mite', 1, 'TINDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'N�mero total de inicios de un tr�mite. Este contador se incrementar� siempre que se inicie cualquier tipo de tr�mite.'
, 'Inici d''un tr�mit', 'Nombre total d''inicis d''un tr�mit. Aquest contador s''incrementar� sempre que s''inicie qualsevol tipus de tr�mit.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'CARTRA', 'CATALG', 'S', 'Carga de un tr�mite', 2, 'TINDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'Representa el n�mero total de tr�mites cargados. El sistema permite recuperar los datos de un tr�mite que no se ha terminando, pudiendo as�, acabar la tramitaci�n.'
, 'C�rrega d''un tr�mit', 'Representa el nombre total de tr�mits carregats. El sistema permet recuperar les dades d''un tr�mit que no s''haja acabat, podent aix�, acabar la tramitaci� en qualsevol moment.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'DELTRA', 'CATALG', 'S', 'Borrado tramite', 3, 'TDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'N�mero total de tr�mite borrados. Este contador representa los tr�mites que se han borrado del sistema, es decir, una vez borrado no se tendr� acceso al �l.'
, 'Esborrat d''un tr�mit', 'Nombre total de tr�mits esborrats. Aquest contador representa els tr�mits que s''han esborrat del sistema, �s a dir, un cop esborrat no es tindr� acc�s.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'ENVTRA', 'CATALG', 'S', 'Registro tramite', 4, 'TINDGX', 'es.caib.audita.persistence.util.evento.RegistroVisualizacionHandler'
, 'N�mero total tr�mites enviados. Este contador representa los tr�mites que se han terminado de forma totalmente telem�tica.'
, 'Registre tr�mit', 'Nombre total de tr�mits enviats. Aquest contador representa els tr�mits que s''han acabat de forma totalment telem�tica.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'CONPRE', 'CATALG', 'S', 'Confirmacion prerregistro', 6, 'TDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'N�mero total de Preregistros confirmados. Este contador representa los tr�mites  de la zona de pre-registro que han sido confirmados con la presentaci�n de la documentaci�n necesaria en el Registro de Entrada presencial.'
, 'Confirmaci� prerregistre', 'Nombre total de Preregistres confirmats. Aquest contador representa els tr�mits de la zona de pre-registre que han sigut confirmats amb la presentaci� de la documentaci� necess�ria en el Registre d''Entrada presencial.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PRETRA', 'CATALG', 'S', 'Preregistro tramite', 5, 'TINDGX', 'es.caib.audita.persistence.util.evento.PreRegistroVisualizacionHandler'
, 'N�mero total de tr�mites con Preregistro terminados. Representa los tr�mites que se han almacenado en la zona de pre-registro. Para que el tr�mite se de por concluido tiene que confirmarse con la presentaci�n de documentaci�n'
, 'Preregistre tr�mit', 'Nombre total de tr�mits amb Preregistre acabats. Representa els tr�mits que s''han emmagatzemat en la zona de pre-registre. Per a que el tr�mit es done per acabat s''ha de confirmar amb la presentaci� de documentaci�'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PAGO', 'CATALG', 'S', 'Pago', 7, 'TINDGX', 'es.caib.audita.persistence.util.evento.PagosVisualizacionHandler'
, 'N�mero total de tr�mites que han realizado un Pago, tanto telem�tico como presencial.'
, 'Pagament', 'Nombre total de tr�mits que han realitzat un Pagament, tant telem�tic com presencial'); 

UPDATE AUD_TIPOEV  SET   TIP_ORDEN  = 10
WHERE  TIP_TIPO = 'PAGO';

INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATEIN', 'CATALG', 'S', 'Iniciar pago telem�tico', 7, NULL, NULL, NULL, 'Inicio pago telem�tico'
, 'Inici pagament telem�tic');


INSERT INTO AUD_TIPOEV (
   TIP_TIPO, TIP_MODUL, TIP_AUDIT, 
   TIP_DESC, TIP_ORDEN, TIP_PRPCLS, 
   TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, 
   TIP_AYUDAC) 
VALUES ( 'PATETA',
'CATALG',
'S',
'Pago telem�tico con tarjeta',
11,
null,
null,
'Pago telem�tico con tarjeta',
'Pagament telem�tic amb targeta',
'Pagament telem�tic amb targeta');

COMMIT;

-- v2.2.8
Insert into AUD_TIPOEV
   (TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS, TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC)
 Values
   ('COMUNI', 'CATALG', 'S', 'Comunicaci�n', 12, 
    'TIDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleNotifComunHandler', 'Comunicaci�n', 'Comunicaci�', 'Comunicaci�');
Insert into AUD_TIPOEV
   (TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS, TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC)
 Values
   ('NOTIFI', 'CATALG', 'S', 'Notificaci�n', 13, 
    'TIDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleNotifComunHandler', 'Notificaci�n', 'Notificaci�', 'Notificaci�');
COMMIT;
