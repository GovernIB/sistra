<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.modelInterfaz.ConstantesZPE"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<script type="text/javascript">
<!--
function cancelarTramite( url )
{
	if ( confirm ( '<bean:message key="cancelacionTramite.confirmacion" />' ) )
	{
		document.location.href=url;
	} 
}
-->
</script>
<bean:define id="urlEliminarTramite">
        <html:rewrite page="/protected/abandonarTramite.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<div id="capsalUsuari">

<!--  Acceso anonimo -->
<logic:equal name="metodoAutenticacion" value="A">
	<bean:message key="datosUsuario.usuario" />: <strong><bean:message key="datosUsuario.anonimo" /></strong>
	<logic:present name="tramite"> 
		<bean:define id="urlGuardarClave">
		        <html:rewrite page="/protected/guardarClave.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
		</bean:define>
		<bean:define id="pasoNoRetorno" name="tramite" property="pasoNoRetorno" /> 
		<bean:define id="idPersistencia" name="tramite" property="idPersistencia" /> 

		<logic:equal name="tramite" property="circuitoReducido" value="false">
			<logic:lessEqual name="tramite" property="pasoActual" value="<%= String.valueOf( pasoNoRetorno ) %>">
				<bean:message key="datosUsuario.claveTramite" />: <strong><bean:write name="tramite" property="idPersistencia"/></strong>
				 &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;
				   <html:link styleClass="guardar" href="<%= urlGuardarClave + \"&idPersistencia=\" + idPersistencia %>"><bean:message key="datosUsuario.guardarClaveTextoEnlace" /></html:link>
				   <a href="#" class="cancelar" onclick="javascript:cancelarTramite( '<%= urlEliminarTramite %>' )" title="<bean:message key="datosUsuario.cancelarTramiteTextoEnlace" />"><bean:message key="datosUsuario.cancelarTramiteTextoEnlace" /></a>
			</logic:lessEqual>
		</logic:equal>
	</logic:present>
</logic:equal>

<!--  Acceso autenticado -->
<logic:notEqual name="metodoAutenticacion" value="A">
	<logic:present name="datossesion">
		<bean:define id="datosSesion" name="datossesion" type="es.caib.sistra.model.DatosSesion" />
		<bean:message key="datosUsuario.usuario" />: <strong><%= datosSesion.getNombreCompletoUsuario() %></strong>. 
		<logic:equal name="datosSesion" property="perfilAcceso" value="<%=ConstantesZPE.DELEGACION_PERFIL_ACCESO_DELEGADO %>">
		 - <bean:message key="datosUsuario.accesoDelegado" arg0="<%=datosSesion.getNombreCompletoDelegado() %>" />
		</logic:equal>
		
		
	</logic:present>	
	<logic:present name="tramite">
		<bean:define id="pasoNoRetorno" name="tramite" property="pasoNoRetorno" /> 
		<bean:define id="idPersistencia" name="tramite" property="idPersistencia" /> 
		<logic:lessEqual name="tramite" property="pasoActual" value="<%= String.valueOf( pasoNoRetorno ) %>">
			&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;<a href="#" class="cancelar" onclick="javascript:cancelarTramite( '<%= urlEliminarTramite %>' )" title="<bean:message key="datosUsuario.cancelarTramiteTextoEnlace" />"><bean:message key="datosUsuario.cancelarTramiteTextoEnlace" /></a>
		</logic:lessEqual>
	</logic:present>
</logic:notEqual>

</div>