<%@ page language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<html:xhtml/>
<script type="text/javascript">
     <!--
     function viewAyudaExpresion() {
        var url = '<html:rewrite page="/gestorBandeja/ayudaPantalla.jsp" />';
        obrir(url, "Edicion", 540, 400);
     }
     // -->
</script>
<tr>
	<td class="separador" colspan="2"><bean:message key="permiso.definicion"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="permiso.usuarioSeycon"/></td>
    <td class="input"><html:text styleClass="data" tabindex="1" property="values.usuarioSeycon" maxlength="50" readonly="<%= request.getAttribute( "idReadOnly" ) != null %>"/></td>
</tr>
<tr>
	<td class="separador" colspan="2"><bean:message key="permiso.propiedades"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="permiso.email"/></td>
    <td class="input">Si<html:radio property="values.email" value="1"/> No<html:radio property="values.email" value="0"/></td>
</tr>
<tr>
    <td class="labelo"><bean:message key="permiso.sms"/></td>
    <td class="input">Si<html:radio property="values.sms" value="1"/> No<html:radio property="values.sms" value="0"/></td>
</tr>
<tr>
	<td class="labelo"><bean:message key="permiso.cuenta"/></td>
	<td class="labelo">
	<!--  
	<table width="100%">		
		<logic:iterate id="cuenta" name="cuentaOptions" type="es.caib.mobtratel.model.Cuenta">		
			<tr>			
				<td align="left">
					<html:multibox property="cuentas" value="<%= cuenta.getCodigo() %>"/> <bean:write name="cuenta" property="codigo" />
				</td>
			</tr>			
		</logic:iterate>
	</table>
	-->
	<html:select property="cuentas">
   			<html:options collection="cuentaOptions" property="codigo" labelProperty="nombre" />
    </html:select>
	
	</td>
</tr>

