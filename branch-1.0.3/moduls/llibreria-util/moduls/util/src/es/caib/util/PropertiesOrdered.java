package es.caib.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

public class PropertiesOrdered extends LinkedHashMap {
    
  
   
    public synchronized Object setProperty(String key, String value) {
        return put(key, value);
    }

  
    public synchronized void load(InputStream inStream) throws IOException {
        char[] convtBuf = new char[1024];
        LineReader lr = new LineReader(inStream);

        int limit;
        int keyLen;
        int valueStart;
        char c;
        boolean hasSep;
        boolean precedingBackslash;

        while ((limit = lr.readLine()) >= 0) {
            c = 0;
            keyLen = 0;
            valueStart = limit;
            hasSep = false;

	    //System.out.println("line=<" + new String(lineBuf, 0, limit) + ">");
            precedingBackslash = false;
            while (keyLen < limit) {
                c = lr.lineBuf[keyLen];
                //need check if escaped.
                if ((c == '=' ||  c == ':') && !precedingBackslash) {
                    valueStart = keyLen + 1;
                    hasSep = true;
                    break;
                } else if ((c == ' ' || c == '\t' ||  c == '\f') && !precedingBackslash) {
                    valueStart = keyLen + 1;
                    break;
                } 
                if (c == '\\') {
                    precedingBackslash = !precedingBackslash;
                } else {
                    precedingBackslash = false;
                }
                keyLen++;
            }
            while (valueStart < limit) {
                c = lr.lineBuf[valueStart];
                if (c != ' ' && c != '\t' &&  c != '\f') {
                    if (!hasSep && (c == '=' ||  c == ':')) {
                        hasSep = true;
                    } else {
                        break;
                    }
                }
                valueStart++;
            }
            String key = loadConvert(lr.lineBuf, 0, keyLen, convtBuf);
            String value = loadConvert(lr.lineBuf, valueStart, limit - valueStart, convtBuf);
	    put(key, value);
	}
    }

    /* read in a "logical line" from input stream, skip all comment and
     * blank lines and filter out those leading whitespace characters 
     * (\u0020, \u0009 and \u000c) from the beginning of a "natural line". 
     * Method returns the char length of the "logical line" and stores 
     * the line in "lineBuf". 
     */
    class LineReader {
        public LineReader(InputStream inStream) {
            this.inStream = inStream;
	}
        byte[] inBuf = new byte[8192]; 
        char[] lineBuf = new char[1024];
        int inLimit = 0;
        int inOff = 0;
        InputStream inStream;

        int readLine() throws IOException {
            int len = 0;
            char c = 0;

            boolean skipWhiteSpace = true;
            boolean isCommentLine = false;
            boolean isNewLine = true;
            boolean appendedLineBegin = false;
            boolean precedingBackslash = false;
	    boolean skipLF = false;

            while (true) {
                if (inOff >= inLimit) {
                    inLimit = inStream.read(inBuf);
		    inOff = 0;
		    if (inLimit <= 0) {
			if (len == 0 || isCommentLine) { 
			    return -1; 
			}
			return len;
		    }
		}     
                //The line below is equivalent to calling a 
                //ISO8859-1 decoder.
		c = (char) (0xff & inBuf[inOff++]);
                if (skipLF) {
                    skipLF = false;
		    if (c == '\n') {
		        continue;
		    }
		}
		if (skipWhiteSpace) {
		    if (c == ' ' || c == '\t' || c == '\f') {
			continue;
		    }
		    if (!appendedLineBegin && (c == '\r' || c == '\n')) {
			continue;
		    }
		    skipWhiteSpace = false;
		    appendedLineBegin = false;
		}
		if (isNewLine) {
		    isNewLine = false;
		    if (c == '#' || c == '!') {
			isCommentLine = true;
			continue;
		    }
		}
		
		if (c != '\n' && c != '\r') {
		    lineBuf[len++] = c;
		    if (len == lineBuf.length) {
		        int newLength = lineBuf.length * 2;
		        if (newLength < 0) {
		            newLength = Integer.MAX_VALUE;
		        }
			char[] buf = new char[newLength];
			System.arraycopy(lineBuf, 0, buf, 0, lineBuf.length);
			lineBuf = buf;
		    }
		    //flip the preceding backslash flag
		    if (c == '\\') {
			precedingBackslash = !precedingBackslash;
		    } else {
			precedingBackslash = false;
		    }
		}
		else {
		    // reached EOL
		    if (isCommentLine || len == 0) {
			isCommentLine = false;
			isNewLine = true;
			skipWhiteSpace = true;
			len = 0;
			continue;
		    }
		    if (inOff >= inLimit) {
			inLimit = inStream.read(inBuf);
			inOff = 0;
			if (inLimit <= 0) {
			    return len;
			}
		    }
		    if (precedingBackslash) {
			len -= 1;
			//skip the leading whitespace characters in following line
			skipWhiteSpace = true;
			appendedLineBegin = true;
			precedingBackslash = false;
			if (c == '\r') {
                            skipLF = true;
			}
		    } else {
			return len;
		    }
		}
	    }
	}
    }    
    
    /*
     * Converts encoded &#92;uxxxx to unicode chars
     * and changes special saved chars to their original forms
     */
    private String loadConvert (char[] in, int off, int len, char[] convtBuf) {
        if (convtBuf.length < len) {
            int newLen = len * 2;
            if (newLen < 0) {
	        newLen = Integer.MAX_VALUE;
	    } 
	    convtBuf = new char[newLen];
        }
        char aChar;
        char[] out = convtBuf; 
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];   
                if(aChar == 'u') {
                    // Read the xxxx
                    int value=0;
		    for (int i=0; i<4; i++) {
		        aChar = in[off++];  
		        switch (aChar) {
		          case '0': case '1': case '2': case '3': case '4':
		          case '5': case '6': case '7': case '8': case '9':
		             value = (value << 4) + aChar - '0';
			     break;
			  case 'a': case 'b': case 'c':
                          case 'd': case 'e': case 'f':
			     value = (value << 4) + 10 + aChar - 'a';
			     break;
			  case 'A': case 'B': case 'C':
                          case 'D': case 'E': case 'F':
			     value = (value << 4) + 10 + aChar - 'A';
			     break;
			  default:
                              throw new IllegalArgumentException(
                                           "Malformed \\uxxxx encoding.");
                        }
                     }
                    out[outLen++] = (char)value;
                } else {
                    if (aChar == 't') aChar = '\t'; 
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f'; 
                    out[outLen++] = aChar;
                }
            } else {
	        out[outLen++] = (char)aChar;
            }
        }
        return new String (out, 0, outLen);
    }

    /*
     * NOT USED
     * 
     * Converts unicodes to encoded &#92;uxxxx and escapes
     * special characters with a preceding slash
     
    private String saveConvert(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\'); outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch(aChar) {
		case ' ':
		    if (x == 0 || escapeSpace) 
			outBuffer.append('\\');
		    outBuffer.append(' ');
		    break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\'); outBuffer.append(aChar);
                    break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>  8) & 0xF));
                        outBuffer.append(toHex((aChar >>  4) & 0xF));
                        outBuffer.append(toHex( aChar        & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }
 */

    /*
     * NOT USED
    private static void writeln(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }
  */
    

 


    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * <code>null</code> if the property is not found.
     *
     * @param   key   the property key.
     * @return  the value in this property list with the specified key value.
     * @see     #setProperty
     * @see     #defaults
     */
    public String getProperty(String key) {
	Object oval = super.get(key);
	String sval = (oval instanceof String) ? (String)oval : null;
	return sval;	
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns the
     * default value argument if the property is not found.
     *
     * @param   key            the hashtable key.
     * @param   defaultValue   a default value.
     *
     * @return  the value in this property list with the specified key value.
     * @see     #setProperty
     * @see     #defaults
     */
    public String getProperty(String key, String defaultValue) {
	String val = getProperty(key);
	return (val == null) ? defaultValue : val;
    }

    
    
    /* NOT USED
     * Convert a nibble to a hex character
     * @param	nibble	the nibble to convert.
     
    private static char toHex(int nibble) {
	return hexDigit[(nibble & 0xF)];
    }
    */
    
    /* NOT USED
     *  A table of hex digits 
    private static final char[] hexDigit = {
	'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
    */
}



