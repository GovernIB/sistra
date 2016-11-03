
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

function ParametroPost(name,value){
	this._name=name;
	this._value=value;
	this._nameEncoded=encodeString(name);
	this._valueEncoded=encodeString(value);
	this.getName=function(){return this._name;};
	this.getValue=function(){return this._value;};	
	this.getNameEncoded=function(){return encodeString(this._name);};
	this.getValueEncoded=function(){return encodeString(this._value);};		
}

function encodeString(value){
	var s = new String(escape(value));	
	s = s.replace(/\+/g,"%2B");	
	return encodeURI(s);
}

function obtenerAjaxRequest()
{

return xmlhttp;

	var ajaxHttp;
	
	if( window.XMLHttpRequest )
		{
		ajaxHttp = new XMLHttpRequest(); // No IE o IE7
		if (ajaxHttp.overrideMimeType) 
			{
			    ajaxHttp.overrideMimeType('text/html');
			}
		}
	else if (window.ActiveXObject) { // IE
			 var aVersions = [ "Msxml2.XMLHTTP.6.0", "Msxml2.XMLHTTP.5.0", "Msxml2.XMLHTTP.4.0","MSXML2.XMLHTTP.3.0", "Microsoft.XMLHTTP"];
			 
			 for (var i = 0; i < aVersions.length; i++) {
			        try {
			            ajaxHttp = new ActiveXObject(aVersions[i]);
			            break;
			        } catch (ex) {
			        }
			      }
         }
   return ajaxHttp;
}

function postAjax(uri, parametersPost, async) {
	var ajaxHttp;
	
	ajaxHttp = obtenerAjaxRequest();
		
	// Generamos cadena de parametros escapando los caracteres	
	var parametros = "";
	for (i=0;i<parametersPost.length;i++){
		if (i>0) parametros = parametros + "&";
		parametros = parametros + parametersPost[i].getNameEncoded() + "=" + parametersPost[i].getValueEncoded();
	}
	
	//alert("CADENA PARAM:" + parametros);
         
	// Enviamos la peticion
	ajaxHttp.open( "POST", uri, async );
	
	ajaxHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	//ajaxHttp.setRequestHeader("Content-length", parametros.length);
	//ajaxHttp.setRequestHeader("Connection", "close");
	
	ajaxHttp.send( parametros );
	
	if (!async) {
		var resultPost = ajaxHttp.responseText;	
		return resultPost;
	} else {
		return "";
	}	
}

function syncPost(uri, parametersPost)
{
	return postAjax(uri, parametersPost, false);		
}


//Hace un post asincrono
function asyncPost(uri, parametersPost)
{
	return postAjax(uri, parametersPost, true);
}

