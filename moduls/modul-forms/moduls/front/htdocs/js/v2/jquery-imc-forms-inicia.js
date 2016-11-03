// index

var idioma,
	$imc_contenidor,
	$imc_ajuda,
	$imc_bt_ajuda,
	$imc_formulari;

var bLoaded = false;

//onReady
$(function(){
	
	idioma = $("html").attr("lang");
	$imc_contenidor = $("#imc-contenidor");
	$imc_ajuda = $("#imc-ajuda");
	$imc_bt_ajuda = $("#imc-bt-ajuda");
	$imc_formulari = $("#imc-formulari");
	
	// arbre
	$imc_formulari.find(".imc-el-arbre").arbre();
	
	// taula
	$imc_formulari.find(".imc-el-taula").taula();
	
	// formateig de dades
	dadesFormateig();
	
	// ajuda
	$imc_bt_ajuda.ajuda();

	// Desactivamos autocompletar
	$("#pantallaForm").attr('autocomplete', 'off');	
	
	// Disparador logica pantalla
	onFieldChange(document.pantallaForm, null);
	
	// Mantenemos url sesion sistra (realizamos peticion y programamos que se repita cada 5 min)
	mantenimientoSesionSistra();
 	window.setInterval(mantenimientoSesionSistra, 5 * 60 * 1000);
 	
 	// Indicamos que la pagina se ha cargadp
 	bLoaded = true;
	
});
// /onReady
	
