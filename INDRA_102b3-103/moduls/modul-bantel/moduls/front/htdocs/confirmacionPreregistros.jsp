<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<html:javascript formName="confirmacionForm"/>

<br/>

<bean:define id="btnEnviar" type="java.lang.String">
	<bean:message key="confirmacion.enviar"/>
</bean:define>

<p class="alerta">
<strong><bean:message key="confirmacion.aviso"/></strong>
</p>

<br/>
 
<html:form action="informacionPreregistro" onsubmit="return validateConfirmacionForm(this)">

<p>
	<bean:message key="confirmacion.numeroPreregistro"/>
	<html:text property="numeroPreregistro"/>
</p>

<p align="center">
	<html:submit value="<%=btnEnviar%>"/>
</p>


<html:hidden property="numeroRegistro"/>
<html:hidden property="fechaRegistro"/>
</html:form>

</p>
