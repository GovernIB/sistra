<%@ page language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<bean:define id="securePath" name="securePath" scope="request"/>
<html:xhtml/>

<nested:root>
    <nested:define id="campo" type="org.ibit.rol.form.model.FileBox"/>
    <nested:define id="nombre" property="nombreLogico" type="java.lang.String"/>
    <% boolean autocalculo = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionAutocalculo())); %>
    <% boolean disabled = StringUtils.isNotEmpty(StringUtils.strip(campo.getExpresionDependencia())); %>
    <html:hidden
       property="<%=nombre%>"
       onchange='<%=(!autocalculo) ? "onFieldChange(this.form, this.name)" : ""%>'
    />
    <input type="hidden" name="<%=nombre%>_mime" />
    <input type="hidden" name="<%=nombre%>_size" />
    <iframe name="<%=nombre%>_iframe"
            <% if (disabled) { %>
            src="<html:rewrite page='<%=securePath + "/anexo.do?disabled=true&nombre=" + nombre%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />"
            <% } else { %>
            src="<html:rewrite page='<%=securePath + "/anexo.do?nombre=" + nombre%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />"
            <% } %>
            id="<%=nombre%>_iframe"
            frameborder="0"
            scrolling="no"
            allowtransparency="true"
            marginheight="0"
            marginwidth="0"
            width="100%"
            height="24"
    ></iframe>
</nested:root>