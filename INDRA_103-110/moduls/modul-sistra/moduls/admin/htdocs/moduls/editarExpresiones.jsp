<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<tiles:useAttribute name="tabindex" classname="java.lang.Integer" />
<% int ti = tabindex.intValue(); %>
<script type="text/javascript">
     <!--
     function switchExpresiones() {
        swichDisplay('trShowExp');
        swichDisplay('trExpAut');
        swichDisplay('trExpDep');
        swichDisplay('trExpVal');
        swichDisplay('trExpVpo');
        swichDisplay('trExpPos');
        swichDisplay('trHideExp');
     }

     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/componente/ayudaExpresion.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<tr id="trShowExp">
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="javascript:switchExpresiones()">
            <bean:message key="boton.showExpresiones"/>
        </button>
    </td>
</tr>
<tr id="trExpAut" style="display: none;">
    <td class="label"><bean:message key="componente.autocalculo"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="values.expresionAutocalculo" /></td>
</tr>
<tr id="trExpDep" style="display: none;">
    <td class="label"><bean:message key="componente.dependencia"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="values.expresionDependencia" /></td>
</tr>
<tr id="trExpVal" style="display: none;">
    <td class="label"><bean:message key="componente.validacion"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="values.expresionValidacion" /></td>
</tr>
<tr id="trExpVpo" style="display: none;">
    <td class="label"><bean:message key="componente.expvalores"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="values.expresionValoresPosibles" /></td>
</tr>
<tr id="trExpPos" style="display: none;">
    <td class="label"><bean:message key="componente.exppostproceso"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="values.expresionPostProceso" /></td>
</tr>
<tr id="trHideExp" style="display: none;">
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="viewAyudaExpresion()">
            <bean:message key="boton.ayuda.expresion"/>
        </button>
        <button class="buttonl" type="button" onclick="javascript:switchExpresiones()">
            <bean:message key="boton.hideExpresiones"/>
        </button>

    </td>
</tr>