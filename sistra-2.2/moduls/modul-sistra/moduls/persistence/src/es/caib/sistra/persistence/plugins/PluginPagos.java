package es.caib.sistra.persistence.plugins;

import java.util.Map;

import es.caib.zonaper.modelInterfaz.DocumentoPersistentePAD;
import es.caib.zonaper.modelInterfaz.TramitePersistentePAD;
import es.caib.sistra.model.DatosPago;
import es.caib.sistra.model.ReferenciaCampo;

/**
 * Plugin que permite acceder a los datos de los formularios y de los pagos
 */
public class PluginPagos {
	
	private Map datosPagos = null;
	private TramitePersistentePAD tramitePersistentePAD;	
		
	/**
	 * Establece datos de los formularios y pagos
	 */
	public void setDatosPagos(Map datosPagos){		
		this.datosPagos =  datosPagos;
	}
	
	/**
	 * Establece estado persistencia
	 */
	public void setEstadoPersistencia(TramitePersistentePAD tramitePAD){
		tramitePersistentePAD = tramitePAD;
	}
	
	public Map getDatosPagos() {
		return datosPagos;
	}
	
	/**
	 * Obtiene el valor de un campo de pago 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getDatoPago(String idDocumento,int instancia,String referenciaCampo) throws Exception{
		
		DatosPago pago = (DatosPago) datosPagos.get(idDocumento + "-" + instancia);
		if (pago == null) throw new Exception("Referencia no válida: no existe documento " +idDocumento+"-"+ instancia);
		
		return pago.getValorCampo(referenciaCampo);
		
	}
		
	/**
	 * Obtiene el valor de un campo del pago 
	 * 
	 * @param referenciaCampo en formato idDocumento.instancia.campo
	 * @return
	 * @throws Exception
	 */
	public String getDatoPago(String referenciaCampo) throws Exception{		
		ReferenciaCampo ref = new ReferenciaCampo(referenciaCampo);				
		return getDatoPago(ref.getIdentificadorDocumento(),ref.getInstancia(),ref.getCampo());
	}
	
	/**
	 * Obtiene el estado del pago 
	 * 
	 * @param idDocumento
	 * @param instancia
	 * @param referenciaCampo
	 * @return
	 * @throws Exception
	 */
	public String getEstadoPago(String idDocumento,int instancia) throws Exception{
		DocumentoPersistentePAD docPAD = (DocumentoPersistentePAD) tramitePersistentePAD.getDocumentos().get(idDocumento + "-" + instancia);
		if (docPAD == null) throw new Exception("No existe documento " + idDocumento + "-" + instancia);
		return Character.toString(docPAD.getEstado());
	}
		
}
