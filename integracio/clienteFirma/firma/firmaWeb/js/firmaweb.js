var firmaweb_divPadre = "contenidor";

$(document).ready(function(){
	
	$.mostrarFirmaWeb = function( lang, nif, documentoB64UrlSafe, filename, callback, options)
	{			
	 
	  firmaweb_divPadre = "contenidor";
	  
	  if (options != null && options != undefined && options.contenedor != undefined && options.contenedor != null) {
		  firmaweb_divPadre = options.contenedor;
	  }
	  
	  // Target: parent / self
	  var callbackTarget = "parent";
	  if (callback.target != null && callback.target != undefined) {
		  callbackTarget = callback.target;
	  }
	  
	  if ($("#fonsFirmaWeb").size() == 0) {
		  
		  		  	
		  	var params = { accion: "iniciarFirma",
		  				   documentoContentB64UrlSafe: documentoB64UrlSafe,
		  				   documentoFileName: filename,
		  				   documentoNif: nif,
		  				   configLang: lang,
		  				   configSignType:FIRMAWEB_SIGN_TYPE,
		  				   configSignMode: FIRMAWEB_SIGN_MODE,
		  				   configSignAlgorithm: FIRMAWEB_SIGN_ALGORITHM,
		  				   callbackUrl: callback.url,
		  				   callbackParamSignature: callback.paramSignature,
		  				   callbackUrlCancel: callback.urlCancel,
		  				   callbackTarget: callbackTarget
		  				   };
		  	
		  	for (var prop in callback.paramOthers) {
		        if(!callback.paramOthers.hasOwnProperty(prop)) continue;
		        params["callbackParamOther_" + prop] = callback.paramOthers[prop];	        		        
		  	}
		  	
		  	$.ajax({
		  	  type: "POST",
		  	  url: FIRMAWEB_CONTEXTO + FIRMAWEB_SERVLET,
		  	  data: params,
		  	  dataType: "json",
		  	  success: function(data)
	  		    {	  				
	  				$.redireccionarPasarelaFirma(data.id);	  
	  		    },
	  		    error: function() {
	  		    	alert("Error accediendo pasarela");
	  		    }	  		    
		  	});		  		  					
	};
	
	$.redireccionarPasarelaFirma = function(signaturesSetID)
	{		  	
		  	// tamany escritori treball
			finestraX = document.documentElement.clientWidth;
			finestraY = document.documentElement.clientHeight;
			bodyY = document.body.offsetHeight;
			documentY = document.documentElement.offsetHeight;
			if (bodyY > documentY) documentY = bodyY;
			
			// creem fons
			$("#" + firmaweb_divPadre).append("<div id='fonsFirmaWeb'></div>");
			$("#fonsFirmaWeb").css("opacity",".5");
			$("#fonsFirmaWeb").css("position","absolute");
			$("#fonsFirmaWeb").css("top","0");
			$("#fonsFirmaWeb").css("left","0");
			$("#fonsFirmaWeb").css("width","100%");
			$("#fonsFirmaWeb").css("background-color","#c0c0c0");
			$("#fonsFirmaWeb").css("z-index","9");
			
			// apliquem tamany
			$("#fonsFirmaWeb").width(finestraX);
			if (documentY > finestraY) {
				$("#fonsFirmaWeb").height(documentY);
			} else {
				$("#fonsFirmaWeb").height(finestraY);
			}
			
			// creamos div para firma
			if ($("#firmaWeb").size() == 0) {
				$("#" + firmaweb_divPadre).append("<div id='firmaWeb'></div>");
				$("#firmaWeb").css("z-index","10");
				$("#firmaWeb").css("background-color","white");
				$("#firmaWeb").css("border","0.5em solid #85bbe4");				
			}
			
			$("#firmaWeb").html('<iframe src="' + FIRMAWEB_CONTEXTO + FIRMAWEB_SERVLET + '?accion=firmar&signaturesSetID=' + signaturesSetID + '" style="border: 0pt none; width: 500px; height: 450px;" scrolling="no"></iframe>');
			
			// mostrem suport
			$("#firmaWeb").fadeIn("slow");
			
			suportW = $(document).find("#firmaWeb").width();
			suportH = $(document).find("#firmaWeb").height();
			suportL = (finestraX-suportW)/2;
			suportT = (finestraY-suportH)/2;
			$("#firmaWeb").css("left",suportL+"px");
			if (window.XMLHttpRequest) {
				$("#firmaWeb").css("position","fixed").css("top",suportT+"px");
			} else {
				finestraScrollT = document.documentElement.scrollTop;
				$("#firmaWeb").css("top",suportT+finestraScrollT+"px");
			}
		}
	}
	
	
	$.ocultarFirmaWeb = function()
	{
		$("#fonsFirmaWeb").remove();
		$("#firmaWeb").remove();
	}
	
});
