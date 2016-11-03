<%@ page language="java" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>
<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }
     // -->
</script>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.instruccionesInicio"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="traduccion.instruccionesInicio"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=traduccion.instruccionesInicio&titulo=especificacionesTramite.instruccionesInicio" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.mensajeInactivo"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="traduccion.mensajeInactivo"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=traduccion.mensajeInactivo&titulo=especificacionesTramite.mensajeInactivo" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.instruccionesFin"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="traduccion.instruccionesFin"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.instruccionesEntrega"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="traduccion.instruccionesEntrega"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=traduccion.instruccionesEntrega&titulo=especificacionesTramite.instruccionesEntrega" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="especificacionesTramite.mensajeFechaLimiteEntregaPresencial"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="traduccion.mensajeFechaLimiteEntregaPresencial"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=traduccion.mensajeFechaLimiteEntregaPresencial&titulo=especificacionesTramite.mensajeFechaLimiteEntregaPresencial" %>');"/></td>
</tr>