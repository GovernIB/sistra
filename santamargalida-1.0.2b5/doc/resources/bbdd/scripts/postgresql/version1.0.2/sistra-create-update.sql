--
-- 30/07/2010
--

create table STR_OCUPAC (
   OCU_CODIGO int8 not null,
   OCU_DESC_CA varchar(200) not null,
   OCU_DESC_ES varchar(200) not null,
   OCU_PREU double precision not null,
   primary key (OCU_CODIGO)
);
