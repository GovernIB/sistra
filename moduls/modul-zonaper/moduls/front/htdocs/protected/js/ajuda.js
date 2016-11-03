// Suport tècnic

$(document).ready(function(){
	// borde de la tabla llistat
	if (!$.browser.msie) {
		$(".llistatElements").css("border-collapse","separate");
	}
	// amaguem la capa ajuda
	$("#suportTecnic").css("display","none");
	// obrir ajuda
	$("#equipSuport").click(function () {
		if ($("#fons").size() == 0) {
			// tamany escritori treball
			finestraX = document.documentElement.clientWidth;
			finestraY = document.documentElement.clientHeight;
			bodyY = document.body.offsetHeight;
			documentY = document.documentElement.offsetHeight;
			if (bodyY > documentY) documentY = bodyY;
			// nomes per a IE, amaguem tots els SELECT
			if ($.browser.msie && $.browser.version < 7) {
				$("select").css("display","none");
			}
			// creem fons
			$("#contenidor").append("<div id='fons'></div>");
			$("#fons").css("opacity",".5");
			// apliquem tamany
			$("#fons").width(finestraX);
			if (documentY > finestraY) {
				$("#fons").height(documentY);
			} else {
				$("#fons").height(finestraY);
			}
			// mostrem suport
			$("#suportTecnic").fadeIn("slow");
			suportW = $(document).find("#suportTecnic").width();
			suportH = $(document).find("#suportTecnic").height();
			suportL = (finestraX-suportW)/2;
			suportT = (finestraY-suportH)/2;
			$("#suportTecnic").css("left",suportL+"px");
			if (window.XMLHttpRequest) {
				$("#suportTecnic").css("position","fixed").css("top",suportT+"px");
			} else {
				finestraScrollT = document.documentElement.scrollTop;
				$("#suportTecnic").css("top",suportT+finestraScrollT+"px");
			}
		}
	});
	$("#suportDescartar").click(function () {
		if ($.browser.msie && $.browser.version < 7) {
			$("select").css("display","inline");
		}	
		$("#fons").remove();
		$("#suportTecnic").fadeOut("slow");
	});
});
