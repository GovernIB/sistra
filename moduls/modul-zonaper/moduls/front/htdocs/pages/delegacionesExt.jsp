<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="es.caib.zonaper.front.util.PermisosUtil" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/mensaje.js"></script>


<script type="text/javascript">
<!--				      
	
	var mensajeEnviando = '<bean:message key="delegaciones.enviando"/>';
	
	function realizarAltaDelegacion() {
		
		Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: mensajeEnviando});
		
		var codigoRDS = '<bean:write name="codigoRDS"/>';
		var claveRDS = '<bean:write name="claveRDS"/>';
		var firmaJSP = '<bean:write name="firmaJSP"/>';
		
		var url_jsonAFIRMA = '<html:rewrite page="/protected/crearDelegacion.do"/>';
		var dataAFIRMA = 'codigoRDS=' + codigoRDS + '&claveRDS=' + claveRDS + '&firmaJSP=' + firmaJSP;
		
		$.postJSON(url_jsonAFIRMA, dataAFIRMA, function(datos2) {
			if (datos2.error==""){								
				alert("<bean:message key="delegaciones.alta.correcta"/>");											
			}else{
				alert(datos2.error);
			}
		});
		
		document.location='<html:rewrite page="/protected/mostrarDelegaciones.do" />';				
		Mensaje.cancelar();
	}
	
	$(document).ready(function(){
		
		$.postJSON = function(url, data, callback) {
        	return jQuery.ajax({
		        'type': 'POST',
		        'url': url,
		        'data': data,
		        'dataType': 'json',
		        'success': callback
		    });
		};
		
		realizarAltaDelegacion();
	}
);
	
-->	
</script>	