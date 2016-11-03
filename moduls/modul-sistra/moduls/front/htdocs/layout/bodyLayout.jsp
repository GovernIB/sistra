<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="es.caib.sistra.front.Constants"%>
<%@ page import="es.caib.sistra.model.PasoTramitacion"%>
<%@ page import="es.caib.sistra.front.util.TramiteRequestHelper"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="zonaPasoTramite" name="zonaPasoTramite" type="java.lang.String"/>
<bean:define id="mostrarPasosNav" name="mostrarPasosNav"  type="java.lang.String"/>
<bean:define id="isTramiteReducido" name="<%= Constants.TRAMITE_REDUCIDO_KEY %>"/>
<logic:empty name="tramite">
	<tiles:insert name=".message" />
</logic:empty>
<logic:notEmpty name="tramite">
<bean:define id="step" name="tramite" property="pasoActual" type="java.lang.Integer" />
<bean:define id="steps" name="tramite" property="pasos" type="java.util.ArrayList" />
<%
	PasoTramitacion pasoTramitacion = ( PasoTramitacion ) steps.get( step.intValue() );
	int tipoPaso = pasoTramitacion.getTipoPaso();
	//System.out.println( "Cogiendo paso " + step + "; tipo paso " + tipoPaso );
%>
<% 
String tileStepDefinition = StringUtils.isEmpty ( zonaPasoTramite ) ?  ( ( tipoPaso < 0 ) ? ".step0" : ".step" + tipoPaso ) : zonaPasoTramite;
//System.out.println( "Including " + tileStepDefinition );
//System.out.println( "TILE STEP DEFINITION " + tileStepDefinition );
if ( tipoPaso >= 1 ) 
{
%>
		<logic:notEqual name="isTramiteReducido" value="true">
		<tiles:insert name="stepTabs"/>
		</logic:notEqual>
		<!-- paso en cuestión -->
		<div id="pasoInf">
<% 
}
%>
		<tiles:insert name="message" />
		<tiles:insert name="<%= tileStepDefinition %>"/>
<% 		
if ( tipoPaso >= 1  )
{
%>

	</div><!-- pasoInf -->
	<logic:notEqual name="isTramiteReducido" value="true">			
		<logic:equal name="mostrarPasosNav" value="true"> 
		<tiles:insert name="stepNav"/>
		</logic:equal>
	</logic:notEqual>
<% 
}
%>
</logic:notEmpty>