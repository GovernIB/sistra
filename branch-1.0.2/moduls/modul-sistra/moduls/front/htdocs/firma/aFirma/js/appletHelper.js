/*
 * cargarApplet(nombreApplet, codeApplet, archiveApplet, params[, codebase]) -> A?ade un applet 'invisible' seg?n los par?metros pasados
 */


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
