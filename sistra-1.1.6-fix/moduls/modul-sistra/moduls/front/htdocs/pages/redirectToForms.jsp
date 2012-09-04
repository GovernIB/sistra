<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="datos" name="params" property="datos" type="java.lang.String"/>
<tiles:importAttribute name="urlTarget" scope="page" />
<tiles:importAttribute name="urlReturn" scope="page" />
<tiles:importAttribute name="urlCancel" scope="page" />
<bean:define id="formTarget" name="urlTarget" type="java.lang.String"/>
<bean:define id="retornoTramitacion" name="urlReturn" type="java.lang.String"/>
<bean:define id="urlCancelacion" name="urlCancel" type="java.lang.String"/>
<html>
<body onLoad="javascript:document.forms[0].submit();">
<form action="<%= formTarget %>" method="POST" name="formularioRedireccion">
<html:hidden name="irAFormularioForm" property="ID_INSTANCIA"/>
<html:hidden name="irAFormularioForm" property="identificador"/>
<html:hidden name="irAFormularioForm" property="instancia"/>
<html:hidden name="irAFormularioForm" property="modelo"/>
<html:hidden property="urlRetorno" value="<%=  retornoTramitacion %>"/>
<html:hidden property="urlCancelacion" value="<%=  urlCancelacion %>"/>
<html:hidden property="datosAnteriores" value="<%= datos %>"/>
<html:hidden property="descripcion" value="<%= ( String ) request.getAttribute( "descripcion" ) %>"/>
</form>
</body>
</html>