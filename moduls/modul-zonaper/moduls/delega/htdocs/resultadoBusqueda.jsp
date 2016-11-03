<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

	<bean:define id="urlModificarEntidad"  type="java.lang.String">
		<html:rewrite page="/modificarEntidad.do"/>
	</bean:define>
	<bean:define id="urlDelegacionesEntidad"  type="java.lang.String">
		<html:rewrite page="/delegacionesEntidad.do"/>
	</bean:define>
	
	<!-- Formulario búsqueda -->
	<tiles:insert name=".formularioBusqueda" />
	
	<!--  Resultados búsqueda -->	
	<div id="resultatsRecerca">
	<h3><bean:message key="resultadoBusqueda.titulo"/></h3>		
	<table cellpadding="8" cellspacing="0" id="tablaResultats">
		<tr>
			<th><bean:message key="resultadoBusqueda.nif"/></th>
			<th><bean:message key="resultadoBusqueda.nombreEntidad"/></th>			
			<th></th>
			<th></th>
		</tr>				
		<logic:present name="entidades">
			<logic:iterate id="entidad" name="entidades" >
				<bean:define id="nifEntidad" name="entidad" property="nif" type="java.lang.String" />
				<tr>
					<td><bean:write name="entidad" property="nif" /></td>
					<td><bean:write name="entidad" property="nombreCompleto"/></td>
					<td>
						<a href='<%=urlModificarEntidad%>?nif=<%=nifEntidad %>' > 
							 <bean:message key="resultadoBusqueda.modificar" />
						</a>
					</td>
					<td>
						<a href='<%=urlDelegacionesEntidad%>?nif=<%=nifEntidad %>' > 
							 <bean:message key="resultadoBusqueda.delegaciones" />
						</a>
					</td>
				</tr>
			</logic:iterate>
		</logic:present>															
	</table> 
</div>