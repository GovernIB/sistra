package es.caib.sistra.plugins.firma.impl.mock;

import java.security.cert.X509Certificate;
import java.util.Vector;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Name;

public class DatosCertificado {

		private boolean personaFisica;
		private boolean personaJuridica;
		private String nif;
		private String fullName;
		private String nifResponsable;
		private String surName;
		private String givenName;
		private DERObjectIdentifier NIFOID = new DERObjectIdentifier("1.3.6.1.4.1.18838.1.1");
	    private DERObjectIdentifier productorOID = new DERObjectIdentifier("1.3.6.1.4.1.22896.1.1");
	    private DERObjectIdentifier proxyOID = new DERObjectIdentifier("1.3.6.1.4.1.22896.1.2");
		private String productor;
		private boolean proxy;
		private String policy;


		public DatosCertificado(X509Certificate cert) throws Exception{
		        byte b[] = cert.getEncoded();
		        ASN1InputStream asn1is = new ASN1InputStream(b);
		        org.bouncycastle.asn1.DERObject obj = asn1is.readObject();
		        X509CertificateStructure certificate = new X509CertificateStructure((ASN1Sequence)obj);
		        X509Name name = certificate.getSubject();
		        personaFisica = false;
		        personaJuridica = false;
		        Vector v = name.getOIDs();
		        Vector value = name.getValues();
		        for(int i = 0; i < v.size(); i++)
		        {
		            if(v.get(i).equals(X509Name.CN))
		                processName(value.get(i).toString());
		            if(v.get(i).equals(X509Name.SURNAME))
		                surName = value.get(i).toString();
		            if(v.get(i).equals(X509Name.GIVENNAME))
		                givenName = value.get(i).toString();
		            if(v.get(i).equals(X509Name.CN))
		                processName(value.get(i).toString());
		            if(v.get(i).equals(X509Name.SN))
		            {
		                nif = value.get(i).toString();
		                if(!personaJuridica)
		                    personaFisica = true;
		            }
		            if(v.get(i).equals(NIFOID))
		            {
		                nifResponsable = v.get(i).toString();
		                personaFisica = false;
		                personaJuridica = true;
		            }
		        }

    }



		public void processName(String cn){
		        if(cn != null && cn.startsWith("NOMBRE "))
		        {
		            int i = cn.indexOf(" - ");
		            if(i > 0 && cn.substring(i).startsWith(" - NIF "))
		            {
		                nif = cn.substring(i + 7);
		                fullName = cn.substring(7, i);
		                personaFisica = true;
		                personaJuridica = false;
		            }
		        } else
		        if(cn != null && cn.startsWith("ENTIDAD "))
		        {
		            personaFisica = false;
		            personaJuridica = true;
		            int i = cn.indexOf(" - ");
		            if(i > 0 && cn.substring(i).startsWith(" - CIF "))
		            {
		                int j = cn.indexOf(" - ", i + 7);
		                int k = cn.indexOf(" - NIF ", i + 7);
		                if(j > 0 && k > 0)
		                {
		                    nif = cn.substring(i + 8, j);
		                    fullName = cn.substring(7, i);
		                    nifResponsable = cn.substring(k + 7);
		                }
		            }
		        } else
		        {
		            fullName = cn;
		        }
		    }



		public String getFullName() {
			return fullName;
		}


		public String getNif() {
			return nif;
		}


	}
