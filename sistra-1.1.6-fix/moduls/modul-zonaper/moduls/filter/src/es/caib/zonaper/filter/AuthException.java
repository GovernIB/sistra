package es.caib.zonaper.filter;

public class AuthException extends Exception {

	public static final int ERROR_DESCONOCIDO = 0;
	public static final int ERROR_CONEXION_PAD= 1;
	public static final int ERROR_NIF_NO_CONCUERDA = 2;
	public static final int ERROR_NO_NIF = 3;
	public static final int ERROR_NIF_YA_EXISTE = 4;
	
	private int codigoError;
	
	public AuthException(int codigoError,Throwable cause){
		super(cause);
		this.codigoError=codigoError;
	}
	
	public AuthException(int codigoError){
		this.codigoError=codigoError;
	}

	public int getCodigoError() {
		return codigoError;
	}
	
}
