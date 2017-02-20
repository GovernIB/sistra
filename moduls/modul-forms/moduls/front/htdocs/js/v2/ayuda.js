jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - 50 - $(this).outerHeight()) / 2) + 
                                                $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) + 
                                                $(window).scrollLeft()) + "px");
    return this;
}

function mostrarAyudaAdmin() {
	$("#contactoAdministradorContent").hide();
	$("#contactoAdministradorSoporte").show();
	$("#contactoAdministrador").show();
	$("#contactoAdministrador").center();
	
}

function ocultarAyudaAdmin() {
	$("#contactoAdministrador").hide();	
}

function mostrarFormularioIncidencias(url) {
	var capaI = document.getElementById('contactoAdministradorContent');	
	capaI.innerHTML = 
		'<iframe src="' + url + '" style="border: 0pt none; width: 500px; height: 370px;" scrolling="no"></iframe>';
	$("#contactoAdministradorSoporte").hide();
	$("#contactoAdministradorContent").show();	
	$("#contactoAdministrador").center();
}