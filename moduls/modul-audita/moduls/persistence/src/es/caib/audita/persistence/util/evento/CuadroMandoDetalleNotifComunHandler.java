package es.caib.audita.persistence.util.evento;

public class CuadroMandoDetalleNotifComunHandler extends
		CuadroMandoDetalleHandler
{

	public CuadroMandoDetalleNotifComunHandler() {
		super();
		// Renombramos dao class para que coja fichero propiedades a partir del classname de esta clase
		this.changeDaoClass = true;
	}
		
}
