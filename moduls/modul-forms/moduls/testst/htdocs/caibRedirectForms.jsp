<%@ page import="java.util.Vector,es.caib.xml.analiza.*,es.caib.xml.*,es.caib.xml.util.HashMapIterable"%>
<%
        String xmlConfig = request.getParameter("xmlConfig");
        String ls_nodos = request.getParameter("nodos");
        String ls_valores = request.getParameter("valores");
        String xmlData = "";
        try{
            String[] nodos = split(ls_nodos,"#");
            String[] valores = split(ls_valores,"#");
            Generador generador = new Generador ();
            generador.setCaracteresIdentacion(0);
            HashMapIterable hojas = new HashMapIterable();
            for(int i=0; i<nodos.length; i++)
            {
                Nodo nodo = new Nodo("nodo" + (i+1), "nodo" + (i+1));		
				nodo.setXpath(nodos[i]);
				nodo.setValor(valores[i]);
				System.out.println("nodo: " + nodos[i] + " valor " + valores[i]);
	    		hojas.put(nodo.getXpath(),nodo);
            }
            xmlData = generador.generarXML(hojas);
            /* quitamos saltos de linea */
            xmlData = replace(xmlData,"\n","");
            xmlData = replace(xmlData,"'","\\'");
            System.out.println("generado: " + xmlData);
//    		generador.setEncoding(ConstantesXML.ENCODING);
        }
        catch(Throwable t)
        {
        	System.out.println("Error: "+ t.getCause() );
        }
        
//        response.sendRedirect(redirectURL);
%>
<%!
public String[] split(String as_cadena, String as_separador)
throws Throwable
{
	if (as_cadena == null) return null;
	Vector lv_splitted = new Vector();
	int li_beginIndex = 0;
	int li_endIndex = 0;
	int li_auxIndex = 0; 
	String ls_aux = null;
	boolean lb_inicial = true;
	while (li_beginIndex > -1)
	{
		if (!lb_inicial)
		{
			li_auxIndex = li_beginIndex + as_separador.length();
		}
		lb_inicial = false;
		if (li_auxIndex > as_cadena.length() - 1)
		{
			break;
		}
		li_endIndex = as_cadena.indexOf(as_separador, li_auxIndex);
		if (li_endIndex == -1)
		{
			ls_aux = as_cadena.substring(li_auxIndex);
			lv_splitted.addElement(ls_aux);
			break;
		} 
		ls_aux = as_cadena.substring(li_auxIndex, li_endIndex);
		lv_splitted.addElement(ls_aux);
		li_beginIndex = li_endIndex;
		
	}

	if (lv_splitted.size() > 0)
	{
		String[] ls_splitted = new String[lv_splitted.size()];
		for (int i=0; i<lv_splitted.size();i++)
		{
			ls_splitted[i] = (String) lv_splitted.elementAt(i);
		}
		return ls_splitted;
	}
	 
	return null;
}
%>
<%!
public String replace(String s, String one, String another)
throws Throwable 
{
// En el String 's' sustituye 'one' por 'another'
  if (s == null) 
  {
  	if (one == null && another != null)
  	{
  		return another; 
  	}
  	return null;
  }
   
   
 	
	if (s.length() == 0) 
	{
		if (one != null && one.length() == 0)
		{
			return another; 
		}
		return "";
	} 
	
	if (one == null || one.length()==0)
	{
		return s;
	}

	
	String res = "";
  int i = s.indexOf(one,0);
  int lastpos = 0;
  while (i != -1) {
    res += s.substring(lastpos,i) + another;
    lastpos = i + one.length();
    i = s.indexOf(one,lastpos);
  }
  res += s.substring(lastpos);  // the rest
  return res;
}
%>
<head>
</head>
<body>
<form action="<%=request.getContextPath()%>/iniciForm" method="POST" target="Nueva">
        <input type="hidden" id=xmlData" name="xmlData"/>
        <input type="hidden" id="xmlConfig" name="xmlConfig"/>
</form>
</body>
<script>
document.forms[0].xmlConfig.value = '<%= xmlConfig %>';
document.forms[0].xmlData.value = '<%= xmlData %>';
document.forms[0].submit();
</script>
