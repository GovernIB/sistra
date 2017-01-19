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
	
	<script type="text/javascript">
	<!--		
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
	
	<p class="error">
		<fmt:message key="error"/>:
		<c:if test="${empty error}">
		    <fmt:message key="errorGenerico"/>
		</c:if>
		<c:if test="${not empty var1}">
		    ${error}
		</c:if>	
		<c:if test="${not empty errorCodigo}">
		   (<fmt:message key="errorCodigo"/> ${errorCodigo})
		</c:if>
	</p>
	  		
		
	<div id="footer">
		<p class="centro">
			<input type="button" onclick="javascript:volverAsistenteTramitacion();" value="<fmt:message key="volver"/>"/>
		</p>
	</div>	
	
	</div>		
</body>
</html>