<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%--
    Apartado de un menu.
    Atributs tiles: 
        titulo - Nombre etiqueta del titulo
        opciones - lista de items con las opciones.
                    value - Etiqueta
                    link - Action encargada
--%>
<html:xhtml />
<tiles:importAttribute name="titulo" scope="page" />
<tiles:importAttribute name="opciones" scope="page" />
<li class="nom"><bean:message name="titulo"/>:</li>
<bean:size id="numOpciones" name="opciones" />
<ul>
<logic:iterate id="item" name="opciones" indexId="index">
    <li>
    <bean:define id="accion" name="item" property="link" type="java.lang.String" />
    <html:link styleClass="boton" action="<%=accion%>" target="Ventana">
        <bean:message name="item" property="value"/>
    </html:link><% if (index.intValue() < (numOpciones.intValue() - 1)) { %><% } %>
    </li>
</logic:iterate>
</ul>
