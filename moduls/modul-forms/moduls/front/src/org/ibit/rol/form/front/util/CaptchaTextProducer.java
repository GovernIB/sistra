package org.ibit.rol.form.front.util;

import nl.captcha.text.producer.TextProducer;

/**
 * 
 * Captcha text producer.
 * 
 * @author Indra
 * 
 */
public class CaptchaTextProducer implements TextProducer {

    private final String key;

    public CaptchaTextProducer(final String pKey) {
        key = pKey;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.captcha.text.producer.TextProducer#getText()
     */
    public String getText() {
        return key;
    }

}
