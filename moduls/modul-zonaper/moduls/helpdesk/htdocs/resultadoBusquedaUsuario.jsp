<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		
		<!-- formulario busqueda -->
		<tiles:insert name=".formularioBusquedaUsuario" />
		
		<!--/continguts -->
		<div class="continguts">
		
			<!-- resultats -->
			<h2><bean:message key="resultadoBusqueda.titulo"/></h2>
			<logic:empty name="persona">
				<bean:message key="errors.noExisteUsuario"/>
			</logic:empty>
		
			<logic:notEmpty name="persona">
				
					<!-- Datos actuales usuario -->
					<div id="justificant">
						<table class="pagament" cellpadding="0" cellspacing="0">
							<caption><bean:message key="usuarios.datosUsuario"/></caption>
							<tbody>
								<tr>
									<th><bean:message key="usuarios.codigo"/></th>
									<td><bean:write name="persona" property="usuarioSeycon"/></td>
								</tr>
								<tr>
									<th><bean:message key="usuarios.nif"/></th>
									<td><bean:write name="persona" property="nif"/></td>
								</tr>
								<tr>
									<th><bean:message key="usuarios.nombre"/></th>
									<td><bean:write name="persona" property="nombreCompleto"/></td>
								</tr>
								
							</tbody>
						</table>
			
				<br/>								
				
				<!--  Modificar usuario -->
				<html:form action="modificarUsuario" styleClass="formulari">					
					<p>					
						<html:hidden property="usuarioCodiOld"/>					
						<label for="usuarioCodiNew" >
							<bean:message key="usuarios.codigoNuevo"/>: 
							<html:text property="usuarioCodiNew" styleId="usuarioCodiNew" size="20"  />
						</label>
						<html:submit>						
							<bean:message key="usuarios.modificar"/>
						</html:submit>
					</p>
				</html:form>
				
				<!--  Modificar NIF usuario -->
				<html:form action="modificarNifUsuario" styleClass="formulari">					
					<p>					
						<html:hidden property="usuarioCodiOld"/>					
						<label for="usuarioCodiNew" >
							<bean:message key="usuarios.nif"/>: 
							<html:text property="usuarioNifNew" styleId="usuarioCodiNew" size="20"  />
						</label>
						<html:submit>						
							<bean:message key="usuarios.modificarNif"/>
						</html:submit>
					</p>
				</html:form>
				
				
			</logic:notEmpty>
			
			
			
	
		</div>
	<!-- /continguts -->
