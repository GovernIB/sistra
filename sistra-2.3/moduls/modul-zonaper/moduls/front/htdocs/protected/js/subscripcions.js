// Paso 1

$(document).ready(function(){	
	// amaguem les temes
	$("#subscripcionsTemes ul").css("display","none");
	// carpetes
	$("#subscripcionsTemes a").bind("click", function(e){
		$(this).parent().find("ul").slideToggle("slow");
	});
});