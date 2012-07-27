INSERT INTO RDS_MODELO (
   MOD_CODIGO, 
   MOD_MODELO, 
   MOD_NOMBRE, 
   MOD_DESC, 
   MOD_ESTRUC, 
   MOD_CUSTOD) 
VALUES (
RDS_SEQMOD.NEXTVAL, 
'GE0013NOTIFEXT', 
'Modelo documento externo notificacion', 
'Usado para documentos externos de anexos de notificaciones y avisos en los que se indica una url al documento. En el redose se almacenara un xml con la url de acceso',
'S',
'N');

INSERT INTO RDS_VERS (
   VER_CODIGO, VER_CODMOD, VER_VERSIO, 
   VER_DESC) 
VALUES ( RDS_SEQVER.NEXTVAL,
 (SELECT MOD_CODIGO FROM RDS_MODELO WHERE MOD_MODELO = 'GE0013NOTIFEXT'),
 1,
 'VERSION 1');


COMMIT;

alter table RDS_USOS modify USO_REF VARCHAR2(100);