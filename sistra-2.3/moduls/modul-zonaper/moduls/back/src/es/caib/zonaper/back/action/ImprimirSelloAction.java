package es.caib.zonaper.back.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.lowagie.text.Font;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import es.caib.util.StringUtil;
import es.caib.zonaper.back.Constants;
import es.caib.zonaper.back.form.ImprimirSelloForm;
import es.caib.zonaper.model.EntradaPreregistro;
import es.caib.zonaper.model.PeticionImpresionSello;
import es.caib.zonaper.persistence.delegate.DelegateUtil;
import es.caib.zonaper.persistence.delegate.EntradaPreregistroDelegate;
import es.indra.util.pdf.ImageStamp;
import es.indra.util.pdf.ObjectStamp;
import es.indra.util.pdf.UtilPDF;

/**
 * @struts.action
 *  name="imprimirSelloForm"
 *  path="/imprimirSello"
 *  scope="request"
 *  validate="false"
 *  
 * @struts.action-forward
 *  name="inicio" path="/init.do"
 *
 * @struts.action-forward
 *  name="fail" path=".error"
 */
public class ImprimirSelloAction extends BaseAction
{
	
	private Log log = LogFactory.getLog( ImprimirSelloAction.class );
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception 
    {
		ImprimirSelloForm formulario = ( ImprimirSelloForm ) form;
		
		// Cogemos la petición de la sesion y la borramos xa que no se reimprima
		if (request.getSession().getAttribute(formulario.getCodigo()) == null){
			return mapping.findForward( "inicio" );
		}
		PeticionImpresionSello pi = (PeticionImpresionSello) request.getSession().getAttribute(formulario.getCodigo());
			
		EntradaPreregistroDelegate delegate = DelegateUtil.getEntradaPreregistroDelegate();
		EntradaPreregistro preregistro	= delegate.obtenerEntradaPreregistro( pi.getCodigoEntradaPreregistro() );
		
		String numeroRegistro 	= preregistro.getNumeroRegistro();
		Date fechaRegistro 		= preregistro.getFechaConfirmacion();
		
		String strFechaRegistro = StringUtil.timestampACadena( fechaRegistro );
		
		String descOficina = DelegateUtil.getDominiosDelegate().obtenerDescripcionSelloOficina(pi.getCodigoOficinaRegistro());
		if ( descOficina == null )
		{
			this.setMessage( request, "errors.descSelloOficinaVacio" );
			return mapping.findForward( "fail" );
		}
		
		List arlParametrosExpansion = new ArrayList();
		arlParametrosExpansion.add( StringUtil.escapeBadCharacters( descOficina ) );
		arlParametrosExpansion.add( StringUtil.escapeBadCharacters( numeroRegistro ));
		arlParametrosExpansion.add( StringUtil.escapeBadCharacters( strFechaRegistro ) );
		String nombreFichero = StringUtil.expansion( Constants.NOMBRE_FICHERO_SELLO,  arlParametrosExpansion );
		
		// Volcamos fichero en stream response		
		ByteArrayInputStream bis = new ByteArrayInputStream(stampSelloVacio( descOficina, numeroRegistro, strFechaRegistro ));
		try{
			response.reset();					
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment; filename="+ nombreFichero + ";");
            copy(bis,response.getOutputStream());
        }finally{
            if(bis != null)
            	bis.close();
        }
		
        return null;
    }
	
	public byte[] stampSelloVacio( String oficina, String numeroRegistro, String fechaRegistro ) throws Exception
	{
		log.debug("Stamp inicio ...");
		
		ObjectStamp textos [] = new ObjectStamp[1];
	
		String selloOrganismo = DelegateUtil.getConfiguracionDelegate().obtenerConfiguracion().getProperty("organismo.sello");
		
		byte [] sello = generarSelloRegistro(selloOrganismo,											  
				oficina,
				"REGISTRE: ENTRADES",
				"Núm: " + numeroRegistro,
				"Data: " + fechaRegistro );
			
		textos[0] = new ImageStamp();
		((ImageStamp) textos[0]).setImagen(sello);		
		textos[0].setPage(0);
		textos[0].setX(370);
		textos[0].setY(720);		
		textos[0].setOverContent(true);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		UtilPDF.stamp(baos,new ByteArrayInputStream( this.obtenerBlankPdf().toByteArray() ),textos);

		log.debug("Stamp finalizado");
		
		return baos.toByteArray();


	}
	
	private byte [] generarSelloRegistro(String organismo,String puntoRegistro,String tipoRegistro,String numeroRegistro,String fechaRegistro) throws Exception{
        // Crear Imagen
        int width=200, height=80;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        // Objetener el contexto de dibujo
        Graphics g = image.getGraphics();

        // Rellenar el fondo        
        // g.setColor(Color.RED);
        // g.drawRect(0, 0, width, height);
        //g.setColor(new Color(223,223,223));
        g.setColor(Color.WHITE);
        //g.fillRect(1, 1, width-2, height-2);
        g.fillRect(0, 0, width, height);
        
        int saltoLinea = 15;
        int linea = 1;
        int x = 10;
        g.setColor(Color.RED);
        g.setFont(new java.awt.Font("TimesRoman",Font.BOLD,10));
        g.drawString(organismo,x,linea=linea+saltoLinea);
        g.drawString(puntoRegistro,x,linea=linea+saltoLinea);
        g.drawString(tipoRegistro,x,linea=linea+saltoLinea);
        g.drawString(numeroRegistro,x,linea=linea+saltoLinea);
        g.drawString(fechaRegistro,x,linea=linea+saltoLinea);
                        
        // Liberar el contexto
        g.dispose();

        // Enviar el fondo de la imagen
        ByteArrayOutputStream bos = new ByteArrayOutputStream();                
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
        encoder.encode(image);
        
        return bos.toByteArray();
        
	}
	
	private ByteArrayOutputStream obtenerBlankPdf() throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		UtilPDF.generateBlankPdf( baos );
		return baos;
	}
	
	private int copy(InputStream input, OutputStream output) throws IOException{
		byte buffer[] = new byte[4096];
	    int count = 0;
	    for(int n = 0; -1 != (n = input.read(buffer));)
	    {
	        output.write(buffer, 0, n);
	        count += n;
	    }
	
	    return count;
	}	
}
