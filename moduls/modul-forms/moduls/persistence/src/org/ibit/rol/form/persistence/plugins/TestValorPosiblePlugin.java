package org.ibit.rol.form.persistence.plugins;

import java.util.List;
import java.util.ArrayList;

/**
 * Exemple de generador de valors posibles.
 */
public class TestValorPosiblePlugin extends ValorPosiblePlugin {

    public Object execute(String lang) throws Exception {

        Object cached = getFromCache(lang);
        if (cached != null) return cached;

        List result = new ArrayList();
        result.add(crearValorPosible("v1", lang, "valor test 1"));
        result.add(crearValorPosible("v2", lang, "valor test 2"));
        result.add(crearValorPosible("v3", lang, "valor test 3"));
        result.add(crearValorPosible("v4", lang, "valor test 4"));
        result.add(crearValorPosible("v5", lang, "valor test 5"));
        result.add(crearValorPosible("v6", lang, "valor test 6"));

        saveToCache(lang, (ArrayList) result);

        return result;
    }
}
