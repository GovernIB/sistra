function detalleFuenteDatos(id){
	document.location = "detalleFuenteDatos.do?identificador=" + id;	
}

/* seleccionar �tem d'una tabla */
function selecItemTabla(obj) {
	clase = obj.className;
	obj.className = 'perDamunt';
	obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
}
