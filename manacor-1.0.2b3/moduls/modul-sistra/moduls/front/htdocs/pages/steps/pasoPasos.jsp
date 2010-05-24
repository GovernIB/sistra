<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.sistra.front.util.TramiteRequestHelper"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
		<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
		<bean:define id="referenciaPortal"  type="java.lang.String">
			<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property='<%="referenciaPortal("+ lang +")"%>'/>
		</bean:define>
		<bean:define id="urlSiguiente">
		        <html:rewrite page="/protected/siguientePaso.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
		</bean:define>
		<bean:define id="idPersistencia" name="tramite" property="idPersistencia" type="java.lang.String"/>	
		<bean:define id="diasPersistencia" name="tramite" property="diasPersistencia" type="java.lang.Integer"/>			
		<bean:define id="urlGuardarClave" type="java.lang.String">
		      	<html:rewrite page="/protected/guardarClave.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
		</bean:define>
			<p>
				<bean:message key="pasoPasos.instrucciones.texto1"/> <strong><bean:message key="pasoPasos.instrucciones.texto2"/></strong> <bean:message key="pasoPasos.instrucciones.texto3"/> <strong><bean:message key="pasoPasos.instrucciones.texto4"/></strong> <bean:message key="pasoPasos.instrucciones.texto5"/>	
			</p>
			
			<logic:notEmpty name="tramite" property="fechaFinPlazo">
				<p>
					<logic:equal name="tramite" property="tipoTramitacion" value="T">
						<bean:message key="pasoPasos.instrucciones.fechaFinPlazo.telematica"/>
					</logic:equal>
					
					<logic:notEqual name="tramite" property="tipoTramitacion" value="T">
						<bean:message key="pasoPasos.instrucciones.fechaFinPlazo.presencial"/>
					</logic:notEqual>				
					 
					<strong><bean:write name="tramite" property="fechaFinPlazo" format="dd/MM/yyyy"/></strong>.				
				</p>
			</logic:notEmpty>
			
			<logic:equal name="metodoAutenticacion" value="A">
				<p><bean:message key="pasoPasos.instrucciones.guardarClave" arg0="<%= idPersistencia %>" arg1="<%= urlGuardarClave %>"/></p>
			</logic:equal>
			
			
			<p>
				<logic:greaterThan name="diasPersistencia" value="0">
					<bean:message key="pasoPasos.instrucciones.accesoBuzon" arg0="<%=referenciaPortal%>" arg1="<%= diasPersistencia.toString() %>"/>
				</logic:greaterThan>
				<logic:lessEqual name="diasPersistencia" value="0">
					<bean:message key="pasoPasos.instrucciones.accesoBuzon.plazoCumplido" arg0="<%=referenciaPortal%>"/>
				</logic:lessEqual>
				
			</p>
			
			
		<!-- pasos -->
		<logic:notEmpty name="pasos">
			<bean:define id="pasos" name="pasos" type="java.util.ArrayList"/>
			<%
				int nPaso = 0;
				int nPasos = pasos.size(); 
			%>
		<div id="pasos">
			<div id="pasosPre"></div>
			<p><bean:message key="pasoPasos.pasos.explicacion.texto1" /> <strong><bean:message key="pasoPasos.pasos.explicacion.texto2" /></strong>.</p>
			<!--  iteracion pasos -->
			<logic:iterate id="paso" name="pasos" type="es.caib.sistra.model.PasoTramitacion">		
			<%
			if ( nPaso != 0 )
			{
			%>
			<div class="paso">
				<h2><bean:message key="pasoPasos.paso"/> <%= String.valueOf( nPaso ) %></h2>
				<bean:message key="<%= paso.getClaveTitulo() %>"/>
			</div>
			<div class="pasoTexto"><bean:message key="<%= paso.getClaveCuerpo() %>"/></div>
			<!--  sólo si no es el ultimo paso-->
			<% 	
				if ( nPaso != nPasos - 1 )
				{
			%>
			<div class="pasoSep"></div>
			<% 
				}
			}
			nPaso++;
			%>
			</logic:iterate>
			<!--  end iteracion pasos -->
		</div>
		</logic:notEmpty>
		<!-- iniciar tramitacion -->
		<div id="iniciarTramitacionPre"></div>
		<div id="iniciarTramitacion">
			<img src="imgs/tramitacion/index_iniciar.gif" alt="" />
			<p><bean:message key="pasoPasos.informacionComienzoTramitacion"/></p>
			<p class="iniciar"><a href="<%= urlSiguiente %>"><bean:message key="pasoPasos.iniciarTramitacion"/></a></p>
		</div>
			