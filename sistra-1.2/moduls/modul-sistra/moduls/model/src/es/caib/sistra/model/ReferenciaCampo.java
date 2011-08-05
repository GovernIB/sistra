package es.caib.sistra.model;

/**
 * Modela referencia a un campo en scripts
 */
public class ReferenciaCampo {

	private String identificadorDocumento;
	private int instancia;
	private String campo;
	
	public ReferenciaCampo(String referenciaCampo) throws Exception{
		 
		int posDoc = referenciaCampo.indexOf('.');
		if (posDoc == -1) throw new Exception("Error en el formato de la referencia al campo. Formato esperado: documento.instancia.campo. Formato recibido:" + referenciaCampo);
		identificadorDocumento = referenciaCampo.substring(0,posDoc);
		int posIns = referenciaCampo.indexOf('.',posDoc + 1);
		if (posDoc == -1) throw new Exception("Error en el formato de la referencia al campo. Formato esperado: documento.instancia.campo. Formato recibido:" + referenciaCampo);
		try{
			instancia = Integer.parseInt(referenciaCampo.substring(posDoc + 1,posIns));
		}catch(Exception e){
			throw new Exception("Error en el formato de la referencia al campo. Formato esperado: documento.instancia.campo. Formato recibido:" + referenciaCampo);
		}
		campo = referenciaCampo.substring(posIns + 1);		
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getIdentificadorDocumento() {
		return identificadorDocumento;
	}

	public void setIdentificadorDocumento(String identificadorDocumento) {
		this.identificadorDocumento = identificadorDocumento;
	}

	public int getInstancia() {
		return instancia;
	}

	public void setInstancia(int instancia) {
		this.instancia = instancia;
	}
	
}
