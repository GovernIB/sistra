/*******************************************************************************
 * Ruta al los instalables.                                                    *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var baseDownloadURL;

/*******************************************************************************
 * Ruta al instalador.                                                         *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var base; // Valor por defecto

/*******************************************************************************
 * Algoritmo de firma. Puede ser 'sha1WithRsaEncryption' o                     *
 * 'md5WithRsaEncryption'. Se estable al llamar a configuraFirma en firma.js   *
 ******************************************************************************/
var signatureAlgorithm = 'sha1WithRsaEncryption'; // Valor por defecto

/*******************************************************************************
 * Formato de firma. Puede ser 'CMS', 'XADES', 'XMLDSIGN' o 'NONE'.            *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var signatureFormat = 'CMS'; // Valor por defecto

/*******************************************************************************
 * Mostrar los errores al usuario. Puede ser 'true' o 'false'.                 *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var showErrors = 'true'; // Valor por defecto

/*******************************************************************************
 * Ruta al los instalables.                                                    *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var baseDownloadURL;

/*******************************************************************************
 * Ruta al instalador.                                                         *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var base; // Valor por defecto

/*******************************************************************************
 * Algoritmo de firma. Puede ser 'sha1WithRsaEncryption' o                     *
 * 'md5WithRsaEncryption'. Se estable al llamar a configuraFirma en firma.js   *
 ******************************************************************************/
var signatureAlgorithm = 'sha1WithRsaEncryption'; // Valor por defecto

/*******************************************************************************
 * Formato de firma. Puede ser 'CMS', 'XADES', 'XMLDSIGN' o 'NONE'.            *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var signatureFormat = 'CMS'; // Valor por defecto

/*******************************************************************************
 * Mostrar los errores al usuario. Puede ser 'true' o 'false'.                 *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var showErrors = 'true'; // Valor por defecto

/*******************************************************************************
 * Filtro de certificados (expresi?n que determina qu? certificados se le      *
 * permite elegir al usuario). Ver la documentaci?n.                           *
 * Se estable al llamar a configuraFirma en firma.js                           *
 *                                                                             *
 * Ejemplos:                                                                   *
 * - S?lo mostrar certificados de DNIe de firma:                               *
 * var certFilter = '{ISSUER.DN#MATCHES#{"CN=AC DNIE 00(1|2|3),OU=DNIE,'+      *
 *      'O=DIRECCION GENERAL DE LA POLICIA,C=ES"}&&{SUBJECT.DN#MATCHES#'+      *
 *      '{".*(FIRMA).*"}}}';                                                   *
 *                                                                             *
 * - S?lo mostrar certificados de la FNMT:                                     *
 * var certFilter = '{ISSUER.DN={"OU = FNMT Clase 2 CA,O= FNMT,C = ES"}}';     *
 *                                                                             *
 * - Mostrar todos los certificados menos el de validaci?n:                    *
 * var certFilter = '{SUBJECT.DN#NOT_MATCHES#{".*(AUTENTICACI?N).*"}}}'        *
 ******************************************************************************/
var certFilter; // Valor por defecto


var _info = navigator.userAgent; 
var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
var _ns = (navigator.appName.indexOf("Netscape") >= 0 && ((_info.indexOf("Win") > 0 && _info.indexOf("Win16") < 0 && java.lang.System.getProperty("os.version").indexOf("3.5") < 0) || _info.indexOf("Sun") > 0));

function cargarApplet(nombreApplet, codeApplet, archiveApplet, params, codebase)
{

	//"http://java.sun.com/products/plugin/1.4/jinstall-14-win32.cab#Version=1,4,0,mn";
	/* Creamos el applet con APPLET u OBJECT en funci?n del navegador */
	var applet;
	
	if(_ie)
	{
		applet				= document.createElement("OBJECT");
		if(codebase!=undefined)
		{
			applet.codebase		= codebase;
		}
		appendParam(applet, "classid", "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"); // ?ltima JRE instalada
	}
	else 
	{
		applet				= document.createElement("OBJECT");
		applet.type			= "application/x-java-applet;version=1.4";
		if(codebase!=undefined)
		{
			appendParam(applet, "codebase", codebase);
		}
	}

	applet.name		= nombreApplet;
    applet.id		= nombreApplet;
    applet.height	= 1;
    applet.width	= 1;
    applet.code		= codeApplet;

    appendParam(applet, "java_archive", archiveApplet);

	/* Par?metros del applet */
	for(i=0; i<params.length; i++)
	{
		appendParam(applet, params[i][0], params[i][1]);
	}
	
	document.getElementsByTagName('body')[0].appendChild(applet);
	
	return applet;
}

function cargarApplet2(nombreApplet, codeApplet, archiveApplet, archiveLibs, baseApplet,baseJars,params)
{

	//"http://java.sun.com/products/plugin/1.4/jinstall-14-win32.cab#Version=1,4,0,mn";
	/* Creamos el applet con APPLET u OBJECT en funci?n del navegador */
	var applet;
	applet				= document.createElement("OBJECT");
	//if(codebase!=undefined)
		//{
		//	applet.codebase		= codebase;
		//}
	if(_ie)
	{
		appendParam(applet, "classid", "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"); // ?ltima JRE instalada
	}
	else 
	{
		applet.type			= "application/x-java-applet;version=1.4";
	}
	
	var libs=archiveLibs.split(",");
	var modJars="";
	ruta=quitaBarra(baseJars);
	for (i=0;i<libs.length;i++){
		modJars=modJars+","+ruta+"/"+libs[i]
	}
	modJars=archiveApplet+modJars;
	applet.name		= nombreApplet;
    applet.id		= nombreApplet;
    applet.height	= 1;
    applet.width	= 1;
    applet.code		= codeApplet;

	if(baseApplet!=undefined)
		{
			appendParam(applet, "codebase", baseApplet);
		}    

    appendParam(applet, "java_archive", modJars);

	/* Par?metros del applet */
	for(i=0; i<params.length; i++)
	{
		appendParam(applet, params[i][0], params[i][1]);
	}
	
	document.getElementsByTagName('body')[0].appendChild(applet);
	
	return applet;
}

/*******************
 * M?todos propios *
 *******************/

function appendParam(parent, name, value)
{
	var param 	= document.createElement("param");
	param.name	= name;
	param.value	= value;
	parent.appendChild(param);
	
	return param;
}

function quitaBarra(src){
	var res=src;
	while (res.indexOf("\\")!=-1){
		res=res.replace("\\","/");
	}
	return res;
}
/**
 * Funciones de tiempo.
 *
 * whenTry(condicion, comando[, msgErr]) -> Ejecuta el comando cuando se cumpla la condici?n. Si no se cumple transcurrido un tiempo, y se ha especificado msgErr, se muestra un alert con msgErr.
 *
 * waitFor(condicion, millis) -> Espera un tiempo m?ximo a que se cumpla la condici?n
 */

function whenTry(condicion, comando, msgErr, intento)
{
	var whenTry;
	try
	{
		whenTry = eval(condicion);
	}
	catch(e)
	{
		whenTry = false;
	}

	if(whenTry)
	{
		eval(comando);
	}
	else
	{
		if(intento == undefined)
		{
			intento = 1;
		}
		else 
		{
			if(intento > 100)
			{
				if(msgErr != undefined)
				{
					alert(msgErr);
				}
				return false;
			}
			else
			{
				intento = intento + 1;
			}
		}
		
		if(msgErr == undefined)
		{
			setTimeout("whenTry(\""+condicion+"\", \""+comando+"\", undefined, "+intento+")", 100);
		}
		else
		{
			setTimeout("whenTry(\""+condicion+"\", \""+comando+"\", \""+msgErr+"\", "+intento+")", 100);
		}
	}
}

function waitFor(_condition, _millis)
{
	var _aux	= new Date().getTime();
	
	var _dif = new Date().getTime() - _aux;
	while( !eval(_condition) && (_dif < _millis) )
	{
		_dif = new Date().getTime() - _aux;
	}
}

/**
 * Depende de appletHelper.js, de time.js y de constantes.js [opcional].
 *
 * Si se ha definido baseDownloadURL se usa como URL base para la descarga de los instalables.
 *
 * cargarAppletFirma():
 *      Carga el applet de firma en la variable "SignApplet". El applet no est? cargado hasta que SignAppletCargado= true.
 *      Si es necesario carga el instalador e instala el applet en local.
 *
 * cargarAppletInstalador():
 *      Carga el applet instalador en la variable "instalador". No se termina de cargar hasta que instaladorCargado= true
 *
 * instalar():
 *      Carga el applet instalador (si no se ha cargado) e instala el cliente de firma en local
 *
 * actualizar():
 *      Carga el applet instalador (si no se ha cargado) y actualiza la versi?n instalada en local
 *
 * desinstalar():
 *      Carga el applet instalador (si no se ha cargado) y desinstala el cliente de firma de local
 *
 * isInstalado():
 *      Carga el applet instalador (si no se ha cargado) y devuelve true si el cliente de firma est? instalado en local (false si no).
 *
 * isActualizado():
 *      Carga el applet instalador (si no se ha cargado) y devuelve true si el cliente de firma est? actualizado (coinciden las versiones local/remota) en local (false si no).
 *
 * getDirectorioInstalacion():
 *      Carga el applet instalador (si no se ha cargado) y devuelve la ruta de instalaci?n del cliente de firma en local.
 *
 */
 
//cargarAppletInstalador();
 
var instalador;
var instaladorCargado = false; 
var SignApplet;
var SignAppletCargado = false;

var err_CargarApplet= "No se ha podido iniciar el Applet instalador."

function cargarAppletInstalador()
{


	if(instalador == undefined)
	{
		var params= new Array();
		params[0]= new Array();
		params[0][0]= "userAgent";
		params[0][1]= window.navigator.userAgent;
		params[1]= new Array();
		params[1][0]= "appName"
		params[1][1]= window.navigator.appName;
		if(baseDownloadURL)
		{
			alert("entrado"+baseDownloadURL)
			params[2]   = new Array();
			params[2][0]= "baseDownloadURL"
			params[2][1]= baseDownloadURL;
		}
	
		if(base)
			instalador = cargarApplet("instalador", "com.telventi.afirma.cliente.instalador.InstaladorCliente.class", "instaladorClienteFirmaAFirma5.jar", params, base);
		else
			instalador = cargarApplet("instalador", "com.telventi.afirma.cliente.instalador.InstaladorCliente.class", "instaladorClienteFirmaAFirma5.jar", params);
		
		whenTry("instalador.isIniciado() == true", "instaladorCargado = true");
	}
}

function cargarAppletFirma()
{
	// Comprobamos que el instalador est? cargado
	if(instalador == undefined)
	{
		cargarAppletInstalador();
		whenTry("instaladorCargado == true", "cargarAppletFirma()", "No se ha podido cargar el applet instalador");
		return;
	}
	
	// Comprobamos que el instalador est? iniciado
	if(instaladorCargado != true)
	{
		whenTry("instaladorCargado == true", "cargarAppletFirma()", "No se ha podido cargar el applet instalador");
		return;
	}
	
	// Comprobamos que el applet de firma est? instalador
	if( instalador.isInstalado() == false )
	{
		instalar();
		whenTry("instalador.isInstalado() == true", "cargarAppletFirma()", "No se ha podido instalar el applet de firma");
		return
	}
	
	// Comprobamos que est? actualizado
	if( instalador.isActualizado() == false )
	{
		actualizar();
		whenTry("instalador.isActualizado() == true", "cargarAppletFirma()", "No se ha podido actualizar el applet de firma");
		return;
	}
	
	// Cargamos el applet de firma
	if(SignApplet == undefined)
	{
		/* base para cargar las librerias */
  	codeBase 		= 'file:///' + instalador.getInstallationDirectory();
		/* Preparamos los par?metros para cargarApplet */	
		var params		= new Array();
		params[0]		= new Array();
		params[0][0]	= "userAgent";
		params[0][1]	= window.navigator.userAgent;
		params[1]		= new Array();
		params[1][0]	= "appName"
		params[1][1]	= window.navigator.appName;
		
		/* Cargamos el applet */
		var allJars= instalador.getAllJars();

		SignApplet = cargarApplet2("SignApplet", "com.telventi.afirma.cliente.SignApplet.class", "clienteFirmaAFirma5.jar", allJars, base,codeBase,params);
		whenTry("SignApplet.isInitialized() == true", "SignAppletCargado=true;");
		return;		
	}
}

function instalar()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
		whenTry("instaladorCargado == true", "instalar()", "No se ha podido cargar el instalador");
		return;
	}
	if(instaladorCargado != true )
	{
		whenTry("instaladorCargado == true", "instalar()", "No se ha podido cargar el instalador");
		return;
	}
	
	setTimeout("instalador.instalar();", 1);
}

function actualizar(baseDownloadURL)
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
		whenTry("instaladorCargado == true", "actualizar()", "No se ha podido cargar el instalador");
		return;
	}
	if(instaladorCargado != true )
	{
		whenTry("instaladorCargado == true", "actualizar()", "No se ha podido cargar el instalador");
		return;
	}
	
	instalador.actualizar();
}

function desinstalar()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
		whenTry("instaladorCargado == true", "desinstalar()", "No se ha podido cargar el instalador");
		return;
	}
	if(instaladorCargado != true )
	{
		whenTry("instaladorCargado == true", "desinstalar()", "No se ha podido cargar el instalador");
		return;
	}
		
	instalador.desinstalar();
}

function isInstalado()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	waitFor("instaladorCargado == true", 10000);
	
	return instalador.isInstalado();
}

function isActualizado()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	waitFor("instaladorCargado == true", 10000);
	
	return instalador.isActualizado();
}

function getDirectorioInstalacion()
{
	if(instalador== undefined)
	{
		cargarAppletInstalador();
	}
	
	waitFor("instaladorCargado == true", 10000);
	
	return instalador.getInstallationDirectory();
}


function cargaAppletAFirma50(){
	cargarAppletFirma();
}