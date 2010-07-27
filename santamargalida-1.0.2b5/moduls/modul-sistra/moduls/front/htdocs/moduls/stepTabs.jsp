<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.sistra.model.PasoTramitacion"%>
<%@ page import="java.util.List,java.util.Map"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
	<!-- pestañas -->
	<logic:notEmpty name="tramite">
		<bean:define id="urlIrAPaso">
		        <html:rewrite page="/protected/irAPaso.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
		</bean:define>
		<bean:define id="urlRemitirFlujo">
		        <html:rewrite page="/protected/remitirFlujo.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
		</bean:define>
		<bean:define id="estadoTramite" name="tramite" type="es.caib.sistra.model.TramiteFront"/>
		<bean:define id="pasoActual" name="tramite" property="pasoActual" type="java.lang.Integer"/>
		<bean:define id="indicePasoNoRetorno" name="tramite" property="pasoNoRetorno" type="java.lang.Integer"/>
		<bean:define id="navegacion" name="navegacion" type="java.util.Map"/>
		
		<ul id="pesPasos">
		<% 
			// indice paso actual
			int iPasoActual = pasoActual.intValue();
			// indice iteracion
			int nPaso = 0; 
			// nº de pasos
			int nPasos = estadoTramite.getPasos().size(); 			
		%>
		<logic:iterate id="paso" name="tramite" property="pasos" type="es.caib.sistra.model.PasoTramitacion">
		<% 
			
	    	if ( nPaso != 0 )
			{
		%>
			<bean:define id="tipoPaso" name="paso" property="tipoPaso" type="java.lang.Integer"/>
			<bean:define id="strPaso" value="<%= String.valueOf( nPaso) %>"/>
			<bean:define id="descripcion">
				<bean:message key='<%= "pasos." + tipoPaso %>'/>
			</bean:define>
			<logic:equal name="pasoActual" value="<%= strPaso %>">
				<li class="<%= nPasos != nPaso + 1? "seleccionado" : "ultimo seleccionado" %>"><bean:message key="pasoPasos.paso"/> <%= String.valueOf( nPaso ) %><br /><bean:message key="<%= paso.getClaveTab() %>"/></li>
			</logic:equal>
			<logic:notEqual name="pasoActual" value="<%= strPaso %>">
				
				<%
				String tabClass = nPasos==nPaso + 1 ? " class='ultimo'" : "";
				boolean link =  ((Boolean) ((List) navegacion.get("anterior")).get(nPaso)).booleanValue() || ((Boolean) ((List) navegacion.get("siguiente")).get(nPaso)).booleanValue();
				
				if (link){
				%>
					<li<%=  tabClass %>>
						<html:link href="<%= urlIrAPaso %>" paramId="step" paramName="strPaso">
							<bean:message key="pasoPasos.paso"/> <%= strPaso %><br />
							<bean:message key="<%= paso.getClaveTab() %>"/>							
						</html:link>
					</li>
				<%
				}
				else
				{ 
					// Si se ha enviado mostramos pasos anteriores deshabilitados
					if (iPasoActual > indicePasoNoRetorno.intValue() && nPaso <= indicePasoNoRetorno.intValue()) {						
						tabClass = " class='deshabilitado'";					
					}
				%>
					<li<%=  tabClass %>>
						<bean:message key="pasoPasos.paso"/> <%= strPaso %><br />
						<bean:message key="<%= paso.getClaveTab() %>"/>						
					</li>
				<% 				
				}
				%>
			</logic:notEqual>
		<% 
			}
			nPaso++; 
		%>
		</logic:iterate>	
		</ul>
		&nbsp;
		<div class="barraAzul"></div>
		
		<logic:present name="pasarFlujoTramitacion">
			<div class="alerta">				
				<logic:notEmpty name="tramite" property="flujoTramitacionDatosPersonaActual">					
					<script type="text/javascript">
						function remitir(){
							document.location='<%= urlRemitirFlujo %>';
							return;
						}
					</script>
					<p>
						<bean:message key="flujoTramitacion.remitirAUsuario" arg0="<%=estadoTramite.getFlujoTramitacionDatosPersonaActual().getNombreCompleto()%>" arg1="<%=estadoTramite.getFlujoTramitacionNif() %>"/>												
					</p>
					<br/>
					<div align="center">
						<html:button property="enviar" onclick="javascript:remitir();"> 
							<bean:message key="flujoTramitacion.remitir"/>
						</html:button>					
					</div>
				</logic:notEmpty>
				<logic:empty name="tramite" property="flujoTramitacionDatosPersonaActual">
					<p>
						<bean:message key="flujoTramitacion.noExisteUsuario" arg0="<%=estadoTramite.getFlujoTramitacionNif()%>"/>
					</p>
				</logic:empty>				
			</div>
		</logic:present>
		
	</logic:notEmpty>	