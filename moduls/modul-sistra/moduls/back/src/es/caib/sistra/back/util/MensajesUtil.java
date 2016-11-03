package es.caib.sistra.back.util;

import org.apache.struts.util.MessageResources;

public class MensajesUtil {

	public static MessageResources msg;

	public static void setMsg(MessageResources msg) {
		if(MensajesUtil.msg==null){
			MensajesUtil.msg = msg;
		}
	}
	
	public static String getValue(String key) {
		if(msg!=null)
			return msg.getMessage(key);
		else
			return "";
	}
	
}
