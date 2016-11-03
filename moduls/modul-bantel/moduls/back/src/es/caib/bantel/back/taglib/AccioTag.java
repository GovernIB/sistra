package es.caib.bantel.back.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * Tag per mostrar submits de tipus CRUD:
 *  "alta, baixa, modificar, tornar ... ".
 * 
 * @author Antoni Reus
 * 
 * Basat els tags de struts CancelTag de Jeff Hutchinson
 * i MessageTag de Craig R. McClanahan.
 * 
 * <p>El tipus ha d'esser: alta, baixa, modificar o tornar.</p>
 * <p>L'ordre per pintar l'etiqueta del bot&oacute; &eacute;s:
 * <ol>
 *  <li>Atribut value</li>
 *  <li>Body del tag</li> 
 *  <li>Etiqueta 'boton.<i>tipo</i>'</li>
 * </ol>
 * </p>  
 * 
 * @jsp.tag
 *  name="accio"
 *  body-content="JSP"  
 */
public class AccioTag extends BaseHandlerTag {

    /** Propietat que s'enviara */
    protected String tipus = null;

    /** Etiqueta de l'input */
    protected String value = null;

    /** clau de l'atribut de contexte amb els recursos */
    protected String bundle = Globals.MESSAGES_KEY;

    /** clau de l'atribut de sessi&oacute; que guarda el locale  */
    protected String localeKey = Globals.LOCALE_KEY;

    /** Variable d'utilitat per agafar el texte del contingut */
    private String text = null;
    
    
    // -------------- getters & setters
    
    /** 
     * @jsp.attribute
     *  required="true"
     *  rtexprvalue="true"   
     */
    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getLocale() {
        return localeKey;
    }

    public void setLocale(String localeKey) {
        this.localeKey = localeKey;
    }

    // --------- Getters sobreescrits per taglib

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getAccesskey() {
        return super.getAccesskey();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getTabindex() {
        return super.getTabindex();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public boolean getIndexed() {
        return super.getIndexed();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnclick() {
        return super.getOnclick();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOndblclick() {
        return super.getOndblclick();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnmousedown() {
        return super.getOnmousedown();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnmouseup() {
        return super.getOnmouseup();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnmousemove() {
        return super.getOnmousemove();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnmouseover() {
        return super.getOnmouseover();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnmouseout() {
        return super.getOnmouseout();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnkeydown() {
        return super.getOnkeydown();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnkeyup() {
        return super.getOnkeyup();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnkeypress() {
        return super.getOnkeypress();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnchange() {
        return super.getOnchange();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnselect() {
        return super.getOnselect();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnblur() {
        return super.getOnblur();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getOnfocus() {
        return super.getOnfocus();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public boolean getDisabled() {
        return super.getDisabled();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public boolean getReadonly() {
        return super.getReadonly();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getStyle() {
        return super.getStyle();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getStyleClass() {
        return super.getStyleClass();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getStyleId() {
        return super.getStyleId();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getAlt() {
        return super.getAlt();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getAltKey() {
        return super.getAltKey();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getTitle() {
        return super.getTitle();
    }

    /** 
     * @jsp.attribute
     *  required="false"
     *  rtexprvalue="true"   
     */
    public String getTitleKey() {
        return super.getTitleKey();
    }
    
    // --------- M&egrave;todes publics del tags
    
    /**
     * Processar començament del tag
     * @exception JspException Si es produeix una excepci&oacute JSP
     */
    public int doStartTag() throws JspException {
        this.text = null;
        return (EVAL_BODY_BUFFERED);
    }

    /**
     * Guarda l'etiqueta si s'ha especificat al bodycontent
     * @exception JspException Si es produeix una excepci&oacute JSP
     */
    public int doAfterBody() throws JspException {
        if (bodyContent != null) {
            String value = bodyContent.getString().trim();
            if (value.length() > 0) {
                text = value;
            }
        }
        return (SKIP_BODY);
    }

    /**
     * Pinta el tag
     * @exception JspException Si es produeix una excepci&oacute JSP
     */
    public int doEndTag() throws JspException {

        // Obtenir etiqueta
        String label = value; // 1. Agafar value
        if ((label == null) && (text != null)) {
            label = text; // 2. Agafar content
        }
        if ((label == null) || (label.trim().length() < 1)) {
            String key = "boton." + tipus;     // 3. Intentar etiqueta "boton.<tipus>"
            label = RequestUtils.message(pageContext, this.bundle, this.localeKey, key);
        }
                
        // Generar HTML
        StringBuffer results = new StringBuffer();
        results.append("<input type=\"submit\"");
        results.append(" name=\"");
        results.append(property(tipus));
        results.append("\"");
        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }
        results.append(" value=\"");
        results.append(label);
        results.append("\"");
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(getElementClose());

        // Treure el contingut
        ResponseUtils.write(pageContext, results.toString());

        return (EVAL_PAGE);

    }

    /** Alliberar recursos */
    public void release() {
        super.release();
        tipus = null;
        text = null;
        value = null;
        bundle = Globals.MESSAGES_KEY;
        localeKey = Globals.LOCALE_KEY;
    }

    /** Retorna la clau del missatge que correspon al tipus de boto especificat */
    protected String property(String tipus) throws JspException {
        if ("alta".equals(tipus)) {
            return Constants.ALTA_PROPERTY;
        } else if ("baixa".equals(tipus)) {
            return Constants.BAIXA_PROPERTY;
        } else if ("modificacio".equals(tipus)) {
            return Constants.MODIFICACIO_PROPERTY;
        } else if ("seleccio".equals(tipus)) {
            return Constants.SELECCIO_PROPERTY;
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("Error en AccioTag, el tipo debe ser: ");
            sb.append("'alta', 'baixa', 'modificacio', 'seleccio' ò 'tornar'");
            throw new JspException(sb.toString());
        }
    }
}