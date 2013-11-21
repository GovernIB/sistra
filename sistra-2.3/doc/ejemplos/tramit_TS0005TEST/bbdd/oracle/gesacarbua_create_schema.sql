
CREATE TABLE str_uniadm
(
 codigo NUMBER(4) NOT NULL,
 descripcion VARCHAR2(40) NOT NULL,
 parent NUMBER(4),
 CONSTRAINT codigo_pk PRIMARY KEY (codigo)
);