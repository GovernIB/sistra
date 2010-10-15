<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

	<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
	<bean:define id="urlPortal"  type="java.lang.String">
		<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlPortal" />
	</bean:define>
	<bean:define id="tituloPortal"  type="java.lang.String">
		<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="tituloPortal("+ lang +")"%>'/>
	</bean:define>
	

		<!-- logo illes balears -->
		<div id="cap">
		<html:link href="<%=urlPortal%>" paramId="lang" paramName="lang" accesskey="0" >
			<img id="logoCAIB" class="logo" src="<bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo" />" alt="Logo <bean:write name="<%=es.caib.zonaper.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre" />" />
		</html:link>
		</div>
		
		<!-- molla pa -->
		<tiles:insert name=".enlacesNavegacion"/>
		<!-- /molla pa -->
		
		<!-- titol aplicacio -->
		
		<!-- usuari -->
		<div id="dadesUsuari">
		<logic:present name="es.caib.zonaper.front.DATOS_SESION" scope="session">
			<logic:notEqual name="es.caib.zonaper.front.DATOS_SESION" property="nivelAutenticacion" scope="session" value="A">				
				<bean:define id="usuario">
					<bean:write name="es.caib.zonaper.front.DATOS_SESION" property="nombreUsuario" scope="session"/>
				</bean:define>
				<p id="titolAplicacio"><bean:write name="tituloPortal"/>, <span><bean:message key="head.miPortal.autenticado" arg0="<%=usuario%>"/></span></p>
			</logic:notEqual>
			<logic:equal name="es.caib.zonaper.front.DATOS_SESION" property="nivelAutenticacion" scope="session" value="A">								
				<p id="titolAplicacio"><bean:write name="tituloPortal"/>: <span><bean:message key="head.miPortal.anonimo"/></span></p>
			</logic:equal>
		</logic:present>
		</div>
