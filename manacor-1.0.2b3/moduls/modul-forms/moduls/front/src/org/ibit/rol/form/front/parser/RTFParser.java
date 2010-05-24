package org.ibit.rol.form.front.parser;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ibit.rol.form.front.util.RTFUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class RTFParser implements Parser {

    private static final Log log = LogFactory.getLog(RTFParser.class);

    private byte[] rtf;
    private List images = new ArrayList();

    public String contentType() {
        return "application/rtf";
    }

    public void load(byte[] data) throws ParserException {
        rtf = RTFUtil.removeNonshppict(data);
        images = RTFUtil.imagesRTF(rtf);
        rtf = RTFUtil.rtfImageTags(rtf);
    }

    public void populate(Map map) throws ParserException {
        log.debug("Entrant a populate");
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = map.get(key);
            if (StringUtils.isNotEmpty(key)) {
                if (value instanceof byte[]) {
                    addImage(Integer.parseInt(key), RTFUtil.toHexadecimal64((byte[]) value));
                } else {                    
                    String textValue = (String) value;
                    rtf = RTFUtil.replace(rtf, key, textValue);
                }
            }
        }
    }

    public void addBarcode(String barcode, int x, int y) throws ParserException {

    }

    public byte[] write() throws ParserException {
        rtf = RTFUtil.rtfTagsImatge(rtf, images);
        return rtf;
    }

    private void addImage(int index, String imageHex) {
        String antiga = (String) images.get(index);
        int indexBlip = antiga.indexOf("\\bliptag");

        char[] image = antiga.substring(indexBlip + 1).toCharArray();
        int end = 0;
        int count = -1;
        while (count != 0) {
            if (image[end] == '\n') {
                count = 0;
            }
            if (image[end] == '{') {
                if (count == -1) {
                    count = 0;
                }
                count ++;
            }
            if (image[end] == '}') {
                count --;
            }
            end++;
        }
        end++;
        int indexInici = indexBlip + end;
        int indexFinal = antiga.indexOf('}', indexInici);
        String nova = antiga.substring(0, indexInici) + imageHex + antiga.substring(indexFinal);
        images.set(index, nova);
    }
}