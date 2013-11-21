<%@ page language="java" contentType="text/html; charset=ISO-8859-1" errorPage="/moduls/errorEnJsp.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.text.DecimalFormat,java.util.Locale,java.text.SimpleDateFormat"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<html:xhtml/>
<bean:define id="urlPago">
        <html:rewrite page="/protected/irAPago.do" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"/>
</bean:define>
<bean:define id="datosPago" name="datospago" type="es.caib.sistra.model.DatosPago" />
<bean:define id="tipoPago" name="irAPagoForm" property="tipoPago" type="java.lang.Character"/>
<bean:define id="pago" name="pago" type="es.caib.sistra.model.DocumentoFront" />

<bean:define id="labelIniciarPago" type="java.lang.String">
	<bean:message key="pago.iniciarPago"/>
</bean:define>

<bean:define id="labelAnularPago" type="java.lang.String">
	<bean:message key="pago.anularPago"/>
</bean:define>

<bean:define id="labelConfirmarPago" type="java.lang.String">
	<bean:message key="pago.confirmarPago"/>
</bean:define>


<%
	String preffixKey = "pago.";
	switch (pago.getEstado()){
		case 'V': preffixKey += "presentacion.";
			break;
		case 'N': preffixKey += "iniciado." ;
			break;
		case 'S': preffixKey += "pagado." ;
			break;
	}
	String suffixKey 		= tipoPago.charValue() == 'T' ? "telematico" : "presencial";
	String keyDatosPago 	= preffixKey + "datospago." + suffixKey;
	String keyInstrucciones = preffixKey + "instrucciones." + suffixKey;	
	String titlePagoKey = "pago.pago.pago" + suffixKey;
	
	// Formateamos importe
	double impDec = Double.parseDouble(datosPago.getImporte()) / 100 ;	
	DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance(new Locale("es"));
	f.setDecimalSeparatorAlwaysShown(true);
	f.setMaximumFractionDigits(2);
	f.setMinimumFractionDigits(2);
	f.setMinimumIntegerDigits(1);
	String importe = f.format(impDec);
	
	// Formateamos fecha
	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");               
     String fechaDevengo = sdf.format( datosPago.getFechaDevengo() );
%>
<script type="text/javascript">
var bloqueador=0;
</script>	
		<logic:present name="urlAcceso">
			<script type="text/javascript">
		<!--	
				// Abrimos ventana plataforma pagos
				ventanaPrueba = window.open("<bean:write name="urlAcceso" filter="false" />");
				if(!ventanaPrueba) bloqueador = 1;
				else bloqueador = 0;
		-->		
			</script>
		</logic:present>
			<h2><bean:message key="<%= titlePagoKey %>"/></h2>
			<script type="text/javascript">
				<!--
				texto = "<p class='error'><bean:message key="pago.instruccionesEmergentes" /></p>";
				if(bloqueador == 1) document.write(texto);
				-->
			</script>
			<p><bean:message key="<%= keyDatosPago %>"/></p>
			<div id="datosPago">
				<strong><bean:message key="pago.pago.datosPago"/></strong>
				<p>					
					<span class="label"><bean:message key="pago.pago.modelo"/></span> <span><bean:write name="datosPago" property="modelo" /></span>
					<span class="label"><bean:message key="pago.pago.concepto"/></span> <span><bean:write name="datosPago" property="concepto" /></span>
					<span class="label"><bean:message key="pago.pago.fechaDevengo"/></span> <span><%=fechaDevengo%></span>
					<span class="label"><bean:message key="pago.pago.importe"/></span><span><%=importe%> &euro;</span>
				</p>
			</div>

			<p><bean:message key="<%= keyInstrucciones %>"/></p>
			
			<html:form action="/protected/iniciarPago">
			<html:hidden name="irAPagoForm" property="ID_INSTANCIA"/>
			<html:hidden name="irAPagoForm" property="instancia" />
			<html:hidden name="irAPagoForm" property="identificador" />
			<html:hidden name="irAPagoForm" property="tipoPago" />
		<logic:equal name="pago" property="estado" value='V'>
			<p class="alerta"><bean:message key="pago.instruccionesModificacion"/></p>			
			<p class="importante"><html:button onclick="this.form.submit();" property="iniciarPago" value="<%=labelIniciarPago%>"/></p>			
		</logic:equal>
		<logic:equal name="pago" property="estado" value='N'>
			<p class="alerta"><bean:message key="pago.iniciado.aviso.telematico"/></p>			
			<p class="importante"><html:button onclick="this.form.action='confirmarPago.do';this.form.submit();" property="confirmarPago" value="<%=labelConfirmarPago%>"/> <html:button onclick="this.form.action='anularPago.do';this.form.submit();" property="anularPago" value="<%=labelAnularPago%>"/></p>
		</logic:equal>
		<logic:equal name="pago" property="estado" value='S'>
			<p class="importante"><html:button onclick="this.form.action='anularPago.do';this.form.submit();" property="anularPago" value="<%=labelAnularPago%>"/></p>
		</logic:equal>
		</html:form>

		<logic:equal name="pago" property="estado" value='V'>	
		<p id="getAdobeReader">
			<bean:message key="pago.informacionPDF"/>
			<br />
			<a target="_blank" href="http://www.adobe.es/products/acrobat/readstep2.html"><bean:message key="pago.urlAdobeReader"/></a>
		</p>
		</logic:equal>
				
		<p class="volver"><html:link action="/protected/irAPaso" paramId="ID_INSTANCIA" paramName="ID_INSTANCIA"><bean:message key="pago.volverListadoTasas"/></html:link></p>
		
		<div class="sep"></div>
		

	