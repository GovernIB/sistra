package es.caib.sistra.admin.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class FicheroCuadernoForm extends ValidatorForm
{
	private Long codigoCuaderno;
	private Long codigoFichero;
	private transient FormFile fitxer;
	private boolean dominio = false;
	private boolean tramite = false;
	private boolean form = false;
	private static Pattern DOMINIO_PATTERN  = Pattern.compile( "^dominio-[\\w]{1,10}\\.xml$" );
	private static Pattern TRAMITE_PATTERN  = Pattern.compile( "^tramite-[\\w]{1,10}-[\\d]{1,2}\\.xml$" );
	private static Pattern FORM_PATTERN 	= Pattern.compile( "^form-[\\w]{1,3}\\.xml$" );
	
	
	public FormFile getFitxer()
	{
		return fitxer;
	}
	public void setFitxer(FormFile fitxer)
	{
		this.fitxer = fitxer;
	}
	public Long getCodigoCuaderno()
	{
		return codigoCuaderno;
	}
	public void setCodigoCuaderno(Long codigoCuaderno)
	{
		this.codigoCuaderno = codigoCuaderno;
	}
	public Long getCodigoFichero()
	{
		return codigoFichero;
	}
	public void setCodigoFichero(Long codigoFichero)
	{
		this.codigoFichero = codigoFichero;
	}
	public Long getCodigo()
	{
		return getCodigoCuaderno();
	}
	public void setCodigo( Long codigoCuaderno )
	{
		setCodigoCuaderno( codigoCuaderno );
	}
	public ActionErrors validate( ActionMapping mapping, HttpServletRequest request )
	{
	    ActionErrors errors = super.validate(mapping, request);
        if (errors == null) 
        {
            errors = new ActionErrors();
        }
		FormFile fitxer = this.getFitxer();
		// fichero no incluido
		if (  fitxer == null )
		{
			errors.add( "ficheroCuaderno.importar.fitxer", new ActionError("errors.ficheroCuaderno.importar.fitxer.vacio"  ) );
		}
		// validacion del nombre del fichero
		dominio = match( DOMINIO_PATTERN, fitxer.getFileName() );
		tramite = match( TRAMITE_PATTERN, fitxer.getFileName() );
		form = match( FORM_PATTERN, fitxer.getFileName() );
		if ( ! ( dominio || tramite || form ) )
		{
			errors.add( "ficheroCuaderno.importar.fitxer", new ActionError("errors.ficheroCuaderno.importar.fitxer.nombreFicheroNoAdecuado"  ) );
		}
		return errors;
	}
	
	private boolean match( Pattern pattern, String input )
	{
		Matcher matcher = pattern.matcher( input );
		return matcher.matches();
	}
	
	
	public static void main( String args[] )
	{
		
		Matcher matcher = DOMINIO_PATTERN.matcher( "dominio-XXX.xml" );
		System.out.println( matcher.matches() );
		
		matcher = TRAMITE_PATTERN.matcher( "tramite-TST_BTENSI-1.xml" );
		System.out.println( matcher.matches() );
		
		matcher = FORM_PATTERN.matcher( "form-061.xml" );
		System.out.println( matcher.matches() );
	}
	
	public boolean isDominio()
	{
		return dominio;
	}
	public boolean isForm()
	{
		return form;
	}
	public boolean isTramite()
	{
		return tramite;
	}
	
	
}
