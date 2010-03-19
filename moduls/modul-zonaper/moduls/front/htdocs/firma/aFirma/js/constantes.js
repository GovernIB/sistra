/*******************************************************************************
 * Ruta al los instalables.                                                    *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var baseDownloadURL="/zonaperfront/firma/aFirma";

/*******************************************************************************
 * Ruta al instalador.                                                         *
 * Si no se establece, supone que est?n en el mismo directorio(que el HTML).   *
 ******************************************************************************/
var base="/zonaperfront/firma/aFirma"; 

/*******************************************************************************
 * Algoritmo de firma. Puede ser 'sha1WithRsaEncryption' o                     *
 * 'md5WithRsaEncryption'. Se estable al llamar a configuraFirma en firma.js   *
 ******************************************************************************/
var signatureAlgorithm = 'sha1WithRsaEncryption'; // Valor por defecto

/*******************************************************************************
 * Formato de firma. Puede ser 'CMS', 'XADES', 'XMLDSIGN' o 'NONE'.            *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var signatureFormat = 'CMS'; // Valor por defecto

/*******************************************************************************
 * Mostrar los errores al usuario. Puede ser 'true' o 'false'.                 *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var showErrors = 'true'; // Valor por defecto

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
 * Directorio donde se instalar? las librer?as del cliente                     *
 * Por defecto: USER_HOME/.clienteFirmaArrobaFirma5/                           *
 ******************************************************************************/
var installDirectory;

/*******************************************************************************
 * En caso de que existan varias distribuciones del cliente firmados por       *
 * distintos organismos, se deber? especificar un directorio de distribuci?n   *
 * para que cada aplicaci?n acceda a la versi?n y cliente firmado adecuado.    *
 * Esto facilita la instalaci?n multiplataforma pero evitando sobreescribir    *
 * distribuciones distintas.                                                   *
 * El directorio quedar? de la siguiente manera:                               *
 * INSTALL_DIRECTORY/{$distinctDistroDir}/							*
 * Por defecto: no definido			                                               *
 *                                                                             *
  ******************************************************************************/
var distinctDistroDir="Desarrollo2d4d1";

/*******************************************************************************
 * Mostrar los errores al usuario. Puede ser 'true' o 'false'.                 *
 * Se estable al llamar a configuraFirma en firma.js                           *
 ******************************************************************************/
var showErrors = 'true'; // Valor por defecto