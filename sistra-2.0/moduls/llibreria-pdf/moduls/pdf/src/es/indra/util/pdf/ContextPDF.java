package es.indra.util.pdf;

import java.awt.Color;
import java.util.HashMap;

import com.lowagie.text.Font;

public class ContextPDF {
	
	private HashMap fonts = new HashMap();
	
	private HashMap colors = new HashMap();
	
	
	public Font getDefaultFont()
	{
		return getFont(Font.HELVETICA,10,Font.NORMAL);
	}
	
	public Font getFont(int baseFont, float size, int style)
	{
		String key = Integer.toString(baseFont) + "_" + Float.toString(size) + "_" + Integer.toString(style);
		if(fonts.containsKey(key))
		{
			return (Font)fonts.get(key);
		}
		Font font = new Font(baseFont, size, style);
		fonts.put(key,font);
		return font;
	}

	public Font getFont(String estilo)
	{
		if(fonts.containsKey(estilo))
		{
			return (Font)fonts.get(estilo);
		}
		return getDefaultFont();
	}
	
	public void initFonts()
	{
		Font font = new Font(Font.HELVETICA, 14, Font.NORMAL);
		fonts.put("Titulo1", font);
		font = new Font(Font.HELVETICA, 10, Font.BOLD);
		fonts.put("Titulo2", font);
		font = new Font(Font.HELVETICA, 10, Font.NORMAL);
		fonts.put("Normal", font);
		font = new Font(Font.HELVETICA, 9, Font.NORMAL);
		fonts.put("Arial9", font);
		font = new Font(Font.HELVETICA, 9, Font.BOLD);
		fonts.put("Arial9Bold", font);
		font = new Font(Font.HELVETICA, 11, Font.NORMAL);
		fonts.put("Arial11", font);
		font = new Font(Font.HELVETICA, 11, Font.BOLD);
		fonts.put("Arial11Bold", font);
		font = new Font(Font.HELVETICA, 8, Font.NORMAL);
		fonts.put("Arial8", font);		
		font = new Font(Font.HELVETICA, 8, Font.BOLD);
		fonts.put("Arial8Bold", font);

		
	}

	public Color getDefaultColor()
	{
		return getColor(0xff,0xff,0xff);
	}
	
	public Color getColor(int r, int g, int b)
	{
		String key = Float.toString(r) + "_" + Float.toString(g) + "_" + Float.toString(b);
		if(colors.containsKey(key))
		{
			return (Color)colors.get(key);
		}
		Color color = new Color(r,g,b);
		colors.put(key,color);
		return color;
	}	

}
