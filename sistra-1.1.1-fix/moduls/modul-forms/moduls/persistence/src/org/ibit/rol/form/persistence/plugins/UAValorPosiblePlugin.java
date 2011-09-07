package org.ibit.rol.form.persistence.plugins;
/*
import org.ibit.rol.sac.persistence.util.UnidadAdministrativaFacadeUtil;
import org.ibit.rol.sac.persistence.intf.UnidadAdministrativaFacadeHome;
import org.ibit.rol.sac.persistence.intf.UnidadAdministrativaFacade;
import org.ibit.rol.sac.model.UnidadAdministrativa;
import org.ibit.rol.sac.model.TraduccionUA;
import org.ibit.rol.form.persistence.auth.CredentialManager;
import org.ibit.rol.form.persistence.auth.CredentialManagerFactory;


import javax.naming.Context;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
*/

/**
 * Genera valors posibles a partir del SAC mitjançant RMI.
 */
public class UAValorPosiblePlugin extends ValorPosiblePlugin {

    private String providerUrl;
    private Long arrel;


    /**
     * Constructor.
     * @param providerUrl Adreça RMI del servidor.
     * @param arrel Codi de la unitat administrativa pare de les opcions.
     * Les opcions generades seran una per cada unitat administrativa filla de la
     * indicada.
     */
    public UAValorPosiblePlugin(String providerUrl, Long arrel) {
        this.providerUrl = providerUrl;
        this.arrel = arrel;
    }

    public Object execute(String lang) throws Exception {
    	return null;
    	/*
        String cacheKey = arrel + "/" + lang + "/" + providerUrl;
        Object cached = getFromCache(cacheKey);
        if (cached != null) return cached;

        // Abans de conectarnos a un EJB remot hem llevar la autenticació actual pq sinó
        // s'enviarà amb la nova petició.
        CredentialManager credManager = CredentialManagerFactory.getCredentialManager();
        credManager.saveCredentials();
        credManager.clearCredentials();

        Properties props = new Properties();
        props.setProperty(Context.PROVIDER_URL, providerUrl);

        UnidadAdministrativaFacadeHome home = UnidadAdministrativaFacadeUtil.getHome(props);
        UnidadAdministrativaFacade facade = home.create();
        List unitats = facade.listarHijosUA(arrel);
        List result = new ArrayList(unitats.size());
        for (int i = 0; i < unitats.size(); i++) {
            UnidadAdministrativa unitat = (UnidadAdministrativa) unitats.get(i);
            TraduccionUA traduccio = (TraduccionUA) unitat.getTraduccion(lang);
            if (traduccio == null) traduccio = (TraduccionUA) unitat.getTraduccion();
            result.add(crearValorPosible(unitat.getId().toString(), lang, traduccio.getNombre()));
        }

        saveToCache(cacheKey, (ArrayList) result);

        // Restauram l'autenticació.        
        credManager.restoreSavedCredentials();

        return result;
        */
    }

}
