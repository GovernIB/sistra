<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib uri="back" prefix="back"%>
<%--
 Parametros tiles:
    title: key del título de la página.
    alta.title: key del título de la acción alta
    modificacion.title: key del título de la acción modificación
    subtitulo: key del subtitulo
    validateMethod: Nombre del metodo de validación
    form.action: Action a la que se realiza el post.
    form.bean: Nombre del form.
    
    paginaValues: pagina que muestra los campos generales a editar.
    paginaTraduccio: pagina que muestra los campos traducibles a editar.
    paginaTramite: pagina que muestra los campos de un tramite
    
 --%>
<tiles:importAttribute name="title" scope="page" />
<tiles:importAttribute name="alta.title" scope="page" />
<tiles:importAttribute name="modificacion.title" scope="page" />
<tiles:importAttribute name="subtitle" scope="page" />
<tiles:importAttribute name="validateMethod" scope="page" />
<tiles:importAttribute name="listaRelaciones" scope="page" />
<tiles:useAttribute id="action" name="form.action" classname="java.lang.String" scope="page" />
<tiles:useAttribute id="actionBaja" name="form.actionBaja" classname="java.lang.String" scope="page" />
<tiles:useAttribute id="bean" name="form.bean" classname="java.lang.String" scope="page" />
<%
request.setAttribute( "form.beanName", bean );
%>
<tiles:useAttribute name="paginaValues" scope="page" />
<tiles:useAttribute name="paginaTraduccion" scope="page" />
<tiles:useAttribute name="paginaTramite" scope="page" />
<tiles:useAttribute name="botonesTramite" scope="page" />
<html:html locale="true" xhtml="true">
<head>
   <title><bean:message name="title"/></title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href='<html:rewrite page="/css/styleA.css"/>' type="text/css" />
   <script src="<html:rewrite page='/moduls/staticJs.jsp'/>" type="text/javascript"></script>
   <script src="<html:rewrite page='/moduls/funcions.js'/>" type="text/javascript"></script>
   <script type="text/javascript">
   <!--
        function reload(lang) {
            document.forms[0].select.value = lang;
            if ( validar(document.forms[0]) ) {
                document.forms[0].submit();
            }
        }

        function validar(form) {
            return <bean:write name="validateMethod" />(form);
        }
        
        function baja( url )
        {
			document.location.href = url;
        }
        
        <logic:present name="reloadMenu">
            //top.Menu.location.reload(true);
            <bean:define id="urlRecargaMenu">
            	<html:rewrite page="/arbol.do" paramId="id" paramName="nodoId" />
            </bean:define>
            parent.treeframe.location.href = '<bean:write name="urlRecargaMenu" />';
        </logic:present>

        function swichDisplay(name) {
            var elStyle = document.getElementById(name).style;
            if (elStyle.display == 'none') {
                elStyle.display = 'block';
                try { // table-row per els qui ho soportin, els altres (IE) toquen funcionar amb block.
                    elStyle.display = 'table-row';
                } catch (ex) { ; }
            } else {
                elStyle.display = 'none';
            }
         }
   //-->
   </script>
</head>

<body class="ventana">

<!-- Muestra el arbol de navegación en la parte superior de la pantalla-->
<!-- 
<logic:present name="formulario">
    <p class="path">
    <i><bean:message key="formulario.path" /></i>: <b><bean:write name="formulario" /></b>
    <logic:present name="pantalla">
        &gt;&gt; <i><bean:message key="pantalla.path" /></i>: <b><bean:write name="pantalla" /></b>
        <logic:present name="componente">
            &gt;&gt; <i><bean:message key="componente.path" /></i>: <b><bean:write name="componente" /></b>
            <logic:present name="valor">
                &gt;&gt; <i><bean:message key="valorposible.path" /></i>: <b><bean:write name="valor" /></b>
            </logic:present>
        </logic:present>
    </logic:present>
    </p>
</logic:present>
<br />
 -->
<table class="marc">
    <tr><td class="titulo">
        <logic:notPresent name="<%=bean%>" property="values.codigo">
            <bean:message name="alta.title" />
        </logic:notPresent>
        <logic:present name="<%=bean%>" property="values.codigo">
            <bean:message name="modificacion.title" />
        </logic:present>        
    </td></tr>
    <!--<tr><td class="subtitulo"><bean:message name="subtitle" /></td></tr>   -->
</table>

<logic:present name="alert">
<table class="marc">
    <tr><td class="alert"><bean:message key="alert" /></td></tr>
</table>
<br />
</logic:present>

<html:errors/>

    
    <table class="nomarc">
    
	    <html:form action="<%=action%>" styleId="<%=bean%>">
	    <html:hidden property="page" />
	    <html:hidden property="values.codigo" />
	    
	    <logic:notEmpty name="paginaTraduccion">
	        <html:hidden property="lang" />    
	        <html:hidden property="select" />    
	    </logic:notEmpty>
	
		<!-- -->
	    <logic:notEmpty name="paginaTramite">
	        <table class="marc"><tiles:insert attribute="paginaTramite" /></table><br />
	    </logic:notEmpty>
	    
	    <logic:notEmpty name="botonesTramite">
	        <table class="marc"><tiles:insert attribute="botonesTramite" /></table><br />
	    </logic:notEmpty>
	    
	    <logic:notEmpty name="paginaValues">
	        <table class="marc"><tiles:insert attribute="paginaValues" /></table><br />
	    </logic:notEmpty>
	    
	    <logic:notEmpty name="paginaTraduccion">
	        <tiles:insert definition=".langtabs">
	            <tiles:put name="select" beanName="<%=bean%>" beanProperty="lang" />
	        </tiles:insert>        
	        <table class="marc"><tiles:insert attribute="paginaTraduccion" /></table><br />
	    </logic:notEmpty>
	    
       <tr>
           <td align="left">
                <logic:present name="<%=bean%>" property="values.codigo">                	
                	<logic:notPresent name="bloqueado">
	                    <back:accio tipus="modificacio" styleClass="button" onclick="return validar(this.form);" />
						<%						
							if ( !StringUtils.isEmpty( actionBaja.toString() ) ) 
							{
						%>
	                    <bean:define id="mensajeBaja"><bean:message key='messatge.baja' /></bean:define>                                                           
		                    <logic:notPresent name="<%=bean%>" property="tramite.codigo">
		                    	<bean:define id="urlBaja"><html:rewrite page='<%= actionBaja + ".do" %>' paramId="codigo" paramName="<%= bean %>" paramProperty="values.codigo"/></bean:define>
		                    	<button class="button" type="button" onclick="confirmAndForward('<%= StringEscapeUtils.escapeJavaScript( mensajeBaja ) %>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
		                    </logic:notPresent>
		                    <logic:present name="<%=bean%>" property="tramite.codigo">
		                    	<bean:define id="urlBaja"><html:rewrite page='<%= actionBaja + ".do" %>' paramId="codigo" paramName="<%= bean %>" paramProperty="tramite.codigo"/></bean:define>
		                    	<button class="button" type="button" onclick="confirmAndForward('<%= StringEscapeUtils.escapeJavaScript( mensajeBaja ) %>', '<%=urlBaja%>')"><bean:message key="boton.baixa" /></button>
		                    </logic:present>
				        <%
							}
						%>
					</logic:notPresent>					
					<%
						if ( !StringUtils.isEmpty( actionBaja.toString() ) ) 
						{
					%>	                
                    <logic:equal name="bean" value="tramiteVersionForm">
	                	<bean:define id="urlExportacion">
	                		<html:rewrite page="/generar/xml.do" paramId="codigo" paramName="<%= bean %>" paramProperty="values.codigo" />
	                	</bean:define>
	                	<button class="button" type="button" onclick="javascript:document.location.href='<%= urlExportacion %>'"><bean:message key="boton.exportar" /></button>
	                	
	                	<bean:define id="urlBloquear">
	                		<html:rewrite page="/back/tramiteVersion/bloquear.do" paramId="codigo" paramName="<%= bean %>" paramProperty="values.codigo" />
	                	</bean:define>
	                	
	                	<logic:present name="bloqueado">
	                		<logic:empty name="bloqueado">
			                	<button class="button" type="button" onclick="javascript:document.location.href='<%= urlBloquear %>&bloquear=S'"><bean:message key="boton.bloquear" /></button>
	                		</logic:empty>	                	 	
			            </logic:present>
	                	
	                	<logic:notPresent name="bloqueado">
	                		<button class="button" type="button" onclick="javascript:document.location.href='<%= urlBloquear %>&bloquear=N'"><bean:message key="boton.desbloquear" /></button>
			            </logic:notPresent>
                	</logic:equal>
                    <%
						}
					%>                    
                </logic:present>                
                <logic:notPresent name="<%=bean%>" property="values.codigo">
                    <back:accio tipus="alta" styleClass="button" onclick="return validar(this.form);" />
                </logic:notPresent>           
                <html:reset styleClass="button"><bean:message key="boton.reiniciar" /></html:reset>
                <button class="button" type="button" onclick="viewAyudaExpresion()"><bean:message key="boton.ayuda.expresion" /></button>
            </td>
            
			<%
			if ( StringUtils.isEmpty( actionBaja.toString() ) ) 
			{
			%>
            <td align="right">
                <html:cancel styleClass="button" ><bean:message key="boton.cancel" /></html:cancel>
            </td>
            <%
			}
            %>            
        </tr>
        </html:form>
    </table>

<logic:present name="<%=bean%>" property="values.codigo">
    <br />
    <logic:iterate id="element" name="listaRelaciones">
        <tiles:insert beanName="element" flush="false" >
            <tiles:put name="id" beanName="<%=bean%>" beanProperty="values.codigo" />
        </tiles:insert>
    </logic:iterate>
</logic:present>

<bean:define id="pagina" name="<%=bean%>" property="page" type="java.lang.Integer"/>
<!-- XAPUÇA -->
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript
    formName="<%=bean%>"
    dynamicJavascript="true"
    staticJavascript="false"
    page="<%=pagina.intValue()%>"
    htmlComment="true"
    cdata="false"
 />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>
</body>
</html:html>