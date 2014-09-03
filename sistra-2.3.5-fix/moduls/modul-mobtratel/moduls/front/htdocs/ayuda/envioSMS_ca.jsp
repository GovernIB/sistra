<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<P class=centrado>En aquesta pàgina pot   donar d'alta un enviament  SMS:</P>
<table width="100%" border="0">
  <tr>
    <td width="151"><strong>Nom del enviament</strong></td>
    <td width="199">Text descriptiu del enviament</td>
  </tr>
  
  <tr>
    <td><strong>Missatge</strong></td>
    <td>Missatge SMS. </td>
  </tr>
  <tr>
    <td><strong>Destinataris</strong></td>
    <td>Llista de destinataris del missatge separats por &quot;;&quot; </td>
  </tr>
  <tr>
    <td><strong>Compte</strong></td>
    <td>Comptes d'enviament sobre la qual el gestor té permís.</td>
  </tr>
  <tr>
    <td><strong>Tipus de programació</strong></td>
    <td><p>Inmediata: enviament que es produirà després de l'alta  (en un espai de 1 minut ). </p>
      <p>Programada: enviament programat per a una certa data - hora.</p></td>
  </tr>
  <tr>
    <td><strong>Data de caducitat </strong></td>
    <td><p>Caducitat del missatge. Si el missatge no s'ha pogut enviar   després d'aquesta data de   caducitat es cancel·larà automàticament. </p></td>
  </tr>
</table>