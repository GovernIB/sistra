
// dadesFormateig
function dadesFormateig(options) {

	var settings = $.extend({
			element: $("body")
		}, options),
		element = settings.element;
	
	// data
	if (!Modernizr.inputtypes.date) {
		element.find("input[type=date]").addClass("imc-no-type-date").dataCompletar().datepicker({ dateFormat: "dd/mm/yy", changeMonth: true, changeYear: true });
	}
	
	// hora
	if (!Modernizr.inputtypes.time) {
		element.find("input[type=time]").addClass("imc-no-type-time").horaCompletar();
	}
	
	// placeHolder
	if (!Modernizr.input.placeholder) {
		element.find("input.imc-placeholder").placeHolder();
	}
	
	// generatedcontent
	if (!Modernizr.generatedcontent) {
		element.find(".imc-el-obligatori label").prepend("<span class=\"imc-obligatori\">*</span>");
	}
	
	// per a tothom
	element
		.find("div.imc-opcions:not(.imc-opcions-no-events)").inputSelect().end()
		.find("*[title]").title().end()
		.find("input[type=number]").inputNumero().end()
		.find("h4.imc-seccio").each(function() { // select.a amb opcions
			var elm = $(this),
				elm_marca = elm.find("span.imc-se-marca"),
				elm_marca_H = elm_marca.height(),
				elm_titol = elm.find("span.imc-se-titol"),
				elm_titol_H = elm_titol.height();
			if (elm_marca_H !== elm_titol_H) {
				elm_marca.css("height", elm_titol_H);
			}
		}).end()
		.find("a:not([href])").attr("tabindex", "0").attr("href", "javascript:;").end()
		.find(".imc-el-import").immport();
	
}
// /dadesFormateig


// arbre
$.fn.arbre = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			onClick = function(e) {
				var elm = $(e.target);
				if (elm.is("P") || (elm.is("span") && elm.hasClass("imc-arbre-lectura"))) {
					var p_elm = elm.is("P") ? $(this) : $(this).closest("p"),
						li_elm = p_elm.parent();
					li_elm.find("ul:first").slideToggle();
					p_elm.toggleClass("imc-amb-fills-on");
				}	
			};
		// elements
		element.find("li").each(function() {
			var li_elm = $(this),
				li_ul_elm = li_elm.find("ul"),
				li_ul_elm_size = li_ul_elm.length;
			
			if (li_ul_elm_size > 0) {
				li_elm.find("p:first").addClass("imc-amb-fills").off('.arbre').on('click.arbre', onClick);
			}
			
		});
	});
	return this;
}
// /arbre


// taula
$.fn.taula = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			element_taula_elm = element.find(".imc-el-taula-elements:first"),
			onClick = function(e) {
				var elm = $(e.target);
				if (elm.is("P")) {
					var p_elm = $(this),
						li_elm = p_elm.parent();
					li_elm.find("ul:first").slideToggle();
					p_elm.toggleClass("imc-amb-fills-on");
				}	
			};
		// sense elements
		if (element_taula_elm.find("p").length) {
			element.find("button:not(:eq(0))").attr("disabled","disabled");
		}
		// botonera lateral
		if (element.hasClass("imc-el-taula-botonera-lateral")) {
			element.find("button").each(function() {
				var bt_elm = $(this),
					bt_text = bt_elm.find("span").text();
				bt_elm.attr("title", bt_text).append("<span class=\"imc-bt-icona\">&nbsp;</span>")
			});
		}
	});
	return this;
}
// /taula


// immport
$.fn.immport = function(options) {
	var settings = $.extend({
		element: ""
	}, options);
	this.each(function(){
		var element = $(this),
			el_immport_elm = element.find("input:first"),
			onBlur = function() {
				el_immport_elm.parseNumber({format: "#,##0.00", locale:"es"});
   			el_immport_elm.formatNumber({format: "#,##0.00", locale:"es"});	
			};
		// sense elements
		el_immport_elm.off(".immport").on("blur.immport", onBlur);
	});
	return this;
}
// /immport


// funcions comuns

var el_element, el_tipus, el_camps;

function control_verifica(name) {
	
	el_element = $imc_formulari.find(".imc-el-name-"+name+":first");
	el_tipus = el_element.attr("data-type");
	el_camps = $imc_formulari.find(document.getElementsByName(name));
	el_camps_primer = el_camps.filter(":eq(0)");

	return el_element, el_tipus, el_camps, el_camps_primer;
	
}

function control_select(name, valor, neteja) {
	
	control_verifica(name);
		
	if (!el_camps.length) {
		return false;
	}
		
	if ((el_tipus === "text" || el_tipus === "textarea" || el_tipus === "time" || el_tipus === "date" || el_tipus === "date-time")) {
		el_camps_primer.val(valor);
	} else if (el_tipus === "select") {
		var elm_seleccionat = el_element.find(".imc-select-seleccionat:first"),
			elm_seleccionat_index = el_element.find("ul li").index(elm_seleccionat),
			text_nou,
			valor_nou;
		elm_seleccionat.removeClass("imc-select-seleccionat");
		el_element
			.find("ul a").each(function() {
				var elm_a = $(this),
					elm_a_valor = elm_a.attr("data-value");
				if (elm_a_valor === valor) {
					text_nou = elm_a.text();
					valor_nou = elm_a_valor;
					elm_a.parent().addClass("imc-select-seleccionat");
				}
			}).end()
			.find("span:first").text(text_nou).end()
			.find("input:first").val(valor_nou);
		if (!el_element.find(".imc-select-seleccionat").length) {
			el_element.find("ul li:eq(" + elm_seleccionat_index + ")").addClass("imc-select-seleccionat");
		}
	} else if (el_tipus === "check-list" || el_tipus === "check-list-scroll" || el_tipus === "tree") {
		if (typeof neteja === "undefined") {
			neteja = false;
		}
		if (neteja) {
			el_camps.attr("checked", false);
		}
		valor_es_string = (typeof valor === "string") ? true : false;
		if (valor_es_string) {
			el_camps.filter("[value=" + valor + "]").attr("checked", true);
		} else {
			var valor_size = valor.length;
			for (var i=0; i<valor_size; i++) {
				el_camps.filter("[value=" + valor[i] + "]").attr("checked", true);
			}
		}
	} else if (el_tipus === "radio-list" || el_tipus === "radio-list-scroll") {
		el_camps.filter("[value=" + valor + "]").attr("checked", true);
	} else if (el_tipus === "check") {
		if (valor) {
			el_camps_primer.attr("checked", true);
		} else {
			el_camps_primer.attr("checked", false);
		}
	}
}

function control_disabled(name, disabled) {
	
	control_verifica(name);
		
	if (!el_camps.length) {
		return false;
	}
	
	if (disabled) {
		el_camps.attr("disabled", "disabled");
		if (el_tipus === "select") {
			el_element.find(".imc-select:first").addClass("imc-select-deshabilitat");
		} else if (el_tipus === "date-time") {
			el_element.find("input:last").attr("disabled", "disabled");
		} else if (el_tipus === "table") {
			el_element.find("button").attr("disabled", "disabled");
		}
	} else {
		el_camps.removeAttr("disabled");
		if (el_tipus === "select") {
			el_element.find(".imc-select:first").removeClass("imc-select-deshabilitat");
		} else if (el_tipus === "date-time") {
			el_element.find("input:last").removeAttr("disabled");
		} else if (el_tipus === "table") {
			el_element.find("button").removeAttr("disabled");
		}
	}
}

function control_readOnly(name, esLectura) {
	
	control_verifica(name);
		
	if (!el_camps.length) {
		return false;
	}
	
	if (el_tipus === "date-time") {
		
		var el_control = el_element.find(".imc-el-control:first");
		
		el_control
			.find("span").remove().end()
			.find("input").each(function() {
				var in_elm = $(this),
					in_class = (in_elm.is("[type=date]")) ? "imc-data-lectura" : "imc-hora-lectura"
					in_valor = (in_elm.val() === "") ? "&nbsp;" : in_elm.val();
				
				if (esLectura) {
					in_elm.before( $("<span>").html(in_valor).addClass(in_class) ).addClass(in_class);
				} else {
					in_elm.removeClass(in_class)
				}
				
			});
	
	} else if (el_tipus === "date" || el_tipus === "time") {
		
		var el_control = el_element.find(".imc-el-control:first"),
			el_class = (el_tipus === "date") ? "imc-data-lectura" : "imc-hora-lectura",
			el_valor = (el_camps_primer.val() === "") ? "&nbsp;" : el_camps_primer.val();
		
		if (esLectura) {
			el_control
				.find("span").remove().end()
				.find("input").attr("readonly", "readonly").before( $("<span>").html(el_valor).addClass(el_class) ).addClass(el_class);
		} else {
			el_control
				.find("input").removeAttr("readonly").removeClass(el_class).end()
				.find("span").remove();
		}
		
	} else if (el_tipus === "tree") {
		
		el_element.find("p").each(function() {
			var elm = $(this),
				elm_label = elm.find("label").text(),
				elm_seleccionat = elm.find("input").is(":checked");
				
			if (esLectura) {
				elm
					.find("input, label").addClass("imc-arbre-lectura").end()
					.find("span").remove().end()
					.append( $("<span>").html(elm_label).addClass("imc-arbre-lectura") );
				if (elm_seleccionat) {
					elm.addClass("imc-arbre-input-seleccionat");
				}
			} else {
				elm
					.find("input, label").removeClass("imc-arbre-lectura").end()
					.find("span").remove().end()
					.removeClass("imc-arbre-input-seleccionat");
			}
			
		});
	
	} else if (el_tipus === "table") {
		
		if (esLectura) {
			el_element
				.find("button").attr("disabled", "disabled").end()
				.find("input:radio").hide().before("<span class=\"imc-radio-lectura\">&nbsp;</span>");
		} else {
			el_element.find("button").removeAttr("disabled").end()
				.find("input:radio").show().end()
				.find(".imc-radio-lectura").remove();
		}
	
	} else if (el_tipus === "text" || el_tipus === "textarea") {
		
		if (esLectura) {
			el_camps_primer.attr("readonly", "readonly");
		} else {
			el_camps_primer.removeAttr("readonly");
		}
		
	} else if (el_tipus === "radio-list" || el_tipus === "radio-list-scroll") {
	
		var elm_UL = el_element.find("ul:first"),
			escritura = function() {
				elm_UL
					.removeClass("imc-sols-lectura")
					.closest(".imc-element").find("span.imc-sols-lectura, .imc-el-multiple-lectura").remove();
			};
		
		if (el_camps.filter(":checked").length) {
			
			var elm_checked_ID = el_camps.filter(":checked").attr("id"),
				label_text = (el_tipus === "radio-list-scroll") ? el_camps.filter(":checked").parent().text() : elm_UL.find("label[for=" + elm_checked_ID + "]").text();
			
			if (esLectura && !elm_UL.closest(".imc-element").find("span.imc-sols-lectura").length) {
																	
				var html_lectura = $("<span>").html(label_text).addClass("imc-sols-lectura");
				if (el_tipus === "radio-list-scroll") {
					html_lectura = $("<div>").html( html_lectura ).addClass("imc-el-multiple-lectura");
				}
				elm_UL.addClass("imc-sols-lectura").before(html_lectura);
				
			} else if (!esLectura) {
				escritura();
			}
		
		} else {
			if (esLectura) {
			
				var html_lectura = $("<span>").html("&nbsp;").addClass("imc-sols-lectura imc-sols-lectura-cap");
				if (el_tipus === "radio-list-scroll") {
					html_lectura = $("<div>").addClass("imc-el-multiple-lectura");
				}
				elm_UL.addClass("imc-sols-lectura").before(html_lectura);
			
			} else {
				escritura();
			}
		}
		
	} else if (el_tipus === "check-list" || el_tipus === "check-list-scroll") {
		
		var elm_UL = el_element.find("ul:first");
		
		if (esLectura && !elm_UL.closest(".imc-element").find("span.imc-sols-lectura").length) {
			
			elm_UL.addClass("imc-sols-lectura");
			
			if (el_tipus === "check-list-scroll") {
				elm_UL.before($("<div>").html( html_lectura ).addClass("imc-el-multiple-lectura"));
				var multiple_DIV = elm_UL.parent().find(".imc-el-multiple-lectura:first");
			}
			
			if (el_camps.filter(":checked").length) {
				
				el_camps.filter(":checked").each(function() {
					var elm = $(this),
						elm_checked_ID = elm.attr("id"),
						label_text = (el_tipus === "check-list-scroll") ? elm.parent().text() : elm_UL.find("label[for=" + elm_checked_ID + "]").text();
					
					if (el_tipus === "check-list-scroll") {
						multiple_DIV.append( $("<span>").html(label_text).addClass("imc-sols-lectura") );
					} else {
						elm_UL.before( $("<span>").html(label_text).addClass("imc-sols-lectura") );
					}
					
				});
			
			} else {
				
				if (el_tipus !== "check-list-scroll") {
					elm_UL.before( $("<span>").html("&nbsp;").addClass("imc-sols-lectura imc-sols-lectura-cap") );
				}
				
			}
			
		} else if (!esLectura) {
			elm_UL
				.removeClass("imc-sols-lectura")
				.closest(".imc-element").find("span.imc-sols-lectura, .imc-el-multiple-lectura").remove();
		}
		
	} else if (el_tipus === "check") {
		
		var check_class = (el_camps_primer.is(":checked")) ? "imc-check-lectura" : "imc-check-lectura imc-check-lectura-noSel";
		
		if (esLectura) {
			el_element.find("label:first").addClass(check_class);
		} else {
			el_element.find("label:first").removeAttr("class");
		}
		
	}
	
	if (el_tipus === "select" && esLectura) {
		el_element.find(".imc-select:first").addClass("imc-select-lectura");
	} else if (el_tipus === "select" && !esLectura) {
		el_element.find(".imc-select:first").removeClass("imc-select-lectura");
	}
	
	
}

function control_refill(name, valors) {
	
	control_verifica(name);
	
	if (el_tipus === "check-list-scroll" || el_tipus === "radio-list-scroll") {
	
		var el_UL = el_element.find("ul:first"),
			el_input_tipus = (el_tipus === "check-list-scroll") ? "checkbox" : "radio",
			canvia = function() {
				el_UL.find("li").remove();
				$(valors).each(function(i) {
					var elm = this,
						elm_input = $("<input>").attr({ type: el_input_tipus, id: name+"_"+i, name: name, value: elm.valor } ),
						elm_span = $("<span>").text(elm.etiqueta),
						elm_linea = $("<li>").append( $("<label>").append( elm_input ).append( elm_span ) );
					
					el_UL.append( elm_linea );
					
					if (elm.defecto) {
						el_UL.find("input:last").attr("checked", "checked");
					}
					
				});
				el_UL.hide().fadeIn(200);
			};
		//
		canvia();
	
	} else if (el_tipus === "tree") {
	
		var el_UL = el_element.find("ul:first"),
			canvia = function() {
				el_UL.find("li").remove();
				$(valors).each(function(i) {
					var elm = this,
						elm_input = $("<input>").attr({ type: "checkbox", id: name+"_"+i, name: name, value: elm.valor } ),
						elm_label = $("<label>").attr("for",name+"_"+i).text(elm.etiqueta),
						elm_linea = $("<li>").html( $("<p>").append( elm_input ).append( elm_label ) ).attr("data-valor", elm.valor);
					
					if (typeof elm.parentValor !== "undefined" && elm.parentValor !== "") {
						elm_LI = el_UL.find("li[data-valor=" + elm.parentValor + "]");
						if (!elm_LI.find("ul").length) {
							elm_LI.append("<ul>");
						}
						elm_UL_a_afegir = elm_LI.find("ul:first");
						elm_LI.find("p:first").html( elm_LI.find("p:first").text() );
					} else {
						elm_UL_a_afegir = el_UL;
					}
					
					elm_UL_a_afegir.append( elm_linea );
					
				});
				el_UL.hide().fadeIn(200).arbre();
			};
		//
		canvia();
	
	} else if (el_tipus === "select") {
	
		var elm_select = el_element.find(".imc-select:first"),
			canvia = function() {
				
				el_element.find(".imc-el-control-info").remove();
				elm_select
					.find("a:first").html( $("<span>").html("&nbsp;") ).end()
					.find("input:first").val("");
				
				if (!elm_select.find("ul").length || !elm_select.find("a").length) {
					elm_select
						.append( $("<a>").html( $("<span>") ).attr({ tabindex: "0" }).addClass("imc-select") )
						.append( $("<input>").attr({ type: "hidden", name: name }) )
						.append( $("<ul>") );
				} else {
					el_element.find("ul:first").addClass("imc-select-per-esborrar").before( $("<ul>") );
				}
				
				var elm_UL = el_element.find("ul:first");
				
				//elm_UL.find("li").remove();
				$(valors).each(function(i) {
					
					var elm = this,
						elm_a = $("<a>").text(elm.etiqueta).attr("data-value", elm.valor),
						elm_linea = $("<li>").append( elm_a );
					
					elm_UL.append( elm_linea );
					
					if (elm.defecto) {
						elm_UL.find("li:last").addClass("imc-select-seleccionat");
						elm_select
							.find("a:first").html( $("<span>").text(elm.etiqueta) ).end()
							.find("input:first").val(elm.valor);
					}
				});
				
				elm_select.hide();
				mostra();
				el_element.find(".imc-select-per-esborrar").remove();
				
			},
			mostra = function() {
				elm_select.fadeIn(200, function() {
					elm_select.find("a:first").focus();
				});
				dadesFormateig({ element: el_element });
			};
		//
		canvia();
		
	} else if (el_tipus === "check-list" || el_tipus === "radio-list") {
		
		var el_UL = el_element.find("ul:first"),
			el_input_tipus = (el_tipus === "check-list") ? "checkbox" : "radio",
			canvia = function() {
				el_UL.find("li").remove();
				$(valors).each(function(i) {
					var elm = this,
						elm_input = $("<input>").attr({ type: el_input_tipus, id: name+"_"+i, name: name, value: elm.valor } ),
						elm_label = $("<label>").attr("for",name+"_"+i).text(elm.etiqueta),
						elm_linea = $("<li>").append( elm_input ).append( elm_label );
					
					el_UL.append( elm_linea );
					
					if (elm.defecto) {
						el_UL.find("input:last").attr("checked", "checked");
					}
					
				});
				el_UL.hide().fadeIn(200);
			};
			
		//
		canvia();
		
	}
			
}


function control_values(name) {
	
	control_verifica(name);
		
	if (!el_camps.length) {
		return false;
	}
	
	if (el_tipus === "date-time") {
		
		return {
			etiqueta: el_element.find("label[for=" + el_camps_primer.attr("id") + "]").text(),
			valor: el_camps_primer.val() + " " + el_element.find("input:last").val()
		};
		
	} else if (el_tipus === "text" || el_tipus === "textarea" || el_tipus === "date" || el_tipus === "time") {
		
		return { etiqueta: el_element.find("label[for=" + el_camps_primer.attr("id") + "]").text(), valor: el_camps_primer.val() };
		
	} else if (el_tipus === "radio-list" || el_tipus === "radio-list-scroll") {
		
		var input_seleccionat = el_camps.filter(":checked");
		if (input_seleccionat.length) {
			var label_text = (input_seleccionat.parent().is("LABEL")) ? input_seleccionat.parent().text() : el_element.find("label[for=" + input_seleccionat.attr("id") + "]").text(),
				a_retornar = { etiqueta: label_text, valor: input_seleccionat.val() };
		} else {
			var a_retornar = { etiqueta: "", valor: "" };
		}
		return a_retornar;
		
	} else if (el_tipus === "check-list" || el_tipus === "check-list-scroll" || el_tipus === "tree") {
		
		var input_seleccionat = el_camps.filter(":checked"),
			a_retornar = [];
			
		if (input_seleccionat.length) {
			
			var a_retornar = [];
			input_seleccionat.each(function() {
				var input_elm = $(this),
					label_text = (input_elm.parent().is("LABEL")) ? input_elm.parent().text() : el_element.find("label[for=" + input_elm.attr("id") + "]").text();
				a_retornar.push({ etiqueta: label_text, valor: input_elm.val() });
			});
			
		}
		return a_retornar;
	
	} else if (el_tipus === "select") {
		
		return { etiqueta: el_camps_primer.parent().find("span:first").text(), valor: el_camps_primer.val() };
		
	} else if (el_tipus === "check") {
		
		return { etiqueta: el_camps_primer.parent().find("span:first").text(), valor: el_camps_primer.is(":checked") };
		
	}
	
}


function control_expandAll(name, valor) {
	
	control_verifica(name);
		
	if (!el_camps.length) {
		return false;
	}
	
	if (el_tipus === "tree") {
		
		if (valor) {
			el_element
				.find(".imc-amb-fills").addClass("imc-amb-fills-on").end()
				.find("ul ul").show(200);
		} else {
			el_element
				.find(".imc-amb-fills-on").removeClass("imc-amb-fills-on").end()
				.find("ul ul").hide(200);
		}
		
	}
	
}


function control_tableDetall(name, url) {
	
	control_verifica(name);
	
	var iframe_ID = "iframe" + name,
		iframe_fons = $("<div>").attr("id", "imc-iframe-fons").addClass("imc-iframe-fons"),
		form_elm = $imc_contenidor.find("form:first");
	
	if (el_element.hasClass("imc-el-taula-detall-centrat")) {
		
		var window_W = $(window).width(),
			window_H = $(window).height(),
			el_legend_H = 0,
			iframe = $("<iframe>")
									.attr({ id: iframe_ID, name: iframe_ID, frameborder: "0", scrolling: "no" })
									.addClass("imc-el-taula-iframe imc-el-taula-iframe-centrat");
			
		
	} else {
	
		var el_T = el_element.offset().top,
			el_L = el_element.offset().left,
			el_W = el_element.width(),
			el_H = el_element.height(),
			el_legend_H = el_element.find("legend:first").outerHeight(),
			iframe = $("<iframe>")
									.attr({ id: iframe_ID, name: iframe_ID, frameborder: "0", scrolling: "no" })
									.css({ top:(el_T+el_legend_H)+"px", height:(el_H-el_legend_H)+"px" })
									.addClass("imc-el-taula-iframe");
	
	}
	
	$("body")
		.append(iframe_fons)
		.append(iframe).end()
		.find("iframe:last").addClass(".imc-el-taula-iframe-on");
	
	form_elm.attr({ action: url, target: iframe_ID }).removeAttr('onsubmit').submit();
	
	var window_H = $(window).height(),
		contenidor_H = $imc_contenidor.outerHeight();
	
	if (contenidor_H > window_H) {
		$("#imc-iframe-fons").css("height", contenidor_H+"px");
	}
	
	//form_elm.submit();
	
	// dins del iframe
	$("#"+iframe_ID).load(function() {
		
		var iframe_H = $("#"+iframe_ID).height(),
			iframe_contenidor = $("#"+iframe_ID).contents().find("#imc-contenidor"),
			iframe_botonera_H = iframe_contenidor.find(".imc-iframe-botonera:first").outerHeight(),
			iframe_form = iframe_contenidor.find(".imc-form:first");
			
		iframe_form.css({ height:(iframe_H - iframe_botonera_H - 3)+"px" });
		
		// errores?
		if (iframe_contenidor.find(".imc-errores").length) {
			var errors_elm = iframe_contenidor.find(".imc-errores:first"),
				errors_html = errors_elm.html();
			errors_elm.remove();
			iframe_contenidor.find(".imc-formulari:first").prepend( $("<div>").html(errors_html).addClass("imc-errores") );
		}
		
	});
	
	
}


function control_tableDetall_accio() {
	top.location.reload();
}
