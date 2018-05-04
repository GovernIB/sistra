<%@ page contentType="text/html; charset=ISO-8859-1"  import="javax.naming.*,java.util.*,java.sql.*"%>
<html>
<style type="text/css">
<!--
body { 
	font:normal 85% Arial, Helvetica, sans-serif; color:#494949;
	background-color:#c6d7e2; margin:0; padding:0; }
a { color:#0047b2; }
.estilo1 {
	margin-left: 200px;
}
li {
           margin-top: 10px;
       }
-->
</style>
<% 

	es.caib.sistra.persistence.delegate.ConfiguracionDelegate delegateF = es.caib.sistra.persistence.delegate.DelegateUtil.getConfiguracionDelegate();
	
	java.util.Properties configProperties =  delegateF.obtenerConfiguracion();
	
	String entorno = configProperties.getProperty("entorno");
	
%>	
<body>
<% if (entorno.equals("DESARROLLO")){ %>
<h2>Lista de Trámites</h2>
<p class="estilo1">
<ul>
<%

// Context Path
String contextPath = request.getContextPath().substring(0, request.getContextPath().indexOf("/tramites"));
// Buscamos un locale enviado por el navegador que nos coincida con los soportados.
Enumeration enumer = request.getLocales();
String lang = "";
while (enumer.hasMoreElements()) {
	Locale locale = (Locale) enumer.nextElement();
    lang = locale.getLanguage().trim();

    if (lang.equals("ca") || lang.equals("es")) {
      break;
    }
}

Connection con = null;
ResultSet rs = null;
Statement s = null;

try {
	String jndiDatasource = "java:/es.caib.sistra.db";
	String cfactory = "org.jnp.interfaces.NamingContextFactory";
	String url = "localhost";
	String urlPkg = "org.jboss.naming:org.jnp.interfaces";	 
	
    Hashtable ht = new Hashtable();
    ht.put(Context.INITIAL_CONTEXT_FACTORY , cfactory );
    ht.put(Context.PROVIDER_URL , url );
  	ht.put(Context.URL_PKG_PREFIXES, urlPkg);			  
  	InitialContext iniCtx = new InitialContext( ht );		
	javax.sql.DataSource datasource  = ( javax.sql.DataSource ) iniCtx.lookup( jndiDatasource );
	
	PreparedStatement stmt = null;
	con = datasource.getConnection();
	
	// TODO: Incompatible amb drivers Postgresql. Si realment es necessari, cercar
	// alternativa igual que a PluguinDominio, sinó llevar.
    //s.setQueryTimeout(30);
    String query = "SELECT TRA_IDENTI, TRV_VERSIO,TTR_DESC, TRV_IDISOP FROM STR_TRAMIT,STR_TRAVER,STR_TRATRA WHERE STR_TRAMIT.TRA_CODIGO = STR_TRAVER.TRV_CODTRA AND STR_TRAMIT.TRA_CODIGO = STR_TRATRA.TTR_CODTRA AND STR_TRATRA.TTR_CODIDI = ? ORDER BY TRA_IDENTI,TRV_VERSIO";
	stmt = con.prepareStatement(query);
	stmt.setString(1, lang);
	rs = stmt.executeQuery();     
	
	String id="",des,idAnt;
	int version;
	while (rs.next()){	
		idAnt = id;
		id = rs.getString("TRA_IDENTI");
		version = rs.getInt("TRV_VERSIO");
		des = rs.getString("TTR_DESC");
			
		if (!id.equals(idAnt)){		
%>								
	    <li>[<%=id%>] - <%=des %></li><br/>
<%		} %>
		&nbsp;&nbsp;&nbsp;Versión <%=version%>  - 
		Inicio directo:
<%		
		StringTokenizer st = new StringTokenizer(rs.getString("TRV_IDISOP"),",");
   		String idioma;
   		while (st.hasMoreTokens()){
   			idioma = st.nextToken();
%>
			[<a href="<%=contextPath%>/sistrafront/inicio?language=<%=idioma%>&modelo=<%=id%>&version=<%=version%>"><%=idioma%></a>]
			&nbsp;
<%   			
   		}
%>					
		<br>		
<% 		
	}	  
  } catch( Exception e ) {	  
	  out.println("Error obteniendo lista de tramites: " + e.toString() );
	  e.printStackTrace();
  }finally{
	  if (rs != null) {try{rs.close();}catch(Exception ex){}}
	  if (con != null) {try{con.close();}catch(Exception ex){}}
  }
%>	
</li>
</ul>
</p><%} else {%>
<h2>Página no disponible</h2>
<%}%>
</body>
</html>      