alter table AUD_AUDIT  add   AUD_PROCED           VARCHAR2(100);
comment on column AUD_AUDIT.AUD_PROCED is 'PROCEDIMIENTO';

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