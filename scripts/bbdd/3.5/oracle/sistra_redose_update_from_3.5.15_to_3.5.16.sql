Insert into RDS_MODELO (MOD_CODIGO,MOD_MODELO,MOD_NOMBRE,MOD_DESC,MOD_ESTRUC,MOD_CUSTOD)
    values (RDS_SEQMOD.nextval,'GE0015CONSREG','Documento consentimiento registro','Consentimiento expreso por parte del ciudadano para registrar la solicitud','N','N');

Insert into RDS_VERS (VER_CODIGO,VER_CODMOD,VER_VERSIO,VER_DESC)
    values (RDS_SEQVER.nextval,(SELECT MOD_CODIGO FROM RDS_MODELO WHERE MOD_MODELO = 'GE0015CONSREG'),'1','Versión 1');

COMMIT;
