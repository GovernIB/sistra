alter table RDS_DOCUM ADD DOC_DOCFOR  bigint;
alter table RDS_DOCUM ADD DOC_ESFORM  VARCHAR2(1) default 'N' not null;

alter table RDS_DOCUM add constraint RDS_DOCDOC_FK foreign key (DOC_DOCFOR) references RDS_DOCUM (DOC_CODIGO);

drop index RDS_DOC_AK;
