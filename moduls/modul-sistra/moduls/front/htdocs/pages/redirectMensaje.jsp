<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ page import="es.caib.sistra.front.Constants" %>
<html>
<body onLoad="javascript:document.location.href='<%=(String) request.getParameter(Constants.MENSAJE_URL_REDIRECT) %>';" />
</html>