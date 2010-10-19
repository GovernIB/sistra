function cargarCertificado()
{
	var applet = whichApplet();
	res = applet.inicializarDispositivo( contentType );
	if (!res) 
	{
		alert(applet.getLastError());
		return;
	}
}

function whichApplet ()
{
	var _info = navigator.userAgent;
	if (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 &&
        _info.indexOf("Windows 3.1") < 0) {
        return document.appfirma;
	} else {
        return document.appfirma2;
	}
}