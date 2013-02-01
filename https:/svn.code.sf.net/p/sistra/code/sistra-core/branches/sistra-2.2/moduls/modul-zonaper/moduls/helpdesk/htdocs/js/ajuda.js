// Document JavaScript

// activar ajuda

function ajudaActivar() {
	capaAjuda = document.getElementById('ajuda');
	if(capaAjuda.style.display == 'block') {
		capaAjuda.style.display = 'none';
		for(i=0;i<capesContinguts.length;i++) {
			capesContinguts[i].className = 'continguts';
		}
		if(typeof ajudaColumnaIniciar != 'undefined') ajudaColumnaIniciar(false);
		this.title = 'Activar ajuda';
		this.getElementsByTagName('img')[0].src = 'imgs/botons/ajuda.gif';
	} else {
		capaAjuda.style.display = 'block';
		capesContinguts = getElementsByClassName(document,"div","continguts");
		capesContingutsX = posicionOffset(capesContinguts[0],"top");
		capaAjuda.style.top = capesContingutsX + 'px';
		capaAjudaAltura = capaAjuda.offsetHeight;
		capesContingutsAltura = 0;
		for(i=0;i<capesContinguts.length;i++) {
			capesContinguts[i].className = 'contingutsAjuda';
			capesContingutsAltura += capesContinguts[i].offsetHeight;
		}
		if(capesContingutsAltura > capaAjudaAltura) capaAjuda.style.height = capesContingutsAltura - 30 + 'px';
		fundidoMostrar('ajuda');
		if(typeof ajudaColumnaIniciar != 'undefined') ajudaColumnaIniciar(true);
		this.title = 'Tancar ajuda';
		this.getElementsByTagName('img')[0].src = 'imgs/botons/ajudatancar.gif';
	}
}

// events

function addEvent(obj,tipo,fn,modo) {
	if(obj.addEventListener) {
		obj.addEventListener(tipo, fn, modo);
		return true;
	} else {
		obj['on' + tipo] = fn;
	}
}


function removeEvent(obj,tipo,fn,modo) {
	if(obj.addEventListener) {
		obj.removeEventListener(tipo,fn,modo);
	} else {
		obj['on' + tipo] = "";
	}
}

// posicio dels objectes

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
	if(tablaResultats[0] == undefined) return;
	ths = tablaResultats[0].getElementsByTagName('th');
	for(i=0;i<ths.length;i++) {
		if((ajudaColumnes[i] != undefined) && (ajudaColumnes[i] != "")) {
			if(tipo == true) {
				addEvent(ths[i],'mouseover',ajudaColumnaActivar,true);
				addEvent(ths[i],'mouseout',ajudaColumnaEsconder,true);
				ths[i].className = 'ajudaColumna t' + i;
			} else {
				removeEvent(ths[i],'mouseover',ajudaColumnaActivar,true);
				removeEvent(ths[i],'mouseout',ajudaColumnaEsconder,true);
				ths[i].className = '';
			}
		}
	}
}
			
function ajudaColumnaActivar() {
	if(this.className != null) {
		columnaNum = this.className.substr(14);
		cAjudaColumna = document.getElementById('ajudaColumna');
		cAjudaColumna.style.display = 'block';
		cAjudaColumna.innerHTML = ajudaColumnes[columnaNum];
		cAjudaColumnaAltura = cAjudaColumna.offsetHeight;
		cAjudaColumnaAnchura = cAjudaColumna.offsetWidth;
		columnaX = posicionOffset(this,"left");
		columnaY = posicionOffset(this,"top");
		tablaAnchura = this.parentNode.offsetWidth;
		cAjudaColumna.style.top = columnaY - cAjudaColumnaAltura + 'px';
		if((columnaX + cAjudaColumnaAnchura) > tablaAnchura) cAjudaColumna.style.left = tablaAnchura - cAjudaColumnaAnchura + 14 + 'px';
		else cAjudaColumna.style.left = columnaX + 'px';
		if(!document.all) fundidoMostrar('ajudaColumna');
	}
}

function ajudaColumnaEsconder() {
	document.getElementById('ajudaColumna').style.display = 'none';
}

function ajudaIniciar() {
		addEvent(document.getElementById('ajudaBoto'),'click',ajudaActivar,true);
}


