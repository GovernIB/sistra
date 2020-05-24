<p align="center"><strong><em>Per a poder modificar una versi&oacute; d'un tr&agrave;mit
  previament s'ha de Bloquejar la versi&oacute;.</em></strong></p>

<table width="100%" border="1">
  <tr>
    <td width="22%"><div align="center"><strong>Camp</strong></div></td>
    <td width="78%"><div align="center"><strong>Descripci&oacute;</strong></div></td>
  </tr>
  <tr>
    <td>Versi&oacute;</td>
    <td>Versi&oacute; del tr&agrave;mit</td>
  </tr>
  <tr>
    <td>Motiu</td>
    <td>Motiu que genera el canvi de versi&oacute; per a control intern.</td>
  </tr>
    <tr>
    <td>Tag cuadern de carrega</td>
    <td>Tag que permet verificar al desenvolupador la versió posada en producció.</td>
  </tr>
  <tr>
    <td>Fecha exportación trámite</td>
    <td>Fecha en la que se exportó el xml del trámite con el cual ha sido importado. Sirve para identificar que xml ha sido utilizado para importar un trámite.</td>
  </tr>

  <tr>
    <td>Termini presentaci&oacute;</td>
    <td>En cas de que el tr&agrave;mit dispose de termini de presentaci&oacute;
      s'ha d'especificar aquest termini</td>
  </tr>
   <tr>
    <td>Idiomes</td>
    <td>S'indica la llista d'idiomes soportats per al tr&agrave;mit. Al modificar
      qualsevol element de la versi&oacute; que requereixi traducci&oacute; es
      solicitar&agrave; que s'introdueixi la traducci&oacute; per a aquestos idiomes.</td>
  </tr>
  <tr>
    <td>Ha de signar-se</td>
    <td>Indica que el tr&agrave;mite ha de signar-se en el pas Enviar. Si l'usuari
      no disposa de certificat digital el tipus de tramitaci&oacute; ser&agrave;
      presencial. El tr&agrave;mit haur&aacute; de ser signat per el representant.
      <br/>
      En cas que s'accedeixi en forma delegada haur&agrave; de signar-se per un delegat que
      tingui perm&iacute;s per a enviar tr&agrave;mits en nom de l'entitat
      </td>
  </tr>
  <tr>
    <td>Circuit redu&iuml;t </td>
    <td>Esquema redu&iuml;t que redueix el nombre &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;de pantalles que es pot habilitar per tr&agrave;mits que nom&eacute;s tinguin un formulari, no requereixin &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;signatura (sol&middot;licitud o registre) i no tingui activat la verificaci&oacute; de m&ograve;bil o confirmaci&oacute; notificaci&oacute; telem&agrave;tica. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Si es marca aquesta opci&oacute;, per&ograve; el tr&agrave;mit no compleix les condicions anteriors, es comportar&agrave; com un tr&agrave;mit normal.</td>
  </tr>
  <tr>
    <td>Ir directe a url fi</td>
    <td>Per a tr&agrave;mits amb circuit reduc&iuml;t passa a la url de finalitzaci&oacute; sense passar pel pas de justificant</td>
  </tr>
  <tr>
    <td>Autenticaci&oacute; an&ograve;nima per defecte</td>
    <td>Si el tr&agrave;mit te activat el nivell an&ograve;nim i altres nivells es pot indicar que si s'accedeix al tr&agrave;mit i no se esta autenticat, es autentique autom&agrave;ticament per defecte de forma an&ograve;nima</td>
  </tr>
  <tr>
    <td>Registre automn&agrave;tic</td>
    <td>Al accedir al pas de registrar es realitzar&agrave; autom&agrave;ticament el enviament.</td>
  </tr>
  <tr>
    <td>Destinació del tr&agrave;mit</td>
    <td>Indica destinació del tr&agrave;mit:<br>
      - Registre: passa per registre d'entrada (i posteriormente a la safata)<br>
      - Safata: va directe a la safata (sense passar per registre d'entrada). Per a la destinaci&oacute; Safata es pot habilitar la confirmaci&oacute; autom&aacute;tica: els preenviaments es confirmen autom&agrave;ticament despr&eacute;s d'enviar-se i  no necessiten ser confirmats en el punt de registre) i passen a la Safata.<br>
      - Consulta: tr&agrave;mits que realitzen una consulta i obtinguin com resultat
      u o m&eacute;s documents. (aquests tr&agrave;mits no realitzen entrada en
      safata i nom&eacute;s permeten tenir formularis)<br>
      - Asistent: tr&agrave;mits que utilitzen la plataforma per a rellenar els formularis per després presentarlos presencialment. (aquests tr&agrave;mits no realitzen entrada en
      safata i nom&eacute;s permeten tenir formularis) </td>
  </tr>
  <tr>
    <td>Organisme destinatari</td>
    <td>Organisme al que va dirigit el tr&agrave;mit.
    	La llista de serveis destinaci&oacute; &eacute;s proporcionada pel registre de l'organisme
    </td>
  </tr>
  <tr>
    <td>Unitat administrativa</td>
    <td>Unitat Administrativa responsable tr&agrave;mit.
    	La llista d'unitats administratives s'establix mitjançant els dominis que estableixen l'estructura org&agrave;nica de l'organisme (GESACUNADM y GESACARBUA)
    </td>
  </tr>
  <tr>
    <td>Oficina Registre</td>
    <td>Oficina de registre on es realitzar&agrave; l'apunt registral.
    	La llista d'oficines de registre &eacute;s proporcionada pel registre de l'organisme.
    	Per a tr&agrave;mits que no passen per registre pot prendre qualsevol valor.
	</td>
  </tr>
  <tr>
    <td>Tipus Assumpte</td>
    <td>Tipus d'assumpte de l'apunt registral.
    	La llista d'oficines de registre &eacute;s proporcionada pel registre de l'organisme.
      Per a tr&agrave;mits que no passen per registre pot agafar qualsevol valor.</td>
  </tr>
    <tr>
    <td>Tipus d'acc&eacute;s</td>
    <td>Per a un tr&agrave;mit de tipus consulta indica si s'accedeix al Backoffice mitjançant EJB o Webservice  </td>
  </tr>
  <tr>
  </tr>
    <tr>
    <td>Url de acc&eacute;s</td>
    <td>Indica la url d'acc&eacute;s (necess&agrave;ria en cas de ser webservice o EJB remot).<br/>
      Al formar la url es pot emprar la variable @backoffice.url@, que ser&agrave; reemplaçada segons el valor indicat en els fitxers de configuraci&oacute;.
</td>
  </tr>
  <tr>
  <tr>
    <td>EJB de Consulta</td>
    <td>Quan el tipus d'acc&eacute;s &eacute;s EJB indica el JNDI d'un EJB que implementi l'interf&iacute;cie de destinaci&oacute; de consulta que retorni els documents de resposta</td>
  </tr>
  <tr>
    <td>Localitzaci&oacute; EJB</td>
    <td>Indica si el EJB &eacute;s local o remot (situat en diferent servidor
      d'aplicacions) </td>
  </tr>
  <tr>
    <td>Autenticaci&oacute; expl&iacute;cita </td>
    <td>Indica si per a accedir al EJB/Webservice s'ha de realitzar una autenticaci&oacute;
      expl&iacute;cita (acc&eacute;s amb usuari diferent al que ha iniciat la
      sessi&oacute; de tramitaci&oacute;):
      	<ul>
      		<li>No: el contenidor d'EJB ser&agrave; l'encarregat de traslladar l'autenticaci&oacute; (no v&agrave;lid per a webservices)</li>
      		<li>Amb usuari/password: s'utilitza l'usuari/password indicat per a autenticar la cridada </li>
	      	<li>Autenticaci&oacute; organisme : s'utilitza l'usuari/password suministrat pel plugin d'autenticaci&oacute; expl&iacute;ta de l'organisme per a autenticar la cridada</li>
      	</ul>
      </td>
  </tr>
  <tr>
    <td>Usuari/Password <br></td>
    <td>En cas de requerir una autenticaci&oacute; expl&iacute;cita s'indica l'usuari/password
      amb la qual es realitza </td>
  </tr>
</table>
