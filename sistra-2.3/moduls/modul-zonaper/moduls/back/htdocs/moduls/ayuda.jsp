<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.back.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
	<bean:define id="titulo" type="java.lang.String">
		<bean:message key="<%= request.getParameter( Constants.MESSAGE_KEY ) + \".titulo\" %>"/>
	</bean:define>
	<bean:define id="cuerpo" type="java.lang.String">
		<bean:message key="<%= request.getParameter( Constants.MESSAGE_KEY ) + \".cuerpo\" %>"/>
	</bean:define>
		<h2><bean:write name="titulo" filter="false"/></h2>
		<bean:write name="cuerpo" filter="false"/>
		<p class="tornarArrere"><strong><html:link href="javascript:window.close()"><bean:message key="ayuda.close" /></html:link></strong></p>