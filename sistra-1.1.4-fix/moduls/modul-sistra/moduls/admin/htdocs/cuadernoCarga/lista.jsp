<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="isAdmin" name="isAdmin" />
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message key="cuadernoCarga.titulo"/></title>
   <meta http-equiv="Content-type" content='text/html; charset="ISO-8859-1"' />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        <logic:present name="reloadMenu">
            parent.Menu.location.reload(true);
        </logic:present>
        
        function auditoriaRequerida()
        {
        	<logic:present name="requiereAuditoria">
	        	<logic:equal name="requiereAuditoria" value="false">
	        		alert( '<bean:message key="cuadernoCarga.auditoriaNoRequerida" />' );
	        	</logic:equal>	
	        </logic:present>	
        }
   //-->
   </script>
</head>

<body class="ventana" onLoad="javascript:auditoriaRequerida();">
<table class="marc">
    <tr><td class="titulo">
        <bean:message name="cabeceraKey" />
    </td></tr>
    <tr><td class="subtitulo"><bean:message key="cuadernoCarga.selec.subtitulo" /></td></tr>
</table>

<br />

<logic:empty name="cuadernosCarga">
    <table class="marc">
      <tr><td class="alert"><bean:message key="cuadernoCarga.selec.vacio" /></td></tr>
    </table>
</logic:empty>

<logic:notEmpty name="cuadernosCarga">
    <table class="marc">
    
    	<tr>
            <td class="outputd">
                <b><bean:message key="cuadernoCarga.descripcion"/></b>
            </td>
            <td class="outputd">
                <b><bean:message key="cuadernoCarga.fechaCarga"/></b>
            </td>
			<logic:equal name="isAuditor" value="true">                
				<td class="outputd">
					<b><bean:message key='cuadernoCarga.fechaEnvio' /></b>
				</td>
			</logic:equal>            
			<logic:equal name="isAdmin" value="true">                
				<td class="outputd">
					<b><bean:message key='cuadernoCarga.estado' /></b>
				</td>
				<td class="outputd">
					<b><bean:message key='cuadernoCarga.importado' /></b>
				</td>
			</logic:equal>            
            <td align="left">&nbsp;</td> 
        </tr>

        <logic:iterate id="cuadernoCarga" name="cuadernosCarga">
            <bean:define id="urlEditar"><html:rewrite page="/admin/cuadernoCarga/seleccion.do" paramId="codigo" paramName="cuadernoCarga" paramProperty="codigo"/></bean:define>
            <bean:define id="urlEditarAuditor"><html:rewrite page="/admin/cuadernoCarga/seleccionAuditor.do" paramId="codigo" paramName="cuadernoCarga" paramProperty="codigo"/></bean:define>
            <bean:define id="descripcion" name="cuadernoCarga" property="descripcion" type="java.lang.String"/>
            <bean:define id="codigoCuadernoCarga" name="cuadernoCarga" property="codigo" type="java.lang.Long"/>
            <bean:define id="mensajeBaja"><bean:message arg0='<%=codigoCuadernoCarga.toString()%>' arg1='<%=StringUtils.escape(descripcion)%>' key='cuadernoCarga.baja' /></bean:define>
            <bean:define id="urlBaja"><html:rewrite page="/admin/cuadernoCarga/baja.do" paramId="codigo" paramName="cuadernoCarga" paramProperty="codigo"/></bean:define>
			<bean:define id="urlAuditar"><html:rewrite page="/admin/cuadernoCarga/envioAuditoria.do" paramId="codigo" paramName="cuadernoCarga" paramProperty="codigo"/></bean:define>            
			<bean:define id="estadoAuditoria" name="cuadernoCarga" property="estadoAuditoria" />
           
            
                
            <tr>
                <td class="outputd">
                    <bean:write name="cuadernoCarga" property="descripcion" />
                </td>
                <td class="outputd">
                    <bean:write name="cuadernoCarga" property="fechaCarga" format="dd/MM/yyyy"/>
                </td>
            <logic:equal name="isAuditor" value="true">                
				<td class="outputd">
					<bean:write name="cuadernoCarga" property="fechaEnvio" format="dd/MM/yyyy"/>
				</td>
			</logic:equal>  
			<logic:equal name="isAdmin" value="true">                
				<td class="outputd">
					<bean:message key='<%= "cuadernoCarga.estado." + estadoAuditoria.toString() %>' />
				</td>
				<td class="outputd">
					<logic:equal name="cuadernoCarga" property="importado" value="S">
						<bean:message key="cuadernoCarga.importado" />
					</logic:equal> 
				</td>
			</logic:equal>				
				<td align="left">
					<button class="button" type="button" onclick="forward('<%= "true".equals( isAdmin.toString() ) ? urlEditar : urlEditarAuditor %>')"><bean:message key="boton.selec" /></button>
				<logic:equal name="isAdmin" value="true">	
                    <button class="button" type="button" onclick="confirmAndForward('<%= StringUtils.escape( mensajeBaja )%>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
                    <logic:equal name="estadoAuditoria" value="I">
						<button class="button" type="button" onclick="forward('<%=urlAuditar%>')"><bean:message key="listado.boton.auditar" /></button>
					</logic:equal>	
				</logic:equal>	
				</td>
            </tr>
        </logic:iterate>
    </table>
</logic:notEmpty>

<br />

<logic:equal name="isAdmin" value="true">
    <table class="nomarc">
      <tr>
        <td align="left">
            <button class="buttond" type="button" onclick="forward('<html:rewrite page="/admin/cuadernoCarga/alta.do"/>')">
                <bean:message key="boton.nuevo" />
            </button>
        </td>
      </tr>
    </table>
</logic:equal>    

</body>
</html:html>