package org.ibit.rol.form.front.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.struts.util.ResponseUtils;
import org.ibit.rol.form.front.json.JSONArray;
import org.ibit.rol.form.front.json.JSONObject;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.ValorPosible;

/**
 * Classe de utilidad para generación de javascript.
 */
public final class JSUtil {

    private static String stringPattern = "var {0} = ''{1}'';\n";
    private static String intPattern = "var {0} = parseInt({1});\n";
    private static String floatPattern = "var {0} = parseFloat({1});\n";
    private static String anyPattern = "var {0} = {1};\n";
    private static String nullPattern = "var {0} = null;\n";

    private static String arrayPattern = "var {0} = new Array();\n";
    private static String pushStringPattern = "{0}.push(''{1}'');\n";
    private static String pushIntPattern = "{0}.push(parseInt({1}));\n";
    private static String pushFloatPattern = "{0}.push(parseFloat({1}));\n";
    private static String pushAnyPattern = "{0}.push({1});\n";

    public JSUtil() {
    }

    private static String declareVar(String name, Object value) {

        if (value != null && value.getClass().isArray()) {
            return declareVar(name, (Object[]) value);
        }

        String pattern = anyPattern;
        String strValue = null;

        if (value == null) {
            pattern = nullPattern;
        } else if (value instanceof String) {
            strValue = (String) value;
            if (strValue.indexOf('\n') != -1) {
                if (strValue.indexOf("\r\n") != -1) {
                    return declareVar(name, StringUtils.split(strValue, "\r\n"));
                } else {
                    return declareVar(name, StringUtils.split(strValue, "\n"));
                }
            } else {
                pattern = stringPattern;
            }
        } else if (value instanceof Number) {
            strValue = value.toString();
            if (value instanceof Float || value instanceof Double) {
                pattern = floatPattern;
            } else {
                pattern = intPattern;
            }
        } else {
            strValue = value.toString();
        }

        if (strValue != null) {
          //  INDRA: BUG HAY QUE HACER EL ESCAPE DE COMILLAS SIMPLE 		
          //  strValue = ResponseUtils.filter(strValue);  El filter cambia ' por la entidad html (#--;)
        	strValue = StringUtils.replace(strValue, "\\", "\\\\");
        	strValue = StringUtils.replace(strValue, "'", "\\'");
        }

        return MessageFormat.format(pattern, new Object[]{name, strValue});
    }

    private static String declareVar(String name, Object[] values) {
        StringBuffer sb = new StringBuffer();

        sb.append(MessageFormat.format(arrayPattern, new Object[]{name}));
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];


            String pattern = pushAnyPattern;
            String strValue;

            if (value == null) {
                pattern = pushStringPattern;
                strValue = "";
            } else if (value instanceof String) {
                strValue = ((String) value).trim();
                pattern = pushStringPattern;
            } else if (value instanceof Number) {
                strValue = value.toString();
                if (value instanceof Float || value instanceof Double) {
                    pattern = pushFloatPattern;
                } else {
                    pattern = pushIntPattern;
                }
            } else {
                strValue = value.toString().trim();
            }

            // INDRA: BUG HAY QUE HACER EL ESCAPE DE COMILLAS SIMPLE
            if (strValue != null) {
              strValue = StringUtils.replace(strValue, "\\", "\\\\");
	          strValue = StringUtils.replace(strValue, "'", "\\'");
	        }
            
            sb.append(MessageFormat.format(pattern, new Object[]{name, strValue}));
        }
        return sb.toString();
    }

    public static String declareVarMap(Map vars) {
        StringBuffer sb = new StringBuffer();

        for (Iterator iterator = vars.keySet().iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            Object value = vars.get(name);
            sb.append(declareVar(name, value));
        }

        return sb.toString();
    }

    public static String escapeJsExpression(String expression) {
        if (!expression.startsWith("eval(")) {
            expression = StringUtils.replace(expression, "\"", "\\\"");
            expression = "eval(\"" + expression + "\")";
        }

        if (expression.indexOf('\n') != -1) {
            String split = "\n";
            if (expression.indexOf("\r\n") != -1) {
                split = "\r\n";
            }
            String[] lines = StringUtils.split(expression, split);
            // INDRA: BUG
            //expression = StringUtils.join(lines, " \\\n");
            expression = StringUtils.join(lines, " \n");
        }

        return expression;
    }

    public static String escapeAndJoinJsExpression(String expression) {
        if (!expression.startsWith("eval(")) {
            expression = StringUtils.replace(expression, "\"", "\\\"");
            expression = "eval(\"" + expression + "\")";
        }

        if (expression.indexOf('\n') != -1) {
            String split = "\n";
            if (expression.indexOf("\r\n") != -1) {
                split = "\r\n";
            }
            String[] lines = StringUtils.split(expression, split);
            expression = StringUtils.join(lines, " ");
        }

        return expression;
    }
    
    public static String valoresPosiblesToJS(List valores){    
	    if (valores != null) {
	        JSONArray array = new JSONArray();
	        for (int i = 0; i < valores.size(); i++) {
	            ValorPosible posible = (ValorPosible) valores.get(i);
	            JSONObject object = new JSONObject();
	            object.put("valor", posible.getValor());
	            
	            // INDRA: PARA TEXTBOX SERÍA NULO
	            TraValorPosible tvp = ((TraValorPosible) posible.getTraduccion());
	            object.put("etiqueta", (tvp==null?"":tvp.getEtiqueta()));
	            // FIN INDRA
	            
	            object.put("defecto", posible.isDefecto());
	            
	            // INDRA: GESTION TREE                
	            object.put("parentValor", posible.getParentValor());
	            
	            array.put(object);
	        }	
	        return "eval('" + StringUtils.replace(array.toString(), "'", "\\'") + "')";
	    }
	    return null;
    }
    
    
    /**
     * Dado un string devuelve el string normalizado (saltos línea, etc.) en formato 'string_normalizado'
     * 
     * @param s
     * @return
     */
    public static String escapeStringToJS(String expression){
	    /*
    	expression = StringUtils.replace(expression, "\\", "\\\\'");
    	expression = StringUtils.replace(expression, "'", "\\'");
    	expression = StringUtils.replace(expression, "\n", "\\n");
    	expression = StringUtils.replace(expression, "\r", "\\r");
    	*/
    	
    	return "'" + StringEscapeUtils.escapeJavaScript(expression) + "'";
    }
    
    /**
     * Dado una lista de strings devuelve el string normalizado (saltos línea, etc.) en formato 'eval('array') 
     * @param results
     * @return
     */
    public static String escapeStringArrayToJS(List results){
    	JSONArray array = new JSONArray();
        for (int i = 0; i < results.size(); i++) {
            array.put((String) results.get(i));
        }	
        String resultStr = "eval('" + StringEscapeUtils.escapeJavaScript(array.toString()) + "')";
        return resultStr;
    }
    
}
