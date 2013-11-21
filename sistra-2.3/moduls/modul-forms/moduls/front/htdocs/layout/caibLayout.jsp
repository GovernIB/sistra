<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested" %>
<%@ page import="org.ibit.rol.form.model.Formulario"%>
<bean:define id="pathIconografia" name="pathIconografia" scope="request"/>
<bean:define id="securePath" name="securePath" scope="request"/>
<html:html xhtml="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><bean:message  bundle="caibMessages" key="main.title"/></title>
        <link rel="stylesheet" href="<html:rewrite page='<%="/estilo_caib/css/forms-0.0.2.css" %>'/>" type="text/css" />
        <logic:notEmpty name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom">
			<link href="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlCssCustom" />" rel="stylesheet" type="text/css" />
		</logic:notEmpty>
        <script type="text/javascript" src="<html:rewrite page='/js/v0/MM_functions.js' />"></script>
        <script type="text/javascript" src="<html:rewrite page='/js/v0/iframe_functions.js' />"></script>
        <script type="text/javascript">
        <!--
            // Variable global que servirá para saber si la pagina se ha terminado de cargar.
            var bLoaded = false;

            function doLoad() {
                if (typeof loadHook != 'undefined') { // Si existe la funcion loadHook ejecutarla.
                    loadHook();
                }
                
                // La pagina ha terminado de cargar.
                bLoaded = true;
                
                // està dins d'un iframe?
				if(top.window.location != self.window.location) {
					contenidor_H = document.getElementById("contenidor").offsetHeight;
					top.window.document.getElementById("frm").style.height = parseInt((contenidor_H/14.5), 10) + "em";
				}
            }
						
						/* para mantener la ayuda en pantalla */
						function reposicionaMenu(){
							//IE6 in non-quirks doesnt get document.body.scrollTop:
							var pos = (document.body.scrollTop)?document.body.scrollTop:document.documentElement.scrollTop;
							ayuda_posX = saberPosX(document.getElementById("ayuda"));
							ayuda_posY = saberPosY(document.getElementById("ayuda"));
							menu_posY = saberPosY(document.getElementById("menu"));
							if(pos > menu_posY) {
								document.getElementById("ayuda").style.top = parseInt(pos - menu_posY) + "px";
							}
						}
						
						function saberPosX(obj) {
							var curleft = 0;
							if (obj.offsetParent) {
								while (obj.offsetParent) {
									curleft += obj.offsetLeft;
									obj = obj.offsetParent;
								}
							} else if (obj.x) {
								curleft += obj.x;
							}
							return curleft;
						}
						
						function saberPosY(obj) {
							var curtop = 0;
							if (obj.offsetParent) {
								while (obj.offsetParent) {
									curtop += obj.offsetTop;
									obj = obj.offsetParent;
								}
							} else if (obj.y) {
								curtop += obj.y;
							}
							return curtop;
						}
						
						function ayudaScroll(){
							window.onscroll = reposicionaMenu;
						}
						/* fin - para mantener la ayuda en pantalla */
						
        //-->
        </script>
    </head>
    <body onload="doLoad();ayudaScroll()">
        <div style='visibility:hidden;position:absolute'>
			Version :<%=org.ibit.rol.form.front.util.Util.getVersion()%>
			<%
			 Formulario formul = (Formulario) request.getAttribute("formulario");
			 if ( formul != null){
			%>		 
			Tag Cuaderno Carga:  <%=formul.getCuadernoCargaTag()%>
			Fecha exportación xml: <%=es.caib.util.StringUtil.timestampACadena(formul.getFechaExportacion()) %>		  
			<%
			 }
			%>
		</div>
    <div id="contenidor">
        <!-- capçal -->
        <logic:equal name="<%=org.ibit.rol.form.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
        <div id="cap">
	        	<img class="logo" src="<bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="urlLogo"/>" alt="Logo <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>" />		    	
        </div>
		</logic:equal>        
				<!-- titol -->
				<p id="titolAplicacio">
				<logic:notEmpty name="propiedadesForm">
                    <bean:write name="propiedadesForm" property="aplicacion"/>
                </logic:notEmpty>
				</p>
				<!-- titol 
		    <h1 class="document">
		    	<logic:notEmpty name="propiedadesForm">
                    <bean:write name="propiedadesForm" property="tramite"/>
                </logic:notEmpty>
		    </h1>
		    -->
				<!-- continguts -->
        <div id="continguts">
            <div id="formularioFORM">
                <!-- dades del trámit telemàtic -->
                <logic:notEmpty name="propiedadesForm">
                    <h2>
                        <logic:notEmpty name="propiedadesForm">
		                    <bean:write name="propiedadesForm" property="formulario"/>
		                </logic:notEmpty>
                    </h2>
                </logic:notEmpty>
                <table id="centrar" cellpadding="0" cellspacing="0">
								<tr>
									<td align="center" style="vertical-align: middle;">
										<div id="general">
											<table id="cuerpo" cellpadding="0" cellspacing="0">
											<tr>
												<td id="menu" align="left">
													<div id="ayuda">
														<br />
														<div id="nombre_campo" style="display: none;"></div>
														<div id="ayuda_campo" style="display: none;"></div>
														<br />
													</div>
												</td>
												<td id="canal" align="left">
													<div id="contenido">
														<h3><tiles:getAsString name="title" /></h3>
														<tiles:insert attribute="contenido"/>
													</div>
												</td>
											</tr>
											</table>
											<!--<div id="bajos">&nbsp;</div>-->
										</div>
									</td>
								</tr>
                </table>
								<table id="botoneraForms" cellpadding="0" cellspacing="3">
								<tr>
									<td id="abandonar">
										<img src="estilo_caib/images/abandonar.gif" alt="" /> <a href="javascript:discard()" title="<bean:message bundle="caibMessages" key="boton.cancelar"/>" onfocus="this.blur()"><bean:message bundle="caibMessages" key="boton.cancelar"/></a>
									</td>
									<td id="pantallaAnterior">
										<logic:equal name="backButton" value="true">
											<img src="estilo_caib/images/izquierda.gif" alt="" /> <a href="javascript:back()" title="<bean:message bundle="caibMessages" key="boton.back"/>" onfocus="this.blur()"><bean:message bundle="caibMessages" key="boton.backText"/></a>
										</logic:equal>
									</td>
									<td id="pantallaSiguiente">
										<logic:equal name="nextButton" value="true">
											<a href="javascript:next()" title="<bean:message bundle="caibMessages" key="boton.next"/>" onfocus="this.blur()"><bean:message bundle="caibMessages" key="boton.nextText"/></a> <img src="estilo_caib/images/derecha.gif" alt="" />
										</logic:equal>
									</td>
									<td id="terminarTramite">
										<logic:equal name="endButton" value="true">
											<a href="javascript:next()" title="<bean:message bundle="caibMessages" key="boton.terminar"/>" onfocus="this.blur()"><bean:message bundle="caibMessages" key="boton.terminar"/></a> <img src="estilo_caib/images/terminar.gif" alt="" />
										</logic:equal>
										<logic:equal name="endButton" value="false">
											&nbsp;
										</logic:equal>
									</td>
								</tr>
								</table>
            <!-- FORM fin -->
        </div>
    </div>
    <!-- peu -->
    <logic:equal name="<%=org.ibit.rol.form.front.Constants.MOSTRAR_EN_IFRAME%>" value="false">
		<div id="peu">
			&copy; <bean:write name="<%=org.ibit.rol.form.front.Constants.ORGANISMO_INFO_KEY%>" property="nombre"/>				
		</div>
	</logic:equal>		
</div>
</body>
</html:html>