package es.caib.zonaper.persistence.delegate;

public final class PadBackOfficeUtil
{
	private PadBackOfficeUtil()
	{
	}
	
	public static PadBackOfficeDelegate getBackofficeExpedienteDelegate()
	{
		return ( PadBackOfficeDelegate ) DelegateFactory.getDelegate( PadBackOfficeDelegate.class );
	}
}
