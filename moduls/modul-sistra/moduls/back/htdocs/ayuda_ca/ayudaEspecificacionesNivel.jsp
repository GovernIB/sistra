<p>Aquestes propietats es poden establir de forma genèrica per a tots els nivells
  d'autenticació suportats i també es poden particularizar per nivells,
  de manera que si una propietat no aquesta farcida per a un nivell es prendrà el valor
  de la propietat de les especificacions genèriques. <br/>
  Perquè un nivell d'autenticació aquest suportat pel tràmit ha d'aparèixer
  habilitat en una especificació per nivell. </p>
<p><br/>
</p>
<table width="100%" border="1">
  <tr>
    <td width="22%"><div align="center"><strong>Camp</strong></div></td>
    <td width="78%"><div align="center"><strong>Descripci&oacute;</strong></div></td>
  </tr>
  <tr>
    <td>Actiu</td>
    <td>Indica si el tr&agrave;mit est&agrave; actiu o no</td>
  </tr>
  <tr>
    <td>Flux de tramitaci&oacute;</td>
    <td>Indica si el tr&agrave;mit permet flux de tramitaci&oacute;: permet que
      el tr&agrave;mit es vagi remetent a diferents usuaris perqu&egrave; sigui
      completat (nom&eacute;s amb autenticaci&oacute;) </td>
  </tr>


   <tr>
    <td>Dies de persist&egrave;ncia</td>
    <td>
    Indica el nombre de dies que se permitir&aacute; que el tr&agrave;mit es mantinga sense modificar-se en la zona de persist&egrave;ncia.
    Una vegada haja passat estos dies el tr&agrave;mit ser&aacute; eliminat.
	<br/>
    Per a que es tinga en compte ha de agafar un valor major que 0.     </td>
  </tr>
  <tr>
    <td>Dies de preregistre</td>
    <td>
    Indica el nombre de dies que, una vegada enviat el tr&agrave;mit, aquest es mantindr&aacute; en la zona de preregistre
      per a que el ciutad&aacute; vaja al punt de registre a confirmar-lo mitjançant el lliurament de la documentaci&oacute; presencial.
      <br/>
      La data limit de presentaci&oacute; resultant no excedir&aacute; de la data de fin de termini
      (si volem que es puga confirmar durant tot el termini haurem d'establir tants dies de preregistre com dies tinga el termini).
      <br/>
      Per a que es tinga en compte ha de agafar un valor major que 0. Si t&eacute; un valor menor o igual a 0 es d&oacute;na 365 dies.</td>
  </tr>

  <tr>
    <td>Script de validació d'inici</td>
    <td>
    	<p>
    		Permet realitzar una validaci&oacute; abans d'iniciar (o carregar de persistencia) un tr&agrave;mit.
	      	El tipus d'aquest camp és <a class="enlaceAyuda" href="../ayuda_ca/ayudaScript.jsp">script</a>
      		i no espera cap resultat. En cas de no permetre iniciar-se ha de generar
      		un error.
      	</p>
      	<p>
		    Aquest script s'ejecuta al inici del tr&agrave;mit i no pot dependre de dades dels formularis.</p>
      	<p>
		    En este script a m&eacute;s  es permiteix establir propietats d'un tr&agrave;mit de forma din&agrave;mica.
    		El plugin <em>CONFIGURACION_DINAMICA</em> permiteix establir aquestes propietats. Els m&eacute;tods disponibles son:
   			<br/>
		   	<em>
		   		// Establir termini presentacio dinamicament<br/>
		   		// (format dates: YYYYMMDDHHMISS)<br/>
		   		CONFIGURACION_DINAMICA.setPlazoDinamico(true);<br/>
		   		CONFIGURACION_DINAMICA.setPlazoInicio('20070115080000');<br/>
		        CONFIGURACION_DINAMICA.setPlazoFin('20070115235959');<br/>
		      </em>
		 </p>
      </td>
  </tr>
  <tr>
    <td>Refer&egrave;ncia representant</td>
    <td>
    	<p>Per crear el seient cal indicar qui &eacute;s el representant i la seva proced&egrave;ncia geogr&agrave;fica.</p>

    	<p>Es pot indicar de dues maneres:
    		<ul>
    			<li>Scripts separats (NIF, nom, prov&iacute;ncia, localitat i pa&iacute;s): s'especifiquen les dades un a cada script</li>
    				<ul>
    					<li>Nif: cal indicar el NIF</li>
    					<li>Nom: nom complet</li>
    					<li>Pa&iacute;s: s'ha d'indicar el codi de pa&iacute;s</li>
    					<li>Prov&iacute;ncia: s'ha d'indicar el codi de prov&iacute;ncia</li>
    					<li>Localitat: s'ha d'indicar el codi de localitat</li>
    				</ul>
    			<li>Script unificat de dades representant: en un sol script s'especifiquen les dades (a m&eacute;s es poden incloure les dades postals)
    				<br/>
        				<em>
					   		// Indica si és anònim <br/>
							INTERESADO.setAnonimo(false);<br/>
							// NIF<br/>
							INTERESADO.setNif("11111111H");<br/>
							// Passaport (És excloent amb el seteig del NIF, i el format del document haurà de ser CODPAIS(3)/NUMPASSAPORT(20))<br/>
							INTERESADO.setPasaporte("ESP/1234567");<br/>
							// Es pot indicar el nom de forma desglossada o completa (té preferència la desglossada)<br/>
							INTERESADO.setNombre("Jose");<br/>
							INTERESADO.setApellido1("Garcia");<br/>
							INTERESADO.setApellido2("Lopez");<br/>
							INTERESADO.setApellidosNombre("Garcia Lopez, Jose");<br/>
							// Procedència geogràfica<br/>
							INTERESADO.setCodigoPais("ESP");<br/>
							INTERESADO.setCodigoProvincia("7");<br/>
							INTERESADO.setCodigoLocalidad("40");<br/>
							// Dades postals<br/>
							INTERESADO.setDireccion("C. Principal, 40 pta 7");<br/>
							INTERESADO.setCodigoPostal("07040");<br/>
							INTERESADO.setTelefono("901234567");<br/>
							INTERESADO.setEmail("correo@dominio.es");<br/>
		      			</em>
    			</li>
    		</ul>
    	</p>

    	<br />
    	<p>Aquestes dades s&oacute;n <strong>obligat&ograve;ries</strong> especificar si el tr&agrave;mit &eacute;s autenticat (en les especificacions gen&egrave;riques o per nivell).<br>
        <br>L'&uacute;nica excepci&oacute; permesa &eacute;s per a acc&eacute;s An&ograve;nim. Si la destinaci&oacute; &eacute;s registre el NIF podr&agrave; ser opcional per&ograve; el nom ser&agrave; obligatori. Si el dest&iacute; no &eacute;s registre el NIF i el nom podran ser opcionals. Per no establir el NIF o el nom s'haur&agrave; en funci&oacute; del script usat:
        <ul>
        	<li>Scripts separats: deixar buit l'script o b&eacute; que l'script torni la cadena &quot;NO-VALOR&quot;</li>
        	<li>Script unificat: perqu&egrave; sigui an&ograve;nim cal indicar-ho mitjan&ccedil;ant la funci&oacute; setAnonimo</li>
        </ul>
        </p>

    	 <p>
        	El tipus d'aquests camps &eacute;s <a class="enlaceAyuda" href="../ayuda_ca/ayudaScript.jsp">script</a>
        	i espera en cada cas el valor del NIF i el nom complet.
        </p>
        <p>Per les dades geogr&agrave;fiques existeixen dominis gen&egrave;rics per a l'obtenci&oacute; d'aquestes llistes de valors:
        	<ul>
        		<li>Pa&iacute;s: Domini GEPAISES</li>
        		<li>Prov&iacute;ncies (Espanya): Domini GEPROVINCI</li>
        		<li>Municipis d'una prov&iacute;ncia: Domini GEGMUNICI</li>
        	</ul>
        </p>
  </tr>
  <tr>
    <td>Refer&egrave;ncia representat</td>
    <td>
    	<p>En cas que hi hagi representaci&oacute; cal indicar qui &eacute;s el representat.</p>
    	<p>Es pot indicar de dues maneres:
    		<ul>
    			<li>Scripts separats ( nif , nom ) : s'especifiquen les dades un a cada script</li>
    				<ul>
    					<li>Nif : cal indicar el NIF</li>
    					<li>Nom : nom complet</li>
    				</ul>
    			<li>Script unificat de dades representat : en un sol script s'especifiquen les dades (a m&eacute;s es poden incloure les dades postals ) . Tenen el mateix format que l'script de representant</li>
    		</ul>
    	</p>
    	<p>El tipus d'aquests camps &eacute;s <a class="enlaceAyuda" href="../ayuda_ca/ayudaScript.jsp">script</a> i espera en cada cas el valor del NIF i el nom complet.</p>
     </td>
  </tr>
  <tr>
    <td>Script procediment dest&iacute; tr&agrave;mit</td>
    <td>
    	<p>
    		Aquest script permet establir din&agrave;micament el procedimient dest&iacute; del tr&agrave;mit
    		Encara que s'estableixi din&agrave;micament per script s'haur&agrave; d'establir un valor inicial a l'alta del tr&agrave;mit.
    		Per defecte el procediment dest&iacute; del tr&agrave;mit tindr&agrave; aquest valor.
    	</p>
    	<p>
    		El tipus d'aquest camp &eacute;s <a class="enlaceAyuda" href="/sistraback/ayuda_es/ayudaScript.jsp">script</a>
    		i no s'espera cap resultat. En cas d'error s'ha de generar un error de script.
    	</p>
		<p>
		    Aquest script s'ejecuta al inici del tr&agrave;mit i NO pot dependre de dades dels formularis.
		</p>
		<p>
		    Per a establir els valors del destinatari es disposar&agrave; del plugin <em>PROCEDIMIENTO_DESTINO_TRAMITE</em>. El m&egrave;tode disponible &eacute;s:
   			<br/>
		   	<em>
		   		// Establir procediment dest&iacute; din&agrave;micament<br/>
		   		PROCEDIMIENTO_DESTINO_TRAMITE.setProcedimiento('PROC1');<br/>
		      </em>
		 </p>
	  </td>
  </tr>
  <tr>
    <td>Script de destinatari tr&agrave;mit</td>
    <td>
    	<p>
    		Aquest script permet establir din&agrave;micament el destinatari del tr&agrave;mit: oficina registral, organ destinació
    		i unitat administrativa. Encara que s'estableixi din&agrave;micament per script s'haurien d'establir uns valors
    		inicials en la fitxa de la versi&oacute; del tr&agrave;mit. Per defecte el destinatari tindr&agrave; aquests valors.
    	</p>
    	<p>
    		El tipus d'aquest camp &eacute;s <a class="enlaceAyuda" href="/sistraback/ayuda_es/ayudaScript.jsp">script</a>
    		i no s'espera cap resultat. En cas d'error s'ha de generar un error de script.
    	</p>
    	<p>
		    Aquest script s'executa a la passa registrar del tr&agrave;mit i per tant pot dependre de dades dels formularis introdu&iuml;des previament pel ciutad&agrave;.</p>
    	<p>
			Per a establir els valors del destinatari es disposar&agrave; del plugin <em>DESTINATARIO_TRAMITE</em>.	Els m&egrave;todes disponibles s&oacute;n:
   			<br/>
		   	<em>
		   		// Establir destinatari din&agrave;micament<br/>
		   		DESTINATARIO_TRAMITE.setOficinaRegistral('OF1');<br/>
		   		DESTINATARIO_TRAMITE.setOrganoDestino('1');<br/>
		        DESTINATARIO_TRAMITE.setUnidadAdministrativa('2');<br/>
		      </em>
		 </p>
	  </td>
  </tr>



  <tr>
    <td>Url fi</td>
    <td>Al finalitzar el tr&agrave;mit es redirigeix per defecte al portal del organisme. Mitjan&ccedil;ant aquest <a class="enlaceAyuda" href="/sistraback/ayuda_es/ayudaScript.jsp">script</a> es pot particularitzar aquesta redirecci&oacute;. S'ha de tornar una cadena indicant la url de finalitzaci&oacute;.  Si es retorna la cadena "[ZONAPER]" es redirigir&agrave; a la zona personal mostrant el tr&agrave;mit.</td>
  </tr>
  <tr>
    <td>Script validaci&oacute; de fin</td>
    <td><p>Permet realitzar una   validaci&oacute; abans de finalitzar el tr&agrave;mit (previ a l'enviament/registre). El tipus   d'aquest camp &eacute;s <a class="enlaceAyuda" href="/sistraback/ayuda_es/ayudaScript.jsp">script</a>  i no s'espera cap resultat. En cas de no permetre   finalitzar s'ha de generar un error i indicar el missatge oport&uacute;.</p>    </td>
  </tr>
  <tr>
    <td>Notificaci&oacute; telem&agrave;tica</td>
    <td>
      	<p>
     		Indica si un tr&agrave;mit pot   generar notificacions telem&agrave;tiques. En cas de permetre's es recollir&agrave; la conformitat del ciutad&agrave; per a   ser notificat telem&agrave;ticament.
     	</p>
     	<p>
     		En cas que Sistra estigui configurat perqu&egrave; siguin obligatoris els avisos per a les notificacions es permetr&agrave;:
     		<ul>
     			<li>indicar si s'ha de recollir el m&ograve;bil per als avisos.</li>
     			<li>establir en els scripts de contacte (email/sms) els valors per defecte per avisos de notificació (sino s'agafaran els valors de la zona personal).
     			En el pas de conformitat se li donar&agrave; al ciutad&agrave; opci&oacute; a verificar aquests valors per defecte.</li>
     		</ul>
     	</p>
    </td>
  </tr>
  <tr>
     <td>Alertes tramitaci&oacute;</td>
     <td>
     	<p>
     		Indica si es generen alertes avisant al ciutad&agrave; durant la fase d'emplenament i registre d'una sol·licitud. Actualment existeixen 3 possibles tipus d'alertes:
     		<ul>
     			<li>si es finalitza el tr&agrave;mit (email). Una &uacute;nica alerta despr&eacute;s de finalitzaci&oacute; de sol·licitud</li>
	     		<li>es realitzen pagaments i no es finalitza el tr&agrave;mit (email/SMS). Posibilitat de repetici&oacute; peri&ograve;dica</li>
	     		<li>es finalitza un preregistre i no es confirma (email/SMS)</li>
     		</ul>
     		En cas d'habilitar les alertes de tramitaci&oacute; s'haura d'informar les dades de contacte per establir email/sms.
     		<br/><br/>
     		En les alertes amb repetici&oacute; peri&ograve;dica es realitzar&agrave; un av&iacute;s inicial i posteriorment una av&iacute;s peri&ograve;dic fins que se superi la situaci&oacute; que provoca l'alerta. A nivell de configuraci&oacute; de la plataforma s'estableix si s'ha de generar l'alerta, el temps de retard per a l'av&iacute;s inicial, si es generen avisos peri&ograve;dics i l'interval de repetici&oacute;. Si t&eacute; algun dubte sobre com estan configurades les alertes, consulteu amb l'administrador.
     		<br/><br/>
     		Si s'habilita la finalitzaci&oacute; autom&agrave;tica de tr&agrave;mits, abans d'enviar correu d'alerta es intentessin finalitzar els tr&agrave;mits an&ograve;nims que s'hagin pagat i estiguin pendents d'enviar.
     	</p>
     </td>
   </tr>
   <tr>
     <td>Dades contacte</td>
     <td>
     	<p>
     		Permet establir el email i sms de contacte amb el ciutad&agrave; per ser usat en notificacions i alertes de tramitaci&oacute;.
     	</p>
     	<p>
     		Es pot indicar si es valida el n&uacute;mero de m&ograve;bil enviant un SMS amb un codi de confirmaci&oacute;. Aquest codi s'envia i es demana al pas de registre.
     	</p>
     </td>
   </tr>
  <tr>
  	<td>Personalitzaci&oacute; justificant</td>
    <td>Permet personalitzar el justificant de registre estandard:
    	<ul>
    		<li>Ocultar clau tramitaci&oacute;: Per a tr&agrave;mits an&ograve;nims permet establir que no es mostri la clau de tramitaci&oacute; en el justificant.
    		Aquesta clau serveix per a realitzar el seguiment del tr&agrave;mit a través de la zona personal.</li>
    		<li>Ocultar nif/nom: Permet establir que no es mostri el nif/nom en el justificant.</li>
    		<li>Dades sol·licitud: Permet establir en el justificant un apartat espec&iacute;fic amb dades particulars de la sol·licitud</li>
    	</ul>
    </td>
   </tr>
  <tr>
    <td>Dades justificant</td>
    <td>Permet generar dades particularitzades en el Justificant de Registre segons
      les dades dels formularis.</td>
  </tr>
  <tr>
    <td height="71">Instruccions Inici (texte HTML)</td>
    <td>Es poden indicar instruccions que apareixeran en el pas inicial &quot;Ha
      de saber&quot;.</td>
  </tr>
  <tr>
    <td>Oficina Registre</td>
    <td>Oficina de registre. Camp Oficina. Relacionat amb taula de Registre amb
      BORGANI- FAXCORGA. Per a tr&agrave;mits que no passen per registre pot agafar
      qualsevol valor.</td>
  </tr>
  <tr>
    <td>Missatge Inactivitat</td>
    <td>En cas de marcar un tr&agrave;mit com inactiu es permet particularitzar
      el missatge que li apareixer&agrave; a l'usuari.</td>
  </tr>
  <tr>
    <td>Instruccions fi</td>
    <td>Permet personalitzar el missatge de finalitzaci&oacute; del tr&agrave;mit
      <br>Aquest missatge apareixerà en el justificant  </td>
  </tr>
  <tr>
    <td>Instruccions presentació (texte HTML)</td>
    <td>Quan calgui lliurar documentaci&oacute; presencial poden establir-se instruccions
      de lliurament especifiques.</td>
  </tr>
  <tr>
    <td>Data l&iacute;mit lliurament   presencial </td>
    <td>Quan calgui lliurar documentaci&oacute; presencial poden establir un missatge particularitzat per a la data l&iacute;mit de lliurament en   lloc del missatge est&agrave;ndard: &quot;La data l&iacute;mit de lliurament &eacute;s ...&quot;</td>
  </tr>
</table>
