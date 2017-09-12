alter table ZPE_PERSON modify PER_IDENTI VARCHAR2(50);

alter table ZPE_EXPEDI modify EXP_NIFRTE VARCHAR2(50);
alter table ZPE_EXPEDI modify EXP_NIFRDO VARCHAR2(50);

alter table ZPE_INDELE modify IND_NIF VARCHAR2(50);

alter table ZPE_ENTTEL modify ENT_NIFRTE VARCHAR2(50);
alter table ZPE_ENTTEL modify ENT_NIFRDO VARCHAR2(50);
alter table ZPE_ENTTEL modify ENT_NIFDLG VARCHAR2(50);


alter table ZPE_PREREG modify PRE_NIFRTE VARCHAR2(50);
alter table ZPE_PREREG modify PRE_NIFRDO VARCHAR2(50);
alter table ZPE_PREREG modify PRE_NIFDLG VARCHAR2(50);

alter table ZPE_NOTTEL modify NOT_NIFRTE VARCHAR2(50);
alter table ZPE_NOTTEL modify NOT_NIFRDO VARCHAR2(50);

alter table ZPE_PREBCK modify PRB_NIFRTE VARCHAR2(50);
alter table ZPE_PREBCK modify PRB_NIFRDO VARCHAR2(50);
alter table ZPE_PREBCK modify PRB_NIFDLG VARCHAR2(50);

alter table ZPE_RPAGOS modify PAG_NIFDEC VARCHAR2(50);

alter table ZPE_RPGTPV modify TPV_NIFDEC VARCHAR2(50);

alter table ZPE_TICKET modify TCK_NIF VARCHAR2(50);

alter table ZPE_TCKCRT modify TKC_NIF VARCHAR2(50);

alter table ZPE_TICKEX modify TCX_NIF VARCHAR2(50);
