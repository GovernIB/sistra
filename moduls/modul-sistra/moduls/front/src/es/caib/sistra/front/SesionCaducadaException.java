package es.caib.sistra.front;

/**
 * Excepción que indica que la sesión de tramitación ha caducado
 */
public class SesionCaducadaException extends Exception{
	public SesionCaducadaException(Throwable cause) {
        super("Sesion de tramitacion caducada",cause);
    }
}
