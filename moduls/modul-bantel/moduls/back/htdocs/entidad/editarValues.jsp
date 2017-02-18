<%@ page language="java" import="es.caib.bantel.model.Procedimiento"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
<script type="text/javascript">
     <!--
     function viewAyuda() {
        var url = '<html:rewrite page="/entidad/ayuda.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="entidad.titulo"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="entidad.codigo"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.identificador" maxlength="50" readonly="<%= request.getAttribute( \"idReadOnly\" ) != null %>" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="entidad.descripcion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.descripcion" maxlength="100"/></td>
</tr>
