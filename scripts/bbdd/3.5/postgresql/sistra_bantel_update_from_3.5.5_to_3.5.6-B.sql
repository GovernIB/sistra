/* Borrar IDEPRO (tras revisar que funciona script 'sistra_bantel_update_from_3.5.5_to_3.5.6-script_rollback.sql':
 *  el roolback de script 'sistra_bantel_update_from_3.5.5_to_3.5.6.sql' ) */

alter table BTE_TRAPRO drop column PRO_IDEPRO;
alter table BTE_FUEDAT drop column FUE_IDEPRO;
alter table BTE_TRAMIT drop column TRA_IDEPRO;
alter table BTE_GESPRO drop column GAP_IDEPRO;
