/* Copyright (C) 2012 [Gobierno de Espana]
 * This file is part of "Cliente @Firma".
 * "Cliente @Firma" is free software; you can redistribute it and/or modify it under the terms of:
 *   - the GNU General Public License as published by the Free Software Foundation; 
 *     either version 2 of the License, or (at your option) any later version.
 *   - or The European Software License; either version 1.1 or (at your option) any later version.
 * Date: 11/01/11
 * You may contact the copyright holder at: soporte.afirma5@seap.minhap.es
 */

/**
 *
 * Version: 2.2.2
 *
 * Depende de deployJava.js y de constantes.js [opcional].
 *
 * Si se ha definido baseDownloadURL se usa como URL base para la descarga de los instalables.
 *
 * cargarAppletFirma(build):
 *      Carga el applet de firma en la variable "clienteFirma". Si el applet detecta que requiere alguna dependencia que no esta
 *		en el sistema del usuario, la instalara. Recibe como parametro la construccion del Cliente que se desea cargar "LITE", "MEDIA"
 *		o "COMPLETA".
 */

var instalador;
var instaladorCargado = false;
var clienteFirma;

function cargarAppletFirma(build, jnlp)
{
	/* Si ya esta cargado, no continuamos con el proceso */
	if (clienteFirma != undefined) {
		return;
	}
	
	/* Definimos las contruccion que se va a utilizar */
	var confBuild = configureBuild(build);

	/* Comprobamos si se desea la carga JNLP (opcion por defecto) */
	var loadByJnlp = jnlp == undefined || jnlp == null || jnlp != false;
	
	/* Con Google Chrome nunca cargaremos el Bootloader, porque no soportaria 2 applet en la misma pagina si la JVM no esta actualizada. */
	if (deployJava.browserName2 != 'Chrome') {	
	
		/* Cargaremos el Bootloader en aquellos casos en los que:
		 *    - Tengamos Java 5.
		 *    - Tengamos Java6u28 o inferior de 64 bits en Windows.
		 *    - Tengamos Java7u0 o inferior de 64 bits en Windows.
		 */
		if ((!deployJava.versionCheck('1.6') && !deployJava.versionCheck('1.7')) ||
		    (isWindows() && is64BitsSystem() && (!deployJava.versionCheck('1.6.0_29+') || deployJava.versionCheck('1.7.0_00')))) {
				instalar(confBuild, null, null);
		}
	}
	
	var jarArchive = confBuild + "_j6_afirma5_core.jar";
	if (deployJava.versionCheck('1.6.0+') == false) {
                jarArchive = confBuild + "_j5_afirma5_core.jar";
				loadByJnlp = false;
	}
	
	var codeBase = base;
	if (codeBase == undefined || codeBase == null) {
		codeBase = '.';
	}

	var defaultLocale = locale;
	if (defaultLocale == undefined) {
		defaultLocale = null;
	}
	
	var attributes = {
		id: 'firmaApplet',
	 	width: 1,
		height: 1
	};
	var parameters;
	
	// En el caso de Linux, desactivamos el despliegue de JNLP para evitar los problemas derivados de IcedTea
	if (loadByJnlp && !isLinux()) {
		parameters = {
			userAgent: window.navigator.userAgent,
			appName: window.navigator.appName,
			showExpiratedCertificates: showExpiratedCertificates,
			showMozillaSmartCardWarning: showMozillaSmartCardWarning,
			code: 'es.gob.afirma.applet.SignApplet.class',
			archive: jarArchive,
			codebase: codeBase,
			locale: defaultLocale,
			java_arguments: '-Djnlp.packEnabled=true -Xms512M -Xmx512M',
			separate_jvm: true,
			jnlp_href: confBuild + '_afirma.jnlp'
		};
	}
	else {
		parameters = {
			userAgent: window.navigator.userAgent,
			appName: window.navigator.appName,
			showExpiratedCertificates: showExpiratedCertificates,
			showMozillaSmartCardWarning: showMozillaSmartCardWarning,
			code: 'es.gob.afirma.applet.SignApplet.class',
			archive: jarArchive,
			codebase: codeBase,
			locale: defaultLocale,
			java_arguments: '-Xms512M -Xmx512M',
			separate_jvm: true
		};
	}
	
 	deployJava.runApplet(attributes, parameters, '1.5');

	clienteFirma = document.getElementById("firmaApplet");
	
	/* Realizamos una espera para que de tiempo a cargarse el applet */
	for (var i = 0; i < 100; i++) {
		try {
			setTimeout("clienteFirma != undefined && clienteFirma.isInitialized()", 100);
			break;
		} catch (e) {
			/*
			 * Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
			 * una vez estuviese cargado aunque no iniciado
			 */
		}
	}
}

function instalar(build, jsMethodName, jsMethodParams)
{
	// Definimos las contruccion que se va a utilizar
	var confBuild = configureBuild(build);

	if(instalador == undefined)
	{
		cargarAppletInstalador(confBuild);
	}
}

function cargarAppletInstalador(confBuild)
{
	if(instalador == undefined)
	{
		/* Definicion de las constantes necesarias */
		var codeBase = '.';
		if (base != undefined) {
			if(base.toLowerCase().substring(0, 7) != "file://" && 
					base.toLowerCase().substring(0, 7) != "http://" &&
					base.toLowerCase().substring(0, 8) != "https://") {
				codeBase = './' + base;
			}
			else {
				codeBase = base;
			}
		}
		
		// Si hay definida una URL desde la que descargar los instalables, la establecemos
		var baseDownloadURLVar = '.';
		if (baseDownloadURL != undefined) {
			if(baseDownloadURL.toLowerCase().substring(0, 7) != "file://" && 
					baseDownloadURL.toLowerCase().substring(0, 7) != "http://" &&
					baseDownloadURL.toLowerCase().substring(0, 8) != "https://") {

				var url = document.location.toString();
				baseDownloadURLVar = url.substring(0, url.lastIndexOf("/")) + '/' + baseDownloadURL;
			}
			else {
				baseDownloadURLVar = baseDownloadURL;
			}
		}
		
		var parameters = {
					baseDownloadURL:baseDownloadURLVar,
					installType:confBuild
					};
		var attributes = {
					id:'instaladorApplet',
					code:'es.gob.afirma.install.AfirmaBootLoader.class',	
					archive:codeBase+'/afirmaBootLoader.jar',
					width:1, height:1
					};
		var version = '1.5';
		
		try {
			deployJava.runApplet(attributes, parameters, version);
		} catch(e) {}

		instalador = document.getElementById("instaladorApplet");
		
		for(var i=0; i<100; i++) {
			try {
				setTimeout("instalador != undefined", 100);
				instaladorCargado = true;
				break;
			} catch(e) {
				instaladorCargado = false;
				// Capturamos la excepcion que se produciria si no se hubiese cargado aun el applet, aunque no se lanzaria
				// una vez estuviese cargado aunque no iniciado
			}
		}
	}
}

/**
 * Si no se ha indicado una construccion por parametro, ni hay establecida una por defecto en "constantes.js", se instala la 'LITE'
 */
function configureBuild(build)
{
	var confBuild = null;
	if(build != null && build != undefined)
	{
		confBuild = build;
	}
	else if(defaultBuild != null && defaultBuild != undefined) {
		confBuild = defaultBuild;
	}
	else {
		confBuild = 'LITE';
	}
	return confBuild;
}

 function isWindows()
 {
	return navigator.appVersion.indexOf("Win") != -1;
 }

  function isLinux()
 {
	return navigator.appVersion.indexOf("Linux") != -1 || navigator.appVersion.indexOf("X11") != -1;
 }
 
function is64BitsSystem()
{
	var is64BitBrowser = false;
	if( window.navigator.cpuClass != null && window.navigator.cpuClass.toLowerCase() == "x64" ) {
		is64BitBrowser = true;
	}
	if( window.navigator.platform.toLowerCase() == "win64" ) {
		is64BitBrowser = true
	}
	return is64BitBrowser;
}