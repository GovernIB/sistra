package es.indra.util.graficos.xml;

public class XmlError {

	public static String getCabXML ()
	{
		return "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n<ERROR>\n";
	}
	
	public static String getpieXML ()
	{
		return "</ERROR>";
	}
	
	public static String genDatos (String numError, String descError)
	{
		
		return getCabXML()+
		"<CODIGO>"+numError+"</CODIGO>\n"+
		"<DESCRIPCION>"+descError+"</DESCRIPCION>\n"+
		getpieXML();
	}

}

