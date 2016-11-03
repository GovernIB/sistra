
function validDate(fecha){
	// regular expression to match required date format
    re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
    return fecha.match(re);    
}
