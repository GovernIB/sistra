// COMUNS


// title
$.fn.title = function(options) {
	var settings = $.extend({
		temps: 100,
		posicio: "esquerre", // esquerre/dreta
		contenidor: $("body")
	}, options);
	this.each(function(){
		var element = $(this),
		title_text = element.attr("title") || "",
		onEnter = function() {
			if ($("#title").size() === 1) {
				$("#title").remove();
			}
			$("body").append("<div id=\"title\"><p>" + element.data("title") + "</p><div class=\"cueta-baix\">&nbsp;</div></div>");
			var contenidor_H = settings.contenidor.outerHeight(),
				contenidor_W = settings.contenidor.outerWidth(),
				element_T = element.offset().top,
				element_L = element.offset().left,
				element_H = element.outerHeight(),
				element_W = element.outerWidth(),
				title_elm = $("#title"),
				title_H = title_elm.outerHeight(),
				title_W = title_elm.outerWidth(),
				title_Padding = parseInt(title_elm.find("p").css("paddingLeft"), 10);
				
			if (settings.posicio === "dreta") {
				element_L = element_L + element_W - 15;
			}
			if (element_L+title_W+10 > contenidor_W) {
				element_L = element_L -title_W+element_W;
				title_elm.find("div").css("width", title_W - ((title_Padding*2) - (title_Padding/2)));
			} else {
				title_elm.find("div").removeAttr("style");
			}
			title_elm.css({ top: element_T + "px", left: element_L + "px" });
			element.attr("title", "");
			element_T = element_T - title_H;
			if (element_T < 5) {
				title_elm.html("<div class=\"cueta-dalt\">&nbsp;</div><p>" + title_text + "</p>");
				element_T = element.offset().top;
				element_T = element_T + element_H + 5;
			}
			title_elm.css({ "top": (element_T-2) + "px" });
		},
		onLeave = function() {
			$("#title").hide().remove();
			element.attr("title", element.data("title"));
		};
		if (title_text !== "") {
			element.data("title", title_text).off('.title').on('mouseenter.title', onEnter).on('mouseleave.title', onLeave).on('focus.title', onEnter).on('blur.title', onLeave);
		}
	});
	return this;
};
// /title


// submenu
$.fn.submenu = function(options) {
	this.each(function(){
		var settings = $.extend({
			submenu: $(this).closest("td").find("div:first"),
			aliniacio: "esquerre",
			posicio : "offset",
			amplariaIgual: true,
			tancarAlClicarDins: false
		}, options);
		var element = $(this),
		submenu_elm = settings.submenu,
		tancarAlClicarDins = settings.tancarAlClicarDins,
		onClick = function() {
			if (!element.hasClass("imc-select-on") && !element.parent().hasClass("imc-select-deshabilitat") && !element.parent().hasClass("imc-select-lectura")) {
				element.addClass("imc-select-on imc-select-obrint");
				submenu_elm.addClass("imc-submenu-on");
				var window_H = $(window).height(),
					window_T = $(window).scrollTop(),
					element_T = (settings.posicio == "position") ? element.position().top : element.offset().top,
					element_L = (settings.posicio == "position") ? element.position().left : element.offset().left,
					element_H = element.outerHeight(),
					element_W = element.outerWidth(),
					submenu_W = submenu_elm.outerWidth(),
					submenu_H = submenu_elm.outerHeight(),
					submenu_L = (settings.aliniacio == "esquerre") ? element_L : element_L-submenu_W+element_W,
					submenu_T = element_T+element_H;
				if (settings.amplariaIgual) {
					submenu_elm.css({ width: element_W+"px" });
				}
				
				if ((element_T+element_H+submenu_H) > (window_H+window_T)) {
					submenu_T = element_T-submenu_H;
					submenu_elm.addClass("imc-opcions-superior");
				} else {
					submenu_elm.removeClass("imc-opcions-superior");
				}
				
				submenu_elm.css({ top: submenu_T+"px", left: submenu_L+"px" }).attr("tabindex", "-1").slideDown(200, function() {
					$(document).on("click.submenu", onWindow).on("keydown.submenu", onKeyDown);
					submenu_elm.focus();
					element.removeClass("imc-select-obrint");
				});
			} else {
				if (!element.hasClass("imc-select-obrint")) {
					onWindow();
				}
			}
		},
		onWindow = function(e) {
			var tanca = true;
			if (!tancarAlClicarDins) {
				var elm = (typeof e === "undefined") ? element : $(e.target);
				tanca = (elm.closest(".imc-submenu-on").length === 1) ? false : true;
			} else {
				var elm = (typeof e === "undefined") ? element : $(e.target);
				if (elm.closest(".imc-index-alfabet").length) {
					tanca = false;
				}
			}
			if (tanca) {
				element.removeClass("imc-select-on");
				submenu_elm.removeClass("imc-submenu-on");
				$(document).off("click.submenu").off("keydown.submenu");
				submenu_elm
					.find("a").removeClass("hover").end()
					.slideUp(100);
			}
		},
		onKeyDown = function(e) {
			
			if (e.keyCode === 38 || e.keyCode === 40) { // amunt

				var submenu_items = submenu_elm.find(":not(.imc-select-seleccionat) a"),
					submenu_item_marcat = submenu_elm.find(":not(.imc-select-seleccionat) a.hover").length;

			}

			if (e.keyCode === 38) { // amunt

				if (!submenu_item_marcat) {
					submenu_items.eq( submenu_items.length - 1 ).addClass("hover").focus();
				} else {
					var submenu_item_marcat = submenu_elm.find(":not(.imc-select-seleccionat) a.hover:first"),
						marcat_index = submenu_items.index(submenu_item_marcat),
						marcat_index = (marcat_index < 0) ? submenu_items.length - 1 : marcat_index - 1;
					submenu_items.removeClass("hover").eq( marcat_index ).addClass("hover").focus();
				}
				e.preventDefault();
				return false;

			} else if (e.keyCode === 40) { // avall

				if (!submenu_item_marcat) {
					submenu_items.eq( 0 ).addClass("hover").focus();
				} else {
					var submenu_item_marcat = submenu_elm.find(":not(.imc-select-seleccionat) a.hover:first"),
						marcat_index = submenu_items.index(submenu_item_marcat),
						marcat_index = (marcat_index >= submenu_items.length-1) ? 0 : marcat_index + 1;
					submenu_items.removeClass("hover").eq( marcat_index ).addClass("hover").focus();
				}
				e.preventDefault();
				return false;

			}

		};
		element.off('.submenu').on('click.submenu', onClick);
	});
	return this;
};
// /submenu


// dataCompletar
var AVUI_DIA,
	AVUI_MES,
	AVUI_ANY;

$.fn.dataCompletar = function(options) {
	var settings = $.extend({
		contenidor: $("body"),
		separador: "/"
	}, options);
	this.each(function(){
		var element = $(this),
		separador = settings.separador,
		onBlur = function() {
			var data_val = element.val();
			// completar
			if (data_val.indexOf("__" + separador + "____") !== -1) {
				var data_val_dia = data_val.substr(0,2);
				element.val(data_val_dia + separador + AVUI_MES + separador + AVUI_ANY);
			} else if (data_val.indexOf("____") !== -1) {
				var data_val_dia_i_mes = data_val.substr(0,5);
				element.val(data_val_dia_i_mes + separador + AVUI_ANY);
			}
			// verificar
			var data_val = element.val(),
				data = data_val.split(separador),
				dataDia = parseInt(data[0], 10),
				dataMes = parseInt(data[1], 10),
				dataAnyo = parseInt(data[2], 10),
				data_error = false;
			
			if (dataAnyo == 0) {
				data_error = true;
			}
			if (dataMes > 12 || dataMes == 0) {
				data_error = true;
			}
			if (dataDia == 0 || (dataMes == 4 || dataMes == 6 || dataMes == 9 || dataMes == 11) && dataDia > 30) {
				data_error = true;
			} else if ((dataMes == 1 || dataMes == 3 || dataMes == 5 || dataMes == 7 || dataMes == 8 || dataMes == 10 || dataMes == 12) && dataDia > 31) {
				data_error = true;
			} else if (dataMes == 2) {
				var febreroDias = (dataAnyo % 4 != 0) ? 28 : 29;
				if (dataDia > febreroDias) {
					data_error = true;
				}
			}
			if (data_error) {
				element.closest("div").find(".imc-input-error").slideDown(200);
				element.val(AVUI_DIA + separador + AVUI_MES + separador + AVUI_ANY);
			} else {
				element.closest("div").find(".imc-input-error").slideUp(200);
			}
		};
		element.off('.dataCompletar').on('blur.dataCompletar', onBlur).mask("99" + separador + "99" + separador + "9999");
	});
	return this;
}
// /dataCompletar


// horaCompletar
$.fn.horaCompletar = function(options) {
	var settings = $.extend({
		contenidor: $("body"),
		separador: ":"
	}, options);
	this.each(function(){
		var element = $(this),
			separador = settings.separador,
			onBlur = function() {
				var hora_val = element.val(),
					hora = hora_val.split(separador),
					horaHora = parseInt(hora[0], 10),
					horaMinuts = parseInt(hora[1], 10);
				// completar
				if (hora_val.indexOf(separador+"__") != -1 && horaMinuts === "__") {
					horaMinuts = "00";
				}
				// verificar
				horaHora = (horaHora > 23) ? "23" : (horaHora === 0) ? "00" : horaHora;
				horaMinuts = (horaMinuts > 59) ? "59" : (horaMinuts === 0) ? "00" :  horaMinuts;
				// pintar
				element.val(horaHora + separador + horaMinuts);
			
			};
		element.off('.horaCompletar').on('blur.horaCompletar', onBlur).mask("99"+separador+"99");
	});
	return this;
}
// /horaCompletar


// missatge
var missatge_temps;
$.missatge = function(options){

	var settings = $.extend({
			tipus: "alerta", //missatge, alerta, confirmacio
			mode: "atencio", // executant, atencio, correcte, error, informacio, cancela
			cancela: "no",
			titol: "", // text
			text: "", // text amb etiquetes HTML
			AlAcceptar: function(opcions) { amaga(opcions); }, // funcio executada a l'acceptar, si es false simplement desapareix el missatge
			AlMostrar: function() {}, // funcio executada al mostrar el missatge,
			contenidor: $("body"),
			intrusiu: "si",
			segons: 20,
			nom: "",
			url: "",
			sempre: ""
		}, options),
		missatge_elm,
		contingut_elm,
		contingut_T,
		contingut_T_ie,
		codi_missatge,
		codi_modul,
		modul_L,
		focus_nodes,
		focus_nodes_size,
		focus_node,
		esIE6 = ($.browser.msie && $.browser.version < 7) ? true : false,
		b_elm,
		intrusiu = settings.intrusiu,
		segons = settings.segons,
		nom = settings.nom,
		url = settings.url,
		sempre = settings.sempre,
		no_intrusiu_T,
		
		scrollea = function() {
			
			missatge_elm
				.find(".intrusiu:first .contingut").css("top", ($(document).scrollTop() + contingut_T)  + "px").end()
				.find(".no-intrusiu:first").css("top", ($(document).scrollTop() + no_intrusiu_T)  + "px");
			
		},
	
		crea = function() {
			
			if ($("#missatge").size() === 0) {
				codi_missatge = "<div id=\"missatge\"></div>";
				$("body").append(codi_missatge);
			}
			missatge_elm = $("#missatge");
			
			if (intrusiu === "si" && missatge_elm.find(".intrusiu").size() === 0) {
				codi_missatge = "<div class=\"continguts intrusiu\">";
				codi_missatge += "<div class=\"finestra_fons\"></div>";
				codi_missatge += "<div class=\"contingut\" tabindex=\"-1\" role=\"alertdialog\"><div class=\"contingut_fons\"></div></div>";
				codi_missatge += "</div>";
			} else if (intrusiu === "no" && missatge_elm.find(".no-intrusiu").size() === 0) {
				codi_missatge = "<ul class=\"contingut no-intrusiu\"></ul>";
			}
			missatge_elm.append(codi_missatge);
			
			contingut_elm = (intrusiu === "si") ? missatge_elm.find(".intrusiu:first .contingut") : missatge_elm.find(".no-intrusiu:first");
			
			if (settings.tipus === "missatge" && settings.cancela === "si") {
				
				contingut_elm
					.off("click").on("click.cancela", ".bt-cancela", function() {
						b_elm = $(this);
						mode = (b_elm.parent().is("LI")) ? "intrusiu" : "no-intrusiu";
						settings.AlAcceptar({ boto: b_elm, mode: mode });
						amaga({ boto: b_elm, mode: "intrusiu" });
					});
				
			} else {
				
				contingut_elm
					.off("click").on("click.accepta", ".bt-accepta", function() {
						b_elm = $(this);
						mode = (b_elm.parent().is("LI")) ? "intrusiu" : "no-intrusiu";
						settings.AlAcceptar({ boto: b_elm, mode: mode });
						amaga({ boto: b_elm, mode: "intrusiu" });
					})
					.on("click.cancela", ".bt-cancela", function(e) {
						b_elm = $(this);
						amaga({ boto: b_elm, mode: "intrusiu" });
					});
			
			}
			
			pinta();
			
		},
		
		pinta = function() {
			
			if (intrusiu === "si") {
				codi_modul = "<div class=\"modul mo-" + settings.mode + "\">";
				codi_modul += "<div class=\"icona\"></div>";
				codi_modul += "<div class=\"info\">";
				codi_modul += "<p class=\"titol\">" + settings.titol + "</p>";
				codi_modul += (settings.text !== "") ? "<p class=\"text\">" + settings.text + "</p>" : "";
				if (settings.tipus !== "missatge") {
					codi_modul += "<ul class=\"botonera\">";
					codi_modul += "<li><a class=\"bt bt-accepta bt-important\" href=\"javascript:;\"><span class=\"i\">&nbsp;</span><span class=\"t\">" + txtAccepta + "</span></a></li>";
					codi_modul += (settings.tipus === "confirmacio") ? "<li><a class=\"bt-cancela\" href=\"javascript:;\">" + txtCancela + "</a></li>" : "";
					codi_modul += "</ul>";
				}
				if (settings.tipus === "missatge" && settings.cancela === "si") {
					codi_modul += "<ul class=\"botonera\">";
					codi_modul += "<li><a class=\"bt-cancela\" href=\"javascript:;\">" + txtCancela + "</a></li>";
					codi_modul += "</ul>";
				}
				codi_modul += "</div>";
				codi_modul += "</div>";
			} else {
				codi_modul = "<li tabindex=\"-1\" role=\"alertdialog\">";
				codi_modul += "<div class=\"contingut_fons\"></div>";
				codi_modul += "<div class=\"modul mo-" + settings.mode + "\">";
				codi_modul += "<div class=\"icona\"></div>";
				codi_modul += "<div class=\"info\">";
				codi_modul += "<p class=\"titol\">" + settings.titol + "</p>";
				codi_modul += (settings.text !== "") ? "<p class=\"text\">" + settings.text + "</p>" : "";
				codi_modul += "<p class=\"botonera\"><a class=\"bt bt-accepta\" href=\"javascript:;\" title=\"" + txtAccepta + "\"><span class=\"i\">&nbsp;</span></a><span class=\"temps\"></span></p>";
				codi_modul += "</div>";
				codi_modul += "</div>";
				codi_modul += "</li>";
			}
			
			contingut_elm
				.append(codi_modul).end()
				.find(".contingut_fons").css({ opacity: 0.7 });
				
			modul_elms = contingut_elm.find(".modul");
			
			mides();
			
			if (intrusiu === "si") {
				
				contingut_elm
					.find(".modul").css({ left: modul_L }).end()
					.css({ top: contingut_T_ie + "px" })
					.data("nom", nom)
					.data("url", url)
					.data("sempre", sempre);
					
			} else {
				
				//alert(nom);
				
				if (nom != "") {
					contingut_elm.find(".modul").each(function() {
						var modul_node = $(this);
						if (modul_node.data("nom") == nom) {
							amaga({ boto: modul_node.find("a:first"), mode: "no-intrusiu" });
						}
					 });
				}
				
				contingut_elm
					.find(".modul:last").data("temps", segons).data("nom", nom).end()
					.find(".modul:last span.temps").text(segons);
				if (missatge_temps) {
					clearInterval(missatge_temps);
				}
				missatge_temps = setInterval(function() { temps(); },1000);
				
			}
			
			mostra();
			
		},
		
		mides = function() {
			
			var finestra_W = $(window).width(),
				finestra_H = $(window).height(),
				contenidor_W = settings.contenidor.outerWidth(),
				contenidor_H = settings.contenidor.outerHeight(),
				fons_W = (finestra_W > contenidor_W) ? finestra_W : contenidor_W,
				fons_H = (finestra_H > contenidor_H) ? finestra_H : contenidor_H,
				contingut_H = contingut_elm.outerHeight(),
				modul_W = contingut_elm.find(".modul:eq(0)").outerWidth();
				
			missatge_elm.find(".finestra_fons:first").css({ display: "block", width: fons_W + "px", height: fons_H + "px", opacity: 0.7 });

			contingut_T = (finestra_H-contingut_H)/2;
			contingut_T_ie = (!esIE6) ? contingut_T : $(document).scrollTop() + contingut_T;
			modul_L = (finestra_W-modul_W)/2 + "px";
			if (missatge_elm.find(".no-intrusiu:first").size() > 0) {
				no_intrusiu_T = missatge_elm.find(".no-intrusiu:first").offset().top;
			}
			
		},
		
		mostra = function() {
			
			var modul_size = modul_elms.size();
			
			if (intrusiu === "si" && modul_size === 1) {
				
				contingut_elm
					.find(".modul:first").css({ top: "3em", opacity: 0 }).animate({ top: 0, opacity: 1 }, 500).end()
					.css({ display: "block", opacity: 0 }).animate({ opacity:1 }, 500, function() {
						resalta();
						$(window).on("resize.missatge",posiciona);
					});
				
				if (esIE6) {
					contingut_elm.css({ position: "absolute" });
					$(window).on("scroll.missatge",scrollea);
					settings.contenidor.find("select").visibilitatIE();
				}
				
				reestructura_intrusiu();
				
			} else if (intrusiu === "si" && modul_size > 1) {
				
				contingut_elm
					.find(".modul:first").animate({ top: "-2em", opacity: 0 }, 500).end()
					.find(".modul:last").css({ top: "3em", opacity:0 }).animate({ top: 0, opacity: 1 }, 500, function() {
						contingut_elm.find(".modul:first").remove();
						resalta();
					});
				
				reestructura_intrusiu();
				
			} else if (intrusiu === "no") {
			
				contingut_elm
					.find("li:last").css({ opacity: 0 }).animate({ top: "1em", opacity: 1 }, 500, function() {
						resalta();
					});
					
				if (esIE6) {
					contingut_elm.css({ position: "absolute" });
					$(window).on("scroll.missatge",scrollea);
				}
				
				reestructura_no_intrusiu();
			
			}
			
		},
		
		reestructura_intrusiu = function() {
			
			var modul_H = contingut_elm.find(".modul:last").outerHeight(),
				contingut_H_re = ($(modul_H).toEm() + 1.5) + "em";
			
			contingut_elm
				.find(".contingut_fons").animate({ height: contingut_H_re }, 300, function() {
					posiciona();
				}).end()
				.css({ height: contingut_H_re }, 300);
			
			focus_nodes = $("#missatge .intrusiu, #missatge .intrusiu a");
			focus_nodes_size = focus_nodes.size();
			focus_node = 0;
			$(document).on("keydown.missatge",tabula);
			
		},
		
		reestructura_no_intrusiu = function() {
			
			var modul_H = contingut_elm.find(".modul:last").outerHeight(),
				contingut_H_re = ($(modul_H).toEm() + 1.5) + "em";
			
			contingut_elm.find("li:last, .contingut_fons:last").css({ height: contingut_H_re });
			
		},
		
		resalta = function() {
			
			contingut_elm.focus();
			settings.AlMostrar();
			
		},
		
		amaga = function(opcions) {
			
			if (opcions.mode === "intrusiu") {
				
				var missatge_elm = $("#missatge"),
					nom = opcions.nom || "";
				
				if (nom === "" || missatge_elm.find(".intrusiu:first .contingut").data("nom") === nom) {
				
					missatge_elm
						.find(".intrusiu .modul:first").animate({ top: "-2em", opacity: 0 }, 500, function() {
							$(window).off("resize.missatge");
							$(document).off("keydown.missatge");
						}).end()
						.find(".intrusiu:first").fadeOut(500, function() {
							$(this).remove();
							if (missatge_elm.find("div").size() === 0) {
								missatge_elm.remove();
							}
						});
					
					if (esIE6) {
						$(window).off("scroll.missatge");
						settings.contenidor.find("select").visibilitatIE();
					}
				
				}
				
			} else {
			
				var missatge_elm = $("#missatge"),
					li_elm = opcions.boto.closest("li"),
					ul_elm = li_elm.parent("ul");
				li_elm.animate({ top: "-2em", opacity: 0, height: 0 }, 500, function() {
					li_elm.remove();
					if (ul_elm.find(".modul").size() === 0) {
						clearInterval(missatge_temps);
						missatge_elm.find(".no-intrusiu").remove();
						if (missatge_elm.find("div").size() === 0) {
							missatge_elm.remove();
						}
					}
				});
			
			}
			
		},
		
		posiciona = function() {

			mides();
			contingut_elm
				.find(".modul").animate({ left: modul_L }, 300).end()
				.animate({ top: contingut_T + "px" }, 300);
		
		},
		
		tabula = function(e) {
			
			if (e.keyCode === 9) { // 9 -> Tab
				focus_node++;
				if (focus_nodes_size === focus_node) {
					focus_node = 0;
				}
				focus_nodes.eq(focus_node).focus();
				e.preventDefault();
			}
			
		},
		
		temps = function() {
			
			missatge_elm.find(".no-intrusiu .modul").each(function() {
				
				var modul_node = $(this),
					modul_temps = modul_node.data("temps") - 1;
				
				modul_node.find("span.temps").text(modul_temps);
				
				if (modul_temps <= 0) {
					amaga({ boto: modul_node.find("a:first"), mode: "no-intrusiu" });
				} else {
					modul_node.data("temps", modul_temps);
				}
				
			 });
			
		};
	
	// inicia
	if (settings.mode === "cancela") {
		
		if ($("#missatge .continguts:first").hasClass("intrusiu")) {
			amaga({ boto: false, mode: "intrusiu", nom: nom });
		} else {
			$("#missatge .no-intrusiu:first .modul").each(function() {
				var modul_node = $(this);
				if (modul_node.data("nom") == settings.nom) {
					amaga({ boto: modul_node.find("a:first"), mode: "no-intrusiu" });
				}
			 });
		}
		
	} else {
		crea();
	}
	
};
// /missatge


// toEm
$.fn.toEm = function(options) {
	var settings = $.extend({
		etiqueta: 'body'
	}, options);
	var elmValor = parseInt(this[0],10);
	var capaTest = $('<div id="imc-toEm" style="display: none; font-size: 1.2em; font-family: arial, helvetica; margin: 0; padding:0; height: auto; line-height: 1; border:0;"> </div>').appendTo(settings.etiqueta);
	var capaValor = parseInt($("#imc-toEm").innerHeight(),10); 
	capaTest.remove();
	return (elmValor / capaValor);// + 'em';
};
// /toEm


// inputSelect
$.fn.inputSelect = function(options) {
	var settings = $.extend({
		selector: "unic", // unic, multiple
		alAcabar: function() { }
	}, options);
	this.each(function(){
		var element = $(this),
			element_a = element.find("a:first"),
			seleccionat_input = element.find("input:first"),
			seleccionat_span = element.find("span:first"),
			opcions_elm = element.find("ul:first"),
			onClick = function(e) {
				var elm = $(e.target);
				
				if (elm.is("A") && !elm.parent().hasClass("imc-select-seleccionat")) {
					
					seleccionat_input.val(elm.attr("data-value"));
					element_a.html( $("<span>").text( elm.text() ) );
					
					opcions_elm.find("li").removeClass("imc-select-seleccionat");
					elm.parent().addClass("imc-select-seleccionat");
					
					settings.alAcabar();
					
				}
				
			},
			onMouseEnter = function() {
				$(this).addClass("hover");
			},
			onMouseLeave = function() {
				$(this).removeClass("hover");
			},
			seleccionat = function() {
				//alert(seleccionat_input.val());
				opcions_elm.find("a[data-value=\"" +seleccionat_input.val()+ "\"]").parent().addClass("imc-select-seleccionat");
			};
			
		seleccionat();
		
		element_a.submenu({ submenu: element_a.closest("div").find(".imc-select-submenu:first"), posicio: "position", amplariaIgual: true, tancarAlClicarDins: true });
		
		opcions_elm.off('.inputSelect').on('click.inputSelect', onClick);
		if(typeof Modernizr !== "undefined" && !Modernizr.cssanimations) {
			opcions_elm.find("a").off('.inputSelect').on('mouseenter.inputSelect', onMouseEnter).on('mouseleave.inputSelect', onMouseLeave);
		}
		
	});
	return this;
}
// /inputSelect


function inputSelectSeleccionaValor(options) {
	var settings = $.extend({
			element: "",
			valor: ""
		}, options),
		element = settings.element,
		valor = settings.valor;
		
	if (element !== "" && valor !== "") {
		var text_nou, valor_nou;
		element
			.find(".imc-select-seleccionat:first").removeClass("imc-select-seleccionat").end()
			.find("ul a").each(function() {
				var elm_a = $(this),
					elm_a_valor = elm_a.attr("data-value");
					
				if (elm_a_valor === valor) {
					text_nou = elm_a.text();
					valor_nou = elm_a_valor;
					elm_a.parent().addClass("imc-select-seleccionat")
				}
			}).end()
			.find("a:first span").text(text_nou).end()
			.find("input:first").val(valor_nou);
	}
}


// inputSelectForm
$.fn.inputSelectForm = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			radio_elms = element.find("input[type=radio]"),
			button_elm = element.find("button"),
			onClick = function() {
				
				var radio_val = element.find("input.imc-dias").val(),
					select_elm = element.closest(".imc-select-form");
				
				select_elm
					.find("span:first").text( radio_val + " days" ).end()
					.find("input:first").val( radio_val ).end()
					.find("ul").removeAttr("class");
				
			},
			onChange = function() {
				
				var radio_elm = element.find("input[type=radio]:checked"),
					radio_val = radio_elm.val(),
					select_elm = element.closest(".imc-select-form");
				
				if (radio_val === "o") {
					
					element
						.find(".imc-set-duration").removeClass("imc-set-duration-on");
					select_elm
						.find("span:first").text( radio_elm.parent().text() ).end()
						.find("input:first").val( radio_val );
						
				} else {
					element.find(".imc-set-duration").addClass("imc-set-duration-on");
				}
				
			};
		radio_elms.off('.inputSelectForm').on('change.inputSelectForm', onChange);
		button_elm.off('.inputSelectForm').on('click.inputSelectForm', onClick);
		
	});
	return this;
}
// /inputSelectForm


// inputSelectBuscador
$.fn.inputSelectBuscador = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			seleccionat_input = element.find("input:first"),
			seleccionat_span = element.find("span:first"),
			opcions_elm = element.find("div:first"),
			cercador_elm = element.find(".imc-select-busca:first"),
			onClick = function(e) {
				var elm = $(e.target);
				
				if (elm.is("A") || (elm.is("SPAN") && elm.parent().is("A")) || (elm.is("STRONG") && elm.parent().is("A"))) {
					
					var elm_a = (elm.is("A")) ? elm : elm.parent();
					
					seleccionat_input.val(elm_a.attr("data-value"));
					seleccionat_span.text(elm_a.attr("data-text") + " " + elm_a.find("span:last").text());
					
					var att_class = (elm_a.attr("class")) ? elm_a.attr("class") : "";
					seleccionat_span.attr("class", att_class);

					opcions_elm.find("li").removeClass("imc-select-seleccionat");
					elm_a.parent().addClass("imc-select-seleccionat");
					
					elm_a
						.closest(".imc-submenu-on").removeClass("imc-submenu-on")
						.parent().find(".on:first").removeClass("on");
						
					var td_cantidad = elm_a.closest(".imc-tr").find(".imc-cantidad:first");
					
					td_cantidad
						.find("input:first").val( elm_a.attr("data-quantity") ).end()
						.removeClass("imc-cantidad-off");
					
					inputSelectSeleccionaValor({ element: td_cantidad.find(".imc-select:first"), valor: elm_a.attr("data-type") });
					
					elm_a.closest(".imc-tr").find(".imc-opcions-off").removeClass("imc-opcions-off");
				}
				
			},
			onMouseEnter = function() {
				$(this).addClass("hover");
			},
			onMouseLeave = function() {
				$(this).removeClass("hover");
			},
			onKeyUp = function() {
				
				var input_elm = $(this),
					input_val = input_elm.val();
				
				if (input_val.length >= 3) {
					filtra(input_val);	
				} else {
					mostraTot();	
				}
				
			},
			filtra = function(input_val) {
				
				opcions_elm.find("a").each(function(i) {
					var elm = $(this),
						elm_data_text = elm.attr("data-text"),
						elm_strong = elm.find("strong");
					
					if (elm_data_text.indexOf(input_val) !== -1) {
						elm_data_text = elm_data_text.replace(input_val, "<span class=\"imc-resaltado\">" + input_val + "</span>");
						elm_strong.html(elm_data_text);
					} else {
						elm.parent().addClass("imc-invisible");	
					}
					
				});
				
			},
			mostraTot = function() {
				opcions_elm.find("li").each(function() {
					var elm = $(this),
						elm_a = elm.find("a"),
						elm_strong = elm_a.find("strong");
					
					elm.removeClass("imc-invisible");
					elm_strong.text( elm_a.attr("data-text") );
					
				});
			};
		
		opcions_elm.off('.inputSelectBuscador').on('click.inputSelectBuscador', onClick);
		cercador_elm.off('.inputSelectBuscador').on('keyup.inputSelectBuscador', onKeyUp);
		if(typeof Modernizr !== "undefined" && !Modernizr.cssanimations) {
			opcions_elm.find("a").off('.inputSelectBuscador').on('mouseenter.inputSelectBuscador', onMouseEnter).on('mouseleave.inputSelectBuscador', onMouseLeave);
		}
		
	});
	return this;
}
// /inputSelectBuscador


// aTip
$.fn.aTip = function(options) {
	var settings = $.extend({
		element: "",
		posicio: "position"
	}, options);
	this.each(function(){
		var element = $(this),
			tip_elm = element.find("p"),
			onMouseEnter = function() {
				var finestra_H = $(window).height(),
					element_T = (settings.posicio == "position") ? element.position().top : element.offset().top,
					element_L = (settings.posicio == "position") ? element.position().left : element.offset().left,
					element_H = element.outerHeight(),
					tip_H = tip_elm.outerHeight(),
					tip_T = element_T + element_H,
					tip_T = (tip_T + tip_H > finestra_H) ? element_T - tip_H : tip_T;

				tip_elm.css({ top: tip_T + "px", left: element_L + "px" }).fadeIn(200);
			},
			onMouseLeave = function() {
				tip_elm.fadeOut(200);
			};
		element.off('.aTip').on('focus.aTip', onMouseEnter).on('mouseenter.aTip', onMouseEnter).on('blur.aTip', onMouseLeave).on('mouseleave.aTip', onMouseLeave);
	});
	return this;
}
// /aTip


// placeHolder
$.fn.placeHolder = function(options) {
	var settings = $.extend({
		element: "",
		posicio: "position"
	}, options);
	this.each(function(){
		var element = $(this),
			placeholder_text = element.attr("placeholder"),
			el_control = element.parent(),
			el_placeholder_simulat = false,
			crea = function() {
				$("<input>")
					.attr("type", "text")
					.val(placeholder_text)
					.addClass("imc-placeholder-simulat")
					.on('click.placeHolder', onClick)
					.appendTo( el_control );
				el_placeholder_simulat = el_control.find(".imc-placeholder-simulat:first")
			},
			onClick = function() {
				el_placeholder_simulat.addClass("imc-placeholder-simulat-off");
				if (element.hasClass("imc-placeholder-off")){
					element.val("").removeClass("imc-placeholder-off").focus();
				}
			},
			onBlur = function() {
				if (element.val() === ""){
					el_placeholder_simulat.removeClass("imc-placeholder-simulat-off");
					element.addClass("imc-placeholder-off");
				}
			};
		//
		crea();
		//
		element
			.addClass("imc-placeholder-off")
			.data("placeHolder", placeholder_text)
			.on('blur.placeHolder', onBlur);
	});
	return this;
}
// /placeHolder


// inputNumero
$.fn.inputNumero = function(options) {
	var settings = $.extend({
		element: "",
		posicio: "position"
	}, options);
	this.each(function(){
		var element = $(this),
			element_maxlength = element.attr("maxlength") || "",
			onKeydown = function(event) {
				
				if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
					 	event.keyCode == 190 || // decimal (.)
						 
		             // Allow: Ctrl+A
		            (event.keyCode == 65 && event.ctrlKey === true) || 
		             // Allow: home, end, left, right
		            (event.keyCode >= 35 && event.keyCode <= 39)) {
		                 // let it happen, don't do anything
		                 return;
		        }
		        else {
		            // Ensure that it is a number and stop the keypress
		            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
		                event.preventDefault(); 
		            }   
		        }
			};

		if (Modernizr.inputtypes.number && element_maxlength !== "") {
			element.addClass("no-type-number-"+element_maxlength);
		} else {
			var html = "<div class=\"imc-f-number-botones\">"
						+ "<p>"
							+ "<a href=\"#\" class=\"imc-suma\" title=\"Suma\">Suma</a>"
							+ "<a href=\"#\" class=\"imc-resta\" title=\"Resta\"><span>Resta</span></a>"
						+ "</p>";
					+ "</div>";
			//element.before(html);
		}

		element.off('.inputNumero').on('keydown.inputNumero', onKeydown);
	});
	return this;
}
// /inputNumero


// imcF_getMultipleScript
$.imcA_scripts = new Array();
$.imcF_getMultipleScript = function(a, b) {
	carrega = function(c) {
		scripts_length = $.imcA_scripts.length;
		esta_en_scripts = false;
		if (scripts_length > 0) {
			for (i=0; i<scripts_length; i++) {
				if (c == $.imcA_scripts[i]) {
					esta_en_scripts = true;
				}
			}
		}
		if (esta_en_scripts) {
			executa(c);
		} else {
			$.getScript(c, function() {
				executa(c);
			});
		}
	};
	executa = function(c) {
		$.imcA_scripts.push(c);
		a.shift();
		if (a.length != 0) {
			carrega(a[0]);
		} else {
			if (b) {
				b();
			}
		}
	}
	carrega(a[0]);
};
// /imcF_getMultipleScript


// input cursor al final del text
function inputCursor(el) {
    if (typeof el.selectionStart == "number") {
        el.selectionStart = el.selectionEnd = el.value.length;
    } else if (typeof el.createTextRange != "undefined") {
        el.focus();
        var range = el.createTextRange();
        range.collapse(false);
        range.select();
    }
}
// /input cursor al final del text


// ajuda
$.fn.ajuda = function(opcions) {
	var settings = $.extend({
		element: ""
	}, opcions);
	this.each(function(){
		var element = $(this),
			ajuda_inicial = $imc_ajuda.hasClass("imc-ajuda-desactivada") ? "off" : "on",
			txtAjudaInfo,
			txtAjudaBoto,
			ajuda_data;
			onClick = function() {
				
				if ($imc_ajuda.data("ajuda") === "off") {
					$imc_ajuda.removeClass("imc-ajuda-desactivada");
					$imc_formulari.ajudaCamp();
					txtAjudaInfo = txtAjuda + " " + txtActivada;
					txtAjudaBoto = txtDesactiva;
					ajuda_data = "on";
				} else {
					$imc_ajuda.addClass("imc-ajuda-desactivada");
					$imc_formulari.off('.ajudaCamp');
					txtAjudaInfo = txtAjuda + " " + txtDesctivada;
					txtAjudaBoto = txtActiva;
					ajuda_data = "off";
				}
				
				if (!Modernizr.csstransforms) {
					$imc_ajuda.fadeOut(200, function() {
						canvia();
						$imc_ajuda.fadeIn(200);
					});
				} else {
					canvia();
				}
				
			},
			canvia = function() {
				
				$imc_ajuda
					.find("p").text(txtAjudaInfo).end()
					.find("a").text(txtAjudaBoto).end()
					.data("ajuda", ajuda_data)
					.addClass("flip animated");
				
				var espera = window.setTimeout( function(){
						$imc_ajuda.removeClass("flip animated")
					},
					1300
				);
				
			};
			
		// verifica
		if (!$imc_ajuda.hasClass("imc-ajuda-desactivada")) {
			$imc_formulari.ajudaCamp();
			$imc_ajuda.data("ajuda", "on");
		}
		// inicia
		$imc_ajuda.data("ajuda", ajuda_inicial);
		element.off('.ajuda').on('click.ajuda', onClick);
	});
	return this;
}
// /ajuda


// ajudaCamp
$.fn.ajudaCamp = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			ajuda_elm = false,
			ajuda_anterior_elm = false,
			onMouseEnter = function() {
				var elm = $(this);
				ajuda_elm = elm.find(".imc-el-ajuda:first");
				if (ajuda_elm.length && ajuda_elm.html() !== "") {
					
					onMouseLeave();
					
					ajuda_anterior_elm = ajuda_elm;
					
					var window_W = $(window).width(),
						window_H = $(window).height(),
						window_scroll_T = $(window).scrollTop(),
						elm_T = elm.offset().top,
						elm_L = elm.offset().left,
						elm_H = elm.outerHeight(),
						elm_label = elm.find(".imc-el-etiqueta:first"),
						elm_label_W = elm_label.outerWidth(),
						elm_label_H = elm_label.outerHeight(),
						elm_control = elm.find(".imc-el-control:first"),
						elm_control_W = elm_control.outerWidth(),
						elm_control_H = elm_control.outerHeight(),
						ajuda_W = ajuda_elm.outerWidth(),
						ajuda_H = ajuda_elm.outerHeight();
					
					var ajuda_T = elm_T-ajuda_H-5,
						ajuda_L = elm_L,
						cueta_html = "<div class=\"imc-el-aj-pestanya\"><span></span></div>";
					
					if (!ajuda_elm.find(".imc-el-aj-pestanya").length) {
						ajuda_elm.append(cueta_html);
					}
					
					if ((elm_L+ajuda_W) > window_W) {
						ajuda_L = elm_L - ajuda_W + elm_control_W;
						ajuda_elm.find(".imc-el-aj-pestanya").css({ left: (ajuda_W-elm_label_W+5)+"px" });
					} else {
						ajuda_elm.find(".imc-el-aj-pestanya").removeAttr("style");
					}
					
					var ajuda_T_inici = ajuda_T+5;
					
					if (window_scroll_T > ajuda_T) {
						ajuda_T = elm_T + elm_H;
						ajuda_T_inici = ajuda_T-5;
						ajuda_elm.find(".imc-el-aj-pestanya").addClass("imc-el-aj-pestanya-dalt");
					} else {
						ajuda_elm.find(".imc-el-aj-pestanya").removeClass("imc-el-aj-pestanya-dalt");
					}
					
					ajuda_elm
						.css({ top: ajuda_T_inici+"px", left: ajuda_L+"px", opacity: 0 })
						.addClass("imc-el-ajuda-on")
						.animate({ top: ajuda_T+"px", opacity:1 }, 200)
						.off(".ajudaCamp")
						.on("mouseenter.ajudaCamp", onMouseLeave);
					
				}
			},
			onMouseLeave = function() {
				if (ajuda_elm) {
					ajuda_elm.stop();
					if (ajuda_anterior_elm) {
						ajuda_anterior_elm.removeClass("imc-el-ajuda-on").removeAttr("style");
					}
				}
			};
		
		// inicia
		element
			.off('.ajudaCamp')
			.on('mouseenter.ajudaCamp', ".imc-element", onMouseEnter)
			.on('focus.ajudaCamp', ".imc-element", onMouseEnter)
			.on('mouseleave.ajudaCamp', ".imc-element", onMouseLeave)
			.on('blur.ajudaCamp', ".imc-element", onMouseLeave);
	});
	return this;
}
// /ajudaCamp


// mostrarCapaEnviando
function mostrarCapaEnviando() {
	
	var missatge_titol_elm = $("<p>").text(txtEnviantDades).addClass("imc-mi-titol"),
		missatge_text_elm = $("<p>").text(txtEspere).addClass("imc-mi-text"),
		missatge_elm = $("<div>").html( $("<div>").addClass("imc-missatge-contingut").append(missatge_titol_elm).append(missatge_text_elm) ).attr("id", "imc-missatge").addClass("imc-missatge imc-mi imc-mi-enviant"),
		missatge_fons_elm = $("<div>").attr("id", "imc-missatge-fons").addClass("imc-missatge-fons");
	
	$("body").append(missatge_fons_elm).append(missatge_elm);
	
	$("#imc-missatge").fadeIn(200);
	
	var window_H = $(window).height(),
		contenidor_H = $imc_contenidor.outerHeight();
	
	if (contenidor_H > window_H) {
		$("#imc-missatge-fons").css("height", contenidor_H+"px");
	}
	
}
// /mostrarCapaEnviando


// ocultarCapaEnviando
function ocultarCapaEnviando() {
	
	$("#imc-missatge").stop().fadeOut(200, function() {
		$(this).remove();
	});
	$("#imc-missatge-fons").remove();
	
}
// /ocultarCapaEnviando


// normalize
var normalize = (function() {
	
  var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
      to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
      mapping = {};
 
  for(var i = 0, j = from.length; i < j; i++ )
      mapping[ from.charAt( i ) ] = to.charAt( i );
 
  return function( str ) {
      var ret = [];
      for( var i = 0, j = str.length; i < j; i++ ) {
          var c = str.charAt( i );
          if( mapping.hasOwnProperty( str.charAt( i ) ) )
              ret.push( mapping[ c ] );
          else
              ret.push( c );
      }
      return ret.join( '' );
  }
 
})();
// /normalize
