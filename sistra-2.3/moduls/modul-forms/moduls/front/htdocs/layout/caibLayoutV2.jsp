<!doctype html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested" %>
<%@ page import="org.ibit.rol.form.model.Formulario,org.apache.commons.lang.StringUtils"%>

<bean:define id="locale" name="<%=Globals.LOCALE_KEY%>" scope="Session" type="java.util.Locale"/>

<!--  Retorno pantalla detalle -->
<logic:present name="listaelementos@retorno" scope="request">
	<html lang="<%=locale.getLanguage()%>_<%=locale.getLanguage().toUpperCase()%>">
	<head>
		<script type="text/javascript">
	    <!--
			parent.location="ver.do?ID_INSTANCIA=<%=request.getAttribute("ID_INSTANCIA")%>";					
		// -->
	    </script>
	</head>
	<body>
	</body>
	</html> 
</logic:present>

<!--  Pagina normal / pagina detalle -->
<logic:notPresent name="listaelementos@retorno" scope="request">

<html lang="<%=locale.getLanguage()%>_<%=locale.getLanguage().toUpperCase()%>">

<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9" />
	
	<title><bean:message  bundle="caibMessages" key="main.title"/></title>
	        
	<!-- 
	<link href="imgs/favicon.ico" type="image/x-icon" rel="shortcut icon" />
	 -->
	
	<link rel="stylesheet" media="screen" href="<html:rewrite page='/estilo_caibV2/css/ui-lightness/jquery-ui-1.10.3.custom.css'/>">
	<link rel="stylesheet" media="screen" href="<html:rewrite page='/estilo_caibV2/css/imc-forms.css'/>"> 
	<link rel="stylesheet" media="screen" href="<html:rewrite page='/estilo_caibV2/css/imc-select.css'/>">
	<link rel="stylesheet" media="screen" href="<html:rewrite page='/estilo_caibV2/css/imc-animate.css'/>">	
	
	
	<logic:present name="pantallaDetalle">
		<logic:equal name="pantallaDetalle" value="true">
			<link rel="stylesheet" media="screen" href="<html:rewrite page='/estilo_caibV2/css/imc-forms-taula-detall.css'/>">			
		</logic:equal>		
	</logic:present>	
	
	<logic:notEmpty name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
		<link href="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
	</logic:notEmpty>
	
	<!--[if lt IE 9]>
	<script src="js/html5.js"></script>
	<link rel="stylesheet" media="screen"  href="<html:rewrite page='/estilo_caibV2/css/imc-ie8.css'/>">
	<![endif]-->
	
	<script src="<html:rewrite page='/js/v2/utils/modernizr-imc-0.3.js'/>"></script>
	<script src="<html:rewrite page='/js/v2/utils/jquery-1.8.3.min.js'/>"></script>
	<script src="<html:rewrite page='/js/v2/utils/jquery-ui-1.10.3.custom.min.js'/>"></script>
	<script src="<html:rewrite page='/js/v2/utils/jquery-maskedinput.min.js'/>"></script>
	<script src="<html:rewrite page='/js/v2/utils/jshashtable-3.0.js'/>"></script>
	<script src="<html:rewrite page='/js/v2/utils/jquery-numberformatter-1.2.4.min.js'/>"></script>
	<script src="<html:rewrite page='<%="/js/v2/literals/jquery-imc-literals-calendari-" + locale.getLanguage() + "_" + locale.getLanguage().toUpperCase() + ".js"%>'/>"></script>
	<script src="<html:rewrite page='<%="/js/v2/literals/vars-imc-literals-" + locale.getLanguage() + "_" + locale.getLanguage().toUpperCase() + ".js"%>'/>"></script>	
	<script src="<html:rewrite page='/js/v2/jquery-imc-comuns.js'/>"></script>
	<script src="<html:rewrite page='/js/v2/jquery-imc-forms-funcions.js'/>"></script>	
		
	<logic:present name="pantalla">
		<script src="<html:rewrite page='/js/v2/xmlhttp.js'/>"></script>		
		<%@include file="../js/v2/logicaPantalla.jsp"%>	
	</logic:present>
	
	<!-- inicia! -->
	<script src="<html:rewrite page='/js/v2/jquery-imc-forms-inicia.js'/>"></script>
	
		
</head>

<body>
	<!-- contenidor -->
	<div id="imc-contenidor">
	
		<header id="imc-cap" class="imc-cap">
			<img src="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" 
				 alt="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />
			<h1>
				<logic:notEmpty name="propiedadesForm">
                    <bean:write name="propiedadesForm" property="aplicacion"/>
                </logic:notEmpty>
            </h1>
            <div class="imc-usuari">
            	<p>
            		<logic:notEmpty name="propiedadesForm" property="usuario">
            		<bean:message bundle="caibMessages" key="cabecera.usuario"/>: <strong><bean:write name="propiedadesForm" property="usuario"/></strong>
            		</logic:notEmpty>	
            		<logic:notEmpty name="propiedadesForm" property="claveTramitacion">
            		<bean:message bundle="caibMessages" key="cabecera.claveTramitacion"/>: <strong><bean:write name="propiedadesForm" property="claveTramitacion"/></strong>
            		</logic:notEmpty>
				</p>
				
        		
				<!-- 
				<ul>
					<li><a class="imc-usuari-guarda" tabindex="0" href="javascript:;">Guarda la clau</a></li>
					<li><a class="imc-usuari-elimina" tabindex="0" href="javascript:;">Elimina el tràmit</a></li>
				</ul>
				 -->
			</div>
		</header>
		
		<h2>
			<logic:notEmpty name="propiedadesForm">                              
                 <bean:write name="propiedadesForm" property="tramite"/>
        	</logic:notEmpty>
        </h2>
       
       
       
		<logic:equal name="propiedadesForm" property="circuitoReducido" value="false">
		<bean:define id="pasosTramiteStr" name="propiedadesForm" property="pasosTramite" type="java.lang.String"/>
		<bean:define id="pasoActual" name="propiedadesForm" property="pasoActual" type="java.lang.String"/>
	    <header class="imc-pasos" id="imc-pasos">
			<ul>
				<%
					java.util.List pasosList = es.caib.util.StringUtil.deserializarList(pasosTramiteStr);
					for (int i = 0; i < pasosList.size(); i++){
						String classStyle = "";
						if ( (i+"").equals(pasoActual)) {
							classStyle = "class=\"imc-pasos-seleccionat\"";
						} else if (i == (pasosList.size() - 1) ) {
							classStyle = "class=\"imc-pasos-ultim\"";
						}
				%>		
						<li <%=classStyle%>>Paso <%=i + 1%><br><%=pasosList.get(i)%></li>
				<%				
					}
				%>				
			</ul>
		</header>
		</logic:equal>
        
        <header class="imc-formulari-titol imc-fo-ti" id="imc-formulari-titol">
			<div class="imc-fo-ti-text">
				<p><bean:message bundle="caibMessages" key="cabecera.formulario"/></p>
				<h3>
					<logic:notEmpty name="propiedadesForm">                              
                 		<bean:write name="propiedadesForm" property="formulario"/>
        			</logic:notEmpty>
        		</h3>
			</div>
			<!-- 
			<p class="imc-fo-ti-paginacio"><span>Página 9 de 9</span></p>
			 -->
			 <logic:equal name="<%=org.ibit.rol.form.front.Constants.AYUDA_ACTIVADA_KEY%>" value="true" scope="session">
				 <div id="imc-ajuda" class="imc-ajuda imc-ajuda-activada">
					<p><bean:message bundle="caibMessages" key="cabecera.ayudaActivada"/></p>
					<a id="imc-bt-ajuda" class="imc-destacat"><bean:message bundle="caibMessages" key="cabecera.ayudaDesactivar"/></a>
				</div>
			 </logic:equal>
			 <logic:notEqual name="<%=org.ibit.rol.form.front.Constants.AYUDA_ACTIVADA_KEY%>" value="true" scope="session">
				 <div id="imc-ajuda" class="imc-ajuda imc-ajuda-desactivada">
					<p><bean:message bundle="caibMessages" key="cabecera.ayudaDesactivada"/></p>
					<a id="imc-bt-ajuda" class="imc-destacat"><bean:message bundle="caibMessages" key="cabecera.ayudaActivar"/></a>
				</div>
			 </logic:notEqual>			
		</header>
        
		<logic:notEmpty name="<%= Globals.ERROR_KEY %>">
		<div class="imc-errores">
			<p><bean:message bundle="caibMessages" key="errores.titulo"/></p>
			<ul>
				<html:messages id="error">
					<li><bean:write name="error"/></li> 
				</html:messages>		
			</ul>
		</div>
		</logic:notEmpty>
			
		<tiles:insert attribute="contenido"/>
				
	
		<!-- PIE -->
		<logic:equal name="<%=org.ibit.rol.form.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
		<aside>
			<p class="imc-govern">&copy; <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/></p>
			<address>
				<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="pieContactoHTML" filter="false"/>				
			</address>
			<!--  
			<p class="imc-ajuda-suport">¿Necesita Ayuda? Contacte con el <a href="#">equipo de soporte</a>.</p>
			 -->			 
			<logic:present name="pantalla">				
				<bean:define id="urlLog" type="java.lang.String">
				        <html:rewrite page="/logScript.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
				</bean:define>
				 <% if (org.ibit.rol.form.front.util.Util.permitirDebugScript())  { %>
					<input type="button" onclick="obrir('<%=urlLog%>','log',600,600)" value="Debug script" />
				<% } %>
			</logic:present>
		</aside>
		</logic:equal>
	
	</div>
	<!-- /contenidor -->

</body>

</html>

</logic:notPresent>