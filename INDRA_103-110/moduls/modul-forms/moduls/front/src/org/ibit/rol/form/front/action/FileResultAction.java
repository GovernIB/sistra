package org.ibit.rol.form.front.action;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.conector.FileResult;
import org.ibit.rol.form.front.registro.RegistroManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @struts.action path="/fileresult"
 *
 * @struts.action path="/auth/fileresult"
 *
 * @struts.action path="/secure/fileresult"
 *
 * @struts.action path="/auth/secure/fileresult"
 */
public class FileResultAction extends BaseAction {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        Result[] resultados = RegistroManager.recuperarResultados(request);
        if (resultados == null) {
            return null;
        }

        String resultString = request.getParameter("result");
        if (resultString == null || resultString.length() == 0) {
            return null;
        }

        int result = Integer.parseInt(resultString);

        if (result > resultados.length) {
            return null;
        }

        if (!(resultados[result] instanceof FileResult)) {
            return null;
        }

        FileResult fileResult = (FileResult) resultados[result];

        response.reset();
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileResult.getName() + "\"");
        response.setContentType(fileResult.getContentType());
        response.setContentLength(fileResult.getLength());
        response.getOutputStream().write(fileResult.getBytes());

        return null;
    }

}
