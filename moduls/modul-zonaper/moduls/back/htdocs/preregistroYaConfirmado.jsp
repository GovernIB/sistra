<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.back.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>


<bean:define id="strAction" name="<%= Constants.MESSAGE_ACTION_KEY %>" type="java.lang.String"/>
<bean:define id="mapActionParams" name="<%= Constants.MESSAGE_ACTION_PARAMS_KEY %>" type="java.util.Map"/>
<bean:define id="imprimirSelloAction" type="java.lang.String">
	<html:rewrite page="/imprimirSello.do" name="<%= Constants.MESSAGE_ACTION_PARAMS_KEY %>" />
</bean:define>
<bean:define id="botonImprimir" type="java.lang.String">
 <bean:message key="exitoConfirmacion.enlaceImpresionSello"/>
</bean:define>
<bean:define id="messageAyuda" value="ayuda.impresionSello" />
<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>
<script type="text/javascript">
<!--
	function imprimirSello()
	{
		window.open('<%= imprimirSelloAction %>','mywindow','width=400,height=200');
	}
-->
</script>
		<h2><bean:message key="ayuda.impresionSello.titulo" /></h2>
		<p align="right"><html:link href="#" onclick="<%= "javascript:obrir('" + urlAyuda + "', 'Edicion', 540, 400);"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
		<p class="centrado"><bean:write name="message" filter="false"/></p>
		<p class="centrado"><html:button title="<%= botonImprimir %>" value="<%= botonImprimir %>" property="enviar" onclick="javascript:imprimirSello()" /></p>
		<p class="tornarArrere"><strong><html:link href="<%=Constants.URL_APLICACION_REGISTRO%>"><bean:message key="mensajes.enlaceVolver" /></html:link></strong></p>