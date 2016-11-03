package es.caib.mobtratel.persistence.delegate;


import java.util.HashMap;
import java.util.Map;

/**
 * Factoria de objetos delegate.
 */
public class DelegateFactory {
	

    private static Map delegates = new HashMap();

    protected static synchronized Delegate getDelegate(Class clazz ) 
    {
    	if (!Delegate.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz + " no es subclase de " + Delegate.class);
        }

        // Utilizamos un cache para reutilizar instancias cacheables.
        if (StatelessDelegate.class.isAssignableFrom(clazz)) {
            Object delegate = delegates.get(clazz);
            if (delegate == null) {
                delegate = getEnhancedInstance(clazz);
                delegates.put(clazz, delegate);
            }
            return (Delegate) delegate;
        } else {
            return (Delegate) getEnhancedInstance(clazz);
        }
    }
        
    private static Object getEnhancedInstance(Class clazz) {
        try {
            	return clazz.newInstance();
            /*
            Class [] parameterTypes = new Class[] { environment.getClass() };
            Constructor constructor = clazz.getConstructor( parameterTypes ); 
            return constructor.newInstance( new Object[] { environment });
            */
        } catch (Throwable t) {
        	t.printStackTrace();
            return null;
        }
        // Descomentar si es volen Logs.
        //return Enhancer.create(clazz, new LogInterceptor("delegate"));
    }
}
