package org.ibit.rol.form.front.action;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.Captcha.Builder;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.text.producer.TextProducer;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.ibit.rol.form.front.registro.RegistroManager;
import org.ibit.rol.form.front.util.CaptchaTextProducer;
import org.ibit.rol.form.persistence.delegate.InstanciaDelegate;

/**
 * @struts.action 
 * 	path="/captchaDownload" 
 * 	scope="request" 
 * 	validate="false"
 */
public class CaptchaDownloadAction extends Action {
	private static Log _log = LogFactory.getLog(CaptchaDownloadAction.class);
	private static int _width = 120;
	private static int _height = 50;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Obtenemos nombre campo captcha
		String nomCampoCaptcha = request.getParameter("fieldName");

		// Obtenemos valor captcha
		InstanciaDelegate delegate = RegistroManager
				.recuperarInstancia(request);
		String valorCampoCaptcha = delegate.obtenerCaptcha(nomCampoCaptcha);
		
		// Generamos imagen captcha
		final TextProducer tp = new CaptchaTextProducer(valorCampoCaptcha);
		final Builder builder = new nl.captcha.Captcha.Builder(_width, _height);
		final Captcha captcha = builder.addText(tp).gimp().addBorder().addNoise().addBackground().build();		
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(8192);
		ImageIO.write(captcha.getImage(), "png", bos);		
		String nombreFichero = "captcha.png";
		byte[] datosFichero = bos.toByteArray();
		bos.close();

		// Volcamos imagen captcha en stream response
		ByteArrayInputStream bis = new ByteArrayInputStream(datosFichero);
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ nombreFichero + ";");
			IOUtils.copy(bis, response.getOutputStream());
		} catch (java.io.IOException exc) {
			_log.info("Client aborted");
		} catch (Exception exc) {
			_log.error(exc);
		} finally {
			try {
				if (!response.isCommitted())
					response.flushBuffer();
			} catch (Exception ex) {
				_log.error(ex);
			}
			if (bis != null)
				bis.close();
		}

		return null;
	}

}