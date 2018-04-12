<p>Estas propiedades se pueden establecer de forma genérica para todos los niveles
  de autenticación soportados y también se pueden particularizar por niveles,
  de forma que si una propiedad no esta rellena para un nivel se tomará el valor
  de la propiedad de las especificaciones genéricas. <br/>
  Para que un nivel de autenticación este soportado por el trámite tiene que aparecer
  habilitado en una especificación por nivel. </p>
<p><br/>
</p>
<table width="100%" border="1">
  <tr>
    <td width="22%"><div align="center"><strong>Campo</strong></div></td>
    <td width="78%"><div align="center"><strong>Descripci&oacute;n</strong></div></td>
  </tr>
  <tr>
    <td>Activo</td>
    <td>Indica si el tr&aacute;mite est&aacute; activo o no</td>
  </tr>
  <tr>
    <td>Flujo Tramitaci&oacute;n</td>
    <td>Indica si el tr&aacute;mite permite flujo de tramitaci&oacute;n: permite
      que el tr&aacute;mite se vaya remitiendo a diferentes usuarios para que
      sea completado (s&oacute;lo con autenticaci&oacute;n)</td>
  </tr>
  <tr>
    <td>D&iacute;as de persistencia</td>
    <td>
    Indica el n&uacute;mero de d&iacute;as que se permitir&aacute; que el tr&aacute;mite se mantenga sin modificarse en la zona de persistencia.
    Una vez pasados estos d&iacute;as el tr&aacute;mite ser&aacute; eliminado.
    <br/>
    Para que se tenga en cuenta debe tomar un valor mayor que 0. Si tiene un valor menor o igual a 0 se da 365 d&iacute;as.</td>
  </tr>
  <tr>
    <td>D&iacute;as de preregistro</td>
    <td>Indica el n&uacute;mero de d&iacute;as que, una vez enviado el tr&aacute;mite, este se mantendr&aacute; en la zona de preregistro
      para que el ciudadano vaya al punto de registro a confirmarlo mediante la entrega de la documentaci&oacute;n presencial.
      <br/>
      La fecha l&iacute;mite de presentaci&oacute;n resultante no exceder&aacute; de la fecha de fin de plazo
      (si queremos que se pueda confirmar durante todo el plazo deberemos establecer tantos días de preregistro como días tenga el plazo).
      <br/>
      Para que se tenga en cuenta debe tomar un valor mayor que 0. </td>
  </tr>
  <tr>
    <td>Script de validacion de inicio</td>
    <td>
    	<p>
    		Permite realizar una validaci&oacute;n antes de iniciar (o cargar de persistencia) un tr&aacute;mite.
	      	El tipo de este campo es <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a>
		    y no se espera ning&uacute;n resultado. En caso de no permitir iniciar se debe generar un error.		</p>
		<p>
		    Este script se ejecuta al inicio del tr&aacute;mite y no puede depender de datos de los formularios.</p>
		<p>
		    En este script adem&aacute;s se permiten establecer propiedades de un tr&aacute;mite de forma din&aacute;mica.
    		La forma de indicar estas propiedades es mediante el plugin <em>CONFIGURACION_DINAMICA</em>. Los m&eacute;todos disponibles son:
   			<br/>
		   	<em>
		   		// Establecer plazo presentacion dinamicamente<br/>
		   		// (formato fechas: YYYYMMDDHHMISS)<br/>
		   		CONFIGURACION_DINAMICA.setPlazoDinamico(true);<br/>
		   		CONFIGURACION_DINAMICA.setPlazoInicio('20070115080000');<br/>
		        CONFIGURACION_DINAMICA.setPlazoFin('20070115235959');<br/>
		      </em>    	</p>   </td>


     </td>
  </tr>
  <tr>
    <td>Referencia representante</td>
    <td>
    	<p>Para crear el asiento es necesario indicar qui&eacute;n es el representante y su procedencia geogr&aacute;fica.</p>

    	<p>Se puede indicar de dos formas:
    		<ul>
    			<li>Scripts separados (nif, nombre, provincia, localidad y pa&iacute;s): se especifican los datos uno en cada script</li>
    				<ul>
    					<li>Nif: debe indicarse el NIF</li>
    					<li>Nombre: nombre completo</li>
    					<li>Pa&iacute;s: debe indicarse el c&oacute;digo de pa&iacute;s</li>
    					<li>Provincia: debe indicarse el c&oacute;digo de provincia</li>
    					<li>Localidad: debe indicarse el c&oacute;digo de localidad</li>
    				</ul>
    			<li>Script unificado de datos representante: en un solo script se especifican los datos (adem&aacute;s se pueden incluir los datos postales)
    				<br/>
        				<em>
					   		// Indica si es anonimo <br/>
							INTERESADO.setAnonimo(false);<br/>
							// Nif<br/>
							INTERESADO.setNif("11111111H");<br/>
							// Pasaporte (Es exluyente con el seteo del NIF, y el formato del documento deberá ser CODPAIS(3)/NUMPASAPORTE(20))<br/>
							INTERESADO.setPasaporte("ESP/1234567");<br/>
							// Se puede indicar el nombre de forma desglosada o completa (tiene preferencia la desglosada)<br/>
							INTERESADO.setNombre("Jose");<br/>
							INTERESADO.setApellido1("Garcia");<br/>
							INTERESADO.setApellido2("Lopez");<br/>
							INTERESADO.setApellidosNombre("Garcia Lopez, Jose");<br/>
							// Procedencia geografica<br/>
							INTERESADO.setCodigoPais("ESP");<br/>
							INTERESADO.setCodigoProvincia("7");<br/>
							INTERESADO.setCodigoLocalidad("40");<br/>
							// Datos postales<br/>
							INTERESADO.setDireccion("C. Principal, 40 pta 7");<br/>
							INTERESADO.setCodigoPostal("07040");<br/>
							INTERESADO.setTelefono("901234567");<br/>
							INTERESADO.setEmail("correo@dominio.es");<br/>
		      			</em>
    			</li>
    		</ul>
    	</p>

    	<p>Estos datos son <strong>obligatorios</strong> especificarlos si el trámite es autenticado (en las especificaciones gen&eacute;ricas o por nivel).<br>
        <br>La &uacute;nica excepci&oacute;n permitida es para acceso An&oacute;nimo. Si el destino es registro el nif podr&aacute; ser opcional pero el
        nombre ser&aacute; obligatorio. Si el destino no es registro el nif y el nombre podr&aacute;n ser opcionales.
        Para no establecer el nif o el nombre se deber&aacute; en funci&oacute;n del script usado:
        <ul>
        	<li>Scripts separados: dejar vac&iacute;o el script o bien que el script devuelva la cadena "NO-VALOR"</li>
        	<li>Script unificado: para que sea an&oacute;nimo hay que indicarlo mediante la funcion setAnonimo</li>
        </ul>
        </p>

    	 <p>
        	El tipo de estos campos es <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a>
        	y espera en cada caso el valor del NIF y el nombre completo.
        </p>


        <p>Para los datos geogr&aacute;ficos existen dominios gen&eacute;ricos para la obtenci&oacute;n de estas listas de valores:
        	<ul>
        		<li>Pa&iacute;s: Dominio GEPAISES</li>
        		<li>Provincias (España): Dominio GEPROVINCI</li>
        		<li>Municipios de una provincia: Dominio GEGMUNICI</li>
        	</ul>
        </p>

  </tr>
  <tr>
    <td>Referencia representado</td>
    <td>
    	<p>En caso de que exista representaci&oacute;n es necesario indicar qui&eacute;n es el representado.</p>
    	<p>Se puede indicar de dos formas:
    		<ul>
    			<li>Scripts separados (nif, nombre): se especifican los datos uno en cada script</li>
    				<ul>
    					<li>Nif: debe indicarse el NIF</li>
    					<li>Nombre: nombre completo</li>
    				</ul>
    			<li>Script unificado de datos representado: en un solo script se especifican los datos (adem&aacute;s se pueden incluir los datos postales).
    			Tienen el mismo formato que el script de representante</li>
    		</ul>
    	</p>
    	<p>El tipo de estos campos es <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a> y espera en cada caso el valor del NIF y el nombre completo.</p>
     </td>
  </tr>
  
  <tr>
    <td>Script procedimiento destino tr&aacute;mite</td>
    <td>
    	<p>
    		Este script permite establecer din&aacute;micamente el procedimiento destino del tr&aacute;mite
    		Aunque se establezca din&aacute;micamente por script se deber&aacute;n establecer un valor inicial en el alta del tr&aacute;mite.
    		Por defecto el procedimiento destino del tr&aacute;mite tendrá ese valor.
    	</p>
    	<p>
	      	El tipo de este campo es <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a>
		    y no se espera ning&uacute;n resultado. En caso de error se debe generar un error de script.
		<p/>
		<p>
		    Este script se ejecuta al inicio del trámite y no puede depender de datos de los formularios.
		</p>
		<p>
		    Para establecer los valores del destinatario se dispondr&aacute; del plugin <em>PROCEDIMIENTO_DESTINO_TRAMITE</em>. El m&eacute;todo disponible es:
   			<br/>
		   	<em>
		   		// Establecer procedimiento destinatario dinamicamente<br/>
		   		PROCEDIMIENTO_DESTINO_TRAMITE.setProcedimiento('PROC1');<br/>
		      </em>
		 </p>
	  </td>
  </tr>

  <tr>
    <td>Script de destinatario tr&aacute;mite</td>
    <td>
    	<p>
    		Este script permite establecer din&aacute;micamente el destinatario del tr&aacute;mite: oficina registral, organo destino y unidad administrativa.
    		Aunque se establezca din&aacute;micamente por script se deber&aacute;n establecer unos valores iniciales en la ficha de la versi&oacute;n del tr&aacute;mite.
    		Por defecto el destinatario tendr&aacute; estos valores.
    	</p>
    	<p>
	      	El tipo de este campo es <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a>
		    y no se espera ning&uacute;n resultado. En caso de error se debe generar un error de script.
		<p/>
		<p>
		    Este script se ejecuta en el paso registrar y por tanto puede depender de datos de los formularios introducidos previamente por el ciudadano.
		</p>
		<p>
		    Para establecer los valores del destinatario se dispondr&aacute; del plugin <em>DESTINATARIO_TRAMITE</em>. Los m&eacute;todos disponibles son:
   			<br/>
		   	<em>
		   		// Establecer destinatario dinamicamente<br/>
		   		DESTINATARIO_TRAMITE.setOficinaRegistral('OF1');<br/>
		   		DESTINATARIO_TRAMITE.setOrganoDestino('1');<br/>
		        DESTINATARIO_TRAMITE.setUnidadAdministrativa('2');<br/>
		      </em>
		 </p>
	  </td>
  </tr>

  <tr>
    <td>Url fin </td>
    <td>Al finalizar el tr&aacute;mite se redirige por defecto al portal del organismo. Mediante este  <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a> se puede particularizar esta redirecci&oacute;n. Debe devolver una cadena indicando la url de finalizaci&oacute;n. Si se devuelve la cadena "[ZONAPER]" se redirigir&aacute; a la zona personal mostrando el tr&aacute;mite. </td>
  </tr>
   <tr>
     <td>Script validaci&oacute;n de fin </td>
     <td><p>Permite realizar una validaci&oacute;n antes de finalizar el tr&aacute;mite (previo al envio/registro).
       El tipo de este campo es <a class="enlaceAyuda" href="../ayuda_es/ayudaScript.jsp">script</a> y no se espera ning&uacute;n resultado. En caso de no permitir finalizar se debe generar un error e indicar el mensaje oportuno.</p>     </td>
   </tr>
   <tr>
     <td>Notificaci&oacute;n telem&aacute;tica</td>
     <td>
     	<p>
     		Indica si  un tr&aacute;mite puede generar notificaciones telem&aacute;ticas. En caso de permitirse se recoger&aacute; la conformidad del ciudadano para ser notificado telem&aacute;ticamente.
     	</p>
     	<p>
     		En caso de que Sistra est&eacute; configurado para que sean obligatorios los avisos para las notificaciones se permitir&aacute;:
     		<ul>
     			<li>indicar si se debe recoger el m&oacute;vil para los avisos.</li>
     			<li>establecer en los scripts de Datos de contacto (email/sms) los valores por defecto para los avisos de notificaci&oacute;n (sino se coger&aacute;n los valores de la zona personal).
     			En la pantalla de conformidad se le dar&aacute; opci&oacute;n al ciudadano a verificar estos valores por defecto.</li>
     		</ul>
     	</p>
     </td>
   </tr>
   <tr>
     <td>Alertas tramitaci&oacute;n</td>
     <td>
     	<p>
     		Indica si se generan alertas avisando al ciudadano durante la fase de cumplimentaci&oacute;n y registro de una solicitud. Actualmente existen 3 posibles tipos de alertas:
     		<ul>
     			<li>si se finaliza el tr&aacute;mite (email). Una &uacute;nica alerta tras finalizaci&oacute;n de solicitud.</li>
	     		<li>si se realizan pagos y no se finaliza el tr&aacute;mite (email/SMS). Posibilidad de repetici&oacute;n peri&oacute;dica</li>
	     		<li>se finaliza un preregistro pero no se confirma (email/SMS). Posibilidad de repetici&oacute;n peri&oacute;dica</li>
     		</ul>
     		En caso de habilitar las alertas de tramitaci&oacute;n se deber&aacute; informar los Datos de contacto para establecer email/sms.
     		<br/><br/>
     		En las alertas con repetición peri&oacute;dica se realizar&aacute; un aviso inicial y posteriormente una aviso peri&oacute;dico hasta que se supere la situaci&oacute;n que provoca la alerta.
     		A nivel de configuraci&oacute;n de la plataforma se establece si se debe generar la alerta, el tiempo de retardo para el aviso inicial, si se generan avisos peri&oacute;dicos y el intervalo de repetici&oacute;n.
     		Si tiene alguna duda sobre c&oacute;mo están configuradas las alertas, consulte con el administrador.
     		<br/><br/>
     		Si se habilita la finalizaci&oacute;n autom&aacute;tica de tr&aacute;mites, antes de enviar correo de alerta se intentaran finalizar los tr&aacute;mites an&oacute;nimos que se hayan pagado y est&eacute;n pendientes de enviar.
     	</p>
     </td>
   </tr>
   <tr>
     <td>Datos contacto</td>
     <td>
     	<p>
     		Se establece mediante scripts cual es el email y sms de contacto con el ciudadano para poder ser usado en notificaciones y alertas de tramitaci&oacute;n.
     	</p>
     	<p>
     		Se puede indicar si se valida el número de m&oacute;vil enviando un SMS con un c&oacute;digo de confirmaci&oacute;n. Este c&oacute;digo se envia y se solicita en el paso de registro.
     	</p>
     </td>
   </tr>
  <tr>
    <td>Personalizaci&oacute;n justificante</td>
    <td>Permite personalizar el justificante de registro est&aacute;ndard:
    	<ul>
    		<li>Ocultar clave tramitaci&oacute;n: Para tr&aacute;mites an&oacute;nimos permite establecer que no se muestre la clave de tramitaci&oacute;n en el justificante.
    		Esta clave sirve para realizar el seguimiento del tr&aacute;mite a trav&eacute;s de la zona personal.</li>
    		<li>Ocultar nif/nombre: Permite establecer que no se muestre el nif/nombre en el justificante.</li>
    		<li>Datos solicitud: Permite establecer en el justificante un apartado espec&iacute;fico con datos particulares de la solicitud.</li>
    	</ul>
    </td>
  </tr>
  <tr>
    <td height="49">Instrucciones Inicio (texto HTML)</td>
    <td>Se pueden indicar instrucciones que aparecer&iacute;an en el paso inicial
      &quot;Debe saber&quot;.</td>
  </tr>
  <tr>
    <td>Mensaje Inactividad</td>
    <td>En caso de marcar un tr&aacute;mite como inactivo se permite particularizar
      el mensaje que le aparecer&aacute; al usuario.</td>
  </tr>
  <tr>
    <td>Instrucciones fin</td>
    <td>Permite personalizar el mensaje de finalizaci&oacute;n del tr&aacute;mite. Este mensaje aparecerá en el justificante.</td>
  </tr>
  <tr>
    <td>Instrucciones entrega  </td>
    <td>Cuando haya que entregar documentacion presencial pueden establecerse
      instrucciones de entrega especificas.</td>
  </tr>
  <tr>
    <td>Fecha l&iacute;mite entrega presencial</td>
    <td>Cuando haya que entregar documentaci&oacute;n presencial pueden establecer un mensaje particularizado para la fecha tope de entrega en lugar del mensaje est&aacute;ndard: &quot;La fecha l&iacute;mite de entrega es ...&quot; </td>
  </tr>
</table>
