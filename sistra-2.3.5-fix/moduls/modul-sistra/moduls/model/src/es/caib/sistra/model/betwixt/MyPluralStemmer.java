package es.caib.sistra.model.betwixt;

import org.apache.commons.betwixt.ElementDescriptor;
import org.apache.commons.betwixt.strategy.DefaultPluralStemmer;

import java.util.Map;

/**
 * Implementació per defecte afegint però el mapping valorPosible -> valoresPosibles.
 */
public class MyPluralStemmer extends DefaultPluralStemmer {

    public ElementDescriptor findPluralDescriptor(String propertyName, Map map) {
        ElementDescriptor answer = null;
        
        //System.out.println( "PLURAL STEMMER " + propertyName +  ":" + map );
        
        if ("valorPosible".equals(propertyName)) {
            answer = (ElementDescriptor) map.get("valoresPosibles");
        }

        if (answer != null) {
            return answer;
        }

        return super.findPluralDescriptor(propertyName, map);
    }
}
