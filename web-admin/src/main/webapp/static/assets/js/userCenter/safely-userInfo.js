$(document).ready(function(e) {
	$('select:not(.ignore)').niceSelect();      
	$('.datePicker').dcalendarpicker({
			format:'yyyy-mm-dd'
	}); 
})