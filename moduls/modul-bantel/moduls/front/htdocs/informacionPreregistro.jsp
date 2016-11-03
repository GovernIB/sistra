<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:javascript formName="confirmacionForm"/>

<br/>

<bean:define id="btnEnviar" type="java.lang.String">
	<bean:message key="confirmacion.enviar"/>
</bean:define>

<bean:define id="numPrereg" type="java.lang.String">
	<bean:write name="preregistro" property="numeroPreregistro"/>
</bean:define>

<html:form action="confirmarPreregistro" onsubmit="return validateConfirmacionForm(this)">

<!--  Pedimos confirmacion de preregistro y si fuera necesario numero y fecha de registro de entrada -->
<p>
<strong><bean:message key="preregistro.confirmacion"/></strong>
</p>



<!--  Mostramos informacion preregistro  -->
<div id="dadesJustificant">			
	<!--  Para tipo preregistro mostramos numero de registro con el que se ha confirmado -->
	<h4 class="titulo"><bean:message key="preregistro.info"/></h4>
	<ul>
		<li><span class="label"><bean:message key="preregistro.numero"/>:</span> <span><bean:write name="preregistro" property="numeroPreregistro"/></span></li>
		<li><span class="label"><bean:message key="preregistro.fecha"/>:</span> <span><bean:write name="preregistro" property="fechaPreregistro" format="dd/MM/yyyy - HH:mm 'h.'"/></span></li>			
		<logic:notEmpty name="preregistro" property="nif">
			<li><span class="label"><bean:message key="preregistro.nombre"/>:</span> <span><bean:write name="preregistro" property="nombre"/></span></li>
			<li><span class="label"><bean:message key="preregistro.nif"/>:</span> <span><bean:write name="preregistro" property="nif"/></span></li>
		</logic:notEmpty>	
		<li><span class="label"><bean:message key="preregistro.asunto"/>:</span> <span><bean:write name="preregistro" property="asunto"/></span></li>
	</ul>
</div>

<br/>



<!--  	
		DESHABILITAMOS ESTA OPCION PORQUE PUEDE SER UNA FUENTE DE PROBLEMAS SI LOS 
		GESTORES INTRODUCEN MAL LOS NUMEROS DE REGISTROS

 Pedimos informacion registro de entrada 
 
<p class="alerta">
<strong><bean:message key="preregistro.datosRegistro"/></strong>
</p>


<div id="dadesJustificant">				
	<h4 class="titulo"><bean:message key="preregistro.registro"/></h4>
	<ul>
		<li>
			<span class="label"><bean:message key="preregistro.numeroRegistro"/>:</span> 
			<span><html:text property="numeroRegistro"/></span>
		</li>
		<li>
			<span class="label"><bean:message key="preregistro.fechaRegistro"/>:</span> 
			<span><html:text property="fechaRegistro"/></span><i>(dd/mm/yyyy hh:mi)</i>
		</li>					
	</ul>

</div>
-->
<!-- PONEMOS OCULTOS LOS CAMPOS DESHABILITADOS  -->		
<html:hidden property="numeroRegistro"/>
<html:hidden property="fechaRegistro"/>


<br/>

<!--  ERRORES EN FORMULARIO -->
<html:errors/>

<p align="center">
	<html:submit value="<%=btnEnviar%>"/>
</p>	

<html:hidden property="numeroPreregistro" value="<%=numPrereg%>"/>
</html:form>

