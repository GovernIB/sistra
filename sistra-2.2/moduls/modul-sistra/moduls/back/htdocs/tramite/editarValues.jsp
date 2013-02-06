<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/tramite/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<html:hidden property="idOrgano" />
<tr>
	<td class="separador" colspan="2"><bean:message key="tramite.datosTramite"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramite.identificador"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.identificador" maxlength="20" /></td>    
</tr>
<tr>
    <td class="labelo"><bean:message key="tramite.procedimiento"/></td>
    <td class="input"><html:text styleClass="text" tabindex="2" property="values.procedimiento" maxlength="100" /></td>    
</tr>

