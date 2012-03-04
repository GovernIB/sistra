<%@ page language="java" import="es.caib.mobtratel.model.Cuenta"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyuda() {
        var url = '<html:rewrite page="/cuenta/ayuda.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="cuenta.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="cuenta.codigo"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.codigo" maxlength="5" readonly="<%= request.getAttribute( "idReadOnly" ) != null %>" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="cuenta.nombre"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="2" property="values.nombre" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="cuenta.email"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="3" property="values.email" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="cuenta.sms"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="4" property="values.sms" maxlength="50"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="cuenta.defecto"/></td>
    <td class="input">Si<html:radio property="values.defecto" value="1"/> No <html:radio property="values.defecto" value="0"/></td>
