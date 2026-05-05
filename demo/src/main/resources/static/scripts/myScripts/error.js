 $(document).ready(function() {
        
        	$(window).load(function() {
        		// Animate loader off screen
        		$("#animation_box").slideDown(3000, function() {});
        	});
        	
        	
        	
   
            
        	$( "#cancelPage" ).click(function( event ) {
        		window.location.replace("http://localhost:8080/auction");
        		  
        		});
        });