<%@ page language="java" contentType="text/javascript; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

function validaFormulario( form )
{
	// Validamos fechas
	if (form.fechaDesde.value == ''){
		alert("<bean:message key="formularioBusquedaExpedientes.fechaInicioVacia"/>");
		return;
	}
	
	if (form.fechaDesde.value != ''){
		if (!validDate(form.fechaDesde.value)){
			alert("<bean:message key="formularioBusquedaExpedientes.fechaInicioNoValida"/>");
			return;
		}		
	}
	if (form.fechaHasta.value != ''){
		if (!validDate(form.fechaHasta.value)){
			alert("<bean:message key="formularioBusquedaExpedientes.fechaHastaNoValida"/>");
			return;
		}		
	}
	
	form.pagina.value = '0';
	form.submit();
}

function detalleExpediente(id, unidadAdm, claveExp){
	document.location = "recuperarExpediente.do?identificadorExp=" + id + "&unidadAdm=" + unidadAdm + "&claveExp=" + claveExp;	
}

/* seleccionar ítem d'una tabla */
function selecItemTabla(obj) {
	clase = obj.className;
	obj.className = 'perDamunt';
	obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
}

function alta(){
	document.location = "altaExpediente.do?flagValidacion=entradaAlta";	
}


$(function() {    $( "#fechaDesde" ).datepicker({ changeYear: true });  });
$(function() {    $( "#fechaHasta" ).datepicker({ changeYear: true });  });