package org.ibit.rol.form.persistence.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Node;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.plugins.DatosListaElementos;
import org.mozilla.javascript.NativeArray;

import java.util.*;

/**
 * Mètodes relacionats amb camps.
 */
public final class CampoUtils {

    protected static Log log = LogFactory.getLog(CampoUtils.class);

    private CampoUtils() {
    }

    /**
     * Executarà l'expressió de valors posibles del camp i les enllaçarà a aquest.
     * @param campo
     * @param variables Map amb els valors dels camps emplenats (les claus són f_xxx per
     * els camps de la mateixa pantalla i f_yyy_xxx per els camps de la pantalla yyy.)
     */
    public static void calcularValoresPosibles(Campo campo, Map variables, boolean debugEnabled) {
        final String script = campo.getExpresionValoresPosibles();
        if (script != null && script.trim().length() > 0) {
            List valores = new LinkedList();
            Object result = ScriptUtil.evalScript(script, variables, debugEnabled);
            if (result != null) {
                if (result instanceof Collection) {
                    valores.addAll((Collection) result);
                } else if (result.getClass().isArray()) {
                    valores.addAll(Arrays.asList((Object[]) result));
                } else if (result instanceof ValorPosible) {
                    valores.add(result);
                } else {
                    ValorPosible vp = new ValorPosible();
                    vp.setDefecto(false);
                    vp.setValor(result.toString());
                    TraValorPosible trVp = new TraValorPosible();
                    trVp.setEtiqueta(result.toString());
                    vp.setTraduccion(campo.getCurrentLang(), trVp);
                    vp.setCurrentLang(campo.getCurrentLang());
                    valores.add(vp);
                }
            }
            campo.setValoresPosiblesCalculados(valores);
        }
    }

    public static Object calcularValorDefecto(Campo campo, Map variables, boolean debugEnabled) {
        final String script = campo.getExpresionValoresPosibles();
        if (script != null && script.trim().length() > 0) {
            return ScriptUtil.evalScript(script, variables, debugEnabled);
        }
        return null;
    }

    /**
     * Decide si un campo debe estar bloqueado o no.
     * @param campo del que se quiere verificar si ha de estar bloqueado o no.
     * @param doc documento XML con la configuración de los campos.
     * @param xPathBloqTodos expresión xPath que indica que todos los campos del formulario deben estar bloqueados.
     * @param xPathBloqCampo expresión xPath que busca si hay campos a bloquear.
     * @return true si el campo debe estar bloqueado. false e.o.c.
     */
    public static boolean bloquearCampo(Campo campo, Document doc, String xPathBloqTodos, String xPathBloqCampo) {
        if (doc.selectSingleNode(xPathBloqTodos) != null) {
            return true;
        } else {
            List camposBloqXML = doc.selectNodes(xPathBloqCampo);
            if (camposBloqXML.isEmpty()) {
                return false;
            }
            for (Iterator iterCamposBloqXML = camposBloqXML.iterator(); iterCamposBloqXML.hasNext();) {
                Node node = (Node) iterCamposBloqXML.next();
                if (node.getText().equals(campo.getEtiquetaPDF())) {
                    return true;
                }
            }
            return false;
        }
    }

    // -- INDRA: UTILIDAD PARA EJECUTAR EXPRESION DE AUTORRELLENABLE
    /**
     * Executarà l'expressió de autorrelleno del camp i retorna llista de valors calculats
     *
     * Para campos lista de elementos usará una variable de tipo ListaElementos
     *
     * @param campo
     * @param variables Map amb els valors dels camps emplenats (les claus són f_xxx per
     * els camps de la mateixa pantalla i f_yyy_xxx per els camps de la pantalla yyy.)
     */
    public static List calcularAutorrellenable(Campo campo, Map variables, boolean debugEnabled) {
        final String script = campo.getExpresionAutorellenable();
        List valores = new LinkedList();
        if (script != null && script.trim().length() > 0) {

        	// Si es un campo lista de elementos usamos variable LISTAELEMENTOS
        	if (campo instanceof ListaElementos){
        		variables.put("LISTAELEMENTOS",new DatosListaElementos());
        	}

             Object result = ScriptUtil.evalScript(script, variables, debugEnabled);

             // Si es un campo lista de elementos no esperara ningun valor, ya que
             // los valores se han establecido mendiante la vble LISTAELEMENTOS
             if (campo instanceof ListaElementos){
            	 // Devolvemos como resultado la lista de elementos
            	 valores.add(variables.get("LISTAELEMENTOS"));
             }else{
             // Si no es un campo lista de elementos esperara un valor o lista de valores
             if (result != null) {
                 if (result instanceof Collection) {
                     valores.addAll((Collection) result);
                 } else if (result.getClass().isArray()) {
                	 valores.addAll(Arrays.asList((Object[]) result));
                 } else if (result instanceof ValorPosible) {
                     valores.add(result);
                 } else if (result instanceof NativeArray) {
                 	NativeArray params = (NativeArray) result;
             		if ( params != null )
             		{
             			Object [] ids = params.getIds();
             			for ( int i = 0; i < ids.length; i++ )
             			{
             				Object valorParametro = params.get( (( Integer ) ids[i] ).intValue() , params );
             				ValorPosible vp = new ValorPosible();
                            vp.setDefecto(false);
                            vp.setValor(valorParametro.toString());
                            TraValorPosible trVp = new TraValorPosible();
                            trVp.setEtiqueta(valorParametro.toString());
                            vp.setTraduccion(campo.getCurrentLang(), trVp);
                            vp.setCurrentLang(campo.getCurrentLang());
                            valores.add(vp);
             			}
             		}
                 }else {
                     ValorPosible vp = new ValorPosible();
                     vp.setDefecto(false);
                     vp.setValor(result.toString());
                     TraValorPosible trVp = new TraValorPosible();
                     trVp.setEtiqueta(result.toString());
                     vp.setTraduccion(campo.getCurrentLang(), trVp);
                     vp.setCurrentLang(campo.getCurrentLang());
                     valores.add(vp);
                 }
             }
        }
        }
        return valores;
    }
    // -- INDRA: FIN

    // -- INDRA: OBTENER REFERENCIAS CAMPO DETALLE
    public static String getPantallaListaElementos(String referencia){
    	return referencia.split("#@#")[0];
    }
    public static String getCampoListaElementos(String referencia){
    	return referencia.split("#@#")[1];
    }
    public static String getReferenciaListaElementos(String pantalla,String campo){
    	return pantalla + "#@#" + campo;
    }
    // -- INDRA: FIN

}
