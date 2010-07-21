/**
 * Depende de appletHelper.js, de time.js y de constantes.js [opcional].
 *
 * Si se ha definido baseDownloadURL se usa como URL base para la descarga de los instalables.
 *
 * cargarAppletFirma():
 *      Carga el applet de firma en la variable "clienteFirma". El applet no est? cargado hasta que clienteFirmaCargado= true.
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
var clienteFirma;
var clienteFirmaCargado = false;

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
		i=2;
		if(installDirectory)
		{	
			params[i]   = new Array();
			params[i][0]="installDirectory"; 
			params[i][1]=installDirectory;
			i++;
		}

		if(distinctDistroDir)
		{
			params[i]   = new Array();
			params[i][0]="distinctDistroDir"; 
			params[i][1]=distinctDistroDir;
			i++;
		}
		
		if(baseDownloadURL)
		{
			params[i]   = new Array();
			params[i][0]= "baseDownloadURL";
			params[i][1]= baseDownloadURL;
			i++;
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
	if(clienteFirma == undefined)
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
		params[2]   	= new Array();
		params[2][0]	= "installDirectory"; 
		params[2][1]	= instalador.getInstallationDirectory();
		i=3;

		/* Cargamos el applet */
		var allJars= instalador.getAllJars();

		clienteFirma = cargarApplet2("clienteFirma", "com.telventi.afirma.cliente.SignApplet.class", "clienteFirmaAFirma5.jar", allJars, base,codeBase,params);
		whenTry("clienteFirma.isInitialized() == true", "clienteFirmaCargado=true;");
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

function instalarXAdES()
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
	
	setTimeout("instalador.instalar('XAdES');", 1);
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
