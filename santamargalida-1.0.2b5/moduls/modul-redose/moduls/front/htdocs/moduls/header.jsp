<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>


	<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
	<bean:define id="urlPortal">
		<bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="urlPortal"/>
	</bean:define>

	<%-- HEADER --%>
	<div class="header">
		<%-- Nombre del Organismo --%>
		<h1><bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></h1>
		<%-- Titulo de la Aplicación --%>
		<h2><bean:message key="tituloAplicacion"/></h2>
		<div>
			<h3><bean:message key="comprobarDocumento.existe"/></h3>
		</div>
	</div>
<%--
	<div id="cap">
		<!--  Logo -->
		<html:link href="<%=urlPortal%>" paramId="lang" paramName="lang" accesskey="0" >			
			<img id="logoCAIB" class="logo" src="<bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" alt="Logo <bean:write name="<%=es.caib.redose.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />
		</html:link>
	</div>

	<p id="titolAplicacio">
		<bean:message key="tituloAplicacion"/>
	</p>
--%>