<%@ page language="java"%>
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
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="nested" uri="http://jakarta.apache.org/struts/tags-nested"%>
<bean:define id="securePath" name="securePath" scope="request"/>
<html:xhtml/>
<% String sufijoModoFuncionamiento = (String) request.getAttribute("sufijoModoFuncionamiento"); %>

<div style="display: none;">MODO FUNCIONAMIENTO: verPantallaCaib<bean:write name="sufijoModoFuncionamiento"/></div>


<script src="<html:rewrite page='/js/htmlform'/><bean:write name="sufijoModoFuncionamiento"/>.jsp" type="text/javascript"></script>
<script src="<html:rewrite page='/js/xmlhttp'/><bean:write name="sufijoModoFuncionamiento"/>.js" type="text/javascript"></script>


<script type="text/javascript">
<!--

	function mantenimientoSesionSistra() {
		// URL para mantenimiento sesion de sistra
		URL_SISTRA_MANTENIMIENTO_SESION="<%=request.getAttribute("urlSisTraMantenimientoSesion")%>";
		asyncPost(URL_SISTRA_MANTENIMIENTO_SESION,new Array());		
	} 

// Definición de los valores de pantallas anteriores
<bean:define id="datosAnteriores" name="datosAnteriores" type="java.util.Map"/>
<%=JSUtil.declareVarMap(datosAnteriores)%>

// Definición de los valores de las listas de elementos de la pantalla
<bean:define id="datosListasElementos" name="datosListasElementos" type="java.util.Map"/>
<%=JSUtil.declareVarMap(datosListasElementos)%>

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
	    if (form.<%=nombre%>.selectedIndex >= 0) {
	        f_<%=nombre%> = form.<%=nombre%>.options[form.<%=nombre%>.selectedIndex].value;
	        f_<%=nombre%>_text = form.<%=nombre%>.options[form.<%=nombre%>.selectedIndex].text;
	        parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
	    }
    } else {
    	parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
    }
    <% } else if (campo instanceof CheckBox) { %>
     f_<%=nombre%> = (""+form.<%=nombre%>.checked == "true");     
     parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
    <% } else if (campo instanceof ListBox) { %>    
	    f_<%=nombre%> = new Array();
	    f_<%=nombre%>_text = new Array();
	    for (i = 0; i < form.<%=nombre%>.length; i++) {
	        if (form.<%=nombre%>.options[i].selected) {
	            f_<%=nombre%>.push(form.<%=nombre%>.options[i].value);
	            f_<%=nombre%>_text.push(form.<%=nombre%>.options[i].text);
	            parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",form.<%=nombre%>.options[i].value);
	            
	        }
	    }    
    <% } else if (campo instanceof TreeBox) { %>    	
	    	f_<%=nombre%> = new Array();
			f_<%=nombre%>_text = new Array();
		    
	     	chksArbol = document.getElementsByName("<%=nombre%>");
	    
	  		for (i = 0; i < chksArbol.length; i++) {
	        	if (chksArbol[i].checked) {
	            	f_<%=nombre%>.push(chksArbol[i].value);
		            f_<%=nombre%>_text.push(document.getElementById('tree_check_text_'+chksArbol[i].value).value);
	    	        parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",chksArbol[i].value);            
		        }        
		    } 	  	    		 
    <% } else if (campo instanceof FileBox) { %>
    <%--
        TODO Veure quin és el value que passam
    --%>
     f_<%=nombre%> = form.<%=nombre%>.value;     
     parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
    <% } else if (campo instanceof RadioButton) { %>
    f_<%=nombre%>='';
    f_<%=nombre%>_text='';
    for (i = 0; i < form.<%=nombre%>.length; i++) {
        if (form.<%=nombre%>[i].checked) {
            f_<%=nombre%> = form.<%=nombre%>[i].value;            
            f_<%=nombre%>_text = form.<%=nombre%>[i].text;
            parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
            continue;
        }
    }
    <% } else if (campo instanceof TextBox) { %>
        <% if (((TextBox) campo).isMultilinea()) { %>
        f_<%=nombre%> = new Array();
        for (i = 0; i < form.<%=nombre%>_l.length; i++) {
            f_<%=nombre%>.push(form.<%=nombre%>_l.options[i].text);
            parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",form.<%=nombre%>_l.options[i].text);
        }
        <% } else if (campo.isNatural()) { %>
         f_<%=nombre%> = parseInt(form.<%=nombre%>.value);         
         parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
        <% } else if (campo.isReal()) { %>
         f_<%=nombre%> = parseFloat(form.<%=nombre%>.value);         
         parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
        <% } else { %>
         f_<%=nombre%> = form.<%=nombre%>.value;         
         parametersPost[parametersPost.length] = new ParametroPost("<%=nombre%>",f_<%=nombre%>);
        <% } %>
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

function anyadirAnexo(fieldName, name, mime, size) {
    document.pantallaForm.elements[fieldName].value = name;
    document.pantallaForm.elements[fieldName + '_mime'].value = mime;
    document.pantallaForm.elements[fieldName + '_size'].value = size;
    if (bLoaded) onFieldChange(document.pantallaForm, fieldName);
}

// Funcion q se llama al cargar la pagina
function loadHook() {
	Fondo.crear();
    onFieldChange(document.pantallaForm, null);
    Fondo.destruir();
}

var v_form,v_fieldName;
function onFieldChange(form,fieldName){

	var b_mostrar = false;
	
	<logic:iterate id="element" name="ajaxOnChange">
		<logic:equal name="element" property="value" value="true">
			if (fieldName == '<bean:write name="element" property="key"/>') b_mostrar = true;
		</logic:equal> 				
	</logic:iterate>
	
	if (b_mostrar) mostrarCapaEnviando("Espere por favor...");
	v_form=form;
	v_fieldName=fieldName;
	
	setTimeout("onFieldChange_imp()",1);	
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

        <% if (campo instanceof ComboBox)  { %>
        selectOption(form.<%=nombre%>, f_<%=nombre%>);      	  
        <% } else if (campo instanceof RadioButton) { %>
        selectRadio(form.<%=nombre%>, f_<%=nombre%>);
        <% } else if (campo instanceof ListBox) { %>
        selectOptions(form.<%=nombre%>, f_<%=nombre%>);
        <% } else if (campo instanceof TreeBox) { %>
        	selectOptionsTree("<%=nombre%>", f_<%=nombre%>);
        <% } else if (campo instanceof CheckBox) { %>
        form.<%=nombre%>.checked = (""+f_<%=nombre%> == "true");                    
        <% } else if (campo instanceof FileBox) { %>
        <%--
            TODO Veure com gestionam autocalculo.
        --%>
        // Els input.file no es poden actualitzar.
        ///form.<%=nombre%>.value = f_<%=nombre%>;
        <% } else if (campo instanceof TextBox) { %>
            <% if (((TextBox) campo).isMultilinea()) { %>

			// -- INDRA: EL RESULTADO YA ES UNA CADENA CON SALTOS DE LINEA
			//form.<%=nombre%>.value = f_<%=nombre%>.join('\r\n');
			form.<%=nombre%>.value = f_<%=nombre%>;
            
            saveMulti(form.<%=nombre%>_l, form.<%=nombre%>);
            
            <% } else { %>
            form.<%=nombre%>.value = f_<%=nombre%>;
            <% } %>
        <% } else { %>
        // En teoria nunca llega aqui.
        form.<%=nombre%>.value = f_<%=nombre%>;
        <% } %>        
     }     
    </logic:notEmpty>
</logic:iterate>



// Expresiones de autocalculo i actualización de los campos.
<logic:iterate id="campo" name="pantalla" property="campos" type="org.ibit.rol.form.model.Campo">
    <bean:define id="nombre" name="campo" property="nombreLogico"/>
    <logic:notEmpty name="campo" property="expresionAutocalculo">
    <logic:empty name="campo" property="expresionAutorellenable"> 
    <% String deps = ScriptUtil.dependencias(campo.getExpresionAutocalculo()); %>
    if (  <%=campo.getExpresionAutocalculo().trim().length()>0%> && (!fieldName || depends("<%=deps%>", modificados)) ) {
	    
	    // Obtener valores actuales formulario	    
	    parametersPost = getUrlParams(form, false);
	    
		// Indicamos que el campo esta modificado
        modificados.push("f_<%=nombre%>");

		// Calculamos nuevo valor
        f_<%=nombre%> = autocalculo('<%=nombre%>', parametersPost);               
        
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

        <% if (campo instanceof ComboBox)  { %>
        selectOption(form.<%=nombre%>, f_<%=nombre%>);
      	  // INDRA: SI ES UN COMBO SIMULAMOS READONLY      	 	
     	  comboReadOnly(form.<%=nombre%>,true);       	  
		  // INDRA: FIN MODIFICACION	
        <% } else if (campo instanceof RadioButton) { %>
        selectRadio(form.<%=nombre%>, f_<%=nombre%>);
        radioReadOnly(form.<%=nombre%>, true);
        <% } else if (campo instanceof ListBox) { %>
        selectOptions(form.<%=nombre%>, f_<%=nombre%>);
        listboxReadOnly(form.<%=nombre%>, f_<%=nombre%>);
        <% } else if (campo instanceof TreeBox) { %>
        selectOptionsTree("<%=nombre%>", f_<%=nombre%>);
        readOnlyTree("<%=nombre%>",true);
        <% } else if (campo instanceof CheckBox) { %>
        form.<%=nombre%>.checked = (""+f_<%=nombre%> == "true");
		checkboxReadOnly( form.<%=nombre%>,true);       	        
        <% } else if (campo instanceof FileBox) { %>
        <%--
            TODO Veure com gestionam autocalculo.
        --%>
        // Els input.file no es poden actualitzar.
        ///form.<%=nombre%>.value = f_<%=nombre%>;
        <% } else if (campo instanceof TextBox) { %>
            <% if (((TextBox) campo).isMultilinea()) { %>
            
           // -- INDRA: EL RESULTADO YA ES UNA CADENA CON SALTOS DE LINEA
			//form.<%=nombre%>.value = f_<%=nombre%>.join('\r\n');
			form.<%=nombre%>.value = f_<%=nombre%>;
            
            saveMulti(form.<%=nombre%>_l, form.<%=nombre%>);            
            <% } else { %>
            form.<%=nombre%>.value = f_<%=nombre%>;
            <% } %>
        <% } else { %>
        // En teoria nunca llega aqui.
        form.<%=nombre%>.value = f_<%=nombre%>;
        <% } %>        
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
				
					// Actualizamos valores posibles
					<% if (campo instanceof TreeBox)  { %>
    				refillTree('<%=nombre%>',valoresPosibles('<%=nombre%>', parametersPost));    	
    				<%}else  { %>
					refillSelect(form.<%=nombre%>, valoresPosibles('<%=nombre%>', parametersPost) );    				
    				<% } %>
		        	
		            <% if (campo instanceof ComboBox)  { %>
		            selectOption(form.<%=nombre%>, f_<%=nombre%>);
		            <% } else if (campo instanceof RadioButton) { %>
		            selectRadio(form.<%=nombre%>, f_<%=nombre%>);
		            <% } else if (campo instanceof TreeBox) { %>
	            	selectOptionsTree("<%=nombre%>", f_<%=nombre%>);		            	
		            <% } else if (campo instanceof ListBox) { %>
		            selectOptions(form.<%=nombre%>, f_<%=nombre%>);
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
        
        // INDRA: MODIFICACION PARA PERMITIR ESTABLECER DISABLED O READONLY   
        expDep = (<%=JSUtil.escapeAndJoinJsExpression(campo.getExpresionDependencia())%>);
        
        // CONTROL DE FOCO EN READONLY: SI LO MARCAMOS COMO READONLY Y TIENE EL FOCO, SE LO QUITAMOS
       	<% if (campo instanceof TextBox) {%>           	 
          	if (form.<%=nombre%>.hasFocus && expDep == 'readOnly') window.focus();
        <% } %>
        
        // -- INICIALIZAMOS CAMPOS A READONLY=FALSE
        // Si el campo tiene expresión de autocalculo no debemos cambiar el atributo readonly
        <logic:empty name="campo" property="expresionAutocalculo">             
	        form.<%=nombre%>.readOnly = false;        
	        form.<%=nombre%>.className = 'frm';
	        <%if (campo instanceof ComboBox){ %>
	       		// Simulamos readonly con campo de texto
	       		comboReadOnly(form.<%=nombre%>,false);
	       	<%}%>
	        <%if (campo instanceof ListBox){ %>
	       		// Simulamos readonly con campo de texto
	       		listboxReadOnly(form.<%=nombre%>,false);
	       	<%}%>
	       	<% if (campo instanceof TextBox) { 
           	     if (((TextBox) campo).isMultilinea()) { %>	        	 	
            	form.<%=nombre%>_feed.className = 'frmr';
   	        	form.<%=nombre%>_l.className = 'frmr';
           	 <% } 
       		  } %>
       		<% if (campo instanceof RadioButton) { %>            
				radioReadOnly( form.<%=nombre%>,false);       	        
	        <%}%>
	        <% if (campo instanceof CheckBox) { %>            
				checkboxReadOnly( form.<%=nombre%>,false);       	        
	        <%}%>
	        <% if (campo instanceof TreeBox) { %>        
				readOnlyTree("<%=nombre%>",false);       	        
	        <%}%>
	        <% if (campo instanceof ListaElementos) { %>        
				readOnlyListaElementos("<%=nombre%>",false);       	        
	        <%}%>
	        
        </logic:empty>
        
        // -- INICIALIZAMOS CAMPOS A DISABLED=TRUE
        <% if (campo instanceof RadioButton) { %>            
			disableRadio( form.<%=nombre%>,false);       
		<%}else if (campo instanceof TreeBox){%>
			disableTree("<%=nombre%>",false);
        <%}else if (campo instanceof ListaElementos){%>
			disableListaElementos("<%=nombre%>",false);
        <%}else{%>
        	form.<%=nombre%>.disabled = false;
        <%}%>
        
        
        // -- ESTABLECEMOS ACCESO CAMPO EN FUNCION EXPRESION DEPENDENCIA
        if (expDep+'' == 'true'){         
        	<% if (campo instanceof RadioButton) { %>            
				disableRadio( form.<%=nombre%>,false);       							
	        <%}else{%>
	        form.<%=nombre%>.disabled = false;	               
	        <%}%>		    	        
		}else if (expDep+'' == 'false'){         
		    <% if (campo instanceof RadioButton) { %>            
				disableRadio( form.<%=nombre%>,true);  
			<%}else if (campo instanceof TreeBox){%>
				disableTree("<%=nombre%>",true);   
			<%}else if (campo instanceof ListaElementos){%>
				disableListaElementos("<%=nombre%>",true);     
	        <%}else{%>
	        	form.<%=nombre%>.disabled = true;
	        	form.<%=nombre%>.className = 'frmdisabled';	 
	        <%}%>
		}else if (expDep == 'readOnly'){ 
			// Si tiene autocalculo no lo tocamos
			<logic:empty name="campo" property="expresionAutocalculo">    
				form.<%=nombre%>.readOnly = true;
				form.<%=nombre%>.className = 'frmro';
				
				<%if (campo instanceof ComboBox){ %>
	        		// Simulamos readonly con campo de texto
	        		comboReadOnly(form.<%=nombre%>,true);        		
	        	<%}%>

	        	<%if (campo instanceof ListBox){ %>
	        		// Simulamos readonly con campo de texto
	        		listboxReadOnly(form.<%=nombre%>,true);        		
	        	<%}%>
	        	
	        	<% if (campo instanceof TextBox) { 
            	     if (((TextBox) campo).isMultilinea()) { %>	        	 	
	            	form.<%=nombre%>_feed.className = 'frmro';
    	        	form.<%=nombre%>_l.className = 'frmro';
            	 <% } 
        		  } %>
        		  
        		<% if (campo instanceof RadioButton) { %>            
					radioReadOnly( form.<%=nombre%>,true);       	        
		        <%}%>
		        
		        <% if (campo instanceof CheckBox) { %>            
					checkboxReadOnly( form.<%=nombre%>,true);       	        
		        <%}%>
		        
		        <% if (campo instanceof TreeBox) { %>            
					readOnlyTree("<%=nombre%>",true);       	        
		        <%}%>
		        
		        <% if (campo instanceof ListaElementos) { %>            
					readOnlyListaElementos("<%=nombre%>",true);       	        
		        <%}%>
	        	
	        </logic:empty>
		}		
        
        <% if (campo instanceof TextBox) { %>
            <% if (((TextBox) campo).isMultilinea()) { %>
            form.<%=nombre%>_feed.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_l.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_add.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_del.disabled = form.<%=nombre%>.disabled;
            
            form.<%=nombre%>_feed.className = form.<%=nombre%>.className ;
            form.<%=nombre%>_l.className = form.<%=nombre%>.className ;
            
            form.<%=nombre%>_feed.readOnly = form.<%=nombre%>.readOnly;
            form.<%=nombre%>_l.readOnly = form.<%=nombre%>.readOnly;            
            form.<%=nombre%>_add.disabled = form.<%=nombre%>.readOnly;
            form.<%=nombre%>_del.disabled = form.<%=nombre%>.readOnly;
            
            <% } %>
        <% } else if (campo instanceof FileBox) { %>
            form.<%=nombre%>_mime.disabled = form.<%=nombre%>.disabled;
            form.<%=nombre%>_size.disabled = form.<%=nombre%>.disabled;
            if (form.<%=nombre%>.disabled) {
                window.frames['<%=nombre%>_iframe'].location = '<html:rewrite page='<%=securePath + "/anexo.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />&disabled=true&nombre=<%=nombre%>';
            } else {
                window.frames['<%=nombre%>_iframe'].location = '<html:rewrite page='<%=securePath + "/anexo.do"%>' paramId="ID_INSTANCIA" paramName="ID_INSTANCIA" />&nombre=<%=nombre%>';
            }
            
            form.<%=nombre%>_mime.readOnly = form.<%=nombre%>.readOnly;
            form.<%=nombre%>_size.readOnly = form.<%=nombre%>.readOnly;
        <% } %>
    }
    </logic:notEmpty>
</logic:iterate>
	
ocultarCapaEnviando();

}
// -->
</script>

<script type="text/javascript">
<!--

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
        document.pantallaForm.submit();
    }
}

function save() {

	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;
	
	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.pantallaForm.elements['SAVE'].disabled = false;
    document.pantallaForm.elements['DISCARD'].disabled = true;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.pantallaForm.submit();
}

function discard() {
	
	// Protegemos para que no pueda pulsarse antes de que la pagina este cargada
	if (!bLoaded) return;
	
	// Prevenimos doble click
	if (bDobleClick) return;
	bDobleClick=true;
	
    document.pantallaForm.elements['<%=Constants.CANCEL_PROPERTY%>'].disabled = false;
    document.pantallaForm.elements['SAVE'].disabled = true;
    document.pantallaForm.elements['DISCARD'].disabled = false;
    document.pantallaForm.elements['PANTALLA_ANTERIOR'].disabled = true;
    document.pantallaForm.submit();
}
// -->
</script>

<script type="text/javascript">
<!--
var ayudascampo = new Array();
var nombrescampo = new Array();

function setAyuda(i) {
    var ayuda = document.getElementById("ayuda_campo");
    ayuda.innerHTML = ayudascampo[i];
    ayuda.style.display = 'block';

    var nombre = document.getElementById("nombre_campo");
    nombre.innerHTML = nombrescampo[i];
    nombre.style.display = 'block';
}

function unsetAyuda() {
    var ayuda = document.getElementById("ayuda_campo");
    ayuda.innerHTML = '';
    ayuda.style.display = 'none';

    var nombre = document.getElementById("nombre_campo");
    nombre.innerHTML = '';
    nombre.style.display = 'none';
}

<logic:iterate id="comp" name="pantalla" property="componentes" indexId="ind">
<% if (comp instanceof Campo) { %>
    ayudascampo[<%=ind%>] = "<bean:write name="comp" property='traduccion.ayuda' filter="false"/>";
    nombrescampo[<%=ind%>] = "<bean:write name="comp" property="traduccion.nombre" filter="false"/>";
<% } %>
</logic:iterate>

// -->
</script>

<html:errors />

<%String action = "procesar";%>
<logic:present name="pantallaDetalle">
	<logic:equal name="pantallaDetalle" value="true">
		<%action = "procesarDetalle"; %>	
	</logic:equal>
	<bean:define id="detalleAccion" value="<%=request.getParameter(\"listaelementos@accion\")%>"/>
</logic:present>


<html:form action='<%=securePath + "/" + action%>' onsubmit="return validatePantallaForm(this)" styleId="pantallaForm">
    <input type="hidden" name="<%=Constants.CANCEL_PROPERTY%>" disabled="disabled" value="true" />
    <input type="hidden" name="SAVE" disabled="disabled" value="true" />
    <input type="hidden" name="DISCARD" disabled="disabled" value="true" />
    <input type="hidden" name="PANTALLA_ANTERIOR" disabled="disabled" value="" />
    <input type="hidden" name="ID_INSTANCIA" value="<%=request.getAttribute("ID_INSTANCIA")%>" />            
    
    <logic:present name="pantallaDetalle">
		<logic:equal name="pantallaDetalle" value="true">
			<input type="hidden" name="listaelementos@accion" value="<%=request.getParameter("listaelementos@accion")%>" />
		    <input type="hidden" name="listaelementos@campo" value="<%=request.getParameter("listaelementos@campo")%>" />
		    <input type="hidden" name="listaelementos@indice" value="<%=request.getParameter("listaelementos@indice")%>" />
			<logic:equal name="detalleAccion" value="insertar">
				<input type="hidden" name="INSERT_POST_SAVE" value="false" />				
			</logic:equal>
		</logic:equal>
	</logic:present>
    
    <p>
    <nested:iterate id="comp" name="pantalla" property="componentes" indexId="ind" >
        <logic:greaterThan name="ind" value="0">
            <logic:equal name="comp" property="posicion" value="0"></p><p></logic:equal>
            <logic:equal name="comp" property="posicion" value="1">&nbsp;</logic:equal>
        </logic:greaterThan>
        <% if (comp instanceof Campo) { %>
        <span onmouseover="setAyuda(<%=ind%>)" style="<%=((Campo)comp).isOculto()?"display: none;":""%>">
        <%
            if (comp instanceof TextBox) {
                if (((TextBox) comp).isMultilinea()) {
                    %><jsp:include page='<%="/ui/caib/multibox" + sufijoModoFuncionamiento + ".jsp"%>' /><%
                } else {
                    %><jsp:include page='<%="/ui/caib/textbox" + sufijoModoFuncionamiento + ".jsp"%>'/><%
                }
            } else if (comp instanceof ComboBox) {
                %><jsp:include page='<%="/ui/caib/combobox" + sufijoModoFuncionamiento + ".jsp"%>'/><%
            } else if (comp instanceof RadioButton) {
                %><jsp:include page='<%="/ui/caib/radiobutton" + sufijoModoFuncionamiento + ".jsp"%>' /><%
            } else if (comp instanceof CheckBox) {
                %><jsp:include page='<%="/ui/caib/checkbox" + sufijoModoFuncionamiento + ".jsp"%>'/><%
            } else if (comp instanceof FileBox) {
                %><jsp:include page='<%="/ui/caib/filebox" + sufijoModoFuncionamiento + ".jsp"%>'/><%
            } else if (comp instanceof ListBox) {
                %><jsp:include page='<%="/ui/caib/listbox" + sufijoModoFuncionamiento + ".jsp"%>'/><%
            }else if (comp instanceof TreeBox) {
                %><jsp:include page='<%="/ui/caib/treebox" + sufijoModoFuncionamiento + ".jsp"%>'/><%
            }else if (comp instanceof ListaElementos) {
                %><jsp:include page='<%="/ui/caib/listaelementos" + sufijoModoFuncionamiento + ".jsp"%>'/><%
            }
        %></span>
        <%
            } else if (comp instanceof Label) {
            %><span class="<nested:write property='estilo'/>"><nested:write property="traduccion.etiqueta" filter="false"/></span><%
            }
        %>
    </nested:iterate>
    </p>
</html:form>

<%-- Botonera pagina detalle --%>
<logic:present name="pantallaDetalle">
	<logic:equal name="pantallaDetalle" value="true">
		<br/>
		<p style="text-align: center;background-color: #dedede;padding: 5px;">
			
			<logic:equal name="detalleAccion" value="insertar">
				<input type="button" value="<bean:message bundle="caibMessages" key="boton.guardarInsertarElemento"/>" onclick=" document.pantallaForm.elements['INSERT_POST_SAVE'].value = true;next();"/>&nbsp;&nbsp;&nbsp;
				<input type="button" value="<bean:message bundle="caibMessages" key="boton.guardarElemento"/>" onclick="next();"/>&nbsp;&nbsp;&nbsp;
				<input type="button" value="<bean:message bundle="caibMessages" key="boton.cancelarElemento"/>" onclick="back();"/>
			</logic:equal>
			
			<logic:notEqual name="detalleAccion" value="insertar">
				<input type="button" value="<bean:message bundle="caibMessages" key="boton.aceptarElemento"/>" onclick="next();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="<bean:message bundle="caibMessages" key="boton.cancelarElemento"/>" onclick="back();"/>
			</logic:notEqual>		
			
		</p>
	</logic:equal>
</logic:present>
<script type="text/javascript">
<!--
	// Desactivamos autocompletar
	document.getElementById("pantallaForm").setAttribute("autocomplete","off");	

	// Mantenemos url sesion sistra (realizamos peticion y programamos que se repita cada 5 min)
	mantenimientoSesionSistra();
 	window.setInterval(mantenimientoSesionSistra, 5 * 60 * 1000);
	
	// Añadimos a los componentes text un tag para saber si tienen el foco
	for(var i=0, df=document.forms, len=df.length; i<len; i++)
	 for(j=0, els=df[i].elements; j<els.length; j++)
	  if( /^text/.test( els[j].type ) )
	  {
	   els[j].hasFocus=false;
	  }	
// -->
</script>

<%-- Això és una xapuça --%>
<% pageContext.removeAttribute(Globals.XHTML_KEY);%>
<html:javascript formName="pantallaForm" staticJavascript="false" htmlComment="true" cdata="false" />
<% pageContext.setAttribute(Globals.XHTML_KEY, "true");%>
