<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="firmaweb-messages" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="${lang}" xml:lang="${lang}">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
	<title>Govern de les Illes Balears</title>
	<link href="css/estils.css" rel="stylesheet" type="text/css" />
	
	<c:if test="${not empty urlCssCustom}">
		<link href="${urlCssCustom}" rel="stylesheet" type="text/css" />    
	</c:if>	
	
	<script type="text/javascript">
	<!--
		function firmar(pluginId) {
			document.location = "firmaWebServlet?accion=realizarFirma&signaturesSetID=${signaturesSetID}&plugin=" + pluginId;
		}
		
		function volverAsistenteTramitacion() {
			<c:choose>
			  <c:when test="${target == '_self'}">
			    window.location = "${urlCancel}"
			  </c:when>
			  <c:otherwise>
			  	top.location = "${urlCancel}";
			  </c:otherwise>
			</c:choose>			
		}
	//-->
	</script>
	
</head>
<body>
	<div id="contenidor">
	
	<p class="alerta">
		<fmt:message key="seleccionar"/>
	</p>
	<div id="plugins">
	<p>
		<fmt:message key="infoFirma">
			<fmt:param value="${urlInfoFirma}"/>
		</fmt:message>
	</p>
	<br/>
	<c:forEach var="plugin" items="${plugins}">
		<h3><fmt:message key="plugin.${plugin.pluginID}.titulo"/></h3>
		<p><fmt:message key="plugin.${plugin.pluginID}.texto"/></p>
		<div class="botonera">
			<input onclick="firmar('${plugin.pluginID}');" value="<fmt:message key="plugin.${plugin.pluginID}.firmar"/>" type="button"/>	
		</div>		
		<br/>
	</c:forEach>  		
		
	<div id="footer">
		<p class="centro">
			<input type="button" onclick="javascript:volverAsistenteTramitacion();" value="<fmt:message key="cancelar"/>"/>
		</p>
	</div>
	</div>	
	
	</div>		
</body>
</html>