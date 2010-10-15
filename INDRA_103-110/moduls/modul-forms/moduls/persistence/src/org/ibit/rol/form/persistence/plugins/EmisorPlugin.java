package org.ibit.rol.form.persistence.plugins;

import java.util.List;
import java.util.ArrayList;

/**
 * Utilitats per generar les sigles de la conselleria, direccio general i usuari que ens passen
 */
public class EmisorPlugin {

    public static String obtenerEmisor(String nombreConselleria, String nombreDireccionGen, String usuario){
        String abreviaturaCons = "";
        String abreviaturaDire = "";

        nombreConselleria = nombreConselleria.replaceAll("de", "");
        nombreConselleria = nombreConselleria.replaceAll("d'", "");
        nombreConselleria = nombreConselleria.replaceAll("i", "");
        nombreConselleria = nombreConselleria.replaceAll("y", "");
        nombreConselleria = nombreConselleria.replaceAll(",", "");

        List palabrasCons = separarPalabras(nombreConselleria);
        for (int i = 0; i < palabrasCons.size(); i++) {
            String palabra =  (String)palabrasCons.get(i);
            abreviaturaCons = abreviaturaCons + palabra.charAt(0);
        }

        nombreDireccionGen = nombreDireccionGen.replaceAll("de", "");
        nombreDireccionGen = nombreDireccionGen.replaceAll("d'", "");
        nombreDireccionGen = nombreDireccionGen.replaceAll("i", "");
        nombreDireccionGen = nombreDireccionGen.replaceAll("y", "");
        nombreDireccionGen = nombreDireccionGen.replaceAll(",", "");

        List palabrasDire = separarPalabras(nombreDireccionGen);
        for (int i = 0; i < palabrasDire.size(); i++) {
            String palabra =  (String)palabrasDire.get(i);
            abreviaturaDire = abreviaturaDire + palabra.charAt(0);
        }

        return abreviaturaCons+"/"+abreviaturaDire+"/"+usuario;
    }


     private static List separarPalabras(String text){

        List paraules = new ArrayList();
        boolean quedenParaules = true;
        int espai = 0;
        String tmp;

        String txt = text.trim(); //llevam els blancs del principi i del final

        while(quedenParaules){

            txt = txt.substring(espai,txt.length());

            /*Recorrem cada paraula de la cerca*/
            espai = txt.indexOf(" ");

            if(espai!=-1){
                tmp = txt.substring(0,espai);
                if(tmp!=null && !tmp.equals("") && !tmp.equals(" ")){
                    paraules.add(tmp);
                }
                espai++;
            } else {
                tmp = txt.substring(0,txt.length());
                if(tmp!=null && !tmp.equals("") && !tmp.equals(" ")){
                    paraules.add(tmp);
                }
                quedenParaules = false;
            }
        }
        return paraules;
    }


}
