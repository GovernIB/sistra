package es.caib.sistra.loginibclient;

/**
 * Excepción LoginIB.
 *
 * @author Indra
 *
 */
@SuppressWarnings("serial")
public class LoginIBClientException extends Exception {

	public LoginIBClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoginIBClientException(String message) {
		super(message);
	}

}
