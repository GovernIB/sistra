<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.sistra.front.util.TramiteRequestHelper"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
		<bean:define id="urlSiguiente">
		        <html:rewrite page="/protected/siguientePaso.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
		</bean:define>
		<tiles:insert name=".title" />
<%
	// Esto deberia ir a tramite controller, probablemente
	if ( TramiteRequestHelper.isSetError( request ) && TramiteRequestHelper.getTramiteFront( request ) == null )
	{
%>
	<tiles:insert name=".message" />
<% 
	}
	else
	{
%>
  		<tiles:insert name=".datosUsuario"/>
		<div id="tramitacion">
			<tiles:insert name="message" />
			<tiles:insert name=".step0" />
		</div><!-- tramitacion -->
		<div class="sep"></div>
<%
	}
%>