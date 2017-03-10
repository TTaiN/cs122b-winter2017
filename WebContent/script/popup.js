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
       show: null, // show immediately
       hide: {
    	   delay: 800
       },
       open: function(event, ui) // Credit = http://stackoverflow.com/questions/16660576/only-close-tooltip-if-mouse-is-not-over-target-or-tooltip
       {
           if (typeof(event.originalEvent) === 'undefined')
           {
               return false;
           }

           var $id = $(ui.tooltip).attr('id');

           // close any lingering tooltips
           $('div.ui-tooltip').not('#' + $id).remove();

           // ajax function to pull in data and add it to the tooltip goes here
       },
       close: function(event, ui)
       {
           ui.tooltip.hover(function()
           {
               $(this).stop(true).fadeTo(400, 1); 
           },
           function()
           {
               $(this).fadeOut('400', function()
               {
                   $(this).remove();
               });
           });
       }
    });
} );