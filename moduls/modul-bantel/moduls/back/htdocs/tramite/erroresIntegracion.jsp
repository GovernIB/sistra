 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="tramite.errores.popup.titulo.value"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
</head>
<script type="text/javascript">
     function mostrarErrores(){
		<%
			String cod = org.apache.commons.lang.StringUtils.isBlank(request.getParameter("codigoTramiteError"))? "" : request.getParameter("codigoTramiteError");
		%>
     	var codigo = "<%=cod%>";
     	if( codigo != null && codigo != '' ){
			var url_json = '<html:rewrite page="/mostrarErrores.do"/>';
			var data = "codigoTramiteError="+codigo;	
			$.postJSON(url_json, data, function(datos) {
				if(datos.error==""){
					document.getElementById('erroresDiv').innerHTML = datos.StringErrors;
				}else{
					document.getElementById('erroresDiv').innerHTML = datos.error;
				}
			});
		}else{
			document.getElementById('erroresDiv').innerHTML = '<bean:message key="tramite.error.mostrarError.no.codigo"/>';
		}
	}
	
	//funcion que se ejecutan solo entrar en la pagina
	$(document).ready(function(){
		$('#firmarDocumentosApplet').hide();
		$('#anexar').hide();
		
		$.postJSON = function(url, data, callback) {
        	return jQuery.ajax({
		        'type': 'POST',
		        'url': url,
		        'data': data,
		        'dataType': 'json',
		        'success': callback
		    });
		};
		        
	}
	);
</script>
<body class="ventana" onload="mostrarErrores()">
<input type='hidden' id='errores' name='errores' value=''/>
<table class="nomarcd">
    <tr><td class="titulo"><bean:message key="tramite.errores.popup.value"/></td></tr>
</table>
<table class="marcd">
    <tr>
       <td class="labelm">
       		<div id="erroresDiv"></div>	
       </td>
    </tr>
    <tr>
       <td class="labelm">
			<input type="button" onclick="window.close()" value="<bean:message key="tramite.errores.popup.cerrar"/>"  class="button"/>
       </td>
    </tr>
</table>
<br />
</body>
</html:html>
 
 
 
 
