<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>

<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/datoJustificante/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<script type="text/javascript">
     <!--
     function edit(url) {
       obrir(url, "Edicion", 940, 600);
     }
     // -->
</script>
<html:hidden property="idEspecTramiteNivel" />
<html:hidden property="idTramiteNivel" />
<tr>
	<td class="separador" colspan="2"><bean:message key="datoJustificante.datos"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="datoJustificante.tipo"/></td>
    <td class="input"><bean:message key="datoJustificante.separadorBloques"/><html:radio property="values.tipo" value="B"/> <bean:message key="datoJustificante.campo"/><html:radio property="values.tipo" value="C"/> <bean:message key="datoJustificante.indice"/><html:radio property="values.tipo" value="I"/></td>
</tr>
<!-- 
<tr>
    <td class="label"><bean:message key="datoJustificante.orden"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.orden" maxlength="2" /></td>
</tr>
 -->
<tr>
    <td class="label"><bean:message key="datoJustificante.referenciaCampo"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="values.referenciaCampo"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=values.referenciaCampo&titulo=datoJustificante.referenciaCampo" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="datoJustificante.valorScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="valorCampoScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=valorCampoScript&titulo=datoJustificante.valorScript" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="datoJustificante.visibleScript"/></td>
    <td class="input"><html:textarea styleClass="text" tabindex="10" property="visibleScript"/><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=visibleScript&titulo=datoJustificante.visibleScript" %>');"/></td>
</tr>


