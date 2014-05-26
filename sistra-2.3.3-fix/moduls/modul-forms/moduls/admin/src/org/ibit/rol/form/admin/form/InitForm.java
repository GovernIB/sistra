package org.ibit.rol.form.admin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

/**
 * Para forms que necesitan un método init que llamara el request processor.
 */
public interface InitForm {

    public void init(ActionMapping mapping, HttpServletRequest request);

}
