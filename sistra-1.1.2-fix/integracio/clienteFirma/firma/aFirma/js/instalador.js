/**
 * Depende de deployJava.js, de time.js y de constantes.js [opcional].
 *
 * Si se ha definido baseDownloadURL se usa como URL base para la descarga de los instalables.
 *
 * cargarAppletFirma(build):
 *      Carga el applet de firma en la variable "clienteFirma". El applet no esta cargado hasta que clienteFirmaCargado= true.
 *      La carga del applet verificara que se tiene instalada una construccion igual o superior a la indicada, si no lo esta
 *      se carga el instalador e inicia el proceso de instalacion el applet en local.
 *
 * cargarAppletInstalador():
 *      Carga el applet de carga (BootLoader) en la variable "instalador".
 *
 * instalar(build):
 *      Carga el applet instalador (si no se ha cargado), instala la construccion del cliente en local y devuelve el directorio de
 *      instalacion (null si no).
 *
 * actualizar(baseDownloadUrl):
 *      Carga el applet instalador (si no se ha cargado), actualiza la version instalada en local y devuelve true si el proceso
 *      finaliza correctamente (false si no).
 *
 * desinstalar():
 *      Carga el applet instalador (si no se ha cargado), desinstala el cliente de firma de local y devuelve true si el proceso
 *      finaliza correctamente (false si no).
 *
 * isInstalado(build):
 *      Carga el applet instalador (si no se ha cargado) y comprueba que este instalada la construccion indicada o una superior.
 *      Si no se indica ninguna se comprueba que este instalada la construccion basica del cliente. Devuelve true si la comprobacion
 *      fue positiva y false en caso contrario.
 *
 * isActualizado():
 *      Carga el applet instalador (si no se ha cargado) y devuelve true si el cliente de firma esta actualizado (coinciden las
 *      versiones local/remota) en local (false si no).
 *
 * getDirectorioInstalacion():
 *      Carga el applet instalador (si no se ha cargado) y devuelve la ruta de instalacion del cliente de firma en local.
 *
 * getVersion():
 *      Carga el applet instalador (si no se ha cargado) y devuelve la version del instalador.
 *
 * getVersionCliente():
 *      Carga el applet instalador (si no se ha cargado), comprueba que el cliente este instalado y si lo esta devuelve la version del cliente.
 *
 */ 

var instalador;
var instaladorCargado = false;
var clienteFirma;

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
	else if( isActualizado() == false )
	{
		actualizar(baseDownloadURL);

		// Si fallo o se cancelo la instalacion, informamos de ello
		if( isActualizado() == false )
		{
			alert("No se ha podido actualizar el cliente \u0040firma a la \u00FAltima versi\u00F3n disponible. Por motivos de seguridad se cancelar\u00E1 la carga del mismo.");
			return;
		}
	}

	// Cargamos el applet de firma
	if(clienteFirma == undefined)
	{
		/* Definición de las constantes necesarias */

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
				setTimeOut("clienteFirma != undefined && clienteFirma.isInitialized()", 100);
				break;
			} catch(e) {
				// Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
				// una vez estuviese cargado aunque no iniciado
			}
		}
	}
}

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
					archive:'afirmaBootLoader.jar',
					codebase:codeBaseVar,
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
				setTimeOut("instalador != undefined && instalador.isIniciado()", 100);
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

function getVersion()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	return instalador.getVersion();
}

function getVersionCliente()
{
	if(instalador == undefined)
	{
		cargarAppletInstalador();
	}
	
	return instalador.getClientVersion();
}
