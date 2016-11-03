function ocultarEnviando() {

	var capaIF = document.getElementById('capaInfoFondo');
	var capaI = document.getElementById('capaInfoForms');
	
	capaI.style.display = 'none';
	capaIF.style.display = 'none';
}


function accediendoEnviando(mensaje) {
	
	contenido = mensaje;
	
	var capaIF = document.getElementById('capaInfoFondo');
	var capaI = document.getElementById('capaInfoForms');
	// tama?os de la ventana y la p?gina
	var scroller = document.documentElement.scrollTop;
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenedor').offsetHeight;
	// la capa de fondo ocupa toda la p?gina
	with (capaIF) {
		if(ventanaY > capaY) style.height = ventanaY + 'px';
		else style.height = capaY + 'px';
		if(document.all) style.filter = "alpha(opacity=40)";
		else style.MozOpacity = 0.4;
		if(document.all) style.width = ventanaX + 'px';
		style.display = 'block';
	}
	// colocamos el texto adecuado
	document.getElementById('mensajeEnviando').innerHTML = contenido;
	
	// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = parseInt((ventanaX-capaInfoX)/2) + 'px';
		if(document.all) style.top = parseInt(scroller+(ventanaY-capaInfoY)/2) + 'px';
		else style.top = parseInt((ventanaY-capaInfoY)/2) + 'px';
		if(!document.all) style.position = 'fixed';
		if(document.all) style.filter = "alpha(opacity=0)";
		else style.MozOpacity = 0;
	}
	// y mostramos la capa ayuda
	mostrarCapa();
}

function reposicionaCapa(){
	var scroller = (document.body.scrollTop) ? document.body.scrollTop : document.documentElement.scrollTop;
	var ventanaY = document.documentElement.clientHeight;
	if(document.getElementById('capaInfoForms') != 'undefined') var capaI = document.getElementById('capaInfoForms');
	capaInfoY = capaI.offsetHeight;
	capaI.style.top = parseInt(scroller+(ventanaY-capaInfoY)/2) + 'px';
}

opacidad = 0;
function mostrarCapa() {
	if(opacidad < 100) {
		opacidad += 20;
		if(document.all) document.getElementById('capaInfoForms').filters.alpha.opacity = opacidad;
		else document.getElementById('capaInfoForms').style.MozOpacity = opacidad/100;
		tiempo = setTimeout('mostrarCapa()', 50);
	} else {
		clearTimeout(tiempo);
		opacidad = 0;
	}
}