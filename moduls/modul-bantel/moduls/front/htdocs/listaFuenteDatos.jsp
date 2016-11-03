<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/listaFuenteDatos.js"></script>

<h2><bean:message key="listaFuenteDatos.titulo"/></h2>
<br/>
<!--  Resultados búsqueda -->	
<div id="resultatsRecerca">
	<logic:empty name="page" property="list">
		<p><bean:message key="listaFuenteDatos.noEncontrados"/></p>
	</logic:empty>
	<logic:notEmpty name="page" property="list">
	<table cellpadding="8" cellspacing="10" id="tablaResultats">
	<tr>
		<th width="20%"><bean:message key="listaFuenteDatos.procedimiento.identificador"/></th>
		<th width="20%"><bean:message key="listaFuenteDatos.fuenteDatos.identificador"/></th>			
		<th width="60%"><bean:message key="listaFuenteDatos.fuenteDatos.descripcion"/></th>
	</tr>				
		<bean:define id="numeroPagina" name="page" property="page" type="java.lang.Integer"/>
		<logic:iterate id="pagina" name="page" property="list">
		<bean:define id="idFuenteDatos" name="pagina" property="identificador" type="java.lang.String"/>
		<tr onmouseover="selecItemTabla(this);" onclick="detalleFuenteDatos('<%=StringEscapeUtils.escapeJavaScript(idFuenteDatos)%>');" class="nou" title="<bean:message key="listaFuenteDatos.verDetalleFuenteDatos"/>">
			<td><bean:write name="pagina" property="procedimiento.identificador"/></td>
			<td><bean:write name="pagina" property="identificador" /></td>
			<td><bean:write name="pagina" property="descripcion" /></td>						
		</tr>
		</logic:iterate>														
	</table> 
	<p id="barraNav">
		&lt;
		<logic:equal name="page" property="previousPage" value="true">
			<a href="<html:rewrite page="/listaFuenteDatos.do"/>?pagina=<%= String.valueOf ( numeroPagina.intValue() - 1 )%>" title=""> <bean:message key="listaFuenteDatos.anterior"/></a>
		</logic:equal>
		<logic:equal name="page" property="previousPage" value="false">
		<bean:message key="listaFuenteDatos.anterior"/> &nbsp; 
		</logic:equal>
		|
		<logic:equal name="page" property="nextPage" value="true">
			<a href="<html:rewrite page="/listaFuenteDatos.do"/>?pagina=<%= String.valueOf ( numeroPagina.intValue() + 1 )%>" title=""><bean:message key="listaFuenteDatos.proxima"/> </a>
		</logic:equal>
		<logic:equal name="page" property="nextPage" value="false">
		 <bean:message key="listaFuenteDatos.proxima"/> &nbsp; 
		</logic:equal>
		&gt;
	</p>
	</logic:notEmpty>
</div>