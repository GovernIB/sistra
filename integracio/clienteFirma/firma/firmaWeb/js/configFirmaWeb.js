/* Configuración firma */

// Indica si firma los PDS con formato distinto (FIRMAWEB_SIGN_TYPE_PDF).
// El plugin de firma debe adaptarse, ya que se enviara como prefijo a la firma el campo [FORMATO:PADES]
var FIRMAWEB_ENABLE_SIGN_PDF = true;


// Para CAIB: FIRMAWEB_SIGN_TYPE=SIME / FIRMAWEB_SIGN_MODE = 1
var FIRMAWEB_SIGN_TYPE = "CAdES";  // PAdES / XAdES / CAdES / SMIME   
var FIRMAWEB_SIGN_MODE = 1; // 0 - Attached / 1 - Detached (Para Xades y Cades)
var FIRMAWEB_SIGN_ALGORITHM = "SHA-1";

// Valores para firma de PDF (deben ser estos fijos en caso de habilitar la firma PDF)
var FIRMAWEB_SIGN_TYPE_PDF = "PAdES"; // Debe ser PADES
var FIRMAWEB_SIGN_MODE_PDF = 0; // Al ser PADES debe ser Attached
var FIRMAWEB_SIGN_ALGORITHM_PDF = "SHA-1";

// Contexto de acceso a firmaweb
var FIRMAWEB_CONTEXTO = "";
var FIRMAWEB_SERVLET = "/firmaweb/firmaWebServlet";


