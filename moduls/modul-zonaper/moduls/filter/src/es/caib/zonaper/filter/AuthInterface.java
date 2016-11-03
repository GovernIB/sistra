package es.caib.zonaper.filter;

import java.security.Principal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface AuthInterface
{
	public Principal getAuthObject(AuthConf conf, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws AuthException; 
	public void doAuthAction(AuthConf conf, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws AuthException;
	public void doAuthErrorAction(int codigoError,AuthConf conf, ServletContext context, HttpServletRequest request, HttpServletResponse response) throws AuthException;
}
