<p>
	Per a definir un gestor de formularis cal establir un identificador i les urls que es veuen embolicades en la comunicaci&oacute;. 
	<br/>
	El flux de cridades per a invocar a un formulari &eacute;s el següent: 
	<ul>
 		<li>SISTRA passa les dades al gestor de formularis invocant a la  <em>Url de tramitaci&oacute; del formulari</em></li>
 		<li>el navegador &eacute;s redirigit al gestor de formularis mitjançant la <em>Url de redirecci&oacute; del formulari</em></li>
 	</ul>
</p>

 <table width="100%" border="1">
  <tr> 
    <td width="22%"><div align="center"><strong>Camp</strong></div></td>
    <td width="78%"><div align="center"><strong>Descripci&oacute;</strong></div></td>
  </tr>
  <tr> 
    <td>Identificador</td>
    <td>Identificador del gestor de formularis.</td>
  </tr>
  <tr> 
    <td>Descripci&oacute;</td>
    <td>Descripci&oacute; del gestor de formularis.</td>
  </tr>
   <tr> 
    <td>Url del Gestor</td>
    <td>Url base del gestor de formularis. En cas que estigui instal·lat en el mateix servidor que la plataforma pot establir-se amb la variable: @sistra.url@</td>
  </tr>  
  <tr> 
    <td>Url de tramitaci&oacute; del formulari</td>
    <td>Url que ser&agrave; invocada per SISTRA per a passar les dades al gestor de formularis. Aquesta url ha de ser absoluta. Pot contenir variables com la url base  @forms.server@</td>
  </tr>
  <tr> 
    <td>Url de redirecci&oacute; del formulari</td>
    <td>Url a la qual es redirigir&agrave; el navegador per a redirigir-se al gestor. En cas que el gestor aquest instal·lat en el mateix servidor que la plataforma, aquesta url pot ser relativa. Tamb&eacute; pot contenir variables com la url base @forms.server@. </td>
  </tr>
</table>
