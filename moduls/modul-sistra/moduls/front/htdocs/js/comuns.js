// JavaScript Document

function setCookie(nombre, valor, expiraDias) {
	var ExpiraFecha = new Date();
  ExpiraFecha.setTime(ExpiraFecha.getTime() + (expiraDias * 24 * 3600 * 1000));
  document.cookie = nombre + "=" + escape(valor) + 
  ((expiraDias == null) ? "" : "; expires=" + ExpiraFecha.toGMTString()) + "; path=/";
}

function getCookie(nombre) {
	if(document.cookie.length > 0) {
			begin = document.cookie.indexOf(nombre+"=");
			if(begin != -1) {
			 begin += nombre.length+1;
			 end = document.cookie.indexOf(";", begin);
				if(end == -1) end = document.cookie.length;
				return unescape(document.cookie.substring(begin, end));
			} 
		}
	return null;
}

// aumentar - disminuir lletra

tamanyInicial = 80; // %
var tamanyLletra = {
	iniciar: function() {
		if(getCookie("caib") != null) {
			tamanyInicial = getCookie("caib");
			document.body.style.fontSize = tamanyInicial + '%';
		};
		DIVs = $('#eines div');
		if(DIVs[2] != null) {
			codi = "<a id=\"lletraAumentar\" title=\"Aumentar tamany lletra\">A+</a> / <a id=\"lletraDisminuir\" title=\"Disminuir tamany lletra\">A-</a>";
			DIVs[2].innerHTML = codi;
		};
		$("#lletraAumentar").bind("click", function(e){
			tamanyLletra.aumentar();
			e.preventDefault();
		});
		$("#lletraDisminuir").bind("click", function(e){
			tamanyLletra.disminuir();
			e.preventDefault();
		});
	},
	aumentar: function() {
		tamanyInicial = parseInt(tamanyInicial) + 8;
		if(tamanyInicial > 128) tamanyInicial = 128;
		document.body.style.fontSize = tamanyInicial + '%';
		setCookie("caib",tamanyInicial,365);
	},
	disminuir: function() {
		tamanyInicial = parseInt(tamanyInicial) - 8;
		if(tamanyInicial < 48) tamanyInicial = 48;
		document.body.style.fontSize = tamanyInicial + '%';
		setCookie("caib",tamanyInicial,365);
	}
};

$(document).ready(function(){
	tamanyLletra.iniciar();									 
});