<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

 		<!-- Formulario búsqueda -->
		<tiles:insert name=".formularioBusqueda" />
	
		<!--  Resultados búsqueda -->	
		<div id="resultatsRecerca">
		<h3><bean:message key="resultadoBusqueda.titulo"/></h3>		
		<logic:empty name="page">
			<p><bean:message key="resultadoBusqueda.noEncontrados"/></p>
		</logic:empty>
		<logic:notEmpty name="page">
		<table cellpadding="8" cellspacing="0" id="tablaResultats">
		<tr>
			<th class="estado"><bean:message key="resultadoBusqueda.estado"/></th>	
			<th><bean:message key="resultadoBusqueda.numeroEntrada"/></th>
			<th><bean:message key="resultadoBusqueda.fecha"/></th>			
			<th><bean:message key="resultadoBusqueda.numeroRegistro"/></th>
			<th>NIF</th>
			<th><bean:message key="resultadoBusqueda.tramite"/></th>
		</tr>				
			<bean:define id="numeroPagina" name="page" property="page" type="java.lang.Integer"/>
			<logic:iterate id="pagina" name="page" property="list">
			<tr onmouseover="selecItemTabla(this);" onclick="detalleTramite(<bean:write name="pagina" property="codigo"/>);" class="nou" title="<bean:message key="resultadoBusqueda.verDetalleTramite"/>">
				<td class="estado">
					<!--  Icono procesado / no procesado -->
					<logic:equal name="pagina" property="procesada" value='S'>
						<img src="imgs/icones/form_procesado_si.gif" title="<bean:message key="resultadoBusqueda.procesado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="procesada" value='N'>
						<img src="imgs/icones/form_procesado_no.gif" title="<bean:message key="resultadoBusqueda.noProcesado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="procesada" value='X'>
						<img src="imgs/icones/form_procesado_error.gif" title="<bean:message key="resultadoBusqueda.procesadoError"/>"/>
					</logic:equal>
					<!--  Icono envio / registro / preregistro / preenvio -->
					<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>">
						<logic:equal name="pagina" property="firmada" value="S">
							<img src="imgs/icones/envio_firmado.gif" title="<bean:message key="resultadoBusqueda.envio.firmado"/>"/>
						</logic:equal>
						<logic:equal name="pagina" property="firmada" value="N">
							<img src="imgs/icones/envio.gif" title="<bean:message key="resultadoBusqueda.envio"/>"/>
						</logic:equal>
					</logic:equal>
					<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>">
						<logic:equal name="pagina" property="firmada" value="S">
							<img src="imgs/icones/registro_firmado.gif" title="<bean:message key="resultadoBusqueda.registro.firmado"/>"/>
						</logic:equal>
						<logic:equal name="pagina" property="firmada" value="N">
							<img src="imgs/icones/registro.gif" title="<bean:message key="resultadoBusqueda.registro"/>"/>
						</logic:equal>
					</logic:equal>
					<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREENVIO)%>">
						<img src="imgs/icones/preenvio.gif" title="<bean:message key="resultadoBusqueda.preenvioConfirmado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREREGISTRO)%>">
						<img src="imgs/icones/preregistro.gif" title="<bean:message key="resultadoBusqueda.preregistroConfirmado"/>"/>
					</logic:equal>															
					<!--  Icono certificado / usuario / anonimo -->
					<logic:equal name="pagina" property="nivelAutenticacion" value="<%=Character.toString(ConstantesBTE.AUTENTICACION_CERTIFICADO)%>">
						<img src="imgs/icones/certificado.gif" title="<bean:message key="resultadoBusqueda.certificado"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="nivelAutenticacion" value="<%=Character.toString(ConstantesBTE.AUTENTICACION_USUARIOPASSWORD)%>">
						<img src="imgs/icones/usuario.gif" title="<bean:message key="resultadoBusqueda.usuarioPassword"/>"/>
					</logic:equal>
					<logic:equal name="pagina" property="nivelAutenticacion" value="<%=Character.toString(ConstantesBTE.AUTENTICACION_ANONIMO)%>">
						<img src="imgs/icones/anonimo.gif" title="<bean:message key="resultadoBusqueda.anonimo"/>"/>
					</logic:equal>
					
				</td>				
				
				<td><bean:write name="pagina" property="numeroEntrada" /></td>
				<td><bean:write name="pagina" property="fecha" format="dd/MM/yyyy HH:mm"/></td>
				
				<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>">
					<td><bean:write name="pagina" property="numeroRegistro" /></td>
				</logic:equal>
				<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>">
					<td><bean:write name="pagina" property="numeroRegistro" /></td>
				</logic:equal>
				<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREENVIO)%>">
					<td><bean:write name="pagina" property="numeroPreregistro" /></td>
				</logic:equal>
				<logic:equal name="pagina" property="tipo" value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREREGISTRO)%>">
					<td><bean:write name="pagina" property="numeroPreregistro" /></td>
				</logic:equal>			
								
				<td><bean:write name="pagina" property="usuarioNif" /></td>
				<td><bean:write name="pagina" property="descripcionTramite" /></td>
			</tr>
			</logic:iterate>														
		</table> 
		<p id="barraNav">
			&lt;
			<logic:equal name="page" property="previousPage" value="true">
				<a href="javascript:document.forms['busquedaTramitesForm'].pagina.value='<%= String.valueOf ( numeroPagina.intValue() - 1 )%>'; document.forms['busquedaTramitesForm'].submit();" title=""> <bean:message key="resultadoBusqueda.anterior"/></a>
			</logic:equal>
			<logic:equal name="page" property="previousPage" value="false">
			<bean:message key="resultadoBusqueda.anterior"/> &nbsp; 
			</logic:equal>
			|
			<logic:equal name="page" property="nextPage" value="true">
				<a href="javascript:document.forms['busquedaTramitesForm'].pagina.value='<%= String.valueOf ( numeroPagina.intValue() + 1 )%>'; document.forms['busquedaTramitesForm'].submit();" title=""><bean:message key="resultadoBusqueda.proxima"/> </a>
			</logic:equal>
			<logic:equal name="page" property="nextPage" value="false">
			 <bean:message key="resultadoBusqueda.proxima"/> &nbsp; 
			</logic:equal>
			&gt;
		</p>
		</logic:notEmpty>
	</div>