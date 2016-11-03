package es.caib.sistra.persistence.util;

public class AuditoriaUtil
{
	public static String PANTALLA_PATTERN 	= "FOR[modelo]VER[version]PANT[nombreLogico]";
	public static String CAMPO_PATTERN		= PANTALLA_PATTERN + "CAMPO[tipo][nombreLogicoCampo]";
	public static String PATRON_PATTERN 	= CAMPO_PATTERN + "PATRON[nombrePatron]";
	public static String PROPSALIDA_PATTERN =  "FOR[modelo]VER[version]PUNTOSALIDA[nombrePunto]PROPIEDAD[nombrePropiedad]"; 
	
	public static String TRAMITEVERSION_PATTERN =  "TRA[identificador]VER[version]";
	public static String TRAMITEVERJUST_PATTERN =  TRAMITEVERSION_PATTERN + "JUS[tipo][orden]";
	public static String TRAMITEVERDOC_PATTERN =   TRAMITEVERSION_PATTERN + "DOC[idDoc]DNIV[nivelAutenticacion]";
	
	public static String TRAMITENIVEL_PATTERN = TRAMITEVERSION_PATTERN + "TNV[nivelAutenticacion]";
	public static String TRAMITENIVJUST_PATTERN = TRAMITENIVEL_PATTERN + "JUS[tipo][orden]";
	
	public static String RE_ID = "[^\\-]*\\-";
	public static String RE_ID_END = "\\-[^\\-]*";
	
	public static String getIdPantalla ( String modelo, int version, String nombreLogico )
	{
		return getIdentificadorPantalla( PANTALLA_PATTERN, modelo, version, nombreLogico );
	}
	
	public static String getIdCampoPantalla ( String modelo, int version, String nombreLogico, String tipo, String nombrelogicoCampo  )
	{
		return  getIdentificadorCampo ( CAMPO_PATTERN, modelo, version, nombreLogico, tipo, nombrelogicoCampo );
	}
	
	public static String getIdPatronCampo( String modelo, int version, String nombreLogico, String tipo, String nombrelogicoCampo, String nombrePatron  )
	{
		return  getIdentificadorCampo ( PATRON_PATTERN, modelo, version, nombreLogico, tipo, nombrelogicoCampo ).replaceAll( "nombrePatron", nombrePatron );
	}
	
	public static String getIdPropSalida( String modelo, int version, String nombrePunto, String nombrePropiedad )
	{
		return PROPSALIDA_PATTERN.replaceAll( "modelo", modelo ).replaceAll( "version", "" + version ).replaceAll( "nombrePunto", nombrePunto ).replaceAll( "nombrePropiedad", nombrePropiedad );
	}
	
	public static String getIdTraVer( String id, int version )
	{
		return getIdentificadorTramite( TRAMITEVERSION_PATTERN, id,version ); 
	}
	
	public static String getIdTraVerJust( String idTr, int vTr, char tipo, int orden )
	{
		return getIdentificadorTramite( TRAMITEVERJUST_PATTERN, idTr,vTr ).replaceAll( "tipo", "" + tipo ).replaceAll( "orden", "" + orden );
	}
	
	public static String getIdDoc( String idTr, int vTr, String identificador, String nivelAutenticacion )
	{
		return getIdentificadorTramite( TRAMITEVERDOC_PATTERN, idTr, vTr).replaceAll( "idDoc", identificador ).replaceAll( "nivelAutenticacion", nivelAutenticacion );
	}
	
	public static String getIdTraNivel( String idTr, int vTr, String nivelAut )
	{
		return getIdentificadorTramiteNivel( TRAMITENIVEL_PATTERN, idTr,vTr, nivelAut );
	}
	
	public static String getIdTraNivelJust( String idTr, int vTr, String nivelAut, char tipo, int orden )
	{
		return getIdentificadorJustificante( getIdentificadorTramiteNivel( TRAMITENIVJUST_PATTERN, idTr,vTr, nivelAut ), tipo, orden );
	}
	
	private static String getIdentificadorTramite( String patron, String identificador, int version )
	{
		return patron.replaceAll( "identificador", identificador ).replaceAll( "version", "" + version );
	}
	
	private static String getIdentificadorJustificante( String patron, char tipo, int orden )
	{
		return patron.replaceAll( "tipo", "" + tipo ).replaceAll( "orden", "" + orden );
	}
	
	private static String getIdentificadorTramiteNivel( String patron, String identificador, int version, String nivelAut )
	{
		return getIdentificadorTramite( patron, identificador,version ).replaceAll( "nivelAutenticacion", nivelAut );
	}
	
	private static String getIdentificadorPantalla( String pattern, String modelo, int version, String nombreLogico )
	{
		return pattern.replaceAll( "modelo", modelo ).replaceAll( "version", "" + version ).replaceAll("nombreLogico", nombreLogico );
	}

	private static String getIdentificadorCampo( String pattern, String modelo, int version, String nombreLogico, String tipo, String nombreLogicoCampo )
	{
		return getIdentificadorPantalla( pattern, modelo, version, nombreLogico ).replaceAll( "tipo", tipo ).replaceAll( "nombreLogicoCampo", nombreLogicoCampo ) ;
	}
	
	public static String getDescriptionKey( String identificador )
	{
		return identificador.replaceAll( RE_ID, "" );
	}
	
	public static String getNombre( String identificador )
	{
		return identificador.replaceAll( RE_ID_END, "" );
	}
	
	public static void main ( String args[] )
	{
		String id = "FOR[modelo]VER[version]PANT[nombrelogico]-BLABLABLALBLALZLAL";
		System.out.println( AuditoriaUtil.getNombre( id ) ); 
	}
}
