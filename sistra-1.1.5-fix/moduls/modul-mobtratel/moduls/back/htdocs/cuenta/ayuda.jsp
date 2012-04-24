 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html locale="true" xhtml="true">
<head>
   <title>Fichero exportacion</title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" />
   <link rel="stylesheet" href="../css/styleA.css" type="text/css" />
</head>

<body class="ventana">
<table class="nomarcd">
    <tr><td class="titulo">Fichero exportacion</td></tr>
	</table>
	<table class="marcd">
	     <tr>
	        <td class="labelm">
	            <table width="100%" border="1">
				  <tr> 
				    <td width="100%">
				    	<p>Hay que construir un fichero de properties que contenga pares de valores: XPATH=Descripcion columna</p>
				    	<p>Para referenciar el XPATH se pueden utilizar los siguientes valores:</p>
				    	<p>VALORES ENTRADA</p>
				    	<ul>
				    		<li>TRAMITE.NUMEROENTRADA</li>
				    		<li>TRAMITE.FECHAENTRADA</li>
				    		<li>TRAMITE.TIPO</li>
				    		<li>TRAMITE.PROCESADA</li>
				    		<li>TRAMITE.IDENTIFICADORTRAMITE</li>
				    		<li>TRAMITE.VERSIONTRAMITE</li>
				    		<li>TRAMITE.NIVELAUTENTICACION</li>
				    		<li>TRAMITE.USUARIOSEYCON</li>
				    		<li>TRAMITE.NUMEROREGISTRO</li>
				    		<li>TRAMITE.FECHAREGISTRO</li>
				    		<li>TRAMITE.NUMEROPREREGISTRO</li>
				    		<li>TRAMITE.FECHAPREREGISTRO</li>
				    		<li>TRAMITE.USUARIONIF</li>
				    		<li>TRAMITE.USUARIONOMBRE</li>
				    		<li>TRAMITE.REPRESENTADONIF</li>
				    		<li>TRAMITE.REPRESENTADONOMBRE</li>
				    		<li>TRAMITE.IDIOMA</li>
				   </ul>
				   <p>VALORES FORMULARIO</p>
				   <p>Se construyen de la siguiente forma: FORMULARIO.<i>ID FORMULARIO</i>.<i>INSTANCIA FORMULARIO</i>.<i>XPATH DATOS</i></p>
				   <br/>
				   <br/>
				   <p>Ejemplo de fichero:</p>
				   <p>				   
				   		TRAMITE.NUMEROENTRADA=NUMERO ENTRADA<br/>
						TRAMITE.FECHAENTRADA=FECHA ENTRADA<br/>				
						TRAMITE.PROCESADA=PROCESADA<br/>
						TRAMITE.IDIOMA=IDIOMA<br/>
						FORMULARIO.FORM1.1.DATOS.DATO1[CODIGO]=codigo Dato 1<br/>
						FORMULARIO.FORM1.1.DATOS.DATO1=Dato 1<br/>						
						FORMULARIO.FORM1.1.DATOS.DATO2=Dato 2<br/>
						FORMULARIO.FORM1.1.DATOS.DATO3=Dato 3<br/>
					</p>				    					    	
				    </td>
				  </tr>				 
				</table>
        </td>
    </tr>
</table>
<br />
</body>
</html:html>
 
 
 
 
