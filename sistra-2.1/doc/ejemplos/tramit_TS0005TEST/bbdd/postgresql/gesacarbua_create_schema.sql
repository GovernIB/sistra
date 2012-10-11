
CREATE TABLE str_uniadm
(
 codigo integer NOT NULL,
 descripcion character varying(40) NOT NULL,
 parent integer,
 CONSTRAINT codigo_pk PRIMARY KEY (codigo)
);