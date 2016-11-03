package es.indra.util.graficos.util;

	import java.io.*;
	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.StringTokenizer;

	public final class StackTraceElement extends Object
	{
		private String	_clase = null;
		private String	_metodo = null;
		private String	_linea = null;
		private String	_archivo = null;
		
		
		private static String stackTraceWithThrowable(Throwable aThrowable)
		{
			if ( aThrowable != null )
			{
				Writer		aStringWriter = new StringWriter();
				PrintWriter	aWriter = new PrintWriter( aStringWriter, true );
			
				aThrowable.printStackTrace( aWriter );
			
				return aStringWriter.toString();
			}
			
			return null;
		}
		
		public static StackTraceElement[] getStackTrace(Throwable aThrowable)
		{
			StackTraceElement[]	someElements = null;
			
			if ( aThrowable != null )
			{
				String		aClassName = StackTraceElement.class.getName();
				Collection	aCollection = new ArrayList();
				String		aStackTrace = StackTraceElement.stackTraceWithThrowable( aThrowable );
				StringTokenizer	aTokenizer = new StringTokenizer( aStackTrace, System.getProperty( "line.separator" ) );
				
				aTokenizer.nextToken();
			
				while ( aTokenizer.hasMoreTokens() == true )
				{
					String			aStack = aTokenizer.nextToken();
					StackTraceElement	anElement = new StackTraceElement( aStack.trim() );
					
					if ( aClassName.equals( anElement.getClase() ) == false )
					{
						aCollection.add( anElement );
					}
				}
				
				if ( aCollection.isEmpty() == false )
				{
					someElements = ( StackTraceElement[] ) aCollection.toArray( new StackTraceElement[ aCollection.size() ] );
				}
			}
			
			return someElements;
		}
		
		public static StackTraceElement[] getStackTrace()
		{
			StackTraceElement[]	someElements = null;
			
			try
			{
				throw new Throwable();
			}
			catch(Throwable aThrowable)
			{
				someElements = StackTraceElement.getStackTrace( aThrowable );
			}
			
			return someElements;
		}
		
		private StackTraceElement()
		{
			super();
		}
	
		private StackTraceElement(String aValue)
		{
			this();
			
			this.inicializar( aValue );
		}
		
		public String toString()
		{
			String ls_res = "<b>Clase:</b> " +getClase() +" <b>Método:</b> " + getMetodo() + 
							" <b>Archivo Origen:</b> " + getArchivo() + " ";
	        if (getLinea() != null ) ls_res += "<b>Línea:</b> " + this.getLinea()+" ";
	        ls_res += "\n<b>Mensaje:</b> ";
			return ls_res;
		}
		
		private void inicializar(String aValue)
		{
			if ( aValue != null )
			{
				StringBuffer	aBuffer = new StringBuffer();
				int		index = 0;
				
				index = aValue.indexOf( '(' );
				// Obtener archivo y línea
				int endIndex = -1;
				int beginIndex = index;
				endIndex = aValue.indexOf(')');
				String lArchivo = null;
				String lLinea = null;
				if (endIndex > -1)
				{
					lArchivo = aValue.substring(index + 1, endIndex);
					beginIndex = lArchivo.lastIndexOf(':');
					setArchivo(" Archivo fuente desconocido. ");
					setLinea(lLinea);
					
					if (beginIndex > -1)
					{
						lLinea = lArchivo.substring(beginIndex + 1);
						lArchivo = lArchivo.substring(0, beginIndex);
						setLinea(lLinea);
						setArchivo(lArchivo);
					}
				}
				
				while( ( index-- ) >= 0 )
				{
					char	aChar = aValue.charAt( index );
					
					if ( ( aChar == '.' ) || ( aChar == '<' ) || ( aChar == '>' ) ||
						( Character.isJavaIdentifierPart( aChar ) == true ) )
					{
						aBuffer.insert( 0, aChar );
					}
					else
					{
						break;
					}
				}
				
				if ( aBuffer.length() > 2 )
				{
					String	aString = aBuffer.toString();				
					int	anIndex = aString.lastIndexOf( '.' );
					
					if ( anIndex > 0 )
					{
						this.setClase( aString.substring( 0, anIndex ) );
						this.setMetodo( aString.substring( anIndex + 1 ) );
					}
				}
			}
		}
		
		public String getClase()
		{
			return _clase;
		}
		
		private void setClase(String aValue)
		{
			_clase = aValue;
		}
		
		public String getMetodo()
		{
			return _metodo;
		}
		
		private void setMetodo(String aValue)
		{
			_metodo = aValue;
		}
		
		
				
		/**
		 * @return
		 */
		public String getArchivo()
		{
			return _archivo;
		}

		/**
		 * @return
		 */
		public String getLinea()
		{
			return _linea;
		}

		/**
		 * @param a_string
		 */
		public void setArchivo(String a_string)
		{
			_archivo = a_string;
		}

		/**
		 * @param a_string
		 */
		public void setLinea(String a_string)
		{
			_linea = a_string;
		}

	}

