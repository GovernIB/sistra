package es.caib.bantel.front.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.tiles.ComponentContext;
import org.apache.struts.util.MessageResources;

import es.caib.bantel.front.Constants;

public class MessageController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		if ( request.getAttribute( Constants.MESSAGE_KEY ) == null )
		{
			MessageResources resources = ((MessageResources) request.getAttribute(Globals.MESSAGES_KEY));
	    	request.setAttribute( Constants.MESSAGE_KEY,  resources.getMessage( getLocale( request ), "", null ) );			
		}
		
		String strAction 			= ( String ) request.getAttribute( Constants.MESSAGE_ACTION_KEY );
		Map mParams 				= ( Map ) request.getAttribute( Constants.MESSAGE_ACTION_PARAMS_KEY );
		String strActionLabelKey 	= ( String ) request.getAttribute( Constants.MESSAGE_ACTION_LABEL_KEY );
		
		if ( StringUtils.isEmpty ( strAction ) )
		{
			request.setAttribute( Constants.MESSAGE_ACTION_KEY, "/init" );
		}
		if ( mParams == null )
		{
			request.setAttribute( Constants.MESSAGE_ACTION_PARAMS_KEY, new HashMap() );
		}
		if ( strActionLabelKey == null )
		{
			request.setAttribute( Constants.MESSAGE_ACTION_LABEL_KEY, "mensaje.volver" );
		}
		
	}

}
