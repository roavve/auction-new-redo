$(".cancelButtonNotCanceled").click(function( event ) {
console.log(1);
		 if (confirm('Are you sure?')) {
			 console.log(2);
			var recordKey = $(this).data("reckey");
			 console.log(3);
			var url = $(this).attr('href');
			 console.log(4);
	        $('#content').load(url);
	    	$.ajax({
	    	  type: "GET",
	    	  url: "/auction/company/cancell_user?recordKey="+recordKey
  	        });

	   	 //   window.location.reload()   
	 		$('#content').load(url);	   	    
		 }
	});
	
    function btnStartClick(recordKey){
    	  if (confirm('Are you sure?')) {
    		    var url = $(this).attr('href');
    		
    	        $('#content').load(url);
	    	    	$.ajax({
	    	    	  type: "GET",
	    	    	  url: "/auction/cancell_user?recordKey="+recordKey
	    
	
	    	        });
    		  
       	     window.location.reload()   
             $('#content').load(url);
         }
        else{
       	   console.log("NO"); 
        }
    }
    
    $(document).ready(function(){
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
     });
            
            $( "#cancelPage" ).click(function( event ) {
       		  window.history.back();
      		  
      		});

            
          
/*  
        $('#cancelButtonNotCanceled').click(function () {
            // escape here if the confirm is false;
alert(document.getElementById('idId').value);
           if (confirm('Are you sure?')) {
        	   console.log(document.getElementById('').value);
      var url = $(this).attr('href');
      $('#content').load(url);
    }
           else{
        	   console.log("NO"); 
           }
        });
        */

        });