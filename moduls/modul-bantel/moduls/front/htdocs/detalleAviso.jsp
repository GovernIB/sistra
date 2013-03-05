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
		document.location='<html:rewrite page="/recuperarExpediente.do?identificadorExp='+identificadorExp+'&unidadAdm='+unidadAdm+'&claveExp='+claveExp+'" />';		
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
					<dt><bean:message key="detalle.aviso.fechaLectura"/></dt>
					<dd>
						<logic:notEmpty name="aviso" property="fechaConsulta">
							<bean:write name="aviso" property="fechaConsulta" format="dd/MM/yyyy '-' HH:mm"/>
						</logic:notEmpty>
					</dd>
					<dt><bean:message key="detalle.aviso.asunto"/></dt>
					<dd><bean:write name="aviso" property="titulo"/></dd>
					<dt><bean:message key="expediente.descripcion"/></dt>
					<dd><bean:write name="aviso" property="texto"/></dd>
					
					<dt><bean:message key="detalle.aviso.accesoPorClave"/></dt>
					<dd>
						<logic:equal name="aviso" property="accesiblePorClave" value="true">
							<bean:message key="expediente.si"/>
						</logic:equal>
						<logic:equal name="aviso" property="accesiblePorClave" value="false">
							<bean:message key="expediente.no"/>
						</logic:equal>
					</dd>	
					<logic:equal name="aviso" property="accesiblePorClave" value="true">						   
						<dt><bean:message key="detalle.aviso.claveAcceso"/></dt>						
						<dd><bean:write name="aviso" property="claveAcceso"/></dd>
					</logic:equal>
					
					<logic:notEmpty name="aviso" property="documentos">		
						<dt><bean:message key="aviso.documentoanexo" />:</dt>
						<dd>
							<ul class="docs">
								<logic:iterate id="documento" name="aviso" property="documentos"  type="es.caib.zonaper.modelInterfaz.DocumentoExpedientePAD">
								<li>
									
									<bean:define id="codigoFirma" type="java.lang.String">
										<bean:write name="documento" property="codigoRDS" />
									</bean:define>
									
									<logic:empty name="<%=\"URL-\" + codigoFirma %>" scope="request">
										<a href='<%=url%>?codigo=<%=documento.getCodigoRDS() %>&clave=<%=documento.getClaveRDS() %>&idioma=<%=expediente.getIdioma() %>'> 
											<bean:write name="documento" property="titulo" />
										</a>																
									</logic:empty>	
									
									<logic:notEmpty name="<%=\"URL-\" + codigoFirma %>" scope="request">
										<a href="<bean:write name="<%=\"URL-\" + codigoFirma %>" scope="request"/>" target="_blank">
											<bean:write name="documento" property="titulo" />
										</a>													
									</logic:notEmpty>
									
									<span class="pequenyo">
									<logic:notEmpty name="<%=codigoFirma %>" scope="request">
										<br/>
										<bean:message key="comprobarDocumento.firmadoPor"/>
										<logic:iterate name="<%=codigoFirma %>" id="firma" scope="request" type="es.caib.sistra.plugins.firma.FirmaIntf">							
											&nbsp;
											<a href="/bantelfront/mostrarFirmaDocumento.do?codigo=<%=documento.getCodigoRDS()%>&clave=<%=documento.getClaveRDS()%>&nif=<%=firma.getNif()%>" >
												<bean:write name="firma" property="nombreApellidos"/>  									
											</a>	
										</logic:iterate>			
									</logic:notEmpty>
									
									<logic:notEmpty name="<%=\"CUST-\" + codigoFirma %>" scope="request">
										<br/>
										<bean:message key="comprobarDocumento.urlCustodia"/>
										<a href="/bantelfront/mostrarDocumentoCustodia.do?codigo=<%=documento.getCodigoRDS()%>&clave=<%=documento.getClaveRDS()%>" target="_blank">
											<bean:write name="<%=\"CUST-\" + codigoFirma %>" />
										</a>														
									</logic:notEmpty>
									</span>
									
								</li>
								</logic:iterate>
							</ul>
						</dd>				
					</logic:notEmpty>
				</dl>
		
			</div>
			
			<!-- tornar enrere -->
			
			<div id="enrere">
				<a href="#" onclick="javascript:volver('<%=expediente.getIdentificadorExpediente()%>','<%=expediente.getUnidadAdministrativa()%>','<%=expediente.getClaveExpediente()%>')">
					<bean:message key="detalle.aviso.tornar" />				
				</a>					
			</div>
			
			<!-- /tornar enrere -->
			
		</div>
		<!-- /continguts -->
		

