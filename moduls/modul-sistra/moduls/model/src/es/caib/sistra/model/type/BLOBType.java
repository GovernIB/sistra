package es.caib.sistra.model.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.UserType;

public class BLOBType implements UserType {
	
	private String CHAR_SET = "UTF-8";

	public int[] sqlTypes() {
		return new int[] {Types.BLOB};
	}

	public Class returnedClass() {
		return String.class;
	}

	public boolean equals(Object arg0, Object arg1) throws HibernateException 
	{
		if(arg0 == null || arg1 == null)
		{
			return false;
		}
		if(!(arg0 instanceof String) || !(arg1 instanceof String)) 
		{
			return false;
		}
		return ((String)arg0).equals((String) arg1);
	}

	public Object nullSafeGet(ResultSet arg0, String[] arg1, Object arg2)
			throws HibernateException, SQLException 
	{		
		ByteArrayOutputStream baos = new ByteArrayOutputStream( 8192 );
		InputStream is = null;
		try 
		{
			is = arg0.getBinaryStream(arg1[0]);
			
			if (arg0.wasNull()) return "";
			
			byte[] buf = new byte[1024];
			int read = -1;
			
			while((read = is.read(buf)) > 0) 
			{				
				baos.write( buf, 0, read );			
			}			
			String ret = baos.toString( CHAR_SET );					
			return ret;
		}
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
			throw new HibernateException("Unable to read from resultset",ioe);
		}finally{
			if (is!=null){try{is.close();}catch(Exception ex){ex.printStackTrace();}}
			if (baos!=null){try{baos.close();}catch(Exception ex){ex.printStackTrace();}}			
		}				
	}

	public void nullSafeSet(PreparedStatement pst, Object data, int index)
			throws HibernateException, SQLException 
	{
		try
		{			
			String in = (String)data;
			if (in == null) in = "";
			
									
			byte[] buf = in.getBytes( CHAR_SET );
			int len = buf.length;
				
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			pst.setBinaryStream(index,bais,len);
				
		}
		catch( UnsupportedEncodingException uee )
		{
			throw new HibernateException( uee );
		}
		
		/*
		try
		{			
			String in = (String)data;
			if (in == null) in = "";
			byte[] buf = in.getBytes( CHAR_SET );			
			
			Connection conn =	pst.getConnection().getMetaData().getConnection();
			OutputStream tempBlobOutputStream = null;
			BLOB tempBlob = BLOB.createTemporary(conn, true,BLOB.DURATION_SESSION);
            try {
                tempBlob.open(BLOB.MODE_READWRITE);
                tempBlobOutputStream = tempBlob.getBinaryOutputStream();
                tempBlobOutputStream.write((byte[])buf);
                tempBlobOutputStream.flush();
            } finally {
                if (tempBlobOutputStream != null)
                    tempBlobOutputStream.close();
                tempBlobOutputStream.close();
            }
            pst.setBlob(index, (Blob) tempBlob);
		}
		catch( Exception uee )
		{
			throw new HibernateException( uee );
		}
		*/
	}

	public Object deepCopy(Object arg0) throws HibernateException 
	{
		try
		{
			String ret = null;
			
			if (arg0 == null) return null;
						
			String in = (String)arg0;
			byte[] buf = in.getBytes( CHAR_SET );
			ret = new String(buf, 0, buf.length, CHAR_SET );
			return ret;
		}
		catch( UnsupportedEncodingException uee )
		{
			throw new HibernateException( uee );
		}
	}

	public boolean isMutable() {		
		return false;
	}

}
