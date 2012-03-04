
/* Dejamos solo castellano, catalan e ingles */
DELETE FROM rfr_idioma WHERE idi_codi = 'it';
DELETE FROM rfr_idioma WHERE idi_codi = 'pt';
UPDATE rfr_idioma SET idi_orden=1  WHERE idi_codi = 'ca';
INSERT INTO rfr_idioma (idi_codi, idi_orden) VALUES ('en', 2);

COMMIT;