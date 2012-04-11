<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function switchValidadores() {
        swichDisplay('validadoresFirma');
     }

     function switchRoles() {
         swichDisplay('roles');
     }
       // -->
</script>

<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	

<tr>
    <td class="labelo"><bean:message key="formulario.modelo"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.modelo" maxlength="20" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.version"/></td>
    <td class="input"><html:text styleClass="t30" maxlength="3" property="values.version" disabled="true" /></td>
</tr>

<logic:present name="formularioSeguroForm" property="values.logotipo1">
    <tr>
        <td class="label"><bean:message key="formulario.logo1" /></td>
        <td class="input">
            <html:img page="/back/formulario/logotipo1.do" paramId="id" paramName="formularioSeguroForm" paramProperty="values.id" />
            <%-- <input type="text" name="temp" class="curt" readonly="readonly" value='<bean:write name="formularioForm" property="values.logotipo1.nombre"/>' /> --%>
            <html:submit property="borrarLogo1" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.logo1.nuevo"/></td>
</logic:present>
<logic:notPresent name="formularioSeguroForm" property="values.logotipo1">
    <input type="hidden" name="temp" value="" />
    <tr>
        <td class="label"><bean:message key="formulario.logo1"/></td>
</logic:notPresent>
    <td class="input"><html:file property="logotipo1" styleClass="file" tabindex="2"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.logotipo1"/></td>
</tr>

<tr>
    <td class="label"><bean:message key="formulario.url1"/></td>
    <td class="input">
        <html:text tabindex="3" styleClass="url" property="values.urlEntidad1" maxlength="256" />
        <html:button  property="boto"   styleClass="button" onclick="obrirTest(this.form.elements['values.urlEntidad1'].value)">
           <bean:message key="boton.test"/>
        </html:button>
    </td>
</tr>

<logic:present name="formularioSeguroForm" property="values.logotipo2">
    <tr>
        <td class="label"><bean:message key="formulario.logo2" /></td>
        <td class="input">
            <html:img page="/back/formulario/logotipo2.do" paramId="id" paramName="formularioSeguroForm" paramProperty="values.id" />
            <%-- <input type="text" name="temp" class="curt" readonly="readonly" value='<bean:write name="formularioForm" property="values.logotipo2.nombre"/>' /> --%>
            <html:submit property="borrarLogo2" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
        </td>
    </tr>
    <tr>
        <td class="label"><bean:message key="formulario.logo2.nuevo"/></td>
</logic:present>
<logic:notPresent name="formularioSeguroForm" property="values.logotipo2">
    <input type="hidden" name="temp" value="" />
    <tr>
        <td class="label"><bean:message key="formulario.logo2"/></td>
</logic:notPresent>
    <td class="input"><html:file property="logotipo2" styleClass="file" tabindex="4"/></td>
</tr>
<tr>
    <td class="labela" colspan="2"><bean:message key="ayuda.logotipo2"/></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.url2"/></td>
    <td class="input">
        <html:text tabindex="5" styleClass="url" property="values.urlEntidad2" maxlength="256" />
        <html:button  property="boto"   styleClass="button" onclick="obrirTest(this.form.elements['values.urlEntidad2'].value)">
           <bean:message key="boton.test"/>
        </html:button>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.hasbarcode"/></td>
    <bean:define id="hasBarcode" name="formularioSeguroForm" property="values.hasBarcode" type="java.lang.Boolean"/>
    <td class="input"><html:checkbox styleClass="check" tabindex="6" property="values.hasBarcode" onchange="changeBarcode(this)" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.posbarcodex"/></td>
    <td class="input"><html:text styleClass="data" maxlength="4" tabindex="7" property="values.posBarcodeX" disabled="<%=!hasBarcode.booleanValue()%>" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.posbarcodey"/></td>
    <td class="input"><html:text styleClass="data" maxlength="4" tabindex="8" property="values.posBarcodeY" disabled="<%=!hasBarcode.booleanValue()%>" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.seguro.ssl"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="9" property="values.https" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.seguro.login"/></td>
    <bean:define id="reqLogin" name="formularioSeguroForm" property="values.requerirLogin" type="java.lang.Boolean"/>
    <td class="input"><html:checkbox styleClass="check" tabindex="10" property="values.requerirLogin" onchange="javascript:switchRoles()"/></td>
</tr>

 <% if(reqLogin.booleanValue()){ %>
        <tr id="roles">
            <td class="label"><bean:message key="formulario.seguro.roles"/></td>
            <td class="input">
                  <html:text tabindex="13" styleClass="text" name="formularioSeguroForm" property="rolesString" maxlength="400" />
            </td>
        </tr>
        <%}else{%>
        <tr id="roles" style="display:none;">
            <td class="label"><bean:message key="formulario.seguro.roles"/></td>
            <td class="input">
                <html:text tabindex="13" styleClass="text" name="formularioSeguroForm" property="rolesString" maxlength="400" />
            </td>
        </tr>
<%}%>
<tr>
    <td class="label"><bean:message key="formulario.seguro.certificado"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="11" property="values.requerirCertificado" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="formulario.seguro.firma"/></td>
    <bean:define id="reqFirma" name="formularioSeguroForm" property="values.requerirFirma" type="java.lang.Boolean"/>
    <td class="input"><html:checkbox styleClass="check" tabindex="12" property="values.requerirFirma"  onchange="javascript:switchValidadores()"/></td>
</tr>




<% if(reqFirma.booleanValue()){ %>
    <tr id="validadoresFirma">
        <td class="label"><bean:message key="formulario.seguro.validadores"/></td>

        <td class="input">
            <html:select multiple="multiple" name="formularioSeguroForm" property="validadores_ids" size="5">
                <html:options collection="validadorOptions" property="id" labelProperty="nombre"/>
            </html:select>
        </td>
    </tr>
<%}else{%>
    <tr id="validadoresFirma" style="display:none;">
        <td class="label"><bean:message key="formulario.seguro.validadores"/></td>

        <td class="input">
            <html:select multiple="multiple" name="formularioSeguroForm" property="validadores_ids" size="5">
                <html:options collection="validadorOptions" property="id" labelProperty="nombre"/>
            </html:select>
        </td>
    </tr>
<%}%>

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
