function insertarFilaFuenteDatos(id){
	document.location = "insertarFilaFuenteDatos.do?identificador=" + id;	
}

function exportarFuenteDatos(id){
	document.location = "exportCSVFuenteDatosAction.do?identificador=" + id;	
}

function importarFuenteDatos(id){
	document.location = "mostrarImportCSVFuenteDatos.do?identificador=" + id;	
}

function borrarFilaFuenteDatos(id, numfila){
	document.location = "borrarFilaFuenteDatos.do?identificador=" + id + "&numfila=" + numfila;	
}


/* seleccionar ítem d'una tabla */
function selecItemTabla(obj) {
	clase = obj.className;
	obj.className = 'perDamunt';
	obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
}

function mostrarCambioValor(mostrar, idFuenteDatosModificar, numFila, idCampoModificar) {
	var capaI = document.getElementById('divCambioValor');
	
    if (mostrar) {

    	Tamanyos.iniciar();
    	Fondo.mostrar();
    	
    	$("#valorCampo").val("");
    	$("#idFuenteDatos").val(idFuenteDatosModificar);
    	$("#numFila").val(numFila);
    	$("#idCampo").val(idCampoModificar);
 		
		var ventanaX = document.documentElement.clientWidth;
		var ventanaY = document.documentElement.clientHeight;
		var capaY = document.getElementById('contenedor').offsetHeight;
		
		// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
		capaI.style.display = 'block';
		capaInfoX = capaI.offsetWidth;
		capaInfoY = capaI.offsetHeight;
		with (capaI) {
			style.left = (ventanaX-capaInfoX)/2 + 'px';
			style.top = (ventanaY-capaInfoY)/2 + 'px';
		}		
    } else {    	
    	Fondo.esconder();
    	capaI.style.display = 'none';
	}
}

function realizarCambioCampo(){
	
	var valorCampo = $("#valorCampo").val();
	var idFuenteDatos = $("#idFuenteDatos").val();
	var numFila = $("#numFila").val();
	var idCampo = $("#idCampo").val();
	
	var mapVars = {};
	mapVars["idFuenteDatos"] = idFuenteDatos;
	mapVars["numFila"] = numFila;
	mapVars["idCampo"] = idCampo;
	mapVars["valorCampo"] = valorCampo;
	
	$.ajax({
		type: "POST",
		url: "modificarValorCampoFuenteDatos.do",
		data: mapVars,
		dataType: "json",
		error: function() {
			alert("Error enviando datos al servidor. Intentelo de nuevo.");					
		},
		success: function(json) {					
			if (json.error == "") {
				mostrarCambioValor(false,null,null,null);	
				var numfila = json.numfila;
				var idCampo = json.idCampo;
				var valorCampo = json.valorCampo;
				if (valorCampo == "") {
					valorCampo = "[ --- ]";
				}
				$("#f" + numfila + "-" + idCampo).text(valorCampo);
			} else {
 				alert(json.error);
			}						
		}
	});
 		
	
}
