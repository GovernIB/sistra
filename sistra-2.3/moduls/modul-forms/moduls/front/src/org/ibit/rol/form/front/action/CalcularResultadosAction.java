package org.ibit.rol.form.front.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;
import org.ibit.rol.form.persistence.conector.Result;
import org.ibit.rol.form.persistence.conector.FileResult;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.front.util.InstanciaZipCodec;
import org.ibit.rol.form.front.parser.ParserFactory;
import org.ibit.rol.form.front.parser.Parser;
import org.ibit.rol.form.model.InstanciaBean;
import org.ibit.rol.form.model.Archivo;
import org.ibit.rol.form.model.Formulario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * @struts.action
 *  name="finalizarForm"
 *  path="/calcularResultados"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/auth/calcularResultados"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/secure/calcularResultados"
 *  scope="request"
 *  validate="false"
 *
 * @struts.action
 *  name="finalizarForm"
 *  path="/auth/secure/calcularResultados"
 *  scope="request"
 *  validate="false"
 */
public class CalcularResultadosAction extends BaseAction {

    protected static Log log = LogFactory.getLog(CalcularResultadosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        InstanciaDelegate delegate = RegistroManager.recuperarInstancia(request);
        if (delegate == null) {
            ActionErrors errors = new ActionErrors();
            errors.add(null, new ActionError("errors.session"));
            saveErrors(request, errors);
            return mapping.findForward("fail");
        }

        if (isCancelled(request)) {
            if (request.getParameter("SAVE") != null) {
                // Es guardar
                InstanciaBean bean = delegate.obtenerInstanciaBean();

                // Metemos el bean en un array de bytes.
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                InstanciaZipCodec.encodeInstancia(bean, result);

                // Enviamos el resultado.
                sendFile(response, "saveform.zip", "application/octet-stream", result.toByteArray());
                return null;

            } else if (request.getParameter("DISCARD") != null) {

                RegistroManager.desregistrarInstancia(request);
                return mapping.findForward("main");

            } else { // Es tornar enrera.

                String pantallaAnterior = request.getParameter("PANTALLA_ANTERIOR");
                if (pantallaAnterior == null || pantallaAnterior.length() == 0) {
                    delegate.retrocederPantalla();
                } else {
                    delegate.retrocederPantalla(pantallaAnterior);
                }

                response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + "/ver.do"));
                response.flushBuffer();
                return null;
            }
        }

        Result[] resultados = delegate.ejecutarSalidas();

        if (resultados == null || resultados.length == 0) {
            // SI NO HAY RESULTADOS USAMOS COMPORTAMIENTO ANTERIOR CON PLANTILLA
            log.warn("No hay 'Salidas' configuradas, usando antiguo sistema de plantillas.");
            Formulario formulario = delegate.obtenerFormulario();
            Archivo plantilla = delegate.obtenerPlantilla();
            if (plantilla == null) {
                ActionErrors errors = new ActionErrors();
                errors.add(null, new ActionError("errors.plantilla"));
                saveErrors(request, errors);
                return mapping.findForward("fail");
            }

            Parser parser = ParserFactory.getParser(plantilla.getTipoMime());
            parser.load(plantilla.getDatos());
            Map dades = delegate.obtenerDatosCompletos();
            parser.populate(dades);

            if (formulario.getHasBarcode()) {
                log.debug("Preparar codigo de barras");
                String numJus = delegate.generarNumeroJustificante();
                String appid = getServlet().getServletContext().getInitParameter("barcode.appid");
                String format = getServlet().getServletContext().getInitParameter("barcode.format");
                String orgcode = getServlet().getServletContext().getInitParameter("barcode.orgcode");
                parser.addBarcode(
                        appid + format + orgcode + "0" + numJus,
                        formulario.getPosBarcodeX(),
                        formulario.getPosBarcodeY());
            }

            log.debug("Preparar salida");
            byte[] sortida = parser.write();

            final FileResult fileResult = new FileResult();
            fileResult.setContentType(parser.contentType());
            fileResult.setBytes(sortida);
            fileResult.setLength(sortida.length);
            fileResult.setName(plantilla.getNombre());

            resultados = new Result[] { fileResult };
        }

        RegistroManager.guardarResultados(request, resultados);

        response.sendRedirect(prepareRedirectInstanciaURL(request, response, request.getAttribute("securePath") + "/resultados.do"));
        response.flushBuffer();
        return null;
    }
}
