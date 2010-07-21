package org.ibit.rol.form.persistence.plugins;

import org.ibit.rol.form.model.ValorPosible;

/**
 * Plugin per la sel·lecció de municipis.
 */
public class MunicipiPlugin extends ValorPosiblePlugin {

    public static final String[] MUNICIPIS = new String[] {
        "Alaior",
        "Alaró",
        "Alcúdia",
        "Algaida",
        "Andratx",
        "Ariany",
        "Artà",
        "Banyalbufar",
        "Binissalem",
        "Búger",
        "Bunyola",
        "Calvià",
        "Campanet",
        "Campos",
        "Capdepera",
        "Es Castell",
        "Ciutadella de Menorca",
        "Consell",
        "Costitx",
        "Deià",
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
        "Llubí",
        "Llucmajor",
        "Maó",
        "Manacor",
        "Mancor de la Vall",
        "Maria de la Salut",
        "Marratxí",
        "Es Mercadal",
        "Es Migjorn Gran",
        "Montuïri",
        "Muro",
        "Palma",
        "Petra",
        "Sa Pobla",
        "Pollença",
        "Porreres",
        "Puigpunyent",
        "Ses Salines",
        "Sant Antoni de Portmany",
        "Sant Joan",
        "Sant Joan de Labritja",
        "Sant Josep de sa Talaia",
        "Sant Llorenç des Cardassar",
        "Sant Lluís",
        "Santa Eugènia",
        "Santa Eulària des Riu",
        "Santa Margalida",
        "Santa Maria del Camí",
        "Santanyí",
        "Selva",
        "Sencelles",
        "Sineu",
        "Sóller",
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
