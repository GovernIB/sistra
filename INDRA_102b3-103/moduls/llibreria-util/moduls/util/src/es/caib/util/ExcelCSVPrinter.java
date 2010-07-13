package es.caib.util;
import java.io.*;

public class ExcelCSVPrinter {

	/**
	 * Default state of auto flush
	 */
	private static final boolean AUTO_FLUSH_DEFAULT = true;

	/**
	 * If auto flushing is enabled.
	 */
	protected boolean autoFlush = AUTO_FLUSH_DEFAULT;

	/**
	 * Default state of always quote
	 */
	private static final boolean ALWAYS_QUOTE_DEFAULT = false;

	/**
	 * If auto flushing is enabled.
	 */
	protected boolean alwaysQuote = ALWAYS_QUOTE_DEFAULT;

	/**
	 * true iff an error has occurred.
	 */
	protected boolean error = false;

	/**
	 * Default delimiter character
	 */
	private static final char DELIMITER_DEFAULT = ',';

	/**
	 * Character written as field delimiter.
	 */
	protected char delimiterChar = DELIMITER_DEFAULT;

	/**
	 * Default quoting character
	 */
	private static final char QUOTE_DEFAULT = '"';

	/**
	 * Quoting character.
	 */
	protected char quoteChar = QUOTE_DEFAULT;

	/**
	 * The place that the values get written.
	 */
	protected Writer out;

	/**
	 * True iff we just began a new line.
	 */
	protected boolean newLine = true;

	/**
	 * Line ending default
	 */
	private static final String LINE_ENDING_DEFAULT = "\n";

	/**
	 * Line ending indicating the system line ending should be chosen
	 */
	private static final String LINE_ENDING_SYSTEM = null;

	/**
	 * The line ending, must be one of "\n", "\r", or "\r\n"
	 */
	protected String lineEnding = "\n";

	/**
	 * Create a printer that will print values to the given
	 * stream.	 Character to byte conversion is done using
	 * the default character encoding.  The delimiter will
	 * be the comma, the line ending will be the default system
	 * line ending, the quote character will be double quotes,
	 * quotes will be used when needed, and auto flushing
	 * will be enabled.
	 *
	 * @param out stream to which to print.
	 * @throws Exception 
	 */
	public ExcelCSVPrinter(OutputStream out) throws Exception{
		this (
			new OutputStreamWriter(out)
		);
	}
	
	public ExcelCSVPrinter(OutputStream out,String encoding) throws Exception{
		this (
			new OutputStreamWriter(out,encoding)
		);
	}

	/**
	 * Create a printer that will print values to the given
	 * stream.	  The delimiter will be the comma, the line
	 * ending will be the default system line ending, the quote
	 * character will be double quotes, quotes will be used
	 * when needed, and auto flushing will be enabled.
	 *
	 * @param out stream to which to print.
	 */
	public ExcelCSVPrinter(Writer out) throws Exception{
		this(
			out,
			ALWAYS_QUOTE_DEFAULT,
			AUTO_FLUSH_DEFAULT
		);
	}

	/**
	 * Create a printer that will print values to the given
	 * stream.	  The delimiter will be the comma, the line ending
	 * will be the default system line ending, and the quote character
	 * will be double quotes.
	 *
	 * @param out stream to which to print.
	 * @param alwaysQuote true if quotes should be used even when not strictly needed.
	 * @param autoFlush should auto flushing be enabled.
	 */
	public ExcelCSVPrinter(Writer out, boolean alwaysQuote, boolean autoFlush) throws Exception{
		this(
			out,
			QUOTE_DEFAULT,
			DELIMITER_DEFAULT,
			alwaysQuote,
			autoFlush
		);
	}

	/**
	 * Create a printer that will print values to the given
	 * stream.	The line ending will be the default system line
	 * ending, quotes will be used when needed, and auto flushing
	 * will be enabled.
	 *
	 * @param out stream to which to print.
	 * @param delimiter The new delimiter character to use.
	 * @param quote The new character to use for quoting.
	 * @throws BadQuoteException if the character cannot be used as a quote.
	 * @throws BadDelimiterException if the character cannot be used as a delimiter.
	 */
	public ExcelCSVPrinter(Writer out, char quote, char delimiter) throws Exception {
		this(
			out,
			quote,
			delimiter,
			ALWAYS_QUOTE_DEFAULT,
			AUTO_FLUSH_DEFAULT
		);
	}

	/**
	 * Create a printer that will print values to the given
	 * stream.	Quotes will be used when needed, and auto flushing
	 * will be enabled.
	 *
	 * @param out stream to which to print.
	 * @param delimiter The new delimiter character to use.
	 * @param quote The new character to use for quoting.
	 * @param lineEnding The new line ending, or null to use the default line ending.
	 * @throws BadQuoteException if the character cannot be used as a quote.
	 * @throws BadDelimiterException if the character cannot be used as a delimiter.
	 * @throws BadLineEndingException if the line ending is not one of the three legal line endings.
	 */
	public ExcelCSVPrinter(Writer out, char quote, char delimiter, String lineEnding) throws Exception {
		this(
			out,
			quote,
			delimiter,
			lineEnding,
			ALWAYS_QUOTE_DEFAULT,
			AUTO_FLUSH_DEFAULT
		);
	}

	/**
	 * Create a printer that will print values to the given
	 * stream.  The line ending will be the default system line ending.
	 *
	 * @param out stream to which to print.
	 * @param delimiter The new delimiter character to use.
	 * @param quote The new character to use for quoting.
	 * @param alwaysQuote true if quotes should be used even when not strictly needed.
	 * @param autoFlush should auto flushing be enabled.
	 * @throws BadQuoteException if the character cannot be used as a quote.
	 * @throws BadDelimiterException if the character cannot be used as a delimiter.
	 */
	public ExcelCSVPrinter(Writer out, char quote, char delimiter, boolean alwaysQuote, boolean autoFlush) throws Exception {
		this(
			out,
			quote,
			delimiter,
			LINE_ENDING_SYSTEM,
			alwaysQuote,
			autoFlush
		);
	}

	/**
	 * Create a printer that will print values to the given
	 * stream.
	 *
	 * @param out stream to which to print.
	 * @param delimiter The new delimiter character to use.
	 * @param quote The new character to use for quoting.
	 * @param lineEnding The new line ending, or null to use the default line ending.
	 * @param alwaysQuote true if quotes should be used even when not strictly needed.
	 * @param autoFlush should auto flushing be enabled.
	 * @throws BadQuoteException if the character cannot be used as a quote.
	 * @throws BadDelimiterException if the character cannot be used as a delimiter.
	 * @throws BadLineEndingException if the line ending is not one of the three legal line endings.
	 */
	public ExcelCSVPrinter(Writer out, char quote, char delimiter, String lineEnding, boolean alwaysQuote, boolean autoFlush) throws Exception {
		this.out = out;
		changeQuote(quote);
		changeDelimiter(delimiter);
		setAlwaysQuote(alwaysQuote);
		setAutoFlush(autoFlush);
		setLineEnding(lineEnding);
	}

	/**
	 * Change this printer so that it uses a new delimiter.
	 *
	 * @param newDelimiter The new delimiter character to use.
	 * @throws BadDelimiterException if the character cannot be used as a delimiter.
	 */
	public void changeDelimiter(char newDelimiter) throws Exception {
		if (delimiterChar == newDelimiter) return; // no need to do anything.
		if (newDelimiter == '\n' || newDelimiter == '\r' ||
				newDelimiter == delimiterChar || newDelimiter == quoteChar){
			throw new Exception("BadDelimiterException");
		}
		delimiterChar = newDelimiter;
	}

	/**
	 * Change this printer so that it uses a new character for quoting.
	 *
	 * @param newQuote The new character to use for quoting.
	 * @throws BadQuoteException if the character cannot be used as a quote.
	 *
	 */
	public void changeQuote(char newQuote) throws Exception {
		if (quoteChar == newQuote) return; // no need to do anything.
		if (newQuote == '\n' || newQuote == '\r' ||
				newQuote == delimiterChar || newQuote == quoteChar){
			throw new Exception("BadQuoteException");
		}
		quoteChar = newQuote;
	}

	/**
	 * Change this printer so that it uses a new line ending.
	 * <p>
	 * A line ending must be one of "\n", "\r", or "\r\n".
	 * <p>
	 * The default line ending is the system line separator as specified by
	 * <code>System.getProperty("line.separator")</code>, or "\n" if the system
	 * line separator is not a legal line ending.
	 *
	 * @param lineEnding The new line ending, or null to use the default line ending.
	 * @throws BadLineEndingException if the line ending is not one of the three legal line endings.
	 */
	public void setLineEnding(String lineEnding) throws Exception {
		boolean setDefault = lineEnding == null;
		if (setDefault){
			lineEnding = System.getProperty("line.separator");
		}
		if (!"\n".equals(lineEnding) && !"\r".equals(lineEnding) && !"\r\n".equals(lineEnding)){
			if (setDefault){
				lineEnding = LINE_ENDING_DEFAULT;
			} else {
				throw new Exception("BadLineEndingException");
			}
		}
		this.lineEnding = lineEnding;
	}

	/**
	 * Print the string as the last value on the line.	The value
	 * will be quoted if needed.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding writeln method.
	 *
	 * @param value value to be outputted.
	 */
	public void println(String value){
		try {
			writeln(value);
		} catch (Exception iox){
			error = true;
		}
	}

	/**
	 * Print the string as the last value on the line.	The value
	 * will be quoted if needed.
	 *
	 * @param value value to be outputted.
	 * @throws IOException if an error occurs while writing.
	 */
	public void writeln(String value) throws Exception {
		try {
			write(value);
			writeln();
		} catch (Exception iox){
			error = true;
			throw iox;
		}
	}

	/**
	 * Output a blank line.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding writeln method.
	 *
	 */
	public void println(){
		try {
			writeln();
		} catch (IOException iox){
			error = true;
		}
	}

	/**
	 * Output a blank line.
	 *
	 * @throws IOException if an error occurs while writing.
	 */
	public void writeln() throws IOException {
		try {
			out.write(lineEnding);
			if (autoFlush) flush();
			newLine = true;
		} catch (IOException iox){
			error = true;
			throw iox;
		}
	}

	/**
	 * Print a single line of comma separated values.
	 * The values will be quoted if needed.  Quotes and
	 * and other characters that need it will be escaped.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding writeln method.
	 *
	 * @param values values to be outputted.
	 *
	 */
	public void println(String[] values){
		try {
			writeln(values);
		} catch (IOException iox){
			error = true;
		}
	}

	/**
	 * Print a single line of comma separated values.
	 * The values will be quoted if needed.  Quotes and
	 * and other characters that need it will be escaped.
	 *
	 * @param values values to be outputted.
	 * @throws IOException if an error occurs while writing.
	 *
	 */
	public void writeln(String[] values) throws IOException {
		try {
			print(values);
			writeln();
		} catch (IOException iox){
			error = true;
			throw iox;
		}
	}

	/**
	 * Print a single line of comma separated values.
	 * The values will be quoted if needed.  Quotes and
	 * and other characters that need it will be escaped.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding writeln method.
	 *
	 * @param values values to be outputted.
	 *
	 */
	public void print(String[] values){
		try {
			write(values);
		} catch (Exception iox){
			error = true;
		}
	}

	/**
	 * Print a single line of comma separated values.
	 * The values will be quoted if needed.  Quotes and
	 * and other characters that need it will be escaped.
	 *
	 * @param values values to be outputted.
	 * @throws IOException if an error occurs while writing.
	 *
	 */
	public void write(String[] values) throws Exception {
		try {
			for (int i=0; i<values.length; i++){
				write(values[i]);
			}
		} catch (Exception iox){
			error = true;
			throw iox;
		}
	}

	/**
	 * Print several lines of comma separated values.
	 * The values will be quoted if needed.  Quotes and
	 * newLine characters will be escaped.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding writeln method.
	 *
	 * @param values values to be outputted.
	 *
	 */
	public void println(String[][] values){
		try {
			writeln(values);
		} catch (IOException iox){
			error = true;
		}
	}

	/**
	 * Print several lines of comma separated values.
	 * The values will be quoted if needed.  Quotes and
	 * newLine characters will be escaped.
	 *
	 * @param values values to be outputted.
	 * @throws IOException if an error occurs while writing.
	 *
	 */
	public void writeln(String[][] values) throws IOException {
		try {
			for (int i=0; i<values.length; i++){
				writeln(values[i]);
			}
			if (values.length == 0){
				writeln();
			}
		} catch (IOException iox){
			error = true;
			throw iox;
		}
	}

	/**
	 * Since ExcelCSV format does not support comments,
	 * this method will ignore the comment and start
	 * a new row.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding writelnComment method.
	 *
	 * @param comment the comment to output (ignored).
	 *
	 */
	public void printlnComment(String comment){
		println();
	}

	/**
	 * Since ExcelCSV format does not support comments,
	 * this method will ignore the comment and start
	 * a new row.
	 *
	 * @param comment the comment to output (ignored).
	 * @throws IOException if an error occurs while writing.
	 */
	public void writelnComment(String comment) throws IOException {
		writeln();
	}

	/**
	 * Print the string as the next value on the line.	The value
	 * will be quoted if needed.  If value is null, an empty value is printed.
	 * <p>
	 * This method never throws an I/O exception. The client may inquire as to whether
	 * any errors have occurred by invoking checkError().  If an I/O Exception is
	 * desired, the client should use the corresponding println method.
	 *
	 * @param value value to be outputted.
	 *
	 */
	public void print(String value){
		try {
			write(value);
		} catch (Exception iox){
			error = true;
		}
	}

	/**
	 * Print the string as the next value on the line.	The value
	 * will be quoted if needed.  If value is null, an empty value is printed.
	 *
	 * @param value value to be outputted.
	 * @throws IOException if an error occurs while writing.
	 *
	 */
	public void write(String value) throws Exception {
		try {
			if (value == null) value = "";
			boolean quote = false;
			if (alwaysQuote){
				quote = true;
			} else if (value.length() > 0){
				for (int i=0; i<value.length(); i++){
					char c = value.charAt(i);
					if (c==quoteChar || c==delimiterChar || c=='\n' || c=='\r'){
						quote = true;
					}
				}
			} else if (newLine) {
				// always quote an empty token that is the first
				// on the line, as it may be the only thing on the
				// line.  If it were not quoted in that case,
				// an empty line has no tokens.
				quote = true;
			}
			if (newLine){
				newLine = false;
			} else {
				out.write(delimiterChar);
			}
			if (quote){
				out.write(escapeAndQuote(value));
			} else {
				out.write(value);
			}
			if (autoFlush) flush();
		} catch (IOException iox){
			error = true;
			throw iox;
		}
	}

	/**
	 * Enclose the value in quotes and escape the quote
	 * and comma characters that are inside.
	 *
	 * @param value needs to be escaped and quoted.
	 *
	 * @return the value, escaped and quoted.
	 */
	private String escapeAndQuote(String value) throws Exception{
		
		String s = StringUtil.replace(value, String.valueOf(quoteChar), String.valueOf(quoteChar) + String.valueOf(quoteChar));		
		//String s = StringHelper.replace(value, String.valueOf(quoteChar), String.valueOf(quoteChar) + String.valueOf(quoteChar));
		return (new StringBuffer(2 + s.length())).append(quoteChar).append(s).append(quoteChar).toString();
	}

	/**
	 * Flush any data written out to underlying streams.
	 *
	 * @throws IOException if an error occurs while writing.
	 */
	public void flush() throws IOException {
		out.flush();
	}

	/**
	 * Close any underlying streams.
	 *
	 * @throws IOException if an error occurs while writing.
	 */
	public void close() throws IOException {
		out.close();
	}

	/**
	 * Flush the stream if it's not closed and check its error state.
	 * Errors are cumulative; once the stream encounters an error,
	 * this routine will return true on all successive calls.
	 *
	 * @return True if the print stream has encountered an error,
	 * either on the underlying output stream or during a format conversion.
	 *
	 */
	public boolean checkError(){
		try {
			if (error) return true;
			flush();
			if (error) return true;
			if (out instanceof PrintWriter){
				error = ((PrintWriter)out).checkError();
			}
		} catch (IOException iox){
			error = true;
		}
		return error;
	}

	/**
	 * Set whether values printers should always be quoted, or
	 * whether the printer may, at its discretion, omit quotes
	 * around the value.
	 *
	 * @param alwaysQuote true if quotes should be used even when not strictly needed.
	 *
	 */
	public void setAlwaysQuote(boolean alwaysQuote){
		this.alwaysQuote = alwaysQuote;
	}

	/**
	 * Set flushing behavior.  Iff set, a flush command
	 * will be issued to any underlying stream after each
	 * print or write command.
	 *
	 * @param autoFlush should auto flushing be enabled.
	 *
	 */
	public void setAutoFlush(boolean autoFlush){
		this.autoFlush = autoFlush;
	}
}

