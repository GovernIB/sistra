<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
	<!-- enlaces anterior siguiente -->
<logic:notEmpty name="ID_INSTANCIA">		
		<div class="barraAzul"></div>
		
		<div class="imc-navegacio imc-nav" id="imc-navegacio">
			<ul>
				<!--  Vacio -->
				<li class="imc-nav-desa">
					<span>&nbsp;</span>
				</li>
				
				<!--  Paso anterior -->
				<logic:equal name="pasoAnterior" value="true">
					<li class="imc-nav-anterior">
						<html:link styleId="pasoAnterior" styleClass="imc-nav-anterior" action="/protected/anteriorPaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" ><bean:message key="stepNav.textoEnlaceAnterior" /></html:link>
					</li>					
				</logic:equal>
				<logic:equal name="pasoAnterior" value="false">
					<li class="imc-nav-anterior">
						<span>&nbsp;</span>
					</li>
				</logic:equal>
				
				<!--  Paso siguiente -->
				<logic:equal name="pasoSiguiente" value="true">
					<li class="imc-nav-seguent">
						<html:link styleId="pasoSiguiente" styleClass="imc-nav-seguent" action="/protected/siguientePaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" ><bean:message key="stepNav.textoEnlaceSiguiente" /></html:link>
					</li>
				</logic:equal>
				<logic:equal name="pasoSiguiente" value="false">
					<li class="imc-nav-seguent">						
						<span>&nbsp;</span>
					</li>
				</logic:equal>
				
				<!--  Vacio -->
				<li class="imc-nav-desa">
					<span>&nbsp;</span>
				</li>
				
				
			</ul>
		</div>
</logic:notEmpty>