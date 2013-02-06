function validaFormulario( form )
{
	form.pagina.value = '0';
	form.submit();
}

function detalleExpediente(id, unidadAdm, claveExp){
	document.location = "recuperarExpediente.do?identificadorExp=" + id + "&unidadAdm=" + unidadAdm + "&claveExp=" + claveExp;	
}

/* seleccionar ítem d'una tabla */
function selecItemTabla(obj) {
	clase = obj.className;
	obj.className = 'perDamunt';
	obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
}

function alta(){
	document.location = "altaExpediente.do?flagValidacion=entradaAlta";	
}