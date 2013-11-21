<%@ page language="java" contentType="text/javascript; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>


function obrirRecercaAv(obj) {
	capaAv = document.getElementById('recercaAv');
	if(capaAv.style.display == 'block') {
		capaAv.style.display = 'none';
		obj.parentNode.innerHTML = '<a href="javascript:void(0);" onclick="obrirRecercaAv(this);">[+]</a>';
	} else {
		capaAv.style.display = 'block';
		obj.parentNode.innerHTML = '<a href="javascript:void(0);" onclick="obrirRecercaAv(this);">[-]</a>';
	}
}


/* seleccionar ítem d'una tabla */
function selecItemTabla(obj) {
	clase = obj.className;
	obj.className = 'perDamunt';
	obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
}

// Valida y envia formulario
function validaFormulario( form )
{
	// Validamos fechas
	if (form.fechaDesde.value == ''){
		alert("<bean:message key="formularioBusqueda.fechaInicioVacia"/>");
		return;
	}
	
	if (form.fechaDesde.value != ''){
		if (!validDate(form.fechaDesde.value)){
			alert("<bean:message key="formularioBusqueda.fechaInicioNoValida"/>");
			return;
		}		
	}
	if (form.fechaHasta.value != ''){
		if (!validDate(form.fechaHasta.value)){
			alert("<bean:message key="formularioBusqueda.fechaHastaNoValida"/>");
			return;
		}		
	}
	
	form.pagina.value = '0';
   	form.submit();
}
    
function detalleTramite(id){
   	document.location = "detalleTramite.do?codigo=" + id;
}
    
function ocultarCambioEstadoMasivo() {
	var capaI = document.getElementById('cambioEstadoMasivo');
	capaI.style.display = 'none';
	Fondo.esconder();
}

// Abre pantalla de ayuda
function mostrarCambioEstadoMasivo() {
	
	var capaI = document.getElementById('cambioEstadoMasivo');
	
	Tamanyos.iniciar();
	Fondo.mostrar();
	
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenedor').offsetHeight;
	
	// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = (ventanaX-capaInfoX)/2 + 'px';
		style.top = (ventanaY-capaInfoY)/2 + 'px';
	}		
	
}

function cambioEstadoMasivo(mensajeConfirmacion,mensajeTodosTramites){
	
	// Copiamos criterios de b?squeda
	fb = document.getElementById("busquedaTramitesForm");
	fc = document.getElementById("cambioEstadoMasivoForm");
	
 	fc.fechaDesde.value = fb.fechaDesde.value;
 	fc.fechaHasta.value = fb.fechaHasta.value;
 	fc.usuarioNif.value = fb.usuarioNif.value;
 	fc.usuarioNombre.value = fb.usuarioNombre.value;
 	fc.tipo.value = fb.tipo.options[fb.tipo.selectedIndex].value;
 	fc.procesada.value = fb.procesada.options[fb.procesada.selectedIndex].value;
 	fc.nivelAutenticacion.value = fb.nivelAutenticacion.options[fb.nivelAutenticacion.selectedIndex].value;
 	fc.identificadorProcedimiento.value = fb.identificadorProcedimiento.options[fb.identificadorProcedimiento.selectedIndex].value;
 	
 	if (fb.identificadorProcedimiento.selectedIndex == 0){ 	
 		alert(mensajeTodosTramites);	
 		return;
 	}
 				
	if (confirm(mensajeConfirmacion)) fc.submit();
	 else ocultarCambioEstadoMasivo();
}

$(function() {    $( "#fechaDesde" ).datepicker({ changeYear: true });  });
$(function() {    $( "#fechaHasta" ).datepicker({ changeYear: true });  });
