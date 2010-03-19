<%@ page language="java" %>
<%@ page import="org.ibit.rol.form.back.util.Util"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>

<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/componente/ayudaExpresion.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>

<html:hidden property="idFormulario" />
<input type="hidden" name="idOperacion" value="<%=Util.getIdOperacion(request)%>"/>	

<logic:empty name="pantallaForm" property="values.componenteListaElementos"> 
<tr>
    <td class="labelo"><bean:message key="pantalla.nombre"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.nombre" maxlength="128" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="pantalla.expresion"/></td>
    <td class="input"><html:textarea tabindex="2" property="values.expresion" /></td>
</tr>
<tr>
    <td class="label" colspan="2">
        <button class="buttonl" type="button" onclick="viewAyudaExpresion()">
            <bean:message key="boton.ayuda.expresion"/>
        </button>
    </td>
</tr>
<tr>
    <td class="label"><bean:message key="pantalla.pinicial"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="3" property="values.inicial" /></td>
</tr>
<tr>
    <td class="label"><bean:message key="pantalla.pfinal"/></td>
    <td class="input"><html:checkbox styleClass="check" tabindex="4" property="values.ultima" /></td>
</tr>
<!-- 
<tr>
    <td class="label"><bean:message key="pantalla.componenteListaElementos"/></td>
    <td class="input"><html:text styleClass="text" tabindex="1" property="values.componenteListaElementos" maxlength="128" /></td>
</tr>
 -->
</logic:empty>

<logic:notEmpty name="pantallaForm" property="values.componenteListaElementos"> 
   <html:hidden property="values.nombre"/>
   <html:hidden property="values.expresion" />
   <html:hidden property="values.inicial" />
   <html:hidden property="values.ultima" />   
</logic:notEmpty>