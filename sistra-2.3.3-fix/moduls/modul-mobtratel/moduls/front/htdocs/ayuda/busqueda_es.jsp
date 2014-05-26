<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<P class=centrado>En esta página se puede consultar el estado de los envíos. Para ello seleccione los parámetros del filtro y haga click sobre &quot;Búsqueda de envíos&quot; : </P>
<table width="100%" border="0">
  <tr>
    <td width="36"><img src="imgs/icones/form_procesado_no.gif"></td>
    <td width="791">Envío pendiente de procesar.</td>
    </tr>
  <tr>
    <td><img src="imgs/icones/documento_enviandose.gif"></td>
    <td>Envío procesándose. Debido a la tasa de salida de mensajes SMS (3 x seg.) y dependiendo del número de mensajes SMS a enviar el envío puede ocupar varios períodos de envío (1 período = 1 hora).</td>
  </tr>
  <tr>
    <td><img src="imgs/icones/form_procesado_si.gif"></td>
    <td>Envío se ha realizado correctamente. </td>
    </tr>
  <tr>
    <td><img src="imgs/icones/document_parat.gif"></td>
    <td>Envío se ha cancelado.</td>
    </tr>
  <tr>
    <td><img src="imgs/icones/form_procesado_error.gif"></td>
    <td>Envío se esta procesando y se han encontrado errores. Se volverá a intentar en el próximo período de envío.</td>
    </tr>
</table>
<P class=centrado><STRONG></STRONG>  Para consultar el detalle de un envío haga click sobre dicho envío y se le mostrará una pantalla con la información. En esta pantalla de detalle tendrá la opción de cancelar un envío y de exportar su información (mensajes, destinatarios, errores producidos, etc.) a una hoja Excel. </P>
<P class=centrado>Además puede dar de alta de forma manualmente envíos de tipo Email y de tipo SMS. También puede importar un envío a partir de un fichero XML. </P>