<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%--
    Apartado de un menu.
    Atributs tiles:
        titulo - Nombre etiqueta del titulo
        link - Action encargada
   --%>
<html:xhtml />
<tiles:importAttribute name="pre-titulo" scope="page" />
<tiles:importAttribute name="titulo" scope="page" />
<tiles:importAttribute name="link" scope="page" />
<tiles:importAttribute name="role" scope="page" />
<bean:define id="accion" name="link" type="java.lang.String" />
<bean:define id="rol" name="role" type="java.lang.String" />
<logic:equal name="<%= rol %>" value="true">
	<logic:notEmpty name="pre-titulo">
		<tr><td style="text-decoration:underline"><bean:message name="pre-titulo" /></td></tr>
	</logic:notEmpty>	
<tr><td onMouseOver="this.style.backgroundColor='#97aac0'" onMouseOut="this.style.backgroundColor='#515b67'">
<html:link action="<%=accion%>" target="Ventana">
    - <bean:message name="titulo"/>
</html:link>    
</td>
</tr>
</logic:equal>
