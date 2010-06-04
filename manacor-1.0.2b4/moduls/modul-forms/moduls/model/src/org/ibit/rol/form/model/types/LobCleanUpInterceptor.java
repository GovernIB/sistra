package org.ibit.rol.form.model.types;

import net.sf.hibernate.Interceptor;
import net.sf.hibernate.type.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class LobCleanUpInterceptor implements Interceptor, Serializable {

    protected static final Log log = LogFactory.getLog(LobCleanUpInterceptor.class);

    // a thread local set to store temperary LOBs
    private static final ThreadLocal threadTempLobs = new ThreadLocal();

    public boolean onLoad(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4) {
        return false;
    }

    public boolean onFlushDirty(Object arg0, Serializable arg1, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) {
        return false;
    }

    public boolean onSave(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4) {
        return false;
    }

    public void onDelete(Object arg0, Serializable arg1, Object[] arg2, String[] arg3, Type[] arg4) {
    }

    public void preFlush(Iterator arg0) {
    }

    public Boolean isUnsaved(Object arg0) {
        return null;
    }

    public int[] findDirty(Object arg0, Serializable arg1, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) {
        return null;
    }

    public Object instantiate(Class arg0, Serializable arg1) {
        return null;
    }

    public void postFlush(Iterator arg0) {
        Set tempLobs = (Set) threadTempLobs.get();

        if (tempLobs == null) {
            return;
        }

        try {
            for (Iterator iter = tempLobs.iterator(); iter.hasNext();) {
                Object lob = iter.next();
                Method freeTemporary = lob.getClass().getMethod("freeTemporary", new Class[0]);
                freeTemporary.invoke(lob, new Object[0]);
                log.debug("lob cleaned");
            }
        } catch (SecurityException e) {
            log.error("clean LOB failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            log.error("clean LOB failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            log.error("clean LOB failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            log.error("clean LOB failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("clean LOB failed: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            threadTempLobs.set(null);
            tempLobs.clear();
        }
    }

    // register oracle temperary BLOB/CLOB into
    // a thread-local set, this should be called at
    // the end of nullSafeSet(...) in BinaryBlobType
    public static void registerTempLobs(Object lob) {
        getTempLobs().add(lob);
    }

    // lazy create temperary lob storage
    public static Set getTempLobs() {
        Set tempLobs = (Set) threadTempLobs.get();

        if (tempLobs == null) {
            tempLobs = new HashSet();
            threadTempLobs.set(tempLobs);
        }

        return tempLobs;
    }
}