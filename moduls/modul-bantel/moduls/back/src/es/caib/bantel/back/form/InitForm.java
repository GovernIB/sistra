package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * Intef�cie que implementaran els forms
 * que necessitin un m�tode d'inicialitzaci�.
 */
public interface InitForm {

    public void init(ActionMapping mapping, HttpServletRequest request);

}
