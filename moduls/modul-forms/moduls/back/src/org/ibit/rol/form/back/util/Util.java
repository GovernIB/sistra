package org.ibit.rol.form.back.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.Pantalla;

public class Util {

	
	private static String version = null;	
	
	/**
	 * Detecta si hay campos con el mismo xpath y devuelve una lista de strings con el formato xpath = nombres de campos con ese xpath 
	 * @param formulario
	 * @return
	 */
	public static List controlXpathDuplicados(Formulario formulario){
		Map xpaths = new HashMap();
		for (Iterator it = formulario.getPantallas().iterator();it.hasNext();){
	    	Pantalla p = (Pantalla) it.next();
	    	for (Iterator it2 = p.getCampos().iterator();it2.hasNext();){
	        	Campo c = (Campo) it2.next();
	        	if (StringUtils.isNotEmpty(c.getEtiquetaPDF())){
	        		if (!xpaths.containsKey(c.getEtiquetaPDF())){     
	        			xpaths.put(c.getEtiquetaPDF(),new ArrayList());                			
	        		}
	        		((List) xpaths.get(c.getEtiquetaPDF())).add(c);
	        	}
	        }
	    }
		
		List duplicados = new ArrayList();
		for (Iterator iterator = xpaths.keySet().iterator();iterator.hasNext();){
			String xpath = (String) iterator.next();
			List listaCampos = (List) xpaths.get(xpath);
			if (listaCampos.size() > 1) {
				String descCampos = xpath + " = ";
				for (Iterator iterator2 = listaCampos.iterator();iterator2.hasNext();){
					Campo campo = (Campo) iterator2.next();
					descCampos+= " " + campo.getPantalla().getNombre() + "." + campo.getNombreLogico();
				}
				duplicados.add(descCampos);
			}
		}
		
		return duplicados;
	}
	
	public static String getUrl(HttpServletRequest request)
    {
        String uri = request.getRequestURI();
        if(request.getQueryString() != null)
        {
            if(!uri.endsWith("?"))
                uri = uri + "?";
            uri = uri + request.getQueryString();
        }
        //uri = uri.substring(request.getContextPath().length(), uri.length());
        uri = uri.substring(0, uri.length());
        return uri;
    }
	
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
}
