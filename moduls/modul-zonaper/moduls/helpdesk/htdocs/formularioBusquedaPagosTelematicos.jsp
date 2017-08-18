<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

	<!-- calendari -->
<link href="estilos/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/lang/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/calendario.js"></script>
<script type="text/javascript" src="js/fechas.js"></script>
<script type="text/javascript" src="js/formularioBusqueda.js" charset="utf-8"></script>
<script type="text/javascript" src="js/ajuda.js"></script>
<script type="text/javascript">
	<!--
	addEvent(window,'load',ajudaIniciar,true);
	-->
</script>

<script type="text/javascript">
	<!--
		function formNivellAutenticacio(valor) {
			lCP = document.getElementById('labelCP');
			lN = document.getElementById('labelN');
			h = document.getElementById('horario');
			if(valor == '<%=Character.toString(Constants.MODO_AUTENTICACION_ANONIMO)%>') {
				lCP.style.display = 'inline';
				lN.style.display = 'none';
				h.style.display = 'none';
			} else if((valor == '<%=Character.toString(Constants.MODO_AUTENTICACION_CERTIFICADO)%>') || 
			          (valor == '<%=Character.toString(Constants.MODO_AUTENTICACION_USUARIO_PWD)%>')) {
				lCP.style.display = 'none';
				lN.style.display = 'inline';
				h.style.display = 'inline';
			} else {
				lCP.style.display = 'none';
				lN.style.display = 'none';
				h.style.display = 'none';
			}
		}
		function esborrarDia() {
			document.getElementById('fecha').value = '';
		}
		

		
		function validaFormulario( form )
	 	{
	 	
	 		var res = validaClavePersistenciaNif(form);
	 		if(res != "")
	 		{
	 			if(res == "error.clavePersistencia") alert("<bean:message key='error.clavePersistencia'/>");
	 			if(res == "error.clavePersistenciaValida") alert("<bean:message key='error.clavePersistenciaValida'/>");
	 			if(res == "error.nif") alert("<bean:message key='error.nif'/>");
	 			if(res == "error.nifValido") alert("<bean:message key='error.nifValido'/>");
	 			return false;
	 		}
			
			// Limpiamos espacios en blanco
			TrimField('horaInicial');
			TrimField('minInicial');
			TrimField('horaFinal');
			TrimField('minFinal');
			
			var sn = document.getElementById('nivelAutenticacion');
	    	var nivel = sn.options[sn.selectedIndex].value;
	    	if (nivel == 'A') return true;
			
			
			/* Fecha */
			if(isEmptyObject(form.fecha))
			{ 
				alert("<bean:message key='errors.required' arg0='fecha'/>");
				return false;
			}
			/* HoraInicial */
			if(isEmptyObject(form.horaInicial))
			{ 
				alert("<bean:message key='errors.required' arg0='horaInicial'/>");
				return false;
			}
			if(minlength(form.horaInicial.value,2))
			{
				alert("<bean:message key='errors.minlength' arg0='horaInicial' arg1='2'/>");
				return false;
			}
			if(!esHora(form.horaInicial.value))
			{
				alert("<bean:message key='errors.hora' arg0='Inicial'/>");
				return false;
			}
			/* MinInicial */
			if(isEmptyObject(form.minInicial))
			{ 
				alert("<bean:message key='errors.required' arg0='minInicial'/>");
				return false;
			}
			if(minlength(form.minInicial.value,2))
			{
				alert("<bean:message key='errors.minlength' arg0='minInicial' arg1='2'/>");
				return false;
			}
			if(!esMinutos(form.minInicial.value))
			{
				alert("<bean:message key='errors.minutos' arg0='Inicial'/>");
				return false;
			}
			/* HoraFinal */
			if(isEmptyObject(form.horaFinal))
			{ 
				alert("<bean:message key='errors.required' arg0='horaFinal'/>");
				return false;
			}
			if(minlength(form.horaFinal.value,2))
			{
				alert("<bean:message key='errors.minlength' arg0='horaFinal' arg1='2'/>");
				return false;
			}
			if(!esHora(form.horaFinal.value))
			{
				alert("<bean:message key='errors.hora' arg0='Final'/>");
				return false;
			}
			/* MinFinal */
			if(isEmptyObject(form.minFinal))
			{ 
				alert("<bean:message key='errors.required' arg0='minFinal'/>");
				return false;
			}
			if(minlength(form.minFinal.value,2))
			{
				alert("<bean:message key='errors.minlength' arg0='minFinal' arg1='2'/>");
				return false;
			}
			if(!esMinutos(form.minFinal.value))
			{
				alert("<bean:message key='errors.minutos' arg0='Final'/>");
				return false;
			}
			return true;
    
	    }

		
	    
	-->
</script>
	<!-- ajuda boto -->
	<button id="ajudaBoto" type="button" title="<bean:message key="ayuda.boton.titulo"/>"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="ayuda.boton"/></button>


	<!-- titol -->
	<h1 class="ajuda"><bean:message key="formularioBusqueda.pagosTelematicos" /></h1>
	<div id="titolOmbra"></div>
	<!-- /titol -->
	<!-- ajuda -->
	<div id="ajuda">
		<bean:message key="tabs.pagosTelematicos.info"/>
	</div>	
	
	<!-- continguts -->

	<div class="continguts">
		<!-- form reserca -->
		<html:form action="busquedaPagosTelematicos" styleClass="formulari" onsubmit="return validaFormulario(this);">
			<p>
				<label for="nivelAutenticacion">
					<bean:message key="formularioBusqueda.nivelAutencicacion"/>
					<html:select property="nivelAutenticacion"   styleId="nivelAutenticacion"  property="nivelAutenticacion"   onchange="formNivellAutenticacio(this.value);">
						<html:option value="<%=Character.toString(Constants.MODO_AUTENTICACION_CERTIFICADO)%>" ><bean:message key="formularioBusqueda.nivel.certificado"/></html:option>
						<html:option value="<%=Character.toString(Constants.MODO_AUTENTICACION_USUARIO_PWD)%>" ><bean:message key="formularioBusqueda.nivel.usuario"/></html:option>
						<html:option value="<%=Character.toString(Constants.MODO_AUTENTICACION_ANONIMO)%>" ><bean:message key="formularioBusqueda.nivel.anonimo"/></html:option>
					</html:select>
				</label>

				<label id="labelCP" for="clavePersistencia" 
				<logic:present name="nivel">
					<logic:equal name="nivel" value="<%=Character.toString(Constants.MODO_AUTENTICACION_CERTIFICADO)%>"> 
					class="noVisible"
					</logic:equal>
					<logic:equal name="nivel" value="<%=Character.toString(Constants.MODO_AUTENTICACION_USUARIO_PWD)%>"> 
					class="noVisible"
					</logic:equal>
				</logic:present>
				<logic:notPresent name="nivel">
					class="noVisible"
				</logic:notPresent>
				>
					<bean:message key="formularioBusqueda.clavePersistencia"/>:
					<html:text property="clavePersistencia"  styleId="clavePersistencia"  property="clavePersistencia"  size="50"/>
				</label>
				<label id="labelN" for="nif" 
				<logic:present name="nivel">
					<logic:equal name="nivel" value="<%=Character.toString(Constants.MODO_AUTENTICACION_ANONIMO)%>"> 
					class="noVisible"
					</logic:equal>
				</logic:present>
				>
					<bean:message key="formularioBusqueda.nif"/>:
					<html:text property="usuarioNif"  styleId="usuarioNif" size="23" />
				</label>
			</p>
			<div id="horario"
				<logic:present name="nivel">
					<logic:equal name="nivel" value="<%=Character.toString(Constants.MODO_AUTENTICACION_ANONIMO)%>"> 
					class="noVisible"
					</logic:equal>
				</logic:present>
			>
			<p>
				<label for="dia" class="separacio">
					<bean:message key="formularioBusqueda.dia"/>: 
					<html:text property="fecha" styleId="fecha" readonly="true" size="10" />
					 <button id="dia_calendario" type="button" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" title="Esborrar dia" onclick="esborrarDia();"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
				</label> 
				<script type="text/javascript">
				<!--
					Calendar.setup({
							inputField: "fecha", ifFormat: "%d/%m/%Y", button: "dia_calendario", weekNumbers: false
					});
				-->
				</script>
				<bean:message key="formularioBusqueda.horaInicial"/>:
				<html:text property="horaInicial"  styleId="horaInicial"  size="1" maxlength="2" styleClass="hora" /> : 
				<label class="separacio">
					<html:text property="minInicial"  styleId="minInicial"  size="1" maxlength="2" /> h.
				</label>
				<bean:message key="formularioBusqueda.horaFinal"/>:
				<html:text property="horaFinal"  styleId="horaFinal"  size="1" maxlength="2" styleClass="hora" /> : 
				<html:text property="minFinal" styleId="minFinal"   size="1" maxlength="2"  /> h.
			</p>
			</div>
			<p>
					<bean:define id="botonEnviar" type="java.lang.String">
	                <bean:message key="boton.enviar" />
               </bean:define>
               <html:submit><%=botonEnviar%></html:submit>
			</p>
		</html:form>	
		<!-- /form reserca -->
	</div>

