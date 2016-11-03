<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<script type="text/javascript">
<!--

function resetHasta()
{
    document.forms[0].anyoFinal.value = "";
}

function buscar()
{
    resetHasta();
	document.forms[0].submit();
}

function buscarHasta()
{
	if (document.forms[0].anyoFinal.value == "" ) 
	{
	   alert("Debe indicar el año Hasta");
	   return;
	}
	if (document.forms[0].anyoFinal.value < document.forms[0].anyoInicio.value) 
	{
	   alert("El año Hasta debe ser superior al año Desde");
	   return;
	}

	document.forms[0].submit();
}
//-->
</script>

<%
  String anyoInicio = (String)request.getAttribute("anyoInicio");
  String anyoFinal = (String)request.getAttribute("anyoFinal");
%>
<div style="padding:10px; ">
	<html:form action="/anual">
		<div style="background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; ">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr>
				<td width="1%">&nbsp;</td>
				<td width="5%"><img src="././images/cuadromando/i_plazo.gif" width="36" height="26" border="0"></td>
				<td width="15%">
				<!--  
    	            <html:select property="anyoInicio" >
		              <html:options name="anyos" labelName="anyos" />
     				</html:select> 
     				-->
     				<strong><bean:message key="anual.anyo"/>:
     			    <select name="anyoInicio" id="anyoInicio" property="anyoInicio" class="txt">
						<logic:iterate id="anyo" name="anyos">
						   <logic:equal name="anyo" value="<%= anyoInicio %>">
								<option selected="true" value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
						   </logic:equal>
 				           <logic:notEqual name="anyo" value="<%= anyoInicio %>">
								<option  value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
 				           </logic:notEqual>
						</logic:iterate>
     				</select>
					</strong>
				</td>
				<td width="10%"><a href="javascript:buscar();" class="buscarDesde"><bean:message key="anual.buscar"/></a></td>
				<td width="20%">&nbsp;</td>
				<td width="15%">
					<strong><bean:message key="anual.hasta"/>: 
					<select name="anyoFinal" id="anyoFinal" property="anyoFinal" class="txt">
					   	<logic:equal name="anyo" value="<%= anyoFinal %>">
							<option  value="" selected="true" /></option>
					   	</logic:equal>
			           	<logic:notEqual name="anyo" value="<%= anyoFinal %>">
							<option  value="" /></option>
				         </logic:notEqual>
						<logic:iterate id="anyo" name="anyos">
						   <logic:equal name="anyo" value="<%= anyoFinal %>">
								<option selected="true" value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
						   </logic:equal>
 				           <logic:notEqual name="anyo" value="<%= anyoFinal %>">
								<option  value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
 				           </logic:notEqual>
						</logic:iterate>
     				 </select>
			</strong>
				</td>
				<td width="10%"><a href="javascript:buscarHasta();" class="buscarHasta"><bean:message key="anual.hasta.buscar"/></a></td>
				<td>&nbsp;</td>
			</tr>
			</table>
		</div>
		
		<tiles:insert name=".cuadroMandoTemporal" />
		
	</html:form>
</div>