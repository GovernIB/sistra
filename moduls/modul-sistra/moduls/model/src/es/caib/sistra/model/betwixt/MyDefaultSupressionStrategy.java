package es.caib.sistra.model.betwixt;

import org.apache.commons.betwixt.strategy.PropertySuppressionStrategy;

/**
 * Extensió del PropertySupressionStrategy per defecte per permetre l'atribut class
 * en les col·leccions de Componentes.
 */
public class MyDefaultSupressionStrategy extends PropertySuppressionStrategy {

    public boolean suppressProperty(Class clazz, Class type, String name) 
    {
    	/*
    	if (Componente.class.isAssignableFrom(clazz)) {
            return false;
        } else {
        */
            return PropertySuppressionStrategy.DEFAULT.suppressProperty(clazz, type, name);
        /*            
        }
        */
    }
}
