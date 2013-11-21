<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<P class=centrado>En aquesta página es pot   consultar l'estat dels enviaments. Seleccioneu els parámetres del filtre i feu clic sobre "Recerca d'enviaments": </P> 
<table width="100%" border="0"> 
  <tr> 
    <td width="36"><img src="imgs/icones/form_procesado_no.gif"></td> 
    <td width="705">Enviament pendent de   processar.</td> 
    </tr> 
  <tr> 
    <td><img src="imgs/icones/documento_enviandose.gif"></td> 
    <td>Enviament processant-se. A causa de la taxa de sortida de missatges SMS (3 x seg.) i depenent del nombre de missatges SMS  a enviar l'enviament pot   ocupar diversos períodes d'enviament (1 període = 1 hora). </td> 
  </tr> 
  <tr> 
    <td><img src="imgs/icones/form_procesado_si.gif"></td> 
    <td>Enviament s'ha realitzat correctament</td> 
    </tr>
  <tr>
    <td><img src="imgs/icones/document_parat.gif"></td>
    <td>Enviament s'ha cancel·lat. </td>
    </tr>
  <tr>
    <td><img src="imgs/icones/form_procesado_error.gif"></td>
    <td>Enviament processant-se i s'han produït errors. Es tornará a intentar en el próxim període d'enviament. </td>
    </tr>
</table>
<P class=centrado><STRONG></STRONG>  Per a consultar el detall d'un enviament feu clic sobre aquest enviament i se li mostrará una   pantalla amb la informació. En aquesta pantalla de detall   tindreu l'opció de cancel·lar un enviament i d'exportar la seva informació (missatges,   destinataris, errors produïts, etc.) a una fulla Excel. </P>
<P class=centrado> A més podeu donar d'alta de forma manualment enviaments de tipus Email   i de tipus SMS. També podeu importar un enviament a partir d'un fitxer XML .</P>