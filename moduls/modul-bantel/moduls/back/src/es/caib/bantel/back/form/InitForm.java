package es.caib.bantel.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * Intefície que implementaran els forms
 * que necessitin un mètode d'inicialització.
 */
public interface InitForm {

    public void init(ActionMapping mapping, HttpServletRequest request);

}
