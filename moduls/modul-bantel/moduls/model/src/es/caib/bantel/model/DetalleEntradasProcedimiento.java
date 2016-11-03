package es.caib.bantel.model;

/**
 * Detalle entradas procedimiento en un intervalo de tiempo.
 * @author rsanz
 *
 */
public class DetalleEntradasProcedimiento {

	private long procesadasOk;
	
	private long procesadasError;
	
	private long noProcesadas;
	
	private long procesadasErrorPendientes;
	
	private long noProcesadasPendientes;

	public long getProcesadasOk() {
		return procesadasOk;
	}

	public void setProcesadasOk(long procesadasOk) {
		this.procesadasOk = procesadasOk;
	}

	public long getProcesadasError() {
		return procesadasError;
	}

	public void setProcesadasError(long procesadasError) {
		this.procesadasError = procesadasError;
	}

	public long getNoProcesadas() {
		return noProcesadas;
	}

	public void setNoProcesadas(long noProcesadas) {
		this.noProcesadas = noProcesadas;
	}

	public long getProcesadasErrorPendientes() {
		return procesadasErrorPendientes;
	}

	public void setProcesadasErrorPendientes(long procesadasErrorPendientes) {
		this.procesadasErrorPendientes = procesadasErrorPendientes;
	}

	public long getNoProcesadasPendientes() {
		return noProcesadasPendientes;
	}

	public void setNoProcesadasPendientes(long noProcesadasPendientes) {
		this.noProcesadasPendientes = noProcesadasPendientes;
	}
	
	public boolean existenEntradas() {
		return (procesadasOk + procesadasError + noProcesadas + procesadasErrorPendientes + noProcesadasPendientes) > 0;
	}
	
}
