package es.caib.sistra.casClient.loginModule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;

public class CasPrincipal implements Principal, Serializable {

	public final static String PREFIX_SERIALIZED ="CAS@";
	
	private String name;
	private String apellidosNombre;
	private String nif;
	private char metodoAutenticacion;
	private List<String> roles;
	
	public CasPrincipal (String name, String apellidosNombre, String nif,char metodoAutenticacion,List<String> roles) 
	{
		this.name = name;
		this.apellidosNombre = apellidosNombre;
		this.nif = nif;
		this.metodoAutenticacion = metodoAutenticacion;
		this.roles = roles;
	}
	
	public String getName() {
		return name;
	}
	
	public String getApellidosNombre(){
		return apellidosNombre;
	}

	public String getNif() {
		return nif;
	}
	
	public char getMetodoAutenticacion() {
		return metodoAutenticacion;
	}

	
	public List<String> getRoles() {
		return roles;
	}
	
	public static String serializar(CasPrincipal principal) throws Exception{
		ByteArrayOutputStream o = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(o);
        oout.writeObject(principal);
        oout.close();
        o.close();
        byte data[] = o.toByteArray();
        StringBuffer buffer = new StringBuffer(data.length * 2);
        for(int i = 0; i < data.length; i++)
        {
            int value = data[i];
            if(value < 0)
                value += 256;
            buffer.append(toHexChar(value / 16));
            buffer.append(toHexChar(value % 16));
        }
        return PREFIX_SERIALIZED + buffer.toString();
	}
	
	public static CasPrincipal deserializar(String principalSerialized) throws Exception{
		
		if (!principalSerialized.startsWith(PREFIX_SERIALIZED)){
			throw new Exception("Error al deserializar: no es un objeto CimPrincipal");
		}
		
		principalSerialized = principalSerialized.substring(PREFIX_SERIALIZED.length());
		
		byte b[] = new byte[principalSerialized.length() / 2];
        int i = 0;
        int j = 0;
        while(i < principalSerialized.length()) 
        {
            int c = getHexValue(principalSerialized.charAt(i++)) * 16;
            c += getHexValue(principalSerialized.charAt(i++));
            b[j++] = (byte)c;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        ObjectInputStream objIn = new ObjectInputStream(in);
        CasPrincipal cp;
        cp = (CasPrincipal)objIn.readObject();
        objIn.close();
        in.close();
        return cp;
	}
	
	private static char toHexChar(int i)
    {
        if(i < 10)
            return (char)(48 + i);
        else
            return (char)(97 + (i - 10));
    }
	
	private static int getHexValue(char c)
	{
	    if(c >= '0' && c <= '9')
	        return c - 48;
	    if(c >= 'A' && c <= 'F')
	        return (c - 65) + 10;
	    if(c >= 'a' && c <= 'f')
	        return (c - 97) + 10;
	    else
	        throw new RuntimeException("Invalid hex character " + c);
	}
	
}
