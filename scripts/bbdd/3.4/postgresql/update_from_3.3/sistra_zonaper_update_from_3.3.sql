alter table ZPE_PERSON ALTER COLUMN PER_IDENTI TYPE character varying(50);

alter table ZPE_EXPEDI ALTER COLUMN EXP_NIFRTE TYPE character varying(50);
alter table ZPE_EXPEDI ALTER COLUMN EXP_NIFRDO TYPE character varying(50);

alter table ZPE_INDELE ALTER COLUMN IND_NIF TYPE character varying(50);

alter table ZPE_ENTTEL ALTER COLUMN ENT_NIFRTE TYPE character varying(50);
alter table ZPE_ENTTEL ALTER COLUMN ENT_NIFRDO TYPE character varying(50);
alter table ZPE_ENTTEL ALTER COLUMN ENT_NIFDLG TYPE character varying(50);


alter table ZPE_PREREG ALTER COLUMN PRE_NIFRTE TYPE character varying(50);
alter table ZPE_PREREG ALTER COLUMN PRE_NIFRDO TYPE character varying(50);
alter table ZPE_PREREG ALTER COLUMN PRE_NIFDLG TYPE character varying(50);

alter table ZPE_NOTTEL ALTER COLUMN NOT_NIFRTE TYPE character varying(50);
alter table ZPE_NOTTEL ALTER COLUMN NOT_NIFRDO TYPE character varying(50);

alter table ZPE_PREBCK ALTER COLUMN PRB_NIFRTE TYPE character varying(50);
alter table ZPE_PREBCK ALTER COLUMN PRB_NIFRDO TYPE character varying(50);
alter table ZPE_PREBCK ALTER COLUMN PRB_NIFDLG TYPE character varying(50);

alter table ZPE_RPAGOS ALTER COLUMN PAG_NIFDEC TYPE character varying(50);

alter table ZPE_RPGTPV ALTER COLUMN TPV_NIFDEC TYPE character varying(50);

alter table ZPE_TICKET ALTER COLUMN TCK_NIF TYPE character varying(50);

alter table ZPE_TCKCRT ALTER COLUMN TKC_NIF TYPE character varying(50);

alter table ZPE_TICKEX ALTER COLUMN TCX_NIF TYPE character varying(50);
