<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="strAction" name="<%= Constants.MESSAGE_ACTION_KEY %>" type="java.lang.String"/>
<bean:define id="mapActionParams" name="<%= Constants.MESSAGE_ACTION_PARAMS_KEY %>" type="java.util.Map"/>
<h2><bean:message key="header.titulo" /></h2>
<p class="mensaje">
	<bean:write name="message" filter="false"/>
</p>
<p class="tornarArrere">
	<strong>
		<html:link action="<%= strAction %>" name="<%= Constants.MESSAGE_ACTION_PARAMS_KEY %>">
			<bean:message name="<%= Constants.MESSAGE_ACTION_LABEL_KEY %>"/>
		</html:link>
	</strong>
</p>