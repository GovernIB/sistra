<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="sesion" name="<%=es.caib.zonaper.front.Constants.DATOS_SESION_KEY%>" type="es.caib.zonaper.model.DatosSesion" />
<bean:define id="firstPage" value="0" />
		<!-- informacio -->		
		<div id="info">
			<!-- continguts -->
		<div id="continguts">
			<!-- titol -->
			<h1>
				<bean:message key="actualizarDatosPersonales.titulo"/>
			</h1>
			<!-- /titol -->
			
			<p><bean:message key="actualizarDatosPersonales.instrucciones"/></p>
		
			<!--  errores -->
			<html:errors />			
			
			<!--  formulario -->
			<html:form action="/protected/actualizarDatosPersonales" styleId="formConfirmacion">
				
				<p>
					<label for="nif">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.nif"/></span>
						<bean:write name="actualizarDatosPersonalesForm" property="nif"/>
					</label>
				</p>
				<p>
					<label for="nom">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.nombre"/></span>
						<html:text property="nombre" maxlength="50" size="20"/>						
					</label>
				</p>
				
				<p>
					<label for="llinatge1">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.apellido1"/></span>
						<html:text property="apellido1" maxlength="50" size="20"/>
					</label>
				</p>
				
				<p>
					<label for="llinatge2">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.apellido2"/></span>
						<html:text property="apellido2" maxlength="50" size="20"/>
					</label>
				</p>
				
				<p>
					<label for="adresa">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.direccion"/></span>
						<html:text property="direccion" maxlength="200" size="50"/>
					</label>
				</p>
				
				<p>
					<label for="cp">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.codigoPostal"/></span>
						<html:text property="codigoPostal" maxlength="5" size="5"/>
					</label>
				</p>
				<p>
					<label for="provincia">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.provincia"/></span>
						<html:select property="provincia" styleId="provincia" onchange="javascript:llenarMunicipios();">
							<logic:iterate id="provincia" name="provincias">	
								<html:option value="<%=((es.caib.zonaper.model.ValorDominio)provincia).getCodigo()%>" ><bean:write name="provincia" property="descripcion"/></html:option>
							</logic:iterate>
						</html:select>
					</label>
				</p>
				<p>
					<label for="localitat">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.municipio"/></span>
						<html:select property="municipio" styleId="municipio">
							<logic:present name="municipios">
								<logic:iterate id="municipio" name="municipios">	
									<html:option value="<%=((es.caib.zonaper.model.ValorDominio)municipio).getCodigo().toString()%>"><bean:write name="municipio" property="descripcion"/></html:option>
								</logic:iterate>
							</logic:present>
						</html:select>
					</label>
				</p>
				
				<p>
					<label for="telefonFixe">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.telefonoFijo"/></span>
						<html:text property="telefonoFijo" maxlength="10" size="10"/>					
					</label>
				</p>
				
				<p>
					<label for="telefonMobil">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.telefonoMovil"/></span>						
						<html:text property="telefonoMovil" maxlength="10" size="10"/>
					</label>
				</p>
				
				<p>
					<label for="email">
						<span class="etiqueta"><bean:message key="actualizarDatosPersonales.email"/></span>
						<html:text property="email" maxlength="50" size="50"/>
					</label>
				</p>
				
				<div class="botonera">
					<button type="submit" tabindex="9"><bean:message key="actualizarDatosPersonales.guardar"/></button>
				</div>
					
			</html:form>
			
			<br/>
			
			<!-- 
			<div id="notaLegalPie"/>
				<p>
				<bean:message key="actualizarDatosPersonales.lopd"/> 
				</p>
			</div>				    
			 -->	
		</div>
		
		<script>
			function llenarMunicipios(){
				codProv = $("#provincia").val();						
				$.ajaxSetup({
				         scriptCharset: "utf-8" ,
				         contentType: "application/json; charset=utf-8"});
				$.getJSON("listarMunicipiosJSON.do", { provincia: codProv }, function(json){
  							fillMunicipios(json);
 				});
			}
			
			function fillMunicipios(locs){
				$("#municipio").removeOption(/./);
				$("#municipio").removeOption(""); 
				$("#municipio").addOption(locs, false); 				
			}
		</script>