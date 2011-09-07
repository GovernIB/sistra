<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.back.Constants"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="messageAyuda" value="ayuda.busqueda" />

<bean:define id="urlAyuda" type="java.lang.String">
	<html:rewrite page="/ayuda.do" paramId="<%= Constants.MESSAGE_KEY %>" paramName="messageAyuda"/>
</bean:define>

<script type="text/javascript">
<!--
	function enviar(){
		if (document.forms[0].numeroPreregistro.value.length <= 0){
			alert("<bean:message key="busqueda.numeroEntrada.vacio" />");
			return;
		}
		if (document.forms[0].digitoControl.value.length <= 0){
			alert("<bean:message key="busqueda.digitoControl.vacio" />");
			return;
		}
		document.forms[0].submit();
	}
	
	
	function calcularDC(){
		if (document.forms[0].numeroPreregistro.value.length <= 0){
			alert("<bean:message key="busqueda.numeroEntrada.vacio" />");
			return;
		}
		obrir('/zonaperback/calcularDC.do?numeroPreregistro='+document.forms[0].numeroPreregistro.value, 'calcularDC', 540, 200);
	}
	
			
//-->
</script>


		<h2><bean:message key="busqueda.busquedaTramites" /></h2>
		<p align="right"><html:link href="#" onclick="<%= "javascript:obrir('" + urlAyuda + "', 'Edicion', 540, 400);"%>"><img src="imgs/icones/ico_ayuda.gif" border="0"/><bean:message key="ayuda.enlace" /></html:link></p>
			<html:form action="busquedaPreregistro" styleClass="centrat">
					<p>
						<bean:message key="busqueda.numeroEntrada" /> <html:text property="numeroPreregistro" size="16" maxlength="50"/> 
						<bean:message key="busqueda.digitoControl" /> <html:text property="digitoControl" size="4" maxlength="2"/> 
						<!-- 
							<input type="button" value="<bean:message key="busqueda.calcularDC"/>"  onclick="javascript:calcularDC();" />
						 -->
					</p>
				<bean:define id="botonEnviar" type="java.lang.String">
                  <bean:message key="boton.enviar"/>
                 </bean:define>	
				<p class="centrado"><input name="imprimirSolicitudBoton" id="imprimirSolicitudBoton" type="button" value="<%=botonEnviar%>" onclick="javascript:enviar();"/></p>
				<div class="separacio"></div>			
			</html:form>	
