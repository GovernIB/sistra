package es.caib.zkutils.zk.model;

/**
 * Seleccion selector.
 * @author rsanz
 *
 */
public class SeleccionSelector {

	/**
	 * Tipo selector.
	 */
	private String tipoSelector;
	
	/**
	 * Valor seleccionado.
	 */
	private Object valorSeleccionado;

	/**
	 * Constructor.
	 */
	public SeleccionSelector() {
		super();
	}

	/**
	 * Constructor.
	 * @param tipoSelector tipoSelector
	 * @param valorSeleccionado Valor seleccionado
	 */
	public SeleccionSelector(String tipoSelector, Object valorSeleccionado) {
		super();
		this.tipoSelector = tipoSelector;
		this.valorSeleccionado = valorSeleccionado;
	}

	public String getTipoSelector() {
		return tipoSelector;
	}

	public void setTipoSelector(String tipoSelector) {
		this.tipoSelector = tipoSelector;
	}

	public Object getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(Object valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}
	
	
	
	
	
}
