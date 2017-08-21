	function obrirRecercaAv(obj) {
		capaAv = document.getElementById('recercaAv');
		if(capaAv.style.display == 'block') {
			capaAv.style.display = 'none';
			obj.parentNode.innerHTML = '<a href="javascript:void(0);" onclick="obrirRecercaAv(this);">[+]</a>';
		} else {
			capaAv.style.display = 'block';
			obj.parentNode.innerHTML = '<a href="javascript:void(0);" onclick="obrirRecercaAv(this);">[-]</a>';
		}
	}
	

	/* seleccionar ítem d'una tabla */
	function selecItemTabla(obj) {
		clase = obj.className;
		obj.className = 'perDamunt';
		obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
	}

	function TrimString( str )
    {
        var tmp = ( str || "" ).toString();

        var reTrimRight = / +$/;
        var reTrimLeft = /^ +/;

        tmp = tmp.replace( reTrimRight, "" );
        tmp = tmp.replace( reTrimLeft, "" );
        return tmp;
    }
    
	function TrimField(id)
	{
			var f = document.getElementById(id);
			f.value = TrimString(f.value);
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

    	valor = cif.toUpperCase();
		
		var patronCif = "^[ABCDEFGHJKLMNPQRSUVW]{1}[0-9]{7}([0-9]||[ABCDEFGHIJ]){1}$";
		var regExp=new RegExp(patronCif);
		if (!regExp.test(valor)) {
			return false;
		}
		
		var codigoControl = valor.substring(valor.length - 1, valor.length);
		
		var v1 = [ 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 ];
		var v2 = [ "J", "A", "B", "C", "D", "E", "F", "G", "H", "I" ];
		
		var suma = 0;
        for (i = 2; i <= 6; i += 2) {
        	suma += v1[parseInt(valor.substring(i - 1, i), 10)];
            suma += parseInt(valor.substring(i, i + 1));
        }
		
        suma += v1[parseInt(valor.substring(7, 8))];
        suma = (10 - (suma % 10));
        if (suma == 10) {
            suma = 0;
        }
        var letraControl = v2[suma];
        res = (codigoControl == (suma + "") || codigoControl.toUpperCase() == letraControl);
        return res;
    }
    
    function validaPasaporte ( pasaporte ){
    	
    	valor = pasaporte.toUpperCase();
    	
    	var patronPasaporte = "^[A-Z]{3}/.{1,20}$";
		var regExp=new RegExp(patronPasaporte);
		if (!regExp.test(valor)) {
			return false;
		}
    }
    
    function esNIE(temp){
		if (/^[XYZ]/.test(temp)) return true;
		return false;
	}

 
	function rellenarCerosIzquierda(cadena,posiciones){
    	if (cadena.length==posiciones) return cadena;
        var cadenaDevolver="";
        for (i=cadena.length;i<posiciones;i++)
        	cadenaDevolver+="0";
        cadenaDevolver+=cadena;
        return cadenaDevolver;
	}

 

	function quitamosCerosCadena(cadena){
    	var eliminar=true;
        var cadenaADevolver="";
        for (var i=0;i<cadena.length;i++){
        	if (cadena.charAt(i)!='0')
            	eliminar=false;   
            if (!eliminar){
            	cadenaADevolver+=cadena.charAt(i);
            }
        }
        return cadenaADevolver;
	}

    function validaNIE(temp){
    	var letraPrincipio=temp.charAt(0);
        var letraFinal=temp.charAt(temp.length-1);
        var numero=temp.substring(1,temp.length-1);
        var numeroDigitos=(/^[X]/.test(temp))?7:8;
        if (isNaN(letraFinal)){
        	numero=quitamosCerosCadena(numero);
        }else{
        	var letraFinal="";
            var numero=temp.substring(1,temp.length);
            numero=quitamosCerosCadena(numero);
        }
        temp=letraPrincipio+numero+letraFinal;
        if (esNIE(temp)){
        	var cadenadni="TRWAGMYFPDXBNJZSQVHLCKET";
            var v1 = new Array(0,2,4,6,8,1,3,5,7,9);
            var temp1=temp.substr(1,8);
            posicion = temp1 % 23; /*Resto de la division entre 23 es la posicion en la cadena*/
            letra = cadenadni.substring(posicion,posicion+1);
            if (/^[X]/.test(temp) && (parseInt(numero)<1 || parseInt(numero)>9999999)){ //Es menos de 9 d?gitos, a?adimos la letra
                a.value=a.value+letra ;
                return false;
            }else if (/^[Y]/.test(temp) && (parseInt(numero)<10000000 || parseInt(numero)>19999999)){ //Es menos de 9 d?gitos, a?adimos la letra
                a.value=a.value+letra ;
                return false;
            }else if (/^[Z]/.test(temp) && (parseInt(numero)<20000000 || parseInt(numero)>29999999)){ //Es menos de 9 d?gitos, a?adimos la letra
                a.value=a.value+letra ;
                return false;
            }else{ //Tiene los 9 d?gitos, comprobamos si la letra esta bien
            	var temp1=numero;
                posicion = temp1 % 23; /*Resto de la division entre 23 es la posicion en la cadena*/
                letra = cadenadni.charAt(posicion);
                if (letraFinal==""){
                    return true;
                }
                var letranie=letraFinal;
                if (letra != letranie){
                    return false;                              
                }
                return true;
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
       
    function detallePago(codigo,clave,idioma){
    	if((codigo == null) || (codigo == '')) return;
    	document.location = "detallePago.do?codigo=" + codigo + "&clave=" + clave + "&idioma=" + idioma;
    }
    
    function minlength(valor,length)
    {
      if (valor.length < length) return true;
		return false;
    }
    
    function isAllDigits(argvalue) {
        argvalue = argvalue.toString();
        var validChars = "0123456789";
        var startFrom = 0;
                
        for (var n = 0; n < argvalue.length; n++) {
           if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
        }

        return true;
    }
    
    function esHora(valor)
    {
    	if(!isAllDigits(valor)) return false;
    	hora = parseInt(valor);
    	if (isNaN(hora) || hora > 23 || hora < 0) return false;
    	return true;
    }
    
    function esMinutos(valor)
    {
    	if(!isAllDigits(valor)) return false;
    	minutos = parseInt(valor);
    	if (isNaN(minutos) || minutos > 59 || minutos < 0) return false;
    	return true;
    }
    
    function validaClavePersistenciaNif(form)
    {
	    	var sn = document.getElementById('nivelAutenticacion');
	    	var nivel = sn.options[sn.selectedIndex].value;
	    	if (nivel == 'A')
			{
				var cp = document.getElementById('clavePersistencia');
				if(cp.value == null || cp.value == '' )
				{
					cp.focus();
					return "error.clavePersistencia";
				}
		        var filter  = /^\w{8}\-\w{8}\-\w{8}$/;
	    	    if ( !filter.test( cp.value ) )
	        	{
					cp.focus();
	        		return "error.clavePersistenciaValida";
	        	}
			}
			else
			{
				var un = document.getElementById('usuarioNif');
				if(un.value == null || un.value == '' )
				{
					un.focus();
					return "error.nif";
				}
				
				// Pasamos NIF a mayusculas
				un.value = un.value.toUpperCase();

				if(!validaNIF(un.value))
				{
					if(!validaCIF(un.value))
					{									
						if(!validaNIE(un.value))
						{			
							if(!validaPasaporte(un.value))
							{
								un.focus();
								return "error.nifValido";
							}
						}
					}					
				}
			}
			return "";
    }
    
