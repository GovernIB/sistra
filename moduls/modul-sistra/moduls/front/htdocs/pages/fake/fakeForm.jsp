<%@ page language="java" contentType="text/html; charset=ISO-8859-15" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean"%>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic"%>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<bean:define id="urlCancelacion" name="fakeFormForm" property="urlCancelacion" type="java.lang.String"/>
<bean:define id="identificador" name="fakeFormForm" property="modelo" type="java.lang.String"/>
<% identificador = "form" + identificador + ".jsp"; %>
<script type="text/javascript">
<!--

	
	
	
	function TrimString( str )
    {
        var tmp = ( str || "" ).toString();

        var reTrimRight = / +$/;
        var reTrimLeft = /^ +/;

        tmp = tmp.replace( reTrimRight, "" );
        tmp = tmp.replace( reTrimLeft, "" );
        return tmp;
    }

    function isEmptyString ( cad )
    {
    	if (cad != null && (TrimString(cad)).length != 0)
    		return false;
    	return true;
    }
    
    function isEmptyObject ( obj )
    {
       isEmpty = true;

       switch(obj.type)
       {
          case "hidden":
          case "password":
          case "text":
          case "textarea":
             isEmpty = isEmptyString ( obj.value );
           break;

          case "select-multiple":
          case "select-one":

           // The first option is the 'Select option value' option so we do not consider it
           for(j = 1; j < obj.options.length; j++)
           {
            if(obj.options[j].selected)
            {
                isEmpty = false;
                break;
            }
           }
           break;

          case "radio":
          case "checkbox":
           if(obj.checked)
             isEmpty = false;

           break;

          default:
          // Cuando es un array de radios o checkboxes
           for(j = 0; j < obj.length; j++)
           {
            if(obj[j].checked)
            {
                isEmpty = false;
                break;
            }
           }

          break;

       }

       return isEmpty;
    }
	
	function caracter(cadena,tipo)
    {
        var c=0;
        letras="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ñÑàèìòùáéíóúÁÉÍÓÚÀÈÌÒÙäëïöüÄËÏÖÜçÇ-'âêîôûÂÊÎÔÛ";
        numero="0123456789";
        signos_puntuacion=".,;:";
        caracter_control="§!|ºª\"\\·$%&/()=?¿¡{}+*'[]_,;:<>";
        empresa=letras+signos_puntuacion+numero+caracter_control;
        numero_letra=numero+letras;

        if (tipo == "es_numero")
        {
            for (c=0;c<cadena.length;c++)
            {
                if (numero.indexOf(cadena.charAt(c)) == -1)
                    return false;
            }
        }
        if (tipo == "es_letra")
        {
            for (c=0;c<cadena.length;c++)
            {
                if (letras.indexOf(cadena.charAt(c)) == -1)
                    return false;
            }
        }
        if (tipo == "es_empresa")
        {
            for (c=0;c<cadena.length;c++)
            {
                if (empresa.indexOf(cadena.charAt(c)) == -1)
                    return false;
            }
        }
        if (tipo == "numero_letra")
        {
            for (c=0;c<cadena.length;c++)
            {
                if (numero_letra.indexOf(cadena.charAt(c)) == -1)
                    return false;
            }
        }
        return true;
    }	

	function esEmail( email )
    {
        email = email.replace( / +$/, "" ).replace( /^ +/, "" );
        var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9])+$/;
        return filter.test( email );
    }

    function esTelefono( telefono )
    {
        telefono = telefono.replace( / +$/, "" ).replace( /^ +/, "" );
        var filter  = /^[0-9\.\-\(\)\+ ]+$/;
        return filter.test( telefono ) && telefono_contacto( telefono );
    }
    
    function esNIF (numero, letra)
    {
        letras = new Array("T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E");
        indice = numero-(parseInt(numero/23)*23);
        if(letra.toUpperCase() != letras[indice]) return false;
        return true;
    }

    function validaNIF (cadenaNIF)
    {
        numero = cadenaNIF.substr(0, cadenaNIF.length-1);
        letra = cadenaNIF.substr(cadenaNIF.length-1, 1);
        if(!caracter(numero,"es_numero") || !caracter(letra,"es_letra"))
        {
            return false;
        }
        numero = cadenaNIF.substr(0, cadenaNIF.length-1);
        letra = cadenaNIF.substr(cadenaNIF.length-1, 1);
        if(!esNIF(numero,letra))
        {
            return false;
        }
        return true;
    }

    function validaCIF( cif )
    {

        cif = cif.toUpperCase();

        if(cif.length != 9){
             alert("El cif debe de tener nueve dígitos");
             return false;
        }

        letrasComienzo1 = "ABCDEFGHN";
        letrasComienzo2 = "PQS";
        letrasFin = "JABCDEFGHI";
        numeros = "0123456789";

        caracter0 = cif.substring(0,1);
        caracter8 = cif.substring(8,9);

        if(letrasComienzo1.indexOf(caracter0) != -1){
            if(letrasFin.indexOf(caracter8) != -1)
                terminaNumero = false;
            else{
                if(numeros.indexOf(caracter8) != -1){
                    terminaNumero = true;
                }else{
                    alert("CIF erróneo");
                    return false;
                }
            }
        }
        else{
            if(letrasComienzo2.indexOf(caracter0) != -1){
                if(letrasFin.indexOf(caracter8) != -1)
                    terminaNumero = false;
                else{
                    alert("CIF erróneo");
                    return false;
                }
            }
            else{
                 alert("CIF erróneo");
                 return false;
                }
        }

        for(i=1; i<=7; i++){
            if(numeros.indexOf(cif.substring(i, i+1)) == -1)
            {
             alert("CIF erróneo");
             return false;
            }
        }


        R1 = parseInt(cif.substring(2,3)) +
             parseInt(cif.substring(4,5)) +
             parseInt(cif.substring(6,7));


        R21 = 2 * parseInt(cif.substring(1,2));
        if(R21 > 9)
            R21 = 1 + R21 % 10;

        R22 = 2 * parseInt(cif.substring(3,4));
        if(R22 > 9)
            R22 = 1 + R22 % 10;

        R23 = 2 * parseInt(cif.substring(5,6));
        if(R23 > 9)
            R23 = 1 + R23 % 10;

        R24 = 2 * parseInt(cif.substring(7,8));
        if(R24 > 9)
            R24 = 1 + R24 % 10;

        DC = (10 - ((R1 + R21 + R22 + R23 + R24) % 10)) % 10;

        if(terminaNumero){
            if(parseInt(caracter8) == DC)
                return true;
            else{
                  alert("CIF erróneo");
                  return false;
                }
        }
        else{
            if(caracter8 == letrasFin.substring(DC, DC+1))
                return true;
            else{
                 alert("CIF erróneo");
                 return false;
                }
        }
    }
    
    function esCodigoPostal( codigo )
    {
      cod = new String(codigo);

      if ((cod.length > 5)||(cod.length < 5))
      {
            alert ("El código postal debe tener 5 dígitos");
            return false;
      }
      if ( !caracter(cod,"es_numero") )
      {
            alert ("El código postal debe estar formado por números");
            return false;
      }
      indice = parseFloat(cod.substring(0,2));
      if ((indice < 1) || (indice > 52))
      {
            alert ("El código postal no corresponde a ninguna provincia española");
            return false;
      }
      return true;
    }
-->    
</script>
		<h2>Rellenar formulario</h2>
		<p class="ultimo">Cumplimente de forma adecuada los datos del formulario.</p>
		<div id="datosTramiteImprimir">
		<html:form action="/protected/fakeRetornarATramitacion">
			<html:hidden name="fakeFormForm" property="ID_INSTANCIA"/>
			<html:hidden name="fakeFormForm" property="instancia" />
			<html:hidden name="fakeFormForm" property="identificador" />
			<html:hidden name="fakeFormForm" property="urlRetorno" />
			<tiles:insert page="<%= identificador  %>" />
			</div>
			<p class="centrado"><input name="imprimirSolicitudBoton" id="imprimirSolicitudBoton" type="button" value="Enviar" onclick="javascript:validaFormulario( this.form );"/> <input name="cancelarEnvio" id="cancelarEnvio" type="button" value="Cancelar" onclick="javascript:this.form.action='<html:rewrite href="<%= urlCancelacion %>" />';this.form.submit()"/></p>
			<div class="separacio"></div>
		</html:form>	