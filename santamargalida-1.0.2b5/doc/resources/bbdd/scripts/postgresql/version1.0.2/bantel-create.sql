create table BTE_TRAAPL (
   TAP_IDETRA varchar(255) not null,
   TAP_DESC varchar(100) not null,
   TAP_INMED char(1),
   TAP_INFORM int8,
   TAP_TIPACC char(1) not null,
   TAP_URL varchar(200),
   TAP_EJBREM char(1) not null,
   TAP_EJBAUT char(1) not null,
   TAP_JNDI varchar(100),
   TAP_USR varchar(200),
   TAP_PWD varchar(50),
   TAP_ROL varchar(100),
   TAP_AVISO timestamp,
   TAP_NOMFIC varchar(255),
   primary key (TAP_IDETRA)
);
create table BTE_GESTOR (
   GES_SEYCON varchar(255) not null,
   GES_EMAIL varchar(500) not null,
   GES_INFORM int8,
   GES_AVISO timestamp,
   GES_UPDEST char(1),
   GES_UPDESM char(1),
   primary key (GES_SEYCON)
);
create table BTE_FICEXP (
   FIC_IDETRA varchar(255) not null,
   FIC_DATOS bytea not null,
   primary key (FIC_IDETRA)
);
create table BTE_DOCUM (
   DOC_CODIGO int8 not null,
   DOC_CODTRA int8 not null,
   DOC_PRESE varchar(1) not null,
   DOC_DESC varchar(500) not null,
   DOC_DOCIDE varchar(5),
   DOC_DOCNUM int4,
   DOC_RDSCOD int8,
   DOC_RDSCLA varchar(10),
   DOC_TIPDOC varchar(1),
   DOC_COMPUL varchar(1),
   DOC_FOTPIA varchar(1),
   DOC_FIRMA varchar(1),
   primary key (DOC_CODIGO)
);
create table BTE_TRAMIT (
   TRA_CODIGO int8 not null,
   TRA_NUMENT varchar(50),
   TRA_CLAVE varchar(50),
   TRA_FECHA timestamp not null,
   TRA_TIPO char(1) not null,
   TRA_PROCES char(1) not null,
   TRA_IDETRA varchar(255) not null,
   TRA_VERTRA int4 not null,
   TRA_UNIADM int8 not null,
   TRA_NIVAUT char(1) not null,
   TRA_DESTRA varchar(500) not null,
   TRA_CODASI int8 not null,
   TRA_CLAASI varchar(10) not null,
   TRA_CODJUS int8 not null,
   TRA_CLAJUS varchar(10) not null,
   TRA_NUMREG varchar(50) not null,
   TRA_FECREG timestamp not null,
   TRA_NUMPRE varchar(50),
   TRA_FECPRE timestamp,
   TRA_SEYCON varchar(50),
   TRA_NIFRTE varchar(12),
   TRA_NOMRTE varchar(500),
   TRA_NIFRDO varchar(12),
   TRA_NOMRDO varchar(500),
   TRA_IDIOMA varchar(2) not null,
   TRA_TICOPR varchar(255),
   TRA_RESPRO varchar(255),
   TRA_FECPRO timestamp,
   TRA_FIRMAD char(1),
   TRA_AVISOS varchar(255),
   TRA_AVISMS varchar(255),
   TRA_AVIEMA varchar(255),
   TRA_NOTTEL varchar(255),
   primary key (TRA_CODIGO)
);
create table BTE_GESTRA (
   GAP_IDETRA varchar(255) not null,
   GAP_CODGES varchar(255) not null,
   primary key (GAP_CODGES, GAP_IDETRA)
);
alter table BTE_FICEXP add constraint FKBE1C1DA96567F7F8 foreign key (FIC_IDETRA) references BTE_TRAAPL;
alter table BTE_DOCUM add constraint FK3794E0A4ECCD2A32 foreign key (DOC_CODTRA) references BTE_TRAMIT;
alter table BTE_TRAMIT add constraint FKD67DFD612811B4F5 foreign key (TRA_IDETRA) references BTE_TRAAPL;
alter table BTE_GESTRA add constraint FKBFA015BAF45A8086 foreign key (GAP_CODGES) references BTE_GESTOR;
alter table BTE_GESTRA add constraint FKBFA015BAFDFD3862 foreign key (GAP_IDETRA) references BTE_TRAAPL;
create sequence BTE_SEQTRA;
create sequence BTE_SEQDOC;
