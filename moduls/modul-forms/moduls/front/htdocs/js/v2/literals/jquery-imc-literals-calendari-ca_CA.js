// literals calendari

$.datepicker.regional['ca'] = {
		closeText: 'Tanca',
		prevText: 'Anterior',
		nextText: 'Següent',
		currentText: 'Avuí',
		monthNames: ['Gener', 'Febrer', 'Març', 'Abril', 'Maig', 'Juny', 'Juliol', 'Agost', 'Setembre', 'Octubre', 'Novembre', 'Decembre'],
		monthNamesShort: ['Gen','Feb','Mar','Abr', 'Mai','Jun','Jul','Ago','Set', 'Oct','Nov','Dec'],
		dayNames: ['Diumenge', 'Dilluns', 'Dimarts', 'Dimedres', 'Dijous', 'Divendres', 'Dissabte'],
		dayNamesShort: ['Diu','Dll','Dma','Dme','Djo','Dve','Dss'],
		dayNamesMin: ['Du','Dl','Dm','Dx','Dj','Dv','Ds'],
		weekHeader: 'Stm',
		dateFormat: 'dd/mm/yy',
		firstDay: 1,
		isRTL: false,
		showMonthAfterYear: false,
		yearSuffix: '',
		yearRange: "-100:+100"
};
$.datepicker.setDefaults($.datepicker.regional['ca']);
