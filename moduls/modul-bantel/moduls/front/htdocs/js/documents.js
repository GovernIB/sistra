// Documents

var Documents = {
	iniciar: function() {
		documentos_elm = document.getElementById('documentos');
		addEvent(document.getElementById('afegirEnllas'),'click',Documents.crear,false);
		addEvent(documentos_elm,'click',Documents.esborrar,false);
	},
	crear: function() {
		codi = "<p><label>T?tol:</label><input name=\"titol_doc\" type=\"text\" /><label class=\"enLinia\">Fitxer:</label><input name=\"fitxer_doc\" type=\"file\" /><a href=\"#\" class=\"esborrar\">Esborrar</a></p>";
		LI = document.createElement("li");
		LI.innerHTML = codi;
		documentos_elm.appendChild(LI);
	},
	esborrar: function(e) {
		obj = e.target || e.srcElement;
		if (obj.nodeName == "A") {
			obj.parentNode.parentNode.removeChild(obj.parentNode);
		}
	}
};

// INICIEM
addEvent(window,'load',Documents.iniciar,false);