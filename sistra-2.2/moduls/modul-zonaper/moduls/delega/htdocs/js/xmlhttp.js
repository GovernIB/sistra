
// Definir objeto XMLHttpRequest
var xmlhttp=false;
/*@cc_on @*/
/*@if (@_jscript_version >= 5)
try {
    xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
} catch (e) {
    try {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (E) {
        xmlhttp = false;
    }
}
@end @*/
if (!xmlhttp && typeof XMLHttpRequest!='undefined') {
    xmlhttp = new XMLHttpRequest();
}

// Hace un post sincrono, devolviendo el resultado.
function syncPost(urlString, postString) {
    xmlhttp.open("POST", urlString, false);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(postString);
    return xmlhttp.responseText;
}


function callBackGenerico(ajaxHttp, fCallBack)
{
    if (ajaxHttp.readyState == 4) {
        if ((ajaxHttp.status == 200) || (ajaxHttp.status == 0)) {	
			var resultPost = ajaxHttp.responseText;
			fCallBack(resultPost);
			return;			
		} else {						        
			fCallBack("ERROR:Error de comunicacion con servidor");
			return;
		}
	}
}

function asyncPost(urlString,postString, funcionCallB)
{
	// Enviamos la peticion
	 xmlhttp.open( "POST", urlString, true );
	 if (funcionCallB != null)
	 	xmlhttp.onreadystatechange = function() { callBackGenerico(xmlhttp, funcionCallB); };
	 xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");		
	 xmlhttp.send( postString );	
}
