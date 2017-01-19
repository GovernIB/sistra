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
</head>
<body onload="document.getElementById('frmCallback').submit()">
	<form id="frmCallback" action="${callbackAppUrl}" method="post" target="${callbackTarget}">
		<input type="hidden" name="${callbackAppParamSignature}" value="${firmaB64}"/>
		<c:forEach var="entry" items="${callbackAppParamOthers}">
			<input type="hidden" name="${entry.key}" value="${entry.value}"/>
		</c:forEach>  				
	</form>
	
	
	<div id="enviando"/>
	
	
	 
</body>
</html>