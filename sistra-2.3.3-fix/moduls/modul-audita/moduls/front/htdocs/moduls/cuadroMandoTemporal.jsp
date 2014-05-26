<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals,java.lang.*"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<%
	java.util.HashMap parametros = new java.util.HashMap();
	parametros.put("desde", request.getAttribute("desde"));
	parametros.put("hasta", request.getAttribute("hasta"));
	parametros.put("opcion", request.getAttribute("opcion"));
    pageContext.setAttribute("parametros", parametros);
%>

		<!-- ESTADÍSTICAS Y NÚMEROS VARIOS QUE NO SON DEL RESUMEN -->
<logic:present name="cuadroMando">
	<logic:iterate id="modulo" name="cuadroMando">
	<%
	   int idx = 1;
	   int impar = 0;
	%>
		<table cellpadding="2" cellspacing="0" style="width:100%; border:0; margin-bottom:20px; ">
		<logic:iterate id="evento" name="modulo" property="eventosAuditados" indexId="index">
		<% 
		   impar = idx % 2;
		   if(idx == 1)
		   {
		%>
			<tr style="background-color:#F3F7FA;color:black; ">
			<td width="10%" class="tituloDetalle"><bean:write name="modulo" property="descripcion" /></td>  
		<% } else if(impar == 0) {%>
			<tr style="background-color:#ffffff;">
			<td>&nbsp;</td>
		<% } else {%>
			<tr style="background-color:#F3F7FA;color:black; ">
			<td style="background-color:#ffffff;">&nbsp;</td>
		<% }%>
			<td width="1%" class="tituloDetalle">&nbsp;</td>
			<td width="20%" style="cursor:help; " onmouseover='mostrarMensaje("<bean:write name="evento" property="ayuda"/>");' onmouseout='esconderMensaje()'>&nbsp;&nbsp;<bean:write name="evento" property="descripcion"/></td>
			<td width="5%" style="font-weight:bold; text-align:right; "><bean:write name="evento" property="total"/></td>
			
			<td width="10%" >
			<logic:present name="evento" property="opcionesVisualizacion">
			<logic:match name="evento" property="opcionesVisualizacion" value="I">
			   <logic:iterate name="evento" property="totalesIdioma" id="element">
			    <logic:empty name="element" property="key">
			    	<img src="./images/cuadromando/bandera_null.gif" title="<bean:message key="icono.idioma.null"/>" style="vertical-align:middle; ">
			    </logic:empty>
			   	<logic:notEmpty name="element" property="key">
			   	  <logic:match name="element" property="key" value="es">
			         <img src="./images/cuadromando/bandera_es.gif" title="<bean:message key="icono.idioma.castellano"/>" style="vertical-align:middle; ">
			      </logic:match>
			      <logic:match name="element" property="key" value="ca">
			         <img src="./images/cuadromando/bandera_ca.gif" title="<bean:message key="icono.idioma.catalan"/>" style="vertical-align:middle; ">
			      </logic:match>
			      <logic:match name="element" property="key" value="en">
			         <img src="./images/cuadromando/bandera_en.gif" title="<bean:message key="icono.idioma.ingles"/>" style="vertical-align:middle; ">
			      </logic:match>
			      <logic:match name="element" property="key" value="de">
			         <img src="./images/cuadromando/bandera_de.gif" title="<bean:message key="icono.idioma.aleman"/>" style="vertical-align:middle; ">
			      </logic:match>
			     </logic:notEmpty>
			     <bean:write name="element" property="value"/>
			   </logic:iterate>
			</logic:match>
			<logic:notMatch name="evento" property="opcionesVisualizacion" value="I">&nbsp;</logic:notMatch>&nbsp;
			</logic:present>				   
			</td>
			<td width="10%">
			<logic:present name="evento" property="opcionesVisualizacion">
			<logic:match name="evento" property="opcionesVisualizacion" value="N">
			   <logic:iterate name="evento" property="totalesNivelAutenticacion" id="element">
			      <logic:match name="element" property="key" value="A">
			         <img src="./images/cuadromando/i_nivel_a.gif" title="<bean:message key="icono.nivel.anonimo"/>" style="vertical-align:middle; ">
			      </logic:match>
			      <logic:match name="element" property="key" value="U">
			         <img src="./images/cuadromando/i_nivel_u.gif" title="<bean:message key="icono.nivel.usuario"/>" style="vertical-align:middle; ">
			      </logic:match>
			      <logic:match name="element" property="key" value="C">
			         <img src="./images/cuadromando/i_nivel_c.gif" title="<bean:message key="icono.nivel.certificado"/>" style="vertical-align:middle; ">
			      </logic:match>
			      <bean:write name="element" property="value"/>
			   </logic:iterate>
			</logic:match>
			<logic:notMatch name="evento" property="opcionesVisualizacion" value="I">&nbsp;</logic:notMatch>&nbsp;
			</logic:present>				   
			</td>
			<td width="10%" id="visualizacionParticular">
			<logic:present name="evento" property="opcionesVisualizacion">
         		<logic:match name="evento" property="opcionesVisualizacion" value="X">
		    		<bean:write name="evento" property="codigoVisualizacionParticular" filter="false"/>
			</logic:match>
			</logic:present>				   
			</td>
			<td width="3%">
			<logic:present name="evento" property="opcionesVisualizacion">
         		<logic:match name="evento" property="opcionesVisualizacion" value="D">
					<logic:equal name="evento" property="total" value="0">
			   			<img src="./images/cuadromando/i_documini_off.gif" alt="<bean:message key="icono.verDetalle"/>" border="0">
					</logic:equal>
					<logic:notEqual name="evento" property="total" value="0">
						<html:link action="/detalle" name="parametros" paramId="evento" paramName="evento" paramProperty="tipo"><img src="./images/cuadromando/i_documini.gif" alt="Detalle por Servicio" border="0"></html:link>
					</logic:notEqual>
				</logic:match>
			</logic:present>				   
			</td>
			<td width="3%">
			<logic:present name="evento" property="opcionesVisualizacion">
				<logic:notEqual name="hasta"  value="">
					<img src="./images/cuadromando/i_grafica_off.gif" alt="<bean:message key="icono.verGrafico"/>" border="0">
				</logic:notEqual>
				<logic:equal name="hasta"  value="">
					<logic:match name="evento" property="opcionesVisualizacion" value="G">
						<logic:equal name="evento" property="total" value="0">
							<img src="./images/cuadromando/i_grafica_off.gif" alt="Ver gr&aacute;fico" border="0">
						</logic:equal>
						<logic:notEqual name="evento" property="total" value="0">
							<html:link action="/grafico" name="parametros" paramId="evento" paramName="evento" paramProperty="tipo"><img src="./images/cuadromando/i_grafica.gif" alt="Ver gr&aacute;fico" border="0"></html:link>
						</logic:notEqual>
					</logic:match>
				</logic:equal>
			</logic:present>				   
			</td>
			<td width="1%">&nbsp;</td>
			</tr>
		<% idx++; %>
		</logic:iterate>
		</table>
	</logic:iterate>
</logic:present>				   
