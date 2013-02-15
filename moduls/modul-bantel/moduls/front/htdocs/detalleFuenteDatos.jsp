<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

<script type="text/javascript" src="js/mensaje.js"></script>	
<script type="text/javascript" src="js/fuenteDatos.js"></script>

<bean:define id="fuenteDatos" name="fuenteDatos" type="es.caib.bantel.model.FuenteDatos"/>

<h2><bean:message key="fuenteDatos.titulo" arg0="<%=fuenteDatos.getIdentificador()%>"/></h2>

<br/>

<!--  Resultados búsqueda -->	
<div id="resultatsRecerca">

	<logic:empty name="fuenteDatos" property="campos">
		<p><bean:message key="fuenteDatos.noCampos"/></p>
	</logic:empty>
	
	<logic:notEmpty name="fuenteDatos" property="campos">
		<table cellpadding="8" cellspacing="10" id="tablaResultats">
		<tr>
			<logic:iterate id="campo" name="fuenteDatos" property="campos">
				<th><bean:write name="campo" property="identificador"/></th>								
			</logic:iterate>
			<th width="5%">&nbsp;</th>
		</tr>				
			
			<logic:empty name="fuenteDatos" property="filas">
				<p><bean:message key="fuenteDatos.noFilas"/></p>
			</logic:empty>
			
			<% int numfila = 1;%>
			<logic:iterate id="fila" name="fuenteDatos" property="filas" type="es.caib.bantel.model.FilaFuenteDatos">
				<tr>
					<logic:iterate id="campo" name="fuenteDatos" property="campos" type="es.caib.bantel.model.CampoFuenteDatos">
						<td>
							<bean:define id="valorCampo" value="<%=StringUtils.defaultString(fila.getValorFuenteDatos(campo.getIdentificador()))%>"/>
							<a href="javascript:mostrarCambioValor(true,'<%=fuenteDatos.getIdentificador()%>',<%=numfila%>,'<%=campo.getIdentificador()%>')">
								<div id="f<%=numfila%>-<%=campo.getIdentificador()%>">
								<logic:empty name="valorCampo">
									[ --- ]
								</logic:empty>
								<logic:notEmpty name="valorCampo">
									<bean:write name="valorCampo"/>
								</logic:notEmpty>
								</div>
							</a>							
						</td>			
					</logic:iterate>
						<td>
							<input type="button" onclick='borrarFilaFuenteDatos("<%=fuenteDatos.getIdentificador()%>", <%=numfila%> )'
								value="<bean:message key="fuenteDatos.borrarFila"/>"/>							
						</td>									
				</tr>			
				<% numfila ++;%>
			</logic:iterate>
			
																	
		</table> 
		<p id="barraNav">
			<input type="button" onclick='insertarFilaFuenteDatos("<%=fuenteDatos.getIdentificador()%>")'
				value="<bean:message key="fuenteDatos.nuevaFila"/>"/>				
		</p>
	</logic:notEmpty>
</div>

<!--  capa fondo -->
<div id="fondo"></div>	

 <!--  capa cambio valor -->
 <div id="divCambioValor" class="divCambioValor">	
 	<p>
		<bean:message key="fuenteDatos.nuevoValor"/>
	</p>	
 	<form  class="remarcar opcions">
 		<p>
			<input type="text" id="valorCampo" name="valorCampo" class="pc70" maxlength="500"/>
			<input type="hidden" id="idFuenteDatos" name="idFuenteDatos"/>
			<input type="hidden" id="numFila" name="numFila"/>
			<input type="hidden" id="idCampo" name="idCampo"/>			
		</p> 	
		<p class="botonera">
			<input type="button" onclick="realizarCambioCampo();" value="<bean:message key="fuenteDatos.boton.aceptar"/>"/>			
		</p>
		<p>
			<a href="javascript:mostrarCambioValor(false);"><bean:message key="fuenteDatos.boton.cancelar"/></a>
		</p>
 	</form> 	
</div>	