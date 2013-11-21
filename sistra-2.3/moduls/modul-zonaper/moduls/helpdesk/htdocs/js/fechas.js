// Funciones Javascript para fechas

function esFecha(fecha) {

if (esBlanco(fecha))
	return true;	
if (getValorFecha(fecha,"","D") == null) {
return false;
}
else {
return true;
   }
}


function getValorFecha(fecha, formatoOut, formatoIn) {
var strDatestyle = "EU";
if (formatoIn.substring(0,1).toUpperCase() == 'M')
{
	strDatestyle = "US";
}

var strDate;
var strDateArray;
var strDay;
var strMonth;
var strYear;
var intday;
var intMonth;
var intYear;
var booFound = "";
var strSeparatorArray = new Array("-"," ","/",".");
var intElementNr;
var err = 0;
var strHour;
var strMinute;
var strSecond;
var strMonthArray = new Array(12);
strMonthArray[0] = "Ene";
strMonthArray[1] = "Feb";
strMonthArray[2] = "Mar";
strMonthArray[3] = "Abr";
strMonthArray[4] = "May";
strMonthArray[5] = "Jun";
strMonthArray[6] = "Jul";
strMonthArray[7] = "Ago";
strMonthArray[8] = "Sep";
strMonthArray[9] = "Oct";
strMonthArray[10] = "Nov";
strMonthArray[11] = "Dic";

var strMonthArraySp = new Array(12);
strMonthArraySp[0] = "Enero";
strMonthArraySp[1] = "Febrero";
strMonthArraySp[2] = "Marzo";
strMonthArraySp[3] = "Abril";
strMonthArraySp[4] = "Mayo";
strMonthArraySp[5] = "Junio";
strMonthArraySp[6] = "Julio";
strMonthArraySp[7] = "Agosto";
strMonthArraySp[8] = "Septiembre";
strMonthArraySp[9] = "Octubre";
strMonthArraySp[10] = "Noviembre";
strMonthArraySp[11] = "Diciembre";

var strMonthArrayV = new Array(12);
strMonthArrayV[0] = "Gener";
strMonthArrayV[1] = "Febrer";
strMonthArrayV[2] = "Mar?";
strMonthArrayV[3] = "Abril";
strMonthArrayV[4] = "Maig";
strMonthArrayV[5] = "Juny";
strMonthArrayV[6] = "Juli";
strMonthArrayV[7] = "Agost";
strMonthArrayV[8] = "Setembre";
strMonthArrayV[9] = "Octobre";
strMonthArrayV[10] = "Novembre";
strMonthArrayV[11] = "Decembre";

var strMonthArrayVC = new Array(12);
strMonthArrayVC[0] = "Gener/Enero";
strMonthArrayVC[1] = "Febrer/Febrero";
strMonthArrayVC[2] = "Mar?/Marzo";
strMonthArrayVC[3] = "Abril/Abril";
strMonthArrayVC[4] = "Maig/Mayo";
strMonthArrayVC[5] = "Juny/Junio";
strMonthArrayVC[6] = "Juli/Julio";
strMonthArrayVC[7] = "Agost/Agosto";
strMonthArrayVC[8] = "Setembre/Septiembre";
strMonthArrayVC[9] = "Octobre/Octubre";
strMonthArrayVC[10] = "Novembre/Noviembre";
strMonthArrayVC[11] = "Decembre/Diciembre";


var strMonthArrayUS = new Array(12);
strMonthArrayUS[0] = "Jan";
strMonthArrayUS[1] = "Feb";
strMonthArrayUS[2] = "Mar";
strMonthArrayUS[3] = "Apr";
strMonthArrayUS[4] = "May";
strMonthArrayUS[5] = "Jun";
strMonthArrayUS[6] = "Jul";
strMonthArrayUS[7] = "Aug";
strMonthArrayUS[8] = "Sep";
strMonthArrayUS[9] = "Oct";
strMonthArrayUS[10] = "Nov";
strMonthArrayUS[11] = "Dec";

strDate = fecha;
if (strDate == null || strDate.length < 1) 
{
	err = 1;
	return fecha;
}
for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) 
{
	if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) 
	{
		strDateArray = strDate.split(strSeparatorArray[intElementNr]);
		
		if (strDateArray.length == 3) 
		{
			booFound = "true";
			strDay = strDateArray[0];
			strMonth = strDateArray[1];
			strYear = strDateArray[2];
			break;
		}
   	}
}

if (booFound == "") 
{
	if (strDate.length == 8) 
	{
		strDay = strDate.substr(0, 2);
		strMonth = strDate.substr(2, 2);
		strYear = strDate.substr(4);
	}
	else
	{
		return null;
	}
}

if (strDay.length == 4)
{
	strTemp = strDay;
	strDay = strYear;
	strYear = strTemp;
}

intYear = parseInt(strYear, 10);
if (strYear.length == 2) 
{
	if (intYear >=50 && intYear <= 99)
		strYear = '19' + strYear;
	else
		strYear = '20' + strYear;
}
if (strYear.length == 1) 
{	
	strYear = '200' + strYear;
}
// US style
if (strDatestyle == "US") 
{
	strTemp = strDay;
	strDay = strMonth;
	strMonth = strTemp;
}

if (strMonth.length > 2)
{
	for (var j=0; j<12; j++)
	{
		if (strMonth == strMonthArrayUS[j])
		{
			strMonth = j+1;
			j = 12;
		}
	}
}	
if (strMonth.length > 2)
{
	for (var j=0; j<12; j++)
	{
		if (strMonth == strMonthArraySp[j])
		{
			strMonth = j+1;
			j = 12;
		}
	}
}
if (strMonth.length > 2)
{
	for (var j=0; j<12; j++)
	{
		if (strMonth == strMonthArray[j])
		{
			strMonth = j+1;
			j = 12;
		}
	}		
}
if (strMonth.length > 2)
{
	for (var j=0; j<12; j++)
	{
		if (strMonth == strMonthArrayV[j])
		{
			strMonth = j+1;
			j = 12;
		}
	}
}
if (strMonth.length > 2)
{
	for (var j=0; j<12; j++)
	{
		if (strMonth == strMonthArrayVC[j])
		{
			strMonth = j+1;
			j = 12;
		}
	}		
}


if (isNaN(strDay)) {
	err = 2;
	return null;
}
intday = parseInt(strDay, 10);
if (isNaN(strMonth)) 
{
	err = 3;
	return null;
}
intMonth = parseInt(strMonth, 10);

if (isNaN(strYear)) 
{
	err = 4;
	return null;
}
intYear = parseInt(strYear, 10);

if (intMonth>12 || intMonth<1) {
	err = 5;
	return null;
}
if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) 
{
	err = 6;
	return null;
}
if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
	err = 7;
	return null;
}
if (intMonth == 2) {
if (intday < 1) {
	err = 8;
	return null;
}
if (LeapYear(intYear) == true) {
if (intday > 29) {
err = 9;
return null;
}
}
else {
if (intday > 28) {
err = 10;
return null;
}
}
}
//strMonthArray[intMonth-1] + "/" + intday+"/" + strYear;
var strMonth = "0";
if (intMonth < 10)
   strMonth = strMonth + intMonth;
else 
   strMonth = intMonth;

var strDay = "0";
if (intday < 10)
   strDay = strDay + intday;
else 
   strDay = intday;   
if (formatoOut.toUpperCase()  == 'DD-MM-YYYY')
{
	fecha = strDay + "-" + strMonth + "-" + strYear;
	
}
else if (formatoOut.toUpperCase()  == 'DD/MM/YYYY')
{
	fecha = strDay + "/" + strMonth + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD/MM/YY')
{
	fecha = strDay + "/" + strMonth + "/" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DDMMYY')
{
	fecha = strDay + strMonth + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DDMMYYYY')
{
	fecha = strDay + strMonth + strYear;
}
else if (formatoOut.toUpperCase()  == 'YYYYMMDD')
{
	fecha = strYear + strMonth + intday;
}
else if (formatoOut.toUpperCase()  == 'DD-MM-YY')
{
	fecha = strDay + "-" + strMonth + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DD-MMM-YYYY')
{
	fecha = strDay + "-" + strMonthArray[intMonth-1] + "-" + strYear;
}
else if (formatoOut.toUpperCase() == 'DD/MMM/YYYY')
{
	fecha = strDay + "/" + strMonthArray[intMonth-1] + "/" + strYear;
}
else if (formatoOut.toUpperCase() == 'DD/MMM/YY')
{
	fecha = strDay + "/" + strMonthArraySp[intMonth-1] + "/" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase() == 'MM-DD-YYYY')
{
	fecha = strMonth + "-" + strDay + "-" + strYear;
}
else if (formatoOut.toUpperCase() == 'MES-DD-YYYY')
{
	fecha = strMonthArraySp[intMonth-1] + "-" + strDay + "-" + strYear;
}
else if (formatoOut.toUpperCase()  == 'MMM-DD-YYYY')
{
	fecha = strMonthArray[intMonth-1] + "-" + strDay + "-" + strYear;
}
else if (formatoOut.toUpperCase()  == 'MM/DD/YYYY')
{
	fecha.toUpperCase()  = intMonth + "/" + strDay + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'MES/DD/YYYY')
{
	fecha = strMonthArraySp[intMonth-1] + "/" + strDay + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'MMM/DD/YYYY')
{
	fecha = strMonthArray[intMonth-1] + "/" + strDay + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'MM-DD-YY')
{
	fecha = strMonth + "-" + strDay + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'MES-DD-YY')
{
	fecha = strMonthArraySp[intMonth-1] + "-" + strDay + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'MMM-DD-YY')
{
	fecha.toUpperCase()  = strMonthArray[intMonth-1] + "-" + strDay + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'MM/DD/YY')
{
	fecha = strMonth + "/" + strDay + "/" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'MES/DD/YY')
{
	fecha = strMonthArraySp[intMonth-1] + "/" + strDay + "/" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'MMM/DD/YY')
{
	fecha = strMonthArray[intMonth-1] + "/" + strDay + "/" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DD-MES-YYYY')
{
	fecha = strDay + "-" + strMonthArraySp[intMonth-1] + "-" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD-MES-YY')
{
	fecha = strDay + "-" + strMonthArraySp[intMonth-1] + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DD/MES/YYYY')
{
	fecha = strDay + "/" + strMonthArraySp[intMonth-1] + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD DE MES DE YYYY')
{
	fecha = strDay + " de " + strMonthArraySp[intMonth-1] + " de " + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD/MES/YY')
{
	fecha = strDay + "/" + strMonthArraySp[intMonth-1] + "/" + strYear.substring(2,2);
}

else if (formatoOut.toUpperCase()  == 'DD-MESV-YYYY')
{
	fecha = strDay + "-" + strMonthArrayV[intMonth-1] + "-" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD-MESV-YY')
{
	fecha = strDay + "-" + strMonthArrayV[intMonth-1] + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DD/MESV/YYYY')
{
	fecha = strDay + "/" + strMonthArrayV[intMonth-1] + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD DE MESV DE YYYY')
{
	fecha = strDay + " de " + strMonthArrayV[intMonth-1] + " de " + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD/MESV/YY')
{
	fecha = strDay + "/" + strMonthArrayV[intMonth-1] + "/" + strYear.substring(2,2);
}

else if (formatoOut.toUpperCase()  == 'DD-MESVC-YYYY')
{
	fecha = strDay + "-" + strMonthArrayVC[intMonth-1] + "-" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD-MESVC-YY')
{
	fecha = strDay + "-" + strMonthArrayVC[intMonth-1] + "-" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase()  == 'DD/MESVC/YYYY')
{
	fecha = strDay + "/" + strMonthArrayVC[intMonth-1] + "/" + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD DE MESVC DE YYYY')
{
	fecha = strDay + " de " + strMonthArrayVC[intMonth-1] + " de " + strYear;
}
else if (formatoOut.toUpperCase()  == 'DD/MESVC/YY')
{
	fecha = strDay + "/" + strMonthArrayVC[intMonth-1] + "/" + strYear.substring(2,2);
}
else if (formatoOut.toUpperCase() == 'PARSEDATE')
{
	fecha = strMonthArrayUS[intMonth-1] + " " + strDay + ", " + strYear;
}
else if (esBlanco(formatoOut) )
{
	fecha = strDay + "/" + strMonth + "/" + strYear;
}
else
{
	err = 11;
	fecha = null;
}


return fecha;
}

function LeapYear(intYear) {
if (intYear % 100 == 0) {
if (intYear % 400 == 0) { return true; }
}
else {
if ((intYear % 4) == 0) { return true; }
}
return "";
}

// Comprueba si from es menor que to
function compruebaPeriodoFechas(from, to) {
if (from.length != 10 ||  to.length != 10) {
	alert("Formato de fecha incorrecto (dd/mm/yyyy)");
	return false;
}
var fecFrom = new Date(from.substring(6,10),parseInt(from.substring(3,5),10) - 1,from.substring(0,2));
var fecTo = new Date(to.substring(6,10),parseInt(to.substring(3,5),10) - 1,to.substring(0,2));	
if (fecFrom <= fecTo) {
	return true;
}
else 
{
	if (from.value == "" || to.value == "") 
	{
		return false;
	}
}
return false;
}

// Dias entre from y to (sin incluir from)
function getDiasEntreFechas(from, to)
{

if (from.length != 10 ||  to.length != 10) {
	alert("Formato de fecha incorrecto (dd/mm/yyyy)");
	return -1;
}

var fecFrom = new Date(from.substring(6,10),parseInt(from.substring(3,5)) - 1,from.substring(0,2));
var fecTo = new Date(to.substring(6,10),parseInt(to.substring(3,5)) - 1,to.substring(0,2));	

if (fecFrom <= fecTo) {
	return (fecTo - fecFrom)/86400000;
}
else 
{
	if (from.value == "" || to.value == "") 
		return -1;
}
return -1;
}