alter table MOB_MSEMAI  add
   MSE_ACKENV           int4                      default 0 not null;
alter table MOB_MSEMAI  add   
   MSE_ACKEST           VARCHAR(1);
alter table MOB_MSEMAI  add   
   MSE_ACKERR           VARCHAR(4000);

alter table MOB_MSSMS  add
   MSS_ACKENV           int4                      default 0 not null;
alter table MOB_MSSMS  add   
   MSS_ACKEST           VARCHAR(1);
alter table MOB_MSSMS  add   
   MSS_ACKERR           VARCHAR(4000);
   