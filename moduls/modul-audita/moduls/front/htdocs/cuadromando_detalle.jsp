<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<%
	String desde = (String)request.getAttribute("desde");
	String opcion = (String)request.getParameter("opcion");
	String evento = (String)request.getAttribute("evento");
%>
<%
	java.util.HashMap parametros = new java.util.HashMap();
	parametros.put("desde", desde);
	parametros.put("evento", evento);
	parametros.put("opcion", opcion);
    pageContext.setAttribute("parametros", parametros);
%>
<div style="padding:10px; ">
	<h1><bean:message key="detalle.titulo"/></h1>
	<logic:present name="cuadroMandoDetalle">
		<table cellpadding="5" cellspacing="0" style="width:100%; background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; ">
			<tr>
				<td width="40%"><h2><bean:write name="cuadroMandoDetalle" property="titulo" /></h2></td>
				<td width="1%"><img src="././images/cuadromando/i_plazo.gif" alt="" width="36" height="26"></td>
				<td><strong><bean:write name="cuadroMandoDetalle" property="temporal" /></strong></td>
				<td width="1%" nowrap>
				    <% if(!opcion.equals("diario"))  { %>
					<img src="././images/cuadromando/i_resumen.gif" alt="" width="36" height="26" style="vertical-align:middle; "> 
					<html:link action="/cuadranteServicios" name="parametros" styleClass="naranja"><strong><bean:message key="detalle.tablaCruzada"/></strong></html:link>
					<% } %>
				</td>
			</tr>
		</table>
	<logic:present name="cuadroMandoDetalle" property="detalleOrganismos">
		<table cellpadding="5" cellspacing="0" style="width:100%; border:0; ">
		<logic:iterate id="organismo" name="cuadroMandoDetalle" property="detalleOrganismos">
			<tr style="background-color:#94BCE6; color:#FFFFFF; ">
				<td width="2%"></td>
				<td width="65%"><strong><bean:write name="organismo" property="titulo"/></strong></td>
				<td width="6%" style="background-color:#336598; text-align:center; font-weight:bold; "><bean:write name="organismo" property="total"/></td>
				<td width="1%">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>	
			<%
			   int idx = 0;
			   int impar = 0;
			%>
 			<logic:iterate id="linea" name="organismo" property="lineasDetalle">
			<% 
			   impar = idx % 2;
				if(impar == 0) {%>
				<tr>
			<% } else {%>
				<tr style="background-color:#F3F6F9; ">
			<% }%>
					<td></td>  
					<td><strong><bean:write name="linea" property="titulo"/></strong></td>
					<td style="background-color:#F3F6F9; text-align:center; font-weight:bold; "><bean:write name="linea" property="total"/></TD>
					<td>&nbsp;</td>
					<td align="center"><bean:write name="linea" property="visualizacion" filter="false"/>
					</td>
				</tr>
			<% idx++; %>
			</logic:iterate>
		</logic:iterate>
			<tr style="background-color:#FFE1C3; ">
				<td></td>
				<td style="text-align:right; font-weight:bold; ">TOTAL</td>
				<td style="text-align:center; font-weight:bold; background-color:#FFCC99; "><bean:write name="cuadroMandoDetalle" property="total"/></td>
				<td></td>	
				<td></td>	
			</tr>
		</table>
	</logic:present>
</logic:present>
	<table cellpadding="1" cellspacing="0" style="width:100%; background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; ">
		<tr>
			<td style="text-align:center">
				<img src="././images/atras.gif" alt="" style="vertical-align:middle; "> 
				<a href="javascript:history.back()"><strong><bean:message key="detalle.atras"/></strong></a>		
			</td>
		</tr>
	</table>
		
</div>