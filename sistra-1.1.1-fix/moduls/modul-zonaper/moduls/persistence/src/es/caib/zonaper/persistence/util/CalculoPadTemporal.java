package es.caib.zonaper.persistence.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.zonaper.modelInterfaz.PersonaPAD;

import es.caib.util.NifCif;

/**
 * 
 * Calcula usuario Seycon a partir del NIF de manera temporal
 * sobre tablas temporales hasta que este integrada la PAD
 * 
 */
public class CalculoPadTemporal {
	
	private static String datasource = "java:/jdbc/PadTemporalDS";
	private static HashMap cachingNif = new HashMap();
	private static HashMap cachingUsu = new HashMap();
	
	protected static Log log = LogFactory.getLog(CalculoPadTemporal.class);
	
	public static PersonaPAD calcularUsuarioSeyconPorNif(String nif) throws Exception{
		Connection con = null;
		try{
			
			// Normalizamos nif
			nif = NifCif.normalizarDocumento(nif);
						
			if (cachingNif.containsKey(nif)) return (PersonaPAD) cachingNif.get(nif);
			
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(datasource);
			con = ds.getConnection();
			
			PreparedStatement ps = con.prepareStatement("SELECT TIN_SEYCON,TIN_NOM FROM PAD_TMPIND WHERE UPPER(TIN_NIF) = ?");
			ps.setString(1,nif);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next()) return null;
			
			PersonaPAD pers = new PersonaPAD();			
			pers.setUsuarioSeycon(rs.getString(1));
			pers.setNif(nif);
			pers.setNombre(rs.getString(2));
			
			cachingNif.put(nif,pers);
			
			return pers; 

		}catch(Exception ex){
			log.error("Error accediendo a PAD");
			throw new Exception("Error accediendo a PAD: " + ex.getMessage(),ex);
		}finally{
			if (con != null) {
				try{con.close();}catch(Exception exc){
					exc.printStackTrace();
				}
			}
		}
		
	}
	
	public static PersonaPAD calcularUsuarioSeyconPorUsuarioSeycon(String usuario)  throws Exception{
		Connection con = null;
		try{
						
			if (cachingUsu.containsKey(usuario)) return (PersonaPAD) cachingUsu.get(usuario);
			
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(datasource);
			con = ds.getConnection();
			
			PreparedStatement ps = con.prepareStatement("SELECT TIN_NIF,TIN_NOM FROM PAD_TMPIND WHERE TIN_SEYCON = ?");
			ps.setString(1,usuario);
			ResultSet rs = ps.executeQuery();
			
			if (!rs.next()) return null;
			
			PersonaPAD pers = new PersonaPAD();			
			pers.setUsuarioSeycon(usuario);
			pers.setNif(rs.getString(1));
			pers.setNombre(rs.getString(2));
			
			cachingUsu.put(usuario,pers);
			
			return pers; 

		}catch(Exception ex){
			log.error("Error accediendo a PAD");
			throw new Exception("Error accediendo a PAD: " + ex.getMessage(),ex);
		}finally{
			if (con != null) {
				try{con.close();}catch(Exception exc){exc.printStackTrace();}
			}
		}
		
	}
}
