ALTER table ZPE_PERSON ADD PER_IDEMOD VARCHAR(4000);
comment on column ZPE_PERSON.PER_IDEMOD is 'Ante cambios de NIF se almacenan los NIF anteriores a modo de log';
