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
	<td class="separador" colspan="2"><bean:message key="mensajeTramite.datosTextos"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="mensajeTramite.mensaje"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="traduccion.mensaje" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=traduccion.mensaje&titulo=mensajeTramite.mensaje" %>');"/></td>
</tr>
