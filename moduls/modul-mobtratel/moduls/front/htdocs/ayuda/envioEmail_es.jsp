<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<P class=centrado>En esta página puede dar de alta un envío Email:</P>
<table width="100%" border="0">
  <tr>
    <td width="151"><strong>Nombre del envío </strong></td>
    <td width="199">Texto descriptivo del envío </td>
  </tr>
  <tr>
    <td><strong>Título</strong></td>
    <td>Asunto del mensaje Email </td>
  </tr>
  <tr>
    <td><strong>Mensaje</strong></td>
    <td>Cuerpo del mensaje Email. Es de tipo HTML. </td>
  </tr>
  <tr>
    <td><strong>Destinatarios</strong></td>
    <td>Lista de destinatarios del mensaje separados por &quot;;&quot; </td>
  </tr>
  <tr>
    <td><strong>Cuenta</strong></td>
    <td>Cuentas de envío sobre la que el gestor tiene permiso. </td>
  </tr>
  <tr>
    <td><strong>Tipo de programación </strong></td>
    <td><p>Inmediata: envío que se producirá después del alta (en un espacio de 1 minuto). </p>
      <p>Programada: envío programado para una cierta fecha - hora.</p></td>
  </tr>
  <tr>
    <td><strong>Fecha de caducidad </strong></td>
    <td>Caducidad del mensaje. Si el mensaje no se ha podido enviar tras esa fecha de caducidad se cancelará automáticamente. </td>
  </tr>
</table>