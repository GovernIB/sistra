<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="migracion.titulo"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <link type="text/css" rel="stylesheet" href="<html:rewrite page='/css/jquery-ui.min.css'/>"/>
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/js/jquery-1.9.1.min.js'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/js/jquery.selectboxes.pack.js'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/js/jquery.ui.core.min.js'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/js/jquery.ui.datepicker.min.js'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/js/jquery.ui.datepicker-es.js'/>" type="text/javascript"></script>
   
    <script type="text/javascript">
    	// Selectores fechas
   		$(function() {    $( "#fechaDesde" ).datepicker({ changeYear: true });  });
		$(function() {    $( "#fechaHasta" ).datepicker({ changeYear: true });  });

		// Alerta borrar de ubicacion origen
		function alertaBorrarUbicacionOrigen() {
			if ($("#borrarUbicacionOrigen").val() == "true"){
				alert("<bean:message key="migracion.alertaBorrarUbicacionOrigen"/>");
			}
		}
		
		// Post ajax asincrono
		function asyncPostJquery(urlString,params, funcionCallBSuccess, funcionCallBError)
		{
			 $.ajax({
			  type: 'POST',
			  url: urlString,
			  data: params,
			  success: funcionCallBSuccess,
			  error: funcionCallBError,
			  dataType: 'text'
			});
		}
		
		// Funciones migracion
		// - Id trabajo
		var IDTRABAJO = "";
		// - Indica si se debe cancelar proceso en siguiente iteracion
		var CANCELAR_PROCESO = false;
		// Timeout entre iteraciones
		var TIMEOUT_ITERACIONES = 5;

		function lanzarMigracion() {	
			// Mostramos mensaje inicio proceso
			$("#botonIniciarProceso").hide();
			$("#botonPararProceso").hide();
			$("#botonFinProceso").hide();
			$("#detalleProceso").hide();
			$("#errorProceso").hide();
			$("#cancelandoProceso").hide();			
			$("#procesoMigracion").show();
			$("#inicioProceso").show();			

			// Recogemos parametros
	    	var ubicacionOrigen = $("#ubicacionOrigen").val();
	    	var ubicacionDestino = $("#ubicacionDestino").val();
	    	var fechaDesde = $("#fechaDesde").val();
	    	var fechaHasta = $("#fechaHasta").val();	    	
	    	var numDocsIteracion = $("#numDocsIteracion").val();
	    	var timeoutIteracion = $("#timeoutIteracion").val();
	    	var numMaxDocs = $("#numMaxDocs").val();
	    	var numMaxErrores = $("#numMaxErrores").val();
	    	var borrarUbicacionOrigen = $("#borrarUbicacionOrigen").val();

			var params = {};
			params["ubicacionOrigen"] = ubicacionOrigen;
			params["ubicacionDestino"] = ubicacionDestino;
			if (fechaDesde != "") {
				params["desde"] = fechaDesde;
			}
			if (fechaHasta != "") {
				params["hasta"] = fechaHasta;
			}			
			params["numDocsIteracion"] = numDocsIteracion;
			params["timeoutIteracion"] = timeoutIteracion;
			params["numMaxDocs"] = numMaxDocs;
			params["numMaxErrores"] = numMaxErrores;
			params["borrarUbicacionOrigen"] = borrarUbicacionOrigen;
			
			
			// 	Iniciamos proceso migracion
			IDTRABAJO = "INIT";
			CANCELAR_PROCESO = false;
			TIMEOUT_ITERACIONES = timeoutIteracion;
			asyncPostJquery("migracionInitAction.do",params,procesaResultadoIteracion,muestraError);											
		}

		function iniciarMigracion() {
			$("#botonIniciarProceso").hide();
			$("#botonFinProceso").hide();			
			$("#botonPararProceso").show();			
			
			// Lanza primera iteracion del proceso			
			var params = {};
			params["id"] = IDTRABAJO;
			params["cancel"] = CANCELAR_PROCESO;
			asyncPostJquery("migracionIterationAction.do",params,procesaResultadoIteracion,muestraError);
			return;
		}
		
		function procesaResultadoIteracion(result){

			// Si se ha producido un error, lo mostramos
			if (result.indexOf("ERROR:") != -1){
				muestraError(result);
				return;
			}
			
			// Si se ha parado el trabajo, invocamos a finalizar proceso
			if (result.indexOf("CANCEL:")  != -1) {
				// Invocamos a finalizar proceso
				var params = {};
				params["id"] = IDTRABAJO;
				asyncPostJquery("migracionFinalizarAction.do",params,procesaResultadoIteracion,muestraError);				
				return;
			}

			// Si se ha iniciado el trabajo, almacenamos id trabajo y comenzamos con la primera iteracion
			if (result.indexOf("INIT:") != -1){
				// INIT:ID_TRABAJO-NUM_DOCS_MIGRAR-NUM_DOCS_TOTAL
				result = result.substring("INIT:".length);
				var resInit = result.split("-");
				IDTRABAJO =  resInit[0];
				muestraProgreso("0 / " + resInit[1], "0", resInit[2]);
				migracionPreparada();
				return;
			}

			// Si es el resultado de una iteracion mostramos progreso
			if (result.indexOf("PROCESS:") != -1){
				// PROCESS:NUM_DOCS_PROCESADOS-TOTAL_DOCS_A_PROCESAR-TOTAL_DOCS-NUM_ERRORES-FINALIZADO
				result = result.substring("PROCESS:".length);
				var resProcess = result.split("-");
				muestraProgreso(resProcess[0] + " / " + resProcess[1], resProcess[3], resProcess[2]);

				//  Verificamos finalizacion proceso
				if (resProcess[4] == "true"){
					// Si se ha finalizado, Invocamos a finalizar proceso
					var params = {};
					params["id"] = IDTRABAJO;
					asyncPostJquery("migracionFinalizarAction.do",params,procesaResultadoIteracion,muestraError);					
				}else{
					// Si no, realizamos otra iteracion	
					setTimeout(function(){
								var params = {};
								params["id"] = IDTRABAJO;
								params["cancel"] = CANCELAR_PROCESO;
								asyncPostJquery("migracionIterationAction.do",params,procesaResultadoIteracion,muestraError);
							}, TIMEOUT_ITERACIONES * 1000 );					
				}		
				return;
			}

			// Si ha finalizado proceso, mostramos boton de finalizar y mostramos errores si los hay
			if (result.indexOf("FIN:") != -1){
				//FIN:DESC_ERRORES
				var errores = result.substring(result.indexOf(':')+1);				
				if (errores != "NO_ERROR") {
					muestraError("ERROR:" + errores);
				} else {
					migracionFinalizada();
				}
				return;
			}
		}

		function muestraProgreso(avance, errores, totaldocs) {
			$("#inicioProceso").hide();
			$("#detalleProceso").show();
			$("#botonPararProceso").show();
			$("#detalleProcesoAvance").text(avance);
			$("#detalleProcesoErrores").text(errores);
			$("#detalleTotalDocumentos").text(totaldocs);
		}

		function muestraError(result) {
			if (result.indexOf("ERROR:") != -1){
				error = result.substring("ERROR:".length);	
			} else {
		    	error = '';
		  	}
			migracionFinalizada();
			$("#errorProceso").show();			
			//$("#erroresProceso").text(error);
			$("#erroresProceso").html(htmlForTextWithEmbeddedNewlines(error));
			
		}
		
		function cancelarMigracion() {
			CANCELAR_PROCESO = true;
			$("#cancelandoProceso").show();
			$("#botonPararProceso").hide();				
		}

		function migracionPreparada() {
			$("#botonIniciarProceso").show();
			$("#botonPararProceso").hide();
			$("#botonFinProceso").show();								
		}
		
		function migracionFinalizada() {
			$("#inicioProceso").hide();		
			$("#botonIniciarProceso").hide();
			$("#botonPararProceso").hide();
			$("#botonFinProceso").show();								
		}
		
		function cerrarMigracion() {
			$("#procesoMigracion").hide();		
		}

		function cancelExport(){
			ocultarEnviando();
			if (IDTRABAJO == "CANCEL" || IDTRABAJO == "")  return;
			asyncPostJquery("exportCSVCancelAction.do","id="+IDTRABAJO,null,errorExport);	
		}

		function htmlForTextWithEmbeddedNewlines(text) {
		    var htmls = [];
		    var lines = text.split(/\n/);
		    // The temporary <div/> is to perform HTML entity encoding reliably.
		    //
		    // document.createElement() is *much* faster than jQuery('<div/>')
		    // http://stackoverflow.com/questions/268490/
		    //
		    // You don't need jQuery but then you need to struggle with browser
		    // differences in innerText/textContent yourself
		    var tmpDiv = jQuery(document.createElement('div'));
		    for (var i = 0 ; i < lines.length ; i++) {
		        htmls.push(tmpDiv.text(lines[i]).html());
		    }
		    return htmls.join("<br>");
		}
		
	</script>
</head>

<body class="ventana">

<br />

<!--  TITULO -->
<table class="marc">
    <tr>
    	<td class="titulo"><bean:message key="migracion.titulo"/></td>
    </tr>
    <tr>
    	<td class="subtitulo"><bean:message key="migracion.subtitulo"/></td>
    </tr>
</table>
<br />

<!--  FORM -->
<form id="migracion">
	
	<!-- OPCIONES -->
	<table class="marc">
		<tr>
	    	<td class="labelo"><bean:message key="migracion.ubicacionOrigen"/></td>
	    	<td class="input">
	    		<select id="ubicacionOrigen">
	    			<logic:iterate id="ubicacion" name="ubicaciones">
	    				<option value="<bean:write name="ubicacion" property="codigo"/>"><bean:write name="ubicacion" property="codigoUbicacion"/> - <bean:write name="ubicacion" property="nombre"/></option>
	    			</logic:iterate>					  
				</select>
	    	</td>
		</tr>
		<tr>
	    	<td class="labelo"><bean:message key="migracion.ubicacionDestino"/></td>
	    	<td class="input">
	    		<select id="ubicacionDestino">
				  <logic:iterate id="ubicacion" name="ubicaciones">
	    				<option value="<bean:write name="ubicacion" property="codigo"/>"><bean:write name="ubicacion" property="codigoUbicacion"/> - <bean:write name="ubicacion" property="nombre"/></option>
	    		 </logic:iterate>		
				</select>
	    	</td>
		</tr>
		<tr>
	    	<td class="label"><bean:message key="migracion.intervalo"/></td>
	    	<td class="input">
	    		<bean:message key="migracion.intervalo.desde"/>: <input type="text" id="fechaDesde" class="t70"/> - <bean:message key="migracion.intervalo.hasta"/>: <input type="text" id="fechaHasta" class="t70"/> 
	    	</td>
		</tr>
		<tr>
	    	<td class="labelo"><bean:message key="migracion.numDocsIteracion"/></td>
	    	<td class="input">
	    		<select id="numDocsIteracion">
	    			  <option value="10">10</option>
	    			  <option value="25">25</option>
					  <option value="50">50</option>
					  <option value="100">100</option>					  
				</select>
	    	</td>
		</tr>
		<tr>
	    	<td class="labelo"><bean:message key="migracion.timeoutIteracion"/></td>
	    	<td class="input">
	    		<select id="timeoutIteracion">
	    				<option value="1">1</option>
	    				<option value="2">2</option>
	    			<% for (int i = 5; i <=	 60; i=i+5) { %>
				  		<option value="<%=i%>"><%=i%></option>
				  	<% } %>				  
				</select>
	    	</td>
		</tr>
		<tr>
	    	<td class="labelo"><bean:message key="migracion.numMaxDocs"/></td>
	    	<td class="input">
	    		<select id="numMaxDocs">
	    			  <option value="100">100</option>
					  <option value="500">500</option>
					  <option value="1000">1.000</option>
					  <option value="5000">5.000</option>			  
					  <option value="10000">10.000</option>
					  <option value="20000">20.000</option>
					  <option value="50000">50.000</option>
					  <option value="100000">100.000</option>
					  <option value="500000">500.000</option>
				</select>
	    	</td>
		</tr>
		<tr>
	    	<td class="labelo"><bean:message key="migracion.numMaxErrores"/></td>
	    	<td class="input">
	    		<select id="numMaxErrores">
	    			  <option value="1">1</option>
					  <option value="10">10</option>
					  <option value="50">50</option>			  					  
				</select>
	    	</td>
		</tr>
		<tr>
	    	<td class="labelo"><bean:message key="migracion.borrarUbicacionOrigen"/></td>
	    	<td class="input">
	    		<select id="borrarUbicacionOrigen" onchange="alertaBorrarUbicacionOrigen()">
	    			  <option value="false"><bean:message key="negacion"/></option>
	    			  <option value="true"><bean:message key="afirmacion"/></option>					  			  					 
				</select>
	    	</td>
		</tr>
	</table>

  	<!-- BOTONERA -->  
    <table class="nomarc">
       <tr>
           <td align="center">
              	<input type="button" class="buttond" value="<bean:message key="migracion.migrar"/>" onclick="lanzarMigracion();"/>
           </td>
        </tr>
    </table>
</form>

<!-- Capa migracion -->
<div id="procesoMigracion">
	<p align="center">
		<img src="../../img/migrar.png" alt="<bean:message key="migracion.titulo"/>" />
	</p>
	<p>
		<br/>
	</p>
	<p>
		<span id="inicioProceso">
			<i><bean:message key="migracion.proceso.iniciando"/></i>			
		</span>
		<span id="detalleProceso">
			<bean:message key="migracion.proceso.avance"/> <span id="detalleProcesoAvance">1/100</span> <br/>
			<bean:message key="migracion.proceso.errores"/> <span id="detalleProcesoErrores">1</span>   <br/>
			<bean:message key="migracion.proceso.totalDocumentos"/> <span id="detalleTotalDocumentos">2000</span>		 	
		</span>
		<span id="cancelandoProceso">
			<br/><br/>
			<i><bean:message key="migracion.proceso.cancelando"/></i>			
		</span>			
	</p>
	<p>
		<br/>
	</p>	
	<div id="errorProceso"> 
		<p>
			<em><bean:message key="migracion.proceso.error"/></em> <br/>			
		</p>
		<div id="erroresProceso"></div>		
	</div>
	<p>
		<input type="button"  id="botonIniciarProceso" onclick="iniciarMigracion();" value="<bean:message key="migracion.proceso.boton.inicio"/>" class="buttond"/>
		<input type="button"  id="botonPararProceso" onclick="cancelarMigracion();" value="<bean:message key="migracion.proceso.boton.parar"/>" class="buttond"/>
		<input type="button"  id="botonFinProceso" onclick="cerrarMigracion();" value="<bean:message key="migracion.proceso.boton.fin"/>" class="buttond"/>
	</p>
</div>

</body>
</html:html>