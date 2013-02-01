package org.ibit.rol.form.front.action.telematic;

import org.apache.struts.action.ActionForm;

/**
 * @struts.form name="iniciTelematicForm"
 */
public class IniciTelematicForm extends ActionForm {

    String xmlData;
    String xmlConfig;
    
    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public String getXmlConfig() {
        return xmlConfig;
    }

    public void setXmlConfig(String xmlConfig) {
        this.xmlConfig = xmlConfig;
    }
}
