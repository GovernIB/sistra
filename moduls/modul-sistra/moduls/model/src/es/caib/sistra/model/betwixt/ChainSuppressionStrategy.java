package es.caib.sistra.model.betwixt;

import org.apache.commons.betwixt.strategy.PropertySuppressionStrategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Permet encadenar una llista de {@link PropertySuppressionStrategy},
 * si algún d'ells dona <code>true</code> la propietat s'elimina.
 */
public class ChainSuppressionStrategy extends PropertySuppressionStrategy {

    private final List strategies = new ArrayList();

    /**
     * @see #suppressProperty(java.lang.Class, java.lang.Class, java.lang.String)
     */
    public boolean suppressProperty(Class classContainingTheProperty, Class propertyType, String propertyName) {
        boolean result = false;
        for (Iterator it = strategies.iterator(); it.hasNext();) {
            PropertySuppressionStrategy strategy = (PropertySuppressionStrategy) it.next();
            if (strategy.suppressProperty(classContainingTheProperty, propertyType, propertyName)) {
                result = true;
                break;
            }

        }
        return result;
    }

    /**
     * Adds a strategy to the list
     * @param strategy <code>PropertySuppressionStrategy</code>, not null
     */
    public void addStrategy(PropertySuppressionStrategy strategy) {
        strategies.add(strategy);
    }
}
