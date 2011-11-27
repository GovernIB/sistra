package es.caib.redose.modelInterfaz;

/**
 * Referencia a un documento en el RDS. Un documento en el RDS se referencia mediante una Referencia RDS compuesta por un c�digo y una clave de acceso. 
 * 
 */
public class ReferenciaRDS  implements java.io.Serializable {
	
	private long codigo;
	private String clave;
	
	public ReferenciaRDS(){
	}
	
	public ReferenciaRDS(long codigo,String clave){
		this.codigo=codigo;
		this.clave=clave;
	}
	
	/**
	 * Obtiene clave de acceso	 
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * Establece clave de acceso
	 * @param clave Clave de acceso
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	/**
	 * Obtiene c�digo del documento
	 * @return C�digo del documento
	 */
	public long getCodigo() {
		return codigo;
	}
	/**
	 * Establece c�digo del documento
	 * @param codigo C�digo del documento
	 */
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	public String toString(){
		return this.codigo + "-" + this.clave;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof ReferenciaRDS){
			ReferenciaRDS r = (ReferenciaRDS) obj;
			if (r.getCodigo() != this.getCodigo()) return false;
			if (!r.getClave().equals(this.getClave())) return false;
			return true;
		}else{
			return super.equals(obj);
		}
	}
}
