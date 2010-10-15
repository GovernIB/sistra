package org.ibit.rol.form.persistence.auth;

/**
 * Classe <i>factory</i> per obtenir implementacions de {@link CredentialManager}.
 */
public class CredentialManagerFactory {

    public static CredentialManager getCredentialManager() {
        return new JbossCredentialManager();
    }
}
