<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:html locale="true" xhtml="true">
<tiles:useAttribute name="idEspecTramiteNivel"/>
<bean:define id="idTrNivel" name="idTramiteNivel"/>
<head>
   <title><bean:message key="datoJustificante.titulo"/></title>
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
        <bean:message key="datoJustificante.selec" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="datoJustificante.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:empty name="datoJustificanteOptions">
    <table class="marc">
      <tr><td class="alert"><bean:message key="datoJustificante.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="datoJustificanteOptions">
    <table class="marc">
        <logic:iterate id="datoJustificante" name="datoJustificanteOptions">
            <tr>
                <td class="outputd" width="70%">
                    <bean:write name="datoJustificante" property="codigo" />
                    <bean:define id="modTramiteNivel" name="datoJustificante" property="codigo" type="java.lang.Long"/>
                    <logic:equal name="datoJustificante" property="tipo" value="<%=Character.toString(es.caib.sistra.model.DatoJustificante.TIPO_BLOQUE)%>">
                    	(Separador de bloques)
                    </logic:equal>
                    <logic:equal name="datoJustificante" property="tipo" value="<%=Character.toString(es.caib.sistra.model.DatoJustificante.TIPO_CAMPO)%>">
                   	    <logic:notEmpty name="datoJustificante" property="referenciaCampo">
		                    (<bean:write name="datoJustificante" property="referenciaCampo" />)
	                    </logic:notEmpty>
	                    <logic:empty name="datoJustificante" property="referenciaCampo">
		                     (Script)
	                    </logic:empty>
                    </logic:equal>            
                </td>
                <td align="right">
                    <bean:define id="urlEditar"><html:rewrite page='<%= "/back/datoJustificante/seleccion.do?idEspecTramiteNivel=" + idEspecTramiteNivel  + "&idTramiteNivel=" + idTrNivel %>' paramId="codigo" paramName="datoJustificante" paramProperty="codigo"/></bean:define>
                    <button class="button" type="button" onclick="forward('<%=urlEditar%>')"><bean:message key="boton.selec" /></button>
					
					<bean:define id="descripcion" name="datoJustificante" property="codigo" type="java.lang.Long"/>
					<logic:notPresent name="bloqueado">
	                    <bean:define id="mensajeBaja"><bean:message arg0='<%=modTramiteNivel.toString()%>' arg1='<%=StringUtils.escape(descripcion.toString())%>' key='datoJustificante.baja' /></bean:define>
	                    <bean:define id="urlBaja"><html:rewrite page='<%= "/back/datoJustificante/baja.do?idEspecTramiteNivel=" + idEspecTramiteNivel  + "&idTramiteNivel=" + idTrNivel %>' paramId="codigo" paramName="datoJustificante" paramProperty="codigo"/></bean:define>
	                    <button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape( mensajeBaja )%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
	                    <bean:define id="urlUp"><html:rewrite page='<%= "/back/datoJustificante/up.do?idEspecTramiteNivel=" + idEspecTramiteNivel  + "&idTramiteNivel=" + idTrNivel %>' paramId="codigo" paramName="datoJustificante" paramProperty="codigo"/></bean:define>
	                    <button class="button" type="button" onclick="javascript:document.location.href='<%= urlUp %>'"><bean:message key="boton.up" /></button>
	                    <bean:define id="urlDown"><html:rewrite page='<%= "/back/datoJustificante/down.do?idEspecTramiteNivel=" + idEspecTramiteNivel  + "&idTramiteNivel=" + idTrNivel %>' paramId="codigo" paramName="datoJustificante" paramProperty="codigo"/></bean:define>
	                    <button class="button" type="button" onclick="javascript:document.location.href='<%= urlDown %>'"><bean:message key="boton.down" /></button>
                    </logic:notPresent>
                </td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

    <table class="nomarc">
      <tr>
        <logic:notPresent name="bloqueado">      
	        <td align="left">
	    	    <bean:define id="urlNuevo">
			        <html:rewrite page="/back/datoJustificante/alta.do" paramId="idEspecTramiteNivel" paramName="idEspecTramiteNivel"/>
			    </bean:define>
	            <button class="buttond" type="button" onclick="forward('<%= urlNuevo + "&codigo=" + idEspecTramiteNivel + "&idTramiteNivel=" + idTrNivel%>')">
	                <bean:message key="boton.nuevo" />
	            </button>
	        </td>
        </logic:notPresent>
        <td align="right">
	        <bean:define id="funcionalidad" value="tramiteVersion" />
	        <logic:notEmpty name="idTrNivel"><% funcionalidad = "tramiteNivel"; %></logic:notEmpty>
      		<logic:empty name="idTrNivel"><% funcionalidad = "especificacionesGenericas"; %></logic:empty>
        	<bean:define id="codigo" name="idEspecTramiteNivel" />
       		<logic:notEmpty name="idTrNivel"><% codigo = idTrNivel; %></logic:notEmpty>
       		<logic:empty name="idTrNivel"><% codigo = idEspecTramiteNivel; %></logic:empty>
	        <bean:define id="urlBack">
		        <html:rewrite page="<%="/back/" + funcionalidad + "/seleccion.do?codigo=" + codigo%>" />
		    </bean:define>
            <button class="buttond" type="button" onclick="forward('<%= urlBack %>')">
                <bean:message key="boton.cancel" />
            </button>    
        </td>
      </tr>
    </table>

</body>
</html:html>