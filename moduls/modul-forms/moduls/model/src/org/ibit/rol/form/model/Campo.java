package org.ibit.rol.form.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Campo extends Traducible implements Componente {

    private Long id;
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Pantalla pantalla;

    public Pantalla getPantalla() {
        return pantalla;
    }

    public void setPantalla(Pantalla pantalla) {
        this.pantalla = pantalla;
    }

    public boolean hasPantalla() {
        return pantalla != null;
    }

    private Paleta paleta;

    public Paleta getPaleta() {
        return paleta;
    }

    public void setPaleta(Paleta paleta) {
        this.paleta = paleta;
    }

    private String nombreLogico;

    public String getNombreLogico() {
        return nombreLogico;
    }

    public void setNombreLogico(String nombreLogico) {
        this.nombreLogico = nombreLogico;
    }

    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    private int posicion;

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    private String etiquetaPDF;

    public String getEtiquetaPDF() {
        return etiquetaPDF;
    }

    public void setEtiquetaPDF(String etiquetaPDF) {
        this.etiquetaPDF = etiquetaPDF;
    }

    private int numero;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    private boolean oculto = false;

    public boolean isOculto() {
        return oculto;
    }

    public void setOculto(boolean oculto) {
        this.oculto = oculto;
    }

    private String expresionAutorellenable;

    public String getExpresionAutorellenable() {
        return expresionAutorellenable;
    }

    public void setExpresionAutorellenable(String expresionAutorellenable) {
        this.expresionAutorellenable = expresionAutorellenable;
    }
    
    private String expresionAutocalculo;

    public String getExpresionAutocalculo() {
        return expresionAutocalculo;
    }

    public void setExpresionAutocalculo(String expresionAutocalculo) {
        this.expresionAutocalculo = expresionAutocalculo;
    }

    private String expresionDependencia;

    public String getExpresionDependencia() {
        return expresionDependencia;
    }

    public void setExpresionDependencia(String expresionDependencia) {
        this.expresionDependencia = expresionDependencia;
    }

    private String expresionValidacion;

    public String getExpresionValidacion() {
        return expresionValidacion;
    }

    public void setExpresionValidacion(String expresionValidacion) {
        this.expresionValidacion = expresionValidacion;
    }

    private String expresionValoresPosibles;

    public String getExpresionValoresPosibles() {
        return expresionValoresPosibles;
    }

    public void setExpresionValoresPosibles(String expresionValoresPosibles) {
        this.expresionValoresPosibles = expresionValoresPosibles;
    }

    private String expresionPostProceso;

    public String getExpresionPostProceso() {
        return expresionPostProceso;
    }

    public void setExpresionPostProceso(String expresionPostProceso) {
        this.expresionPostProceso = expresionPostProceso;
    }

    protected String tipoValor = "java.lang.String";

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public boolean isNatural() {
        return (getTipoValor().equals("java.lang.Integer") ||
                getTipoValor().equals("java.lang.Long") ||
                getTipoValor().equals("java.lang.Short"));
    }

    public boolean isReal() {
        return (getTipoValor().equals("java.lang.Float") ||
                getTipoValor().equals("java.lang.Double"));
    }

    public boolean isImagen() {
        return (getTipoValor().equals("org.ibit.rol.form.model.Archivo"));
    }

    public boolean isArray() {
        return (getTipoValor().endsWith("[]"));
    }

    public abstract boolean isIndexed();

    private Patron patron;

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    private List validaciones = new ArrayList();

    public List getValidaciones() {
        return validaciones;
    }

    protected void setValidaciones(List validaciones) {
        this.validaciones = validaciones;
    }

    public void addValidacion(Validacion validacion) {
        validacion.setCampo(this);
        validacion.setOrden(validaciones.size());
        validaciones.add(validacion);
    }

    public void removeValidacion(Validacion validacion) {
        int ind = validaciones.indexOf(validacion);
        if (ind > -1) {
            validaciones.remove(ind);
            for (int i = ind; i < validaciones.size(); i++) {
                Validacion val = (Validacion) validaciones.get(i);
                val.setOrden(i);
            }
        }
    }

    public Validacion findValidacion(String mascara) {
        for (int i = 0; i < validaciones.size(); i++) {
            Validacion validacion = (Validacion) validaciones.get(i);
            if (validacion.getMascara().getNombre().equals(mascara)) {
                return validacion;
            }
        }
        return null;
    }

    private List valoresPosibles = new ArrayList();

    public List getValoresPosibles() {
        return valoresPosibles;
    }

    protected void setValoresPosibles(List valoresPosibles) {
        this.valoresPosibles = valoresPosibles;
    }

    public void addValorPosible(ValorPosible valorPosible) {
        valorPosible.setCampo(this);
        valorPosible.setOrden(valoresPosibles.size());
        valoresPosibles.add(valorPosible);
    }

    public void removeValorPosible(ValorPosible valorPosible) {
        int ind = valoresPosibles.indexOf(valorPosible);
        valoresPosibles.remove(ind);
        for (int i = ind; i < valoresPosibles.size(); i++) {
            ValorPosible vp = (ValorPosible) valoresPosibles.get(i);
            vp.setOrden(i);
        }
    }

    private List valoresPosiblesCalculados = new ArrayList();

    public List getAllValoresPosibles() {
        final List valores = new ArrayList(valoresPosibles);
        valores.addAll(valoresPosiblesCalculados);
        return valores;
    }

    public void setValoresPosiblesCalculados(List valoresPosiblesCalculados) {
        this.valoresPosiblesCalculados = valoresPosiblesCalculados;
    }

    public void setCurrentLang(String currentLang) {
        super.setCurrentLang(currentLang);
        for (int i = 0; i < valoresPosibles.size(); i++) {
            ValorPosible vp = (ValorPosible) valoresPosibles.get(i);
            vp.setCurrentLang(currentLang);
        }
    }

    public void addTraduccion(String lang, TraCampo traduccion) {
        setTraduccion(lang, traduccion);
    }

    private boolean bloqueado = false;

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isBloqueado() {
        return this.bloqueado;
    }

    
    // --- INDRA: PROPS LISTA ELEMENTOS    
    private boolean mostrarEnTabla;
	private Integer anchoColumna;
   
    
    public Integer getAnchoColumna() {   
		return anchoColumna;
	}
	
	public void setAnchoColumna(Integer anchoColumna) {
		this.anchoColumna = anchoColumna;
	}
	
	public boolean isMostrarEnTabla() {
		return mostrarEnTabla;
	}
	
	public void setMostrarEnTabla(boolean mostrarEnTabla) {
		this.mostrarEnTabla = mostrarEnTabla;
	}
    // --- INDRA: PROPS LISTA ELEMENTOS
    
	
	// -- INDRA: AÑADIR PROPS MEJORA VISUAL FORMS V3
	private int colSpan = 1;
	
	public int getColSpan () {
		return colSpan;
	}
    
    public void setColSpan (int pColSpan) {
    	colSpan = pColSpan;
    }
    
    private boolean sinEtiqueta = false;
    
    public boolean isSinEtiqueta() {
    	return sinEtiqueta;
    }
    
    public void setSinEtiqueta (boolean pSinEtiqueta) {
    	sinEtiqueta = pSinEtiqueta;
    }
    
    private boolean encuadrar = false;
    
    public  boolean isEncuadrar(){
    	return encuadrar;
    }
    
    public void setEncuadrar (boolean pEncuadrar){
    	encuadrar = pEncuadrar;
    }
        
    private String alineacion = "I";
    
    public String getAlineacion(){
    	return alineacion;
    }
    
    public void setAlineacion ( String pAlineacion){
    	alineacion = pAlineacion;
    }
	// -- INDRA: AÑADIR PROPS MEJORA VISUAL FORMS V3
}
