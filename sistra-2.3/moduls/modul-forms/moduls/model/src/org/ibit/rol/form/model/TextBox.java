package org.ibit.rol.form.model;

public class TextBox extends Campo {

	// Tipo texto: NO(Normal) / FE (Fecha) / HO (Hora) / NU (Numero) / IM (Importe)
	private String tipoTexto;
	

	public String getTipoTexto() {
		return tipoTexto;
	}

	public void setTipoTexto(String tipoTexto) {
		this.tipoTexto = tipoTexto;
	}
		
    private int filas;

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    private int columnas;

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    private boolean multilinea;

    public boolean isMultilinea() {
        return multilinea;
    }

    public void setMultilinea(boolean multilinea) {
        this.multilinea = multilinea;
    }

    public boolean isIndexed() {
        return false;
    }

    public ValorPosible getValorPosible() {
        if (getValoresPosibles().isEmpty()) {
            ValorPosible vp = new ValorPosible();
            vp.setDefecto(true);
            addValorPosible(vp);
        }

        return (ValorPosible) getValoresPosibles().get(0);
    }

    public void setTraduccion(String idioma, Traduccion traduccion) {
        super.setTraduccion(idioma, traduccion);
        if (traduccion == null) {
            getValorPosible().setTraduccion(idioma, null);
        }
    }

}
