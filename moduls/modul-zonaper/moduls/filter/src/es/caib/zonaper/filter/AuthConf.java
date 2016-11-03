package es.caib.zonaper.filter;

import org.apache.commons.lang.StringUtils;

public class AuthConf
{
	private String authURL;
	private String authErrorURL;
	private String[] excluded;

	public String getAuthURL()
	{
		return authURL;
	}

	public void setAuthURL(String authURL)
	{
		this.authURL = authURL;
	}
	
	public void setExcluded( String urls )
	{
		if ( !StringUtils.isEmpty( urls ) )
		excluded = urls.split( "," );
	}
	
	public boolean isExcluded( String url )
	{
		if ( excluded == null )
		{
			return false;
		}
		for ( int i = 0; i < excluded.length; i++ ) 
		{
			if ( url.matches( excluded[i] ) )
				return true;
		}
		return false;
	}

	public String getAuthErrorURL() {
		return authErrorURL;
	}

	public void setAuthErrorURL(String authError) {
		this.authErrorURL = authError;
	}

}
