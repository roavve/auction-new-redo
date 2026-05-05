       $(document).ready(function() {

    
 
    	   setInterval(function(){

    		   if($("#comentBox").is(":focus")){
    			  // window.location.reload(1);
    		   }
    		   else{
    			   window.location.reload(1);
    		   }
    	   }, 10000);
    	
    	   
    	   
//    	   var activeAuction=document.getElementById("activeAuction").value;
//    
//    	   console.log((activeAuction!='key.auctionStatus.active') + " activeAuction");
//    	   
//           if(activeAuction!='key.auctionStatus.active'){
//		       $("#bidButton").css("display", "none");
//		       $("#sendCommentDiv").css("display", "none");
//           }

           var activeAuction=document.getElementById("auctionEndDateFinal").value;
           console.log('  console.log(activeAuction);');
           console.log(activeAuction);
           if(activeAuction!=1){
		       $("#bidButton").css("display", "none");
		       $("#sendCommentDiv").css("display", "none");
           }
           
           
           var countBidsByPeriod=document.getElementById("countBidsByPeriod").value;
   
           
           var isBeforeDiscuss=document.getElementById("isBeforeDiscuss").value;
           var isDiscussDate=document.getElementById("isDiscussDate").value;
           var isPriceDate=document.getElementById("isPriceDate").value;
           var isBidDate=document.getElementById("isBidDate").value;
           var isMiddleDate=document.getElementById("isFirstMiddlePeriod").value;
           var isSecondMiddleDate=document.getElementById("isSecondMiddlePeriod").value;
           
           var auctionStepStatusKey=document.getElementById("bidStepStatusVal").value;
           
           console.log('auctionStepStatusKey');
           console.log(auctionStepStatusKey);
           
//           console.log(isBeforeDiscuss );
//           console.log(isDiscussDate + " isDiscussDate");
//           console.log(isMiddleDate + " isMiddleDate");
//           console.log(isPriceDate + " isPriceDate");
//           console.log(isSecondMiddleDate + " isSecondMiddleDate");
//           console.log("isBidDate");
//           console.log(isBidDate);
//
//           console.log(isPriceDate!=1 && isBidDate!=1 && isDiscussDate!=1 + " isPriceDate!=1 &&  isBidDate!=1");

           if(auctionStepStatusKey!='key.auctionStep.discuss' ){
        	   $("#sendCommentDiv").css("display", "none");
           }else{
        	   $("#sendCommentDiv").css("display", "block");
           }
           
           if(isBeforeDiscuss==1){
        	   $("#bidButton").css("display", "none");
        	//   $("#sendCommentDiv").css("display", "none");
        	 //  $("#periodMiddle").css("display", "block");
        	   $("#periodBeforeDiscuss").css("display", "block");
           }

           if(isMiddleDate==1){
        	   $("#bidButton").css("display", "none");
        	   $("#periodMiddle").css("display", "block");
        	//   $("#sendCommentDiv").css("display", "none");
           }

           if(isSecondMiddleDate==1){
        	   $("#bidButton").css("display", "none");
        	   $("#periodSecondMiddle").css("display", "block");
        	 //  $("#sendCommentDiv").css("display", "none");
           }
           
           
  
           if(isDiscussDate==1){
        	   $("#bidButton").css("display", "none");
        	   $("#divDiscussStatus").css("display", "block");
        	//   $("#sendCommentDiv").css("display", "block");
           }
           
           
           if(isPriceDate!=1 && isBidDate!=1 && isDiscussDate!=1){
        	  
        	   $("#bidButton").css("display", "none");
        	   $("#divDiscussStatus").css("display", "none");
        	//   $("#periodMiddle").css("display", "block");
           //    $("#sendCommentDiv").css("display", "none");
        	   
           }
           

           if(isPriceDate==1){
        	 
     	      $("#bidButton").css("display", "block");
     	      $("#countBidPeriodError").css("display", "none");
     	      $("#divPriceStatus").css("display", "block");
     	      $("#divSecondPeriod").css("display", "none");
     	      
     	      
     	      
        }
           

           if(isBidDate==1){
        	      $("#bidButton").css("display", "block");
        	      //$("#countBidPeriodError").css("display", "block");
        	      $("#divSecondPeriod").css("display", "block");
           }
        
        
        
           
           //timer
           var diferrence=document.getElementById("diferrence").value;
   		   var endms = parseInt(diferrence);
   	
     	  if(endms<0)
		   {
			
			 $("#timerTimer").css("display", "none");
			 $("#bidButton").css("display", "none");
	       
		   }
   		
       	   
       	       function getTimeRemaining(endtimer) {
       		   var t = endtimer;
       		   
       		 
       		   var seconds = Math.floor((t / 1000) % 60);
       		   var minutes = Math.floor((t / 1000 / 60) % 60);
       		   var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
       		   var days = Math.floor(t / (1000 * 60 * 60 * 24));
       		   return {
       		     'total': t,
       		     'days': days,
       		     'hours': hours,
       		     'minutes': minutes,
       		     'seconds': seconds
       		   };
       		   
       		 }

       		 function initializeClock(id) {
       		
       		   var clock = document.getElementById(id);
       		//  var daysSpan = clock.querySelector('.days');
       		   var hoursSpan = clock.querySelector('.hours');
       		   var minutesSpan = clock.querySelector('.minutes');
       		   var secondsSpan = clock.querySelector('.seconds');

       		   function updateClock() {	 
       			 endms = endms - 1000;
       			 var t = getTimeRemaining(endms);
       			 
       
       		     //daysSpan.innerHTML = t.days;
       		     hoursSpan.innerHTML = ('0' + t.hours).slice(-2);
       		     minutesSpan.innerHTML = ('0' + t.minutes).slice(-2);
       		     secondsSpan.innerHTML = ('0' + t.seconds).slice(-2);
                    console.log(secondsSpan.innerHTML);

       		     if (t.total <= 0) {
       		       clearInterval(timeinterval);
       		     }
       		   }

       		   updateClock();
       		   var timeinterval = setInterval(updateClock, 1000);
       		 }


       		 initializeClock('clockdiv');
       	   
       	   //end timer
    	   
    	   
    	   
    	   
    	   
    	   $("#sendComment").click(function() {
    		   $(this).find('input[type="submit"]').attr('disabled','disabled');
    	   });
    	   
    	  $("#comment").keydown(function(event) {
    		  console.log('comment');
    		  console.log( $(this).data);
			  var recordKey = $(this).data("reckey");
			  console.log(recordKey);
			  var comment=document.getElementById("comment").value; 
			  var auctionKey=document.getElementById("auctionKey").value; 
			  var tab=document.getElementById("tab-4"); 
			    if (((event.keyCode || event.which) == 13) && !event.shiftKey) {
			    	if(comment!=''){
			    		
				    	$.ajax({
				    	  type: "POST",
				    	  url: "/auction/auction/add_comment?auctionId="+recordKey+"&comment="+comment,
				    	  success: function (data) {
				    		  $("#loadDiv").load(location.href + " #loadDiv");
				    		  document.getElementById("comment").value='';
				    		  $("html, body").animate({ scrollTop: $(document).height() }, 1000);
				            }
				    
			  	        });  
						}
						else
							{
							
							}

			     }
			}).focus(function(){
			    if(this.value == "Write comment ..."){
			         this.value = "";
			         
			    }

			}).blur(function(){
			    if(this.value==""){
			         this.value = "Write comment ...";
			    }
			});     
    	 
    	   
    	 //  var hideAuctionStartView=document.getElementById("hideAuctionStartView");
    	 //  var hideAuctionEndView=document.getElementById("hideAuctionEndView");
    	
    	   
    	//   hideAuctionStartShow.value=hideAuctionStartView.value.substring(0,10);
    	//   hideAuctionEndShow.value=hideAuctionEndView.value.substring(0,10);
    	   
    	   
    	   var showLastBid=document.getElementById('showLastBid').value;
    	   console.log('showLastBid');
    	   console.log(showLastBid);
    	   var bidError=document.getElementById('bidError');
    	   
    	
    	
    	   if(showLastBid=="true"){
    		   
    		   $("#stepStep").css("display", "block");
    		   $("#lastBidBid").css("display", "block");
    		   $("#lastBidBidDate").css("display", "block");
    		   $("#divSecondPeriod").css("display", "none");
    		   
    		   $("#divLastShow").css("display", "block");
    		   $("#stepShow").css("display", "block");
    		 
    		  
    		   
    			  if(endms<0)
    			   {
    				
    				 $("#timerTimer").css("display", "none");

    		       
    			   }
    			  else{
    				  $("#timerTimer").css("display", "block");
    			  }

    	       if(countBidsByPeriod<=0){
        	       $("#bidButton").css("display", "none");
        	       $("#countBidPeriodError").css("display", "block");
        	       
                   }
    	       if(countBidsByPeriod>0 && activeAuction==1){ //activeAuction=='key.auctionStatus.active'
    	    	   $("#divSecondPeriod").css("display", "block");
    	 
    	       }
    		 
    	   }
    	  // $("#clockdiv").css("display", "none");
    	   
    	   /*   if(bidError.value==-1){
    		   console.log(bidError.value);
    		   $("#bidErrorJeradi").css("display", "block");
    		   $("#bidErrorLast").css("display", "none");
    	   }
    	   
    	   if(bidError.value==-2){
    		   console.log(bidError.value);
    		   $("#bidErrorLast").css("display", "block");
    		   $("#bidErrorJeradi").css("display", "none");
    	   }

    		$(".updateBid").click(function( event ) {
    			
						var recordKey = $(this).data("reckey");
						var bidValue=document.getElementById("bidbid").value;
						
						
						//if(bidError==-1){
						//	window.location.href="/auction/auction/bidView?auctionId="+recordKey;
						// $("#stepStep").css("display", "block");
						//}	
						
						//var startValue=document.getElementById("startValue").value;
						//var endValue=document.getElementById("endValue");
						//var step=document.getElementById("step").value;
						
					
								console.log("here");
										    	$.ajax({
										    	  type: "POST",
										    	  url: "/auction/auction/update_bid?recordKey="+recordKey+"&bidValue="+bidValue,
										    	  success: function (data) {
										    	window.location.href='/auction/user/client_home';
										    		  
										            }
										    
									  	        });
								 
									
    	       });*/

    		
    		 
    		  
    		   
		});    