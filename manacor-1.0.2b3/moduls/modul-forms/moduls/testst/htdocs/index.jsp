<%@ page import="org.ibit.rol.form.testst.UrlForms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="ca" xml:lang="ca">
<script>

function loadConfig(url)
{
    var result = '';
    result += '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><configuracion><datos>';
    result += '<idioma>' + document.forms[0].idioma.value + '</idioma>';
    result += '<modelo>' + document.forms[0].modelo.value + '</modelo>';
    result += '<version>' + document.forms[0].version.value + '</version>';
    result += '<codigoPerfil>CAIB_AZUL</codigoPerfil>';
    result += '<layout>caib</layout>';
    result += '<urlSisTraOK>' + url + '/form-test/recepcioForm</urlSisTraOK>';
    result += '<urlRedireccionOK>' + url + '/form-test/continuarTramitacio</urlRedireccionOK>';
    result += '<urlSisTraCancel>' + url + '/form-test/cancelarForm</urlSisTraCancel>';
    result += '<urlRedireccionCancel>' + url + '/form-test/continuarCancelacio</urlRedireccionCancel>';
    result += '<nomParamXMLDatosIni>xmlInicial</nomParamXMLDatosIni>';
    result += '<nomParamXMLDatosFin>xmlFinal</nomParamXMLDatosFin>';
    result += '<nomParamTokenRetorno>TOKEN</nomParamTokenRetorno></datos>';
    result += '<propiedades>';
    result += '<propiedad><nombre>aplicacion</nombre><valor>Asistente de Tramitación</valor></propiedad>';
	result += '<propiedad><nombre>usuario</nombre><valor>Nombre Apellido1 Apellido2</valor></propiedad>';
	result += '<propiedad><nombre>tramite</nombre><valor>Nombre del Trámite</valor></propiedad>';
	result += '<propiedad><nombre>formulario</nombre><valor>NOMBRE DEL FORMULARIO</valor></propiedad>';
	result += '</propiedades>';
    result += '<bloqueo>';
	var options = document.forms[0].bloqueos.options;
	for(i=0; i<options.length; i++)
	{
	     var opcion = options[i].text;
         result += '<xpath>' + opcion + '</xpath>';
	}
    result += '</bloqueo>';
	result += '</configuracion>';
    document.forms[0].xmlConfig.value = result;
}

function loadDatos()
{
    var result = '';
	var opts = document.forms[0].snodos.options;
	for(i=0; i<opts.length; i++)
	{
	     var opcion = opts[i].text;
         result += opcion;
         if(i < (opts.length - 1))
         {
            result += '#';
         }
	}
    document.forms[0].nodos.value = result;
    result = '';
	opts = document.forms[0].svalores.options;
	for(i=0; i<opts.length; i++)
	{
	     var opcion = opts[i].text;
         result += opcion;
         if(i < (opts.length - 1))
         {
            result += '#';
         }
	}
    document.forms[0].valores.value = result;
}

function enviar(url)
{
    loadConfig(url);
    loadDatos();
    document.forms[0].submit();
}
</script>
<script type="text/javascript">

function Add() {
    var formObject = document.forms[0]
    if (formObject.optionText.value!="") {
        addOption(formObject.bloqueos,formObject.optionText.value,formObject.optionText.value)
    } else {
        alert("Debe rellenar el campo Xpath")
    }
} 
function addOption(selectObject,optionText,optionValue) {
    var optionObject = new Option(optionText,optionValue)
    var optionRank = selectObject.options.length
    selectObject.options[optionRank]=optionObject
}

function Clear()
{
    document.forms[0].bloqueos.options.length = 0;
}

function Remove()
{
    document.forms[0].bloqueos.remove(document.forms[0].bloqueos.selectedIndex);
}

</script>
<script type="text/javascript">

function AddNodo() {
    var formObject = document.forms[0]
    if (formObject.nodo.value!="" && formObject.valor.value!="") {
        addOption(formObject.snodos,formObject.nodo.value,formObject.nodo.value)
        addOption(formObject.svalores,formObject.valor.value,formObject.valor.value)
    } else {
        alert("Debe rellenar el campo Nodo y Valor");
    }
} 

function ClearNodos()
{
    document.forms[0].snodos.options.length = 0;
    document.forms[0].svalores.options.length = 0;
}

function RemoveNodo()
{
    var index = document.forms[0].nodos.selectedIndex;
    document.forms[0].snodos.remove(document.forms[0].snodos.selectedIndex);
    document.forms[0].svalores.remove(document.forms[0].svalores.selectedIndex);
}

function select(objectId)
{
   if(objectId == 'nodos')
   {
      document.forms[0].svalores.selectedIndex = document.forms[0].snodos.selectedIndex;
   }
   else
   {
      document.forms[0].snodos.selectedIndex = document.forms[0].svalores.selectedIndex;
   }
}

</script>
<head>
    <title>SISTEMA TRAMITACIÓN</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
    <style type="text/css">
        body {
            background-color : #E6ECF3;
            font-family: sans-serif;
            font-size: 11px;
            color: black;
        }
        h1 {
            font-family: sans-serif;
	        font-size: 18px;
	        color: #777777;
        }
        li {
            margin-top: 10px;
        }
        a {
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>

</head>
<body>
    <h1>Abrir Formulario Telemáticamente</h1>
    <hr />
    <form action="/form-test/caibRedirectForms.jsp" method="post" target="Nueva">
        <table>
        <tr style="background-color:#006699;color:#CCCCCC;text-align:center;height::30px;font-size:14px">
	        <td><strong>Configuración</strong></td>
	        <td><strong>Datos</strong></td>
        </tr>
        <tr>
	        <td style="background-color:#B8D0D8">
    	       <table>
        		   <tr>
			           <td><label><strong>Idioma</strong></label></td>
			           <td><input id="idioma" type="text" name="idioma" /></td>
		           </tr>
		           <tr>
			           <td><label><strong>Modelo</strong></label></td>
			           <td><input id="modelo" type="text" name="modelo" /></td>
		           </tr>
		           <tr>
		    	       <td><label><strong>Versión</strong></label></td>
			           <td><input id="version" type="text" name="version" /></td>
		           </tr>
		           <tr>
		               <td colspan="2">
						   <table>
						   		<tr>
							        <td><h4>Bloqueos</h4></td>
						   		</tr>
						   		<tr>
							        <td>
								        <select name="bloqueos" size="5" style="table-layout:fixed;width:320px;background-color:#CCCCCC">
								        </select>
							        </td>
						   		</tr>
						   		<tr>
							        <td><strong>Elemento:</strong><label style="font-size:10px;font-weight:bold;color:#006633;margin-left:50px">Ej: /FORMULARIO/convocatoria/fecha</label></td>
						   		</tr>
						   		<tr>
							        <td>
								        <input type="text" name="optionText" size="40"/>
							        </td>
						   		</tr>
						   		<tr>
							        <td>
        								<input type="button" value="Añadir" onclick="Add()"/>
								        <input type="button" value="Limpiar" onclick="Clear()"/>
								        <input type="button" value="Eliminar" onclick="Remove()"/>
        					        </td>
						   		</tr>
						   </table>
     					</td>
     				</tr>
     			</table>
     		</td>
     		<td style="background-color:#B8D0D8">
     			<table>
	     			<tr style="text-align:center">
		     			<td><strong>Nodo</strong>
		     			</td>
		     			<td><strong>Valor</strong>
		     			</td>
	     			</tr>
     				<tr>
     					<td>
					        <select name="snodos" size="12" style="table-layout:fixed;width:400px;background-color:#CCCCCC" onchange="javascript:select('nodos')">
					        </select>
     					</td>
     					<td>
					        <select name="svalores" size="12" style="table-layout:fixed;width:200px;background-color:#CCCCCC "  onchange="javascript:select('valores')">
					        </select>
     					</td>
     				</tr>
					<tr>
						<td><strong>Nodo:</strong><label style="font-size:10px;font-weight:bold;color:#006633;margin-left:50px">Ej: /FORMULARIO/convocatoria/fecha</label></td>
						<td><strong>Valor:</strong></td>
					</tr>
     				<tr>
     					<td><input type="text" name="nodo" size="50"/></td>
     					<td><input type="text" name="valor"/></td>
     				</tr>
     				<tr colspan="2">
     					<td>
					        <input type="button" value="Añadir" onclick="AddNodo()"/>
					        <input type="button" value="Limpiar" onclick="ClearNodos()"/>
					        <input type="button" value="Eliminar" onclick="RemoveNodo()"/>
     					</td>
     				</tr>
     			</table>
     		</td>
        </tr>
        <tr>
        	<td colspan="2" style="text-align:center;background-color:#CCCCCC">
		        <hr />
		        <input type="button" name="iniciar" value="Iniciar" onclick="javascript:enviar('<%=UrlForms.getUrl()%>');" style="font-size:16px;font-weight:bold"/>
        	</td>
        </tr>
     </table>
     <input type="hidden" id="nodos" name="nodos"/>
     <input type="hidden" id="valores" name="valores"/>
     <input type="hidden" id="xmlConfig" name="xmlConfig" />
    </form>
</body>
</html>