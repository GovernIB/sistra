INSERT INTO AUD_MODUL ( MOD_MODUL, MOD_DESC, MOD_ORDEN, MOD_DESCCA ) VALUES ( 
'CATALG', 'Tramitación', 2, 'Tramitació'); 

INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATEAN', 'CATALG', 'S', 'Anular pago telemático', 8, NULL, NULL, 'Pagos telemáticos anulados'
, 'Pagament telemàtic anul·lat', 'Pagaments telemàtics anul·lats'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATECO', 'CATALG', 'S', 'Confirmar pago telemático', 9, NULL, NULL, 'Pagos telemáticos confirmados'
, 'Pagament telemàtic confirmat', 'Pagaments telemàtics confirmats'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'AZPER', 'CATALG', 'S', 'Acceso a la Zona Personal', 3, 'TING', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'Número total de Accesos al Buzón Personal. Este contador representa los accesos a la Zona Personal de Usuarios Autenticados.'
, 'Accés a la Zona Personal', 'Nombre total d''Accesos a la Bustia Personal. Aquest contador representa els accesos a la Zona Personal d''Usuaris Autenticats.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'INITRA', 'CATALG', 'S', 'Inicio de un trámite', 1, 'TINDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'Número total de inicios de un trámite. Este contador se incrementará siempre que se inicie cualquier tipo de trámite.'
, 'Inici d''un tràmit', 'Nombre total d''inicis d''un tràmit. Aquest contador s''incrementará sempre que s''inicie qualsevol tipus de tràmit.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'CARTRA', 'CATALG', 'S', 'Carga de un trámite', 2, 'TINDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'Representa el número total de trámites cargados. El sistema permite recuperar los datos de un trámite que no se ha terminando, pudiendo así, acabar la tramitación.'
, 'Càrrega d''un tràmit', 'Representa el nombre total de tràmits carregats. El sistema permet recuperar les dades d''un tràmit que no s''haja acabat, podent així, acabar la tramitació en qualsevol moment.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'DELTRA', 'CATALG', 'S', 'Borrado tramite', 3, 'TDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'Número total de trámite borrados. Este contador representa los trámites que se han borrado del sistema, es decir, una vez borrado no se tendrá acceso al él.'
, 'Esborrat d''un tràmit', 'Nombre total de tràmits esborrats. Aquest contador representa els tràmits que s''han esborrat del sistema, és a dir, un cop esborrat no es tindrà accés.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'ENVTRA', 'CATALG', 'S', 'Registro tramite', 4, 'TINDGX', 'es.caib.audita.persistence.util.evento.RegistroVisualizacionHandler'
, 'Número total trámites enviados. Este contador representa los trámites que se han terminado de forma totalmente telemática.'
, 'Registre tràmit', 'Nombre total de tràmits enviats. Aquest contador representa els tràmits que s''han acabat de forma totalment telemàtica.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'CONPRE', 'CATALG', 'S', 'Confirmacion prerregistro', 6, 'TDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleHandler'
, 'Número total de Preregistros confirmados. Este contador representa los trámites  de la zona de pre-registro que han sido confirmados con la presentación de la documentación necesaria en el Registro de Entrada presencial.'
, 'Confirmació prerregistre', 'Nombre total de Preregistres confirmats. Aquest contador representa els tràmits de la zona de pre-registre que han sigut confirmats amb la presentació de la documentació necessària en el Registre d''Entrada presencial.'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PRETRA', 'CATALG', 'S', 'Preregistro tramite', 5, 'TINDGX', 'es.caib.audita.persistence.util.evento.PreRegistroVisualizacionHandler'
, 'Número total de trámites con Preregistro terminados. Representa los trámites que se han almacenado en la zona de pre-registro. Para que el trámite se de por concluido tiene que confirmarse con la presentación de documentación'
, 'Preregistre tràmit', 'Nombre total de tràmits amb Preregistre acabats. Representa els tràmits que s''han emmagatzemat en la zona de pre-registre. Per a que el tràmit es done per acabat s''ha de confirmar amb la presentació de documentació'); 
INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PAGO', 'CATALG', 'S', 'Pago', 7, 'TINDGX', 'es.caib.audita.persistence.util.evento.PagosVisualizacionHandler'
, 'Número total de trámites que han realizado un Pago, tanto telemático como presencial.'
, 'Pagament', 'Nombre total de tràmits que han realitzat un Pagament, tant telemàtic com presencial'); 

UPDATE AUD_TIPOEV  SET   TIP_ORDEN  = 10
WHERE  TIP_TIPO = 'PAGO';

INSERT INTO AUD_TIPOEV ( TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS,
TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC ) VALUES ( 
'PATEIN', 'CATALG', 'S', 'Iniciar pago telemático', 7, NULL, NULL, NULL, 'Inicio pago telemático'
, 'Inici pagament telemàtic');


INSERT INTO AUD_TIPOEV (
   TIP_TIPO, TIP_MODUL, TIP_AUDIT, 
   TIP_DESC, TIP_ORDEN, TIP_PRPCLS, 
   TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, 
   TIP_AYUDAC) 
VALUES ( 'PATETA',
'CATALG',
'S',
'Pago telemático con tarjeta',
11,
null,
null,
'Pago telemático con tarjeta',
'Pagament telemàtic amb targeta',
'Pagament telemàtic amb targeta');

-- From 2.2.7 to 2.2.8
Insert into AUD_TIPOEV
   (TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS, TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC)
 Values
   ('COMUNI', 'CATALG', 'S', 'Comunicación', 12, 
    'TIDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleNotifComunHandler', 'Comunicación', 'Comunicació', 'Comunicació');
Insert into AUD_TIPOEV
   (TIP_TIPO, TIP_MODUL, TIP_AUDIT, TIP_DESC, TIP_ORDEN, TIP_PRPCLS, TIP_HANDLR, TIP_AYUDA, TIP_DESCCA, TIP_AYUDAC)
 Values
   ('NOTIFI', 'CATALG', 'S', 'Notificación', 13, 
    'TIDG', 'es.caib.audita.persistence.util.evento.CuadroMandoDetalleNotifComunHandler', 'Notificación', 'Notificació', 'Notificació');
COMMIT;
