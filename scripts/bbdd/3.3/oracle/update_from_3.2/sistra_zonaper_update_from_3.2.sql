drop index ZPE_NOTNRG_UNI; 

alter table ZPE_REGLOG  add RLG_IDEENT  VARCHAR2(100);

alter table ZPE_REGLOG drop constraint ZPE_RLG_PK; 
drop index ZPE_RLG_PK; 


-- HAY QUE ACTUALIZAR CON EL CODIGO DE ENTIDAD CORRESPONDIENTE
UPDATE ZPE_REGLOG SET RLG_IDEENT = 'XXXX';
COMMIT;


alter table ZPE_REGLOG  modify RLG_IDEENT not null;

alter table ZPE_REGLOG add constraint ZPE_RLG_PK primary key (RLG_TIPREG, RLG_IDEENT, RLG_NUMREG);
   