// Document JavaScript

// events

function addEvent(obj,tipo,fn,modo) {
	if(obj.addEventListener) {
		obj.addEventListener(tipo,fn,modo);
		return true;
	} else if(obj.attachEvent) {
		var r = obj.attachEvent('on'+tipo,fn);
		return r;
	} else {
		obj['on'+tipo] = fn;
	}
}

function removeEvent(obj,tipo,fn,modo) {
	if(obj.addEventListener) {
		obj.removeEventListener(tipo,fn,modo);
	} else if(obj.attachEvent) {
		obj.detachEvent("on"+tipo,fn);
	} else {
		obj['on'+tipo] = "";
	}
}

// activar ajuda

function ajudaActivar(e) {
	boto = e.target || e.srcElement;
	capaAjuda = document.getElementById('ajuda');
	if(capaAjuda.style.display == 'block') {
		capaAjuda.style.display = 'none';
		for(i=0;i<capesContinguts.length;i++) {
			capesContinguts[i].className = 'continguts';
		}
		boto.title = 'Activar ajuda';
		boto.getElementsByTagName('img')[0].src = 'imgs/botons/ajuda.gif';
		tablaResultats = getElementsByClassName(document,"table","resultats");
		if(tablaResultats.length > 0) ajudaColumnaIniciar(false);
	} else {
		capaAjuda.style.display = 'block';
		capesContinguts = getElementsByClassName(document,"div","continguts");
		capesContingutsX = posicionOffset(capesContinguts[0],"top");
		capaAjuda.style.top = capesContingutsX + 'px';
		capaAjudaAltura = capaAjuda.offsetHeight;
		capesContingutsAltura = 0;
		for(i=0;i<capesContinguts.length;i++) {
			capesContinguts[i].className = capesContinguts[i].className + ' ajuda';
			capesContingutsAltura += capesContinguts[i].offsetHeight;
		}
		if(capesContingutsAltura > capaAjudaAltura) capaAjuda.style.height = capesContingutsAltura - 30 + 'px';
		fundidoMostrar('ajuda');
		boto.title = 'Tancar ajuda';
		boto.getElementsByTagName('img')[0].src = 'imgs/botons/ajudatancar.gif';
		tablaResultats = getElementsByClassName(document,"table","resultats");
		if(tablaResultats.length > 0) ajudaColumnaIniciar(true);
	}
}

function ajudaIniciar() {
	addEvent(document.getElementById('ajudaBoto'),'click',ajudaActivar,false);
}

addEvent(window,'load',ajudaIniciar,false); // ací comença tot

// posició dels objectes

function posicionOffset(obj,tipo) {
	if(obj != null) {
		var totalOffset = (tipo == "left") ? obj.offsetLeft : obj.offsetTop;
		var parentEl = obj.offsetParent;
		while (parentEl != null){
			totalOffset = (tipo == "left") ? totalOffset+parentEl.offsetLeft : totalOffset+parentEl.offsetTop;
			parentEl = parentEl.offsetParent;
		}
		return totalOffset;
	}
}

// ByClassName

function getElementsByClassName(oElm, strTagName, oClassNames) {
	var arrElements = (strTagName == "*" && oElm.all)? oElm.all : oElm.getElementsByTagName(strTagName);
	var arrReturnElements = new Array();
	var arrRegExpClassNames = new Array();
	if(typeof oClassNames == "object"){
		for(var i=0; i<oClassNames.length; i++){
			arrRegExpClassNames.push(new RegExp("(^|\\s)" + oClassNames[i].replace(/\-/g, "\\-") + "(\\s|$)"));
		}
	}
	else{
		arrRegExpClassNames.push(new RegExp("(^|\\s)" + oClassNames.replace(/\-/g, "\\-") + "(\\s|$)"));
	}
	var oElement;
	var bMatchesAll;
	for(var j=0; j<arrElements.length; j++){
		oElement = arrElements[j];
		bMatchesAll = true;
		for(var k=0; k<arrRegExpClassNames.length; k++){
			if(!arrRegExpClassNames[k].test(oElement.className)){
				bMatchesAll = false;
				break;
			}
		}
		if(bMatchesAll){
			arrReturnElements.push(oElement);
		}
	}
	return (arrReturnElements);
}

// efecte fundit

opacidad = 0;
function fundidoMostrar(id) {
	obj = document.getElementById(id);
	if(opacidad < 100) {
		opacidad += 20;
		if(typeof obj.style.filter != 'undefined') {
			if(window.opera) obj.style.opacity = opacidad/100;
			else obj.style.filter = "alpha(opacity="+opacidad+")";
		} else {
			obj.style.opacity = opacidad/100;
		}
		tiempo = setTimeout('fundidoMostrar("'+id+'")', 50);
	} else {
		clearTimeout(tiempo);
		opacidad = 0;
	}
}

// ajuda per a les columnes

function ajudaColumnaIniciar(tipo) {
	tablaResultats = getElementsByClassName(document,"table","resultats");
	ths = tablaResultats[0].getElementsByTagName('th');
	for(i=0;i<ths.length;i++) {
		if(ajudaColumnes[i] != "") {
			if(tipo == true) {
				addEvent(ths[i],'mouseover',ajudaColumnaActivar,false);
				addEvent(ths[i],'mouseout',ajudaColumnaEsconder,false);
				ths[i].className = 'ajudaColumna t' + i;
			} else {
				removeEvent(ths[i],'mouseover',ajudaColumnaActivar,false);
				removeEvent(ths[i],'mouseout',ajudaColumnaEsconder,false);
				ths[i].className = '';
			}
		}
	}
}
			
function ajudaColumnaActivar(e) {
	obj = e.target || e.srcElement;
	if(obj.className != null) {
		columnaNum = obj.className.substr(14);
		cAjudaColumna = document.getElementById('ajudaColumna');
		cAjudaColumna.style.display = 'block';
		cAjudaColumna.innerHTML = ajudaColumnes[columnaNum];
		cAjudaColumnaAltura = cAjudaColumna.offsetHeight;
		cAjudaColumnaAnchura = cAjudaColumna.offsetWidth;
		columnaX = posicionOffset(obj,"left");
		columnaY = posicionOffset(obj,"top");
		tablaAnchura = obj.parentNode.offsetWidth;
		cAjudaColumna.style.top = columnaY - cAjudaColumnaAltura + 'px';
		if((columnaX + cAjudaColumnaAnchura) > tablaAnchura) cAjudaColumna.style.left = tablaAnchura - cAjudaColumnaAnchura + 14 + 'px';
		else cAjudaColumna.style.left = columnaX + 'px';
		if(!document.all) fundidoMostrar('ajudaColumna');
	}
}

function ajudaColumnaEsconder() {
	document.getElementById('ajudaColumna').style.display = 'none';
}

