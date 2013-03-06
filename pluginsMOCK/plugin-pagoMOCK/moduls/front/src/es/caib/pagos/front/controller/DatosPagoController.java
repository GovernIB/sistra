package es.caib.pagos.front.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.tiles.ComponentContext;

import es.caib.pagos.persistence.delegate.SesionPagoDelegate;
import es.caib.sistra.plugins.pagos.DatosPago;


public class DatosPagoController extends BaseController
{

	public void execute(ComponentContext tileContext,
			HttpServletRequest request, HttpServletResponse response,
			ServletContext servletContext) throws Exception
	{
		// Prepara los datos del pago en el formato correcto		
		SesionPagoDelegate dlg = getSesionPago(request);
		
		// Obtenemos datos del pago
		DatosPago datosPago = dlg.obtenerDatosPago();
		
		// Importe (convertimos de cents)
		double impDec = Double.parseDouble(datosPago.getImporte()) / 100 ;	
		DecimalFormat f = (DecimalFormat) DecimalFormat.getInstance(getLocale(request));
		f.setDecimalSeparatorAlwaysShown(true);
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		f.setMinimumIntegerDigits(1);
		String importe = f.format(impDec);
				
		// Fecha (convertimos desde Date)
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");               
	    String fechaDevengo = sdf.format( datosPago.getFechaDevengo() );
	    
	   	// Redirigimos a pagina de pago
	    request.setAttribute("modelo",datosPago.getModelo());
	    request.setAttribute("concepto",datosPago.getConcepto());
	    request.setAttribute("fechaDevengo",fechaDevengo);
	    request.setAttribute("importe",importe);
	    request.setAttribute("urlRetornoSistra",dlg.obtenerUrlRetornoSistra());
		
	}

}
