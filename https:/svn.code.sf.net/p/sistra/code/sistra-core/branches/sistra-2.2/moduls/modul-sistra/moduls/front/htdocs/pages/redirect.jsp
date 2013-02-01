<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<html:xhtml/>
<bean:define id="accionRedireccion" name="accionRedireccion" type="java.lang.String"/>
<bean:define id="urlRedireccion" type="java.lang.String">
        <html:rewrite page="<%= accionRedireccion %>" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<html>
<body onLoad="javascript:document.location.href='<%= urlRedireccion %>';" />
</html>