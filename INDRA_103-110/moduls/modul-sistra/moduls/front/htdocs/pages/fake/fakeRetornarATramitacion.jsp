<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="datos" name="datos" type="java.lang.String"/>
<bean:define id="retornoTramitacion" name="fakeRetornarATramitacionForm" property="urlRetorno" type="java.lang.String"/>
<html>
<body onLoad="javascript:document.forms[0].submit();">
<form name="retornoTramitacion" action="<%= retornoTramitacion %>" method="POST">
<html:hidden name="fakeRetornarATramitacionForm" property="ID_INSTANCIA"/>
<html:hidden name="fakeRetornarATramitacionForm" property="identificador"/>
<html:hidden name="fakeRetornarATramitacionForm" property="instancia"/>
<html:hidden property="datosNuevos" value="<%= datos %>"/>
</form>
</body>
</html>