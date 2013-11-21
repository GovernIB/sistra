<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/mensajePlataforma/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>

<bean:define id="identificador" name="mensajePlataformaForm" property="values.identificador" type="java.lang.String"/>

<tr>
	<td class="separador" colspan="2"><bean:message key="mensajePlataforma.datosTramite"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="mensajePlataforma.identificador"/></td>    
    <td class="input"><bean:message key="<%=\"mensajePlataforma.\"+identificador%>"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="mensajePlataforma.activo"/></td>    
    <td class="input">Si<html:radio property="values.activo" value="S"/> No<html:radio property="values.activo" value="N"/></td>
</tr>



