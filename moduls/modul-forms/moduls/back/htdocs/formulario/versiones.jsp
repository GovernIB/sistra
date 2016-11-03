<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="formulario.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
   //-->
   </script>
</head>

<body class="ventana">
<table class="marc">
    <tr><td class="titulo">
        <bean:message key="formulario.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="formulario.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:empty name="formularioOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="formulario.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="formularioOptions">
    <table class="marc">
        <logic:iterate id="formulario" name="formularioOptions">
            <tr>
                <td class="outputd" width="50%">
                    <bean:write name="formulario" property="modelo" />
                    <bean:define id="modelo" name="formulario" property="modelo" type="java.lang.String"/>
                    (<bean:write name="formulario" property="traduccion.titulo" />)
                    v<bean:write name="formulario" property="version" />
                </td>
                <td align="left">
                    <!--Botón para editar un formulario-->
                    <bean:define id="urlEditar"><html:rewrite page="/back/formulario/seleccion.do" paramId="id" paramName="formulario" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>

                    <!--Botón para exportar un formulario-->
                    <bean:define id="urlExportar"><html:rewrite page="/generar/xml.do" paramId="idForm" paramName="formulario" paramProperty="id"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlExportar%>')"><bean:message key="boton.exportar" /></button>
                  
                    <!--Botón para eliminar un formulario-->
                    <bean:define id="titulo" name="formulario" property="traduccion.titulo" type="java.lang.String"/>
                    <bean:define id="modelo" name="formulario" property="modelo" type="java.lang.String"/>
                    <bean:define id="version" name="formulario" property="version" type="java.lang.Integer"/>
                    
                    
                    <logic:equal name="formulario" property="bloqueado" value="false">
                        <button class="button" type="button" disabled="disabled"><bean:message key="boton.baixa"/></button>
                    </logic:equal>
                    
                    <logic:equal name="formulario" property="bloqueado" value="true">
                    	<logic:equal name="formulario" property="bloqueadoPor" value="<%=request.getUserPrincipal().getName()%>">
	                        <bean:define id="urlBaja"><html:rewrite page="/back/formulario/baja.do" paramId="id" paramName="formulario" paramProperty="id"/></bean:define>
	                        <bean:define id="mensaje"><bean:message arg0='<%=modelo%>' arg1='<%=StringUtils.escape(titulo)%>' arg2='<%=version.toString()%>' key='formulario.baja' /></bean:define>
	                         <%
    	                    	mensaje = mensaje.replace("\'","&#145;");
	    	                    mensaje = mensaje.replace("\"","&#34;");
    	                    %>
	                        <button class="button" type="button" onclick="confirmAndForward('<%=mensaje%>', '<%=urlBaja%>')"><bean:message key="boton.baixa"/></button>
	                    </logic:equal>	                    
	                    <logic:notEqual name="formulario" property="bloqueadoPor" value="<%=request.getUserPrincipal().getName()%>">
	       	           		<button class="button" type="button" disabled="disabled"><bean:message key="boton.baixa"/></button>
	       	           </logic:notEqual>
                    </logic:equal>

                    <!--Botón para bloquear/desbloquear un formulario-->                    
                    <logic:equal name="formulario" property="bloqueado" value="true">
                    	<logic:equal name="formulario" property="bloqueadoPor" value="<%=request.getUserPrincipal().getName()%>">
	                        <bean:define id="urlDesbloquear"><html:rewrite page="/back/formulario/desbloquear.do" paramId="id" paramName="formulario" paramProperty="id"/></bean:define>
	                        <button class="button" type="button" onclick="forward('<%=urlDesbloquear%>')" ><bean:message key="boton.desbloquear"/></button>
	                    </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="formulario" property="bloqueado" value="true">
                        <bean:define id="urlBloquear"><html:rewrite page="/back/formulario/bloquear.do" paramId="id" paramName="formulario" paramProperty="id"/></bean:define>
                        <button class="button" type="button" onclick="forward('<%=urlBloquear%>')"><bean:message key="boton.bloquear"/></button>
                    </logic:notEqual>                                    
                  
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

</body>
</html:html>