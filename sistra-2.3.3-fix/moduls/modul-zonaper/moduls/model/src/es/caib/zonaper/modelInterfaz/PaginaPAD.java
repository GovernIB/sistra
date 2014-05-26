package es.caib.zonaper.modelInterfaz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *	 Clase que representa una busqueda paginada.
 * </p>
 */
public class PaginaPAD implements Serializable
{
	/**
	 * Numero de pagina actual.
	 */
	private int numeroPagina;
	
	/**
	 * Indica si hay pagina previa.
	 */
	private boolean previousPage;
	
	/**
	 * Indica si hay siguiente pagina.
	 */
	private boolean nextPage;
	
	/**
	 * Numero resultados.
	 */
	private int totalResults;
	
	/**
	 * Lista de resumenes expediente.	
	 */
	private List list = new ArrayList();

	public int getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(int numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public boolean isPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
			
}
