<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="es.caib.util.StringUtil"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript">
	function volver(identificadorExp,unidadAdm,claveExp){
		document.forms["0"].action='<html:rewrite page="/recuperarExpediente.do?identificadorExp='+identificadorExp+'&unidadAdm='+unidadAdm+'&claveExp='+claveExp+'" />';
		document.forms["0"].submit();
	}
</script>
<bean:define id="aviso" name="elemento" type="es.caib.zonaper.modelInterfaz.EventoExpedientePAD" />
<bean:define id="expediente" name="expediente" type="es.caib.zonaper.modelInterfaz.ExpedientePAD" />
<bean:define id="url"  type="java.lang.String" >
<html:rewrite page="/abrirDocumento.do"/>
</bean:define>
	
		
		
		<!-- continguts -->
		<div class="continguts">

			<p class="titol"><bean:message key="detalle.aviso.detalle" /></p>
			
			<div class="avis">
			
				<dl>
					<dt><bean:message key="detalle.aviso.fechaEmision"/></dt>
					<dd><bean:write name="aviso" property="fecha" format="dd/MM/yyyy '-' HH:mm"/></dd>
					<dt><bean:message key="detalle.aviso.asunto"/></dt>
					<dd><bean:write name="aviso" property="titulo"/></dd>
					<dt><bean:message key="expediente.descripcion"/></dt>
					<dd><bean:write name="aviso" property="texto"/></dd>
					<logic:notEmpty name="aviso" property="documentos">		
						<dt><bean:message key="aviso.documentoanexo" />:</dt>
						<dd>
							<ul class="docs">
								<logic:iterate id="documento" name="aviso" property="documentos"  type="es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD">
								<li>
									
									<bean:define id="codigoFirma" type="java.lang.String">
										<bean:write name="documento" property="codigoRDS" />
									</bean:define>
									
									<logic:empty name="<%="URL-" + codigoFirma %>" scope="request">
										<a href='<%=url%>?codigo=<%=documento.getCodigoRDS() %>&clave=<%=documento.getClaveRDS() %>&idioma=<%=expediente.getIdioma() %>'> 
											<bean:write name="documento" property="titulo" />
										</a>																
									</logic:empty>	
									
									<logic:notEmpty name="<%="URL-" + codigoFirma %>" scope="request">
										<a href="<bean:write name="<%="URL-" + codigoFirma %>" scope="request"/>" target="_blank">
											<bean:write name="documento" property="titulo" />
										</a>													
									</logic:notEmpty>
									
									<logic:notEmpty name="<%=codigoFirma %>" scope="request">
										<bean:message key="comprobarDocumento.firmadoPor"/>
										<logic:iterate name="<%=codigoFirma %>" id="firma" scope="request">							
											&nbsp;<bean:write name="firma" property="nombreApellidos"/>
										</logic:iterate>			
									</logic:notEmpty>
									
								</li>
								</logic:iterate>
							</ul>
						</dd>				
					</logic:notEmpty>
				</dl>
		
			</div>
			
			<!-- tornar enrere -->
			
			<div id="enrere">
				<html:form style="background-color:white" action="recuperarExpediente" >
				<a href="#" onclick="javascript:volver('<%=expediente.getIdentificadorExpediente()%>','<%=expediente.getUnidadAdministrativa()%>','<%=expediente.getClaveExpediente()%>')">
					<bean:message key="detalle.aviso.tornar" />				
				</a>	
				</html:form>
			</div>
			
			<!-- /tornar enrere -->
			
		</div>
		<!-- /continguts -->
		

