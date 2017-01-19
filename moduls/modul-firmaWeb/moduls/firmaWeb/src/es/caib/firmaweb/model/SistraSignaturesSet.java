package es.caib.firmaweb.model;

import java.util.Date;
import java.util.Map;

import org.fundaciobit.plugins.signature.api.CommonInfoSignature;
import org.fundaciobit.plugins.signature.api.FileInfoSignature;
import org.fundaciobit.plugins.signatureweb.api.SignaturesSetWeb;

/**
 *
 * @author anadal
 *
 */
public class SistraSignaturesSet extends SignaturesSetWeb {

  private String urlFinalOriginal;

  private String callbackAppUrl;
  
  private String callbackAppParamSignature;
	
  private Map<String, String> callbackAppParamOthers;
  
  private String callbackAppUrlCancel;
  
  private String callbackAppTarget;
  
  private Long pluginID = null;

  /**
   * @param signaturesSetID
   * @param expiryDate
   * @param commonInfoSignature
   * @param fileInfoSignatureArray
   */
  public SistraSignaturesSet(String signaturesSetID, Date expiryDate,
      CommonInfoSignature commonInfoSignature, FileInfoSignature[] fileInfoSignatureArray,
      String urlFinal) {
    super(signaturesSetID, expiryDate, commonInfoSignature, fileInfoSignatureArray, urlFinal);
    this.urlFinalOriginal = this.getUrlFinal();
  }

  public String getUrlFinalOriginal() {
    return urlFinalOriginal;
  }

  public Long getPluginID() {
    return pluginID;
  }

  public void setPluginID(Long pluginID) {
    this.pluginID = pluginID;
  }

public String getCallbackAppUrl() {
	return callbackAppUrl;
}

public void setCallbackAppUrl(String urlCallbackApp) {
	this.callbackAppUrl = urlCallbackApp;
}

public void setUrlFinalOriginal(String urlFinalOriginal) {
	this.urlFinalOriginal = urlFinalOriginal;
}

public String getCallbackAppParamSignature() {
	return callbackAppParamSignature;
}

public void setCallbackAppParamSignature(String paramCallbackApp) {
	this.callbackAppParamSignature = paramCallbackApp;
}

public Map<String, String> getCallbackAppParamOthers() {
	return callbackAppParamOthers;
}

public void setCallbackAppParamOthers(Map<String, String> callbackAppParamOthers) {
	this.callbackAppParamOthers = callbackAppParamOthers;
}

public String getCallbackAppUrlCancel() {
	return callbackAppUrlCancel;
}

public void setCallbackAppUrlCancel(String callbackAppUrlCancel) {
	this.callbackAppUrlCancel = callbackAppUrlCancel;
}

public String getCallbackAppTarget() {
	return callbackAppTarget;
}

public void setCallbackAppTarget(String callbackAppTarget) {
	this.callbackAppTarget = callbackAppTarget;
}

}
