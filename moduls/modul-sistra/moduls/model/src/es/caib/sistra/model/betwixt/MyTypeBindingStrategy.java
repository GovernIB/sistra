package es.caib.sistra.model.betwixt;

import org.apache.commons.betwixt.strategy.TypeBindingStrategy;

/**
 * TypeBindingStrategy que empra la implementaci� per defecte excepte per
 * arrays de bytes que diu que �s un tipus primitiu.
 */
public class MyTypeBindingStrategy extends TypeBindingStrategy {
    public TypeBindingStrategy.BindingType bindingType(Class type) {
        if (type.equals(byte[].class)) {
            return TypeBindingStrategy.BindingType.PRIMITIVE;
        } else {
            return DEFAULT.bindingType(type);
        }
    }
}
