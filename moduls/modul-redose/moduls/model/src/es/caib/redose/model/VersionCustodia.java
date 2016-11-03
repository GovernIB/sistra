package es.caib.redose.model;
// Generated 07-mar-2006 18:09:27 by Hibernate Tools 3.1.0 beta3

import java.util.Date;

public class VersionCustodia  implements java.io.Serializable {


    // Fields    

     private String codigo;
     private Documento documento;
     private Date fecha;
     private char borrar;


    // Constructors

    /** default constructor */
    public VersionCustodia() {
    }


	public char getBorrar() {
		return borrar;
	}


	public void setBorrar(char borrar) {
		this.borrar = borrar;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public Documento getDocumento() {
		return documento;
	}


	public void setDocumento(Documento documento) {
		this.documento = documento;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

    
}