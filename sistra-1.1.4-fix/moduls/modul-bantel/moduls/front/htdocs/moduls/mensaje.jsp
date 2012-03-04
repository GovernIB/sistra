<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.front.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="strAction" name="<%= Constants.MESSAGE_ACTION_KEY %>" type="java.lang.String"/>
<bean:define id="mapActionParams" name="<%= Constants.MESSAGE_ACTION_PARAMS_KEY %>" type="java.util.Map"/>
		<h2><bean:message key="header.titulo" /></h2>
		<p class="centrado"><bean:write name="message" filter="false"/></p>
		
		<p class="tornarArrere">
			<strong>
			<logic:notPresent name="enlace">
			<html:link action="<%= strAction %>" name="<%= Constants.MESSAGE_ACTION_PARAMS_KEY %>">
				<bean:message name="<%= Constants.MESSAGE_ACTION_LABEL_KEY %>"/>				
			</html:link>
			</logic:notPresent>
			<logic:present name="enlace">
				<logic:equal name="enlace" value="errorExpediente">
					<html:link action="confirmacionRecuperacionExpediente">
				<bean:message name="<%= Constants.MESSAGE_ACTION_LABEL_KEY %>"/>			
					</html:link>
				</logic:equal>
				<logic:notEqual name="enlace" value="errorExpediente">
					<a href="javascript:history.back(1)"><bean:message name="<%= Constants.MESSAGE_ACTION_LABEL_KEY %>"/></a>
				</logic:notEqual>
			</logic:present>			
			</strong>
		</p>