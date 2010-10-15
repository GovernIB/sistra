
/**
 * Establece los valores del applet para cifrado. Ver constantes.js.
 */ 
function configuraCifrador()
{
    if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	var command	= "";
	if( cipherAlgorithm != undefined ){
		command	+= "clienteFirma.setCipherAlgorithm('"+cipherAlgorithm+"');";
	}else{
	    command	+= "clienteFirma.setCipherAlgorithm('AES');";
	}
	if( showErrors != undefined )
	{
		command	+= "clienteFirma.setShowErrors('"+showErrors+"');";
	}
	if (key != undefined){
	    command += "clienteFirma.setKey('"+key+"');";
	}else{
	    if( keyMode != undefined ){
		    command	+= "clienteFirma.setKeyMode('"+keyMode+"');";
	    }else{
	        command	+= "clienteFirma.setKeyMode('AUTOGENERATE');";
	    }
	}

	whenTry("clienteFirmaCargado == true", command);
}

/**
 * Prepara el cliente para cifrar o descifrar.
 */
function initialize(){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.initialize(); configuraCifrador();", "No se ha podido iniciar el Applet de firma.");
}

function establecerKey(clave){
    if(clienteFirma==undefined){
        cargarAppletFirma();
    }
    whenTry("clienteFirmaCargado == true", "clienteFirma.setKey("+clave+")", "No se ha podido iniciar el Applet de firma.");
}

function establecerPassword(clave){
    if(clienteFirma==undefined){
        cargarAppletFirma();
    }
    whenTry("clienteFirmaCargado == true", "clienteFirma.setPassword("+clave+")", "No se ha podido iniciar el Applet de firma.");
}

function cifrarDatos(datos){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.cipherData('"+datos+"')", "No se ha podido iniciar el Applet de firma.");
}

function descifrarDatos(datos){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.decipherData('"+datos+"')", "No se ha podido iniciar el Applet de firma.");
}

function cifrarFichero(uri){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.cipherFile('"+uri+"')", "No se ha podido iniciar el Applet de firma.");
}

function obtenerResultadoCifrado(){
    if(clienteFirma == undefined){
		return null;
	}
	var out=clienteFirma.getCipherData();
	return out;
}

function obtenerResultadoPlano(){
    if(clienteFirma == undefined){
		return null;
	}
	var out=clienteFirma.getPlainData();
	return out;
}

function descifrarFichero(uri){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.decipherFile('"+uri+"')", "No se ha podido iniciar el Applet de firma.");
}

function cambiaAlgoritmo(alg){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.setCipherAlgorithm('"+alg+"')", "No se ha podido iniciar el Applet de firma.");
	alert(clienteFirma.getCipherAlgorithm());
}

function cambiaModoDeClave(modo){
	if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.setKeyMode('"+modo+"')", "No se ha podido iniciar el Applet de firma.");
    //alert(clienteFirma.getKeyMode());
}

function obtenerAlgoritmo(){
	if(clienteFirma == undefined){
		return 'No definido';
	}
	return clienteFirma.getCipherAlgorithm();
}

function recuperaEnvelopedDataIE(data){
    if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	var EnvelopedData = new ActiveXObject("CAPICOM.EnvelopedData");
	var Utilities = new ActiveXObject("CAPICOM.Utilities");
    EnvelopedData.Decrypt(data);
    var b64ResMSIE=Utilities.Base64Encode(EnvelopedData.Content);
    whenTry("clienteFirmaCargado == true", "return clienteFirma.getTextFromBase64('"+b64ResMSIE+"')", "No se ha podido iniciar el Applet de firma.");
}

function recuperaEnvelopedDataMozilla(data){
    if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.setData('"+data+"')", "No se ha podido iniciar el Applet de firma.");
	if(clienteFirma.recoverCMS()){
		return clienteFirma.getData();
	}else{
	    alert("Error el la recuperaci?n del CMS");
	    return "";
	}
}
	
function recuperaEnvelopedData(data){
    if(_ie)
        return recuperaEnvelopedDataIE(data);
    else
        return recuperaEnvelopedDataMozilla(data);
}

function recuperaEncryptedData(data){
    if(clienteFirma == undefined){
		cargarAppletFirma();
	}
	whenTry("clienteFirmaCargado == true", "clienteFirma.setData('"+data+"')", "No se ha podido iniciar el Applet de firma.");
	if(clienteFirma.recoverCMS()){
		return clienteFirma.getData();
	}else{
	    alert("Error el la recuperaci?n del CMS");
	    return "";
	}
}
    

