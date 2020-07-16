<%@ page import="org.ibit.rol.form.model.TextBox,
                 org.ibit.rol.form.model.ComboBox,
                 org.ibit.rol.form.model.RadioButton,
                 org.ibit.rol.form.model.CheckBox,
                 org.ibit.rol.form.model.FileBox,
                 org.ibit.rol.form.model.ListBox,
                 org.ibit.rol.form.model.TreeBox,
                 org.ibit.rol.form.model.ListaElementos,
                 org.ibit.rol.form.model.Label,
                 org.ibit.rol.form.model.Campo,
                 org.apache.struts.taglib.html.Constants,
                 org.apache.struts.Globals,
                 org.ibit.rol.form.front.util.JSUtil,
                 org.ibit.rol.form.persistence.util.ScriptUtil"%>

<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<bean:define id="securePath" name="securePath" scope="request"/>
<bean:define id="sufijoModoFuncionamiento" name="sufijoModoFuncionamiento" scope="request" type="java.lang.String"/>

<script type="text/javascript">
<!--
	// Modo funcionamiento / Version / Tag cuaderno carga / Fecha exportacion xml
	var MODO_FUNCIONAMIENTO = '<bean:write name="sufijoModoFuncionamiento"/>';
	var VERSION = '<%=org.ibit.rol.form.front.util.Util.getVersion()%>';
	var TAG_CUADERNO_CARGA = '<bean:write name="formulario" property="cuadernoCargaTag"/>';
	var FECHA_EXPORTACION_XML = '<bean:write name="formulario" property="fechaExportacion"  format="dd/MM/yyyy '-' HH:mm:ss"/>';

	// Mensaje enviando
	var TXT_MSG_ENVIANDO = "<bean:message  bundle="caibMessages" key="enviando.mensaje"/>";

	// Formulario y campo para onFieldChange
	var v_form,v_fieldName;

	// Dia actual
	var AVUI_DIA = "<%=java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)%>";
	var AVUI_MES = "<%=java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1%>";
	var AVUI_ANY = "<%=java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)%>";


	// --------------------- Funciones navegacion ----------------------------------------------
	// Prevenimos doble click en enlaces
	var bDobleClick=false;

	function back() {

		// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
		if (!bLoaded) return;

		// Prevenimos doble click
		if (bDobleClick) return;
		bDobleClick=true;

	    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
	    document.pantallaForm.elements['SAVE'].disabled = true;
	    document.pantallaForm.elements['DISCARD'].disabled = true;
	    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
	    mostrarCapaEnviando(TXT_MSG_ENVIANDO);
	    document.pantallaForm.submit();
	}

	function backTo(pantalla) {

		// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
		if (!bLoaded) return;

		// Prevenimos doble click
		if (bDobleClick) return;
		bDobleClick=true;

	    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
	    document.pantallaForm.elements['SAVE'].disabled = true;
	    document.pantallaForm.elements['DISCARD'].disabled = true;
	    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = false;
	    document.pantallaForm.elements['PANTALLA_ANTERIOR'].value = pantalla;
	    mostrarCapaEnviando(TXT_MSG_ENVIANDO);
	    document.pantallaForm.submit();
	}

	function next() {

		// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
		if (!bLoaded) return;

		// Prevenimos doble click
		if (bDobleClick) return;

		document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = true;
	    document.pantallaForm.elements['SAVE'].disabled = true;
	    document.pantallaForm.elements['DISCARD'].disabled = true;
	    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
	    if (validatePantallaForm(document.pantallaForm)) {
			bDobleClick=true;
			mostrarCapaEnviando(TXT_MSG_ENVIANDO);
	        document.pantallaForm.submit();
	    }
	}

	function save() {

		// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
		if (!bLoaded) return;

		// Prevenimos doble click
		if (bDobleClick) return;
		bDobleClick=true;

		if ( confirm ( '<bean:message bundle="caibMessages" key="aviso.guardarFormulario" />' ) )
		{
		    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
		    document.pantallaForm.elements['SAVE'].disabled = false;
		    document.pantallaForm.elements['DISCARD'].disabled = true;
		    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
		    mostrarCapaEnviando(TXT_MSG_ENVIANDO);
		    document.pantallaForm.submit();
		} else {
			bDobleClick = false;
		}
	}

	function discard() {

		// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
		if (!bLoaded) return;

		// Prevenimos doble click
		if (bDobleClick) return;
		bDobleClick=true;

		if ( confirm ( '<bean:message bundle="caibMessages" key="aviso.cancelarFormulario" />' ) )
		{
			document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
	    	document.pantallaForm.elements['SAVE'].disabled = true;
	    	document.pantallaForm.elements['DISCARD'].disabled = false;
	    	document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
	    	mostrarCapaEnviando(TXT_MSG_ENVIANDO);
	    	document.pantallaForm.submit();
		} else {
			bDobleClick=false;
		}
	}


	// Obrir un pop up
	function obrir(url, name, x, y) {
	   window.open(url, name, 'scrollbars=yes, resizable=yes, width=' + x + ',height=' + y);
	}



	// --------------------- Funciones logica formulario ----------------------------------------------
	// Busca si los campos de los que depende, estan modificados
	function depends(deps, modified) {
	    for (i = 0; i < modified.length; i++) {
			if (deps.indexOf(modified[i] + " ") > -1) {
	            return true;
	        }
	    }
	    return false;
	}

	// Funcion para invocar a Sistra para mantener sesion activa
	function mantenimientoSesionSistra() {
		URL_SISTRA_MANTENIMIENTO_SESION="<%=request.getAttribute("urlSisTraMantenimientoSesion")%>";
		// asyncPost(URL_SISTRA_MANTENIMIENTO_SESION,new Array());
		$.get(URL_SISTRA_MANTENIMIENTO_SESION, function(data, status){
		   // alert("mantenimientoSesionSistra: " + data);
		  });
	}

	// Definición de los valores de pantallas anteriores
	<bean:define id="datosAnteriores" name="datosAnteriores" type="java.util.Map"/>
	<%=JSUtil.declareVarMap(datosAnteriores)%>

	// Definición de las variables para almacernar los valores de la pantallas actual
	<bean:write name="definicionVariablesForm"/>


	 // Funcion para calcular los valores de los campos
	 // MODIFICACION: resetValoresIndexed - tras la primera llamada en las sucesivas llamadas puede pasar q los campos indexados no tengan actualizada la lista de valores posibles, con lo
	 //				que respetamos el valor previo para intentar establecerlo FALTARIA METER LA LOGICA XA EL TREE Y LISTBOX
	 function getUrlParams(form, resetValoresIndexed) {
	    var parametersPost = new Array();
	    // Definicion de los valores de los campos de esta pantalla
	<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
	    <bean:define id="nombre" name="campo" property="nombreLogico"/>

		<% if (campo instanceof ComboBox)  { %>
		    if (resetValoresIndexed) {
			    f_<%=nombre%>='';
			    f_<%=nombre%>_text='';
			    var v_<%=nombre%> = control_values("<%=nombre%>");
			    // -- REMOZADO FORMS: si no hay valor seleccionado q  devuelve?
			    if (v_<%=nombre%> != null) {
			    	f_<%=nombre%> = v_<%=nombre%>.valor;
			    	f_<%=nombre%>_text = v_<%=nombre%>.etiqueta;
			    	parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
			    }
		    } else {
		    	parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
		    }
	    <% } else if (campo instanceof CheckBox) { %>
			 var v_<%=nombre%> = control_values("<%=nombre%>");
	     	 f_<%=nombre%> = v_<%=nombre%>.valor;
	    	 parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
	   	<% } else if (campo instanceof RadioButton) { %>
			var v_<%=nombre%> = control_values("<%=nombre%>");
	     	 f_<%=nombre%> = v_<%=nombre%>.valor;
	     	 f_<%=nombre%>_text = v_<%=nombre%>.etiqueta;
	    	 parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
	    <% } else if (campo instanceof ListBox || campo instanceof TreeBox) { %>
		    f_<%=nombre%> = new Array();
		    f_<%=nombre%>_text = new Array();
		    var v_<%=nombre%> = control_values("<%=nombre%>");
		    for (i = 0; i < v_<%=nombre%>.length; i++) {
		        f_<%=nombre%>.push(v_<%=nombre%>[i].valor);
		        f_<%=nombre%>_text.push(v_<%=nombre%>[i].etiqueta);
		        parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",v_<%=nombre%>[i].valor);
		    }
	    <% } else if (campo instanceof TextBox) { %>
	    	var v_<%=nombre%> = control_values("<%=nombre%>");
	        <% if (campo.isNatural()) { %>
	         f_<%=nombre%> = parseInt(v_<%=nombre%>);
	        <% } else if (campo.isReal()) { %>
	         f_<%=nombre%> = parseFloat(v_<%=nombre%>);
	        <% } else { %>
	         f_<%=nombre%> = form.<%=nombre%>.value;
	        <% } %>
	        parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
	    <% } else if (campo instanceof ListaElementos) { %>
	    	// No se asigna ningun valor
	    <% } else { %>
	    // En teoria nunca se llega aqui.
	     f_<%=nombre%> = form.<%=nombre%>.value;
	     parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
	    <% } %>
	</logic:iterate>
	 return parametersPost;

	 }


	// Funcion para llamar autorellenable de un campo en el servidor.
	function autorellenable(fieldName, parameters) {

	    url = "<html:rewrite page='<%=securePath + "/expresion/autorellenable" + sufijoModoFuncionamiento + ".do"%>' />";

	    parameters[parameters.length] = new ParametroPost("ID_INSTANCIA","<%=request.getAttribute("ID_INSTANCIA")%>");
	    parameters[parameters.length] = new ParametroPost("fieldName",fieldName);

	    return new Function("return " + syncPost(url, parameters))();
	}

	// Funcion para llamar autocalculo de un campo en el servidor.
	function autocalculo(fieldName, parameters) {

	    url = "<html:rewrite page='<%=securePath + "/expresion/autocalculo" + sufijoModoFuncionamiento + ".do"%>' />";

	    parameters[parameters.length] = new ParametroPost("ID_INSTANCIA","<%=request.getAttribute("ID_INSTANCIA")%>");
	    parameters[parameters.length] = new ParametroPost("fieldName",fieldName);

	    return new Function("return " + syncPost(url, parameters))();
	}

	// Funcion para llamar dependencia de un campo en el servidor.
	function dependencia(fieldName, parameters) {

	    url = "<html:rewrite page='<%=securePath + "/expresion/dependencia" + sufijoModoFuncionamiento + ".do"%>' />";

	    parameters[parameters.length] = new ParametroPost("ID_INSTANCIA","<%=request.getAttribute("ID_INSTANCIA")%>");
	    parameters[parameters.length] = new ParametroPost("fieldName",fieldName);

	    return new Function("return " + syncPost(url, parameters))();
	}

	// Funcion para llamar calculo de valores posibles de un campo en el servidor
	function valoresPosibles(fieldName, parameters) {

	    url = "<html:rewrite page='<%=securePath + "/expresion/valores" + sufijoModoFuncionamiento + ".do"%>' />";

	    parameters[parameters.length] = new ParametroPost("ID_INSTANCIA","<%=request.getAttribute("ID_INSTANCIA")%>");
	    parameters[parameters.length] = new ParametroPost("fieldName",fieldName);

	    return new Function("return " + syncPost(url, parameters))();

	}

	function onFieldChange(form,fieldName){

		var b_mostrar = false;

		<logic:iterate id="element" name="ajaxOnChange">
			<logic:equal name="element" property="value" value="true">
				if (fieldName == '<bean:write name="element" property="key"/>') b_mostrar = true;
			</logic:equal>
		</logic:iterate>

		if (b_mostrar) mostrarCapaEnviando(TXT_MSG_ENVIANDO);
		v_form=form;
		v_fieldName=fieldName;

		setTimeout("onFieldChange_imp()",200);
	}

	// fieldName Nombre del campo modificado (sin f_)
	function onFieldChange_imp() {

	     form = v_form;
	     fieldName	= v_fieldName;

	    // Valores que han sido modificados (el campo modificado + los autocalculados)
	    var modificados = new Array();
	    if (fieldName) {
	        modificados.push("f_" + fieldName);
	    }

		// Valores actuales formulario
		var parametersPost = getUrlParams(form, true);

	// Expresiones de autorrelleno
	<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
	    <bean:define id="nombre" name="campo" property="nombreLogico"/>
	    <logic:notEmpty name="campo" property="expresionAutorellenable">
	    <% String deps = ScriptUtil.dependencias(campo.getExpresionAutorellenable()); %>
	    if (depends("<%=deps%>", modificados)) {

		    // Obtener valores actuales formulario
		    parametersPost = getUrlParams(form, false);

			// Indicamos que el campo esta modificado
	        modificados.push("f_<%=nombre%>");

			// Calculamos nuevo valor
			f_<%=nombre%> = autorellenable('<%=nombre%>', parametersPost);

			<% if (campo instanceof CheckBox) { %>
			f_<%=nombre%> =  ("true" == f_<%=nombre%>);
			<%}%>

	        <% if (campo.isReal()) { %>
	        if (isNaN(f_<%=nombre%>)) {
	            f_<%=nombre%> = 0.0;
	        } else {
	            f_<%=nombre%> = Math.round(f_<%=nombre%> * 1000) / 1000;
	        }
	        <% } else if (campo.isNatural()) { %>
	        if (isNaN(f_<%=nombre%>)) {
	            f_<%=nombre%> = 0;
	        } else {
	            f_<%=nombre%> = parseInt(f_<%=nombre%>);
	        }
	        <% } %>


			// Establecemos valor
			control_select("<%=nombre%>", f_<%=nombre%>, true);

	     }
	    </logic:notEmpty>
	</logic:iterate>



	// Expresiones de autocalculo i actualización de los campos.
	<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
	    <bean:define id="nombre" name="campo" property="nombreLogico"/>
	    <logic:notEmpty name="campo" property="expresionAutocalculo">
	    <logic:empty name="campo" property="expresionAutorellenable">
	    <% String deps = ScriptUtil.dependencias(campo.getExpresionAutocalculo()); %>
	    if (  <%=(campo.getExpresionAutocalculo().trim().length()>0)%> && (!fieldName || depends("<%=deps%>", modificados)) ) {

		    // Obtener valores actuales formulario
		    parametersPost = getUrlParams(form, false);

			// Indicamos que el campo esta modificado
	        modificados.push("f_<%=nombre%>");

			// Calculamos nuevo valor
	        f_<%=nombre%> = autocalculo('<%=nombre%>', parametersPost);

	        <% if (campo instanceof CheckBox) { %>
			f_<%=nombre%> =  ("true" == f_<%=nombre%>);
			<%}%>

	        <% if (campo.isReal()) { %>
	        if (isNaN(f_<%=nombre%>)) {
	            f_<%=nombre%> = 0.0;
	        } else {
	            f_<%=nombre%> = Math.round(f_<%=nombre%> * 1000) / 1000;
	        }
	        <% } else if (campo.isNatural()) { %>
	        if (isNaN(f_<%=nombre%>)) {
	            f_<%=nombre%> = 0;
	        } else {
	            f_<%=nombre%> = parseInt(f_<%=nombre%>);
	        }
	        <% } %>


			// Ponemos a solo lectura
			control_readOnly("<%=nombre%>", true);

	        // Establecemos valor
	        control_select("<%=nombre%>", f_<%=nombre%>, true);

	     }
	    </logic:empty>
	    </logic:notEmpty>
	</logic:iterate>

	// Expresiones valores posibles.
	<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
	    <bean:define id="nombre" name="campo" property="nombreLogico"/>
	    <logic:notEmpty name="campo" property="expresionValoresPosibles">
		<% if (campo.isIndexed()) { %>
	    	<% String deps = ScriptUtil.dependencias(campo.getExpresionValoresPosibles()); %>
		        if (depends("<%=deps%>", modificados)) {

	    				// Obtener valores actuales formulario
						parametersPost = getUrlParams(form, false);

						var vps = valoresPosibles('<%=nombre%>', parametersPost);

				<% if (campo instanceof ComboBox) { %>
						// Añadimos opcion vacia para combos
						vps.unshift({"defecto":false,"valor":"","etiqueta":"..."});
				<% } %>

						// Actualizamos valores posibles
						control_refill('<%=nombre%>',vps);

				<% if ( (campo.getExpresionAutocalculo() != null && campo.getExpresionAutocalculo().trim().length()>0)
						||
						(campo.getExpresionAutorellenable() != null && campo.getExpresionAutorellenable().trim().length()>0)
					  ) { %>
						// Si tiene expresion autocalculo o autorellenable mantenemos valor
						control_select("<%=nombre%>", f_<%=nombre%>, true);
				<% } else {   %>
						// Reseteamos valor
						control_select("<%=nombre%>", "", true);
				<% } %>

		        }
	  	 <% } %>
	    </logic:notEmpty>
	</logic:iterate>

	// Calculo de dependencias
		// Obtener valores actuales formulario (para asegurar que sean los ultimos valores a la hora de evaluar las dependencias)
		parametersPost = getUrlParams(form, true);

	<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
	    <bean:define id="nombre" name="campo" property="nombreLogico"/>
	    <logic:notEmpty name="campo" property="expresionDependencia">
	    <%-- Amb la següent executam el codi al servidor, amb la segona, al client. Configurable? --%>
	    <% String deps = ScriptUtil.dependencias(campo.getExpresionDependencia()); %>
	    if (!fieldName || depends("<%=deps%>", modificados)) {
	        <%-- form.<%=nombre%>.disabled = !(dependencia('<%=nombre%>', urlParams)); --%>

			valueReadOnly = false;
			valueDisabled = false;

			<logic:equal name="campo" property="bloqueado" value="true">
			// Bloqueado por configuracion (ponemos a readonly)
			valueReadOnly = true;
			</logic:equal>

			<logic:equal name="campo" property="bloqueado" value="false">
	     	// INDRA: MODIFICACION PARA PERMITIR ESTABLECER DISABLED O READONLY
	        expDep = (<%=JSUtil.escapeAndJoinJsExpression(campo.getExpresionDependencia())%>);
	        expDep = expDep + '';
	        if (expDep != 'true' && expDep != 'false' && expDep != 'readOnly') expDep = 'true';


	       	<% if (campo instanceof TextBox) {%>
	     	// CONTROL DE FOCO EN READONLY: SI LO MARCAMOS COMO READONLY Y TIENE EL FOCO, SE LO QUITAMOS
	        if (form.<%=nombre%>.hasFocus && expDep == 'readOnly') window.focus();
	        <% } %>

			// -- ESTABLECEMOS ACCESO CAMPO EN FUNCION EXPRESION DEPENDENCIA
	        if (expDep == 'false'){
	        	valueDisabled = true;
			} else if (expDep == 'readOnly'){
				valueReadOnly = true;
			}

	        <logic:notEmpty name="campo" property="expresionAutocalculo">
			// SI ES AUTOCALCULO FORZAMOS READONLY SI NO ESTA DISABLED
			if (!valueDisabled) valueReadOnly = true;
        	</logic:notEmpty>

	        </logic:equal>

	        // Establecemos valores readonly/disabled
	        control_disabled("<%=nombre%>", valueDisabled);
	        if (!valueDisabled) {
		        // Solo ponemos readonly si no esta disabled
	        	control_readOnly("<%=nombre%>", valueReadOnly);
	        }


	    }
	    </logic:notEmpty>
	</logic:iterate>

	ocultarCapaEnviando();

	}

	// -----------------  Funciones Lista elementos ---------------------------------------------
	function detalleElemento(campo,accion){

		// Obtenemos indice seleccionado
		indice=-1;
		radio = document.getElementsByName("listaelementos@"+campo+"-index");
		if (radio != null && radio != undefined){
			for (var i = 0; i < radio.length; i++) {
		        if (radio[i].checked){
		            indice = radio[i].value;
		            break;
		        }
		    }
		}

		// Obligatorio indice para diferentes opciones que insertar
		if (indice == -1 && accion != 'insertar'){
			alert("<bean:message  bundle="caibMessages" key="listaElementos.validacionSeleccionarElemento"/>");
			return;
		}
		if (indice == -1) {
			indice = radio.length;
		}

		// Vamos al detalle
		var url_action= "irPantallaDetalle.do?listaelementos@accion="+accion+"&listaelementos@campo="+campo+"&listaelementos@indice="+indice;
		// En caso de alta/modificacion abrimos iframe
		if (accion == 'insertar' || accion == 'modificar') {
			control_tableDetall(campo, url_action);
		} else {
			f = document.getElementById("pantallaForm");
			f.action = url_action;
			mostrarCapaEnviando(TXT_MSG_ENVIANDO);
			f.submit();
		}
	}

	// -- Captcha
	function recaptcha(fieldname) {
		 // Pedimos cambio captcha
		 var res = regeneraCaptcha(fieldname);

		 // Recargamos imagen
		 if (res == "OK") {
			 var urlImage = 'captchaDownload.do?fieldName=' + fieldname + '&ID_INSTANCIA=<%=request.getAttribute("ID_INSTANCIA")%>&ts=' + (new Date()).getMilliseconds();
			 var idImage = '#' + fieldname + '_imgCaptcha';
		 	$(idImage).attr('src', urlImage);
		 }
	}

	// Funcion para llamar por ajax a regenerar captcha
	function regeneraCaptcha(fieldName) {
		var parameters = new Array();
	    url = "<html:rewrite page='<%=securePath + "/expresion/recaptcha" + sufijoModoFuncionamiento + ".do"%>' />";
	    parameters[parameters.length] = new ParametroPost("ID_INSTANCIA","<%=request.getAttribute("ID_INSTANCIA")%>");
	    parameters[parameters.length] = new ParametroPost("fieldName",fieldName);
	    return new Function("return '" + syncPost(url, parameters) + "'")();
	}

	// -- Funciones validacion
	<html:javascript dynamicJavascript="false" staticJavascript="true" />


	function test() {
		var vps = eval('[{"defecto":false,"valor":"1","etiqueta":"ADSUBIA"},{"defecto":false,"valor":"2","etiqueta":"AGOST"},{"defecto":false,"valor":"3","etiqueta":"AGRES"},{"defecto":false,"valor":"4","etiqueta":"AIGÜES"},{"defecto":false,"valor":"14","etiqueta":"ALACANT"},{"defecto":false,"valor":"5","etiqueta":"ALBATERA"},{"defecto":false,"valor":"6","etiqueta":"ALCALALÍ"},{"defecto":false,"valor":"7","etiqueta":"ALCOCER DE PLANES"},{"defecto":false,"valor":"9","etiqueta":"ALCOI"},{"defecto":false,"valor":"8","etiqueta":"ALCOLEJA"},{"defecto":false,"valor":"10","etiqueta":"ALFAFARA"},{"defecto":false,"valor":"11","etiqueta":"ALFÀS DEL PI (L\')"},{"defecto":false,"valor":"12","etiqueta":"ALGORFA"},{"defecto":false,"valor":"13","etiqueta":"ALGUEÑA"},{"defecto":false,"valor":"15","etiqueta":"ALMORADÍ"},{"defecto":false,"valor":"16","etiqueta":"ALMUDAINA"},{"defecto":false,"valor":"17","etiqueta":"ALQUERIA D\'ASNAR (L\')"},{"defecto":false,"valor":"18","etiqueta":"ALTEA"},{"defecto":false,"valor":"19","etiqueta":"ASPE"},{"defecto":false,"valor":"20","etiqueta":"BALONES"},{"defecto":false,"valor":"21","etiqueta":"BANYERES DE MARIOLA"},{"defecto":false,"valor":"22","etiqueta":"BENASAU"},{"defecto":false,"valor":"23","etiqueta":"BENEIXAMA"},{"defecto":false,"valor":"24","etiqueta":"BENEJÚZAR"},{"defecto":false,"valor":"25","etiqueta":"BENFERRI"},{"defecto":false,"valor":"26","etiqueta":"BENIARBEIG"},{"defecto":false,"valor":"27","etiqueta":"BENIARDÁ"},{"defecto":false,"valor":"28","etiqueta":"BENIARRÉS"},{"defecto":false,"valor":"30","etiqueta":"BENIDOLEIG"},{"defecto":false,"valor":"31","etiqueta":"BENIDORM"},{"defecto":false,"valor":"32","etiqueta":"BENIFALLIM"},{"defecto":false,"valor":"33","etiqueta":"BENIFATO"},{"defecto":false,"valor":"29","etiqueta":"BENIGEMBLA"},{"defecto":false,"valor":"34","etiqueta":"BENIJÓFAR"},{"defecto":false,"valor":"35","etiqueta":"BENILLOBA"},{"defecto":false,"valor":"36","etiqueta":"BENILLUP"},{"defecto":false,"valor":"37","etiqueta":"BENIMANTELL"},{"defecto":false,"valor":"38","etiqueta":"BENIMARFULL"},{"defecto":false,"valor":"39","etiqueta":"BENIMASSOT"},{"defecto":false,"valor":"40","etiqueta":"BENIMELI"},{"defecto":false,"valor":"41","etiqueta":"BENISSA"},{"defecto":false,"valor":"43","etiqueta":"BIAR"},{"defecto":false,"valor":"44","etiqueta":"BIGASTRO"},{"defecto":false,"valor":"45","etiqueta":"BOLULLA"},{"defecto":false,"valor":"46","etiqueta":"BUSOT"},{"defecto":false,"valor":"49","etiqueta":"CALLOSA DE SEGURA"},{"defecto":false,"valor":"48","etiqueta":"CALLOSA D\'EN SARRIÀ"},{"defecto":false,"valor":"47","etiqueta":"CALP"},{"defecto":false,"valor":"51","etiqueta":"CAMP DE MIRRA (EL)"},{"defecto":false,"valor":"50","etiqueta":"CAMPELLO (EL)"},{"defecto":false,"valor":"52","etiqueta":"CAÑADA"},{"defecto":false,"valor":"53","etiqueta":"CASTALLA"},{"defecto":false,"valor":"54","etiqueta":"CASTELL DE CASTELLS"},{"defecto":false,"valor":"55","etiqueta":"CATRAL"},{"defecto":false,"valor":"56","etiqueta":"COCENTAINA"},{"defecto":false,"valor":"57","etiqueta":"CONFRIDES"},{"defecto":false,"valor":"58","etiqueta":"COX"},{"defecto":false,"valor":"59","etiqueta":"CREVILLENT"},{"defecto":false,"valor":"61","etiqueta":"DAYA NUEVA"},{"defecto":false,"valor":"62","etiqueta":"DAYA VIEJA"},{"defecto":false,"valor":"63","etiqueta":"DÉNIA"},{"defecto":false,"valor":"64","etiqueta":"DOLORES"},{"defecto":false,"valor":"66","etiqueta":"ELDA"},{"defecto":false,"valor":"65","etiqueta":"ELX"},{"defecto":false,"valor":"67","etiqueta":"FACHECA"},{"defecto":false,"valor":"68","etiqueta":"FAMORCA"},{"defecto":false,"valor":"69","etiqueta":"FINESTRAT"},{"defecto":false,"valor":"70","etiqueta":"FORMENTERA DEL SEGURA"},{"defecto":false,"valor":"72","etiqueta":"GAIANES"},{"defecto":false,"valor":"71","etiqueta":"GATA DE GORGOS"},{"defecto":false,"valor":"73","etiqueta":"GORGA"},{"defecto":false,"valor":"74","etiqueta":"GRANJA DE ROCAMORA"},{"defecto":false,"valor":"75","etiqueta":"GUADALEST"},{"defecto":false,"valor":"76","etiqueta":"GUARDAMAR DEL SEGURA"},{"defecto":false,"valor":"77","etiqueta":"HONDÓN DE LAS NIEVES"},{"defecto":false,"valor":"78","etiqueta":"HONDÓN DE LOS FRAILES"},{"defecto":false,"valor":"79","etiqueta":"IBI"},{"defecto":false,"valor":"80","etiqueta":"JACARILLA"},{"defecto":false,"valor":"85","etiqueta":"LLÍBER"},{"defecto":false,"valor":"86","etiqueta":"MILLENA"},{"defecto":false,"valor":"87","etiqueta":"MIRAFLOR"},{"defecto":false,"valor":"88","etiqueta":"MONFORTE DEL CID"},{"defecto":false,"valor":"89","etiqueta":"MONÒVER"},{"defecto":false,"valor":"903","etiqueta":"MONTESINOS (LOS)"},{"defecto":false,"valor":"91","etiqueta":"MURLA"},{"defecto":false,"valor":"92","etiqueta":"MURO DE ALCOY"},{"defecto":false,"valor":"90","etiqueta":"MUTXAMEL"},{"defecto":false,"valor":"93","etiqueta":"NOVELDA"},{"defecto":false,"valor":"94","etiqueta":"NUCIA (LA)"},{"defecto":false,"valor":"95","etiqueta":"ONDARA"},{"defecto":false,"valor":"96","etiqueta":"ONIL"},{"defecto":false,"valor":"97","etiqueta":"ORBA"},{"defecto":false,"valor":"99","etiqueta":"ORIHUELA"},{"defecto":false,"valor":"84","etiqueta":"ORXA (L\')"},{"defecto":false,"valor":"98","etiqueta":"ORXETA"},{"defecto":false,"valor":"100","etiqueta":"PARCENT"},{"defecto":false,"valor":"101","etiqueta":"PEDREGUER"},{"defecto":false,"valor":"102","etiqueta":"PEGO"},{"defecto":false,"valor":"103","etiqueta":"PENÀGUILA"},{"defecto":false,"valor":"104","etiqueta":"PETRER"},{"defecto":false,"valor":"902","etiqueta":"PILAR DE LA HORADADA"},{"defecto":false,"valor":"105","etiqueta":"PINOSO"},{"defecto":false,"valor":"106","etiqueta":"PLANES"},{"defecto":false,"valor":"42","etiqueta":"POBLE NOU DE BENITATXELL (EL)"},{"defecto":false,"valor":"901","etiqueta":"POBLETS (ELS)"},{"defecto":false,"valor":"107","etiqueta":"POLOP"},{"defecto":false,"valor":"108","etiqueta":"PUEBLA DE ROCAMORA"},{"defecto":false,"valor":"60","etiqueta":"QUATRETONDETA"},{"defecto":false,"valor":"109","etiqueta":"RAFAL"},{"defecto":false,"valor":"110","etiqueta":"RÀFOL D\'ALMÚNIA (EL)"},{"defecto":false,"valor":"111","etiqueta":"REDOVÁN"},{"defecto":false,"valor":"112","etiqueta":"RELLEU"},{"defecto":false,"valor":"113","etiqueta":"ROJALES"},{"defecto":false,"valor":"114","etiqueta":"ROMANA (LA)"},{"defecto":false,"valor":"115","etiqueta":"SAGRA"},{"defecto":false,"valor":"116","etiqueta":"SALINAS"},{"defecto":false,"valor":"118","etiqueta":"SAN FULGENCIO"},{"defecto":false,"valor":"904","etiqueta":"SAN ISIDRO"},{"defecto":false,"valor":"120","etiqueta":"SAN MIGUEL DE SALINAS"},{"defecto":false,"valor":"117","etiqueta":"SANET Y NEGRALS"},{"defecto":false,"valor":"119","etiqueta":"SANT JOAN D\'ALACANT"},{"defecto":false,"valor":"122","etiqueta":"SANT VICENT DEL RASPEIG"},{"defecto":false,"valor":"121","etiqueta":"SANTA POLA"},{"defecto":false,"valor":"123","etiqueta":"SAX"},{"defecto":false,"valor":"124","etiqueta":"SELLA"},{"defecto":false,"valor":"125","etiqueta":"SENIJA"},{"defecto":false,"valor":"126","etiqueta":"SETLA Y MIRARROSA"},{"defecto":false,"valor":"127","etiqueta":"TÁRBENA"},{"defecto":false,"valor":"128","etiqueta":"TEULADA"},{"defecto":false,"valor":"129","etiqueta":"TIBI"},{"defecto":false,"valor":"130","etiqueta":"TOLLOS"},{"defecto":false,"valor":"131","etiqueta":"TORMOS"},{"defecto":false,"valor":"132","etiqueta":"TORRE DE LES MAÇANES (LA)"},{"defecto":false,"valor":"133","etiqueta":"TORREVIEJA"},{"defecto":false,"valor":"134","etiqueta":"VALL D\'ALCALÀ (LA)"},{"defecto":false,"valor":"135","etiqueta":"VALL DE EBO"},{"defecto":false,"valor":"136","etiqueta":"VALL DE GALLINERA"},{"defecto":false,"valor":"137","etiqueta":"VALL DE LAGUART (LA)"},{"defecto":false,"valor":"138","etiqueta":"VERGER (EL)"},{"defecto":false,"valor":"139","etiqueta":"VILA JOIOSA (LA)"},{"defecto":false,"valor":"140","etiqueta":"VILLENA"},{"defecto":false,"valor":"82","etiqueta":"XÀBIA"},{"defecto":false,"valor":"81","etiqueta":"XALÓ"},{"defecto":false,"valor":"83","etiqueta":"XIXONA"}]');
		control_refill('cTablaListbox2',vps);
	}
-->
</script>