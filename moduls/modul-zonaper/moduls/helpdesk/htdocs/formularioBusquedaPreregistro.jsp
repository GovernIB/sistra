<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="es.caib.zonaper.helpdesk.front.Constants" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>

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
		
		function esborrarDia(valor) {
			document.getElementById(valor).value = '';
		}
	
		function validaFormulario( form )
	 	{

			/* Fecha Inicial*/
			
			if(isEmptyObject(form.fechaInicial))
			{ 
				alert("<bean:message key='errors.required' arg0='Dia inicial'/>");
				return false;
			}
			
			/* Fecha Final*/
			if(isEmptyObject(form.fechaFinal))
			{ 
				alert("<bean:message key='errors.required' arg0='Dia final'/>");
				return false;
			}

			/* Nif: si no esta vacio validamos nif */
			if(!isEmptyObject(form.nif))
			{ 
				if (!validaNIF(form.nif.value) && !validaCIF(form.nif.value) && !validaNIE(form.nif.value) && !validaPasaporte(form.nif.value)) {
					alert("<bean:message key='error.nifValido'/>");
					return false;
				}
			}
    		
    		return true;
    		
	    }
	    
		
	    
	-->
</script>

	<!-- ajuda boto -->
	<!-- ajuda boto -->
	<button id="ajudaBoto" type="button" title="<bean:message key="ayuda.boton.titulo"/>"><img src="imgs/botons/ajuda.gif" alt="" /> <bean:message key="ayuda.boton"/></button>

	<!-- titol -->
	<h1 class="ajuda"><bean:message key="formularioBusqueda.preregistro"/></h1>
	<div id="titolOmbra"></div>
	<!-- /titol -->
	<!-- ajuda -->
	<div id="ajuda">
		<bean:message key="tabs.preregistro.info"/>
	</div>	
	
	<!-- continguts -->

	<div class="continguts">
		<!-- form reserca -->
		<html:form action="busquedaTramitesPreregistro" styleClass="formulari" onsubmit="return validaFormulario(this);">
			<p>
				<label for="modelo">
					<bean:message key="formularioBusqueda.tramite"/>: 
			    	<html:select property="modelo" styleClass="selectTramites">
			    		<html:option value="0"><bean:message key="formularioBusqueda.tramite.todos"/></html:option>
			   			<html:options collection="tramites" property="key" labelProperty="value" />
			    	</html:select>
				</label>
			</p>
			<p>
				<label for="caducidad">
					<bean:message key="formularioBusqueda.caducidad"/>: 
			    	<html:select property="caducidad">
			    		<html:option value="T"><bean:message key="formularioBusqueda.caducidad.todos"/></html:option>
			    		<html:option value="S"><bean:message key="formularioBusqueda.caducidad.caducados"/></html:option>
			    		<html:option value="N"><bean:message key="formularioBusqueda.caducidad.noCaducados"/></html:option>
			    	</html:select>
				</label>  
				<label for="estadoConfirmacion">
					<bean:message key="formularioBusqueda.estadoConfirmacion"/>: 
			    	<html:select property="estadoConfirmacion">
			    		<html:option value="T"><bean:message key="formularioBusqueda.estadoConfirmacion.todos"/></html:option>
			    		<html:option value="S"><bean:message key="formularioBusqueda.estadoConfirmacion.confirmados"/></html:option>
			    		<html:option value="N"><bean:message key="formularioBusqueda.estadoConfirmacion.noConfirmados"/></html:option>
			    	</html:select>
				</label>
		
				<label for="tipo">
					<bean:message key="formularioBusqueda.tipo"/>: 
			    	<html:select property="tipo">
			    		<html:option value="T"><bean:message key="formularioBusqueda.tipo.todos"/></html:option>
			    		<html:option value="N"><bean:message key="formularioBusqueda.tipo.bandeja"/></html:option>
			    		<html:option value="P"><bean:message key="formularioBusqueda.tipo.registro"/></html:option>
			    	</html:select>
				</label>
				<label for="nivelAutenticacion">
					<bean:message key="formularioBusqueda.nivelAutencicacion"/>:
			    	<html:select property="nivel">
			    		<html:option value="T"><bean:message key="formularioBusqueda.todos"/></html:option>
			    		<html:option value="C"><bean:message key="formularioBusqueda.nivel.certificado"/></html:option>
			    		<html:option value="U"><bean:message key="formularioBusqueda.nivel.usuario"/></html:option>
			    		<html:option value="A"><bean:message key="formularioBusqueda.nivel.anonimo"/></html:option>
			    	</html:select>
				</label>        
			</p>
			<div id="horario">
			<p>
				<label for="diaInicial" class="separacio">
					<bean:message key="formularioBusqueda.diaInicial"/>: 
					<html:text property="fechaInicial" styleId="fechaInicial" readonly="true" size="10" />
					 <button id="dia_calendario_inicial" type="button" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" title="Esborrar dia" onclick="esborrarDia('fechaInicial');"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
				</label> 
				<script type="text/javascript">
				<!--
					Calendar.setup({
							inputField: "fechaInicial", ifFormat: "%d/%m/%Y", button: "dia_calendario_inicial", weekNumbers: false
					});
				-->
				</script>
				<label for="diaFinal" class="separacio">
					<bean:message key="formularioBusqueda.diaFinal"/>: 
					<html:text property="fechaFinal" styleId="fechaFinal" readonly="true" size="10" />
					 <button id="dia_calendario_final" type="button" title="Mostrar calendari"><img src="imgs/botons/calendari.gif" alt="" /></button> <button type="button" title="Esborrar dia" onclick="esborrarDia('fechaFinal');"><img src="imgs/botons/calendari_borrar.gif" alt="" /></button>
				</label> 
				<script type="text/javascript">
				<!--
					Calendar.setup({
							inputField: "fechaFinal", ifFormat: "%d/%m/%Y", button: "dia_calendario_final", weekNumbers: false
					});
				-->
				</script>
				<label for="nif">
					<bean:message key="formularioBusqueda.nif"/>: 
			    	<html:text property="nif"/>
				</label>
			</p>
			</div>
			<p>
					<bean:define id="botonEnviar" type="java.lang.String">
	                <bean:message key="boton.enviar" />
               </bean:define>
               <html:submit><%=botonEnviar%></html:submit>
			</p>
		</html:form>	
		
		<html:errors/>
		<!-- /form reserca -->
	</div>
	


