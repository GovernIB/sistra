package org.ibit.rol.form.persistence.auth;

import javax.security.auth.Subject;
import java.security.Principal;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Implementació de {@link CredentialManager} per JBoss.
 * Actualment provat amb JBoss 3.2.6-7.
 * Empra reflection per evitar dependències de compliació amb els packages de jboss.
 */
public class JbossCredentialManager implements CredentialManager {

    private static final String securityAssociation = "org.jboss.security.SecurityAssociation";

    Object savedCredential = null;
    Principal savedPrincipal = null;
    Subject savedSubject = null;

    public void saveCredentials() {
        savedCredential = invokeStaticMethod(securityAssociation,
                "getCredential", null, null);
        savedPrincipal = (Principal) invokeStaticMethod(securityAssociation,
                "getPrincipal", null, null);
        savedSubject = (Subject) invokeStaticMethod(securityAssociation,
                "getSubject", null, null);
        /*
        savedCredential = org.jboss.security.SecurityAssociation.getCredential();
        savedPrincipal = org.jboss.security.SecurityAssociation.getPrincipal();
        savedSubject = org.jboss.security.SecurityAssociation.getSubject();
        */
    }

    public void clearCredentials() {
        invokeStaticMethod(securityAssociation, "clear", null, null);
        /*
        org.jboss.security.SecurityAssociation.clear();
        */
    }

    public void restoreSavedCredentials() {
        invokeStaticMethod(securityAssociation, "setCredential",
                new Class[] {Object.class}, new Object[] {savedCredential} );
        invokeStaticMethod(securityAssociation, "setPrincipal",
                new Class[] {Principal.class}, new Object[] {savedPrincipal} );
        invokeStaticMethod(securityAssociation, "setSubject",
                new Class[] {Subject.class}, new Object[] {savedSubject} );
        /*
        org.jboss.security.SecurityAssociation.setCredential(savedCredential);
        org.jboss.security.SecurityAssociation.setPrincipal(savedPrincipal);
        org.jboss.security.SecurityAssociation.setSubject(savedSubject);
        */
    }

    private Object invokeStaticMethod(String className, String methodName,
                                      Class[] paramTypes, Object[] parameters) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, paramTypes);
            return method.invoke(null, parameters);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}