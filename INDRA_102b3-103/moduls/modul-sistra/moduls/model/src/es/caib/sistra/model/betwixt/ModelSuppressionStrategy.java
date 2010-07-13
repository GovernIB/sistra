package es.caib.sistra.model.betwixt;

import org.apache.commons.betwixt.strategy.PropertySuppressionStrategy;

import es.caib.sistra.model.DatoJustificante;
import es.caib.sistra.model.Documento;
import es.caib.sistra.model.DocumentoNivel;
import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.EspecTramiteNivel;
import es.caib.sistra.model.MensajeTramite;
import es.caib.sistra.model.TramiteNivel;
import es.caib.sistra.model.TramiteVersion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Controla les propietats que es suprimiran del model.
 */
public class ModelSuppressionStrategy extends PropertySuppressionStrategy {

    private Map propiedadesInvalidas = new HashMap();

    protected void addSupressionList(Class clazz, String[] properties) {
        Set set = new HashSet(Arrays.asList(properties));
        propiedadesInvalidas.put(clazz, set);
    }

    public ModelSuppressionStrategy() 
    {
    	addSupressionList( TramiteVersion.class, new String[] {"codigo", "tramite"} );
    	addSupressionList( EspecTramiteNivel.class, new String[] {"codigo" } );
    	addSupressionList( Documento.class, new String[] {"codigo", "tramiteVersion"} );
    	addSupressionList( DocumentoNivel.class, new String[] {"codigo", "documento"} );
    	addSupressionList( TramiteNivel.class, new String[] {"codigo", "tramiteVersion"} );
    	addSupressionList( MensajeTramite.class, new String[] {"codigo", "tramiteVersion"} );
    	addSupressionList( DatoJustificante.class, new String[] { "codigo", "especTramiteNivel" }  );
    	addSupressionList( Dominio.class, new String[] {"codigo", "organoResponsable" } );
    }

    public boolean suppressProperty(Class clazz, Class tipus, String nom) {
        // si es un traducible eliminam directament una serie de propietats.
    	//System.out.println( clazz.getName() + ": Propiedad [" + nom + "]" );
        if (es.caib.sistra.model.Traducible.class.isAssignableFrom(clazz)) {
            if (nom.equals("langs") || nom.equals("currentLang") || nom.equals("traduccion")) {
                return true;
            }
        }

        // si nó, miram les llistes.
        if (propiedadesInvalidas.containsKey(clazz)) {
            if (((Set) propiedadesInvalidas.get(clazz)).contains(nom)) {
                return true;
            }
        }

        return false;
    }
}
