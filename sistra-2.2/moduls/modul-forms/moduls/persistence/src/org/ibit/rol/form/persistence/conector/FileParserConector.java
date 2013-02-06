package org.ibit.rol.form.persistence.conector;

import org.ibit.rol.form.model.Archivo;

/**
 * TODO documentar
 */
public abstract class FileParserConector implements Conector {

    protected Archivo plantilla;

    public void setPlantilla(Archivo archivo) {
        this.plantilla = archivo;
    }
}
