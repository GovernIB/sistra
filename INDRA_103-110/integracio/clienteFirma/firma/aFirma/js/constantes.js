/*******************************************************************************
 * Ruta al directorio de los instalables.                                      *
 * Si no se establece, supone que estan en el mismo directorio(que el HTML).   *
 * Para indicar un directorio en local se debe usar el prefijo "file://", por  *
 * ejemplo "file://C:/Instalables". Se debe usar siempre el separador "/"	 *
 * (nunca "\").											 *
 ******************************************************************************/
var baseDownloadURL;

/*******************************************************************************
 * Ruta directorio del instalador.                                             *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 * Para indicar un directorio en local se debe usar el prefijo "file://", por  *
 * ejemplo "file://C:/Instalador". Se debe usar siempre el separador "/"	 *
 * (nunca "\").											 *
 ******************************************************************************/
var base;

/*******************************************************************************
 * Algoritmo de firma. Puede ser 'SHA1withRSA', 'MD5withRSA' o, salvo que sea  *
 * firma XML, MD2withRSA. Se estable al llamar a configuraFirma en firma.js    *
 ******************************************************************************/
var signatureAlgorithm = 'SHA1withRSA'; // Valor por defecto

/*******************************************************************************
 * Formato de firma. Puede ser 'CMS', 'XADES', 'XMLDSIGN' o 'NONE'.            *
 * Se estable al llamar a configuraFirma en firma.js      				 *
 * Por defecto: CMS.										 *
 ******************************************************************************/
//var signatureFormat = 'XADES-BES'; // Valor por defecto
var signatureFormat = 'CMS'; // Valor por defecto

/*******************************************************************************
 * Mostrar los errores al usuario. Puede ser 'true' o 'false'.                 *
 * Se estable al llamar a configuraFirma en firma.js                           *
 * Por defecto: false.										 *
 ******************************************************************************/
var showErrors = 'false'; // Valor por defecto

/*******************************************************************************
 * Filtro de certificados (expresi?n que determina qu? certificados se le      *
 * permite elegir al usuario). Ver la documentaci?n.                           *
 * Se estable al llamar a configuraFirma en firma.js                           *
 *                                                                             *
 * Ejemplos:                                                                   *
 * - S?lo mostrar certificados de DNIe de firma:                               *
 * var certFilter = '{ISSUER.DN#MATCHES#{"CN=AC DNIE 00(1|2|3),OU=DNIE,'+      *
 *      'O=DIRECCION GENERAL DE LA POLICIA,C=ES"}&&{SUBJECT.DN#MATCHES#'+      *
 *      '{".*(FIRMA).*"}}}';                                                   *
 *                                                                             *
 * - S?lo mostrar certificados de la FNMT:                                     *
 * var certFilter = '{ISSUER.DN={"OU = FNMT Clase 2 CA,O= FNMT,C = ES"}}';     *
 *                                                                             *
 * - Mostrar todos los certificados menos el de validaci?n:                    *
 * var certFilter = '{SUBJECT.DN#NOT_MATCHES#{".*(AUTENTICACI?N).*"}}}'        *
 ******************************************************************************/
var certFilter; // Valor por defecto

/*******************************************************************************
 * Directorio donde se instalará las librerías del cliente                     *
 * Por defecto: USER_HOME/afirma.5/								 *
 ******************************************************************************/
var installDirectory = "afirma.5";

/*******************************************************************************
 * Acción establecida a realizar cuando durante la instalacion se detecten	 *
 * versiones antiguas del cliente (v2.4 y anteriores).				 *
 * Opciones disponibles:									 *
 * 	- 1: Preguntar al usuario.								 *
 *	- 2: No eliminar.										 *
 *	- 3: Eliminar sin preguntar.								 *
 * Por defecto: 1 (Preguntar al usuario).							 *
 ******************************************************************************/
var oldVersionsAction = 1;

/*******************************************************************************
 * Mostrar los certificados caducados en la listas de seleccion de		 *
 * certificados.						 					 *
 * Por defecto: 'true'.										 *
 ******************************************************************************/
var showExpiratedCertificates = 'true';

/*******************************************************************************
 * Construccion del cliente que se instalara cuando no se indique			 *
 * explicitamente.										 *
 * Los valores aceptados son:									 *
 *   - 'LITE':     Incluye los formatos de firma CMS y CADES.			 *
 *   - 'MEDIA':    Incluye los formatos de firma de la LITE + XMLDSIG y XADES. *
 *   - 'COMPLETA': Incluye los formatos de firma de la MEDIA + PDF.		 *
 * Por defecto: 'LITE'.										 *
 ******************************************************************************/
var defaultBuild;

