// JavaScript Document

function appletJava() {
	p = document.createElement('p');
	codigo = '<span class="etiqueta">Certificados disponibles:</span>';
	codigo += '	<!--[if !IE]> Firefox y otros navegadores usaran la etiqueta object externa -->';
	codigo += '	<object name="appfirma" classid="java:signatureapi.class,appletFirma.class" type="application/x-java-applet" archive="signatureapi.jar,appletFirma.jar" width="50%" height="35">';
	codigo += '	<!-- El navegador Konqueror necesita el siguiente parametro -->';
	codigo += '	<param name="archive" value="signatureapi.jar,appletFirma.jar" />';
	codigo += '	<!--<![endif]-->';
	codigo += '	<!-- MSIE (Microsoft Internet Explorer) usara el object interno -->';
	codigo += '	<object name="appfirma" classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" codebase="http://java.sun.com/update/1.5.0/jinstall-1_5_0-windows-i586.cab" width="50%" height="35">';
	codigo += '	<param name="codebase" value="http://192.168.145.39:8080/ejemploAutenticacion" />';
	codigo += '	<param name="code" value="es.caib.loginModule.applet.AppletFirma" />';
	codigo += '	<param name="archive" value="signatureapi.jar, appletFirma.jar" />';
	codigo += '	<param name="idioma" value="es" />';
	codigo += '	<param name="type" value="application/x-java-applet;jpi-version=1.4.2" />';
	codigo += '	<param name="scriptable" value="true" />';
	codigo += '	<span class="noJava">Este navegador tiene <strong>deshabilitado</strong> o <strong>no tiene instalado</strong> el Plug-in de Java. <a href="http://java.sun.com/products/plugin/downloads/index.html">Descargue la &uacute;ltima versi&oacute;n de Java</a></span>';
	codigo += '	</object>';
	codigo += '	<!--[if !IE]> cerramos el object externo -->';
	codigo += '	</object>';
	codigo += '	<!--<![endif]-->';
	p.innerHTML = codigo;
	$('#formCD p').before(p);
}

$(document).ready(function(){
	// escondemos forms
	$("#opcions form").attr('class','js');			
	// eventos para los divs
	$("#opcions div").bind("click", function(e){
		tamanyo = $(this).find('form p').size();
		if (tamanyo < 2 && $(this).find('form').attr('name') == 'formCD') {
			appletJava();
		}
		$(this).find('form').slideToggle("slow");
	});
	$("#opcions div").bind("mouseenter mouseleave", function(e){
		$(this).toggleClass("over");
	});
	// eventos para los input
	$("#opcions input").click(function(e){
		e.stopPropagation();
	});
});