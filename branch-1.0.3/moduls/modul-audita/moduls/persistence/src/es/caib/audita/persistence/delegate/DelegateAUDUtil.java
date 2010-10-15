package es.caib.audita.persistence.delegate;

public class DelegateAUDUtil
{	
	public static AuditaDelegate getAuditaDelegate()
	{
		return ( AuditaDelegate ) DelegateFactory.getDelegate( AuditaDelegate.class );
	}	
}
