<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<tiles:useAttribute name="tabindex" classname="java.lang.Integer" />
<tiles:useAttribute name="expresionesPermitidas" classname="java.lang.String" ignore="true" />


<% int ti = tabindex.intValue(); %>
<script type="text/javascript">
     <!--
     function switchExpresiones() {
       	swichDisplay('trExpAur');
        swichDisplay('trShowExp');       
	    swichDisplay('trExpVpo');
        swichDisplay('trExpAut');
        swichDisplay('trExpDep');
        swichDisplay('trExpVal');
        /** -- INDRA: NO SENTIDO EN TELEMATICO 
        swichDisplay('trExpPos');
        **/
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
<!-- INDRA: MODIFICACION PARA QUE MUESTRE NUEVA EXPRESION AUTORELLENABLE	-->
<tr id="trExpAur" style="display: none;">
    <td class="label"><bean:message key="componente.autorellenable"/></td>
    <td class="input"><html:textarea readonly="<%=!(expresionesPermitidas==null || (expresionesPermitidas!=null&&expresionesPermitidas.indexOf("1")!=-1))%>" tabindex="<%=Integer.toString(ti++)%>" property="values.expresionAutorellenable" /></td>
</tr>
<!-- INDRA: FIN -->
<tr id="trExpAut" style="display: none;">
    <td class="label"><bean:message key="componente.autocalculo"/></td>
    <td class="input"><html:textarea readonly="<%=!(expresionesPermitidas==null || (expresionesPermitidas!=null&&expresionesPermitidas.indexOf("2")!=-1))%>" tabindex="<%=Integer.toString(ti++)%>" property="values.expresionAutocalculo" /></td>
</tr>
<tr id="trExpVpo" style="display: none;">
    <td class="label"><bean:message key="componente.expvalores"/></td>
    <td class="input"><html:textarea readonly="<%=!(expresionesPermitidas==null || (expresionesPermitidas!=null&&expresionesPermitidas.indexOf("3")!=-1))%>" tabindex="<%=Integer.toString(ti++)%>" property="values.expresionValoresPosibles" /></td>
</tr>
<tr id="trExpDep" style="display: none;">
    <td class="label"><bean:message key="componente.dependencia"/></td>
    <td class="input"><html:textarea readonly="<%=!(expresionesPermitidas==null || (expresionesPermitidas!=null&&expresionesPermitidas.indexOf("4")!=-1))%>" tabindex="<%=Integer.toString(ti++)%>" property="values.expresionDependencia" /></td>
</tr>
<tr id="trExpVal" style="display: none;">
    <td class="label"><bean:message key="componente.validacion"/></td>
    <td class="input"><html:textarea readonly="<%=!(expresionesPermitidas==null || (expresionesPermitidas!=null&&expresionesPermitidas.indexOf("5")!=-1))%>" tabindex="<%=Integer.toString(ti++)%>" property="values.expresionValidacion" /></td>
</tr>
<!-- INDRA: NO SENTIDO EN TELEMATICO 
<tr id="trExpPos" style="display: none;">
    <td class="label"><bean:message key="componente.exppostproceso"/></td>
    <td class="input"><html:textarea tabindex="<%=Integer.toString(ti++)%>" property="values.expresionPostProceso" /></td>
</tr>
 -->
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