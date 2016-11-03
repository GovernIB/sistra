package org.ibit.rol.form.front.action.expresion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.action.BaseAction;
import org.ibit.rol.form.front.action.PantallaForm;
import org.ibit.rol.form.front.json.JSONArray;
import org.ibit.rol.form.front.json.JSONObject;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.model.TraValorPosible;
import org.ibit.rol.form.model.ValorPosible;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Calcula la expresió d'autocalcul d'un camp de la pantalla actual.
 * El resultat es torna com a text pla, per emprar amb objectes XMLHttpRequest
 * desde javascript.
 * @struts.action
 *  name="pantallaForm"
 *  path="/expresion/valores"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/expresion/valores"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/secure/expresion/valores"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="pantallaForm"
 *  path="/auth/secure/expresion/valores"
 *  scope="request"
 *  validate="false"
 *
 */
public class CalcularValoresAction extends BaseAction {

    protected static Log log = LogFactory.getLog(CalcularValoresAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

    	request.setCharacterEncoding("utf-8");
    	
        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) return null;

        String fieldName = request.getParameter("fieldName");
        if (fieldName == null || fieldName.trim().length() == 0) {
            return null;
        }

        PantallaForm pantallaForm = (PantallaForm) form;
        delegate.introducirDatosPantalla(pantallaForm.getMap());

        List valores = delegate.expresionValoresPosiblesCampo(fieldName);
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
                array.put(object);
            }

            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().print(array.toString());
        }
        return null;
    }
}
