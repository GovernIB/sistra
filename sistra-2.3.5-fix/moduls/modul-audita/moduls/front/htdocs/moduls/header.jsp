<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<!-- marco superior -->
<div id="marcosup">
	<div id="capsalH"></div>	

	<div id="tituloPagina">
		<span><bean:message key="header.titulo"/></span>
	</div>
	<table cellpadding="0" cellspacing="0" style="width:100%; border:0; ">
	<tr>
		<td style="width:10px; height:4px; "></td>
		<td <logic:equal name="opcion" value="inicio">class="pestanyaSombra"</logic:equal>></td>
		<td style="width:10px;"></td>
		<td <logic:equal name="opcion" value="anual">class="pestanyaSombra"</logic:equal>></td>
		<td style="width:10px;"></td>
		<td <logic:equal name="opcion" value="mensual">class="pestanyaSombra"</logic:equal>></td>
		<td style="width:10px;"></td>
		<td <logic:equal name="opcion" value="diario">class="pestanyaSombra"</logic:equal>></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td valign="bottom" id="<logic:equal name="opcion" value="inicio">pesInicioOn</logic:equal><logic:notEqual name="opcion" value="inicio">pesInicioOff</logic:notEqual>"><img src="./images/nada.gif" alt=""><html:link action="/inicio"><bean:message key="header.tabs.inicio"/></html:link></td>
		<td></td>
		<td valign="bottom" id="<logic:notEqual name="opcion" value="anual">pesAnyoOff</logic:notEqual><logic:equal name="opcion" value="anual">pesAnyoOn</logic:equal>"><img src="./images/nada.gif" alt=""><html:link action="/anual"><bean:message key="header.tabs.anyo"/></html:link></td>
		<td></td>
		<td valign="bottom" id="<logic:notEqual name="opcion" value="mensual">pesMesOff</logic:notEqual><logic:equal name="opcion" value="mensual">pesMesOn</logic:equal>"><img src="./images/nada.gif" alt=""><html:link action="/mensual"><bean:message key="header.tabs.mes"/></html:link></td>
		<td></td>
		<td valign="bottom" id="<logic:notEqual name="opcion" value="diario">pesDiaOff</logic:notEqual><logic:equal name="opcion" value="diario">pesDiaOn</logic:equal>"><img src="./images/nada.gif" alt=""><html:link action="/diario"><bean:message key="header.tabs.dia"/></html:link></td>
		<td>&nbsp;</td>
	</tr>
	</table>
</div>