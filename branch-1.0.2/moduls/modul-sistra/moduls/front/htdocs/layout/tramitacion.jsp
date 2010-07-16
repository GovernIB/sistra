<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.sistra.front.util.TramiteRequestHelper"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<tiles:insert name=".title" />

<%
	// Esto deberia ir a tramite controller, probablemente
	if ( TramiteRequestHelper.isSetError( request ) && TramiteRequestHelper.getTramiteFront( request ) == null )
	{
%>
	<tiles:insert name=".error" />
<% 
	}
	else
	{
%>			
		<div id="tramitacion">
			<tiles:insert name="body"/>
		</div><!-- tramitacion -->
		<div class="sepFinal"></div>
<%
	}
%>