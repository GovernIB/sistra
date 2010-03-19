package org.ibit.rol.form.persistence.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.ibit.rol.form.model.Campo;
import org.ibit.rol.form.model.CheckBox;
import org.ibit.rol.form.model.ComboBox;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.ListBox;
import org.ibit.rol.form.model.ListaElementos;
import org.ibit.rol.form.model.Pantalla;
import org.ibit.rol.form.model.RadioButton;
import org.ibit.rol.form.model.TextBox;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.TreeBox;
import org.ibit.rol.form.model.ValorPosible;

/**
 * Utilidades para generar un Map de valores por defecto a partir de una pantalla.
 */
public final class PantallaUtils {

    protected static Log log = LogFactory.getLog(PantallaUtils.class);

    private PantallaUtils() {

    }

   
    /**
     * 
     * FUNCION NO SE EMPLEA PARA FORMULARIOS TELEMATICOS
     * 
     * 
     * Calcula els valors inicials dels camps d'una pantalla.
     * @param pantalla
     * @param variables Map amb els valors dels camps emplenats (les claus són f_xxx per
     * els camps de la mateixa pantalla i f_yyy_xxx per els camps de la pantalla yyy.)
     * @return Valors inicials.
     * TODO Tenir en compte filebox
     */
    public static Map valoresDefecto(Pantalla pantalla, Map variables) {
        List campos = pantalla.getCampos();
        Map valores = new HashMap(campos.size());

        for (int i = 0; i < campos.size(); i++) {
            try {
                Campo campo = (Campo) campos.get(i);

                String key = campo.getNombreLogico();
                String tipoValor = campo.getTipoValor();

                String initial = null;

                if (campo.getExpresionValoresPosibles() != null && campo.getExpresionValoresPosibles().trim().length() > 0) {
                    CampoUtils.calcularValoresPosibles(campo, variables);
                }
                List valoresPosibles = campo.getAllValoresPosibles();

                if (campo instanceof CheckBox) {

                    boolean defecto = ((CheckBox) campo).isValorDefecto();
                    initial = String.valueOf(defecto);

                } else if (campo instanceof ListBox || campo instanceof TreeBox) {

                    tipoValor = "java.lang.String[]";
                    for (int j = 0; j < valoresPosibles.size(); j++) {
                        ValorPosible vp = (ValorPosible) valoresPosibles.get(j);
                        if (vp.isDefecto()) {
                            if (initial == null) {
                                initial = "" + vp.getValor();
                            } else {
                                initial += ", " + vp.getValor();
                            }
                        }
                    }
                } else if (campo instanceof TextBox) {

                    if (campo.getExpresionValoresPosibles() != null
                            && campo.getExpresionValoresPosibles().trim().length() > 0) {
                        Object calculat = CampoUtils.calcularValorDefecto(campo, variables);
                        if (calculat != null) initial = calculat.toString();
                    } else {
                        TraValorPosible traVp = ((TraValorPosible) ((TextBox) campo).getValorPosible().getTraduccion());
                        if (traVp != null) initial = traVp.getEtiqueta();
                    }


                } else { // !CheckBox && !ListBox && !TextBox -> ComboBox || RadioButton

                    // campo.isIndexed
                    tipoValor = "java.lang.String";

                    for (int j = 0; j < valoresPosibles.size(); j++) {
                        ValorPosible vp = (ValorPosible) valoresPosibles.get(j);
                        if (vp.isDefecto()) {
                            initial = vp.getValor();
                            break;
                        }
                    }
                }

                Class clazz = getClass(tipoValor);
                Object value = toValue(initial, clazz);

                valores.put(key, value);
                variables.put("f_" + key, value);

            } catch (ClassNotFoundException e) {
                log.error(e);
            }
        }
        return valores;
    }

    /**
     * Carga los valores por defecto de cada campo de una determianda pantalla del formulario telemático.
     * @param pantalla pantalla en curso del formulario.
     * @param doc documento xml con los valores por defecto de los campos de la pantalla.
     * @param variables Map con los valores de los campos rellenados (las llaves són f_xxx
     * para los campos de la misma pantalla y f_yyy_xxx para los campo de la pantalla yyy.)
     * @param nombreAtributo es el nombre del atributo con el indice a seleccionar en un campo de tipo indexado.
     * @return valores iniciales de los campos de la pantalla.
     */
    public static Map valoresDefecto(Pantalla pantalla, Document doc, Map variables, String nombreAtributo) {
        List campos = pantalla.getCampos();
        Map valores = new HashMap(campos.size());
        //valorEtiqueta mantiene, para los campos indexados, el valor asociado a cada índice seleccionado.
        Object valorEtiqueta = null;
        boolean hasText;
        for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
            try {
                hasText = false;
                Campo campo = (Campo) iterator.next();
                String key = campo.getNombreLogico();
                
                if (campo.getExpresionValoresPosibles() != null &&
                    campo.getExpresionValoresPosibles().trim().length() > 0) {
                    CampoUtils.calcularValoresPosibles(campo, variables);
                }
                
                boolean autorrellenable = false;
                List autorelleno = null;
                if (campo.getExpresionAutorellenable() != null &&
                    campo.getExpresionAutorellenable().trim().length() > 0) {
                		autorrellenable = true;
                    	autorelleno = CampoUtils.calcularAutorrellenable(campo,variables);
                }
                
                
                String xpath = campo.getEtiquetaPDF();
                boolean valorDesdeXML = (xpath != null) && (xpath.trim().length() > 0) &&
                        (doc != null) && (doc.selectSingleNode(xpath) != null);

                List valoresPosibles = campo.getAllValoresPosibles();
                String valIni = null;
                String tipoValor = campo.getTipoValor();
                if (campo instanceof CheckBox) {
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        valIni = doc.selectSingleNode(xpath).getText().trim();
                        if (!"true".equals(valIni)) valIni = "false";
                    } else if (autorrellenable) {   
                    	//valor por expresion de autorrelleno                    	
                    	valIni = (autorelleno.size()<=0?"false":(!((ValorPosible)autorelleno.get(0)).getValor().equals("true")?"false":"true"));                    	
                    }else{	
                        //valor por defecto definido en el mismo campo.
                        boolean defecto = ((CheckBox) campo).isValorDefecto();
                        valIni = String.valueOf(defecto);
                    }
                } else if (campo instanceof ListBox || campo instanceof TreeBox) {
                    tipoValor = "java.lang.String[]";
                    //Esta lista, mantiene los valores asociados a cada indice seleccionado en el campo.
                    ArrayList valoresTextIndex = new ArrayList();
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        List valoresInit = doc.selectNodes(xpath);
                        for (Iterator i = valoresInit.iterator(); i.hasNext();) {
                            Element element = (Element) i.next();
                            String vInicial = element.attributeValue(nombreAtributo);
                            for (Iterator j = valoresPosibles.iterator(); j.hasNext();) {
                                ValorPosible vp = (ValorPosible) j.next();
                                String vPosible = vp.getValor();
                                if (vPosible.equals(vInicial)) {
                                    if (valIni == null) {
                                        valIni = "";
                                    } else {
                                        valIni += ", ";
                                    }
                                    valIni += vPosible;
                                    //Almaceno el valor asociado al indice en curso, seleccionado por defecto.
                                    valoresTextIndex.add(((TraValorPosible) vp.getTraduccion()).getEtiqueta());
                                    hasText = true;
                                }
                            }
                        }
                        /*
                          Convertimos la lista a un array de objetos (Object[]) para que puede ser asignada valorEtiqueta que
                          es de tipo Object
                        */
                        valorEtiqueta = valoresTextIndex.toArray();
                    } else if (autorrellenable) {   
                    	//valor por expresion de autorrelleno                    	
                        for (Iterator i = autorelleno.iterator(); i.hasNext();) {
                            ValorPosible element = (ValorPosible) i.next();
                            String vInicial =element.getValor();
                            for (Iterator j = valoresPosibles.iterator(); j.hasNext();) {
                                ValorPosible vp = (ValorPosible) j.next();
                                String vPosible = vp.getValor();
                                if (vPosible.equals(vInicial)) {
                                    if (valIni == null) {
                                        valIni = "";
                                    } else {
                                        valIni += ", ";
                                    }
                                    valIni += vPosible;
                                    //Almaceno el valor asociado al indice en curso, seleccionado por defecto.
                                    valoresTextIndex.add(((TraValorPosible) vp.getTraduccion()).getEtiqueta());
                                    hasText = true;
                                }
                            }
                        }
                        /*
                          Convertimos la lista a un array de objetos (Object[]) para que puede ser asignada valorEtiqueta que
                          es de tipo Object
                        */
                        valorEtiqueta = valoresTextIndex.toArray();
                    	
                    } else {
                        //valor por defecto definido en el mismo campo
                        for (int j = 0; j < valoresPosibles.size(); j++) {
                            ValorPosible vp = (ValorPosible) valoresPosibles.get(j);
                            if (vp.isDefecto()) {
                                if (valIni == null) {
                                    valIni = "";
                                } else {
                                    valIni += ", ";
                                }
                                valIni += vp.getValor();
                                //Almaceno el valor asociado al indice en curso, seleccionado por defecto.
                                valoresTextIndex.add(((TraValorPosible) vp.getTraduccion()).getEtiqueta());
                            }
                        }
                        /*
                          Convertimos la lista a un array de objetos (Object[]) para que puede ser asignada valorEtiqueta que
                          es de tipo Object
                        */
                        valorEtiqueta = valoresTextIndex.toArray();
                    }
                } else if (campo instanceof TextBox) {
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        valIni = doc.selectSingleNode(xpath).getText().trim();

                    }else if (autorrellenable) {   
                    	//valor por expresion de autorrelleno                    	
                    	valIni = (autorelleno.size()<=0?"":((ValorPosible)autorelleno.get(0)).getValor());                    	
                    } else {
                        //valor por defecto definido en el mismo campo
                        String exprValPos = campo.getExpresionValoresPosibles();
                        if (exprValPos != null && exprValPos.trim().length() > 0) {
                            Object calculat = CampoUtils.calcularValorDefecto(campo, variables);
                            if (calculat != null) valIni = calculat.toString();
                        } else {
                            TraValorPosible traVp = ((TraValorPosible) ((TextBox) campo).getValorPosible().getTraduccion());
                            if (traVp != null) valIni = traVp.getEtiqueta();
                        }
                    }
                } else if ( (campo instanceof ComboBox) || (campo instanceof RadioButton) ) {
                    tipoValor = "java.lang.String";
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        valIni = ((Element) doc.selectSingleNode(xpath)).attributeValue(nombreAtributo);
                        for (Iterator i = valoresPosibles.iterator(); i.hasNext();) {
                            ValorPosible vp = (ValorPosible) i.next();
                            if (vp.getValor().equals(valIni)) {
                                vp.setDefecto(true);
                                //Almaceno el valor asociado al indice seleccionado por defecto en el campo.
                                valorEtiqueta = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                hasText = true;
                                break;
                            }
                        }

                    }else if (autorrellenable) {   
                    	//valor por expresion de autorrelleno                    	                    	
                    	valIni = (autorelleno.size()<=0?"":((ValorPosible)autorelleno.get(0)).getValor());  
                        for (Iterator i = valoresPosibles.iterator(); i.hasNext();) {
                            ValorPosible vp = (ValorPosible) i.next();
                            if (vp.getValor().equals(valIni)) {
                                vp.setDefecto(true);
                                //Almaceno el valor asociado al indice seleccionado por defecto en el campo.
                                valorEtiqueta = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                hasText = true;
                                break;
                            }
                        }                   	
                    } else {
                        //valor por defecto definido en el mismo campo
                        for (int j = 0; j < valoresPosibles.size(); j++) {
                            ValorPosible vp = (ValorPosible) valoresPosibles.get(j);
                            if (vp.isDefecto()) {
                                valIni = vp.getValor();
                                //Almaceno el valor asociado al indice seleccionado por defecto en el campo.
                                valorEtiqueta = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                hasText = true;
                                break;
                            }
                        }
                    }
                }
                
                //
                Class clazz = getClass(tipoValor);
                Object value = toValue(valIni, clazz);
                valores.put(key, value);
                /*
                  En los campos indexados, ademas de almacenar el valor de índice,
                  debo almacenar el texto asociado a ese índice.
                */
                if (hasText) {
                    valores.put(key + "_text", valorEtiqueta);
                }

                variables.put("f_" + key, value);
            } catch (ClassNotFoundException e) {
                log.error(e);
            }
        }
        return valores;
    }

    private static Class getClass(String name) throws ClassNotFoundException {
        boolean indexed = false;
        if (name.endsWith("[]")) {
            name = name.substring(0, name.length() - 2);
            indexed = true;
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = PantallaUtils.class.getClassLoader();
        }
        Class baseClass = cl.loadClass(name);


        if (indexed) {
            return Array.newInstance(baseClass, 0).getClass();
        } else {
            return baseClass;
        }
    }

    private static Object toValue(String initial, Class clazz) {
        Object value;
        try {
            if (initial != null) {
                value = ConvertUtils.convert(initial, clazz);
            } else {
                if (clazz.isArray()) {
                    value = Array.newInstance(clazz.getComponentType(), 0);
                } else {
                    value = clazz.newInstance();
                }
            }
        } catch (Throwable t) {
            log.warn("No es pot convertir '" + initial + "' a " + clazz.getName());
            value = null;
        }
        return (value);
    }

    // --- INDRA: LISTA ELEMENTOS ----
    /**
     * Realiza la carga desde el xml de los elementos de una lista de elementos
     */
    public static void valoresDefectoDetalles(Map valoresDetalles,Formulario formulario,Pantalla pantalla, Document doc, Map variables, String nombreAtributo) {
    	
    	
    	List campos = pantalla.getCampos();
        for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
        	Campo campo = (Campo) iterator.next();
        	if (!(campo instanceof ListaElementos)) continue;
        	
        	// Buscamos pantalla detalle asociada al campo
        	Pantalla p = null;
        	boolean enc=false;
        	String referencia = CampoUtils.getReferenciaListaElementos(pantalla.getNombre(),campo.getNombreLogico());
            for (Iterator it = formulario.getPantallas().iterator();it.hasNext();){
            	p = (Pantalla) it.next();
            	if (referencia.equals(p.getComponenteListaElementos())) {
            		enc = true;
            		break;
            	}
            }
            if (!enc) {
            	log.error("No se encuenta pantalla detalle asociada al campo " + campo.getNombreLogico());
            	continue;
            }
            
            // Cargamos elementos de la lista
        	List valoresLista = valoresDefectoDetalle(campo,p,doc,variables,nombreAtributo);   
        	valoresDetalles.put(referencia,valoresLista);        		
        }
    }	
    	
  
    /**
     * Realiza la carga desde el xml de los elementos de una lista de elementos
     */
    public static List valoresDefectoDetalle(Campo campoLista,Pantalla pantallaDetalle, Document doc, Map variables, String nombreAtributo) {
    	List listaElementos = new ArrayList();

		String xpath = campoLista.getEtiquetaPDF();
		if (StringUtils.isEmpty(xpath)) return listaElementos;
		
		List elementos = doc.selectNodes(xpath + "/*");
		String xpathElemento = xpath + "/ID";
		for (int i = 1;i<=elementos.size(); i++) {
			Map variablesScript = new HashMap();
			variablesScript.putAll(variables);
			Map datosElemento = valoresDefectoElementoDetalle(pantallaDetalle, doc, variablesScript, nombreAtributo, xpathElemento + i);
			listaElementos.add(datosElemento);
		}
		return listaElementos;    	
    }
   
   
    
    /**
	 * Realiza la carga desde el xml de un elemento de una lista de elementos
	 */
    private static Map valoresDefectoElementoDetalle(Pantalla pantalla, Document doc, Map variables, String nombreAtributo,String xpathElemento) {
        
    	List campos = pantalla.getCampos();
        Map valores = new HashMap(campos.size());
        //valorEtiqueta mantiene, para los campos indexados, el valor asociado a cada índice seleccionado.
        Object valorEtiqueta = null;
        boolean hasText;
        for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
            try {
                hasText = false;
                Campo campo = (Campo) iterator.next();
                String key = campo.getNombreLogico();
                
                if (campo.getExpresionValoresPosibles() != null &&
                    campo.getExpresionValoresPosibles().trim().length() > 0) {
                    CampoUtils.calcularValoresPosibles(campo, variables);
                }
                
                String xpath = (StringUtils.isEmpty(campo.getEtiquetaPDF())?campo.getEtiquetaPDF():xpathElemento+"/"+campo.getEtiquetaPDF());
                boolean valorDesdeXML = (xpath != null) && (xpath.trim().length() > 0) &&
                        (doc != null) && (doc.selectSingleNode(xpath) != null);

                List valoresPosibles = campo.getAllValoresPosibles();
                String valIni = null;
                String tipoValor = campo.getTipoValor();
                if (campo instanceof CheckBox) {
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        valIni = doc.selectSingleNode(xpath).getText().trim();
                        if (!"true".equals(valIni)) valIni = "false";
                    } else{	
                        //valor por defecto definido en el mismo campo.
                        boolean defecto = ((CheckBox) campo).isValorDefecto();
                        valIni = String.valueOf(defecto);
                    }
                } else if (campo instanceof ListBox || campo instanceof TreeBox) {
                    tipoValor = "java.lang.String[]";
                    //Esta lista, mantiene los valores asociados a cada indice seleccionado en el campo.
                    ArrayList valoresTextIndex = new ArrayList();
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        List valoresInit = doc.selectNodes(xpath);
                        for (Iterator i = valoresInit.iterator(); i.hasNext();) {
                            Element element = (Element) i.next();
                            String vInicial = element.attributeValue(nombreAtributo);
                            for (Iterator j = valoresPosibles.iterator(); j.hasNext();) {
                                ValorPosible vp = (ValorPosible) j.next();
                                String vPosible = vp.getValor();
                                if (vPosible.equals(vInicial)) {
                                    if (valIni == null) {
                                        valIni = "";
                                    } else {
                                        valIni += ", ";
                                    }
                                    valIni += vPosible;
                                    //Almaceno el valor asociado al indice en curso, seleccionado por defecto.
                                    valoresTextIndex.add(((TraValorPosible) vp.getTraduccion()).getEtiqueta());
                                    hasText = true;
                                }
                            }
                        }
                        //Convertimos la lista a un array de objetos (Object[]) para que puede ser asignada valorEtiqueta que
                        // es de tipo Object
                        valorEtiqueta = valoresTextIndex.toArray();
                    } else {
                        //valor por defecto definido en el mismo campo
                        for (int j = 0; j < valoresPosibles.size(); j++) {
                            ValorPosible vp = (ValorPosible) valoresPosibles.get(j);
                            if (vp.isDefecto()) {
                                if (valIni == null) {
                                    valIni = "";
                                } else {
                                    valIni += ", ";
                                }
                                valIni += vp.getValor();
                                //Almaceno el valor asociado al indice en curso, seleccionado por defecto.
                                valoresTextIndex.add(((TraValorPosible) vp.getTraduccion()).getEtiqueta());
                            }
                        }
                        // Convertimos la lista a un array de objetos (Object[]) para que puede ser asignada valorEtiqueta que
                        // es de tipo Object
                        valorEtiqueta = valoresTextIndex.toArray();
                    }
                } else if (campo instanceof TextBox) {
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        valIni = doc.selectSingleNode(xpath).getText().trim();
                    }else {
                        //valor por defecto definido en el mismo campo
                        String exprValPos = campo.getExpresionValoresPosibles();
                        if (exprValPos != null && exprValPos.trim().length() > 0) {
                            Object calculat = CampoUtils.calcularValorDefecto(campo, variables);
                            if (calculat != null) valIni = calculat.toString();
                        } else {
                            TraValorPosible traVp = ((TraValorPosible) ((TextBox) campo).getValorPosible().getTraduccion());
                            if (traVp != null) valIni = traVp.getEtiqueta();
                        }
                    }
                } else if ( (campo instanceof ComboBox) || (campo instanceof RadioButton) ) {
                    tipoValor = "java.lang.String";
                    if (valorDesdeXML) {
                        //valor por defecto definido desde documento xml.
                        valIni = ((Element) doc.selectSingleNode(xpath)).attributeValue(nombreAtributo);
                        for (Iterator i = valoresPosibles.iterator(); i.hasNext();) {
                            ValorPosible vp = (ValorPosible) i.next();
                            if (vp.getValor().equals(valIni)) {
                                vp.setDefecto(true);
                                //Almaceno el valor asociado al indice seleccionado por defecto en el campo.
                                valorEtiqueta = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                hasText = true;
                                break;
                            }
                        }

                    }else {
                        //valor por defecto definido en el mismo campo
                        for (int j = 0; j < valoresPosibles.size(); j++) {
                            ValorPosible vp = (ValorPosible) valoresPosibles.get(j);
                            if (vp.isDefecto()) {
                                valIni = vp.getValor();
                                //Almaceno el valor asociado al indice seleccionado por defecto en el campo.
                                valorEtiqueta = ((TraValorPosible) vp.getTraduccion()).getEtiqueta();
                                hasText = true;
                                break;
                            }
                        }
                    }
                }
                
                //
                Class clazz = getClass(tipoValor);
                Object value = toValue(valIni, clazz);
                valores.put(key, value);
                // En los campos indexados, ademas de almacenar el valor de índice,
                // debo almacenar el texto asociado a ese índice.
                if (hasText) {
                    valores.put(key + "_text", valorEtiqueta);
                }

                variables.put("f_" + key, value);
            } catch (ClassNotFoundException e) {
                log.error(e);
            }
        }
        return valores;
    }
     
    // --- INDRA: LISTA ELEMENTOS ----
    
}
