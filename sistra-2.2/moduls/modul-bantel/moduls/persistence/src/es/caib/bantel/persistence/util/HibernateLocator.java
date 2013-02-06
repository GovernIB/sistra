package es.caib.bantel.persistence.util;

import java.net.URL;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.cfg.Environment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe para inicializar y guardar referencia a SessionFactory.
 */
public class HibernateLocator {

    protected static Log log = LogFactory.getLog(HibernateLocator.class);

    private final SessionFactory sf;

    private HibernateLocator() {
        sf = initSessionFactory();
    }

    protected void finalize() throws Throwable {
        sf.close();
    }

    // Singleton
    private static HibernateLocator instance;

    protected static HibernateLocator getInstance() {
        if (instance == null) {
            synchronized(HibernateLocator.class) {
                if (instance == null) {
                    instance = new HibernateLocator();
                }
            }
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() {
        return getInstance().sf;
    }

    private static SessionFactory initSessionFactory() {
        try {
            System.setProperty(Environment.USE_STREAMS_FOR_BINARY, "true");
        } catch (SecurityException e) {
            log.warn("No s'ha pogut fixar la propietat " + Environment.USE_STREAMS_FOR_BINARY);
        }
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL url = cl.getResource("bantel-hibernate.cfg.xml");
            Configuration cfg = new Configuration().configure(url);
            return cfg.buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}