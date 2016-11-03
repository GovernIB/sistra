<%@ page language="java" import="es.caib.bantel.model.Procedimiento"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.1.min.js"></script>
<script type="text/javascript">
     <!--
     function viewAyuda() {
        var url = '<html:rewrite page="/fuenteDatos/ayuda.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }          
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="fuenteDatos.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="fuenteDatos.identificador"/></td>
    <td class="input">
    	<html:text styleClass="data" tabindex="1" property="values.identificador" styleId="identificador" maxlength="20" />
    	<html:hidden property="identificadorOld"/>
    </td>
</tr>
<tr>
    <td class="labelo"><bean:message key="fuenteDatos.descripcion"/></td>
    <td class="input"><html:text styleClass="textLargo" tabindex="10" property="values.descripcion" maxlength="100"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="fuenteDatos.procedimiento"/></td>
    <td class="input">
    	<html:select property="idProcedimiento">
			<logic:iterate id="procedimiento" name="procedimientosOptions" type="es.caib.bantel.model.Procedimiento">															
				<html:option value="<%=procedimiento.getIdentificador()%>">
					<%=procedimiento.getIdentificador() + "-" + (procedimiento.getDescripcion().length()>60?procedimiento.getDescripcion().substring(0,60)+"...":procedimiento.getDescripcion())%>
				</html:option>
			</logic:iterate>
		</html:select>
    </td>
</tr>
	