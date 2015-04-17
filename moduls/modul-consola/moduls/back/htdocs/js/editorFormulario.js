/*Utilidades para la seleccion y comunicacion entre el iframe del contenedor HTML y el parent ZK*/

var seleccionado = null;

document.onmouseover = myOnMouseOver;
document.onmouseout = myOnMouseOut;
document.onclick = myOnClick;

function seleccionarOnload(identificador) {
	// Deshabilitamos todos los tags a
    var anchors = document.getElementsByTagName("a");
    for (var i = 0; i < anchors.length; i++) {
        anchors[i].onclick = function() {return(false);};
    }
	if (identificador != 'null') {
		var objSeleccionadoOnLoad;
		if (document.getElementById(identificador) != null) {
			objSeleccionadoOnLoad = document.getElementById(identificador);
		} else {
			objSeleccionadoOnLoad = identificador;
		}
		objSeleccionadoOnLoad.originalBackground = objSeleccionadoOnLoad.style.backgroundColor;
		seleccionarElemento(objSeleccionadoOnLoad);
		
		
		var div_elm = document.getElementsByTagName("div")[0], // el primer div
		i_elm = objSeleccionadoOnLoad; // el campo texto
		
		if (objSeleccionadoOnLoad.tagname == "label"){
			i_elm = objSeleccionadoOnLoad.parent();
		}
		
		i_TOP = i_elm.offsetTop; // posicion desde arriba

		div_elm.scrollTop = i_TOP; // escrolea muchacho!
		i_elm.focus();
		
		
		
		//		objSeleccionadoOnLoad.scrollTo(); //Mirar para que haga el scroll
	}
//	lanzarEventoParent('finOnLoad');
}

function lanzarEventoParent(args) {
	parent.lanzarEventoIframe(args);
}

function seleccionarElemento(obj) {
	if (seleccionado != obj) {
		if (seleccionado != null) {
			seleccionado.style.backgroundColor = seleccionado.originalBackground;
			seleccionado.originalBackground = '';
		}
		obj.style.backgroundColor = '#6f97d2';
		seleccionado = obj;
	}
}

function onMouseOverElemento(obj) {

	if (obj != seleccionado) {
		obj.originalBackground = obj.style.backgroundColor;
		obj.style.backgroundColor = '#d0def0';
		if (obj.tagName == 'img'.toUpperCase()) {
			setOpacity(obj, '5');
		}
	}
}

function onMouseOutElemento(obj) {
	if (obj != seleccionado) {
		obj.style.backgroundColor = obj.originalBackground;
		obj.originalBackground = '';
		if (obj.tagName == 'img'.toUpperCase()) {
			setOpacity(obj, '10');
		}
	}
}

function setOpacity(obj, value) {
	obj.style.opacity = value / 10;
	obj.style.filter = 'alpha(opacity=' + value * 10 + ')';
}

function getILC(myobj) {
	var ret = null;
	var curobj;
	curobj = myobj;
	while (curobj) {
		if (curobj.id && curobj.id.indexOf("ILC_") == 0) {
			ret = curobj;
			break;
		}else{
			curobj = curobj.parentNode;	
		}
	}
	return ret;
}
 

function myOnMouseOver(_e) {
	var e = window.event ? window.event : _e;
	if (e.target)
		targ = e.target;
	else if (e.srcElement)
		targ = e.srcElement;
	if (targ.nodeType == 3) // defeat Safari bug
		targ = targ.parentNode;
	obj = targ;
	if (obj) {
		var iLC = getILC(obj);
		if (iLC) {
			onMouseOverElemento(iLC);
		} else if (obj.id && obj.id != '') {
			onMouseOverElemento(obj);
		}
	}

	// Se cancela la propagación para que no llegue mas de uno si hay
	// capas solapadas
	e.cancelBubble = true;
	if (e.stopPropagation)
		e.stopPropagation();
	return false;
}

function myOnMouseOut(_e) {
	var e = window.event ? window.event : _e;
	if (e.target)
		targ = e.target;
	else if (e.srcElement)
		targ = e.srcElement;
	if (targ.nodeType == 3) // defeat Safari bug
		targ = targ.parentNode;
	obj = targ;
	if (obj) {
		var iLC = getILC(obj);
		if (iLC) {
			onMouseOutElemento(iLC);
		} else if (obj.id && obj.id != '') {
			onMouseOutElemento(obj);
		}
	}
	// Se cancela la propagación para que no llegue mas de uno si hay
	// capas solapadas
	e.cancelBubble = true;
	if (e.stopPropagation)
		e.stopPropagation();
	return false;
}

function myOnClick(_e) {
	var e = window.event ? window.event : _e;
	if (e.target)
		targ = e.target;
	else if (e.srcElement)
		targ = e.srcElement;
	if (targ.nodeType == 3) // defeat Safari bug
		targ = targ.parentNode;
	obj = targ;
	if (obj) {
		var iLC = getILC(obj);
		if (iLC) {
			lanzarEventoParent(iLC.id);
			seleccionarElemento(iLC);
		} else if (obj.id && obj.id != '') {
			lanzarEventoParent(obj.id);
			seleccionarElemento(obj);
		}
	}

	// Se cancela la propagación del click para que no llegue mas de uno si hay
	// capas solapadas
	e.cancelBubble = true;
	if (e.stopPropagation)
		e.stopPropagation();
}
