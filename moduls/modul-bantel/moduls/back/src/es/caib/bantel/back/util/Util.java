package es.caib.bantel.back.util;

import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.caib.bantel.model.Procedimiento;
import es.caib.bantel.model.TraProcedimiento;

public class Util {
	private static String version = null;	
	
	public static boolean getOperacionPermitida(HttpServletRequest request){
		String idOp = request.getParameter("idOperacion");
        HttpSession sesion = request.getSession(true);
        String idOpSesion = (String)sesion.getAttribute("idOperacion");
        if(idOpSesion == null){
        	idOpSesion = idOp;
        }
       	if(idOp.equals(idOpSesion)){
       		request.setAttribute("idOperacion",idOp);
       		return true;
       	}else{
       		return false;
       	}
	}
	
	public static String getIdOperacion(HttpServletRequest request){
		String idOperacion;
		HttpSession sesion = request.getSession(true);
		if(request.getAttribute("idOperacion") != null){
			idOperacion =(String) request.getAttribute("idOperacion");	
		}else{
			Date date = new Date();
			String dateString = date.getTime()+"";
			sesion.setAttribute("idOperacion",dateString);
			idOperacion = dateString;	
		}
		return idOperacion;
	}
	
	/**
	 * Obtiene version (en web.xml)
	 */
	public static String getVersion(){
		if (version == null) {
			try{
				InitialContext ic = new InitialContext();
				version = (String) ic.lookup("java:comp/env/release.cvs.tag");
			}catch(Exception ex){
				version = null;
			}		
		}
		return version;
	}
	
	public static String getDescripcionProcedimientoCombo(Procedimiento procedimiento, String lang) {		
		String id = procedimiento.getIdentificador(); 
		String desc = ((TraProcedimiento) procedimiento.getTraduccion(lang)).getDescripcion();
		
		if (desc.length() > 60) {
			desc = desc.substring(0,60) + "...";
		}
		
		return id + "-" + desc;				
	}
	
}
