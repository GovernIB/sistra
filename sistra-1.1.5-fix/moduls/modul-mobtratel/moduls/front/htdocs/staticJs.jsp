<%@ page language="java" contentType="text/javascript"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<html:javascript dynamicJavascript="false" staticJavascript="true" />

function depends(deps, modified) {
    for (i = 0; i < modified.length; i++) {
        if (deps.indexOf(modified[i]) > -1) {
            return true;
        }
    }
    return false;
}

function updateMulti(select, hidden) {
    var values = new Array();
    for (i = 0; i < select.length; i++) {
        values.push(select.options[i].text);
    }
    hidden.value = values.join('\r\n');

    onFieldChange(hidden.form, select.name);
}

function saveMulti(select, hidden) {
    for (i = 0; i < select.length; i++) select.options[i] = null;
    var value = hidden.value;
    if (value != '') {
        variables = value.split('\r\n');
        for (i = 0; i < variables.length; i++) {
            select.options[i] = new Option(variables[i]);
        }
    }
}

function selectOption(select, valor) {
    for (var i = 0; i < select.length; i++) {
        if (select.options[i].value == valor) {
            select.options[i].selected = true;
        }
    }
}

function selectOptions(select, valors) {
    // Limpiar select
    for (var i = 0; i < select.length; i++) {
        select.options[i].selected = false;
    }
    // Marcar cada opcion
    for (var i = 0; i < valors.length; i++) {
        selectOption(select, valors[i]);
    }
}

function selectRadio(radio, valor) {
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].value == valor) {
            radio[i].checked = true;
        } else {
            radio[i].checked = false;
        }
    }
}

function refillSelect(select, valors) {
    var nopcions = select.length;
    for (var i = 1; i < nopcions; i++) {
        select.remove(1);
    }
    if (nopcions > 0 && select.options[0].value != '') {
        select.remove(0);
    }
    for (var i = 0; i < valors.length; i++) {
        var opcio = new Option(valors[i].etiqueta, valors[i].valor, valors[i].defecto, false);
        select.add(opcio, null);
    }
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i < a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i< a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i< d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i< d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
