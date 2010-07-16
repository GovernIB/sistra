package org.ibit.rol.form.persistence.auth;

/**
 * Interficie per implementar m�todes especifics del contenidor
 * per desactivar moment�niament la autenticaci�.
 */
public interface CredentialManager {

    /**
     * Guarda la autenticaci� actual.
     */
    void saveCredentials();

    /**
     * Recupera la autenticaci� guardada mitjan�ant {@link #saveCredentials}.
     */
    void restoreSavedCredentials();

    /**
     * Elimina l'autenticaci� actual. Abans de cridar aquest m�tode �s convenient
     * cridar {@link #saveCredentials()} si despr�s es vol recuperar l'autenticaci� amb
     * {@link #restoreSavedCredentials()}.
     */
    void clearCredentials();
}
