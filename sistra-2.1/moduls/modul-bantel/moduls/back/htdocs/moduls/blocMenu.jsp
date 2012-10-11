<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%--
    Bloque de un menu (p.e. Mascara, etc ....)
    Atributs tiles: 
        titulo - Nombre etiqueta del titulo
        apartados - lista de definiciones de apartados.
--%>
<html:xhtml />
<tiles:importAttribute name="titulo" scope="page" />
<tiles:importAttribute name="apartados" scope="page" />
<tiles:importAttribute name="expand" scope="page" />
<tiles:useAttribute name="name" scope="page" classname="java.lang.String" />
<logic:notEqual name="expand" value="<%=name%>">
    <tr><td onMouseOver="this.style.backgroundColor='#97aac0'" onMouseOut="this.style.backgroundColor='#515b67'">
        <html:link action="/menu" paramId="expand" paramName="name"><bean:message name="titulo"/></html:link>
    </td></tr>
</logic:notEqual>
<logic:equal name="expand" value="<%=name%>">
    <tr><td class="select1">
        <bean:define id="none">none</bean:define>
        <html:link action="/menu" paramId="expand" paramName="none"><bean:message name="titulo"/></html:link>
    </td></tr>
    <tr><td class="select2">
        <logic:iterate id="apartado" name="apartados">
            <ul><tiles:insert beanName="apartado" flush="false"/></ul>
        </logic:iterate>
    </td></tr>

</logic:equal>

