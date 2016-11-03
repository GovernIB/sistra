package org.ibit.rol.form.model.betwixt;

import org.apache.commons.betwixt.BindingConfiguration;
import org.apache.commons.betwixt.IntrospectionConfiguration;
import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;
import org.ibit.rol.form.model.Formulario;
import org.ibit.rol.form.model.FormularioSeguro;

import java.beans.IntrospectionException;

/**
 * Utilitat per configurar els BeanReader i BeanWriter de betwixt.
 */
public class Configurator {

    public static void configure(BeanReader beanReader) throws IntrospectionException {
        configure(beanReader.getBindingConfiguration());
        configure(beanReader.getXMLIntrospector().getConfiguration());
        beanReader.registerBeanClass(Formulario.class);
        beanReader.registerBeanClass(FormularioSeguro.class);
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
