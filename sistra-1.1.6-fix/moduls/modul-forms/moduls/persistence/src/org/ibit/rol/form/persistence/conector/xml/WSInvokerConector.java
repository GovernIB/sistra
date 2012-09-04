package org.ibit.rol.form.persistence.conector.xml;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.ibit.rol.form.persistence.conector.ConectorException;
import org.ibit.rol.form.persistence.conector.FileResult;
import org.ibit.rol.form.persistence.conector.MessageResult;
import org.ibit.rol.form.persistence.conector.Result;

import javax.xml.rpc.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * TODO documentar
 */
public class WSInvokerConector extends XmlConector {

    protected static final Log log = LogFactory.getLog(WSInvokerConector.class);

    private String namespaceMapping;

    public void setNamespaceMapping(String namespaceMapping) {
        this.namespaceMapping = namespaceMapping;
    }

    private String endPoint;

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String resultPath;

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String soapAction;

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    public Result exec(Map formValues) throws ConectorException {

        FileResult fileResult = (FileResult) super.exec(formValues);

        Properties namespaceMappingProps = new Properties();
        try {
            if (namespaceMapping != null && namespaceMapping.length() > 0) {
                namespaceMappingProps.load(new ByteArrayInputStream(namespaceMapping.getBytes()));
                log.debug("Namespace Mappings: " + namespaceMappingProps);
            }
        } catch (IOException e) {
            throw new ConectorException(e.getMessage());
        }

        try {
            Service service = new Service();
            Call call = (Call) service.createCall();

            call.setTargetEndpointAddress(endPoint);
            log.debug("Invocando endPoint " + endPoint);
            if (soapAction != null) {
                call.setSOAPActionURI(soapAction);
            }

            SOAPEnvelope se = new SOAPEnvelope();

            ByteArrayInputStream bais = new ByteArrayInputStream(fileResult.getBytes());
            SOAPBodyElement sbe = new SOAPBodyElement(bais);
            se.addBodyElement(sbe);

            SOAPEnvelope re = call.invoke(se);

            String resultString = re.getFirstBody().toString();
            log.debug("Recibida respuesta: " + resultString);

            if (resultPath != null) {
                Document doc = DocumentHelper.parseText(resultString);
                procesarNamespaceNode(doc.getRootElement(), namespaceMappingProps);

                Node node = doc.selectSingleNode(resultPath);
                if (node == null) {
                    log.warn(resultPath + " evalua a null");
                } else {
                    resultString = node.getText();
                }
            }


            return new MessageResult(resultString);

        } catch (DocumentException e) {
            throw new ConectorException(e.getMessage());
        } catch (ServiceException e) {
            throw new ConectorException(e.getMessage());
        } catch (AxisFault axisFault) {
            throw new ConectorException(axisFault.getMessage());
        }
    }

    private void procesarNamespaceNode(Element element, Properties nmprops) {
        Namespace namespace = element.getNamespace();
        if (!Namespace.NO_NAMESPACE.equals(namespace)) {
            String uri = namespace.getURI();
            String prefix = nmprops.getProperty(uri);
            if (prefix != null) {
                element.add(new Namespace(prefix, uri));
            }
        }

        for (int i = 0; i < element.elements().size(); i++) {
            Element child = (Element) element.elements().get(i);
            procesarNamespaceNode(child, nmprops);
        }
    }
}
