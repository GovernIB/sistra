function actualizaMiga(pIdioma, pArchivo, pDescripcion) {
var oIframe = parent.document.getElementById('cap');
var oDoc = (oIframe.contentWindow || oIframe.contentDocument);
if (oDoc.document) oDoc = oDoc.document;
var miga = oDoc.getElementById('miga').innerHTML;
var migaAdd = '<li><a target=metodos href="' + pIdioma + '/' + pArchivo + '.html">' + pDescripcion + '</a></li>';
var migaSearch = '>'+ pDescripcion + '</a></li>';
var n = miga.toLowerCase().indexOf(migaSearch.toLowerCase());
var largo = migaSearch.length;
if (miga.length > 0) {
	if (n >= 0) {
		oDoc.getElementById('miga').innerHTML =  miga.substr(0, n + largo);
	} 
	else {
		oDoc.getElementById('miga').innerHTML =  miga + migaAdd;
	}
}
else {
	oDoc.getElementById('miga').innerHTML = migaAdd;
}
}