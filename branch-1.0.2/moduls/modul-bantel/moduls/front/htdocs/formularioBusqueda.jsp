<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.bantel.modelInterfaz.ConstantesBTE"%>
<%@ page import="es.caib.xml.registro.factoria.ConstantesAsientoXML" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/formularioBusqueda.js"></script>

		<h2><bean:message key="formularioBusqueda.busquedaTramites"/></h2>
		
		<logic:empty name="tramites">
			<bean:message key="errors.noGestor"/>
		</logic:empty>
		<logic:notEmpty name="tramites">				
		

		 
			<html:form action="busquedaTramites" styleId="busquedaTramitesForm" styleClass="centrat">
				<html:hidden property="pagina" />				
					<p>
					<bean:message key="formularioBusqueda.año"/> 
					<html:select property="anyo">
						<logic:iterate id="tmpAnyo" name="anyos">
									<html:option value="<%= tmpAnyo.toString() %>" />
						</logic:iterate>			
					</html:select> 
					<bean:message key="formularioBusqueda.mes"/>
					<html:select property="mes">
						<logic:iterate id="tmpMes" name="meses">
									<html:option value="<%= tmpMes.toString() %>"><bean:message key='<%= "mes." + tmpMes %>' /></html:option>
						</logic:iterate>			
					</html:select> 					
					<bean:message key="formularioBusqueda.tipo"/>
					<html:select property="tipo">
						<html:option value="T" ><bean:message key="formularioBusqueda.tipo.todos"/></html:option>
						<html:option value="<%=Character.toString(ConstantesAsientoXML.TIPO_REGISTRO_ENTRADA)%>" ><bean:message key="formularioBusqueda.tipo.registro"/></html:option>
						<html:option value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREREGISTRO)%>" ><bean:message key="formularioBusqueda.tipo.preregistro"/></html:option>
						<html:option value="<%=Character.toString(ConstantesAsientoXML.TIPO_ENVIO)%>" ><bean:message key="formularioBusqueda.tipo.envio"/></html:option>
						<html:option value="<%=Character.toString(ConstantesAsientoXML.TIPO_PREENVIO)%>" ><bean:message key="formularioBusqueda.tipo.preenvio"/></html:option>					
					</html:select>
					<bean:message key="formularioBusqueda.nivelAutencicacion"/>
					<html:select property="nivelAutenticacion">
						<html:option value="T" ><bean:message key="formularioBusqueda.todos"/></html:option>
						<html:option value="<%=Character.toString(ConstantesBTE.AUTENTICACION_CERTIFICADO)%>" ><bean:message key="formularioBusqueda.nivel.certificado"/></html:option>
						<html:option value="<%=Character.toString(ConstantesBTE.AUTENTICACION_USUARIOPASSWORD)%>" ><bean:message key="formularioBusqueda.nivel.usuario"/></html:option>
						<html:option value="<%=Character.toString(ConstantesBTE.AUTENTICACION_ANONIMO)%>" ><bean:message key="formularioBusqueda.nivel.anonimo"/></html:option>
					</html:select>
					<bean:message key="formularioBusqueda.procesada"/>
					<html:select property="procesada">
						<html:option value="T" ><bean:message key="formularioBusqueda.procesada.todas"/></html:option>
						<html:option value="<%=ConstantesBTE.ENTRADA_NO_PROCESADA%>" ><bean:message key="formularioBusqueda.procesada.noprocesadas"/></html:option>
						<html:option value="<%=ConstantesBTE.ENTRADA_PROCESADA%>" ><bean:message key="formularioBusqueda.procesada.correctas"/></html:option>
						<html:option value="<%=ConstantesBTE.ENTRADA_PROCESADA_ERROR%>" ><bean:message key="formularioBusqueda.procesada.error"/></html:option>
					</html:select>				
			<!-- 
					&nbsp;&nbsp;&nbsp;&nbsp;<span><a href="javascript:void(0);" onclick="obrirRecercaAv(this);">[+]</a></span>
			 -->
					</p>					
					<div id="recercaAv">
						<p><bean:message key="formularioBusqueda.tramite"/>
							<html:select property="identificadorTramite">
								<html:option value="-1" ><bean:message key="formularioBusqueda.tramite.todos"/></html:option>
								<logic:iterate id="tramite" name="tramites" type="es.caib.bantel.model.Tramite">															
									<html:option value="<%=tramite.getIdentificador()%>">
										<%=tramite.getIdentificador() + "-" + (tramite.getDescripcion().length()>60?tramite.getDescripcion().substring(0,60)+"...":tramite.getDescripcion())%>
									</html:option>
								</logic:iterate>
							</html:select>
							NIF <html:text property="usuarioNif" size="9" /> 
							<bean:message key="formularioBusqueda.nom"/> <html:text property="usuarioNombre" size="15"/>
						</p>
					</div>
				<bean:define id="botonEnviar" type="java.lang.String">
	                  <bean:message key="boton.enviarBusqueda" />
                 </bean:define>
				<p class="centrado">
					<input name="imprimirSolicitudBoton" id="imprimirSolicitudBoton" type="button" value="<%=botonEnviar%>" onclick="javascript:validaFormulario( this.form );"/>
					<logic:equal name="permitirCambioEstadoMasivo" value="S">
						<html:button property="cancelar" onclick="javascript:mostrarCambioEstadoMasivo()" >
							<bean:message key="resultadoBusqueda.cambioEstadoMasivo.enlace"/>
						</html:button>
					</logic:equal>
					<input name="export" id="export" type="button" value="<bean:message key="exportCSV.exportarTramites"/>" onclick="javascript:document.location='exportCSVTramitesAction.do';"/>
				</p>
				<div class="separacio"></div>			
			</html:form>	
		</logic:notEmpty>				
		
		
		<!--  div para cambio de estado -->
		<logic:equal name="permitirCambioEstadoMasivo" value="S">
		<script>
			var mensajeConfirmacion = "<bean:message key="resultadoBusqueda.cambioEstadoMasivo.confirmacion"/>";
			var mensajeErrorTodosTramites = "<bean:message key="resultadoBusqueda.cambioEstadoMasivo.errorTodosTramites"/>";
		</script>
		<div id="cambioEstadoMasivo" class="cambioEstadoMasivo">						
				<html:form styleId="cambioEstadoMasivoForm" action="cambioEstadoMasivo">
					<!--  Propiedades a copiar del formulario de busqueda -->
					<html:hidden property="anyo" />	
					<html:hidden property="mes" />	
					<html:hidden property="usuarioNif" />	
					<html:hidden property="usuarioNombre" />	
					<html:hidden property="tipo" />	
					<html:hidden property="procesada" />	
					<html:hidden property="nivelAutenticacion" />	
					<html:hidden property="identificadorTramite" />	
					
					<p>
						<bean:message key="resultadoBusqueda.cambioEstadoMasivo.info"/>
					</p>
					<p>
						<bean:message key="resultadoBusqueda.cambioEstadoMasivo.nuevoEstado"/>									
						<html:select property="estadoNuevo">
							<html:option value="<%=ConstantesBTE.ENTRADA_NO_PROCESADA%>" ><bean:message key="detalleTramite.marcarNoProcesado"/></html:option>
							<html:option value="<%=ConstantesBTE.ENTRADA_PROCESADA%>" ><bean:message key="detalleTramite.marcarProcesado"/></html:option>
						</html:select>	
					
						<html:button property="cancelar" onclick="cambioEstadoMasivo(mensajeConfirmacion,mensajeErrorTodosTramites)"  >
							<bean:message key="resultadoBusqueda.cambioEstadoMasivo.botonCambiar"/>
						</html:button>
						<html:button property="cancelar" onclick="ocultarCambioEstadoMasivo()" >
							<bean:message key="resultadoBusqueda.cambioEstadoMasivo.botonCancelar"/>
						</html:button>
					</p>					
				</html:form>
		</div>	
		</logic:equal>