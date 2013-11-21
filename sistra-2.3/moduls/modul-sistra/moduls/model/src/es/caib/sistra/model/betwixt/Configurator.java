package es.caib.sistra.model.betwixt;

import java.beans.IntrospectionException;

import org.apache.commons.betwixt.BindingConfiguration;
import org.apache.commons.betwixt.IntrospectionConfiguration;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.caib.sistra.model.Dominio;
import es.caib.sistra.model.TramiteVersion;

/**
 * Utilitat per configurar els BeanReader i BeanWriter de betwixt.
 */
public class Configurator {

    private static Log _log = LogFactory.getLog( Configurator.class );
	
	public static void configure(BeanReader beanReader) throws IntrospectionException {
        configure(beanReader.getBindingConfiguration());
        configure(beanReader.getXMLIntrospector().getConfiguration());

        /*
        BeanCreationChain beanCreationChain = beanReader.getReadConfiguration().getBeanCreationChain();
        BeanCreationList chain = BeanCreationList.createStandardChain();
        
        ChainedBeanCreator creator = new MyBeanCreator();
        chain.insertBeanCreator(1, creator);
        beanReader.getReadConfiguration().setBeanCreationChain( chain );
        */

        beanReader.registerBeanClass(TramiteVersion.class);
        beanReader.registerBeanClass( Dominio.class );
        //beanReader.registerBeanClass(MensajeTramite.class);
    }

    public static void configure(BeanWriter beanWriter) {
        configure(beanWriter.getBindingConfiguration());
        configure(beanWriter.getXMLIntrospector().getConfiguration());
        beanWriter.enablePrettyPrint();
    }

    private static void configure(IntrospectionConfiguration configuration) {
        configuration.setAttributesForPrimitives(true);
        configuration.setPluralStemmer(new MyPluralStemmer());
        configuration.setTypeBindingStrategy(new MyTypeBindingStrategy());
        configuration.setClassNormalizer(new MyClassNormalizer());
        ChainSuppressionStrategy suppressionStrategy = new ChainSuppressionStrategy();
        suppressionStrategy.addStrategy(new MyDefaultSupressionStrategy());
        suppressionStrategy.addStrategy(new ModelSuppressionStrategy());
        configuration.setPropertySuppressionStrategy(suppressionStrategy);
    }

    private static void configure(BindingConfiguration bindingConfiguration) {
        bindingConfiguration.setMapIDs(false);
        bindingConfiguration.setClassNameAttribute("class");
        bindingConfiguration.setObjectStringConverter(new MyObjectStringConverter());
    }
}
