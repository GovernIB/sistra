


update bte_traapl set tap_tipacc = 'E';

update bte_traapl  set TAP_USR = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where TAP_USR IS NOT NULL;
update bte_traapl  set TAP_PWD = '6f80fcb1f11f76b5bf10301a1cb4c5c0' where TAP_PWD IS NOT NULL;


update bte_traapl set tap_wsver = 'v1' where tap_tipacc = 'W';