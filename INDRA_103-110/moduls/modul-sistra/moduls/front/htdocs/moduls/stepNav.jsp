<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
	<!-- enlaces anterior siguiente -->
<logic:notEmpty name="ID_INSTANCIA">		
		<div class="barraAzul"></div>
		<div id="pasosNav">
		<logic:equal name="pasoAnterior" value="true">
			<html:link styleId="pasoAnterior" action="/protected/anteriorPaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" ><bean:message key="stepNav.textoEnlaceAnterior" /></html:link>
		</logic:equal>
		<logic:notEqual name="pasoAnterior" value="true">
			<div id="pasoAnteriorDIV"></div>
		</logic:notEqual>
		<logic:equal name="pasoSiguiente" value="true">
			<html:link styleId="pasoSiguiente" action="/protected/siguientePaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" ><bean:message key="stepNav.textoEnlaceSiguiente" /></html:link>
		</logic:equal>
		<logic:notEqual name="pasoSiguiente" value="true">
			<div id="pasoSiguienteDIV"></div>
		</logic:notEqual>
		</div>
</logic:notEmpty>