<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
		<!-- peu -->
		<div id="peu">
			
			<div class="esquerra">&copy; <bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></div>
			
			<!-- contacte -->
			<div class="centre">			
				<bean:write name="<%=es.caib.sistra.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/>				
			</div>
			
			<!-- /contacte -->
			<div class="dreta">
				<bean:message key="header.mailAdministrador"/>
				 <a href="javascript:void(0)" onclick="mostrarAyudaAdmin();">
					<bean:message key="header.mailAdministrador.enlace"/>
				</a>	
			</div>
		
		</div>