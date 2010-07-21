package org.ibit.rol.form.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Pantalla extends Traducible {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Formulario formulario;

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private int orden;

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    private String expresion;

    public String getExpresion() {
        return expresion;
    }

    public void setExpresion(String expresion) {
        this.expresion = expresion;
    }

    private boolean ultima;

    public boolean isUltima() {
        return ultima;
    }

    public void setUltima(boolean ultima) {
        this.ultima = ultima;
    }

    private boolean inicial;

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    private Set ayudas = new HashSet();

    public Set getAyudas() {
        return ayudas;
    }

    protected void setAyudas(Set ayudas) {
        this.ayudas = ayudas;
    }

    public void addAyuda(AyudaPantalla ayuda) {
        ayuda.setPantalla(this);
        ayudas.add(ayuda);
    }

    private List componentes = new ArrayList();

    public List getComponentes() {
        return componentes;
    }

    protected void setComponentes(List componentes) {
        this.componentes = componentes;
    }

    public void addComponente(Componente componente) {
        componente.setPantalla(this);
        componente.setOrden(componentes.size());
        componentes.add(componente);
    }

    public void removeComponente(Componente componente) {
        int ind = componentes.indexOf(componente);
        componentes.remove(ind);
        for (int i = ind; i < componentes.size(); i++) {
            Componente comp = (Componente) componentes.get(i);
            comp.setOrden(i);
        }
    }

    public List getCampos() {
        List camps = new ArrayList();
        for (int i = 0; i < getComponentes().size(); i++) {
            Componente componente = (Componente) getComponentes().get(i);
            if (componente instanceof Campo) {
                camps.add(componente);
            }
        }

        return camps;
    }

    public Campo findCampo(String nombre) {
        for (int i = 0; i < componentes.size(); i++) {
            Componente comp = (Componente) componentes.get(i);
            if (comp instanceof Campo && comp.getNombreLogico().equals(nombre)) {
                return (Campo) comp;
            }
        }
        return null;
    }

    public void setCurrentLang(String currentLang) {
        super.setCurrentLang(currentLang);
        for (Iterator iterator = ayudas.iterator(); iterator.hasNext();) {
            AyudaPantalla ayudaPantalla = (AyudaPantalla) iterator.next();
            ayudaPantalla.setCurrentLang(currentLang);
        }
        for (int i = 0; i < componentes.size(); i++) {
            Componente componente = (Componente) componentes.get(i);
            if (componente instanceof Traducible) {
                ((Traducible) componente).setCurrentLang(currentLang);
            }
        }
    }

    public void addTraduccion(String lang, TraPantalla traduccion) {
        setTraduccion(lang, traduccion);
    }
    
    // INDRA: PANTALLA DETALLE DE UN COMPONENTE TIPO LISTA ELEMENTOS
    private String componenteListaElementos;

	public String getComponenteListaElementos() {
		return componenteListaElementos;
	}

	public void setComponenteListaElementos(String componenteListaElementos) {
		this.componenteListaElementos = componenteListaElementos;
	}
    
    // INDRA: PANTALLA DETALLE DE UN COMPONENTE TIPO LISTA ELEMENTOS
    
}
