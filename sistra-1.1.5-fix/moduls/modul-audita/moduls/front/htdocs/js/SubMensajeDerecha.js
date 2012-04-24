ns = (document.layers) ? true : false;
ie = (document.all) ? true : false;
dom = (document.getElementById && !document.all) ? true : false;

// inicializamos variables
var capaActiva = 0; // no hay ninguna capa activa

if (ns) msj = document.capaMensaje; // nuestra capa creada en el HTML en NS
if (ie) msj = document.all["capaMensaje"].style; // nuestra capa creada en el HTML en IE
if (dom) msj = document.getElementById("capaMensaje").style; // nuestra capa creada en el HTML en DOM

// capturamos el movimiento del ratón
document.onmousemove = movimientoRaton;
if (ns) document.captureEvents(Event.MOUSEMOVE);

// para mostrar la capa y poner su contenido
function mostrarMensaje(txt) {
	if (txt!=null && txt!='null' && txt!='' && txt!=' ') {
		escribirCapa(txt); // dibujamos el texto dentro de la capa con la funcion que esta mas abajo
		// el lugar donde se dibujara segun el tipo de navegador
		if (ns) {
			msj.top = y + 15;
			msj.left = x - 20;
			msj.visibility = "show";
		} else if (ie) {
			msj.posTop = y + 15;
			msj.posLeft = x - 20;
			msj.visibility = "visible";
		} else if (dom) {
			msj.top = y + 15 + "px";
			msj.left = x - 20 + "px";
			msj.visibility = "visible";
		}
		
		capaActiva = 1; // y la variable a mostrada
	}
}

function escribirCapa(txt) {
	if (ns) {
		var nsCapa = document.capaMensaje.document;
		nsCapa.write(txt);
		nsCapa.close();
	} else if (ie) {
		document.all["capaMensaje"].innerHTML = txt;
	} else if (dom) {
		document.getElementById("capaMensaje").innerHTML = txt;
	}
}

// para esconder la capa
function esconderMensaje() {
	capaActiva = 0 ; // cambiamos la variable a oculta
	
	if (ns) {
		msj.visibility = "hide";
		msj.top = 0;
		msj.left = 0;
	} else if (ie) {
		msj.visibility = "hidden";
		msj.posTop = 0;
		msj.posLeft = 0;
	} else if (dom) {
		msj.visibility = "hidden";
		msj.top = 0;
		msj.left = 0;
	}
}

// para capturar los movimientos del ratón mientras se mueve la página
function movimientoRaton(e) {
	if (ns) {
		x = e.pageX;
		y = e.pageY;
	} else if (ie) {
		x = event.x + document.documentElement.scrollLeft;
		y = event.y + document.documentElement.scrollTop;
	} else if (dom) {
		x = e.clientX + document.documentElement.scrollLeft;
        y = e.clientY + document.documentElement.scrollTop;
	}
	
	if (capaActiva == 1) // si hay capa seguimos colocándola donde corresponda
	{
		if (ns) {
			msj.top = y + 15;
			msj.left = x - 20;
			msj.visibility = "show";
		} else if (ie) {
			msj.posTop = y + 15;
			msj.posLeft = x - 20;
			msj.visibility = "visible";
		} else if (dom) {
			msj.top = y + 15 + "px";
			msj.left = x - 20 + "px";
			msj.visibility = "visible";
		}
	}
}
