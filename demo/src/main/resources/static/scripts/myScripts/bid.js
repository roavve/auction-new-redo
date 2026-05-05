       $(document).ready(function() { 
       
           var showLastBid=document.getElementById('showLastBid').value;
    	   var bidError=document.getElementById('bidError');
    	   
    	   
 
    	  
    	   
    	   if(showLastBid=="true"){
    		   
    		   $("#stepStep").css("display", "block");
    		   $("#lastBidBid").css("display", "block");
    		   $("#lastBidBidDate").css("display", "block");
    		   
    		   
    		   $("#divLastShow").css("display", "block");
    		   $("#stepShow").css("display", "block");
    		 
    	   }
  
    	   
    	   if(bidError.value==-1){
    		   console.log(bidError.value);
    		   $("#bidErrorJeradi").css("display", "block");
    		   $("#bidErrorLast").css("display", "none");
    	   }
    	   
    	   if(bidError.value==-2){
    		   console.log(bidError.value);
    		   $("#bidErrorLast").css("display", "block");
    		   $("#bidErrorJeradi").css("display", "none");
    	   }
    	   
    	   
    	   if(bidError.value==-5){
    		   console.log(bidError.value);
    		   $("#bidErrorLast").css("display", "none");
    		   $("#bidErrorJeradi").css("display", "none");
    		   $("#initialBidError").css("display", "block");
    	   }
    	   
    	   if(bidError.value==-6){
    		   console.log(bidError.value);
    		   $("#bidErrorLastSell").css("display", "none");
    		   $("#bidErrorJeradiSell").css("display", "none");
    		   $("#initialBidErrorSell").css("display", "block");
    	   }
 
    	   if(bidError.value==-7){
    		   console.log(bidError.value);
    		   $("#bidErrorLastSell").css("display", "block");
    		   $("#bidErrorJeradi").css("display", "none");
    		   $("#initialBidErrorSell").css("display", "none");
    	   }
    	   
    	   if(bidError.value==-8){
    		   console.log(bidError.value);
    		   $("#bidErrorJeradiSell").css("display", "block");
    		   $("#bidErrorLastSell").css("display", "none");
    		   $("#initialBidErrorSell").css("display", "none");
    	   }
    	   
    	   if(bidError.value==-9){
    		   console.log(bidError.value);
    		   $("#bidErrorLastSell").css("display", "none");
    		   $("#bidErrorJeradiSell").css("display", "none");
    		   $("#initialBidErrorSell").css("display", "block");
    	   }
    	   
    	   $("#bidSendButton").click(function() {
    		   $(this).find('input[type="submit"]').attr('disabled','disabled');
    	   });
    	   
    	   $("#bidSendButtonJS").click(function() {
    		   $(this).find('input[type="submit"]').attr('disabled','disabled');
    	   });
    	   
       
           		   
		});    