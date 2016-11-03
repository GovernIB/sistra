<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<script type="text/javascript">
<!--

function resetHasta()
{
    document.forms[0].anyoFinal.value = "";
    document.forms[0].mesFinal.value = "";
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
	if ( document.forms[0].mesFinal.value == "" )
	{
	   alert("Debe indicar el mes Hasta");
	   return;
	}
	if (!compruebaPeriodoFechas("01/" + document.forms[0].mesInicio.value + "/" + document.forms[0].anyoInicio.value ,"01/" + document.forms[0].mesFinal.value + "/" + document.forms[0].anyoFinal.value)) 
	{
	   alert("La fecha Hasta debe ser superior a la fecha Desde");
	   return;
	} 			
	document.forms[0].submit();
}
//-->
</script>

<%
  String anyoInicio = (String)request.getAttribute("anyoInicio");
  String mesInicio = (String)request.getAttribute("mesInicio");
  String anyoFinal = (String)request.getAttribute("anyoFinal");
  String mesFinal = (String)request.getAttribute("mesFinal");
%>
<div style="padding:10px; ">
    <html:form action="/mensual">
		<input type="hidden" name="OPCION" value="M">  
		<input type="hidden" name="HASTA">  
		<div style="background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; ">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr>
				<td width="1%">&nbsp;</td>
				<td width="5%"><img src="././images/cuadromando/i_plazo.gif" width="36" height="26" border="0"></td>
				<td width="14%">
					<strong><bean:message key="mensual.anyo"/>:
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
				<td width="19%">
					<strong><bean:message key="mensual.mes"/>:
						<select name="mesInicio" class="txt">
							<option value="01" <% if (mesInicio.equals("01")) {%>selected="true"<%}%>><bean:message key="mes.0"/></option>
							<option value="02" <% if (mesInicio.equals("02")) {%>selected="true"<%}%>><bean:message key="mes.1"/></option>
							<option value="03" <% if (mesInicio.equals("03")) {%>selected="true"<%}%>><bean:message key="mes.2"/></option>
							<option value="04" <% if (mesInicio.equals("04")) {%>selected="true"<%}%>><bean:message key="mes.3"/></option>
							<option value="05" <% if (mesInicio.equals("05")) {%>selected="true"<%}%>><bean:message key="mes.4"/></option>
							<option value="06" <% if (mesInicio.equals("06")) {%>selected="true"<%}%>><bean:message key="mes.5"/></option>
							<option value="07" <% if (mesInicio.equals("07")) {%>selected="true"<%}%>><bean:message key="mes.6"/></option>
							<option value="08" <% if (mesInicio.equals("08")) {%>selected="true"<%}%>><bean:message key="mes.7"/></option>
							<option value="09" <% if (mesInicio.equals("09")) {%>selected="true"<%}%>><bean:message key="mes.8"/></option>
							<option value="10" <% if (mesInicio.equals("10")) {%>selected="true"<%}%>><bean:message key="mes.9"/></option>
							<option value="11" <% if (mesInicio.equals("11")) {%>selected="true"<%}%>><bean:message key="mes.10"/></option>
							<option value="12" <% if (mesInicio.equals("12")) {%>selected="true"<%}%>><bean:message key="mes.11"/></option>                         
						</select>
					</strong>
				</td>
				<td width="10%"><a href="javascript:buscar();" class="buscarDesde"><bean:message key="mensual.buscar"/></a> </td>
				<td width="10%">&nbsp;</td>
				<td width="14%">
					<strong><bean:message key="mensual.hasta"/>: 
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
				<td width="19%">
					<strong>
						<select name="mesFinal" class="txt">
							<option value="" <% if (mesFinal.equals("0")) {%>selected<%}%>></option>
							<option value="01" <% if (mesFinal.equals("01")) {%>selected<%}%>><bean:message key="mes.0"/></option>
							<option value="02" <% if (mesFinal.equals("02")) {%>selected<%}%>><bean:message key="mes.1"/></option>
							<option value="03" <% if (mesFinal.equals("03")) {%>selected<%}%>><bean:message key="mes.2"/></option>
							<option value="04" <% if (mesFinal.equals("04")) {%>selected<%}%>><bean:message key="mes.3"/></option>
							<option value="05" <% if (mesFinal.equals("05")) {%>selected<%}%>><bean:message key="mes.4"/></option>
							<option value="06" <% if (mesFinal.equals("06")) {%>selected<%}%>><bean:message key="mes.5"/></option>
							<option value="07" <% if (mesFinal.equals("07")) {%>selected<%}%>><bean:message key="mes.6"/></option>
							<option value="08" <% if (mesFinal.equals("08")) {%>selected<%}%>><bean:message key="mes.7"/></option>
							<option value="09" <% if (mesFinal.equals("09")) {%>selected<%}%>><bean:message key="mes.8"/></option>
							<option value="10" <% if (mesFinal.equals("10")) {%>selected<%}%>><bean:message key="mes.9"/></option>
							<option value="11" <% if (mesFinal.equals("11")) {%>selected<%}%>><bean:message key="mes.10"/></option>
							<option value="12" <% if (mesFinal.equals("12")) {%>selected<%}%>><bean:message key="mes.11"/></option>                         
						</select>
					</strong>
				</td>
				<td width="10%" ><a href="javascript:buscarHasta();" class="buscarHasta"><bean:message key="mensual.hasta.buscar"/></a></td>
				<td>&nbsp;</td>
				</td>
			</tr>
			</table>
		</div>
		<!-- // BUSCADOR DIA -->
		
		<tiles:insert name=".cuadroMandoTemporal" />
		
	</html:form>

</div>