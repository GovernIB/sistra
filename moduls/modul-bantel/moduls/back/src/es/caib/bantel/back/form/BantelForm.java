package es.caib.bantel.back.form;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.ValidatorForm;

import es.caib.bantel.back.action.TraFormBeanConfig;

public abstract class BantelForm extends ValidatorForm implements InitForm 
{
protected static Log log = LogFactory.getLog(BantelForm.class);
	
	private Serializable values;
	
	protected boolean inicialitat = false;
	
	protected String valuesClassName;
	
	public void init(ActionMapping mapping, HttpServletRequest request) 
	{
        log.debug("llamada a Init");
        log.debug(mapping.getName());
        if (!inicialitat) {
            ModuleConfig config = RequestUtils.getModuleConfig(request, getServlet().getServletContext());
            TraFormBeanConfig beanConfig = (TraFormBeanConfig) config.findFormBeanConfig(mapping.getName());
            valuesClassName = beanConfig.getValuesClassName();
            log.debug("valuesClassName=" + valuesClassName);
            inicialitat = true;
        }
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
        log.debug("llamada a Reset");
        log.debug(mapping.getName());
        init(mapping, request);
    }
	
	public void destroy(ActionMapping mapping, HttpServletRequest request) {
        log.debug("llamada a destroy " + mapping.getName());
        setValues(null);
        setPage(1);
    }

	public Serializable getValues() 
	{
		if (values == null) 
		{
            try 
            {
                values = (Serializable) RequestUtils.applicationInstance(valuesClassName);
            } catch (Throwable t) 
            {
                log.error("No se ha podido crear el value ", t);
                return null;
            }
        }
        return values;
	}

	public void setValues(Serializable values) 
	{
		this.values = values;
	}
	
		public String getValuesClassName() {
		return valuesClassName;
	}

	public void setValuesClassName(String valuesClassName) {
		this.valuesClassName = valuesClassName;
	}

}
