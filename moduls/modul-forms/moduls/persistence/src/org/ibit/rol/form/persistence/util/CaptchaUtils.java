package org.ibit.rol.form.persistence.util;

import java.security.SecureRandom;

public class CaptchaUtils {

	/**
	 * Letras que pueden formar parte del captcha.
	 */
	private static final String[] LETRAS = { "A", "B", "C", "D", "E", "F", "G",
			"H", "J", "K", "L", "P", "R", "S", "T", "U", "X" };

	/**
	 * Genera captcha.
	 * @return captcha
	 */
	public static String generateCaptcha() {
		final SecureRandom sr = new SecureRandom();
		final StringBuffer sb = new StringBuffer(10);
		for (int i = 0; i < 4; i++) {
			final int numChar = sr.nextInt(LETRAS.length);
			sb.append(LETRAS[numChar]);
		}
		return sb.toString();
	}

}
