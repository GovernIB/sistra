<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<script type="text/javascript">
<!--

function refreshDatosDesde()
{
	document.forms[0].anyoInicio.value = document.forms[0].FECHADIA.value.substring(6,10);
	document.forms[0].mesInicio.value = document.forms[0].FECHADIA.value.substring(3,5);	
	document.forms[0].diaInicio.value = document.forms[0].FECHADIA.value.substring(0,2);			 
}

function refreshDatosHasta()
{
	document.forms[0].anyoFinal.value = document.forms[0].FECHADIAH.value.substring(6,10);
	document.forms[0].mesFinal.value = document.forms[0].FECHADIAH.value.substring(3,5);	
	document.forms[0].diaFinal.value = document.forms[0].FECHADIAH.value.substring(0,2);			 
}

function resetHasta()
{
	document.forms[0].anyoFinal.value = "";
	document.forms[0].mesFinal.value = "";	
	document.forms[0].diaFinal.value = "";			 
}

function buscar()
{
    resetHasta();
    refreshDatosDesde();
	document.forms[0].submit();
}
function buscarHasta()
{
	if (document.forms[0].FECHADIAH.value == "" ) 
	{
	   alert("Debe indicar la fecha Hasta");
	   return;
	} 			
    if (!compruebaPeriodoFechas(document.forms[0].FECHADIA.value,document.forms[0].FECHADIAH.value)) 
    {
       alert("La fecha Hasta debe ser superior a la fecha Desde");
       return;
    } 			

    refreshDatosDesde();
    refreshDatosHasta();
	document.forms[0].submit();
}
//-->
</script>

<div style="padding:10px; ">
    <html:form  action="/diario">
		<input type="hidden" name="HASTA">  
		<div style="background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; ">
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr>
				<td width="1%">&nbsp;</td>
				<td width="5%"><img src="././images/cuadromando/i_plazo.gif" width="36" height="26" border="0"></a></td>
				<td width="5%"><strong>D&iacute;a:</strong> </td>
				<td width="15%" nowrap>
					<input class="txt" readOnly="true" size="12" maxsize="12" type="text" name="FECHADIA" id="FECHADIA" value="<bean:write name="desde" />" style="color:#0099FF; "><input type="button" value=" ... " class="boton" onclick="setActiveStyleSheet(this, 'summer');showCalendar('FECHADIA', 'dd/mm/y');">											 
					<input type="hidden" name="diaInicio" value="0">
					<input type="hidden" name="mesInicio" value="0">
					<input type="hidden" name="anyoInicio" value="0">					
				</td>
				<td width="2%">&nbsp;</td>
				<td width="5%"><a href="javascript:buscar();" class="buscarDesde"><bean:message key="diario.buscar"/></a></td>
				<td width="20%"></td>
				<td width="6%"><strong><bean:message key="diario.hasta"/>:</strong> </td>
				<td width="10%" nowrap>
				    <input type="hidden" name="diaFinal" value="0">
					<input type="hidden" name="mesFinal" value="0">
					<input type="hidden" name="anyoFinal" value="0">					
					<input class="txt" readOnly="true" size="12" maxsize="12" type="text" name="FECHADIAH" id="FECHADIAH" value = "<bean:write name="hasta" />" style="color:#0099FF; "><input type="button" value=" ... " class="boton" onclick="setActiveStyleSheet(this, 'summer'); showCalendar('FECHADIAH', 'dd/mm/y');">
			   </td>
			   <td width="2%">&nbsp;</td>
			   <td><a href="javascript:buscarHasta();" class="buscarHasta"><bean:message key="diario.hasta.buscar"/></a></td>
			</tr>
			</table>
		</div>
		
		<tiles:insert name=".cuadroMandoTemporal" />
	</html:form>
</div>