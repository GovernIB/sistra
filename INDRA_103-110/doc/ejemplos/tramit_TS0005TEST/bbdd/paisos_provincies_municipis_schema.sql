
CREATE TABLE str_dpais
(
  pai_codigo integer NOT NULL,
  pai_codalf character(3) NOT NULL,
  pai_dencas char(50) NOT NULL,
  pai_dencat char(50) NOT NULL,
  pai_vigenc char(1) NOT NULL,
  pai_cod2af char(3),
  CONSTRAINT str_pai_pk PRIMARY KEY (pai_codigo)
);


CREATE TABLE str_dprovi
(
  pro_codigo integer NOT NULL,
  pro_codcau integer,
  pro_dencas char(50),
  pro_dencat char(50),
  CONSTRAINT str_pro_pk PRIMARY KEY (pro_codigo)
);

CREATE TABLE str_dmunic
(
  mun_codigo integer NOT NULL,
  mun_provin integer NOT NULL,
  mun_denofi char(50) NOT NULL,
  CONSTRAINT str_mun_pk PRIMARY KEY (mun_codigo, mun_provin),
  CONSTRAINT str_munpro_fk FOREIGN KEY (mun_provin)
      REFERENCES str_dprovi (pro_codigo)
      ON UPDATE NO ACTION ON DELETE NO ACTION
);