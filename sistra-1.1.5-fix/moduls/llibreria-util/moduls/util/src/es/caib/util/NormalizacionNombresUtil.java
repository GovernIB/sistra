package es.caib.util;

import java.util.Locale;

public class NormalizacionNombresUtil
{
	private static String [][] REGEXP =
		{
			{ "[ÀàÁáäÄâÂ]", "a" },
			{ "[éÉèÈëËêÊ]", "e" },
			{ "[íÍìÌïÏîÎ]", "i" },
			{ "[öÖóÓòÒôÔ]", "o" },
			{ "[üÜûÛúÚùÙ]", "u" }
		};
	
	public static String capitalize( String cad, Locale locale )
	{
		if ( cad == null || cad != null && cad.length() == 0 )
			return cad;
		cad = cad.toLowerCase( locale ).trim();
		StringBuffer sb = new StringBuffer( cad.substring( 0, 1 ).toUpperCase() );
		sb.append( ( cad.length() > 1 ? cad.substring( 1 ) : "" ) );
		return sb.toString();		
	}
	
	
	
	public static String capitalizeAllTokens( String cad, Locale locale )
	{
		cad = cad.replaceAll( "\\s+", " " );
		String [] particulas = cad.split( "\\s" );
		StringBuffer sb = new StringBuffer( capitalize( particulas[0], locale ));
		for ( int i = 1; i < particulas.length; i++ )
		{
			sb.append( " " ).append( capitalize( particulas[i], locale ) );
		}
		return sb.toString();
	}
	
	public static boolean isEquivalentName( String name1, String name2 )
	{
		if ( name1 == null && name2 != name1 )
			return false;
		if ( name2 == null && name1 != name2 )
			return false;
		if ( name1 == null && name2 == name1 )
			return true;
			
		String cad1 = normalize( name1 );
		String cad2 = normalize( name2 );
		return cad1.equalsIgnoreCase( cad2 );
	}
	
	public static String normalize( String cad )
	{
		for ( int i = 0; i < REGEXP.length; i++ )
		{
			cad = cad.replaceAll( REGEXP[i][0], REGEXP[i][1] );
		}
		return cad;
	}
	
	public static boolean existeParticulaEnElNombre( String particula, String fullName )
	{
		String[] particulas = fullName.split( "\\s" );
		for ( int i = 0; i < particulas.length; i++ )
		{			
			if ( isEquivalentName( particulas[i], particula ) )
			{
				return true;
			}
		}
		return false;
	}
	
}
