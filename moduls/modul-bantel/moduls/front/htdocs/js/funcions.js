
// Obrir un pop up
function obrir(url, name, x, y) {
   window.open(url, name, 'scrollbars=yes, resizable=yes, width=' + x + ',height=' + y);
}

// Obrir un pop up per el calendari
function obrirFixa(url) {
   window.open(url, 'Calendari', 'scrollbars=no, resizable=no, width=235 ,height=175');
}

function obrirTest(url) {
   window.open( url, '', 'toolbar, menubar, scrollbars, resizable');
}

// Auxiliar para formularios
function myIndex(o) {
   var f = o.form;
   
   for (i = 0; i < f.elements.length ; i++) {
      if (f.elements[i] == o) return i;
   }
}

// Confirma una baja mediante un di?logo
function confirmaBaja(texto) {
    var result = confirm(texto)

    if (result == true) {
        return true;
    } else {
        return false;
    }
}

function forward(url) {
    document.location.href = url;
}

function confirmAndForward(text, url) {
    if (confirm(text)) {
        forward(url);
    }
}

function b64UrlSafeToB64(b64UrlSafe){
	var b64 = b64UrlSafe.replace(/-/g, "+");
	b64 = b64.replace(/_/g, "/");
	return b64;
}

function b64ToB64UrlSafe(b64){
	var b64UrlSafe = b64.replace(/\+/g,"-");
	b64UrlSafe = b64UrlSafe.replace(/\//g,"_");
	b64UrlSafe = b64UrlSafe.replace(/(\r\n|[\r\n])/g,"");
	return b64UrlSafe;
}

function checkURL(value) {
    var urlregex = new RegExp("^(http:\/\/|https:\/\/){1}([0-9A-Za-z]+\.)");
    if (urlregex.test(value)) {
        return (true);
    }
    return (false);
}

