package es.caib.mock.loginModule.util;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.pkcs.*;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cms.*;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Utilidad para acceder a datos de una firma PKCS7 / CMS
 */
public class DatosFirma {

		public final int PKCS7 = 1;
	    public final int XML = 2;
	    private X509Certificate aObjCertificado;
	    private byte aBytDatos[];
	    private byte aBytFirma[];
	    private int aIntOrigen;
	    private boolean aBolInicializado;
	    private byte aBytPKCS7[];
	    private Document aObjDoc;
	    private int aIntNumFirmas;
	    private CMSProcessableByteArray aCMSBytDatos;
	    private CMSSignedData aCMSDatosFirmados;

	
	 public DatosFirma(String pStrBase64) throws Exception{
         aBytPKCS7 = Utilidades.Base64toBytes(pStrBase64);
         DERObject lObjDER = Utilidades.toDER(aBytPKCS7);
         ContentInfo lObjPKCS7 = ContentInfo.getInstance(lObjDER);
         aCMSDatosFirmados = new CMSSignedData(aBytPKCS7);
         SignedData lObjSignedData = SignedData.getInstance(lObjPKCS7.getContent());
         ContentInfo lObjContent = lObjSignedData.getContentInfo();
         if(lObjContent.getContent() != null)
         {
             byte lBytDatosUnicode[] = ((ASN1OctetString)lObjContent.getContent()).getOctets();
             aBytDatos = lBytDatosUnicode;
         }
         ASN1Set lObjSet = lObjSignedData.getSignerInfos();
         aIntNumFirmas = lObjSet.size();
         if(aIntNumFirmas == 1)
         {
             Utilidades.insertarProveedorBouncy();
             aObjCertificado = obtenerCertificadoPKCS7(aBytPKCS7);
         }
         aBolInicializado = true;
         cargaCMS(aBytPKCS7);         
	 }
	 
	 private void cargaCMS(byte pObjCad[]) throws Exception{
	      DERObject lObjDER = Utilidades.toDER(pObjCad);
	      ContentInfo lObjPKCS7 = ContentInfo.getInstance(lObjDER);
	      SignedData lObjSignedData = SignedData.getInstance(lObjPKCS7.getContent());
	      ContentInfo lObjContent = lObjSignedData.getContentInfo();
	      if(lObjContent.getContent() != null)
	      {
	          byte lBytDatosUnicode[] = ((ASN1OctetString)lObjContent.getContent()).getOctets();
	          aCMSBytDatos = new CMSProcessableByteArray(Utilidades.fromUnicode(lBytDatosUnicode));
	          aCMSDatosFirmados = new CMSSignedData(aCMSBytDatos, pObjCad);
	      } else
	      {
	          aCMSBytDatos = null;
	          aCMSDatosFirmados = new CMSSignedData(pObjCad);
	      }
	  }

	 private X509Certificate obtenerCertificadoPKCS7(byte pBytPKCS7[]) throws Exception{
	     CMSSignedData s = new CMSSignedData(pBytPKCS7);
	     CertStore certs = s.getCertificatesAndCRLs("Collection", "BC");
	     SignerInformationStore signers = s.getSignerInfos();
	     Collection c = signers.getSigners();
	     Iterator it = c.iterator();
	     if(it.hasNext())
	     {
	         SignerInformation signer = (SignerInformation)it.next();
	         Collection certCollection = certs.getCertificates(signer.getSID());
	         Iterator certIt = certCollection.iterator();
	         X509Certificate cert = (X509Certificate)certIt.next();
	         return cert;         
	     }
	     return null;
	 }

	public X509Certificate getCertificado() {
		return aObjCertificado;
	}


	
}
