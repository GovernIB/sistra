/**
 * Formato de la firma que puede tener los siguientes valores:
 *  CAdES
 *  CAdEStri
 *  Adobe PDF
 *  PAdEStri
 *  XAdES
 *  XAdEStri
 *  FacturaE
 *  AUTO 
*/
sistra_ClienteaFirma_SignatureFormat="CAdES";
/**
 * Algoritmo utilizado que puede tener los siguientes valores:
 * 
 * SHA1withRSA
 * SHA512withRSA
 */
sistra_ClienteaFirma_SignatureAlgorithm="SHA1withRSA";
/**
 * Parámetros extra. Por ejemplo, se puede enviar el server externo para firmar:
 * 
 * serverUrl=http://localhost:8080/afirma-server-triphase-signer/SignatureService 
 */
sistra_ClienteaFirma_SignatureParams="";
