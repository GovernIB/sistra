// JavaScript Document

function redimensionarIframe() {
	if(document.all) alturaDoc = document.body.offsetHeight;
	else alturaDoc = document.documentElement.offsetHeight;
	iframeDoc = window.top.document.getElementById('tramitacionIframe');
	if (iframeDoc != undefined) iframeDoc.style.height = alturaDoc + 'px';
}
window.onload = redimensionarIframe;
