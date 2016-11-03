package es.caib.redose.back.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.validator.ValidatorForm;
import es.caib.redose.back.action.TraduccionFormBeanConfig;
import es.caib.redose.model.Idioma;
import es.caib.redose.model.Traduccion;
import es.caib.redose.model.Traducible;


/**
 * Funcionalidad basica para validar un form de traducción.
 */
public class TraduccionValidatorForm extends ValidatorForm implements InitForm {

    protected static Log log = LogFactory.getLog(TraduccionValidatorForm.class);

    public TraduccionValidatorForm() {
        log.info("Instancia creada");
        setLang(Idioma.DEFAULT);
        setSelect(Idioma.DEFAULT);
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        log.info("llamada a Reset");
        log.info(mapping.getName());
        init(mapping, request);
        setSelect(Idioma.DEFAULT);
    }

    public void validaTraduccion(ActionMapping mapping, HttpServletRequest request) {
        int pagina = getPage();
        setPage(1);
        ActionErrors errors = validate(mapping, request);
        if (errors != null && !errors.isEmpty()) {
            setTraduccion(null);
        }
        setPage(pagina);
    }

    public void reloadLang() {
        if (getSelect() == null || getSelect().equals(Idioma.DEFAULT)) {
            setPage(1);
            setLang(Idioma.DEFAULT);
        } else {
            setLang(getSelect());
            setPage(0);
        }
    }

    protected String valuesClassName;

    protected String traduccionClassName;

    protected boolean inicialitat = false;

    public void init(ActionMapping mapping, HttpServletRequest request) {
        log.info("llamada a Init");
        log.info(mapping.getName());
        if (!inicialitat) 
        {
            ModuleConfig config = RequestUtils.getModuleConfig(request, getServlet().getServletContext());
            TraduccionFormBeanConfig beanConfig = (TraduccionFormBeanConfig) config.findFormBeanConfig(mapping.getName());
            valuesClassName = beanConfig.getValuesClassName();
            traduccionClassName = beanConfig.getTraduccionClassName();
            log.info("valuesClassName=" + valuesClassName);
            log.info("traduccionClassName=" + traduccionClassName);
            inicialitat = true;
        }
    }

    public void destroy(ActionMapping mapping, HttpServletRequest request) {
        log.info("llamada a destroy " + mapping.getName());
        setValues(null);
        setLang(Idioma.DEFAULT);
        setPage(1);
    }

    private String select;

    /**
     * Idioma seleccionado por el usuario cuando quiere cambiar de traduccion
     */
    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    private String lang;

    /**
     * Idioma de la traduccion que se esta editando
     */
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Bean con los datos de la entidad
     */
    private Traducible values;

    public Traducible getValues() {
        if (values == null) {
            try {
                values = (Traducible) RequestUtils.applicationInstance(valuesClassName);
            } catch (Throwable t) {
                log.error("No se ha podido crear el value ", t);
                return null;
            }
        }
        return values;
    }

    public void setValues(Traducible values) {
        this.values = values;
    }


    /**
     * Acceso directo a los datos traducidos.
     */
    public Traduccion getTraduccion() {
        Traduccion traduccion = values.getTraduccion(lang);
        if (traduccion == null)
            try {
                traduccion = (Traduccion) RequestUtils.applicationInstance(traduccionClassName);
                getValues().setTraduccion(lang, traduccion);
            } catch (Throwable t) {
                log.error("No se ha podidio crear la traducción", t);
                return null;
            }

        return traduccion;
    }

    public void setTraduccion(Traduccion traduccion) {
        values.setTraduccion(lang, traduccion);
    }

}
