<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
	
<%
	String id = (String) request.getAttribute("id");
%>	
	

<div id="vdDetalles">	

	<p class="validado">
		<bean:message key="comprobarDocumento.existe"/> 						
	</p>

	<ul>
			<!-- Fecha documento -->
			<li class="fecha">					
				<strong><bean:message key="comprobarDocumento.fecha"/>:</strong> 
				<bean:write name="documento" property="fechaRDS" format="dd/MM/yyyy"/> 										
			</li>

			<!-- Tipo de documento -->
			<logic:equal name="documento" property="estructurado" value="true">
				<li class="documento">					
					<strong><bean:message key="comprobarDocumento.tipoDocumento"/>:</strong> XML - 
					<strong><bean:message key="comprobarDocumento.visualizacion"/>:</strong> PDF 										
				</li>			
			</logic:equal>
			<logic:equal name="documento" property="estructurado" value="false">
				<li class="documento">					
					<strong><bean:message key="comprobarDocumento.tipoDocumento"/>:</strong> 
					<bean:write name="documento" property="extensionFichero"/> 										
				</li>							
			</logic:equal>

			<!--  Firmas digitales -->
			<logic:empty name="documento" property="firmas">
				<li class="firma">					
					<strong><bean:message key="comprobarDocumento.firmaDigital"/>:</strong> 
					No										
				</li>
			</logic:empty>
			<logic:notEmpty name="documento" property="firmas">
				<%
						int	indiceFirma=0;
				%>	
				<logic:iterate name="firmas" id="firma" >							
				<li class="firma">					
						<strong><bean:message key="comprobarDocumento.firmaDigital"/>:</strong> 				
						<bean:message key="comprobarDocumento.firmadoPor"/> <bean:write name="firma" property="nombreApellidos"/> 										
						
						<bean:define id="indice" value="<%=Integer.toString(indiceFirma)%>"/>
						-	
						<a href="descargarFirma.do?id=<%=id%>&index=<%=indice%>">											
							<bean:message key="comprobarDocumento.descargarFirma"/>
						</a>
						
						<%
							indiceFirma++;
						%>						
					</li>								
				</logic:iterate>			
			</logic:notEmpty>
	</ul>
</div>
	
	<iframe id="vdDocumento" src="mostrarDocumento.do?id=<%=id%>" frameborder="0" scrolling="auto" title="Documento"></iframe>
	
	
	<bean:define id="lang" value="<%=((java.util.Locale) session.getAttribute(org.apache.struts.Globals.LOCALE_KEY)).getLanguage()%>" type="java.lang.String"/>
	<div id="capsalIdioma">
			<span class="invisible">Idioma: </span>
			<ul>
				<logic:equal name="lang" value="ca">
					<li><strong>catal&agrave;</strong> . </li>
					<li><a href="init.do?lang=es&<%=request.getAttribute("queryString")%>" title="Cambiar el idioma a Espa&ntilde;ol">espa&ntilde;ol</a> . </li>
				</logic:equal>				
				<logic:equal name="lang" value="es">
					<li><a href="init.do?lang=ca&<%=request.getAttribute("queryString")%>" title="Cambiar el idioma a Catal&agrave;">catal&agrave;</a> . </li>
					<li><strong>espa&ntilde;ol</strong> . </li>
				</logic:equal>
				<logic:equal name="lang" value="en">
					<li><a href="init.do?lang=en&id=<%=request.getAttribute("queryString")%>" title="Change language to English">english</a> . </li>
					<li><strong>english</strong> . </li>
				</logic:equal>
			</ul>
		</div>
	