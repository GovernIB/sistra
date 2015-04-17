/*
 * 
 */
package es.caib.consola;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Class NullFilterChain.
 */
public final class NullFilterChain implements FilterChain {

    /* (non-Javadoc)
     * @see javax.servlet.FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    //@Override
    public void doFilter(final ServletRequest servletrequest,
            final ServletResponse servletresponse) throws IOException,
            ServletException {
    }

    /**
     * Instancia un nuevo null filter chain de NullFilterChain.
     */
    public NullFilterChain() {
    }

}
