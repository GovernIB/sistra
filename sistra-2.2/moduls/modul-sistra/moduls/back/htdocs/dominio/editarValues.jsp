<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<bean:define id="urlEditarText">
    <html:rewrite page="/editarText.jsp"/>
</bean:define>

<bean:define id="urlPing">
    <html:rewrite page="/back/dominio/ping.do"/>
</bean:define>

<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/dominio/ayudaPantalla.jsp" />';
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
<tr>
	<td class="separador" colspan="2"><bean:message key="dominio.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="dominio.identificador"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.identificador" maxlength="20" /></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="dominio.parametrosDominio"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="dominio.descripcion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="1" property="values.descripcion" maxlength="100" /></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="dominio.organo"/></td>
    <td class="input">
	    	<html:select property="idOrgano">
	   			<html:options collection="organoOptions" property="codigo" labelProperty="descripcion" />
	    	</html:select>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="dominio.tipo"/></td>
    <td class="input">
	    <table>
	    	<tr>
	    		<td>EJB <html:radio property="values.tipo" value="E"/></td>
	    		<td>Web Service <html:radio property="values.tipo" value="W"/></td>
	    		<td>SQL <html:radio property="values.tipo" value="S"/></td>
	    		<td><bean:message key="dominio.tipo.fuenteDatos"/> <html:radio property="values.tipo" value="F"/></td>
	    	</tr>
	    </table>
	</td>
</tr>
<tr>
    <td class="labelo">Cacheable:</td>
    <td class="input"><table><tr><td>Si <html:radio property="values.cacheable" value="S"/></td><td>No <html:radio property="values.cacheable" value="N"/></td></tr></table></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.url"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.url" maxlength="200"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.versionWS"/></td>
    <td class="input">
    	<html:select property="values.versionWS">
   			<html:options collection="listaVersionesWS" property="CODIGO" labelProperty="DESCRIPCION" />
    	</html:select>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.sql"/></td>
    <td class="input"><html:textarea tabindex="10" property="values.sql" /><input type="button" value="..."  class = "botonEditar" onclick="edit('<%=urlEditarText + "?id=values.sql&titulo=dominio.sql" %>');"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.JNDIName"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.JNDIName" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.localizacionEJB"/></td>
    <td class="input"><table><tr><td>Local <html:radio property="values.localizacionEJB" value="L"/></td><td>Remoto <html:radio property="values.localizacionEJB" value="R"/></td></tr></table></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.tipoAcceso"/></td>
    <td class="input"><table><tr><td>No <html:radio property="values.autenticacionExplicita" value="N"/></td><td><bean:message key="dominio.tipoAcceso.authExplicitaUserPass"/> <html:radio property="values.autenticacionExplicita" value="S"/><td><bean:message key="dominio.tipoAcceso.authExplicitaOrganismo"/> <html:radio property="values.autenticacionExplicita" value="C"/></td></tr></table></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.usr"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="userPlain" maxlength="200"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="dominio.pwd"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="passPlain" maxlength="50"/></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td class="input">
		<button onclick="obrir('<%=urlPing%>?dominio=<bean:write name="dominioForm" property="values.identificador"/>')" class="button">Ping</button>
	</td>
</tr>

