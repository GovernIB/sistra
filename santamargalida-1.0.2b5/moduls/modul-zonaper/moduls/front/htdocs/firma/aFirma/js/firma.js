/*
 * Depende de instalador.js y de constantes.js (opcional)
 *
 * initialize():
 *      Vuelve el applet a su estado inicial
 *
 * getEstructuraNodos():
 *      Devuelve una cadena que contiene los nombres de los firmantes de cada firma, co-firma y contra-firma. Los nombres van separados por '\n' y
 *      empiezan por tantos '\t' como nivel ocupe el nodo en el arbol. P. ej, para la siguiente estructura de nodos:
 *       +---> A
 *       | +---> C
 *       | +---> D
 *       +---> B
 *       +---> E
 *      El documento est? co-firmado por A, B y E, y la co-firma de A, est? contra-firmada por C y D. La cadena que devolver?a getEstructuraNodos() es
 *      la siguiente: "A\n\tC\n\tD\nB".
 *
 * firmar(), coFirmar(), contraFirmarNodos([cadenaDeIndices]), contraFirmarArbol(), contraFirmarHojas(), contraFirmarFirmantes([cadenaDeFirmantes]):
 *      Inician los respectivos procesos de firma
 *		-> cadenaDeIndices es una cadena de enteros separados por '\n' que indican qu? nodos contraFirmar. Los indices(0, 1, ...) est?n referidos al 
 *        resultado de getEstructuraNodos(). Por ejemplo, para firmar los nodos 0 y 4 la cadena ser?a '0\n4'
 *      -> cadenaDeFirmantes es una cadena de nombres de firmantes separados por '\n' que indican qu? firmantes contrafirmar. Los nombres de los
 *        posibles firmantes se obtienen de getEstructuraNodos().
 *
 */


/**
 * Establece los valores de firma. Ver constantes.js.
 */ 
function configuraFirma()
{
	var command	= "";
	if( signatureAlgorithm != undefined )
	{
		command	+= "clienteFirma.setSignatureAlgorithm('"+signatureAlgorithm+"');";
	}
	if( signatureFormat != undefined )
	{
		command	+= "clienteFirma.setSignatureFormat('"+signatureFormat+"');";
	}
	if( showErrors != undefined )
	{
		command	+= "clienteFirma.setShowErrors('"+showErrors+"');";
	}
	if( certFilter != undefined )
	{
		command	+= "clienteFirma.setCertFilter('"+certFilter+"');";
	}
	
	whenTry("clienteFirmaCargado == true", command);
}

/**
 * Prepara el cliente para iniciar un proceso de firma.
 */
function initialize()
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	whenTry("clienteFirmaCargado == true", "clienteFirma.initialize(); configuraFirma();", "No se ha podido iniciar el Applet de firma.");
}

function firmar()
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	whenTry("clienteFirmaCargado == true", "clienteFirma.sign()", "No se ha podido iniciar el Applet de firma.");
}

function coFirmar()
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	whenTry("clienteFirmaCargado == true", "clienteFirma.coSign()", "No se ha podido iniciar el Applet de firma.");
}

function contraFirmarNodos(cadenaDeIndices)
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	var command	= "clienteFirma.counterSignIndexes()";
	if(cadenaDeIndices != undefined)
	{
		command	= "clienteFirma.setSignersToCounterSign('"+cadenaDeIndices+"'); " + command;
	}
	
	whenTry("clienteFirmaCargado == true", command, "No se ha podido iniciar el Applet de firma.");
}

function contraFirmarArbol()
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	whenTry("clienteFirmaCargado == true", "clienteFirma.counterSignTree()", "No se ha podido iniciar el Applet de firma.");
}

function contraFirmarHojas()
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	whenTry("clienteFirmaCargado == true", "clienteFirma.counterSignLeafs()", "No se ha podido iniciar el Applet de firma.");
}

function contraFirmarFirmantes(cadenaDeFirmantes)
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	var command	= "clienteFirma.counterSignSigners()";
	if(cadenaDeIndices != undefined)
	{
		command	= "clienteFirma.setSignersToCounterSign('"+cadenaDeFirmantes+"'); " + command;
	}
	
	whenTry("clienteFirmaCargado == true", command, "No se ha podido iniciar el Applet de firma.");
}

function getEstructuraNodos()
{
	if(clienteFirma == undefined)
	{
		cargarAppletFirma();
	}
	
	// Esperamos a que el cliente est? cargado
	waitFor("clienteFirmaCargado == true", 10000);
	
	if(clienteFirmaCargado)
	{
		return clienteFirma.getSignersStructure();
	}
	else
	{
		alert("No se ha podido iniciar el Applet de firma.");
	}
}
