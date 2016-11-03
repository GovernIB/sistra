	package es.caib.sistra.model;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.xml.sax.SAXException;

import es.caib.sistra.model.betwixt.Configurator;

public class Dominio  implements java.io.Serializable {
	
	public final static char DOMINIO_EJB='E';
	public final static char DOMINIO_WEBSERVICE='W';
	public final static char DOMINIO_SQL='S';
	public final static char DOMINIO_FUENTE_DATOS='F';
	
	// localizacionEJB
	public final static char EJB_REMOTO = 'R';
	public final static char EJB_LOCAL  = 'L';
	
	// autenticacionExplicita: Indica si hay que realizar autenticacion explicita
	public final static char AUTENTICACION_EXPLICITA_SINAUTENTICAR 	= 'N';
	public final static char AUTENTICACION_EXPLICITA_ESTANDAR 	= 'S';		
	public final static char AUTENTICACION_EXPLICITA_ORGANISMO 	= 'C';	
	
	private Long codigo;
	private OrganoResponsable organoResponsable;
	private String identificador;
	private String descripcion;
	private char tipo;
	private String url;
	private String sql;
	private char localizacionEJB = EJB_LOCAL;
	private char autenticacionExplicita = AUTENTICACION_EXPLICITA_SINAUTENTICAR;
	private String JNDIName;
	private String usr;
	private String pwd;
	private char cacheable='N';
	private String versionWS;
	private String soapActionWS;
	
	
	
	
	
	public Dominio(){
		
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersionWS() {
		return versionWS;
	}

	public void setVersionWS(String versionWS) {
		this.versionWS = versionWS;
	}

	public char getLocalizacionEJB()
	{
		return localizacionEJB;
	}

	public void setLocalizacionEJB(char localizacionEJB)
	{
		this.localizacionEJB = localizacionEJB;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public char getAutenticacionExplicita()
	{
		return autenticacionExplicita;
	}

	public void setAutenticacionExplicita(char tipoAccesoEJB)
	{
		this.autenticacionExplicita = tipoAccesoEJB;
	}

	public String getUsr()
	{
		return usr;
	}

	public void setUsr(String usr)
	{
		this.usr = usr;
	}

	public String getJNDIName()
	{
		return JNDIName;
	}

	public void setJNDIName(String name)
	{
		JNDIName = name;
	}
	
	public boolean isSecured()
	{
		return (autenticacionExplicita != AUTENTICACION_EXPLICITA_SINAUTENTICAR);  
	}

	public char getCacheable() {
		return cacheable;
	}

	public void setCacheable(char cacheable) {
		this.cacheable = cacheable;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public OrganoResponsable getOrganoResponsable()
	{
		return organoResponsable;
	}

	public void setOrganoResponsable(OrganoResponsable organoResponsable)
	{
		this.organoResponsable = organoResponsable;
	}
	
	public byte[] toXml()
    {
		byte[] result = null;
    	try
    	{
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    	BeanWriter beanWriter = new BeanWriter(baos, "UTF-8");
	        beanWriter.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
	        Configurator.configure(beanWriter);
	        beanWriter.write(this);
	        beanWriter.close();
	        result = baos.toByteArray();
    	}
        catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( IntrospectionException ie )
		{
			ie.printStackTrace();
		}
		catch ( SAXException saxe )
		{
			saxe.printStackTrace();
		}
    	return result;
    }
	
	public static Dominio fromXml( byte[] xml )
	{
		Dominio d = null;
		try
		{
			BeanReader beanReader = new BeanReader();
	        Configurator.configure(beanReader);
	        d = ( Dominio ) beanReader.parse( new ByteArrayInputStream( xml ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		catch ( IntrospectionException ie )
		{
			ie.printStackTrace();
		}
		catch ( SAXException saxe )
		{
			saxe.printStackTrace();
		}
		return d;
	}

	public String getSoapActionWS() {
		return soapActionWS;
	}

	public void setSoapActionWS(String soapActionWS) {
		this.soapActionWS = soapActionWS;
	}

}
