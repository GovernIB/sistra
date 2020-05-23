package es.caib.sistra.loginmodule.loginib.formauthenticator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.Cookie;

import org.apache.tomcat.util.buf.ByteChunk;

public final class SavedRequest
  implements Serializable
{
  private ArrayList<Cookie> cookies = new ArrayList();

  public void addCookie(Cookie cookie)
  {
    this.cookies.add(cookie);
  }

  public Iterator getCookies()
  {
    return this.cookies.iterator();
  }

  private HashMap<String, ArrayList<String>> headers = new HashMap();

  public void addHeader(String name, String value)
  {
    ArrayList<String> values = (ArrayList)this.headers.get(name);
    if (values == null)
    {
      values = new ArrayList();
      this.headers.put(name, values);
    }
    values.add(value);
  }

  public Iterator getHeaderNames()
  {
    return this.headers.keySet().iterator();
  }

  public Iterator getHeaderValues(String name)
  {
    ArrayList values = (ArrayList)this.headers.get(name);
    if (values == null) {
      return new ArrayList().iterator();
    }
    return values.iterator();
  }

  private ArrayList<Locale> locales = new ArrayList();

  public void addLocale(Locale locale)
  {
    this.locales.add(locale);
  }

  public Iterator getLocales()
  {
    return this.locales.iterator();
  }

  private String method = null;

  public String getMethod()
  {
    return this.method;
  }

  public void setMethod(String method)
  {
    this.method = method;
  }

  private HashMap<String, String[]> parameters = new HashMap();

  public void addParameter(String name, String[] values)
  {
    this.parameters.put(name, values);
  }

  public Iterator getParameterNames()
  {
    return this.parameters.keySet().iterator();
  }

  public String[] getParameterValues(String name)
  {
    return (String[])this.parameters.get(name);
  }

  private String queryString = null;

  public String getQueryString()
  {
    return this.queryString;
  }

  public void setQueryString(String queryString)
  {
    this.queryString = queryString;
  }

  private String requestURI = null;

  public String getRequestURI()
  {
    return this.requestURI;
  }

  public void setRequestURI(String requestURI)
  {
    this.requestURI = requestURI;
  }

  private ByteChunk body = null;

  public ByteChunk getBody()
  {
    return this.body;
  }

  public void setBody(ByteChunk body)
  {
    this.body = body;
  }

  private String contentType = null;

  public String getContentType()
  {
    return this.contentType;
  }

  public void setContentType(String contentType)
  {
    this.contentType = contentType;
  }
}