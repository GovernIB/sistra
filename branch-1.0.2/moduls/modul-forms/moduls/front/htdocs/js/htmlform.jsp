<%@ page language="java" contentType="text/javascript"%>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html"%>
<html:javascript dynamicJavascript="false" staticJavascript="true" />

// Funciones de utilidad.
function esNumero(valor, alt) {
    if (isNaN(valor)) {
        return alt;
    } else {
        return valor;
    }
}

function existe(valor, alt) {
    if (valor == undefined || valor == null) {
        return alt;
    } else {
        return valor;
    }
}

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

    onFieldChange(hidden.form, hidden.name);
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
        try {
            select.add(opcio, null);
        } catch(E) {
            select.add(opcio);
        }
    }
}
