ALTER table ZPE_ELEEX  ADD   ELE_CODAVI           VARCHAR(50);

ALTER table ZPE_NOTTEL  ADD  NOT_FECPLZ           timestamp;

ALTER table ZPE_NOTTEL  ADD  NOT_RECHAZ         bool not null;

UPDATE ZPE_NOTTEL SET    NOT_RECHAZ = false;
COMMIT;