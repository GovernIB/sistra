package org.ibit.rol.form.persistence.conector;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.beans.PropertyDescriptor;

/**
 * TODO documentar
 */
public class ConectorConfigurator {

    protected static final Log log = LogFactory.getLog(ConectorConfigurator.class);

    public static void setConectorProperties(Conector conector, Map properties) {
        for (Iterator iterator = properties.keySet().iterator(); iterator.hasNext();) {
            String propertyName = (String) iterator.next();
            try {
                PropertyUtils.setProperty(conector, propertyName, properties.get(propertyName));
            } catch (Throwable t) {
                log.error("Error fijando propiedad " + propertyName, t);
            }
        }
    }

    public static Conector initConector(String className) throws ConectorException {
        try {
            Class clazz = Class.forName(className);
            return (Conector) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            log.error(e);
            throw new ConectorException(e.getMessage());
        } catch (InstantiationException e) {
            log.error(e);
            throw new ConectorException(e.getMessage());
        } catch (IllegalAccessException e) {
            log.error(e);
            throw new ConectorException(e.getMessage());
        }
    }

    public static List /*String*/ getConectorPropertyNames(Conector conector) {
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(conector);
        List list = new ArrayList(descriptors.length);
        for (int i = 0; i < descriptors.length; i++) {
            PropertyDescriptor descriptor = descriptors[i];
            if (descriptor.getWriteMethod() != null) {
                list.add(descriptor.getName());
            }
        }
        return list;
    }

    public static List /*String*/ getConectorPropertyNames(Class clazz) {
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
        List list = new ArrayList(descriptors.length);
        for (int i = 0; i < descriptors.length; i++) {
            PropertyDescriptor descriptor = descriptors[i];
            if (descriptor.getWriteMethod() != null) {
                list.add(descriptor.getName());
            }
        }
        return list;
    }
}
