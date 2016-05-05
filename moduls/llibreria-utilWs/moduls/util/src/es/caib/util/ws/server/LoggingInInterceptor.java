package es.caib.util.ws.server;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.jfree.util.Log;

import es.caib.util.ws.ConfigurationUtil;

/**
 * A simple logging handler which outputs the bytes of the message to the
 * Logger.
 */
public class LoggingInInterceptor extends AbstractPhaseInterceptor<Message> {

	private Boolean debugEnabled = null;

	private static final Logger LOG = LogUtils.getL7dLogger(LoggingInInterceptor.class);

    private int limit = 100 * 1024;
    private PrintWriter writer;
    private boolean prettyLogging;



    public LoggingInInterceptor() {
        super(Phase.RECEIVE);
    }

    public LoggingInInterceptor(String phase) {
        super(phase);
    }

    public LoggingInInterceptor(int lim) {
        this();
        limit = lim;
    }

    public LoggingInInterceptor(PrintWriter w) {
        this();
        this.writer = w;
    }

    public void setPrettyLogging(boolean flag) {
        prettyLogging = flag;
    }

    public boolean isPrettyLogging() {
        return prettyLogging;
    }

    public void setPrintWriter(PrintWriter w) {
        writer = w;
    }

    public PrintWriter getPrintWriter() {
        return writer;
    }

    public void setLimit(int lim) {
        limit = lim;
    }

    public int getLimit() {
        return limit;
    }

    public void handleMessage(Message message) throws Fault {
    	if (isDebugEnabled()) {
	        if (writer != null || LOG.isLoggable(Level.INFO)) {
	            logging(message);
	        }
    	}
    }

    /**
     * Transform the string before display. The implementation in this class
     * does nothing. Override this method if you want to change the contents of the
     * logged message before it is delivered to the output.
     * For example, you can use this to mask out sensitive information.
     * @param originalLogString the raw log message.
     * @return transformed data
     */
    protected String transform(String originalLogString) {
        return originalLogString;
    }

    protected void writePayload(StringBuilder builder, CachedOutputStream cos,
                                String encoding, String contentType)
        throws Exception {
        if (isPrettyLogging() && (contentType != null && contentType.indexOf("xml") >= 0)) {
            Transformer serializer = XMLUtils.newTransformer(2);
            // Setup indenting to "pretty print"
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StringWriter swriter = new StringWriter();
            serializer.transform(new StreamSource(cos.getInputStream()), new StreamResult(swriter));
            String result = swriter.toString();
            if (result.length() < limit || limit == -1) {
                builder.append(swriter.toString());
            } else {
                builder.append(swriter.toString().substring(0, limit));
            }

        } else {
             cos.writeCacheTo(builder, limit);
        }
    }

    private void logging(Message message) throws Fault {
        if (message.containsKey(LoggingMessage.ID_KEY)) {
            return;
        }
        String id = (String)message.getExchange().get(LoggingMessage.ID_KEY);
        if (id == null) {
            id = LoggingMessage.nextId();
            message.getExchange().put(LoggingMessage.ID_KEY, id);
        }
        message.put(LoggingMessage.ID_KEY, id);
        final LoggingMessage buffer
            = new LoggingMessage("Inbound Message\n----------------------------", id);

        Integer responseCode = (Integer)message.get(Message.RESPONSE_CODE);
        if (responseCode != null) {
            buffer.getResponseCode().append(responseCode);
        }

        String encoding = (String)message.get(Message.ENCODING);

        if (encoding != null) {
            buffer.getEncoding().append(encoding);
        }
        String ct = (String)message.get(Message.CONTENT_TYPE);
        if (ct != null) {
            buffer.getContentType().append(ct);
        }
        Object headers = message.get(Message.PROTOCOL_HEADERS);

        if (headers != null) {
            buffer.getHeader().append(headers);
        }
        String uri = (String)message.get(Message.REQUEST_URI);
        if (uri != null) {
            buffer.getAddress().append(uri);
        }

        InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            CachedOutputStream bos = new CachedOutputStream();
            try {
                IOUtils.copy(is, bos);

                bos.flush();
                is.close();

                message.setContent(InputStream.class, bos.getInputStream());
                if (bos.getTempFile() != null) {
                    //large thing on disk...
                    buffer.getMessage().append("\nMessage (saved to tmp file):\n");
                    buffer.getMessage().append("Filename: " + bos.getTempFile().getAbsolutePath() + "\n");
                }
                if (bos.size() > limit) {
                    buffer.getMessage().append("(message truncated to " + limit + " bytes)\n");
                }
                writePayload(buffer.getPayload(), bos, encoding, ct);

                bos.close();
            } catch (Exception e) {
                throw new Fault(e);
            }
        }

        if (writer != null) {
            writer.println(transform(buffer.toString()));
        } else if (LOG.isLoggable(Level.INFO)) {
            LOG.info(transform(buffer.toString()));
        }
    }

    public boolean isDebugEnabled() {

		if (debugEnabled == null) {
			try {
				debugEnabled = new Boolean( "true".equals(ConfigurationUtil.getInstance().obtenerPropiedades().getProperty("sistra.ws.server.logCalls")));
			} catch (Exception e) {
				Log.error("Error obteniendo configuracion loging. Se establece a false", e);
				debugEnabled = new Boolean(false);
			}
		}

		return debugEnabled.booleanValue();
	}
}