/*
Permite mostrar mensaje en toda la pantalla mientras se hace algun proceso

Hay que crear al final de la p?gina el div: <div id="fondo"></div>

- tipo: mensaje, alerta, confirmacion
- modo: ejecutando, atencion, correcto, error, informacion
- titulo: texto
- texto: texto con etiquetas HTML
Modificar
- fundido: si/no
*/

var mensaje_ie6 = false;
var txtAceptar = "Aceptar";
var txtCancelar = "Cancelar"; 

var Mensaje = {
	mostrar: function(opciones) {
		// teclado
		/*
		if (teclado == "S") {
			Teclado.cancelar();
		}
		*/
		// fons
		Tamanyos.iniciar();
		Fondo.mostrar();
		// crear
		if ($("#mensaje").size() == 0) {
			$("<div>").attr("id","mensaje").appendTo("body");
		}
		// pintar
		mensaje_div = $("#mensaje");
		codigo = "<p class=\"titulo\">" + opciones.titulo + "</p>";
		if (typeof opciones.texto != "undefined" && opciones.texto != "") {
			codigo += opciones.texto;
		}
		if (opciones.tipo == "alerta" || opciones.tipo == "confirmacion") {
			codigo += "<div class=\"botonera\"><button id=\"mensaje_aceptar\" type=\"button\">" + txtAceptar + "</button>";
			if (opciones.tipo == "confirmacion") {
				codigo += ", o <a id=\"mensaje_cancelar\" href=\"#\">"+txtCancelar+"</a>"
			}
			codigo += "</div>";
		}
		mensaje_div.html(codigo).attr("class",opciones.modo);
		if (typeof opciones.funcion != "undefined" && opciones.funcion != "") {
			$("#mensaje_aceptar").bind("click",opciones.funcion);
			$("#mensaje_cancelar").bind("click",Mensaje.cancelar);
		} else {
			$("#mensaje_aceptar").bind("click",Mensaje.cancelar);
		}
		// posicion
		capa_W = mensaje_div.width();
		capa_H = mensaje_div.height();
		if (window.XMLHttpRequest) {
			mensaje_div.css({top: (ventana_H-capa_H)/2+"px", left: (ventana_W-capa_W)/2+"px"});
		} else {
			mensaje_ie6 = true;
			mensaje_div.css({position: "absolute", top: $(document).scrollTop()+((ventana_H-capa_H)/2)+"px", left: (ventana_W-capa_W)/2+"px"});
			$(window).bind("scroll",Mensaje.scrollear);
			Selects.esconder();
		}
		mensaje_div.fadeIn(
			300,
			function() {
				$("#mensaje_aceptar").focus();
				// teclado
				//Teclado.mensaje_on();
				if (typeof opciones.funcionAlMostrar != "undefined" && opciones.funcionAlMostrar != "") {
					opciones.funcionAlMostrar();
				}
			});
	},
	cancelar: function() {
		mensaje_div.fadeOut(
			300,
			function() {
				mensaje_div.remove();
				Fondo.esconder();
				// teclado
				/*
				if (teclado == "S") {
					teclado_vez = 0;
					Teclado.iniciar();
				}
				*/
			});
		exit = false;
		if (mensaje_ie6) {
			$(window).unbind("scroll",Mensaje.scrollear);
			mensaje_ie6 = false;
			Selects.mostrar();
		}
	},
	scrollear: function() {
		$("#mensaje").css("top",$(document).scrollTop()+((ventana_H-capa_H)/2)+"px");
	},
	modificar: function(opciones) {
		if (typeof opciones.fundido != "undefined" && opciones.fundido == "si") {
			mensaje_div.fadeOut(
				300,
				function() {
					Mensaje.mostrar(opciones);
				});
		} else {
			Mensaje.mostrar(opciones);
		}
	}
};

var Fondo = {
	iniciar: function() {
		$("<div>").attr("id","fondo").appendTo("body");
	},
	mostrar: function() {
		fons_W = (ventana_W > contenidor_W) ? ventana_W : contenidor_W;
		fons_H = (ventana_H > contenidor_H) ? ventana_H : contenidor_H;
		$("#fondo").css({display: "block", width: fons_W+"px", height: fons_H+"px", opacity: 0.5});
	},
	esconder: function() {
		$("#fondo").hide();
	}
};

var Tamanyos = {
	iniciar: function() {
		ventana_W = $(window).width();
		ventana_H = $(window).height();
		
		contenidor_W = $("body").width();
		contenidor_H = $("body").height();
		return ventana_W, ventana_H, contenidor_W, contenidor_H;
	}
};

var Selects = {
	esconder: function() {
		$("body").find("select").css("display","none");
	},
	mostrar: function() {
		$("body").find("select").css("display","inline");
	}
};
