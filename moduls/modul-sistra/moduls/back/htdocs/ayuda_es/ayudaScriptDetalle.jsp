 <p>
  <b>Sintaxis</b><br/>
  Los scripts se codifican mediante sentencias de Javascript. Dentro de este c&oacute;digo 
  se podr&aacute;n utilizar una serie de <strong>Plugins</strong> que nos permitir&aacute;n 
  consultar dominios, obtener valores de campos de formularios, obtener datos 
  del usuario, etc.</p>
<p>
  Para retornar un resultado <b>no hay que utilizar</b> la sentencia <b>return</b>, 
  sino que hay que dejar una &uacute;ltima l&iacute;nea con la variable a devolver.<br>
  <em> Por ejemplo si queremos devolver el resultado de la variable result, la 
  &uacute;ltima l&iacute;nea de nuestro script deber&iacute;a ser:</em></p>

    <p><em>&lt;&lt; resto del script &gt;&gt;<br>
      </em><em>result; <br>
      </em></p>
      
<p><b>Plugins</b> </p>
<ul>
  <li><b>Gesti&oacute;n de errores<br/>
    </b>La gesti&oacute;n de errores se realiza a trav&eacute;s del plugin <strong>ERRORSCRIPT. 
    </strong>A trav&eacute;s de este plugin indicaremos si se ha producido un 
    error en el script. Adem&aacute;s podemos establecer el mensaje de error de dos formas distintas:
    <ul>
      <li>mediante un c&oacute;digo de mensaje 
        definido en los mensajes de validaci&oacute;n del tr&aacute;mite.</li>
      <li>mediante un texto din&aacute;mico directamente desde el script</li>
    </ul>
    <blockquote>
      <p>(Si no establecemos 
        un mensaje se utilizar&aacute; uno gen&eacute;rico que indicar&aacute; 
        que no se ha pasado la validaci&oacute;n.)<br>
        <br>
        <em>ERRORSCRIPT.setExisteError(true);</em><br>
        <em>ERRORSCRIPT.setMensajeError(&quot;ERR1&quot;);</em> // Establece mensaje por c&oacute;digo de error<br> 
        <em>ERRORSCRIPT.setMensajeDinamicoError(&quot;Texto de error&quot;);</em> // Establece mensaje con texto din&aacute;mico </p>
    </blockquote>
  </li>
  
  <li><b>Acceso a mensajes<br/>
    </b>Podemos acceder a mensajes de validacion definidos en el tramite a traves del plugin <strong>PLUGIN_MENSAJES. 
    </strong>
    <br>
    <em>PLUGIN_MENSAJES.getMensaje(&quot;ERR1&quot;);</em><br/>
    <br/>
  </li>
  <li><b>Acceso a dominios<br/>
    </b>A trav&eacute;s de <strong>PLUGIN_DOMINIOS</strong> podemos recuperar 
        dominios (consultas) definidas en la plataforma<br>
    <br>
    <em>id = PLUGIN_DOMINIOS.crearDominio('LOCALIDADES');<br>
    PLUGIN_DOMINIOS.establecerParametro(id,'03');<br>
    PLUGIN_DOMINIOS.recuperaDominio(id);<br>
    num=PLUGIN_DOMINIOS.getValoresDominio(id).getNumeroFilas();<br>
    for (i=1;i&le;num;i++){<br>
    nombre = PLUGIN_DOMINIOS.getValoresDominio(id).getValor(i,&quot;LOCALIDAD&quot;);<br>
    codigo = PLUGIN_DOMINIOS.getValoresDominio(id).getValor(i,&quot;CODIGO&quot;);<br>
    } <br>
    PLUGIN_DOMINIOS.removeDominio(id);</em> </li>
  <br/>
  <br/>
  <li><b>Acceso a datos de formularios<br/>
    </b>A trav&eacute;s de <strong>PLUGIN_FORMULARIOS</strong> podemos obtener 
      datos de los formularios. Como par&aacute;metro hay que pasar el identificador 
      del documento, la instancia (ser&aacute; 1) y el nombre del campo.El nombre del campo se construir&aacute; a partir del XPATH de la siguiente forma:<br />
    /FORMULARIO/DATOS_PERSONALES/NOMBRE = DATOS_PERSONALES.NOMBRE<br>
    <br>
    <em> valor = PLUGIN_FORMULARIOS.getDatoFormulario('FOR1',1,'campo1');</em> 
    <br>
    <br>
    Si el campo del formulario es una <strong>lista desplegable</strong> se puede acceder al código del elemento seleccionado de la siguiente forma: <br>
    <br>
    <em>valor = PLUGIN_FORMULARIOS.getDatoFormulario('FOR1',1,'campo1[CODIGO]');</em> 
    <br/>
    <br/>
    Si el campo del formulario es <strong>multivaluado</strong> (lista desplegada, lista árbol, ...) se puede acceder al número de valores y a cada valor en particular de la siguiente forma: <br>
    <br/>
    <em>numValores = PLUGIN_FORMULARIOS.getNumeroValoresCampo('FOR1',1,'campo1'); </em>
    <br/>
    for (i = 0; i < numValores; i++) {
    <br/>
    <em> valor = PLUGIN_FORMULARIOS.getDatoFormulario('FOR1',1,'campo1',i); </em>
    <br/>
    <em> codigo = PLUGIN_FORMULARIOS.getDatoFormulario('FOR1',1,'campo1[CODIGO]',i); </em>
	<br/>
	<em>} </em>
	<br/>
    <br/>
    <br>
    Para acceder al estado actual de un formulario (p.e. para saber si un formulario se ha completado correctamente): <br>
    <br>
    <em>// Valores estado: Correcto (S) - Incorrecto (N) - No rellenado (V) <br/>
    valor = PLUGIN_FORMULARIOS.getEstadoFormulario('FOR1',1);</em> </li>
  <br/>
  <br/>
  <li><b> Acceso a datos de la sesi&oacute;n<br/>
    </b>Mediante <strong>PLUGIN_DATOSSESION</strong> podemos obtener informaci&oacute;n 
      relativa al usuario que ha iniciado sesi&oacute;n.<br>
    <br>
    <em> codUsuario = PLUGIN_DATOSSESION.getCodigoUsuario();</em> <br>
    <em> nivel = PLUGIN_DATOSSESION.getNivelAutenticacion(); // Certificado (C) / Usuario (U) / An&oacute;nimo (A)</em> <br>
    <em> nif = PLUGIN_DATOSSESION.getNifUsuario();</em><br>
    <em> nombre = PLUGIN_DATOSSESION.getNombreUsuario();</em><br>
    <em> apellido1 = PLUGIN_DATOSSESION.getApellido1Usuario();</em><br>
    <em> apellido2 = PLUGIN_DATOSSESION.getApellido2Usuario();</em><br>
    <em> nombreCompleto = PLUGIN_DATOSSESION.getNombreCompletoUsuario();</em><br>
    <em> idioma = PLUGIN_DATOSSESION.getIdioma();</em>   <br> 
    <em> email = PLUGIN_DATOSSESION.getEmail();</em> <br>
	<em> movil = PLUGIN_DATOSSESION.getTelefonoMovil();</em> <br>
	<em> fijo = PLUGIN_DATOSSESION.getTelefonoFijo();</em> <br>
	<em> cp = PLUGIN_DATOSSESION.getCodigoPostal();</em> <br>
	<em> direccion = PLUGIN_DATOSSESION.getDireccion();</em> <br>
	<em> provincia = PLUGIN_DATOSSESION.getProvincia();</em> <br>	
	<em> municipio = PLUGIN_DATOSSESION.getMunicipio();</em><br>
	<em> habilitarAvisos = PLUGIN_DATOSSESION.getHabilitarAvisos();</em><br/>
	<em> perfilAcceso = PLUGIN_DATOSSESION.getPerfilAcceso(); // CIUDADANO / DELEGADO </em> <br>
	<em> idTramitacion = PLUGIN_DATOSSESION.getIdTramitacion(); // Id tramitacion</em> 
	<br/>
	<br/>
	En caso de el perfil de acceso sea DELEGADO los datos anteriores se referir&aacute;n a la entidad que delega 
	y se podr&aacute; acceder a los datos del delegado que realiza el tr&aacute;mite mediante:
	<br/>
	<em> nifDelegado = PLUGIN_DATOSSESION.getNifDelegado();</em><br>
    <em> nombreDelegado = PLUGIN_DATOSSESION.getNombreDelegado();</em><br>
    <em> apellido1Delegado = PLUGIN_DATOSSESION.getApellido1Delegado();</em><br>
    <em> apellido2Delegado = PLUGIN_DATOSSESION.getApellido2Delegado();</em><br>
    <em> nombreCompletoDelegado = PLUGIN_DATOSSESION.getNombreCompletoDelegado();</em><br>
    </li>
</ul>
<ul>
  <li><b> Acceso a datos del tr&aacute;mite <br/>
    </b>Mediante <strong>PLUGIN_TRAMITE</strong> podemos obtener informaci&oacute;n 
    relativa a la definci&oacute;n del tr&aacute;mite.<br />
    <br />
    <em> fechaini = PLUGIN_TRAMITE.getPlazoInicio();</em>  <em>//en formato dd/MM/yyyy HH:mm:ss </em><br />
    <em> fechafin = PLUGIN_TRAMITE.getPlazoFin();</em> <em>//en formato dd/MM/yyyy HH:mm:ss </em><br />
  </li>
</ul>
<ul>
  <li><b>Acceso a par&aacute;metros inicio<br/>
    </b>A trav&eacute;s de <strong>PLUGIN_PARAMETROSINICIO</strong> podemos acceder 
    a par&aacute;metros de inicio que se indiquen en la url al iniciar el tr&aacute;mite. 
    <br/>
    <br/>
    Por ejemplo, la url para iniciar un tr&aacute;mite es: /sistrafront/init.do?language=es&amp;modelo=TRAMITE&amp;version=1<br>
    hay casos en los que interesa parametrizar un tr&aacute;mite mediante par&aacute;metros 
    de inicio de forma que a la url de inicio le a&ntilde;adimos parametros: /sistrafront/init.do?language=es&amp;modelo=TRAMITE&amp;version=1&amp;par1=dd&amp;par2=ee 
    <br/>
    Estos par&aacute;metos son accesibles mediante este plugin:<br>
    <em>par1 = PLUGIN_PARAMETROSINICIO.getParametro('par1');</em><br>
    <br/>   
    Para los tr&aacute;mites de subsanaci&oacute;n hay dos par&aacute;metros impl&iacute;citos:
    <ul>
    	<li>subsanacionExpedienteId: indica id del expediente al que pertenece el tr&aacute;mite</li>
    	<li>subsanacionExpedienteUA: indica unidad administrativa del expediente al que pertenece el tr&aacute;mite</li>
    </ul>
    <br/>
  </li>
  <li><b> Debug de script<br/>
    </b>Los scripts se pueden tracear internamente mediante el plugin <strong>PLUGIN_LOG</strong>. 
    <br>
    <br>
    <em>PLUGIN_LOG.debug(res);</em><br>
  </li>
</ul>
<ul>
  <li><b>Validaciones de Forms<br/>
    </b>Est&aacute;n disponibles las validaciones que se pueden realizar en Forms: <strong>ValidacionesPlugin</strong> y <strong>DataPlugin</strong>  </li>
</ul>
<br/>
<br/>
<br/>
<div style="TEXT-ALIGN: center;"></div><a href="javascript:window.history.back();" class="enlaceAyuda">Volver</a></div>