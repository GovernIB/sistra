<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="es.caib.zonaper.delega.util.PermisosUtil" %>

<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>


<script type="text/javascript">
	<!--

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
		
		crearDelegacion();
	}
	);

	
	function crearDelegacion(){
		Mensaje.mostrar({tipo: "mensaje", modo: "ejecutando", fundido: "si", titulo: "<bean:message key="delegaciones.enviando"/>"});
		
		var nifDelegado = '<bean:write name="nifDelegado"/>';
		var nifDelegante = '<bean:write name="nifDelegante"/>';
		var codigoRDS = '<bean:write name="codigoRDS"/>';
		var claveRDS = '<bean:write name="claveRDS"/>';
		var firmaJSP = '<bean:write name="firmaJSP"/>';		
		
		
		var url_json2 = '<html:rewrite page="/asignarRepresentante.do"/>';
		var url_redirect = '<html:rewrite page="/delegacionesEntidad.do"/>?nif=' + nifDelegante;
		
		
		var data2 ='nifDelegante='+nifDelegante+'&codigoRDS='+codigoRDS+'&claveRDS='+claveRDS+'&firmaJSP='+firmaJSP;
		$.postJSON(url_json2, data2, function(datos2) {
			if (datos2.error==""){	
				alert("<bean:message key="delegaciones.alta.correcta"/>");												            
			}else{
				alert(datos2.error);				
			}
			Mensaje.cancelar();
			document.location = url_redirect;
        });
		
		
	}
</script>
						
		<!-- continguts -->
		<div class="continguts">
		</div>
	
		<!-- /continguts -->
		<div id="fondo"></div>
	