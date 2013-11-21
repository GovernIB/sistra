package es.caib.bantel.model;

import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

public class ConsultaFuenteDatos {
	
	private String idFuenteDatos;
	
	private List campos = new ArrayList();
	
	private List filtros = new ArrayList();
	
	private String campoOrden;

	public String getIdFuenteDatos() {
		return idFuenteDatos;
	}

	public void setIdFuenteDatos(String idFuenteDatos) {
		this.idFuenteDatos = idFuenteDatos;
	}

	public List getCampos() {
		return campos;
	}

	public void setCampos(List campos) {
		this.campos = campos;
	}

	public List getFiltros() {
		return filtros;
	}

	public void setFiltros(List filtros) {
		this.filtros = filtros;
	}

	public String getCampoOrden() {
		return campoOrden;
	}

	public void setCampoOrden(String campoOrden) {
		this.campoOrden = campoOrden;
	}
	
	public String print() {
		
		String sql = "SELECT ";
		
		if (campos != null) {
			boolean primer = true;
			for (Iterator it = getCampos().iterator(); it.hasNext();) {
				if (primer) {
					primer = false;
				} else {
					sql += ", ";
				}
				sql += it.next().toString();
			}
		}
		
		sql += "\nFROM " + getIdFuenteDatos();
		
		if (getFiltros() != null && getFiltros().size() > 0) {
			sql += "\nWHERE ";
			for (Iterator it = getFiltros().iterator(); it.hasNext();) {
				FiltroConsultaFuenteDatos ffd = (FiltroConsultaFuenteDatos) it.next();
				if (StringUtils.isNotBlank(ffd.getConector())) {
					sql += " " + ffd.getConector();
				}
				sql += " " + ffd.getCampo() + " " + ffd.getOperador() + " ?";				
			}
		}
		
		if (StringUtils.isNotBlank(getCampoOrden())) {
			sql += "\nORDER BY " + getCampoOrden();
		}
		
		return sql;
	}

}
