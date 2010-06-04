<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/organo/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<tr>
    <td class="labelo"><bean:message key="organo.descripcion"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.descripcion" maxlength="200" /></td>
</tr>