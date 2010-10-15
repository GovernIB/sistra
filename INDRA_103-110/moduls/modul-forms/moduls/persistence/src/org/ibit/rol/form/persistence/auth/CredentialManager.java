package org.ibit.rol.form.persistence.auth;

/**
 * Interficie per implementar mètodes especifics del contenidor
 * per desactivar momentàniament la autenticació.
 */
public interface CredentialManager {

    /**
     * Guarda la autenticació actual.
     */
    void saveCredentials();

    /**
     * Recupera la autenticació guardada mitjançant {@link #saveCredentials}.
     */
    void restoreSavedCredentials();

    /**
     * Elimina l'autenticació actual. Abans de cridar aquest mètode és convenient
     * cridar {@link #saveCredentials()} si després es vol recuperar l'autenticació amb
     * {@link #restoreSavedCredentials()}.
     */
    void clearCredentials();
}
