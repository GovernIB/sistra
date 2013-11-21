
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

function postAjax(urlString, postString, async) {
    xmlhttp.open("POST", urlString, async);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(postString);
    if (!async) {
    	return xmlhttp.responseText;
	} else {
		return "";
	}	    
}

// Hace un post sincrono, devolviendo el resultado.
function syncPost(urlString, postString) {
    return postAjax(urlString, postString, false);
}

//Hace un post sincrono, devolviendo el resultado.
function asyncPost(urlString, postString) {
	return postAjax(urlString, postString, true);    
}