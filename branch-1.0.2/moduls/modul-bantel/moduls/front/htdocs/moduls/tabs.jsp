<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlConfirmacion" type="java.lang.String">
	<html:rewrite href="/zonaperback/init.do" paramId="lang" paramName="<%= Globals.LOCALE_KEY  %>" paramProperty="language" paramScope="session"/>
</bean:define>

<div id="tabs">
	<logic:notPresent name="enlace">
		<ul>
			<!--  OPCION 1: BANDEJA TELEMATICA -->
			<logic:equal name="<%=es.caib.bantel.front.Constants.OPCION_SELECCIONADA_KEY%>" value="1">
				<li class="selec">
			</logic:equal>
			<logic:notEqual name="<%=es.caib.bantel.front.Constants.OPCION_SELECCIONADA_KEY%>" value="1">
				<li>
			</logic:notEqual>
			
					<html:link action="init">
						<bean:message key="tabs.titulo"/>	
					</html:link>		
			
				</li>
			
			<!--  OPCION 2: CONFIRMACION REGISTROS INCORRECTOS -->
			<logic:equal name="<%=es.caib.bantel.front.Constants.OPCION_SELECCIONADA_KEY%>" value="2">
				<li class="selec">
			</logic:equal>
			<logic:notEqual name="<%=es.caib.bantel.front.Constants.OPCION_SELECCIONADA_KEY%>" value="2">
				<li>
			</logic:notEqual>				
					<html:link action="confirmacionPreregistros">
						<bean:message key="tabs.confirmacion"/>							
					</html:link>	
			</li>		
		</ul>
	</logic:notPresent>		
</div>