package es.caib.sistra.loginmodule.loginib.formauthenticator;

import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Realm;
import org.apache.catalina.Session;
import org.apache.catalina.authenticator.AuthenticatorBase;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.ActionCode;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.MimeHeaders;

public class FormAuthenticator
  extends AuthenticatorBase
{
  private static Log log = LogFactory.getLog(FormAuthenticator.class);
  protected static final String info = "org.apache.catalina.authenticator.FormAuthenticator/1.0";
  protected String characterEncoding = null;

  public String getInfo()
  {
    return "org.apache.catalina.authenticator.FormAuthenticator/1.0";
  }

  public String getCharacterEncoding()
  {
    return this.characterEncoding;
  }

  public void setCharacterEncoding(String encoding)
  {
    this.characterEncoding = encoding;
  }

  public boolean authenticate(org.apache.catalina.connector.Request request, Response response, LoginConfig config)
    throws IOException
  {
    Session session = null;

    Principal principal = request.getUserPrincipal();
    String ssoId = (String)request.getNote("org.apache.catalina.request.SSOID");
    if (principal != null)
    {
      if (log.isDebugEnabled()) {
        log.debug("Already authenticated '" + principal.getName() + "'");
      }
      if (ssoId != null) {
        associate(ssoId, request.getSessionInternal(true));
      }
      return true;
    }
    if (ssoId != null)
    {
      if (log.isDebugEnabled()) {
        log.debug("SSO Id " + ssoId + " set; attempting " + "reauthentication");
      }
      if (reauthenticateFromSSO(ssoId, request)) {
        return true;
      }
    }
    if (!this.cache)
    {
      session = request.getSessionInternal(true);
      if (log.isDebugEnabled()) {
        log.debug("Checking for reauthenticate in session " + session);
      }
      String username = (String)session.getNote("org.apache.catalina.session.USERNAME");

      String password = (String)session.getNote("org.apache.catalina.session.PASSWORD");
      if ((username != null) && (password != null))
      {
        if (log.isDebugEnabled()) {
          log.debug("Reauthenticating username '" + username + "'");
        }
        principal = this.context.getRealm().authenticate(username, password);
        if (principal != null)
        {
          session.setNote("org.apache.catalina.authenticator.PRINCIPAL", principal);
          if (!matchRequest(request))
          {
            register(request, response, principal, "FORM", username, password);

            return true;
          }
        }
        if (log.isDebugEnabled()) {
          log.debug("Reauthentication failed, proceed normally");
        }
      }
    }
    if (matchRequest(request))
    {
      session = request.getSessionInternal(true);
      if (log.isDebugEnabled()) {
        log.debug("Restore request from session '" + session.getIdInternal() + "'");
      }
      principal = (Principal)session.getNote("org.apache.catalina.authenticator.PRINCIPAL");

      register(request, response, principal, "FORM", (String)session.getNote("org.apache.catalina.session.USERNAME"), (String)session.getNote("org.apache.catalina.session.PASSWORD"));
      if (this.cache)
      {
        session.removeNote("org.apache.catalina.session.USERNAME");
        session.removeNote("org.apache.catalina.session.PASSWORD");
      }
      if (restoreRequest(request, session))
      {
        if (log.isDebugEnabled()) {
          log.debug("Proceed to restored request");
        }
        return true;
      }
      if (log.isDebugEnabled()) {
        log.debug("Restore of original request failed");
      }
      response.sendError(400);
      return false;
    }
    MessageBytes uriMB = MessageBytes.newInstance();
    CharChunk uriCC = uriMB.getCharChunk();
    uriCC.setLimit(-1);
    String contextPath = request.getContextPath();
    String requestURI = request.getDecodedRequestURI();
    response.setContext(request.getContext());

    boolean loginAction = (requestURI.startsWith(contextPath)) && (requestURI.endsWith("/j_security_check"));
    if (!loginAction)
    {
      session = request.getSessionInternal(true);
      if (log.isDebugEnabled()) {
        log.debug("Save request in session '" + session.getIdInternal() + "'");
      }
      try
      {
        saveRequest(request, session);
      }
      catch (IOException ioe)
      {
        log.debug("Request body too big to save during authentication");
        response.sendError(403, sm.getString("authenticator.requestBodyTooBig"));

        return false;
      }
      forwardToLoginPage(request, response, config);
      return false;
    }
    Realm realm = this.context.getRealm();
    if (this.characterEncoding != null) {
      request.setCharacterEncoding(this.characterEncoding);
    }
    String username = request.getParameter("j_username");
    String password = request.getParameter("j_password");
    if (log.isDebugEnabled()) {
      log.debug("Authenticating username '" + username + "'");
    }

    // Verificacion de tickets loginib y sesiones http
    if ((username != null) && (username.startsWith("{TICKET-")))
    {
      String sesionId = username.substring("{TICKET-".length(), username.length() - 1);
      session = request.getSessionInternal(true);
      if (!sesionId.equals(session.getId()))
      {
        log.error("Se intenta login con ticket perteneciente a otra sesion");
        response.sendError(403, "Se intenta login con ticket perteneciente a otra sesion");

        return false;
      }
    }
    principal = realm.authenticate(username, password);
    if (principal == null)
    {
      forwardToErrorPage(request, response, config);
      return false;
    }
    if (log.isDebugEnabled()) {
      log.debug("Authentication of '" + username + "' was successful");
    }
    if (session == null) {
      session = request.getSessionInternal(false);
    }
    if (session == null)
    {
      if (this.containerLog.isDebugEnabled()) {
        this.containerLog.debug("User took so long to log on the session expired");
      }
      response.sendError(408, sm.getString("authenticator.sessionExpired"));

      return false;
    }
    session.setNote("org.apache.catalina.authenticator.PRINCIPAL", principal);

    session.setNote("org.apache.catalina.session.USERNAME", username);
    session.setNote("org.apache.catalina.session.PASSWORD", password);

    requestURI = savedRequestURL(session);
    if (log.isDebugEnabled()) {
      log.debug("Redirecting to original '" + requestURI + "'");
    }
    if (requestURI == null) {
      response.sendError(400, sm.getString("authenticator.formlogin"));
    } else {
      response.sendRedirect(response.encodeRedirectURL(requestURI));
    }
    return false;
  }

  protected void forwardToLoginPage(org.apache.catalina.connector.Request request, Response response, LoginConfig config)
  {
    RequestDispatcher disp = this.context.getServletContext().getRequestDispatcher(config.getLoginPage());
    try
    {
      disp.forward(request.getRequest(), response.getResponse());
      response.finishResponse();
    }
    catch (Throwable t)
    {
      log.warn("Unexpected error forwarding to login page", t);
    }
  }

  protected void forwardToErrorPage(org.apache.catalina.connector.Request request, Response response, LoginConfig config)
  {
    RequestDispatcher disp = this.context.getServletContext().getRequestDispatcher(config.getErrorPage());
    try
    {
      disp.forward(request.getRequest(), response.getResponse());
    }
    catch (Throwable t)
    {
      log.warn("Unexpected error forwarding to error page", t);
    }
  }

  protected boolean matchRequest(org.apache.catalina.connector.Request request)
  {
    Session session = request.getSessionInternal(false);
    if (session == null) {
      return false;
    }
    SavedRequest sreq = (SavedRequest)session.getNote("org.apache.catalina.authenticator.REQUEST");
    if (sreq == null) {
      return false;
    }
    if (session.getNote("org.apache.catalina.authenticator.PRINCIPAL") == null) {
      return false;
    }
    String requestURI = request.getRequestURI();
    if (requestURI == null) {
      return false;
    }
    return requestURI.equals(sreq.getRequestURI());
  }

  protected boolean restoreRequest(org.apache.catalina.connector.Request request, Session session)
    throws IOException
  {
    SavedRequest saved = (SavedRequest)session.getNote("org.apache.catalina.authenticator.REQUEST");

    session.removeNote("org.apache.catalina.authenticator.REQUEST");
    session.removeNote("org.apache.catalina.authenticator.PRINCIPAL");
    if (saved == null) {
      return false;
    }
    request.clearCookies();
    Iterator cookies = saved.getCookies();
    while (cookies.hasNext()) {
      request.addCookie((Cookie)cookies.next());
    }
    MimeHeaders rmh = request.getCoyoteRequest().getMimeHeaders();
    rmh.recycle();
    boolean cachable = ("GET".equalsIgnoreCase(saved.getMethod())) || ("HEAD".equalsIgnoreCase(saved.getMethod()));

    Iterator names = saved.getHeaderNames();
    while (names.hasNext())
    {
      String name = (String)names.next();
      if ((!"If-Modified-Since".equalsIgnoreCase(name)) && ((!cachable) || (!"If-None-Match".equalsIgnoreCase(name))))
      {
        Iterator values = saved.getHeaderValues(name);
        while (values.hasNext()) {
          rmh.addValue(name).setString((String)values.next());
        }
      }
    }
    request.clearLocales();
    Iterator locales = saved.getLocales();
    while (locales.hasNext()) {
      request.addLocale((Locale)locales.next());
    }
    request.getCoyoteRequest().getParameters().recycle();

    request.getCoyoteRequest().getParameters().setQueryStringEncoding(request.getConnector().getURIEncoding());
    if ("POST".equalsIgnoreCase(saved.getMethod()))
    {
      ByteChunk body = saved.getBody();
      if ((body != null) && (body.getLength() > 0))
      {
        request.clearParameters();
        request.getCoyoteRequest().action(ActionCode.ACTION_REQ_SET_BODY_REPLAY, body);

        MessageBytes contentType = MessageBytes.newInstance();

        String savedContentType = saved.getContentType();
        if (savedContentType == null) {
          savedContentType = "application/x-www-form-urlencoded";
        }
        contentType.setString(savedContentType);
        request.getCoyoteRequest().setContentType(contentType);
      }
      else
      {
        Iterator params = saved.getParameterNames();
        while (params.hasNext())
        {
          String name = (String)params.next();
          request.addParameter(name, saved.getParameterValues(name));
        }
      }
    }
    request.getCoyoteRequest().method().setString(saved.getMethod());

    request.getCoyoteRequest().queryString().setString(saved.getQueryString());

    request.getCoyoteRequest().requestURI().setString(saved.getRequestURI());
    return true;
  }

  protected void saveRequest(org.apache.catalina.connector.Request request, Session session)
    throws IOException
  {
    HttpServletRequest hreq = request.getRequest();

    SavedRequest saved = new SavedRequest();
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        saved.addCookie(cookies[i]);
      }
    }
    Enumeration names = request.getHeaderNames();
    while (names.hasMoreElements())
    {
      String name = (String)names.nextElement();
      Enumeration values = request.getHeaders(name);
      while (values.hasMoreElements())
      {
        String value = (String)values.nextElement();
        saved.addHeader(name, value);
      }
    }
    Enumeration locales = request.getLocales();
    while (locales.hasMoreElements())
    {
      Locale locale = (Locale)locales.nextElement();
      saved.addLocale(locale);
    }
    Map parameters = hreq.getParameterMap();
    Iterator paramNames = parameters.keySet().iterator();
    while (paramNames.hasNext())
    {
      String paramName = (String)paramNames.next();
      String[] paramValues = (String[])parameters.get(paramName);
      saved.addParameter(paramName, paramValues);
    }
    saved.setMethod(request.getMethod());
    saved.setQueryString(request.getQueryString());
    saved.setRequestURI(request.getRequestURI());

    session.setNote("org.apache.catalina.authenticator.REQUEST", saved);

    hreq.getSession().setAttribute("savedrequest", saved);
  }

  protected String savedRequestURL(Session session)
  {
    SavedRequest saved = (SavedRequest)session.getNote("org.apache.catalina.authenticator.REQUEST");
    if (saved == null) {
      return null;
    }
    StringBuffer sb = new StringBuffer(saved.getRequestURI());
    if (saved.getQueryString() != null)
    {
      sb.append('?');
      sb.append(saved.getQueryString());
    }
    return sb.toString();
  }
}