<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/especificacionesTramite/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<html:hidden property="idTramiteVersion" />
<tr>
	<td class="separador" colspan="2"><bean:message key="tramiteNivel.nivelAutenticacion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="tramiteNivel.nivelAutenticacion"/></td>
    <td class="input">Certificado <html:multibox property="nivelesAutenticacionSelected" value="C"/> Usuario/Password <html:multibox property="nivelesAutenticacionSelected" value="U"/> Anónimo <html:multibox property="nivelesAutenticacionSelected" value="A"/></td>
</tr>



