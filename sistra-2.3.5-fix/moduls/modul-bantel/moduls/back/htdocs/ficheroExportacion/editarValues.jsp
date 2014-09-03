<%@ page language="java"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyuda() {
        var url = '<html:rewrite page="/ficheroExportacion/ayuda.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }        
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="ficheroExportacion.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="ficheroExportacion.codigo"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.identificadorTramite" maxlength="20" readonly="<%= request.getAttribute( \"idReadOnly\" ) != null %>" /></td>
</tr>

<tr>
	<td class="separador" colspan="2"><bean:message key="ficheroExportacion.ficheroExportacion"/></td>
</tr>
<logic:present name="ficheroExportacionForm" property="values.archivoFicheroExportacion">															  
    <tr>
        <td class="label"><bean:message key="ficheroExportacion.fichero" /></td>
        <input type="hidden" name="temp" value="tmp" />
        <td class="input">            
            <html:link page='<%="/back/ficheroExportacion/mostrarFichero.do"%>' paramId="codigo" paramName="ficheroExportacionForm" paramProperty="values.identificadorTramite"><bean:write name="ficheroExportacionForm" property="values.nombre"/></html:link>
            <html:submit property="borrarFicheroExportacion" styleClass="button"><bean:message key="boton.borrar"/></html:submit>
        </td>
    </tr>   
</logic:present>

<logic:notPresent name="ficheroExportacionForm" property="values.archivoFicheroExportacion">
    <!--   <input type="hidden" name="temp" value="" /> -->
    <tr>
        <td class="label"><bean:message key="ficheroExportacion.fichero"/></td>
        <td class="input"><html:file property="fichero" styleClass="file" tabindex="13"/></td>
    </tr>
</logic:notPresent>
        

<tr>
        <td class="label">&nbsp;</td>
        <td class="input"><input type="button" onclick="viewAyuda();" value="Ayuda"  class="button"/></td>            
</tr>