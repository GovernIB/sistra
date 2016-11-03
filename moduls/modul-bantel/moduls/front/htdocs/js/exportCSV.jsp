<%@ page language="java" contentType="text/javascript; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>



var IDTRABAJO = "";

function asyncPostJquery(urlString,postString, funcionCallBSuccess, funcionCallBError)
{
 $.ajax({
  type: 'POST',
  url: urlString,
  data: postString,
  success: funcionCallBSuccess,
  error: funcionCallBError,
  dataType: 'text'
});
}

function exportCSV(){

	IDTRABAJO = "";
	
	var form = document.getElementById("export");
	var params;
	
	params = "identificadorProcedimientoTramite="+form.identificadorProcedimientoTramite.options[form.identificadorProcedimientoTramite.selectedIndex].value;
	params+= "&procesada="+form.procesada.options[form.procesada.selectedIndex].value;
	
	if (form.desde.value != ''){
		if (!validDate(form.desde.value)){
			alert("<bean:message key="exportCSV.fechaInicioNoValida"/>");
			return;
		}else{
			params+="&desde="+form.desde.value;
		}		
	}
	
	if (form.hasta.value != ''){
		if (!validDate(form.hasta.value)){
			alert("<bean:message key="exportCSV.fechaHastaNoValida"/>");
			return;
		}else{
			params+="&hasta="+form.hasta.value;
		}
	}	
	
	// 	Iniciamos proceso exportacion
	IDTRABAJO = "INIT";
	asyncPostJquery("exportCSVInitAction.do",params,exportProcesed,errorExport);	
	accediendoEnviando("<bean:message key="exportCSV.iniciandoProceso"/>");
		
}


function cancelExport(){
	ocultarEnviando();
	if (IDTRABAJO == "CANCEL" || IDTRABAJO == "")  return;
	asyncPostJquery("exportCSVCancelAction.do","id="+IDTRABAJO,null,errorExport);	
}


function exportProcesed(result){

	// Comprobamos si se ha cancelado el trabajo
	if (IDTRABAJO == "CANCEL") {ocultarEnviando(); return;}

	if (result.indexOf("ERROR:") != -1){
		error = result.substring("ERROR:".length);
		alert("<bean:message key="exportCSV.errorProceso"/>" + " \n\n<bean:message key="exportCSV.detalleErrorProceso"/>\n" + error );
		ocultarEnviando();
		return;
	}
	
	if (result.indexOf("INIT:") != -1){
		IDTRABAJO =  result.substring("INIT:".length);				
		
		asyncPostJquery("exportCSVProcessAction.do","id="+IDTRABAJO,exportProcesed,errorExport);
		accediendoEnviando("<bean:message key="exportCSV.iniciadoProceso"/>");
		return;
	}
	
	if (result.indexOf("PROCESS:") != -1){
		numProcesadas =  result.substring("PROCESS:".length,result.indexOf('-'));
		numTotal = result.substring(result.indexOf('-')+1);
		
		if (numProcesadas == numTotal){
			// finalizado
			accediendoEnviando("<bean:message key="exportCSV.procesandoEntradasInicio"/> " + numProcesadas + " <bean:message key="exportCSV.procesandoEntradasFin"/> " + numTotal);
			ocultarEnviando();
			
			mostrarDownloadCSV();
			
			//this.document.location = "exportCSVDownloadAction.do?id="+IDTRABAJO;
      //window.open ("exportCSVDownloadAction.do?id="+IDTRABAJO,"donwloadExport");						
		}else{
			asyncPostJquery("exportCSVProcessAction.do","id="+IDTRABAJO,exportProcesed,errorExport);
			accediendoEnviando("<bean:message key="exportCSV.procesandoEntradasInicio"/> " + numProcesadas + " <bean:message key="exportCSV.procesandoEntradasFin"/> " + numTotal);
		}		
		return;
	}
}

function errorExport(result){
 	if (result.indexOf("ERROR:") != -1){
		error = result.substring("ERROR:".length);	
	} else {
    error = '';
  }
	alert("<bean:message key="exportCSV.errorProceso"/>" + " \n\n<bean:message key="exportCSV.detalleErrorProceso"/>\n" + error );
	ocultarEnviando();		
}

function downloadCSV(){
  ocultarDownloadCSV();
  this.document.location = "exportCSVDownloadAction.do?id="+IDTRABAJO;
}


function ocultarDownloadCSV() {

	var capaIF = document.getElementById('capaInfoFondo');
	var capaI = document.getElementById('capaDownloadExport');
	
	capaI.style.display = 'none';
	capaIF.style.display = 'none';
}

function mostrarDownloadCSV() {
	
	var capaIF = document.getElementById('capaInfoFondo');
	var capaI = document.getElementById('capaDownloadExport');
	// tama?os de la ventana y la p?gina
	var scroller = document.documentElement.scrollTop;
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenedor').offsetHeight;
	// la capa de fondo ocupa toda la p?gina
	with (capaIF) {
		if(ventanaY > capaY) style.height = ventanaY + 'px';
		else style.height = capaY + 'px';
		if(document.all) style.filter = "alpha(opacity=40)";
		else style.MozOpacity = 0.4;
		if(document.all) style.width = ventanaX + 'px';
		style.display = 'block';
	}	
	
	// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = parseInt((ventanaX-capaInfoX)/2) + 'px';
		if(document.all) style.top = parseInt(scroller+(ventanaY-capaInfoY)/2) + 'px';
		else style.top = parseInt((ventanaY-capaInfoY)/2) + 'px';
		if(!document.all) style.position = 'fixed';
		if(document.all) style.filter = "alpha(opacity=0)";
		else style.MozOpacity = 0;
	}
	// y mostramos la capa ayuda
	mostrarCapa('capaDownloadExport');
}

function ocultarEnviando() {

	var capaIF = document.getElementById('capaInfoFondo');
	var capaI = document.getElementById('capaInfoForms');
	
	capaI.style.display = 'none';
	capaIF.style.display = 'none';
}


function accediendoEnviando(mensaje) {
	
	contenido = mensaje;
	
	var capaIF = document.getElementById('capaInfoFondo');
	var capaI = document.getElementById('capaInfoForms');
	// tama?os de la ventana y la p?gina
	var scroller = document.documentElement.scrollTop;
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenedor').offsetHeight;
	// la capa de fondo ocupa toda la p?gina
	with (capaIF) {
		if(ventanaY > capaY) style.height = ventanaY + 'px';
		else style.height = capaY + 'px';
		if(document.all) style.filter = "alpha(opacity=40)";
		else style.MozOpacity = 0.4;
		if(document.all) style.width = ventanaX + 'px';
		style.display = 'block';
	}
	// colocamos el texto adecuado
	document.getElementById('mensajeEnviando').innerHTML = contenido;
	
	// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = parseInt((ventanaX-capaInfoX)/2) + 'px';
		if(document.all) style.top = parseInt(scroller+(ventanaY-capaInfoY)/2) + 'px';
		else style.top = parseInt((ventanaY-capaInfoY)/2) + 'px';
		if(!document.all) style.position = 'fixed';
		if(document.all) style.filter = "alpha(opacity=0)";
		else style.MozOpacity = 0;
	}
	// y mostramos la capa ayuda
	mostrarCapa('capaInfoForms');
}

function reposicionaCapa(idCapa){
	var scroller = (document.body.scrollTop) ? document.body.scrollTop : document.documentElement.scrollTop;
	var ventanaY = document.documentElement.clientHeight;
	if(document.getElementById(idCapa) != 'undefined') var capaI = document.getElementById(idCapa);
	capaInfoY = capaI.offsetHeight;
	capaI.style.top = parseInt(scroller+(ventanaY-capaInfoY)/2) + 'px';
}

opacidad = 0;
function mostrarCapa(idCapa) {
	if(opacidad < 100) {
		opacidad += 20;
		if(document.all) document.getElementById(idCapa).filters.alpha.opacity = opacidad;
		else document.getElementById(idCapa).style.MozOpacity = opacidad/100;
		tiempo = setTimeout("mostrarCapa('" + idCapa + "')", 50);
	} else {
		clearTimeout(tiempo);
		opacidad = 0;
	}
}

$(function() {    $( "#desde" ).datepicker({ changeYear: true });  });
$(function() {    $( "#hasta" ).datepicker({ changeYear: true });  });