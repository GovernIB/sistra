package es.caib.redose.model;


/**
 * TablaTransformacionCsv
 */

public class TablaTransformacionCsv  implements java.io.Serializable {


    // Fields    
	private String codigo;
	private byte[] claves;

	public byte[] getClaves() {
		return claves;
	}

	public void setClaves(byte[] claves) {
		this.claves = claves;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


}
