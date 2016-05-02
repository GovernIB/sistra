package es.caib.exception;

import javax.ejb.EJBException;

public class LoggableEJBException extends EJBException {

	private boolean logged = false;

	public LoggableEJBException() {
		super();
	}

	public LoggableEJBException(Exception ex) {
		super(ex);
	}

	public LoggableEJBException(String message, Exception ex) {
		super(message, ex);
	}

	public LoggableEJBException(String message) {
		super(message);
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

}
