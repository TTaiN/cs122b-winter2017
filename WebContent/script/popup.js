$( function() {
	
    $( document ).tooltip({
      items: ".popup",
      content: function(response) {
        	$.get('Popup', {movieId: $(this).attr('id')}, function(data) {
        		response(data);
        	}
       )},
       position: {
         my: "center bottom",
         at: "center top",
         collision: "flipfit"
      },
       show: {
           duration: 800
       }
    });
} );