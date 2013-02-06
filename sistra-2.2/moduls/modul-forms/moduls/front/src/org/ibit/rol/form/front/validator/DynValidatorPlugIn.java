package org.ibit.rol.form.front.validator;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.ValidatorResourcesInitializer;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.validator.ValidatorPlugIn;

/**
 * Arranca el validator añadiendo ademas un generador de forms a partir de pantallas.
 */
public class DynValidatorPlugIn extends ValidatorPlugIn {

    protected static Log log = LogFactory.getLog(DynValidatorPlugIn.class);
    protected ActionServlet servlet = null;

    public void init(ActionServlet servlet, ModuleConfig config)
            throws ServletException {
        this.servlet = servlet;
        super.init(servlet, config);
    }

    protected void initResources() throws IOException, ServletException {

        // Espcificamos nuestro propio ValidatorResources
        // el resto del método és indentico al de ValidatorPlugIn.
        this.resources = new DynValidatorResources();

        if (getPathnames() == null || getPathnames().length() <= 0) {
            return;
        }

        StringTokenizer st = new StringTokenizer(getPathnames(), ",");
        while (st.hasMoreTokens()) {
            String validatorRules = st.nextToken().trim();

            if (log.isInfoEnabled()) {
                log.debug("Loading validation rules file from '" + validatorRules + "'");
            }

            InputStream input = servlet.getServletContext().getResourceAsStream(validatorRules);

            if (input != null) {
                BufferedInputStream bis = new BufferedInputStream(input);

                try {
                    // pass in false so resources aren't processed
                    // until last file is loaded
                    ValidatorResourcesInitializer.initialize(resources, bis, false);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                } finally {
                    bis.close();
                }

            } else {
                log.error(
                        "Skipping validation rules file from '"
                        + validatorRules
                        + "'.  No stream could be opened.");
            }
        }

        resources.process();
    }

    public void destroy() {
        this.servlet = null;
        super.destroy();
    }
}
