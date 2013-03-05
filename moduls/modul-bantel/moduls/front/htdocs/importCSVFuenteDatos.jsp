<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript" src="js/mensaje.js"></script>	

<bean:define id="identificador" name="identificador" scope="request" type="java.lang.String"/>

<!-- continguts -->
<div class="continguts">

	<p class="titol"><bean:message key="importacionFuenteDatos.titulo"/></p>
		
	<div class="remarcar">
		<p>
			<bean:message key="importacionFuenteDatos.texto"/>
		</p>
		<html:form action="importCSVFuenteDatosAction" enctype="multipart/form-data" styleClass="remarcar opcions">
			<html:hidden property="identificador" value="<%=identificador%>"/>
			<html:file property="csv" styleClass="pc40" size="100"/>
			<p class="alerta">
				<bean:message key="importacionFuenteDatos.avisoImportar"/>				
			</p>
			<p class="botonera">
	  			<html:submit><bean:message key="importacionFuenteDatos.importar"/></html:submit>
	  		</p>
	  	</html:form>	
	</div>
	
	<div id="enrere">
		<html:link action="detalleFuenteDatos" paramId="identificador" paramName="identificador">
			<bean:message key="importacionFuenteDatos.cancelar"/>
		</html:link>					 
	</div>
		
</div>
<!-- /continguts -->		
		