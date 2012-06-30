<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript" src="js/jquery.selectboxes.pack.js"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript" src="js/funcions.js"></script>
<script type="text/javascript">
	function consulta(){
		var index = document.forms["0"].unidadAdm.selectedIndex;
		document.forms["0"].flagValidacion.value="consulta";
		document.forms["0"].nombreUnidad.value=document.forms["0"].unidadAdm.options[index].text;
		document.forms["0"].action='<html:rewrite page="/recuperarExpediente.do"/>';
		document.forms["0"].submit();
	}
</script>
<script type="text/javascript">
     <!--
     function mostrarArbolUnidades(url) {
        obrir(url, "Arbol", 540, 400);
     }
     // -->
</script>
<bean:define id="urlArbol">
    <html:rewrite page="/arbolUnidades.do"/>
</bean:define>
<bean:define id="btnAlta" type="java.lang.String">
	<bean:message key="confirmacion.alta"/>
</bean:define>
<bean:define id="btnRecuperar" type="java.lang.String">
	<bean:message key="confirmacion.altaEntrada"/>
</bean:define>
<bean:define id="btnConsultar" type="java.lang.String">
	<bean:message key="confirmacion.consultar"/>
</bean:define>
	
		<!-- ajuda boto -->
		<button id="ajudaBoto" type="button" title="Activar ajuda"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="confirmacion.ayuda"/></button>
		<!-- /ajuda boto -->
		<div id="opcions">
				&nbsp;
		</div>
		<!-- titol -->
		<!--<h1>Gestión de expedientes</h1>-->
		<!-- /titol -->
		
		<!-- ajuda -->
		<div id="ajuda">
			<h2><bean:message key="ajuda.titulo"/></h2>
			<br/>
			<bean:message key="ajuda.CampoObligarorio"/>
			<bean:message key="ajuda.expediente.alta.identificador"/>
			<bean:message key="ajuda.expediente.unidad"/>
			<bean:message key="ajuda.finCampoObligarorio"/>
			<bean:message key="ajuda.expediente.consulta.clave"/>
		</div>
		<!-- /ajuda -->
		<html:errors/>
		
		<!-- continguts -->
		<div class="continguts">
		
			<p><bean:message key="confirmacion.explicacion"/></p>
			
			<html:form action="recuperarExpediente" styleClass="remarcar opcions">
				<html:hidden property="flagValidacion"/>
				<html:hidden property="nombreUnidad"/>
				<p>
					<label for="identificadorExp"><bean:message key="confirmacion.identificadorExpediente"/>:</label>
					<html:text property="identificadorExp"/>
				</p>
				
				<p>
					<label for="unitat_admin"><bean:message key="confirmacion.unidadAdministrativa"/></label>
					<html:select property="unidadAdm">
						<logic:iterate id="unidad" name="unidades">	
							<html:option value="<%=((es.caib.bantel.front.json.UnidadAdministrativa)unidad).getCodigo()%>" ><bean:write name="unidad" property="descripcion"/></html:option>
						</logic:iterate>
					</html:select>
					<button type="button" onclick="mostrarArbolUnidades('<%=urlArbol + "?id=unidadAdm" %>');"><bean:message key="confirmacion.seleccionar"/></button>
				</p>
				
				<p>
					<label for="claveExp"><bean:message key="confirmacion.claveExpediente"/></label>
					<html:text property="claveExp"/>
				</p>

				
				
				<p class="botonera">
					<input type="button" value="<%=btnConsultar%>" onclick="consulta();"/>
				</p>
			
			</html:form>
			
		</div>
		<!-- /continguts -->
		


