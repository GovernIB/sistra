<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="firstPage" value="0" />
		<!-- informacio -->
		<div id="info">
			<p><bean:message key="expedientes.encabezado.parrafo1.texto" /></p>
			<p><bean:message key="expedientes.encabezado.parrafo2.texto1" /> <strong><bean:write name="page" property="totalResults" /> <bean:message key="expedientes.encabezado.parrafo2.texto2" /></p>
			<div id="TT_icos">
				<strong><bean:message key="tramitesInacabados.iconografia" /></strong>:
				<ul>
					<li><img src="imgs/tramitaciontelematica/ex_novisto.gif" alt="<bean:message key="expedientes.noVisto" />" /><bean:message key="expedientes.noVisto" /></li>
					<!-- <li><img src="imgs/tramitaciontelematica/ex_visto.gif" alt="<bean:message key="expedientes.visto" />" /> <bean:message key="expedientes.visto" /></li> -->
				</ul>
			</div>
			
			<table cellpadding="8" cellspacing="0" id="tabla_ttNotificacions">
			<tr>
				<th><bean:message key="expedientes.tabla.fecha" /></th>
				<th><bean:message key="expedientes.tabla.idExpediente" /></th>
				<th><bean:message key="expedientes.tabla.expediente" /></th>
			</tr>
		<logic:iterate id="expediente" name="page" property="list">	
			<bean:define id="urlDetalleExpediente" type="java.lang.String">
				<html:rewrite page="/protected/detalleExpediente.do" paramId="codigo" paramName="expediente" paramProperty="codigo"/>				
			</bean:define>
			<tr onmouseover="selecItemTabla(this);" onclick="javascript:document.location.href='<%= urlDetalleExpediente %>';" title="Veure expedient">
				<td><logic:empty name="expediente" property="fechaConsulta"><img src="imgs/tramitaciontelematica/ex_novisto.gif" border="0"/>&nbsp;<strong></logic:empty><!--<logic:notEmpty name="expediente" property="fechaConsulta"><img src="imgs/tramitaciontelematica/ex_visto.gif" border="0"/></logic:notEmpty>--><bean:write name="expediente" property="fecha" format="dd/MM/yyyy '-' HH:mm"/><logic:empty name="expediente" property="fechaConsulta"></strong></logic:empty></td>
				<td><logic:empty name="expediente" property="fechaConsulta"><strong></logic:empty><bean:write name="expediente" property="idExpediente" /><logic:empty name="expediente" property="fechaConsulta"></strong></logic:empty></td>
				<td>
					<logic:empty name="expediente" property="fechaConsulta"><strong></logic:empty><bean:write name="expediente" property="descripcion" /><logic:empty name="expediente" property="fechaConsulta"></strong></logic:empty>
				</td>
			</tr>
		</logic:iterate>	
			</table>
			
			<div id="barraNav">
			<logic:equal name="page" property="previousPage" value="true">
			
			&lt;&lt; <html:link action="/protected/expedientes" paramId="pagina" paramName="firstPage"><bean:message key="expedientes.paginacion.inicio" /></html:link> &lt; <html:link action="/protected/expedientes" paramId="pagina" paramName="page" paramProperty="previousPageNumber"><bean:message key="expedientes.paginacion.anterior" /></html:link>
			</logic:equal> 
			- Del <bean:write name="page" property="firstResultNumber" /> al <bean:write name="page" property="lastResultNumber" />, de <bean:write name="page" property="totalResults" /> - 
			<logic:equal name="page" property="nextPage" value="true">			 
			<html:link action="/protected/expedientes" paramId="pagina" paramName="page" paramProperty="nextPageNumber"><bean:message key="expedientes.paginacion.siguiente" /></html:link> &gt; <html:link action="/protected/expedientes" paramId="pagina" paramName="page" paramProperty="lastPageNumber"><bean:message key="expedientes.paginacion.final" /></html:link> &gt;&gt;
			</logic:equal>
			</div>
			
			<p class="parrafFinal"><html:link action="/protected/tramitesTelematicos" styleClass="tornar"><bean:message key="tramitesInacabados.volverPanelTramitacions.textoEnlace" /></html:link></p>
		</div>