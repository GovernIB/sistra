alter table BTE_FICEXP drop constraint BTE_FICTAP_FK; 

alter table BTE_TRAMIT drop constraint BTE_TRATAP_FK ;

alter table BTE_GESTRA drop constraint BTE_GAPTAP_FK;

alter table BTE_TRAAPL RENAME TO BTE_PROAPL;

alter table BTE_PROAPL rename column TAP_IDETRA to TAP_IDEPRO;

alter table BTE_PROAPL alter column TAP_IDEPRO type VARCHAR(100);

alter table BTE_FICEXP add FIC_NOMFIC VARCHAR(500);

update BTE_FICEXP set FIC_NOMFIC = (SELECT TAP_NOMFIC FROM BTE_PROAPL WHERE TAP_IDEPRO = FIC_IDETRA);


alter table BTE_PROAPL DROP COLUMN TAP_NOMFIC;

alter table BTE_GESTRA rename column GAP_IDETRA to GAP_IDEPRO;

alter table BTE_GESTRA alter column GAP_IDEPRO type VARCHAR(100);

alter table BTE_GESTRA RENAME TO BTE_GESPRO;

ALTER TABLE BTE_TRAMIT ADD TRA_IDEPRO VARCHAR(100);

UPDATE BTE_TRAMIT SET TRA_IDEPRO = TRA_IDETRA;


ALTER TABLE BTE_TRAMIT alter column TRA_IDEPRO set NOT NULL;

alter table BTE_TRAMIT add constraint BTE_TRATAP_FK foreign key (TRA_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);

alter table BTE_GESPRO add constraint BTE_GAPTAP_FK foreign key (GAP_IDEPRO)
      references BTE_PROAPL (TAP_IDEPRO);    
      
create table BTE_ARCFEX  (
   AFE_IDEFIC           VARCHAR(20)                    not null,
   AFE_DATOS            BYTEA                          not null
);

comment on table BTE_ARCFEX is
'Archivo fichero exportacion';

comment on column BTE_ARCFEX.AFE_IDEFIC is
'IDENTIFICADOR DEL TRÁMITE';

comment on column BTE_ARCFEX.AFE_DATOS is
'DATOS FICHERO';

alter table BTE_ARCFEX
   add constraint BTE_AFE_PK primary key (AFE_IDEFIC);

alter table BTE_ARCFEX
   add constraint BTE_AFEFIC_FK foreign key (AFE_IDEFIC)
      references BTE_FICEXP (FIC_IDETRA);      

INSERT INTO BTE_ARCFEX 
	SELECT BTE_FICEXP.FIC_IDETRA, BTE_FICEXP.FIC_DATOS
		FROM BTE_FICEXP;

	

alter table BTE_FICEXP drop column FIC_DATOS;

alter table BTE_TRAMIT  add  TRA_INIPRO           DATE; 

comment on column BTE_TRAMIT.TRA_INIPRO is
'INDICA FECHA DE INICIO DE PROCESO. SE REINICIARA CADA VEZ QUE PASE A ESTADO NO PROCESADA';

update  BTE_TRAMIT  set TRA_INIPRO = TRA_FECHA;


alter table BTE_TRAMIT  alter column TRA_INIPRO set not null;

--

ALTER TABLE BTE_PROAPL ADD TAP_UNIADM BIGINT;

UPDATE BTE_PROAPL
SET  TAP_UNIADM = ( 
		    SELECT  TR.TRA_UNIADM  
				FROM BTE_TRAMIT TR
				WHERE TR.TRA_CODIGO =  
					( SELECT MAX(T.TRA_CODIGO)
					FROM BTE_TRAMIT T
					WHERE T.TRA_IDEPRO = TAP_IDEPRO
					GROUP BY T.TRA_IDEPRO)
			);
		

ALTER TABLE BTE_PROAPL ADD  TAP_SMS              VARCHAR(1)                    default 'N' not null;

comment on column BTE_PROAPL.TAP_UNIADM is
'UNIDAD ADMINISTRATIVA';

comment on column BTE_PROAPL.TAP_SMS is
'INDICA SI SE PERMITE INDICAR EL SMS EN LOS AVISOS DE EXPEDIENTE';