package es.caib.exception;

public class LoggableException extends Exception {

	private boolean logged = false;

	public LoggableException() {
		super();
	}

	public LoggableException(String message, Throwable cause) {
		super(message, cause);
	}

	public LoggableException(String message) {
		super(message);
	}

	public LoggableException(Throwable cause) {
		super(cause);
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

}
