package org.ibit.rol.form.persistence.plugins;

import org.ibit.rol.form.model.ValorPosible;

/**
 * Plugin per la sel�lecci� de municipis.
 */
public class MunicipiPlugin extends ValorPosiblePlugin {

    public static final String[] MUNICIPIS = new String[] {
        "Alaior",
        "Alar�",
        "Alc�dia",
        "Algaida",
        "Andratx",
        "Ariany",
        "Art�",
        "Banyalbufar",
        "Binissalem",
        "B�ger",
        "Bunyola",
        "Calvi�",
        "Campanet",
        "Campos",
        "Capdepera",
        "Es Castell",
        "Ciutadella de Menorca",
        "Consell",
        "Costitx",
        "Dei�",
        "Eivissa",
        "Escorca",
        "Esporles",
        "Estellencs",
        "Felanitx",
        "Ferreries",
        "Formentera",
        "Fornalutx",
        "Inca",
        "Lloret de Vistalegre",
        "Lloseta",
        "Llub�",
        "Llucmajor",
        "Ma�",
        "Manacor",
        "Mancor de la Vall",
        "Maria de la Salut",
        "Marratx�",
        "Es Mercadal",
        "Es Migjorn Gran",
        "Montu�ri",
        "Muro",
        "Palma",
        "Petra",
        "Sa Pobla",
        "Pollen�a",
        "Porreres",
        "Puigpunyent",
        "Ses Salines",
        "Sant Antoni de Portmany",
        "Sant Joan",
        "Sant Joan de Labritja",
        "Sant Josep de sa Talaia",
        "Sant Lloren� des Cardassar",
        "Sant Llu�s",
        "Santa Eug�nia",
        "Santa Eul�ria des Riu",
        "Santa Margalida",
        "Santa Maria del Cam�",
        "Santany�",
        "Selva",
        "Sencelles",
        "Sineu",
        "S�ller",
        "Son Servera",
        "Valldemossa",
        "Vilafranca de Bonany"};

    public Object execute(String lang) throws Exception {

        String cacheKey =lang;
        Object cached = getFromCache(cacheKey);
        if (cached != null) return cached;

        ValorPosible[] valorsPosibles = new ValorPosible[MUNICIPIS.length];
        for (int i = 0; i < MUNICIPIS.length; i++) {
            valorsPosibles[i] = crearValorPosible(String.valueOf(i), lang, MUNICIPIS[i]);
        }

        saveToCache(cacheKey, valorsPosibles);

        return valorsPosibles;
    }
}
