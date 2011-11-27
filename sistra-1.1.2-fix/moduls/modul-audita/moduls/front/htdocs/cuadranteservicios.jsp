<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>

<%
	String mesInicio = (String)request.getAttribute("mesInicio");
	String anyoInicio = (String)request.getAttribute("anyoInicio");
	String desde = (String)request.getAttribute("desde");
	String opcion = (String)request.getParameter("opcion");
	String evento = (String)request.getAttribute("evento");
	String detalle = (String)request.getAttribute("detalle");
	pageContext.setAttribute("opcion", opcion);
	pageContext.setAttribute("detalle", detalle);
	
	int numModelos = 0; 
	int idxOrganismo = 0;
%>
<%
	java.util.HashMap parametros = new java.util.HashMap();
	parametros.put("desde", desde);
	parametros.put("evento", evento);
	parametros.put("opcion", opcion);
  	pageContext.setAttribute("parametros", parametros);
%>


<body onload="mostrarDetalle();">
<div style="padding:10px; " onload="mostrarDetalle();">
	<h1><bean:message key="servicios.nodoCentralizado"/></h1>
	<logic:present name="cuadroMando" >
	<h2 style="margin-bottom:10px; "><img src="./images/ico_organismo.gif" alt="" style="vertical-align:middle; "><bean:write name="cuadroMando" property="titulo"/></h2>
	<table cellpadding="5" cellspacing="0" style="background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; width:100% ">
	<html:form action="/cuadranteServicios">
		<input type="hidden" name="_resultado" value="S">
		<tr>
			
			<td>
				<logic:equal name="opcion" value="mensual" >
				   <a href="javascript:mostrarAnyo();" class="buscarDesde"><bean:message key="servicios.anyo"/>:</a>
				</logic:equal>
				<logic:notEqual name="opcion" value="mensual" >
				   <bean:message key="servicios.anyo"/>:
				</logic:notEqual>
				<select name="A_ANYO" id="anyo" property="anyo" class="txt">
					<logic:iterate id="anyo" name="anyos">
					   <logic:equal name="anyo" value="<%= anyoInicio %>">
							<option selected="true" value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
					   </logic:equal>
				           <logic:notEqual name="anyo" value="<%= anyoInicio %>">
							<option  value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
				           </logic:notEqual>
					</logic:iterate>
    			</select>
			</td>
			<td>
			<logic:equal name="opcion" value="anual" >
			<div id="buscadorMes" style="display:none;">
			</logic:equal>
			<logic:notEqual name="opcion" value="anual" >
			<div id="buscadorMes" >
			</logic:notEqual>
				<strong><bean:message key="servicios.mes"/>:</strong>
					<select name="M_MES" class="txt">
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
			</div>
			</td>
			<td style="width:10%; font-weight:bold; text-align:right; "><bean:message key="servicios.evento"/>:</td>
			<td width="1%">
				<select name="evento">
					<logic:iterate id="tipoEvento" name="eventos">
						<logic:iterate id="mapEvento" name="tipoEvento" length="1">
						   <logic:equal name="mapEvento"  property="key" value="<%= evento %>">
								<option selected="true" value="<bean:write name="mapEvento" property="key" />"><bean:write name="mapEvento"  property="value" /></option>
						   </logic:equal>
 				           <logic:notEqual name="mapEvento"  property="key"  value="<%= evento %>">
								<option value="<bean:write name="mapEvento" property="key" />"><bean:write name="mapEvento"  property="value" /></option>
 				           </logic:notEqual>
						</logic:iterate>
					</logic:iterate>
				</select>
			</td>
			<td width="1%"><input type="button"  onClick="javascript:buscar();" name="Button" value="<bean:message key="servicios.buscar"/>" class="boton"></td>
			<td>&nbsp;</td>
			<td width="1%" nowrap>
				<img src="././images/cuadromando/i_verfichero.gif" alt="" width="16" height="16" style="vertical-align:middle; ">
				<input type="button" name="btnDetalle" value="<bean:message key='servicios.ocultarDetalle'/>" class="botonClaro" onclick="mostrarDetalleClick();">
		  </td>
		</tr>
		<input type="hidden" name="desde" value="<bean:write name="desde" />">
		<input type="hidden" name="opcion" value="<bean:write name="opcion" />">
		<input type="hidden" name="detalle" value="<bean:write name="detalle" />">
	</html:form>
	</table>
	<!-- TABLA DATOS -->
	<table cellspacing="1" cellpadding="2" style="width:100%; border:0; " id="cuadranteServicio">
	<%
		
		int idxKeys = 0;
		int idxModelo = 0;
	%>
		<logic:iterate id="organismo" name="cuadroMando" property="organismos">
	    <% 
	  	 	idxOrganismo++;
	    	String ls_cabkeys = "cabkeys" + idxOrganismo;
	    	String ls_caborganismo = "caborganismo" + idxOrganismo;
	    	String ls_organismo = "organismo" + idxOrganismo;
	    %>
			<tr id="<%= ls_cabkeys %>" style="background-color:#94BCE6; ">
				<logic:equal name="opcion" value="anual" >
				<td style="width:400px" >&nbsp;</td>
				</logic:equal>
				<logic:notEqual name="opcion" value="anual" >
				<td style="width:700px" >&nbsp;</td>
				</logic:notEqual>
				<% idxKeys = 0; %>
				<logic:iterate id="elemento" name="organismo" property="totales" >
					<logic:iterate id="mapTotal" name="elemento" length="1">
					<% idxKeys++; %>
					<logic:equal name="opcion" value="anual" >
					<td style="width:80px" align="center"><a href="javascript:mostrarMes(<%= idxKeys %>);"><bean:write name="mapTotal" property="key" /></a></td>
					</logic:equal>
					<logic:notEqual name="opcion" value="anual" >
					<td style="width:40px" align="center"><bean:write name="mapTotal" property="key" /></td>
					</logic:notEqual>
					</logic:iterate>
				</logic:iterate>
				<td align="center"><bean:message key="servicios.total"/></td>
		 	</tr>
			<tr id="<%= ls_caborganismo %>" style="background-color:#94BCE6; ">
				<logic:equal name="opcion" value="mensual" >
				<td colspan="33" bgcolor="#D3E0EE"><bean:write name="organismo" property="descripcion" /></td>
				</logic:equal>
				<logic:notEqual name="opcion" value="mensual" >
				<td colspan="14" bgcolor="#D3E0EE"><bean:write name="organismo" property="descripcion" /></td>
				</logic:notEqual>
			</tr>
			<logic:iterate id="modelo" name="organismo" property="modelos">
			<% 
				numModelos++;
		   		idxModelo++;
	    			String ls_modelo = "modelo" + numModelos;
			%>
			<tr id="<%= ls_modelo %>" style="background-color:#F3F6F9; ">
				<td><bean:write name="modelo" property="descripcion" /></td>
				<logic:iterate id="elemento" name="modelo" property="totales" >
					<logic:iterate id="mapTotal" name="elemento" length="1">
 						<td align="center">
						<logic:notEqual name="mapTotal" property="value" value="0" >
 						<bean:write name="mapTotal" property="value" />
 						</logic:notEqual>
 						</td>
					</logic:iterate>
				</logic:iterate>
					<td style="background-color:#F3F6F9; text-align:center; font-weight:bold; "><bean:write name="modelo" property="suma" /></td>
				</tr>
			</logic:iterate>
				<tr>
					<td style="background-color:#F3F6F9; ">
						<div id="<%= ls_organismo %>" style="display: none;"><bean:write name="organismo" property="descripcion" /></div>
					</td>
				<logic:iterate id="elemento" name="organismo" property="totales" >
					<logic:iterate id="mapTotal" name="elemento" length="1">
 						<td style="background-color:#F3F6F9; text-align:center; font-weight:bold; ">
						<logic:notEqual name="mapTotal" property="value" value="0" >
 						<bean:write name="mapTotal" property="value" />
 						</logic:notEqual>
 						</td>
					</logic:iterate>
				</logic:iterate>
					<td style="background-color:#E5EEF7; text-align:center; font-weight:bold; "><bean:write name="organismo" property="suma" /></td>
				</tr>
			</logic:iterate>
    			<tr>
	    			<td style="background-color:#336598; color:#FFFFFF; ">&nbsp;</td>
		    		<logic:iterate id="elemento" name="cuadroMando" property="totales" >
			    		<logic:iterate id="mapTotal" name="elemento" length="1">
							<td style="background-color:#336598; text-align:center; font-weight:bold; color:#FFFFFF; ">
							<logic:notEqual name="mapTotal" property="value" value="0" >
							<bean:write name="mapTotal" property="value" />
							</td>
	 						</logic:notEqual>
						</logic:iterate>
					</logic:iterate>
					<td style="background-color:#215080; text-align:center; font-weight:bold; color:#FFFFFF; "><bean:write name="cuadroMando" property="suma" /></td>
				</tr>
	</table>
	</logic:present>
	<% if (idxOrganismo == 0) {%>
	<h2 style="color:red; text-align:center"><bean:message key="servicios.sinDatos"/></h2>
	<% } %>
</div>
</body>

<script type="text/javascript">	
<!--
	
	function buscar(){	
		var a_opcion = document.forms[0].opcion.value;
		// Fecha		
		if (a_opcion == "anual"){
			document.forms[0].desde.value = "01/01/" + document.forms[0].A_ANYO.value;
			document.forms[0].opcion.value = 'anual';			 
		}
		if (a_opcion == "mensual"){
			document.forms[0].desde.value = "01/" + document.forms[0].M_MES.value + "/" + 
			                                        document.forms[0].A_ANYO.value;
			document.forms[0].opcion.value = 'mensual';			 
		}
		document.forms[0].submit();
	}
	
	function mostrarAnyo()
	{
		document.forms[0].opcion.value = 'anual';			 
	   	buscar();
	}
//-->
</script>
<script language="javascript">
<!--		
function mostrarDetalle(){
	if (document.forms[0].detalle.value == "0"){
		for (i=1;i<=<%= numModelos%>;i++){
			eval('document.getElementById("modelo' + i + '").style.display = "none"');
		}
		for (i=1;i<=<%= idxOrganismo%>;i++){
			eval('document.getElementById("organismo' + i + '").style.display = ""');
			eval('document.getElementById("caborganismo' + i + '").style.display = "none"');
			if (i>1) eval('document.getElementById("cabkeys' + i + '").style.display = "none"');
		}
		document.forms[0].btnDetalle.value = "<bean:message key="servicios.mostrarDetalle"/>";
	}else{
		for (i=1;i<=<%= numModelos%>;i++){
			eval('document.getElementById("modelo' + i + '").style.display = ""');
		}
		for (i=1;i<=<%= idxOrganismo%>;i++){
			eval('document.getElementById("organismo' + i + '").style.display = "none"');
			eval('document.getElementById("caborganismo' + i + '").style.display = ""');
			if (i>1) eval('document.getElementById("cabkeys' + i + '").style.display = ""');
		}
		document.forms[0].btnDetalle.value = "<bean:message key="servicios.ocultarDetalle"/>";
	}
}

function mostrarDetalleClick(){
	// Cambiamos estado detalle
	if (document.forms[0].detalle.value == "1" )
		document.forms[0].detalle.value = "0";
	else
		document.forms[0].detalle.value = "1";
	// Cambiamos detalle
	mostrarDetalle();
}

function redirige(url,obj) {
	obj.location = url;
}

function mostrarMes(ai_mes){

	var mes;
	if(ai_mes < 10)
	{
	   mes = "0";
	}
	mes = mes + ai_mes;
	document.forms[0].desde.value = "01/" + mes + "/" + document.forms[0].A_ANYO.value;
	document.forms[0].opcion.value = 'mensual';	
	document.forms[0].submit();	 

	
}
//-->
</script>



