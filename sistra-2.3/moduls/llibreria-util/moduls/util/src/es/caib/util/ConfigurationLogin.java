package es.caib.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.security.auth.AuthPermission;
import javax.security.auth.login.AppConfigurationEntry;

import sun.security.util.PropertyExpander;
import sun.security.util.ResourcesMgr;



/**
 * Clase que permite indicar una configuracion de login JAAS sin el fichero login.config
 * 
 * Hay que crearla con una cadena que sería el equivalente al contenido del fichero login.config
 * 
 * @author rsanz
 *
 */
public class ConfigurationLogin extends javax.security.auth.login.Configuration {

	private String configuracionLogin;

	private StreamTokenizer st;

	private int lookahead;

	private int linenum;

	private HashMap configuration;

	private boolean expandProp = true;

	private boolean testing = false;

	/**
	 * Create a new <code>Configuration</code> object.
	 */
	public ConfigurationLogin(String confLogin) {
		try {
			configuracionLogin=confLogin;
			init();
		} catch (IOException ioe) {
			throw (SecurityException) new SecurityException(ioe.getMessage())
					.initCause(ioe);
		}
	}

	/**
	 * Read and initialize the entire login Configuration.
	 * 
	 * <p>
	 * 
	 * @exception IOException
	 *                if the Configuration can not be initialized.
	 *                <p>
	 * @exception SecurityException
	 *                if the caller does not have permission to initialize the
	 *                Configuration.
	 */
	private void init() throws IOException {
		// new configuration
		HashMap newConfig = new HashMap();
		init(newConfig);
		configuration = newConfig;
	}

	private void init(HashMap newConfig) throws IOException {
		InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(
				configuracionLogin.getBytes("UTF-8")), "UTF-8");
		readConfig(isr, newConfig);
		isr.close();
	}

	/**
	 * Retrieve an entry from the Configuration using an application name as an
	 * index.
	 * 
	 * <p>
	 * 
	 * @param applicationName
	 *            the name used to index the Configuration.
	 * @return an array of AppConfigurationEntries which correspond to the
	 *         stacked configuration of LoginModules for this application, or
	 *         null if this application has no configured LoginModules.
	 */
	public AppConfigurationEntry[] getAppConfigurationEntry(
			String applicationName) {

		LinkedList list = null;
		synchronized (configuration) {
			list = (LinkedList) configuration.get(applicationName);
		}

		if (list == null || list.size() == 0)
			return null;

		AppConfigurationEntry[] entries = new AppConfigurationEntry[list.size()];
		Iterator iterator = list.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			AppConfigurationEntry e = (AppConfigurationEntry) iterator.next();
			entries[i] = new AppConfigurationEntry(e.getLoginModuleName(), e
					.getControlFlag(), e.getOptions());
		}
		return entries;
	}

	/**
	 * Refresh and reload the Configuration by re-reading all of the login
	 * configurations.
	 * 
	 * <p>
	 * 
	 * @exception SecurityException
	 *                if the caller does not have permission to refresh the
	 *                Configuration.
	 */
	public synchronized void refresh() {

		java.lang.SecurityManager sm = System.getSecurityManager();
		if (sm != null)
			sm.checkPermission(new AuthPermission("refreshLoginConfiguration"));

		java.security.AccessController
				.doPrivileged(new java.security.PrivilegedAction() {
					public Object run() {
						try {
							init();
						} catch (java.io.IOException ioe) {
							throw new SecurityException(ioe
									.getLocalizedMessage());
						}
						return null;
					}
				});
	}

	private void readConfig(Reader reader, HashMap newConfig)
			throws IOException {

		if (!(reader instanceof BufferedReader))
			reader = new BufferedReader(reader);

		st = new StreamTokenizer(reader);
		st.quoteChar('"');
		st.wordChars('$', '$');
		st.wordChars('_', '_');
		st.wordChars('-', '-');
		st.lowerCaseMode(false);
		st.slashSlashComments(true);
		st.slashStarComments(true);
		st.eolIsSignificant(true);

		lookahead = nextToken();
		while (lookahead != StreamTokenizer.TT_EOF) {

			if (testing)
				System.out.print("\tReading next config entry: ");
			parseLoginEntry(newConfig);
		}
	}

	private void parseLoginEntry(HashMap newConfig) throws IOException {

		String appName;
		String moduleClass;
		String sflag;
		AppConfigurationEntry.LoginModuleControlFlag controlFlag;
		LinkedList configEntries = new LinkedList();

		// application name
		appName = st.sval;
		lookahead = nextToken();

		if (testing)
			System.out.println("appName = " + appName);

		match("{");

		// get the modules
		while (peek("}") == false) {
			// get the module class name
			moduleClass = match("module class name");

			// controlFlag (required, optional, etc)
			sflag = match("controlFlag");
			if (sflag.equalsIgnoreCase("REQUIRED"))
				controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;
			else if (sflag.equalsIgnoreCase("REQUISITE"))
				controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUISITE;
			else if (sflag.equalsIgnoreCase("SUFFICIENT"))
				controlFlag = AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT;
			else if (sflag.equalsIgnoreCase("OPTIONAL"))
				controlFlag = AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
			else {
				MessageFormat form = new MessageFormat(ResourcesMgr.getString(
						"Configuration Error:\n\tInvalid control flag, flag",
						"sun.security.util.AuthResources"));
				Object[] source = { sflag };
				throw new IOException(form.format(source));
			}

			// get the args
			HashMap options = new HashMap();
			String key;
			String value;
			while (peek(";") == false) {
				key = match("option key");
				match("=");
				try {
					value = expand(match("option value"));
				} catch (PropertyExpander.ExpandException peee) {
					throw new IOException(peee.getLocalizedMessage());
				}
				options.put(key, value);
			}

			lookahead = nextToken();

			// create the new element
			if (testing) {
				System.out.print("\t\t" + moduleClass + ", " + sflag);
				java.util.Iterator i = options.keySet().iterator();
				while (i.hasNext()) {
					key = (String) i.next();
					System.out.print(", " + key + "="
							+ (String) options.get(key));
				}
				System.out.println("");
			}
			AppConfigurationEntry entry = new AppConfigurationEntry(
					moduleClass, controlFlag, options);
			configEntries.add(entry);
		}

		match("}");
		match(";");

		// add this configuration entry
		if (newConfig.containsKey(appName)) {
			MessageFormat form = new MessageFormat(ResourcesMgr.getString(
					"Configuration Error:\n\t"
							+ "Can not specify multiple entries for appName",
					"sun.security.util.AuthResources"));
			Object[] source = { appName };
			throw new IOException(form.format(source));
		}
		newConfig.put(appName, configEntries);
		if (testing)
			System.out.println("\t\t***Added entry for " + appName
					+ " to overall configuration***");
	}

	private String match(String expect) throws IOException {

		String value = null;

		switch (lookahead) {
		case StreamTokenizer.TT_EOF:

			MessageFormat form1 = new MessageFormat(ResourcesMgr.getString(
					"Configuration Error:\n\texpected [expect], "
							+ "read [end of file]",
					"sun.security.util.AuthResources"));
			Object[] source1 = { expect };
			throw new IOException(form1.format(source1));

		case '"':
		case StreamTokenizer.TT_WORD:

			if (expect.equalsIgnoreCase("module class name")
					|| expect.equalsIgnoreCase("controlFlag")
					|| expect.equalsIgnoreCase("option key")
					|| expect.equalsIgnoreCase("option value")) {
				value = st.sval;
				lookahead = nextToken();
			} else {
				MessageFormat form = new MessageFormat(ResourcesMgr.getString(
						"Configuration Error:\n\tLine line: "
								+ "expected [expect], found [value]",
						"sun.security.util.AuthResources"));
				Object[] source = { new Integer(linenum), expect, st.sval };
				throw new IOException(form.format(source));
			}
			break;

		case '{':

			if (expect.equalsIgnoreCase("{")) {
				lookahead = nextToken();
			} else {
				MessageFormat form = new MessageFormat(ResourcesMgr.getString(
						"Configuration Error:\n\tLine line: expected [expect]",
						"sun.security.util.AuthResources"));
				Object[] source = { new Integer(linenum), expect, st.sval };
				throw new IOException(form.format(source));
			}
			break;

		case ';':

			if (expect.equalsIgnoreCase(";")) {
				lookahead = nextToken();
			} else {
				MessageFormat form = new MessageFormat(ResourcesMgr.getString(
						"Configuration Error:\n\tLine line: expected [expect]",
						"sun.security.util.AuthResources"));
				Object[] source = { new Integer(linenum), expect, st.sval };
				throw new IOException(form.format(source));
			}
			break;

		case '}':

			if (expect.equalsIgnoreCase("}")) {
				lookahead = nextToken();
			} else {
				MessageFormat form = new MessageFormat(ResourcesMgr.getString(
						"Configuration Error:\n\tLine line: expected [expect]",
						"sun.security.util.AuthResources"));
				Object[] source = { new Integer(linenum), expect, st.sval };
				throw new IOException(form.format(source));
			}
			break;

		case '=':

			if (expect.equalsIgnoreCase("=")) {
				lookahead = nextToken();
			} else {
				MessageFormat form = new MessageFormat(ResourcesMgr.getString(
						"Configuration Error:\n\tLine line: expected [expect]",
						"sun.security.util.AuthResources"));
				Object[] source = { new Integer(linenum), expect, st.sval };
				throw new IOException(form.format(source));
			}
			break;

		default:
			MessageFormat form = new MessageFormat(ResourcesMgr.getString(
					"Configuration Error:\n\tLine line: "
							+ "expected [expect], found [value]",
					"sun.security.util.AuthResources"));
			Object[] source = { new Integer(linenum), expect, st.sval };
			throw new IOException(form.format(source));
		}
		return value;
	}

	private boolean peek(String expect) {
		boolean found = false;

		switch (lookahead) {
		case ',':
			if (expect.equalsIgnoreCase(","))
				found = true;
			break;
		case ';':
			if (expect.equalsIgnoreCase(";"))
				found = true;
			break;
		case '{':
			if (expect.equalsIgnoreCase("{"))
				found = true;
			break;
		case '}':
			if (expect.equalsIgnoreCase("}"))
				found = true;
			break;
		default:
		}
		return found;
	}

	private int nextToken() throws IOException {
		int tok;
		while ((tok = st.nextToken()) == StreamTokenizer.TT_EOL) {
			linenum++;
		}
		return tok;
	}

	/* not used
	 * Fast path reading from file urls in order to avoid calling
	 * FileURLConnection.connect() which can be quite slow the first time it is
	 * called. We really should clean up FileURLConnection so that this is not a
	 * problem but in the meantime this fix helps reduce start up time
	 * noticeably for the new launcher. -- DAC
	
	private InputStream getInputStream(URL url) throws IOException {
		if ("file".equals(url.getProtocol())) {
			String path = url.getFile().replace('/', File.separatorChar);
			return new FileInputStream(path);
		} else {
			return url.openStream();
		}
	}
  */
	
	private String expand(String value)
			throws PropertyExpander.ExpandException, IOException {

		if ("".equals(value)) {
			return value;
		}

		if (expandProp) {

			String s = PropertyExpander.expand(value);

			if (s == null || s.length() == 0) {
				MessageFormat form = new MessageFormat(
						ResourcesMgr
								.getString(
										"Configuration Error:\n\tLine line: "
												+ "system property [value] expanded to empty value",
										"sun.security.util.AuthResources"));
				Object[] source = { new Integer(linenum), value };
				throw new IOException(form.format(source));
			}
			return s;
		} else {
			return value;
		}
	}
}
