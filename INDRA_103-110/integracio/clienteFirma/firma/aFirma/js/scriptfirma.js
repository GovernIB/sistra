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
 * Algoritmo de firma. Puede ser 'SHA1withRSA' o                     *
 * 'MD5withRSA'. Se estable al llamar a configuraFirma en firma.js   *
 ******************************************************************************/
var signatureAlgorithm = 'SHA1withRSA'; // Valor por defecto

/*******************************************************************************
 * Formato de firma. Puede ser 'CMS', 'CADES', 'XADES', 'XMLDSIGN' o 'NONE'.            *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var signatureFormat = 'CMS'; // Valor por defecto

/*******************************************************************************
 * Mostrar los errores al usuario. Puede ser 'true' o 'false'.                 *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var showErrors = 'true'; // Valor por defecto

/*******************************************************************************
 * Ruta a los instalables.                                                    *
 * Si no se establece, supone que estan en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var baseDownloadURL;

/*******************************************************************************
 * Ruta al instalador.                                                         *
 * Si no se establece, supone que estan en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var base; // Valor por defecto

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

/*******************************************************************************
 * Acci?n establecida a realizar cuando durante la instalacion se detecten	 *
 * versiones antiguas del cliente (v2.4 y anteriores).				 *
 * Opciones disponibles:									 *
 * 	- 1: Preguntar al usuario.								 *
 *	- 2: No eliminar.										 *
 *	- 3: Eliminar sin preguntar.								 *
 * Por defecto: 1 (Preguntar al usuario).							 *
 ******************************************************************************/
var oldVersionsAction = 1;

/*******************************************************************************
 * Mostrar los certificados caducados en la listas de seleccion de		 *
 * certificados.						 					 *
 * Por defecto: 'true'.										 *
 ******************************************************************************/
var showExpiratedCertificates = 'true';

/*******************************************************************************
 * Construccion del cliente que se instalara cuando no se indique			 *
 * explicitamente.										 *
 * Los valores aceptados son:									 *
 *   - 'LITE':     Incluye los formatos de firma CMS y CADES.			 *
 *   - 'MEDIA':    Incluye los formatos de firma de la LITE + XMLDSIG y XADES. *
 *   - 'COMPLETA': Incluye los formatos de firma de la MEDIA + PDF.		 *
 * Por defecto: 'LITE'.										 *
 ******************************************************************************/
var defaultBuild;



var _info = navigator.userAgent; 
var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);

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

	/* Parametros del applet */
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
		/* Definicion de las constantes necesarias */
		var codeBaseVar = '.';
		if(base != undefined) {
			codeBaseVar = base;
		}

		var attributes = {id:'instaladorApplet',
					code:'es.gob.afirma.install.AfirmaBootLoader.class',	
					archive:codeBaseVar+'/afirmaBootLoader.jar',
					width:1, height:1};
		var parameters = {userAgent:window.navigator.userAgent,
					appName:window.navigator.appName,
					installDirectory:installDirectory,
					oldVersionsAction:oldVersionsAction};
		var version = '1.5';

		deployJava.runApplet(attributes, parameters, version);

		instalador = document.getElementById("instaladorApplet");
		
		/* Realizamos una espera para que de tiempo a cargarse el applet */
		whenTry("instalador.isIniciado() == true", "instaladorCargado = true");

		for(var i=0; i<100; i++) {
			try {
				setTimeout("instalador != undefined && instalador.isIniciado()", 100);
				break;
			} catch(e) {
				// Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
				// una vez estuviese cargado aunque no iniciado
			}
		}
	}
}

function cargarAppletFirma(build)
{
	// Comprobamos que el instalador esta cargado
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	// Comprobamos que el applet de firma esta instalado
	if( instaladorCargado == false )
	{
		whenTry("instaladorCargado == true", "cargarAppletFirma(build)", "No se ha podido cargar el applet instalador");
		return;
	}

	// Comprobamos que el applet de firma esta instalado
	if( isInstalado(build) == false )
	{
		instalar(build);

		// Si fallo o se cancelo la instalacion, informamos de ello
		if( isInstalado(build) == false )
		{
			alert("No se ha finalizado la instalaci\u00F3n del cliente \u0040firma por lo que no se podr\u00E1n realizar operaciones de firma electr\u00F3nica.");
			return;
		}
	}
		
	// Cargamos el applet de firma
	if(clienteFirma == undefined)
	{
		/* Definici?n de las constantes necesarias */

		var installationDirectory = instalador.getInstallationDirectory();
		var jarArchive = 'file:///' + installationDirectory + '/afirma5_coreV3.jar';

		var attributes = {id:'firmaApplet',
					code:'es.gob.afirma.cliente.SignApplet.class',
					archive:jarArchive,
					width:1, height:1};
		var parameters = {userAgent:window.navigator.userAgent,
					appName:window.navigator.appName,
					installDirectory:installDirectory,
					showExpiratedCertificates:showExpiratedCertificates,
					java_arguments:'-Djnlp.packEnabled=true -Xmx512M'};
		var version = '1.5';

		deployJava.runApplet(attributes, parameters, version);

		clienteFirma = document.getElementById("firmaApplet");

		/* Realizamos una espera para que de tiempo a cargarse el applet */
		for(var i=0; i<100; i++) {
			try {
				setTimeout("clienteFirma != undefined && clienteFirma.isInitialized()", 100);
				break;
			} catch(e) {
				// Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
				// una vez estuviese cargado aunque no iniciado
			}
		}
	}
}

function instalar(build)
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	if(isInstalado(build)) {
		alert("El cliente de @firma 5 v3 ya est\u00E1 instalado");
		return true;
	}

	// Si hay definida una URL desde la que descargar los instalables, la establecemos
	if(baseDownloadURL != undefined) {
		instalador.setBaseDownloadURL(baseDownloadURL);
	}

	// Si no se ha indicado una construccion por parametro ni hay establecida una por defecto en "constantes.js", se instala la 'LITE'
	var confBuild = null;
	if(build == null || build == undefined)
	{
		if(defaultBuild == null || defaultBuild == undefined)
		{
			return instalador.instalar();
		} else {
			confBuild = defaultBuild;
		}
	} else {
		confBuild = build;
	}

	// Si se ha indicado una construccion de alguna manera (por defecto o por parametro), se instala esa

	return instalador.instalar(confBuild);
}

function actualizar(baseDownloadURL)
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	// Si se ha establecido una URL base, la establecemos para tomar de ahi los paquetes.
	if(baseDownloadURL != undefined)
	{
		instalador.setBaseDownloadURL(baseDownloadURL);
	}

	return instalador.actualizar();
}

function desinstalar()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	return instalador.desinstalar();
}

function isInstalado(build)
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	// Si no se ha indicado una construccion por parametro ni hay establecida una por defecto en "constantes.js", se comprueba la 'LITE'
	var confBuild = null;
	if(build == null || build == undefined)
	{
		if(defaultBuild == null || defaultBuild == undefined)
		{
			return instalador.isInstalado();
		} else {
			confBuild = defaultBuild;
		}
	} else {
		confBuild = build;
	}

	// Si se ha indicado una construccion de alguna manera (por defecto o por parametro), se comprueba esa

	return instalador.isInstalado(confBuild);
}

function isActualizado()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	return instalador.isActualizado();
}

function getDirectorioInstalacion()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	return instalador.getInstallationDirectory();
}

function getDirectorioInstalacion()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	return instalador.getInstallationDirectory();
}


function cargaAppletAFirma50(){
	cargarAppletFirma();
}

//------------------------------------------------------------
//Carga el applet de firma
//Guardar compatibilidad con versiones 4.x de la plataforma
cargarAppletFirma();
//------------------------------------------------------------=