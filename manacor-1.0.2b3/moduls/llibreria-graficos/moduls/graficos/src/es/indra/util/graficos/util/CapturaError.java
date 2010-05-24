package es.indra.util.graficos.util;

/**
 * @author genred
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CapturaError extends Throwable
{
	public StackTraceElement[] getTraza()
	{
		try
		{
			
			return StackTraceElement.getStackTrace(this);
		}
		catch (Throwable ex)
		{
			return null;
		}
	}
}
