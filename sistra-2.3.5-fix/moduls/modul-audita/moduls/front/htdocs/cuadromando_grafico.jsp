<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript">	
<!--
	function MM_findObj(n, d) { //v4.0
	  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
	    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
	  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
	  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
	  if(!x && document.getElementById) x=document.getElementById(n); return x;
	}
	
	function MM_changeProp(objName,x,theProp,theValue) { //v3.0
	  var obj = MM_findObj(objName);
	  if (obj && (theProp.indexOf("style.")==-1 || obj.style)) eval("obj."+theProp+"='"+theValue+"'");
	}
	
	function redirige(url,obj) {
  		obj.location.replace(url);
	}

		
	function verBandaHoraria() {			  			
		document.location = "cuadromando_grafico.jsp?OPCION=AH&ANYO=2006&MES=08&DIA=09&ELEMENTO=T_TRAMITESINI" ;		
		//frmLOV = open(ls_url,"GraficoHorario", "scrollbars=yes,toolbar=no, location=no, directories=no,status=no,menubar=no,resizable=yes,width=" + ( screen.availWidth - 5) + ",height=" + (screen.availHeight -50) + ",screenX=0,screenY=0,left=0,top=0");
	}	
	
	
	function mostrarBuscador(a_opcion){
				
		MM_changeProp("buscadorAnyo","","style.display","none","DIV");		
		MM_changeProp("buscadorMes","","style.display","none","DIV");
		MM_changeProp("buscadorDia","","style.display","none","DIV");
			
		if (a_opcion == "A"){
			MM_changeProp("buscadorAnyo","","style.display","block","DIV");
		}
		if (a_opcion == "M"){
			MM_changeProp("buscadorMes","","style.display","block","DIV");
		}
		if (a_opcion == "D"){
			MM_changeProp("buscadorDia","","style.display","block","DIV");
		}
	
	}
	
	function buscar(a_opcion){	
		// Fecha		
		if (a_opcion == "A"){
			document.forms[0].desde.value = "01/01/" + document.forms[0].M_ANYO.value;
			document.forms[0].opcion.value = 'anual';			 
		}
		if (a_opcion == "M"){
			document.forms[0].desde.value = "01/" + document.forms[0].M_MES.value + "/" + 
			                                        document.forms[0].M_ANYO.value;
			document.forms[0].opcion.value = 'mensual';			 
		}
		if (a_opcion == "D"){	
			document.forms[0].desde.value = document.forms[0].FECHADIA.value.substring(0,2) + "/" +
			                                document.forms[0].FECHADIA.value.substring(3,5) + "/" + 
			                                document.forms[0].FECHADIA.value.substring(6,10);
			document.forms[0].opcion.value = 'diario';			 
		}
		document.forms[0].submit();
	}
//-->
</script>
</script>	

<%
	String mesInicio = (String)request.getAttribute("mesInicio");
	String anyoInicio = (String)request.getAttribute("anyoInicio");
	String desde = (String)request.getAttribute("desde");
	String opcion = (String)request.getParameter("opcion");
	String evento = (String)request.getAttribute("evento");
%>
<div style="padding:10px; ">
	<h1><bean:message key="grafico.titulo"/></h1>
	<table cellpadding="5" cellspacing="0" align="center" style="background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; width:96%; font-size:1em; ">
	<html:form action="/grafico">
	<tr>
		<td width="30%">
			
					<strong><bean:message key="grafico.buscarEn"/></strong>:
					<br>
					
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
		<td width="15%" nowrap>
			
				<strong><bean:message key="grafico.buscarPor"/></strong>:
				<br>
				<a href="javascript:mostrarBuscador('A');"><bean:message key="grafico.buscarPor.anyo"/></a> | 
				<a href="javascript:mostrarBuscador('M');"><bean:message key="grafico.buscarPor.mes"/></a> | 
				<a href="javascript:mostrarBuscador('D');"><bean:message key="grafico.buscarPor.dia"/></a>
			
		</td>
		<td width="1%"><img src="././images/cuadromando/i_plazo.gif" width="36" height="26" border="0"></td>
		<td>
				<div id="buscadorAnyo" <% if (!opcion.equals("anual")) {%>style="display:none;"<%}%>>
					<strong><bean:message key="grafico.buscadorAnyo.anyo"/>:</strong> 
					<select name="A_ANYO" id="anyoInicio" property="anyoInicio" class="txt">
						<logic:iterate id="anyo" name="anyos">
						   <logic:equal name="anyo" value="<%= anyoInicio %>">
								<option selected="true" value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
						   </logic:equal>
 				           <logic:notEqual name="anyo" value="<%= anyoInicio %>">
								<option  value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
 				           </logic:notEqual>
						</logic:iterate>
     				</select>
					&nbsp;&nbsp;&nbsp;
					<a href="javascript:buscar('A');" class="buscarDesde"><bean:message key="grafico.buscadorAnyo.buscar"/></a>
					
				</div>
				<div id="buscadorMes" <% if (!opcion.equals("mensual")) {%>style="display:none;"<%}%>>
					<strong><bean:message key="grafico.buscadorMes.anyo"/>:</strong> 
					<select name="M_ANYO" id="anyoInicio" property="anyoInicio" class="txt">
						<logic:iterate id="anyo" name="anyos">
						   <logic:equal name="anyo" value="<%= anyoInicio %>">
								<option selected="true" value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
						   </logic:equal>
 				           <logic:notEqual name="anyo" value="<%= anyoInicio %>">
								<option  value="<bean:write name="anyo" />"><bean:write name="anyo" /></option>
 				           </logic:notEqual>
						</logic:iterate>
     				</select>
					<strong><bean:message key="grafico.buscadorMes.mes"/>:</strong> 
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
					&nbsp;&nbsp;&nbsp;
					<a href="javascript:buscar('M');" class="buscarDesde"><bean:message key="grafico.buscadorMes.buscar"/></a>
				</div>
				<div id="buscadorDia" <% if (!opcion.equals("diario")) {%>style="display:none;"<%}%>>
					<strong><bean:message key="grafico.buscadorDia.dia"/>:</strong> 
					<input class="txt" readOnly="true" size="12" maxsize="12" type="text" name="FECHADIA" id="FECHADIA" value="<bean:write name="desde" />" style="color:#0099FF; "><input type="button" value=" ... " class="boton" onclick="setActiveStyleSheet(this, 'summer');showCalendar('FECHADIA', 'dd/mm/y');">					&nbsp;&nbsp;&nbsp;
					<a href="javascript:buscar('D');" class="buscarDesde"><bean:message key="grafico.buscadorDia.buscar"/></a>
				</div>
			
		</td>
		<!--  <td align="left" width="10%" nowrap><img src="./images/cuadromando/i_grafica_hora.gif" alt="" width="18" height="18" style="vertical-align:middle; margin-right:5px; "> <a href="javascript:verBandaHoraria();" class="naranja"><strong>Banda horaria</strong></a></td>-->
	</tr>
	<input type="hidden" name="desde" value="<bean:write name="desde" />">
	<input type="hidden" name="opcion" value="<%= opcion %>">					
	</html:form>
	</table>
		<!-- GRAFICOS -->
	<div style="text-align:center; padding-bottom: 20px ">
  		<img border="0" height="350" width="750" alt="" src="mostrarGrafico.do?desde=<%= desde %>&evento=<%= evento %>&opcion=<%= opcion %>">
	</div> 
	<!-- FIN GRAFICOS -->
	<table cellpadding="1" cellspacing="0" style="width:100%; background-color:#F3F6F9; border:1px solid #E5EEF7; margin-bottom:20px; ">
		<tr>
			<td style="text-align:center">
				<img src="././images/atras.gif" alt="" style="vertical-align:middle; "> 
				<a href="javascript:history.back()"><strong><bean:message key="grafico.atras"/></strong></a>		
			</td>
		</tr>
	</table>
	


</div>