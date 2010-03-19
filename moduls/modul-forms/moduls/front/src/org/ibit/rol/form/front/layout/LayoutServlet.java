package org.ibit.rol.form.front.layout;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.persistence.delegate.DelegateException;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.delegate.InstanciaTelematicaDelegate;

/**
 * @web.servlet name="layoutServlet" load-on-startup="100"
 * @web.servlet-mapping url-pattern="/layoutServlet"
 * @web.servlet-init-param name="defaultLayoutName" value="main"
 * @web.env-entry name="layout/main" type="java.lang.String" value="/layout/mainLayout.jsp"
 * @web.env-entry name="layout/caib" type="java.lang.String" value="/layout/caibLayout.jsp"
 */
public class LayoutServlet extends HttpServlet {

    protected static final Log log = LogFactory.getLog(LayoutServlet.class);

    /** Nombre del layout por defecto */
    protected String defaultLayoutName;

    /** Map que asocia nombres de layout con su path */
    protected Map layoutPathMap = new HashMap();

    public void init() throws ServletException {
        super.init();
        // Nombre del layout por defecto
        defaultLayoutName = getServletConfig().getInitParameter("defaultLayoutName");

        try {
            Context context = new InitialContext();
            // Bajo JNDI habrá todos los pares: nombre, path que metemos en el Map
            NamingEnumeration namingEnum = context.listBindings("java:comp/env/layout");
            while (namingEnum.hasMore()) {
                Binding binding = (Binding) namingEnum.next();
                layoutPathMap.put(binding.getName(), binding.getObject());
            }
        } catch (NamingException e) {
            log.error("Error accediendo a JNDI", e);
        }

        // El layout por defecto debe estar en el Map!
        if (!layoutPathMap.containsKey(defaultLayoutName)) {
            log.error("El layout por defecto \"" + defaultLayoutName + "\" no está definido bajo java:comp/env/layout");
            throw new UnavailableException("Error de configuración");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);

            String path;
            if (delegate instanceof InstanciaTelematicaDelegate) {
                InstanciaTelematicaDelegate itd = (InstanciaTelematicaDelegate) delegate;                
                path = getLayoutPath(itd.obtenerLayOut());                
            } else {
                path = getLayoutPath(defaultLayoutName);
            }

            log.debug("Devolviendo: " + path);
            RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
            rd.include(request, response);

        } catch (DelegateException e) {
            log.error("Error a delegate", e);
        }
    }

    protected String getLayoutPath(String name) {
        if (name != null && layoutPathMap.containsKey(name)) {
            return (String) layoutPathMap.get(name);
        } else {
            log.warn("Layout \"" + name + "\" no definido, devolviendo \"" + defaultLayoutName + "\"");
            return (String) layoutPathMap.get(defaultLayoutName);
        }
    }
}
