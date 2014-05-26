package es.caib.bantel.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import java.util.Iterator;

public class FilaFuenteDatos implements Serializable{

	private Long codigo;	
	private FuenteDatos fuenteDatos;
	private Set valores = new HashSet(0);
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public FuenteDatos getFuenteDatos() {
		return fuenteDatos;
	}
	public void setFuenteDatos(FuenteDatos fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}
	public Set getValores() {
		return valores;
	}
	public void setValores(Set valores) {
		this.valores = valores;
	}
	
	public String getValorFuenteDatos(String identificadorCampo) {
		String valor = null;
		if (getValores() != null) {
			ValorFuenteDatos vfdCampo = null;
			for (Iterator it = getValores().iterator(); it.hasNext();) {
				ValorFuenteDatos vfd =  (ValorFuenteDatos) it.next();
				if (vfd.getCampoFuenteDatos().getIdentificador().equals(identificadorCampo)) {
					vfdCampo = vfd;
					break;
				}
			}
			if (vfdCampo != null) {
				valor = vfdCampo.getValor();				
			}		
		}		
		return valor;
	}
	
	
	public void addValorFuenteDatos(CampoFuenteDatos cfd, String valor) {
		ValorFuenteDatos vfd = new ValorFuenteDatos();
		vfd.setFilaFuenteDatos(this);
		vfd.setCampoFuenteDatos(cfd);
		vfd.setValor(valor);
		this.getValores().add(vfd);
	}
	
	public ValorFuenteDatos removeValorFuenteDatos(String identificadorCampo) {
		ValorFuenteDatos vfdDelete = null;
		if (getValores() != null) {
			for (Iterator it = getValores().iterator(); it.hasNext();) {
				ValorFuenteDatos vfd =  (ValorFuenteDatos) it.next();
				if (vfd.getCampoFuenteDatos().getIdentificador().equals(identificadorCampo)) {
					vfdDelete = vfd;
					break;
				}
			}
			if (vfdDelete != null) {
				getValores().remove(vfdDelete);				
			}		
		}		
		return vfdDelete;
	}		
		
}
