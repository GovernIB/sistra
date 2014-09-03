<%@ page import="org.ibit.rol.form.model.*"%>
<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml />

<tiles:importAttribute name="titulo" scope="page" />
<tiles:importAttribute name="id" scope="page" />
<tiles:useAttribute name="options" scope="page" classname="java.lang.String" />
<tiles:useAttribute name="prop" scope="page" classname="java.lang.String" />
<tiles:importAttribute name="componenteOptions" scope="page" />
<tiles:importAttribute name="idPantalla" scope="page" />

<bean:size id="numOptions" name="<%=options%>" />

<logic:equal name="numOptions" value="0">
    <ul><li><i><bean:message key="arbol.componente.no" /></i></li></ul>
</logic:equal>

<logic:notEqual name="numOptions" value="0">
    <ul>
    <logic:iterate  id="nodo" name="<%=options%>" >
        <li>
        <html:link action="/back/componente/seleccion" paramId="id" paramName="nodo" paramProperty="id" target="Ventana">
            <bean:write name="nodo" property="<%=prop%>" />
        </html:link>
        </li>
    </logic:iterate>
    </ul>
</logic:notEqual>
<br />
