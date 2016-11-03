package es.caib.bantel.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import java.util.Iterator;

public class FuenteDatos implements Serializable{

	private Long codigo;	
	private String identificador;
	private String descripcion;		
	private Procedimiento procedimiento;
	private Set campos = new HashSet(0);
	private Set filas = new HashSet(0);
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Procedimiento getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(Procedimiento procedimiento) {
		this.procedimiento = procedimiento;
	}
	public Set getCampos() {
		return campos;
	}
	public void setCampos(Set campos) {
		this.campos = campos;
	}
	public Set getFilas() {
		return filas;
	}
	public void setFilas(Set filas) {
		this.filas = filas;
	}
	
	public void addCampoFuenteDatos(CampoFuenteDatos cfd) {
		cfd.setFuenteDatos(this);
		campos.add(cfd);
	}
	
	public void removeCampoFuenteDatos(CampoFuenteDatos cfd) {
		if (campos != null && campos.contains(cfd))  {
			campos.remove(cfd);
		}		
	}
	
	public FilaFuenteDatos addFilaFuenteDatos() {
		FilaFuenteDatos ffd = new FilaFuenteDatos();
		ffd.setFuenteDatos(this);
		filas.add(ffd);
		return ffd;
	}
	
	public void removeFilaFuenteDatos(FilaFuenteDatos ffd) {
		if (filas != null && filas.contains(ffd))  {
			filas.remove(ffd);
		}		
	}
	
	public void removeFilasFuenteDatos() {
		if (filas != null) {
			filas.clear();
		}
	}
	
	public CampoFuenteDatos getCampoFuenteDatos(String idCampo) {
		CampoFuenteDatos res = null;
		if (campos != null) {
			for (Iterator it = campos.iterator(); it.hasNext();){
				CampoFuenteDatos c = (CampoFuenteDatos) it.next();
				if (c.getIdentificador().equals(idCampo)) {
					res = c;
					break;
				}
			}
		}
		return res;
	}
	
	public FilaFuenteDatos getFilaFuenteDatos(int numFila) {
		if (getFilas() == null || numFila <= 0 || numFila > getFilas().size()) {
    		return null;			
    	}
    	
    	int num = 1;
    	FilaFuenteDatos ffd = null;
    	for (Iterator it = getFilas().iterator(); it.hasNext();) {
    		FilaFuenteDatos f = (FilaFuenteDatos) it.next();
    		if (num == numFila) {
    			ffd = f;
    			break;
    		}
    		num++;
    	}
    	
    	return ffd; 
	}
	
}
