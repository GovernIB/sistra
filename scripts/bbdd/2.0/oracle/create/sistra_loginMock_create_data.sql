INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'admin', 'admin' , 'Usuario administrador' ,null );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'gestor', 'gestor' , 'Usuario gestor' ,null );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'registro', 'registro' , 'Usuario punto registro' ,null );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'helpdesk', 'helpdesk' , 'Usuario HelpDesk' ,null );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'auto', 'auto' , 'Usuario procesos automaticos' ,null );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'demo', 'demo' , 'Jose García García' ,'12345678Z' );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'delega', 'delega' , 'Administrador representaciones' ,null );

INSERT INTO SC_WL_USUARI (USU_CODI, USU_PASS, USU_NOM, USU_NIF) 
VALUES ( 'nobody', 'nobody' , 'Usuari No Autenticat' , '00000000T' );

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'admin', 'tothom');

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'admin', 'STR_ADMIN'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'gestor', 'tothom'); 
INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'gestor', 'STR_GESTOR'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'registro', 'tothom'); 
INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'registro', 'RWE_USUARI'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'helpdesk', 'tothom'); 
INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'helpdesk', 'STR_HELPDESK'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'auto', 'STR_AUTO'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'demo', 'tothom'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'nobody', 'tothom');

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'delega', 'STR_DELEGA'); 

INSERT INTO SC_WL_USUGRU ( UGR_CODUSU, UGR_CODGRU ) VALUES ( 
'delega', 'tothom'); 
