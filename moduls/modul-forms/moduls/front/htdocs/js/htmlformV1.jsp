<%@ page language="java" contentType="text/javascript"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<html:javascript dynamicJavascript="false" staticJavascript="true" />

// Funciones de utilidad.
function esNumero(valor, alt) {
    if (isNaN(valor)) {
        return alt;
    } else {
        return valor;
    }
}

function existe(valor, alt) {
    if (valor == undefined || valor == null) {
        return alt;
    } else {
        return valor;
    }
}

function depends(deps, modified) {
    for (i = 0; i < modified.length; i++) {

		// -- INDRA: BUG SI HAY DOS CAMPOS CUYO NOMBRE COMIENZA IGUAL HARA MATCH!     	
    	// if (deps.indexOf(modified[i]) > -1) {
        //    return true;
        //}
    
        if (deps.indexOf(modified[i] + " ") > -1) {
            return true;
        }
        
        // -- INDRA: FIN
    }
    return false;
}

function updateMulti(select, hidden) {
    var values = new Array();
    for (i = 0; i < select.length; i++) {
        values.push(select.options[i].text);
    }
    hidden.value = values.join('\r\n');

    onFieldChange(hidden.form, hidden.name);
}

function saveMulti(select, hidden) {
    for (i = 0; i < select.length; i++) select.options[i] = null;
    var value = hidden.value;
    if (value != '') {
        variables = value.split('\r\n');
        for (i = 0; i < variables.length; i++) {
            select.options[i] = new Option(variables[i]);
        }
    }
}



function selectOption(select, valor) {
    for (var i = 0; i < select.length; i++) {
        if (select.options[i].value == valor) {
            select.options[i].selected = true;
        }
    }
}

function selectOptions(select, valors) {
    // Limpiar select
    for (var i = 0; i < select.length; i++) {
        select.options[i].selected = false;
    }
    // Marcar cada opcion
    for (var i = 0; i < valors.length; i++) {
        selectOption(select, valors[i]);
    }
}

function selectRadio(radio, valor) {
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].value == valor) {
            radio[i].checked = true;
        } else {
            radio[i].checked = false;
        }
    }
}

function refillSelect(select, valors) {
    var nopcions = select.length;
    for (var i = 1; i < nopcions; i++) {
        select.remove(1);
    }
    if (nopcions > 0 && select.options[0].value != '') {
        select.remove(0);
    }
    for (var i = 0; i < valors.length; i++) {
        var opcio = new Option(valors[i].etiqueta, valors[i].valor, valors[i].defecto, false);
        try {
            select.add(opcio, null);
        } catch(E) {
            select.add(opcio);
        }
    }
}


// Funciones para control tree
function createNodo(name,nodo,nodos){	
	if (isHoja(nodo,nodos)){
		txtNodo = "['" + nodo.etiqueta.replace(/'/g, "\\'") + "', ['javascript:void(0)',,,,,,'"+ name +"','"+ nodo.valor + "']]"		
		return txtNodo
	}else{
		txtNodo = "['" + nodo.etiqueta.replace(/'/g, "\\'") + "', ['javascript:void(0)',,'folder']"
		txtNodo += ",["	
		var primer = true
		for (var i = 0; i < nodos.length; i++) {				    			
			if (nodos[i].parentValor == nodo.valor) {				
				if (!primer) txtNodo += "," 
				txtNodo += createNodo(name,nodos[i],nodos) ;				
				primer = false
			 }			 			 
		}		
		txtNodo +="]]"
		return txtNodo
	}
}

// Comprueba si en la lista de valores hay algun hijo
function isHoja(nodo,nodos){ 	 	
  for (var i = 0; i < nodos.length; i++) {
  	  if (nodos[i].parentValor == nodo.valor) return false;      
   }
   return true;
}


function refillTree(name, nodos, expand) {		

	var tree = "[['', ['#'],[";
	var primer = true;
	for (var i = 0; i < nodos.length; i++) {
		if (nodos[i].parentValor ==  undefined || nodos[i].parentValor == '') {
			val = createNodo(name,nodos[i],nodos);
			
			//alert(val);
			
			if (!primer) tree += ","
			else primer = false
			tree += val ;						
				
		}
	}
	tree +="]]]"
	
	arrNodes = eval(tree);
	renderTree();expandAll();
	if (!expand) closeAll();	
}


function readOnlyTree(nombre,readonly) { 
	
	// Limpiar tree
    checks = document.getElementsByName(nombre);
    for (var i = 0; i < checks.length; i++) {
    	document.getElementById("tree_link_"+checks[i].value).className='';
        
        if (readonly){
        	checks[i].style.display='none';        	
        	if (checks[i].checked)
        	  	document.getElementById("tree_link_"+checks[i].value).className='linkReadonly'        	
        }else{
        	checks[i].style.display='inline';
        }
        
    }
            
}

function disableTree(nombre,dis){
    checks = document.getElementsByName(nombre);
    for (var i = 0; i < checks.length; i++) {
        checks[i].disabled = dis ;
    }
}


function selectOptionsTree(nombre,valors) {

	// Limpiar tree
    checks = document.getElementsByName(nombre);
    for (var i = 0; i < checks.length; i++) {
        checks[i].checked = false;        
    }
    
    
    // Marcar cada opcion
    for (var i = 0; i < valors.length; i++) {
    	c = document.getElementById("tree_check_"+valors[i]);    	
    	if (c!=null && c!=undefined){
	    	document.getElementById("tree_check_"+valors[i]).checked = true;
	    }
    }
    
}

function readOnlyListaElementos(nombre,readonly) { 
	var id = "listaelementos@"+nombre+"_";
    document.getElementById(id+"insertar").disabled=readonly;
    document.getElementById(id+"modificar").disabled=readonly;
    document.getElementById(id+"eliminar").disabled=readonly;  
}

function disableListaElementos(nombre,dis){
	var id = "listaelementos@"+nombre+"_";
    document.getElementById(id+"insertar").disabled=dis;
    document.getElementById(id+"modificar").disabled=dis;
    document.getElementById(id+"eliminar").disabled=dis;

}

// Funciones lista elementos
 function detalleElemento(campo,accion,mensajeValidacion){
	
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
		alert(mensajeValidacion);
		return;
	}
	
	// Vamos al detalle
	f = document.getElementById("pantallaForm");
	f.action = "irPantallaDetalle.do?listaelementos@accion="+accion+"&listaelementos@campo="+campo+"&listaelementos@indice="+indice;
	f.submit();
}

// -- INDRA: FUNCIONES UTILIDAD
function disableRadio(radio,disabled){
	for (var i = 0; i < radio.length; i++) {
        radio[i].disabled=disabled;
    }    	
}

// Funcion para simular el readonly en un combo
function comboReadOnly(obj,readonly) { 
 	
 	//alert("comboReadOnly:" + obj.name);
 	
 	 //Codigo/Valor del combo
 	 var codigo = '';
 	 var texto = '';
 	 if (obj.options.length > 0){
	     codigo = obj.options[obj.selectedIndex].value;
    	 texto  = obj.options[obj.selectedIndex].text;
   	 }
    
     // Mostramos combo
     obj.style.display = 'inline'; 	
     
     // Id del input a crear
     idInput =obj.name + "@simulaReadOnly" ;
     
     // Eliminamos input creados anteriormente
     input = document.getElementById(idInput); 
     if (input != null){    
		 obj.parentNode.removeChild(input);
	 }else{
	 	// alert("input no existe");
	 }
     
     // Creamos input si es readonly
     if(readonly){		
        input = document.createElement('input');
        input.type = "text";
        input.readOnly = true;
        input.value = texto;
        input.id = idInput;
        input.name = idInput;
        input.style.width = obj.offsetWidth + "px";
        input.className = 'frmro';
        
        // Insertamos input
        obj.parentNode.insertBefore(input,obj);
        
        // Ocultamos combo
        obj.style.display = 'none';
      }
 }
 
 
// Funcion para simular el readonly en un radiobutton
function radioReadOnly(obj,readonly) {  
	
	if (obj.length <= 0) return;
	
	nomRadio = obj[0].name;
	spanRadios = document.getElementById(nomRadio + "@radios");
	simulaRO = document.getElementById(nomRadio + "@simulaReadonly");
	
	// Obtenemos texto del elemento seleccionado
 	 var texto = '';
 	 for (var i = 0; i < obj.length; i++) {
       	if (obj[i].checked){        		        	
       		 texto = document.getElementById(obj[i].name + "@textoOpcion_" + i).value; 
       		 break;
       	}        		
    }
    
     // Mostramos radios
     spanRadios.style.display =  'block';
     
     // Id del input a crear
     idInput =nomRadio + "@simulaReadonly_text";
     
     // Eliminamos input creados anteriormente
     input = document.getElementById(idInput); 
     if (input != null){    
		 spanRadios.parentNode.removeChild(input);
	 }else{
	 	// alert("input no existe");
	 }
     
     // Creamos input si es readonly
     if(readonly){		
        input = document.createElement('input');
        input.type = "text";
        input.readOnly = true;
        input.value = texto;
        input.id = idInput;
        input.name = idInput;
        input.style.width = spanRadios.offsetWidth + "px";
        input.className = 'frmro';
        
        // Insertamos input
        spanRadios.parentNode.insertBefore(input,spanRadios);
        
        // Ocultamos combo
        spanRadios.style.display = 'none';
      }
	
 }
 
 
// Funcion para simular el readonly en un checkbox
function checkboxReadOnly(obj,readonly) { 
	nameEtiqueta = obj.name + "@simulaReadonly";
	if (readonly) { 
		obj.style.display = 'none';
		if (obj.checked)
			document.getElementById(nameEtiqueta).innerHTML="Si";	
		else
			document.getElementById(nameEtiqueta).innerHTML="No";	
		document.getElementById(nameEtiqueta).style.display =  'inline';
		document.getElementById(nameEtiqueta).className =  'frmro';	
	}else{
		obj.style.display = 'inline';
		document.getElementById(nameEtiqueta).style.display = 'none';        	
        	document.getElementById(nameEtiqueta).className='frm';
	}
 }
 
 
 
 // Funciones para mostrar capa de enviando cuando se invoque a ajax
 function mostrarCapaEnviando(mensaje){
 
 	// Creamos capa de fondo
 	Fondo.crear();

	// Mostramos capa enviando 
 	var capaI = document.getElementById('capaInfoForms');
 	if (capaI == undefined) return;
	
	// tama?os de la ventana y la pagina
	var scroller = document.documentElement.scrollTop;
	var ventanaX = document.documentElement.clientWidth;
	var ventanaY = document.documentElement.clientHeight;
	var capaY = document.getElementById('contenidor').offsetHeight;
	
	// colocamos el texto adecuado
	capaI.innerHTML = mensaje;
	
	// mostramos, miramos su tama?o y centramos la capaInfo con respecto a la ventana
	capaI.style.display = 'block';
	capaInfoX = capaI.offsetWidth;
	capaInfoY = capaI.offsetHeight;
	with (capaI) {
		style.left = parseInt((ventanaX-capaInfoX)/2) + 'px';		
		if(document.all) style.top = parseInt(scroller+(ventanaY-capaInfoY)/2) + 'px';
		else style.top = parseInt((ventanaY-capaInfoY)/2) + 'px';
		/*
		if(document.all) style.filter = "alpha(opacity=0.4)";
		else style.MozOpacity = 0.4;
		*/
		style.position = 'fixed';
		style.display = 'block';				
	}						
 }
 
 function ocultarCapaEnviando(){ 
	
	// Creamos capa de fondo
 	Fondo.destruir();

	// Ocultamos capa enviando 
	 var capaI = document.getElementById('capaInfoForms');	
	 if (capaI == undefined) return;
 	 capaI.style.display = 'none'; 	 	  	 
 }
 
 
 // Capa de fondo para evitar acceder a controles mientras
 // se ejecutan javascript y ajax -> solo xa firefox
  var Fondo = {

            crear: function() {
						
						var detect = navigator.userAgent.toLowerCase();
						if (detect.indexOf('firefox') ==-1) return;
						 
						
                        opacidad = 30; // por ciento

                        if (document.getElementById('fondo') == null) {

                                   // creamos capa por encima

                                   fondoC = document.createElement('div');

                                   fondoC.setAttribute('id','fondo');

                                   document.body.appendChild(fondoC);

                        }

                        // capturamos tamanyos

                        var ventanaX = document.documentElement.clientWidth;

                        var ventanaY = document.documentElement.clientHeight;

                        var escritorioX = document.getElementById('contenidor').offsetWidth;

                        var escritorioY = (document.body.offsetHeight > document.documentElement.offsetHeight) ? document.body.offsetHeight : document.documentElement.offsetHeight;

                        fondoX = (ventanaX > escritorioX) ? ventanaX : escritorioX;

                        fondoY = (ventanaY > escritorioY) ? ventanaY : escritorioY;

                        // mostramos el fondo

                        fondoDiv = document.getElementById('fondo');

                        fondoDiv.style.display = 'block';

                        if(fondoDiv.style.opacity === undefined) fondoDiv.style.filter = "alpha(opacity="+opacidad+")";

                        else fondoDiv.style.opacity = opacidad/100;

                        fondoDiv.style.width = fondoX + 'px';

                        fondoDiv.style.height = fondoY + 'px';

            },

            destruir: function() {

						var detect = navigator.userAgent.toLowerCase();
						if (detect.indexOf('firefox') ==-1) return;

                        if (document.getElementById('fondo') != null) {

                                   document.body.removeChild(document.getElementById('fondo'));

                        }

            }

};

 
// -- INDRA: FIN
