$(document).ready(function(e) {
	$('select:not(.ignore)').niceSelect();      
	$('.dateChange').on('click',
        function() {
            laydate({
                elem: '#dateCont'
            });
    });
})