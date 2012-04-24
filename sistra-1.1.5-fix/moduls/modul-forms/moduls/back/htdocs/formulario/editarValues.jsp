<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	

<logic:present name="bloqueadoPor">
	<tr>
		<td>&nbsp;</td>
		<td><bean:message key="formulario.bloqueadoPor" arg0="<%=(String) request.getAttribute("bloqueadoPor")%>"/></td>
	</tr>
</logic:present>
<tr>
    <td class="labelo"><bean:message key="formulario.modelo"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.modelo" maxlength="20" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.version"/></td>
    <td class="input"><html:text styleClass="t30" maxlength="3" property="values.version" disabled="true" /></td>
</tr>


<!--  INDRA: MODOS FUNCIONAMIENTO -->
<tr>
	<td class="labelo"><bean:message key="formulario.modoFuncionamiento"/></td>
    <td class="input">
		<html:select name="formularioForm" property="modoFuncionamientoCod">
        	<html:options collection="modosFuncionamiento" property="codigo" labelProperty="nombre" />
    	</html:select>
    </td>
</tr>
<!--  INDRA: FIN -->
	
<logic:present name="formularioForm" property="values.logotipo1">
    <tr>
        <td class="label"><bean:message key="formulario.logo1" /></td>
        <td class="input">
            <html:img page="/back/formulario/logotipo1.do" paramId="id" paramName="formularioForm" paramProperty="values.id" />
            <%-- <input type="text" name="temp" class="curt" readonly="readonly" value='<bean:write name="formularioForm" property="values.logotipo1.nombre"/>' /> --%>
            <logic:present name="bloqueado">
                <html:submit property="borrarLogo1" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
            </logic:present>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.logo1.nuevo"/></td>
</logic:present>
<logic:notPresent name="formularioForm" property="values.logotipo1">
    <input type="hidden" name="temp" value="" />
    <tr>
        <td class="label"><bean:message key="formulario.logo1"/></td>
</logic:notPresent>
    <td class="input"><html:file property="logotipo1" styleClass="file" tabindex="4"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.logotipo1"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="formulario.url1"/></td>
    <td class="input">
        <html:text tabindex="3" styleClass="url" property="values.urlEntidad1" maxlength="256" />
        <html:button  property="boto" styleClass="button" onclick="obrirTest(this.form.elements['values.urlEntidad1'].value)">
           <bean:message key="boton.test"/>
        </html:button>
    </td>
</tr>

<logic:present name="formularioForm" property="values.logotipo2">
    <tr>
        <td class="label"><bean:message key="formulario.logo2" /></td>
        <td class="input">
            <html:img page="/back/formulario/logotipo2.do" paramId="id" paramName="formularioForm" paramProperty="values.id" />
            <%-- <input type="text" name="temp" class="curt" readonly="readonly" value='<bean:write name="formularioForm" property="values.logotipo2.nombre"/>' /> --%>
            <logic:present name="bloqueado">
                <html:submit property="borrarLogo2" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
            </logic:present>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.logo2.nuevo"/></td>
</logic:present>
<logic:notPresent name="formularioForm" property="values.logotipo2">
    <input type="hidden" name="temp" value="" />
    <tr>
        <td class="label"><bean:message key="formulario.logo2"/></td>
</logic:notPresent>
    <td class="input"><html:file property="logotipo2" styleClass="file" tabindex="6"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.logotipo2"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.url2"/></td>
    <td class="input">
        <html:text tabindex="7" styleClass="url" property="values.urlEntidad2" maxlength="256" />
        <html:button  property="boto"   styleClass="button" onclick="obrirTest(this.form.elements['values.urlEntidad2'].value)">
           <bean:message key="boton.test"/>
        </html:button>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.hasbarcode"/></td>
    <bean:define id="hasBarcode" name="formularioForm" property="values.hasBarcode" type="java.lang.Boolean"/>
    <td class="input"><html:checkbox styleClass="check" tabindex="8" property="values.hasBarcode" onchange="changeBarcode(this)" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.posbarcodex"/></td>
    <td class="input"><html:text styleClass="data" maxlength="4" tabindex="9" property="values.posBarcodeX" disabled="<%=!hasBarcode.booleanValue()%>" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.posbarcodey"/></td>
    <td class="input"><html:text styleClass="data" maxlength="4" tabindex="10" property="values.posBarcodeY" disabled="<%=!hasBarcode.booleanValue()%>" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.tagCuadernoCarga"/></td>
    <td class="input"><html:text styleClass="text" tabindex="10" property="values.cuadernoCargaTag" maxlength="100"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.fechaExportacion"/></td>
    <td class="input"><bean:write name="formularioForm" property="values.fechaExportacion" format="dd/MM/yyyy - HH:mm:ss"/></td>
</tr>
<script type="text/javascript">
<!--
    function changeBarcode(camp) {
        campx = camp.form.elements['values.posBarcodeX'];
        campy = camp.form.elements['values.posBarcodeY'];
        campx.disabled = !camp.checked
        campy.disabled = !camp.checked
    }
//-->
</script>
