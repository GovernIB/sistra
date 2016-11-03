<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/formularioBusqueda.js" charset="utf-8"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript">
	<!--
	addEvent(window,'load',ajudaIniciar,true);
	-->
</script>

<script type="text/javascript">
	<!--
		function validaFormulario( form )
	 	{
	 	
	 		var un = document.getElementById('usuarioNif');
	 		var uc = document.getElementById('usuarioCodi');
	 		
	 		if ( !isEmptyObject(un) && !isEmptyObject(uc)){
		 		alert("<bean:message key='errors.NIFoCodigo'/>");
				un.focus();
				return false;
	 		}
	 		
	 		if ( isEmptyObject(un) && isEmptyObject(uc) ){
		 		alert("<bean:message key='errors.NIFoCodigo'/>");
				un.focus();
				return false;
	 		}
	 		
	 		if ( !isEmptyObject(un)) {
		 		if(!validaNIF(un.value))
				{
					if(!validaCIF(un.value))
					{
						if(!validaNIE(un.value))
						{
							alert("<bean:message key='error.nifValido'/>");
							un.focus();
							return false;
						}
					}
				}
			}
				
			return true;
    
	    }
	    
	-->
</script>

	<!-- ajuda boto -->
	<!-- ajuda boto -->
	<button id="ajudaBoto" type="button" title="<bean:message key="ayuda.boton.titulo"/>"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="ayuda.boton"/></button>

	<!-- titol -->
	<h1 class="ajuda"><bean:message key="formularioBusqueda.usuarios"/></h1>
	<div id="titolOmbra"></div>
	<!-- /titol -->
	<!-- ajuda -->
	<div id="ajuda">
		<bean:message key="tabs.usuarios.info"/>
	</div>	
	
	<!-- continguts -->

	<div class="continguts">

		<!-- form reserca -->
		<html:form action="busquedaUsuario" styleClass="formulari" onsubmit="return validaFormulario(this);">
			<p>
				<label id="labelN" for="usuarioNif" >
					NIF/CIF: <html:text property="usuarioNif" styleId="usuarioNif" size="9" />
				</label>
				<label for="usuarioCodi">
					<bean:message key="formularioBusqueda.codigoUsuario"/>: 
			    	<html:text property="usuarioCodi" styleId="usuarioCodi" size="20"  />
				</label> 
			</p>
			<html:submit><bean:message key="boton.enviar"/></html:submit>
		</html:form>	
		<!-- /form reserca -->
	</div>


