package es.caib.sistra.front;

/**
 * Excepci�n que indica que la sesi�n de tramitaci�n ha caducado
 */
public class SesionCaducadaException extends Exception{
	public SesionCaducadaException(Throwable cause) {
        super("Sesion de tramitacion caducada",cause);
    }
}
