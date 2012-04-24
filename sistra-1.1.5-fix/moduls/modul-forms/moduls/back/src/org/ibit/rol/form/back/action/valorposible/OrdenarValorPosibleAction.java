package org.ibit.rol.form.back.action.valorposible;

import org.ibit.rol.form.back.action.BaseAction;
import org.ibit.rol.form.back.form.ValorPosibleForm;
import org.ibit.rol.form.back.util.ComponenteConfig;
import org.ibit.rol.form.persistence.delegate.DelegateUtil;
import org.ibit.rol.form.persistence.delegate.ValorPosibleDelegate;
import org.ibit.rol.form.model.Componente;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action para ordenar Valores Posibles.
 *
 * @struts.action
 *  name="valorPosibleForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/valorposible/orden"
 *  parameter="orden"
 *
 * @struts.action
 *  name="valorPosibleForm"
 *  scope="session"
 *  validate="false"
 *  path="/back/valorposible/cambiaorden"
 *  parameter="cambio"
 *
 * @struts.action-forward
 *  name="success" path="/valorposible/orden.jsp"
 *
 * @struts.action-forward
 *  name="textbox" path=".textbox.editar"
 * @struts.action-forward
 *  name="label" path=".label.editar"
 * @struts.action-forward
 *  name="checkbox" path=".checkbox.editar"
 * @struts.action-forward
 *  name="filebox" path=".filebox.editar"
 * @struts.action-forward
 *  name="combobox" path=".combobox.editar"
 * @struts.action-forward
 *  name="listbox" path=".listbox.editar"
 * @struts.action-forward
 *  name="treebox" path=".treebox.editar"
 * @struts.action-forward
 *  name="radiobutton" path=".radiobutton.editar"
 *
 *  @struts.action-forward
 *  name="fail" path="/valorposible/orden.jsp"
 */
public class OrdenarValorPosibleAction extends BaseAction{
    protected static Log log = LogFactory.getLog(OrdenarValorPosibleAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        log.debug("Entramos en OrdenarValorPosible");
        ValorPosibleDelegate valorposibleDelegate = DelegateUtil.getValorPosibleDelegate();
        String param = mapping.getParameter();


        if (request.getParameter("idComponente") != null){
            Long idComponente = new Long(request.getParameter("idComponente"));
            ValorPosibleForm vpForm = (ValorPosibleForm) obtenerActionForm(mapping,request, "/back/valorposible/editar");
            vpForm.setIdComponente(idComponente);
            if (isCancelled(request)) {
                Componente comp = guardarComponente(mapping, request, idComponente);
                String tipo = ComponenteConfig.getTipo(comp);
                return mapping.findForward(tipo);
            }

            if ("cambio".equals(param)) {
                Long codigo1 = new Long(request.getParameter("codigo1"));
                Long codigo2 = new Long(request.getParameter("codigo2"));
                valorposibleDelegate.cambiarOrden(codigo1, codigo2);
                request.setAttribute("reloadMenu", "true");
            }
            request.setAttribute("idComponente", idComponente);
            request.setAttribute("valoresOptions", valorposibleDelegate.listarValoresPosiblesCampo(idComponente));

            return mapping.findForward("success");
        }

        return mapping.findForward("fail");
    }


}
