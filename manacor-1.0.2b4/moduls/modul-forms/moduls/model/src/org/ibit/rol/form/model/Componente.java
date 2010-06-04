package org.ibit.rol.form.model;

public interface Componente {

    public Long getId();

    public void setId(Long id);

    public Pantalla getPantalla();

    public void setPantalla(Pantalla pantalla);

    public Paleta getPaleta();

    public void setPaleta(Paleta paleta);

    public boolean hasPantalla();

    public String getNombreLogico();

    public void setNombreLogico(String nombreLogico);

    public int getOrden();

    public void setOrden(int orden);

    public int getPosicion();

    public void setPosicion(int posicion);           
    
}
