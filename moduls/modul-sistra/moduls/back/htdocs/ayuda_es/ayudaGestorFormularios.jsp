 <p>
 	Para definir un gestor de formularios hay que establecer un identificador y las urls que se ven
 	envueltas en la comunicaci&oacute;n.
 	<br/>
 	El flujo de llamadas para invocar a un formulario es el siguiente:
 	<ul>
 		<li>SISTRA pasa los datos al gestor de formularios invocando a la <em>Url de tramitaci&oacute;n del formulario</em></li>
 		<li>el navegador es redirigido al gestor de formularios mediante la <em>Url de redirecci&oacute;n del formulario</em></li>
 	</ul>
 </p>
 <table width="100%" border="1">
  <tr> 
    <td width="22%"><div align="center"><strong>Campo</strong></div></td>
    <td width="78%"><div align="center"><strong>Descripci&oacute;n</strong></div></td>
  </tr>
  <tr> 
    <td>Identificador</td>
    <td>Identificador del gestor de formularios.</td>
  </tr>
  <tr> 
    <td>Descripci&oacute;n</td>
    <td>Descripci&oacute;n del gestor de formularios</td>
  </tr>
  <tr> 
    <td>Url del Gestor</td>
    <td>Url base del gestor de formularios. En caso de que est&eacute; instalado en el mismo servidor que la plataforma puede establecerse con la variable @sistra.url@.</td>
  </tr>
  <tr> 
    <td>Url de tramitaci&oacute;n del formulario</td>
    <td>Url que ser&aacute; invocada por SISTRA para pasar los datos al gestor de formularios. Esta url ha de ser absoluta. Puede contener variables como la url base @forms.server@</td>
  </tr>
  <tr> 
    <td>Url de redirecci&oacute;n del formulario</td>
    <td>Url a la que se redirigir&aacute; el navegador para redirigirse al gestor. En caso de que el gestor este instalado en el mismo servidor que la plataforma, esta url puede ser relativa. También puede contener variables como la url base @forms.server@.</td>
  </tr>
</table>
