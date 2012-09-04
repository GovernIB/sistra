package es.caib.mock.loginModule.util;

import java.io.*;
import java.security.Provider;
import java.security.Security;
import java.util.Properties;
import java.util.Vector;
import org.bouncycastle.asn1.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Utilidades {

	public static DERObject toDER(byte[] pBytDER) throws Exception{
		DERObject lObjRes;
        ByteArrayInputStream lObjIn = new ByteArrayInputStream(pBytDER);
        lObjRes = null;
        ASN1InputStream lObjDerOut = new ASN1InputStream(lObjIn);
        lObjRes = lObjDerOut.readObject();
        return lObjRes;        
	}

	public static byte[] fromUnicode(byte pBytUnicode[])
    {
        byte lBytNormal[] = new byte[pBytUnicode.length / 2];
        for(int i = 0; i < lBytNormal.length; i++)
            lBytNormal[i] = pBytUnicode[2 * i];

        return lBytNormal;
    }



	public static void insertarProveedorBouncy()
    {
        if(!existeProveedorCriptografico("BC"))
            Security.addProvider(new BouncyCastleProvider());
    }


  public static boolean existeProveedorCriptografico(String pStrIdentificadorProveedor)
    {
        boolean lBolExiste = false;
        Provider lObjProveedores[] = Security.getProviders();
        for(int i = 0; i < lObjProveedores.length; i++)
            if(lObjProveedores[i].getName().equalsIgnoreCase(pStrIdentificadorProveedor))
                lBolExiste = true;

        return lBolExiste;
    }

  public static byte[] Base64toBytes(String pStrBase64) throws Exception {
	  byte lBytDestino[];
	  BASE64Decoder lObjDecodificador = new BASE64Decoder();
	  lBytDestino = lObjDecodificador.decodeBuffer(pStrBase64);
	  return lBytDestino;  
  }

}
