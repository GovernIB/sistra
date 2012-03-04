<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/xmlhttp.js"></script>
<script type="text/javascript" src="js/exportCSV.js"></script>

<script type="text/javascript">
<!--

var IDTRABAJO = "";

function exportCSV(){

	IDTRABAJO = "";
	
	var form = document.getElementById("export");
	var params;
	
	params = "identificadorTramite="+form.identificadorTramite.options[form.identificadorTramite.selectedIndex].value;
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
	asyncPost("exportCSVInitAction.do",params,exportProcesed);	
	accediendoEnviando("<bean:message key="exportCSV.iniciandoProceso"/>");
		
}


function cancelExport(){
	ocultarEnviando();
	if (IDTRABAJO == "CANCEL" || IDTRABAJO == "")  return;
	asyncPost("exportCSVCancelAction.do","id="+IDTRABAJO,null);	
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
		
		asyncPost("exportCSVProcessAction.do","id="+IDTRABAJO,exportProcesed);
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
			this.document.location = "exportCSVDownloadAction.do?id="+IDTRABAJO;						
		}else{
			asyncPost("exportCSVProcessAction.do","id="+IDTRABAJO,exportProcesed);
			accediendoEnviando("<bean:message key="exportCSV.procesandoEntradasInicio"/> " + numProcesadas + " <bean:message key="exportCSV.procesandoEntradasFin"/> " + numTotal);
		}		
		return;
	}
	
	
	
}

function validDate(fecha){
//	var regex = new RegExp("\d{2}/\d{2}/\d{4}");
//	var pattern = new RegExp([0-3][0-9]-0|1[0-9]-19|20[0-9]{2});		
//	return fecha.match(regex);
	
	// regular expression to match required date format
    re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
    return fecha.match(re);
    
}
//-->
</script>

		<h2><bean:message key="exportCSV.exportarTramites"/></h2>
		
		<logic:empty name="tramites">
			<bean:message key="errors.noGestorCSV" />
		</logic:empty>
		
		<logic:notEmpty name="tramites">	
			<form name="export" id="export" class="centrat">
			<p>
				<bean:message key="exportCSV.tramite"/>
				<select name="identificadorTramite">
					<logic:iterate id="tramite" name="tramites" type="es.caib.bantel.model.Tramite">															
						<option value="<%=tramite.getIdentificador()%>">
							<%=tramite.getIdentificador() + "-" + (tramite.getDescripcion().length()>60?tramite.getDescripcion().substring(0,60)+"...":tramite.getDescripcion())%>
						</option>
					</logic:iterate>
				</select>
												
				<bean:message key="exportCSV.estado"/>
				<select name="procesada">
					<option value="T"><bean:message key="formularioBusqueda.procesada.todas"/></option>	
					<option value="<%=ConstantesBTE.ENTRADA_NO_PROCESADA%>" ><bean:message key="formularioBusqueda.procesada.noprocesadas"/></option>
					<option value="<%=ConstantesBTE.ENTRADA_PROCESADA%>" ><bean:message key="formularioBusqueda.procesada.correctas"/></option>
					<option value="<%=ConstantesBTE.ENTRADA_PROCESADA_ERROR%>" ><bean:message key="formularioBusqueda.procesada.error"/></option>									
				</select>
			 </p>	
			<p>
				
				<bean:message key="exportCSV.fechaInicio"/>
				<input type="text" name="desde"/>
				
				<bean:message key="exportCSV.fechaHasta"/>
				<input type="text" name="hasta"/>
				</p>
				<p class="botonera">
					<input type="button" onclick="javascript:exportCSV();" value="<bean:message key="exportCSV.exportar"/>"/>	
				</p>
			</form>
		
		
		</logic:notEmpty>
		
		
		<!-- capa accediendo formularios -->
		<div id="capaInfoFondo"></div>
		<div id="capaInfoForms"><span id="mensajeEnviando"></span><br/><br/><input type="button" onclick="javascript:cancelExport();" value="<bean:message key="exportCSV.cancelar"/>"/></div>