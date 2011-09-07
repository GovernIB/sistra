	

	/* seleccionar ?tem d'una tabla */
	function selecItemTabla(obj) {
		clase = obj.className;
		obj.className = 'perDamunt';
		obj.onmouseout = function() { if(clase == 'nou') obj.className = 'nou'; else obj.className = ''; };
	}

    function validaFormulario( form )
    {
    	form.pagina.value = '0';
    	form.submit();
    }
    
    function detalleEnvio(id){
    	document.location = "detalleEnvio.do?codigo=" + id;
    }
    
