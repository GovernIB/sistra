<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<tiles:importAttribute name="urlFin" scope="page" />
<bean:define id="urlFin" name="urlFin" type="java.lang.String"/>
<html>
<META HTTP-EQUIV="Refresh" CONTENT="0;url=<%= urlFin %>">
</html>